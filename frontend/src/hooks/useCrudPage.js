import { ref } from 'vue'
import { MessagePlugin } from 'tdesign-vue-next'
import { usePagination } from './usePagination'
import { useConfirm } from './useConfirm'

/**
 * CRUD 列表页通用样板：列表数据 + loading + 分页 + 搜索 + 删除确认。
 * 表单弹窗的复杂逻辑（字段差异大）保留在各页面，此处只收敛最通用的部分。
 *
 * @param {Object} options
 * @param {Function} options.fetchList 加载列表函数（(query) => Promise<res>，res.data.list / res.data.total）
 * @param {Object} [options.defaultQuery] 默认查询参数
 * @param {Function} [options.deleteApi] 删除接口（(rowOrId) => Promise<res>）
 * @param {Function} [options.onDeleted] 删除成功后的回调（默认重新 load）
 * @param {Array<number>} [options.pageSizes] 页大小选项
 */
export function useCrudPage(options = {}) {
  const {
    fetchList,
    defaultQuery = {},
    deleteApi,
    onDeleted,
    pageSizes
  } = options

  const loading = ref(false)
  const list = ref([])
  const total = ref(0)
  const query = ref({ pageNum: 1, pageSize: 20, ...defaultQuery })

  const { currentPage, handleCurrentChange, handleSizeChange } = usePagination({
    query,
    fetch: load,
    pageSizes
  })

  async function load() {
    loading.value = true
    try {
      const res = await fetchList(query.value)
      list.value = res.data?.list ?? res.list ?? []
      total.value = res.data?.total ?? res.total ?? 0
    } finally {
      loading.value = false
    }
  }

  function search() {
    currentPage.value = 1
    query.value.pageNum = 1
    load()
  }

  function resetSearch() {
    query.value = { pageNum: 1, pageSize: query.value.pageSize, ...defaultQuery }
    search()
  }

  async function remove(rowOrId, message = '此操作将永久删除该记录, 是否继续?') {
    if (!deleteApi) return false
    const { confirm } = useConfirm()
    const ok = await confirm(message)
    if (!ok) return false
    const res = await deleteApi(rowOrId)
    if (res.code !== 200) {
      MessagePlugin.error(res.msg)
      return false
    }
    MessagePlugin.success(res.msg)
    if (onDeleted) onDeleted()
    else load()
    return true
  }

  return {
    loading,
    list,
    total,
    query,
    currentPage,
    pageSizes: pageSizes || [20, 100, 500],
    handleCurrentChange,
    handleSizeChange,
    load,
    search,
    resetSearch,
    remove
  }
}

export default useCrudPage

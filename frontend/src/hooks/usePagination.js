import { ref, isRef } from 'vue'

/**
 * 统一分页状态与事件处理。
 * 封装最常见的 admin 分页样板：currentPage/pageSizes 状态 + 翻页/改页大小事件，
 * 事件会自动把页码写回查询对象（兼容 ref 与 reactive）并触发加载函数。
 *
 * @param {Object} options
 * @param {Object|import('vue').Ref} options.query 查询对象（ref 或 reactive/普通对象），写入 pageNum/pageSize
 * @param {Function} options.fetch 翻页或改页大小后触发的加载函数
 * @param {Array<number>} [options.pageSizes] 页大小选项（默认 [10, 20, 30, 50, 100]）
 * @param {number} [options.pageSize] 初始页大小（默认取 pageSizes[0]）
 * @param {boolean} [options.resetToFirstOnSizeChange] 改页大小后回到第一页（默认 true）
 */
export function usePagination(options = {}) {
  const {
    query,
    fetch,
    pageSizes = [10, 20, 30, 50, 100],
    pageSize = pageSizes[0],
    resetToFirstOnSizeChange = true
  } = options

  const currentPage = ref(1)

  const setQuery = (key, value) => {
    if (!query || typeof query !== 'object') return
    if (isRef(query)) {
      query.value[key] = value
    } else {
      query[key] = value
    }
  }

  const handleCurrentChange = (page) => {
    currentPage.value = page
    setQuery('pageNum', page)
    fetch && fetch()
  }

  const handleSizeChange = (size) => {
    setQuery('pageSize', size)
    if (resetToFirstOnSizeChange) {
      currentPage.value = 1
      setQuery('pageNum', 1)
    }
    fetch && fetch()
  }

  // 初始页大小回写，保证与模板 :page-size 绑定一致
  if (query && pageSize !== undefined) setQuery('pageSize', pageSize)

  return {
    currentPage,
    pageSizes,
    handleCurrentChange,
    handleSizeChange
  }
}

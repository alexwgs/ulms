<script>
import { h, ref, computed, watch } from 'vue'
import { Table, EnhancedTable } from 'tdesign-vue-next'

const SELECTION_TYPES = new Set(['multiple', 'single'])

export default {
  name: 'CustomTable',
  inheritAttrs: false,
  props: {
    data: { type: Array, default: () => [] },
    // 可选的外部选中态控制（TDesign: Array<string | number>）
    selectedRowKeys: { type: Array, default: undefined }
  },
  setup(props, { slots, attrs, expose }) {
    const tableRef = ref(null)
    const internalSelectedRowKeys = ref([])
    const internalFilterValue = ref({})
    // 每次渲染时重建的 Element 风格列筛选映射（非响应式，避免渲染循环）
    let elementFilterMap = {}

    const currentRowKey = computed(() => attrs.rowKey || 'id')

    // Element tree-props/expand-all -> TDesign tree 配置
    const hasExpandAllAttr = () => 'expand-all' in attrs || 'expandAll' in attrs
    const treeExpandAll = ref(hasExpandAllAttr())
    // 重挂载 key：展开/收起所有节点时通过 key 变化让 EnhancedTable 重新初始化
    const tableKey = ref(0)

    const treeConfig = computed(() => {
      const tp = attrs['tree-props'] || attrs.treeProps
      if (!tp) return undefined
      return {
        childrenKey: tp.children || 'children',
        treeNodeColumnIndex: tp.treeNodeColumnIndex ?? 0,
        defaultExpandAll: treeExpandAll.value
      }
    })

    // 带 expand-all 的树表：数据加载完成后重挂载一次，让 TDesign 的 defaultExpandAll 真正生效
    // （TDesign 在空数据时执行 expandAll 后会把 isDefaultExpandAllExecute 置位，后续数据到达不再展开）
    let treeExpandInitialized = false
    watch(
      () => props.data,
      (rows) => {
        if (
          treeConfig.value &&
          treeExpandAll.value &&
          Array.isArray(rows) &&
          rows.length > 0 &&
          !treeExpandInitialized
        ) {
          treeExpandInitialized = true
          tableKey.value++
        }
      },
      { flush: 'post' }
    )

    const isSelectionControlled = () => props.selectedRowKeys !== undefined

    // 简单路径取值，支持 'a.b.c'（与 TDesign lodash get 语义一致）
    const getByPath = (obj, path) => {
      if (!obj || !path) return undefined
      return String(path)
        .split('.')
        .reduce((acc, key) => (acc == null ? undefined : acc[key]), obj)
    }

    // Element `sortable`（客户端排序）-> TDesign 默认比较函数
    const defaultSorter = (key) => (a, b) => {
      const va = getByPath(a, key)
      const vb = getByPath(b, key)
      if (va == null && vb == null) return 0
      if (va == null) return 1
      if (vb == null) return -1
      if (typeof va === 'number' && typeof vb === 'number') return va - vb
      return String(va).localeCompare(String(vb), 'zh-Hans-CN')
    }

    const selectedKeys = computed(() =>
      isSelectionControlled() ? props.selectedRowKeys || [] : internalSelectedRowKeys.value
    )

    const rowKeyOf = (rowOrKey) =>
      rowOrKey && typeof rowOrKey === 'object' ? rowOrKey[currentRowKey.value] : rowOrKey

    const syncSelection = (keys) => {
      if (isSelectionControlled()) {
        if (typeof attrs['onUpdate:selectedRowKeys'] === 'function') {
          attrs['onUpdate:selectedRowKeys'](keys)
        }
      } else {
        internalSelectedRowKeys.value = keys
      }
    }

    // 程序化变更选中后，同步转发 select-change（页面依赖 ctx.selectedRowData 统计已选）
    const notifySelectChange = (keys) => {
      if (typeof attrs.onSelectChange !== 'function') return
      const selectedRowData = keys
        .map((k) => props.data.find((row) => getByPath(row, currentRowKey.value) === k))
        .filter(Boolean)
      attrs.onSelectChange(keys, { selectedRowData, type: 'check' })
    }

    const clearSelection = () => {
      syncSelection([])
      notifySelectChange([])
    }

    const setAllExpanded = (state) => {
      treeExpandAll.value = state
      tableKey.value++
    }

    const toggleRowSelection = (rowOrKey, selected) => {
      const key = rowKeyOf(rowOrKey)
      if (key === undefined) return
      const current = selectedKeys.value
      const exists = current.includes(key)
      let next
      if (selected === undefined) {
        next = exists ? current.filter((k) => k !== key) : [...current, key]
      } else if (selected) {
        next = exists ? current : [...current, key]
      } else {
        next = exists ? current.filter((k) => k !== key) : current
      }
      syncSelection(next)
      notifySelectChange(next)
    }

    // 兼容 API：getData() 返回全量数据，getData(key) 返回匹配行
    const getData = (key) =>
      key === undefined
        ? props.data
        : props.data.find((row) => row[currentRowKey.value] === key)

    // 兼容 Element Plus 的 doLayout()：TDesign 表格会随数据自动重排，无需手动触发
    const doLayout = () => {}

    const handleSelectChange = (keys, ctx) => {
      if (!isSelectionControlled()) internalSelectedRowKeys.value = keys
      if (typeof attrs.onSelectChange === 'function') attrs.onSelectChange(keys, ctx)
    }

    const handleFilterChange = (value, ctx) => {
      const colKey = ctx?.col?.colKey
      if (colKey && elementFilterMap[colKey]) {
        internalFilterValue.value = { ...(value || {}) }
      }
      if (typeof attrs.onFilterChange === 'function') attrs.onFilterChange(value, ctx)
    }

    // Element `{ prop, order }` -> TDesign `{ sortBy, descending }`
    const normalizeSort = (sort) => {
      if (sort && typeof sort === 'object' && !Array.isArray(sort) && 'prop' in sort && 'order' in sort) {
        return { sortBy: sort.prop, descending: String(sort.order) === 'descending' }
      }
      return sort
    }

    expose({ clearSelection, toggleRowSelection, getData, setAllExpanded, doLayout })

    return () => {
      const defaultVNodes = slots.default ? slots.default() : []
      const columns = []
      const columnSlots = {}
      const headerSlotFns = new Map()
      elementFilterMap = {}
      let colIndex = 0

      for (const vnode of defaultVNodes) {
        if (!vnode || !vnode.type) continue

        const typeName = vnode.type.__name || vnode.type.name || ''
        if (typeName !== 'TableColumn') continue

        const colDef = { ...(vnode.props || {}) }

        // Element Plus prop -> TDesign colKey
        if (colDef.prop && !colDef.colKey) {
          colDef.colKey = colDef.prop
        }
        // Element Plus property -> TDesign colKey（兼容旧属性名写法）
        if (colDef.property && !colDef.colKey && !colDef.prop) {
          colDef.colKey = colDef.property
        }
        // Element Plus label -> TDesign title
        if (colDef.label && !colDef.title) {
          colDef.title = colDef.label
        }

        let colKey = colDef.colKey || colDef.prop

        // Element `sortable` / `sort-method` -> TDesign `sorter`
        // 裸 sortable（Element 语义=客户端排序）映射为默认比较函数；
        // sortable="custom" 仅触发 sort-change，交由服务端处理。
        if (colDef.sortable && !colDef.sorter) {
          const key = colDef.colKey || colDef.prop
          if (colDef.sortable === 'custom' || !key) {
            colDef.sorter = true
          } else {
            colDef.sorter = defaultSorter(key)
          }
        }
        if (typeof colDef.sortMethod === 'function' && !colDef.sorter) {
          colDef.sorter = colDef.sortMethod
        }
        delete colDef.sortable
        delete colDef.sortMethod

        // Element `selectable`（行可选）-> TDesign 选择列 `disabled`
        if (SELECTION_TYPES.has(colDef.type) && typeof colDef.selectable === 'function' && !colDef.disabled) {
          const selectable = colDef.selectable
          colDef.disabled = ({ row }) => !selectable(row)
        }
        delete colDef.selectable

        // Element `filters` + `filter-method` -> TDesign 列筛选 + 客户端过滤
        if (Array.isArray(colDef.filters)) {
          const list = colDef.filters.map((f) => ({
            label: f.text ?? f.label ?? f.value,
            value: f.value
          }))
          if (!colDef.filter) {
            colDef.filter = { type: 'single', list, showConfirmAndReset: true }
          }
          if (typeof colDef.filterMethod === 'function' && colDef.colKey) {
            elementFilterMap[colDef.colKey] = colDef.filterMethod
          }
        }
        delete colDef.filters
        delete colDef.filterMethod

        // Extract slot functions from the TableColumn's children
        const children = vnode.children
        if (children && typeof children === 'object' && !Array.isArray(children)) {
          // Columns with custom templates need a colKey for TDesign's slot resolver.
          // If none is provided, generate a stable fallback key.
          const hasCellSlot = typeof children.default === 'function'
          if (hasCellSlot && !colKey) {
            colKey = `_col_${colIndex}`
            colDef.colKey = colKey
          }

          for (const [slotName, slotFn] of Object.entries(children)) {
            if (typeof slotFn !== 'function') continue

            if (slotName === 'default' && colKey) {
              columnSlots[colKey] = (scope) => {
                return slotFn({ ...scope, $index: scope.rowIndex })
              }
            } else if (slotName === 'header') {
              headerSlotFns.set(colIndex, slotFn)
            }
          }
        }

        columns.push(colDef)
        colIndex++
      }

      // TDesign 的表头插槽按 `col.title` 查找，因此把自定义表头模板
      // 注册到列标题下（无标题列使用合成标题）。
      for (const [idx, headerFn] of headerSlotFns) {
        const col = columns[idx]
        if (!col) continue
        let headerKey = typeof col.title === 'string' && col.title ? col.title : null
        if (!headerKey) {
          headerKey = `_header_${idx}`
          col.title = headerKey
        }
        columnSlots[headerKey] = headerFn
      }

      // Merge pass-through slots (excluding default which we consumed)
      const tableSlots = {}
      for (const key of Object.keys(slots)) {
        if (key !== 'default') {
          tableSlots[key] = slots[key]
        }
      }
      Object.assign(tableSlots, columnSlots)

      // Element 风格客户端筛选模拟：TDesign 不会自动过滤行数据
      let dataForTable = props.data
      const filterKeys = Object.keys(elementFilterMap)
      if (filterKeys.length && Array.isArray(props.data)) {
        let list = props.data
        for (const fk of filterKeys) {
          const value = internalFilterValue.value[fk]
          if (value === undefined || value === null || value === '') continue
          const method = elementFilterMap[fk]
          list = list.filter((row) => method(value, row))
        }
        dataForTable = list
      }
      const {
        'tree-props': treePropsAttr,
        treeProps: treePropsCamel,
        'expand-all': expandAllAttr,
        expandAll: expandAllCamel,
        ...restAttrs
      } = attrs
      const tableProps = {
        ref: tableRef,
        ...restAttrs,
        columns,
        data: dataForTable,
        key: tableKey.value
      }
      if (treeConfig.value) {
        tableProps.tree = treeConfig.value
      }
      tableProps.sort = normalizeSort(tableProps.sort)
      tableProps.defaultSort = normalizeSort(tableProps.defaultSort)
      tableProps.selectedRowKeys = selectedKeys.value
      tableProps.onSelectChange = handleSelectChange
      tableProps.onFilterChange = handleFilterChange

      // TDesign 树形表格需要 EnhancedTable（普通 Table 不支持 tree 配置）
      const tableComponent = treeConfig.value ? EnhancedTable : Table
      return h(tableComponent, tableProps, tableSlots)
    }
  }
}
</script>

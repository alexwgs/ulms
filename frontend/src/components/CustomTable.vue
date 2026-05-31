<script>
import { h, ref } from 'vue'
import { Table } from 'tdesign-vue-next'

export default {
  name: 'CustomTable',
  inheritAttrs: false,
  props: {
    data: { type: Array, default: () => [] }
  },
  setup(props, { slots, attrs, expose }) {
    const tableRef = ref(null)

    expose({
      clearSelection: () => tableRef.value?.clearSelection(),
      getData: (key) => tableRef.value?.getData(key)
    })

    return () => {
      const defaultVNodes = slots.default ? slots.default() : []
      const columns = []
      const columnSlots = {}
      let colIndex = 0

      for (const vnode of defaultVNodes) {
        if (!vnode || !vnode.type) continue

        const typeName = vnode.type.__name || vnode.type.name || ''
        if (typeName !== 'TableColumn') continue

        const colDef = { ...(vnode.props || {}) }

        // Normalize: Element Plus prop → TDesign colKey
        if (colDef.prop && !colDef.colKey) {
          colDef.colKey = colDef.prop
        }
        // Normalize: Element Plus label → TDesign title
        if (colDef.label && !colDef.title) {
          colDef.title = colDef.label
        }

        let colKey = colDef.colKey || colDef.prop

        // Extract slot functions from the TableColumn's children
        const children = vnode.children
        if (children && typeof children === 'object' && !Array.isArray(children)) {
          // Check if this column has a default slot (custom cell template)
          const hasCellSlot = typeof children.default === 'function'

          // Columns with custom templates need a colKey for TDesign's slot resolver.
          // If none is provided, generate a stable fallback key.
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
            } else if (slotName === 'header' && colKey) {
              columnSlots[`${colKey}-title`] = slotFn
            }
          }
        }

        columns.push(colDef)
        colIndex++
      }

      // Merge pass-through slots (excluding default which we consumed)
      const tableSlots = {}
      for (const key of Object.keys(slots)) {
        if (key !== 'default') {
          tableSlots[key] = slots[key]
        }
      }
      Object.assign(tableSlots, columnSlots)

      return h(Table, { ref: tableRef, ...attrs, columns, data: props.data }, tableSlots)
    }
  }
}
</script>

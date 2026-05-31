<template>
  <!-- Stub: renders nothing. CustomTable reads VNode props from this component. -->
</template>

<script setup>
defineProps({
  colKey: String,
  prop: String,
  type: String,
  label: String,
  title: String,
  width: [String, Number],
  minWidth: [String, Number],
  maxWidth: [String, Number],
  fixed: String,
  align: String,
  ellipsis: [Boolean, Object],
  sortable: [Boolean, String, Object],
  sortType: String,
  sorter: [Boolean, Function],
  filter: [Object, Array],
  resizable: Boolean,
  order: Number
})
</script>

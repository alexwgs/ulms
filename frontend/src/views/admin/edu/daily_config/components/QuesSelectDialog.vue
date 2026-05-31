<template>
  <t-dialog
    header="请选择每日一招题目"
    v-model:visible="dialogVisible"
    width="50%"
    :close-on-overlay-click="false"
  >
    <t-input
      placeholder="输入要搜索的内容"
      size="small"
      v-model="quesQuerInfo.query"
    >
      <template #prepend>
        <t-select
          v-model="quesQuerInfo.querytype"
          size="small"
          style="width: 100px"
          placeholder="请选择"
        >
          <t-option label="题目" value="quesStem"></t-option>
          <t-option label="题目编号" value="quesCode"></t-option>
        </t-select>
      </template>
      <template #append>
        <t-button size="small" @click="getTableList"><template #icon><DynamicIcon name="search" /></template></t-button>
      </template>
    </t-input>
    <CustomTable rowKey="id" :data="questionTable" height="400px">
      <TableColumn property="quesStem" label="题干"></TableColumn>
      <TableColumn property="quesStem" label="操作" width="80">
        <template #default="scope">
          <t-button theme="primary" size="small" @click="selectQues(scope.row)">
            选择
          </t-button>
        </template>
      </TableColumn>
    </CustomTable>
    <t-pagination
      @current-change="handleCurrentChange"
      :current="currentPage"
      :page-size="20"

      :total="total"
    ></t-pagination>
  </t-dialog>
</template>

<script setup>
import { ref, reactive, computed } from 'vue'
import { MessagePlugin } from 'tdesign-vue-next'
import { dailyConfigApi } from '@/api/edu/dailyConfig'

// Props
const props = defineProps({
  visible: {
    type: Boolean,
    default: false
  }
})

// Emits
const emit = defineEmits(['update:visible', 'select'])

// Computed
const dialogVisible = computed({
  get: () => props.visible,
  set: (val) => emit('update:visible', val)
})

// Reactive data
const quesQuerInfo = reactive({
  quesStat: '1',
  querytype: 'quesStem',
  query: '',
  orderType: ' desc',
  order: ' libCode ',
  pageNum: 1
})

const questionTable = ref([])
const total = ref(0)
const currentPage = ref(1)

// Methods
const getTableList = async () => {
  if (quesQuerInfo.query.length < 1) {
    return MessagePlugin.error('请输入查询条件！')
  }

  const res = await dailyConfigApi.getSensitiveQuesList(quesQuerInfo)
  if (res.code !== 200) {
    return MessagePlugin.error(res.msg)
  }

  questionTable.value = res.data.list
  total.value = res.data.total
}

const handleCurrentChange = (page) => {
  currentPage.value = page
  quesQuerInfo.pageNum = page
  getTableList()
}

const selectQues = (row) => {
  emit('select', row)
  emit('update:visible', false)
}
</script>

<style scoped></style>

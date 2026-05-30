<template>
  <el-dialog
    title="请选择每日一招题目"
    v-model="dialogVisible"
    width="50%"
    :close-on-click-modal="false"
  >
    <el-input
      placeholder="输入要搜索的内容"
      size="small"
      v-model="quesQuerInfo.query"
    >
      <template #prepend>
        <el-select
          v-model="quesQuerInfo.querytype"
          size="small"
          style="width: 100px"
          placeholder="请选择"
        >
          <el-option label="题目" value="quesStem"></el-option>
          <el-option label="题目编号" value="quesCode"></el-option>
        </el-select>
      </template>
      <template #append>
        <el-button icon="search" size="small" @click="getTableList"></el-button>
      </template>
    </el-input>
    <el-table :data="questionTable" height="400px">
      <el-table-column property="quesStem" label="题干"></el-table-column>
      <el-table-column property="quesStem" label="操作" width="80">
        <template #default="scope">
          <el-button type="primary" size="small" @click="selectQues(scope.row)">
            选择
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
      @current-change="handleCurrentChange"
      :current-page="currentPage"
      :page-size="20"
      layout="total, prev, pager, next, jumper"
      :total="total"
    ></el-pagination>
  </el-dialog>
</template>

<script setup>
import { ref, reactive, computed } from 'vue'
import { ElMessage } from 'element-plus'
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
    return ElMessage.error('请输入查询条件！')
  }

  const res = await dailyConfigApi.getSensitiveQuesList(quesQuerInfo)
  if (res.code !== 200) {
    return ElMessage.error(res.msg)
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

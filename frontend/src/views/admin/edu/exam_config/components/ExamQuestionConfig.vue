<template>
  <div>
    <el-dialog
      title="试题配置"
      v-model="dialogFormVisible"
      :close-on-click-modal="false"
      width="70%"
    >
      <el-row :gutter="10">
        <el-col :span="8">
          <el-card class="box-card">
            <el-input
              size="small"
              placeholder="输入关键字进行过滤"
              v-model="filterText"
            />
            <el-tree
              :data="treeData"
              ref="treeRef"
              node-key="libCode"
              highlight-current
              @node-click="nodeclick"
              :filter-node-method="filterNode"
            >
              <template #default="{ node, data }">
                <span class="custom-tree-node">
                  <span
                    >{{ data.libName }}
                    <el-tag v-if="data.libLevel === 2" size="small">{{
                      data.quesNum
                    }}</el-tag></span
                  >
                </span>
              </template>
            </el-tree>
          </el-card>
        </el-col>
        <el-col :span="16">
          <el-row style="margin-bottom: 5px">
            <el-col :span="16"
              ><el-tag>选择：{{ libForm.libName }}</el-tag></el-col
            >
            <el-col :span="6"
              ><el-input-number
                v-model="libForm.quesNum"
                style="width: 100px"
                size="small"
                :min="1"
                :max="100"
                label="题目数"
            /></el-col>
            <el-col :span="2"
              ><el-button
                type="primary"
                size="small"
                icon="plus"
                @click="addLib()"
            /></el-col>
          </el-row>
          <el-table
            ref="tableRef"
            :data="tableData"
            show-summary
            size="small"
            height="415px"
            border
            style="width: 100%"
          >
            <!-- <el-table-column prop="libCode" label="题库编号" width="180"> </el-table-column> -->
            <el-table-column prop="libName" label="题库名称"> </el-table-column>
            <el-table-column prop="quesLimit" label="题库量" width="90">
            </el-table-column>
            <el-table-column prop="quesNum" label="抽取量" width="90">
            </el-table-column>
            <el-table-column label="操作" width="80">
              <template #default="scope">
                <el-button
                  type="danger"
                  size="small"
                  icon="delete"
                  @click="deleteLib(scope.row)"
                />
              </template>
            </el-table-column>
          </el-table>
        </el-col>
      </el-row>
      <template #footer>
        <span class="dialog-footer">
          <el-button size="small" @click="dialogFormVisible = false"
            >取 消</el-button
          >
          <el-button size="small" type="primary" @click="submit()"
            >提 交</el-button
          >
        </span>
      </template>
    </el-dialog>
  </div>
</template>
<script setup>
import { ref, reactive, watch, nextTick } from 'vue'
import { ElMessage } from 'element-plus'
import { examConfigApi } from '@/api/edu/examConfig'
import { questionLibApi } from '@/api/edu/questionLib'
// 声明要触发的事件
const emit = defineEmits(['refresh'])
const treeRef = ref(null)
const tableRef = ref(null)
const dialogFormVisible = ref(false)
const filterText = ref('')
const examCode = ref('')
const treeData = ref([])
const tableData = ref([])

const libForm = reactive({
  libName: '',
  libCode: '',
  examCode: '',
  quesLimit: 0,
  quesNum: 1
})

watch(filterText, (val) => {
  treeRef.value?.filter(val)
})

const show = (code) => {
  examCode.value = code
  libForm.examCode = code
  getTreeDataList().then(() => {
    getTableData()
  })
  dialogFormVisible.value = true
}

const getTreeDataList = async () => {
  try {
    const res = await questionLibApi.getTreeData()
    if (res.code !== 200) return ElMessage.error(res.msg)
    treeData.value = res.data
  } catch (error) {
    console.error(error)
    ElMessage.error('获取树数据失败')
  }
}

const getTableData = async () => {
  try {
    const res = await examConfigApi.getExamQuestionTemp(examCode.value)
    if (res.code !== 200) return ElMessage.error(res.msg)
    const datas = res.data
    for (const item of datas) {
      await nextTick(() => {
        const record = treeRef.value.getNode(item.libCode)
        if (!record) {
          item.quesLimit = 0
          item.libName = '题库取路径错误或已被移除,请重新选择试题抽取路径！'
          ElMessage.error(
            '获取配置的试题抽取路径错误或已被移除！请重新选择试题抽取路径。'
          )
        } else {
          item.libName =
            record.parent.parent.data.libName +
            '->' +
            record.parent.data.libName +
            '->' +
            record.data.libName
          item.quesLimit = record.data.quesNum
        }
      })
    }
    tableData.value = datas
  } catch (error) {
    console.error(error)
    ElMessage.error('获取表格数据失败')
  }
}

const filterNode = (value, data) => {
  if (!value) return true
  return data.libName.indexOf(value) !== -1
}

const nodeclick = (obj, node, self) => {
  if (obj.libLevel === 2) {
    libForm.libName =
      node.parent.parent.data.libName +
      '->' +
      node.parent.data.libName +
      '->' +
      obj.libName
    libForm.libCode = obj.libCode
    libForm.quesLimit = obj.quesNum
    libForm.examCode = examCode.value
  }
}

const addLib = () => {
  if (tableData.value.find((e) => e.libCode === libForm.libCode))
    return ElMessage.warning('当前已添加过此题库！')
  if (libForm.quesLimit < libForm.quesNum)
    return ElMessage.warning('抽取题目数量超过题库题目数量！')
  tableData.value.push(JSON.parse(JSON.stringify(libForm)))
  updateTable()
}

const updateTable = () => {
  nextTick(() => {
    tableRef.value?.doLayout()
  })
}

const deleteLib = (row) => {
  const index = tableData.value.findIndex((e) => e.libCode === row.libCode)
  if (index !== -1) {
    tableData.value.splice(index, 1)
    updateTable()
  }
}

const submit = async () => {
  try {
    // 校验抽取题目数量是否超过题库题目数量
    let quesNum = 0
    let quesLimit = 0
    tableData.value.forEach((item) => {
      quesNum += item.quesNum
      quesLimit += item.quesLimit
    })
    if (quesNum > quesLimit)
      return ElMessage.warning('抽取题目数量超过题库题目数量！')
    const res = await examConfigApi.saveExamQuestionTemp(
      examCode.value,
      tableData.value
    )
    if (res.code !== 200) return ElMessage.error(res.msg)
    ElMessage.success(res.msg)
    // ../index.vue 方法getExamInfoList 刷新父级列表
    emit('refresh')
    dialogFormVisible.value = false
  } catch (error) {
    ElMessage.error('提交失败，请重试')
  }
}

defineExpose({
  show
})
</script>
<style lang="less" scoped>
.box-card {
  height: 450px;
  overflow: auto;
}

.custom-tree-node {
  flex: 1;
  display: flex;
  align-items: center;
  justify-content: space-between;
  font-size: 14px;
  padding-right: 8px;
}
</style>

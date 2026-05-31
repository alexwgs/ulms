<template>
  <div>
    <t-dialog
      header="试题配置"
      v-model:visible="dialogFormVisible"
      :close-on-overlay-click="false"
      width="70%"
    >
      <t-row :gutter="10">
        <t-col :span="4">
          <t-card class="box-card">
            <t-input
              size="small"
              placeholder="输入关键字进行过滤"
              v-model="filterText"
            />
            <t-tree
              :data="treeData"
              ref="treeRef"
              :keys="{ value: 'libCode', label: 'libName', children: 'children' }"
              activable
              @click="nodeclick"
              :filter="filterNode"
            >
              <template #default="{ node }">
                <span class="custom-tree-node">
                  <span
                    >{{ node.data.libName }}
                    <t-tag v-if="node.data.libLevel === 2" size="small">{{
                      node.data.quesNum
                    }}</t-tag></span
                  >
                </span>
              </template>
            </t-tree>
          </t-card>
        </t-col>
        <t-col :span="8">
          <t-row style="margin-bottom: 5px">
            <t-col :span="8"
              ><t-tag>选择：{{ libForm.libName }}</t-tag></t-col
            >
            <t-col :span="3"
              ><t-input-number
                v-model="libForm.quesNum"
                style="width: 100px"
                size="small"
                :min="1"
                :max="100"
                label="题目数"
            /></t-col>
            <t-col :span="1"
              ><t-button
                theme="primary"
                size="small" @click="addLib()"
            ><template #icon><DynamicIcon name="plus" /></template></t-button></t-col>
          </t-row>
          <CustomTable rowKey="id"
            ref="tableRef"
            :data="tableData"
            show-summary
            size="small"
            height="415px"
            border
            style="width: 100%">
            <!-- <TableColumn colKey="libCode" label="题库编号" width="180"> </TableColumn> -->
            <TableColumn colKey="libName" label="题库名称"> </TableColumn>
            <TableColumn colKey="quesLimit" label="题库量" width="90">
            </TableColumn>
            <TableColumn colKey="quesNum" label="抽取量" width="90">
            </TableColumn>
            <TableColumn label="操作" width="80">
              <template #default="scope">
                <t-button
                  theme="danger"
                  size="small" @click="deleteLib(scope.row)"
                ><template #icon><DynamicIcon name="delete" /></template></t-button>
              </template>
            </TableColumn>
          </CustomTable>
        </t-col>
      </t-row>
      <template #footer>
        <span class="dialog-footer">
          <t-button size="small" @click="dialogFormVisible = false"
            >取 消</t-button
          >
          <t-button size="small" theme="primary" @click="submit()"
            >提 交</t-button
          >
        </span>
      </template>
    </t-dialog>
  </div>
</template>
<script setup>
import { ref, reactive, watch, nextTick } from 'vue'
import { MessagePlugin } from 'tdesign-vue-next'
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
  treeRef.value?.refresh()
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
    if (res.code !== 200) return MessagePlugin.error(res.msg)
    treeData.value = res.data
  } catch (error) {
    console.error(error)
    MessagePlugin.error('获取树数据失败')
  }
}

const getTableData = async () => {
  try {
    const res = await examConfigApi.getExamQuestionTemp(examCode.value)
    if (res.code !== 200) return MessagePlugin.error(res.msg)
    const datas = res.data
    for (const item of datas) {
      await nextTick(() => {
        const record = treeRef.value.getItem(item.libCode)
        if (!record) {
          item.quesLimit = 0
          item.libName = '题库取路径错误或已被移除,请重新选择试题抽取路径！'
          MessagePlugin.error(
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
    MessagePlugin.error('获取表格数据失败')
  }
}

const filterNode = (node) => {
  if (!filterText.value) return true
  return node.data.libName.indexOf(filterText.value) !== -1
}

const nodeclick = (context) => {
      const { node: treeNode } = context;
      const obj = treeNode.data;
  if (obj.libLevel === 2) {
    libForm.libName =
      treeNode.parent.parent.data.libName +
      '->' +
      treeNode.parent.data.libName +
      '->' +
      obj.libName
    libForm.libCode = obj.libCode
    libForm.quesLimit = obj.quesNum
    libForm.examCode = examCode.value
  }
}

const addLib = () => {
  if (tableData.value.find((e) => e.libCode === libForm.libCode))
    return MessagePlugin.warning('当前已添加过此题库！')
  if (libForm.quesLimit < libForm.quesNum)
    return MessagePlugin.warning('抽取题目数量超过题库题目数量！')
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
      return MessagePlugin.warning('抽取题目数量超过题库题目数量！')
    const res = await examConfigApi.saveExamQuestionTemp(
      examCode.value,
      tableData.value
    )
    if (res.code !== 200) return MessagePlugin.error(res.msg)
    MessagePlugin.success(res.msg)
    // ../index.vue 方法getExamInfoList 刷新父级列表
    emit('refresh')
    dialogFormVisible.value = false
  } catch (error) {
    MessagePlugin.error('提交失败，请重试')
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

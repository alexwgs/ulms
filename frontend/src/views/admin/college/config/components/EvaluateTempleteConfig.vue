<template>
  <div>
    <t-row :gutter="15" style="margin-bottom: 10px">
      <t-col :span="12">
        <t-select v-model="tempInfo.tempStat" size="small" style="width: 100px; margin-right: 10px"
          placeholder="请选择模板状态" @change="getTempleteList">
          <t-option label="生效" :value="1"></t-option>
          <t-option label="失效" :value="0"></t-option>
        </t-select>
        <t-button theme="primary" size="small" @click="handleAdd"><template #icon><DynamicIcon name="plus" /></template>新增评价模板</t-button>
      </t-col>
    </t-row>

    <CustomTable rowKey="id" :data="tempList" size="small" height="calc(100vh - 250px)" stripe style="width: 100%">
      <TableColumn colKey="tempName" label="模板名称" ellipsis></TableColumn>
      <TableColumn colKey="tempStat" label="状态" width="90px" ellipsis>
        <template #default="scope">
          <t-tag :theme="scope.row.tempStat ? 'success' : 'danger'" size="small">{{ scope.row.tempStat ? '生效' : '失效'
          }}</t-tag>
        </template>
      </TableColumn>
      <TableColumn label="操作" fixed="right" width="100px">
        <template #default="scope">
          <t-button theme="primary" size="small" @click="handleEdit(scope.row)" shape="circle"><template #icon><DynamicIcon name="edit" /></template></t-button>
        </template>
      </TableColumn>
    </CustomTable>

    <!-- 模板管理弹窗 -->
    <t-dialog header="模板管理" v-model:visible="tempInfo.tempDialogVisible" width="50%">
      <t-form ref="tempFormRef" :data="tempInfo" :rules="rules" size="small" label-width="100px">
        <t-form-item label="模板名称" name="tempName">
          <t-input v-model="tempInfo.tempName" placeholder="请输入名称" :maxlength="100" clearable
            :style="{ width: '100%' }"></t-input>
        </t-form-item>
        <t-form-item label="操作" name="sort">
          <t-button-group>
            <t-button theme="primary" @click="addQuestion(1)"><template #icon><DynamicIcon name="arrow-left" /></template>添加评分</t-button>
            <t-button theme="primary" @click="addQuestion(2)">添加问答<template #suffix><DynamicIcon name="arrow-right" /></template></t-button>
          </t-button-group>
        </t-form-item>
        <t-form-item :label="'题目' + (index + 1)" v-for="(item, index) in tempInfo.record" :key="index">
          <t-input placeholder="题干内容" v-model="item.quesCont">
            <template #prepend>{{
              item.quesType == 1 ? '评分题目' : '开放问题'
            }}</template>
            <template #append>
              <t-button @click="removeQuestion(index)"><template #icon><DynamicIcon name="delete" /></template></t-button>
            </template>
          </t-input>
        </t-form-item>
        <t-form-item label="状态" name="tempStat">
          <t-select v-model="tempInfo.tempStat" placeholder="请选择状态" clearable :style="{ width: '100%' }">
            <t-option label="有效" :value="1"></t-option>
            <t-option label="无效" :value="0"></t-option>
          </t-select>
        </t-form-item>
      </t-form>
      <template #footer>
        <div class="dialog-footer">
          <t-button size="small" @click="tempInfo.tempDialogVisible = false">取 消</t-button>
          <t-button size="small" theme="primary" @click="handleSubmit">确 定</t-button>
        </div>
      </template>
    </t-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { MessagePlugin } from 'tdesign-vue-next'
import { evaluateApi } from '@/api/college/evaluate'

const tempList = ref([])
const tempFormRef = ref(null)
const isEdit = ref(false)

const tempInfo = reactive({
  tempName: '',
  tempId: '',
  tempStat: 1,
  tempDialogVisible: false,
  record: []
})

const rules = reactive({
  tempName: [{ required: true, message: '请输入模板名称', trigger: 'blur' }],
  tempStat: [{ required: true, message: '请选择状态', trigger: 'change' }]
})

// 获取评价模板列表
const getTempleteList = async () => {
  try {
    const res = await evaluateApi.getEvaluateTemplateList({
      tempStat: tempInfo.tempStat
    })
    if (res.code !== 200) return MessagePlugin.error(res.msg)
    tempList.value = res.data
  } catch (error) {
    MessagePlugin.error('获取评价模板列表失败')
  }
}

// 添加评价模板
const handleAdd = () => {
  isEdit.value = false
  tempInfo.record = []
  tempInfo.tempName = ''
  tempInfo.tempId = ''
  tempInfo.tempStat = 1
  tempInfo.tempDialogVisible = true
}

// 编辑评价模板
const handleEdit = async (row) => {
  isEdit.value = true
  tempInfo.tempId = row.tempId
  tempInfo.tempName = row.tempName
  tempInfo.tempStat = row.tempStat
  try {
    const res = await evaluateApi.getEvaluateTemplate(row.tempId)
    if (res.code !== 200) return MessagePlugin.error(res.msg)
    tempInfo.record = res.data
    tempInfo.tempDialogVisible = true
  } catch (error) {
    MessagePlugin.error('获取评价模板详情失败')
  }
}

// 添加题目
const addQuestion = (type) => {
  tempInfo.record.push({ quesType: type, quesCont: '' })
}

// 删除题目
const removeQuestion = (index) => {
  tempInfo.record.splice(index, 1)
}

// 提交表单
const handleSubmit = async () => {
  for (let i = 0; i < tempInfo.record.length; i++) {
    tempInfo.record[i].tempId = tempInfo.tempId
    tempInfo.record[i].tempName = tempInfo.tempName
    tempInfo.record[i].tempStat = tempInfo.tempStat
    tempInfo.record[i].tempNo = i + 1
  }
  try {
    if (isEdit.value) {
      const res = await evaluateApi.updateEvaluateTemplate(tempInfo.record)
      if (res.code !== 200) return MessagePlugin.error(res.msg)
      MessagePlugin.success(res.msg)
      tempInfo.tempDialogVisible = false
      getTempleteList()
      return
    } else {
      const res = await evaluateApi.addEvaluateTemplate(tempInfo.record)
      if (res.code !== 200) return MessagePlugin.error(res.msg)
      MessagePlugin.success(res.msg)
      tempInfo.tempDialogVisible = false
      getTempleteList()
      return
    }
  } catch (error) {
    MessagePlugin.error('保存评价模板失败')
  }
}

onMounted(() => {
  getTempleteList()
})

defineExpose({
  getTempleteList
})
</script>

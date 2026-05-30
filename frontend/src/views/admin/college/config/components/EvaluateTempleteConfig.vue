<template>
  <div>
    <el-row :gutter="15" style="margin-bottom: 10px">
      <el-col :span="24">
        <el-select v-model="tempInfo.tempStat" size="small" style="width: 100px; margin-right: 10px"
          placeholder="请选择模板状态" @change="getTempleteList">
          <el-option label="生效" :value="1"></el-option>
          <el-option label="失效" :value="0"></el-option>
        </el-select>
        <el-button type="primary" size="small" icon="plus" @click="handleAdd">新增评价模板</el-button>
      </el-col>
    </el-row>

    <el-table :data="tempList" size="small" height="calc(100vh - 250px)" stripe style="width: 100%">
      <el-table-column prop="tempName" label="模板名称" show-overflow-tooltip></el-table-column>
      <el-table-column prop="tempStat" label="状态" width="90px" show-overflow-tooltip>
        <template #default="scope">
          <el-tag :type="scope.row.tempStat ? 'success' : 'danger'" size="small">{{ scope.row.tempStat ? '生效' : '失效'
          }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作" fixed="right" width="100px">
        <template #default="scope">
          <el-button type="primary" icon="Edit" size="small" @click="handleEdit(scope.row)" circle></el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 模板管理弹窗 -->
    <el-dialog title="模板管理" v-model="tempInfo.tempDialogVisible" width="50%">
      <el-form ref="tempFormRef" :model="tempInfo" :rules="rules" size="small" label-width="100px">
        <el-form-item label="模板名称" prop="tempName">
          <el-input v-model="tempInfo.tempName" placeholder="请输入名称" :maxlength="100" clearable
            :style="{ width: '100%' }"></el-input>
        </el-form-item>
        <el-form-item label="操作" prop="sort">
          <el-button-group>
            <el-button type="primary" @click="addQuestion(1)" icon="ArrowLeft">添加评分</el-button>
            <el-button type="primary" @click="addQuestion(2)">添加问答<el-icon class="el-icon--right">
                <ArrowRight />
              </el-icon></el-button>
          </el-button-group>
        </el-form-item>
        <el-form-item :label="'题目' + (index + 1)" v-for="(item, index) in tempInfo.record" :key="index">
          <el-input placeholder="题干内容" v-model="item.quesCont">
            <template #prepend>{{
              item.quesType == 1 ? '评分题目' : '开放问题'
            }}</template>
            <template #append>
              <el-button icon="Delete" @click="removeQuestion(index)"></el-button>
            </template>
          </el-input>
        </el-form-item>
        <el-form-item label="状态" prop="tempStat">
          <el-select v-model="tempInfo.tempStat" placeholder="请选择状态" clearable :style="{ width: '100%' }">
            <el-option label="有效" :value="1"></el-option>
            <el-option label="无效" :value="0"></el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button size="small" @click="tempInfo.tempDialogVisible = false">取 消</el-button>
          <el-button size="small" type="primary" @click="handleSubmit">确 定</el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
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
    if (res.code !== 200) return ElMessage.error(res.msg)
    tempList.value = res.data
  } catch (error) {
    ElMessage.error('获取评价模板列表失败')
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
    if (res.code !== 200) return ElMessage.error(res.msg)
    tempInfo.record = res.data
    tempInfo.tempDialogVisible = true
  } catch (error) {
    ElMessage.error('获取评价模板详情失败')
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
      if (res.code !== 200) return ElMessage.error(res.msg)
      ElMessage.success(res.msg)
      tempInfo.tempDialogVisible = false
      getTempleteList()
      return
    } else {
      const res = await evaluateApi.addEvaluateTemplate(tempInfo.record)
      if (res.code !== 200) return ElMessage.error(res.msg)
      ElMessage.success(res.msg)
      tempInfo.tempDialogVisible = false
      getTempleteList()
      return
    }
  } catch (error) {
    ElMessage.error('保存评价模板失败')
  }
}

onMounted(() => {
  getTempleteList()
})

defineExpose({
  getTempleteList
})
</script>

<template>

    <el-drawer :title="'表信息管理[' + dialog.title + ']'" v-model="dialog.dialogVisible" direction="ltr" size="70%" style="margin:10px;box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1)">
      <el-row :gutter="20" style="width:100%;padding:10px">
        <el-form ref="elForm" :model="tableFormData" :rules="rules" size="small" label-width="0">
          <el-col :span="24">
            <el-form-item prop="tableName">
              <el-input v-model="tableFormData.tableName" placeholder="输入中文表名" :maxlength="30" show-word-limit clearable>
                <template #prepend>表名</template>
              </el-input>
            </el-form-item>
          </el-col>
          <el-col :span="20">
            <el-form-item prop="memo">
              <el-input v-model="tableFormData.memo" placeholder="说明" :maxlength="500" show-word-limit clearable>
                <template #prepend>说明</template>
              </el-input>
            </el-form-item>
          </el-col>
          <el-col :span="4">
            <el-form-item size="small">
              <el-button type="primary" @click="tableSubmit">提交</el-button>
            </el-form-item>
          </el-col>
        </el-form>
      </el-row>
      <el-divider content-position="left">字段信息</el-divider>
      <el-table :data="columns" style="width: 100%;padding:10px" size="small" height="calc(100vh - 300px)">
        <el-table-column prop="col" label="column" width="120"></el-table-column>
        <el-table-column prop="colName" label="名称" width="180">
            <template #default="scope">
                <el-input placeholder="请输入内容" v-model="scope.row.colName" size="small"></el-input>
            </template>
        </el-table-column>
        <el-table-column prop="describe" label="描述">
            <template #default="scope">
              <el-input placeholder="请输入内容" v-model="scope.row.describe" style="width:90%" size="small"></el-input>
            </template>
        </el-table-column>
      </el-table>
      <div style="width:100%;text-align:center;padding-top:5px">
        <el-button size="small" type="primary" @click="columnsSubmit">提交修改</el-button>
      </div>

    </el-drawer>
</template>
<script setup>
import { ref, reactive } from 'vue'
import { ElMessage } from 'element-plus'
import { getColumnInfo, updateColumnInfo, updateTableInfo } from '../../../../../api/db/index.js'

const dialog = reactive({
  title: '',
  dialogVisible: false
})

const columns = ref([])
const tableFormData = ref({})
const elFormRef = ref(null)

const rules = reactive({
  tableName: [
    { required: true, message: '请输入表名', trigger: 'blur' }
  ]
})

const show = async (table) => {
  tableFormData.value = JSON.parse(JSON.stringify(table))
  dialog.title = table.name
  const res = await getColumnInfo(table.id)
  if (res.code !== 200) return ElMessage.error(res.msg)
  columns.value = res.data
  dialog.dialogVisible = true
}

const columnsSubmit = async () => {
  const res = await updateColumnInfo(columns.value)
  if (res.code !== 200) return ElMessage.error(res.msg)
  ElMessage.success(res.msg)
  dialog.dialogVisible = false
}

const tableSubmit = async () => {
  if (!elFormRef.value) return
  
  await elFormRef.value.validate(async (valid) => {
    if (valid) {
      try {
        const res = await updateTableInfo(tableFormData.value)
        if (res.code !== 200) return ElMessage.error(res.msg)
        ElMessage.success(res.msg)
      } catch (error) {
        console.error('提交失败:', error)
        ElMessage.error('提交失败')
      }
    } else {
      ElMessage.error('表单校验失败！')
      return false
    }
  })
}

defineExpose({
  show
})
</script>
<style lang="less" scoped>
.el-drawer__header {
  margin-bottom: 15px;
}
</style>
<template>

    <t-drawer :header="'表信息管理[' + dialog.title + ']'" v-model:visible="dialog.dialogVisible" placement="left" size="70%" style="margin:10px;box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1)">
      <t-row :gutter="20" style="width:100%;padding:10px">
        <t-form ref="elForm" :data="tableFormData" :rules="rules" size="small" label-width="0">
          <t-col :span="12">
            <t-form-item name="tableName">
              <t-input v-model="tableFormData.tableName" placeholder="输入中文表名" :maxlength="30" show-limit-number clearable>
                <template #prepend>表名</template>
              </t-input>
            </t-form-item>
          </t-col>
          <t-col :span="10">
            <t-form-item name="memo">
              <t-input v-model="tableFormData.memo" placeholder="说明" :maxlength="500" show-limit-number clearable>
                <template #prepend>说明</template>
              </t-input>
            </t-form-item>
          </t-col>
          <t-col :span="2">
            <t-form-item size="small">
              <t-button theme="primary" @click="tableSubmit">提交</t-button>
            </t-form-item>
          </t-col>
        </t-form>
      </t-row>
      <t-divider content-position="left">字段信息</t-divider>
      <CustomTable rowKey="id" :data="columns" style="width: 100%;padding:10px" size="small" height="calc(100vh - 300px)">
        <TableColumn colKey="col" label="column" width="120"></TableColumn>
        <TableColumn colKey="colName" label="名称" width="180">
            <template #default="scope">
                <t-input placeholder="请输入内容" v-model="scope.row.colName" size="small"></t-input>
            </template>
        </TableColumn>
        <TableColumn colKey="describe" label="描述">
            <template #default="scope">
              <t-input placeholder="请输入内容" v-model="scope.row.describe" style="width:90%" size="small"></t-input>
            </template>
        </TableColumn>
      </CustomTable>
      <div style="width:100%;text-align:center;padding-top:5px">
        <t-button size="small" theme="primary" @click="columnsSubmit">提交修改</t-button>
      </div>

    </t-drawer>
</template>
<script setup>
import { ref, reactive } from 'vue'
import { MessagePlugin } from 'tdesign-vue-next'
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
  if (res.code !== 200) return MessagePlugin.error(res.msg)
  columns.value = res.data
  dialog.dialogVisible = true
}

const columnsSubmit = async () => {
  const res = await updateColumnInfo(columns.value)
  if (res.code !== 200) return MessagePlugin.error(res.msg)
  MessagePlugin.success(res.msg)
  dialog.dialogVisible = false
}

const tableSubmit = async () => {
  if (!elFormRef.value) return
  
  const valid = await elFormRef.value.validate()
  if (valid === true) {
    try {
      const res = await updateTableInfo(tableFormData.value)
      if (res.code !== 200) return MessagePlugin.error(res.msg)
      MessagePlugin.success(res.msg)
    } catch (error) {
      console.error('提交失败:', error)
      MessagePlugin.error('提交失败')
    }
  } else {
    MessagePlugin.error('表单校验失败！')
  }
}

defineExpose({
  show
})
</script>
<style lang="less" scoped>
.t-drawer__header {
  margin-bottom: 15px;
}
</style>
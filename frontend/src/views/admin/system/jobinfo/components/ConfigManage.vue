<template>
  <t-dialog
    :header="dialogTitle"
    v-model:visible="dialogFormVisible"
    :close-on-overlay-click="false"
    width="600px"
  >
    <t-form :data="form" size="small" label-width="160px">
      <t-form-item label="岗位ID" name="jobLevel">
        <t-input v-model="form.jobLevel" autocomplete="off"></t-input>
      </t-form-item>
      <t-form-item label="岗位名称" name="jobName">
        <t-input
          v-model="form.jobName"
          autocomplete="off"
          maxlength="50"
          show-limit-number
        ></t-input>
      </t-form-item>
      <t-form-item label="部门号码" name="deptNum">
        <t-input
          v-model="form.deptNum"
          type="number"
          autocomplete="off"
        ></t-input>
      </t-form-item>
      <t-form-item label="岗位描述" name="jobDesc">
        <t-input
          v-model="form.jobDesc"
          autocomplete="off"
          maxlength="50"
          show-limit-number
        ></t-input>
      </t-form-item>
      <t-form-item label="所属机构" name="organ">
        <t-input v-model="form.organ" autocomplete="off"></t-input>
      </t-form-item>
      <t-form-item label="状态" name="jobStatus">
        <t-select v-model="form.jobStatus" placeholder="请选择表单状态">
          <t-option
            v-for="item in (dictStore.dictList?.sys_dict_status || [])"
            :key="item.id"
            :label="item.codeval"
            :value="item.code"
          ></t-option>
        </t-select>
      </t-form-item>
    </t-form>
    <template #footer>
      <t-space>
        <t-button variant="outline" size="small" @click="dialogFormVisible = false"
          >取 消</t-button
        >
        <t-button variant="outline" size="small" theme="primary" @click="handleSubmit"
          >确 定</t-button
        >
      </t-space>
    </template>
  </t-dialog>
</template>

<script setup>
import { ref, computed } from 'vue'
import { useDictStore } from '@/stores'
import { jobinfoApi } from '@/api/system/jobinfo'

const dictStore = useDictStore()
const dialogFormVisible = ref(false)
const form = ref({
  jobLevel: '',
  jobName: '',
  deptNum: '',
  jobStatus: '0',
  jobDesc: '',
  organ: ''
})
const operationType = ref('add')

const dialogTitle = computed(() => {
  return operationType.value === 'add' ? '新增岗位' : '编辑岗位'
})

const openDialog = (record) => {
  if (record) {
    form.value = { ...record }
    operationType.value = 'edit'
  } else {
    resetForm()
    operationType.value = 'add'
  }
  dialogFormVisible.value = true
}

const resetForm = () => {
  form.value = {
    jobLevel: '',
    jobName: '',
    deptNum: '',
    jobStatus: '0',
    jobDesc: '',
    organ: ''
  }
}

const handleSubmit = async () => {
  try {
    let res = null
    if (operationType.value === 'add') {
      res = await jobinfoApi.addJobLevel(form.value)
    } else {
      res = await jobinfoApi.updateJobLevel(form.value)
    }
    if (res.code !== 200) return

    MessagePlugin.success(res.msg)
    dialogFormVisible.value = false
    emit('success')
  } catch (error) {
    MessagePlugin.error('操作失败，请重试')
  }
}

// 暴露方法给父组件
defineExpose({
  openDialog
})

// 定义事件
const emit = defineEmits(['success'])
</script>

<style lang="less" scoped>
.dialog-footer {
  text-align: right;
}
</style>

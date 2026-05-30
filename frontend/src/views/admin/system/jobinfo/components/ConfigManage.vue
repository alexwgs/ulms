<template>
  <el-dialog
    :title="dialogTitle"
    v-model="dialogFormVisible"
    :close-on-click-modal="false"
    width="600px"
  >
    <el-form :model="form" size="small" label-width="160px">
      <el-form-item label="岗位ID" prop="jobLevel">
        <el-input v-model="form.jobLevel" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="岗位名称" prop="jobName">
        <el-input
          v-model="form.jobName"
          autocomplete="off"
          maxlength="50"
          show-word-limit
        ></el-input>
      </el-form-item>
      <el-form-item label="部门号码" prop="deptNum">
        <el-input
          v-model="form.deptNum"
          type="number"
          autocomplete="off"
        ></el-input>
      </el-form-item>
      <el-form-item label="岗位描述" prop="jobDesc">
        <el-input
          v-model="form.jobDesc"
          autocomplete="off"
          maxlength="50"
          show-word-limit
        ></el-input>
      </el-form-item>
      <el-form-item label="所属机构" prop="organ">
        <el-input v-model="form.organ" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="状态" prop="jobStatus">
        <el-select v-model="form.jobStatus" placeholder="请选择表单状态">
          <el-option
            v-for="item in dictStore.dictList.sys_dict_status"
            :key="item.id"
            :label="item.codeval"
            :value="item.code"
          ></el-option>
        </el-select>
      </el-form-item>
    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button size="small" @click="dialogFormVisible = false"
          >取 消</el-button
        >
        <el-button size="small" type="primary" @click="handleSubmit"
          >确 定</el-button
        >
      </span>
    </template>
  </el-dialog>
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

    ElMessage.success(res.msg)
    dialogFormVisible.value = false
    emit('success')
  } catch (error) {
    ElMessage.error('操作失败，请重试')
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

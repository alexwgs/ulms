<template>
  <el-dialog
    title="分配角色"
    v-model="dialogVisible"
    :close-on-click-modal="false"
    @close="handleClose"
    width="500px"
  >
    <el-form :model="formData" ref="formRef">
      <el-select
        v-model="formData.roles"
        size="small"
        multiple
        placeholder="请选择分配角色"
        style="width: 100%"
      >
        <el-option
          v-for="item in list"
          :key="item.id"
          :label="item.roleName"
          :value="item.id"
        />
      </el-select>
    </el-form>

    <template #footer>
      <el-button size="small" @click="dialogVisible = false">取消</el-button>
      <el-button
        size="small"
        type="primary"
        @click="submitForm"
        :loading="loading"
      >
        确定
      </el-button>
    </template>
  </el-dialog>
</template>

<script setup>
import { ref } from 'vue'
import { employeeApi } from '@/api/system/employee'
import { roleApi } from '@/api/system/role'

const emit = defineEmits(['refresh'])

const dialogVisible = ref(false)
const loading = ref(false)
const currentEmployeeId = ref('')

const list = ref([])

const formData = ref({
  roles: []
})

const getRoleList = async () => {
  try {
    loading.value = true
    const res = await roleApi.listAllRole()
    if (res.code != 200) return
    list.value = res.data
  } catch (error) {
    console.error('获取员工列表失败:', error)
  } finally {
    loading.value = false
  }
}

// 打开对话框
const openDialog = (employee) => {
  currentEmployeeId.value = employee.ploNum
  formData.value.roles = employee.roles
    ? employee.roles.split(',').map(Number)
    : []
  dialogVisible.value = true
}

// 关闭对话框
const handleClose = () => {
  formData.value.roles = []
}

// 提交表单
const submitForm = async () => {
  try {
    loading.value = true

    const res = await employeeApi.dispatchRole(
      currentEmployeeId.value,
      formData.value
    )

    if (res.code !== 200) {
      throw new Error(res.msg)
    }

    ElMessage.success(res.msg)
    dialogVisible.value = false
    emit('refresh')
  } catch (error) {
    ElMessage.error(error.message || '角色分配失败')
  } finally {
    loading.value = false
  }
}
onMounted(() => {
  getRoleList()
})

defineExpose({
  openDialog
})
</script>

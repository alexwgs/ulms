<template>
  <t-dialog
    header="分配角色"
    v-model:visible="dialogVisible"
    :close-on-overlay-click="false"
    @close="handleClose"
    width="500px"
  >
    <t-form :data="formData" ref="formRef">
      <t-select
        v-model="formData.roles"
        size="small"
        multiple
        placeholder="请选择分配角色"
        style="width: 100%"
      >
        <t-option
          v-for="item in list"
          :key="item.id"
          :label="item.roleName"
          :value="item.id"
        />
      </t-select>
    </t-form>

    <template #footer>
      <t-button size="small" @click="dialogVisible = false">取消</t-button>
      <t-button
        size="small"
        theme="primary"
        @click="submitForm"
        :loading="loading"
      >
        确定
      </t-button>
    </template>
  </t-dialog>
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

    MessagePlugin.success(res.msg)
    dialogVisible.value = false
    emit('refresh')
  } catch (error) {
    MessagePlugin.error(error.message || '角色分配失败')
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

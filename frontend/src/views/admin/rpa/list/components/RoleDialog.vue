<template>
  <t-dialog
    header="分配角色"
    v-model:visible="dialogVisible"
    :close-on-overlay-click="false"
    @close="handleClose"
    width="500px"
  >
    <t-select
      v-model="selectedRoles"
      size="small"
      multiple
      placeholder="请选择分配角色"
      style="width: 100%"
    >
      <t-option
        v-for="item in roleList"
        :key="item.id"
        :label="item.roleName"
        :value="item.id"
      />
    </t-select>

    <template #footer>
      <t-button size="small" @click="dialogVisible = false">取消</t-button>
      <t-button
        size="small"
        theme="primary"
        @click="submitRoles"
        :loading="loading"
      >
        确定
      </t-button>
    </template>
  </t-dialog>
</template>

<script setup>
import { ref, watch } from 'vue'
import { MessagePlugin } from 'tdesign-vue-next'
import { updateRpaTool } from '@/api/rpa/rpa.js'
import { getRoleList } from '@/api/auth'

const emit = defineEmits(['refresh'])

// 对话框状态
const dialogVisible = ref(false)
const loading = ref(false)
const roleList = ref([])
// 表单数据
const currentRecord = ref({})
const selectedRoles = ref([])

// 打开对话框
const openDialog = (record) => {
  currentRecord.value = record
  selectedRoles.value = record.roles ? record.roles.split(',').map(Number) : []
  dialogVisible.value = true
}

// 关闭对话框
const handleClose = () => {
  selectedRoles.value = []
}

// 提交角色分配
const submitRoles = async () => {
  try {
    loading.value = true

    const res = await updateRpaTool({
      id: currentRecord.value.id,
      roles: selectedRoles.value.join(',')
    })

    if (res.code !== 200) {
      throw new Error(res.msg)
    }

    MessagePlugin.success(res.msg)
    emit('refresh')
    dialogVisible.value = false
  } catch (error) {
    MessagePlugin.error(error.message || '角色分配失败')
  } finally {
    loading.value = false
  }
}

// 获取角色列表
const fetchRoleList = async () => {
  try {
    const res = await getRoleList()
    roleList.value = res.data
  } catch (error) {
    MessagePlugin.error('获取角色列表失败')
  }
}

// 初始化获取角色列表
fetchRoleList()

// 暴露方法供父组件调用
defineExpose({
  openDialog
})
</script>

<style lang="less" scoped>
.t-select {
  margin-bottom: 20px;
}
</style>

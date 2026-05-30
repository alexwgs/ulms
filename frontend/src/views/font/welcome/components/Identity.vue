<template>
  <el-dialog
    title="身份选择"
    v-model="dialogVisible"
    width="50%"
    :before-close="handleClose"
  >
    <el-form :model="form" label-width="80px">
      <el-form-item label="选择身份">
        <el-checkbox-group v-model="form.identity">
          <el-checkbox label="无身份">无身份</el-checkbox>
          <el-checkbox
            v-for="item in identityList"
            :key="item.id"
            :label="item.statusName"
          ></el-checkbox>
        </el-checkbox-group>
      </el-form-item>
    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="handleClose">取消</el-button>
        <el-button type="primary" @click="handleSubmit">确定</el-button>
      </span>
    </template>
  </el-dialog>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import { getIdentityList } from '@/api/welcome/index.js'

const dialogVisible = ref(false)
const identityList = ref([])
const form = reactive({
  identity: []
})

const show = () => {
  dialogVisible.value = true
  getIdentity()
}

const handleClose = () => {
  dialogVisible.value = false
}

const handleSubmit = () => {
  ElMessage.success('身份选择成功')
  handleClose()
}

const getIdentity = async () => {
  try {
    const res = await getIdentityList()
    if (res.code !== 200) {
      ElMessage.error(res.msg)
      return
    }
    identityList.value = res.data || []
  } catch (error) {
    console.error('获取身份列表失败', error)
  }
}

defineExpose({
  show
})
</script>

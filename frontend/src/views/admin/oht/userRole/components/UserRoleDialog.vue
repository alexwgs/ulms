<template>
  <el-dialog title="角色名单维护" :close-on-click-modal="false" v-model="dialogVisible" @close="handleClose">
    <el-form :model="userRoleForm" ref="ruleFormRef" :rules="roleFormRules">
      <el-form-item label="工号" :label-width="formLabelWidth" prop="ploNum">
        <el-input size="small" v-model="userRoleForm.ploNum" :readonly="dialogType === 'add' ? false : true" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="角色类型" :label-width="formLabelWidth" prop="roleType">
        <el-select size="small" v-model="userRoleForm.roleType" placeholder="角色类型">
          <el-option v-for="item in dictStore.dictList.oht_role_type" :key="item.code" :label="item.codeval" :value="parseInt(item.code)"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="角色编号" :label-width="formLabelWidth" prop="roleCode">
        <el-select size="small" v-model="userRoleForm.roleCode" readonly placeholder="角色编号">
          <el-option label="不可选择" :value="0"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="角色状态" :label-width="formLabelWidth" prop="roleStat">
        <el-select size="small" v-model="userRoleForm.roleStat" placeholder="请选择">
          <el-option v-for="item in dictStore.dictList.oht_role_status" :key="item.code" :label="item.codeval" :value="parseInt(item.code)"></el-option>
        </el-select>
      </el-form-item>
    </el-form>
    <template #footer>
      <div class="dialog-footer">
        <el-button size="small" @click="handleClose">取 消</el-button>
        <el-button size="small" type="primary" @click="submitForm">确 定</el-button>
      </div>
    </template>
  </el-dialog>
</template>

<script setup>
import { ref, reactive, watch } from 'vue'
import { useDictStore } from '@/stores'
import { userRoleApi } from '@/api/oht/userRole'

const dictStore = useDictStore()

const props = defineProps({
  visible: {
    type: Boolean,
    default: false
  },
  dialogType: {
    type: String,
    default: 'add'
  },
  formData: {
    type: Object,
    default: () => ({})
  }
})

const emit = defineEmits(['update:visible', 'success'])

const dialogVisible = ref(props.visible)
const ruleFormRef = ref(null)
const formLabelWidth = '120px'

const userRoleForm = reactive({
  ploNum: null,
  roleType: null,
  roleCode: 0,
  roleStat: 1
})

const roleFormRules = {
  ploNum: [
    { required: true, message: '工号信息必须输入', trigger: 'blur' },
    { min: 6, max: 6, message: '请输入6位数字工号', trigger: 'blur' }
  ],
  roleType: [
    { required: true, message: '请选择角色类型', trigger: 'change' }
  ],
  roleStat: [
    { required: true, message: '请选择角色状态', trigger: 'change' }
  ]
}

// 监听外部visible变化
watch(() => props.visible, (newVal) => {
  dialogVisible.value = newVal
})

// 监听内部dialogVisible变化
watch(dialogVisible, (newVal) => {
  emit('update:visible', newVal)
})

// 监听formData变化
watch(() => props.formData, (newVal) => {
  if (newVal) {
    Object.assign(userRoleForm, newVal)
  }
}, { deep: true })

// 关闭对话框
const handleClose = () => {
  dialogVisible.value = false
}

// 提交表单
const submitForm = async () => {
  if (!ruleFormRef.value) return
  
  try {
    await ruleFormRef.value.validate()
    
    let res = null
    if (props.dialogType === 'add') {
      res = await userRoleApi.addUserRole(userRoleForm)
    } else {
      res = await userRoleApi.updateUserRole(userRoleForm)
    }
    
    if (res.code === 200) {
      emit('success')
      dialogVisible.value = false
    }
  } catch (error) {
    console.error('表单验证失败:', error)
  }
}
</script>

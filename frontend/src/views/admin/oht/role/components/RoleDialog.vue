<template>
  <t-dialog :header="dialogTitle" :close-on-overlay-click="false" v-model:visible="dialogVisible" @close="handleClose">
    <t-form :data="roleForm" ref="ruleFormRef" :rules="roleFormRules">
      <t-form-item label="角色ID" :label-width="formLabelWidth" name="roleCode">
        <t-input size="small" v-model="roleForm.roleCode" autocomplete="off" placeholder="角色ID自动生成" disabled></t-input>
      </t-form-item>
      <t-form-item label="角色名称" :label-width="formLabelWidth" name="roleName">
        <t-input size="small" v-model="roleForm.roleName" autocomplete="off"></t-input>
      </t-form-item>
      <t-form-item label="角色类型" :label-width="formLabelWidth" name="roleType">
        <t-select size="small" v-model="roleForm.roleType" placeholder="请选择">
          <t-option
            v-for="item in (dictStore.dictList?.oht_role_type || [])"
            :key="item.code"
            :label="item.codeval"
            :value="parseInt(item.code)"
          ></t-option>
        </t-select>
      </t-form-item>
      <t-form-item label="角色状态" :label-width="formLabelWidth" name="roleStat">
        <t-select size="small" v-model="roleForm.roleStat" placeholder="请选择">
          <t-option
            v-for="item in (dictStore.dictList?.oht_role_status || [])"
            :key="item.code"
            :label="item.codeval"
            :value="parseInt(item.code)"
          ></t-option>
        </t-select>
      </t-form-item>
    </t-form>
    <template #footer>
      <div class="dialog-footer">
        <t-button size="small" @click="handleClose">取 消</t-button>
        <t-button size="small" theme="primary" @click="submitRole">确 定</t-button>
      </div>
    </template>
  </t-dialog>
</template>

<script setup>
import { ref, reactive, watch } from 'vue'
import { useDictStore } from '@/stores'
import { roleApi } from '@/api/oht/role.js'

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
const dialogTitle = ref(props.dialogType === 'add' ? '新增角色信息' : '修改角色信息')

const roleForm = reactive({
  roleCode: '',
  roleName: '',
  roleType: 0,
  roleStat: 1
})

const roleFormRules = {
  roleName: [
    { required: true, max: 10, min: 2, message: '请输入2-10个字符的角色名称', trigger: 'blur' }
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
    Object.assign(roleForm, newVal)
  }
  dialogTitle.value = props.dialogType === 'add' ? '新增角色信息' : '修改角色信息'
}, { deep: true })

// 关闭对话框
const handleClose = () => {
  dialogVisible.value = false
  // 重置表单
  Object.assign(roleForm, { roleCode: '', roleName: '', roleType: 0, roleStat: 1 })
}

// 提交表单
const submitRole = async () => {
  if (!ruleFormRef.value) return
  
  try {
    const valid = await ruleFormRef.value.validate()
    if (valid !== true) return

    let res = null
    if (props.dialogType === 'add') {
      res = await roleApi.addRole(roleForm)
    } else {
      res = await roleApi.updateRole(roleForm)
    }
    
    if (res.code === 200) {
      emit('success')
      dialogVisible.value = false
      // 重置表单
      Object.assign(roleForm, { roleCode: '', roleName: '', roleType: 0, roleStat: 1 })
    }
  } catch (error) {
    console.error('表单验证失败:', error)
  }
}
</script>

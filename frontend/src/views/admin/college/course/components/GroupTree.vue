<template>
    <t-dialog header="授课对象分组配置" :visible="visiable" :close-on-overlay-click="false" :close-on-esc-keydown="false" :close-btn="false" @close="emit('update:visiable', false)">
    <t-input v-model="groupName" size="small" placeholder="授课对象分组名称"></t-input>

    <t-button-group size="small">
      <t-button size="small" theme="primary" @click="openEmpTree('101')">一线员工</t-button>
      <t-button size="small" theme="primary" @click="openEmpTree('100')">业务主任</t-button>
      <t-button size="small" theme="primary" @click="openEmpTree('199')">主管</t-button>
    </t-button-group>

    <t-dialog header="选择员工" :visible="treeDialogVisiable" width="500px" @close="treeDialogVisiable = false">
      <EmployeeSelect
        conditionType="joblevel"
        v-model="selectedPloNum"
        :params="jobLevel"
        :allowClear="false"
        multiple
        @getUser="getUserInfo"
      ></EmployeeSelect>
      <template #footer>
        <div class="dialog-footer">
          <t-button size="small" @click="treeDialogVisiable = false">确 定</t-button>
        </div>
      </template>
    </t-dialog>

    <template #footer>
      <t-button size="small" theme="primary" @click="submitSelected()">确定</t-button>
      <t-button size="small" @click="emit('update:visiable', false)">关闭</t-button>
    </template>
    </t-dialog>
</template>
<script setup>
import { ref, reactive } from 'vue'
import EmployeeSelect from '@/components/EmployeeSelect.vue'

const props = defineProps({
  visiable: Boolean
})

const emit = defineEmits(['update:visiable', 'getSelected'])

const groupName = ref('')
const treeDialogVisiable = ref(false)
const jobLevel = ref('101')
const selectedPloNum = ref('')
const selectedUsers = ref([])
const params = reactive({
  userList: ''
})

const getUserInfo = (user) => {
  if (user) {
    selectedUsers.value.push(user)
  }
}

const openEmpTree = (level) => {
  jobLevel.value = level
  treeDialogVisiable.value = true
}

const submitSelected = () => {
  // 处理选中的用户列表
  params.userList = selectedPloNum.value.join(',')
  emit('getSelected', { groupName: groupName.value, userList: params.userList })
  emit('update:visiable', false)
}
</script>
<style lang="less" scoped>

</style>

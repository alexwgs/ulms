<template>
    <el-dialog title="授课对象分组配置" :visible.sync="visiable" :close-on-click-modal="false" :close-on-press-escape="false" :show-close="false">
    <el-input v-model="groupName" size="small" placeholder="授课对象分组名称"></el-input>

    <el-button-group size="small">
      <el-button size="small" type="primary" @click="openEmpTree('101')">一线员工</el-button>
      <el-button size="small" type="primary" @click="openEmpTree('100')">业务主任</el-button>
      <el-button size="small" type="primary" @click="openEmpTree('199')">主管</el-button>
    </el-button-group>
    
    <el-dialog title="选择员工" v-model="treeDialogVisiable" width="500px">
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
          <el-button size="small" @click="treeDialogVisiable = false">确 定</el-button>
        </div>
      </template>
    </el-dialog>

    <div slot="footer" class="dialog-footer">
      <el-button size="small" type="primary" @click="submitSelected()">确定</el-button>
      <el-button size="small" @click="visiable = !visiable">关闭</el-button>
    </div>
    </el-dialog>
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

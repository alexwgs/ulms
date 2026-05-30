<template>
  <div>
    <el-card class="box-card">
      <el-row>
        <el-col :span="24">
          <el-button type="primary" size="small" @click="addQuickMemo">添加快捷消息</el-button>
        </el-col>
      </el-row>
      <el-table :data="quickMemoTableList" size="small" @sort-change="tableSort" height="calc(100vh - 325px)" stripe>
        <el-table-column prop="journo" sortable="custom" label="JOURNO" width="180"></el-table-column>
        <el-table-column prop="roleType" sortable="custom" label="快捷消息类型">
          <template #default="scope">
            {{ dictStore.getDictLabel('oht_role_type', scope.row.roleType) }}
          </template>
        </el-table-column>
        <el-table-column prop="memo" sortable="custom" label="快捷消息"></el-table-column>
        <el-table-column prop="status" sortable="custom" label="消息状态">
          <template #default="scope">
            <el-tag size="small" :type="scope.row.status == 0 ? 'danger' : 'success'" effect="dark">
              {{ dictStore.getDictLabel('oht_role_status', scope.row.status) }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" fixed="right" width="120px">
          <template #default="scope">
            <el-button type="warning" size="small" icon="Edit" @click="quickMemoEdit(scope.row)" circle></el-button>
            <el-button type="danger" size="small" icon="Delete" @click="removeQuickMemo(scope.row)" circle></el-button>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="currentPage"
        :page-sizes="pageSizes" :page-size="queryInfo.pageSize" layout="total, sizes, prev, pager, next, jumper"
        :total="total">
      </el-pagination>
    </el-card>

    <el-dialog :title="dialogTitle" :close-on-click-modal="false" v-model="quickMemoAddVisible"
      @close="closeQuickMemoAddDialog">
      <el-form :model="quickMemoForm" ref="addQuickMemoFormRef" :rules="quickMemoFormRules">
        <el-form-item label="流水号" :label-width="formLabelWidth" prop="journo">
          <el-input size="small" v-model="quickMemoForm.journo" autocomplete="off" placeholder="快捷消息ID自动生成"
            disabled></el-input>
        </el-form-item>
        <el-form-item label="快捷消息类型" :label-width="formLabelWidth" prop="roleType">
          <el-select size="small" v-model="quickMemoForm.roleType" placeholder="请选择">
            <el-option v-for="item in dictStore.dictList.oht_role_type" :key="item.code" :label="item.codeval"
              :value="item.code">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="快捷消息" :label-width="formLabelWidth" prop="memo">
          <el-input size="small" v-model="quickMemoForm.memo" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="快捷消息状态" :label-width="formLabelWidth" prop="status">
          <el-select size="small" v-model="quickMemoForm.status" placeholder="请选择">
            <el-option v-for="item in dictStore.dictList.oht_role_status" :key="item.code" :label="item.codeval"
              :value="parseInt(item.code)">
            </el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button size="small" @click="quickMemoAddVisible = false">取 消</el-button>
          <el-button size="small" type="primary" @click="submitQuickMemo">确 定</el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import {
  quickMemoApi
} from '@/api/oht/quickMemo.js'
import { useDictStore } from '@/stores'

const dictStore = useDictStore()

const quickMemoTableList = ref([])
const queryInfo = reactive({
  orderType: ' desc',
  order: ' journo',
  querytype: '',
  query: '',
  pageSize: 20,
  pageNum: 1
})
const currentPage = ref(1)
const total = ref(0)
const pageSizes = [20, 100, 500]
const dialogTitle = ref('')
const formLabelWidth = '120px'
const quickMemoAddVisible = ref(false)
const addQuickMemoFormRef = ref(null)

const quickMemoForm = reactive({
  journo: '',
  roleType: '0',
  memo: '',
  status: 1
})

const quickMemoFormRules = {
  roleType: [
    { required: true, message: '请选择快捷消息群组', trigger: 'change' }
  ],
  memo: [
    { required: true, message: '快捷消息内容不可为空', trigger: 'blur' },
    {
      max: 500,
      min: 2,
      message: '请输入2-250个字符的快捷消息',
      trigger: 'blur'
    }
  ],
  status: [{ required: true, message: '请选择快捷消息状态', trigger: 'change' }]
}

const getquickMemoList = async () => {
  try {
    const res = await quickMemoApi.getQuickMemoList(queryInfo)
    if (res.code !== 200) {
      ElMessage.error(res.msg)
      return
    }
    quickMemoTableList.value = res.data.list
    total.value = res.data.total
  } catch (error) {
    ElMessage.error('获取快捷消息列表失败')
  }
}

const handleSizeChange = (pageSize) => {
  queryInfo.pageSize = pageSize
  getquickMemoList()
}

const handleCurrentChange = (page) => {
  queryInfo.pageNum = page
  currentPage.value = page
  getquickMemoList()
}

const removeQuickMemo = (row) => {
  ElMessageBox.confirm('此操作将永久删除该记录, 是否继续?', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  })
    .then(async () => {
      try {
        const res = await quickMemoApi.deleteQuickMemo(row.journo)
        if (res.code !== 200) {
          ElMessage.error(res.msg)
          return
        }
        ElMessage.success(res.msg)
        getquickMemoList()
      } catch (error) {
        ElMessage.error('删除快捷消息失败')
      }
    })
    .catch(() => {
      ElMessage.error('取消删除')
    })
}

const addQuickMemo = () => {
  dialogTitle.value = '新增快捷消息信息'
  quickMemoAddVisible.value = true
}

const quickMemoEdit = (row) => {
  dialogTitle.value = '修改快捷消息信息'
  quickMemoForm.journo = row.journo
  quickMemoForm.roleType = row.roleType
  quickMemoForm.memo = row.memo
  quickMemoForm.status = row.status
  quickMemoAddVisible.value = true
}

const submitQuickMemo = async () => {
  if (!addQuickMemoFormRef.value) return

  await addQuickMemoFormRef.value.validate(async (valid) => {
    if (valid) {
      try {
        let res
        if (dialogTitle.value === '新增快捷消息信息') {
          res = await quickMemoApi.addQuickMemo(quickMemoForm)
        } else if (dialogTitle.value === '修改快捷消息信息') {
          res = await quickMemoApi.updateQuickMemo(quickMemoForm)
        }

        if (res.code !== 200) {
          ElMessage.error(res.msg)
          return
        }

        ElMessage.success(res.msg)
        getquickMemoList()
        quickMemoAddVisible.value = false
      } catch (error) {
        ElMessage.error('操作失败')
      }
    } else {
      ElMessage.error('表单校验失败！请检查表单！')
    }
  })
}

const closeQuickMemoAddDialog = () => {
  quickMemoForm.journo = ''
  quickMemoForm.roleType = '0'
  quickMemoForm.memo = ''
  quickMemoForm.status = 1
  if (addQuickMemoFormRef.value) {
    addQuickMemoFormRef.value.resetFields()
  }
}

const tableSort = (data) => {
  if (data.order === 'ascending') queryInfo.orderType = ' asc '
  else if (data.order === 'descending') queryInfo.orderType = ' desc '
  queryInfo.order = data.prop
  getquickMemoList()
}

onMounted(() => {
  getquickMemoList()
})
</script>

<style lang="less" scoped>
.box-card {
  height: calc(100vh - 190px);
  overflow: auto;
}
</style>

<template>
  <div>
    <t-card class="box-card">
      <t-row>
        <t-col :span="12">
          <t-button theme="primary" size="small" @click="addQuickMemo">添加快捷消息</t-button>
        </t-col>
      </t-row>
      <CustomTable rowKey="id" :data="quickMemoTableList" size="small" @sort-change="tableSort" height="calc(100vh - 325px)" stripe>
        <TableColumn colKey="journo" sortable="custom" label="JOURNO" width="180"></TableColumn>
        <TableColumn colKey="roleType" sortable="custom" label="快捷消息类型">
          <template #default="scope">
            {{ dictStore.getDictLabel('oht_role_type', scope.row.roleType) }}
          </template>
        </TableColumn>
        <TableColumn colKey="memo" sortable="custom" label="快捷消息"></TableColumn>
        <TableColumn colKey="status" sortable="custom" label="消息状态">
          <template #default="scope">
            <t-tag size="small" :theme="scope.row.status == 0 ? 'danger' : 'success'" effect="dark">
              {{ dictStore.getDictLabel('oht_role_status', scope.row.status) }}
            </t-tag>
          </template>
        </TableColumn>
        <TableColumn label="操作" fixed="right" width="120px">
          <template #default="scope">
            <t-button theme="warning" size="small" @click="quickMemoEdit(scope.row)" shape="circle"><template #icon><DynamicIcon name="edit" /></template></t-button>
            <t-button theme="danger" size="small" @click="removeQuickMemo(scope.row)" shape="circle"><template #icon><DynamicIcon name="delete" /></template></t-button>
          </template>
        </TableColumn>
      </CustomTable>
      <t-pagination @page-size-change="handleSizeChange" @current-change="handleCurrentChange" :current="currentPage"
        :page-size-options="pageSizes" :page-size="queryInfo.pageSize"
        :total="total">
      </t-pagination>
    </t-card>

    <t-dialog :header="dialogTitle" :close-on-overlay-click="false" v-model:visible="quickMemoAddVisible"
      @close="closeQuickMemoAddDialog">
      <t-form :data="quickMemoForm" ref="addQuickMemoFormRef" :rules="quickMemoFormRules">
        <t-form-item label="流水号" :label-width="formLabelWidth" name="journo">
          <t-input size="small" v-model="quickMemoForm.journo" autocomplete="off" placeholder="快捷消息ID自动生成"
            disabled></t-input>
        </t-form-item>
        <t-form-item label="快捷消息类型" :label-width="formLabelWidth" name="roleType">
          <t-select size="small" v-model="quickMemoForm.roleType" placeholder="请选择">
            <t-option v-for="item in dictStore.dictList.oht_role_type" :key="item.code" :label="item.codeval"
              :value="item.code">
            </t-option>
          </t-select>
        </t-form-item>
        <t-form-item label="快捷消息" :label-width="formLabelWidth" name="memo">
          <t-input size="small" v-model="quickMemoForm.memo" autocomplete="off"></t-input>
        </t-form-item>
        <t-form-item label="快捷消息状态" :label-width="formLabelWidth" name="status">
          <t-select size="small" v-model="quickMemoForm.status" placeholder="请选择">
            <t-option v-for="item in dictStore.dictList.oht_role_status" :key="item.code" :label="item.codeval"
              :value="parseInt(item.code)">
            </t-option>
          </t-select>
        </t-form-item>
      </t-form>
      <template #footer>
        <div class="dialog-footer">
          <t-button size="small" @click="quickMemoAddVisible = false">取 消</t-button>
          <t-button size="small" theme="primary" @click="submitQuickMemo">确 定</t-button>
        </div>
      </template>
    </t-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { MessagePlugin, DialogPlugin } from 'tdesign-vue-next'
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
      MessagePlugin.error(res.msg)
      return
    }
    quickMemoTableList.value = res.data.list
    total.value = res.data.total
  } catch (error) {
    MessagePlugin.error('获取快捷消息列表失败')
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
  DialogPlugin.confirm('此操作将永久删除该记录, 是否继续?', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  })
    .then(async () => {
      try {
        const res = await quickMemoApi.deleteQuickMemo(row.journo)
        if (res.code !== 200) {
          MessagePlugin.error(res.msg)
          return
        }
        MessagePlugin.success(res.msg)
        getquickMemoList()
      } catch (error) {
        MessagePlugin.error('删除快捷消息失败')
      }
    })
    .catch(() => {
      MessagePlugin.error('取消删除')
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

  const valid = await addQuickMemoFormRef.value.validate()
  if (valid === true) {
    try {
      let res
      if (dialogTitle.value === '新增快捷消息信息') {
        res = await quickMemoApi.addQuickMemo(quickMemoForm)
      } else if (dialogTitle.value === '修改快捷消息信息') {
        res = await quickMemoApi.updateQuickMemo(quickMemoForm)
      }

      if (res.code !== 200) {
        MessagePlugin.error(res.msg)
        return
      }

      MessagePlugin.success(res.msg)
      getquickMemoList()
      quickMemoAddVisible.value = false
    } catch (error) {
      MessagePlugin.error('操作失败')
    }
  } else {
    MessagePlugin.error('表单校验失败！请检查表单！')
  }
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
  if (!data.descending) queryInfo.orderType = ' asc '
  else if (data.descending) queryInfo.orderType = ' desc '
  queryInfo.order = data.sortBy
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

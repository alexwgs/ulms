<template>
  <PageTips
    title="操作说明"
    theme="info"
    :closable="false"
    message="请正确使用信息点信息：1.基于IP地址获取终端相关信息及员工登录信息。2.分机号码已无实际意义，主要为过往登记Avaya电话分机。3.楼层仅供参考，部分楼层搬位置后IP已与楼层不一一对应。"
  />
  <t-card class="management-card">
    <t-form :data="queryInfo" label-width="80px" colon class="filter-form">
      <t-row :gutter="[24, 24]">
        <t-col :span="6">
          <t-form-item label="查询条件" name="query">
            <t-input-adornment class="input-with-select">
              <template #prepend>
                <t-select
                  size="small"
                  v-model="queryInfo.queryType"
                  placeholder="请选择"
                >
                  <t-option label="分机号" value="extnNum"></t-option>
                  <t-option label="PC_IP" value="pcIp"></t-option>
                  <t-option label="MEMO" value="memo"></t-option>
                  <t-option label="员工编号" value="ploNum"></t-option>
                  <t-option label="楼层" value="floorNum"></t-option>
                </t-select>
              </template>
              <template #append>
                <t-button variant="outline" theme="primary" size="small"
                  @click="getStationList">搜索</t-button>
              </template>
              <t-input size="small" placeholder="请输入内容" v-model="queryInfo.query"></t-input>
            </t-input-adornment>
          </t-form-item>
        </t-col>
        <t-col :span="3" class="operation-container">
          <t-button variant="outline" theme="primary" size="small" @click="addStation"
          >添加信息点</t-button
          >
        </t-col>
      </t-row>
    </t-form>
    <CustomTable rowKey="id"
      :data="stationList"
      size="small"
      @sort-change="tableSort"
      height="calc(100vh - 400px)">
      <TableColumn type="index"></TableColumn>
      <TableColumn
        label="分机号"
        prop="extnNum"
        sortable="custom"></TableColumn>
      <TableColumn
        label="楼层"
        prop="floorNum"
        sortable="custom"></TableColumn>
      <TableColumn
        label="分机IP"
        prop="extnIp"
        sortable="custom"></TableColumn>
      <TableColumn
        label="电脑IP"
        prop="pcIp"
        sortable="custom"></TableColumn>
      <TableColumn
        label="员工"
        prop="ploNum"
        sortable="custom"></TableColumn>
      <TableColumn
        label="更新时间"
        prop="updateTime"
        sortable="custom"></TableColumn>
      <TableColumn
        label="备注"
        prop="memo"
        sortable="custom"
        ellipsis>
        <template #default="scope">
          <div v-if="scope.row.memo && JSON.parse(scope.row.memo).os">
            操作系统：{{ JSON.parse(scope.row.memo).os }} 系统位数：{{
              JSON.parse(scope.row.memo).osbit
            }}<br />
            客户端信息：{{ JSON.parse(scope.row.memo).clientInfo }}
          </div>
        </template>
      </TableColumn>
      <TableColumn label="操作" fixed="right" width="120">
        <template #default="scope">
          <t-button variant="outline"
            size="small" theme="primary"
            @click="handleEdit(scope.$index, scope.row)"
           >编辑</t-button>
          <t-button variant="outline"
            size="small" theme="danger"
            @click="handleDelete(scope.$index, scope.row)"
           >删除</t-button>
        </template>
      </TableColumn>
    </CustomTable>
    <t-pagination
      @page-size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current="queryInfo.pageNum"
      :page-size-options="pageSizes"
      :page-size="queryInfo.pageSize"

      :total="total"
    >
    </t-pagination>
  </t-card>
  <!-- 弹出窗口 -->
  <t-dialog
    :header="dialogTitle"
    :close-on-overlay-click="false"
    v-model:visible="dialogFormVisible"
  >
    <t-form :data="stationForm" ref="stationFormRef" :rules="stationRules">
      <t-form-item label="分机号" :label-width="formLabelWidth" name="extnNum">
        <t-input
          size="small"
          v-model="stationForm.extnNum"
          autocomplete="off"
        ></t-input>
      </t-form-item>
      <t-form-item label="分机IP" :label-width="formLabelWidth" name="extnIp">
        <t-input
          size="small"
          v-model="stationForm.extnIp"
          autocomplete="off"
        ></t-input>
      </t-form-item>
      <t-form-item label="电脑IP" :label-width="formLabelWidth" name="pcIp">
        <t-input
          size="small"
          v-model="stationForm.pcIp"
          autocomplete="off"
          :disabled="pcIpDisabled"
        ></t-input>
      </t-form-item>
      <t-form-item
        label="员工绑定"
        :label-width="formLabelWidth"
        name="ploNum"
      >
        <t-input
          size="small"
          v-model="stationForm.ploNum"
          autocomplete="off"
        ></t-input>
      </t-form-item>
      <t-form-item label="备注" :label-width="formLabelWidth" name="memo">
        <t-textarea size="small"
          :autosize="{ minRows: 2, maxRows: 4 }"
          placeholder="信息点备注"
          v-model="stationForm.memo" />
      </t-form-item>
    </t-form>
    <template #footer>
      <t-space>
        <t-button variant="outline" size="small" @click="dialogFormVisible = false"
          >取 消</t-button
        >
        <t-button variant="outline" size="small" theme="primary" @click="dialogFormSubmit"
          >确 定</t-button
        >
      </t-space>
    </template>
  </t-dialog>
</template>

<script setup>
import { MessagePlugin } from 'tdesign-vue-next'
import { ref, reactive, onMounted } from 'vue'
import { stationApi } from '@/api/system/station'
import { usePagination } from '@/hooks/usePagination'
import { useConfirm } from '@/hooks/useConfirm'

const stationList = ref([])
const queryInfo = reactive({
  orderType: 'asc',
  order: 'extnNum',
  queryType: 'pcIp',
  query: '',
  pageSize: 20,
  pageNum: 1
})
const total = ref(0)
const pcIpDisabled = ref(true)
const stationForm = reactive({
  extnNum: '',
  extnIp: '',
  pcIp: '',
  ploNum: '',
  memo: ''
})
const formLabelWidth = '120px'
const dialogFormVisible = ref(false)
const dialogTitle = ref('')
const stationFormRef = ref(null)

const stationRules = reactive({
  extnNum: [
    {
      required: true,
      max: 5,
      min: 5,
      message: '请输入5位数字分机号！',
      trigger: 'blur'
    }
  ],
  pcIp: [
    {
      required: true,
      max: 15,
      min: 7,
      message: '请输入电脑IP地址！',
      trigger: 'blur'
    }
  ]
})

onMounted(() => {
  getStationList()
})

const getStationList = async () => {
  const res = await stationApi.listStation(queryInfo)
  if (res.code !== 200) return MessagePlugin.error(res.msg)
  stationList.value = res.data.list
  total.value = res.data.total
}

const handleEdit = (index, row) => {
  dialogTitle.value = '修改信息点'
  pcIpDisabled.value = true
  dialogFormVisible.value = true
  Object.assign(stationForm, row)
}

const handleDelete = async (index, row) => {
  const ok = await confirm('此操作将永久删除该记录, 是否继续?')
  if (!ok) return
  const res = await stationApi.deleteStation(row.pcIp)
  if (res.code !== 200) return MessagePlugin.error(res.msg)
  MessagePlugin.success(res.msg)
  getStationList()
}

const addStation = () => {
  dialogTitle.value = '信息点新增'
  pcIpDisabled.value = false
  dialogFormVisible.value = true
  Object.assign(stationForm, {
    extnNum: '',
    extnIp: '',
    pcIp: '',
    ploNum: '',
    memo: ''
  })
}

const { pageSizes, handleCurrentChange, handleSizeChange } = usePagination({
  query: queryInfo,
  fetch: getStationList,
  pageSizes: [20, 100, 500],
  resetToFirstOnSizeChange: false
})
const { confirm } = useConfirm()

const dialogFormSubmit = async () => {
  const valid = await stationFormRef.value.validate()
  if (valid !== true) return

  try {
    const res =
      dialogTitle.value === '信息点新增'
        ? await stationApi.addStation('station', stationForm)
        : await stationApi.updateStation('station', stationForm)
    if (res.code !== 200) return
    MessagePlugin.success(res.msg)
    dialogFormVisible.value = false
    getStationList()
  } catch (error) {
    MessagePlugin.error('表单校验失败！请检查表单！')
  }
}

const tableSort = ({ sortBy, descending }) => {
  queryInfo.orderType = !descending ? ' asc ' : ' desc '
  queryInfo.order = sortBy
  getStationList()
}
</script>

<style lang="less" scoped>
.management-card {
  height: calc(100vh - 240px);
  overflow: auto;
}
</style>

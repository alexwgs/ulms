<template>
  <el-alert
    title="操作说明"
    type="info"
    :closable="false"
    description="请正确使用信息点信息：1.基于IP地址获取终端相关信息及员工登录信息。2.分机号码已无实际意义，主要为过往登记Avaya电话分机。3.楼层仅供参考，部分楼层搬位置后IP已与楼层不一一对应。"
  />
  <el-card class="box-card">
    <el-row :gutter="20">
      <el-col :span="14">
        <el-input
          size="small"
          placeholder="请输入内容"
          v-model="queryInfo.query"
          class="input-with-select"
        >
          <template #prepend>
            <el-select
              size="small"
              style="width: 100px"
              v-model="queryInfo.queryType"
              placeholder="请选择"
            >
              <el-option label="分机号" value="extnNum"></el-option>
              <el-option label="PC_IP" value="pcIp"></el-option>
              <el-option label="MEMO" value="memo"></el-option>
              <el-option label="员工编号" value="ploNum"></el-option>
              <el-option label="楼层" value="floorNum"></el-option>
            </el-select>
          </template>
          <template #append>
            <el-button
              icon="Search"
              size="small"
              @click="getStationList"
            ></el-button>
          </template>
        </el-input>
      </el-col>
      <el-col :span="10">
        <el-button type="primary" size="small" @click="addStation"
          >添加信息点</el-button
        >
      </el-col>
    </el-row>
    <el-alert title="操作说明" type="info" :closable="false">
      <template #description>
        请正确使用部门编号：1.一共需设置4位数字。2.第一位为地区编码3为武汉。3.第二位为科室编号。4.最后两位为组别编号。
      </template>
    </el-alert>
    <el-table
      :data="stationList"
      size="small"
      @sort-change="tableSort"
      height="calc(100vh - 400px)"
    >
      <el-table-column type="index"></el-table-column>
      <el-table-column
        label="分机号"
        prop="extnNum"
        sortable="custom"
      ></el-table-column>
      <el-table-column
        label="楼层"
        prop="floorNum"
        sortable="custom"
      ></el-table-column>
      <el-table-column
        label="分机IP"
        prop="extnIp"
        sortable="custom"
      ></el-table-column>
      <el-table-column
        label="电脑IP"
        prop="pcIp"
        sortable="custom"
      ></el-table-column>
      <el-table-column
        label="员工"
        prop="ploNum"
        sortable="custom"
      ></el-table-column>
      <el-table-column
        label="更新时间"
        prop="updateTime"
        sortable="custom"
      ></el-table-column>
      <el-table-column
        label="备注"
        prop="memo"
        sortable="custom"
        show-overflow-tooltip
      >
        <template #default="scope">
          <div v-if="scope.row.memo && JSON.parse(scope.row.memo).os">
            操作系统：{{ JSON.parse(scope.row.memo).os }} 系统位数：{{
              JSON.parse(scope.row.memo).osbit
            }}<br />
            客户端信息：{{ JSON.parse(scope.row.memo).clientInfo }}
          </div>
        </template>
      </el-table-column>
      <el-table-column label="操作" fixed="right" width="120">
        <template #default="scope">
          <el-button
            size="small"
            icon="Edit"
            type="warning"
            @click="handleEdit(scope.$index, scope.row)"
            circle
          ></el-button>
          <el-button
            size="small"
            icon="Delete"
            type="danger"
            @click="handleDelete(scope.$index, scope.row)"
            circle
          ></el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current-page="queryInfo.pageNum"
      :page-sizes="pageSizes"
      :page-size="queryInfo.pageSize"
      layout="total, sizes, prev, pager, next, jumper"
      :total="total"
    >
    </el-pagination>
  </el-card>
  <!-- 弹出窗口 -->
  <el-dialog
    :title="dialogTitle"
    :close-on-click-modal="false"
    v-model="dialogFormVisible"
  >
    <el-form :model="stationForm" ref="stationFormRef" :rules="stationRules">
      <el-form-item label="分机号" :label-width="formLabelWidth" prop="extnNum">
        <el-input
          size="small"
          v-model="stationForm.extnNum"
          autocomplete="off"
        ></el-input>
      </el-form-item>
      <el-form-item label="分机IP" :label-width="formLabelWidth" prop="extnIp">
        <el-input
          size="small"
          v-model="stationForm.extnIp"
          autocomplete="off"
        ></el-input>
      </el-form-item>
      <el-form-item label="电脑IP" :label-width="formLabelWidth" prop="pcIp">
        <el-input
          size="small"
          v-model="stationForm.pcIp"
          autocomplete="off"
          :disabled="pcIpDisabled"
        ></el-input>
      </el-form-item>
      <el-form-item
        label="员工绑定"
        :label-width="formLabelWidth"
        prop="ploNum"
      >
        <el-input
          size="small"
          v-model="stationForm.ploNum"
          autocomplete="off"
        ></el-input>
      </el-form-item>
      <el-form-item label="备注" :label-width="formLabelWidth" prop="memo">
        <el-input
          size="small"
          type="textarea"
          :autosize="{ minRows: 2, maxRows: 4 }"
          placeholder="信息点备注"
          v-model="stationForm.memo"
        >
        </el-input>
      </el-form-item>
    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button size="small" @click="dialogFormVisible = false"
          >取 消</el-button
        >
        <el-button size="small" type="primary" @click="dialogFormSubmit"
          >确 定</el-button
        >
      </span>
    </template>
  </el-dialog>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { stationApi } from '@/api/system/station'

const stationList = ref([])
const queryInfo = reactive({
  orderType: 'asc',
  order: 'extnNum',
  queryType: 'pcIp',
  query: '',
  pageSize: 20,
  pageNum: 1
})
const pageSizes = [20, 100, 500]
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
  if (res.code !== 200) return ElMessage.error(res.msg)
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
  try {
    await ElMessageBox.confirm('此操作将永久删除该记录, 是否继续?', '提示', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    }).then(async () => {
      const res = await stationApi.deleteStation(row.pcIp)
      if (res.code !== 200) return ElMessage.error(res.msg)
      ElMessage.success(res.msg)
      getStationList()
    })
  } catch (error) {
    ElMessage.error('取消删除')
  }
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

const handleSizeChange = (pageSize) => {
  queryInfo.pageSize = pageSize
  getStationList()
}

const handleCurrentChange = (page) => {
  queryInfo.pageNum = page
  getStationList()
}

const dialogFormSubmit = async () => {
  try {
    await stationFormRef.value.validate()
    const res =
      dialogTitle.value === '信息点新增'
        ? await stationApi.addStation('station', stationForm)
        : await stationApi.updateStation('station', stationForm)
    if (res.code !== 200) return
    ElMessage.success(res.msg)
    dialogFormVisible.value = false
    getStationList()
  } catch (error) {
    ElMessage.error('表单校验失败！请检查表单！')
  }
}

const tableSort = ({ order, prop }) => {
  queryInfo.orderType = order === 'ascending' ? ' asc ' : ' desc '
  queryInfo.order = prop
  getStationList()
}
</script>

<style lang="less" scoped>
.box-card {
  height: calc(100vh - 240px);
  overflow: auto;
}
</style>

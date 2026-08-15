<template>
  <PageTips title="操作说明" theme="info" :closable="false"
    message="请正确使用岗位信息：1.岗位编号为3位数字，第一位按照一二线区分设置。2.理论上数值越大岗位级别越高。3.请不要随意设置岗位。" />
  <t-card class="management-card">
    <t-form :data="queryInfo" label-width="80px" colon class="filter-form">
      <t-row :gutter="[24, 24]">
        <t-col :span="4">
          <t-form-item label="岗位状态" name="jobStatus">
            <t-select size="small" v-model="queryInfo.jobStatus" @change="getList" placeholder="全部">
              <t-option value="" label="全部"></t-option>
              <t-option v-for="item in (dictStore.dictList?.sys_dict_status || [])" :key="item.id" :label="item.codeval"
                :value="String(item.code)"></t-option>
            </t-select>
          </t-form-item>
        </t-col>
        <t-col :span="3" class="operation-container">
          <t-button variant="outline" theme="primary" size="small" @click="configManageRef?.openDialog()">新增</t-button>
        </t-col>
      </t-row>
    </t-form>
    <div class="table-container">
      <CustomTable rowKey="id" :data="tableData" size="small" sortable="custom" @sort-change="tableSort" stripe
        height="calc(100vh - 380px)">
        <TableColumn colKey="jobLevel" sortable="custom" label="岗位编号" width="160"></TableColumn>
        <TableColumn colKey="jobName" sortable="custom" label="岗位名称" width="120"></TableColumn>
        <TableColumn colKey="deptNum" sortable="custom" label="部门编号"></TableColumn>
        <TableColumn colKey="jobDesc" sortable="custom" label="岗位描述"></TableColumn>
        <TableColumn colKey="organ" label="机构号" width="160"></TableColumn>
        <TableColumn colKey="status" label="状态" width="120">
          <template #default="scope">
            <t-tag size="small" :theme="String(scope.row.jobStatus) === '0' ? 'danger' : 'success'" variant="light">{{
              dictStore.getDictLabel('sys_dict_status', scope.row.jobStatus)
            }}</t-tag>
          </template>
        </TableColumn>
        <TableColumn colKey="status" label="操作" width="120">
          <template #default="scope">
            <t-button variant="outline" theme="default" size="small" @click="
              configManageRef?.openDialog(
                JSON.parse(JSON.stringify(scope.row))
              )
              ">编辑</t-button>
          </template>
        </TableColumn>
      </CustomTable>
      <t-pagination @page-size-change="handleSizeChange" @current-change="handleCurrentChange"
        :current="queryInfo.pageNum" :page-size-options="pageSizes" :page-size="queryInfo.pageSize"
 :total="total">
      </t-pagination>
    </div>
  </t-card>
  <ConfigManage ref="configManageRef"></ConfigManage>
</template>

<script setup>
import { MessagePlugin } from 'tdesign-vue-next'
import { ref, reactive, onMounted } from 'vue'
import ConfigManage from './components/ConfigManage.vue'
import { jobinfoApi } from '@/api/system/jobinfo'
import { useDictStore } from '@/stores'

const dictStore = useDictStore()
const configManageRef = ref(null)

const tableData = ref([])
const dataRange = ref([])
const queryInfo = reactive({
  orderType: ' desc',
  order: ' jobLevel ',
  status: '',
  query: '',
  jobStatus: '',
  pageSize: 20,
  pageNum: 1
})
const pageSizes = [20, 100, 500]
const total = ref(0)

onMounted(() => {
  getList()
})

const getList = async () => {
  const res = await jobinfoApi.listAllJobLevel(queryInfo)
  if (res.code !== 200) return MessagePlugin.error(res.msg)
  tableData.value = res.data.list
  total.value = res.data.total
}

const daterangeChange = () => {
  queryInfo.begDate = dataRange.value[0]
  queryInfo.endDate = dataRange.value[1]
  getList()
}

const handleSizeChange = (pageSize) => {
  queryInfo.pageSize = pageSize
  getList()
}

const handleCurrentChange = (page) => {
  queryInfo.pageNum = page
  getList()
}

const tableSort = ({ sortBy, descending }) => {
  queryInfo.orderType = !descending ? ' asc ' : ' desc '
  queryInfo.order = sortBy
  getList()
}
</script>

<style lang="less" scoped>
.table-filter {
  padding: 0;
}

.t-link {
  font-size: 12px;
}

.management-card {
  height: calc(100vh - 240px);
  overflow: auto;
}
</style>

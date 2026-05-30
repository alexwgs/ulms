<template>
  <el-alert title="操作说明" type="info" :closable="false"
    description="请正确使用岗位信息：1.岗位编号为3位数字，第一位按照一二线区分设置。2.理论上数值越大岗位级别越高。3.请不要随意设置岗位。" />
  <el-card class="box-card">
    <div class="table-filter">
      <el-row>
        <el-col :span="6" v-if="false"> </el-col>
        <el-col :span="4">
          <span>
            <el-select size="small" v-model="queryInfo.jobStatus" @change="getList" placeholder="岗位状态">
              <el-option value="" label="全部"></el-option>
              <el-option v-for="item in dictStore.dictList.sys_dict_status" :key="item.id" :label="item.codeval"
                :value="String(item.code)"></el-option>
            </el-select>
          </span>
        </el-col>
        <el-col :span="12"> </el-col>
        <el-col :span="2">
          <el-button type="primary" size="small" @click="configManageRef?.openDialog()">新增</el-button>
        </el-col>
      </el-row>
      <el-table :data="tableData" size="small" sortable="custom" @sort-change="tableSort" stripe
        height="calc(100vh - 380px)">
        <el-table-column prop="jobLevel" sortable="custom" label="岗位编号" width="160"></el-table-column>
        <el-table-column prop="jobName" sortable="custom" label="岗位名称" width="120"></el-table-column>
        <el-table-column prop="deptNum" sortable="custom" label="部门编号"></el-table-column>
        <el-table-column prop="jobDesc" sortable="custom" label="岗位描述"></el-table-column>
        <el-table-column prop="organ" label="机构号" width="160"></el-table-column>
        <el-table-column prop="status" label="状态" width="120">
          <template #default="scope">
            <el-tag size="small" :type="String(scope.row.jobStatus) === '0' ? 'danger' : 'success'" effect="dark">{{
              dictStore.getDictLabel('sys_dict_status', scope.row.jobStatus)
            }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="status" label="操作" width="120">
          <template #default="scope">
            <el-button type="primary" size="small" icon="Edit" @click="
              configManageRef?.openDialog(
                JSON.parse(JSON.stringify(scope.row))
              )
              " circle></el-button>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange"
        :current-page="queryInfo.pageNum" :page-sizes="pageSizes" :page-size="queryInfo.pageSize"
        layout="total, sizes, prev, pager, next, jumper" :total="total">
      </el-pagination>
    </div>
  </el-card>
  <ConfigManage ref="configManageRef"></ConfigManage>
</template>

<script setup>
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
  if (res.code !== 200) return ElMessage.error(res.msg)
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

const tableSort = ({ order, prop }) => {
  queryInfo.orderType = order === 'ascending' ? ' asc ' : ' desc '
  queryInfo.order = prop
  getList()
}
</script>

<style lang="less" scoped>
.table-filter {
  padding: 10px;

  span {
    font-size: 12px;
    margin-left: 20px;
  }

  .el-select {
    width: 55%;
  }
}

.el-link {
  font-size: 12px;
}

.box-card {
  height: calc(100vh - 240px);
  overflow: auto;
}
</style>

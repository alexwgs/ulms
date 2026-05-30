<template>
  <div>
    <el-card class="box-card">
      <div class="table-filter">
        <el-row :gutter="15">
          <el-col :span="9">
            <el-date-picker size="small" style="width:100%" v-model="params.dataDate" value-format="yyyy-MM-dd"
              type="date" @change="datePick" placeholder="选择日期"></el-date-picker>
          </el-col>
          <el-col :span="9">
            <el-input placeholder="点击右边按钮选择人员" size="small" v-model="userList">
              <template #append>
                <el-button size="small" @click="treeDialogVisiable = !treeDialogVisiable"
                  icon="el-icon-zoom-in"></el-button>
              </template>
            </el-input>
          </el-col>
          <el-col :span="6">
            <el-button size="small" type="primary" @click="getStatusJourDetail">查询</el-button>
            <el-button size="small" type="primary" @click="handleDownloadExcel">报表</el-button>
          </el-col>
        </el-row>
      </div>
      <el-table :data="statusList" size="small" stripe height="calc(100vh - 325px)">
        <el-table-column prop="begTime" label="开始时间"></el-table-column>
        <el-table-column prop="endTime" label="结束时间"></el-table-column>
        <el-table-column prop="userId" label="用户工号"></el-table-column>
        <el-table-column prop="level1" label="一级状态"></el-table-column>
        <el-table-column prop="level2" label="二级状态"></el-table-column>
        <el-table-column prop="level3" label="三级状态"></el-table-column>
        <el-table-column prop="memo" label="备注"></el-table-column>
        <el-table-column prop="identity" label="身份"></el-table-column>
      </el-table>
      <EmpTree v-model:treeVisiable="treeDialogVisiable" type="101" :mutiselect="true" @getChecked="getTreeChecked">
      </EmpTree>
    </el-card>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue'
import { ElMessage } from 'element-plus'
import { httpInstance, downloadExcel as downloadExcelUtil } from '@/utils/request'
import EmpTree from '@/components/EmpTree.vue'

const userList = ref('')
const treeDialogVisiable = ref(false)
const statusList = ref([])

const params = reactive({
  userList: '',
  dataDate: new Date()
})

const getTreeChecked = (data) => {
  userList.value = ''
  params.userList = ''
  for (const item of data) {
    userList.value += item.label + ','
    params.userList += item.id + ','
  }
  params.userList = params.userList.substring(0, params.userList.length - 1)
  if (params.userList.length !== 0 && params.dataDate.length !== 0) {
    getStatusJourDetail()
  }
}

const datePick = () => {
  if (params.userList.length !== 0 && params.dataDate.length !== 0) {
    getStatusJourDetail()
  }
}

const getStatusJourDetail = async () => {
  try {
    const res = await httpInstance.get('oht/statusJour/detail', { params })
    if (res.code !== 200) {
      ElMessage.error(res.msg)
      return
    }
    statusList.value = res.data
  } catch (error) {
    ElMessage.error('获取数据失败')
    console.error(error)
  }
}

const handleDownloadExcel = () => {
  downloadExcelUtil('oht/statusJour/download/detail', params, '业务主任状态明细.xlsx')
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
    width: 70%;
  }
}

.box-card {
  height: calc(100vh - 190px);
  overflow: auto;
}
</style>

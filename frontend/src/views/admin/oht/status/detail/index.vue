<template>
  <div>
    <t-card class="box-card">
      <div class="table-filter">
        <t-row :gutter="15">
          <t-col :span="5">
            <t-date-picker size="small" style="width:100%" v-model="params.dataDate" format="YYYY-MM-DD"
              mode="date" @change="datePick" placeholder="选择日期"></t-date-picker>
          </t-col>
          <t-col :span="5">
            <t-input placeholder="点击右边按钮选择人员" size="small" v-model="userList">
              <template #append>
                <t-button size="small" @click="treeDialogVisiable = !treeDialogVisiable"><template #icon><DynamicIcon name="zoom-in" /></template></t-button>
              </template>
            </t-input>
          </t-col>
          <t-col :span="3">
            <t-button size="small" theme="primary" @click="getStatusJourDetail">查询</t-button>
            <t-button size="small" theme="primary" @click="handleDownloadExcel">报表</t-button>
          </t-col>
        </t-row>
      </div>
      <CustomTable rowKey="id" :data="statusList" size="small" stripe height="calc(100vh - 325px)">
        <TableColumn colKey="begTime" label="开始时间"></TableColumn>
        <TableColumn colKey="endTime" label="结束时间"></TableColumn>
        <TableColumn colKey="userId" label="用户工号"></TableColumn>
        <TableColumn colKey="level1" label="一级状态"></TableColumn>
        <TableColumn colKey="level2" label="二级状态"></TableColumn>
        <TableColumn colKey="level3" label="三级状态"></TableColumn>
        <TableColumn colKey="memo" label="备注"></TableColumn>
        <TableColumn colKey="identity" label="身份"></TableColumn>
      </CustomTable>
      <EmpTree v-model:treeVisiable="treeDialogVisiable" type="101" :mutiselect="true" @getChecked="getTreeChecked">
      </EmpTree>
    </t-card>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue'
import { MessagePlugin } from 'tdesign-vue-next'
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
      MessagePlugin.error(res.msg)
      return
    }
    statusList.value = res.data
  } catch (error) {
    MessagePlugin.error('获取数据失败')
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

  .t-select {
    width: 70%;
  }
}

.box-card {
  height: calc(100vh - 190px);
  overflow: auto;
}
</style>

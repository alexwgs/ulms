<template>
<div style="height:100%">
  <t-card class="management-card">
    <t-row :gutter="15">
      <t-col :span="4">
      </t-col>
      <t-col :span="8">
        <t-button variant="outline" theme="primary" size="small" @click="() => uploadExcelRef.show({url:'college/group/upload'})"><template #icon><DynamicIcon name="add" /></template>导入分组</t-button>
      </t-col>
    </t-row>
    <t-row :gutter="15">
      <t-col :span="12">
        <CustomTable rowKey="id" :data="data" size="small" height="calc(100vh - 250px)" stripe style="width: 100%">
          <TableColumn colKey="groupName" label="授课对象分组" sortable="custom" ellipsis>
          </TableColumn>
          <TableColumn colKey="ploNum" label="人数" sortable="custom" ellipsis>
          </TableColumn>
          <TableColumn label="操作" fixed="right" width="100px">
            <template #default="scope">
              <t-button variant="outline" theme="default" size="small" @click="getList(scope.row.groupName)">编辑</t-button>
            </template>
          </TableColumn>
        </CustomTable>
      </t-col>
    </t-row>
  </t-card>
    <t-dialog header="名单管理" v-model:visible="dialogVisible" :close-on-overlay-click="false" width="70%">
      <t-row :gutter="10">
        <t-col :span="5">
          <t-input-adornment>
              <template #prepend>
              <t-select v-model="empListQueryInfo.queryType" placeholder="请选择">
                  <t-option label="姓名" value="ploName"></t-option>
                  <t-option label="工号" value="ploNum"></t-option>
              </t-select>
              </template>
              <template #append>
              <t-button variant="outline" theme="primary" @click="getList()">搜索</t-button>
              </template>
              <t-input placeholder="请输入对应的搜索内容" v-model="empListQueryInfo.query" size="small"></t-input>
          </t-input-adornment>
        </t-col>
        <t-col :span="5">
          <t-input-adornment>
              <template #append>
              <t-button variant="outline" theme="primary" @click="addUser({groupName: empListQueryInfo.groupName, ploNum: ploNum})"><template #icon><DynamicIcon name="add" /></template>增加</t-button>
              </template>
              <t-input placeholder="输入工号添加" v-model="ploNum" size="small"></t-input>
          </t-input-adornment>
        </t-col>
      </t-row>
        <CustomTable rowKey="id" :data="empList" size="small" height="400px" stripe @sort-change="tableSort" style="width: 100%">
            <TableColumn colKey="deptNum" label="科室" width="120px">
              <template #default="scope">{{scope.row.user.deptName}}</template>
            </TableColumn>
            <TableColumn colKey="deptGroup" label="组别" width="120px">
              <template #default="scope">{{scope.row.user.groupName}}</template>
            </TableColumn>
            <TableColumn colKey="ploNum" label="员编" sortable="custom"></TableColumn>
            <TableColumn colKey="ploName" label="姓名" width="100px">
              <template #default="scope">{{scope.row.user.ploName}}</template>
            </TableColumn>
            <TableColumn colKey="batchGroup" label="期数" width="120px">
              <template #default="scope">{{scope.row.user.batchGroup}}</template>
            </TableColumn>
            <TableColumn colKey="jobLevel" label="岗位码" width="120px">
              <template #default="scope">{{scope.row.user.jobLevel}}</template>
            </TableColumn>
            <TableColumn label="操作"  width="80px">
                <template #default="scope">
                    <t-button variant="outline" theme="danger" size="small" @click="()=>{ deleteUser(scope.row) }">删除</t-button>
                </template>
            </TableColumn>
        </CustomTable>
        <t-pagination
            @page-size-change="handleSizeChange"
            @current-change="handleCurrentChange"
            :current="empListQueryInfo.pageNum"
            :page-size-options="pageSizes"
            :page-size="empListQueryInfo.pageSize"

            :total="total">
        </t-pagination>
        <template #footer>
            <t-space>
                <t-button size="small" variant="outline" @click="dialogVisible = false">取消</t-button>
                <t-button size="small" variant="outline" theme="primary" @click="dialogVisible = false">确定</t-button>
            </t-space>
        </template>
    </t-dialog>
  <UploadExcel ref="uploadExcelRef" title="授课对象分组导入" info="不在分组名单内为全员；导入【授课对象分组】，表头需有“员编”、“授课对象”列！ "></UploadExcel>
</div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { MessagePlugin } from 'tdesign-vue-next'
import UploadExcel from '../../../../components/UploadExcel.vue'
import { teachGroupApi } from '@/api/college/teachGroup'  

const uploadExcelRef = ref(null)

const queryInfo = reactive({
  orderType: ' desc',
  order: 'dataDate',
  queryType: '',
  status: '',
  query: '',
  pageSize: 20,
  pageNum: 1
})

const empListQueryInfo = reactive({
  orderType: ' desc',
  order: 'ploNum',
  queryType: 'ploNum',
  query: '',
  groupName: '',
  pageSize: 20,
  pageNum: 1
})

const ploNum = ref('')
const dialogVisible = ref(false)
const empList = ref([])
const data = ref([])
const total = ref(0)
const pageSizes = [10, 20, 50, 100]

const getTeachGroup = async () => {
  try {
    const res = await teachGroupApi.getTeachGroupList()
    if (res.code !== 200) return MessagePlugin.error(res.msg)
    data.value = res.data
  } catch (error) {
    MessagePlugin.error('获取授课对象分组列表失败')
  }
}

const getList = async (groupName) => {
  if (groupName) {
    empListQueryInfo.groupName = groupName
    dialogVisible.value = true
  }
  try {
    const res = await teachGroupApi.getTeachGroupUserList(empListQueryInfo)
    if (res.code !== 200) return MessagePlugin.error(res.msg)
    empList.value = res.data.list
    total.value = res.data.total
  } catch (error) {
    MessagePlugin.error('获取分组用户列表失败')
  }
}

const deleteUser = async (record) => {
  try {
    const res = await teachGroupApi.deleteTeachGroupUser(record)
    if (res.code !== 200) return MessagePlugin.error(res.msg)
    MessagePlugin.success(res.msg)
    getList()
    getTeachGroup()
  } catch (error) {
    MessagePlugin.error('删除用户失败')
  }
}

const addUser = async (record) => {
  try {
    const res = await teachGroupApi.addTeachGroupUser(record)
    if (res.code !== 200) return MessagePlugin.error(res.msg)
    MessagePlugin.success(res.msg)
    getList()
    getTeachGroup()
    ploNum.value = ''
  } catch (error) {
    MessagePlugin.error('添加用户失败')
  }
}

const handleSizeChange = (pageSize) => {
  empListQueryInfo.pageSize = pageSize
  getList()
}

const handleCurrentChange = (page) => {
  empListQueryInfo.pageNum = page
  getList()
}

const tableSort = (data) => {
  if (!data.descending) empListQueryInfo.orderType = ' asc '
  else if (data.descending) empListQueryInfo.orderType = ' desc '
  empListQueryInfo.order = data.sortBy
  getList()
}

onMounted(() => {
  getTeachGroup()
})
</script>

<style lang="less" scoped>
</style>

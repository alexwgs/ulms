<template>
<div style="height:100%">
  <el-card class="box-card">
    <el-row :gutter="15">
      <el-col :span="8">
      </el-col>
      <el-col :span="16">
        <el-button type="primary" size="small" icon="Plus" @click="() => uploadExcelRef.show({url:'college/group/upload'})"></el-button>
      </el-col>
    </el-row>
    <el-row :gutter="15">
      <el-col :span="24">
        <el-table :data="data" size="small" height="calc(100vh - 250px)" stripe style="width: 100%">
          <el-table-column prop="groupName" label="授课对象分组" sortable="custom" show-overflow-tooltip>
          </el-table-column>
          <el-table-column prop="ploNum" label="人数" sortable="custom" show-overflow-tooltip>
          </el-table-column>
          <el-table-column label="操作" fixed="right" width="100px">
            <template #default="scope">
              <el-button type="primary" icon="Edit" size="small"  @click="getList(scope.row.groupName)" circle></el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-col>
    </el-row>
  </el-card>
    <el-dialog title="名单管理" v-model="dialogVisible" :close-on-click-modal="false" width="70%">
      <el-row :gutter="10">
        <el-col :span="10">
          <el-input placeholder="请输入对应的搜索内容" v-model="empListQueryInfo.query" size="small">
              <el-select v-model="empListQueryInfo.queryType" slot="prepend" style="width:130px" placeholder="请选择">
                  <el-option label="姓名" value="ploName"></el-option>
                  <el-option label="工号" value="ploNum"></el-option>
              </el-select>
              <el-button slot="append" icon="Search" @click="getList()"></el-button>
          </el-input>
        </el-col>
        <el-col :span="10">
          <el-input placeholder="输入工号添加" v-model="ploNum" size="small">
              <el-button slot="append" type="primary" icon="CirclePlus" @click="addUser({groupName: empListQueryInfo.groupName, ploNum: ploNum})">增加</el-button>
          </el-input>
        </el-col>
      </el-row>
        <el-table :data="empList" size="small" height="400px" stripe @sort-change="tableSort" style="width: 100%">
            <el-table-column prop="deptNum" label="科室" width="120px">
              <template #default="scope">{{scope.row.user.deptName}}</template>
            </el-table-column>
            <el-table-column prop="deptGroup" label="组别" width="120px">
              <template #default="scope">{{scope.row.user.groupName}}</template>
            </el-table-column>
            <el-table-column prop="ploNum" label="员编" sortable="custom"></el-table-column>
            <el-table-column prop="ploName" label="姓名" width="100px">
              <template #default="scope">{{scope.row.user.ploName}}</template>
            </el-table-column>
            <el-table-column prop="batchGroup" label="期数" width="120px">
              <template #default="scope">{{scope.row.user.batchGroup}}</template>
            </el-table-column>
            <el-table-column prop="jobLevel" label="岗位码" width="120px">
              <template #default="scope">{{scope.row.user.jobLevel}}</template>
            </el-table-column>
            <el-table-column label="操作"  width="80px">
                <template #default="scope">
                    <el-button type="danger" icon="Delete"  size="small" @click="()=>{ deleteUser(scope.row) }" circle></el-button>
                </template>
            </el-table-column>
        </el-table>
        <el-pagination
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange"
            :current-page="empListQueryInfo.pageNum"
            :page-sizes="pageSizes"
            :page-size="empListQueryInfo.pageSize"
            layout="total, sizes, prev, pager, next, jumper"
            :total="total">
        </el-pagination>
        <template #footer>
            <div class="dialog-footer">
                <el-button size="small" @click="dialogVisible = false">取 消</el-button>
                <el-button size="small" type="primary" @click="dialogVisible = false">确 定</el-button>
            </div>
        </template>
    </el-dialog>
  <UploadExcel ref="uploadExcelRef" title="授课对象分组导入" info="不在分组名单内为全员；导入【授课对象分组】，表头需有“员编”、“授课对象”列！ "></UploadExcel>
</div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
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
    if (res.code !== 200) return ElMessage.error(res.msg)
    data.value = res.data
  } catch (error) {
    ElMessage.error('获取授课对象分组列表失败')
  }
}

const getList = async (groupName) => {
  if (groupName) {
    empListQueryInfo.groupName = groupName
    dialogVisible.value = true
  }
  try {
    const res = await teachGroupApi.getTeachGroupUserList(empListQueryInfo)
    if (res.code !== 200) return ElMessage.error(res.msg)
    empList.value = res.data.list
    total.value = res.data.total
  } catch (error) {
    ElMessage.error('获取分组用户列表失败')
  }
}

const deleteUser = async (record) => {
  try {
    const res = await teachGroupApi.deleteTeachGroupUser(record)
    if (res.code !== 200) return ElMessage.error(res.msg)
    ElMessage.success(res.msg)
    getList()
    getTeachGroup()
  } catch (error) {
    ElMessage.error('删除用户失败')
  }
}

const addUser = async (record) => {
  try {
    const res = await teachGroupApi.addTeachGroupUser(record)
    if (res.code !== 200) return ElMessage.error(res.msg)
    ElMessage.success(res.msg)
    getList()
    getTeachGroup()
    ploNum.value = ''
  } catch (error) {
    ElMessage.error('添加用户失败')
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
  if (data.order === 'ascending') empListQueryInfo.orderType = ' asc '
  else if (data.order === 'descending') empListQueryInfo.orderType = ' desc '
  empListQueryInfo.order = data.prop
  getList()
}

onMounted(() => {
  getTeachGroup()
})
</script>

<style lang="less" scoped>
</style>
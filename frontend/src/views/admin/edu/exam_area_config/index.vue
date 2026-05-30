<template>
  <div>
    <el-card class="box-card">
      <div class="text item">
        <el-row :gutter="15">
          <el-col :span="12">
            <el-row :gutter="15">
              <el-col :span="12">考场配置</el-col>
              <el-col :span="6">
                <el-select size="small" v-model="areaConfigQueryInfo.status" placeholder="状态" @change="getAreaConfig">
                  <el-option label="生效" :value="1"></el-option>
                  <el-option label="失效" :value="0"></el-option>
                </el-select>
              </el-col>
              <el-col :span="6">
                <el-button type="primary" size="small" @click="addAreaConfigBtn">新增配置</el-button>
              </el-col>
            </el-row>
            <el-table :data="areaConfig" size="small" stripe height="calc(100vh - 360px)">
              <el-table-column prop="areaName" label="配置名称" width="130"></el-table-column>
              <el-table-column prop="areaDesc" show-overflow-tooltip label="配置说明"></el-table-column>
              <el-table-column label="状态" width="80">
                <template #default="scope">
                  <el-tag size="small" :type="scope.row.areaStat ? 'success' : 'danger'" effect="plain">{{ scope.row.areaStat ? '生效' : '失效' }}</el-tag>
                </template>
              </el-table-column>
              <el-table-column prop="handlePlo" label="维护工号" width="90"></el-table-column>
              <el-table-column prop="handleDate" show-overflow-tooltip label="维护时间" width="100"></el-table-column>
              <el-table-column label="操作" width="110">
                <template #default="scope">
                  <el-button type="primary" icon="Search" size="small" @click="viewAreaListBtn(scope.row)"></el-button>
                  <el-button type="warning" icon="Edit" size="small" @click="editAreaConfigBtn(scope.row)"></el-button>
                </template>
              </el-table-column>
            </el-table>
          </el-col>
          <el-col :span="12">
            <el-row :gutter="15">
              <el-col :span="12">时间配置</el-col>
              <el-col :span="6">
                <el-select size="small" v-model="timeConfigQueryInfo.status" placeholder="状态" @change="getTimeConfig">
                  <el-option label="生效" :value="1"></el-option>
                  <el-option label="失效" :value="0"></el-option>
                </el-select>
              </el-col>
              <el-col :span="6">
                <el-button type="primary" size="small" @click="addTimeConfigBtn">新增配置</el-button>
              </el-col>
            </el-row>
            <el-table :data="timeConfig" size="small" stripe height="calc(100vh - 360px)">
              <el-table-column prop="bookName" label="配置名称" width="130"></el-table-column>
              <el-table-column prop="bookDesc" show-overflow-tooltip label="配置说明"></el-table-column>
              <el-table-column label="状态" width="80">
                <template #default="scope">
                  <el-tag size="small" :type="scope.row.bookStat ? 'success' : 'danger'" effect="plain">{{ scope.row.bookStat ? '生效' : '失效' }}</el-tag>
                </template>
              </el-table-column>
              <el-table-column prop="handlePlo" label="维护工号" width="90"></el-table-column>
              <el-table-column prop="handleDate" show-overflow-tooltip label="维护时间" width="100"></el-table-column>
              <el-table-column label="操作" width="110">
                <template #default="scope">
                  <el-button type="primary" icon="Search" size="small" @click="viewTimeListBtn(scope.row)"></el-button>
                  <el-button type="warning" icon="Edit" size="small" @click="editTimeConfigBtn(scope.row)"></el-button>
                </template>
              </el-table-column>
            </el-table>
          </el-col>
        </el-row>
      </div>
    </el-card>

    <!-- 考场配置对话框 -->
    <AreaConfigDialog 
      v-model:visible="areaConifgDialogVisible" 
      :dialog-title="areaConifgDialogTitle" 
      :form-data="areaConfigForm"
      @success="getAreaConfig"
    />

    <!-- 时间配置对话框 -->
    <TimeConfigDialog 
      v-model:visible="timeConifgDialogVisible" 
      :dialog-title="timeConifgDialogTitle" 
      :form-data="timeConfigForm"
      @success="getTimeConfig"
    />

    <!-- 考场座位管理对话框 -->
    <AreaListDialog 
      v-model:visible="areaListDialogVisible" 
      :dialog-title="areaListDialogTitle" 
      :area-code="areaListQueryInfo.areaCode"
    />

    <!-- 考试时间管理对话框 -->
    <TimeListDialog 
      v-model:visible="timeListDialogVisible" 
      :dialog-title="timeListDialogTitle" 
      :book-code="timeListQueryInfo.bookCode"
    />
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import AreaConfigDialog from './components/AreaConfigDialog.vue'
import TimeConfigDialog from './components/TimeConfigDialog.vue'
import AreaListDialog from './components/AreaListDialog.vue'
import TimeListDialog from './components/TimeListDialog.vue'
import { examAreaConfigApi } from '@/api/edu/examAreaConfig'
import { bookInfoApi } from '@/api/edu/bookInfo'

// Reactive data
const areaConfigQueryInfo = reactive({
  status: 1,
  querytype: '',
  query: '',
  pageSize: 20,
  pageNum: 1
})

const areaConfig = ref([])
const areaListDialogVisible = ref(false)
const areaListQueryInfo = reactive({
  areaCode: '',
  querytype: '',
  query: '',
  pageSize: 20,
  pageNum: 1
})

const areaListDialogTitle = ref('')

// 考试配置相关参数
const areaConifgDialogTitle = ref('')
const areaConifgDialogVisible = ref(false)
const areaConfigForm = reactive({ areaCode: '', areaName: '', areaDesc: '', areaStat: 1 })

// 考试时间配置相关参数
const timeConfigQueryInfo = reactive({
  status: 1,
  querytype: '',
  query: '',
  pageSize: 20,
  pageNum: 1
})

const timeConfig = ref([])
const timeListDialogVisible = ref(false)
const timeListQueryInfo = reactive({
  bookCode: '',
  querytype: '',
  query: '',
  pageSize: 20,
  pageNum: 1
})

const timeListDialogTitle = ref('')
const timeConifgDialogTitle = ref('')
const timeConifgDialogVisible = ref(false)
const timeConfigForm = reactive({ bookCode: '', bookName: '', bookDesc: '', bookStat: 1 })

// Lifecycle
onMounted(() => {
  getAreaConfig()
  getTimeConfig()
})

// Methods
const getAreaConfig = async () => {
  const res = await examAreaConfigApi.getAreaConfigList(areaConfigQueryInfo)
  if (res.code !== 200) return ElMessage.error(res.msg)
  areaConfig.value = res.data
}

const getTimeConfig = async () => {
  const res = await bookInfoApi.getBookConfigList(timeConfigQueryInfo)
  if (res.code !== 200) return ElMessage.error(res.msg)
  timeConfig.value = res.data
}

const addAreaConfigBtn = () => {
  areaConifgDialogTitle.value = '新增考场配置'
  Object.assign(areaConfigForm, {
    areaCode: '',
    areaName: '',
    areaDesc: '',
    areaStat: 1
  })
  areaConifgDialogVisible.value = true
}

const editAreaConfigBtn = (row) => {
  Object.assign(areaConfigForm, row)
  areaConifgDialogTitle.value = '修改考场配置'
  areaConifgDialogVisible.value = true
}

const viewAreaListBtn = (row) => {
  areaListQueryInfo.areaCode = row.areaCode
  areaListDialogTitle.value = '考场座位管理'
  areaListDialogVisible.value = true
}

const addTimeConfigBtn = () => {
  timeConifgDialogTitle.value = '新增时间配置'
  Object.assign(timeConfigForm, {
    bookCode: '',
    bookName: '',
    bookDesc: '',
    bookStat: 1
  })
  timeConifgDialogVisible.value = true
}

const editTimeConfigBtn = (row) => {
  Object.assign(timeConfigForm, row)
  timeConifgDialogTitle.value = '修改时间配置'
  timeConifgDialogVisible.value = true
}

const viewTimeListBtn = (row) => {
  timeListQueryInfo.bookCode = row.bookCode
  timeListDialogTitle.value = '考试时间管理'
  timeListDialogVisible.value = true
}
</script>

<style lang="less" scoped>
.box-card {
  height: calc(100vh - 190px);
  overflow: auto;
}

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

.el-link {
  font-size: 12px;
}
</style>

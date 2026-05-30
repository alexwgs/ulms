<template>
  <el-card class="box-card">
    <el-row>
      <el-col :span="6">
        <span>
          <el-date-picker
            size="small"
            style="width: 70%"
            v-model="dataRange"
            @change="daterangeChange"
            type="daterange"
            value-format="YYYY-MM-DD"
            range-separator="至"
            start-placeholder="开始日期"
            end-placeholder="结束日期"
          />
        </span>
      </el-col>
      <el-col :span="6">
        <span>
          <el-select
            size="small"
            v-model="queryInfo.fileSuffix"
            @change="getTableList"
            placeholder="请选择,文件扩展名"
          >
            <el-option value="" label="全部"></el-option>
            <el-option
              v-for="item in suffix"
              :key="item"
              :value="item"
              :label="item"
            ></el-option>
          </el-select>
        </span>
      </el-col>
      <el-col :span="6">
        <span>
          <el-input
            size="small"
            v-model="queryInfo.fileName"
            @change="getTableList"
            clearable
            placeholder="使用文件名模糊查询"
          />
        </span>
      </el-col>
    </el-row>

    <el-table
      :data="tableData"
      size="small"
      sortable="custom"
      @sort-change="tableSort"
      height="calc(100vh - 325px)"
    >
      <el-table-column
        prop="fileId"
        sortable="custom"
        label="文件ID"
        width="150"
      ></el-table-column>
      <el-table-column
        prop="fileName"
        sortable="custom"
        label="文件名称"
        show-overflow-tooltip
      ></el-table-column>
      <el-table-column
        prop="fileSuffix"
        sortable="custom"
        label="扩展名"
        width="100"
      ></el-table-column>
      <el-table-column
        prop="filePath"
        sortable="custom"
        label="文件地址"
        width="120"
      ></el-table-column>
      <el-table-column
        prop="userId"
        sortable="custom"
        label="上传用户"
        width="100"
      ></el-table-column>
      <el-table-column
        prop="dataDate"
        sortable="custom"
        label="上传时间"
        width="160"
        show-overflow-tooltip
      ></el-table-column>
      <el-table-column sortable="custom" label="操作" width="120">
        <template #default="scope">
          <el-button-group>
            <el-button
              type="primary"
              size="small"
              :icon="Picture"
              :disabled="
                scope.row.fileSuffix !== 'jpg' && scope.row.fileSuffix !== 'png'
              "
              @click="
                perviewImage(
                  fsURL +
                    'upload/getFile/' +
                    scope.row.filePath +
                    '/' +
                    scope.row.fileId +
                    '.' +
                    scope.row.fileSuffix
                )
              "
            ></el-button>
            <el-button
              type="primary"
              size="small"
              :icon="Download"
              @click="
                downloadFile(
                  fsURL +
                    'upload/getFile/' +
                    scope.row.filePath +
                    '/' +
                    scope.row.fileId +
                    '.' +
                    scope.row.fileSuffix
                )
              "
            ></el-button>
          </el-button-group>
        </template>
      </el-table-column>
    </el-table>

    <el-dialog title="图片预览" width="700px" v-model="imageViewDialog">
      <el-image
        style="width: 600px; height: 500px"
        :src="imgUrl"
        fit="contain"
      ></el-image>
    </el-dialog>

    <!-- 分页组件 -->
    <div class="pagination-container">
      <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="currentPage"
        :page-sizes="pageSizes"
        :page-size="queryInfo.pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total"
      />
    </div>
  </el-card>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import { Picture, Download } from '@element-plus/icons-vue'
import { userFileApi } from '@/api/admin/userFiles' // 导入API函数

// 环境变量
const fsURL = ref(import.meta.env.VITE_FILE_MANAGE_BASE)

// 表格数据
const tableData = ref([])
const suffix = ref([])
const dataRange = ref([])

// 查询参数
const queryInfo = reactive({
  orderType: ' desc',
  order: ' id ',
  fileSuffix: '',
  fileName: '',
  begDate: '',
  endDate: '',
  querytype: '',
  query: '',
  pageSize: 20,
  pageNum: 1
})

// 分页参数
const currentPage = ref(1)
const pageSizes = ref([20, 100, 500])
const total = ref(0)

// 图片预览
const imageViewDialog = ref(false)
const imgUrl = ref('')

// 获取文件扩展名列表
const getSuffixList = async () => {
  try {
    const res = await userFileApi.getSuffix()
    if (res.code !== 200) {
      ElMessage.error(res.msg)
      return
    }
    suffix.value = res.data
  } catch (error) {
    ElMessage.error('获取文件扩展名失败')
    console.error(error)
  }
}

// 获取表格数据
const getTableList = async () => {
  try {
    const res = await userFileApi.getFileList(queryInfo)
    if (res.code !== 200) {
      ElMessage.error(res.msg)
      return
    }
    tableData.value = res.data.list
    total.value = res.data.total
  } catch (error) {
    ElMessage.error('获取文件列表失败')
    console.error(error)
  }
}

// 日期范围变化
const daterangeChange = () => {
  if (dataRange.value && dataRange.value.length === 2) {
    queryInfo.begDate = dataRange.value[0]
    queryInfo.endDate = dataRange.value[1]
  } else {
    queryInfo.begDate = ''
    queryInfo.endDate = ''
  }
  getTableList()
}

// 分页大小变化
const handleSizeChange = (pageSize) => {
  queryInfo.pageSize = pageSize
  getTableList()
}

// 当前页变化
const handleCurrentChange = (page) => {
  queryInfo.pageNum = page
  currentPage.value = page
  getTableList()
}

// 表格排序
const tableSort = (data) => {
  if (data.order === 'ascending') queryInfo.orderType = ' asc '
  else if (data.order === 'descending') queryInfo.orderType = ' desc '
  queryInfo.order = data.prop
  getTableList()
}

// 图片预览
const perviewImage = (url) => {
  imgUrl.value = url
  imageViewDialog.value = true
}

// 下载文件
const downloadFile = (url) => {
  // 这里可以使用window.open或者创建一个隐藏的a标签来实现下载
  window.open(url, '_blank')
}

// 组件挂载时获取数据
onMounted(() => {
  getSuffixList()
  getTableList()
})
</script>

<style scoped>
.table-filter {
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
  height: calc(100vh - 190px);
  overflow: auto;
}

.pagination-container {
  margin-top: 15px;
  text-align: right;
}
</style>

<template>
  <t-card class="box-card">
    <t-row>
      <t-col :span="3">
        <span>
          <t-date-range-picker size="small" style="width: 100%" v-model="dataRange" @change="daterangeChange" :placeholder="['开始日期', '结束日期']" />
        </span>
      </t-col>
      <t-col :span="3">
        <span>
          <t-select
            size="small"
            v-model="queryInfo.fileSuffix"
            @change="getTableList"
            placeholder="请选择,文件扩展名"
          >
            <t-option value="" label="全部"></t-option>
            <t-option
              v-for="item in suffix"
              :key="item"
              :value="item"
              :label="item"
            ></t-option>
          </t-select>
        </span>
      </t-col>
      <t-col :span="3">
        <span>
          <t-input
            size="small"
            v-model="queryInfo.fileName"
            @change="getTableList"
            clearable
            placeholder="使用文件名模糊查询"
          />
        </span>
      </t-col>
    </t-row>

    <CustomTable rowKey="id"
      :data="tableData"
      size="small"
      sortable="custom"
      @sort-change="tableSort"
      height="calc(100vh - 325px)">
      <TableColumn
        prop="fileId"
        sortable="custom"
        label="文件ID"
        width="150"></TableColumn>
      <TableColumn
        prop="fileName"
        sortable="custom"
        label="文件名称"
        ellipsis></TableColumn>
      <TableColumn
        prop="fileSuffix"
        sortable="custom"
        label="扩展名"
        width="100"></TableColumn>
      <TableColumn
        prop="filePath"
        sortable="custom"
        label="文件地址"
        width="120"></TableColumn>
      <TableColumn
        prop="userId"
        sortable="custom"
        label="上传用户"
        width="100"></TableColumn>
      <TableColumn
        prop="dataDate"
        sortable="custom"
        label="上传时间"
        width="160"
        ellipsis></TableColumn>
      <TableColumn sortable="custom" label="操作" width="120">
        <template #default="scope">
          <t-space>
            <t-button
              theme="primary"
              size="small":disabled="
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
            ><template #icon><DynamicIcon name="image" /></template></t-button>
            <t-button
              theme="primary"
              size="small" @click="
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
            ><template #icon><DynamicIcon name="download" /></template></t-button>
          </t-space>
        </template>
      </TableColumn>
    </CustomTable>

    <t-dialog header="图片预览" width="700px" v-model:visible="imageViewDialog">
      <t-image
        style="width: 600px; height: 500px"
        :src="imgUrl"
        fit="contain"
      ></t-image>
    </t-dialog>

    <!-- 分页组件 -->
    <div class="pagination-container">
      <t-pagination
        @page-size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current="currentPage"
        :page-size-options="pageSizes"
        :page-size="queryInfo.pageSize"

        :total="total"
      />
    </div>
  </t-card>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { MessagePlugin } from 'tdesign-vue-next'
import { ImageIcon, DownloadIcon } from 'tdesign-icons-vue-next'
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
      MessagePlugin.error(res.msg)
      return
    }
    suffix.value = res.data
  } catch (error) {
    MessagePlugin.error('获取文件扩展名失败')
    console.error(error)
  }
}

// 获取表格数据
const getTableList = async () => {
  try {
    const res = await userFileApi.getFileList(queryInfo)
    if (res.code !== 200) {
      MessagePlugin.error(res.msg)
      return
    }
    tableData.value = res.data.list
    total.value = res.data.total
  } catch (error) {
    MessagePlugin.error('获取文件列表失败')
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
  if (!data.descending) queryInfo.orderType = ' asc '
  else if (data.descending) queryInfo.orderType = ' desc '
  queryInfo.order = data.sortBy
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

  .t-select {
    width: 55%;
  }
}

.t-link {
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

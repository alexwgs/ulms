<template>
  <t-card class="management-card">
    <t-form :data="queryInfo" label-width="80px" colon class="filter-form">
      <t-row :gutter="[24, 24]">
        <t-col :span="4">
          <t-form-item label="日期范围" name="dataRange">
            <t-date-range-picker size="small" v-model="dataRange" @change="daterangeChange" :placeholder="['开始日期', '结束日期']" />
          </t-form-item>
        </t-col>
        <t-col :span="3">
          <t-form-item label="扩展名" name="fileSuffix">
            <t-select
              size="small"
              v-model="queryInfo.fileSuffix"
              @change="getTableList"
              placeholder="全部"
            >
              <t-option value="" label="全部"></t-option>
              <t-option
                v-for="item in suffix"
                :key="item"
                :value="item"
                :label="item"
              ></t-option>
            </t-select>
          </t-form-item>
        </t-col>
        <t-col :span="4">
          <t-form-item label="文件名" name="fileName">
            <t-input
              size="small"
              v-model="queryInfo.fileName"
              @change="getTableList"
              clearable
              placeholder="使用文件名模糊查询"
            />
          </t-form-item>
        </t-col>
      </t-row>
    </t-form>

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
              variant="outline"
              theme="primary"
              size="small" :disabled="
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
            <t-button variant="outline"
              theme="default"
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
            >下载</t-button>
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
import { ref, onMounted } from 'vue'
import { MessagePlugin } from 'tdesign-vue-next'
import { ImageIcon, DownloadIcon } from 'tdesign-icons-vue-next'
import { userFileApi } from '@/api/admin/userFiles' // 导入API函数
import { useCrudPage } from '@/hooks/useCrudPage'

// 环境变量
const fsURL = ref(import.meta.env.VITE_FILE_BASE_URL)

// 列表 + 分页（useCrudPage 样板）
const {
  list: tableData,
  total,
  query: queryInfo,
  currentPage,
  pageSizes,
  handleCurrentChange,
  handleSizeChange,
  load: getTableList
} = useCrudPage({
  fetchList: (q) => userFileApi.getFileList(q),
  defaultQuery: {
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
  },
  pageSizes: [20, 100, 500]
})

const suffix = ref([])
const dataRange = ref([])

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

// 日期范围变化
const daterangeChange = () => {
  if (dataRange.value && dataRange.value.length === 2) {
    queryInfo.value.begDate = dataRange.value[0]
    queryInfo.value.endDate = dataRange.value[1]
  } else {
    queryInfo.value.begDate = ''
    queryInfo.value.endDate = ''
  }
  getTableList()
}

// 分页大小变化


// 当前页变化


// 表格排序
const tableSort = (data) => {
  if (!data.descending) queryInfo.value.orderType = ' asc '
  else if (data.descending) queryInfo.value.orderType = ' desc '
  queryInfo.value.order = data.sortBy
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
    width: 100%;
  }
}

.t-link {
  font-size: 12px;
}

.management-card {
  height: calc(100vh - 190px);
  overflow: auto;
}

.pagination-container {
  margin-top: 15px;
  text-align: right;
}
</style>

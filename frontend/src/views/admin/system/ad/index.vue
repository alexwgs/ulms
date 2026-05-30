<template>
  <el-alert
    title="操作说明"
    type="info"
    :closable="false"
    description="请正确使用首页轮播图：1.可设置图片按年度循环播放，但选择日期不要跨年。2.图片数量建议不超过5张。3.图片尺寸建议为1920*500。"
  />
  <el-card class="box-card">
    <el-row :gutter="20">
      <el-col :span="10">
        <el-input
          placeholder="请输入内容"
          size="small"
          v-model="queryInfo.query"
          clearable
          class="input-with-select"
          @clear="getAdList"
        >
          <template #append>
            <el-button
              size="small"
              icon="Search"
              @click="getAdList"
            ></el-button>
          </template>
        </el-input>
      </el-col>
      <el-col :span="4">
        <el-select
          v-model="queryInfo.status"
          size="small"
          placeholder="全部状态"
          @change="getAdList"
        >
          <el-option label="全部状态" value="-1"></el-option>
          <el-option
            v-for="item in dictStore.dictList.sys_dict_status"
            :key="item.id"
            :label="item.codeval"
            :value="item.code"
          ></el-option>
        </el-select>
      </el-col>
      <el-col :span="10">
        <el-button type="primary" size="small" @click="addAd"
          >添加图片</el-button
        >
      </el-col>
    </el-row>
    <el-alert title="操作说明" type="info" :closable="false">
      <template #description>
        请正确使用首页轮播图：1.可设置图片按年度循环播放，但选择日期不要跨年。2.
      </template>
    </el-alert>
    <el-table
      :data="adList"
      size="small"
      height="calc(100vh - 400px)"
      @sort-change="tableSort"
      stripe
    >
      <el-table-column
        label="ID"
        prop="id"
        sortable="custom"
        width="80"
      ></el-table-column>
      <el-table-column
        label="名称"
        prop="name"
        sortable="custom"
      ></el-table-column>
      <el-table-column
        label="图片"
        prop="url"
        sortable="custom"
        show-overflow-tooltip
      >
        <template #default="scope">
          <el-image
            style="width: 100px; height: 60px"
            :src="fsURL + scope.row.url"
            :preview-src-list="[fsURL + scope.row.url]"
            fit="cover"
          ></el-image>
        </template>
      </el-table-column>
      <el-table-column
        label="开始日期"
        prop="begDate"
        sortable="custom"
      ></el-table-column>
      <el-table-column
        label="结束日期"
        prop="endDate"
        sortable="custom"
      ></el-table-column>
      <el-table-column
        label="循环标志"
        prop="loopFlag"
        sortable="custom"
        width="100"
      ></el-table-column>
      <el-table-column
        label="排序"
        prop="sort"
        sortable="custom"
        width="80"
      ></el-table-column>
      <el-table-column label="状态" prop="status" sortable="custom" width="120">
        <template #default="scope">
          <el-tag
            size="small"
            :type="scope.row.status === 0 ? 'danger' : 'success'"
            effect="dark"
          >
            {{
              dictStore.dictList.sys_dict_status.find(
                (item) => item.code === scope.row.status.toString()
              )?.codeval
            }}</el-tag
          >
        </template>
      </el-table-column>
      <el-table-column label="操作" fixed="right" width="120">
        <template #default="scope">
          <el-button
            size="small"
            icon="Edit"
            type="warning"
            @click="handleEdit(scope.row)"
            circle
          ></el-button>
          <el-button
            size="small"
            icon="Delete"
            type="danger"
            @click="handleDelete(scope.row)"
            circle
          ></el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current-page="currentPage"
      :page-sizes="pageSizes"
      :page-size="queryInfo.pageSize"
      layout="total, sizes, prev, pager, next, jumper"
      :total="total"
    ></el-pagination>
  </el-card>

  <!-- 弹出窗口 -->
  <el-dialog
    :title="dialogTitle"
    v-model="dialogFormVisible"
    :close-on-click-modal="false"
    width="50%"
  >
    <el-form
      :model="adForm"
      ref="adFormRef"
      :rules="adRules"
      label-width="120px"
    >
      <el-form-item label="名称" prop="name">
        <el-input
          size="small"
          v-model="adForm.name"
          autocomplete="off"
        ></el-input>
      </el-form-item>
      <el-form-item label="图像" prop="url">
        <el-input
          size="small"
          v-model="adForm.url"
          autocomplete="off"
        ></el-input>
        <el-upload
          class="upload-demo"
          :action="fsURL + 'upload/file/AD'"
          :on-success="handleSuccess"
          :file-list="urlList"
          :multiple="false"
          list-type="picture"
        >
          <el-button size="small" type="primary">点击上传</el-button>
          <template #tip>
            <div class="el-upload__tip">只能上传jpg/png文件，且不超过50kb</div>
          </template>
        </el-upload>
      </el-form-item>
      <el-form-item label="展示周期" prop="cycleDate">
        <el-date-picker
          v-model="adForm.cycleDate"
          size="small"
          type="daterange"
          value-format="YYYY-MM-DD"
          range-separator="至"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
          @change="handleDateChange"
        ></el-date-picker>
      </el-form-item>
      <el-form-item label="是否循环" prop="loopFlag">
        <el-switch
          v-model="adForm.loopFlag"
          active-text="按年循环"
          inactive-text="不循环"
          active-value="Y"
          inactive-value="N"
        ></el-switch>
      </el-form-item>
      <el-form-item label="序号">
        <el-input
          size="small"
          type="number"
          v-model="adForm.sort"
          autocomplete="off"
          placeholder="越大越靠前（由大到小）0-9999"
        ></el-input>
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-select
          size="small"
          v-model="adForm.status"
          placeholder="请选择状态"
        >
          <el-option label="有效" :value="1"></el-option>
          <el-option label="无效" :value="0"></el-option>
        </el-select>
      </el-form-item>
    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button size="small" @click="dialogFormVisible = false"
          >取 消</el-button
        >
        <el-button size="small" type="primary" @click="dialogFormSubmit"
          >确 定</el-button
        >
      </span>
    </template>
  </el-dialog>
</template>

<script setup>
import { ref, reactive, onMounted, computed } from 'vue'
import { adApi } from '@/api/system/ad'
import { useDictStore } from '@/stores'

const fsURL = import.meta.env.VITE_FILE_BASE_URL

// 数据
const dictStore = useDictStore()
const adList = ref([])
const queryInfo = reactive({
  orderType: ' desc',
  order: ' sort ',
  querytype: '',
  query: '',
  status: '-1',
  pageSize: 20,
  pageNum: 1
})
const pageSizes = [20, 100, 500]
const currentPage = ref(1)
const total = ref(0)
const cycleDate = ref([])
const adForm = ref({
  id: '',
  name: '',
  url: '',
  begDate: '',
  endDate: '',
  loopFlag: 'N',
  sort: 0,
  status: ''
})
const dialogFormVisible = ref(false)
const dialogTitle = ref('')
const urlList = ref([])
const adFormRef = ref(null)

// 表单验证规则
const adRules = reactive({
  name: [
    { required: true, message: '请输入名称', trigger: 'blur' },
    { min: 2, max: 20, message: '长度在2到20个字符', trigger: 'blur' }
  ],
  url: [{ required: true, message: '请上传图片', trigger: 'blur' }],
  // 设置daterange cycleDate 为必填项
  cycleDate: [{ required: true, message: '请选择展示周期', trigger: 'blur' }],
  status: [{ required: true, message: '请选择状态', trigger: 'change' }]
})

// 计算属性
const isEditMode = computed(() => dialogTitle.value.includes('修改'))

// 生命周期
onMounted(() => {
  getAdList()
})

// 方法
const getAdList = async () => {
  try {
    const res = await adApi.listAd(queryInfo)
    if (res.code !== 200) return
    adList.value = res.data.list
    total.value = res.data.total
  } catch (error) {
    ElMessage.error(error.message)
  }
}

const handleEdit = (row) => {
  dialogTitle.value = '修改轮播图'
  dialogFormVisible.value = true
  adForm.value = { ...row }
  adForm.value.cycleDate = [row.begDate, row.endDate]
  // cycleDate.value = [row.begDate, row.endDate]
  urlList.value = row.url ? [{ name: 'image.png', url: fsURL + row.url }] : []
}

const handleDelete = async (row) => {
  try {
    await ElMessageBox.confirm('此操作将永久删除该记录, 是否继续?', '提示', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    })
    const res = await adApi.deleteAd(row.id)
    if (res.code !== 200) throw new Error(res.msg)
    ElMessage.success(res.msg)
    getAdList()
  } catch (error) {
    if (error !== 'cancel') {
      ElMessage.error(error.message || '取消删除')
    }
  }
}

const addAd = () => {
  dialogTitle.value = '新增轮播图'
  dialogFormVisible.value = true
  adForm.value = {
    id: '',
    name: '',
    url: '',
    begDate: '',
    endDate: '',
    loopFlag: 'N',
    sort: 0,
    status: ''
  }
  cycleDate.value = []
  urlList.value = []
}

const handleSizeChange = (pageSize) => {
  queryInfo.pageSize = pageSize
  getAdList()
}

const handleCurrentChange = (page) => {
  queryInfo.pageNum = page
  getAdList()
}

const handleSuccess = (response) => {
  if (response && response.file && response.file.path) {
    adForm.value.url = response.file.path
    ElMessage.success('上传成功')
  } else {
    ElMessage.error('上传失败')
  }
}

const dialogFormSubmit = async () => {
  try {
    await adFormRef.value.validate()

    // 设置日期
    if (cycleDate.value && cycleDate.value.length === 2) {
      adForm.value.begDate = cycleDate.value[0]
      adForm.value.endDate = cycleDate.value[1]
    }

    // 验证排序
    if (
      adForm.value.sort === null ||
      adForm.value.sort === '' ||
      adForm.value.sort > 9999
    ) {
      throw new Error('请正确输入序号（0-9999）')
    }

    let res = null
    if (isEditMode.value) {
      res = await adApi.updateAd(adForm.value)
    } else {
      res = await adApi.addAd(adForm.value)
    }

    if (res.code !== 200) return
    ElMessage.success(res.msg)
    dialogFormVisible.value = false
    getAdList()
  } catch (error) {
    if (error.message) {
      ElMessage.error(error.message)
    }
  }
}

const tableSort = ({ order, prop }) => {
  queryInfo.orderType = order === 'ascending' ? ' asc ' : ' desc '
  queryInfo.order = prop
  getAdList()
}

const handleDateChange = () => {
}
</script>

<style lang="less" scoped>
.box-card {
  height: calc(100vh - 240px);

  .el-image {
    border-radius: 4px;
    border: 1px solid #eee;
  }
}

.upload-demo {
  margin-top: 10px;
}

.dialog-footer {
  text-align: right;
}
</style>

<template>
  <t-alert
    title="操作说明"
    theme="info"
    :closable="false"
    message="请正确使用首页轮播图：1.可设置图片按年度循环播放，但选择日期不要跨年。2.图片数量建议不超过5张。3.图片尺寸建议为1920*500。"
  />
  <t-card class="box-card">
    <t-row :gutter="20">
      <t-col :span="5">
        <t-input
          placeholder="请输入内容"
          size="small"
          v-model="queryInfo.query"
          clearable
          class="input-with-select"
          @clear="getAdList"
        >
          <template #append>
            <t-button
              size="small" @click="getAdList"><template #icon><DynamicIcon name="search" /></template></t-button>
          </template>
        </t-input>
      </t-col>
      <t-col :span="2">
        <t-select
          v-model="queryInfo.status"
          size="small"
          placeholder="全部状态"
          @change="getAdList"
        >
          <t-option label="全部状态" value="-1"></t-option>
          <t-option
            v-for="item in dictStore.dictList.sys_dict_status"
            :key="item.id"
            :label="item.codeval"
            :value="item.code"
          ></t-option>
        </t-select>
      </t-col>
      <t-col :span="5">
        <t-button theme="primary" size="small" @click="addAd"
          >添加图片</t-button
        >
      </t-col>
    </t-row>
    <CustomTable rowKey="id"
      :data="adList"
      size="small"
      height="calc(100vh - 400px)"
      @sort-change="tableSort"
      stripe>
      <TableColumn
        label="ID"
        prop="id"
        sortable="custom"
        width="80"></TableColumn>
      <TableColumn
        label="名称"
        prop="name"
        sortable="custom"></TableColumn>
      <TableColumn
        label="图片"
        prop="url"
        sortable="custom"
        ellipsis>
        <template #default="scope">
          <t-image
            style="width: 100px; height: 60px"
            :src="fsURL + scope.row.url"
            :preview-src-list="[fsURL + scope.row.url]"
            fit="cover"
          ></t-image>
        </template>
      </TableColumn>
      <TableColumn
        label="开始日期"
        prop="begDate"
        sortable="custom"></TableColumn>
      <TableColumn
        label="结束日期"
        prop="endDate"
        sortable="custom"></TableColumn>
      <TableColumn
        label="循环标志"
        prop="loopFlag"
        sortable="custom"
        width="100"></TableColumn>
      <TableColumn
        label="排序"
        prop="sort"
        sortable="custom"
        width="80"></TableColumn>
      <TableColumn label="状态" colKey="status" sortable="custom" width="120">
        <template #default="scope">
          <t-tag
            size="small"
            :theme="scope.row.status === 0 ? 'danger' : 'success'"
            effect="dark"
          >
            {{
              dictStore.dictList.sys_dict_status.find(
                (item) => item.code === scope.row.status.toString()
              )?.codeval
            }}</t-tag
          >
        </template>
      </TableColumn>
      <TableColumn label="操作" fixed="right" width="120">
        <template #default="scope">
          <t-button
            size="small" theme="warning"
            @click="handleEdit(scope.row)"
            shape="circle"><template #icon><DynamicIcon name="edit" /></template></t-button>
          <t-button
            size="small" theme="danger"
            @click="handleDelete(scope.row)"
            shape="circle"><template #icon><DynamicIcon name="delete" /></template></t-button>
        </template>
      </TableColumn>
    </CustomTable>
    <t-pagination
      @page-size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current="currentPage"
      :page-size-options="pageSizes"
      :page-size="queryInfo.pageSize"

      :total="total"
    ></t-pagination>
  </t-card>

  <!-- 弹出窗口 -->
  <t-dialog
    :header="dialogTitle"
    v-model:visible="dialogFormVisible"
    :close-on-overlay-click="false"
    width="50%"
  >
    <t-form
      :data="adForm"
      ref="adFormRef"
      :rules="adRules"
      label-width="120px"
    >
      <t-form-item label="名称" name="name">
        <t-input
          size="small"
          v-model="adForm.name"
          autocomplete="off"
        ></t-input>
      </t-form-item>
      <t-form-item label="图像" name="url">
        <t-input
          size="small"
          v-model="adForm.url"
          autocomplete="off"
        ></t-input>
        <t-upload
          class="upload-demo"
          :action="fsURL + 'upload/file/AD'"
          @success="handleSuccess"
          :file-list="urlList"
          :multiple="false"
          list-type="picture"
        >
          <t-button size="small" theme="primary">点击上传</t-button>
          <template #tip>
            <div>只能上传jpg/png文件，且不超过50kb</div>
          </template>
        </t-upload>
      </t-form-item>
      <t-form-item label="展示周期" name="cycleDate">
        <t-date-range-picker v-model="adForm.cycleDate" size="small" :placeholder="['开始日期', '结束日期']" @change="handleDateChange" ></t-date-range-picker>
      </t-form-item>
      <t-form-item label="是否循环" name="loopFlag">
        <t-switch
          v-model="adForm.loopFlag"
          active-text="按年循环"
          inactive-text="不循环"
          active-value="Y"
          inactive-value="N"
        ></t-switch>
      </t-form-item>
      <t-form-item label="序号">
        <t-input
          size="small"
          type="number"
          v-model="adForm.sort"
          autocomplete="off"
          placeholder="越大越靠前（由大到小）0-9999"
        ></t-input>
      </t-form-item>
      <t-form-item label="状态" name="status">
        <t-select
          size="small"
          v-model="adForm.status"
          placeholder="请选择状态"
        >
          <t-option label="有效" :value="1"></t-option>
          <t-option label="无效" :value="0"></t-option>
        </t-select>
      </t-form-item>
    </t-form>
    <template #footer>
      <span class="dialog-footer">
        <t-button size="small" @click="dialogFormVisible = false"
          >取 消</t-button
        >
        <t-button size="small" theme="primary" @click="dialogFormSubmit"
          >确 定</t-button
        >
      </span>
    </template>
  </t-dialog>
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
    MessagePlugin.error(error.message)
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
    await DialogPlugin.confirm('此操作将永久删除该记录, 是否继续?', '提示', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    })
    const res = await adApi.deleteAd(row.id)
    if (res.code !== 200) throw new Error(res.msg)
    MessagePlugin.success(res.msg)
    getAdList()
  } catch (error) {
    if (error !== 'cancel') {
      MessagePlugin.error(error.message || '取消删除')
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
    MessagePlugin.success('上传成功')
  } else {
    MessagePlugin.error('上传失败')
  }
}

const dialogFormSubmit = async () => {
  const valid = await adFormRef.value.validate()
  if (valid !== true) return

  try {
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
    MessagePlugin.success(res.msg)
    dialogFormVisible.value = false
    getAdList()
  } catch (error) {
    if (error.message) {
      MessagePlugin.error(error.message)
    }
  }
}

const tableSort = ({ sortBy, descending }) => {
  queryInfo.orderType = !descending ? ' asc ' : ' desc '
  queryInfo.order = sortBy
  getAdList()
}

const handleDateChange = () => {
}
</script>

<style lang="less" scoped>
.box-card {
  height: calc(100vh - 240px);

  .t-image {
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

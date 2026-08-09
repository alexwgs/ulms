<template>
  <div style="height: 100%">
    <t-card class="management-card">
      <t-form :data="queryInfo" label-width="80px" colon class="filter-form">
        <t-row :gutter="[24, 24]">
          <t-col :span="5">
            <t-form-item label="关键字" name="query">
              <t-input-adornment>
                <template #append>
                  <t-button variant="outline" theme="primary" @click="getLecturer()">搜索</t-button>
                </template>
                <t-input placeholder="模糊搜索" size="small" v-model="queryInfo.query"></t-input>
              </t-input-adornment>
            </t-form-item>
          </t-col>
          <t-col :span="3">
            <t-form-item label="状态" name="status">
              <t-select
                v-model="queryInfo.status"
                size="small"
                placeholder="全部"
                @change="getLecturer()"
              >
                <t-option label="全部" value=""></t-option>
                <t-option label="有效" :value="1"></t-option>
                <t-option label="无效" :value="0"></t-option>
              </t-select>
            </t-form-item>
          </t-col>
          <t-col :span="3" class="operation-container">
            <t-button variant="outline" theme="primary" size="small" @click="teacherManager(null)">新建讲师</t-button>
          </t-col>
        </t-row>
      </t-form>
      <PageTips
        title="操作说明"
        theme="default"
        message="请正确使用字典配置：1.虚拟账号请工号输入XN开头。2.非虚拟账号请使用选择控件选人，勿手工修改姓名及工号。3.若不对外展示的讲师或离职人员请将状态设置为失效。"
        :closable="false"
      >
      </PageTips>
      <CustomTable rowKey="id"
        :data="teachers"
        size="small"
        height="calc(100vh - 400px)"
        stripe
        @sort-change="tableSort"
        style="width: 100%">
        <TableColumn
          prop="ploName"
          label="讲师姓名"
          width="100px"
          ellipsis></TableColumn>
        <TableColumn
          prop="ploNum"
          label="讲师工号"
          sortable="custom"
          ellipsis></TableColumn>
        <TableColumn
          prop="dataDate"
          label="身份时间"
          sortable="custom"
          ellipsis></TableColumn>
        <TableColumn
          prop="skillType"
          label="身份类型"
          sortable="custom"
          width="100px">
          <template #default="scope">{{
            skillType[scope.row.skillType].label
          }}</template>
        </TableColumn>
        <TableColumn
          prop="skillName"
          label="身份名称"
          sortable="custom"
          width="100px">
          <template #default="scope">{{
            skillName[scope.row.skillName].label
          }}</template>
        </TableColumn>
        <TableColumn
          prop="courseNum"
          label="授课数"
          sortable="custom"
          width="90px"></TableColumn>
        <TableColumn
          prop="courseHour"
          label="授课时数"
          sortable="custom"
          width="100px"></TableColumn>
        <TableColumn
          prop="score"
          label="评分"
          sortable="custom"
          width="90px"></TableColumn>
        <TableColumn
          prop="status"
          label="状态"
          sortable="custom"
          width="90px">
          <template #default="scope"
            ><t-tag
              @click="updateStatus(scope.row)"
              :type="scope.row.status ? 'success' : 'danger'"
              size="small"
               variant="light">{{ scope.row.status ? '有效' : '无效' }}[{{
                scope.row.status
              }}]</t-tag
            ></template
          >
        </TableColumn>
        <TableColumn label="操作" fixed="right" width="90px">
          <template #default="scope">
            <t-button
              theme="default" size="small"
              @click="teacherManager(scope.row)"
             >编辑</t-button>
          </template>
        </TableColumn>
      </CustomTable>
      <t-pagination
        @page-size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current="queryInfo.pageNum"
        :page-size-options="pageSizes"
        :page-size="queryInfo.pageSize"

        :total="total"
      ></t-pagination>
    </t-card>

    <!-- 弹出窗口 -->
    <t-dialog
      :header="teacherFormTitle"
      :close-on-overlay-click="false"
      width="80%"
      @close="onClose"
      v-model:visible="teacherFormVisible"
    >
      <t-form
        ref="teacherForm"
        :data="teacherFormData"
        :rules="rules"
        size="small"
        label-width="auto"
        style="max-width: 600px"
      >
        <t-form-item label="工号" name="ploNum">
          <t-input-adornment>
            <template #append>
              <t-button variant="outline" theme="primary" @click="treeDialogVisiable = !treeDialogVisiable">选择</t-button>
            </template>
            <t-input
              v-model="teacherFormData.ploNum"
              placeholder="请选择讲师信息"
              clearable
            ></t-input>
          </t-input-adornment>
        </t-form-item>

        <t-form-item label="姓名" name="ploName">
          <t-input
            v-model="teacherFormData.ploName"
            placeholder="请输入讲师姓名"
            clearable
            :style="{ width: '100%' }"
          ></t-input>
        </t-form-item>

        <t-form-item label="讲师类型" name="skillType">
          <t-select
            v-model="teacherFormData.skillType"
            placeholder="请选择讲师类型"
            clearable
            :style="{ width: '100%' }"
          >
            <t-option
              v-for="item in skillType"
              :key="item.value"
              :value="item.value"
              :label="item.label"
              :disabled="!item.status"
            ></t-option>
          </t-select>
        </t-form-item>

        <t-form-item label="分类名称" name="skillName">
          <t-select
            v-model="teacherFormData.skillName"
            placeholder="请选择分类名称"
            clearable
            :style="{ width: '100%' }"
          >
            <t-option
              v-for="item in skillName"
              :key="item.value"
              :value="item.value"
              :label="item.label"
              :disabled="!item.status"
            ></t-option>
          </t-select>
        </t-form-item>

        <t-form-item label="身份时间" name="dataDate">
          <t-date-picker
            v-model="teacherFormData.dataDate"
            mode="date"
           
            placeholder="身份获取时间"
            clearable
            :style="{ width: '100%' }"
          ></t-date-picker>
        </t-form-item>

        <t-form-item label="讲师荣誉" name="honor">
          <t-input
            v-model="teacherFormData.honor"
            placeholder="输入荣誉名称，多荣誉、分开"
            clearable
            :style="{ width: '100%' }"
          ></t-input>
        </t-form-item>

        <t-form-item label="手动排序" name="status">
          <t-input
            v-model="teacherFormData.status"
            placeholder="0为失效，越大越靠前"
            clearable
            :style="{ width: '100%' }"
            type="number"
            maxlength="2"
          ></t-input>
        </t-form-item>

        <t-form-item label="讲师简介" name="introduce">
          <t-textarea v-model="teacherFormData.introduce"
            
            placeholder="请输入讲师简介"
            :maxlength="2000"
            show-limit-number
            :autosize="{ minRows: 4, maxRows: 4 }"
            :style="{ width: '100%' }" />
        </t-form-item>

        <t-form-item label="上传" name="avatar">
          <t-upload
            ref="avatar"
            :action="fsURL + 'upload/file/college-avatar'"
            :file-list="fileList"
            @success="handleSuccess"
            :multiple="false"
            :limit="1"
            list-type="picture-card"
            accept="image/*"
          >
            <AddIcon />
          </t-upload>
        </t-form-item>
      </t-form>
      <template #footer>
        <t-space>
          <t-button size="small" variant="outline" @click="teacherFormVisible = false">取消</t-button>
          <t-button size="small" variant="outline" theme="primary" @click="submitForm()">确定</t-button>
        </t-space>
      </template>
    </t-dialog>
    <t-dialog header="选择员工" v-model:visible="treeDialogVisiable" width="500px">
      <EmployeeSelect
        conditionType="search"
        v-model="teacherFormData.ploNum"
        :allowClear="false"
        @getUser="getUserInfo"
      ></EmployeeSelect>
      <template #footer>
        <t-space>
          <t-button size="small" variant="outline" theme="primary" @click="treeDialogVisiable = false">确定</t-button>
        </t-space>
      </template>
    </t-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { AddIcon } from 'tdesign-icons-vue-next'
import { MessagePlugin } from 'tdesign-vue-next'
import EmployeeSelect from '../../../../components/EmployeeSelect.vue'
import { teacherApi } from '@/api/college/teacher'

const fsURL = import.meta.env.VITE_FILE_BASE_URL
// 展示类图片统一走 HTTPS 文件管理地址，避免混合内容被浏览器拦截
const displayURL = import.meta.env.VITE_FILE_BASE_URL || fsURL

const queryInfo = reactive({
  orderType: ' desc',
  order: 'dataDate',
  queryType: '',
  status: '',
  query: '',
  pageSize: 20,
  pageNum: 1
})

const teachers = ref([])
const total = ref(0)
const treeDialogVisiable = ref(false)
const teacherFormTitle = ref('新增讲师')
const teacherFormVisible = ref(false)
const teacherForm = ref(null)
const avatar = ref(null)

const teacherFormData = reactive({
  ploNum: undefined,
  ploName: undefined,
  skillType: undefined,
  skillName: undefined,
  dataDate: undefined,
  introduce: undefined,
  avatar: null,
  honor: undefined
})

const rules = reactive({
  ploNum: [{ required: true, message: '请选择讲师信息', trigger: 'blur' }],
  ploName: [{ required: true, message: '讲师姓名不可为空', trigger: 'blur' }],
  skillType: [{ required: true, message: '请选择讲师类型', trigger: 'change' }],
  skillName: [{ required: true, message: '请选择分类名称', trigger: 'change' }],
  introduce: [{ required: true, message: '请输入讲师简介', trigger: 'blur' }],
  status: [
    { required: true, message: '状态0为失效,数字越大越靠前', trigger: 'blur' }
  ]
})

const fileList = ref([])

const skillType = reactive({
  0: { value: 0, label: '无身份', status: true },
  1: { value: 1, label: '特约', status: true },
  2: { value: 2, label: '预留', status: false },
  3: { value: 3, label: '初级', status: true },
  4: { value: 4, label: '中级', status: true },
  5: { value: 5, label: '高级', status: true }
})

const skillName = reactive({
  1: { value: 1, label: '讲师', status: true },
  2: { value: 2, label: '辅导老师', status: false }
})

const pageSizes = [10, 20, 50, 100]

const getLecturer = async () => {
  try {
    const res = await teacherApi.getTeacherList(queryInfo)
    if (res.code !== 200) return MessagePlugin.error(res.msg)
    teachers.value = res.data.list
    total.value = res.data.total
  } catch (error) {
    MessagePlugin.error('获取讲师列表失败')
  }
}

const handleSizeChange = (pageSize) => {
  queryInfo.pageSize = pageSize
  getLecturer()
}

const handleCurrentChange = (page) => {
  queryInfo.pageNum = page
  getLecturer()
}

const tableSort = (data) => {
  if (!data.descending) queryInfo.orderType = ' asc '
  else if (data.descending) queryInfo.orderType = ' desc '
  queryInfo.order = data.sortBy
  getLecturer()
}

const onClose = () => {
  if (teacherForm.value) {
    teacherForm.value.resetFields()
  }
}

const teacherManager = (lecturer) => {
  if (lecturer) {
    teacherFormTitle.value = '修改讲师'
    Object.assign(teacherFormData, JSON.parse(JSON.stringify(lecturer)))
    if (lecturer.avatar !== null && lecturer.avatar !== '') {
      fileList.value = [
        {
          name: lecturer.avatar,
          url: displayURL + '/upload/getFile/college-avatar/' + lecturer.avatar
        }
      ]
    }
  } else {
    teacherFormTitle.value = '新增讲师'
    Object.assign(teacherFormData, {
      ploNum: undefined,
      ploName: undefined,
      skillType: undefined,
      skillName: undefined,
      dataDate: undefined,
      introduce: undefined,
      avatar: null,
      honor: undefined
    })
    fileList.value = []
  }
  teacherFormVisible.value = true
}

const submitForm = async () => {
  if (!teacherForm.value) return

  const valid = await teacherForm.value.validate()
  if (valid !== true) return

  try {
    let res
    if (teacherFormTitle.value === '新增讲师') {
      res = await teacherApi.addTeacher(teacherFormData)
    } else {
      res = await teacherApi.updateTeacher(teacherFormData)
    }
    if (res.code !== 200) return MessagePlugin.error(res.msg)
    MessagePlugin.success(res.msg)
    getLecturer()
    teacherFormVisible.value = false
  } catch (error) {
    MessagePlugin.error('保存讲师失败')
  }
}

const getUserInfo = (user) => {
  if (user) {
    teacherFormData.ploName = user.ploName
  }
}

const handleSuccess = (response, file, fileList) => {
  if (response.code !== 200) {
    return MessagePlugin.error(response.msg)
  }
  const fileName = response.file.fileId + '.' + response.file.fileSuffix
  teacherFormData.avatar = fileName
}

const updateStatus = async (row) => {
  try {
    row.status = row.status === 0 ? 1 : 0
    const res = await teacherApi.updateTeacher(row)
    if (res.code !== 200) return MessagePlugin.error(res.msg)
    MessagePlugin.success(res.msg)
    getLecturer()
  } catch (error) {
    MessagePlugin.error('更新讲师状态失败')
  }
}

onMounted(() => {
  getLecturer()
})
</script>

<style lang="less" scoped></style>

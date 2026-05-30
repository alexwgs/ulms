<template>
  <div style="height: 100%">
    <el-card class="box-card">
      <el-row :gutter="20">
        <el-col :span="8">
          <el-input
            placeholder="模糊搜索"
            size="small"
            v-model="queryInfo.query"
          >
            <el-button
              slot="append"
              icon="Search"
              @click="getLecturer()"
            ></el-button>
          </el-input>
        </el-col>
        <el-col :span="6">
          <el-select
            v-model="queryInfo.status"
            size="small"
            placeholder="请选择发布状态"
            @change="getLecturer()"
          >
            <el-option label="全部" value=""></el-option>
            <el-option label="有效" :value="1"></el-option>
            <el-option label="无效" :value="0"></el-option>
          </el-select>
        </el-col>
        <el-col :span="8">
          <el-button type="primary" size="small" @click="teacherManager(null)"
            >新建讲师</el-button
          >
        </el-col>
      </el-row>
      <el-alert
        title="操作说明"
        type="info"
        description="请正确使用字典配置：1.虚拟账号请工号输入XN开头。2.非虚拟账号请使用选择控件选人，勿手工修改姓名及工号。3.若不对外展示的讲师或离职人员请将状态设置为失效。"
        :closable="false"
      >
      </el-alert>
      <el-table
        :data="teachers"
        size="small"
        height="calc(100vh - 400px)"
        stripe
        @sort-change="tableSort"
        style="width: 100%"
      >
        <el-table-column
          prop="ploName"
          label="讲师姓名"
          width="100px"
          show-overflow-tooltip
        ></el-table-column>
        <el-table-column
          prop="ploNum"
          label="讲师工号"
          sortable="custom"
          show-overflow-tooltip
        ></el-table-column>
        <el-table-column
          prop="dataDate"
          label="身份时间"
          sortable="custom"
          show-overflow-tooltip
        ></el-table-column>
        <el-table-column
          prop="skillType"
          label="身份类型"
          sortable="custom"
          width="100px"
        >
          <template #default="scope">{{
            skillType[scope.row.skillType].label
          }}</template>
        </el-table-column>
        <el-table-column
          prop="skillName"
          label="身份名称"
          sortable="custom"
          width="100px"
        >
          <template #default="scope">{{
            skillName[scope.row.skillName].label
          }}</template>
        </el-table-column>
        <el-table-column
          prop="courseNum"
          label="授课数"
          sortable="custom"
          width="90px"
        ></el-table-column>
        <el-table-column
          prop="courseHour"
          label="授课时数"
          sortable="custom"
          width="100px"
        ></el-table-column>
        <el-table-column
          prop="score"
          label="评分"
          sortable="custom"
          width="90px"
        ></el-table-column>
        <el-table-column
          prop="status"
          label="状态"
          sortable="custom"
          width="90px"
        >
          <template #default="scope"
            ><el-tag
              @click="updateStatus(scope.row)"
              :type="scope.row.status ? 'success' : 'danger'"
              size="small"
              >{{ scope.row.status ? '有效' : '无效' }}[{{
                scope.row.status
              }}]</el-tag
            ></template
          >
        </el-table-column>
        <el-table-column label="操作" fixed="right" width="90px">
          <template #default="scope">
            <el-button
              type="primary"
              icon="Edit"
              size="small"
              @click="teacherManager(scope.row)"
              circle
            ></el-button>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="queryInfo.pageNum"
        :page-sizes="pageSizes"
        :page-size="queryInfo.pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total"
      ></el-pagination>
    </el-card>

    <!-- 弹出窗口 -->
    <el-dialog
      :title="teacherFormTitle"
      :close-on-click-modal="false"
      width="80%"
      @close="onClose"
      v-model="teacherFormVisible"
    >
      <el-form
        ref="teacherForm"
        :model="teacherFormData"
        :rules="rules"
        size="small"
        label-width="auto"
        style="max-width: 600px"
      >
        <el-form-item label="工号" prop="ploNum">
          <el-input
            v-model="teacherFormData.ploNum"
            placeholder="请选择讲师信息"
            clearable
            :style="{ width: '100%' }"
          >
            <el-button
              slot="append"
              @click="treeDialogVisiable = !treeDialogVisiable"
              icon="Search"
            ></el-button>
          </el-input>
        </el-form-item>

        <el-form-item label="姓名" prop="ploName">
          <el-input
            v-model="teacherFormData.ploName"
            placeholder="请输入讲师姓名"
            clearable
            :style="{ width: '100%' }"
          ></el-input>
        </el-form-item>

        <el-form-item label="讲师类型" prop="skillType">
          <el-select
            v-model="teacherFormData.skillType"
            placeholder="请选择讲师类型"
            clearable
            :style="{ width: '100%' }"
          >
            <el-option
              v-for="item in skillType"
              :key="item.value"
              :value="item.value"
              :label="item.label"
              :disabled="!item.status"
            ></el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="分类名称" prop="skillName">
          <el-select
            v-model="teacherFormData.skillName"
            placeholder="请选择分类名称"
            clearable
            :style="{ width: '100%' }"
          >
            <el-option
              v-for="item in skillName"
              :key="item.value"
              :value="item.value"
              :label="item.label"
              :disabled="!item.status"
            ></el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="身份时间" prop="dataDate">
          <el-date-picker
            v-model="teacherFormData.dataDate"
            type="date"
            value-format="YYYY-MM-DD"
            placeholder="身份获取时间"
            clearable
            :style="{ width: '100%' }"
          ></el-date-picker>
        </el-form-item>

        <el-form-item label="讲师荣誉" prop="honor">
          <el-input
            v-model="teacherFormData.honor"
            placeholder="输入荣誉名称，多荣誉、分开"
            clearable
            :style="{ width: '100%' }"
          ></el-input>
        </el-form-item>

        <el-form-item label="手动排序" prop="status">
          <el-input
            v-model="teacherFormData.status"
            placeholder="0为失效，越大越靠前"
            clearable
            :style="{ width: '100%' }"
            type="number"
            maxlength="2"
          ></el-input>
        </el-form-item>

        <el-form-item label="讲师简介" prop="introduce">
          <el-input
            v-model="teacherFormData.introduce"
            type="textarea"
            placeholder="请输入讲师简介"
            :maxlength="2000"
            show-word-limit
            :autosize="{ minRows: 4, maxRows: 4 }"
            :style="{ width: '100%' }"
          ></el-input>
        </el-form-item>

        <el-form-item label="上传" prop="avatar">
          <el-upload
            ref="avatar"
            :action="fsURL + 'upload/file/college-avatar'"
            :file-list="fileList"
            :on-success="handleSuccess"
            :multiple="false"
            :limit="1"
            list-type="picture-card"
            accept="image/*"
          >
            <el-icon><Plus /></el-icon>
          </el-upload>
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button size="small" @click="teacherFormVisible = false"
            >取消</el-button
          >
          <el-button size="small" type="primary" @click="submitForm()"
            >确定</el-button
          >
        </div>
      </template>
    </el-dialog>
    <el-dialog title="选择员工" v-model="treeDialogVisiable" width="500px">
      <EmployeeSelect
        conditionType="search"
        v-model="teacherFormData.ploNum"
        :allowClear="false"
        @getUser="getUserInfo"
      ></EmployeeSelect>
      <template #footer>
        <div class="dialog-footer">
          <el-button size="small" @click="treeDialogVisiable = false"
            >确 定</el-button
          >
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import EmployeeSelect from '../../../../components/EmployeeSelect.vue'
import { teacherApi } from '@/api/college/teacher'

const fsURL = import.meta.env.VITE_FILE_BASE_URL

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
    if (res.code !== 200) return ElMessage.error(res.msg)
    teachers.value = res.data.list
    total.value = res.data.total
  } catch (error) {
    ElMessage.error('获取讲师列表失败')
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
  if (data.order === 'ascending') queryInfo.orderType = ' asc '
  else if (data.order === 'descending') queryInfo.orderType = ' desc '
  queryInfo.order = data.prop
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
          url: fsURL + '/upload/getFile/college-avatar/' + lecturer.avatar
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

const submitForm = () => {
  if (!teacherForm.value) return

  teacherForm.value.validate(async (valid) => {
    if (!valid) return
    else {
      try {
        let res
        if (teacherFormTitle.value === '新增讲师') {
          res = await teacherApi.addTeacher(teacherFormData)
        } else {
          res = await teacherApi.updateTeacher(teacherFormData)
        }
        if (res.code !== 200) return ElMessage.error(res.msg)
        ElMessage.success(res.msg)
        getLecturer()
        teacherFormVisible.value = false
      } catch (error) {
        ElMessage.error('保存讲师失败')
      }
    }
  })
}

const getUserInfo = (user) => {
  if (user) {
    teacherFormData.ploName = user.ploName
  }
}

const handleSuccess = (response, file, fileList) => {
  if (response.code !== 200) {
    return ElMessage.error(response.msg)
  }
  const fileName = response.file.fileId + '.' + response.file.fileSuffix
  teacherFormData.avatar = fileName
}

const updateStatus = async (row) => {
  try {
    row.status = row.status === 0 ? 1 : 0
    const res = await teacherApi.updateTeacher(row)
    if (res.code !== 200) return ElMessage.error(res.msg)
    ElMessage.success(res.msg)
    getLecturer()
  } catch (error) {
    ElMessage.error('更新讲师状态失败')
  }
}

onMounted(() => {
  getLecturer()
})
</script>

<style lang="less" scoped></style>

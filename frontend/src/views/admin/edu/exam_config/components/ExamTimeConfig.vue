<template>
  <div>
    <el-dialog
      title="考试预约配置"
      v-model="dialogFormVisible"
      width="60%"
      :close-on-click-modal="false"
    >
      <el-form
        :model="form"
        :inline="true"
        ref="formRef"
        size="small"
        :rules="rules"
        class="inline-form"
      >
        <el-form-item label="预约日期" prop="bookDate">
          <el-date-picker
            v-model="form.bookDate"
            type="date"
            value-format="YYYY-MM-DD"
            placeholder="选择日期"
          />
        </el-form-item>
        <el-form-item label="预约类型" prop="bookType">
          <el-select v-model="form.bookType" placeholder="请选择预约类型">
            <el-option label="常规" :value="0" />
            <el-option label="补考" :value="1" />
          </el-select>
        </el-form-item>
        <el-form-item label="时间规则" prop="bookCode">
          <el-select v-model="form.bookCode" placeholder="请选择时间配置">
            <el-option
              v-for="item in timeConfig"
              :key="item.bookCode"
              :label="item.bookName + '[' + item.bookDesc + ']'"
              :value="item.bookCode"
            />
          </el-select>
        </el-form-item>
        <el-button type="primary" size="small" @click="addBookInfo"
          >新增</el-button
        >
      </el-form>
      <el-table :data="data" size="small" height="300px">
        <el-table-column property="bookDate" label="预约日期" width="150" />
        <el-table-column property="bookCode" label="规则名称">
          <template #default="scope">
            {{ returnBookName(scope.row.bookCode) }}
          </template>
        </el-table-column>
        <el-table-column property="bookType" label="预约类型" width="80">
          <template #default="scope">
            <el-tag
              size="small"
              :type="scope.row.bookType == 0 ? 'success' : 'danger'"
              effect="plain"
            >
              {{ scope.row.bookType == 0 ? '常规' : '补考' }}</el-tag
            >
          </template>
        </el-table-column>
        <el-table-column label="操作" width="80">
          <template #default="scope">
            <el-button
              type="danger"
              icon="delete"
              size="small"
              @click="deleteBookInfo(scope.row.infoCode)"
              circle
            />
          </template>
        </el-table-column>
      </el-table>
      <template #footer>
        <span class="dialog-footer">
          <el-button size="small" @click="dialogFormVisible = false"
            >取 消</el-button
          >
          <el-button
            size="small"
            type="primary"
            @click="dialogFormVisible = false"
            >确 定</el-button
          >
        </span>
      </template>
    </el-dialog>
  </div>
</template>
<script setup>
import { ref, reactive } from 'vue'
import { ElMessage } from 'element-plus'
import { bookInfoApi } from '@/api/edu/bookInfo'
import { useDictStore } from '@/stores'

const dictStore = useDictStore()
const dialogFormVisible = ref(false)
const formRef = ref(null)
const timeConfig = ref([])
const data = ref([])

const form = reactive({ bookDate: '', bookType: 0, bookCode: '', examCode: '' })

const rules = reactive({
  bookDate: [{ required: true, message: '选择可预约的日期', trigger: 'blur' }],
  bookType: [
    { required: true, message: '选择考试预约的类型', trigger: 'blur' }
  ],
  bookCode: [{ required: true, message: '选择考试的时段', trigger: 'blur' }]
})

const show = (examCode) => {
  form.examCode = examCode
  getTimeConfig()
  dialogFormVisible.value = true
  listBookInfo()
}

const getTimeConfig = async () => {
  try {
    const res = await bookInfoApi.getBookConfigList({ status: 1 })
    if (res.code !== 200) return ElMessage.error(res.msg)
    timeConfig.value = res.data
  } catch (error) {
    ElMessage.error('获取时间配置失败')
  }
}

const listBookInfo = async () => {
  try {
    const res = await bookInfoApi.getExamBookTimeInfo(form.examCode)
    if (res.code !== 200) return ElMessage.error(res.msg)
    data.value = res.data
  } catch (error) {
    ElMessage.error('获取预约信息失败')
  }
}

const deleteBookInfo = async (infoCode) => {
  try {
    const res = await bookInfoApi.deleteExamBookInfo(infoCode)
    if (res.code !== 200) return ElMessage.error(res.msg)
    ElMessage.success(res.msg)
    listBookInfo()
  } catch (error) {
    ElMessage.error('删除预约信息失败')
  }
}

const addBookInfo = async () => {
  if (!formRef.value) return

  const valid = await formRef.value.validate()
  if (valid) {
    try {
      const res = await bookInfoApi.addNewExamBookTimeInfo(form)
      if (res.code !== 200) return ElMessage.error(res.msg)
      ElMessage.success(res.msg)
      listBookInfo()
    } catch (error) {
      ElMessage.error('添加预约信息失败')
    }
  }
}

const returnBookName = (bookCode) => {
  const list = timeConfig.value.filter((e) => e.bookCode === bookCode)
  if (list.length < 1) return bookCode
  return list[0].bookName
}

defineExpose({
  show
})
</script>
<style lang="less" scoped>
.inline-form .el-input {
  --el-input-width: 180px;
}
.inline-form .el-select {
  --el-select-width: 180px;
}
</style>

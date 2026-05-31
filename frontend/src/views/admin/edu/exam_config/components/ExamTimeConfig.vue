<template>
  <div>
    <t-dialog
      header="考试预约配置"
      v-model:visible="dialogFormVisible"
      width="60%"
      :close-on-overlay-click="false"
    >
      <t-form
        :data="form"
        layout="inline"
        ref="formRef"
        size="small"
        :rules="rules"
        class="inline-form"
      >
        <t-form-item label="预约日期" name="bookDate">
          <t-date-picker
            v-model="form.bookDate"
            mode="date"
           
            placeholder="选择日期"
          />
        </t-form-item>
        <t-form-item label="预约类型" name="bookType">
          <t-select v-model="form.bookType" placeholder="请选择预约类型">
            <t-option label="常规" :value="0" />
            <t-option label="补考" :value="1" />
          </t-select>
        </t-form-item>
        <t-form-item label="时间规则" name="bookCode">
          <t-select v-model="form.bookCode" placeholder="请选择时间配置">
            <t-option
              v-for="item in timeConfig"
              :key="item.bookCode"
              :label="item.bookName + '[' + item.bookDesc + ']'"
              :value="item.bookCode"
            />
          </t-select>
        </t-form-item>
        <t-button theme="primary" size="small" @click="addBookInfo"
          >新增</t-button
        >
      </t-form>
      <CustomTable rowKey="id" :data="data" size="small" height="300px">
        <TableColumn property="bookDate" label="预约日期" width="150" />
        <TableColumn property="bookCode" label="规则名称">
          <template #default="scope">
            {{ returnBookName(scope.row.bookCode) }}
          </template>
        </TableColumn>
        <TableColumn property="bookType" label="预约类型" width="80">
          <template #default="scope">
            <t-tag
              size="small"
              :theme="scope.row.bookType == 0 ? 'success' : 'danger'"
              effect="plain"
            >
              {{ scope.row.bookType == 0 ? '常规' : '补考' }}</t-tag
            >
          </template>
        </TableColumn>
        <TableColumn label="操作" width="80">
          <template #default="scope">
            <t-button
              theme="danger" size="small"
              @click="deleteBookInfo(scope.row.infoCode)"
              circle
            ><template #icon><DynamicIcon name="delete" /></template></t-button>
          </template>
        </TableColumn>
      </CustomTable>
      <template #footer>
        <span class="dialog-footer">
          <t-button size="small" @click="dialogFormVisible = false"
            >取 消</t-button
          >
          <t-button
            size="small"
            theme="primary"
            @click="dialogFormVisible = false"
            >确 定</t-button
          >
        </span>
      </template>
    </t-dialog>
  </div>
</template>
<script setup>
import { ref, reactive } from 'vue'
import { MessagePlugin } from 'tdesign-vue-next'
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
    if (res.code !== 200) return MessagePlugin.error(res.msg)
    timeConfig.value = res.data
  } catch (error) {
    MessagePlugin.error('获取时间配置失败')
  }
}

const listBookInfo = async () => {
  try {
    const res = await bookInfoApi.getExamBookTimeInfo(form.examCode)
    if (res.code !== 200) return MessagePlugin.error(res.msg)
    data.value = res.data
  } catch (error) {
    MessagePlugin.error('获取预约信息失败')
  }
}

const deleteBookInfo = async (infoCode) => {
  try {
    const res = await bookInfoApi.deleteExamBookInfo(infoCode)
    if (res.code !== 200) return MessagePlugin.error(res.msg)
    MessagePlugin.success(res.msg)
    listBookInfo()
  } catch (error) {
    MessagePlugin.error('删除预约信息失败')
  }
}

const addBookInfo = async () => {
  if (!formRef.value) return

  const valid = await formRef.value.validate()
  if (valid === true) {
    try {
      const res = await bookInfoApi.addNewExamBookTimeInfo(form)
      if (res.code !== 200) return MessagePlugin.error(res.msg)
      MessagePlugin.success(res.msg)
      listBookInfo()
    } catch (error) {
      MessagePlugin.error('添加预约信息失败')
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
.inline-form .t-input {
  --td-input-width: 180px;
}
.inline-form .t-select {
  --td-select-width: 180px;
}
</style>

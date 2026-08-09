<template>
  <t-dialog
    :header="dialogTitle"
    v-model:visible="dialogVisible"
    width="40%"
    :close-on-overlay-click="false"
  >
    <div>
      <!-- 考试时间维护对话框 -->
      <t-dialog
        width="30%"
        header="考试时间维护"
        v-model:visible="timeSetDialog"
        attach="body"
      >
        <t-form 
          ref="timeRef" 
          :data="timeListForm" 
          label-align="right" 
          label-width="100px" 
          size="small" 
          :rules="timeRules"
        >
          <t-form-item label="开始时间" name="begTime">
            <t-input v-model="timeListForm.begTime"></t-input>
          </t-form-item>
          <t-form-item label="结束时间" name="endTime">
            <t-input v-model="timeListForm.endTime"></t-input>
          </t-form-item>
          <t-form-item label="座位数量" name="bookLimit">
            <t-input v-model="timeListForm.bookLimit"></t-input>
          </t-form-item>
          <t-form-item label="状态" name="bookStat">
            <t-select v-model="timeListForm.bookStat" placeholder="请选择考试时间状态">
              <t-option label="生效" :value="1"></t-option>
              <t-option label="失效" :value="0"></t-option>
            </t-select>
          </t-form-item>
          <t-button size="small" @click="timeSetDialog = false">取 消</t-button>
          <t-button size="small" theme="primary" @click="submitTimeSet">确 定</t-button>
        </t-form>
      </t-dialog>

      <t-button theme="primary" size="small" @click="addNewTimeSet">新增时间</t-button>
      <CustomTable rowKey="id" :data="timeList" size="small" stripe height="400px">
        <TableColumn colKey="begTime" label="开始时间" width="120"></TableColumn>
        <TableColumn colKey="endTime" label="结束时间" width="90"></TableColumn>
        <TableColumn colKey="bookLimit" label="座位数量"></TableColumn>
        <TableColumn label="状态" width="80">
          <template #default="scope">
            <t-tag 
              size="small" 
              :theme="scope.row.bookStat ? 'success' : 'danger'" 
              variant="light"
            >
              {{ scope.row.bookStat ? '生效' : '失效' }}
            </t-tag>
          </template>
        </TableColumn>
        <TableColumn label="操作" width="110">
          <template #default="scope">
            <t-button variant="outline" 
              theme="default" size="small" 
              @click="editTimeListBtn(scope.row)">编辑</t-button>
            <t-button variant="outline" 
              theme="danger" size="small" 
              @click="deleteTimeList(scope.row.journo)">删除</t-button>
          </template>
        </TableColumn>
      </CustomTable>
    </div>
    <template #footer>
      <span class="dialog-footer">
        <t-button @click="dialogVisible = false">取 消</t-button>
      </span>
    </template>
  </t-dialog>
</template>

<script setup>
import { ref, reactive, computed, onMounted, watch } from 'vue'
import { MessagePlugin } from 'tdesign-vue-next'
import { bookInfoApi } from '@/api/edu/bookInfo'

// Props
const props = defineProps({
  visible: {
    type: Boolean,
    default: false
  },
  dialogTitle: {
    type: String,
    default: ''
  },
  bookCode: {
    type: String,
    default: ''
  }
})

// Emits
const emit = defineEmits(['update:visible'])

// Computed
const dialogVisible = computed({
  get: () => props.visible,
  set: (val) => emit('update:visible', val)
})

// Reactive data
const timeSetDialog = ref(false)
const timeList = ref([])
const timeListForm = reactive({ 
  journo: '', 
  bookCode: props.bookCode, 
  begTime: '', 
  endTime: '', 
  bookLimit: 0, 
  bookStat: 1 
})

const timeRules = reactive({
  begTime: [{ required: true, message: '请输入开始考试时间', trigger: 'blur' }],
  endTime: [{ required: true, message: '请输入结束考试时间', trigger: 'blur' }],
  bookLimit: [{ required: true, message: '最多同时考试人数', trigger: 'blur' }],
  bookStat: [{ required: true, message: '选择状态', trigger: 'blur' }]
})

const timeRef = ref(null)

// Watch
watch(() => props.bookCode, (newBookCode) => {
  timeListForm.bookCode = newBookCode
  if (props.visible) {
    getTimeList()
  }
}, { immediate: true })

// Lifecycle
onMounted(() => {
  if (props.visible) {
    getTimeList()
  }
})

// Methods
const getTimeList = async () => {
  const res = await bookInfoApi.getBookTimeList({ bookCode: props.bookCode })
  if (res.code !== 200) return MessagePlugin.error(res.msg)
  timeList.value = res.data
}

const addNewTimeSet = () => {
  timeListForm.journo = ''
  timeListForm.begTime = ''
  timeListForm.endTime = ''
  timeListForm.bookLimit = 0
  timeListForm.bookStat = 1
  timeSetDialog.value = true
}

const editTimeListBtn = (row) => {
  Object.assign(timeListForm, row)
  timeSetDialog.value = true
}

const submitTimeSet = async () => {
  const valid = await timeRef.value.validate()
  if (valid !== true) return

  try {
    let res
    if (timeListForm.journo) {
      res = await bookInfoApi.updateBookTime(timeListForm)
    } else {
      res = await bookInfoApi.addBookTime(timeListForm)
    }

    if (res.code !== 200) return MessagePlugin.error(res.msg)
    MessagePlugin.success(res.msg)
    getTimeList()
    timeSetDialog.value = false
  } catch (error) {
    MessagePlugin.error('操作失败，请重试')
  }
}

const deleteTimeList = async (journo) => {
  const res = await bookInfoApi.deleteBookTime(journo)
  if (res.code !== 200) return MessagePlugin.error(res.msg)
  MessagePlugin.success(res.msg)
  getTimeList()
  timeSetDialog.value = false
}
</script>

<style scoped>

</style>

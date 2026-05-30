<template>
  <el-dialog 
    :title="dialogTitle" 
    v-model="dialogVisible" 
    width="40%" 
    :close-on-click-modal="false"
  >
    <div>
      <!-- 考试时间维护对话框 -->
      <el-dialog 
        width="30%" 
        title="考试时间维护" 
        v-model="timeSetDialog" 
        append-to-body
      >
        <el-form 
          ref="timeRef" 
          :model="timeListForm" 
          label-position="right" 
          label-width="100px" 
          size="small" 
          :rules="timeRules"
        >
          <el-form-item label="开始时间" prop="begTime">
            <el-input v-model="timeListForm.begTime"></el-input>
          </el-form-item>
          <el-form-item label="结束时间" prop="endTime">
            <el-input v-model="timeListForm.endTime"></el-input>
          </el-form-item>
          <el-form-item label="座位数量" prop="bookLimit">
            <el-input v-model="timeListForm.bookLimit"></el-input>
          </el-form-item>
          <el-form-item label="状态" prop="bookStat">
            <el-select v-model="timeListForm.bookStat" placeholder="请选择考试时间状态">
              <el-option label="生效" :value="1"></el-option>
              <el-option label="失效" :value="0"></el-option>
            </el-select>
          </el-form-item>
          <el-button size="small" @click="timeSetDialog = false">取 消</el-button>
          <el-button size="small" type="primary" @click="submitTimeSet">确 定</el-button>
        </el-form>
      </el-dialog>

      <el-button type="primary" size="small" @click="addNewTimeSet">新增时间</el-button>
      <el-table :data="timeList" size="small" stripe height="400px">
        <el-table-column prop="begTime" label="开始时间" width="120"></el-table-column>
        <el-table-column prop="endTime" label="结束时间" width="90"></el-table-column>
        <el-table-column prop="bookLimit" label="座位数量"></el-table-column>
        <el-table-column label="状态" width="80">
          <template #default="scope">
            <el-tag 
              size="small" 
              :type="scope.row.bookStat ? 'success' : 'danger'" 
              effect="plain"
            >
              {{ scope.row.bookStat ? '生效' : '失效' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="110">
          <template #default="scope">
            <el-button 
              type="warning" 
              icon="Edit" 
              size="small" 
              @click="editTimeListBtn(scope.row)"
            ></el-button>
            <el-button 
              type="danger" 
              icon="Delete" 
              size="small" 
              @click="deleteTimeList(scope.row.journo)"
            ></el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
      </span>
    </template>
  </el-dialog>
</template>

<script setup>
import { ref, reactive, computed, onMounted, watch } from 'vue'
import { ElMessage } from 'element-plus'
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
  if (res.code !== 200) return ElMessage.error(res.msg)
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
  if (!valid) return

  try {
    let res
    if (timeListForm.journo) {
      res = await bookInfoApi.updateBookTime(timeListForm)
    } else {
      res = await bookInfoApi.addBookTime(timeListForm)
    }

    if (res.code !== 200) return ElMessage.error(res.msg)
    ElMessage.success(res.msg)
    getTimeList()
    timeSetDialog.value = false
  } catch (error) {
    ElMessage.error('操作失败，请重试')
  }
}

const deleteTimeList = async (journo) => {
  const res = await bookInfoApi.deleteBookTime(journo)
  if (res.code !== 200) return ElMessage.error(res.msg)
  ElMessage.success(res.msg)
  getTimeList()
  timeSetDialog.value = false
}
</script>

<style scoped>

</style>

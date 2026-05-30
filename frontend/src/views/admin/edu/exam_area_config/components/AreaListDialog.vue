<template>
  <el-dialog 
    :title="dialogTitle" 
    v-model="dialogVisible" 
    width="40%" 
    :close-on-click-modal="false"
  >
    <div>
      <!-- 考场座位维护对话框 -->
      <el-dialog 
        width="30%" 
        title="考场座位维护" 
        v-model="areaSeatDialog" 
        append-to-body
      >
        <el-form 
          ref="seatRef" 
          :model="areaListForm" 
          label-position="right" 
          label-width="100px" 
          size="small" 
          :rules="seatRules"
        >
          <el-form-item label="位置IP" prop="seatIp">
            <el-input v-model="areaListForm.seatIp"></el-input>
          </el-form-item>
          <el-form-item label="位置MAC" prop="seatMac">
            <el-input v-model="areaListForm.seatMac"></el-input>
          </el-form-item>
          <el-form-item label="位置描述" prop="seatDesc">
            <el-input v-model="areaListForm.seatDesc"></el-input>
          </el-form-item>
          <el-button size="small" @click="areaSeatDialog = false">取 消</el-button>
          <el-button size="small" type="primary" @click="submitAreaSeat">确 定</el-button>
        </el-form>
      </el-dialog>

      <el-button type="primary" size="small" @click="addNewAreaSeat">新增座位</el-button>
      <el-table :data="areaList" size="small" stripe height="400px">
        <el-table-column prop="seatIp" label="座位IP" width="130"></el-table-column>
        <el-table-column prop="seatMac" label="座位MAC"></el-table-column>
        <el-table-column prop="seatDesc" label="座位描述"></el-table-column>
        <el-table-column label="操作" width="110">
          <template #default="scope">
            <el-button 
              type="warning" 
              icon="Edit" 
              size="small" 
              @click="editAreaListBtn(scope.row)"
            ></el-button>
            <el-button 
              type="danger" 
              icon="Delete" 
              size="small" 
              @click="deleteAreaList(scope.row.journo)"
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
import { examAreaConfigApi } from '@/api/edu/examAreaConfig'

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
  areaCode: {
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
const areaSeatDialog = ref(false)
const areaList = ref([])
const areaListForm = reactive({ 
  journo: '', 
  areaCode: props.areaCode, 
  seatIp: '', 
  seatMac: '', 
  seatDesc: '' 
})

const seatRules = reactive({
  seatIp: [{ required: true, min: 6, max: 20, message: '电脑的IP地址', trigger: 'blur' }],
  seatMac: [{ required: true, message: '电脑的MAC地址', trigger: 'blur' }],
  seatDesc: [{ required: true, message: '请输入相关描述，该描述会显示于前端', trigger: 'blur' }]
})

const seatRef = ref(null)

// Watch
watch(() => props.areaCode, (newAreaCode) => {
  areaListForm.areaCode = newAreaCode
  if (props.visible) {
    getAreaList()
  }
}, { immediate: true })

// Lifecycle
onMounted(() => {
  if (props.visible) {
    getAreaList()
  }
})

// Methods
const getAreaList = async () => {
  const res = await examAreaConfigApi.getAreaDetailList({ areaCode: props.areaCode })
  if (res.code !== 200) return ElMessage.error(res.msg)
  areaList.value = res.data
}

const addNewAreaSeat = () => {
  areaListForm.journo = ''
  areaListForm.seatIp = ''
  areaListForm.seatMac = ''
  areaListForm.seatDesc = ''
  areaSeatDialog.value = true
}

const editAreaListBtn = (row) => {
  Object.assign(areaListForm, row)
  areaSeatDialog.value = true
}

const submitAreaSeat = async () => {
  const valid = await seatRef.value.validate()
  if (!valid) return

  try {
    let res
    if (areaListForm.journo) {
      res = await examAreaConfigApi.updateAreaSeat(areaListForm)
    } else {
      res = await examAreaConfigApi.addAreaSeat(areaListForm)
    }

    if (res.code !== 200) return ElMessage.error(res.msg)
    ElMessage.success(res.msg)
    getAreaList()
    areaSeatDialog.value = false
  } catch (error) {
    ElMessage.error('操作失败，请重试')
  }
}

const deleteAreaList = async (journo) => {
  const res = await examAreaConfigApi.deleteAreaSeat(journo)
  if (res.code !== 200) return ElMessage.error(res.msg)
  ElMessage.success(res.msg)
  getAreaList()
  areaSeatDialog.value = false
}
</script>

<style scoped>

</style>

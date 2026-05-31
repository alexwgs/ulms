<template>
  <t-dialog
    :header="dialogTitle"
    v-model:visible="dialogVisible"
    width="40%"
    :close-on-overlay-click="false"
  >
    <div>
      <!-- 考场座位维护对话框 -->
      <t-dialog
        width="30%"
        header="考场座位维护"
        v-model:visible="areaSeatDialog"
        attach="body"
      >
        <t-form 
          ref="seatRef" 
          :data="areaListForm" 
          label-align="right" 
          label-width="100px" 
          size="small" 
          :rules="seatRules"
        >
          <t-form-item label="位置IP" name="seatIp">
            <t-input v-model="areaListForm.seatIp"></t-input>
          </t-form-item>
          <t-form-item label="位置MAC" name="seatMac">
            <t-input v-model="areaListForm.seatMac"></t-input>
          </t-form-item>
          <t-form-item label="位置描述" name="seatDesc">
            <t-input v-model="areaListForm.seatDesc"></t-input>
          </t-form-item>
          <t-button size="small" @click="areaSeatDialog = false">取 消</t-button>
          <t-button size="small" theme="primary" @click="submitAreaSeat">确 定</t-button>
        </t-form>
      </t-dialog>

      <t-button theme="primary" size="small" @click="addNewAreaSeat">新增座位</t-button>
      <CustomTable rowKey="id" :data="areaList" size="small" stripe height="400px">
        <TableColumn colKey="seatIp" label="座位IP" width="130"></TableColumn>
        <TableColumn colKey="seatMac" label="座位MAC"></TableColumn>
        <TableColumn colKey="seatDesc" label="座位描述"></TableColumn>
        <TableColumn label="操作" width="110">
          <template #default="scope">
            <t-button 
              theme="warning" size="small" 
              @click="editAreaListBtn(scope.row)"><template #icon><DynamicIcon name="edit" /></template></t-button>
            <t-button 
              theme="danger" size="small" 
              @click="deleteAreaList(scope.row.journo)"><template #icon><DynamicIcon name="delete" /></template></t-button>
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
  if (res.code !== 200) return MessagePlugin.error(res.msg)
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
  if (valid !== true) return

  try {
    let res
    if (areaListForm.journo) {
      res = await examAreaConfigApi.updateAreaSeat(areaListForm)
    } else {
      res = await examAreaConfigApi.addAreaSeat(areaListForm)
    }

    if (res.code !== 200) return MessagePlugin.error(res.msg)
    MessagePlugin.success(res.msg)
    getAreaList()
    areaSeatDialog.value = false
  } catch (error) {
    MessagePlugin.error('操作失败，请重试')
  }
}

const deleteAreaList = async (journo) => {
  const res = await examAreaConfigApi.deleteAreaSeat(journo)
  if (res.code !== 200) return MessagePlugin.error(res.msg)
  MessagePlugin.success(res.msg)
  getAreaList()
  areaSeatDialog.value = false
}
</script>

<style scoped>

</style>

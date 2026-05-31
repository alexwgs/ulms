<template>
  <t-dialog header="座位信息核对" v-model:visible="dialogVisible" :close-on-overlay-click="false" :close-on-esc-keydown="false"
    :close-btn="false">
    <t-alert title="请注意，请正确输入您当前所使用电脑对应到分机号码！若错误输入，可能导致功能不可使用！" theme="warning" show-icon>
    </t-alert>
    <t-form :data="stationForm" ref="stationRef" :rules="stationRules">
      <t-form-item label="Ip地址" name="pcIp" :label-width="stationLabelWidth">
        <t-input size="small" v-model="stationForm.pcIp" autocomplete="off" :placeholder="ohtStore.station.pcIp"
          disabled></t-input>
      </t-form-item>
      <t-form-item label="所在楼层" name="floorNum" :label-width="stationLabelWidth">
        <t-input size="small" v-model="stationForm.floorNum" autocomplete="off"
          :placeholder="ohtStore.station.floorNum" readonly></t-input>
      </t-form-item>
      <t-form-item label="分机号码" name="extnNum" :label-width="stationLabelWidth">
        <t-input size="small" v-model="stationForm.extnNum" autocomplete="off"
          :placeholder="ohtStore.station.extnNum"></t-input>
      </t-form-item>
    </t-form>
    <div class="dialog-footer">
      <t-button size="small" @click="dialogVisible = false">取 消</t-button>
      <t-button size="small" theme="primary" @click="submitExtNum">确 定</t-button>
    </div>
  </t-dialog>
</template>

<script setup>
import { ref, reactive, computed, onMounted } from 'vue'
import { MessagePlugin } from 'tdesign-vue-next'
import { useOhtStore } from '@/stores'
import { httpInstance } from '@/utils/request'
import { getLocalIp } from '@/utils/tools'
// 定义 props
const props = defineProps({
  updateFlag: {
    type: Boolean,
    default: false
  }
})

// 导入 store
const ohtStore = useOhtStore()

// 响应式数据
const dialogVisible = ref(false)
const stationForm = reactive({ extnNum: '' })
const stationLabelWidth = ref('120px')
const stationRef = ref(null)

// 表单验证规则
const stationRules = reactive({
  // pcIp: [{ required: true, message: 'Ip地址获取不可为空', triggle: 'blur' }],
  // extnNum: [{ required: true, message: '分机号信息不可为空', triggle: 'blur' }, { max: 5, min: 5, message: '请输入5位数字的分机号码！' }],
})

const floorNumMap = {
  '71': '1',
  '72': '2',
  '80': '3',
  '74': '4',
  '75': '5',
  '76': '6',
  '77': '7',
  '78': '8',
  '79': '9',
  '73': '10',
  '81': '11',
  '82': '12',
  '83': '13',
  '84': '14',
  '85': '15',
  '0': '0',
}

const openDialog = () => {
  // 初始化表单数据
  stationForm.value = ohtStore.station
  getLocalIp().then(ip => {
    stationForm.value.pcIp = ip
    // 如果正常获取到IPv4地址
    if (ip) {
      stationForm.value.floorNum = floorNumMap[ip.split('.')[1]] ? floorNumMap[ip.split('.')[1]] : 0
    }
  })
  dialogVisible.value = true
}

// 提交分机号码
const submitExtNum = async () => {
  // 验证表单
  if (stationRef.value) {
    const valid = await stationRef.value.validate()
    if (valid === true) {
      try {
        // 提交数据
        const res = await httpInstance.post('station/update', stationForm)
        if (res.code !== 200) return MessagePlugin.error(res.msg)
        // 显示成功消息
        MessagePlugin.success(res.msg)
        // 更新 store 中的 station 数据
        ohtStore.setStation(res.station)
        // 关闭对话框
        dialogVisible.value = false
      } catch (error) {
        MessagePlugin.error('提交失败！请稍后重试。')
      }
    } else {
      MessagePlugin.error('请正确填写表单！')
    }
  }
}
defineExpose({
  openDialog
})
</script>

<style lang="less" scoped>
.dialog-footer {
  display: flex;
  justify-content: flex-end;
  margin-top: 20px;
}
</style>

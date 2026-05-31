<template>
  <div>
    <t-dialog header="课程评价" width="70%" v-model:visible="dialogFormVisible" :close-on-overlay-click="false">
      <t-radio-group v-model="evaluate.ifAnon" size="small">
        <t-radio-button :value="1">匿名</t-radio-button>
        <t-radio-button :value="0">实名</t-radio-button>
      </t-radio-group>
      <t-form ref="satisfyFormRef" :data="satisfyFormData" :rules="rules" size="small" label-width="100"
        style="margin-top: 10px; height: 50vh; overflow: auto">
        <div v-for="(item, index) in templete" :key="index">
          <div class="ques-style">
            {{ index + 1 }}.{{ item.quesCont }}
            <t-form-item v-if="item.quesType === 1" required>
              <t-rate v-model.number="item.cont" :max="5" allow-half></t-rate>
            </t-form-item>
            <t-form-item v-else-if="item.quesType === 2">
              <t-textarea v-model="item.cont" placeholder="请输入评价内容" :maxlength="500" show-limit-number
                :autosize="{ minRows: 4, maxRows: 4 }" :style="{ width: '100%' }" />
            </t-form-item>
          </div>
        </div>
      </t-form>
      <template #footer>
        <t-button size="small" @click="dialogFormVisible = false">取消</t-button>
        <t-button size="small" theme="primary" @click="handelConfirm">确定</t-button>
      </template>
    </t-dialog>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue'
import { MessagePlugin } from 'tdesign-vue-next'
import { httpInstance } from '@/utils/request'

const dialogFormVisible = ref(false)
const templete = ref([])
const satisfyFormRef = ref(null)

const evaluate = reactive({
  courseId: '',
  ifAnon: 1,
  tempId: '',
  evaluate: []
})

const satisfyFormData = reactive({})

const rules = reactive({})

const show = async (course) => {
  evaluate.courseId = course.courseId
  evaluate.tempId = course.tempId
  // 获取评价模板
  const res = await httpInstance.get(`college/evaluate/templete/${course.tempId}`)
  if (res.code !== 200) return MessagePlugin.error(res.msg)
  templete.value = res.data
  dialogFormVisible.value = true
}

const handelConfirm = async () => {
  const flag = templete.value.filter((e) => e.cont === null)
  if (flag.length > 0) {
    return MessagePlugin.error('请输入评价内容！')
  }
  evaluate.evaluate = templete.value
  const res = await httpInstance.post('college/evaluate', evaluate)
  if (res.code !== 200) return MessagePlugin.error(res.msg)
  MessagePlugin.success(res.msg)
  dialogFormVisible.value = false
  // 调用父组件方法
  if (window.parentGetLog) {
    window.parentGetLog()
  }
}

// 暴露方法给父组件
defineExpose({
  show
})
</script>

<style lang="less" scoped>
.ques-style {
  font-size: 14px;
  font-weight: 800;
  line-height: 40px;
  border: 1px solid #d8d8d8;
  padding: 5px 10px 0 10px;
  width: 90%;
  margin-bottom: 10px;
}
</style>

<template>
  <div>
    <el-dialog title="课程评价" width="70%" v-model="dialogFormVisible" :close-on-click-modal="false">
      <el-radio-group v-model="evaluate.ifAnon" size="small">
        <el-radio-button :value="1">匿名</el-radio-button>
        <el-radio-button :value="0">实名</el-radio-button>
      </el-radio-group>
      <el-form ref="satisfyFormRef" :model="satisfyFormData" :rules="rules" size="small" label-width="100"
        style="margin-top: 10px; height: 50vh; overflow: auto">
        <div v-for="(item, index) in templete" :key="index">
          <div class="ques-style">
            {{ index + 1 }}.{{ item.quesCont }}
            <el-form-item v-if="item.quesType === 1" required>
              <el-rate v-model.number="item.cont" :max="5" allow-half></el-rate>
            </el-form-item>
            <el-form-item v-else-if="item.quesType === 2">
              <el-input v-model="item.cont" type="textarea" placeholder="请输入评价内容" :maxlength="500" show-word-limit
                :autosize="{ minRows: 4, maxRows: 4 }" :style="{ width: '100%' }"></el-input>
            </el-form-item>
          </div>
        </div>
      </el-form>
      <template #footer>
        <el-button size="small" @click="dialogFormVisible = false">取消</el-button>
        <el-button size="small" type="primary" @click="handelConfirm">确定</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue'
import { ElMessage } from 'element-plus'
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
  if (res.code !== 200) return ElMessage.error(res.msg)
  templete.value = res.data
  dialogFormVisible.value = true
}

const handelConfirm = async () => {
  const flag = templete.value.filter((e) => e.cont === null)
  if (flag.length > 0) {
    return ElMessage.error('请输入评价内容！')
  }
  evaluate.evaluate = templete.value
  const res = await httpInstance.post('college/evaluate', evaluate)
  if (res.code !== 200) return ElMessage.error(res.msg)
  ElMessage.success(res.msg)
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

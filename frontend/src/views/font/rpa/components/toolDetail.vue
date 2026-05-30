<template>
  <el-card class="box-card" style="height: calc(100vh - 180px); overflow: auto">
    <div>
      <el-descriptions direction="vertical" size="small" :column="2" border>
        <el-descriptions-item label="工具名称" :span="2">{{
          record.name
        }}</el-descriptions-item>
        <el-descriptions-item label="联系人">
          <el-tag size="small">{{ record.contacts }}</el-tag>
        </el-descriptions-item>
        <el-descriptions-item label="运行时长">{{
          record.runTimes
        }}</el-descriptions-item>
        <el-descriptions-item label="使用说明" :span="2">
          <div v-html="record.memo"></div>
        </el-descriptions-item>
        <el-descriptions-item label="运行参数" :span="2">
          <el-form
            :model="fromData"
            :rules="rules"
            ref="ruleFormRef"
            label-width="120px"
          >
            <div v-for="item in list" :key="item.id">
              <el-form-item
                :label="item.label"
                :prop="item.fieldName + ''"
                :required="item.required"
                :style="item.orderId < 0 ? 'display:none' : ''"
              >
                <el-input
                  v-if="item.type === 'input'"
                  style="width: 100%"
                  v-model="fromData[item.fieldName]"
                  :type="getOptionValue(item.options, 'type')"
                  :maxlength="getOptionValue(item.options, 'maxlength')"
                  :minlength="getOptionValue(item.options, 'minlength')"
                  :rows="getOptionValue(item.options, 'rows')"
                  :placeholder="getOptionValue(item.options, 'placeholder')"
                  size="small"
                ></el-input>
                <el-select
                  v-else-if="item.type === 'select'"
                  style="width: 100%"
                  v-model="fromData[item.fieldName]"
                  size="small"
                  :placeholder="getOptionValue(item.options, 'placeholder')"
                  :multiple="getOptionValue(item.options, 'mutiselect')"
                  :multiple-limit="
                    getOptionValue(item.options, 'multipleLimit')
                  "
                >
                  <el-option
                    v-for="optionItem in getSelectOptions(item.options)"
                    :key="optionItem.value"
                    :label="optionItem.label"
                    :value="optionItem.value"
                  ></el-option>
                </el-select>
                <el-date-picker
                  v-else-if="item.type === 'dateTime'"
                  size="small"
                  v-model="fromData[item.fieldName]"
                  :type="getOptionValue(item.options, 'type')"
                  placeholder="选择日期"
                  :editable="getOptionValue(item.options, 'editable')"
                  :format="getOptionValue(item.options, 'format').toUpperCase()"
                  :value-format="getOptionValue(item.options, 'valueFormat').toUpperCase()"
                ></el-date-picker>
                <el-date-picker
                  v-else-if="item.type === 'dateRange'"
                  style="width: 100%"
                  size="small"
                  v-model="fromData[item.fieldName]"
                  :type="getOptionValue(item.options, 'type')"
                  placeholder="选择日期"
                  :editable="getOptionValue(item.options, 'editable')"
                  :format="getOptionValue(item.options, 'format').toUpperCase()"
                  :value-format="getOptionValue(item.options, 'valueFormat').toUpperCase()"
                ></el-date-picker>
                <UserSelect
                  v-else-if="item.type === 'userSelect'"
                  style="width: 100%"
                  v-model="fromData[item.fieldName]"
                ></UserSelect>
                <OrgCascader
                  v-else-if="item.type === 'orgSelect'"
                  style="width: 100%"
                  v-model="fromData[item.fieldName]"
                  size="small"
                  :type="getOptionValue(item.options, 'type')"
                  :placeholder="getOptionValue(item.options, 'placeholder')"
                  :props="{
                    label: 'label',
                    children: 'children',
                    value: 'label',
                    checkStrictly:
                      getOptionValue(item.options, 'type') === 'dept',
                    emitPath: false,
                    multiple: getOptionValue(item.options, 'mutiselect')
                  }"
                ></OrgCascader>
                <div v-else-if="item.type === 'batchData'">
                  <el-input
                    style="width: 100%"
                    v-model="textareaInput"
                    type="textarea"
                    maxlength="2000"
                    :minlength="getOptionValue(item.options, 'minlength')"
                    :rows="getOptionValue(item.options, 'rows')"
                    size="small"
                    @input="changeValue(item.fieldName)"
                    :validate-event="false"
                  ></el-input>
                  <el-input
                    style="width: 100%; margin-top: 5px"
                    v-model="fromData[item.fieldName]"
                    type="textarea"
                    placeholder="参数预览区"
                    rows="2"
                    size="small"
                    readonly
                    :validate-event="false"
                  ></el-input>
                </div>
                <div v-else-if="item.type === 'userInfo'">
                  <UserSelect
                    v-if="getOptionValue(item.options, 'type') === 'user'"
                    style="width: 100%"
                    v-model="fromData[item.fieldName]"
                    :defaultValue="runUser.ploNum"
                    :disabled="true"
                  ></UserSelect>
                  <OrgCascader
                    v-else
                    style="width: 100%"
                    v-model="fromData[item.fieldName]"
                    size="small"
                    :defaultValue="
                      getOptionValue(item.options, 'type') === 'dept'
                        ? runUser.deptName
                        : runUser.groupName
                    "
                    :type="getOptionValue(item.options, 'type')"
                    :disabled="true"
                    :props="{
                      label: 'label',
                      children: 'children',
                      value: 'label',
                      checkStrictly:
                        getOptionValue(item.options, 'type') === 'dept',
                      emitPath: false,
                      multiple: false
                    }"
                  ></OrgCascader>
                </div>
              </el-form-item>
            </div>
          </el-form>
        </el-descriptions-item>
      </el-descriptions>
      <el-button
        type="primary"
        size="small"
        style="width: 100%"
        :disabled="Object.keys(record).length === 0"
        @click="submit"
        :loading="submitBtnFlag || cdTime > 0"
        >{{ cdTime === 0 ? '提交' : '冷却中...' + cdTime + '秒' }}</el-button
      >
    </div>
  </el-card>
</template>

<script setup>
import { ref, reactive, onMounted, onBeforeUnmount } from 'vue'
import { ElMessage } from 'element-plus'
import UserSelect from '@/components/EmployeeSelect.vue'
import OrgCascader from '@/components/DepartmentSelect.vue'
import {
  getRpaToolTemplete,
  addRpaReport,
  getRpaToolCdTime,
  callRpaTool
} from '@/api/rpa/rpa.js'

// 响应式数据
const record = ref({})
const list = ref([])
const submitBtnFlag = ref(false)
const fromData = ref({})
const rules = ref([])
const cdTime = ref(0)
const textareaInput = ref('')
const timer = ref(null)
const ruleFormRef = ref(null)

// 用户信息
const runUser = ref({})

// 文件服务URL
const fsURL = import.meta.env.VITE_FILE_MANAGE_BASE

// 工具报告数据
const toolReport = reactive({
  toolId: '',
  toolName: '',
  runTime: '',
  runParam: '',
  returnCode: '',
  returnMessage: '',
  returnTime: ''
})

// 初始化数据
const initData = (toolRecord) => {
  record.value = toolRecord
  getTemplete()
}

// 获取工具模板
const getTemplete = async () => {
  fromData.value = {}
  list.value = []
  clearInterval(timer.value)
  try {
    const res = await getRpaToolTemplete(record.value.id)
    if (res.code === 200) {
      list.value = res.data
      getColdDown()
    } else {
      ElMessage.error(res.msg)
    }
  } catch (error) {
    console.error('获取工具模板失败:', error)
    ElMessage.error('获取工具模板失败')
  }
}

// 获取冷却时间
const getColdDown = async () => {
  try {
    const res = await getRpaToolCdTime(record.value.id)
    if (res.code === 200) {
      cdTime.value = res.data
      // 如果cdTime大于0，开启定时器
      if (cdTime.value > 0) {
        startCountdown()
      }
    } else {
      ElMessage.error(res.msg)
    }
  } catch (error) {
    console.error('获取冷却时间失败:', error)
    ElMessage.error('获取冷却时间失败')
  }
}

// 开始倒计时
const startCountdown = () => {
  timer.value = setInterval(() => {
    cdTime.value--
    if (cdTime.value < 1) {
      clearInterval(timer.value)
    }
  }, 1000)
}

// 格式化字符串
const formatString = (input) => {
  let result = input.replace(/\D/g, ',')
  result = result.replace(/,+?/g, ',')
  // 去除字符串开头和结尾的逗号
  result = result.replace(/^,|,$/g, '')
  return result
}

// 文本域输入变化处理
const changeValue = (fieldName) => {
  fromData.value[fieldName] = formatString(textareaInput.value)
}

// 写日志
const writeLog = async (returnCode, returnMessage, paramDefineList) => {
  toolReport.toolId = record.value.id
  toolReport.toolName = record.value.name
  toolReport.returnCode = returnCode
  toolReport.returnMessage =
    returnMessage == null ? '' : returnMessage.toString()
  toolReport.runParam = JSON.stringify(paramDefineList)
  try {
    await addRpaReport(toolReport)
    getColdDown()
  } catch (error) {
    console.error('提交报告失败:', error)
    ElMessage.error('提交报告失败')
  }
}

// 提交表单
const submit = async () => {
  // 验证表单
  if (!ruleFormRef.value) return

  try {
    await ruleFormRef.value.validate((valid) => {
      if (valid) {
        // 处理数组类型的参数
        Object.keys(fromData.value).map((item) => {
          const value = fromData.value[item]
          if (Array.isArray(value)) {
            fromData.value[item] = value.join(',')
          }
        })

        submitBtnFlag.value = true

        // 构建参数列表
        const paramDefineList = {
          paramDefineList: [],
          subscriptionId: record.value.subscribeId
        }

        for (const item in list.value) {
          let value = ''
          if (
            Object.prototype.hasOwnProperty.call(
              fromData.value,
              list.value[item].fieldName
            )
          ) {
            value = fromData.value[list.value[item].fieldName]
          }

          paramDefineList.paramDefineList.push({
            paramName: list.value[item].fieldName,
            paramDescription: '',
            paramValue: value,
            index: list.value[item].orderId
          })
        }

        // 设置运行时间
        const now = new Date()
        toolReport.runTime = now.toISOString().slice(0, 19).replace('T', ' ')

        // 调用工具API
        callRpaTool(record.value.url, paramDefineList, {
          'Content-Type': 'application/json',
          Authorization: record.value.auth
        })
          .then((response) => {
            const resData = response
            if (resData.returnCode === 'SUC0000') {
              ElMessage.success(resData.body.taskId + '任务提交成功！')
            } else {
              ElMessage.error('任务提交失败！' + resData.errorMsg)
            }
            writeLog(resData.returnCode, resData.errorMsg, paramDefineList)
            submitBtnFlag.value = false
          })
          .catch((error) => {
            writeLog('ERROR', error.message, paramDefineList)
            submitBtnFlag.value = false
            ElMessage.error('任务提交失败！' + error.message)
          })
      } else {
        ElMessage.warning('表单校验失败，请检查！')
      }
    })
  } catch (error) {
    console.error('表单验证失败:', error)
    ElMessage.error('表单验证失败')
  }
}

// 辅助函数：获取选项值
const getOptionValue = (options, key) => {
  try {
    const parsedOptions = JSON.parse(options)
    return parsedOptions[key] || ''
  } catch (error) {
    console.error('解析选项失败:', error)
    return ''
  }
}

// 辅助函数：获取选择器选项
const getSelectOptions = (options) => {
  try {
    const parsedOptions = JSON.parse(options)
    return JSON.parse(parsedOptions.options) || []
  } catch (error) {
    console.error('解析选择器选项失败:', error)
    return []
  }
}

// 生命周期钩子
onMounted(() => {
  // 获取当前用户信息
  runUser.value = JSON.parse(window.localStorage.getItem('user') || '{}')
})

onBeforeUnmount(() => {
  // 清除定时器
  if (timer.value) {
    clearInterval(timer.value)
  }
})

// 暴露方法
defineExpose({
  initData
})
</script>

<style lang="less" scoped>
/* 保持原有样式不变 */
</style>

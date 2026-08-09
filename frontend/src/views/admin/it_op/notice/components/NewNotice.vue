<template>
  <div>
    <t-card class="management-card">
      <t-form
        ref="formRef"
        :data="form"
        label-width="80px"
        :rules="formRules"
      >
        <t-form-item label="板块" name="category">
          <t-select v-model="form.category" placeholder="请选择发帖板块">
            <t-option
              v-for="category in (dictStore.dictList?.cyt_system_category || [])"
              :key="category.id"
              :label="category.codeval"
              :value="parseInt(category.code)"
            ></t-option>
          </t-select>
        </t-form-item>
        <t-form-item label="标题" name="title">
          <t-input v-model="form.title"></t-input>
        </t-form-item>
        <t-form-item label="正文" name="content">
          <WangEditor v-model="form.content" :height="500" />
        </t-form-item>
        <t-form-item>
          <t-button
            theme="primary"
            :disabled="form.content.length < 1 || submitStat"
            @click="onSubmit(1)"
            >{{ submitStat ? '正在发布' : '立即发布' }}</t-button
          >
          <t-button @click="onSubmit(2)">存草稿</t-button>
        </t-form-item>
      </t-form>
    </t-card>
  </div>
</template>

<script setup>
import { ref, reactive, computed, watch, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { MessagePlugin } from 'tdesign-vue-next'
import WangEditor from '@/components/WangEditor.vue'
import { manageNoticeApi } from '@/api/admin/manageNotice'
import { getArticalDetail } from '@/api/a6voice/index.js'
import { useDictStore } from '@/stores'

const dictStore = useDictStore()
// 获取全局字典数据
const globalDict = computed(() => {
  // 假设全局字典存储在全局状态中，这里根据实际情况调整
  return window.$global?.dict || {}
})

// 路由
const router = useRouter()

// Props
const props = defineProps({
  id: String
})

// 表单引用
const formRef = ref(null)

// 表单数据
const form = reactive({
  category: '',
  articalType: 9,
  title: '',
  content: '',
  status: 2
})

// 表单验证规则
const formRules = reactive({
  category: [{ required: true, message: '请选择板块', trigger: 'change' }],
  title: [
    { required: true, message: '请输入帖子标题', trigger: 'blur' },
    { max: 100, min: 2, message: '请输入2-100个字符的帖子标题' }
  ],
  content: [
    { required: true, message: '请输入帖子内容', trigger: 'blur' },
    { min: 50, message: '请输入至少50个字符以上到内容！' }
  ]
})

// 提交状态
const submitStat = ref(false)

// 初始化文章
const initArtical = async () => {
  if (props.id !== 'new') {
    try {
      const res = await getArticalDetail(props.id)
      if (res.code !== 200) {
        MessagePlugin.error(res.msg)
        return
      }

      // 将返回的数据合并到form中
      Object.assign(form, res.data)
    } catch (error) {
      MessagePlugin.error('获取文章详情失败')
      console.error(error)
    }
  }
}

// 返回上一页
const goBack = () => {
  router.go(-1) // 返回上一层
}

// 提交表单
const onSubmit = async (status) => {
  if (!formRef.value) return

  try {
    const valid = await formRef.value.validate()
    if (valid === true) {
      submitStat.value = true
      form.status = status

      let res
      if (props.id !== 'new') {
        res = await updateArticalApi('cyt/artical', form)
      } else {
        res = await saveArticalApi('cyt/artical', form)
      }

      if (res.code !== 200) {
        MessagePlugin.error(res.msg)
        return
      }

      MessagePlugin.success(props.id !== 'new' ? '更新成功' : '发布成功')
      // 可以选择返回上一页或清空表单
      // router.go(-1)
    } else {
      MessagePlugin.error('表单验证失败')
    }
  } catch (error) {
    MessagePlugin.error('提交失败')
    console.error(error)
  } finally {
    submitStat.value = false
  }
}

// 监听id变化
watch(
  () => props.id,
  (newVal, oldVal) => {
    if (newVal !== oldVal) {
      initArtical()
    }
  }
)

// 组件挂载时初始化
onMounted(() => {
  initArtical()
})
</script>

<style lang="less" scoped>
/* 可以根据需要添加样式 */
</style>

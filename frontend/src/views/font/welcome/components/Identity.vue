<template>
  <t-dialog
    header="身份选择"
    v-model:visible="dialogVisible"
    width="50%"
    :before-close="handleClose"
  >
    <t-form :data="form" label-width="80px">
      <t-form-item label="选择身份">
        <t-checkbox-group v-model="form.identity">
          <t-checkbox value="无身份">无身份</t-checkbox>
          <t-checkbox
            v-for="item in identityList"
            :key="item.id"
            :value="item.statusName"
          ></t-checkbox>
        </t-checkbox-group>
      </t-form-item>
    </t-form>
    <template #footer>
      <span class="dialog-footer">
        <t-button @click="handleClose">取消</t-button>
        <t-button theme="primary" @click="handleSubmit">确定</t-button>
      </span>
    </template>
  </t-dialog>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { MessagePlugin } from 'tdesign-vue-next'
import { getIdentityList } from '@/api/welcome/index.js'

const dialogVisible = ref(false)
const identityList = ref([])
const form = reactive({
  identity: []
})

const show = () => {
  dialogVisible.value = true
  getIdentity()
}

const handleClose = () => {
  dialogVisible.value = false
}

const handleSubmit = () => {
  MessagePlugin.success('身份选择成功')
  handleClose()
}

const getIdentity = async () => {
  try {
    const res = await getIdentityList()
    if (res.code !== 200) {
      MessagePlugin.error(res.msg)
      return
    }
    identityList.value = res.data || []
  } catch (error) {
    console.error('获取身份列表失败', error)
  }
}

defineExpose({
  show
})
</script>

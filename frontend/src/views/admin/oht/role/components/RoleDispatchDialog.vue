<template>
  <t-dialog header="转接关系管理" :close-on-overlay-click="false" v-model:visible="dialogVisible">
    <t-form :data="dispatchForm" ref="dispatchFormRef">
      <CustomTable rowKey="id" :data="dispatchForm" border style="width: 100%">
        <TableColumn label="监听群组" width="180">
          <template #default="{ row }">
            <t-select v-model="row.roleCode" size="small" placeholder="请选择">
              <t-option
                v-for="item in ohtRoleTableList"
                :key="item.roleCode"
                :label="item.roleName"
                :value="item.roleCode"
              ></t-option>
            </t-select>
          </template>
        </TableColumn>
        <TableColumn label="优先级" width="180">
          <template #default="{ row }">
            <t-input size="small" v-model="row.priority" type="number" autocomplete="off" placeholder=""></t-input>
          </template>
        </TableColumn>
        <TableColumn label="等待时长">
          <template #default="{ row }">
            <t-input size="small" v-model="row.waitTime" type="number" autocomplete="off" placeholder=""></t-input>
          </template>
        </TableColumn>
        <TableColumn label="跨楼层">
          <template #default="{ row }">
            <t-select size="small" v-model="row.stepFloor" placeholder="请选择">
              <t-option
                v-for="item in (dictStore.dictList?.oht_step_floor || [])"
                :key="item.code"
                :label="item.codeval"
                :value="item.code"
              ></t-option>
            </t-select>
          </template>
        </TableColumn>
        <TableColumn label="操作">
          <template #default="{ $index }">
            <t-button size="small" theme="danger" @click="removeDispatchItem($index)">删除</t-button>
          </template>
        </TableColumn>
      </CustomTable>
    </t-form>
    <template #footer>
      <div class="dialog-footer">
        <t-button size="small" @click="dialogVisible = false">取 消</t-button>
        <t-button size="small" theme="primary" @click="submitDispatch">确 定</t-button>
      </div>
    </template>
  </t-dialog>
</template>

<script setup>
import { ref, reactive, watch } from 'vue'
import { useDictStore } from '@/stores'

const dictStore = useDictStore()

const props = defineProps({
  visible: {
    type: Boolean,
    default: false
  },
  ohtRoleTableList: {
    type: Array,
    default: () => []
  }
})

const emit = defineEmits(['update:visible'])

const dialogVisible = ref(props.visible)
const dispatchFormRef = ref(null)

const dispatchForm = ref([{ roleCode: 1, priority: 1, waitTime: 30, stepFloor: 0 }])

// 监听外部visible变化
watch(() => props.visible, (newVal) => {
  dialogVisible.value = newVal
})

// 监听内部dialogVisible变化
watch(dialogVisible, (newVal) => {
  emit('update:visible', newVal)
})

// 删除转接关系项
const removeDispatchItem = (index) => {
  dispatchForm.value.splice(index, 1)
}

// 提交转接关系
const submitDispatch = async () => {
  dialogVisible.value = false
}
</script>

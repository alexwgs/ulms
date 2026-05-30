<template>
  <el-dialog title="转接关系管理" :close-on-click-modal="false" v-model="dialogVisible">
    <el-form :model="dispatchForm" ref="dispatchFormRef">
      <el-table :data="dispatchForm" border style="width: 100%">
        <el-table-column label="监听群组" width="180">
          <template #default="{ row }">
            <el-select v-model="row.roleCode" size="small" placeholder="请选择">
              <el-option
                v-for="item in ohtRoleTableList"
                :key="item.roleCode"
                :label="item.roleName"
                :value="item.roleCode"
              ></el-option>
            </el-select>
          </template>
        </el-table-column>
        <el-table-column label="优先级" width="180">
          <template #default="{ row }">
            <el-input size="small" v-model="row.priority" type="number" autocomplete="off" placeholder=""></el-input>
          </template>
        </el-table-column>
        <el-table-column label="等待时长">
          <template #default="{ row }">
            <el-input size="small" v-model="row.waitTime" type="number" autocomplete="off" placeholder=""></el-input>
          </template>
        </el-table-column>
        <el-table-column label="跨楼层">
          <template #default="{ row }">
            <el-select size="small" v-model="row.stepFloor" placeholder="请选择">
              <el-option
                v-for="item in dictStore.dictList.oht_step_floor"
                :key="item.code"
                :label="item.codeval"
                :value="item.code"
              ></el-option>
            </el-select>
          </template>
        </el-table-column>
        <el-table-column label="操作">
          <template #default="{ $index }">
            <el-button size="small" type="danger" @click="removeDispatchItem($index)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-form>
    <template #footer>
      <div class="dialog-footer">
        <el-button size="small" @click="dialogVisible = false">取 消</el-button>
        <el-button size="small" type="primary" @click="submitDispatch">确 定</el-button>
      </div>
    </template>
  </el-dialog>
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

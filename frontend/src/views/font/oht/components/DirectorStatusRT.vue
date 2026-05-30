<template>
  <el-dialog
    width="80%"
    title="业务主任实时状态表"
    v-model="rtVisiable"
    :close-on-click-modal="false"
    :close-on-press-escape="false"
    :show-close="false"
  >
    <!-- <el-row :gutter="20">
      <el-col :span="8">
        <el-select size="small" v-model="floor" placeholder="根据楼层过滤" @change="filterData($event, 'floor')">
          <el-option v-for="item in floors" :key="item" :label="item+' 楼'" :value="item"></el-option>
        </el-select>
      </el-col>
      <el-col :span="8">
        <el-select size="small" v-model="dept" placeholder="根据科室过滤" @change="filterData($event, 'dept')">
          <el-option v-for="item in dictStore.dict.cyt_artical_resp_dept" :key="item.id" :label="item.codeval" :value="item.code"></el-option>
        </el-select>
      </el-col>
      <el-col :span="8"></el-col>
    </el-row> -->
    <el-alert
      title="点击表头中的箭头可对“楼层”、“科室”进行筛选及排序！支付复核筛选！"
      type="info"
      style="margin-top: -10px"
      show-icon
    >
    </el-alert>
    <el-table
      :data="ohtStore.userList.filter((item) => item.user.jobLevel === '101')"
      stripe
      height="300px"
      size="small"
      style="width: 100%"
    >
      <el-table-column
        prop="station.floorNum"
        label="楼层"
        width="100"
        sortable
        :filters="[
          { text: '3楼', value: '3' },
          { text: '4楼', value: '4' },
          { text: '5楼', value: '5' },
          { text: '6楼', value: '6' },
          { text: '7楼', value: '7' },
          { text: '8楼', value: '8' },
          { text: '9楼', value: '9' },
          { text: '10楼', value: '10' },
          { text: '11楼', value: '11' }
        ]"
        :filter-method="filterFloor"
      ></el-table-column>
      <el-table-column
        prop="user.deptName"
        label="科室"
        width="120"
        sortable
        :filters="getDeptFilter"
        :filter-method="filterDept"
      ></el-table-column>
      <el-table-column
        prop="user.ploName"
        label="业务主任"
        width="100"
      ></el-table-column>
      <el-table-column
        prop="station.extnNum"
        label="分机"
        width="100"
      ></el-table-column>
      <el-table-column prop="identity" label="工作安排"></el-table-column>
      <el-table-column prop="statusName" label="当前状态"></el-table-column>
      <el-table-column prop="statusTime" sortable label="持续时间" width="100">
        <template #default="scope">
          {{ durationFormatter(timestamp(scope.row.statusTime) + count) }}
        </template>
      </el-table-column>
      <el-table-column prop="ohtStatus" sortable label="接单状态" width="120">
        <template #default="scope">
          <el-tag
            :type="scope.row.ohtStatus === 1 ? 'success' : 'danger'"
            effect="plain"
            >{{ scope.row.ohtStatus === 1 ? '可接单' : '不可接单' }}</el-tag
          >
        </template>
      </el-table-column>
    </el-table>
    <template #footer="scope">
      <el-button size="small" @click="statusVisible = !visiable"
        >关闭</el-button
      >
    </template>
  </el-dialog>
</template>
<script setup>
import { ref, computed, onMounted, onBeforeUnmount, watch } from 'vue'
import { useOhtStore, useDictStore } from '@/stores'

// 定义 props
const props = defineProps({
  visiable: {
    type: Boolean,
    default: false
  }
})

// 导入 store
const ohtStore = useOhtStore()
const dictStore = useDictStore()

// 响应式数据
const statusVisible = ref(true)
const count = ref(0)
const addTimer = ref(null)
const floors = ref(['全部', 3, 4, 5, 6, 7, 8, 9, 10, 11])

// 对话框可见性
const rtVisiable = computed({
  get: () => props.visiable === statusVisible.value,
  set: (value) => {
    // 这里可以添加通知父组件的逻辑
  }
})

// 计算属性
const durationFormatter = computed(() => {
  return function (second) {
    return (
      Math.floor(second / 3600) +
      ':' +
      Math.floor((second % 3600) / 60) +
      ':' +
      Math.floor(second % 60)
    )
  }
})

const timestamp = computed(() => {
  return function (bTimeStamp) {
    const cTimeStamp = Math.round(new Date().getTime() / 1000)
    return cTimeStamp - bTimeStamp
  }
})

const getDeptFilter = computed(() => {
  const data = []
  const deptList = dictStore.dict.cyt_artical_resp_dept || []
  for (const item of deptList) {
    data.push({ text: item.codeval, value: item.codeval })
  }
  return data
})

// 方法
const counted = () => {
  addTimer.value = setInterval(() => {
    count.value = 0
    count.value++
  }, 1000)
}

const filterFloor = (value, row, column) => {
  return row.station.floorNum === value
}

const filterDept = (value, row) => {
  return row.user.deptName === value
}

// 生命周期
onMounted(() => {
  counted()
})

onBeforeUnmount(() => {
  if (addTimer.value) {
    clearInterval(addTimer.value)
  }
})
</script>
<style lang="less" scoped></style>

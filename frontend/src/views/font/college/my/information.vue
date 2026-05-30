<template>
  <div>
    <el-card class="box-card">
      <el-row :gutter="15">
        <el-col :span="10">
          <el-select
            v-model="queryInfo.courseId"
            @change="getStudyInfo"
            filterable
            remote
            size="small"
            reserve-keyword
            placeholder="请输入关键词查询课程"
            style="width: 100%"
            :remote-method="remoteCourses"
            :loading="searchLoading">
            <el-option
              v-for="item in courses"
              :key="item.courseId"
              :label="item.courseName"
              :value="item.courseId">
            </el-option>
          </el-select>
        </el-col>
        <el-col :span="10">
          <el-input placeholder="请选择科室组别" size="small" v-model="queryInfo.deptName" @focus="treeDialogVisiable = !treeDialogVisiable" readonly="true">
            <!-- <el-button slot="append" icon="el-icon-search" @click="getStudyInfo"></el-button> -->
          </el-input>
        </el-col>
        <el-col :span="4">
          <el-button type="primary" size="small" :disabled="queryInfo.courseId=='' || queryInfo.deptNum==''" @click="$global.downloadExcel('college/report/course/dowmload/infomation',  queryInfo, '课程学习记录明细BY人员.xlsx')">学习报表</el-button>
        </el-col>
      </el-row>
    <el-alert title="提示" type="info"
        description="请先选择需查看的课程名称(输入课程关键字可自动带出课程名称)，然后再选择查看的口径" show-icon :closable="false">
    </el-alert>
        <el-table :data="records" size="small" height="calc(100vh - 280px)" stripe style="width: 100%" @sort-change="tableSort($event)">
          <el-table-column prop="deptGroup" label="组别" sortable="custom"></el-table-column>
          <el-table-column prop="ploNum" label="工号" width="100px"></el-table-column>
          <el-table-column prop="ploName" label="姓名" width="100px"></el-table-column>
          <!-- <el-table-column prop="coin" label="学分" width="100px"></el-table-column>
          <el-table-column prop="hours" label="课时" width="90px"></el-table-column> -->
            <el-table-column prop="studyDate" label="学习日期" sortable="custom" show-overflow-tooltip></el-table-column>
          <el-table-column prop="studyComp" label="视频进度" sortable="custom" show-overflow-tooltip>
            <template #default="{ row }">
              {{(row.currTime / row.totalTime).toFixed(4)*100}}%
            </template>
          </el-table-column>
          <el-table-column prop="studyComp" label="在线学习"></el-table-column>
        <el-table-column prop="evalDate" label="评价" sortable="custom" width="120px" show-overflow-tooltip>
          <template #default="{ row }">
            <el-tag v-if="row.studyComp !== '未开始'" effect="plain" size="small" :type="row.ifEval=='需要'?row.evalComp =='完成'?'success':'danger':'success'"> {{ row.ifEval=='需要'?row.evalComp:'无需评价' }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="examComp" label="考试" sortable="custom" width="120px" show-overflow-tooltip>
          <template #default="{ row }">
            <el-tag v-if="row.studyComp !== '未开始'" effect="plain" size="small" :type="row.ifExam=='需要'?row.examComp =='完成'?'success':'danger':'success'"> {{ row.ifExam=='需要'?row.examComp :'无需考试' }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="coursePass" label="完成情况" sortable="custom" width="120px" show-overflow-tooltip>
          <template #default="{ row }">
            <el-tag v-if="row.studyComp !== '未开始'" effect="plain" size="small" :type="row.coursePass=='进行中'?'danger':'success'"> {{ row.coursePass }}</el-tag>
          </template>
        </el-table-column>
        </el-table>
        <!-- <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="queryInfo.pageNum" :page-sizes="[20, 40, 100, 200]" :page-size="20" layout="total, sizes, prev, pager, next, jumper" :total="total"></el-pagination> -->
    </el-card>
    <DepartmentSelect v-model="treeDialogVisiable" :multiple="false" @select="getTreeChecked" />
  </div>
</template>

<script setup>
import { ref, reactive, getCurrentInstance } from 'vue'
import DepartmentSelect from '@/components/DepartmentSelect.vue'

const { proxy } = getCurrentInstance()

// 课程查询参数
const courseQuery = reactive({
  orderType: ' desc',
  order: 'handleDate',
  queryType: 'courseName',
  query: '',
  courseType: null,
  pageSize: 20,
  pageNum: 1
})

// 搜索加载状态
const searchLoading = ref(false)

// 课程列表
const courses = ref([])

// 树形对话框可见性
const treeDialogVisiable = ref(false)

// 学习记录列表
const records = ref([])

// 查询参数
const queryInfo = reactive({
  courseId: '',
  deptName: '',
  deptNum: ''
})

// 远程搜索课程
const remoteCourses = (query) => {
  if (query !== '') {
    courseQuery.query = query
    proxy.$http.get('college/course', { params: courseQuery }).then(res => {
      if (res.code !== 200) return proxy.$message.error(res.msg)
      courses.value = res.data.list
    }).then(() => {
    })
  }
}

// 获取树形选择回调
const getTreeChecked = (data) => {
  queryInfo.deptName = data.label
  queryInfo.deptNum = data.id
  getStudyInfo()
}

// 获取学习信息
const getStudyInfo = async () => {
  if (queryInfo.courseId === '' || queryInfo.deptNum === '') return
  const res = await proxy.$http.get('college/report/course/infomation', { params: queryInfo })
  if (res.code !== 200) return proxy.$message.error(res.msg)
  records.value = res.data
}

// 表格排序
const tableSort = (event) => {
  // 排序逻辑
}
</script>

<style lang="less" scoped></style>

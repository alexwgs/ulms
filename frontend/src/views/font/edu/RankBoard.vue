<template>
  <div class="container">
    <t-row :gutter="10">
      <t-col :span="3">
        <t-loading :loading="loading">
        <h4 style="color: #fff; margin: 0">我的答题</h4>
        <div class="my-rank-area" v-if="myRank != undefined">
          <div class="my-rank-info">当前排名：{{ myRank.rank }}</div>
          <div class="my-rank-info">答题天数：{{ myRank.answerDays }} 天</div>
          <div class="my-rank-info">当前积分：{{ myRank.rightNum }} 分</div>
          <div class="my-rank-info">
            正确率：{{ (myRank.rightRate * 100).toFixed(2) }}%
          </div>
          <div class="my-rank-info">薄弱业务一：{{ myRank.weakPoint1 }}</div>
          <div class="my-rank-info">薄弱业务二：{{ myRank.weakPoint2 }}</div>
          <div class="my-rank-info">薄弱业务三：{{ myRank.weakPoint3 }}</div>
        </div>
        <div class="my-rank-area" v-else>
          <div class="my-rank-info">当前排名：N/A</div>
          <div class="my-rank-info">答题天数：0 天</div>
          <div class="my-rank-info">当前积分：0 分</div>
          <div class="my-rank-info">正确率：0 %</div>
          <div class="my-rank-info">薄弱业务一： -</div>
          <div class="my-rank-info">薄弱业务二： -</div>
          <div class="my-rank-info">薄弱业务三： -</div>
        </div>
        <div>
          <img class="rank-icon" src="../../../assets/img/edu/rank-icon.png" />
        </div>
        </t-loading>
      </t-col>
      <t-col :span="6">
        <t-loading :loading="loading">
          <div>
          <h4 style="color: #fff; margin: 0">学霸榜单</h4>
          <div style="margin: 15px 0 15px 0">
            <t-date-range-picker v-model="dateRange" size="small" format="YYYY-MM-DD" @change="dateChange" style="background-color: transparent; margin-right: 10px" :placeholder="['开始日期', '结束日期']"></t-date-range-picker>
            <t-button v-if="
              global?.user?.jobLevel?.indexOf('100,102,103,104,105') === -1
            " theme="primary" size="small" @click="
                downloadExcel(
                  'edu/brush/score/report/hum',
                  queryInfo,
                  '全员刷题情况.xlsx'
                )
                ">全员报表</t-button>
          </div>
          <div>
            <t-row :gutter="10">
              <!-- ({{(rankList[0].rightRate*100).toFixed(2)}}%) -->
              <t-col :span="4">
                <div class="top-rank-area">
                  <img class="rank-list-icon" src="../../../assets/img/edu/no1.png" />
                  <div class="top-rank-font" v-if="rankList[0] != undefined">
                    {{ rankList[0].ploName }}<br />{{
                      rankList[0].deptGroup === '-'
                        ? rankList[0].deptNum
                        : rankList[0].deptGroup
                    }}<br />{{ rankList[0].rightNum }}分
                  </div>
                </div>
              </t-col>
              <t-col :span="4">
                <div class="top-rank-area">
                  <img class="rank-list-icon" src="../../../assets/img/edu/no2.png" />
                  <div class="top-rank-font" v-if="rankList[1] != undefined">
                    {{ rankList[1].ploName }}<br />{{
                      rankList[1].deptGroup === '-'
                        ? rankList[1].deptNum
                        : rankList[1].deptGroup
                    }}<br />{{ rankList[1].rightNum }}分
                  </div>
                </div>
              </t-col>
              <t-col :span="4">
                <div class="top-rank-area">
                  <img class="rank-list-icon" src="../../../assets/img/edu/no3.png" />
                  <div class="top-rank-font" v-if="rankList[2] != undefined">
                    {{ rankList[2].ploName }}<br />{{
                      rankList[2].deptGroup === '-'
                        ? rankList[2].deptNum
                        : rankList[2].deptGroup
                    }}<br />{{ rankList[2].rightNum }}分
                  </div>
                </div>
              </t-col>
            </t-row>
            <t-row class="rank-head-line">
              <t-col :span="1">序号</t-col>
              <t-col :span="2">组别</t-col>
              <t-col :span="2">姓名</t-col>
              <t-col :span="2">积分</t-col>
              <t-col :span="2">正确率</t-col>
              <t-col :span="2">薄弱业务</t-col>
            </t-row>
            <div class="rank-area" v-if="rankList.length > 3">
              <t-row v-for="(item, index) of rankList.slice(3)" :key="item.ploNum" class="rank-area-row">
                <t-col :span="1">{{ index + 4 }}</t-col>
                <t-col :span="2">{{ item.deptGroup }}</t-col>
                <t-col :span="2">{{ item.ploName }}</t-col>
                <t-col :span="2">{{ item.rightNum }}</t-col>
                <t-col :span="2">{{ (item.rightRate * 100).toFixed(2) }}%</t-col>
                <t-col :span="3">{{ item.weakPoint1 }}</t-col>
              </t-row>
            </div>
            <div class="rank-area" v-else style="text-align: center">
              <h5>没有足够的数据</h5>
            </div>
          </div>
          <div></div>
        </div>
        </t-loading>
      </t-col>
      <t-col :span="3">
        <div>
          <h4 style="color: #fff; margin: 0">部门错题榜</h4>
          <t-row class="rank-head-line">
            <t-col :span="10">题目</t-col>
            <t-col :span="2">次数</t-col>
          </t-row>
          <div class="question-area">
            <t-row v-for="(item, index) of wrongQuestionList" :key="item.ploNum" style="
                box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
                padding: 5px;
                font-size: 14px;
                margin-bottom: 5px;
              ">
              <t-tooltip class="item" :content="'答案：' + item.answer" placement="top-start">
                <t-col :span="11">{{ index + 1 }}、{{ item.quesCode }}</t-col>
              </t-tooltip>
              <t-col style="text-align: center" :span="1"><t-tag theme="success" size="small" variant="light">
                  {{ item.passFlag }}</t-tag></t-col>
            </t-row>
          </div>
        </div>
      </t-col>
    </t-row>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { brushReportApi } from '@/api/edu/brushReport'
import { downloadExcel } from '@/utils/request'
import { MessagePlugin } from 'tdesign-vue-next'

const global = window.__POWERED_BY_QIANKUN__ ? window.$global : null

const formatDate = (date) => {
  return new Date(date).toISOString().split('T')[0]
}

const getDefaultDateRange = () => {
  const end = new Date()
  const start = new Date()
  start.setDate(start.getDate() - 6)
  return [formatDate(start), formatDate(end)]
}

const rankList = ref([])
const wrongQuestionList = ref([])
const dateRange = ref(getDefaultDateRange())
const queryInfo = reactive({
  begDate: '',
  endDate: '',
  orderBy: 'right_num desc,right_rate desc,total desc'
})
const myRank = ref({})
const loading = ref(false)

const getHumRank = async () => {
  loading.value = true
  if (dateRange.value.length > 0) {
    queryInfo.begDate = dateRange.value[0]
    queryInfo.endDate = dateRange.value[1]
  }
  const res = await brushReportApi.getHumBrushList(queryInfo)
  if (res.code !== 200) {
    MessagePlugin.error(res.msg)
    loading.value = false
    return
  }
  rankList.value = res.data
  // 获取我的信息
  myRank.value = rankList.value.filter(
    (e) => e.ploNum === global?.user?.ploNum
  )[0]
  loading.value = false
}

const getCenterWrongQuestion = async () => {
  const res = await brushReportApi.getCenterWrongQuestion()
  if (res.code !== 200) {
    MessagePlugin.error(res.msg)
    return
  }
  wrongQuestionList.value = res.data
}

const dateChange = () => {
  queryInfo.begDate = dateRange.value[0]
  queryInfo.endDate = dateRange.value[1]
  const begDate = new Date(queryInfo.begDate)
  const endDate = new Date(queryInfo.endDate)
  const diffDays = Math.ceil((endDate - begDate) / (1000 * 60 * 60 * 24))
  if (diffDays > 90) {
    MessagePlugin.error('最长不可间隔超过90天！')
    return
  }
  getHumRank()
}

onMounted(() => {
  getHumRank()
  getCenterWrongQuestion()
})
</script>

<style lang="less" scoped>
.rank-icon {
  max-height: 300px;
  max-width: 320px;
  height: 40%;
  bottom: 30px;
  position: fixed;
  left: 50px;
  z-index: -10;
}

.rank-head-line {
  margin-top: 15px;
  font-weight: 700;
  font-size: 18px;
  text-align: center;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
  padding: 5px;
  font-size: 14px;
  margin-bottom: 5px;
  height: 30px;
  color: #fff;
}

.top-rank-area {
  border-radius: 10px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
  padding: 10px;
  min-width: 135px;
}

.top-rank-font {
  display: inline-block;
  color: #fff;
  font-size: 14px;
  line-height: 1.6;
}

.rank-area {
  height: calc(100vh - 410px);
  border-radius: 10px;
background-color: rgba(233, 233, 233, 0.24);
  padding: 10px;
  overflow-y: auto;

  .rank-area-row {
    text-align: center;
    box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
    padding: 5px;
    font-size: 14px;
    width: 100%;
    white-space: nowrap;
    overflow: hidden;
  }
}

.question-area {
  height: calc(100vh - 270px);
  border-radius: 10px;
background-color: rgba(233, 233, 233, 0.24);
  padding: 10px;
  overflow-y: auto;
}

.t-range-editor--mini :deep(.t-range-input) {
background-color: transparent;
  color: var(--td-bg-color-container);
}

.my-rank-area {
  margin-top: 15px;
  height: calc(100vh - 510px);
  border-radius: 10px;
background-color: rgba(233, 233, 233, 0.24);
  padding: 10px;
  overflow-y: auto;

  .my-rank-info {
    box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
    padding: 5px;
    font-size: 14px;
    margin-bottom: 5px;
  }
}
</style>

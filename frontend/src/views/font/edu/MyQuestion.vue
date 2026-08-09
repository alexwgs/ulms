<template>
  <t-loading :loading="loading">
    <div class="container">
    <t-row :gutter="10">
      <t-col :span="9">
        <div v-if="currentType === 'wrong'">
          <div class="title">我的错题</div>
          <div class="main-area">
            <div v-for="(item, index) of wrongList" :key="item.journo" class="item-border">
              <div class="ques-stem">
                {{ index + 1 }}、{{ item.question.quesStem }}
              </div>
              <div class="answer">
                答案：{{ item.question.answer }}<br />知识库名：{{
                  item.question.knowledge
                }}
              </div>
            </div>
          </div>
        </div>
        <div v-else-if="currentType === 'collect'">
          <div class="title">我的收藏</div>
          <div class="main-area">
            <div v-for="(item, index) of collectList" :key="item.journo" class="item-border">
              <div class="ques-stem">
                {{ index + 1 }}、{{ item.question?.quesStem }}
              </div>
              <div class="answer">
                答案：{{ item.question?.answer }}<br />知识库名：{{
                  item.question?.knowledge
                }}<t-button variant="outline" class="uncollect-botton" size="small" style="float: right"
                  @click="uncollect(item.journo)"><template #icon><DynamicIcon name="star" /></template></t-button>
              </div>
            </div>
          </div>
        </div>
      </t-col>
      <t-col :span="3">
        <div class="slogan">
          <div style="padding-top: 40px">
            <t-button theme="primary" class="mybutton" @click="changeType('wrong')" shape="round"><template #icon><DynamicIcon name="bookmark" /></template>我 的 错
              题</t-button>
          </div>
          <div style="padding-top: 40px">
            <t-button theme="primary" class="mybutton" @click="changeType('collect')" shape="round"><template #icon><DynamicIcon name="star" /></template>我 的 收
              藏</t-button>
          </div>
          <img class="brush-icon" src="../../../assets/img/edu/my-questions-logo.png" />
        </div>
      </t-col>
    </t-row>
    </div>
  </t-loading>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { brushApi } from '@/api/edu/brush'
import { questionCollectApi } from '@/api/edu/questionCollect'
import { MessagePlugin } from 'tdesign-vue-next'
import { BookmarkIcon, StarIcon } from 'tdesign-icons-vue-next'

const loading = ref(false)
const collectList = ref([])
const wrongList = ref([])
const currentType = ref('wrong')

const getCollectList = async () => {
  const res = await questionCollectApi.getMyCollect()
  if (res.code !== 200) return MessagePlugin.error(res.msg)
  collectList.value = res.data
}

const getWrongQuestion = async () => {
  const res = await brushApi.getWrongQuestion()
  if (res.code !== 200) return MessagePlugin.error(res.msg)
  wrongList.value = res.data
}

const uncollect = async (journo) => {
  const res = await questionCollectApi.uncollectQuestion(journo)
  if (res.code !== 200) return MessagePlugin.error(res.msg)
  collectList.value = res.data
  getCollectList()
}

const changeType = (type) => {
  currentType.value = type
}

onMounted(() => {
  getWrongQuestion()
  getCollectList()
})
</script>

<style lang="less" scoped>
.container {
  height: 100%;
}

.brush-icon {
  max-height: 330px;
  max-width: 420px;
  height: 40%;
  bottom: 10%;
  position: fixed;
  right: 50px;
  z-index: -10;
}

.slogan {
  text-align: center;
}

.mybutton {
  color: #31b97f;
background-color: var(--td-bg-color-container);
  border-color: var(--td-bg-color-container);
  font-size: 20px;
}

.title {
  font-size: 22px;
  font-weight: 500;
  color: #fff;
  line-height: 15px;
  border-bottom: #fff 1px dotted;
  padding-top: 15px;
  padding-bottom: 15px;
}

.main-area {
  height: calc(100vh - 220px);
  overflow-y: auto;

  li {
    margin-bottom: 10px;
    line-height: 2;
  }

  .ques-stem {
    font-size: 16px;
  }

  .answer {
    font-size: 16px;
    padding-left: 25px;
  }

  .uncollect-botton {
    color: #fee300;
background-color: transparent;
    border-color: #ffed44;
    margin-right: 10px;
  }
}

.item-border {
  line-height: 2;
  margin: 10px;
  padding: 10px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
background-color: rgba(255, 255, 255, 0.1);
  border-radius: 4px;
}
</style>

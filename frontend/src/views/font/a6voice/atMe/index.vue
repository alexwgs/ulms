<template>
  <t-card class="management-card">
    <div class="sub-page-header">
      <t-button theme="default" variant="text" @click="router.back()">
        
      返回</t-button>
      <span class="sub-page-title">回复我的消息</span>
    </div>
    <t-divider></t-divider>
    <div class="text item">
      <t-form :data="queryInfo" label-width="80px" colon class="filter-form">
        <t-row :gutter="[24, 24]">
          <t-col :span="4">
            <t-form-item label="回复类型" name="messageType">
              <t-select size="small" v-model="queryInfo.messageType" @change="getMessageList"
                placeholder="全部">
                <t-option label="全部" value=""></t-option>
                <t-option v-for="item in dictStore.getDictByNames('cyt_message_type', 1)" :key="item.id"
                  :label="item.codeval" :value="parseInt(item.code)">
                </t-option>
              </t-select>
            </t-form-item>
          </t-col>
          <t-col :span="4">
            <t-form-item label="已读状态" name="isRead">
              <t-select size="small" v-model="queryInfo.isRead" @change="getMessageList"
                placeholder="全部">
                <t-option v-for="item in dictStore.getDictByNames('cyt_message_is_read', 1)" :key="item.id"
                  :label="item.codeval" :value="parseInt(item.code)">
                </t-option>
              </t-select>
            </t-form-item>
          </t-col>
          <t-col :span="3" class="operation-container">
            <t-button theme="primary" size="small" @click="readAll()">一键已读</t-button>
          </t-col>
        </t-row>
      </t-form>
      <CustomTable rowKey="id" :data="messages" stripe style="width: 100%" size="small" height="calc(100vh - 340px)">
        <TableColumn colKey="messageType" label="类型" width="100">
          <template #default="scope">
            <t-tag size="small" v-for="item in dictStore.getDictByNames('cyt_message_type', 1)" :key="item.code"
              :theme="scope.row.messageType == 1 ? 'danger' : 'default'"
              :style="scope.row.messageType == item.code ? '' : 'display:none'" variant="light">{{ item.codeval }}
            </t-tag>
          </template>
        </TableColumn>
        <TableColumn colKey="article.title" label="标题"></TableColumn>
        <TableColumn colKey="fUser.ploName" label="来自" width="150">
          <template #default="scope">
            {{
              scope.row.fUser == null
                ? scope.row.fromUser === 'system'
                  ? '系统'
                  : '匿名'
                : scope.row.fUser.ploName
            }}
          </template>
        </TableColumn>
        <TableColumn colKey="dateTime" label="发布日期" width="160"></TableColumn>
        <TableColumn label="操作">
          <template #default="scope">
            <t-button v-if="scope.row.messageType != 4" theme="primary" size="small"
              @click="answerComment(scope.$index, scope.row)">回复TA</t-button>
            <div v-else>
              {{ getDictLabel('cyt_artical_status', scope.row.messageId) }}
              <t-button theme="primary" size="small" @click="read(scope.$index, scope.row)">知道了</t-button>
            </div>
          </template>
        </TableColumn>
      </CustomTable>
      <t-pagination @current-change="handleCurrentChange" v-model="currentPage"
        :page-size="queryInfo.pageSize" :total="total">
      </t-pagination>
    </div>
    <t-dialog header="评论及回复" v-model:visible="answerDialogVisible" width="40%" :before-close="answerHandleClose">
      <section class="comment">
        <div class="comment-avatar">
          <div class="avartar-box-mini">
            <img :src="fUser == null ? fsURL + 'upload/getFile/avatar/avatar.png' : fsURL + fUser.avatar" />
          </div>
        </div>
        <div class="comment-info">
          <span class="comment-header" v-if="fUser == null">匿名</span>
          <span class="comment-header" v-else>{{ fUser.ploName }}/{{ fUser.ploNum }}</span>
          <div class="comment-time">{{ comment.dateTime }}</div>
        </div>
        <SafeHtml class="comment-content" :html="comment.content" />
      </section>
      <t-textarea :rows="3" placeholder="请输入回复内容" v-model="answerForm.content">
      </t-textarea>
      <div style="width: 100%; text-align: right; padding-top: 5px">
        <t-checkbox v-model="anonFlag">匿名</t-checkbox>
        <t-button size="small" theme="primary" @click="replay">回复</t-button>
      </div>
      <template #footer>
        <t-button size="small" @click="answerDialogVisible = false">关 闭</t-button>
      </template>
    </t-dialog>
  </t-card>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { MessagePlugin } from 'tdesign-vue-next'
import { httpInstance } from '@/utils/request'
import { useDictStore } from '@/stores'
import { sanitizeHtml } from '@/utils/tools'

const router = useRouter()
const dictStore = useDictStore()

const fsURL = import.meta.env.VITE_FILE_BASE_URL

const messages = ref([])
const currentPage = ref(1)
const total = ref(0)
const answerDialogVisible = ref(false)
const anonFlag = ref(false)
const comment = ref({})
const fUser = ref({})

const queryInfo = reactive({
  messageType: '',
  isRead: 0,
  querytype: '',
  query: '',
  pageSize: 20,
  pageNum: 1,
  order: 'id',
  orderType: 'desc'
})

const answerForm = reactive({
  messageType: '',
  articleId: '',
  commentId: '',
  toUser: '',
  anonFlag: 0,
  content: ''
})

const getDictLabel = (dictName, value) => {
  const dictList = dictStore.getDictByNames(dictName, 1)
  const item = dictList.find(d => d.code == value)
  return item ? item.codeval : ''
}

onMounted(() => {
  getMessageList()
})

const getMessageList = async () => {
  try {
    const res = await httpInstance.get('cyt/message/list', {
      params: queryInfo
    })
    if (res.code !== 200) {
      MessagePlugin.error(res.msg)
      return
    }
    messages.value = res.data.list
    total.value = res.data.total
  } catch (error) {
    MessagePlugin.error('获取消息列表失败')
    console.error(error)
  }
}

const handleCurrentChange = (page) => {
  queryInfo.pageNum = page
  getMessageList()
}

const answerComment = async (index, row) => {
  fUser.value = row.fUser
  answerForm.messageType = row.messageType
  answerForm.articleId = row.articleId
  answerForm.commentId = row.messageId
  answerForm.toUser = row.fromUser
  answerForm.anonFlag = anonFlag.value ? 1 : 0
  answerDialogVisible.value = !answerDialogVisible.value

  try {
    if (row.messageType === 1) {
      const res = await httpInstance.get(`cyt/comment/${row.messageId}`)
      if (res.code !== 200) {
        MessagePlugin.error(res.msg)
        return
      }
      comment.value = res.data
      comment.value.content = sanitizeHtml(res.data.content)
    } else if (row.messageType === 2) {
      const res = await httpInstance.get(`cyt/reply/${row.messageId}`)
      if (res.code !== 200) {
        MessagePlugin.error(res.msg)
        return
      }
      comment.value = res.data
      comment.value.content = sanitizeHtml(res.data.content)
    }

    const readRes = await httpInstance.put(`cyt/message/read/${row.id}`)
    if (readRes.code !== 200) {
      MessagePlugin.error(readRes.msg)
      return
    }
    getMessageList()
  } catch (error) {
    MessagePlugin.error('获取评论内容失败')
    console.error(error)
  }
}

const read = async (index, row) => {
  try {
    const res = await httpInstance.put(`cyt/message/read/${row.id}`)
    if (res.code !== 200) {
      MessagePlugin.error(res.msg)
      return
    }
    getMessageList()
  } catch (error) {
    MessagePlugin.error('标记已读失败')
    console.error(error)
  }
}

const readAll = async () => {
  try {
    const res = await httpInstance.put('cyt/message/read/all')
    if (res.code !== 200) {
      MessagePlugin.error(res.msg)
      return
    }
    MessagePlugin.success(res.msg)
    getMessageList()
  } catch (error) {
    MessagePlugin.error('一键已读失败')
    console.error(error)
  }
}

const answerHandleClose = () => {
  comment.value = {
    messageType: '',
    articleId: '',
    commentId: '',
    toUser: '',
    anonFlag: 0,
    content: ''
  }
  answerDialogVisible.value = false
}

const replay = async () => {
  try {
    if (answerForm.messageType === 1 || answerForm.messageType === 2) {
      const res = await httpInstance.post('cyt/reply/', answerForm)
      if (res.code !== 200) {
        MessagePlugin.error(res.msg)
        return
      }
      MessagePlugin.success(res.msg)
    }
    answerDialogVisible.value = false
    getMessageList()
  } catch (error) {
    MessagePlugin.error('回复失败')
    console.error(error)
  }
}
</script>

<style lang="less" scoped>
.management-card {
  height: calc(100vh - 130px);
}

.table-filter {
  padding: 0;

  span {
    font-size: 14px;
    margin-left: 20px;
  }
}

.comment {
  display: block;
  margin-top: 10px;
  padding: 20px 0 20px 34px;
  border-bottom: 1px solid var(--td-component-stroke);
  position: relative;

  .comment-avatar {
    position: absolute;
    left: 0;
    margin-bottom: 20px;
    display: table;
    table-layout: fixed;
    width: 100%;
    -webkit-box-sizing: border-box;
    box-sizing: border-box;
    padding-left: 0;
  }

  .comment-info {
    position: relative;

    .comment-header {
      margin-bottom: 12px;
      position: relative;
      font-size: 14px;
      line-height: 24px;
    }

    .comment-time {
      position: absolute;
      right: 0;
      top: 0;
      font-size: 12px;
      line-height: 24px;
      color: var(--td-text-color-secondary);
    }
  }

  .comment-content {
    font-size: 14px;
    margin-top: 0;
    line-height: 24px;
    word-wrap: break-word;
    color: #333;
  }
}
</style>

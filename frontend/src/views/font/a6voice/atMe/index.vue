<template>
  <el-card class="box-card">
    <el-page-header @back="() => router.back()" content="回复我的消息">
    </el-page-header>
    <el-divider></el-divider>
    <div class="text item">
      <div class="table-filter">
        <span>回复类型
          <el-select style="width: 200px;" size="small" v-model="queryInfo.messageType" @change="getMessageList"
            placeholder="请选择,默认不限制">
            <el-option label="全部" value=""></el-option>
            <el-option v-for="item in dictStore.getDictByNames('cyt_message_type', 1)" :key="item.id"
              :label="item.codeval" :value="parseInt(item.code)">
            </el-option>
          </el-select>
        </span>
        <span>已读状态
          <el-select style="width: 200px;" size="small" v-model="queryInfo.isRead" @change="getMessageList"
            placeholder="请选择,默认未读">
            <el-option v-for="item in dictStore.getDictByNames('cyt_message_is_read', 1)" :key="item.id"
              :label="item.codeval" :value="parseInt(item.code)">
            </el-option>
          </el-select>
        </span>
        <span>
          <el-button type="primary" size="small" @click="readAll()">一键已读</el-button>
        </span>
      </div>
      <el-table :data="messages" stripe style="width: 100%" size="small" height="calc(100vh - 340px)">
        <el-table-column prop="messageType" label="类型" width="100">
          <template #default="scope">
            <el-tag size="small" v-for="item in dictStore.getDictByNames('cyt_message_type', 1)" :key="item.code"
              :type="scope.row.messageType == 1 ? 'danger' : 'info'"
              :style="scope.row.messageType == item.code ? '' : 'display:none'" effect="plain">{{ item.codeval }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="artical.title" label="标题"></el-table-column>
        <el-table-column prop="fUser.ploName" label="来自" width="150">
          <template #default="scope">
            {{
              scope.row.fUser == null
                ? scope.row.fromUser === 'system'
                  ? '系统'
                  : '匿名'
                : scope.row.fUser.ploName
            }}
          </template>
        </el-table-column>
        <el-table-column prop="dateTime" label="发布日期" width="160"></el-table-column>
        <el-table-column label="操作">
          <template #default="scope">
            <el-button v-if="scope.row.messageType != 4" type="primary" size="small"
              @click="answerComment(scope.$index, scope.row)">回复TA</el-button>
            <div v-else>
              {{ getDictLabel('cyt_artical_status', scope.row.messageId) }}
              <el-button type="primary" size="small" @click="read(scope.$index, scope.row)">知道了</el-button>
            </div>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination @current-change="handleCurrentChange" v-model:current-page="currentPage"
        :page-size="queryInfo.pageSize" layout="total, prev, pager, next, jumper" :total="total">
      </el-pagination>
    </div>
    <el-dialog title="评论及回复" v-model="answerDialogVisible" width="40%" :before-close="answerHandleClose">
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
        <div class="comment-content" v-html="comment.content"></div>
      </section>
      <el-input type="textarea" :rows="3" placeholder="请输入回复内容" v-model="answerForm.content">
      </el-input>
      <div style="width: 100%; text-align: right; padding-top: 5px">
        <el-checkbox v-model="anonFlag">匿名</el-checkbox>
        <el-button size="small" type="primary" @click="replay">回复</el-button>
      </div>
      <template #footer>
        <el-button size="small" @click="answerDialogVisible = false">关 闭</el-button>
      </template>
    </el-dialog>
  </el-card>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { httpInstance } from '@/utils/request'
import { useDictStore } from '@/stores'

const router = useRouter()
const dictStore = useDictStore()

const fsURL = import.meta.env.VITE_FILE_MANAGE_BASE || import.meta.env.VITE_API_BASE_URL

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
  articalId: '',
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
      ElMessage.error(res.msg)
      return
    }
    messages.value = res.data.list
    total.value = res.data.total
  } catch (error) {
    ElMessage.error('获取消息列表失败')
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
  answerForm.articalId = row.articalId
  answerForm.commentId = row.messageId
  answerForm.toUser = row.fromUser
  answerForm.anonFlag = anonFlag.value ? 1 : 0
  answerDialogVisible.value = !answerDialogVisible.value

  try {
    if (row.messageType === 1) {
      const res = await httpInstance.get(`cyt/comment/${row.messageId}`)
      if (res.code !== 200) {
        ElMessage.error(res.msg)
        return
      }
      comment.value = res.data
    } else if (row.messageType === 2) {
      const res = await httpInstance.get(`cyt/reply/${row.messageId}`)
      if (res.code !== 200) {
        ElMessage.error(res.msg)
        return
      }
      comment.value = res.data
    }

    const readRes = await httpInstance.put(`cyt/message/read/${row.id}`)
    if (readRes.code !== 200) {
      ElMessage.error(readRes.msg)
      return
    }
    getMessageList()
  } catch (error) {
    ElMessage.error('获取评论内容失败')
    console.error(error)
  }
}

const read = async (index, row) => {
  try {
    const res = await httpInstance.put(`cyt/message/read/${row.id}`)
    if (res.code !== 200) {
      ElMessage.error(res.msg)
      return
    }
    getMessageList()
  } catch (error) {
    ElMessage.error('标记已读失败')
    console.error(error)
  }
}

const readAll = async () => {
  try {
    const res = await httpInstance.put('cyt/message/read/all')
    if (res.code !== 200) {
      ElMessage.error(res.msg)
      return
    }
    ElMessage.success(res.msg)
    getMessageList()
  } catch (error) {
    ElMessage.error('一键已读失败')
    console.error(error)
  }
}

const answerHandleClose = () => {
  comment.value = {
    messageType: '',
    articalId: '',
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
        ElMessage.error(res.msg)
        return
      }
      ElMessage.success(res.msg)
    }
    answerDialogVisible.value = false
    getMessageList()
  } catch (error) {
    ElMessage.error('回复失败')
    console.error(error)
  }
}
</script>

<style lang="less" scoped>
.box-card {
  height: calc(100vh - 130px);
}

.table-filter {
  padding: 10px;

  span {
    font-size: 14px;
    margin-left: 20px;
  }
}

.comment {
  display: block;
  margin-top: 10px;
  padding: 20px 0 20px 34px;
  border-bottom: 1px solid #eee;
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
      color: #999;
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

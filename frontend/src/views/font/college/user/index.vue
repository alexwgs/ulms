<template>
  <el-row style="margin-top: 15px; box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1)">
    <el-col :span="24">
      <div class="page-list">
        <div class="list-fl">
          <div class="page-tit">排行榜</div>
          <ul class="list-tit">
            <li class="active"><i></i>月度学霸排行榜</li>
          </ul>
        </div>
        <ul class="list-sty">
          <div v-for="item in userList" :key="item.ploNum">
            <li>
              <i class="sty-icon1"></i>
              <span title="占位符" class="db fl"
                ><img :src="fsURL + item.user.avatar" title="占位符"
              /></span>
              <div class="sty-tit" style="padding-left: 10px">
                <b
                  href=""
                  target="_blank"
                  :title="item.user.ploName"
                  class="text-trim text-name"
                  >{{ item.user.ploName }}</b
                >
                <div class="text-trim" :title="item.user.deptName">
                  部门：{{ item.user.deptName }}
                </div>
                <div class="text-trim">
                  学分：<span class="f-color">{{ item.point }}</span>
                </div>
              </div>
            </li>
          </div>
        </ul>
      </div>
    </el-col>
  </el-row>
</template>

<script setup>
const props = defineProps({
  userList: {
    type: Array,
    default: () => []
  }
})

const fsURL = import.meta.env.VITE_FILE_BASE_URL
</script>

<style lang="less" scoped>
.text-trim {
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}
.page-list {
  width: 100%;
  background-color: #fff;
  .list-fl {
    width: 12%;
    height: 264px;
    background: url(@/assets/img/edu/list-bg-2.png);
    float: left;
    .page-tit {
      line-height: 24px;
      color: #fff;
      text-align: center;
      margin-top: 20px;
      font-weight: bold;
    }
    .list-tit {
      margin-top: 99px;
      list-style: none;
      padding: 0;
    }
    .list-tit li {
      cursor: pointer;
      font-size: 12px;
      color: #9b2b23;
      text-align: center;
      position: relative;
      margin: 0 auto;
      height: 20px;
      margin-bottom: 25px;
      line-height: 12px;
      list-style: none;
    }
  }
}

.db {
  display: block;
}
.fl {
  float: left;
}
.list-sty {
  width: 87%;
  display: inline-block;
  background: #fbfbfb;
  padding-top: 3px;
  box-sizing: border-box;
  padding-left: 27px;
  height: 240px;
  overflow: hidden;
  img {
    width: 70px;
    height: 70px;
    border-radius: 50%;
  }
  li {
    position: relative;
    width: 170px;
    margin-top: 30px;
    height: 75px;
    float: left;
    margin-right: 20px;
    list-style: none;
    a {
      margin-right: 15px;
    }
  }
  i {
    display: block;
    position: absolute;
    width: 33px;
    height: 25px;
    top: -13px;
    left: -3px;
  }
  .sty-tit {
    width: 84px;
    float: left;
    font-size: 12px;
    line-height: 25px;
    .text-name {
      font-size: 14px;
      text-decoration: none;
      color: #000;
      font-weight: bold;
    }
  }
}
</style>

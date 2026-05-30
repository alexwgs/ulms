<template>
  <div class="content">
    <el-result
      class="result"
      icon="error"
      title="404"
      sub-title="页面走丢了！请重新确认访问地址是否正确！"
    >
      <template #extra>
        <div class="operation-row">
          <el-button type="primary" @click="back">
            没有查找到该页面,返回首页
          </el-button>
        </div>
      </template>
    </el-result>
  </div>
</template>

<script>
import { defineComponent } from 'vue';
import { useRouter } from 'vue-router';

export default defineComponent({
  setup() {
    const router = useRouter();
    const back = () => {
      // warning： Go to the node that has the permission
      router.push('/home');
    };
    return {
      back,
    };
  },
});
</script>

<style scoped lang="less">
.content {
  // padding-top: 100px;
  position: absolute;
  top: 50%;
  left: 50%;
  margin-left: -95px;
  margin-top: -121px;
  text-align: center;
}
</style>

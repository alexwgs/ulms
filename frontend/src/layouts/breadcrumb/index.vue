<!-- <template>
  <el-breadcrumb class="container-breadcrumb" separator="/">
    <el-breadcrumb-item>
      <el-icon><grid /></el-icon>
    </el-breadcrumb-item>
    <el-breadcrumb-item v-for="item in items" :key="item">
      {{ item }}
    </el-breadcrumb-item>
  </el-breadcrumb>
</template> -->

<!-- <template>
  <el-page-header>
    <template #breadcrumb>
      <el-breadcrumb class="container-breadcrumb" separator="/">
        <el-breadcrumb-item>
          <el-icon><grid /></el-icon>
        </el-breadcrumb-item>
        <el-breadcrumb-item v-for="item in items" :key="item">
          {{ item }}
        </el-breadcrumb-item>
      </el-breadcrumb>
    </template>
    <template #content>
      <span class="text-large font-600 mr-3"> Title </span>
    </template>
  </el-page-header>
</template> -->
<script setup>
import { Grid } from '@element-plus/icons-vue'

defineProps({
  items: {
    type: Array,
    default: () => []
  }
})
</script>

<style scoped lang="less">
.container-breadcrumb {
  margin: 16px 0;

  :deep(.el-breadcrumb__item) {
    .el-breadcrumb__inner {
      color: var(--el-text-color-secondary);

      &:hover {
        color: var(--el-color-primary);
        cursor: pointer;
      }
    }

    &:last-child {
      .el-breadcrumb__inner {
        color: var(--el-text-color-regular);
        font-weight: 500;
      }
    }
  }
}
</style>

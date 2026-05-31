<!-- <template>
  <t-breadcrumb class="container-breadcrumb" separator="/">
    <t-breadcrumb-item>
      <t-icon><grid /></t-icon>
    </t-breadcrumb-item>
    <t-breadcrumb-item v-for="item in items" :key="item">
      {{ item }}
    </t-breadcrumb-item>
  </t-breadcrumb>
</template> -->

<!-- <template>
  <div>
    <template #breadcrumb>
      <t-breadcrumb class="container-breadcrumb" separator="/">
        <t-breadcrumb-item>
          <t-icon><grid /></t-icon>
        </t-breadcrumb-item>
        <t-breadcrumb-item v-for="item in items" :key="item">
          {{ item }}
        </t-breadcrumb-item>
      </t-breadcrumb>
    </template>
    <template #content>
      <span class="text-large font-600 mr-3"> Title </span>
    </template>
  </div>
</template> -->
<script setup>
// Grid icon no longer needed — using TDesign

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

  :deep(.t-breadcrumb__item) {
    .t-breadcrumb__inner {
      color: var(--td-text-color-secondary);

      &:hover {
        color: var(--td-brand-color);
        cursor: pointer;
      }
    }

    &:last-child {
      .t-breadcrumb__inner {
        color: var(--td-text-color-primary);
        font-weight: 500;
      }
    }
  }
}
</style>

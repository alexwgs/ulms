<template>
  <div style="height: 100%">
    <t-card class="box-card">
      <t-row :gutter="15">
        <t-col :span="4">
          <CourseTypeConfig ref="courseTypeRef" />
        </t-col>
        <t-col :span="8">
          <EvaluateTempleteConfig ref="evaluateRef" />
        </t-col>
      </t-row>
    </t-card>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import CourseTypeConfig from './components/CourseTypeConfig.vue'
import EvaluateTempleteConfig from './components/EvaluateTempleteConfig.vue'

const courseTypeRef = ref(null)
const evaluateRef = ref(null)
</script>

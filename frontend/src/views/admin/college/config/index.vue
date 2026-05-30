<template>
  <div style="height: 100%">
    <el-card class="box-card">
      <el-row :gutter="15">
        <el-col :span="8">
          <CourseTypeConfig ref="courseTypeRef" />
        </el-col>
        <el-col :span="16">
          <EvaluateTempleteConfig ref="evaluateRef" />
        </el-col>
      </el-row>
    </el-card>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import CourseTypeConfig from './components/CourseTypeConfig.vue'
import EvaluateTempleteConfig from './components/EvaluateTempleteConfig.vue'

const courseTypeRef = ref(null)
const evaluateRef = ref(null)
</script>

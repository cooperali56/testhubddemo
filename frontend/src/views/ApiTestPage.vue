<template>
  <div class="api-test-page">
    <div class="page-header">
      <h2>前后端连接测试</h2>
      <p>用于测试前端和后端的连接状态</p>
    </div>
    
    <a-card>
      <api-test />
    </a-card>
  </div>
</template>

<script setup lang="ts">
import ApiTest from '@/components/ApiTest.vue'
</script>

<style scoped>
.api-test-page {
  padding: 24px;
}

.page-header {
  margin-bottom: 24px;
}

.page-header h2 {
  font-size: 24px;
  font-weight: 600;
  margin-bottom: 8px;
}

.page-header p {
  color: rgba(0, 0, 0, 0.45);
}
</style> 
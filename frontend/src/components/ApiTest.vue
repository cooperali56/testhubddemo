<template>
  <div class="api-test">
    <h2>API 连接测试</h2>
    <a-space direction="vertical" style="width: 100%">
      <a-alert v-if="error" type="error" :message="error" show-icon />
      <a-alert v-if="success" type="success" :message="success" show-icon />
      <a-button type="primary" @click="testConnection" :loading="loading">
        测试后端连接
      </a-button>
      <a-card v-if="result" title="响应结果">
        <pre>{{ JSON.stringify(result, null, 2) }}</pre>
      </a-card>
    </a-space>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import axios from 'axios'

const loading = ref(false)
const error = ref('')
const success = ref('')
const result = ref<any>(null)

const testConnection = async () => {
  loading.value = true
  error.value = ''
  success.value = ''
  result.value = null
  
  try {
    // 测试后端连接
    const response = await axios.get('/api/test')
    result.value = response.data
    success.value = '后端连接成功！'
  } catch (err: any) {
    console.error('API测试失败:', err)
    error.value = `连接失败: ${err.message}`
    
    if (err.response) {
      result.value = {
        status: err.response.status,
        statusText: err.response.statusText,
        data: err.response.data
      }
    }
  } finally {
    loading.value = false
  }
}
</script>

<style scoped>
.api-test {
  max-width: 800px;
  margin: 0 auto;
  padding: 20px;
}

pre {
  background-color: #f5f5f5;
  padding: 10px;
  border-radius: 4px;
  overflow: auto;
}
</style> 
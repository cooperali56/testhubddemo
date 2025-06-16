<template>
  <div class="not-found">
    <a-result
      status="404"
      title="404"
      sub-title="对不起，您访问的页面不存在。"
    >
      <template #extra>
      <a-button type="primary" @click="goHome">返回首页</a-button>
      </template>
    </a-result>
  </div>
</template>

<script setup lang="ts">
import { useRouter } from 'vue-router'

const router = useRouter()

function goHome() {
  router.push('/')
}
</script>

<style scoped>
.not-found {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
}
</style> 
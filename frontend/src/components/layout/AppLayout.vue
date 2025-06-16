<template>
  <a-layout class="layout">
    <!-- 侧边栏 -->
    <a-layout-sider
      v-model:collapsed="collapsed"
      :trigger="null"
      collapsible
      width="256"
    >
      <div class="logo">
        <h1 v-if="!collapsed">TestHub</h1>
        <h1 v-else>TH</h1>
      </div>
        <a-menu
          v-model:selectedKeys="selectedKeys"
        mode="inline"
        theme="dark"
      >
        <a-menu-item key="home">
          <template #icon>
            <home-outlined />
          </template>
          <router-link to="/home">首页</router-link>
        </a-menu-item>
        <!-- 仅管理员可见的用户管理菜单 -->
        <a-menu-item v-if="isAdmin" key="user">
          <template #icon>
            <user-outlined />
          </template>
          <router-link to="/user">用户管理</router-link>
        </a-menu-item>
        <a-menu-item key="testcase">
          <template #icon>
            <file-outlined />
          </template>
          <router-link to="/testcase">测试用例</router-link>
        </a-menu-item>
        <a-menu-item key="report">
          <template #icon>
            <bar-chart-outlined />
          </template>
          <router-link to="/report">测试报告</router-link>
        </a-menu-item>
        <a-menu-item key="api-test">
          <template #icon>
            <api-outlined />
          </template>
          <router-link to="/api-test">API连接测试</router-link>
        </a-menu-item>
      </a-menu>
    </a-layout-sider>
    
    <!-- 内容区 -->
    <a-layout>
      <!-- 头部 -->
      <a-layout-header class="header">
        <menu-unfold-outlined
          v-if="collapsed"
          class="trigger"
          @click="() => (collapsed = !collapsed)"
        />
        <menu-fold-outlined
          v-else
          class="trigger"
          @click="() => (collapsed = !collapsed)"
        />
        
        <div class="right">
        <a-dropdown>
            <a class="user-dropdown">
              <a-avatar :size="32" class="avatar">
                {{ userInfo?.nickname?.charAt(0) }}
            </a-avatar>
              <span class="username">{{ userInfo?.nickname }}</span>
          </a>
          <template #overlay>
            <a-menu>
                <a-menu-item key="0">
                <user-outlined />
                  <router-link to="/profile">个人中心</router-link>
              </a-menu-item>
              <a-menu-divider />
                <a-menu-item key="1" @click="handleLogout">
                <logout-outlined />
                退出登录
              </a-menu-item>
            </a-menu>
          </template>
        </a-dropdown>
      </div>
    </a-layout-header>
      
      <!-- 主内容区 -->
      <a-layout-content class="content">
        <router-view v-slot="{ Component }">
          <transition name="fade" mode="out-in">
            <component :is="Component" />
          </transition>
        </router-view>
      </a-layout-content>
      
      <!-- 页脚 -->
      <a-layout-footer class="footer">
        TestHub &copy; {{ new Date().getFullYear() }} All Rights Reserved
      </a-layout-footer>
    </a-layout>
  </a-layout>
</template>

<script setup lang="ts">
import { ref, computed, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { message } from 'ant-design-vue'
import {
  HomeOutlined,
  FileOutlined,
  UserOutlined,
  LogoutOutlined,
  MenuFoldOutlined,
  MenuUnfoldOutlined,
  BarChartOutlined,
  ApiOutlined
} from '@ant-design/icons-vue'
import { useUserStore } from '@/store/modules/user'

const route = useRoute()
const router = useRouter()
const userStore = useUserStore()

// 侧边栏收起状态
const collapsed = ref(false)

// 用户信息
const userInfo = computed(() => userStore.userInfo)

// 当前选中的菜单项
const selectedKeys = computed(() => {
  const path = route.path
  if (path.startsWith('/testcase')) {
    return ['testcase']
  }
  if (path.startsWith('/home')) {
    return ['home']
  }
  if (path.startsWith('/report')) {
    return ['report']
  }
  return [path.split('/')[1]]
})

// 退出登录
const handleLogout = async () => {
  try {
    await userStore.logout()
    message.success('已退出登录')
    router.push('/login')
  } catch (error) {
    console.error('退出登录失败', error)
  }
}

// 获取用户信息
onMounted(async () => {
  if (!userStore.userInfo) {
    try {
      await userStore.fetchUserInfo()
    } catch (error) {
      console.error('获取用户信息失败', error)
    }
  }
})

// 判断是否为管理员
const isAdmin = computed(() => {
  if (!userInfo.value) return false
  
  // 添加调试信息
  console.log('当前用户信息:', userInfo.value);
  
  // 尝试从不同的位置获取角色信息
  const roleFromRoles = userInfo.value.roles?.[0]?.code;
  const directRole = (userInfo.value as any).role;
  
  console.log('角色信息 - 从roles获取:', roleFromRoles, '直接获取:', directRole);
  
  // 返回结果
  return roleFromRoles === 'ADMIN' || directRole === 'ADMIN';
})
</script>

<style scoped>
.layout {
  min-height: 100vh;
}

.logo {
  height: 64px;
  display: flex;
  justify-content: center;
  align-items: center;
}

.logo h1 {
  color: #fff;
  margin: 0;
  font-size: 20px;
}

.header {
  background: #fff;
  padding: 0 24px;
  display: flex;
  align-items: center;
  justify-content: space-between;
  box-shadow: 0 1px 4px rgba(0, 0, 0, 0.05);
}

.trigger {
  font-size: 18px;
  cursor: pointer;
  transition: color 0.3s;
  }
  
.trigger:hover {
  color: #1890ff;
  }
  
.right {
    display: flex;
    align-items: center;
}
    
    .user-dropdown {
      display: flex;
      align-items: center;
      cursor: pointer;
}

.avatar {
  background-color: #1890ff;
  margin-right: 8px;
}

.username {
  color: rgba(0, 0, 0, 0.65);
      }
      
.content {
  margin: 24px;
  padding: 24px;
  background: #fff;
  min-height: 280px;
}

.footer {
  text-align: center;
  color: rgba(0, 0, 0, 0.45);
}

/* 路由切换动画 */
.fade-enter-active,
.fade-leave-active {
  transition: opacity 0.2s ease;
}

.fade-enter-from,
.fade-leave-to {
  opacity: 0;
}
</style> 
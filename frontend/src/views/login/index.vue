<template>
  <div class="login-container">
    <div class="login-form">
      <div class="login-header">
        <h1>TestHub</h1>
        <p>测试用例管理平台</p>
      </div>
      <a-tabs v-model:activeKey="activeKey">
        <a-tab-pane key="login" tab="登录">
          <a-form 
            :model="loginForm" 
            :rules="loginRules"
            @finish="handleLoginSubmit"
            layout="vertical"
          >
            <a-form-item name="username" label="用户名">
              <a-input 
                v-model:value="loginForm.username"
                placeholder="请输入用户名"
                size="large"
              >
                <template #prefix>
                  <user-outlined />
                </template>
              </a-input>
            </a-form-item>
            <a-form-item name="password" label="密码">
              <a-input-password 
                v-model:value="loginForm.password"
                placeholder="请输入密码"
                size="large"
              >
                <template #prefix>
                  <lock-outlined />
                </template>
              </a-input-password>
            </a-form-item>
            <a-form-item>
              <a-button 
                type="primary" 
                html-type="submit" 
                :loading="loading"
                block
                size="large"
              >
                登录
              </a-button>
            </a-form-item>
          </a-form>
        </a-tab-pane>
        <a-tab-pane key="register" tab="注册">
          <a-form 
            :model="registerForm" 
            :rules="registerRules"
            @finish="handleRegisterSubmit"
            layout="vertical"
          >
            <a-form-item name="username" label="用户名">
              <a-input 
                v-model:value="registerForm.username"
                placeholder="请输入用户名"
                size="large"
              >
                <template #prefix>
                  <user-outlined />
                </template>
              </a-input>
            </a-form-item>
            <a-form-item name="password" label="密码">
              <a-input-password 
                v-model:value="registerForm.password"
                placeholder="请输入密码"
                size="large"
              >
                <template #prefix>
                  <lock-outlined />
                </template>
              </a-input-password>
            </a-form-item>
            <a-form-item name="name" label="姓名">
              <a-input 
                v-model:value="registerForm.name"
                placeholder="请输入姓名"
                size="large"
              >
                <template #prefix>
                  <user-outlined />
                </template>
              </a-input>
            </a-form-item>
            <a-form-item name="email" label="邮箱">
              <a-input 
                v-model:value="registerForm.email"
                placeholder="请输入邮箱"
                size="large"
              >
                <template #prefix>
                  <mail-outlined />
                </template>
              </a-input>
            </a-form-item>
            <a-form-item name="phone" label="电话">
              <a-input 
                v-model:value="registerForm.phone"
                placeholder="请输入电话"
                size="large"
              >
                <template #prefix>
                  <phone-outlined />
                </template>
              </a-input>
            </a-form-item>
            <a-form-item>
              <a-button 
                type="primary" 
                html-type="submit" 
                :loading="loading"
                block
                size="large"
              >
                注册
              </a-button>
            </a-form-item>
          </a-form>
        </a-tab-pane>
      </a-tabs>
    </div>
  </div>
</template>

<script setup lang="ts">
import { reactive, ref } from 'vue'
import { message } from 'ant-design-vue'
import { useRouter, useRoute } from 'vue-router'
import { UserOutlined, LockOutlined, MailOutlined, PhoneOutlined } from '@ant-design/icons-vue'
import { useUserStore } from '@/store/modules/user'
import { register } from '@/api/auth'

const activeKey = ref('login')

// 登录表单数据
const loginForm = reactive({
  username: '',
  password: ''
})

// 注册表单数据
const registerForm = reactive({
  username: '',
  password: '',
  name: '',
  email: '',
  phone: ''
})

// 登录校验规则
const loginRules = {
  username: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
  password: [{ required: true, message: '请输入密码', trigger: 'blur' }]
}

// 注册校验规则
const registerRules = {
  username: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
  password: [{ required: true, message: '请输入密码', trigger: 'blur' }],
  name: [{ required: true, message: '请输入姓名', trigger: 'blur' }],
  email: [{ required: false, message: '请输入正确的邮箱格式', type: 'email', trigger: 'blur' }],
  phone: [{ required: false, message: '请输入电话', trigger: 'blur' }]
}

const loading = ref(false)
const router = useRouter()
const route = useRoute()
const userStore = useUserStore()

// 提交登录表单
const handleLoginSubmit = async () => {
  loading.value = true
  try {
    const loginResult = await userStore.login(loginForm)
    console.log('登录成功，返回数据:', loginResult)
    message.success('登录成功')
    
    // 如果有重定向路径，则跳转到重定向路径
    const redirect = route.query.redirect as string
    router.push(redirect || '/')
  } catch (error: any) {
    console.error('登录失败:', error)
    
    // 显示具体的错误信息
    let errorMsg = '登录失败'
    
    // 优先使用axios响应中的错误信息
    if (error.response && error.response.data && error.response.data.message) {
      errorMsg = error.response.data.message
    } 
    // 其次使用error对象中的message
    else if (error.message && !error.message.includes('status code')) {
      errorMsg = error.message
    }
    
    message.error(errorMsg)
  } finally {
    loading.value = false
  }
}

// 提交注册表单
const handleRegisterSubmit = async () => {
  loading.value = true
  try {
    await register(registerForm)
    message.success('注册成功')
    // 切换到登录tab，并填充用户名
    activeKey.value = 'login'
    loginForm.username = registerForm.username
    loginForm.password = registerForm.password
  } catch (error: any) {
    message.error(error.message || '注册失败')
  } finally {
    loading.value = false
  }
}
</script>

<style scoped>
.login-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  background-color: #f5f5f5;
}
  
.login-form {
  width: 450px;
  padding: 40px;
  background: #fff;
  border-radius: 8px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
}
    
.login-header {
  text-align: center;
  margin-bottom: 30px;
}

.login-header h1 {
  margin-bottom: 8px;
  font-size: 28px;
  color: #1890ff;
}
      
.login-header p {
  color: #999;
  margin: 0;
}
</style> 
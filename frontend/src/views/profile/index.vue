<template>
  <div class="profile-container">
    <a-card title="个人中心" :bordered="false">
      <a-tabs v-model:activeKey="activeKey">
        <a-tab-pane key="info" tab="基本信息">
          <a-form 
            :model="userForm" 
            :rules="rules"
            @finish="handleSubmit"
            layout="vertical"
            style="max-width: 500px;"
          >
            <a-form-item name="username" label="用户名">
              <a-input 
                v-model:value="userForm.username"
                placeholder="用户名"
                disabled
              />
            </a-form-item>
            <a-form-item name="name" label="姓名">
              <a-input 
                v-model:value="userForm.name"
                placeholder="请输入姓名"
              />
            </a-form-item>
            <a-form-item name="email" label="邮箱">
              <a-input 
                v-model:value="userForm.email"
                placeholder="请输入邮箱"
              />
            </a-form-item>
            <a-form-item name="phone" label="电话">
              <a-input 
                v-model:value="userForm.phone"
                placeholder="请输入电话"
              />
            </a-form-item>
            <a-form-item name="password" label="密码">
              <a-input-password 
                v-model:value="userForm.password"
                placeholder="不修改请留空"
              />
            </a-form-item>
            <a-form-item>
              <a-button 
                type="primary" 
                html-type="submit" 
                :loading="loading"
              >
                保存
              </a-button>
            </a-form-item>
          </a-form>
        </a-tab-pane>
        <a-tab-pane key="security" tab="安全设置">
          <div style="max-width: 500px;">
            <p>角色：{{ userInfo.role === 'ADMIN' ? '管理员' : '测试人员' }}</p>
            <p>状态：{{ userInfo.status === 1 ? '正常' : '已禁用' }}</p>
            <p>创建时间：{{ userInfo.createTime }}</p>
            <p>上次更新：{{ userInfo.updateTime }}</p>
          </div>
        </a-tab-pane>
      </a-tabs>
    </a-card>
  </div>
</template>

<script setup lang="ts">
import { reactive, ref, onMounted } from 'vue'
import { message } from 'ant-design-vue'
import { getCurrentUser, updateUser } from '@/api/user'
import { useRouter } from 'vue-router'

const activeKey = ref('info')
const loading = ref(false)
const userInfo = ref({
  id: 0,
  username: '',
  name: '',
  email: '',
  phone: '',
  role: '',
  status: 1,
  createTime: '',
  updateTime: ''
})

const userForm = reactive({
  id: 0,
  username: '',
  name: '',
  email: '',
  phone: '',
  password: ''
})

const rules = {
  name: [{ required: true, message: '请输入姓名', trigger: 'blur' }],
  email: [{ required: false, type: 'email', message: '请输入正确的邮箱格式', trigger: 'blur' }],
  phone: [{ required: false, message: '请输入电话', trigger: 'blur' }]
}

const router = useRouter()

// 获取当前用户信息
const fetchUserInfo = async () => {
  try {
    // 先检查是否有token
    const token = localStorage.getItem('testhub_token');
    if (!token) {
      message.error('您尚未登录或登录已过期，请重新登录');
      router.push('/login');
      return;
    }
    
    const response = await getCurrentUser()
    const res = response.data;
    if (res.code === 200) {
      userInfo.value = res.data
      
      // 填充表单
      userForm.id = res.data.id
      userForm.username = res.data.username
      userForm.name = res.data.name
      userForm.email = res.data.email || ''
      userForm.phone = res.data.phone || ''
      userForm.password = ''
    }
  } catch (error) {
    console.error('获取用户信息失败', error)
    message.error('获取用户信息失败，请重新登录');
    router.push('/login');
  }
}

// 提交表单
const handleSubmit = async () => {
  loading.value = true
  try {
    const response = await updateUser(userForm.id, userForm)
    const res = response.data;
    if (res.code === 200) {
      message.success('保存成功')
      await fetchUserInfo()
    }
  } catch (error: any) {
    message.error(error.message || '保存失败')
  } finally {
    loading.value = false
  }
}

onMounted(() => {
  fetchUserInfo()
})
</script>

<style scoped>
.profile-container {
  padding: 24px;
}
</style> 
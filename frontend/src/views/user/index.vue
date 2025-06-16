<template>
  <div class="user-container">
    <a-card title="用户管理" :bordered="false">
      <a-table
        :dataSource="userList"
        :columns="columns"
        :loading="loading"
        rowKey="id"
        :pagination="{ pageSize: 10 }"
      >
        <template #bodyCell="{ column, record }">
          <!-- 角色列 -->
          <template v-if="column.key === 'role'">
            <a-tag :color="record.role === 'ADMIN' ? 'blue' : 'green'">
              {{ record.role === 'ADMIN' ? '管理员' : '测试人员' }}
            </a-tag>
          </template>
          
          <!-- 状态列 -->
          <template v-if="column.key === 'status'">
            <a-badge :status="record.status === 1 ? 'success' : 'error'" />
            {{ record.status === 1 ? '正常' : '已禁用' }}
          </template>
          
          <!-- 操作列 -->
          <template v-if="column.key === 'action'">
            <a-space>
              <a-button 
                type="link" 
                :danger="record.status === 1"
                @click="handleStatusChange(record)"
              >
                {{ record.status === 1 ? '禁用' : '启用' }}
              </a-button>
              <a-popconfirm
                title="确定要删除这个用户吗？"
                @confirm="handleDelete(record)"
                okText="确定"
                cancelText="取消"
              >
                <a-button type="link" danger>删除</a-button>
              </a-popconfirm>
            </a-space>
          </template>
        </template>
      </a-table>
    </a-card>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { message } from 'ant-design-vue'
import { getAllUsers, updateUserStatus, deleteUser } from '@/api/user'

const loading = ref(false)
const userList = ref([])

// 表格列定义
const columns = [
  {
    title: 'ID',
    dataIndex: 'id',
    key: 'id',
    width: 60
  },
  {
    title: '用户名',
    dataIndex: 'username',
    key: 'username'
  },
  {
    title: '姓名',
    dataIndex: 'name',
    key: 'name'
  },
  {
    title: '邮箱',
    dataIndex: 'email',
    key: 'email'
  },
  {
    title: '电话',
    dataIndex: 'phone',
    key: 'phone'
  },
  {
    title: '角色',
    dataIndex: 'role',
    key: 'role'
  },
  {
    title: '状态',
    dataIndex: 'status',
    key: 'status'
  },
  {
    title: '操作',
    key: 'action'
  }
]

// 加载用户列表
const loadUserList = async () => {
  loading.value = true
  try {
    const response = await getAllUsers()
    const res = response.data
    console.log('获取用户列表响应:', res)
    if (res.code === 200) {
      userList.value = res.data
    } else {
      message.error('获取用户列表失败')
    }
  } catch (error: any) {
    console.error('获取用户列表错误:', error)
    message.error(error.message || '获取用户列表失败')
  } finally {
    loading.value = false
  }
}

// 修改用户状态
const handleStatusChange = async (record: any) => {
  const newStatus = record.status === 1 ? 0 : 1
  try {
    const response = await updateUserStatus(record.id, newStatus)
    const res = response.data
    if (res.code === 200) {
      message.success('操作成功')
      // 更新本地数据
      record.status = newStatus
    } else {
      message.error('操作失败')
    }
  } catch (error: any) {
    console.error('修改用户状态错误:', error)
    message.error(error.message || '操作失败')
  }
}

// 删除用户
const handleDelete = async (record: any) => {
  try {
    const response = await deleteUser(record.id)
    const res = response.data
    if (res.code === 200) {
      message.success('删除成功')
      loadUserList() // 重新加载用户列表
    } else {
      message.error('删除失败')
    }
  } catch (error: any) {
    console.error('删除用户错误:', error)
    message.error(error.message || '删除失败')
  }
}

onMounted(() => {
  loadUserList()
})
</script>

<style scoped>
.user-container {
  padding: 24px;
}
</style> 
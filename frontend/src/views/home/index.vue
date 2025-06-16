<template>
  <div class="home-container">
    <div class="page-header">
      <h2>仪表盘</h2>
      <p>欢迎使用 TestHub 测试用例管理平台</p>
    </div>
    
      <a-row :gutter="16">
      <a-col :span="6">
        <a-card class="stat-card">
          <template #title>
            <span>
              <file-outlined /> 测试用例总数
            </span>
          </template>
          <div class="stat-number">{{ stats.totalCases }}</div>
        </a-card>
      </a-col>
      <a-col :span="6">
        <a-card class="stat-card">
          <template #title>
            <span>
              <check-circle-outlined /> 已完成用例
            </span>
          </template>
          <div class="stat-number">{{ stats.completedCases }}</div>
        </a-card>
      </a-col>
      <a-col :span="6">
        <a-card class="stat-card">
          <template #title>
            <span>
              <clock-circle-outlined /> 进行中用例
            </span>
          </template>
          <div class="stat-number">{{ stats.inProgressCases }}</div>
        </a-card>
      </a-col>
      <a-col :span="6">
        <a-card class="stat-card">
          <template #title>
            <span>
              <user-outlined /> 用户数量
            </span>
          </template>
          <div class="stat-number">{{ stats.totalUsers }}</div>
        </a-card>
      </a-col>
    </a-row>
    
    <a-row :gutter="16" class="mt-24">
      <a-col :span="12">
        <a-card title="优先级分布">
          <div class="chart-container">
            <div class="placeholder-chart">
              <pie-chart-outlined />
              <p>优先级分布图表</p>
              </div>
            </div>
          </a-card>
        </a-col>
      <a-col :span="12">
        <a-card title="测试用例状态">
          <div class="chart-container">
            <div class="placeholder-chart">
              <bar-chart-outlined />
              <p>测试用例状态图表</p>
            </div>
            </div>
          </a-card>
        </a-col>
      </a-row>

    <a-card title="最近更新的测试用例" class="mt-24">
            <a-table 
              :columns="columns" 
              :data-source="recentTestCases" 
              :pagination="false"
        :loading="loading"
            >
              <template #bodyCell="{ column, record }">
          <template v-if="column.key === 'title'">
            <router-link :to="`/testcase/detail/${record.id}`">
              {{ record.title }}
            </router-link>
          </template>
          <template v-if="column.key === 'priority'">
            <a-tag :class="`tag-${record.priority.toLowerCase()}`">
              {{ record.priority }}
            </a-tag>
          </template>
                <template v-if="column.key === 'status'">
            <a-tag :class="`tag-${record.status.toLowerCase()}`">
                    {{ record.status }}
                  </a-tag>
                </template>
          <template v-if="column.key === 'updateTime'">
            {{ formatDate(record.updateTime) }}
                </template>
              </template>
            </a-table>
      <div class="more-link text-right mt-16">
        <router-link to="/testcase">查看更多测试用例 <right-outlined /></router-link>
      </div>
    </a-card>
    
    <!-- 添加前后端连接测试组件 -->
    <a-card title="前后端连接测试" class="mt-24">
      <api-test />
    </a-card>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import { 
  FileOutlined, 
  CheckCircleOutlined, 
  ClockCircleOutlined, 
  UserOutlined, 
  PieChartOutlined,
  BarChartOutlined,
  RightOutlined
} from '@ant-design/icons-vue'
import type { TestCase } from '@/types/api'
import ApiTest from '@/components/ApiTest.vue'

// 仪表盘统计数据
const stats = reactive({
  totalCases: 0,
  completedCases: 0,
  inProgressCases: 0,
  totalUsers: 0
})

// 表格列定义
const columns = [
  {
    title: '用例标题',
    dataIndex: 'title',
    key: 'title',
    ellipsis: true
  },
  {
    title: '优先级',
    dataIndex: 'priority',
    key: 'priority',
    width: 100
  },
  {
    title: '状态',
    dataIndex: 'status',
    key: 'status',
    width: 100
  },
  {
    title: '创建人',
    dataIndex: 'creator',
    key: 'creator',
    width: 120
  },
  {
    title: '更新时间',
    dataIndex: 'updateTime',
    key: 'updateTime',
    width: 180
  }
]

// 最近更新的测试用例
const recentTestCases = ref<TestCase[]>([])
const loading = ref(false)

// 格式化日期
const formatDate = (dateStr: string) => {
  const date = new Date(dateStr)
  return date.toLocaleString()
}

// 模拟加载数据
onMounted(async () => {
  loading.value = true
  try {
    // 这里应该调用API获取实际数据
    // 为演示，使用模拟数据
    await new Promise(resolve => setTimeout(resolve, 500))
    
    // 模拟统计数据
    stats.totalCases = 128
    stats.completedCases = 78
    stats.inProgressCases = 32
    stats.totalUsers = 12
    
    // 模拟测试用例数据
    recentTestCases.value = [
  {
    id: 1,
        title: '登录功能测试 - 正常登录流程',
    priority: 'P0',
        status: 'DONE',
        type: 'FUNCTIONAL',
    creator: '张三',
        creatorId: 1,
        createTime: '2023-06-01T10:00:00',
        updateTime: '2023-06-05T14:30:00'
  },
  {
    id: 2,
        title: '注册功能 - 邮箱验证',
    priority: 'P1',
        status: 'DOING',
        type: 'FUNCTIONAL',
    creator: '李四',
        creatorId: 2,
        createTime: '2023-06-02T11:20:00',
        updateTime: '2023-06-04T09:15:00'
  },
  {
    id: 3,
        title: '首页性能测试 - 加载时间',
        priority: 'P2',
        status: 'TODO',
        type: 'PERFORMANCE',
    creator: '王五',
        creatorId: 3,
        createTime: '2023-06-03T16:40:00',
        updateTime: '2023-06-03T16:40:00'
  },
  {
    id: 4,
        title: 'API接口安全性测试',
        priority: 'P0',
        status: 'DOING',
        type: 'SECURITY',
        creator: '张三',
        creatorId: 1,
        createTime: '2023-06-04T13:10:00',
        updateTime: '2023-06-05T10:25:00'
  },
  {
    id: 5,
        title: '用户权限管理测试',
        priority: 'P1',
        status: 'DONE',
        type: 'FUNCTIONAL',
        creator: '李四',
        creatorId: 2,
        createTime: '2023-06-05T09:30:00',
        updateTime: '2023-06-06T15:40:00'
  }
    ]
  } catch (error) {
    console.error('加载数据失败', error)
  } finally {
    loading.value = false
  }
})
</script>

<style scoped>
.home-container {
  padding-bottom: 24px;
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

.stat-card {
  text-align: center;
}

.stat-number {
  font-size: 28px;
  font-weight: 500;
  color: #1890ff;
}

.chart-container {
  height: 300px;
  display: flex;
  justify-content: center;
  align-items: center;
}

.placeholder-chart {
  text-align: center;
  color: #d9d9d9;
}

.placeholder-chart > span {
  font-size: 48px;
}

.placeholder-chart p {
  margin-top: 8px;
  font-size: 16px;
}

.more-link {
  font-size: 14px;
}
</style> 
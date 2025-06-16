<template>
  <div class="report-container">
    <div class="page-header">
      <div class="header-title">
        <h2>测试报告</h2>
        <p>查看测试执行情况和统计数据</p>
      </div>
      <div class="header-actions">
        <a-button type="primary" @click="refreshData">
          <reload-outlined /> 刷新数据
        </a-button>
      </div>
    </div>
    
    <!-- 统计卡片 -->
    <a-row :gutter="16">
      <a-col :span="8">
        <a-card class="stat-card">
          <template #title>
            <span>
              <file-outlined /> 测试用例总数
            </span>
          </template>
          <div class="stat-number">{{ stats.totalCases }}</div>
        </a-card>
      </a-col>
      <a-col :span="8">
        <a-card class="stat-card">
          <template #title>
            <span>
              <check-circle-outlined /> 已完成用例
            </span>
          </template>
          <div class="stat-number">{{ stats.completedCases }}</div>
        </a-card>
      </a-col>
      <a-col :span="8">
        <a-card class="stat-card">
          <template #title>
            <span>
              <pie-chart-outlined /> 通过率
            </span>
          </template>
          <div class="stat-number">{{ stats.passRate }}%</div>
        </a-card>
      </a-col>
    </a-row>
    
    <!-- 图表区域 -->
    <a-row :gutter="16" class="mt-24">
      <a-col :span="12">
        <a-card title="优先级分布">
          <div class="chart-container">
            <div class="placeholder-chart">
              <pie-chart-outlined />
              <p>优先级分布图表</p>
              <ul class="chart-legend">
                <li><span class="legend-dot p0"></span> P0: {{ stats.priorityDistribution.P0 || 0 }}</li>
                <li><span class="legend-dot p1"></span> P1: {{ stats.priorityDistribution.P1 || 0 }}</li>
                <li><span class="legend-dot p2"></span> P2: {{ stats.priorityDistribution.P2 || 0 }}</li>
                <li><span class="legend-dot p3"></span> P3: {{ stats.priorityDistribution.P3 || 0 }}</li>
              </ul>
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
              <ul class="chart-legend">
                <li><span class="legend-dot todo"></span> 待处理: {{ stats.statusDistribution.TODO || 0 }}</li>
                <li><span class="legend-dot doing"></span> 进行中: {{ stats.statusDistribution.DOING || 0 }}</li>
                <li><span class="legend-dot done"></span> 已完成: {{ stats.statusDistribution.DONE || 0 }}</li>
              </ul>
            </div>
          </div>
        </a-card>
      </a-col>
    </a-row>
    
    <a-row :gutter="16" class="mt-24">
      <a-col :span="12">
        <a-card title="测试类型分布">
          <div class="chart-container">
            <div class="placeholder-chart">
              <pie-chart-outlined />
              <p>测试类型分布图表</p>
              <ul class="chart-legend">
                <li><span class="legend-dot functional"></span> 功能测试: {{ stats.typeDistribution.FUNCTIONAL || 0 }}</li>
                <li><span class="legend-dot performance"></span> 性能测试: {{ stats.typeDistribution.PERFORMANCE || 0 }}</li>
                <li><span class="legend-dot security"></span> 安全测试: {{ stats.typeDistribution.SECURITY || 0 }}</li>
                <li><span class="legend-dot other"></span> 其他: {{ stats.typeDistribution.OTHER || 0 }}</li>
              </ul>
            </div>
          </div>
        </a-card>
      </a-col>
      <a-col :span="12">
        <a-card title="测试结果分布">
          <div class="chart-container">
            <div class="placeholder-chart">
              <pie-chart-outlined />
              <p>测试结果分布图表</p>
              <ul class="chart-legend">
                <li><span class="legend-dot pass"></span> 通过: {{ resultStats.PASS || 0 }}</li>
                <li><span class="legend-dot fail"></span> 失败: {{ resultStats.FAIL || 0 }}</li>
                <li><span class="legend-dot blocked"></span> 阻塞: {{ resultStats.BLOCKED || 0 }}</li>
              </ul>
            </div>
          </div>
        </a-card>
      </a-col>
    </a-row>
    
    <!-- 最近执行记录 -->
    <a-card title="最近执行记录" class="mt-24">
      <a-table
        :columns="resultColumns"
        :data-source="recentExecutions"
        :pagination="false"
        :loading="loading"
      >
        <template #bodyCell="{ column, record }">
          <template v-if="column.key === 'testCaseTitle'">
            <router-link :to="`/testcase/detail/${record.testCaseId}`">
              {{ record.testCaseTitle }}
            </router-link>
          </template>
          <template v-if="column.key === 'status'">
            <a-tag :color="getResultStatusColor(record.status)">
              {{ getResultStatusText(record.status) }}
            </a-tag>
          </template>
          <template v-if="column.key === 'executionTime'">
            {{ formatDate(record.executionTime) }}
          </template>
        </template>
      </a-table>
    </a-card>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import { message } from 'ant-design-vue'
import { 
  FileOutlined, 
  CheckCircleOutlined, 
  PieChartOutlined,
  BarChartOutlined,
  ReloadOutlined
} from '@ant-design/icons-vue'
import type { TestStatsSummary, TestResult } from '@/types/api'
import { getTestStatsSummary } from '@/api/testcase'

// 加载状态
const loading = ref(false)

// 统计数据
const stats = reactive<TestStatsSummary>({
  totalCases: 0,
  completedCases: 0,
  passRate: 0,
  typeDistribution: {},
  priorityDistribution: {},
  statusDistribution: {},
  recentExecutions: []
})

// 测试结果统计
const resultStats = reactive({
  PASS: 0,
  FAIL: 0,
  BLOCKED: 0
})

// 最近执行记录
const recentExecutions = ref<(TestResult & { testCaseTitle: string })[]>([])

// 结果表格列
const resultColumns = [
  {
    title: '测试用例',
    dataIndex: 'testCaseTitle',
    key: 'testCaseTitle',
    ellipsis: true
  },
  {
    title: '执行时间',
    dataIndex: 'executionTime',
    key: 'executionTime',
    width: 180
  },
  {
    title: '状态',
    dataIndex: 'status',
    key: 'status',
    width: 100
  },
  {
    title: '执行人',
    dataIndex: 'executor',
    key: 'executor',
    width: 120
  },
  {
    title: '环境',
    dataIndex: 'environment',
    key: 'environment',
    width: 120
  }
]

// 格式化日期
const formatDate = (dateStr: string) => {
  if (!dateStr) return ''
  const date = new Date(dateStr)
  return date.toLocaleString()
}

// 测试结果状态颜色
const getResultStatusColor = (status: string) => {
  const colorMap: Record<string, string> = {
    'PASS': 'success',
    'FAIL': 'error',
    'BLOCKED': 'warning'
  }
  return colorMap[status] || 'default'
}

// 测试结果状态文本
const getResultStatusText = (status: string) => {
  const textMap: Record<string, string> = {
    'PASS': '通过',
    'FAIL': '失败',
    'BLOCKED': '阻塞'
  }
  return textMap[status] || status
}

// 刷新数据
const refreshData = () => {
  loadData()
}

// 计算测试结果统计
const calculateResultStats = (executions: TestResult[]) => {
  resultStats.PASS = 0
  resultStats.FAIL = 0
  resultStats.BLOCKED = 0
  
  executions.forEach(result => {
    if (resultStats[result.status as keyof typeof resultStats] !== undefined) {
      resultStats[result.status as keyof typeof resultStats]++
    }
  })
}

// 加载数据
const loadData = async () => {
  loading.value = true
  
  try {
    // 实际项目中，这里会调用API获取数据
    // const res = await getTestStatsSummary()
    // stats = res.data
    
    // 模拟数据
    await new Promise(resolve => setTimeout(resolve, 800))
    
    // 模拟API返回的数据
    const data = {
      totalCases: 128,
      completedCases: 78,
      passRate: 85.3,
      typeDistribution: {
        'FUNCTIONAL': 75,
        'PERFORMANCE': 28,
        'SECURITY': 15,
        'OTHER': 10
      },
      priorityDistribution: {
        'P0': 12,
        'P1': 35,
        'P2': 48,
        'P3': 33
      },
      statusDistribution: {
        'TODO': 25,
        'DOING': 25,
        'DONE': 78
      },
      recentExecutions: [
        {
          id: 1,
          testCaseId: 1,
          testCaseTitle: '登录功能测试 - 正常登录流程',
          status: 'PASS',
          executionTime: '2023-06-10T10:30:00',
          executorId: 1,
          executor: '张三',
          notes: '执行通过，登录功能正常',
          environment: '测试环境',
          version: 'v1.0.2'
        },
        {
          id: 2,
          testCaseId: 2,
          testCaseTitle: '注册功能 - 邮箱验证',
          status: 'FAIL',
          executionTime: '2023-06-08T15:20:00',
          executorId: 2,
          executor: '李四',
          notes: '执行失败，邮箱验证码无法收到',
          environment: '测试环境',
          version: 'v1.0.1'
        },
        {
          id: 3,
          testCaseId: 3,
          testCaseTitle: '首页性能测试 - 加载时间',
          status: 'PASS',
          executionTime: '2023-06-07T09:15:00',
          executorId: 1,
          executor: '张三',
          notes: '执行通过，加载时间小于3秒',
          environment: '开发环境',
          version: 'v1.0.0'
        },
        {
          id: 4,
          testCaseId: 4,
          testCaseTitle: 'API接口安全性测试',
          status: 'BLOCKED',
          executionTime: '2023-06-06T14:20:00',
          executorId: 3,
          executor: '王五',
          notes: '测试阻塞，依赖服务未就绪',
          environment: '测试环境',
          version: 'v1.0.1'
        },
        {
          id: 5,
          testCaseId: 5,
          testCaseTitle: '用户权限管理测试',
          status: 'PASS',
          executionTime: '2023-06-05T11:10:00',
          executorId: 2,
          executor: '李四',
          notes: '执行通过，权限控制正常',
          environment: '测试环境',
          version: 'v1.0.0'
        }
      ]
    }
    
    // 更新统计数据
    Object.assign(stats, data)
    
    // 更新最近执行记录
    recentExecutions.value = data.recentExecutions
    
    // 计算测试结果统计
    calculateResultStats(data.recentExecutions)
    
  } catch (error) {
    console.error('获取统计数据失败', error)
    message.error('获取统计数据失败')
  } finally {
    loading.value = false
  }
}

onMounted(() => {
  loadData()
})
</script>

<style scoped>
.report-container {
  padding-bottom: 24px;
}

.page-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 24px;
}

.header-title h2 {
  font-size: 24px;
  font-weight: 600;
  margin-bottom: 8px;
}

.header-title p {
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
  height: 280px;
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

.chart-legend {
  list-style: none;
  padding: 0;
  margin-top: 16px;
  display: flex;
  justify-content: center;
  flex-wrap: wrap;
}

.chart-legend li {
  margin: 0 12px;
  display: flex;
  align-items: center;
}

.legend-dot {
  display: inline-block;
  width: 10px;
  height: 10px;
  border-radius: 50%;
  margin-right: 8px;
}

.legend-dot.p0 { background-color: #f5222d; }
.legend-dot.p1 { background-color: #fa8c16; }
.legend-dot.p2 { background-color: #1890ff; }
.legend-dot.p3 { background-color: #52c41a; }

.legend-dot.todo { background-color: #fa8c16; }
.legend-dot.doing { background-color: #1890ff; }
.legend-dot.done { background-color: #52c41a; }

.legend-dot.pass { background-color: #52c41a; }
.legend-dot.fail { background-color: #f5222d; }
.legend-dot.blocked { background-color: #faad14; }

.legend-dot.functional { background-color: #1890ff; }
.legend-dot.performance { background-color: #722ed1; }
.legend-dot.security { background-color: #eb2f96; }
.legend-dot.other { background-color: #bfbfbf; }

.mt-24 {
  margin-top: 24px;
}
</style> 
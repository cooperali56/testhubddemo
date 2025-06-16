<template>
  <div class="testcase-container">
    <div class="page-header">
      <div class="header-title">
        <h2>测试用例管理</h2>
        <p>管理和查看所有测试用例</p>
      </div>
      <div class="header-actions">
        <a-button type="primary" @click="handleCreate">
          <plus-outlined /> 创建测试用例
        </a-button>
      </div>
    </div>
    
    <a-card>
      <div class="table-filter">
        <a-form layout="inline" :model="queryParams">
          <a-form-item label="关键词">
            <a-input
          v-model:value="queryParams.keyword"
              placeholder="标题/描述"
              allowClear
            />
          </a-form-item>
          <a-form-item label="优先级">
        <a-select
          v-model:value="queryParams.priority"
              placeholder="请选择"
              style="width: 120px"
              allowClear
        >
          <a-select-option value="P0">P0</a-select-option>
          <a-select-option value="P1">P1</a-select-option>
          <a-select-option value="P2">P2</a-select-option>
          <a-select-option value="P3">P3</a-select-option>
        </a-select>
          </a-form-item>
          <a-form-item label="状态">
            <a-select
              v-model:value="queryParams.status"
              placeholder="请选择"
              style="width: 120px"
              allowClear
            >
              <a-select-option value="TODO">待处理</a-select-option>
              <a-select-option value="DOING">进行中</a-select-option>
              <a-select-option value="DONE">已完成</a-select-option>
            </a-select>
          </a-form-item>
          <a-form-item>
            <a-button type="primary" @click="handleSearch">
              <search-outlined /> 搜索
            </a-button>
            <a-button style="margin-left: 8px" @click="handleReset">
              重置
        </a-button>
          </a-form-item>
        </a-form>
    </div>
    
    <a-table
      :columns="columns"
      :data-source="testCaseList"
        :pagination="pagination"
      :loading="loading"
      @change="handleTableChange"
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
          <template v-if="column.key === 'type'">
            {{ getTestTypeText(record.type) }}
          </template>
          <template v-if="column.key === 'createTime'">
            {{ formatDate(record.createTime) }}
        </template>
          <template v-if="column.key === 'action'">
          <a-space>
              <a-button type="link" size="small" @click="handleEdit(record)">
                编辑
              </a-button>
            <a-popconfirm
                title="确定要删除这个测试用例吗？"
              ok-text="确定"
              cancel-text="取消"
                @confirm="handleDelete(record.id)"
            >
                <a-button type="link" size="small" danger>删除</a-button>
            </a-popconfirm>
          </a-space>
        </template>
      </template>
    </a-table>
    </a-card>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { message } from 'ant-design-vue'
import { PlusOutlined, SearchOutlined } from '@ant-design/icons-vue'
import type { TestCase } from '@/types/api'
import { getTestCaseList, deleteTestCase } from '@/api/testcase'

const router = useRouter()
const loading = ref(false)
const testCaseList = ref<TestCase[]>([])

// 查询参数
const queryParams = reactive({
  keyword: '',
  priority: '',
  status: '',
  page: 1,
  pageSize: 10
})

// 分页配置
const pagination = reactive({
  current: 1,
  pageSize: 10,
  total: 0,
  showSizeChanger: true,
  showTotal: (total: number) => `共 ${total} 条记录`
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
    title: '类型',
    dataIndex: 'type',
    key: 'type',
    width: 120
  },
  {
    title: '创建人',
    dataIndex: 'creator',
    key: 'creator',
    width: 120
  },
  {
    title: '创建时间',
    dataIndex: 'createTime',
    key: 'createTime',
    width: 180
  },
  {
    title: '操作',
    key: 'action',
    width: 150
  }
]

// 格式化日期
const formatDate = (dateStr: string) => {
  const date = new Date(dateStr)
  return date.toLocaleString()
}

// 获取测试类型文本
const getTestTypeText = (type: string) => {
  const typeMap: Record<string, string> = {
    'FUNCTIONAL': '功能测试',
    'PERFORMANCE': '性能测试',
    'SECURITY': '安全测试',
    'OTHER': '其他'
  }
  return typeMap[type] || type
}

// 加载数据
const loadData = async () => {
  loading.value = true
  try {
    // 调用API获取测试用例列表
    // const res = await getTestCaseList(queryParams)
    // 由于后端接口可能还未完成，这里使用模拟数据
    await new Promise(resolve => setTimeout(resolve, 500))
    
    // 模拟API返回的数据
    const res = {
      code: 200,
      message: '操作成功',
      data: {
        list: [
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
        ],
        total: 5
      }
    }
    
    testCaseList.value = res.data.list
    pagination.total = res.data.total
  } catch (error) {
    console.error('加载测试用例失败', error)
    message.error('加载测试用例失败')
  } finally {
    loading.value = false
  }
}

// 搜索
const handleSearch = () => {
  queryParams.page = 1
  loadData()
}

// 重置
const handleReset = () => {
  Object.assign(queryParams, {
    keyword: '',
    priority: '',
    status: '',
    page: 1,
    pageSize: 10
  })
  loadData()
}

// 表格变化
const handleTableChange = (pag: any) => {
  pagination.current = pag.current
  pagination.pageSize = pag.pageSize
  queryParams.page = pag.current
  queryParams.pageSize = pag.pageSize
  loadData()
}

// 创建测试用例
const handleCreate = () => {
  router.push('/testcase/detail/create')
}

// 编辑测试用例
const handleEdit = (record: TestCase) => {
  router.push(`/testcase/detail/${record.id}`)
}

// 删除测试用例
const handleDelete = async (id: number) => {
  try {
    // await deleteTestCase(id)
    // 模拟删除操作
    await new Promise(resolve => setTimeout(resolve, 500))
    message.success('删除成功')
    loadData()
  } catch (error) {
    console.error('删除测试用例失败', error)
    message.error('删除测试用例失败')
  }
}

onMounted(() => {
  loadData()
})
</script>

<style scoped>
.testcase-container {
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

.table-filter {
  margin-bottom: 16px;
}
</style> 
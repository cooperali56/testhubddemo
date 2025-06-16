<template>
  <div class="testcase-detail">
    <div class="page-header">
      <div class="header-title">
        <h2>{{ isCreate ? '创建测试用例' : '测试用例详情' }}</h2>
      </div>
      <div class="header-actions" v-if="!isCreate && !isEdit">
        <a-button @click="handleEdit">编辑</a-button>
        <a-button type="primary" style="margin-left: 8px" @click="goBack">返回</a-button>
      </div>
    </div>
    
      <a-card>
      <a-form
        :model="formData"
        :rules="rules"
        :label-col="{ span: 4 }"
        :wrapper-col="{ span: 16 }"
        v-if="isCreate || isEdit"
      >
        <a-form-item label="标题" name="title">
          <a-input v-model:value="formData.title" placeholder="请输入测试用例标题" />
        </a-form-item>
        
        <a-form-item label="描述" name="description">
          <a-textarea
            v-model:value="formData.description"
            placeholder="请输入测试用例描述"
            :rows="4"
          />
        </a-form-item>
        
        <a-form-item label="优先级" name="priority">
          <a-select v-model:value="formData.priority" placeholder="请选择优先级">
            <a-select-option value="P0">P0 - 最高</a-select-option>
            <a-select-option value="P1">P1 - 高</a-select-option>
            <a-select-option value="P2">P2 - 中</a-select-option>
            <a-select-option value="P3">P3 - 低</a-select-option>
          </a-select>
        </a-form-item>
        
        <a-form-item label="类型" name="type">
          <a-select v-model:value="formData.type" placeholder="请选择类型">
            <a-select-option value="FUNCTIONAL">功能测试</a-select-option>
            <a-select-option value="PERFORMANCE">性能测试</a-select-option>
            <a-select-option value="SECURITY">安全测试</a-select-option>
            <a-select-option value="OTHER">其他</a-select-option>
          </a-select>
        </a-form-item>
        
        <a-form-item label="状态" name="status">
          <a-select v-model:value="formData.status" placeholder="请选择状态">
            <a-select-option value="TODO">待处理</a-select-option>
            <a-select-option value="DOING">进行中</a-select-option>
            <a-select-option value="DONE">已完成</a-select-option>
          </a-select>
        </a-form-item>
        
        <a-form-item label="负责人" name="assigneeId">
          <a-select v-model:value="formData.assigneeId" placeholder="请选择负责人" allowClear>
            <a-select-option v-for="user in userList" :key="user.id" :value="user.id">
              {{ user.nickname }}
            </a-select-option>
          </a-select>
        </a-form-item>
        
        <a-form-item :wrapper-col="{ offset: 4 }">
          <a-button type="primary" @click="handleSubmit" :loading="submitting">保存</a-button>
          <a-button style="margin-left: 8px" @click="handleCancel">取消</a-button>
        </a-form-item>
      </a-form>
      
      <div v-else class="detail-view">
        <a-descriptions :column="1" bordered>
          <a-descriptions-item label="标题">{{ testCase.title }}</a-descriptions-item>
          <a-descriptions-item label="描述">{{ testCase.description || '无' }}</a-descriptions-item>
          <a-descriptions-item label="优先级">
            <a-tag :class="`tag-${testCase.priority.toLowerCase()}`">
              {{ testCase.priority }}
            </a-tag>
          </a-descriptions-item>
          <a-descriptions-item label="类型">
            {{ getTestTypeText(testCase.type) }}
          </a-descriptions-item>
          <a-descriptions-item label="状态">
            <a-tag :class="`tag-${testCase.status.toLowerCase()}`">
              {{ testCase.status }}
            </a-tag>
          </a-descriptions-item>
          <a-descriptions-item label="创建人">{{ testCase.creator }}</a-descriptions-item>
          <a-descriptions-item label="负责人">{{ testCase.assignee || '未分配' }}</a-descriptions-item>
          <a-descriptions-item label="创建时间">{{ formatDate(testCase.createTime) }}</a-descriptions-item>
          <a-descriptions-item label="更新时间">{{ formatDate(testCase.updateTime) }}</a-descriptions-item>
        </a-descriptions>
        
        <!-- 测试执行部分 -->
        <div class="test-execution">
          <a-divider orientation="left">测试执行</a-divider>
        
          <!-- 执行测试按钮 -->
          <div class="execution-actions">
            <a-button type="primary" @click="showAddResultModal">
              <play-circle-outlined /> 添加执行结果
            </a-button>
        </div>
        
          <!-- 测试结果列表 -->
          <a-table
            :columns="resultColumns"
            :data-source="testResults"
            :pagination="false"
            :loading="loadingResults"
            class="mt-16"
            size="small"
          >
            <template #bodyCell="{ column, record }">
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
        </div>
          </div>
    </a-card>
    
    <!-- 添加测试结果弹窗 -->
    <a-modal
      :visible="resultModalVisible"
      title="添加测试执行结果"
      @cancel="resultModalVisible = false"
      :footer="null"
    >
      <a-form :model="resultForm" :label-col="{ span: 4 }" :wrapper-col="{ span: 20 }">
        <a-form-item label="状态" name="status" :rules="[{ required: true, message: '请选择执行状态' }]">
          <a-radio-group v-model:value="resultForm.status">
                <a-radio-button value="PASS">通过</a-radio-button>
                <a-radio-button value="FAIL">失败</a-radio-button>
            <a-radio-button value="BLOCKED">阻塞</a-radio-button>
              </a-radio-group>
            </a-form-item>
        
        <a-form-item label="环境" name="environment">
          <a-input v-model:value="resultForm.environment" placeholder="测试执行环境" />
        </a-form-item>
        
        <a-form-item label="版本" name="version">
          <a-input v-model:value="resultForm.version" placeholder="被测版本" />
        </a-form-item>
        
        <a-form-item label="备注" name="notes">
              <a-textarea 
            v-model:value="resultForm.notes"
            placeholder="请输入执行情况、问题描述等"
                :rows="4" 
              />
            </a-form-item>
        
        <a-form-item :wrapper-col="{ offset: 4, span: 20 }">
          <a-button type="primary" @click="submitTestResult" :loading="submittingResult">
            提交
          </a-button>
          <a-button style="margin-left: 8px" @click="resultModalVisible = false">
            取消
              </a-button>
            </a-form-item>
          </a-form>
    </a-modal>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, computed, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { message } from 'ant-design-vue'
import { PlayCircleOutlined } from '@ant-design/icons-vue'
import type { TestCase, TestCaseParams, TestResult, TestResultParams } from '@/types/api'
import { getTestCaseDetail, createTestCase, updateTestCase, getTestResults, addTestResult } from '@/api/testcase'
import { getUserList } from '@/api/user'

const route = useRoute()
const router = useRouter()
const submitting = ref(false)
const testCaseId = computed(() => route.params.id as string)
const isCreate = computed(() => testCaseId.value === 'create')
const isEdit = ref(false)

// 测试用例数据
const testCase = ref<TestCase>({} as TestCase)
const userList = ref<{ id: number; nickname: string }[]>([])

// 表单数据
const formData = reactive<TestCaseParams>({
  title: '',
  description: '',
  priority: 'P1',
  status: 'TODO',
  type: 'FUNCTIONAL'
})

// 校验规则
const rules = {
  title: [{ required: true, message: '请输入测试用例标题', trigger: 'blur' }],
  priority: [{ required: true, message: '请选择优先级', trigger: 'change' }],
  status: [{ required: true, message: '请选择状态', trigger: 'change' }],
  type: [{ required: true, message: '请选择类型', trigger: 'change' }]
}

// 格式化日期
const formatDate = (dateStr: string) => {
  if (!dateStr) return ''
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

// 测试结果列表
const testResults = ref<TestResult[]>([])
const loadingResults = ref(false)

// 测试结果表格列
const resultColumns = [
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
  },
  {
    title: '版本',
    dataIndex: 'version',
    key: 'version',
    width: 120
  },
  {
    title: '备注',
    dataIndex: 'notes',
    key: 'notes',
    ellipsis: true
  }
]

// 测试结果弹窗相关
const resultModalVisible = ref(false)
const submittingResult = ref(false)
const resultForm = reactive<TestResultParams>({
  testCaseId: 0,
  status: 'PASS',
  notes: '',
  environment: '',
  version: ''
})

// 显示添加结果弹窗
const showAddResultModal = () => {
  resultForm.testCaseId = Number(testCaseId.value)
  resultForm.status = 'PASS'
  resultForm.notes = ''
  resultForm.environment = ''
  resultForm.version = ''
  resultModalVisible.value = true
}

// 提交测试结果
const submitTestResult = async () => {
  submittingResult.value = true
  try {
    // 实际项目中调用API
    // await addTestResult(resultForm)
    
    // 模拟添加测试结果
    await new Promise(resolve => setTimeout(resolve, 500))
    message.success('添加执行结果成功')
    resultModalVisible.value = false
    
    // 刷新测试结果列表
    loadTestResults()
  } catch (error) {
    console.error('添加执行结果失败', error)
    message.error('添加执行结果失败')
  } finally {
    submittingResult.value = false
  }
}

// 加载测试结果列表
const loadTestResults = async () => {
  if (isCreate.value) return
  
  loadingResults.value = true
  try {
    // 实际项目中调用API
    // const res = await getTestResults(Number(testCaseId.value))
    // testResults.value = res.data
    
    // 模拟测试结果
    await new Promise(resolve => setTimeout(resolve, 300))
    testResults.value = [
      {
        id: 1,
        testCaseId: Number(testCaseId.value),
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
        testCaseId: Number(testCaseId.value),
        status: 'FAIL',
        executionTime: '2023-06-08T15:20:00',
        executorId: 2,
        executor: '李四',
        notes: '执行失败，登录按钮点击后无反应',
        environment: '测试环境',
        version: 'v1.0.1'
      },
      {
        id: 3,
        testCaseId: Number(testCaseId.value),
        status: 'PASS',
        executionTime: '2023-06-07T09:15:00',
        executorId: 1,
        executor: '张三',
        notes: '执行通过',
        environment: '开发环境',
        version: 'v1.0.0'
      }
    ]
  } catch (error) {
    console.error('加载测试结果失败', error)
    message.error('加载测试结果失败')
  } finally {
    loadingResults.value = false
  }
}

// 加载测试用例详情
const loadTestCaseDetail = async () => {
  if (isCreate.value) return
  
  try {
    // 实际项目中，这里会调用API获取数据
    // const res = await getTestCaseDetail(Number(testCaseId.value))
    // testCase.value = res.data
    
    // 模拟API返回的数据
    await new Promise(resolve => setTimeout(resolve, 500))
    testCase.value = {
      id: Number(testCaseId.value),
      title: '登录功能测试 - 正常登录流程',
      description: '验证用户使用正确的用户名和密码能够成功登录系统',
      priority: 'P0',
      status: 'DONE',
      type: 'FUNCTIONAL',
      creator: '张三',
      creatorId: 1,
      assignee: '李四',
      assigneeId: 2,
      createTime: '2023-06-01T10:00:00',
      updateTime: '2023-06-05T14:30:00'
    }
    
    // 加载测试结果
    loadTestResults()
  } catch (error) {
    console.error('获取测试用例详情失败', error)
    message.error('获取测试用例详情失败')
  }
}

// 加载用户列表
const loadUserList = async () => {
  try {
    // 实际项目中，这里会调用API获取数据
    // const res = await getUserList()
    // userList.value = res.data
    
    // 模拟用户数据
    userList.value = [
      { id: 1, nickname: '张三' },
      { id: 2, nickname: '李四' },
      { id: 3, nickname: '王五' }
    ]
  } catch (error) {
    console.error('获取用户列表失败', error)
  }
}

// 返回列表页
const goBack = () => {
  router.push('/testcase')
}

// 进入编辑模式
const handleEdit = () => {
  isEdit.value = true
  // 填充表单数据
  Object.assign(formData, {
    title: testCase.value.title,
    description: testCase.value.description,
    priority: testCase.value.priority,
    status: testCase.value.status,
    type: testCase.value.type,
    assigneeId: testCase.value.assigneeId
  })
}

// 取消编辑
const handleCancel = () => {
  if (isCreate.value) {
    goBack()
  } else {
    isEdit.value = false
  }
}

// 提交表单
const handleSubmit = async () => {
  submitting.value = true
  try {
    if (isCreate.value) {
      // 创建测试用例
      // await createTestCase(formData)
      await new Promise(resolve => setTimeout(resolve, 500))
      message.success('创建成功')
      router.push('/testcase')
    } else {
      // 更新测试用例
      // await updateTestCase(Number(testCaseId.value), formData)
      await new Promise(resolve => setTimeout(resolve, 500))
      message.success('更新成功')
      isEdit.value = false
      loadTestCaseDetail() // 重新加载详情
    }
  } catch (error) {
    console.error('保存测试用例失败', error)
    message.error('保存失败')
  } finally {
    submitting.value = false
  }
}

onMounted(async () => {
  await loadUserList()
  await loadTestCaseDetail()
})
</script>

<style scoped>
.testcase-detail {
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
  
.detail-view {
  padding: 16px 0;
  }
  
.test-execution {
  margin-top: 24px;
  }
  
.execution-actions {
    margin-bottom: 16px;
}
</style> 
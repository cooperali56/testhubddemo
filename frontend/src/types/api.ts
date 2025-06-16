/**
 * API数据类型定义
 */

/**
 * API 返回结果接口
 */
export interface ApiResponse<T = any> {
  code: number;
  message: string;
  data: T;
}

/**
 * 分页请求参数
 */
export interface PageParams {
  current: number;
  pageSize: number;
  keyword?: string;
  [key: string]: any;
}

/**
 * 分页返回结果接口
 */
export interface PageResult<T> {
  total: number;
  list: T[];
}

/**
 * 登录请求参数
 */
export interface LoginParams {
  username: string;
  password: string;
  rememberMe?: boolean;
}

/**
 * 注册请求参数
 */
export interface RegisterParams {
  username: string;
  password: string;
  name: string;
  email?: string;
  phone?: string;
}

/**
 * 登录返回结果
 */
export interface LoginResult {
  token: string;
  userInfo: UserInfo;
}

/**
 * 用户角色
 */
export interface Role {
  id: number;
  name: string;
  code: string;
}

/**
 * 用户信息
 */
export interface UserInfo {
  id: number;
  username: string;
  nickname: string;
  email?: string;
  phone?: string;
  avatar?: string;
  role?: string;
  roles: Role[];
  createTime?: string;
  updateTime?: string;
}

/**
 * 测试用例接口
 */
export interface TestCase {
  id: number;
  title: string;
  description?: string;
  priority: 'P0' | 'P1' | 'P2' | 'P3';
  status: 'TODO' | 'DOING' | 'DONE';
  type: 'FUNCTIONAL' | 'PERFORMANCE' | 'SECURITY' | 'OTHER';
  creator: string;
  creatorId: number;
  assignee?: string;
  assigneeId?: number;
  createTime: string;
  updateTime: string;
  testResults?: TestResult[];
}

/**
 * 测试用例创建/更新参数
 */
export interface TestCaseParams {
  title: string;
  description?: string;
  priority: 'P0' | 'P1' | 'P2' | 'P3';
  status: 'TODO' | 'DOING' | 'DONE';
  type: 'FUNCTIONAL' | 'PERFORMANCE' | 'SECURITY' | 'OTHER';
  assigneeId?: number;
}

/**
 * 测试结果接口
 */
export interface TestResult {
  id: number;
  testCaseId: number;
  status: 'PASS' | 'FAIL' | 'BLOCKED';
  executionTime: string;
  executorId: number;
  executor: string;
  notes?: string;
  attachments?: string[];
  duration?: number;
  environment?: string;
  version?: string;
}

/**
 * 测试结果创建参数
 */
export interface TestResultParams {
  testCaseId: number;
  status: 'PASS' | 'FAIL' | 'BLOCKED';
  notes?: string;
  environment?: string;
  version?: string;
}

/**
 * 测试统计概要
 */
export interface TestStatsSummary {
  totalCases: number;
  completedCases: number;
  passRate: number;
  typeDistribution: Record<string, number>;
  priorityDistribution: Record<string, number>;
  statusDistribution: Record<string, number>;
  recentExecutions: TestResult[];
} 
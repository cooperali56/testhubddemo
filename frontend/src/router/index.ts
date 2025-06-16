import { createRouter, createWebHistory } from 'vue-router';
import { getToken } from '@/utils/auth';
import NProgress from 'nprogress';
import 'nprogress/nprogress.css';

// 配置NProgress
NProgress.configure({ showSpinner: false });

// 路由配置
const routes = [
  {
    path: '/login',
    name: 'Login',
    component: () => import('@/views/login/index.vue'),
    meta: { title: '登录', requiresAuth: false }
  },
  {
    path: '/',
    component: () => import('@/components/layout/AppLayout.vue'),
    redirect: '/home',
    meta: { requiresAuth: true },
    children: [
      {
        path: 'home',
        name: 'Home',
        component: () => import('@/views/home/index.vue'),
        meta: { title: '首页', icon: 'home' }
      },
      {
        path: 'profile',
        name: 'Profile',
        component: () => import('@/views/profile/index.vue'),
        meta: { title: '个人中心', hideInMenu: true }
      },
      {
        path: 'user',
        name: 'User',
        component: () => import('@/views/user/index.vue'),
        meta: { title: '用户管理', icon: 'user', roles: ['ADMIN'] }
      },
      {
        path: 'testcase',
        name: 'TestCase',
        component: () => import('@/views/testcase/index.vue'),
        meta: { title: '测试用例', icon: 'file-text' }
      },
      {
        path: 'testcase/detail/:id',
        name: 'TestCaseDetail',
        component: () => import('@/views/testcase/detail.vue'),
        meta: { title: '测试用例详情', hideInMenu: true }
      },
      {
        path: 'report',
        name: 'Report',
        component: () => import('@/views/report/index.vue'),
        meta: { title: '测试报告', icon: 'bar-chart' }
      },
      {
        path: 'api-test',
        name: 'ApiTest',
        component: () => import('@/views/ApiTestPage.vue'),
        meta: { title: 'API连接测试', icon: 'api' }
      }
    ]
  },
  {
    path: '/404',
    name: '404',
    component: () => import('@/views/404.vue'),
    meta: { title: '404', requiresAuth: false, hideInMenu: true }
  },
  {
    path: '/:catchAll(.*)',
    redirect: '/404'
  }
];

const router = createRouter({
  history: createWebHistory(),
  routes
});

// 全局前置守卫
router.beforeEach((to, from, next) => {
  // 开始进度条
  NProgress.start();
  
  // 设置页面标题
  document.title = to.meta.title ? `${to.meta.title} - TestHub` : 'TestHub';
  
  // 检查是否需要登录权限
  const requiresAuth = to.matched.some(record => record.meta.requiresAuth !== false);
  
  if (requiresAuth) {
  const token = getToken();
  
    if (!token) {
      // 未登录，跳转到登录页
      next({
        path: '/login',
        query: { redirect: to.fullPath }
      });
    } else {
      next();
    }
  } else {
    // 不需要登录权限的页面直接放行
    next();
  }
});

// 全局后置钩子
router.afterEach(() => {
  // 结束进度条
  NProgress.done();
});

export default router; 
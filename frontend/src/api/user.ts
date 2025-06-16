import request from '@/utils/request';
import type { LoginParams, UserInfo } from '@/types/api';

/**
 * 用户登录
 * @param data 登录参数
 */
export function login(data: LoginParams) {
  return request({
    url: '/auth/login',
    method: 'post',
    data
  });
}

/**
 * 获取用户信息
 */
export function getUserInfo() {
  return request({
    url: '/auth/info',
    method: 'get'
  });
}

/**
 * 用户登出
 */
export function logout() {
  return request({
    url: '/auth/logout',
    method: 'post'
  });
}

/**
 * 获取当前用户信息
 */
export function getCurrentUser() {
  return request({
    url: '/api/user/current',
    method: 'get'
  });
}

/**
 * 更新用户信息
 * @param id 用户ID
 * @param data 用户信息
 */
export function updateUser(id: number, data: any) {
  return request({
    url: `/api/user/${id}`,
    method: 'put',
    data
  });
}

/**
 * 获取所有用户
 */
export function getAllUsers() {
  return request({
    url: '/api/user',
    method: 'get'
  });
}

/**
 * 更新用户状态
 * @param id 用户ID
 * @param status 状态 1-启用 0-禁用
 */
export function updateUserStatus(id: number, status: number) {
  return request({
    url: `/api/user/${id}/status/${status}`,
    method: 'put'
  });
}

/**
 * 删除用户
 * @param id 用户ID
 */
export function deleteUser(id: number) {
  return request({
    url: `/api/user/${id}`,
    method: 'delete'
  });
} 
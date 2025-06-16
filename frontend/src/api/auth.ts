import request from '@/utils/request';
import type { RegisterParams } from '@/types/api';

/**
 * 用户注册
 * @param data 注册参数
 */
export function register(data: RegisterParams) {
  return request({
    url: 'auth/register',
    method: 'post',
    data
  });
} 
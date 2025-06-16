import axios from 'axios';
import type { AxiosInstance, AxiosRequestConfig, AxiosResponse } from 'axios';
import { message } from 'ant-design-vue';
import { getToken } from './auth';

// 创建axios实例
const request: AxiosInstance = axios.create({
  // 使用相对路径，通过vite代理转发请求
  baseURL: '',
  timeout: 10000,
  headers: {
    'Content-Type': 'application/json;charset=utf-8',
  }
});

// 请求拦截器
request.interceptors.request.use(
  (config) => {
    // 添加token到请求头
    const token = getToken();
    if (token && config.headers) {
      config.headers['Authorization'] = `Bearer ${token}`;
    }
    return config;
  },
  (error) => {
    console.error('请求错误:', error);
    return Promise.reject(error);
  }
);

// 响应拦截器
request.interceptors.response.use(
  (response: AxiosResponse) => {
    const res = response.data;
    if (res.code !== 200) {
      // 显示后端返回的详细错误信息
      message.error(res.message || '请求失败');
      
      // 对特定错误码进行处理
      if (res.code === 401) {
        // 处理未授权情况
        // store.dispatch('user/logout');
      }
      
      // 创建一个包含详细错误信息的Error对象
      const error = new Error(res.message || '请求失败');
      (error as any).data = res;
      (error as any).status = res.code;
      
      return Promise.reject(error);
    }
    return response;
  },
  (error) => {
    console.error('响应错误:', error);
    
    // 从响应中获取错误信息
    const response = error.response;
    let errorMsg = '网络错误';
    
    if (response && response.data) {
      errorMsg = response.data.message || errorMsg;
      // 避免重复显示错误消息
      // 仅在console记录服务器返回的错误
      console.log('服务器返回错误:', response.data);
    } else {
      // 只有在没有response.data时才显示错误消息
      message.error(errorMsg);
    }
    
    return Promise.reject(error);
  }
);

export { request };
export default request; 
import { defineStore } from 'pinia';
import { getUserInfo, login, logout } from '@/api/user';
import { getToken, removeToken, setToken, setUserInfo, getUserInfo as getStoredUserInfo, removeUserInfo } from '@/utils/auth';
import type { LoginParams, UserInfo } from '@/types/api';

export const useUserStore = defineStore('user', {
  state: () => ({
    token: getToken(),
    userInfo: getStoredUserInfo() as UserInfo | null
  }),

  getters: {
    isLoggedIn: (state) => !!state.token && !!state.userInfo,
    username: (state) => state.userInfo?.username || '',
    nickname: (state) => state.userInfo?.nickname || '',
    avatar: (state) => state.userInfo?.avatar || '',
    roles: (state) => state.userInfo?.roles || []
  },

  actions: {
    // 登录
    async login(loginParams: LoginParams) {
      try {
        const response = await login(loginParams);
        const { data } = response.data;
        // 后端返回的数据格式: { userId, username, name, role, token }
        const { token, username, name, role, userId } = data;
        
        // 确保token存在
        if (!token) {
          return Promise.reject(new Error('登录失败，未获取到有效的认证令牌'));
        }
        
        console.log('登录成功，获取到token:', token);
        console.log('登录用户角色:', role);
        
        // 设置token
        this.token = token;
        
        // 构建userInfo对象
        const userInfo = {
          id: userId,
          username,
          nickname: name,
          role, // 直接保存角色字符串，方便后续判断
          roles: [{ id: 1, name: role, code: role }]
        };
        
        this.userInfo = userInfo as UserInfo;
        
        // 保存到本地存储
        setToken(token);
        setUserInfo(userInfo);
        
        return Promise.resolve(data);
      } catch (error) {
        console.error('登录失败:', error);
        return Promise.reject(error);
      }
    },

    // 获取用户信息
    async fetchUserInfo() {
      try {
        const { data } = await getUserInfo();
        const userInfo = {
          id: data.user.id,
          username: data.user.username,
          nickname: data.user.name,
          roles: [{ id: 1, name: data.user.role, code: data.user.role }]
        };
        this.userInfo = userInfo as UserInfo;
        setUserInfo(userInfo);
        return Promise.resolve(userInfo);
      } catch (error) {
        return Promise.reject(error);
      }
    },

    // 登出
    async logout() {
      if (this.token) {
      try {
          await logout();
        } catch (error) {
          console.error('登出请求失败', error);
        }
      }
      
      this.token = '';
      this.userInfo = null;
      removeToken();
      removeUserInfo();
    },

    // 重置用户状态
    resetState() {
      this.token = '';
      this.userInfo = null;
    }
  }
});
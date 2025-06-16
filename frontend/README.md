# TestHub 前端项目

TestHub是一个测试用例和自动化测试管理平台，本仓库包含前端代码。

## 技术栈

- Vue 3
- TypeScript
- Vite
- Pinia
- Vue Router
- Ant Design Vue
- Axios

## 项目结构

```
frontend/
├── public/             # 静态资源
  ├── src/
│   ├── api/            # API请求封装
│   ├── assets/         # 项目资源文件(图片、样式等)
│   ├── components/     # 公共组件
│   ├── hooks/          # 自定义Hooks
│   ├── router/         # 路由配置
│   ├── store/          # 状态管理
│   ├── types/          # TypeScript类型定义
│   ├── utils/          # 工具函数
│   ├── views/          # 页面组件
│   ├── App.vue         # 根组件
│   ├── main.ts         # 入口文件
│   └── env.d.ts        # 环境变量声明
├── .gitignore          # Git忽略文件
├── index.html          # HTML模板
├── package.json        # 项目依赖
├── README.md           # 项目说明
├── tsconfig.json       # TypeScript配置
├── tsconfig.node.json  # Node.js TypeScript配置
└── vite.config.ts      # Vite配置
```

## 功能模块

1. **用户认证**
   - 登录/登出
   - 用户信息管理

2. **测试用例管理**
   - 测试用例列表
   - 测试用例创建、编辑、删除
   - 测试用例详情查看

3. **测试执行**
   - 执行测试用例
   - 记录测试结果
   - 查看历史执行记录

4. **测试报告**
   - 测试执行统计
   - 测试结果分析
   - 数据可视化展示

## 开发指南

### 环境准备

- Node.js 16+ 
- npm 8+

### 安装依赖

```bash
npm install
```

### 启动开发服务器

```bash
npm run dev
```

### 构建生产版本

```bash
npm run build
```

### 本地预览生产构建

```bash
npm run preview
```

## 开发规范

1. **命名规范**
   - 组件使用PascalCase命名
   - 文件使用kebab-case命名
   - 变量和函数使用camelCase命名

2. **代码风格**
   - 使用TypeScript类型
   - 遵循ESLint规则
   - 使用Composition API编写组件

3. **提交规范**
   - feat: 新功能
   - fix: 修复bug
   - docs: 文档更新
   - style: 代码风格调整
   - refactor: 代码重构
   - test: 测试代码
   - chore: 构建过程或辅助工具的变动

## 项目状态

当前完成功能:
- [x] 用户登录/登出
- [x] 测试用例列表展示
- [x] 测试用例详情页
- [x] 测试用例创建/编辑
- [x] 测试执行结果记录
- [x] 测试报告和统计

正在开发的功能:
- [ ] 用户权限管理
- [ ] 自动化测试集成
- [ ] API文档集成
- [ ] 更丰富的数据可视化
- [ ] 团队协作功能 
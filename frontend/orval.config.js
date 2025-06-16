/**
 * @see https://orval.dev/reference/configuration
 */
module.exports = {
  'testhub-api': {
    input: {
      target: 'http://localhost:8080/v3/api-docs',
      /**
       * 使用过滤器来选择性生成代码
       */
      filters: {
        // 在这里列出您在后端定义的所有需要暴露给前端的 Tag 名称
        tags: [
          '测试用例接口', 
          '认证接口', 
          '用户接口',
          '测试报告接口'
        ],
      },
    },
    output: {
      target: './src/api/generated.ts',
      client: 'axios',
      clean: true,
      override: {
        mutator: {
          path: './src/utils/request.ts',
          name: 'request',
        },
        query: {
          useQuery: true,
        },
        baseUrl: '',
      },
    },
    hooks: {
      afterAllFilesWrite: 'prettier --write',
    }
  },
}; 
# TestHub 后端

TestHub是一个专为软件测试团队设计的测试用例和自动化测试管理平台。它提供了完整的测试用例管理、测试计划执行、测试报告生成等功能，帮助测试团队提高工作效率和测试质量。

## 技术栈

- **Spring Boot 2.7.14** - 快速开发Spring应用的框架
- **Spring Security** - 强大的身份认证和授权框架
- **MyBatis-Plus 3.5.3.1** - MyBatis的增强工具包，简化开发
- **MySQL** - 关系型数据库
- **JWT** - 基于Token的无状态认证机制
- **Knife4j/SpringDoc** - API文档生成工具
- **Lombok** - 减少样板代码的工具库
- **Hutool** - 国产工具类库，提供丰富的工具方法

## 项目特点

- 基于RESTful API设计
- JWT无状态认证，提高系统安全性
- 统一响应格式和异常处理
- 基于角色的权限控制
- 完善的API文档
- 代码规范和最佳实践

## 目录结构

```
backend/
├── src/main/java/com/testhub/backend/
│   ├── config/         # 配置类
│   │   ├── CorsConfig.java             # 跨域配置
│   │   ├── GlobalExceptionHandler.java # 全局异常处理
│   │   ├── JwtConfig.java              # JWT配置
│   │   ├── MybatisPlusConfig.java      # MyBatis-Plus配置
│   │   ├── PasswordEncoderConfig.java  # 密码编码器配置
│   │   ├── SecurityConfig.java         # 安全配置
│   │   └── SwaggerConfig.java          # API文档配置
│   │
│   ├── controller/     # 控制器层
│   │   ├── AuthController.java         # 认证控制器
│   │   ├── TestCaseController.java     # 测试用例控制器
│   │   ├── UserController.java         # 用户控制器
│   │   └── ReportController.java       # 报告控制器
│   │
│   ├── service/        # 服务层接口
│   │   ├── AuthService.java            # 认证服务
│   │   ├── TestCaseService.java        # 测试用例服务
│   │   ├── UserService.java            # 用户服务
│   │   └── impl/                       # 服务实现类
│   │
│   ├── repository/     # 数据访问层
│   │   ├── TestCaseRepository.java     # 测试用例数据访问
│   │   └── UserRepository.java         # 用户数据访问
│   │
│   ├── entity/         # 实体类
│   │   ├── TestCase.java               # 测试用例实体
│   │   └── User.java                   # 用户实体
│   │
│   ├── dto/            # 数据传输对象
│   │   ├── LoginRequest.java           # 登录请求
│   │   ├── LoginResponse.java          # 登录响应
│   │   ├── TestCaseDTO.java            # 测试用例DTO
│   │   └── UserDTO.java                # 用户DTO
│   │
│   ├── enums/          # 枚举类
│   │   ├── RoleEnum.java               # 角色枚举
│   │   └── TestCaseStatusEnum.java     # 测试用例状态枚举
│   │
│   ├── security/       # 安全相关
│   │   ├── JwtAuthenticationFilter.java # JWT认证过滤器
│   │   ├── UserDetailsImpl.java         # 用户详情实现
│   │   └── UserDetailsServiceImpl.java  # 用户详情服务实现
│   │
│   ├── utils/          # 工具类
│   │   ├── JwtUtil.java                # JWT工具类
│   │   └── Result.java                 # 统一响应结果
│   │
│   └── TestHubApplication.java         # 应用启动类
│
├── src/main/resources/
│   ├── application.yml                 # 应用主配置
│   ├── application-dev.yml             # 开发环境配置
│   ├── application-prod.yml            # 生产环境配置
│   ├── logback-spring.xml              # 日志配置
│   ├── mapper/                         # MyBatis XML映射文件
│   └── sql/                            # SQL脚本
│       └── schema.sql                  # 数据库初始化脚本
│
├── .lombok.config                      # Lombok配置
└── pom.xml                             # Maven配置
```

## 快速开始

### 前置条件

- JDK 11+
- Maven 3.6+
- MySQL 5.7+

### 数据库配置

1. 创建MySQL数据库:

```sql
CREATE DATABASE testhub DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
```

2. 导入初始化SQL:

```bash
mysql -u your_username -p testhub < src/main/resources/sql/schema.sql
```

或者通过MySQL客户端工具执行`src/main/resources/sql/schema.sql`中的SQL语句。

### 修改配置

编辑`src/main/resources/application.yml`文件，修改以下配置:

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/testhub?useUnicode=true&characterEncoding=utf-8&serverTimezone=Asia/Shanghai
    username: your_username
    password: your_password
```

### 编译与运行

```bash
# 进入项目目录
cd backend

# 编译
mvn clean package -DskipTests

# 运行
java -jar target/testhub-backend-0.0.1-SNAPSHOT.jar

# 或者使用Maven插件运行
mvn spring-boot:run
```

### 访问API文档

启动应用后，访问以下URL查看API文档:
- Knife4j接口文档: http://localhost:8080/doc.html
- Swagger UI: http://localhost:8080/swagger-ui.html

## 默认用户

系统初始化后会创建以下默认用户:

- 管理员: admin / admin123
- 测试人员: tester / test123

## API说明

### 认证接口

- 登录: `POST /auth/login`
  ```json
  {
    "username": "admin",
    "password": "admin123"
  }
  ```

### 用户接口

- 获取用户列表: `GET /api/users`
- 获取用户详情: `GET /api/users/{id}`
- 创建用户: `POST /api/users`
- 更新用户: `PUT /api/users/{id}`
- 删除用户: `DELETE /api/users/{id}`

### 测试用例接口

- 获取测试用例列表: `GET /api/testcases`
- 获取测试用例详情: `GET /api/testcases/{id}`
- 创建测试用例: `POST /api/testcases`
- 更新测试用例: `PUT /api/testcases/{id}`
- 删除测试用例: `DELETE /api/testcases/{id}`

### 认证说明

- 除登录接口外，所有API请求需要在请求头中添加JWT令牌:
  ```
  Authorization: Bearer [token]
  ```

## 开发指南

### 添加新的实体和接口

1. 在`entity`包中创建实体类
2. 在`repository`包中创建数据访问接口
3. 在`dto`包中创建DTO类
4. 在`service`包中创建服务接口和实现类
5. 在`controller`包中创建控制器类
6. 更新API文档注解

### 代码规范

- 使用Lombok注解减少样板代码
- 控制器返回统一使用`Result<T>`包装
- 异常处理使用全局异常处理器
- 所有接口添加API文档注解

### 循环依赖问题解决方案

项目中可能存在的循环依赖已通过以下方式解决:
1. 将`PasswordEncoder`的定义移至独立的`PasswordEncoderConfig`类
2. 在`application.yml`中添加`spring.main.allow-circular-references=true`配置

## 常见问题

### Lombok注解不生效

确保IDE安装了Lombok插件，并且在项目中正确配置了Lombok:

1. 检查`.lombok.config`文件配置
2. 在`pom.xml`中确保正确配置了Lombok依赖和注解处理器

### 数据库连接问题

1. 检查MySQL服务是否正常运行
2. 验证数据库连接配置是否正确
3. 确保数据库用户有足够权限

## 贡献指南

欢迎贡献代码或提出建议，请遵循以下步骤:

1. Fork本仓库
2. 创建特性分支 (`git checkout -b feature/amazing-feature`)
3. 提交更改 (`git commit -m 'Add some amazing feature'`)
4. 推送到分支 (`git push origin feature/amazing-feature`)
5. 创建Pull Request 
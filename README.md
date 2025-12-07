# 动物园管理系统（后端）

本仓库包含动物园管理系统的后端代码（Java + JDBC），并提供基于 Spring 的 REST API（放在 `src/spring/java`）。

## 快速启动（开发）

1. 使用 Maven 构建（跳过测试以加快）：

```powershell
mvn -Pspring-boot -DskipTests package
```

2. 使用 Maven 启动（会将 `src/spring/java` 加入编译源）：

```powershell
mvn -Pspring-boot spring-boot:run
```

默认服务地址： http://localhost:8080

## API 文档（Swagger / OpenAPI）

- 机器可读的 OpenAPI JSON（运行时生成）：
  - http://localhost:8080/v3/api-docs
  - 项目内已保存： `docs/swagger.json`

- Swagger UI：
  - http://localhost:8080/swagger-ui/index.html

说明：`docs/swagger.json` 是当前后端接口的一个快照，可直接交给前端使用（Mock 或自动生成 API 客户端）。如果需要最新的接口定义，请启动后端服务并访问 `/v3/api-docs` 获取。

## 常用 API 列表（示例）
- /api/feeds
  - GET /api/feeds
  - POST /api/feeds
  - PUT /api/feeds/{id}
  - DELETE /api/feeds/{id}
  - POST /api/feeds/{id}/increase?qty=...
  - POST /api/feeds/{id}/decrease?qty=...

- /api/feedings
  - GET /api/feedings
  - POST /api/feedings
  - POST /api/feedings/{id}/approve?verifierId=...&notes=...
  - POST /api/feedings/{id}/reject?verifierId=...&notes=...

- /api/enclosures
  - GET /api/enclosures
  - POST /api/enclosures
  - PUT /api/enclosures/{id}
  - DELETE /api/enclosures/{id}

- /api/users
  - POST /api/users
  - GET /api/users/{id}
  - GET /api/users/by-username?username=...
  - POST /api/users/auth?username=...&password=...

- /api/permissions, /api/logs, /api/configs, /api/auth/check 等（详见 `docs/swagger.json`）


动物园管理记录

后端

创建数据库zoosystem 与模式manage ，导入脚本（顺序：schema → functions → views → triggers → data）

运行 REST API

- 激活 Spring Boot Profile 构建并运行：
  - 保证当前终端使用 JDK 17：
  - 构建打包： mvn -Pspring-boot -DskipTests package
  - 启动服务： mvn -Pspring-boot spring-boot:run
- 默认地址与文档：
  - 服务地址： http://localhost:8080
  - OpenAPI JSON： http://localhost:8080/v3/api-docs （仓库快照在 docs\swagger.json ）
  - Swagger UI： http://localhost:8080/swagger-ui/index.html

前端

打开 http://localhost:8080/ 

账户示例： admin/admin123 （ superadmin/admin123 , zhangwei/staff123 ）

登陆后可以显示自己是哪个用户。不同权限的用户所显示的功能页面不同。 
 超级管理员 (super_admin) 
 ├── 系统管理（用户、角色、权限配置） 
 ├── 所有数据的增删改查 
 ├── 审核所有操作 
 ├── 查看所有日志 
 └── 系统配置 
 
 管理员 (admin) 
 ├── 动物管理（增删改查） 
 ├── 员工管理（增删改查） 
 ├── 展区管理（增删改查） 
 ├── 饲料管理（增删改查） 
 ├── 投喂记录审核 
 └── 查看部分报表 
 
 普通员工 (staff) 
 ├── 查看动物信息 
 ├── 查看饲料信息 
 ├── 记录投喂操作（需审核） 
 └── 查看自己负责的展区 

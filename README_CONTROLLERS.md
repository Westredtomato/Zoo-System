控制器层契约与快速映射指南

本说明描述了 `org.example.controller` 下的无框架（framework-agnostic）控制器类，并示例如何快速将它们映射为 Spring Boot 的 REST 控制器，便于前端直接调用。

概述
- `org.example.controller` 中的类为纯 Java 的接口层契约（调用已有 `service` 层）。这些类作为前端对接的契约，易于移植到任何 Web 框架。

快速映射到 Spring Boot（示例）
- 下面示例展示如何将 `AnimalController` 映射为一个 Spring `@RestController`（完整示例已加入 `src/spring/java/org/example/rest/AnimalRestController.java`）：

示例（核心片段）：

```java
@RestController
@RequestMapping("/api/animals")
public class AnimalRestController {
    private final AnimalController contract = new AnimalController();

    @GetMapping
    public List<Animal> listAll() throws SQLException {
        return contract.listAll();
    }

    @GetMapping("/{id}")
    public Animal get(@PathVariable String id) throws SQLException {
        return contract.get(id);
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody Animal a) throws SQLException {
        contract.create(a);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable String id, @RequestBody Animal a) throws SQLException {
        // ensure id consistency
        a.setAnimalId(id);
        contract.update(a);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) throws SQLException {
        contract.delete(id);
        return ResponseEntity.noContent().build();
    }
}
```

示例说明：
- 在项目中我已提供 `AnimalRestController` 与 Spring Boot 启动类，放置于 `src/spring/java`，该目录在默认构建中不会被编译，除非你激活下面的 Maven profile。

如何启用并运行 Spring Web 服务
1. 使用 Maven 激活 `spring-boot` profile 编译并打包（会包含 Spring Boot 依赖）：

```powershell
mvn -Pspring-boot -DskipTests package
```

2. 启动应用（生成的 jar 在 `target`）：

```powershell
java -jar target/zoosystem-1.0-SNAPSHOT.jar
```

3. 默认端口为 `8080`，示例 API：
- GET http://localhost:8080/api/animals
- POST http://localhost:8080/api/animals

注意事项与推荐
- 异常处理：建议使用 `@ControllerAdvice` 统一将 `SQLException` 等映射为 HTTP 状态码（例如 500 / 400），并返回标准错误对象给前端。
- DTO：建议为前端创建 DTO（避免直接暴露数据库模型），并在 controller 层做转换。
- 认证与鉴权：在生产环境下建议使用 Spring Security 或在 `AuthService` 基础上实现 JWT / Session 机制，保护 API。
- Swagger / OpenAPI：可为这些 REST 控制器添加注解以自动生成接口文档，便于前端开发对接。

后续我可以为你做的事（可选）
- 将所有 `org.example.controller` 类转换成 Spring 的 `@RestController`（我可以批量生成），并在 `pom.xml` 中配置一个可运行的 Spring Boot profile（我已添加）；
- 为控制器添加 Swagger/OpenAPI 注解并生成文档；
- 为前端提供示例 Postman 集合或 curl 脚本。

启用说明：如果你希望我直接启动服务器并演示几个 API 调用，我可以运行 `mvn -Pspring-boot -DskipTests spring-boot:run` 并给出示例请求与返回。

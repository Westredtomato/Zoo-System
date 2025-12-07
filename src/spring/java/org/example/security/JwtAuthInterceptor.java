package org.example.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;
import java.util.Map;

public class JwtAuthInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String path = request.getRequestURI();
        if (isPublic(path)) return true;
        String auth = request.getHeader("Authorization");
        if (auth == null || !auth.startsWith("Bearer ")) return unauthorized(response, "missing_token");
        String token = auth.substring(7);
        Map<String, Object> payload = JwtUtil.validateToken(token);
        if (payload == null) return unauthorized(response, "invalid_token");
        request.setAttribute("userId", payload.get("sub"));
        request.setAttribute("role", payload.get("role"));
        return true;
    }

    private boolean isPublic(String path) {
        if (path == null) return true;
        // 首页与静态资源
        if (path.equals("/") || path.equals("/index.html") || path.equals("/login.html")) return true;
        if (path.startsWith("/static/")) return true;
        if (path.startsWith("/webjars/")) return true;
        if (path.endsWith(".css") || path.endsWith(".js") || path.endsWith(".png") || path.endsWith(".ico") || path.endsWith(".jpg") || path.endsWith(".svg")) return true;
        // 文档
        if (path.startsWith("/swagger-ui")) return true;
        if (path.startsWith("/v3/api-docs")) return true;
        // 登录
        if (path.startsWith("/api/users/auth")) return true;
        return false;
    }

    private boolean unauthorized(HttpServletResponse resp, String code) throws IOException {
        resp.setStatus(401);
        resp.setContentType("application/json;charset=UTF-8");
        resp.getWriter().write("{\"error\":\"" + code + "\",\"message\":\"Unauthorized\"}");
        return false;
    }
}

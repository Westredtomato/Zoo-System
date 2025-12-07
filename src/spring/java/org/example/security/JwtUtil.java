package org.example.security;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Base64;
import java.util.Map;

public class JwtUtil {
    private static final String HMAC_ALG = "HmacSHA256";
    private static String secret() {
        String s = System.getProperty("jwt.secret");
        if (s == null || s.isEmpty()) s = System.getenv("JWT_SECRET");
        if (s == null || s.isEmpty()) s = "changeit";
        return s;
    }

    public static String generateToken(String userId, String role, long ttlSeconds) {
        String headerJson = "{\"alg\":\"HS256\",\"typ\":\"JWT\"}";
        long now = Instant.now().getEpochSecond();
        long exp = now + ttlSeconds;
        String payloadJson = String.format("{\"sub\":\"%s\",\"role\":\"%s\",\"iat\":%d,\"exp\":%d}", escape(userId), escape(role), now, exp);
        String header = base64UrlEncode(headerJson.getBytes(StandardCharsets.UTF_8));
        String payload = base64UrlEncode(payloadJson.getBytes(StandardCharsets.UTF_8));
        String signature = hmacSha256(header + "." + payload, secret());
        return header + "." + payload + "." + signature;
    }

    public static Map<String, Object> validateToken(String token) {
        try {
            String[] parts = token.split("\\.");
            if (parts.length != 3) return null;
            String data = parts[0] + "." + parts[1];
            String expected = hmacSha256(data, secret());
            if (!expected.equals(parts[2])) return null;
            String payloadJson = new String(base64UrlDecode(parts[1]), StandardCharsets.UTF_8);
            Map<String, Object> payload = Json.minParse(payloadJson);
            Object exp = payload.get("exp");
            if (exp instanceof Number) {
                long expVal = ((Number) exp).longValue();
                if (Instant.now().getEpochSecond() > expVal) return null;
            }
            return payload;
        } catch (Exception e) {
            return null;
        }
    }

    private static String hmacSha256(String data, String key) {
        try {
            Mac mac = Mac.getInstance(HMAC_ALG);
            mac.init(new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), HMAC_ALG));
            byte[] raw = mac.doFinal(data.getBytes(StandardCharsets.UTF_8));
            return base64UrlEncode(raw);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static String base64UrlEncode(byte[] src) {
        return Base64.getUrlEncoder().withoutPadding().encodeToString(src);
    }
    private static byte[] base64UrlDecode(String s) { return Base64.getUrlDecoder().decode(s); }

    private static String escape(String s) { return s == null ? "" : s.replace("\\", "\\\\").replace("\"", "\\\""); }

    public static class Json {
        public static Map<String, Object> minParse(String json) {
            // naive minimal parser for flat objects with primitives
            java.util.HashMap<String, Object> map = new java.util.HashMap<>();
            String t = json.trim();
            if (t.startsWith("{") && t.endsWith("}")) t = t.substring(1, t.length()-1);
            for (String pair : t.split(",")) {
                String[] kv = pair.split(":",2);
                if (kv.length != 2) continue;
                String k = kv[0].trim();
                if (k.startsWith("\"") && k.endsWith("\"")) k = k.substring(1, k.length()-1);
                String v = kv[1].trim();
                if (v.startsWith("\"") && v.endsWith("\"")) {
                    v = v.substring(1, v.length()-1);
                    map.put(k, v);
                } else {
                    try { map.put(k, Long.parseLong(v)); } catch (Exception e) { map.put(k, v); }
                }
            }
            return map;
        }
    }
}


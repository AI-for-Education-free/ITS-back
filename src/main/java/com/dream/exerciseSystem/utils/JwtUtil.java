package com.dream.exerciseSystem.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.NoArgsConstructor;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.*;

public class JwtUtil {
    private volatile static JwtUtil instance;
    // 有效期为 60 * 60 *1000  一个小时
    public static final Long HOUR = 60 * 60 * 1000L;
    //设置秘钥明文
    private static final String JWT_KEY = "dream";
    private static final Key KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private static final JwtBuilder jwtBuilder = Jwts.builder();
    // jwt自定义字段
    private final HashMap<String, String> CLAIMS = new HashMap<>();
    // 以下是jwt payload的公共声明：发布时间、过期时间，可用时间（此时间以前不可用），主题，id，发布者，用户
    private Date issueAt;
    private Date expiration;
    private Date notBefore;
    private String subject;
    private String id;
    private String issuer;
    private String audience;

    private JwtUtil() {};

    public static JwtUtil getInstance() {
        if (instance == null) {
            // synchronized确保不会有两个线程同时进入这个块
            synchronized (JwtUtil.class) {
                if (instance == null) {
                    instance = new JwtUtil();
                }
            }
        }
        return instance;
    }

    public static Claims parsedResult(String jwt) {
        return Jwts.parserBuilder().setSigningKey(KEY).build().parseClaimsJws(jwt).getBody();
    }

    public JwtUtil issueAtBuilder(Date issueAt) {
        this.issueAt = issueAt;
        return this;
    }

    public JwtUtil expirationBuilder(Date expiration) {
        this.expiration = expiration;
        return this;
    }

    public JwtUtil notBeforeBuilder(Date notBefore) {
        this.notBefore = notBefore;
        return this;
    }

    public JwtUtil subjectBuilder(String subject) {
        this.subject = subject;
        return this;
    }

    public JwtUtil idBuilder(String id) {
        this.id = id;
        return this;
    }

    public JwtUtil issuerBuilder(String issuer) {
        this.issuer = issuer;
        return this;
    }

    public JwtUtil audienceBuilder(String audience) {
        this.audience = audience;
        return this;
    }

    public JwtUtil claimsBuilder(String key, String value) {
        CLAIMS.put(key, value);
        return this;
    }

    public String build() {
        if (!CLAIMS.isEmpty()) {
            jwtBuilder.setClaims(CLAIMS);
        }
        if (!Objects.isNull(issueAt)) {
            jwtBuilder.setIssuedAt(issueAt);
        }
        if (!Objects.isNull(expiration)) {
            jwtBuilder.setExpiration(expiration);
        }
        if (!Objects.isNull(notBefore)) {
            jwtBuilder.setNotBefore(notBefore);
        }
        if (!Objects.isNull(subject)) {
            jwtBuilder.setSubject(subject);
        }
        if (!Objects.isNull(id)) {
            jwtBuilder.setId(id);
        }
        if (!Objects.isNull(issuer)) {
            jwtBuilder.setIssuer(issuer);
        }
        if (!Objects.isNull(audience)) {
            jwtBuilder.setAudience(audience);
        }
        return jwtBuilder.signWith(KEY).compact();
    }

    public JwtUtil clear() {
        CLAIMS.clear();
        issueAt = null;
        expiration = null;
        notBefore = null;
        subject = null;
        id = null;
        issuer = null;
        audience = null;
        return this;
    }

}

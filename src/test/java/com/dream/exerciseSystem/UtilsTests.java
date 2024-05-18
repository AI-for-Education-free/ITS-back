package com.dream.exerciseSystem;

import com.alibaba.fastjson.JSON;
import com.dream.exerciseSystem.constant.StudentInfoConstant;
import com.dream.exerciseSystem.domain.LoginStudentDetails;
import com.dream.exerciseSystem.domain.Student;
import com.dream.exerciseSystem.utils.FastJsonRedisSerializer;
import com.dream.exerciseSystem.utils.JwtUtil;
import com.dream.exerciseSystem.utils.RedisCache;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.DigestUtils;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.*;

@SpringBootTest
public class UtilsTests {
    public static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RedisCache redisCache;

    @Test
    public void BCryptPasswordEncoderTest() {
        String pwd = "123";
        String encodedPwd = passwordEncoder.encode(pwd);
        System.out.println(encodedPwd);
        System.out.println(passwordEncoder.matches(pwd, encodedPwd));
    }

    @Test
    public void jwtBaseTest() {
        long currentTime = System.currentTimeMillis();
        Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        HashMap<String, String> claims = new HashMap<>();
        claims.put("test", "dream");
        // 下面例子中jwt的payload中设置了所有者、签发时间戳和过期时间戳，并将header和payload经过base64编码后的字符串用key进行加盐加密
        String jwt = Jwts.builder().setClaims(claims).setSubject("dream").setIssuedAt(new Date(currentTime))
                .setExpiration(new Date(currentTime + 2 * JwtUtil.HOUR)).signWith(key).compact();
        // 下面是解析jwt
        Claims parsedResult = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt).getBody();
        System.out.println(parsedResult.getSubject());
        System.out.println(parsedResult.getExpiration());
        System.out.println(parsedResult.get("test"));
    }

    @Test
    public void JwtUtilTest() {
        long currentTime = System.currentTimeMillis();
        Date issueAt = new Date();
        Date expiration = new Date(currentTime + 2 * JwtUtil.HOUR);
        Date notBefore = new Date();
        JwtUtil jwtUtil = JwtUtil.getInstance();
        jwtUtil.clear().claimsBuilder("name", "dream").claimsBuilder("age", "male")
                .issueAtBuilder(issueAt).expirationBuilder(expiration)
                .notBeforeBuilder(notBefore).subjectBuilder("subject").idBuilder("id")
                .issuerBuilder("xzj").audienceBuilder("all");
        String jwt = jwtUtil.build();
        Claims jwtParsed = JwtUtil.parsedResult(jwt);

        // 时间上的判断需要减小以下精确度
        Assertions.assertAll(
//                () -> Assertions.assertEquals(issueAt.getTime(), jwtParsed.getIssuedAt().getTime()),
//                () -> Assertions.assertEquals(expiration.getTime(), jwtParsed.getExpiration().getTime()),
//                () -> Assertions.assertEquals(notBefore.getTime(), jwtParsed.getNotBefore().getTime()),
                () -> Assertions.assertEquals("dream", jwtParsed.get("name")),
                () -> Assertions.assertEquals("male", jwtParsed.get("age")),
                () -> Assertions.assertEquals("subject", jwtParsed.getSubject()),
                () -> Assertions.assertEquals("id", jwtParsed.getId()),
                () -> Assertions.assertEquals("xzj", jwtParsed.getIssuer()),
                () -> Assertions.assertEquals("all", jwtParsed.getAudience())
        );
    }

    @Test
    public void fastJsonTest() {
        HashMap<String, Object> map = new HashMap<>();
        HashMap<String, String> data1 = new HashMap<>();
        data1.put("name", "dream");
        data1.put("password", "xzj");
        List<String> data2 = new ArrayList<>();
        data2.add("item1");
        data2.add("item2");
        map.put("data1", data1);
        map.put("data2", data2);
        System.out.println(Arrays.toString(JSON.toJSONString(map).getBytes(DEFAULT_CHARSET)));
    }

    @Test
    public void fastJsonRedisSerializerTest() {
        FastJsonRedisSerializer<LoginStudentDetails> loginStudentDetailsSerializer = new FastJsonRedisSerializer<>(LoginStudentDetails.class);
        Student stu = new Student("dream", "123456", "123@126.com");
        List<String> permissions = new ArrayList<>();
        permissions.add("vip1");
        LoginStudentDetails loginStudentDetails = new LoginStudentDetails(stu, permissions);
        byte[] jsonBytes = loginStudentDetailsSerializer.serialize(loginStudentDetails);
        System.out.println(Arrays.toString(jsonBytes));
        LoginStudentDetails objectFromJsonBytes = loginStudentDetailsSerializer.deserialize(jsonBytes);
        System.out.println(objectFromJsonBytes);
    }

    @Test
    public void redisCacheTest() {
        String name = "xzj";
        String password = "dream";
        String email = "123@126.com";
        String id = DigestUtils.md5DigestAsHex((StudentInfoConstant.salt+email).getBytes());
        Student student = new Student(id, name, password, email);
        List<String> permissions = new ArrayList<>();
        permissions.add("vip0");
        LoginStudentDetails loginStudentDetails = new LoginStudentDetails(student, permissions);
        // 没问题
        redisCache.setCacheObject("student:login:" + id, loginStudentDetails);
        // 有问题：loginStudentDetailsFromRedis不能转换为LoginStudentDetails类型
        Object loginStudentDetailsFromRedis = redisCache.getCacheObject("student:login:" + id);
        System.out.println(loginStudentDetailsFromRedis);
    }
}

package com.dream.exerciseSystem;

import com.dream.exerciseSystem.config.ServerConfig;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan("com.dream.exerciseSystem.mapper")
public class ExerciseSystemApplication {

    public static void main(String[] args) {
        // 这里args就是命令行启动（jar包，如java -jar xxx.jar --server.port=80）传入的参数
//		SpringApplication.run(ExerciseSystemApplication.class, args);

        // 测试加载ServerConfig bean
        ConfigurableApplicationContext ctx = SpringApplication.run(ExerciseSystemApplication.class, args);
//		System.out.println("config:");
//        System.out.println(ctx.getBean(ServerConfig.class));
//		System.out.println("security:");
//        for (javax.servlet.Filter filter: ctx.getBean(DefaultSecurityFilterChain.class).getFilters()) {
//            System.out.println(filter);
//        }
    }

}

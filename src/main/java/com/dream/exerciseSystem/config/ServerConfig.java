package com.dream.exerciseSystem.config;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.convert.DataSizeUnit;
import org.springframework.boot.convert.DurationUnit;
import org.springframework.stereotype.Component;
import org.springframework.util.unit.DataSize;
import org.springframework.util.unit.DataUnit;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.time.Duration;
import java.time.temporal.ChronoUnit;



@Data
@Component
// 配置文件中是myServer，这里可以是my-server、my_server、myserver
@ConfigurationProperties("my-server")
// 开启校验
@Validated
public class ServerConfig {
    private String ip;
    @Max(value = 10000, message = "最大值不能超过10000")
    @Min(value = 1024, message = "最小值不能小于1024")
    private int port;
    @DurationUnit(ChronoUnit.HOURS)
    private Duration time;
    @DataSizeUnit(DataUnit.MEGABYTES)
    private DataSize size;
}

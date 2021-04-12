package com.wlw.consumerdemo;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDubbo
@EnableHystrix
@ComponentScan({"com.wlw.common", "com.wlw.consumerdemo"})
public class ConsumerDemoApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(ConsumerDemoApplication.class);
        app.setRegisterShutdownHook(false);
        app.run(args);
    }

}
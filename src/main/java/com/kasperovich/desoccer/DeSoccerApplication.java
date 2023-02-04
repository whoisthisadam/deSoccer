package com.kasperovich.desoccer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.EnableAspectJAutoProxy;


@EnableAspectJAutoProxy(proxyTargetClass = true)
@SpringBootApplication
@EnableCaching
public class DeSoccerApplication {
    public static void main(String[] args) {
        SpringApplication.run(DeSoccerApplication.class, args);
    }
}

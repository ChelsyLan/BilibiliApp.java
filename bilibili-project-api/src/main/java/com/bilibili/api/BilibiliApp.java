package com.bilibili.api;
//项目入口
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.bilibili.dao")
public class BilibiliApp {

    public static void main(String[] args) {
        ApplicationContext app=SpringApplication.run(BilibiliApp.class, args);


    }
}

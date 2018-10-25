package com.meteor.curator.web.admin;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

/**
 * Created by y747718944 on 2018/1/23
 * admin服务
 */
@SpringBootApplication
@ServletComponentScan("com.meteor.curator.web")
//@EnableAceCache
public class AdminApplication {
    public static void main(String[] args) {
        SpringApplication.run(AdminApplication.class, args);
    }

}

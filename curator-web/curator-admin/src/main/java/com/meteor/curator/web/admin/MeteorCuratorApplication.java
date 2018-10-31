package com.meteor.curator.web.admin;


import com.meteor.curator.core.ZKUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.env.YamlPropertySourceLoader;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.ByteArrayResource;

import java.io.IOException;
import java.util.Map;

/**
 * Created by Meteor
 * admin服务
 */
@SpringBootApplication
@ServletComponentScan("com.meteor.curator.web")
public class MeteorCuratorApplication {

    public static void main(String[] args) throws Exception {
        SpringApplication app = new SpringApplicationBuilder(MeteorCuratorApplication.class).build();

        String data = ZKUtil.getData("/cloud/meteor/curator/prod");
        beforeRun(app, data);
        app.run(args);
    }


    @SuppressWarnings("unchecked")
    private static void beforeRun(SpringApplication app, String data) throws IOException {
        YamlPropertySourceLoader yamlLoader = new YamlPropertySourceLoader();
        ByteArrayResource byteResource = new ByteArrayResource(data.getBytes());
        PropertySource<?> propertySource = yamlLoader.load("applicationConfig: [classpath:/application.yml]",
                byteResource, null);
        Map<String, Object> source = (Map<String, Object>) propertySource.getSource();
        app.setDefaultProperties(source);
    }

}

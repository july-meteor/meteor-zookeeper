package com.meteor.curator.web.admin.config;

import com.meteor.curator.core.config.CuratorConfig;
import com.meteor.curator.core.utils.lib.StrUtil;
import com.meteor.curator.web.admin.utils.PropertiesLoaderUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * created by Meteor on 2018/10/28
 */
@Configuration
public class MeteorCuratorConfig {

//    private  static Logger log = LoggerFactory.getLogger(MeteorCuratorConfig.class);

    @Autowired
    private Environment env;
    private String servers;
    private String rootPath;
    private String connCount;
    private String connInterval;

    private String sesstionTimeOut;
    private String connTimtOut;
    private List<String>  listener;
    private List<String>  listenerIgnores;

    @PostConstruct
    public  void init(){
//        log.info("Meteot-Curator Config reading");
        PropertiesLoaderUtils prop = new PropertiesLoaderUtils("application.properties");
        servers = prop.getProperty("zk.servers");
        if(StrUtil.isBlank(servers)){
            servers = env.getProperty("zk.servers");
            rootPath = env.getProperty("zk.rootPath");
            connCount = env.getProperty("zk.connCount");
            connInterval = env.getProperty("zk.connInterval");
            sesstionTimeOut = env.getProperty("zk.sesstionTimeOut");
            connTimtOut = env.getProperty("zk.connTimtOut");
            listener = env.getProperty("zk.listener",List.class);
            listenerIgnores = env.getProperty("zk.listenerIgnores",List.class);
        } else{
            rootPath = prop.getProperty("zk.rootPath");
            connCount = prop.getProperty("zk.connCount");
            connInterval = prop.getProperty("zk.connInterval");
            sesstionTimeOut = prop.getProperty("zk.sesstionTimeOut");
            connTimtOut = prop.getProperty("zk.connTimtOut");
            prop.getProperty("zk.listener");
            prop.getProperty("zk.listenerIgnores");
        }
        CuratorConfig.init(servers,rootPath,
                StrUtil.isEmpty(connCount)?null:Integer.parseInt(connCount),
                StrUtil.isEmpty(connInterval)?null:Integer.parseInt(connInterval),
                StrUtil.isEmpty(sesstionTimeOut)?null:Integer.parseInt(sesstionTimeOut),
                StrUtil.isEmpty(connTimtOut)?null:Integer.parseInt(connTimtOut),
                listener,listenerIgnores);

    }
}

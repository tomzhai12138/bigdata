package com.data.bigdata.common;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author:
 * @description:  通过反射加载配置文件 this.getClass().getClassLoader().getResourceAsStream(path)
 * @Date:Created in 2019-11-07 10:34
 */
public class ConfigUtil {

    private static Logger LOG = LoggerFactory.getLogger(ConfigUtil.class);

    private static ConfigUtil configUtil;

    public static ConfigUtil getInstance(){

        if(configUtil == null){
            configUtil = new ConfigUtil();
        }
        return configUtil;
    }


    public Properties getProperties(String path){
        Properties properties = new Properties();
        try {
            LOG.info("开始加载配置文件" + path);
            //得到当前对象的当前类加载器加载
            InputStream insss = this.getClass().getClassLoader().getResourceAsStream(path);
            properties = new Properties();
            properties.load(insss);
        } catch (IOException e) {
            LOG.info("加载配置文件" + path + "失败");
            LOG.error(null,e);
        }

        LOG.info("加载配置文件" + path + "成功");
        System.out.println("文件内容："+properties);
        return properties;
    }

}

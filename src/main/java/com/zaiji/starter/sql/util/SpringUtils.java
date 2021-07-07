package com.zaiji.starter.sql.util;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.Properties;

/**
 * 一些spring的util，主要用于静态方法中从spring中加载某些东西
 *
 * @author zaiji
 * @date 2021/06/07
 */
@Component
@Log4j2
public final class SpringUtils implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    private static Properties properties = new Properties();

    static {
        //加载所有配置文件
        try {
            //jar包内部的配置文件
            final ClassPathResource classPathResource = new ClassPathResource("application.properties");
            final Properties classpathRootproperties = PropertiesLoaderUtils.loadProperties(classPathResource);
            properties.putAll(classpathRootproperties);

            final FileSystemResource fileSystemResource = new FileSystemResource("config/application.properties");
            //jar包外部的文件夹
            Properties fileRootProperties = PropertiesLoaderUtils.loadProperties(fileSystemResource);
            properties.putAll(fileRootProperties);
        } catch (Exception e) {
            throw new RuntimeException("加载配置文件失败");
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringUtils.applicationContext = applicationContext;
    }

    public static <T> T getBean(Class<T> clazz) {
        return applicationContext.getBean(clazz);
    }

    public static <T> T getBean(Class<T> clazz, String beanName) {
        return applicationContext.getBean(clazz, beanName);
    }

    /**
     * 获得resource文件夹下的文件输入流
     *
     * @param fileRelativePath 蚊香相对路径（即resource下的路径，例如:mapper/mysql/audit/AuditConfigMapper.xml）
     */
    public static InputStream getClassPathFileInputStream(String fileRelativePath) throws Exception {
        return new ClassPathResource(fileRelativePath).getInputStream();
    }

    /**
     * 获得指定类型配置项
     *
     * @param keyName key值
     */
    public static String getConfigValue(String keyName) {
        return properties.getProperty(keyName);
    }


}

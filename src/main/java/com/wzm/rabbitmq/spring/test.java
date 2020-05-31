package com.wzm.rabbitmq.spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by Administrator on 2020/4/25 20:08
 */
public class test {
    public static void main(String[] args) {
        String path="app-config-context.xml";
        ApplicationContext applicationContext =new ClassPathXmlApplicationContext(path);
        applicationContext.getBean(foo.class).lisen();
    }
}

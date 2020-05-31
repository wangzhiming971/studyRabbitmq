package com.wzm.rabbitmq.util;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * Created by Administrator on 2020/4/12 15:58
 * 获取MQ的连接
 */
public class ConnectionUtil {
    public static Connection getConnection() throws Exception {
        //定义连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        //设置服务地址
        factory.setHost("localhost");
        //设置端口
        factory.setPort(5672);
        //设置账号信息，用户名、密码、vhost
        factory.setVirtualHost("testhost");
        factory.setUsername("wangzhiming");
        factory.setPassword("wangtian520");
        // 通过工程获取连接
        Connection connection = factory.newConnection();
        return connection;
    }
}


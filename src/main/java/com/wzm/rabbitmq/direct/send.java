package com.wzm.rabbitmq.direct;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.wzm.rabbitmq.util.ConnectionUtil;

/**
 * Created by Administrator on 2020/4/25 12:46
 * 直接模式下根据路由键发送消息
 */
public class send {
    private static final String EXCHANGE_NAME = "text_exchange_direct";

    public static void main(String[] args) throws Exception {
        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(EXCHANGE_NAME, "direct");
        String msg = "Hellow direct";
        String routingKey = "info";
        channel.basicPublish(EXCHANGE_NAME, routingKey, null, msg.getBytes());
        System.out.println("sed:" + msg);
        channel.close();
        connection.close();
    }
}

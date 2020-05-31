package com.wzm.rabbitmq.topic;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.wzm.rabbitmq.util.ConnectionUtil;

/**
 * Created by Administrator on 2020/4/25 14:01
 * 主题模式下采用通配符操作
 */
public class send {
    private static final String EXCHANGE_NAME = "test_exchange_topic";

    public static void main(String[] args) throws Exception {
        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(EXCHANGE_NAME, "topic");
        String msg = "今天很开心";
        String routingKey = "good.add.kkkk";
      /*解决消息在队列和消费者持久化后电脑重启或宕机的情况消息丢失
        AMQP.BasicProperties.Builder builder = new AMQP.BasicProperties.Builder().deliveryMode(2);
        AMQP.BasicProperties basicProperties = builder.build();*/
        channel.basicPublish(EXCHANGE_NAME, routingKey, null, msg.getBytes());
        System.out.println("send:" + msg);
        channel.close();
        connection.close();
    }
}

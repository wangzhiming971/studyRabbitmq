package com.wzm.rabbitmq.simple;

import com.rabbitmq.client.*;
import com.wzm.rabbitmq.util.ConnectionUtil;

import java.io.IOException;

/**
 * Created by Administrator on 2020/4/12 16:33
 * 消费者监听队列消费生产者发布的信息
 */
public class Recv {
    private final  static  String QUEUE_NAME="test_simple";
    public static void main(String[] args) throws Exception{
        // 获取到连接以及mq通道
        Connection connection = ConnectionUtil.getConnection();
        // 从连接中创建通道
        Channel channel = connection.createChannel();
        // 声明队列
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        // 定义队列的消费者
        QueueingConsumer consumer = new QueueingConsumer(channel);
        /** 监听队列，false表示手动返回完成状态，true表示自动
         * 参数一：队列名称
         * 参数二：true-自动回复 false-手动回复完成状态
         */
        channel.basicConsume(QUEUE_NAME, true, consumer);
        // 获取消息
        while (true) {
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            String message = new String(delivery.getBody());
            System.out.println(" 消费者监听消息:'" + message + "'");
        }
    }
}

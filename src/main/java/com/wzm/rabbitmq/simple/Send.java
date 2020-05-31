package com.wzm.rabbitmq.simple;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.wzm.rabbitmq.util.ConnectionUtil;

/**
 * Created by Administrator on 2020/4/12 16:06
 * 生产者发送消息到队列
 * 2.5 简单队列的不足
 * 耦合性高，生产者一一对应消费者（如果想要多个消费者消费队列中的消息，这时候就不行了）。
 * 队列名变更。这时候得同时变更
 */
public class Send {
 private final  static  String QUEUE_NAME="test_simple";

    public static void main(String[] args) throws Exception {
        // 获取到连接以及mq通道
        Connection connection = ConnectionUtil.getConnection();
        //从连接中创建通道
        Channel channel = connection.createChannel();
        // 声明（创建）队列
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        // 消息内容
        String message = "Hello World!";
        channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
        System.out.println(" 生产者发送消息: '" + message + "'");
        //关闭通道和连接
        channel.close();
        connection.close();
    }
}

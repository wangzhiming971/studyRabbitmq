package com.wzm.rabbitmq.simple;

import com.rabbitmq.client.*;
import com.sun.xml.internal.ws.api.streaming.XMLStreamReaderFactory;
import com.wzm.rabbitmq.util.ConnectionUtil;

import java.io.IOException;

/**
 * Created by Administrator on 2020/4/12 16:33
 * 消费者监听队列消费生产者发布的信息
 */
public class Recv1 {
    private final  static  String QUEUE_NAME="test_simple";
    public static void main(String[] args) throws Exception {
        // 获取到连接以及mq通道
        Connection connection = ConnectionUtil.getConnection();
        // 从连接中创建通道
        Channel channel = connection.createChannel();
        // 声明队列
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
         DefaultConsumer defaultConsumer=  new DefaultConsumer(channel){
           @Override
           public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
            String msg =  new String(body,"UTF-8");
               System.out.println(msg);
           }
       };
        channel.basicConsume(QUEUE_NAME,true,defaultConsumer);
    }
}

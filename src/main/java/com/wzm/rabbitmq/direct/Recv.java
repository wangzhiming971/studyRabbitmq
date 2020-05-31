package com.wzm.rabbitmq.direct;

import com.rabbitmq.client.*;
import com.wzm.rabbitmq.util.ConnectionUtil;

import java.io.IOException;

/**
 * Created by Administrator on 2020/4/25 12:54
 */
public class Recv {
    private static final String EXCHANGE_NAME = "text_exchange_direct";
    private static final String QUEUE_NAME = "text_queue_direct_01";

    public static void main(String[] args) throws Exception {
        final Connection connection = ConnectionUtil.getConnection();
        final Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        channel.basicQos(1);
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "error");
        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String msg = new String(body, "UTF-8");
                System.out.println("direct [1]:" + msg);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    channel.basicAck(envelope.getDeliveryTag(),false);
                    System.out.println("done");
                }
                channel.close();
                connection.close();
            }
        };
        //队列名称、回答模式、消费者
        channel.basicConsume(QUEUE_NAME,false,consumer);
    }
}

package com.wang.confirm;

import com.rabbitmq.client.*;
import com.wang.simple.ConnectionUtil;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class RecvConfirm1 {
    private static final String EXCHANGER_NAME = "confirm-exchange";
    private static final String QUEUE_NAME = "confirm-queue1";

    public static void main(String[] args) throws IOException {
        Connection connection = ConnectionUtil.getConnection();
        final Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME, true, false, false, null);
        channel.queueBind(QUEUE_NAME, EXCHANGER_NAME, "*.*.topic");
        channel.basicQos(1);
        DefaultConsumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("conform2模式下接受消息:" +QUEUE_NAME+ new String(body, StandardCharsets.UTF_8));
                try {
                    Thread.sleep(1000);
                    //获取服务提供者消息
                    channel.basicAck(envelope.getDeliveryTag(), false);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        channel.basicConsume(QUEUE_NAME, false, consumer);
    }
}

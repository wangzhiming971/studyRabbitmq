package com.wzm.rabbitmq.topic;

import com.rabbitmq.client.*;
import com.wzm.rabbitmq.util.ConnectionUtil;

import java.io.IOException;

/**
 * Created by Administrator on 2020/4/25 14:06
 */
public class Recv {
    private static final String EXCHANGE_NAME = "test_exchange_topic";
    private static final String QUEUE_NAME = "test_queue_topic_1";

    public static void main(String[] args) throws Exception {
        final Connection connection = ConnectionUtil.getConnection();
        final Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        channel.basicQos(1);
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "good.add");
        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String msg = new String(body, "UTF-8");
                System.out.println("topic【1】模式下:" + msg);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    channel.basicAck(envelope.getDeliveryTag(),false);
                }
                channel.close();
                connection.close();
            }
        };
      channel.basicConsume(QUEUE_NAME,false,consumer);
    }
}

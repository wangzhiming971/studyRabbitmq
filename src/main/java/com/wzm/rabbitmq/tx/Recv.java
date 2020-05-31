package com.wzm.rabbitmq.tx;

import com.rabbitmq.client.*;
import com.wzm.rabbitmq.util.ConnectionUtil;

import java.io.IOException;

/**
 * Created by Administrator on 2020/4/25 14:53
 */
public class Recv {
    private static final String QUEUE_NAME = "test_queue_transaction";

    public static void main(String[] args) throws Exception {
        final Connection connection = ConnectionUtil.getConnection();
        final Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        channel.basicConsume(QUEUE_NAME, true, new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String msg = new String(body, "UTF-8");
                System.out.println("TX[1]接受消息:" + msg);
                channel.close();
                connection.close();
            }
        });
    }
}

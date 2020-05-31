package com.wzm.rabbitmq.confirm;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.wzm.rabbitmq.util.ConnectionUtil;

/**
 * Created by Administrator on 2020/4/25 15:27
 * 串行 confirm模式
 */
public class Send2 {
    private static final String QUEUE_NAME = "test_queue_confirm_2";

    public static void main(String[] args) throws Exception {
        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();
        //生产者调用confirmSelect，将channel设置为confirm模式注意前面如果采用事务模式将会报错
        channel.confirmSelect();
        for (int i = 0; i < 50; i++) {
            String msg = "channel设置为confirm模式"+i;
            channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());
            System.out.println(msg);
        }
        if (channel.waitForConfirms()) {
            System.out.println("message send ok");
        } else {
            System.out.println("message send failed");
        }
        channel.close();
        connection.close();
    }
}

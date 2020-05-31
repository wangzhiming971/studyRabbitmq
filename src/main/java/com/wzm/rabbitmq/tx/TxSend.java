package com.wzm.rabbitmq.tx;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.sun.org.apache.xpath.internal.objects.XString;
import com.wzm.rabbitmq.util.ConnectionUtil;

import java.io.IOException;

/**
 * Created by Administrator on 2020/4/25 14:47
 */
public class TxSend {
    private static final String QUEUE_NAME = "test_queue_transaction";

    public static void main(String[] args) throws Exception {
        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        String msg = "transaction_hh";
        try {
            channel.txSelect();
            channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());
            int i = 1 / 0;
            System.out.println("TX send:" + msg);
            channel.txCommit();
        } catch (Exception  e) {
            channel.txRollback();
            System.out.println("数据回滚了!");
        } finally {
            channel.close();
            connection.close();
        }
    }
}

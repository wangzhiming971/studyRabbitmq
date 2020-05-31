package com.wzm.rabbitmq.confirm;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.wzm.rabbitmq.util.ConnectionUtil;

/**
 * Created by Administrator on 2020/4/25 15:27
 * 串行 confirm模式
 */
public class Send1 {
    private static final String QUEUE_NAME="test_queue_confirm";
    public static void main(String[] args) throws Exception {
        Connection connection = ConnectionUtil.getConnection();
        Channel channel  = connection.createChannel();
        //生产者调用confirmSelect，将channel设置为confirm模式注意前面如果采用事务模式将会报错
        channel.confirmSelect();
        String msg="channel设置为confirm模式";
        channel.basicPublish("",QUEUE_NAME,null,msg.getBytes());
        System.out.println(msg);
        //确认
        if (channel.waitForConfirms()){
            System.out.println("message send ok");
        }else{
            System.out.println("message send failed");
        }
        channel.close();
        connection.close();
    }
}

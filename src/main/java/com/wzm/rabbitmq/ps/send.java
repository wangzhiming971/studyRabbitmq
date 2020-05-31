package com.wzm.rabbitmq.ps;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.wzm.rabbitmq.util.ConnectionUtil;

/**
 * Created by Administrator on 2020/4/25 11:46
 * 订阅者模式
 */
public class send {
    private  static  final String EXCHANGE_NAME="test_exchange_fanout";
    public static void main(String[] args) throws Exception {
        //创建连接
       Connection connection = ConnectionUtil.getConnection();
       //创建通道
        Channel channel = connection.createChannel();
        //声明交换机
         channel.exchangeDeclare(EXCHANGE_NAME,"fanout");
        String msg="订阅者模式[1]下发送消息";
        //交换机名称、队列名称、路由键、发送消息
        channel.basicPublish(EXCHANGE_NAME,"",null,msg.getBytes());
        System.out.println(msg);
        channel.close();
        connection.close();
    }
}

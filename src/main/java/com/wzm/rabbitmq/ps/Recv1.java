package com.wzm.rabbitmq.ps;

import com.rabbitmq.client.*;
import com.wzm.rabbitmq.util.ConnectionUtil;

import java.io.IOException;

/**
 * Created by Administrator on 2020/4/25 11:55
 * 订阅者模式一个生产者发送给交换机绑定多个队列消费同一个信息
 */
public class Recv1 {
    private static final String QUEUE_NAME="test_exchange_sms";
    private static final String EXCHANGE_NAME="test_exchange_fanout";
    public static void main(String[] args) throws Exception {
        //创建连接
        Connection connection  = ConnectionUtil.getConnection();
        //创建通道
       final Channel channel = connection.createChannel();
        //声明队列 队列名称、是否持久化、是否自动删除、、
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        //服务器每次向队列发送一条数据
        channel.basicQos(1);
        //绑定交换机
        channel.queueBind(QUEUE_NAME,EXCHANGE_NAME,"");
        //创建消费者
        Consumer consumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String msg = new String(body,"UTF-8");
                System.out.println("消费者短信消费信息:"+msg);
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    System.out.println("[1] done");
                    channel.basicAck(envelope.getDeliveryTag(),false);
                }
                channel.close();
            }
        };
        //手动执行回执
        channel.basicConsume(QUEUE_NAME,false,consumer);
    }
}

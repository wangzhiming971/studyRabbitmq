package com.wzm.rabbitmq.work;

import com.rabbitmq.client.*;
import com.wzm.rabbitmq.util.ConnectionUtil;

import java.io.IOException;

/**
 * Created by Administrator on 2020/4/24 20:25
 */
public class Recv2 {
    private static final String QUEUE_NAME="test_work";
    public static void main(String[] args) throws Exception {
        //获取连接
        Connection connection = ConnectionUtil.getConnection();
        //创建通道
        final  Channel channel =  connection.createChannel();
        /**创建队列
         * 参数一：队列名称
         * 参数二：是否持久化
         * 参数三：是否自动删除队列
         */
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        channel.basicQos(1);//保证每次只分发一个
        //创建消费者
        Consumer consumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String msg =new String(body,"UTF-8");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally{
                    channel.basicAck(envelope.getDeliveryTag(),false);
                }
                System.out.println("消费者【2】:"+msg);
            }
        };
        /*QueueingConsumer queueingConsumer= new QueueingConsumer(channel);*/
        /** 监听队列，false表示手动返回完成状态，true表示自动
         * 参数一：队列名称
         * 参数二：true-自动回复 false-手动回复完成状态
         */
        channel.basicConsume(QUEUE_NAME,false,consumer);
    }
}

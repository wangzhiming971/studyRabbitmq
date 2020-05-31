package com.wzm.rabbitmq.work;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.wzm.rabbitmq.util.ConnectionUtil;

/**
 * Created by Administrator on 2020/4/14 22:38
 * woker模式
 *
 */
public class Send2 {
    private  static String QUEUE_NAME="test_work";
    public static void main(String[] args) throws Exception {
        Connection connection = ConnectionUtil.getConnection();
        //创建信道
        Channel channel = connection.createChannel();
        /**创建队列
         * 参数一：队列名称
         * 参数二：是否持久化
         * 参数三：是否自动删除队列
         */
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        /**
         * 每个消费者发送确认消息之前，消息队列不发送下一条消息到消费者，一次只处理一个消息
         * 限制发送给同一个消费者 不得超过一条消息
         * 使用basicQos（prefetch=1）,即每次取完之后会对消息队列反馈。
         * （注意：使用公平分发，必须关闭自动应答ack，改成手动）
         */
        channel.basicQos(1);
        for(int i=1;i<100;i++){
            String msg = "罗志祥，你好呀！"+i;
            channel.basicPublish("",QUEUE_NAME,null,msg.getBytes());
            System.out.println("work模式下发送消息:"+msg);
            Thread.sleep(1);
        }
        channel.close();
        connection.close();
    }
}

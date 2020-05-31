package com.wzm.rabbitmq.work;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.QueueingConsumer;
import com.wzm.rabbitmq.util.ConnectionUtil;

import java.io.IOException;

/**
 * Created by Administrator on 2020/4/14 22:38
 * woker模式
 * 消费者1和消费者处理得消息数量是一样的，你一个我一个
 * 这种方式叫做轮询分发，就是不管哪个消费者效率较高，处理速度较快，平均分
 */
public class Send {
    private  static final String QUEUE_NAME="test_work";
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

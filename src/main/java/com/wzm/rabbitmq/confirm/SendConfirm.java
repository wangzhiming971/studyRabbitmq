package com.wang.confirm;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmListener;
import com.rabbitmq.client.Connection;
import com.wang.simple.ConnectionUtil;

import java.io.IOException;
import java.util.Collections;
import java.util.SortedSet;
import java.util.TreeSet;

public class SendConfirm {
    private  static final String EXCHANGER_NAME="confirm-exchange";
    public static void main(String[] args) throws IOException {
        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(EXCHANGER_NAME,"topic");
        channel.confirmSelect();
        final SortedSet<Long> sortedSet = Collections.synchronizedSortedSet(new TreeSet<Long>());
        channel.addConfirmListener(new ConfirmListener() {
            @Override
            public void handleAck(long deliveryTag, boolean multiple) throws IOException {
                System.out.println("消息消费成功:"+deliveryTag);
                if (multiple){
                    sortedSet.headSet(deliveryTag+1).clear();
                }else{
                    sortedSet.remove(deliveryTag);
                }
            }

            @Override
            public void handleNack(long deliveryTag, boolean multiple) throws IOException {
                System.out.println("消息消费失败:"+deliveryTag);
                if (multiple){
                    sortedSet.headSet(deliveryTag+1).clear();
                }else{
                    sortedSet.remove(deliveryTag);
                }
            }
        });

        for(int i=0;i<100;i++){
            String msg="异步confirm模式下"+i;
            try {
                //System.out.println("confirm模式下发送消息:"+msg);
                long seqNo = channel.getNextPublishSeqNo();
                //设置消息持久化
                AMQP.BasicProperties basicProperties = new AMQP.BasicProperties.Builder().deliveryMode(2).build();
                //builder.deliveryMode(2);//1-单线程  2-持久化
                channel.basicPublish(EXCHANGER_NAME,"*.wang.topic",basicProperties,msg.getBytes());
                sortedSet.headSet(seqNo);
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}

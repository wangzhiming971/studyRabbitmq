package com.wzm.rabbitmq.confirm;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmListener;
import com.rabbitmq.client.Connection;
import com.wzm.rabbitmq.util.ConnectionUtil;

import java.io.IOException;
import java.util.Collections;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Created by Administrator on 2020/4/25 15:27
 * 串行 confirm模式 异步
 */
public class Send3 {
    private static final String QUEUE_NAME = "test_queue_confirm_3";

    public static void main(String[] args) throws Exception {
       Connection connection = ConnectionUtil.getConnection();
       Channel channel = connection.createChannel();
        //生产者调用confirmSelect，将channel设置为confirm模式注意前面如果采用事务模式将会报错
        channel.confirmSelect();
        //未确认消息标识
        final SortedSet<Long> confirmSet =  Collections.synchronizedSortedSet(new TreeSet<Long>());
       channel.addConfirmListener(new ConfirmListener() {
           @Override
           public void handleAck(long deliveryTag, boolean multiple) throws IOException {
               if(multiple){
                   System.out.println("===handleAck====multiple");
                   confirmSet.headSet(deliveryTag+1).clear();
               }else{
                   System.out.println("===handleAck====multiple==false");
                   confirmSet.remove(deliveryTag);
               }
           }

           @Override
           public void handleNack(long deliveryTag, boolean multiple) throws IOException {
               if(multiple){
                   System.out.println("===handleNack====multiple");
                   confirmSet.headSet(deliveryTag+1).clear();
               }else{
                   System.out.println("===handleNack====multiple==false");
                   confirmSet.remove(deliveryTag);
               }
           }
       });
       String msg="今天不开心";
        while(true){
            long seqNo=channel.getNextPublishSeqNo();
            channel.basicPublish("",QUEUE_NAME,null,msg.getBytes());
            confirmSet.add(seqNo);
        }
    }
}

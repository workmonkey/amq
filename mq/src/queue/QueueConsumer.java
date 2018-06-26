package queue;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class QueueConsumer {

   public  static void listener(){
       String cluster ="failover:(tcp://127.0.0.1:61616,tcp://127.0.0.1:61618)";
       //连接工厂
       ActiveMQConnectionFactory factory =new ActiveMQConnectionFactory(cluster);
       try {
           //创建会话
           Connection con = factory.createConnection();
           //开启
           con.start();
           //创建会话
           Session session = con.createSession(false, Session.AUTO_ACKNOWLEDGE);
           //创建队列
           Queue first = session.createQueue("first");
           //创建消费者
           MessageConsumer consumer = session.createConsumer(first);
           //注册监听器，接收消息
           consumer.setMessageListener(new MessageListener() {
               @Override
               public void onMessage(Message message) {
                   TextMessage msg = (TextMessage) message;
                   try {
                       System.out.println("收到的消息"+msg.getText());
                   } catch (JMSException e) {
                       e.printStackTrace();
                   }
               }
           });


       } catch (JMSException e) {
           e.printStackTrace();
       }
   }

    public static void main(String[] args) {
        listener();
    }
}

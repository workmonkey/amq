package queue;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * 消息的生产者
 */
public class QueueProducer {
    public static  void send(){
        String cluster ="failover:(tcp://127.0.0.1:61616,tcp://127.0.0.1:61618)";
        //连接工厂   tcp长连接  传输控制协议
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(cluster);
        try {
            //从工厂获取连接
            Connection con = factory.createConnection();
            //使用时再创建会话，false ，Session.AUTO_ACKNOWLEDGE 自动确认
            Session session = con.createSession(false, Session.AUTO_ACKNOWLEDGE);
            //创建队列
            Queue queue = session.createQueue("first");

            //创建生产者
            MessageProducer producer = session.createProducer(queue);
            //添加消息
            TextMessage msg = session.createTextMessage("第三条消息：helloworld");
            //发生消息
            producer.send(msg);
            session.close();

        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        send();
    }
}

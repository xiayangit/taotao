package activemq;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.junit.Test;

public class test1 {

	@Test
	public void test1() throws JMSException {
		//创建连接工厂
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.128.129:61616");
		//根据连接工厂创建单个连接
		Connection createConnection = connectionFactory.createConnection();
		//开启连接
		createConnection.start();
		//创建session对象 选择session的模式
		//第一个是 是否开启事务，一般不使用事务
		//如果第一个参数为true,则无视第二个参数.如果为false,第二个参数为消息的答复模式,即通知该队列消费者已收到消息,自动与手动两种
		Session session = createConnection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		//通过session创建消息管道(队列)
		Queue queue = session.createQueue("test-queue"); // 一对一
		//session.createTopic(topicName); // 一对多
		//通过session创建生产者
		MessageProducer createProducer = session.createProducer(queue); //该生产者指定向该队列发送消息
		//通过session创建消息
		TextMessage createTextMessage = session.createTextMessage("hahahahhaha");
		//发送消息
		createProducer.send(createTextMessage);
		//关闭连接
		createProducer.close();
		session.close();
		createConnection.close();
	}
}

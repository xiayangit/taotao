package activemq;

import java.io.IOException;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.junit.Test;

public class test1 {

	// 一对一发送 queue
	@Test
	public void send() throws JMSException {
		// 创建连接工厂
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.128.129:61616");
		// 根据连接工厂创建单个连接
		Connection createConnection = connectionFactory.createConnection();
		// 开启连接
		createConnection.start();
		// 创建session对象 选择session的模式
		// 第一个是 是否开启事务，一般不使用事务
		// 如果第一个参数为true,则无视第二个参数.如果为false,第二个参数为消息的答复模式,即通知该队列消费者已收到消息,自动与手动两种
		Session session = createConnection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		// 通过session创建消息管道(队列)
		Queue queue = session.createQueue("test-queue"); // 一对一
		// session.createTopic(topicName); // 一对多
		// 通过session创建生产者 (消息的发送者)
		MessageProducer createProducer = session.createProducer(queue); // 该生产者指定向该队列发送消息
		// 通过session创建消息
		TextMessage createTextMessage = session.createTextMessage("666666");
		// 发送消息
		createProducer.send(createTextMessage);
		// 关闭连接
		createProducer.close();
		session.close();
		createConnection.close();
	}

	// 一对一接收
	@Test
	public void receive() throws JMSException, IOException {
		// 创建连接工厂
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.128.129:61616");
		// 根据连接工厂创建单个连接
		Connection createConnection = connectionFactory.createConnection();
		// 开启连接
		createConnection.start();
		// 创建session对象 选择session的模式
		// 第一个是 是否开启事务，一般不使用事务
		// 如果第一个参数为true,则无视第二个参数.如果为false,第二个参数为消息的答复模式,即通知该队列消费者已收到消息,自动与手动两种
		Session session = createConnection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		// 通过session创建消息管道(队列)
		Queue queue = session.createQueue("test-queue"); // 一对一
		// session.createTopic(topicName); // 一对多
		// 通过session创建消费者 (消息的接收者) 并指定一个消息管道
		MessageConsumer consumer = session.createConsumer(queue);
		// 为消费者创建一个消息监听器
		consumer.setMessageListener(new MessageListener() {

			@Override
			public void onMessage(Message message) {
				// 判断该消息是否为TextMessage
				if (message instanceof TextMessage) {
					TextMessage m = (TextMessage) message;
					String text;
					try {
						text = m.getText();
						System.out.println(text);
					} catch (JMSException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			}
		});
		// 等待键盘输入 模拟线程等待
		System.in.read();
		// 关闭连接
		consumer.close();
		session.close();
		createConnection.close();
	}

	// -----------------------------------------------------------------------------------------------------------------

	// 一对多发送 topic
	@Test
	public void sendTopic() throws JMSException {
		// 创建连接工厂
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.128.129:61616");
		// 根据连接工厂创建单个连接
		Connection createConnection = connectionFactory.createConnection();
		// 开启连接
		createConnection.start();
		// 创建session对象 选择session的模式
		// 第一个是 是否开启事务，一般不使用事务
		// 如果第一个参数为true,则无视第二个参数.如果为false,第二个参数为消息的答复模式,即通知该队列消费者已收到消息,自动与手动两种
		Session session = createConnection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		// 通过session创建消息管道(队列)
		// Queue queue = session.createQueue("test-queue"); // 一对一
		Topic topic = session.createTopic("test-topic"); // 一对多
		// 通过session创建生产者 (消息的发送者)
		MessageProducer createProducer = session.createProducer(topic); // 该生产者指定向该队列发送消息
		// 通过session创建消息
		TextMessage createTextMessage = session.createTextMessage("666666");
		// 发送消息
		createProducer.send(createTextMessage);
		// 关闭连接
		createProducer.close();
		session.close();
		createConnection.close();
	}

	// 一对多接收 topic
	@Test
	public void receiveTopic() throws JMSException, IOException {
		// 创建连接工厂
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.128.129:61616");
		// 根据连接工厂创建单个连接
		Connection createConnection = connectionFactory.createConnection();
		// 开启连接
		createConnection.start();
		// 创建session对象 选择session的模式
		// 第一个是 是否开启事务，一般不使用事务
		// 如果第一个参数为true,则无视第二个参数.如果为false,第二个参数为消息的答复模式,即通知该队列消费者已收到消息,自动与手动两种
		Session session = createConnection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		// 通过session创建消息管道(队列)
		// Queue queue = session.createQueue("test-queue"); // 一对一
		Topic topic = session.createTopic("test-topic"); // 一对多
		// 通过session创建消费者 (消息的接收者) 并指定一个消息管道
		MessageConsumer consumer = session.createConsumer(topic);
		// 为消费者创建一个消息监听器
		consumer.setMessageListener(new MessageListener() {

			@Override
			public void onMessage(Message message) {
				// 判断该消息是否为TextMessage
				if (message instanceof TextMessage) {
					TextMessage m = (TextMessage) message;
					String text;
					try {
						text = m.getText();
						System.out.println(text);
					} catch (JMSException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			}
		});
		// 等待键盘输入 模拟线程等待
		System.out.println("接收者3");
		System.in.read();
		// 关闭连接
		consumer.close();
		session.close();
		createConnection.close();
	}

	// -----------------------------------------------------------------------------------------------------------------

	/**
	 * -
	 * 
	 * @throws JMSException
	 */
	// 一对多发送 topic 持久化模式
	@Test
	public void sendTopicDurable() throws JMSException {
		// 创建连接工厂
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.128.129:61616");
		// 根据连接工厂创建单个连接
		Connection createConnection = connectionFactory.createConnection();
		// 开启连接
		createConnection.start();
		// 创建session对象 选择session的模式
		// 第一个是 是否开启事务，一般不使用事务
		// 如果第一个参数为true,则无视第二个参数.如果为false,第二个参数为消息的答复模式,即通知该队列消费者已收到消息,自动与手动两种
		Session session = createConnection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		// 通过session创建消息管道(队列)
		// Queue queue = session.createQueue("test-queue"); // 一对一
		Topic topic = session.createTopic("test-durable-topic"); // 一对多
		// 通过session创建生产者 (消息的发送者)
		MessageProducer createProducer = session.createProducer(topic); // 该生产者指定向该队列发送消息
		// NON_PERSISTENT 非持久化 PERSISTENT 持久化,发送消息时用使用持久模式
		createProducer.setDeliveryMode(DeliveryMode.PERSISTENT);
		// 通过session创建消息
		TextMessage createTextMessage = session.createTextMessage("7777777");
		// 发送消息
		createProducer.send(createTextMessage);
		// 关闭连接
		createProducer.close();
		session.close();
		createConnection.close();
	}

	/**
	 * -接收时该队列必须已存在
	 * @throws JMSException
	 * @throws IOException
	 */
	// 一对多接收 topic 持久化模式
	@Test
	public void receiveTopicDurable() throws JMSException, IOException {
		// 订阅者id
		String clientId = "clientId-001";

		// 创建连接工厂
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.128.129:61616");
		// 根据连接工厂创建单个连接
		Connection createConnection = connectionFactory.createConnection();
		// 客户端ID,持久订阅需要设置 -----------------------------
		createConnection.setClientID(clientId);
		// 开启连接
		createConnection.start();
		// 创建session对象 选择session的模式
		// 第一个是 是否开启事务，一般不使用事务
		// 如果第一个参数为true,则无视第二个参数.如果为false,第二个参数为消息的答复模式,即通知该队列消费者已收到消息,自动与手动两种
		Session session = createConnection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		// 通过session创建消息管道(队列)
		// Queue queue = session.createQueue("test-queue"); // 一对一
		Topic topic = session.createTopic("test-durable-topic"); // 一对多
		// 通过session创建一个消费者 (消息的接收者) 并指定一个可持久化的消息管道 并指定一个订阅者名称
		MessageConsumer consumer = session.createDurableSubscriber(topic, clientId);
		// 为消费者创建一个消息监听器
		consumer.setMessageListener(new MessageListener() {

			@Override
			public void onMessage(Message message) {
				// 判断该消息是否为TextMessage
				if (message instanceof TextMessage) {
					TextMessage m = (TextMessage) message;
					String text;
					try {
						text = m.getText();
						System.out.println(text);
					} catch (JMSException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			}
		});
		// 等待键盘输入 模拟线程等待
		System.out.println("持久化接收者");
		System.in.read();
		// 关闭连接
		consumer.close();
		session.close();
		createConnection.close();
	}

}

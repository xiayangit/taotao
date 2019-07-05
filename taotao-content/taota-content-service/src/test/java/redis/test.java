package redis;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.xy.taotao.redis.JedisClient;

/**
 * redis
 * @author xinlian
 *
 */
public class test {

	@Test
	public void test() {
		ApplicationContext a = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-*.xml");
		JedisClient bean = a.getBean(JedisClient.class);
		bean.set("aaa", "ss");
		System.out.println(bean.get("aaa"));
	}

}

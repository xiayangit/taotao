import org.junit.Test;

import redis.clients.jedis.Jedis;

public class test1 {

	@Test
	public void t1() {
		Jedis j = new Jedis("192.168.128.129",6379);
		j.set("aaa", "bbb");
		String s = j.get("aaa");
		System.out.println(s);
		j.close();
	}
	
	
}

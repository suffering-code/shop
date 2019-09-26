import org.junit.Test;
import redis.clients.jedis.Jedis;

public class RedisTest {
    @Test
    public  void add(){
        Jedis jedis = new Jedis("192.168.91.111",6379);
        jedis.auth("root");
        jedis.set("name","dema");
        String name = jedis.get("name");
        System.out.println(name);
        jedis.close();
    }
}

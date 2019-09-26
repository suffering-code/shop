import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.Map;

@RunWith(value = SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "classpath:applicationContext.xml")
public class ReddTest {
        @Autowired
        private RedisTemplate redisTemplate;

    @Test
    public  void testAdd(){
        ValueOperations valueOperations = redisTemplate.opsForValue();
        valueOperations.set("name","qf");
        valueOperations.set("age",12);

        redisTemplate.delete("age");
        System.out.println(valueOperations.get("name"));
        System.out.println(valueOperations.get("age"));


    }

    @Test
    public void maptest(){
        Map<String,String> map = new HashMap<>();
        map.put("age","十二");
        map.put("name","dema");
        for (Map.Entry<String,String>entry:map.entrySet()){
            System.out.println(entry.getKey()+":"+entry.getValue());
        }
    }

}

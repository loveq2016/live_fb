package cache;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.fsmeeting.live.redis.UserRedisDao;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class UserRedis {
	@Autowired
	@Qualifier("userIdRedisTemplate")
	private RedisTemplate<String, Long> userIdRedisTemplate;
	@Autowired
	private UserRedisDao userRedisDao;

	@Test
	public void getIncrUserId() {

		System.out.println(userRedisDao.getUserId());
	}

}

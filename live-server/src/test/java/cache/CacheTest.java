package cache;

import java.util.Random;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.fastonz.live.sliceprotocol.controller.AppServicePrx;
import com.fastonz.live.sliceprotocol.controller.AppServicePrxHelper;
import com.fsmeeting.live.common.Constants;
import com.fsmeeting.live.common.bean.LiveService;
import com.fsmeeting.live.common.enums.LiveServerType;
import com.fsmeeting.live.redis.AppRedisDao;

import Ice.Communicator;
import Ice.StringHolder;
import Ice.Util;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class CacheTest {
	@Autowired
	private RedisTemplate<String, LiveService> redisTemplate;
	@Autowired
	private AppRedisDao appOperator;

	@Autowired
	private RedisTemplate<String, String> redisRoomInfo;// <房间，tokenlist>

	@Test
	public void getCache() {
		Set<String> keys = redisTemplate.keys(Constants.Redis.Key.SERVICE_ACTIVE_PRIFIXER + "*");
		ValueOperations<String, LiveService> valueOper = redisTemplate.opsForValue();
		for (String key : keys) {
			System.out.println(key + "/" + valueOper.get(key));
		}
	}

	@Test
	public void contains() {
		boolean test = appOperator.isRegistered("{58326a04-2223-26ab-0100-0000fd7f0000}");
		System.out.println(test);
	}

	@Test
	public void removeALL() {

	}

	@Test
	public void load() {
		Communicator ic = Util.initialize();
		String str = ":default -h 192.168.7.178 -p 33001";
		AppServicePrx appService = AppServicePrxHelper.checkedCast(ic.stringToProxy("appService" + str));
		StringHolder add = new StringHolder();
		int result = appService.getMinLoadService(LiveServerType.MEDIA.getCode(), null, add);
		System.out.println(result);
		System.out.println(add.value);
	}
	@Test
	public void tokenHeartbeat() {
		final SetOperations<String, String> room = redisRoomInfo.opsForSet();
		final ValueOperations<String, String> tokens = redisRoomInfo.opsForValue();
		final int requestNumber = 100000;
		int threadNumber = 300;
		long start = System.currentTimeMillis();
		for (int i = 0; i < requestNumber; i++) {

			String roomID ="test-room-id:"+ new Random().nextInt(1);
			String token="38252694-0eea-384d-0100-00007f7f000" + new Random().nextInt(100000);
			tokens.set(token, roomID);
			room.add(roomID, token);
			/*try {
				TimeUnit.SECONDS.sleep(3);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}*/
		}
		/*ExecutorService service = Executors.newFixedThreadPool(threadNumber);
		service.submit(new Runnable() {

			@Override
			public void run() {
				for (int i = 0; i < requestNumber; i++) {

					// room token
					tokens.add("test-room-id:1", "38252694-0eea-384d-0100-00007f7f000" + new Random().nextInt(100));
					try {
						TimeUnit.SECONDS.sleep(3);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}

			}
		});
		 */
		long end = System.currentTimeMillis();
		System.out.println(
				"Threads:" + threadNumber + ",Requests:" + requestNumber * 3 + ",Time:" + (end - start) + "ms");

	}
	
	
	public static void main(String[] args) {
	}

}

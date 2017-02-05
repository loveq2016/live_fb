package com.fsmeeting.live.common;

/**
 * 直播常量
 * 
 * @author yicai.liu<moon>
 *
 */
public class Constants {

	/**
	 * 心跳检测超时时间(s)
	 */
	public static final int SERVICE_HEATBEAT_RATE = 15;

	/**
	 * 心跳检测超时时间(s)
	 */
	public static final int TOKEN_HEATBEAT_RATE = 30;

	/**
	 * Redis 信息
	 *
	 * @author yicai.liu
	 * @date 2016年11月13日 上午1:18:49
	 */
	public static final class Redis {
		/**
		 * 分隔符
		 */
		public static final String SPLITER = ":";
		// String tableName = Constants.Table.T_LiveService.NAME;

		/**
		 * Redis key 管理
		 * 
		 * @author yicai.liu<moon>
		 *
		 */
		public static final class Key {
			/**
			 * 服务列表
			 */
			public static final String SERVICE_LIST = "List" + Redis.SPLITER + Constants.Table.T_LiveService.NAME;

			/**
			 * 活跃服务前缀
			 */
			public static final String SERVICE_ACTIVE_PRIFIXER = Constants.Table.T_LiveService.NAME + Redis.SPLITER
					+ Constants.Table.T_LiveService.FIELD_APPID + Redis.SPLITER;

			/**
			 * 房间token列表前缀
			 */
			public static final String TOKENS_SORTED_SET_IN_LIVEROOM_PREFIXER = "token:set:in:room:id:";

			/**
			 * 用户房间&token对应关系映射
			 */
			public static final String TOKENS_UNSORTED_SET_IN_LIVEROOM_PREFIXER = "token:zset:in:room:id:";

			/**
			 * 房间点数前缀
			 */
			public static final String TOKEN_COUNT_IN_LIVEROOM_PREFIXER = "token:count:in:room:id:";

			/**
			 * 直播运行时房间信息
			 */
			public static final String LIVEROOM_INFO_MAP = "live:room:info:map";

			/**
			 * 用户自增ID
			 */
			public static final String LIVE_USERID_INCR = "live:user:id:incr";

		}

	}

	/**
	 * 表信息
	 * 
	 * @author yicai.liu<moon>
	 */
	public static final class Table {

		public static final class T_LiveService {
			public static final String NAME = "t_liveservice";
			public static final String FIELD_APPID = "appId";
			public static final String FIELD_APPTYPE = "appType";
		}

		public static final class T_LiveRoom {
			public static final String NAME = "T_LiveRoom";
		}
	}

	/**
	 * 用户ID范围最大值
	 */
	public static final int USERID_RANGE_MAX = 0x5FFFFFFF;

	/**
	 * 用户ID范围最小值
	 */
	public static final int USERID_RANGE_MIN = 0x50000000;
}

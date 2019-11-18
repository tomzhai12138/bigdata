package com.data.bigdata.common.constant;

/**
 * 
 * @ClassName:  RedisConstants   
 * @Description:  redis配置文件常量   
 * @author: zhaiyongji
 * @date:   2019年8月14日 上午9:23:51   
 *     
 * @Copyright: © 2019 版权所有
 */
public class RedisConstants {
	/** redis配置文件名称 */
	public static final String REDIS_CONFIG_FILE = "redis";
	
	// 以下是相关属性的key
	
	public static final String REDIS_POOL_MAXTOTAL = "redis.pool.maxTotal";
	public static final String REDIS_POOL_MAXIDLE = "redis.pool.maxIdle";
	public static final String REDIS_POOL_MAXWAIT = "redis.pool.maxWait";
	public static final String REDIS_POOL_TESTONBORROW = "redis.pool.testOnBorrow";
	public static final String REDIS_POOL_TESTONRETURN = "redis.pool.testOnReturn";
	public static final String REDIS_IP = "redis.ip";
	public static final String REDIS_PORT = "redis.port";
	public static final String REDIS_TIMEOUT = "redis.timeout";
	public static final String REDIS_AUTH = "redis.auth";
	public static final String REDIS_EXPIRE = "redis.expire";
}
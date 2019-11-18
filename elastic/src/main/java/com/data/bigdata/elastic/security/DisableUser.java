package com.data.bigdata.elastic.security;

import java.io.IOException;

import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.security.DisableUserRequest;
import org.elasticsearch.client.security.RefreshPolicy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @ClassName:  DisableUser   
 * @Description:  DisableUser  
 * @author: zhaiyongji
 * @date:   2019年11月14日 下午4:18:20   
 *     
 * @Copyright: © 2019 版权所有
 */
public class DisableUser {
	
	private static final Logger log=LoggerFactory.getLogger(DisableUser.class);
	
	public static boolean disableUser(RestHighLevelClient client,String username) {
		boolean flag=true;
		DisableUserRequest request=new DisableUserRequest(username, RefreshPolicy.NONE);
		try {
			boolean response=client.security().disableUser(request, RequestOptions.DEFAULT);
			if (response) {
				flag=response;
				log.info(">>> disableUser ["+username+"] successful !!!!<<<");
			}else {
				flag=false;
				log.info(">>> disableUser ["+username+"] failed !!!!<<<");
			}
		} catch (IOException e) {
			log.error("*** DisableUser 异常 ***:"+e);
			e.printStackTrace();
		}
		return flag;
		
	}

}

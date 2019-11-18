package com.data.bigdata.elastic.security;

import java.io.IOException;

import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.security.ChangePasswordRequest;
import org.elasticsearch.client.security.RefreshPolicy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @ClassName:  ChangePassword   
 * @Description:  change user’s password   
 * @author: zhaiyongji
 * @date:   2019年11月14日 下午4:01:37   
 *     
 * @Copyright: © 2019 版权所有
 */
public class ChangePassword {
	
	private static final Logger log=LoggerFactory.getLogger(ChangePassword.class);
	/**
	 * 
	 * @Title: changeUserPassword   
	 * @Description: 修改用户密码
	 * @auth: zhaiyongji 
	 * @param: @param client
	 * @param: @param username 当前用户
	 * @param: @param password 新密码
	 * @param: @return      
	 * @return: boolean      
	 * @throws
	 */
	public static boolean changeUserPassword(RestHighLevelClient client,String username,char[] password) {
		boolean flag=true;
		ChangePasswordRequest request=new ChangePasswordRequest(username, password, RefreshPolicy.NONE);
		try {
			boolean response=client.security().changePassword(request, RequestOptions.DEFAULT);
			if (response) {
				log.info(">>> 修改用户["+username+"]密码成功！！！！<<<");
				flag=response;
			}else {
				flag=false;
				log.info("*** 修改用户["+username+"]密码失败！！！！***");
			}
		} catch (IOException e) {
			log.error("*** 修改用户["+username+"]密码失败 ***:"+e);
			e.printStackTrace();
		}
		return flag;
	}

}

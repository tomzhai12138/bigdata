package com.data.bigdata.elastic.utils;

import java.io.IOException;

import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.PutMappingRequest;
import org.elasticsearch.common.xcontent.XContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * 
 * @ClassName:  MappingUtil   
 * @Description:  指定索引添加映射   
 * @author: zhaiyongji
 * @date:   2019年11月13日 下午2:07:49   
 *     
 * @Copyright: © 2019 版权所有
 */
public class MappingUtil {
	
	private static Logger log = LoggerFactory.getLogger(MappingUtil.class);
	
	//指定索引添加映射
	public static boolean addMapping(RestHighLevelClient client,String index,String mappingJson) {
		boolean flag=true;
		AcknowledgedResponse putMappingResponse=null;
		PutMappingRequest request = new PutMappingRequest(index);
		request.source(mappingJson, XContentType.JSON);
		try {
			putMappingResponse=client.indices().putMapping(request, RequestOptions.DEFAULT);
			boolean acknowledged = putMappingResponse.isAcknowledged(); 
			if (acknowledged) {
				log.info("创建" + index + "的mapping成功....");
				flag= acknowledged;
			}else {
				log.info("创建" + index + "的mappings失败....");
				flag= false;
			}
		} catch (IOException e) {
			log.error("添加" + index + "的mapping失败....",e);
			e.printStackTrace();
		}
		return flag;
	}

}

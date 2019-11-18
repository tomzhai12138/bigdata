package com.data.bigdata.elastic.indices;

import java.io.IOException;

import org.elasticsearch.action.support.IndicesOptions;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @ClassName:  IndicesExists   
 * @Description:   Indices is Exists  
 * @author: zhaiyongji
 * @date:   2019年11月15日 上午9:33:28   
 *     
 * @Copyright: © 2019 版权所有
 */
public class IndicesExists {
	
	private static final Logger log=LoggerFactory.getLogger(IndicesExists.class);
	
	public static boolean indexExists(RestHighLevelClient client,String index) {
		boolean flag=true;
		GetIndexRequest request = new GetIndexRequest(index);
		
		/**
		 * 	Whether to return local information or retrieve the state from master node
		 */
		request.local(true);
		/**
		 * Return result in a format suitable for humans
		 */
		request.humanReadable(true);
		/**
		 * Whether to return all default setting for each of the indices
		 */
		request.includeDefaults(false);
		/**
		 * Controls how unavailable indices are resolved and how wildcard expressions are expanded
		 */
		request.indicesOptions(IndicesOptions.LENIENT_EXPAND_OPEN);
		try {
			boolean response=client.indices().exists(request, RequestOptions.DEFAULT);
			if (response) {
				flag=response;
				log.info(">>> 索引 ["+index+"] 存在！！！ <<<");
			}else {
				flag=false;
				log.info(">>> 索引 ["+index+"] 不存在！！！ <<<");
			}
		} catch (IOException e) {
			log.error("*** GetIndexRequest ["+index+"] 异常****："+e);
			e.printStackTrace();
		}
		return flag;
		
	}

}

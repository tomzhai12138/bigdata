package com.data.bigdata.elastic.document;

import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * 
 * @ClassName:  Exists   
 * @Description:  if a document exists   
 * @author: zhaiyongji
 * @date:   2019年11月14日 下午2:39:44   
 *     
 * @Copyright: © 2019 版权所有
 */
public class Exists {
	
	private static final Logger log=LoggerFactory.getLogger(Exists.class);
	
	public static void documentExists(RestHighLevelClient client,String index,String id) {
		GetRequest request=new GetRequest();
		/**
		 * 查询索引
		 */
		request.index(index);
		/**
		 * 文档ID
		 */
		request.id(id);
		request.fetchSourceContext(new FetchSourceContext(false)); 
		request.storedFields("_none_");
		client.existsAsync(request, RequestOptions.DEFAULT, new ActionListener<Boolean>() {
			
			@Override
			public void onResponse(Boolean response) {
				log.info(">>>"+index+":"+id+"id exists <<<");
				
			}
			
			@Override
			public void onFailure(Exception e) {
				log.error("***GetRequest failed ***"+e);
				
			}
		});
	}

}

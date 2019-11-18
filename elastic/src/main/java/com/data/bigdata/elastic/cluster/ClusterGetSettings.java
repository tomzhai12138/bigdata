package com.data.bigdata.elastic.cluster;

import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.admin.cluster.settings.ClusterGetSettingsRequest;
import org.elasticsearch.action.admin.cluster.settings.ClusterGetSettingsResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * 
 * @ClassName:  ClusterGetSettings   
 * @Description:  get cluster wide settings  
 * @author: zhaiyongji
 * @date:   2019年11月14日 下午2:23:53   
 *     
 * @Copyright: © 2019 版权所有
 */
public class ClusterGetSettings {
	
	private static final Logger log = LoggerFactory.getLogger(ClusterGetSettings.class);
	public static void getSetting(RestHighLevelClient client) {
		ClusterGetSettingsRequest request=new ClusterGetSettingsRequest();
		/**
		 * By default only those settings that were explicitly set are returned. 
		 * Setting this to true also returns the default settings.
		 */
		request.includeDefaults(true);
		/**
		 * By default the request goes to the master of the cluster to get the latest results. 
		 * If local is specified it gets the results from whichever node the request goes to.
		 */
		request.local(true);
		/**
		 * Timeout to connect to the master node as a TimeValue
		 */
		request.masterNodeTimeout(TimeValue.timeValueMinutes(1)); 
		/**
		 * 异步执行
		 */
		client.cluster().getSettingsAsync(request, RequestOptions.DEFAULT, new ActionListener<ClusterGetSettingsResponse>() {
			
			@Override
			public void onResponse(ClusterGetSettingsResponse response) {
				log.info(">>> 获取集群配置信息成功！！！！ <<<");
				/**
				 * persistent settings.
				 */
				response.getPersistentSettings();
				/**
				 * transient settings
				 */
				response.getTransientSettings();
				/**
				 * default settings
				 */
				response.getDefaultSettings();
				/**
				 * the value as a String for a particular setting
				 */
				response.getSetting("cluster.routing.allocation.enable"); 
				
			}
			
			@Override
			public void onFailure(Exception e) {
				log.error("*** 获取集群配置信息失败 ***"+e);
				
			}
		});
	}

}

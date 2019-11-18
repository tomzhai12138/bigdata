package com.data.bigdata.elastic.cluster;

import java.util.HashMap;
import java.util.Map;

import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.admin.cluster.settings.ClusterUpdateSettingsRequest;
import org.elasticsearch.action.admin.cluster.settings.ClusterUpdateSettingsResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.cluster.routing.allocation.decider.EnableAllocationDecider;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.unit.ByteSizeUnit;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.indices.recovery.RecoverySettings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @ClassName:  ClusetrUpdateSettings   
 * @Description:  update cluster wide settings  
 * @author: zhaiyongji
 * @date:   2019年11月14日 下午2:12:15   
 *     
 * @Copyright: © 2019 版权所有
 */
public class ClusetrUpdateSettings {
	
	private static final Logger log=LoggerFactory.getLogger(ClusetrUpdateSettings.class);
	
	public static void updateSettings(RestHighLevelClient client) {
		ClusterUpdateSettingsRequest request=new ClusterUpdateSettingsRequest();
		
		String transientSettingKey =
		        RecoverySettings.INDICES_RECOVERY_MAX_BYTES_PER_SEC_SETTING.getKey();
		int transientSettingValue = 10;
		Settings transientSettings =
		        Settings.builder()
		        .put(transientSettingKey, transientSettingValue, ByteSizeUnit.BYTES)
		        .build(); 

		String persistentSettingKey =
		        EnableAllocationDecider.CLUSTER_ROUTING_ALLOCATION_ENABLE_SETTING.getKey();
		String persistentSettingValue =
		        EnableAllocationDecider.Allocation.NONE.name();
		Settings persistentSettings =
		        Settings.builder()
		        .put(persistentSettingKey, persistentSettingValue)
		        .build(); 
		
		request.transientSettings(transientSettings); 
		request.persistentSettings(persistentSettings); 
		
		Settings.Builder transientSettingsBuilder =
		        Settings.builder()
		        .put(transientSettingKey, transientSettingValue, ByteSizeUnit.BYTES);
		request.transientSettings(transientSettingsBuilder); 
		request.transientSettings(
		        "{\"indices.recovery.max_bytes_per_sec\": \"10b\"}"
		        , XContentType.JSON); 
		Map<String, Object> map = new HashMap<>();
		map.put(transientSettingKey
		        , transientSettingValue + ByteSizeUnit.BYTES.getSuffix());
		request.transientSettings(map); 
		request.timeout(TimeValue.timeValueMinutes(2));
		request.masterNodeTimeout(TimeValue.timeValueMinutes(1));
		/**
		 * 异步执行
		 */
		client.cluster().putSettingsAsync(request, RequestOptions.DEFAULT, new ActionListener<ClusterUpdateSettingsResponse>() {
			
			@Override
			public void onResponse(ClusterUpdateSettingsResponse response) {
				log.info(">>> update cluster wide settings success <<<");
				response.isAcknowledged();
			}
			
			@Override
			public void onFailure(Exception e) {
				log.info("*** update cluster wide settings failed ***"+e);
				
			}
		});
	}

}

package com.data.bigdata.elastic.tasks;

import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.admin.cluster.node.tasks.list.ListTasksRequest;
import org.elasticsearch.action.admin.cluster.node.tasks.list.ListTasksResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.tasks.TaskId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @ClassName:  ListTasks   
 * @Description:  get information about the tasks currently executing in the cluster.  
 * @author: zhaiyongji
 * @date:   2019年11月14日 下午3:30:40   
 *     
 * @Copyright: © 2019 版权所有
 */
public class ListTasks {
	
	private static final Logger log=LoggerFactory.getLogger(ListTasks.class);
	
	public static void getListTasks(RestHighLevelClient client) {
		ListTasksRequest request = new ListTasksRequest();
		request.setActions("cluster:*"); 
		request.setNodes("nodeId1", "nodeId2"); 
		request.setParentTaskId(new TaskId("parentTaskId", 42)); 
		request.setDetailed(true);
		request.setWaitForCompletion(true); 
		request.setTimeout(TimeValue.timeValueSeconds(50)); 
		client.tasks().listAsync(request, RequestOptions.DEFAULT, new ActionListener<ListTasksResponse>() {
			
			@Override
			public void onResponse(ListTasksResponse response) {
				log.info(">>> <<<<");
				response.getTasks();
				response.getTaskGroups();
				response.getPerNodeTasks();
				response.getTaskFailures();
				response.getNodeFailures();
			}
			
			@Override
			public void onFailure(Exception e) {
				log.error("*** ListTasksRequest failed ***"+e);
				
			}
		}); 
		
	}

}

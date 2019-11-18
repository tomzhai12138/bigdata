package com.data.bigdata.elastic.cluster;

import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.admin.cluster.health.ClusterHealthRequest;
import org.elasticsearch.action.admin.cluster.health.ClusterHealthResponse;
import org.elasticsearch.action.support.ActiveShardCount;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.cluster.health.ClusterHealthStatus;
import org.elasticsearch.common.Priority;
import org.elasticsearch.common.unit.TimeValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @ClassName: ClusterHealth
 * @Description: 集群健康状态
 * @author: zhaiyongji
 * @date: 2019年11月14日 上午11:11:34
 * 
 * @Copyright: © 2019 版权所有
 */
public class ClusterHealth {

	private static final Logger log = LoggerFactory.getLogger(ClusterHealth.class);

	public static ClusterHealthResponse ClusterStatus(RestHighLevelClient client) {
		// 检查所有索引,默认方式
		ClusterHealthRequest request = new ClusterHealthRequest();
		// 指定索引
		request.indices("index1", "index2");
		// 请求超时时间，单位：秒
		request.timeout(TimeValue.timeValueSeconds(50));
		// 连接到主节点超时时间，单位：秒
		request.masterNodeTimeout(TimeValue.timeValueSeconds(20));
		// 等待集群状态，值:GREEN,YELLOW,RED
		request.waitForStatus(ClusterHealthStatus.YELLOW);
		// the priority of the events to wait for
		request.waitForEvents(Priority.NORMAL);
		// 请求返回的集群健康级别，到分片详情
		request.level(ClusterHealthRequest.Level.SHARDS);
		// Wait for 0 relocating shards
		request.waitForNoRelocatingShards(true);
		// Wait for 0 initializing shards
		request.waitForNoInitializingShards(true);
		// Wait for N nodes in the cluster，取值：确切的节点数，>=,le(个数)
		request.waitForNodes(">=2");
		// 等待可用分片数
		request.waitForActiveShards(ActiveShardCount.ALL);
		// 没有主节点时该请求可用
		request.local(true);
		// 异步方法不会阻塞并立即返回，完成后，如果执行成功完成，则使用onResponse方法回调ActionListener，如果失败则使用onFailure方法。
		client.cluster().healthAsync(request, RequestOptions.DEFAULT, new ActionListener<ClusterHealthResponse>() {
			@Override
			public void onResponse(ClusterHealthResponse response) {
				
			}

			@Override
			public void onFailure(Exception e) {
				log.error("*** ClusterHealth请求失败*** " + e);
			}
		});

		return null;
	}

}

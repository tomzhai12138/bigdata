package com.data.bigdata.elastic.client;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.RequestConfig.Builder;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.RestClientBuilder.RequestConfigCallback;

/**
 * 
 * @ClassName: HighLevelClient
 * @Description: elasticsearch高级客户端
 * @author: zhaiyongji
 * @date: 2019年11月13日 上午9:36:48
 * 
 * @Copyright: © 2019 版权所有
 */
public class HighLevelClient {
	private static String hosts = "192.168.1.223";// 集群地址，多个地址用逗号隔开
	private static int port = 9200; // 使用的端口号
	private static String schema = "http"; // 使用的协议
	private static ArrayList<HttpHost> hostList = null;

	private static String user = "elastic";// elasticsearch集群用户名
	private static String password = "123456";// elasticsearch用户密码

	private static int connectTimeOut = 1000; // 连接超时时间
	private static int socketTimeOut = 30000; // 连接超时时间
	private static int connectionRequestTimeOut = 500; // 获取连接的超时时间

	private static int maxConnectNum = 100; // 最大连接数
	private static int maxConnectPerRoute = 100; // 最大路由连接数

	static {
		hostList = new ArrayList<HttpHost>();
		String[] hostStr = hosts.split(",");
		for (String host : hostStr) {
			hostList.add(new HttpHost(host, port, schema));
		}
	}

	// elasticsearch集群未设置x-pack安全模式
	public static RestHighLevelClient getClient() {

		RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(hostList.toArray(new HttpHost[0])));
		return client;
	}

	// elasticsearch集群设置x-pack安全模式
	public static RestHighLevelClient getClientBySecurity() {
		final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
		// 设置elasticsearch集群用户名密码
		credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(user, password));
		// 创建rest client
		RestClientBuilder builder = RestClient.builder(hostList.toArray(new HttpHost[0]))
				.setHttpClientConfigCallback(new RestClientBuilder.HttpClientConfigCallback() {

					public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpClientBuilder) {
						httpClientBuilder.disableAuthCaching();
						// 异步httpclient的连接数配置
						httpClientBuilder.setMaxConnTotal(maxConnectNum);
						httpClientBuilder.setMaxConnPerRoute(maxConnectPerRoute);
						return httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
					}
				});
		// 异步httpclient的连接延时配置
		setConnectTimeOutConfig(builder);
		// 创建elasticsearch高级客户端
		RestHighLevelClient highClient = new RestHighLevelClient(builder);
		return highClient;
	}

	// 异步httpclient的连接延时配置
	public static void setConnectTimeOutConfig(RestClientBuilder builder) {
		builder.setRequestConfigCallback(new RequestConfigCallback() {

			public Builder customizeRequestConfig(Builder requestConfigBuilder) {
				requestConfigBuilder.setConnectTimeout(connectTimeOut);
				requestConfigBuilder.setSocketTimeout(socketTimeOut);
				requestConfigBuilder.setConnectionRequestTimeout(connectionRequestTimeOut);
				return requestConfigBuilder;
			}
		});
	}

	// 关闭elasticsearch客户端
	public static void close(RestHighLevelClient client) {
		try {
			client.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

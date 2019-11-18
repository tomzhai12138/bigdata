package com.data.bigdata.elastic.utils;

import java.io.IOException;
import java.util.List;

import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.data.bigdata.elastic.client.HighLevelClient;
import com.data.bigdata.elastic.entity.FieldInfo;

public class AdminUtil {
	
	private static Logger log = LoggerFactory.getLogger(AdminUtil.class);
	//检查索引是否存在
	public static boolean indexExists(RestHighLevelClient client,String index) {
		GetIndexRequest request = new GetIndexRequest(index);
		boolean exists=false;
		try {
			exists=client.indices().exists(request, RequestOptions.DEFAULT);
		} catch (IOException e) {
			log.error("客户端请求失败!!!:"+e);
			e.printStackTrace();
		}
		return exists;
		
	}
	//创建简单索引，不指定索引映射
	public static boolean createSimpleIndex(RestHighLevelClient client,String index,int shards,
			int replicas) {
		boolean flag=false;
		boolean exists=AdminUtil.indexExists(client,index);
		CreateIndexResponse response;
		//索引不存在，创建索引
		if (!exists) {
			System.out.println("索引:"+index+"不存在，开始创建索引！！！");
			index.toLowerCase();
			CreateIndexRequest request = new CreateIndexRequest(index);
			// 设置分片与副本数
			Settings settings = Settings.builder().put("index.number_of_shards", shards)
					.put("index.number_of_replicas", replicas).build();
			request.settings(settings);
			try {
				response=client.indices().create(request, RequestOptions.DEFAULT);
				flag=response.isAcknowledged();
				log.info("索引:"+index+"创建成功！！！");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else {
			log.error("索引:"+index+"已存在！！！");
			flag=false;
		}
		return flag;
	}
	//创建索引同时创建mapping映射
	public static boolean createIndexAndCreateMapping(String index,List<FieldInfo> fieldInfoList, int shards,
			int replicas, RestHighLevelClient client) {
		boolean flag=true;
		XContentBuilder mapping = null;
		boolean acknowledged=true;
		// elasticsearch中索引名称必须为小写
		index.toLowerCase();
		CreateIndexRequest request = new CreateIndexRequest(index);
		// 设置分片与副本数
		Settings settings = Settings.builder().put("index.number_of_shards", shards)
				.put("index.number_of_replicas", replicas).build();
		try {
			mapping = XContentFactory.jsonBuilder().startObject().startObject("properties");
			for (FieldInfo info : fieldInfoList) {
				String field = info.getField(); // 字段名称
				String dataType = info.getType(); // 字段类型
				if (dataType == null || "".equals(dataType.trim())) {
					dataType = "String";
				}
				dataType = dataType.toLowerCase();
				Integer participle = info.getParticiple(); // 分词类型
				//index”参数用来配置该字段是否可以被用来搜索，true可以通过搜索该字段检索到文档，false为否，配置分词器，用analyzer参数。
				if ("string" == dataType) {
					if (participle == 1) {
						//text类型在存储数据的时候会默认进行分词，并生成索引
						mapping.startObject(field).field("type", "text").field("analyzer", "ik_smart").endObject();
					} else if (participle == 2) {
						mapping.startObject(field).field("type", "text").field("analyzer", "ik_max_word").endObject();
					} else {
						//keyword类型的数据可以满足电子邮箱地址、主机名、状态码、邮政编码和标签等数据的要求，不进行分词，常常被用来过滤、排序和聚合。
						//keyword存储数据的时候，不会分词建立索引，显然，这样划分数据更加节省内存
						//之前2.X版本中一样设置分词使用"index":"not_analyzed"配置时，会有提醒，提示"index"参数只有false和true两个值。
						mapping.startObject(field).field("type", "keyword").field("index", false).endObject();
					}
				} else if ("date".equals(dataType)) {
					mapping.startObject(field).field("type", dataType)
							.field("format", "yyyy-MM-dd HH:mm:ss||yyyy-MM-dd||epoch_millis").endObject();
				} else {
					mapping.startObject(field).field("type", dataType).field("index", "not_analyzed").endObject();
				}
			}
			// 设置之定义字段
			mapping.endObject().endObject();
			request.mapping(mapping);
			request.settings(settings);
			CreateIndexResponse createIndexResponse=client.indices().create(request, RequestOptions.DEFAULT);
			acknowledged=createIndexResponse.isAcknowledged();
			if (acknowledged) {
				log.info(index+"创建索引成功！！！");
				flag=acknowledged;
			}else {
				flag=false;
				log.error(index+"创建索引失败！！！");
			}
		} catch (IOException e) {
			log.error(index+"创建索引失败！！！"+e);
			e.printStackTrace();
		} 
		return flag;
	}
	//动态创建索引
	public static boolean builderIndexAndType(String index,String path,int shard,int replication) {
		boolean flag=false;
		//获取es高级客户端
		RestHighLevelClient client=HighLevelClient.getClientBySecurity();
		boolean exists=AdminUtil.createSimpleIndex(client, index,shard,replication);
		if (exists) {
			//String mappingJson=FileCommon.getAbstractPath(path);
			String mappingJson=null;
			flag=MappingUtil.addMapping(client, index, mappingJson);
		}else {
			flag=false;
			log.error("创建索引失败！！");
		}
		log.info("创建索引成功！！");
		return flag;
	}

}

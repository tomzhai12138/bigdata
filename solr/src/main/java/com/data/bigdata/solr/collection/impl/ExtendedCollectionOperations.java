package com.data.bigdata.solr.collection.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CloudSolrClient;
import org.apache.solr.client.solrj.request.CollectionAdminRequest;
import org.apache.solr.client.solrj.request.FieldAnalysisRequest;
import org.apache.solr.client.solrj.request.CollectionAdminRequest.AddReplica;
import org.apache.solr.client.solrj.request.CollectionAdminRequest.CreateShard;
import org.apache.solr.client.solrj.request.CollectionAdminRequest.Delete;
import org.apache.solr.client.solrj.request.CollectionAdminRequest.DeleteReplica;
import org.apache.solr.client.solrj.request.CollectionAdminRequest.DeleteShard;
import org.apache.solr.client.solrj.request.CollectionAdminRequest.SplitShard;
import org.apache.solr.client.solrj.request.schema.SchemaRequest;
import org.apache.solr.client.solrj.request.schema.SchemaRequest.FieldTypes;
import org.apache.solr.client.solrj.response.CollectionAdminResponse;
import org.apache.solr.client.solrj.response.FieldAnalysisResponse;
import org.apache.solr.client.solrj.response.AnalysisResponseBase.AnalysisPhase;
import org.apache.solr.client.solrj.response.AnalysisResponseBase.TokenInfo;
import org.apache.solr.client.solrj.response.schema.FieldTypeRepresentation;
import org.apache.solr.client.solrj.response.schema.SchemaResponse;
import org.apache.solr.client.solrj.response.schema.SchemaResponse.CopyFieldsResponse;
import org.apache.solr.client.solrj.response.schema.SchemaResponse.DynamicFieldsResponse;
import org.apache.solr.client.solrj.response.schema.SchemaResponse.FieldTypesResponse;
import org.apache.solr.client.solrj.response.schema.SchemaResponse.UpdateResponse;
import org.apache.solr.common.cloud.ClusterState;
import org.apache.solr.common.cloud.DocCollection;
import org.apache.solr.common.params.ModifiableSolrParams;
import org.springframework.util.Assert;

import com.data.bigdata.solr.collection.CollectionOperations;

/**
 * 
 * TODO: solr集群collection操作实现类
 * TODO:「详细描述该 class|package 的作用」 
 * 
 * <p>Created On 2018年11月9日 上午11:12:21 </p> 
 * 
 * @since TODO：「若是非首次程序发布时新增的类，需标注下次发布的版本号。否则，可移除」 
 * @author zhaiyongji
 * @see 「[package.][class][#constructor|method|field|]」
 */
public class ExtendedCollectionOperations implements CollectionOperations {
	//solrCloud客户端
	private final static String groupFiledNamePostfix = "_singleterm";
	/**
	 * 
	 * TODO: 集群中创建新的collection 
	 * TODO: 集群中创建新的collection,并配置分片，副本等信息 
	 * 
	 * @param client 集群客户端连接
	 * @param collection 索引名
	 * @param numShards 分片数
	 * @param numReplicas 副本数
	 * @param maxNumShards 最大分片数
	 * @param routerName 路由名,取值为'implicit'或'compsited',后者为默认值
	 * @param shards 分片名称,当路由为implicit时，该参数为必须参数
	 * @see com.taiji.bdp2.solr.collection.CollectionOperations#createCollection(java.lang.String, int, int, int, java.lang.String)
	 */
	public boolean createCollection(CloudSolrClient client,String collection,int numShards, int numReplicas,int maxNumShards,String routerName,String shards) {
		boolean flag=true;
		if (isExist(client,collection)) {
			try {
				throw new Exception("collection id exit!!!");
			} catch (Exception e) {
				e.printStackTrace();
			}
			return false;
		}else{
			//配置创建collection
			CollectionAdminRequest.Create create=null;
			if (routerName=="compsited" || routerName==null) {
				create=CollectionAdminRequest.Create.createCollection(collection, numShards, numReplicas);
			}else {
				Assert.hasText(shards, "shards must not be 'null' or ''");
				create=CollectionAdminRequest.Create.createCollectionWithImplicitRouter(collection, null, shards, numReplicas);
			}
			create.setMaxShardsPerNode(maxNumShards);
			CollectionAdminResponse response=null;
			
		try {
			response=create.process(client);
			flag=response.isSuccess();
		} catch (SolrServerException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		}
		return flag;
	}
	/**
	 * 
	 * TODO: 获取schema配置文件中所有的CopyFields集合
	 * TODO: 获取schema配置文件中所有的CopyFields集合
	 * 
	 * @param client 集群客户端连接
	 * @param collection 索引名
	 * @return 
	 * @throws IOException 
	 * @throws SolrServerException 
	 * @see com.taiji.bdp2.solr.collection.CollectionOperations#getSchemaCopyFields(java.lang.String)
	 */
	public List<Map<String, Object>> getSchemaCopyFields(CloudSolrClient client,String collection) throws SolrServerException, IOException {
		Assert.hasText(collection, "collection must not be 'null'");
		List<Map<String, Object>> copyFields=new ArrayList<Map<String,Object>>();
        ModifiableSolrParams params=new ModifiableSolrParams();
        //数据返回格式
        params.add("wt","json");
        //设置返回sourcefield信息
        params.add("source.fl","name");
        SchemaRequest.CopyFields allCopyFields=new SchemaRequest.CopyFields(params);
        CopyFieldsResponse response=allCopyFields.process(client, collection);
        copyFields=response.getCopyFields();
		return copyFields;
	}
	/**
	 * 
	 * TODO: 删除指定field 
	 * TODO: 删除指定collection下的指定的field 
	 * 
	 * @param client 集群客户端连接
	 * @param collection 索引名
	 * @param fieldName 字段名
	 * @throws IOException 
	 * @throws SolrServerException 
	 * @see com.taiji.bdp2.solr.collection.CollectionOperations#deleteField(java.lang.String, java.lang.String)
	 */
	public boolean deleteField(CloudSolrClient client,String collection, String fieldName) throws SolrServerException, IOException {
		Assert.hasText(collection, "collection must not be 'null'");
		Assert.hasText(fieldName, "fieldName must not be 'null'");
		SchemaRequest.DeleteField deleteField=new SchemaRequest.DeleteField(fieldName);
		UpdateResponse response=deleteField.process(client, collection);
		boolean flag=response.getResponse().getBooleanArg("success");
		if (flag==true) {
			return flag;
		}else {
			return false;
		}
	}
	/**
	 * 
	 * TODO: 更新schema中的field字段信息 
	 * TODO: 指定collection下的schema进行field字段信息的操作 
	 * 
	 * @param client 集群客户端连接
	 * @param collection 索引名
	 * @throws IOException 
	 * @throws SolrServerException 
	 * @see com.taiji.bdp2.solr.collection.CollectionOperations#updateField(java.lang.String)
	 */
	public boolean updateField(CloudSolrClient client,String collection,List<Map<String, Object>> columns) throws SolrServerException, IOException {
		Assert.hasText(collection, "collection must not be 'null'");
		boolean flag=true;
		UpdateResponse response=null;
		for (Map<String, Object> map : columns) {
			Map<String, Object> fieldAttributes=new HashMap<String, Object>();
			fieldAttributes.put("name",map.get("name"));   //域名
			fieldAttributes.put("type",map.get("type"));   //域的类型，可以是string，int，如果需要分词设为text_ik
			fieldAttributes.put("indexed",true);           //是否索引，默认为true
			fieldAttributes.put("stored",true);            //是否存储，默认为true
			fieldAttributes.put("multiValued",false);      //是否多值，默认为false
			fieldAttributes.put("required",false);         //是否必须，默认false，schema文件中有一个id已默认必须
			SchemaRequest.ReplaceField replaceField=new SchemaRequest.ReplaceField(fieldAttributes);
			response=replaceField.process(client, collection);	
		}
		flag=response.getResponse().getBooleanArg("success");
		if (flag==true) {
			return flag;
		}else {
			return false;
		}
	}
	/**
	 * 
	 * TODO: 创建新的collection时判断集群中是否已经存在该collection
	 * TODO: 判断集群中是否已经存在该collection 
	 * 
	 * @param client 集群客户端连接
	 * @param collection 索引名
	 * @return 
	 * @see com.taiji.bdp2.solr.collection.CollectionOperations#isExist(java.lang.String)
	 */
	public boolean isExist(CloudSolrClient client,String collection) {
		Assert.hasText(collection, "collection must not be 'null' or ''");
		boolean isCollection=client.getZkStateReader().getClusterState().hasCollection(collection);
		if (isCollection) {
			return true;
		}
		return isCollection;
	}

	/**
	 * 
	 * TODO: 获取schema中的field字段集合
	 * TODO: 获取schema中的field字段集合
	 * 
	 * @param client 集群客户端连接
	 * @param collection 索引名
	 * @return 
	 * @throws IOException 
	 * @throws SolrServerException 
	 * @see com.taiji.bdp2.solr.collection.CollectionOperations#getSchemaFields(java.lang.String)
	 */
	public List<Map<String, Object>> getSchemaFields(CloudSolrClient client,String collection) throws SolrServerException, IOException {
		Assert.hasText(collection, "collection must not be 'null'");
		List<Map<String, Object>> fields=new ArrayList<Map<String,Object>>();
		ModifiableSolrParams params=new ModifiableSolrParams();
		params.add("showDefaults","false");  //是否显示域类型的默认信息
		params.add("includeDynamic","true"); //是否返回动态域的定义信息
		//params.add("f1","name,_version_"); //指定返回那些域的定义信息
		SchemaRequest.Fields allFields=new SchemaRequest.Fields(params);
		SchemaResponse.FieldsResponse response=allFields.process(client, collection);
		fields=response.getFields();
		return fields;
	}
	/**
	 * 
	 * TODO: 删除指定colllection
	 * TODO: 通过指定collection删除集合
	 * 
	 * @param client 集群客户端连接
	 * @param collection 索引名
	 * @see com.taiji.bdp2.solr.collection.CollectionOperations#deleteCollection(java.lang.String)
	 */
	public boolean deleteCollection(CloudSolrClient client,String collection) {
		Assert.hasText(collection, "collection must not be 'null'");
		boolean flag=true;
		if (isExist(client,collection)) {
			Delete delete = CollectionAdminRequest.deleteCollection(collection);
			CollectionAdminResponse response=null;
			try {
				response=delete.process(client);
				flag=response.isSuccess();
			} catch (SolrServerException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else{
			try {
				throw new Exception("collection is not exit!!!");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return flag;
	}
	/**
	 * 
	 * TODO: 获取solr中SchemaDynamicFields集合 
	 * TODO: 指定collection获取其schema文件中所有SchemaDynamicFields集合 
	 * 
	 * @param client 集群客户端连接
	 * @param collection 索引名
	 * @return 
	 * @throws IOException 
	 * @throws SolrServerException 
	 * @see com.taiji.bdp2.solr.collection.CollectionOperations#getSchemaDynamicFields(java.lang.String)
	 */
	public List<Map<String, Object>> getSchemaDynamicFields(CloudSolrClient client,String collection) throws SolrServerException, IOException {
		Assert.hasText(collection, "collection must not be 'null'");
		List<Map<String, Object>> dynamicFields=new ArrayList<Map<String,Object>>();
		SchemaRequest.DynamicFields Fields=new SchemaRequest.DynamicFields();
		DynamicFieldsResponse response=Fields.process(client, collection);
		dynamicFields=response.getDynamicFields();
		return dynamicFields;
	}
	/**
	 * 
	 * TODO: 获取collection列表集合
	 * TODO: 获取solr集群中所有collection列表 
	 * 
	 * @param client 集群客户端连接
	 * @return 
	 * @throws SolrServerException 
	 * @throws IOException 
	 * @see com.taiji.bdp2.solr.collection.CollectionOperations#getCollectionsList()
	 */
	public List<String> getCollectionsList(CloudSolrClient client) throws IOException, SolrServerException {
		return CollectionAdminRequest.listCollections(client);
	}
	/**
	 * 
	 * TODO: solr集群状态
	 * TODO: solr集群状态信息 
	 * 
	 * @param client 集群客户端连接
	 * @return 
	 * @see com.taiji.bdp2.solr.collection.CollectionOperations#getCusterState()
	 */
	public ClusterState getCusterState(CloudSolrClient client) {
		return client.getZkStateReader().getClusterState();
	}
	/**
	 * 
	 * TODO: 获取solr集群中collection信息
	 * TODO: 获取solr集群中collection信息 
	 * 
	 * @param client 集群客户端连接
	 * @return 
	 * @see com.taiji.bdp2.solr.collection.CollectionOperations#getCollectionsInfo()
	 */
	public Map<String, DocCollection> getCollectionsInfo(CloudSolrClient client) {
		return client.getZkStateReader().getClusterState().getCollectionsMap();
	}
	/**
	 * 
	 * TODO: 获取指定collection的field类型 
	 * TODO: 获取指定collection的field类型集合 
	 * 
	 * @param client 集群客户端连接
	 * @param collection 索引名
	 * @return 
	 * @throws IOException 
	 * @throws SolrServerException 
	 * @throws Exception 
	 * @see com.taiji.bdp2.solr.collection.CollectionOperations#getFieldType(java.lang.String)
	 */
	public List<Map<String, Object>> getFieldType(CloudSolrClient client,String collection) throws SolrServerException, IOException{
		Assert.hasText(collection, "collection must not be 'null'");
		List<Map<String, Object>> list=new ArrayList<Map<String,Object>>();
		FieldTypes allFields=new SchemaRequest.FieldTypes();
		FieldTypesResponse response=allFields.process(client, collection);
		List<FieldTypeRepresentation> fieldType=response.getFieldTypes();
		for (FieldTypeRepresentation fieldTypeRepresentation : fieldType) {
			Map<String,Object> map=fieldTypeRepresentation.getAttributes();
			list.add(map);
		}
		return list;
	}
	/**
	 * 
	 * TODO: 创建field 
	 * TODO: 批量添加field 
	 * 
	 * @param client 集群客户端连接
	 * @param collection 索引名
	 * @param columns 添加的字段集合
	 * @throws IOException 
	 * @throws SolrServerException 
	 * @see com.taiji.bdp2.solr.collection.CollectionOperations#createFields(java.lang.String, java.util.List)
	 */
	public boolean createFields(CloudSolrClient client,String collection, List<Map<String, Object>> columns) throws SolrServerException, IOException {
		Assert.hasText(collection, "collection must not be 'null'");
		boolean flag=true;
		List<Map<String, Object>> schemaFields = getSchemaFields(client,collection);
		List<SchemaRequest.Update> list = new ArrayList<SchemaRequest.Update>();
		for (Map<String,Object> column : columns) {
			if (schemaFields != null && !schemaFields.contains(column.get("columnZhName"))) {
				// 增加到字段到Collection
				Map<String, Object> fieldAttributes=new HashMap<String, Object>();
				fieldAttributes.put("name", column.get("columnZhName")+groupFiledNamePostfix); //域名
				fieldAttributes.put("type", column.get("type")); //域的类型，可以是string，int，如果需要分词设为text_ik
				fieldAttributes.put("indexed",true);           //是否索引，默认为true
				fieldAttributes.put("stored",true);            //是否存储，默认为true
				fieldAttributes.put("multiValued",false);      //是否多值，默认为false
				fieldAttributes.put("required",false);         //是否必须，默认false，schema文件中有一个id已默认必须
				SchemaRequest.AddField addField=new SchemaRequest.AddField(fieldAttributes);
				list.add(addField);
				fieldAttributes=new HashMap<String,Object>();
				fieldAttributes.put("name", column.get("columnZhName"));  //域名
				fieldAttributes.put("type", column.get("type"));   //域的类型，可以是string，int，如果需要分词设为text_ik
				fieldAttributes.put("indexed",true);           //是否索引，默认为true
				fieldAttributes.put("stored",true);            //是否存储，默认为true
				fieldAttributes.put("multiValued",false);      //是否多值，默认为false
				fieldAttributes.put("required",false);         //是否必须，默认false，schema文件中有一个id已默认必须
				addField=new SchemaRequest.AddField(fieldAttributes);
				list.add(addField);
			}
		}
		SchemaRequest.MultiUpdate multiUpdateRequest = new SchemaRequest.MultiUpdate(list);
		UpdateResponse response=multiUpdateRequest.process(client,collection);
		flag=response.getResponse().getBooleanArg("success");
		if (flag) {
			return true;
		}else {
			return false;
		}
	}
	/**
	 * 
	 * TODO: 添加副本 
	 * TODO: 指定collection下指定的shard添加副本 
	 * 
	 * @param client 集群客户端连接
	 * @param collection 索引名
	 * @param shard 分片名
	 * @throws IOException 
	 * @throws SolrServerException 
	 * @see com.taiji.bdp2.solr.collection.CollectionOperations#addReplicaToShard(java.lang.String, java.lang.String)
	 */
	public boolean addReplicaToShard(CloudSolrClient client,String collection, String shard) throws SolrServerException, IOException {
		Assert.hasText(collection, "collection must not be 'null'");
		Assert.hasText(shard, "shard must not be 'null'");
		AddReplica add=CollectionAdminRequest.Create.addReplicaToShard(collection, shard);
		CollectionAdminResponse response=add.process(client, collection);
		return response.isSuccess();
	}
	/**
	 * 
	 * TODO: 创建分片 
	 * TODO: 指定collection下创建新的分片
	 * 
	 * @param client 集群客户端连接
	 * @param collection 索引名
	 * @param shard 分片名
	 * @throws IOException 
	 * @throws SolrServerException 
	 * @see com.taiji.bdp2.solr.collection.CollectionOperations#createShard(java.lang.String, java.lang.String)
	 */
	public boolean createShard(CloudSolrClient client,String collection, String shard) throws SolrServerException, IOException {
		Assert.hasText(collection, "collection must not be 'null'");
		Assert.hasText(shard, "shard must not be 'null'");
		CreateShard createShard=CollectionAdminRequest.Create.createShard(collection, shard);
		CollectionAdminResponse response=createShard.process(client, collection);
		return response.isSuccess();
	}
	/**
	 * 
	 * TODO: 删除副本 
	 * TODO: 删除指定collection，指定shard的指定副本 
	 * 
	 * @param client 集群客户端连接
	 * @param collection 索引名
	 * @param shard 分片名
	 * @param replica 副本名
	 * @throws IOException 
	 * @throws SolrServerException 
	 * @see com.taiji.bdp2.solr.collection.CollectionOperations#deleteReplica(java.lang.String, java.lang.String, java.lang.String)
	 */
	public boolean deleteReplica(CloudSolrClient client,String collection, String shard, String replica) throws SolrServerException, IOException {
		Assert.hasText(collection, "collection must not be 'null'");
		Assert.hasText(shard, "shard must not be 'null'");
		Assert.hasText(replica, "replica must not be 'null'");
		DeleteReplica deleteReplica=CollectionAdminRequest.Create.deleteReplica(collection, shard, replica);
		CollectionAdminResponse response=deleteReplica.process(client);
		return response.isSuccess();
	}
	/**
	 * 
	 * TODO: 分割分片 
	 * TODO: 指定collection下分割指定的shard 
	 * 
	 * @param client 集群客户端连接
	 * @param collection 索引名
	 * @param shard 分片名
	 * @throws IOException 
	 * @throws SolrServerException 
	 * @see com.taiji.bdp2.solr.collection.CollectionOperations#splitShard(java.lang.String, java.lang.String)
	 */
	public boolean splitShard(CloudSolrClient client,String collection, String shard) throws SolrServerException, IOException {
		Assert.hasText(collection, "collection must not be 'null'");
		Assert.hasText(shard, "shard must not be 'null'");
		SplitShard splitShard=CollectionAdminRequest.Create.splitShard(collection).setShardName(shard);
		CollectionAdminResponse response=splitShard.process(client);
		return response.isSuccess();
	}
	/**
	 * 
	 * TODO: 删除分片 
	 * TODO: 删除指定分片 
	 * 
	 * @param client 集群客户端连接
	 * @param collection 索引民
	 * @param shard 分片名
	 * @throws IOException 
	 * @throws SolrServerException 
	 * @see com.taiji.bdp2.solr.collection.CollectionOperations#deleteShard(java.lang.String, java.lang.String)
	 */
	public boolean deleteShard(CloudSolrClient client,String collection, String shard) throws SolrServerException, IOException {
		Assert.hasText(collection, "collection must not be 'null'");
		Assert.hasText(shard, "shard must not be 'null'");
		DeleteShard deleteShard=CollectionAdminRequest.Create.deleteShard(collection, shard);
		CollectionAdminResponse response=deleteShard.process(client);
		return response.isSuccess();
	}
	/**
	 * 
	 * TODO: solr分词测试方法 
	 * TODO: solr分词测试，可以测试指定collection下的设置了分词类型的字段fieldName来测试指定内容分词结果 
	 * 
	 * @param client 集群客户端连接
	 * @param collection 索引名
	 * @param fieldName 字段名
	 * @param fieldValue 字段值，可为空
	 * @param sentence 测试内容
	 * @return 
	 * @see com.taiji.bdp2.solr.collection.CollectionOperations#getAnalysis(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	public List<String> getAnalysis(CloudSolrClient client,String collection,String fieldName, String fieldValue, String sentence) {
		Assert.hasText(collection, "collection must not be 'null'");
		Assert.hasText(fieldName, "shard must not be 'null'");
		Assert.hasText(sentence, "sentence must not be 'null'");
		FieldAnalysisRequest request = new FieldAnalysisRequest(
				"/analysis/field");
		request.addFieldName(fieldName);  //设置了分词的字段
		request.setFieldValue(fieldValue); //该字段的值，可以为空
		request.setQuery(sentence); //要测试分词的内容
		FieldAnalysisResponse response=null;
		List<String> results=null;
		try {
			response=request.process(client, collection);
			results = new ArrayList<String>();
			Iterator<AnalysisPhase> it = response.getFieldNameAnalysis(fieldName)
	                .getQueryPhases().iterator();
			while(it.hasNext()) {
	            AnalysisPhase pharse = (AnalysisPhase)it.next();
	            List<TokenInfo> list = pharse.getTokens();
	            for (TokenInfo info : list) {
	                results.add(info.getText());
	            }
	        }
		} catch (SolrServerException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return results;
	}
}

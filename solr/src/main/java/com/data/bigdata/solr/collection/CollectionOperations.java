package com.data.bigdata.solr.collection;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CloudSolrClient;
import org.apache.solr.common.cloud.ClusterState;
import org.apache.solr.common.cloud.DocCollection;

/**
 * 
 * TODO:「简要描述该 class|package 的作用，且注意首行必须以“.”结尾」. 
 * TODO:「详细描述该 class|package 的作用」 
 * 
 * <p>Created On 2018年11月7日 上午9:36:01 </p> 
 * 
 * @since TODO：「若是非首次程序发布时新增的类，需标注下次发布的版本号。否则，可移除」 
 * @author zhaiyongji 
 * @see 「[package.][class][#constructor|method|field|]」
 */
public interface CollectionOperations {
	/**
	 * 
	 * TODO: 创建一个collection 
	 * TODO: 新建一个collection，并进行相关的设置
	 * 
	 * 「如果是泛型使用 @param <T> 进行参数说明」 
	 * @param client 集群客户端连接
	 * @param collection 索引名称
	 * @param numShards  分片数
	 * @param numReplicas  副本数
	 * @param maxNumShards 最大分片数
	 * @param routerName  路由名称
	 * @param shards  分片名称
	 * @throws 「若未抛出异常则删除,若抛出异常需写明在什么情况下会抛出该异常」 
	 * @since TODO：「若是非首次程序发布时新增的方法，需标注下次发布的版本号。否则，可移除」 
	 * @see「[package.][class][#constructor|method|field|]」
	 */
	boolean createCollection(CloudSolrClient client,String collection,int numShards, int numReplicas,int maxNumShards,String routerName,String shards);
	/**
	 * 
	 * TODO: 获取schemaCopyFields字段列表信息
	 * TODO: 获取指定索引下schemaCopyFields字段列表信息
	 * 
	 * 「如果是泛型使用 @param <T> 进行参数说明」
	 * @param client 集群客户端连接 
	 * @param collection 索引名称
	 * @return 
	 * @throws IOException 
	 * @throws SolrServerException 
	 * @throws 「若未抛出异常则删除,若抛出异常需写明在什么情况下会抛出该异常」 
	 * @since TODO：「若是非首次程序发布时新增的方法，需标注下次发布的版本号。否则，可移除」 
	 * @see「[package.][class][#constructor|method|field|]」
	 */
	List<Map<String, Object>> getSchemaCopyFields(CloudSolrClient client,String collection) throws SolrServerException, IOException;
	/**
	 * 
	 * TODO: 删除schema中的field 
	 * TODO: 删除指定collection下的schema的field 
	 * 
	 * 「如果是泛型使用 @param <T> 进行参数说明」 
	 * @param client 集群客户端连接
	 * @param collection 索引名称
	 * @param fieldName  字段名称
	 * @throws SolrServerException
	 * @throws IOException 
	 * @throws 「客户端执行操作deleteField.process(client, collection)时，会抛出异常」 
	 * @since TODO：「若是非首次程序发布时新增的方法，需标注下次发布的版本号。否则，可移除」 
	 * @see「[package.][class][#constructor|method|field|]」
	 */
	boolean deleteField(CloudSolrClient client,String collection,String fieldName) throws SolrServerException, IOException;
	/**
	 * 
	 * TODO: 更新field字段信息 
	 * TODO: 更新指定collection下的field字段信息
	 * 
	 * 「如果是泛型使用 @param <T> 进行参数说明」
	 * @param client 集群客户端连接 
	 * @param collection 
	 * @throws IOException 
	 * @throws SolrServerException 
	 * @throws 执行replaceField.process(client, collection)操作,collection不存在或client连接异常时会抛出异常
	 * @since TODO：「若是非首次程序发布时新增的方法，需标注下次发布的版本号。否则，可移除」 
	 * @see「[package.][class][#constructor|method|field|]」
	 */
	boolean updateField(CloudSolrClient client,String collection,List<Map<String, Object>> columns) throws SolrServerException, IOException;
	/**
	 * 
	 * TODO: 判断collection是否存在
	 * TODO: 在对collection进行相关操作时，用于判断该collection是否存在 
	 * 
	 * 「如果是泛型使用 @param <T> 进行参数说明」 
	 * @param client 集群客户端连接
	 * @param collection
	 * @return 
	 * @since TODO：「若是非首次程序发布时新增的方法，需标注下次发布的版本号。否则，可移除」 
	 * @see「[package.][class][#constructor|method|field|]」
	 */
	boolean isExist(CloudSolrClient client,String collection);
	/**
	 * 
	 * TODO: 获取SchemaFields信息
	 * TODO: 获取指定collection中的schema字段信息 
	 * 
	 * 「如果是泛型使用 @param <T> 进行参数说明」 
	 * @param client 集群客户端连接
	 * @param collection
	 * @return 
	 * @throws IOException 
	 * @throws SolrServerException 
	 * @since TODO：「若是非首次程序发布时新增的方法，需标注下次发布的版本号。否则，可移除」 
	 * @see「[package.][class][#constructor|method|field|]」
	 */
	List<Map<String, Object>> getSchemaFields(CloudSolrClient client,String collection) throws SolrServerException, IOException;
	/**
	 * 
	 * TODO: 获取SchemaDynamicFields列表信息 
	 * TODO: 获取指定collection中的schema动态字段信息 
	 * 
	 * 「如果是泛型使用 @param <T> 进行参数说明」
	 * @param client 集群客户端连接 
	 * @param collection 索引名称
	 * @return 
	 * @throws IOException 
	 * @throws SolrServerException 
	 * @throws Exception 
	 * @since TODO：「若是非首次程序发布时新增的方法，需标注下次发布的版本号。否则，可移除」 
	 * @see「[package.][class][#constructor|method|field|]」
	 */
	List<Map<String, Object>> getSchemaDynamicFields(CloudSolrClient client,String collection) throws SolrServerException, IOException;
	/**
	 * 
	 * TODO: 删除collection 
	 * TODO: 删除solr集群中的指定的collection 
	 * 
	 * 「如果是泛型使用 @param <T> 进行参数说明」
	 * @param client 集群客户端连接 
	 * @param collection 索引名称
	 * @return 
	 * @since TODO：「若是非首次程序发布时新增的方法，需标注下次发布的版本号。否则，可移除」 
	 * @see「[package.][class][#constructor|method|field|]」
	 */
	boolean deleteCollection(CloudSolrClient client,String collection);
	/**
	 * 
	 * TODO: 获取所有collection名称 
	 * TODO: 获取solrCloud中所有collection个名称信息 
	 * 
	 * 「如果是泛型使用 @param <T> 进行参数说明」 
	 * @param client 集群客户端连接
	 * @return 
	 * @throws SolrServerException 
	 * @throws IOException 
	 * @since TODO：「若是非首次程序发布时新增的方法，需标注下次发布的版本号。否则，可移除」 
	 * @see「[package.][class][#constructor|method|field|]」
	 */
	List<String> getCollectionsList(CloudSolrClient client) throws IOException, SolrServerException;
	/**
	 * 
	 * TODO: 获取solr集群状态信息 
	 * TODO: 获取solrCloud相关状态 
	 * 
	 * 「如果是泛型使用 @param <T> 进行参数说明」 
	 * @param client 集群客户端连接
	 * @return 
	 * @since TODO：「若是非首次程序发布时新增的方法，需标注下次发布的版本号。否则，可移除」 
	 * @see「[package.][class][#constructor|method|field|]」
	 */
	ClusterState getCusterState(CloudSolrClient client);
	/**
	 * 
	 * TODO: 获取solr集群中collection的相关信息
	 * TODO: 获取solr集群中collection的相关信息 
	 * 
	 * 「如果是泛型使用 @param <T> 进行参数说明」 
	 * @param client 集群客户端连接
	 * @return 
	 * @since TODO：「若是非首次程序发布时新增的方法，需标注下次发布的版本号。否则，可移除」 
	 * @see「[package.][class][#constructor|method|field|]」
	 */
	Map<String, DocCollection> getCollectionsInfo(CloudSolrClient client);
	/**
	 * 
	 * TODO: 获取schema中的field类型 
	 * TODO: 获取指定collection下的schema中的field类型集合 
	 * 
	 * 「如果是泛型使用 @param <T> 进行参数说明」 
	 * @param client 集群客户端连接
	 * @param collection 索引名称
	 * @return 
	 * @throws IOException 
	 * @throws SolrServerException 
	 * @throws Exception 
	 * @since TODO：「若是非首次程序发布时新增的方法，需标注下次发布的版本号。否则，可移除」 
	 * @see「[package.][class][#constructor|method|field|]」
	 */
	List<Map<String, Object>> getFieldType(CloudSolrClient client,String collection) throws SolrServerException, IOException;
	/**
	 * 
	 * TODO: 添加field信息
	 * TODO: 给指定的collection个中添加相应的field配置信息 
	 * 
	 * 「如果是泛型使用 @param <T> 进行参数说明」
	 * @param client 集群客户端连接 
	 * @param collection  索引名称
	 * @param columns  需要添加的field字段集合
	 * @throws IOException 
	 * @throws SolrServerException 
	 * @since TODO：「若是非首次程序发布时新增的方法，需标注下次发布的版本号。否则，可移除」 
	 * @see「[package.][class][#constructor|method|field|]」
	 */
	boolean createFields(CloudSolrClient client,String collection, List<Map<String,Object>> columns) throws SolrServerException, IOException;
	/**
	 * 
	 * TODO: 给指定的分片添加一个副本
	 * TODO: solr集群中对于指定collection下的一个shard添加一个副本 
	 * 
	 * 「如果是泛型使用 @param <T> 进行参数说明」 
	 * @param client 集群客户端连接
	 * @param collection 索引名称
	 * @param shard 分片名称
	 * @return
	 * @throws SolrServerException
	 * @throws IOException 
	 * @throws 「客户端执行添加副本add.process(client, collection)命令时会抛出异常」 
	 * @since TODO：「若是非首次程序发布时新增的方法，需标注下次发布的版本号。否则，可移除」 
	 * @see「[package.][class][#constructor|method|field|]」
	 */
	boolean addReplicaToShard(CloudSolrClient client,String collection,String shard) throws SolrServerException, IOException;
	/**
	 * 
	 * TODO: 创建新的分片
	 * TODO: 指定集合添加新的分片
	 * 
	 * 「如果是泛型使用 @param <T> 进行参数说明」 
	 * @param client 集群客户端连接
	 * @param collection 索引名称
	 * @param shard 分片名称
	 * @return
	 * @throws SolrServerException
	 * @throws IOException 
	 * @throws 「执行createShard.process(client, collection)创建分片时会抛出异常」 
	 * @since TODO：「若是非首次程序发布时新增的方法，需标注下次发布的版本号。否则，可移除」 
	 * @see「[package.][class][#constructor|method|field|]」
	 */
	boolean createShard(CloudSolrClient client,String collection,String shard) throws SolrServerException, IOException;
	/**
	 * 
	 * TODO: 删除副本 
	 * TODO: 删除指定的collection下的一个shard指定的副本 
	 * 
	 * 「如果是泛型使用 @param <T> 进行参数说明」 
	 * @param client 集群客户端连接
	 * @param collection 索引名称
	 * @param shard 分片名
	 * @param replica 副本名
	 * @return
	 * @throws SolrServerException
	 * @throws IOException 
	 * @throws 「客户端在执行删除命令deleteReplica.process(client)时会抛出异常」 
	 * @since TODO：「若是非首次程序发布时新增的方法，需标注下次发布的版本号。否则，可移除」 
	 * @see「[package.][class][#constructor|method|field|]」
	 */
	boolean deleteReplica(CloudSolrClient client,String collection, String shard, String replica) throws SolrServerException, IOException;
	/**
	 * 
	 * TODO: 分割分片  
	 * TODO: 分割分片 
	 * 
	 * 「如果是泛型使用 @param <T> 进行参数说明」
	 * @param client 集群客户端连接 
	 * @param collection 索引名
	 * @param shard 分片名
	 * @return
	 * @throws SolrServerException
	 * @throws IOException 
	 * @throws 「客户端执行分割操作splitShard.process(client)时，会抛出异常」 
	 * @since TODO：「若是非首次程序发布时新增的方法，需标注下次发布的版本号。否则，可移除」 
	 * @see「[package.][class][#constructor|method|field|]」
	 */
	boolean splitShard(CloudSolrClient client,String collection,String shard) throws SolrServerException, IOException;
	/**
	 * 
	 * TODO: 删除分片
	 * TODO: 删除分片 
	 * 
	 * 「如果是泛型使用 @param <T> 进行参数说明」 
	 * @param client 集群客户端连接
	 * @param collection
	 * @param shard
	 * @return
	 * @throws SolrServerException
	 * @throws IOException 
	 * @throws 「客户端执行删除操作deleteShard.process(client)时，会抛出异常」 
	 * @since TODO：「若是非首次程序发布时新增的方法，需标注下次发布的版本号。否则，可移除」 
	 * @see「[package.][class][#constructor|method|field|]」
	 */
	boolean deleteShard(CloudSolrClient client,String collection,String shard) throws SolrServerException, IOException;
	/**
	 * 
	 * TODO: solr分词测试方法 
	 * TODO: solr分词测试指定语句分词效果 
	 * 
	 * 「如果是泛型使用 @param <T> 进行参数说明」 
	 * @param client 集群客户端连接
	 * @param collection 索引名
	 * @param fieldName 字段名
	 * @param fieldValue 字段值，可为空
	 * @param sentence 测试字段
	 * @return 
	 * @since TODO：「若是非首次程序发布时新增的方法，需标注下次发布的版本号。否则，可移除」 
	 * @see「[package.][class][#constructor|method|field|]」
	 */
	List<String> getAnalysis(CloudSolrClient client,String collection,String fieldName,String fieldValue,String sentence);
}

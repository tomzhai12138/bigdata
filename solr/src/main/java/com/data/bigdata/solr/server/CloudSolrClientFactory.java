/*
 * Copyright 2014 - 2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.data.bigdata.solr.server;

import java.util.List;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.impl.CloudSolrClient;

/**
 * 
 * @ClassName:  CloudSolrClientFactory   
 * @Description: CloudSolrClientFactory新增   
 * @author: zhiayongji
 * @date:   2018年8月24日 下午4:27:04   
 * A factory class which can be used as a parameter to {@link SolrTemplate} constructor in order to use
 * {@link CloudSolrClient} and connect to a SolrCloud collection installation.    
 * @Copyright: © 2018 太极计算机股份有限公司版权所有
 */
public class CloudSolrClientFactory extends SolrClientFactoryBase {

	public CloudSolrClientFactory() {

	}

	public CloudSolrClientFactory(SolrClient client) {
		super(client);
	}

	/**
	 * Returns the same as {@link #getSolrClient()}, as we are in SolrCloud mode.
	 */
	@Override
	public SolrClient getSolrClient() {
		return super.getSolrClient();
	}

	@Override
	public SolrClient getSolrClient(String core) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getCores() {
		// TODO Auto-generated method stub
		return null;
	}
}
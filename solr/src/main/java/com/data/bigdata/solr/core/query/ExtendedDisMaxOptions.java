/**
 * Created on 2018年9月25日 上午11:16:46 <br>
 * TODO:「描述该 package 或 Java File 的作用」
 * @author tj.wlj
 * <p>Copyright: 2018 www.taiji.com.cn.com Inc. All rights reserved.</p> 
 * <p>注意：本内容仅限于太极计算机股份有限公司内部传阅，禁止外泄以及用于其他的商业目</p> 
 */
package com.data.bigdata.solr.core.query;

import java.util.Map;

import org.springframework.util.Assert;

/**
 * 
 * @ClassName:  ExtendedDisMaxOptions   
 * @Description:  TODO(这里用一句话描述这个类的作用)   
 * @author: zhaiyongji
 * @date:   2019年5月21日 上午11:08:08   
 *     
 * @Copyright: © 2019 版权所有
 */
public class ExtendedDisMaxOptions extends DisMaxOptions {
		
	private ExtendedDisMaxOptions() {
	
	}
	
	public static ExtendedDisMaxOptions builder() {
		return new ExtendedDisMaxOptions();
	}

	/**
	 * uf 用户字段。制定模式的字段可以被用户显示的查询。此参数支持通配符。
	 */
	public ExtendedDisMaxOptions withUserFields(String value) {
		Assert.notNull(value, "uf must not be null!");
		
		super.getOptions().put("uf", value);
		return this;
	}
	
	/**
	 * pf2 短语两字母字段。
	 * e.g. “the brown fox jumped” is queried as “the brown” “brown fox” “fox jumped”。
	 */
	public ExtendedDisMaxOptions withPhraseBigramFields(String value) {
		Assert.notNull(value, "pf2 must not be null!");
		
		super.getOptions().put("pf2", value);
		return this;
	}
	
	/**
	 * pf3 短语三字母字段。
	 * e.g. “the brown fox jumped” is queried as “the brown fox” “brown fox jumped”。
	 */
	public ExtendedDisMaxOptions withPhraseTrigramFields(String value) {
		Assert.notNull(value, "pf3 must not be null!");
		
		super.getOptions().put("pf3", value);
		return this;
	}
	
	/**
	 * ps2 短语两字母坡度。如果未指定，将使用”ps”。
	 */
	public ExtendedDisMaxOptions withPhraseBigramSlop(String value) {
		Assert.notNull(value, "ps2 must not be null!");
		
		super.getOptions().put("ps2", value);
		return this;
	}
	
	/**
	 * ps3 短语三字母坡度。如果未指定，将使用”ps”。
	 */
	public ExtendedDisMaxOptions withPhraseTrigramSlop(String value) {
		Assert.notNull(value, "ps3 must not be null!");
		
		super.getOptions().put("ps3", value);
		return this;
	}

	/**
	 * 
	 * <p>Title: getOptions</p>   
	 * <p>Description: </p>  
	 * <p>auth: zhaiyongji</p> 
	 * @return   
	 * @see com.data.cluster.component.solr.core.query.DisMaxOptions#getOptions()
	 */
	@Override
	public Map<String, String> getOptions() {
		Map<String, String> map=super.getOptions();
		map.put("defType", "edismax");
		return map;
	}
}

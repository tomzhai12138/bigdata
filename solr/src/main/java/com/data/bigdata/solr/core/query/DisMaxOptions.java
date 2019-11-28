/**
 * Created on 2018年9月25日 上午11:16:03 <br>
 */
package com.data.bigdata.solr.core.query;

import java.util.HashMap;
import java.util.Map;

import org.apache.solr.common.params.DisMaxParams;
import org.springframework.util.Assert;

/**
 * 
 * @ClassName:  DisMaxOptions   
 * @Description:  TODO(这里用一句话描述这个类的作用)   
 * @author: zhaiyongji
 * @date:   2019年5月21日 上午11:07:50   
 *     
 * @Copyright: © 2019 版权所有
 */
public class DisMaxOptions {
	
	public static final String DEFAULT_Q_ALT="*:*";
	
	private Map<String, String> dismax = new HashMap<String, String>();
	
	public DisMaxOptions() {
		
	}
	
	public static DisMaxOptions builder() {
		return new DisMaxOptions();
	}


	/**
	 * q.alt 当q字段为空时，用于设置缺省的query，通常设置q.alt为*:*
	 */
	public DisMaxOptions withAltQuery(String value) {
		Assert.notNull(value, "QueryAlt must not be null!");
		
		dismax.put(DisMaxParams.ALTQ, value);
		return this;
	}

		
	/**
	 * qf 指定solr从哪些field中搜索 。也可以针对某个 Field 增加 boost 权重，提升其相关度得分
	 */
	public DisMaxOptions addQueryFields(String... values) {
		Assert.notNull(values, "QueryFields must not be null!");
		
		StringBuffer buffer = new StringBuffer();
		for (String value:values){
			buffer.append(value).append(" ");
		}
		//dismax.put(DisMaxParams.ALTQ, buffer.toString());
		dismax.put(DisMaxParams.QF, buffer.toString());
		buffer.delete(0, buffer.length());
		return this;
	}
	
	/**
	 * mm Solr支持三种查询clause，即“必须出现”， “不能出现”和“可以出现”，分别对应于AND, -, OR。
	 * 当 mm 不设置的时候，如果设置 boolean 查询逻辑为 AND ，则 mm=100%，搜索词被切分后都必须出现；
	 * 如果查询逻辑为 OR，则 mm=1 出现搜索串切分后的一个词语就可以
	 * mm取值包括：正整数、负整数、正百分数、负百分数。
	 * 正数标识分析器分词后的查询结果为必须出现的词的个数；负数标识可以不出现的词的个数
	 * 
	 * 如，取值 -2 标识可以有任意两个词不匹配中
	 */
	public DisMaxOptions withMM(String value) {
		Assert.notNull(value, "mm must not be null!");
		
		dismax.put("mm", value);
		return this;
	}
	
	/**
	 * qs 查询短语坡度:查询短语坡度是指短语查询明确包含用户查询的字符串(在qf字段，影响匹配)。
	 */
	public DisMaxOptions withQueryPhraseSlop(String value) {
		Assert.notNull(value, "qs must not be null!");
		
		dismax.put("qs", value);
		return this;
	}
	
	
	/**
	 * tie float值作为决胜局中DisjunctionMaxQueries使用(应该是远小于1)
	 */
	public DisMaxOptions withTirBreaker(String value) {
		Assert.notNull(value, "tie must not be null!");
		
		dismax.put("tie", value);
		return this;
	}
	
	/**
	 * bq 对某个field的value进行boost,例如field:value^5.0
	 */
	public DisMaxOptions withBoostQuery(String value) {
		Assert.notNull(value, "bq must not be null!");
		
		dismax.put("bq", value);
		return this;
	}
	
	/**
	 * bf 用函数的方式计算boost，用来影响最终得分
	 */
	public DisMaxOptions withBoostFunction(String value) {
		Assert.notNull(value, "bf must not be null!");
		
		dismax.put("bf", value);
		return this;
	}
	
	/**
	 * pf 用于指定一组field，当query完全匹配pf指定的某一个field时，来进行boost
	 */
	public DisMaxOptions withPhraseFields(String value) {
		Assert.notNull(value, "pf must not be null!");
		
		dismax.put("pf", value);
		return this;
	}
	
	/**
	 * ps 短语坡度。短语查询的坡度量用在pf字段，影响boost。
	 */
	public DisMaxOptions withPhraseSlop(String value) {
		Assert.notNull(value, "ps must not be null!");
		
		dismax.put("ps", value);
		return this;
	}
	
	public Map<String, String> getOptions() {
		dismax.put("defType", "dismax");
		return dismax;
	}
}

package com.data.bigdata.elastic.entity;

import java.io.Serializable;
/**
 * 
 * TODO: 创建mapping映射字段设置模板类
 * TODO: 创建mapping映射字段设置模板类 
 * 
 * <p>Created On 2018年11月13日 下午3:59:37 </p> 
 * 
 * @since TODO：「若是非首次程序发布时新增的类，需标注下次发布的版本号。否则，可移除」 
 * @author zhaiyongji 
 * @see 「[package.][class][#constructor|method|field|]」
 */
public class FieldInfo implements Serializable{

	/**  
	 * TODO:「描述该变量的作用」 
	 */
	private static final long serialVersionUID = 1L;
	private String field; //字段名称
	private String type;  //字段类型
	private int participle; //分词类型，取值：0,不分词；1，ik_smart；2，ik_max_word
	
	
	public String getField() {
		return field;
	}
	public void setField(String field) {
		this.field = field;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getParticiple() {
		return participle;
	}
	public void setParticiple(int participle) {
		this.participle = participle;
	}
	@Override
	public String toString() {
		return "FieldInfo [field=" + field + ", type=" + type + ", participle=" + participle + "]";
	}
	public FieldInfo(String field, String type, int participle) {
		super();
		this.field = field;
		this.type = type;
		this.participle = participle;
	}
	public FieldInfo() {
		super(); 
	}
	

}

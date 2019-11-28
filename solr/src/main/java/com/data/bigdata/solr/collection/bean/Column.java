package com.data.bigdata.solr.collection.bean;
/**
 * 
 * TODO:「field字段实体类」. 
 * TODO:「详细描述该 class|package 的作用」 
 * 
 * <p>Created On 2018年11月9日 上午11:16:24 </p> 
 * 
 * @since TODO：「若是非首次程序发布时新增的类，需标注下次发布的版本号。否则，可移除」 
 * @author Dell 
 * @see 「[package.][class][#constructor|method|field|]」
 */
public class Column{
	private String name;
	private String type;
	private boolean indexed;
	private boolean stored;
	private boolean multiValued;
	private boolean required;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public boolean isIndexed() {
		return indexed;
	}
	public void setIndexed(boolean indexed) {
		this.indexed = indexed;
	}
	public boolean isStored() {
		return stored;
	}
	public void setStored(boolean stored) {
		this.stored = stored;
	}
	public boolean isMultiValued() {
		return multiValued;
	}
	public void setMultiValued(boolean multiValued) {
		this.multiValued = multiValued;
	}
	public boolean isRequired() {
		return required;
	}
	public void setRequired(boolean required) {
		this.required = required;
	}
	@Override
	public String toString() {
		return "Column [name=" + name + ", type=" + type + ", indexed=" + indexed + ", stored=" + stored
				+ ", multiValued=" + multiValued + ", required=" + required + "]";
	}

}

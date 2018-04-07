package com.fuxi.mybatis.generator.domain;

import java.util.HashMap;
import java.util.Map;

import com.fuxi.mybatis.generator.StringUtils;

public class ColumnInfo {
	private static Map<String, String> classNameMap = new HashMap<>();
	private String name;
	private int type;
	private String className;
	private String comment;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = getClassName(className);
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	
	public String getPropName() {
		return StringUtils.camelized(name);
	}
	
	@Override
	public String toString() {
		return "ColumnInfo [name=" + name + ", type=" + type + ", className=" + className + ", comment=" + comment
				+ "]";
	}
	
	static {
		classNameMap.put("java.sql.Timestamp", "java.util.Date");
	}
	
	private static String getClassName(String className) {
		String newClassName = classNameMap.get(className);
		return newClassName == null ? className : newClassName;
	}
}

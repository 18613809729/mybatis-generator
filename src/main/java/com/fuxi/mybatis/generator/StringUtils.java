package com.fuxi.mybatis.generator;

public class StringUtils {

	public static String camelized(String str) {
		if(str != null) {
			StringBuilder sb = new StringBuilder();
			String[] items = str.toLowerCase().split("_");
			for (String item : items) {
				if(item.length() > 0 ) {
					sb.append(item.substring(0, 1).toUpperCase());
				}
				if(item.length() > 1 ) {
					sb.append(item.substring(1));
				}
			}
			str = sb.substring(0, 1).toLowerCase() + sb.substring(1);
		}
		return str;
	}

	public static String alias(String str) {
		if(str != null) {
			StringBuilder sb = new StringBuilder();
			String[] items = str.toLowerCase().split("_");
			for (String item : items) {
				if(item.length() > 0 ) {
					sb.append(item.substring(0, 1).toUpperCase());
				}
			}
			str = sb.toString().toLowerCase();
		}
		return str;
	}
	
	public static String capFirst(String str) {
		return str.substring(0, 1).toUpperCase() + str.substring(1);
	}
}

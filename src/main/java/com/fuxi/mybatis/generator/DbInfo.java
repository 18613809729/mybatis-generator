package com.fuxi.mybatis.generator;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.fuxi.mybatis.generator.domain.ColumnInfo;

public class DbInfo {
	private String jdbcUrl = "jdbc:mysql://193.112.44.135:3306/jiaxiao?serverTimezone=UTC&useUnicode=true&characterEncoding=utf8";
	private String dbuser = "root";
	private String dbpwd = "!LiX0i9a2n2g";
	
	
	public Connection getConnection() throws SQLException, ClassNotFoundException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		return DriverManager.getConnection(jdbcUrl, dbuser, dbpwd);
	}
	
	/**
	 * 获取当前数据库下的所有表名称
	 * @return
	 * @throws Exception
	 */
	public List<String> getAllTableName() throws SQLException, ClassNotFoundException {
		List<String> tables = new ArrayList<>();
		try(Connection conn =  getConnection()){
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SHOW TABLES ");
			while (rs.next()) {
				String tableName = rs.getString(1);
				tables.add(tableName);
			}
			return tables;
		}
	}
	

	/**
	 * 获得某表的建表语句
	 * @param tableName
	 * @return
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 * @throws Exception
	 */
	public String getComment(String tableName) throws ClassNotFoundException, SQLException  {
		try(Connection conn =  getConnection()){
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SHOW CREATE TABLE " + tableName);
			if (rs != null && rs.next()) {
				return exactComment(rs.getString(2));
			}
		}
		return null;
	}
	/**
	 * 获得某表中所有字段的注释
	 * @param tableName
	 * @return
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 * @throws Exception
	 */
	public Map<String, String> getColumnComment(String tableName) throws ClassNotFoundException, SQLException  {
		Map<String, String> commentMap = new HashMap<>();
		try(Connection conn =  getConnection()){
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("show full columns from " + tableName);
			 while (rs.next()) {   
				 commentMap.put(rs.getString("Field").toUpperCase(), rs.getString("Comment"));
			} 
		}
		return commentMap;
	}

	public List<ColumnInfo>  getColumnInfo(String tableName) throws ClassNotFoundException, SQLException  {
		List<ColumnInfo> columnInfos = new ArrayList<>();
		Map<String, String> comments = getColumnComment(tableName);
		try(Connection conn =  getConnection()){
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("select * from " + tableName);
			ResultSetMetaData metaData = rs.getMetaData();
			for (int i = 1; i <= metaData.getColumnCount(); i++) {
				ColumnInfo columnInfo = new ColumnInfo();
				columnInfo.setName(metaData.getColumnName(i).toUpperCase());
				columnInfo.setType(metaData.getColumnType(i));
				columnInfo.setClassName(metaData.getColumnClassName(i));
				columnInfo.setComment(comments.get(columnInfo.getName()));
				columnInfos.add(columnInfo);
			}
		}
		return columnInfos;
	}

	/**
	 * 返回注释信息
	 * @param all
	 * @return
	 */
	
	public static String exactComment(String createSql) {
		Pattern pattern = Pattern.compile("COMMENT='(.+)'");
		Matcher matcher = pattern.matcher(createSql);
		if(matcher.find()){
			return matcher.group(1);
		}
		return "";
	}

	public static void main(String[] args) throws Exception {
		DbInfo dbInfo = new DbInfo();
		System.out.println(dbInfo.getComment("user_info"));
		System.out.println(dbInfo.getColumnInfo("user_info"));
	}

}

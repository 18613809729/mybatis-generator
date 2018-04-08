package com.fuxi.mybatis.generator;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import com.fuxi.mybatis.generator.domain.ColumnInfo;

import freemarker.core.ParseException;
import freemarker.ext.beans.BeansWrapper;
import freemarker.template.Configuration;
import freemarker.template.MalformedTemplateNameException;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateHashModel;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;
import freemarker.template.TemplateNotFoundException;
import freemarker.template.utility.StringUtil;

public class Generator {
	public static void addUtils(Map<String, Object> root) throws TemplateModelException {
		BeansWrapper wrapper = BeansWrapper.getDefaultInstance();
		TemplateHashModel staticModels = wrapper.getStaticModels();
		TemplateModel stringUtilsStatics =  staticModels.get("com.fuxi.mybatis.generator.StringUtils");
		root.put("StringUtils", stringUtilsStatics);
	}
	public static Template getTemplate(String name) throws TemplateNotFoundException, MalformedTemplateNameException, ParseException, IOException {
		Configuration cfg = new Configuration();
		cfg.setClassForTemplateLoading(Generator.class, "/ftl");
		return cfg.getTemplate(name);
	}

	
	public static void create(String tableName) throws ClassNotFoundException, SQLException, TemplateNotFoundException, MalformedTemplateNameException, ParseException, TemplateException, IOException {
		tableName = tableName.toLowerCase();
		DbInfo dbInfo = new DbInfo();
		List<ColumnInfo> infos = dbInfo.getColumnInfo(tableName);
		infos.removeIf(columnInfo -> "DELETED".equals(columnInfo.getName()));
		Map<String, Object> map = new HashMap<>();
		map.put("columns", infos);
		map.put("mapperPackage", "com.nbs.jiaxiao.mapper");
		map.put("domainPackage", "com.nbs.jiaxiao.domain.po");
		map.put("servicePackage", "com.nbs.jiaxiao.service.db");
		map.put("tableName", tableName);
		map.put("tableComment", dbInfo.getComment(tableName));
		String domainName = StringUtils.capFirst(StringUtils.camelized(tableName));
		map.put("domainName", domainName);
		markSpecialColumn(map, infos);
		addUtils(map);
		String folder = "D:\\git\\jiaxiao\\src\\main\\java\\";
		write(map, "domain.ftl", folder + "com\\nbs\\jiaxiao\\domain\\po\\" + domainName + ".java");
		write(map, "Mapper.ftl", folder + "com\\nbs\\jiaxiao\\mapper\\" + domainName + "Mapper.xml");
		write(map, "MapperInterface.ftl", folder + "com\\nbs\\jiaxiao\\mapper\\" + domainName + "Mapper.java");
		write(map, "service.ftl", folder + "com\\nbs\\jiaxiao\\service\\db\\" + domainName + "Service.java");
		write(map, "serviceImpl.ftl", folder + "com\\nbs\\jiaxiao\\service\\db\\impl\\" + domainName + "ServiceImpl.java");
	}
	
	public static void write(Map<String, Object> map, String template, String fileName) throws TemplateNotFoundException, MalformedTemplateNameException, ParseException, TemplateException, IOException {
		Pattern customizedCodeStartReg = Pattern.compile("customized\\s+code\\s+start");
		Pattern customizedCodeEndReg =  Pattern.compile("customized\\s+code\\s+end");
		Pattern importReg =  Pattern.compile("^\\s*import\\s*");
		
		LinkedHashSet<String> importSet = new LinkedHashSet<>();
		
		//客户代码
		File file = new File(fileName);
		List<String> customizedCodeLst = new ArrayList<String>();
		if(file.exists()) {
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line = null;
			boolean isCustomizedCode = false;
			while((line = br.readLine()) != null) {
				if(importReg.matcher(line).find()) {
					importSet.add(line.trim());
				}
				if(customizedCodeStartReg.matcher(line).find()) {
					isCustomizedCode = true;
				}
				if(isCustomizedCode) {
					customizedCodeLst.add(line);
				}
				if(customizedCodeEndReg.matcher(line).find()) {
					isCustomizedCode = false;
				}
			}
			br.close();
		}
		
		
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		Writer writer = new OutputStreamWriter(bos);
		getTemplate(template).process(map, writer);
		BufferedReader br = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(bos.toByteArray())));
		List<String> lines = new ArrayList<String>();
		String line = null;
		boolean isCustomizedCode = false;
		boolean isImport = false;
		while((line = br.readLine()) != null) {
			if(line.startsWith("package")) {
				isImport = true;
				lines.add(line);
				lines.add("\r\n");
				lines.addAll(importSet);
				continue;
			}
			if(importReg.matcher(line).find()) {
				if(!importSet.contains(line.trim())) {
					lines.add(line);
				}
				continue;
			}
			if(isImport && line.trim().length() == 0) {
				continue;
			}
			
			if(isImport) {
				isImport = false;
				lines.add("\r\n");
			}
			if(customizedCodeStartReg.matcher(line).find()) {
				isCustomizedCode = true;
				lines.addAll(customizedCodeLst);
			}
			
			if(!isCustomizedCode || customizedCodeLst.size() == 0) {
				lines.add(line);
			} 
			
			if(customizedCodeEndReg.matcher(line).find()) {
				isCustomizedCode = false;
			}
		}
		BufferedWriter bw = new BufferedWriter(new FileWriter(file));
		for (String codeLine : lines) {
			bw.write(codeLine);
			bw.newLine();
		}
		bw.close();
	} 
	
	
	public static void main(String[] args) throws ClassNotFoundException, SQLException, TemplateNotFoundException, MalformedTemplateNameException, ParseException, TemplateException, IOException {
		create("pre_seller");
		create("seller");
		create("dict");
		create("fee");
		create("sign_student");
		create("student");
		create("user");
	}
	
	
	private static void markSpecialColumn(Map<String, Object> root, List<ColumnInfo> infos) {
		for (ColumnInfo columnInfo : infos) {
			switch (columnInfo.getName()) {
			case "CREATED_TIME":
				root.put("hasCreatedTime", true);
				break;
			case "MODIFIED_TIME":
				root.put("hasModifiedTime", true);
				break;
			case "LAST_UPDATE_NO":
				root.put("hasLastUpdateNo", true);
				break;
			default:
				break;
			}
		}
	}
}

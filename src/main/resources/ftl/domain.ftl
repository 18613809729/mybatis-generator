package ${domainPackage};

import com.nbs.jiaxiao.domain.base.Base;

/**
 * 
 * ${tableComment}
 *
 */
public class ${StringUtils.camelized(tableName)?cap_first} extends Base{
	
	<#list columns as column>
		<#if column.propName != "createdTime" && column.propName != "modifiedTime" && column.propName != "lastUpdateNo" && column.propName != "lastUpdateNoUserId">
	/* ${column.comment!} */
	private ${column.className} ${column.propName};
		</#if>
	</#list>
	
	
	<#list columns as column>
		<#if column.propName != "createdTime" && column.propName != "modifiedTime" && column.propName != "lastUpdateNo" && column.propName != "lastUpdateNoUserId">
	
	public ${column.className} get${column.propName?cap_first}() {
		return ${column.propName};
	}

	public void set${column.propName?cap_first}(${column.className} ${column.propName}) {
			<#if column.className == "java.lang.String">
		if (${column.propName} != null) {
			this.${column.propName} = ${column.propName}.trim();
		}
			<#else>
		this.${column.propName} = ${column.propName};
			</#if>
	}
		</#if>
	</#list>
	
	/* customized code start */
	
	/* customized code end */
}

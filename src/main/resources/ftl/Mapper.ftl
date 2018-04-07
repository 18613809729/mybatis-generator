<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="${mapperPackage}.${StringUtils.camelized(tableName)?cap_first}Mapper">
	<resultMap id="${StringUtils.camelized(tableName)}ResultMap" type="${domainPackage}.${StringUtils.camelized(tableName)?cap_first}">
  	<#list columns as column>
  		<result property="${column.propName}" column="${column.name}"<#if column.name == "MOBILE" || column.name == "CERT_NO"> typeHandler="com.nbs.jiaxiao.component.CryptTypeHandler"</#if>/>
  	</#list>
	</resultMap>

	<sql id="commonSelect">
		<#list columns as column>${column.name}<#sep>, </#list>
	</sql>
	
	<sql id="whereCondition">
		WHERE DELETED = 0 
		<#list columns as column>
			<#if column.name == "LAST_UPDATE_NO">
				<if test="${column.propName} != null"> AND ${column.name} = ${"#{"}${column.propName}}</if> 
	 			<if test="param.${column.propName}Start != null"> AND ${column.name} <![CDATA[>=]]> ${"#{"}param.${column.propName}Start} </if> 
	 			<if test="param.${column.propName}End != null"> AND ${column.name} <![CDATA[<=]]> ${"#{"}param.${column.propName}End} </if>
	 		<#elseif column.name == "CREATED_TIME" || column.name == "MODIFIED_TIME" >
	 			<if test="param.${column.propName}Start != null"> AND ${column.name} <![CDATA[>=]]> ${"#{"}param.${column.propName}Start} </if> 
	 			<if test="param.${column.propName}End != null"> AND ${column.name} <![CDATA[<=]]> ${"#{"}param.${column.propName}End} </if>
	 		<#else>
	 			<if test="${column.propName} != null"> AND ${column.name} = ${"#{"}${column.propName}<#if column.name == "MOBILE" || column.name == "CERT_NO">, typeHandler=com.nbs.jiaxiao.component.CryptTypeHandler</#if>}</if> 
	 		</#if>
    	</#list>
	</sql>
	
	<insert id="insert" useGeneratedKeys="true" keyProperty="id">
	 	INSERT  INTO  ${tableName} 
	 	<trim prefix="(" suffixOverrides="," suffix=")">
	 		<#list columns as column>
				<if test="${column.propName} != null">${column.name},</if> 
    		</#list>
	 	</trim>
		VALUES
		<trim prefix="(" suffixOverrides="," suffix=")">
		<#list columns as column>
			<if test="${column.propName} != null">${"#{"}${column.propName}<#if column.name == "MOBILE" || column.name == "CERT_NO">, typeHandler=com.nbs.jiaxiao.component.CryptTypeHandler</#if>},</if> 
		</#list>
		</trim>
	</insert>
	
	<update id="updateByPriKey">
		UPDATE   ${tableName} 
		<set>
		<#list columns as column>
			<#if column.name == "LAST_UPDATE_NO">
				LAST_UPDATE_NO = LAST_UPDATE_NO + 1,
			<#elseif column.name != "ID">
				<if test="${column.propName} != null">${column.name}=${"#{"}${column.propName}<#if column.name == "MOBILE" || column.name == "CERT_NO">, typeHandler=com.nbs.jiaxiao.component.CryptTypeHandler</#if>},</if>
			</#if>
		 </#list>
    	</set>
    	WHERE ID = ${r'#{id}'} <#if hasLastUpdateNo??> <if test="lastUpdateNo != null"> AND LAST_UPDATE_NO = ${r'#{lastUpdateNo}'} </if> </#if> AND DELETED = 0 
    </update>
	
	<update id="delete">
		UPDATE  ${tableName} SET DELETED = ID <#if hasLastUpdateNo??>, LAST_UPDATE_NO = LAST_UPDATE_NO + 1 </#if> <include refid="whereCondition"/>
	</update>
	
	<update id="deleteByPriKey">
		UPDATE  ${tableName} SET DELETED = ID <#if hasLastUpdateNo??>, LAST_UPDATE_NO = LAST_UPDATE_NO + 1 </#if> WHERE ID = ${r'#{id}'}  AND DELETED = 0 
	</update>
	
	<delete id="deleteActual">
		DELETE FROM ${tableName} <include refid="whereCondition"/>
	</delete>
	
	<delete id="deleteActualByPriKey">
		DELETE FROM ${tableName} WHERE ID = ${r'#{id}'}
	</delete>
	
	<delete id="clean">
		DELETE FROM ${tableName} WHERE ID > ${r'#{id}'} and DELETED != 0 
	</delete>
	
	<select id="selectByPriKey" resultMap="${StringUtils.camelized(tableName)}ResultMap">
		   SELECT   
		   <include refid="commonSelect" />
		   FROM   ${tableName} WHERE ID = ${r'#{id}'}
	</select>
	
	<select id="selectList" resultMap="${StringUtils.camelized(tableName)}ResultMap">
		   SELECT   
		   <include refid="commonSelect"/>
		   FROM   ${tableName}
		   <include refid="whereCondition"/> 
		   <if test="param.columnSort != null">ORDER BY ${r"${param.columnSort}"} </if>  
		   <if test="param.columnSort == null">ORDER BY ID </if>   
		   <if test="param.limit != null">limit param.offset, param.limit </if>   
	</select>
	
	<select id="selectCount" resultType="long">
	 	SELECT  COUNT(1) CNT FROM   ${tableName}
		<include refid="whereCondition"/> 
	</select>
	
	<select id="exist" resultType="int">
	 	SELECT  ID FROM   ${tableName}
		<include refid="whereCondition"/> limit 1
	</select>
	
	<!-- customized code start -->
	
	<!-- customized code end -->
</mapper>
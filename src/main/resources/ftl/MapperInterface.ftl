package ${mapperPackage};

import java.util.List;

import ${domainPackage}.${domainName};

public interface ${domainName}Mapper {
	
	/**
	 * 保存
	 * @param ${domainName?uncap_first}
	 * @return
	 */
	public int insert(${domainName} ${domainName?uncap_first});
	
	/**
	 * 根据主键更新
	 * @param ${domainName?uncap_first}
	 * @return
	 */
	public int updateByPriKey(${domainName} ${domainName?uncap_first});
	
	/**
	 * 根据条件逻辑删除 慎用
	 * @param con 条件
	 * @return
	 */
	public int delete(${domainName} con);
	
	/**
	 * 根据主键逻辑删除
	 * @param id 主键
	 * @return
	 */
	public int deleteByPriKey(Integer id);
	
	/**
	 * 根据条件物理删除 慎用
	 * @param con
	 * @return
	 */
	public int deleteActual(${domainName} con);
	
	/**
	 * 根据主键物理删除 慎用
	 * @param id
	 * @return
	 */
	public int deleteActualByPriKey(Integer id);

	/**
	 * 清理>=主键的已删除数据
	 * @param id 主键
	 * @return
	 */
	public int clean(Integer id);

	/**
	 * 根据主键查询
	 * @param id
	 * @return
	 */
	public ${domainName} selectByPriKey(Integer id);
	
	/**
	 * 根据条件查询
	 * @param con
	 * @return
	 */
	public List<${domainName}> selectList(${domainName} con);
	
	/**
	 * 根据条件计数
	 * @param con
	 * @return
	 */
	public long selectCount(${domainName} con);
	
	/**
	 * 根据条件判断是否存在
	 * @param con 条件
	 * @return 存在的第一个值的主键
	 */
	public Integer exist(${domainName} con);
	
	/* customized code start */
	
	/* customized code end */

}

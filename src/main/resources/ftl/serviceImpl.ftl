package ${servicePackage}.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import ${domainPackage}.${domainName};
import com.nbs.jiaxiao.exception.ConcurrentException;
import ${mapperPackage}.${domainName}Mapper;
import ${servicePackage}.${domainName}Service;

@Service
public class ${domainName}ServiceImpl implements ${domainName}Service{
	
	@Resource
	private ${domainName}Mapper ${domainName?uncap_first}Mapper;
	
	/**
	 * 保存
	 * @param ${domainName?uncap_first}
	 * @return
	 */
	public int insert(${domainName} ${domainName?uncap_first}){
		return ${domainName?uncap_first}Mapper.insert(${domainName?uncap_first});
	}
	
	/**
	 * 根据主键更新
	 * @param ${domainName?uncap_first}
	 * @return
	 */
	public void updateByPriKey(${domainName} ${domainName?uncap_first}){
		if(${domainName?uncap_first}Mapper.updateByPriKey(${domainName?uncap_first}) != 1) {
			throw new ConcurrentException("并发更新${domainName}失败");
		}
	}
	
	/**
	 * 根据条件逻辑删除 慎用
	 * @param con 条件
	 * @return
	 */
	public int delete(${domainName} con){
		return ${domainName?uncap_first}Mapper.delete(con);
	}
	
	/**
	 * 根据主键逻辑删除
	 * @param id 主键
	 * @return
	 */
	public int deleteByPriKey(Integer id){
		return ${domainName?uncap_first}Mapper.deleteByPriKey(id);
	}
	
	/**
	 * 根据条件物理删除 慎用
	 * @param con
	 * @return
	 */
	public int deleteActual(${domainName} con){
		return ${domainName?uncap_first}Mapper.deleteActual(con);
	}
	
	/**
	 * 根据主键物理删除 慎用
	 * @param id
	 * @return
	 */
	public int deleteActualByPriKey(Integer id){
		return ${domainName?uncap_first}Mapper.deleteActualByPriKey(id);
	}

	/**
	 * 清理>=主键的已删除数据
	 * @param id 主键
	 * @return
	 */
	public int clean(Integer id){
		return ${domainName?uncap_first}Mapper.clean(id);
	}

	/**
	 * 根据主键查询
	 * @param id
	 * @return
	 */
	public ${domainName} selectByPriKey(Integer id){
		return ${domainName?uncap_first}Mapper.selectByPriKey(id);
	}
	
	/**
	 * 根据条件查询
	 * @param con
	 * @return
	 */
	public List<${domainName}> selectList(${domainName} con){
		return ${domainName?uncap_first}Mapper.selectList(con);
	}
	
	/**
	 * 根据条件计数
	 * @param con
	 * @return
	 */
	public long selectCount(${domainName} con){
		return ${domainName?uncap_first}Mapper.selectCount(con);
	}
	
	/**
	 * 根据条件判断是否存在
	 * @param con 条件
	 * @return 存在的第一个值的主键
	 */
	public boolean exist(${domainName} con){
		return ${domainName?uncap_first}Mapper.exist(con) != null;
	}
	
	/* customized code start */
	
	/* customized code end */

}

package com.cn.dao;

import java.util.List;
import java.util.Map;

import com.cn.entity.po.AllpayRole;
import com.cn.entity.po.AllpayRoleFunction;

/**
 * 角色管理
 * @author songzhili
 * 2016年11月23日下午2:01:37
 */
public interface AllpayRoleDao {
   
	public boolean insert(AllpayRole role) throws Exception;
	/****/
	public boolean delete(String roleId,boolean serious,String record)throws Exception;
	/****/
	public boolean update(AllpayRole role)throws Exception;
	/****/
	public AllpayRole obtain(String roleId)throws Exception;
	/*****/
	public List<Map<String, Object>> obtainList(String roleName,String roleIsStart,
			int currentPage,int pageSize)throws Exception;
	/****/
	public int count(String roleName,String roleIsStart)throws Exception;
	/****/
	public boolean insertAllpayRoleFunction(AllpayRoleFunction function) throws Exception;
	/****/
	public List<Map<String, Object>> queryForSystemId(Map<String,String> funcIdMap)throws Exception;
	/****/
	public List<Map<String, Object>> queryForMeuIdAndfuncId(String roleId)throws Exception;
	/****/
	public boolean deleteMenuIdAndFuncId(String roleId)throws Exception;

	boolean isExistRoleName(String s, String roleName) throws Exception;
}

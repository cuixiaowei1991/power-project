package com.cn.service;


import java.util.Map;

/**
 * 角色管理service
 * @author songzhili
 * 2016年11月23日下午2:50:44
 */
public interface AllpayRoleService {
     
	public String insert(Map<String, Object> source) throws Exception;
	/****/
	public String delete(Map<String, Object> source)throws Exception;
	/****/
	public String update(Map<String, Object> source) throws Exception;
	/****/
	public String obtain(Map<String, Object> source)throws Exception;
	/****/
	public String obtainList(Map<String, Object> source)throws Exception;
}

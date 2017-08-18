package com.cn.dao;

import java.util.List;
import java.util.Map;

import com.cn.entity.po.AllpaySecurityCheck;

/**
 * 接口安全校验　DAO
 * @author songzhili
 * 2017年1月14日下午5:56:43
 */
public interface AllpaySecurityCheckDao {

	
	/*****/
	public String insert(AllpaySecurityCheck security)throws Exception;
	/****/
	public String delete(String securityId)throws Exception;
	/****/
	public String update(AllpaySecurityCheck security)throws Exception;
	/****/
	public AllpaySecurityCheck obtain(String securityId)throws Exception;
	/****/
	public String updateEnable(String securityId,String enable)throws Exception;
	/****/
	public String updateStatus(String securityId,String status)throws Exception;
	/****/
	public List<Map<String, Object>> obtainList(int currentPage,int pageSize) throws Exception;
	/****/
	public int count() throws Exception;
}

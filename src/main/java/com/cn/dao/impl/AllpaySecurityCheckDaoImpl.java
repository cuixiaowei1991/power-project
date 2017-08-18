package com.cn.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cn.dao.AllpaySecurityCheckDao;
import com.cn.dao.util.HibernateDAO;
import com.cn.entity.po.AllpaySecurityCheck;
/**
 * 接口安全校验　DAO
 * @author songzhili
 * 2017年1月14日下午5:56:43
 */
@Repository("allpaySecurityCheckDao")
@Transactional
public class AllpaySecurityCheckDaoImpl implements AllpaySecurityCheckDao {
    
	@Autowired
    private HibernateDAO hibernateDAO;
	
	
	@Override
	public String insert(AllpaySecurityCheck security) throws Exception {
		hibernateDAO.save(security);
		return "success";
	}

	@Override
	public String delete(String securityId) throws Exception {
		String sql = "delete from ALLPAY_SECURITY_CHECK che where che.SECURITY_ID = '"+securityId+"'";
		hibernateDAO.executeBySql(sql);
		return "success";
	}

	@Override
	public String updateEnable(String securityId,String enable) throws Exception {
		
		StringBuilder sql = new StringBuilder();
		sql.append("update ALLPAY_SECURITY_CHECK che set che.ENABLE = '").append(enable).append("'");
		sql.append(" where che.SECURITY_ID = '").append(securityId).append("'");
		hibernateDAO.executeBySql(sql.toString());
		return "success";
	}

	@Override
	public String updateStatus(String securityId,String status) throws Exception {
		
		StringBuilder sql = new StringBuilder();
		sql.append("update ALLPAY_SECURITY_CHECK che set che.STATUS = '").append(status).append("'");
		sql.append(" where che.SECURITY_ID = '").append(securityId).append("'");
		hibernateDAO.executeBySql(sql.toString());
		return "success";
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> obtainList(int currentPage, int pageSize)
			throws Exception {
		
		String sql = "select * from ALLPAY_SECURITY_CHECK che order by che.CREATETIME desc";
		List<Map<String, Object>> list = (List<Map<String, Object>>)
				hibernateDAO.listByPageBySQL(sql, pageSize, currentPage-1, false);
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public int count() throws Exception {
		
		String sql = "select count(*) numberr from ALLPAY_SECURITY_CHECK ";
		List<Map<String, Object>> list = (List<Map<String, Object>>)
				hibernateDAO.listBySQL(sql, false);
		if(!list.isEmpty()){
			return Integer.parseInt(list.get(0).get("NUMBERR").toString());
		}
		return 0;
	}

	@Override
	public String update(AllpaySecurityCheck security) throws Exception {
		hibernateDAO.saveOrUpdate(security);
		return "success";
	}

	@Override
	public AllpaySecurityCheck obtain(String securityId) throws Exception {
		AllpaySecurityCheck security = 
				hibernateDAO.listById(AllpaySecurityCheck.class, securityId, false);
		return security;
	}

}

package com.cn.dao.impl;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cn.common.CommonHelper;
import com.cn.dao.AllpayRoleDao;
import com.cn.dao.util.HibernateDAO;
import com.cn.entity.po.AllpayRole;
import com.cn.entity.po.AllpayRoleFunction;
/**
 *  角色管理
 * @author songzhili
 * 2016年11月23日下午2:11:37
 */
@Repository("allpayRoleDao")
@Transactional
public class AllpayRoleDaoImpl implements AllpayRoleDao {
    
	@Autowired
    private HibernateDAO hibernateDAO;
	
	public boolean insert(AllpayRole role) throws Exception {
		
		String result = hibernateDAO.save(role);
		if("success".equals(result)){
			return true;
		}
		return false;
	}

	public boolean delete(String roleId,boolean serious,String record) throws Exception {
        
		String result = null;
		if(serious){
			result = hibernateDAO.removeById(AllpayRole.class, roleId);
		}else{
			StringBuilder together = new StringBuilder();
			together.append("update ALLPAY_ROLE set ALLPAY_LOGICDEL = '2'");
			together.append(",ALLPAY_LOGRECORD = '").append(record).append("'");
			together.append(" where ROLE_ID = '").append(roleId).append("'");
			result = hibernateDAO.executeBySql(together.toString());
			/****/
			together.setLength(0);
			together.append("update ALLPAY_ROLE_FUNCTION set ALLPAY_LOGICDEL = '2'");
			together.append(",ALLPAY_LOGRECORD = '").append(record).append("'");
			together.append(" where ROLE_FUNCTION_ROLEID = '").append(roleId).append("'");
			result = hibernateDAO.executeBySql(together.toString());
		}
		if("success".equals(result)){
			return true;
		}
		return false;
	}
    
	
	public boolean update(AllpayRole role) throws Exception {
		String result = hibernateDAO.saveOrUpdate(role);
		if("success".equals(result)){
			return true;
		}
		return false;
	}

	public AllpayRole obtain(String roleId) throws Exception {
		AllpayRole role = hibernateDAO.listById(AllpayRole.class, roleId, false);
		return role;
	}

	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> obtainList(String roleName, String roleIsStart, int currentPage,
			int pageSize) throws Exception {
		
		StringBuilder together = new StringBuilder();
		together.append("select role.ROLE_ID,role.ROLE_NAME,role.ROLE_ISSTART from ");
		together.append(" ALLPAY_ROLE role where 1=1 and role.ALLPAY_LOGICDEL = '1'");
		if(!CommonHelper.isEmpty(roleName)){
			together.append(" and role.ROLE_NAME like '%").append(roleName).append("%'");
		}
		if(!CommonHelper.isEmpty(roleIsStart)){
			together.append(" and role.ROLE_ISSTART = ").append(roleIsStart);
		}
		together.append(" order by role.ALLPAY_UPDATETIME desc ");
		List<Map<String, Object>> list = (List<Map<String, Object>>) 
				hibernateDAO.listByPageBySQL(together.toString(), 
				pageSize, currentPage, false);
		return list;
	}

	@SuppressWarnings("unchecked")
	public int count(String roleName,String roleIsStart) throws Exception {
		
		StringBuilder together = new StringBuilder();
		together.append("select count(*) numberr from ");
		together.append(" ALLPAY_ROLE ");
		together.append(" role where 1=1 and role.ALLPAY_LOGICDEL = '1' ");
		if(!CommonHelper.isEmpty(roleName)){
			together.append(" and role.ROLE_NAME like '%").append(roleName).append("%'");
		}
		if(!CommonHelper.isEmpty(roleIsStart)){
			together.append(" and role.ROLE_ISSTART = ").append(roleIsStart);
		}
		List<Map<String, Object>> list = (List<Map<String, Object>>) 
				hibernateDAO.listBySQL(together.toString(), false);
		if(!list.isEmpty()){
			Map<String, Object> map = list.get(0);
			if(map.get("NUMBERR") != null){
				return Integer.parseInt(map.get("NUMBERR").toString());
			}
		}
		return 0;
	}

	public boolean insertAllpayRoleFunction(AllpayRoleFunction function)
			throws Exception {
		
		String result = hibernateDAO.save(function);
		if("success".equals(result)){
			return true;
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> queryForSystemId(Map<String,String> funcIdMap)
			throws Exception {
		
		StringBuilder together = new StringBuilder();
		together.append("select af.FUNCTION_ID,af.FUNCTION_SUP_SYSTEMID from ALLPAY_FUNCTION af");
		boolean append = false;
		StringBuilder togetherOne = new StringBuilder();
		Collection<String> collection = funcIdMap.values();
		Iterator<String> iterator = collection.iterator();
		while(iterator.hasNext()){
			append = true;
			togetherOne.append("'").append(iterator.next()).append("'").append(",");
		}
		if(append){
			String ids = togetherOne.substring(0, togetherOne.length()-1);
			together.append(" where af.FUNCTION_ID in (").append(ids).append(")");
		}
		List<Map<String, Object>> list = (List<Map<String, Object>>)
				hibernateDAO.listBySQL(together.toString(), false);
		return list;
	}
	

	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> queryForMeuIdAndfuncId(String roleId)
			throws Exception {
		
		StringBuilder together = new StringBuilder();
		together.append("select arf.ROLE_FUNCTION_MENUID,arf.ROLE_FUNCTION_FUNID from ALLPAY_ROLE_FUNCTION arf");
		together.append(" where arf.ROLE_FUNCTION_ROLEID = '").append(roleId).append("'");
		List<Map<String, Object>> list = (List<Map<String, Object>>)
				hibernateDAO.listBySQL(together.toString(), false);
		return list;
	}
	

	public boolean deleteMenuIdAndFuncId(String roleId) throws Exception {
		
		StringBuilder together = new StringBuilder();
		together.append("delete from ALLPAY_ROLE_FUNCTION arf ");
		together.append(" where arf.ROLE_FUNCTION_ROLEID = '").append(roleId).append("'");
		hibernateDAO.executeBySql(together.toString());
		return true;
	}

	@Override
	public boolean isExistRoleName(String roleId, String roleName) throws Exception {
		StringBuffer sql=new StringBuffer("SELECT R.ROLE_ID FROM ALLPAY_ROLE R WHERE R.ROLE_NAME='").append(roleName).append("' AND R.ALLPAY_LOGICDEL='1'");
		List<Map<String, Object>> list= (List<Map<String, Object>>) hibernateDAO.listBySQL(sql.toString(),false);
		if(list!=null && list.size()>0){
			if(CommonHelper.isNullOrEmpty(roleId)){
				return true;
			}else{
				Map map=list.get(0);
				if(!roleId.equals(CommonHelper.nullToString(map.get("ROLE_ID")))) {
					return true;
				}
			}
		}
		return false;
	}
}






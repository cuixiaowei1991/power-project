package com.cn.dao.impl.shop;

import com.cn.MsgCode.MsgAndCode;
import com.cn.common.CommonHelper;
import com.cn.common.LogHelper;
import com.cn.dao.shop.AllpayShopuserDao;
import com.cn.dao.util.HibernateDAO;
import com.cn.entity.dto.AllpayUserDto;
import com.cn.entity.po.shop.AllpayShopuser;

import org.apache.poi.hssf.record.formula.functions.T;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Restrictions;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static all.union.tools.codec.MD5Helper.md5;

/**
 * 商助用户管理dao层实现
 * Created by WangWenFang on 2016/12/15.
 */
@Repository("allpayShopuserDao")
@Transactional(rollbackFor = {Exception.class, RuntimeException.class})
public class AllpayShopuserDaoImpl implements AllpayShopuserDao {

    @Autowired
    private HibernateDAO hibernateDAO;

    /**
     * 查询具备软POS收银能力的用户信息
     * @param bean
     * @return
     * @throws Exception
     */
    @Override
    public List<?> getAllpayShopuserList(AllpayUserDto bean) throws Exception {
        StringBuffer sql = new StringBuffer("SELECT ASU.SHOPUSER_ID, ASU.SHOPUSER_NAME, ASU.SHOPUSER_PHONE， ASU.SHOPUSER_SHOPID, ASU.SHOPUSER_ROLE,ASU.SHOPUSER_ISSUPER FROM ALLPAY_SHOPUSER ASU WHERE 1=1");
        if(!CommonHelper.isEmpty(""+bean.getShopuserIscashier())){  //是否具备软POS收银能力 1--是 2---否
            sql.append(" AND ASU.SHOPUSER_ISCASHIER = ").append(bean.getShopuserIscashier());
        }
		if(!CommonHelper.isEmpty(bean.getMerchantId())){
			sql.append(" AND ASU.SHOPUSER_SHOPID = '").append(bean.getMerchantId()).append("'");
		}
        sql.append(" AND ASU.ALLPAY_LOGICDEL = 1 ORDER BY ASU.ALLPAY_UPDATETIME DESC");
        List<?> list=hibernateDAO.listBySQL(sql.toString(), false);
        return list;
    }

	/**
	 * 判断商户名称是否已存在
	 * @param merName
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<?> checkMerName(String merName) throws Exception {
		String sql = "SELECT AM.MERCHANT_ID, AM.MERCHANT_BRANCHCOMPANYID, AM.MERCHANT_MERCHNAME FROM ALLPAY_MERCHANT AM WHERE AM.MERCHANT_MERCHNAME = '"+merName + "' AND AM.ALLPAY_LOGICDEL = 1";
		List list = hibernateDAO.listBySQL(sql, false);
		return list;
	}

	/**
	 * 判断当前商户下门店名称是否存在
	 * @param merName
	 * @param storeName
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<?> checkStore(String merName, String storeName) throws Exception {
		String sql = "SELECT AST.STORE_ID, AST.STORE_SHOPNAME FROM ALLPAY_STORE AST LEFT JOIN ALLPAY_MERCHANT AM ON AST.STORE_MERCHANT_ID = AM.MERCHANT_ID WHERE AST.STORE_SHOPNAME = '"+storeName+"' AND AM.MERCHANT_MERCHNAME = '"+merName+"'";
		sql += " AND AST.ALLPAY_LOGICDEL = 1 AND AM.ALLPAY_LOGICDEL = 1";
		List list = hibernateDAO.listBySQL(sql, false);
		return list;
	}

	/**
	 * 判断code的值value是否存在
	 * @param code
	 * @param value
	 * @return
	 * @throws Exception
	 */
	@Override
	public boolean checkIsExist(String code, String value, String id) throws Exception {
		String sql = "SELECT ASH.SHOPUSER_ID FROM ALLPAY_SHOPUSER ASH WHERE ASH."+code+" = '"+value+"' AND ASH.ALLPAY_LOGICDEL = 1";
		List<Map<String, Object>> list = (List<Map<String, Object>>)hibernateDAO.listBySQL(sql, false);
		if(!list.isEmpty()){
			Map<String, Object> map = list.get(0);
			if(CommonHelper.isNullOrEmpty(id)){
				return true;
			}else if(!(id.equals(map.get("SHOPUSER_ID")))) {
				return true;
			}
			return false;
		}
		return false;
	}



	@Override
	public boolean insert(AllpayShopuser shop) throws Exception {
		String result = hibernateDAO.save(shop);
		if("success".equals(result)){
			return true;
		}
		return false;
	}

	@Override
	public boolean update(AllpayShopuser shop) throws Exception {
		hibernateDAO.saveOrUpdate(shop);
		return true;
	}

	@Override
	public AllpayShopuser obtain(String merchantId) throws Exception {
		
		AllpayShopuser user = hibernateDAO.listByField(AllpayShopuser.class, 
    			"shopuserShopid", merchantId);
		return user;
	}

	@Override
	public boolean delete(String shopuserId, boolean serious) throws Exception {
		
		if(serious){
			hibernateDAO.removeById(AllpayShopuser.class, shopuserId);
		}else{
			String sql = "update ALLPAY_SHOPUSER usr set usr.ALLPAY_LOGICDEL = '2'"
			           +" where usr.SHOPUSER_ID = '"+shopuserId+"'";
			hibernateDAO.executeBySql(sql);
		}
		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean checkShopUserExist(Map<String, Object> source)
			throws Exception {
		
		StringBuilder together = new StringBuilder();
		together.append("select usr.SHOPUSER_ID from ALLPAY_SHOPUSER usr where usr.SHOPUSER_NAME = '");
		together.append(source.get("mhtManager")).append("' AND usr.SHOPUSER_PHONE = '")
		.append(source.get("mhtManagerPhone")).append("' AND usr.ALLPAY_LOGICDEL = 1");
		List<Map<String, Object>> list = (List<Map<String, Object>>) 
					hibernateDAO.listBySQL(together.toString(), false);
        if(!list.isEmpty()){
        	return true;
        }
		return false;
	}

	@Override
	public List<?> getShopUserInfoList(AllpayUserDto bean,String marked) throws Exception {
		String sql="SELECT U.SHOPUSER_ID,U.SHOPUSER_NAME,U.SHOPUSER_ISSUPER,U.SHOPUSER_PHONE,U.SHOPUSER_ROLE,U.SHOPUSER_STOREID," +
				"U.SHOPUSER_ISCASHIER,U.ALLPAY_CREATETIME,U.SHOPUSER_ISSTART,STORE.STORE_SHOPNAME FROM ALLPAY_SHOPUSER U LEFT JOIN ALLPAY_STORE STORE ON  U.SHOPUSER_STOREID=STORE.STORE_ID " +
				" WHERE U.SHOPUSER_SHOPID='"+bean.getMerchantId()+"' AND U.ALLPAY_LOGICDEL = 1 ORDER BY U.ALLPAY_UPDATETIME DESC";
		LogHelper.info("sql语句：" + sql);
		List<?>  shopUserList=new ArrayList<>();
		if("page".equals(marked))
		{
			  shopUserList=hibernateDAO.listByPageBySQL(sql, bean.getPageSize(), bean.getCurragePage()-1,false);
		}
		if("findAll".equals(marked))
		{
			  shopUserList=hibernateDAO.listBySQL(sql,false);
		}

		return shopUserList;
	}

	@Override
	public AllpayShopuser getShopUserByID(String shopuserId) throws Exception {

		AllpayShopuser user = hibernateDAO.listById(AllpayShopuser.class,
				 shopuserId,false);
		return user;
	}

	@Override
	public List<?> getAgentInfoByMerchantID(String shopid) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT AGENT.AGENT_NUM,AGENT.AGENT_ID,MERCHANT.MERCHANT_ID FROM ALLPAY_AGENT AGENT LEFT JOIN ALLPAY_MERCHANT MERCHANT ON AGENT.AGENT_ID=MERCHANT.MERCHANT_AGENTID");
		sql.append(" WHERE MERCHANT.MERCHANT_ID='" + shopid + "'");
		LogHelper.info("sql语句："+sql.toString());
		List<?> agentList=hibernateDAO.listBySQL(sql.toString(), false);
		return agentList;
	}

	@Override
	public AllpayShopuser getShopUserByNickName(String nickname) throws Exception {
		AllpayShopuser user = hibernateDAO.listByField(AllpayShopuser.class,
				"shopuseraccountid", nickname);
		return user;
	}

	@Override
	public List<?> getStoreInfo(String storeid) {
		String sql="SELECT STORE.STORE_ID,STORE.STORE_MERCHANT_ID,STORE.STORE_SHOPIDNUMBER,STORE.STORE_SHOPNAME FROM ALLPAY_STORE STORE" +
				" WHERE STORE.STORE_ID='"+storeid+"'";
		List<?> storeList=hibernateDAO.listBySQL(sql, false);
		return storeList;
	}
	@Override
	public List<?> checkShopUserExitByParamters(String paramters) throws Exception
	{
		String sql="select * from ALLPAY_SHOPUSER shopuser where (shopuser.SHOPUSER_NAME='"+paramters+"' or shopuser.SHOPUSER_PHONE='"+paramters+"') AND shopuser.ALLPAY_LOGICDEL = 1" ;

		List<?> storeList=hibernateDAO.listBySQL(sql, false);
		return storeList;
	}

	@Override
	public List<?> getUser(String username,String password) throws Exception {
		String hql = "from AllpayShopuser where (shopuserId = '"+username+"' or shopuserName = '"+username+"' or shopuserPhone = '"+username+"') and shopuserPassword = '"+md5(password, "utf-8")+"' AND ALLPAY_LOGICDEL = 1";
		return hibernateDAO.listByHQL(hql);
	}

	@Override
	public boolean updatePwd(Map<String, Object> source) throws Exception {
		String userNameFromAgentCookie = CommonHelper.nullToString(source.get("UserNameFromBusCookie"));
		JSONObject publicFileds = CommonHelper.getPublicFileds(MsgAndCode.OPERATION_UPDATEPWD, "", userNameFromAgentCookie);
		String userName = publicFileds.getString("userName");
		String now = publicFileds.getString("now");
		String record = publicFileds.getString("record");

		StringBuffer sql = new StringBuffer("UPDATE ALLPAY_SHOPUSER SU SET SU.SHOPUSER_PASSWORD = '").append(md5(source.get("newPassword").toString(), "utf-8")).append("',");
		sql.append(" SU.ALLPAY_UPDATETIME = TO_DATE('").append(now).append("', 'YYYY-MM-DD HH24:MI:SS'),")
				.append(" SU.ALLPAY_UPDATER = '").append(userName).append("',")
				.append(" SU.ALLPAY_LOGRECORD = '").append(record).append("'")
				.append(" WHERE SU.SHOPUSER_ID = '").append(source.get("username")).append("'")
				.append(" AND SU.SHOPUSER_PASSWORD = '").append(md5(source.get("password").toString(), "utf-8")).append("'")
				.append(" AND ALLPAY_LOGICDEL = 1");
		String result = hibernateDAO.executeBySql(sql.toString());
		if ("success".equals(result)){
			return true;
		}
		return false;
	}

	@Override
	public List<Map<String, Object>> getShopIdByWechat(String openid, String appid) throws Exception {
		List<Map<String, Object>> list=null;
		StringBuilder sql = new StringBuilder("SELECT SU.SHOPUSER_SHOPID,SU.SHOPUSER_ROLE,SU.SHOPUSER_STOREID,SU.SHOPUSER_ID FROM ALLPAY_SHOPUSER SU WHERE SU.SHOPUSER_APPID='").append(appid).append("' AND SU.SHOPUSER_OPENID='").append(openid).append("' AND SU.ALLPAY_LOGICDEL=1");
		list= (List<Map<String, Object>>) hibernateDAO.listBySQL(sql.toString(),false);
		return list;
	}
}




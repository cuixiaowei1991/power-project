package com.cn.dao.impl.shop;

import java.util.List;
import java.util.Map;

import com.cn.entity.po.shop.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cn.common.CommonHelper;
import com.cn.dao.shop.AllpayShopfunctionDao;
import com.cn.dao.util.HibernateDAO;
import com.cn.entity.dto.AllpayFunctionDto;

/**
 * 商助资源（功能）管理dao层实现
 * Created by WangWenFang on 2016/11/30.
 */
@Repository("allpayShopfunctionDao")
@Transactional(rollbackFor = {Exception.class, RuntimeException.class})
public class AllpayShopfunctionDaoImpl implements AllpayShopfunctionDao {

    @Autowired
    private HibernateDAO hibernateDAO;

    public List getMenuFuncInfoList() throws Exception {
        String sql="SELECT F.SHOPFUNCTION_ID,F.SHOPFUNCTION_ISDEFAULT,F.SHOPFUNCTION_NAME,F.SHOPFUNCTION_TYPE,F.SHOPFUNCTION_MENUID,F.SHOPFUNCTION_TOP_MENUID,MENU.SHOPMENU_NAME,F.SHOPFUNCTION_SUP_SYSTEMID " +
                "FROM ALLPAY_SHOPFUNCTION F " +
                "INNER JOIN ALLPAY_SHOPMENU MENU " +
                "ON F.SHOPFUNCTION_TOP_MENUID=MENU.SHOPMENU_ID " +
                "WHERE F.SHOPFUNCTION_STATE=1 AND F.ALLPAY_LOGICDEL=1 AND MENU.ALLPAY_LOGICDEL=1 ORDER BY F.SHOPFUNCTION_ORDER";
        List list=hibernateDAO.listBySQL(sql, false);
        return list;
    }

    public List getShopQxInfoList(String shopId) throws Exception{
        StringBuffer sql = new StringBuffer("SELECT AF.SHOPFUNCTION_ID,AF.SHOPFUNCTION_ORDER,AF.SHOPFUNCTION_NAME,AF.SHOPFUNCTION_ISDEFAULT,SM.SHOPMENU_NAME,AF.SHOPFUNCTION_TOP_MENUID,AF.SHOPFUNCTION_TYPE,AF.SHOPFUNCTION_MENUID,AF.SHOPFUNCTION_SUP_SYSTEMID")
                .append(" FROM ALLPAY_SHOPMATCHFUNC ASF")
                .append(" LEFT JOIN ALLPAY_SHOPFUNCTION AF ON ASF.SHOPMATCHFUNC_FUNCTIONID=AF.SHOPFUNCTION_ID AND AF.ALLPAY_LOGICDEL=1")
                .append(" LEFT JOIN ALLPAY_SHOPMENU SM ON AF.SHOPFUNCTION_TOP_MENUID=SM.SHOPMENU_ID AND SM.ALLPAY_LOGICDEL=1")
                .append(" WHERE 1=1 AND ASF.SHOPMATCHFUNC_SHOPID='").append(shopId).append("'")
                .append(" AND AF.SHOPFUNCTION_STATE=1 AND ASF.ALLPAY_LOGICDEL=1")
                .append(" UNION ALL ")
                .append("SELECT F.SHOPFUNCTION_ID,F.SHOPFUNCTION_ORDER,F.SHOPFUNCTION_NAME,F.SHOPFUNCTION_ISDEFAULT,MENU.SHOPMENU_NAME,F.SHOPFUNCTION_TOP_MENUID,F.SHOPFUNCTION_TYPE,F.SHOPFUNCTION_MENUID,F.SHOPFUNCTION_SUP_SYSTEMID")
                .append(" FROM ALLPAY_SHOPFUNCTION F")
                .append(" LEFT JOIN ALLPAY_SHOPMENU MENU ON F.SHOPFUNCTION_TOP_MENUID=MENU.SHOPMENU_ID  AND MENU.ALLPAY_LOGICDEL=1")
                .append(" WHERE F.SHOPFUNCTION_ISDEFAULT=1 AND F.SHOPFUNCTION_STATE=1 AND F.ALLPAY_LOGICDEL=1");

        List list=hibernateDAO.listBySQL(sql.toString(), false);
        return list;
    }

    @Override
    public boolean deleteRoleMatchFunc(String roleType) throws Exception {
        String sql="DELETE FROM ALLPAY_ROLEMATCHFUNC RF WHERE RF.ROLEMATCHFUNC_ROLE="+roleType;
        hibernateDAO.executeBySql(sql);
        return true;
    }

    @Override
    public boolean insertRoleMatchFunc(AllpayRolematchfunc rolematchfunc) throws Exception {
        String result =  hibernateDAO.save(rolematchfunc);
        if("success".equals(result)){
            return true;
        }
        return false;
    }


    public List<?> getShopFuncInfoList(AllpayFunctionDto bean, Integer currentPage, Integer pageSize) throws Exception {
        StringBuffer sql = new StringBuffer("SELECT AF.SHOPFUNCTION_ID,AF.SHOPFUNCTION_ISDEFAULT, AF.SHOPFUNCTION_NAME, AF.SHOPFUNCTION_STATE, AM1.SHOPMENU_NAME AS SUP_MENU_NAME, AM2.SHOPMENU_NAME AS TOP_MENU_NAME, ");
        sql.append("AF.SHOPFUNCTION_CODE, AF.SHOPFUNCTION_URL, AF.SHOPFUNCTION_TYPE FROM ALLPAY_SHOPFUNCTION AF ")
                .append("LEFT JOIN ALLPAY_SHOPMENU AM1 ON AF.SHOPFUNCTION_MENUID = AM1.SHOPMENU_ID ")
                .append("LEFT JOIN ALLPAY_SHOPMENU AM2 ON AF.SHOPFUNCTION_TOP_MENUID = AM2.SHOPMENU_ID WHERE 1=1 ");
        if(!CommonHelper.isEmpty(bean.getFuncName())){
            sql.append("AND AF.SHOPFUNCTION_NAME LIKE '%").append(bean.getFuncName()).append("%' ");
        }
        if(!CommonHelper.isEmpty(bean.getFuncType())){
            sql.append("AND AF.SHOPFUNCTION_TYPE = ").append(bean.getFuncType());
        }
        if(bean.getFuncState() != 0){  //是否启用	1--启用 2--禁用
            sql.append(" AND AF.SHOPFUNCTION_STATE = ").append(bean.getFuncState());
        }
        sql.append(" AND AF.ALLPAY_LOGICDEL = 1 ORDER BY AF.ALLPAY_UPDATETIME DESC");
        List list=hibernateDAO.listByPageBySQL(sql.toString(), Integer.parseInt(bean.getPageSize()), Integer.parseInt(bean.getCurragePage()) - 1, false);
        return list;
    }

    public AllpayShopmenu getMenuName(String function_top_menuId) throws Exception {
        AllpayShopmenu allpayMenu=null;
        allpayMenu=hibernateDAO.listById(AllpayShopmenu.class,function_top_menuId,false);
        return allpayMenu;
    }

    public boolean saveOrUpdate(AllpayShopfunction function) throws Exception {
        String result =  hibernateDAO.saveOrUpdate(function);
        if("success".equals(result)){
            return true;
        }
        return false;
    }

    public AllpayShopfunction getShopFuncInfoById(String funcId) throws Exception {
        AllpayShopfunction function=null;
        function=hibernateDAO.listById(AllpayShopfunction.class,funcId,false);
        return function;
    }

    public boolean delete(String funcId) throws Exception {
        String result = hibernateDAO.removeById(AllpayShopfunction.class, funcId);
        if("success".equals(result)){
            return true;
        }
        return false;
    }

    public int count(AllpayFunctionDto bean) throws Exception {
        StringBuffer sql = new StringBuffer("SELECT AF.SHOPFUNCTION_ID FROM ALLPAY_SHOPFUNCTION AF ")
                .append("LEFT JOIN ALLPAY_SHOPMENU AM1 ON AF.SHOPFUNCTION_MENUID = AM1.SHOPMENU_ID ")
                .append("LEFT JOIN ALLPAY_SHOPMENU AM2 ON AF.SHOPFUNCTION_TOP_MENUID = AM2.SHOPMENU_ID WHERE 1=1 ");
        if(!CommonHelper.isEmpty(bean.getFuncName())){
            sql.append("AND AF.SHOPFUNCTION_NAME LIKE '%").append(bean.getFuncName()).append("%' ");
        }
        if(!CommonHelper.isEmpty(bean.getFuncType())){
            sql.append("AND AF.SHOPFUNCTION_TYPE = ").append(bean.getFuncType());
        }
        if(bean.getFuncState() != 0){  //是否启用	1--启用 2--禁用
            sql.append(" AND AF.SHOPFUNCTION_STATE = ").append(bean.getFuncState());
        }
        sql.append(" AND AF.ALLPAY_LOGICDEL = 1");
        return hibernateDAO.CountBySQL(sql.toString());
    }
    
    @Override
	public String insertMerchantMenuAndFunc(AllpayShopmatchfunc shop)
			throws Exception {
		
		hibernateDAO.save(shop);
		return "success";
	}
    
	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> obtainMerchantMenuAndFuncList(
			String merchantId) throws Exception {
		
		StringBuilder together = new StringBuilder();
		together.append("select sho.SHOPMATCHFUNC_FUNCTIONID,sho.SHOPMATCHFUNC_MENUID,mer.MERCHANT_MERCHNAME");
		together.append(" from ALLPAY_SHOPMATCHFUNC sho left join ALLPAY_MERCHANT　mer");
		together.append(" on sho.SHOPMATCHFUNC_SHOPID = mer.MERCHANT_ID");
		together.append(" where sho.SHOPMATCHFUNC_SHOPID = '").append(merchantId).append("'");
		List<Map<String, Object>> list = (List<Map<String, Object>>) 
				hibernateDAO.listBySQL(together.toString(), false);
		return list;
	}

	@Override
	public String deleteForMerchantId(String merchantId) throws Exception {
		
		StringBuilder together = new StringBuilder();
		together.append("delete from ALLPAY_SHOPMATCHFUNC t where t.SHOPMATCHFUNC_SHOPID = '");
		together.append(merchantId).append("'");
		hibernateDAO.executeBySql(together.toString());
		return "success";
	}

    @Override
    public List<?> getMenuInfoList(AllpayFunctionDto bean, String flag) {
        String common_sql =" SELECT AF.SHOPFUNCTION_ID,SUP_AM.SHOPMENU_ORDER AS TOP_SHOPMENUORDER,AF.SHOPFUNCTION_ORDER,AM.SHOPMENU_ORDER,"
        		+ "AF.SHOPFUNCTION_NAME,AF.SHOPFUNCTION_CODE,AF.SHOPFUNCTION_URL,"
        		+ "AF.SHOPFUNCTION_SUP_SYSTEMID,ASY.SYSTEM_NAME,ASY.SYSTEM_PATH,"
        		+ "AF.SHOPFUNCTION_MENUID,AM.SHOPMENU_NAME, AF.SHOPFUNCTION_TOP_MENUID,"
        		+ " SUP_AM.SHOPMENU_NAME AS TOP_MENUNAME,AF.SHOPFUNCTION_TYPE";
        bean.setFuncType(flag);

        StringBuffer default_sql = new StringBuffer(common_sql)
                .append(getDefaultSql(bean));

        StringBuffer chec_sql = new StringBuffer(common_sql)
                .append(getSql(bean));

        StringBuffer sql1 = new StringBuffer(default_sql)
                .append(" UNION ALL ")
                .append(chec_sql);

        StringBuffer sql = new StringBuffer("SELECT * FROM (")
                .append(sql1)
                .append(" ) TEMP ORDER BY TEMP.SHOPFUNCTION_ORDER ASC");
        return hibernateDAO.listBySQL(sql.toString(), false);
    }

   /* @Override
    public List<?> getMenuInfoList(AllpayFunctionDto bean, String flag) {
        String common_sql =" SELECT AF.SHOPFUNCTION_ID,SUP_AM.SHOPMENU_ORDER AS TOP_SHOPMENUORDER,AF.SHOPFUNCTION_ORDER,AM.SHOPMENU_ORDER,"
                + "AF.SHOPFUNCTION_NAME,AF.SHOPFUNCTION_CODE,AF.SHOPFUNCTION_URL,"
                + "AF.SHOPFUNCTION_SUP_SYSTEMID,ASY.SYSTEM_NAME,ASY.SYSTEM_PATH,"
                + "AF.SHOPFUNCTION_MENUID,AM.SHOPMENU_NAME, AF.SHOPFUNCTION_TOP_MENUID,"
                + " SUP_AM.SHOPMENU_NAME AS TOP_MENUNAME,AF.SHOPFUNCTION_TYPE";
        bean.setFuncType(flag);

        StringBuffer sql = new StringBuffer(common_sql)
                .append(getSql(bean))
                .append(" ORDER BY AF.SHOPFUNCTION_ORDER ASC");

        return hibernateDAO.listBySQL(sql.toString(), false);
    }*/
     
  /*  @Override  默认查询功能资源表中所有数据
    public List<?> getAllMenuInfoList(AllpayFunctionDto bean,String flag) {
        bean.setFuncType(flag);
        StringBuffer sql = new StringBuffer("SELECT AF.SHOPFUNCTION_ID, AF.SHOPFUNCTION_NAME, AF.SHOPFUNCTION_CODE,AF.SHOPFUNCTION_ORDER,SUP_AM.SHOPMENU_ORDER AS TOP_SHOPMENUORDER, AM.SHOPMENU_ORDER,")
                .append("AF.SHOPFUNCTION_URL, AF.SHOPFUNCTION_SUP_SYSTEMID,ASY.SYSTEM_NAME,ASY.SYSTEM_PATH, AF.SHOPFUNCTION_MENUID,AM.SHOPMENU_NAME, AF.SHOPFUNCTION_TOP_MENUID, SUP_AM.SHOPMENU_NAME AS TOP_MENUNAME, AF.SHOPFUNCTION_TYPE")
                .append(" FROM ALLPAY_SHOPFUNCTION AF")
                .append(" LEFT JOIN ALLPAY_SHOPMENU AM ON  AM.SHOPMENU_ID= AF.SHOPFUNCTION_MENUID AND AM.ALLPAY_LOGICDEL = 1")
                .append(" LEFT JOIN ALLPAY_SHOPMENU SUP_AM ON SUP_AM.SHOPMENU_ID = AF.SHOPFUNCTION_TOP_MENUID AND SUP_AM.ALLPAY_LOGICDEL = 1")
                .append(" LEFT JOIN ALLPAY_SYSTEM ASY ON ASY.SYSTEM_ID = AF.SHOPFUNCTION_SUP_SYSTEMID AND ASY.ALLPAY_LOGICDEL = 1")
                .append(" WHERE 1=1");
        if(!CommonHelper.isEmpty(bean.getMenuId())){
            sql.append("  AND AF.SHOPFUNCTION_MENUID= '").append(bean.getMenuId()).append("'");
        }
        if(!CommonHelper.isEmpty(bean.getFuncType())){  //类型 1--菜单栏目 2---功能操作
            sql.append(" AND AF.SHOPFUNCTION_TYPE= ").append(bean.getFuncType());
        }
        sql.append(" AND AF.ALLPAY_LOGICDEL = 1 AND AF.SHOPFUNCTION_STATE = 1 ORDER BY AF.SHOPFUNCTION_ORDER ASC");
        return hibernateDAO.listBySQL(sql.toString(), false);
    }*/

    @Override
    public List<?> getAllMenuInfoList(AllpayFunctionDto bean,String flag) {
        String common_sql =" SELECT AF.SHOPFUNCTION_ID,SUP_AM.SHOPMENU_ORDER AS TOP_SHOPMENUORDER,AF.SHOPFUNCTION_ORDER,AM.SHOPMENU_ORDER,"
                + "AF.SHOPFUNCTION_NAME,AF.SHOPFUNCTION_CODE,AF.SHOPFUNCTION_URL,"
                + "AF.SHOPFUNCTION_SUP_SYSTEMID,ASY.SYSTEM_NAME,ASY.SYSTEM_PATH,"
                + "AF.SHOPFUNCTION_MENUID,AM.SHOPMENU_NAME, AF.SHOPFUNCTION_TOP_MENUID,"
                + " SUP_AM.SHOPMENU_NAME AS TOP_MENUNAME,AF.SHOPFUNCTION_TYPE";
        bean.setFuncType(flag);

        StringBuffer default_sql = new StringBuffer(common_sql)
                .append(getDefaultSql(bean));

        //查询商户下  ALLPAY_SHOPMATCHFUNC  所有的功能菜单
        StringBuffer chec_sql = new StringBuffer(common_sql)
                .append(getShopMatchFuncSql(bean));

        StringBuffer sql1 = new StringBuffer(default_sql)
                .append(" UNION ALL ")
                .append(chec_sql);

        StringBuffer sql = new StringBuffer("SELECT * FROM (")
                .append(sql1)
                .append(" ) TEMP ORDER BY TEMP.SHOPFUNCTION_ORDER ASC");
        return hibernateDAO.listBySQL(sql.toString(), false);
    }


    /**
     * 获取角色权限信息列表
     * @param userType
     * @return
     */
    @Override
    public List<?> getRoleFuncList(String userType) {
        StringBuffer sql = new StringBuffer("SELECT AR.ROLEMATCHFUNC_MENUID, AR.ROLEMATCHFUNC_FUNCTIONID, SF.SHOPFUNCTION_STATE, AR.ROLEMATCHFUNC_ROLE,AR.ROLEMATCHFUNC_ISREDUCE,SF.SHOPFUNCTION_ISDEFAULT")
                .append(" FROM ALLPAY_ROLEMATCHFUNC AR ")
                .append(" LEFT JOIN ALLPAY_SHOPFUNCTION SF ON AR.ROLEMATCHFUNC_FUNCTIONID = SF.SHOPFUNCTION_ID AND SF.ALLPAY_LOGICDEL = 1")
                .append(" LEFT JOIN ALLPAY_SHOPMENU SM ON SM.SHOPMENU_ID = AR.ROLEMATCHFUNC_MENUID AND SM.ALLPAY_LOGICDEL = 1")
                .append(" WHERE 1=1");
        if(!CommonHelper.isEmpty(userType)){  //商户用户角色	网点管理员0，网点业务员1 ， 商户管理员2
            sql.append(" AND AR.ROLEMATCHFUNC_ROLE= ").append(userType);
        }
        sql.append(" AND AR.ALLPAY_LOGICDEL = 1 AND SF.SHOPFUNCTION_STATE = 1");
        return hibernateDAO.listBySQL(sql.toString(), false);
    }

    /**
     * 默认功能
     * @param bean
     * @return
     */
    public  String getDefaultSql(AllpayFunctionDto bean){
        StringBuffer sql = new StringBuffer(" FROM ALLPAY_SHOPFUNCTION AF")
                .append(" LEFT JOIN ALLPAY_SHOPMENU AM ON  AM.SHOPMENU_ID= AF.SHOPFUNCTION_MENUID AND AM.ALLPAY_LOGICDEL = 1")
                .append(" LEFT JOIN ALLPAY_SHOPMENU SUP_AM ON SUP_AM.SHOPMENU_ID = AF.SHOPFUNCTION_TOP_MENUID AND SUP_AM.ALLPAY_LOGICDEL = 1")
                .append(" LEFT JOIN ALLPAY_SYSTEM ASY ON ASY.SYSTEM_ID = AF.SHOPFUNCTION_SUP_SYSTEMID AND ASY.ALLPAY_LOGICDEL = 1")
                .append(" WHERE 1=1")
                .append(" AND AF.SHOPFUNCTION_ID NOT IN (SELECT ARF.ROLEMATCHFUNC_FUNCTIONID FROM ALLPAY_ROLEMATCHFUNC ARF WHERE ARF.ROLEMATCHFUNC_ISREDUCE=0")
                .append(" AND ARF.ROLEMATCHFUNC_ROLE=(SELECT AU.SHOPUSER_ROLE FROM ALLPAY_SHOPUSER AU WHERE AU.SHOPUSER_ID ='").append(bean.getUserId()).append("'))")
                .append(" AND AF.SHOPFUNCTION_ISDEFAULT=1");

        if(!CommonHelper.isEmpty(bean.getMenuId())){
            sql.append(" AND AF.SHOPFUNCTION_MENUID = '").append(bean.getMenuId()).append("'");
        }
        if(!CommonHelper.isEmpty(bean.getFuncType())){  //类型 1--菜单栏目 2---功能操作
            sql.append(" AND AF.SHOPFUNCTION_TYPE = ").append(bean.getFuncType());
        }
        sql.append(" AND AF.ALLPAY_LOGICDEL = 1 AND AF.SHOPFUNCTION_STATE = 1");
        return sql.toString();
    }

    /**
     * 获取公共查询sql
     * @param bean
     * @return
     */
    public String getSql(AllpayFunctionDto bean){
        StringBuffer sql = new StringBuffer(" FROM ALLPAY_SHOPFUNCTION AF")
                .append(" LEFT JOIN ALLPAY_ROLEMATCHFUNC ARF ON AF.SHOPFUNCTION_ID = ARF.ROLEMATCHFUNC_FUNCTIONID AND ARF.ALLPAY_LOGICDEL = 1")
                .append(" LEFT JOIN ALLPAY_SHOPMENU AM ON  AM.SHOPMENU_ID= AF.SHOPFUNCTION_MENUID AND AM.ALLPAY_LOGICDEL = 1")
                .append(" LEFT JOIN ALLPAY_SHOPMENU SUP_AM ON SUP_AM.SHOPMENU_ID = AF.SHOPFUNCTION_TOP_MENUID AND SUP_AM.ALLPAY_LOGICDEL = 1")
                .append(" LEFT JOIN ALLPAY_SYSTEM ASY ON ASY.SYSTEM_ID = AF.SHOPFUNCTION_SUP_SYSTEMID AND ASY.ALLPAY_LOGICDEL = 1 WHERE 1=1")
                .append(" AND ARF.ROLEMATCHFUNC_ISREDUCE=1")
                .append(" AND ARF.ROLEMATCHFUNC_ROLE = (SELECT AU.SHOPUSER_ROLE FROM ALLPAY_SHOPUSER AU WHERE AU.SHOPUSER_ID = '").append(bean.getUserId()).append("')");
        if(!CommonHelper.isEmpty(bean.getMenuId())){
            sql.append(" AND AF.SHOPFUNCTION_MENUID = '").append(bean.getMenuId()).append("'");
        }
        if(!CommonHelper.isEmpty(bean.getFuncType())){  //类型 1--菜单栏目 2---功能操作
            sql.append(" AND AF.SHOPFUNCTION_TYPE = ").append(bean.getFuncType());
        }
        sql.append(" AND AF.ALLPAY_LOGICDEL = 1 AND AF.SHOPFUNCTION_STATE = 1");
        return sql.toString();
    }

    public String getShopMatchFuncSql(AllpayFunctionDto bean){
        StringBuffer sql = new StringBuffer(" FROM ALLPAY_SHOPFUNCTION AF")
                .append(" LEFT JOIN ALLPAY_SHOPMATCHFUNC ASMF ON AF.SHOPFUNCTION_ID = ASMF.SHOPMATCHFUNC_FUNCTIONID AND ASMF.ALLPAY_LOGICDEL = 1")
                .append(" LEFT JOIN ALLPAY_SHOPMENU AM ON  AM.SHOPMENU_ID= AF.SHOPFUNCTION_MENUID AND AM.ALLPAY_LOGICDEL = 1")
                .append(" LEFT JOIN ALLPAY_SHOPMENU SUP_AM ON SUP_AM.SHOPMENU_ID = AF.SHOPFUNCTION_TOP_MENUID AND SUP_AM.ALLPAY_LOGICDEL = 1")
                .append(" LEFT JOIN ALLPAY_SYSTEM ASY ON ASY.SYSTEM_ID = AF.SHOPFUNCTION_SUP_SYSTEMID AND ASY.ALLPAY_LOGICDEL = 1 WHERE 1=1")
                .append(" AND ASMF.SHOPMATCHFUNC_SHOPID = (SELECT AU.SHOPUSER_SHOPID FROM ALLPAY_SHOPUSER AU WHERE AU.SHOPUSER_ID = '").append(bean.getUserId()).append("')");
        if(!CommonHelper.isEmpty(bean.getMenuId())){
            sql.append(" AND AF.SHOPFUNCTION_MENUID = '").append(bean.getMenuId()).append("'");
        }
        if(!CommonHelper.isEmpty(bean.getFuncType())){  //类型 1--菜单栏目 2---功能操作
            sql.append(" AND AF.SHOPFUNCTION_TYPE = ").append(bean.getFuncType());
        }
        sql.append(" AND AF.ALLPAY_LOGICDEL = 1 AND AF.SHOPFUNCTION_STATE = 1");
        return sql.toString();
    }
}

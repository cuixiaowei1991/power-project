package com.cn.dao.impl;

import com.cn.common.CommonHelper;
import com.cn.dao.AllpayFunctionDao;
import com.cn.dao.util.HibernateDAO;
import com.cn.entity.dto.AllpayFunctionDto;
import com.cn.entity.po.AllpayFunction;
import com.cn.entity.po.AllpayMenu;
import com.cn.entity.po.AllpayUser;
import org.hibernate.criterion.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 资源管理 Dao 实现
 * Created by sun.yayi on 2016/11/22.
 */
@Repository
@Transactional
public class AllpayFunctionDaoImpl implements AllpayFunctionDao {

    @Autowired
    private HibernateDAO hibernateDAO;

    public List getMenuFuncInfoList() throws Exception{
        List list=null;
        String sql="SELECT F.FUNCTION_ID,F.FUNCTION_NAME,F.FUNCTION_ISDEFAULT,F.FUNCTION_TYPE,F.FUNCTION_SUP_MENUID,F.FUNCTION_TOP_MENUID,MENU.MENU_NAME,F.FUNCTION_SUP_SYSTEMID" +
                " FROM ALLPAY_FUNCTION F" +
                " INNER JOIN ALLPAY_MENU MENU" +
                " ON F.FUNCTION_TOP_MENUID=MENU.MENU_ID" +
                " WHERE F.FUNCTION_STATE=1 AND F.ALLPAY_LOGICDEL=1 AND MENU.ALLPAY_LOGICDEL=1 ORDER BY F.FUNCTION_ORDER";
        list=hibernateDAO.listBySQL(sql,false);
        return list;
    }

    public List<?> getAllpayFuncInfoList(AllpayFunctionDto bean,Integer currentPage,Integer pageSize) throws Exception{
        StringBuffer sql = new StringBuffer("SELECT AF.FUNCTION_ID,AF.FUNCTION_ISDEFAULT, AF.FUNCTION_NAME, AF.FUNCTION_STATE, AM1.MENU_NAME AS SUP_MENU_NAME, AM2.MENU_NAME AS TOP_MENU_NAME, ");
        sql.append("AF.FUNCTION_CODE, AF.FUNCTION_URL, AF.FUNCTION_TYPE FROM ALLPAY_FUNCTION AF ")
                .append("LEFT JOIN ALLPAY_MENU AM1 ON AF.FUNCTION_SUP_MENUID = AM1.MENU_ID ")
                .append("LEFT JOIN ALLPAY_MENU AM2 ON AF.FUNCTION_TOP_MENUID = AM2.MENU_ID WHERE 1=1 ");
        if(!CommonHelper.isEmpty(bean.getFuncName())){
            sql.append("AND AF.FUNCTION_NAME LIKE '%").append(bean.getFuncName()).append("%' ");
        }
        if(!CommonHelper.isEmpty(bean.getFuncType())){
            sql.append("AND AF.FUNCTION_TYPE = ").append(bean.getFuncType());
        }
        if(bean.getFuncState() != 0){  //是否启用	1--启用 2--禁用
            sql.append(" AND AF.FUNCTION_STATE = ").append(bean.getFuncState());
        }
        sql.append(" AND AF.ALLPAY_LOGICDEL = 1 ORDER BY AF.ALLPAY_UPDATETIME DESC");
        List list=hibernateDAO.listByPageBySQL(sql.toString(), Integer.parseInt(bean.getPageSize()), Integer.parseInt(bean.getCurragePage()) - 1, false);
        return list;
    }

    public AllpayMenu getMenuName(String function_top_menuId) throws Exception{
        AllpayMenu allpayMenu=null;
        allpayMenu=hibernateDAO.listById(AllpayMenu.class,function_top_menuId,false);
        return allpayMenu;
    }

    public boolean saveOrUpdate(AllpayFunction function) throws Exception {
        String result =  hibernateDAO.saveOrUpdate(function);
        if("success".equals(result)){
            return true;
        }
        return false;
    }

    public AllpayFunction getAllpayFunctionById(String funcId) throws Exception{
        AllpayFunction function=null;
        function=hibernateDAO.listById(AllpayFunction.class,funcId,false);
        return function;
    }

    public boolean delete(String funcId) throws Exception {
        String result = hibernateDAO.removeById(AllpayFunction.class, funcId);
        if("success".equals(result)){
            return true;
        }
        return false;
    }

    public int count(AllpayFunctionDto bean) throws Exception {
        StringBuffer sql = new StringBuffer("SELECT AF.FUNCTION_ID FROM ALLPAY_FUNCTION AF ")
                .append("LEFT JOIN ALLPAY_MENU AM1 ON AF.FUNCTION_SUP_MENUID = AM1.MENU_ID ")
                .append("LEFT JOIN ALLPAY_MENU AM2 ON AF.FUNCTION_TOP_MENUID = AM2.MENU_ID WHERE 1=1 ");
        if(!CommonHelper.isEmpty(bean.getFuncName())){
            sql.append("AND AF.FUNCTION_NAME LIKE '%").append(bean.getFuncName()).append("%' ");
        }
        if(!CommonHelper.isEmpty(bean.getFuncType())){
            sql.append("AND AF.FUNCTION_TYPE = ").append(bean.getFuncType());
        }
        if(bean.getFuncState() != 0){  //是否启用	1--启用 2--禁用
            sql.append(" AND AF.FUNCTION_STATE = ").append(bean.getFuncState());
        }
        sql.append(" AND AF.ALLPAY_LOGICDEL = 1");
        return hibernateDAO.CountBySQL(sql.toString());
    }

    @Override
    public List<?> getMenuInfoList(AllpayFunctionDto bean, String flag) throws Exception {
        String common_sql ="SELECT AF.FUNCTION_ID, SUP_AM.MENU_ORDER AS TOP_MENUORDER, AF.FUNCTION_ORDER,AM.MENU_ORDER,AF.FUNCTION_NAME, AF.FUNCTION_CODE, AF.FUNCTION_URL, AF.FUNCTION_SUP_SYSTEMID, ASY.SYSTEM_NAME,ASY.SYSTEM_PATH, AF.FUNCTION_SUP_MENUID, AM.MENU_NAME, AF.FUNCTION_TOP_MENUID, SUP_AM.MENU_NAME AS TOP_MENUNAME, AF.FUNCTION_TYPE";
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
                .append(" ) TEMP ORDER BY TEMP.FUNCTION_ORDER ASC");

        return hibernateDAO.listBySQL(sql.toString(), false);
    }

    public String getDefaultSql(AllpayFunctionDto bean){
        StringBuffer sql = new StringBuffer(" FROM ALLPAY_FUNCTION AF")
                .append(" LEFT JOIN ALLPAY_MENU AM ON AM.MENU_ID = AF.FUNCTION_SUP_MENUID AND AM.ALLPAY_LOGICDEL = 1")
                .append(" LEFT JOIN ALLPAY_MENU SUP_AM ON SUP_AM.MENU_ID = AF.FUNCTION_TOP_MENUID AND SUP_AM.ALLPAY_LOGICDEL = 1")
                .append(" LEFT JOIN ALLPAY_SYSTEM ASY ON ASY.SYSTEM_ID = AF.FUNCTION_SUP_SYSTEMID AND ASY.ALLPAY_LOGICDEL = 1 ")
                .append(" WHERE AF.FUNCTION_ISDEFAULT=1 ");
        if(!CommonHelper.isEmpty(bean.getMenuId())){
            sql.append(" AND AF.FUNCTION_SUP_MENUID = '").append(bean.getMenuId()).append("'");
        }
        if(!CommonHelper.isEmpty(bean.getFuncType())){  //类型 1--菜单栏目 2---功能操作
            sql.append(" AND AF.FUNCTION_TYPE = ").append(bean.getFuncType());
        }
        sql.append(" AND AF.ALLPAY_LOGICDEL = 1 AND AF.FUNCTION_STATE = 1");
        return sql.toString();
    }

    /**
     * 获取公共查询sql
     * @param bean
     * @return
     */
    public String getSql(AllpayFunctionDto bean){
        StringBuffer sql = new StringBuffer(" FROM ALLPAY_FUNCTION AF")
                .append(" LEFT JOIN ALLPAY_ROLE_FUNCTION ARF ON AF.FUNCTION_ID = ARF.ROLE_FUNCTION_FUNID  AND ARF.ALLPAY_LOGICDEL = 1")
                .append(" LEFT JOIN ALLPAY_MENU AM ON AM.MENU_ID = AF.FUNCTION_SUP_MENUID AND AM.ALLPAY_LOGICDEL = 1")
                .append(" LEFT JOIN ALLPAY_MENU SUP_AM ON SUP_AM.MENU_ID = AF.FUNCTION_TOP_MENUID AND SUP_AM.ALLPAY_LOGICDEL = 1")
                .append(" LEFT JOIN ALLPAY_SYSTEM ASY ON ASY.SYSTEM_ID = AF.FUNCTION_SUP_SYSTEMID AND ASY.ALLPAY_LOGICDEL = 1 WHERE 1=1");
        if(!CommonHelper.isEmpty(bean.getUserId())){
            sql.append(" AND ARF.ROLE_FUNCTION_ROLEID = (SELECT AU.USER_ROLEID FROM ALLPAY_USER AU WHERE AU.USER_ID = '").append(bean.getUserId()).append("')");
        }
        if(!CommonHelper.isEmpty(bean.getMenuId())){
            sql.append(" AND AF.FUNCTION_SUP_MENUID = '").append(bean.getMenuId()).append("'");
        }
        if(!CommonHelper.isEmpty(bean.getFuncType())){  //类型 1--菜单栏目 2---功能操作
            sql.append(" AND AF.FUNCTION_TYPE = ").append(bean.getFuncType());
        }
        sql.append(" AND AF.ALLPAY_LOGICDEL = 1 AND AF.FUNCTION_STATE = 1");
        return sql.toString();
    }
}

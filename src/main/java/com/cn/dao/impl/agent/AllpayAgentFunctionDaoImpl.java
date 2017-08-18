package com.cn.dao.impl.agent;

import com.cn.common.CommonHelper;
import com.cn.dao.agent.AllpayAgentFunctionDao;
import com.cn.dao.util.HibernateDAO;
import com.cn.entity.dto.AllpayFunctionDto;
import com.cn.entity.po.AllpayFunction;
import com.cn.entity.po.agent.AllpayAgentFunction;
import com.cn.entity.po.agent.AllpayAgentUserMatchFunc;
import org.hibernate.criterion.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 资源管理 Dao 实现
 * Created by sun.yayi on 2016/11/30.
 */
@Repository
public class AllpayAgentFunctionDaoImpl implements AllpayAgentFunctionDao {

    @Autowired
    private HibernateDAO hibernateDAO;

    public List getMenuFuncInfoList() throws Exception{
        List list=null;
        String sql="SELECT F.AGENTFUNCTION_ID AS FUNCTION_ID,F.AGENTFUNCTION_NAME AS FUNCTION_NAME,F.AGENTFUNCTION_ISDEFAULT,F.AGENTFUNCTION_TYPE AS FUNCTION_TYPE,F.AGENTFUNCTION_TOP_MENUID AS FUNCTION_TOP_MENUID,F.AGENTFUNCTION_MENUID AS FUNCTION_SUP_MENUID,MENU.AGENTMENU_NAME AS MENU_NAME,F.AGENTFUNCTION_SYSTEMID AS FUNCTION_SUP_SYSTEMID" +
                " FROM ALLPAY_AGENTFUNCTION F LEFT JOIN ALLPAY_AGENTMENU MENU" +
                " ON F.AGENTFUNCTION_TOP_MENUID=MENU.AGENTMENU_ID" +
                " WHERE F.AGENTFUNCTION_STATE=1 AND MENU.ALLPAY_LOGICDEL=1 AND F.ALLPAY_LOGICDEL=1 ORDER BY F.AGENTFUNCTION_ORDER";
        list=hibernateDAO.listBySQL(sql, false);
        return list;
    }

    public List<?> getAllpayFuncInfoList(AllpayFunctionDto bean,Integer currentPage,Integer pageSize) throws Exception{
        StringBuffer sql = new StringBuffer("SELECT AF.AGENTFUNCTION_ID,AF.AGENTFUNCTION_ISDEFAULT, AF.AGENTFUNCTION_NAME, AF.AGENTFUNCTION_STATE, AM1.AGENTMENU_NAME AS SUP_MENU_NAME, AM2.AGENTMENU_NAME AS TOP_MENU_NAME, ");
        sql.append("AF.AGENTFUNCTION_CODE, AF.AGENTFUNCTION_URL, AF.AGENTFUNCTION_TYPE FROM ALLPAY_AGENTFUNCTION AF ")
                .append("LEFT JOIN ALLPAY_AGENTMENU AM1 ON AF.AGENTFUNCTION_MENUID = AM1.AGENTMENU_ID ")
                .append("LEFT JOIN ALLPAY_AGENTMENU AM2 ON AF.AGENTFUNCTION_TOP_MENUID = AM2.AGENTMENU_ID WHERE 1=1 ");
        if(!CommonHelper.isEmpty(bean.getFuncName())){
            sql.append("AND AF.AGENTFUNCTION_NAME LIKE '%").append(bean.getFuncName()).append("%' ");
        }
        if(!CommonHelper.isEmpty(bean.getFuncType())){
            sql.append("AND AF.AGENTFUNCTION_TYPE = ").append(bean.getFuncType());
        }
        if(bean.getFuncState() != 0){  //是否启用	1--启用 2--禁用
            sql.append(" AND AF.AGENTFUNCTION_STATE = ").append(bean.getFuncState());
        }
        sql.append(" AND AF.ALLPAY_LOGICDEL = 1 ORDER BY AF.ALLPAY_UPDATETIME DESC");
        List list=hibernateDAO.listByPageBySQL(sql.toString(), Integer.parseInt(bean.getPageSize()), Integer.parseInt(bean.getCurragePage()) - 1, false);
        return list;
    }

    public boolean saveOrUpdate(AllpayAgentFunction function) throws Exception {
        String result =  hibernateDAO.saveOrUpdate(function);
        if("success".equals(result)){
            return true;
        }
        return false;
    }

    public AllpayAgentFunction getAllpayFunctionById(String funcId) throws Exception{
        AllpayAgentFunction function=null;
        function=hibernateDAO.listById(AllpayAgentFunction.class,funcId,false);
        return function;
    }

    public boolean delete(String funcId) throws Exception {
        String result = hibernateDAO.removeById(AllpayAgentFunction.class, funcId);
        if("success".equals(result)){
            return true;
        }
        return false;
    }

    public int count(AllpayFunctionDto bean) throws Exception {
        StringBuffer sql = new StringBuffer("SELECT AF.AGENTFUNCTION_ID FROM ALLPAY_AGENTFUNCTION AF ")
                .append("LEFT JOIN ALLPAY_AGENTMENU AM1 ON AF.AGENTFUNCTION_MENUID = AM1.AGENTMENU_ID ")
                .append("LEFT JOIN ALLPAY_AGENTMENU AM2 ON AF.AGENTFUNCTION_TOP_MENUID = AM2.AGENTMENU_ID WHERE 1=1 ");
        if(!CommonHelper.isEmpty(bean.getFuncName())){
            sql.append("AND AF.AGENTFUNCTION_NAME LIKE '%").append(bean.getFuncName()).append("%' ");
        }
        if(!CommonHelper.isEmpty(bean.getFuncType())){
            sql.append("AND AF.AGENTFUNCTION_TYPE = ").append(bean.getFuncType());
        }
        if(bean.getFuncState() != 0){  //是否启用	1--启用 2--禁用
            sql.append(" AND AF.AGENTFUNCTION_STATE = ").append(bean.getFuncState());
        }
        sql.append(" AND AF.ALLPAY_LOGICDEL = 1");
        return hibernateDAO.CountBySQL(sql.toString());
    }

    @Override
    public List<?> getMenuInfoList(AllpayFunctionDto bean, String flag) {
        String common_sql ="SELECT AF.AGENTFUNCTION_ID,SUP_AM.AGENTMENU_ORDER AS TOP_AGENTMENUORDER,AF.AGENTFUNCTION_ORDER,AM.AGENTMENU_ORDER,AF.AGENTFUNCTION_NAME,AF.AGENTFUNCTION_CODE,AF.AGENTFUNCTION_URL,AF.AGENTFUNCTION_SYSTEMID,ASY.SYSTEM_NAME,ASY.SYSTEM_PATH,AF.AGENTFUNCTION_MENUID,AM.AGENTMENU_NAME,AF.AGENTFUNCTION_TOP_MENUID,SUP_AM.AGENTMENU_NAME AS TOP_MENUNAME,AF.AGENTFUNCTION_TYPE";
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
                .append(" ) TEMP ORDER BY TEMP.AGENTFUNCTION_ORDER ASC");


        return hibernateDAO.listBySQL(sql.toString(), false);
    }

    @Override
    public List<?> getAllMenuInfoList(AllpayFunctionDto bean,String flag) {
//        bean.setFuncType(flag);
        StringBuffer sql = new StringBuffer("SELECT AF.AGENTFUNCTION_ID,AF.AGENTFUNCTION_NAME,AF.AGENTFUNCTION_CODE,AF.AGENTFUNCTION_URL,AF.AGENTFUNCTION_ORDER,SUP_AM.AGENTMENU_ORDER AS TOP_AGENTMENUORDER,AM.AGENTMENU_ORDER,")
                .append("AF.AGENTFUNCTION_SYSTEMID,ASY.SYSTEM_NAME,ASY.SYSTEM_PATH,AF.AGENTFUNCTION_MENUID,AM.AGENTMENU_NAME,AF.AGENTFUNCTION_TOP_MENUID,SUP_AM.AGENTMENU_NAME AS TOP_MENUNAME,AF.AGENTFUNCTION_TYPE")
                .append(" FROM ALLPAY_AGENTFUNCTION AF")
                .append(" LEFT JOIN ALLPAY_AGENTMENU AM ON AM.AGENTMENU_ID = AF.AGENTFUNCTION_MENUID AND AM.ALLPAY_LOGICDEL = 1")
                .append(" LEFT JOIN ALLPAY_AGENTMENU SUP_AM ON SUP_AM.AGENTMENU_ID = AF.AGENTFUNCTION_TOP_MENUID AND SUP_AM.ALLPAY_LOGICDEL = 1")
                .append(" LEFT JOIN ALLPAY_SYSTEM ASY ON ASY.SYSTEM_ID = AF.AGENTFUNCTION_SYSTEMID AND ASY.ALLPAY_LOGICDEL = 1")
                .append(" WHERE 1=1");
        if(!CommonHelper.isEmpty(bean.getMenuId())){
            sql.append("  AND AF.AGENTFUNCTION_MENUID='").append(bean.getMenuId()).append("'");
        }
        if(!CommonHelper.isEmpty(flag)){  //类型 1--菜单栏目 2---功能操作
            sql.append(" AND AF.AGENTFUNCTION_TYPE=").append(flag);
        }
        sql.append(" AND AF.ALLPAY_LOGICDEL = 1 AND AF.AGENTFUNCTION_STATE = 1 ORDER BY AF.AGENTFUNCTION_ORDER ASC");
        return hibernateDAO.listBySQL(sql.toString(), false);
    }

    /**
     *  2.4.1获取用户权限信息列表
     * @param agentUserId
     * @return
     */
    @Override
    public List<?> getAgentFuncList(String agentUserId) {
        StringBuffer sql = new StringBuffer("SELECT AA.AGENTUSERMATCHFUNC_ISREDUCE,AF.AGENTFUNCTION_ISDEFAULT,AA.AGENTUSERMATCHFUNC_MENUID, AA.AGENTROLEMATCHFUNC_FUNCID, AA.AGENTUSERMATCHFUNC_SYSTEMID, AA.AGENTUSERMATCHFUNC_USERID,AU.AGENTUSER_NAME")
                .append(" FROM ALLPAY_AGENTUSERMATCHFUNC AA ")
                .append(" LEFT JOIN ALLPAY_AGENTFUNCTION AF ON AA.AGENTROLEMATCHFUNC_FUNCID = AF.AGENTFUNCTION_ID AND AF.ALLPAY_LOGICDEL = 1")
                .append(" LEFT JOIN ALLPAY_AGENTMENU AM ON AM.AGENTMENU_ID = AA.AGENTUSERMATCHFUNC_MENUID AND AM.ALLPAY_LOGICDEL = 1")
                .append(" LEFT JOIN ALLPAY_AGENTUSER AU ON AA.AGENTUSERMATCHFUNC_USERID = AU.AGENTUSER_ID AND AU.ALLPAY_LOGICDEL = 1")
                .append(" WHERE 1=1");
        if(!CommonHelper.isEmpty(agentUserId)){  //代理商用户id
            sql.append(" AND AA.AGENTUSERMATCHFUNC_USERID= '").append(agentUserId).append("'");
        }
        sql.append(" AND AA.ALLPAY_LOGICDEL = 1 AND AF.AGENTFUNCTION_STATE = 1");
        return hibernateDAO.listBySQL(sql.toString(), false);
    }

    @Override
    public boolean deleteAgentUserInfo(String userId) throws Exception {
        String sql="DELETE FROM ALLPAY_AGENTUSERMATCHFUNC AA WHERE AA.AGENTUSERMATCHFUNC_USERID='"+userId+"'";
        hibernateDAO.executeBySql(sql);
        return true;
    }

    @Override
    public boolean insertAgentUserMatchFunc(AllpayAgentUserMatchFunc agentUserMatchFunc) throws Exception {
        String result =  hibernateDAO.save(agentUserMatchFunc);
        if("success".equals(result)){
            return true;
        }
        return false;
    }

    @Override
    public List<?> getAgentQxInfoList(String agentId) throws Exception {
        StringBuffer sql = new StringBuffer("SELECT AF.AGENTFUNCTION_ID,AF.AGENTFUNCTION_ORDER,AF.AGENTFUNCTION_NAME,AF.AGENTFUNCTION_ISDEFAULT,SM.AGENTMENU_NAME,AF.AGENTFUNCTION_TOP_MENUID,AF.AGENTFUNCTION_TYPE,AF.AGENTFUNCTION_MENUID,AF.AGENTFUNCTION_SYSTEMID,")
                .append(" SM.AGENTMENU_ID, SUP_AM.AGENTMENU_NAME AS TOP_MENU_NAME")
                .append(" FROM ALLPAY_AGENTMATCHFUNC ASF")
                .append(" LEFT JOIN ALLPAY_AGENTFUNCTION AF ON ASF.AGENTMATCHFUNC_FUNCTIONID=AF.AGENTFUNCTION_ID AND AF.ALLPAY_LOGICDEL=1")
                .append(" LEFT JOIN ALLPAY_AGENTMENU SM ON AF.AGENTFUNCTION_MENUID=SM.AGENTMENU_ID AND SM.ALLPAY_LOGICDEL=1")
                .append(" LEFT JOIN ALLPAY_AGENTMENU SUP_AM ON AF.AGENTFUNCTION_TOP_MENUID=SUP_AM.AGENTMENU_ID AND SUP_AM.ALLPAY_LOGICDEL=1")
                .append(" WHERE 1=1 AND ASF.AGENTMATCHFUNC_AGENTID='").append(agentId).append("'")
                .append(" AND AF.AGENTFUNCTION_STATE=1 AND ASF.ALLPAY_LOGICDEL=1")
                .append(" UNION ALL ")
                .append("SELECT F.AGENTFUNCTION_ID,F.AGENTFUNCTION_ORDER,F.AGENTFUNCTION_NAME,F.AGENTFUNCTION_ISDEFAULT,MENU.AGENTMENU_NAME,F.AGENTFUNCTION_TOP_MENUID,")
                .append(" F.AGENTFUNCTION_TYPE,F.AGENTFUNCTION_MENUID,F.AGENTFUNCTION_SYSTEMID,MENU.AGENTMENU_ID,SUP_AM.AGENTMENU_NAME AS TOP_MENU_NAME")
                .append(" FROM ALLPAY_AGENTFUNCTION F")
                .append(" LEFT JOIN ALLPAY_AGENTMENU MENU ON F.AGENTFUNCTION_MENUID=MENU.AGENTMENU_ID  AND MENU.ALLPAY_LOGICDEL=1")
                .append(" LEFT JOIN ALLPAY_AGENTMENU SUP_AM ON F.AGENTFUNCTION_TOP_MENUID=SUP_AM.AGENTMENU_ID  AND MENU.ALLPAY_LOGICDEL=1")
                .append(" WHERE F.AGENTFUNCTION_ISDEFAULT=1 AND F.AGENTFUNCTION_STATE=1 AND F.ALLPAY_LOGICDEL=1");

        List list=hibernateDAO.listBySQL(sql.toString(), false);
        return list;
    }

    /**
     * 默认功能
     * @param bean
     * @return
     */
    public String getDefaultSql(AllpayFunctionDto bean){
        StringBuffer sql = new StringBuffer(" FROM ALLPAY_AGENTFUNCTION AF")
                .append(" LEFT JOIN ALLPAY_AGENTMENU AM ON AM.AGENTMENU_ID = AF.AGENTFUNCTION_MENUID AND AM.ALLPAY_LOGICDEL = 1")
                .append(" LEFT JOIN ALLPAY_AGENTMENU SUP_AM ON SUP_AM.AGENTMENU_ID = AF.AGENTFUNCTION_TOP_MENUID AND SUP_AM.ALLPAY_LOGICDEL = 1")
                .append(" LEFT JOIN ALLPAY_SYSTEM ASY ON ASY.SYSTEM_ID = AF.AGENTFUNCTION_SYSTEMID AND ASY.ALLPAY_LOGICDEL = 1")
                .append(" WHERE 1=1")
                .append(" AND AF.AGENTFUNCTION_ID NOT IN (SELECT AMF.AGENTROLEMATCHFUNC_FUNCID FROM ALLPAY_AGENTUSERMATCHFUNC AMF WHERE AMF.AGENTUSERMATCHFUNC_ISREDUCE=0")
                .append(" AND AMF.AGENTUSERMATCHFUNC_USERID='").append(bean.getUserId()).append("')")
                .append(" AND AF.AGENTFUNCTION_ISDEFAULT=1");
        if(!CommonHelper.isEmpty(bean.getMenuId())){
            sql.append(" AND AF.AGENTFUNCTION_MENUID= '").append(bean.getMenuId()).append("'");
        }
        if(!CommonHelper.isEmpty(bean.getFuncType())){  //类型 1--菜单栏目 2---功能操作
            sql.append(" AND AF.AGENTFUNCTION_TYPE= ").append(bean.getFuncType());
        }
        sql.append(" AND AF.ALLPAY_LOGICDEL = 1 AND AF.AGENTFUNCTION_STATE = 1");
        return sql.toString();
    }

    /**
     * 获取公共查询sql
     * @param bean
     * @return
     */
    public String getSql(AllpayFunctionDto bean){
        StringBuffer sql = new StringBuffer(" FROM ALLPAY_AGENTFUNCTION AF")
                .append(" LEFT JOIN ALLPAY_AGENTUSERMATCHFUNC AAF ON AF.AGENTFUNCTION_ID = AAF.AGENTROLEMATCHFUNC_FUNCID AND AAF.ALLPAY_LOGICDEL = 1")
                .append(" LEFT JOIN ALLPAY_AGENTMENU AM ON AM.AGENTMENU_ID = AF.AGENTFUNCTION_MENUID AND AM.ALLPAY_LOGICDEL = 1")
                .append(" LEFT JOIN ALLPAY_AGENTMENU SUP_AM ON SUP_AM.AGENTMENU_ID = AF.AGENTFUNCTION_TOP_MENUID AND SUP_AM.ALLPAY_LOGICDEL = 1")
                .append(" LEFT JOIN ALLPAY_SYSTEM ASY ON ASY.SYSTEM_ID = AF.AGENTFUNCTION_SYSTEMID AND ASY.ALLPAY_LOGICDEL = 1")
                .append(" WHERE 1=1")
                .append(" AND AAF.AGENTUSERMATCHFUNC_ISREDUCE=1")
                .append(" AND AAF.AGENTUSERMATCHFUNC_USERID='").append(bean.getUserId()).append("'");
        if(!CommonHelper.isEmpty(bean.getMenuId())){
            sql.append(" AND AF.AGENTFUNCTION_MENUID= '").append(bean.getMenuId()).append("'");
        }
        if(!CommonHelper.isEmpty(bean.getFuncType())){  //类型 1--菜单栏目 2---功能操作
            sql.append(" AND AF.AGENTFUNCTION_TYPE= ").append(bean.getFuncType());
        }
        sql.append(" AND AF.ALLPAY_LOGICDEL = 1 AND AF.AGENTFUNCTION_STATE = 1");
        return sql.toString();
    }
}

package com.cn.dao.impl.agent;

import com.cn.common.CommonHelper;
import com.cn.dao.agent.AllpayAgentMatchFuncDao;
import com.cn.dao.util.HibernateDAO;
import com.cn.entity.dto.AllpayAgentMatchFuncDto;
import com.cn.entity.po.agent.AllpayAgentMatchFunc;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 代理商权限 资源菜单 管理
 * Created by sun.yayi on 2016/12/7.
 */
@Repository
public class AllpayAgentMatchFuncDaoImpl implements AllpayAgentMatchFuncDao {
    @Autowired
    private HibernateDAO hibernateDAO;


    public boolean saveOrUpate(AllpayAgentMatchFunc allpayAgentMatchFunc) throws Exception {
        String result =  hibernateDAO.saveOrUpdate(allpayAgentMatchFunc);
        if("success".equals(result)){
            return true;
        }
        return false;
    }

    public List<Map<String, Object>> getAgentSetInfo(AllpayAgentMatchFuncDto bean) throws Exception {
        StringBuffer sql = new StringBuffer("SELECT AF.AGENTMATCHFUNC_AGENTID,AF.AGENTMATCHFUNC_FUNCTIONID,AF.AGENTMATCHFUNC_MENUID,AF.AGENTMATCHFUNC_FUNSYSTEMID,A.AGENT_NAME");
        sql.append(" FROM ALLPAY_AGENTMATCHFUNC AF LEFT JOIN ALLPAY_AGENT A ON AF.AGENTMATCHFUNC_AGENTID=A.AGENT_ID");
        sql.append(" WHERE AF.AGENTMATCHFUNC_AGENTID='").append(bean.getAgentId()).append("' AND  AF.ALLPAY_LOGICDEL=1");
        List<Map<String, Object>> list=null;
        list= (List<Map<String, Object>>) hibernateDAO.listBySQL(sql.toString(),false);
        return list;
    }

    public List<AllpayAgentMatchFunc> getListByAgentId(String agentId) {
        Conjunction con = Restrictions.conjunction();
        con.add(Restrictions.eq("agentmatchfunc_agentid", agentId));
        con.add(Restrictions.eq("ALLPAY_LOGICDEL", "1"));
        List<AllpayAgentMatchFunc> list=null;
        list=hibernateDAO.listByCriteria(AllpayAgentMatchFunc.class,con,false);
        return list;
    }


}

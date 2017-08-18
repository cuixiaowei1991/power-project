package com.cn.dao.impl.agent;

import com.cn.common.CommonHelper;
import com.cn.dao.agent.AllpayAgentMenuDao;
import com.cn.dao.util.HibernateDAO;
import com.cn.entity.dto.AllpayMenuDto;
import com.cn.entity.po.agent.AllpayAgentMenu;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 菜单管理dao层实现
 * Created by WangWenFang on 2016/11/23.
 */
@Repository
public class AllpayAgentMenuDaoImpl implements AllpayAgentMenuDao {

    @Autowired
    private HibernateDAO hibernateDAO;

    public boolean add(AllpayAgentMenu allpayAgentMenu) throws Exception {
        String result =  hibernateDAO.saveOrUpdate(allpayAgentMenu);
        if("success".equals(result)){
            return true;
        }
        return false;
    }

    public boolean delete(String menuId) throws Exception {
        String result = hibernateDAO.removeById(AllpayAgentMenu.class, menuId);
        if("success".equals(result)){
            return true;
        }
        return false;
    }

    public boolean update(AllpayAgentMenu allpayAgentMenu) throws Exception {
        String result =  hibernateDAO.saveOrUpdate(allpayAgentMenu);
        if("success".equals(result)){
            return true;
        }
        return false;
    }

    public List<?> obtainList(AllpayMenuDto allpayMenuDto, Integer currentPage, Integer pageSize) throws Exception {
        StringBuffer sql = new StringBuffer("SELECT AM1.AGENTMENU_ID, AM1.AGENTMENU_NAME, AM1.AGENTMENU_ORDER, AM1.AGENTMENU_SUPERIORID, AM1.AGENTMENU_LEVEL, AM1.AGENTMENU_STATE, AM2.AGENTMENU_NAME AS MENUSUPERIORNAME ");
        sql.append("FROM ALLPAY_AGENTMENU AM1 LEFT JOIN ALLPAY_AGENTMENU AM2 ON AM1.AGENTMENU_SUPERIORID = AM2.AGENTMENU_ID WHERE 1=1 ");

        if(!CommonHelper.isEmpty(allpayMenuDto.getMenuName())){ //菜单名称
            sql.append("AND AM1.AGENTMENU_NAME LIKE '%").append(allpayMenuDto.getMenuName()).append("%' ");
        }
        if(!CommonHelper.isEmpty(allpayMenuDto.getMenuLevel())){ //级别
            sql.append("AND AM1.AGENTMENU_LEVEL = ").append(allpayMenuDto.getMenuLevel());
        }
        if(!CommonHelper.isEmpty(allpayMenuDto.getMenuState())){ //是否启用	1--启用 2--禁用
            sql.append(" AND AM1.AGENTMENU_STATE = ").append(allpayMenuDto.getMenuState());
        }
        sql.append(" AND AM1.ALLPAY_LOGICDEL=1 ORDER BY AM1.ALLPAY_UPDATETIME DESC");  //ALLPAY_LOGICDEL 1 未删除
        return hibernateDAO.listBySQL(sql.toString(), false);
    }

    public AllpayAgentMenu getById(String menuId) throws Exception {
        return hibernateDAO.listById(AllpayAgentMenu.class, menuId, false);
    }

    public int count(Criterion criterion) throws Exception {
        return hibernateDAO.countByCriteria(AllpayAgentMenu.class, criterion, false);
    }

    public boolean isExistMenuName(AllpayMenuDto allpayMenuDto) throws Exception {
        StringBuffer sql=new StringBuffer("SELECT AGENTMENU.AGENTMENU_ID FROM ALLPAY_AGENTMENU AGENTMENU WHERE AGENTMENU.AGENTMENU_NAME='").append(allpayMenuDto.getMenuName()).append("' AND AGENTMENU.ALLPAY_LOGICDEL='1'");
        List<Map<String, Object>> list= (List<Map<String, Object>>) hibernateDAO.listBySQL(sql.toString(),false);
        if(list!=null && list.size()>0){
            if(CommonHelper.isNullOrEmpty(allpayMenuDto.getMenuId())){
                return true;
            }else{
                Map map=list.get(0);
                if(!allpayMenuDto.getMenuId().equals(CommonHelper.nullToString(map.get("AGENTMENU_ID")))){
                    return true;
                }
            }
        }
        return false;
    }
}

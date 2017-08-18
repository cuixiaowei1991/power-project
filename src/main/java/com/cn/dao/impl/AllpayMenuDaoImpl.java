package com.cn.dao.impl;

import com.cn.common.CommonHelper;
import com.cn.dao.AllpayMenuDao;
import com.cn.dao.util.HibernateDAO;
import com.cn.entity.dto.AllpayMenuDto;
import com.cn.entity.po.AllpayMenu;
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
@Repository("allpayMenuDao")
@Transactional
public class AllpayMenuDaoImpl implements AllpayMenuDao {

    @Autowired
    private HibernateDAO hibernateDAO;

    public boolean add(AllpayMenu allpayMenu) throws Exception {
        String result =  hibernateDAO.saveOrUpdate(allpayMenu);
        if("success".equals(result)){
            return true;
        }
        return false;
    }

    public boolean delete(String userId) throws Exception {
        String result = hibernateDAO.removeById(AllpayMenu.class, userId);
        if("success".equals(result)){
            return true;
        }
        return false;
    }

    public boolean update(AllpayMenu allpayMenu) throws Exception {
        String result =  hibernateDAO.saveOrUpdate(allpayMenu);
        if("success".equals(result)){
            return true;
        }
        return false;
    }

    public <T> List<?> obtainList(AllpayMenuDto allpayMenuDto, Integer currentPage, Integer pageSize) throws Exception {
        StringBuffer sql = new StringBuffer("SELECT AM1.MENU_ID, AM1.MENU_NAME, AM1.MENU_ORDER, AM1.MENU_SUPERIORID, AM1.MENU_LEVEL, AM1.MENU_STATE, AM1.ALLPAY_UPDATETIME, AM2.MENU_NAME AS MENUSUPERIORNAME FROM ALLPAY_MENU AM1 ");
        sql.append("LEFT JOIN ALLPAY_MENU AM2 ON AM1.MENU_SUPERIORID = AM2.MENU_ID WHERE 1=1 ");

        if(!CommonHelper.isEmpty(allpayMenuDto.getMenuName())){ //菜单名称
            sql.append("AND AM1.MENU_NAME LIKE '%").append(allpayMenuDto.getMenuName()).append("%' ");
        }
        if(!CommonHelper.isEmpty(allpayMenuDto.getMenuLevel())){ //级别
            sql.append("AND AM1.MENU_LEVEL = ").append(allpayMenuDto.getMenuLevel());
        }
        if(!CommonHelper.isEmpty(allpayMenuDto.getMenuState())){ //是否启用	1--启用 2--禁用
            sql.append(" AND AM1.MENU_STATE = ").append(allpayMenuDto.getMenuState());
        }
        sql.append(" AND AM1.ALLPAY_LOGICDEL=1 ORDER BY AM1.ALLPAY_UPDATETIME DESC"); //ALLPAY_LOGICDEL 1 未删除
        return hibernateDAO.listBySQL(sql.toString(), false);
    }

    public <T> T getById(Class<T> clazz, String userId) throws Exception {
        return hibernateDAO.listById(clazz, userId, false);
    }

    public int count(Criterion criterion) throws Exception {
        return hibernateDAO.countByCriteria(AllpayMenu.class, criterion, false);
    }

    public boolean isExistMenuName(AllpayMenuDto allpayMenuDto) throws Exception{
        StringBuffer sql=new StringBuffer("SELECT MENU.MENU_ID FROM ALLPAY_MENU MENU WHERE MENU.MENU_NAME='").append(allpayMenuDto.getMenuName()).append("' AND MENU.ALLPAY_LOGICDEL='1'");
        List<Map<String, Object>> list= (List<Map<String, Object>>) hibernateDAO.listBySQL(sql.toString(),false);
        if(list!=null && list.size()>0){
            if(CommonHelper.isNullOrEmpty(allpayMenuDto.getMenuId())){
                return true;
            }else{
                Map map=list.get(0);
                if(!allpayMenuDto.getMenuId().equals(CommonHelper.nullToString(map.get("MENU_ID")))){
                    return true;
                }
            }
        }
        return false;
    }
}

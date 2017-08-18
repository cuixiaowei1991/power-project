package com.cn.dao.impl.shop;

import com.cn.MsgCode.MsgAndCode;
import com.cn.common.CommonHelper;
import com.cn.dao.shop.AllpayShopmenuDao;
import com.cn.dao.util.HibernateDAO;
import com.cn.entity.dto.AllpayMenuDto;
import com.cn.entity.po.shop.AllpayShopmenu;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 商助菜单管理dao层实现
 * Created by WangWenFang on 2016/11/30.
 */
@Repository("allpayShopmenuDao")
@Transactional(rollbackFor = {Exception.class, RuntimeException.class})
public class AllpayShopmenuDaoImpl implements AllpayShopmenuDao {

    @Autowired
    private HibernateDAO hibernateDAO;

    public boolean add(AllpayShopmenu allpayShopmenu) throws Exception {
        String result =  hibernateDAO.saveOrUpdate(allpayShopmenu);
        if("success".equals(result)){
            return true;
        }
        return false;
    }

    public boolean delete(String shopmenuId) throws Exception {
        String result = hibernateDAO.removeById(AllpayShopmenu.class, shopmenuId);
        if("success".equals(result)){
            return true;
        }
        return false;
    }

    public boolean update(AllpayShopmenu allpayShopmenu) throws Exception {
        String result =  hibernateDAO.saveOrUpdate(allpayShopmenu);
        if("success".equals(result)){
            return true;
        }
        return false;
    }

    public <T> List<?> obtainList(AllpayMenuDto allpayMenuDto, Integer currentPage, Integer pageSize) throws Exception {
        StringBuffer sql = new StringBuffer("SELECT AM1.SHOPMENU_ID, AM1.SHOPMENU_NAME, AM1.SHOPMENU_ORDER, AM1.SHOPMENU_SUPERIORID, AM1.SHOPMENU_LEVEL, AM1.SHOPMENU_STATE, AM2.SHOPMENU_NAME AS MENUSUPERIORNAME ");
        sql.append("FROM ALLPAY_SHOPMENU AM1 LEFT JOIN ALLPAY_SHOPMENU AM2 ON AM1.SHOPMENU_SUPERIORID = AM2.SHOPMENU_ID WHERE 1=1 ");

        if(!CommonHelper.isEmpty(allpayMenuDto.getMenuName())){ //菜单名称
            sql.append("AND AM1.SHOPMENU_NAME LIKE '%").append(allpayMenuDto.getMenuName()).append("%' ");
        }
        if(!CommonHelper.isEmpty(allpayMenuDto.getMenuLevel())){ //级别
            sql.append("AND AM1.SHOPMENU_LEVEL = ").append(allpayMenuDto.getMenuLevel());
        }
        if(!CommonHelper.isEmpty(allpayMenuDto.getMenuState())){ //是否启用	1--启用 2--禁用
            sql.append(" AND AM1.SHOPMENU_STATE = ").append(allpayMenuDto.getMenuState());
        }
        sql.append(" AND AM1.ALLPAY_LOGICDEL=1 ORDER BY AM1.ALLPAY_UPDATETIME DESC");  //ALLPAY_LOGICDEL 1 未删除
        return hibernateDAO.listBySQL(sql.toString(), false);
    }

    public <T> T getById(Class<T> clazz, String shopmenuId) throws Exception {
        return hibernateDAO.listById(clazz, shopmenuId, false);
    }

    public int count(Criterion criterion) throws Exception {
        return hibernateDAO.countByCriteria(AllpayShopmenu.class, criterion, false);
    }

    public boolean isExistMenuName(AllpayMenuDto allpayMenuDto) throws Exception {
        StringBuffer sql=new StringBuffer("SELECT SHOPMENU.SHOPMENU_ID FROM ALLPAY_SHOPMENU SHOPMENU WHERE SHOPMENU.SHOPMENU_NAME='").append(allpayMenuDto.getMenuName()).append("' AND SHOPMENU.ALLPAY_LOGICDEL='1'");
        List<Map<String,Object>> list=null;
        list= (List<Map<String, Object>>) hibernateDAO.listBySQL(sql.toString(),false);
        if(list!=null && list.size()>0){
            if(CommonHelper.isNullOrEmpty(allpayMenuDto.getMenuId())){
               return true;
            }else{
                Map map=list.get(0);
                if(!allpayMenuDto.getMenuId().equals(CommonHelper.nullToString(map.get("SHOPMENU_ID")))){
                   return true;
                }
            }
        }
        return false;
    }
}

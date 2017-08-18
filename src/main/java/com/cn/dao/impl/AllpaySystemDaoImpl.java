package com.cn.dao.impl;

import com.cn.common.CommonHelper;
import com.cn.dao.AllpaySystemDao;
import com.cn.dao.util.HibernateDAO;
import com.cn.entity.dto.AllpaySystemDto;
import com.cn.entity.po.AllpaySystem;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 系统管理dao层实现
 * Created by WangWenFang on 2016/11/23.
 */
@Repository("allpaySystemDao")
@Transactional
public class AllpaySystemDaoImpl implements AllpaySystemDao {

    @Autowired
    private HibernateDAO hibernateDAO;

    public boolean add(AllpaySystem allpaySystem) throws Exception {
        String result =  hibernateDAO.saveOrUpdate(allpaySystem);
        if("success".equals(result)){
            return true;
        }
        return false;
    }

    public boolean delete(String userId) throws Exception {
        String result = hibernateDAO.removeById(AllpaySystem.class, userId);
        if("success".equals(result)){
            return true;
        }
        return false;
    }

    public boolean update(AllpaySystem allpaySystem) throws Exception {
        String result =  hibernateDAO.saveOrUpdate(allpaySystem);
        if("success".equals(result)){
            return true;
        }
        return false;
    }

    public <T> List<T> obtainList(Class<T> clazz, Criterion criterion, Integer currentPage, Integer pageSize) throws Exception {
        return hibernateDAO.listByCriteria(clazz, criterion, Order.desc("ALLPAY_UPDATETIME"), false);
    }

    public <T> T getById(Class<T> clazz, String userId) throws Exception {
        return hibernateDAO.listById(clazz, userId, false);
    }

    public int count(Criterion criterion) throws Exception {
        return hibernateDAO.countByCriteria(AllpaySystem.class, criterion, false);
    }

    @Override
    public boolean isExistSysName(AllpaySystemDto allpaySystemDto) throws Exception {
        StringBuffer sql=new StringBuffer("SELECT S.SYSTEM_ID FROM ALLPAY_SYSTEM S WHERE S.SYSTEM_NAME='").append(allpaySystemDto.getSystemName()).append("' AND S.ALLPAY_LOGICDEL='1'");
        List<Map<String, Object>> list= (List<Map<String, Object>>) hibernateDAO.listBySQL(sql.toString(),false);
        if(list!=null && list.size()>0){
            if(CommonHelper.isNullOrEmpty(allpaySystemDto.getSystemId())){
                return true;
            }else{
                Map map=list.get(0);
                if(!allpaySystemDto.getSystemId().equals(CommonHelper.nullToString(map.get("SYSTEM_ID")))){
                    return true;
                }
            }
        }
        return false;
    }
}

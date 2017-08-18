package com.cn.dao.impl;

import com.cn.common.CommonHelper;
import com.cn.dao.AllpayOrganizationDao;
import com.cn.dao.util.HibernateDAO;
import com.cn.entity.dto.AllpayOrganizationDto;
import com.cn.entity.po.AllpayOrganization;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 组织机构管理dao层实现
 * Created by WangWenFang on 2016/11/23.
 */
@Repository("allpayOrganizationDao")
@Transactional
public class AllpayOrganizationDaoImpl implements AllpayOrganizationDao {

    @Autowired
    private HibernateDAO hibernateDAO;

    public boolean add(AllpayOrganization organization) throws Exception {
        String result =  hibernateDAO.saveOrUpdate(organization);
        if("success".equals(result)){
            return true;
        }
        return false;
    }

    public boolean delete(String userId) throws Exception {
        String result = hibernateDAO.removeById(AllpayOrganization.class, userId);
        if("success".equals(result)){
            return true;
        }
        return false;
    }

    public boolean update(AllpayOrganization organization) throws Exception {
        String result =  hibernateDAO.saveOrUpdate(organization);
        if("success".equals(result)){
            return true;
        }
        return false;
    }

    public <T> List<T> obtainList(Class<T> clazz, Criterion criterion, Integer currentPage, Integer pageSize) throws Exception {
        return hibernateDAO.listByPageByOrderCriteria(clazz, criterion, pageSize, currentPage, Order.desc("ALLPAY_UPDATETIME"), false);
    }

    public <T> T getById(Class<T> clazz, String userId) throws Exception {
        return hibernateDAO.listById(clazz, userId, false);
    }

    public int count(Criterion criterion) throws Exception {
        return hibernateDAO.countByCriteria(AllpayOrganization.class, criterion, false);
    }

    @Override
    public boolean isExistOrganName(AllpayOrganizationDto organizationDto) throws Exception {
        StringBuffer sql=new StringBuffer("SELECT O.ORGANIZATION_ID FROM ALLPAY_ORGANIZATION O where O.ORGANIZATION_NAME='").append(organizationDto.getOrganizationName()).append("' AND O.ALLPAY_LOGICDEL='1'");
        List<Map<String, Object>> list= (List<Map<String, Object>>) hibernateDAO.listBySQL(sql.toString(),false);
        if(list!=null && list.size()>0){
            if(CommonHelper.isNullOrEmpty(organizationDto.getOrganizationId())){
                return true;
            }else{
                Map map=list.get(0);
                if(!organizationDto.getOrganizationId().equals(CommonHelper.nullToString(map.get("ORGANIZATION_ID")))){
                    return true;
                }
            }
        }
        return false;
    }
}

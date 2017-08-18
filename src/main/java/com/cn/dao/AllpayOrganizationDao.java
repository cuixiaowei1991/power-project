package com.cn.dao;

import com.cn.entity.dto.AllpayOrganizationDto;
import com.cn.entity.po.AllpayOrganization;
import org.hibernate.criterion.Criterion;

import java.util.List;
import java.util.Map;

/**
 * 组织机构管理dao层接口
 * Created by WangWenFang on 2016/11/23.
 */
public interface AllpayOrganizationDao {

    boolean add(AllpayOrganization organization) throws Exception;

    boolean delete(String userId) throws Exception;

    boolean update(AllpayOrganization organization)throws Exception;

    <T> List<T> obtainList(Class<T> clazz,Criterion criterion,Integer currentPage,Integer pageSize)throws Exception;

    <T> T getById(Class<T> clazz, String userId) throws Exception;

    int count(Criterion criterion) throws Exception;

    boolean isExistOrganName(AllpayOrganizationDto organizationDto) throws Exception;
}

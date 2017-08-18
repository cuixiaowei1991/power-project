package com.cn.service;

import com.cn.entity.dto.AllpayOrganizationDto;
import com.cn.entity.po.AllpayOrganization;
import org.hibernate.criterion.Criterion;

import java.util.Map;

/**
 * 组织机构管理业务层接口
 * Created by WangWenFang on 2016/11/23.
 */
public interface AllpayOrganizationService {

    String add(AllpayOrganizationDto organizationDto) throws Exception;

    String delete(AllpayOrganizationDto organizationDto) throws Exception;

    String update(AllpayOrganizationDto organizationDto)throws Exception;

    String obtainList(AllpayOrganizationDto organizationDto, Class clazz,Integer currentPage,Integer pageSize)throws Exception;

    <T> T getById(Class<T> clazz, String userId) throws Exception;

    String getById(AllpayOrganizationDto organizationDto) throws Exception;

    int count(Criterion criterion) throws Exception;
}

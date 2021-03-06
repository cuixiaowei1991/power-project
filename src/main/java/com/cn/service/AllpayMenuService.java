package com.cn.service;

import com.cn.entity.dto.AllpayMenuDto;
import org.hibernate.criterion.Criterion;

/**
 * 菜单管理业务层接口
 * Created by WangWenFang on 2016/11/23.
 */
public interface AllpayMenuService {

    String add(AllpayMenuDto allpayMenuDto) throws Exception;

    String delete(AllpayMenuDto allpayMenuDto) throws Exception;

    String update(AllpayMenuDto allpayMenuDto)throws Exception;

    String obtainList(AllpayMenuDto allpayMenuDto, Integer currentPage,Integer pageSize)throws Exception;

    <T> T getById(Class<T> clazz, String userId) throws Exception;

    String getById(AllpayMenuDto allpayMenuDto) throws Exception;

    int count(Criterion criterion) throws Exception;
}

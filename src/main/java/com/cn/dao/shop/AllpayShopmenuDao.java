package com.cn.dao.shop;

import com.cn.entity.dto.AllpayMenuDto;
import com.cn.entity.po.shop.AllpayShopmenu;
import org.hibernate.criterion.Criterion;

import java.util.List;
import java.util.Map;

/**
 * 商助菜单管理dao层接口
 * Created by WangWenFang on 2016/11/30.
 */
public interface AllpayShopmenuDao {

    boolean add(AllpayShopmenu allpayShopmenu) throws Exception;

    boolean delete(String userId) throws Exception;

    boolean update(AllpayShopmenu allpayShopmenu)throws Exception;

    <T> List<?> obtainList(AllpayMenuDto allpayMenuDto,Integer currentPage,Integer pageSize)throws Exception;

    <T> T getById(Class<T> clazz, String userId) throws Exception;

    int count(Criterion criterion) throws Exception;

    boolean isExistMenuName(AllpayMenuDto allpayMenuDto) throws Exception;
}

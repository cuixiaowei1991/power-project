package com.cn.dao;

import com.cn.entity.dto.AllpayMenuDto;
import com.cn.entity.po.AllpayMenu;
import org.hibernate.criterion.Criterion;

import java.util.List;

/**
 * 菜单管理dao层接口
 * Created by WangWenFang on 2016/11/23.
 */
public interface AllpayMenuDao {

    boolean add(AllpayMenu allpayMenu) throws Exception;

    boolean delete(String userId) throws Exception;

    boolean update(AllpayMenu allpayMenu)throws Exception;

    <T> List<?> obtainList(AllpayMenuDto allpayMenuDto,Integer currentPage,Integer pageSize)throws Exception;

    <T> T getById(Class<T> clazz, String userId) throws Exception;

    int count(Criterion criterion) throws Exception;

    boolean isExistMenuName(AllpayMenuDto allpayMenuDto) throws Exception;
}

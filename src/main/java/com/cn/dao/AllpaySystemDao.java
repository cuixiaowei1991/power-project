package com.cn.dao;

import com.cn.entity.dto.AllpaySystemDto;
import com.cn.entity.po.AllpaySystem;
import org.hibernate.criterion.Criterion;

import java.util.List;

/**
 * 系统管理dao层接口
 * Created by WangWenFang on 2016/11/23.
 */
public interface AllpaySystemDao {

    boolean add(AllpaySystem allpaySystem) throws Exception;

    boolean delete(String userId) throws Exception;

    boolean update(AllpaySystem allpaySystem)throws Exception;

    <T> List<T> obtainList(Class<T> clazz,Criterion criterion,Integer currentPage,Integer pageSize)throws Exception;

    <T> T getById(Class<T> clazz, String userId) throws Exception;

    int count(Criterion criterion) throws Exception;

    boolean isExistSysName(AllpaySystemDto allpaySystemDto) throws Exception;
}

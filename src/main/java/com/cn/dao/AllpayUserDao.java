package com.cn.dao;

import com.cn.entity.dto.AllpayUserDto;
import com.cn.entity.po.AllpayUser;
import org.apache.poi.hssf.record.formula.functions.T;
import org.hibernate.criterion.Criterion;

import java.util.List;
import java.util.Map;

/**
 * 用户管理dao层接口
 * Created by WangWenFang on 2016/11/17.
 */
public interface AllpayUserDao {

    boolean add(AllpayUser allpayUser) throws Exception;

    boolean delete(String userId) throws Exception;

    boolean update(AllpayUser allpayUser)throws Exception;

    <T> List<?> obtainList(AllpayUserDto allpayUserDto,Integer currentPage,Integer pageSize)throws Exception;

    <T> T getById(Class<T> clazz, String userId) throws Exception;

    int count(AllpayUserDto allpayUserDto) throws Exception;

    boolean checkNameOrPhone(String field, String value, String userId)throws Exception;

    List<?> getUser(Map<String, Object> source) throws Exception;

    boolean updatePwd(Map<String, Object> source) throws Exception;
}

package com.cn.service;

import com.cn.entity.dto.AllpayUserDto;
import com.cn.entity.po.AllpayUser;
import org.apache.poi.hssf.record.formula.functions.T;
import org.hibernate.criterion.Criterion;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;

/**
 * 用户管理业务层接口
 * Created by WangWenFang on 2016/11/17.
 */
public interface AllpayUserService {

    String add(AllpayUserDto allpayUserDto) throws Exception;

    String delete(AllpayUserDto allpayUserDto) throws Exception;

    String update(AllpayUserDto allpayUserDto, String flag)throws Exception;

    String obtainList(AllpayUserDto allpayUserDto, Class clazz,Integer currentPage,Integer pageSize)throws Exception;

    <T> T getById(Class<T> clazz, String userId) throws Exception;

    String getById(AllpayUserDto allpayUserDto) throws Exception;

    int count(AllpayUserDto allpayUserDto) throws Exception;

    JSONObject bizUpdatePwd(Map<String, Object> source) throws Exception;

}

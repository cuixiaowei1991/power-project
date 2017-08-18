package com.cn.service;

import com.cn.entity.dto.AllpaySystemDto;
import org.hibernate.criterion.Criterion;

/**
 * 系统管理业务层接口
 * Created by WangWenFang on 2016/11/23.
 */
public interface AllpaySystemService {

    String add(AllpaySystemDto allpaySystemDto) throws Exception;

    String delete(AllpaySystemDto allpaySystemDto) throws Exception;

    String update(AllpaySystemDto allpaySystemDto)throws Exception;

    String obtainList(AllpaySystemDto allpaySystemDto, Class clazz,Integer currentPage,Integer pageSize)throws Exception;

    <T> T getById(Class<T> clazz, String userId) throws Exception;

    String getById(AllpaySystemDto allpaySystemDto) throws Exception;

    int count(Criterion criterion) throws Exception;
}

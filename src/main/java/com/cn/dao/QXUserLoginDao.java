package com.cn.dao;

import java.util.List;
import java.util.Map;

/**
 * 登录管理dao层接口
 * Created by WangWenFang on 2017/1/19.
 */
public interface QXUserLoginDao {

    List<?> getUser(Map<String, Object> source) throws Exception;

    boolean updatePwd(Map<String, Object> source) throws Exception;
}

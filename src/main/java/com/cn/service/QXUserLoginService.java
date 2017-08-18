package com.cn.service;

import org.json.JSONObject;

import java.util.List;
import java.util.Map;

/**
 * 登录管理业务层接口
 * Created by WangWenFang on 2017/1/19.
 */
public interface QXUserLoginService {

    JSONObject getUser(Map<String, Object> source) throws Exception;

    JSONObject updatePwd(Map<String, Object> source) throws Exception;
}

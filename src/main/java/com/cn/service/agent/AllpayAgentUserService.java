package com.cn.service.agent;

import com.cn.entity.dto.AllpayUserDto;
import org.json.JSONObject;

import java.util.Map;

/**
 * Created by sun.yayi on 2017/1/11.
 */
public interface AllpayAgentUserService {

    JSONObject insertAgent(AllpayUserDto bean) throws Exception;

    JSONObject checkAgentUserExist(Map<String, Object> source) throws Exception;

    JSONObject getList(AllpayUserDto bean) throws Exception;

    JSONObject insertAgentUser(AllpayUserDto bean) throws Exception;

    JSONObject update(AllpayUserDto bean) throws Exception;

    JSONObject delete(AllpayUserDto bean) throws Exception;

    JSONObject get(AllpayUserDto bean) throws Exception;

    JSONObject agentUpdatePwd(Map<String, Object> source) throws Exception;

    JSONObject agentResetPwd(Map<String, Object> source) throws Exception;
}

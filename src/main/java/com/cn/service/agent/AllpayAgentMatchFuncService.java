package com.cn.service.agent;

import com.cn.entity.dto.AllpayAgentMatchFuncDto;
import org.json.JSONObject;

/**
 * Created by sun.yayi on 2016/12/7.
 */
public interface AllpayAgentMatchFuncService {

    JSONObject insertAgentSetInfo(AllpayAgentMatchFuncDto allpayAgentMatchFuncDto) throws Exception;

    JSONObject getAgentSetInfo(AllpayAgentMatchFuncDto bean) throws Exception;

    JSONObject updateAgentSetInfo(AllpayAgentMatchFuncDto bean) throws Exception;
}

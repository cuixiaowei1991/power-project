package com.cn.dao.agent;

import com.cn.entity.dto.AllpayAgentMatchFuncDto;
import com.cn.entity.po.agent.AllpayAgentMatchFunc;

import java.util.List;
import java.util.Map;

/**
 * Created by sun.yayi on 2016/12/7.
 */
public interface AllpayAgentMatchFuncDao {
    boolean saveOrUpate(AllpayAgentMatchFunc allpayAgentMatchFunc) throws Exception;

    List<Map<String, Object>> getAgentSetInfo(AllpayAgentMatchFuncDto bean) throws Exception;

    List<AllpayAgentMatchFunc> getListByAgentId(String agentId);
}

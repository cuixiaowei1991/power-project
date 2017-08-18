package com.cn.dao.agent;

import com.cn.entity.dto.AllpayUserDto;
import com.cn.entity.po.agent.AllpayAgentUser;

import java.util.List;
import java.util.Map;

/**
 * Created by sun.yayi on 2017/1/11.
 */
public interface AllpayAgentUserDao {
    AllpayAgentUser getAgentUserById(String agentId) throws Exception;

    boolean saveOrUpdate(AllpayAgentUser agentUser) throws Exception;

    List<Map<String,Object>> isExist(String phone) throws Exception;

    List<?> getList(AllpayUserDto bean,Integer currentPage,Integer pageSize) throws Exception;

    int count(AllpayUserDto bean) throws Exception;

    <T> T getById(Class<T> clazz, String userId) throws Exception;

    List<?> getAgentInfo(String agentId);

    List<?> getUser(Map<String, Object> source) throws Exception;

    boolean updatePwd(Map<String, Object> source) throws Exception;

    boolean checkIsExist(String code, String value, String id) throws Exception;

}

package com.cn.dao.agent;

import com.cn.entity.dto.AllpayFunctionDto;
import com.cn.entity.po.agent.AllpayAgentFunction;
import com.cn.entity.po.agent.AllpayAgentUserMatchFunc;
import org.hibernate.criterion.Criterion;

import java.util.List;

/**
 * Created by sun.yayi on 2016/11/30.
 */
public interface AllpayAgentFunctionDao {

    List getMenuFuncInfoList() throws Exception;


    List<?> getAllpayFuncInfoList(AllpayFunctionDto bean, Integer currentPage, Integer pageSize) throws Exception;

   // AllpayMenu getMenuName(String function_top_menuId) throws Exception;

    boolean saveOrUpdate(AllpayAgentFunction function) throws Exception;

    AllpayAgentFunction getAllpayFunctionById(String funcId) throws Exception;

    boolean delete(String funcId) throws Exception;

    int count(AllpayFunctionDto bean) throws Exception;

    List<?> getMenuInfoList(AllpayFunctionDto bean, String flag);

    List<?> getAllMenuInfoList(AllpayFunctionDto bean,String flag);

    List<?> getAgentFuncList(String agentUserId);

    boolean deleteAgentUserInfo(String userId) throws Exception;

    boolean insertAgentUserMatchFunc(AllpayAgentUserMatchFunc agentUserMatchFunc) throws Exception;

    List<?> getAgentQxInfoList(String agentId) throws Exception;

}

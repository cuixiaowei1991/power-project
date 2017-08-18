package com.cn.dao.agent;

import com.cn.entity.dto.AllpayMenuDto;
import com.cn.entity.po.agent.AllpayAgentMenu;
import org.hibernate.criterion.Criterion;

import java.util.List;

/**
 * 菜单管理dao层接口
 * Created by WangWenFang on 2016/11/23.
 */
public interface AllpayAgentMenuDao {

    boolean add(AllpayAgentMenu allpayAgentMenu) throws Exception;

    boolean delete(String agentMenuId) throws Exception;

    boolean update(AllpayAgentMenu allpayAgentMenu)throws Exception;

    List<?> obtainList(AllpayMenuDto allpayMenuDto, Integer currentPage, Integer pageSize)throws Exception;

    AllpayAgentMenu getById(String agentMenuId) throws Exception;

    int count(Criterion criterion) throws Exception;

    boolean isExistMenuName(AllpayMenuDto allpayMenuDto) throws Exception;
}

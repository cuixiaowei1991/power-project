package com.cn.service.agent;

import com.cn.entity.dto.AllpayMenuDto;

/**
 * 菜单管理业务层接口
 * Created by sunyayi on 2016/11/30.
 */
public interface AllpayAgentMenuService {

    String add(AllpayMenuDto allpayMenuDto) throws Exception;

    String delete(AllpayMenuDto allpayMenuDto) throws Exception;

    String update(AllpayMenuDto allpayMenuDto)throws Exception;

    String obtainList(AllpayMenuDto allpayMenuDto, Integer currentPage, Integer pageSize)throws Exception;

    String getById(AllpayMenuDto allpayMenuDto) throws Exception;


}

package com.cn.service.agent;

import com.cn.entity.dto.AllpayFunctionDto;
import org.json.JSONObject;

import java.util.Map;

/**
 * 资源管理业务层接口
 * Created by sun.yayi on 2016/11/30.
 */
public interface AllpayAgentFunctionService {

     /**
      *  2.3.1获取资源管理信息列表
      * @return
      */
     JSONObject getAllpayFuncInfoList(AllpayFunctionDto bean) throws Exception;


     JSONObject insertFuncInfo(AllpayFunctionDto bean) throws Exception;

     JSONObject insertAgentUserInfo(Map<String, Object> source) throws Exception;

     /**
      * 获取功能所属的菜单_顶级菜单名称
      * @param function_top_menuId
      * @param function_sup_menuId
      * @return
      */
     String getSupTopMenuName(String function_top_menuId, String function_sup_menuId) throws Exception;

     /**
      *  2.3.3修改功能资源信息
      * @param bean
      * @return
      */
     JSONObject updateFuncInfo(AllpayFunctionDto bean) throws Exception;

     /**
      * 2.3.4删除功能资源信息
      * @param bean
      * @return
      * @throws Exception
      */
     JSONObject deleteFuncInfo(AllpayFunctionDto bean) throws Exception;

     /**
      * 2.3.5获取功能资源信息
      * @param bean
      * @return
      * @throws Exception
      */
     JSONObject getAllpayFuncInfoById(AllpayFunctionDto bean) throws Exception;

     /**
      *  2.3.6获取所有菜单及功能信息列表（已启用状态）
      * @return
      */
     JSONObject getMenuFuncInfoList() throws Exception;

     JSONObject getMenuInfoList(AllpayFunctionDto bean, String flag) throws Exception;

     JSONObject getAgentFuncList(AllpayFunctionDto bean) throws Exception;

     JSONObject getAgentQxInfoList(Map<String, Object> source) throws Exception;
}

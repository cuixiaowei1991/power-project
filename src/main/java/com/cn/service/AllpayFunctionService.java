package com.cn.service;

import com.cn.entity.dto.AllpayFunctionDto;
import com.cn.entity.po.AllpayFunction;
import com.cn.entity.po.AllpayUser;
import org.hibernate.criterion.Criterion;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;

/**
 * 资源管理业务层接口
 * Created by sun.yayi on 2016/11/22.
 */
public interface AllpayFunctionService {

     /**
      *  2.3.1获取资源管理信息列表
      * @return
      */
     JSONObject getAllpayFuncInfoList(AllpayFunctionDto bean) throws Exception;

     /**
      *  2.3.2新增功能资源信息
      * @param bean
      * @return
      */
     JSONObject insertFuncInfo(AllpayFunctionDto bean) throws Exception;

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

     /**
      * 2.6.1获取业务菜单信息列表
      * 2.6.2获取菜单功能信息列表
      * @param bean
      * @return
      * @throws Exception
      */
     JSONObject getMenuInfoList(AllpayFunctionDto bean, String flag) throws Exception;

}

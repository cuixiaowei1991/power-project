package com.cn.service.shop;

import java.util.Map;

import com.cn.entity.dto.AllpayFunctionDto;

import org.json.JSONObject;

/**
 * 商助资源（功能）管理业务层接口
 * Created by WangWenFang on 2016/11/30.
 */
public interface AllpayShopfunctionService {

    /**
     *  2.2.1获取商助资源管理信息列表
     * @return
     */
    JSONObject getShopFuncInfoList(AllpayFunctionDto bean) throws Exception;

    /**
     *  2.2.2新增商助功能资源信息
     * @param bean
     * @return
     */
    JSONObject insertShopFuncInfo(AllpayFunctionDto bean) throws Exception;

    /**
     * 获取功能所属的菜单_顶级菜单名称
     * @param function_top_menuId
     * @param function_sup_menuId
     * @return
     */
    String getSupTopMenuName(String function_top_menuId, String function_sup_menuId) throws Exception;

    /**
     *  2.2.3修改商助功能资源信息
     * @param bean
     * @return
     */
    JSONObject updateShopFuncInfo(AllpayFunctionDto bean) throws Exception;

    /**
     * 2.2.4删除商助功能资源信息
     * @param bean
     * @return
     * @throws Exception
     */
    JSONObject deleteShopFuncInfo(AllpayFunctionDto bean) throws Exception;

    /**
     * 2.2.5获取商助功能资源信息
     * @param bean
     * @return
     * @throws Exception
     */
    JSONObject getShopFuncInfoById(AllpayFunctionDto bean) throws Exception;

    /**
     *  2.2.6获取商助所有菜单及功能信息列表（已启用状态）
     * @return
     */
    JSONObject getMenuFuncInfoList() throws Exception;
    /****/
    public String insertMerchantMenuAndFunc(Map<String, Object> source)throws Exception;
    /****/
    public String obtainMerchantMenuAndFuncList(Map<String, Object> source)throws Exception;

    JSONObject getMenuInfoList(AllpayFunctionDto bean, String s) throws Exception;

    JSONObject getShopQxInfoList(Map<String, Object> source) throws Exception;

    JSONObject insertRoleInfo(Map<String, Object> source) throws Exception;
  /**
     * 2.4.1获取角色权限信息列表
     * @param source
     * @return
     * @throws Exception
     */
    JSONObject getRoleFuncList(Map<String, Object> source)throws Exception;
}

package com.cn.service.shop;

import java.util.Map;

import com.cn.entity.dto.AllpayUserDto;

import org.json.JSONObject;

/**
 * 商助用户管理业务层接口
 * Created by WangWenFang on 2016/12/15.
 */
public interface AllpayShopuserService {
    
	public String insert(Map<String, Object> source) throws Exception;
	/****/
	public String update(Map<String, Object> source) throws Exception;
	/****/
    JSONObject getAllpayShopuserList(AllpayUserDto bean) throws Exception;
    /****/
    public String delete(Map<String, Object> source)throws Exception;
    /****/
    public String checkUserExist(Map<String, Object> source)throws Exception;

    JSONObject importShopUser(Map<String, Object> source) throws Exception;

    /**
     * 获取用户信息列表（商助系统用）
     * @param bean
     * @return
     * @throws Exception
     */
    public String getShopUserInfoList(AllpayUserDto bean) throws Exception;

    /**
     * 新增用户信息
     * @param source
     * @return
     * @throws Exception
     */
    public String insertShopUserInfo(Map<String, Object> source) throws Exception;

    /**
     * 修改用户信息
     * @param source
     * @return
     * @throws Exception
     */
    public String updateShopUserInfo(Map<String, Object> source) throws Exception;

    /**
     * 删除用户信息
     * @param source
     * @return
     * @throws Exception
     */
    public String deleteShopUser(Map<String, Object> source) throws Exception;

    /**
     * 获取用户信息
     * @param source
     * @return
     * @throws Exception
     */
    public String getShopUserInfoById(Map<String, Object> source) throws Exception;

    String resetPassword(AllpayUserDto bean) throws Exception;

    JSONObject szUpdatePwd(Map<String, Object> source) throws Exception;

    JSONObject getShopIdByWechat(AllpayUserDto allpayUserDto) throws Exception;

    JSONObject userWechatLogin(Map<String, Object> source) throws Exception;

    JSONObject userWechatLoginOut(Map<String, Object> source) throws Exception;
}

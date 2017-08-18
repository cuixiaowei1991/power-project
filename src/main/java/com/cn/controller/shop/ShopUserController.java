package com.cn.controller.shop;

import com.cn.common.LogHelper;
import com.cn.entity.dto.AllpayUserDto;
import com.cn.service.shop.AllpayShopuserService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * 商助管理系统用户管理
 * Created by cuixiaowei on 2017/1/5.
 *
 */
@Controller
@RequestMapping("/shopUser")
public class ShopUserController {

    @Autowired
    private AllpayShopuserService allpayShopuserService;
    /**
     * 2.1.1 获取商助用户信息列表
     * @param bean
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/getList", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    public String getShopUserInfoList(@RequestBody AllpayUserDto bean)
    {
        LogHelper.info("2.1.1 获取商助用户信息列表 请求参数：" + bean.toString());
        String result="";
        try {
            result= allpayShopuserService.getShopUserInfoList(bean);
        } catch (Exception e) {
            LogHelper.error(e, "获取商助用户信息列表信息异常!!!!!", false);
            e.printStackTrace();
        }
        LogHelper.info("获取商助用户信息列表result=" + result);
        return result;
    }

    /**
     * 新增用户信息
     * @param source
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/insert", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    public String insertShopUserInfo(@RequestBody Map<String, Object> source)
    {
        String result="";
        try {
            result= allpayShopuserService.insertShopUserInfo(source);
        } catch (Exception e) {
            LogHelper.error(e, "新增用户信息异常!!!!!", false);
            e.printStackTrace();
        }
        LogHelper.info("新增用户信息result=" + result);
        return result;
    }

    /**
     * 修改用户信息
     * @param source
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/update", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    public String updateShopUserInfo(@RequestBody Map<String, Object> source)
    {
        String result="";
        try {
            result= allpayShopuserService.updateShopUserInfo(source);
        } catch (Exception e) {
            LogHelper.error(e, "修改用户信息异常!!!!!", false);
            e.printStackTrace();
        }
        LogHelper.info("修改用户信息result=" + result);
        return result;
    }
    /**
     * 删除用户信息
     * @param source
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/delete", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    public String deleteShopUser(@RequestBody Map<String, Object> source)
    {
        String result="";
        try {
            result= allpayShopuserService.deleteShopUser(source);
        } catch (Exception e) {
            LogHelper.error(e, "删除用户信息异常!!!!!", false);
            e.printStackTrace();
        }
        LogHelper.info("删除用户信息result=" + result);
        return result;
    }
    /**
     * 2.1.5获取用户信息
     * @param source
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/get", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    public String getShopUserInfoById(@RequestBody Map<String, Object> source)
    {
        String result="";
        try {
            result= allpayShopuserService.getShopUserInfoById(source);
        } catch (Exception e) {
            LogHelper.error(e, "获取用户信息!!!!!", false);
            e.printStackTrace();
        }
        LogHelper.info("获取用户信息result=" + result);
        return result;
    }

    /**
     * 2.3.1修改密码
     * @param source
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/szUpdatePwd", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    public String szUpdatePwd(@RequestBody Map<String, Object> source)
    {
        LogHelper.info("商助2.3.1修改密码result=" + source.toString());
        JSONObject result= new JSONObject();
        try {
            result= allpayShopuserService.szUpdatePwd(source);
        } catch (Exception e) {
            LogHelper.error(e, "商助2.3.1修改密码", false);
            e.printStackTrace();
        }
        return result.toString();
    }

    /**
     * 2.1.6微信商户用户登录
     * @param source
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/userWechatLogin", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    public String userWechatLogin(@RequestBody Map<String, Object> source)
    {
        LogHelper.info("2.1.6微信商户用户登录 source=" + source.toString());
        JSONObject result= new JSONObject();
        try {
            result= allpayShopuserService.userWechatLogin(source);
        } catch (Exception e) {
            LogHelper.error(e, "2.1.6微信商户用户登录", false);
            e.printStackTrace();
        }
        return result.toString();
    }

    /**
     * 2.1.4微信商户用户退出登录（解绑微信三方信息）
     * @param source
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/userWechatLoginOut", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    public String userWechatLoginOut(@RequestBody Map<String, Object> source)
    {
        LogHelper.info("2.1.4微信商户用户退出登录（解绑微信三方信息） source=" + source.toString());
        JSONObject result= new JSONObject();
        try {
            result= allpayShopuserService.userWechatLoginOut(source);
        } catch (Exception e) {
            LogHelper.error(e, "2.1.4微信商户用户退出登录（解绑微信三方信息）", false);
            e.printStackTrace();
        }
        return result.toString();
    }
}

package com.cn.controller.shop;

import java.util.Map;

import com.cn.MsgCode.MsgAndCode;
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

/**
 * 商助用户管理
 * Created by WangWenFang on 2016/12/15.
 */
@Controller
@RequestMapping("/allpayShopuser")
public class AllpayShopuserController {

    @Autowired
    private AllpayShopuserService allpayShopuserService;

    /**
     * 2.4.11获取商助用户信息列表
     * @param bean
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/getList", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    public String getAllpayShopuserList(@RequestBody AllpayUserDto bean){
        LogHelper.info("2.4.11获取商助用户信息列表 请求参数：" + bean.toString());
        JSONObject resultJO = new JSONObject();
        try{
            resultJO=allpayShopuserService.getAllpayShopuserList(bean);
        }catch (Exception e) {
            resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_001);
            resultJO.put(MsgAndCode.RSP_DESC, e.getMessage());
            return resultJO.toString();
        }
        return resultJO.toString();
    }
    /**
     * 业务系统调用的 新增商助用户信息
     * @param source
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/insert",produces="application/json;charset=UTF-8")
    public String insert(@RequestBody Map<String, Object> source){
    	
    	LogHelper.info("新增用户信息 请求参数："+source);
    	String result = null;
        try{
        	result=allpayShopuserService.insert(source);
        }catch (Exception e) {
        	JSONObject resultJO = new JSONObject();
            resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_001);
            resultJO.put(MsgAndCode.RSP_DESC, e.getMessage());
            return resultJO.toString();
        }
        return result;
    }
    
    /**
     * 业务系统调用的 删除商助用户信息
     * @param source
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/delete",produces="application/json;charset=UTF-8")
    public String delete(@RequestBody Map<String, Object> source){
    	
    	LogHelper.info("删除用户信息 请求参数："+source);
    	String result = null;
        try{
        	result=allpayShopuserService.delete(source);
        }catch (Exception e) {
        	JSONObject resultJO = new JSONObject();
            resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_001);
            resultJO.put(MsgAndCode.RSP_DESC, e.getMessage());
            return resultJO.toString();
        }
        return result;
    }
    
    /**
     * 
     * @param source
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/checkUserExist",produces="application/json;charset=UTF-8")
    public String checkUserExist(@RequestBody Map<String, Object> source){
    	
    	LogHelper.info("检查用户是否已经存在 请求参数："+source);
    	String result = null;
        try{
        	result=allpayShopuserService.checkUserExist(source);
        }catch (Exception e) {
        	JSONObject resultJO = new JSONObject();
            resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_001);
            resultJO.put(MsgAndCode.RSP_DESC, e.getMessage());
            return resultJO.toString();
        }
        return result;
    }
    /**
     * 
     * @param source
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/update",produces="application/json;charset=UTF-8")
    public String update(@RequestBody Map<String, Object> source){
    	
    	LogHelper.info("修改用户信息 请求参数："+source);
    	String result = null;
        try{
        	result=allpayShopuserService.update(source);
        }catch (Exception e) {
        	JSONObject resultJO = new JSONObject();
            resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_001);
            resultJO.put(MsgAndCode.RSP_DESC, e.getMessage());
            return resultJO.toString();
        }
        return result;
    }

    /**
     * 2.4.13软pos人员（账号）批量导入信息
     * @param source
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/importShopUser", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    public String importShopUser(@RequestBody Map<String, Object> source){
        LogHelper.info("2.4.13软pos人员（账号）批量导入信息 请求参数：" + source);
        JSONObject resultJO = new JSONObject();
        try{
            resultJO=allpayShopuserService.importShopUser(source);
        }catch (Exception e) {
            resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_001);
            resultJO.put(MsgAndCode.RSP_DESC, e.getMessage());
            e.printStackTrace();
        }
        LogHelper.info("2.4.13软pos人员（账号）批量导入信息 返回结果：" + resultJO.toString());
        return resultJO.toString();
    }

    /**
     * 2.1.2重置商户密码
     * @param allpayUserDto
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/resetPassword", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    public String resetAllpayUserPassword(@RequestBody AllpayUserDto allpayUserDto){
        LogHelper.info("2.1.2重置商户密码==============allpayUserDto="+allpayUserDto.toString());
        String result = "";
        try {
            result = allpayShopuserService.resetPassword(allpayUserDto);
        } catch (Exception e) {
            LogHelper.error(e, "重置商户密码出现异常！", false);
            JSONObject resultJO = new JSONObject();
            resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_001);  //返回的状态码
            resultJO.put(MsgAndCode.RSP_DESC, e.getMessage());  //返回的状态码描述
            return resultJO.toString();
        }
        return result;
    }


    /**
     * 2.2.6根据微信三方id获取商户信息
     * @param allpayUserDto
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getMerchantInfoByWechatId", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    public String getMerchantInfoByWechatId(@RequestBody AllpayUserDto allpayUserDto){
        LogHelper.info("2.2.6根据微信三方id获取商户信息=============allpayUserDto="+allpayUserDto.toString());
        JSONObject resultJO = new JSONObject();
        try {
            resultJO = allpayShopuserService.getShopIdByWechat(allpayUserDto);
        } catch (Exception e) {
            LogHelper.error(e, "2.2.6根据微信三方id获取商户信息", false);
            resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_001);  //返回的状态码
            resultJO.put(MsgAndCode.RSP_DESC, e.getMessage());  //返回的状态码描述
            return resultJO.toString();
        }
        return resultJO.toString();
    }
    
}

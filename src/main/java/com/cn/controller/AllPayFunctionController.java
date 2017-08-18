package com.cn.controller;

import com.cn.MsgCode.MsgAndCode;
import com.cn.common.CommonHelper;
import com.cn.common.LogHelper;
import com.cn.entity.dto.AllpayFunctionDto;
import com.cn.entity.po.AllpayFunction;
import com.cn.service.AllpayFunctionService;
import org.apache.log4j.Logger;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 资源管理
 * Created by sun.yayi on 2016/11/22.
 */
@Controller
@RequestMapping("/allpayFunction")
public class AllPayFunctionController {

    @Autowired
    private AllpayFunctionService allpayFunctionServiceImpl;


    /**
     *  2.3.1获取资源管理信息列表
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/list", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    public String getAllpayFuncInfoList(@RequestBody AllpayFunctionDto bean){
        LogHelper.info("2.3.1获取资源管理信息列表 请求参数："+bean.toString());
        JSONObject resultJO = new JSONObject();
        try{
            resultJO=allpayFunctionServiceImpl.getAllpayFuncInfoList(bean);
        }catch (Exception e) {
            resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_005);
            resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_005);
            e.printStackTrace();
        }
        return resultJO.toString();
    }

    /**
     * 2.3.2新增功能资源信息
     * @param bean
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/insert", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    public String insertFuncInfo(@RequestBody AllpayFunctionDto bean){
        LogHelper.info("2.3.2新增功能资源信息 请求参数："+bean.toString());
        JSONObject resultJO = new JSONObject();
        try{
           resultJO= allpayFunctionServiceImpl.insertFuncInfo(bean);
        }catch (Exception e) {
            resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_005);
            resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_005);
            e.printStackTrace();
        }
        return resultJO.toString();
    }

    /**
     *  2.3.3修改功能资源信息
     * @param bean
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/update", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    public String updateFuncInfo(@RequestBody AllpayFunctionDto bean){
        LogHelper.info(" 2.3.3修改功能资源信息 请求参数："+bean.toString());
        JSONObject resultJO = new JSONObject();
        try{
            resultJO= allpayFunctionServiceImpl.updateFuncInfo(bean);
        }catch (Exception e) {
            resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_005);
            resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_005);
            e.printStackTrace();
        }
        return resultJO.toString();
    }

    /**
     *  2.3.4删除功能资源信息
     * @param bean
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/delete", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    public String deleteFuncInfo(@RequestBody AllpayFunctionDto bean){
        LogHelper.info("2.3.4删除功能资源信息 请求参数："+bean.toString());
        JSONObject resultJO = new JSONObject();
        try{
            resultJO= allpayFunctionServiceImpl.deleteFuncInfo(bean);
        }catch (Exception e) {
            resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_005);
            resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_005);
            e.printStackTrace();
        }
        return resultJO.toString();
    }

    /**
     * 2.3.5获取功能资源信息
     * @param bean
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/get", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    public String getAllpayFuncInfoById(@RequestBody AllpayFunctionDto bean){
        LogHelper.info("2.3.5获取功能资源信息 请求参数："+bean.toString());
        JSONObject resultJO = new JSONObject();
        try{
            resultJO= allpayFunctionServiceImpl.getAllpayFuncInfoById(bean);
        }catch (Exception e) {
            resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_005);
            resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_005);
            e.printStackTrace();
        }
        return resultJO.toString();
    }


    /**
     * 2.3.6获取所有菜单及功能信息列表（已启用状态）
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/getMenuFuncList", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    public String getMenuFuncInfoList(){
        JSONObject resultJO = new JSONObject();
        try{
            resultJO=allpayFunctionServiceImpl.getMenuFuncInfoList();
        } catch (Exception e) {
            resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_005);
            resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_005);
            e.printStackTrace();
        }
        return resultJO.toString();
    }

    /**
     * 2.6.1获取业务菜单信息列表
     * @param bean
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/getMenuInfoList", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    public String getMenuInfoList(@RequestBody AllpayFunctionDto bean){
        LogHelper.info("2.6.1获取业务菜单信息列表 请求参数："+bean.toString());
        JSONObject resultJO = new JSONObject();
        try{
            resultJO= allpayFunctionServiceImpl.getMenuInfoList(bean, "1");
        }catch (Exception e) {
            resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_005);
            resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_005);
            e.printStackTrace();
        }
        return resultJO.toString();
    }

    /**
     * 2.6.2获取菜单功能信息列表
     * @param bean
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/getMenuFuncInfoList", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    public String getMenuFuncInfoList(@RequestBody AllpayFunctionDto bean){
        LogHelper.info("2.6.2获取菜单功能信息列表 请求参数："+bean.toString());
        JSONObject resultJO = new JSONObject();
        try{
            resultJO= allpayFunctionServiceImpl.getMenuInfoList(bean, null);
        }catch (Exception e) {
            resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_005);
            resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_005);
            e.printStackTrace();
        }
        return resultJO.toString();
    }

}

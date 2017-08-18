package com.cn.controller.agent;

import com.cn.MsgCode.MsgAndCode;
import com.cn.common.LogHelper;
import com.cn.entity.dto.AllpayFunctionDto;
import com.cn.entity.dto.AllpayUserDto;
import com.cn.service.agent.AllpayAgentUserService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * Created by sun.yayi on 2017/1/11.
 */
@Controller
@RequestMapping("/allpayAgentUser")
public class AllpayAgentUserController {

    @Autowired
    private AllpayAgentUserService allpayAgentUserServiceImpl;

    /**
     * 新增用户信息
     * @param bean
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/insert", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    public String insert(@RequestBody AllpayUserDto bean){
        LogHelper.info("新增代理商用户信息 请求参数：" + bean.toString());
        JSONObject resultJO = new JSONObject();
        try{
            resultJO=allpayAgentUserServiceImpl.insertAgent(bean);
        }catch (Exception e) {
            resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_005);
            resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_005);
            e.printStackTrace();
        }
        return resultJO.toString();
    }

    /**
     * 用户帐号手机号是否重复
     * @param source
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/checkAgentUserExist", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    public String checkAgentUserExist(@RequestBody Map<String, Object> source){
        LogHelper.info("代理商用户手机号是否重复 请求参数：" + source);
        JSONObject resultJO = new JSONObject();
        try{
            resultJO=allpayAgentUserServiceImpl.checkAgentUserExist(source);
        }catch (Exception e) {
            resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_005);
            resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_005);
            e.printStackTrace();
        }
        return resultJO.toString();
    }

    /**
     * 代理商系統调用的 2.1.1获取代理商用户信息列表
     * @param bean
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/getList", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    public String getList(@RequestBody AllpayUserDto bean){
        LogHelper.info("代理商系統调用的 2.1.1获取代理商用户信息列表 请求参数：" + bean.toString());
        JSONObject resultJO = new JSONObject();
        try{
            resultJO=allpayAgentUserServiceImpl.getList(bean);
        }catch (Exception e) {
            resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_005);
            resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_005);
            e.printStackTrace();
        }
        return resultJO.toString();
    }

    /**
     * 代理商系統调用的 2.1.2新增代理商用户信息
     * @param bean
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/insertAgentUser", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    public String insertAgentUser(@RequestBody AllpayUserDto bean){
        LogHelper.info("代理商系統调用的 2.1.2新增代理商用户信息 请求参数：" + bean.toString());
        JSONObject resultJO = new JSONObject();
        try{
            resultJO=allpayAgentUserServiceImpl.insertAgentUser(bean);
        }catch (Exception e) {
            resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_005);
            resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_005);
            e.printStackTrace();
        }
        return resultJO.toString();
    }

    /**
     * 代理商系統调用的 2.1.3修改商户用户信息
     * @param bean
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/update", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    public String update(@RequestBody AllpayUserDto bean){
        LogHelper.info("代理商系統调用的 2.1.3修改商户用户信息 请求参数：" + bean.toString());
        JSONObject resultJO = new JSONObject();
        try{
            resultJO=allpayAgentUserServiceImpl.update(bean);
        }catch (Exception e) {
            resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_005);
            resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_005);
            e.printStackTrace();
        }
        return resultJO.toString();
    }

    /**
     * 代理商系統调用的 2.1.4删除用户信息
     * @param bean
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/delete", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    public String delete(@RequestBody AllpayUserDto bean){
        LogHelper.info("代理商系統调用的 2.1.4删除用户信息 请求参数：" + bean.toString());
        JSONObject resultJO = new JSONObject();
        try{
            resultJO=allpayAgentUserServiceImpl.delete(bean);
        }catch (Exception e) {
            resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_005);
            resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_005);
            e.printStackTrace();
        }
        return resultJO.toString();
    }

    /**
     * 代理商系統调用的 2.1.5获取商户用户信息
     * @param bean
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/get", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    public String get(@RequestBody AllpayUserDto bean){
        LogHelper.info("代理商系統调用的 2.1.5获取商户用户信息 请求参数：" + bean.toString());
        JSONObject resultJO = new JSONObject();
        try{
            resultJO=allpayAgentUserServiceImpl.get(bean);
        }catch (Exception e) {
            resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_005);
            resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_005);
            e.printStackTrace();
        }
        return resultJO.toString();
    }

    /**
     * 代理商系統调用的 2.3.1修改密码
     * @param source
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/agentUpdatePwd", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    public String agentUpdatePwd(@RequestBody Map<String, Object> source)
    {
        LogHelper.info("代理商系统的2.3.1修改密码source=" + source.toString());
        JSONObject result= new JSONObject();
        try {
            result= allpayAgentUserServiceImpl.agentUpdatePwd(source);
        } catch (Exception e) {
            LogHelper.error(e, "代理商系统的2.3.1修改密码", false);
            e.printStackTrace();
        }
        return result.toString();
    }

    /**
     * 业务系統调用的 重置代理商用户密码 2.3.2重置密码
     * @param source
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/agentResetPwd", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    public String agentResetPwd(@RequestBody Map<String, Object> source)
    {
        LogHelper.info("业务系統调用的 重置代理商用户密码 2.3.2重置密码  source=" + source.toString());
        JSONObject result= new JSONObject();
        try {
            result= allpayAgentUserServiceImpl.agentResetPwd(source);
        } catch (Exception e) {
            LogHelper.error(e, "重置代理商用户密码", false);
            e.printStackTrace();
        }
        return result.toString();
    }

}

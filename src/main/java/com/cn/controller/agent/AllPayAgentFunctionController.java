package com.cn.controller.agent;

import com.cn.MsgCode.MsgAndCode;
import com.cn.common.LogHelper;
import com.cn.entity.dto.AllpayFunctionDto;
import com.cn.service.agent.AllpayAgentFunctionService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * 资源管理
 * Created by sun.yayi on 2016/11/30.
 */
@Controller
@RequestMapping("/allpayAgentFunction")
public class AllPayAgentFunctionController {

    @Autowired
    private AllpayAgentFunctionService allpayAgentFunctionServiceImpl;


    /**
     *  2.2.1获取资源管理信息列表
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/list", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    public String getAllpayFuncInfoList(@RequestBody AllpayFunctionDto bean){
        LogHelper.info("2.2.1获取资源管理信息列表 请求参数："+bean.toString());
        JSONObject resultJO = new JSONObject();
        try{
            resultJO=allpayAgentFunctionServiceImpl.getAllpayFuncInfoList(bean);
        }catch (Exception e) {
            resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_005);
            resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_005);
            e.printStackTrace();
        }
        return resultJO.toString();
    }

    /**
     * 2.2.2新增功能资源信息
     * @param bean
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/insert", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    public String insertFuncInfo(@RequestBody AllpayFunctionDto bean){
        LogHelper.info("2.2.2新增功能资源信息 请求参数："+bean.toString());
        JSONObject resultJO = new JSONObject();
        try{
           resultJO= allpayAgentFunctionServiceImpl.insertFuncInfo(bean);
        }catch (Exception e) {
            resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_005);
            resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_005);
            e.printStackTrace();
        }
        return resultJO.toString();
    }

    /**
     *  2.2.3修改功能资源信息
     * @param bean
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/update", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    public String updateFuncInfo(@RequestBody AllpayFunctionDto bean){
        LogHelper.info(" 2.2.3修改功能资源信息 请求参数："+bean.toString());
        JSONObject resultJO = new JSONObject();
        try{
            resultJO= allpayAgentFunctionServiceImpl.updateFuncInfo(bean);
        }catch (Exception e) {
            resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_005);
            resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_005);
            e.printStackTrace();
        }
        return resultJO.toString();
    }

    /**
     *  2.2.4删除功能资源信息
     * @param bean
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/delete", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    public String deleteFuncInfo(@RequestBody AllpayFunctionDto bean){
        LogHelper.info("2.2.4删除功能资源信息 请求参数："+bean.toString());
        JSONObject resultJO = new JSONObject();
        try{
            resultJO= allpayAgentFunctionServiceImpl.deleteFuncInfo(bean);
        }catch (Exception e) {
            resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_005);
            resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_005);
            e.printStackTrace();
        }
        return resultJO.toString();
    }

    /**
     * 2.2.5获取功能资源信息
     * @param bean
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/get", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    public String getAllpayFuncInfoById(@RequestBody AllpayFunctionDto bean){
        LogHelper.info("2.2.5获取功能资源信息 请求参数："+bean.toString());
        JSONObject resultJO = new JSONObject();
        try{
            resultJO= allpayAgentFunctionServiceImpl.getAllpayFuncInfoById(bean);
        }catch (Exception e) {
            resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_005);
            resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_005);
            e.printStackTrace();
        }
        return resultJO.toString();
    }

    /**
     * 2.5.8获取代理商所有菜单及功能信息列表（已启用状态）
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/getMenuFuncInfoList", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    public String getMenuFuncInfoList(){
        JSONObject resultJO = new JSONObject();
        try{
            resultJO=allpayAgentFunctionServiceImpl.getMenuFuncInfoList();
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
        LogHelper.info("2.6.1获取代理商业务菜单信息列表 请求参数："+bean.toString());
        JSONObject resultJO = new JSONObject();
        try{
            resultJO= allpayAgentFunctionServiceImpl.getMenuInfoList(bean, "1");
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
    @RequestMapping(value="/getUserMenuFuncInfoList", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    public String getUserMenuFuncInfoList(@RequestBody AllpayFunctionDto bean){
        LogHelper.info("2.6.2获取代理商菜单功能信息列表 请求参数："+bean.toString());
        JSONObject resultJO = new JSONObject();
        try{
            resultJO= allpayAgentFunctionServiceImpl.getMenuInfoList(bean, null);
        }catch (Exception e) {
            resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_005);
            resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_005);
            e.printStackTrace();
        }
        return resultJO.toString();
    }

    /**
     * 2.4.1获取用户权限信息列表
     * @param bean
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/getAgentFuncList", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    public String getAgentFuncList(@RequestBody AllpayFunctionDto bean){
        LogHelper.info("2.4.1获取用户权限信息列表 请求参数："+bean.toString());
        JSONObject resultJO = new JSONObject();
        try{
            resultJO= allpayAgentFunctionServiceImpl.getAgentFuncList(bean);
        }catch (Exception e) {
            resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_005);
            resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_005);
            e.printStackTrace();
        }
        return resultJO.toString();
    }

    /**
     *  2.4.2新增用户权限信息
     * @param source
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/insertAgentUserInfo", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    public String insertAgentUserInfo(@RequestBody Map<String, Object> source){
        LogHelper.info(" 2.4.2新增用户权限信息 请求参数："+source.toString());
        JSONObject resultJO = new JSONObject();
        try{
            resultJO= allpayAgentFunctionServiceImpl.insertAgentUserInfo(source);
        }catch (Exception e) {
            resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_005);
            resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_005);
            e.printStackTrace();
        }
        return resultJO.toString();
    }

    /**
     *  2.4.3获取代理商权限信息列表
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/getAgentQxInfoList", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    public String getAgentQxInfoList(@RequestBody Map<String, Object> source){
        LogHelper.info(" 2.4.3获取代理商权限信息列表 请求参数："+source.toString());
        JSONObject resultJO = new JSONObject(source);
        try{
            resultJO=allpayAgentFunctionServiceImpl.getAgentQxInfoList(source);
        } catch (Exception e) {
            resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_005);
            resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_005);
            e.printStackTrace();
        }
        return resultJO.toString();
    }
}

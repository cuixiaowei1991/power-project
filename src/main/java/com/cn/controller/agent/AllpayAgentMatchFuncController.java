package com.cn.controller.agent;

import com.cn.MsgCode.MsgAndCode;
import com.cn.common.LogHelper;
import com.cn.entity.dto.AllpayAgentMatchFuncDto;
import com.cn.entity.dto.AllpayFunctionDto;
import com.cn.entity.po.agent.AllpayAgentMatchFunc;
import com.cn.service.agent.AllpayAgentFunctionService;
import com.cn.service.agent.AllpayAgentMatchFuncService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 资源管理
 * Created by sun.yayi on 2016/12/7.
 */
@Controller
@RequestMapping("/agentMatchFuncController")
public class AllpayAgentMatchFuncController {

    @Autowired
    private AllpayAgentMatchFuncService allpayAgentMatchFuncServiceImpl;

    /**
     * 2.5.5新增代理商设置信息
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/insertAgentSetInfo", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    public String insertAgentSetInfo(@RequestBody AllpayAgentMatchFuncDto bean){
        LogHelper.info("2.5.5新增代理商设置信息 请求参数："+bean.toString());
        JSONObject resultJO = new JSONObject();
        try{
            resultJO=allpayAgentMatchFuncServiceImpl.insertAgentSetInfo(bean);
        } catch (Exception e) {
            resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_005);
            resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_005);
            e.printStackTrace();
        }
        return resultJO.toString();
    }

    /**
     * 2.5.6获取代理商设置信息
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/getAgentSetInfo", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    public String getAgentSetInfo(@RequestBody AllpayAgentMatchFuncDto bean){
        LogHelper.info("2.5.6获取代理商设置信息 请求参数："+bean.toString());
        JSONObject resultJO = new JSONObject();
        try{
            resultJO=allpayAgentMatchFuncServiceImpl.getAgentSetInfo(bean);
        } catch (Exception e) {
            resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_005);
            resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_005);
            e.printStackTrace();
        }
        return resultJO.toString();
    }

    /**
     * 修改代理商设置信息
     * @param bean
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/updateAgentSetInfo", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    public String updateAgentSetInfo(@RequestBody AllpayAgentMatchFuncDto bean){
        LogHelper.info("修改代理商设置信息 请求参数："+bean.toString());
        JSONObject resultJO = new JSONObject();
        try{
            resultJO=allpayAgentMatchFuncServiceImpl.updateAgentSetInfo(bean);
        } catch (Exception e) {
            resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_005);
            resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_005);
            e.printStackTrace();
        }
        return resultJO.toString();
    }

}

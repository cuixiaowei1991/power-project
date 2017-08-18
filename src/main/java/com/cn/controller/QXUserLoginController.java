package com.cn.controller;

import com.cn.MsgCode.MsgAndCode;
import com.cn.common.CommonHelper;
import com.cn.common.LogHelper;
import com.cn.entity.po.AllpaySuperUser;
import com.cn.service.QXUserLoginService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * 登录管理
 * Created by WangWenFang on 2017/1/19.
 */
@Controller
@RequestMapping("/userLogin")
public class QXUserLoginController {

    @Autowired
    private QXUserLoginService loginService;

    /**
     * 2.7.1登录
     * @param source
     * @return
     */
    @RequestMapping(value="/login",method= RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseBody
    public String login(@RequestBody Map<String, Object> source){
        LogHelper.info("2.7.1登录==========请求参数=" + source);
        JSONObject result = null;
        try {
            result = loginService.getUser(source);
        } catch (Exception e) {
            LogHelper.error(e, "登录出现异常！", false);
            JSONObject resultJO = new JSONObject();
            resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_001);  //返回的状态码
            resultJO.put(MsgAndCode.RSP_DESC, e.getMessage());  //返回的状态码描述
            return resultJO.toString();
        }
        return result.toString();
    }

    /**
     * 2.7.2修改密码
     * @param source
     * @return
     */
    @RequestMapping(value="/updatePwd",method= RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseBody
    public String updatePwd(@RequestBody Map<String, Object> source){
        LogHelper.info("2.7.2修改密码=========请求参数=" + source);
        JSONObject result = null;
        try {
            result = loginService.updatePwd(source);
        } catch (Exception e) {
            LogHelper.error(e, "修改密码出现异常！", false);
            JSONObject resultJO = new JSONObject();
            resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_001);  //返回的状态码
            resultJO.put(MsgAndCode.RSP_DESC, e.getMessage());  //返回的状态码描述
            return resultJO.toString();
        }
        return result.toString();
    }

}

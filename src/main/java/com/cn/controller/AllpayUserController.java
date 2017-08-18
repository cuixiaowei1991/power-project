package com.cn.controller;

import com.cn.MsgCode.MsgAndCode;
import com.cn.common.LogHelper;
import com.cn.entity.dto.AllpayUserDto;
import com.cn.entity.po.AllpayUser;
import com.cn.service.AllpayUserService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 用户管理
 * Created by WangWenFang on 2016/11/17.
 */
@Controller
@RequestMapping("/allpayUser")
public class AllpayUserController {

    @Autowired
    private AllpayUserService allpayUserService;

    /**
     * 2.1.1获取用户信息列表
     * @param allpayUserDto
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/getList", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    public String getAllpayUserInfoList(@RequestBody AllpayUserDto allpayUserDto){
        LogHelper.info("2.1.1获取用户信息列表=============allpayUserDto.getCurragePage()=" + allpayUserDto.getCurragePage());
        String result = "";
        try {
            result = allpayUserService.obtainList(allpayUserDto, AllpayUser.class, allpayUserDto.getCurragePage()-1, allpayUserDto.getPageSize());
        } catch (Exception e) {
            LogHelper.error(e, "获取用户信息列表出现异常！", false);
            JSONObject resultJO = new JSONObject();
            resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_001);  //返回的状态码
            resultJO.put(MsgAndCode.RSP_DESC, e.getMessage());  //返回的状态码描述
            return resultJO.toString();
        }
        return result;
    }

    /**
     * 2.1.2新增用户信息
     * @param allpayUserDto
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/insert", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    public String insertAllpayUserInfo(@RequestBody AllpayUserDto allpayUserDto){
        LogHelper.info("2.1.2新增用户信息==============allpayUserDto.getUserName()="+allpayUserDto.getUserName());
        String result = "";
        try {
            result = allpayUserService.add(allpayUserDto);
        } catch (Exception e) {
            LogHelper.error(e, "新增用户信息出现异常！", false);
            JSONObject resultJO = new JSONObject();
            resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_001);  //返回的状态码
            resultJO.put(MsgAndCode.RSP_DESC, e.getMessage());  //返回的状态码描述
            return resultJO.toString();
        }
        return result;
    }

    /**
     * 2.1.6获取用户信息
     * @param allpayUserDto
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/get", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    public String getAllpayUserInfoById(@RequestBody AllpayUserDto allpayUserDto){
        LogHelper.info("2.1.6获取用户信息============allpayUserDto.getUserId()="+allpayUserDto.getUserId());
        String result = "";
        try {
            result = allpayUserService.getById(allpayUserDto);
        } catch (Exception e) {
            LogHelper.error(e, "获取用户信息出现异常！", false);
            JSONObject resultJO = new JSONObject();
            resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_001);  //返回的状态码
            resultJO.put(MsgAndCode.RSP_DESC, e.getMessage());  //返回的状态码描述
            return resultJO.toString();
        }
        return result;
    }

    /**
     * 2.1.3修改用户信息
     * @param allpayUserDto
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/update", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    public String updateAllpayUserInfo(@RequestBody AllpayUserDto allpayUserDto){
        LogHelper.info("2.1.3修改用户信息==============allpayUserDto.getUserName()="+allpayUserDto.getUserName());
        String result = "";
        try {
            result = allpayUserService.update(allpayUserDto, "update");
        } catch (Exception e) {
            LogHelper.error(e, "修改用户信息出现异常！", false);
            JSONObject resultJO = new JSONObject();
            resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_001);  //返回的状态码
            resultJO.put(MsgAndCode.RSP_DESC, e.getMessage());  //返回的状态码描述
            return resultJO.toString();
        }
        return result;
    }

    /**
     * 2.1.5删除用户信息
     * @param allpayUserDto
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/delete", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    public String deleteAllpayUser(@RequestBody AllpayUserDto allpayUserDto){
        LogHelper.info("2.1.5删除用户信息==============allpayUserDto.getUserId()=" + allpayUserDto.getUserId());
        String result = "";
        try {
            result = allpayUserService.delete(allpayUserDto);
        } catch (Exception e) {
            LogHelper.error(e, "删除用户信息出现异常！", false);
            JSONObject resultJO = new JSONObject();
            resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_001);  //返回的状态码
            resultJO.put(MsgAndCode.RSP_DESC, e.getMessage());  //返回的状态码描述
            return resultJO.toString();
        }
        return result;
    }

    /**
     * 2.1.4重置用户密码
     * @param allpayUserDto
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/resetPassword", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    public String resetAllpayUserPassword(@RequestBody AllpayUserDto allpayUserDto){
        LogHelper.info("2.1.4重置用户密码==============allpayUserDto.getUserId()="+allpayUserDto.getUserId());
        String result = "";
        try {
            result = allpayUserService.update(allpayUserDto, "resetPassword");
        } catch (Exception e) {
            LogHelper.error(e, "重置用户密码出现异常！", false);
            JSONObject resultJO = new JSONObject();
            resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_001);  //返回的状态码
            resultJO.put(MsgAndCode.RSP_DESC, e.getMessage());  //返回的状态码描述
            return resultJO.toString();
        }
        return result;
    }

    /**
     * 2.8.1修改密码
     * @param source
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/bizUpdatePwd", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    public String bizUpdatePwd(@RequestBody Map<String, Object> source)
    {
        LogHelper.info("业务2.8.1修改密码source=" + source.toString());
        JSONObject result= new JSONObject();
        try {
            result= allpayUserService.bizUpdatePwd(source);
        } catch (Exception e) {
            LogHelper.error(e, "业务2.8.1修改密码", false);
            e.printStackTrace();
        }
        return result.toString();
    }

}

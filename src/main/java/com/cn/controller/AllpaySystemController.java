package com.cn.controller;

import com.cn.MsgCode.MsgAndCode;
import com.cn.common.LogHelper;
import com.cn.entity.dto.AllpaySystemDto;
import com.cn.entity.po.AllpaySystem;
import com.cn.service.AllpaySystemService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 系统管理
 * Created by WangWenFang on 2016/11/23.
 */
@Controller
@RequestMapping("/allpaySystem")
public class AllpaySystemController {

    @Autowired
    private AllpaySystemService allpaySystemService;

    /**
     * 2.5.1获取系统信息列表
     * @param allpaySystemDto
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getList", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    public String getSystemInfoList(@RequestBody AllpaySystemDto allpaySystemDto){
        LogHelper.info("2.5.1获取系统信息列表==============");
        String result = "";
        try {
            result = allpaySystemService.obtainList(allpaySystemDto, AllpaySystem.class, null, null);
        } catch (Exception e) {
            LogHelper.error(e, "获取系统信息列表出现异常！", false);
            JSONObject resultJO = new JSONObject();
            resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_001);  //返回的状态码
            resultJO.put(MsgAndCode.RSP_DESC, e.getMessage());  //返回的状态码描述
            return resultJO.toString();
        }
        return result;
    }

    /**
     * 2.5.2新增系统信息
     * @param allpaySystemDto
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/insert", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    public String insertSystemInfo(@RequestBody AllpaySystemDto allpaySystemDto){
        LogHelper.info("2.5.2新增系统信息==============allpaySystemDto.getSystemName()="+allpaySystemDto.getSystemName());
        String result = "";
        try {
            result = allpaySystemService.add(allpaySystemDto);
        } catch (Exception e) {
            LogHelper.error(e, "新增系统信息出现异常！", false);
            JSONObject resultJO = new JSONObject();
            resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_001);  //返回的状态码
            resultJO.put(MsgAndCode.RSP_DESC, e.getMessage());  //返回的状态码描述
            return resultJO.toString();
        }
        return result;
    }

    /**
     * 2.5.3修改系统信息
     * @param allpaySystemDto
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/update", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    public String updateSystemInfo(@RequestBody AllpaySystemDto allpaySystemDto){
        LogHelper.info("2.5.3修改系统信息==============allpaySystemDto.getSystemName()="+allpaySystemDto.getSystemName());
        String result = "";
        try {
            result = allpaySystemService.update(allpaySystemDto);
        } catch (Exception e) {
            LogHelper.error(e, "修改系统信息出现异常！", false);
            JSONObject resultJO = new JSONObject();
            resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_001);  //返回的状态码
            resultJO.put(MsgAndCode.RSP_DESC, e.getMessage());  //返回的状态码描述
            return resultJO.toString();
        }
        return result;
    }

    /**
     * 2.5.4删除系统信息
     * @param allpaySystemDto
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/delete", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    public String deleteSystemInfo(@RequestBody AllpaySystemDto allpaySystemDto){
        LogHelper.info("2.5.4删除系统信息==============allpaySystemDto.getSystemId()=" + allpaySystemDto.getSystemId());
        String result = "";
        try {
            result = allpaySystemService.delete(allpaySystemDto);
        } catch (Exception e) {
            LogHelper.error(e, "删除系统信息出现异常！", false);
            JSONObject resultJO = new JSONObject();
            resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_001);  //返回的状态码
            resultJO.put(MsgAndCode.RSP_DESC, e.getMessage());  //返回的状态码描述
            return resultJO.toString();
        }
        return result;
    }

    /**
     * 2.5.5获取系统信息
     * @param allpaySystemDto
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/get", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    public String getSystemInfoById(@RequestBody AllpaySystemDto allpaySystemDto){
        LogHelper.info("2.5.5获取系统信息============allpaySystemDto.getSystemId()="+allpaySystemDto.getSystemId());
        String result = "";
        try {
            result = allpaySystemService.getById(allpaySystemDto);
        } catch (Exception e) {
            LogHelper.error(e, "获取系统信息出现异常！", false);
            JSONObject resultJO = new JSONObject();
            resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_001);  //返回的状态码
            resultJO.put(MsgAndCode.RSP_DESC, e.getMessage());  //返回的状态码描述
            return resultJO.toString();
        }
        return result;
    }
}

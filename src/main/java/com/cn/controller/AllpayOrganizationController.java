package com.cn.controller;

import com.cn.MsgCode.MsgAndCode;
import com.cn.common.LogHelper;
import com.cn.entity.dto.AllpayOrganizationDto;
import com.cn.entity.po.AllpayOrganization;
import com.cn.service.AllpayOrganizationService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 组织机构管理
 * Created by WangWenFang on 2016/11/23.
 */
@Controller
@RequestMapping("/allpayOrganization")
public class AllpayOrganizationController {

    @Autowired
    private AllpayOrganizationService organizationService;

    /**
     * 2.4.1获取组织机构信息列表
     * @param organizationDto
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getList", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    public String getOrganizationInfoList(@RequestBody AllpayOrganizationDto organizationDto){
        LogHelper.info("2.4.1获取组织机构信息列表=============请求参数=" + organizationDto.toString());
        String result = "";
        try {
            result = organizationService.obtainList(organizationDto, AllpayOrganization.class, Integer.parseInt(organizationDto.getCurragePage())-1, Integer.parseInt(organizationDto.getPageSize()));
        } catch (Exception e) {
            LogHelper.error(e, "获取组织机构信息列表出现异常！", false);
            JSONObject resultJO = new JSONObject();
            resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_001);  //返回的状态码
            resultJO.put(MsgAndCode.RSP_DESC, e.getMessage());  //返回的状态码描述
            return resultJO.toString();
        }
        return result;
    }

    /**
     * 2.4.2新增组织机构信息
     * @param organizationDto
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/insert", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    public String insertOrganizationInfo(@RequestBody AllpayOrganizationDto organizationDto){
        LogHelper.info("2.4.2新增组织机构信息==============organizationDto.getOrganizationName()="+organizationDto.getOrganizationName());
        String result = "";
        try {
            result = organizationService.add(organizationDto);
        } catch (Exception e) {
            LogHelper.error(e, "新增组织机构信息出现异常！", false);
            JSONObject resultJO = new JSONObject();
            resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_001);  //返回的状态码
            resultJO.put(MsgAndCode.RSP_DESC, e.getMessage());  //返回的状态码描述
            return resultJO.toString();
        }
        return result;
    }

    /**
     * 2.4.3修改组织机构信息
     * @param organizationDto
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/update", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    public String updateOrganizationInfo(@RequestBody AllpayOrganizationDto organizationDto){
        LogHelper.info("2.4.3修改组织机构信息==============organizationDto.getOrganizationId()="+organizationDto.getOrganizationId());
        String result = "";
        try {
            result = organizationService.update(organizationDto);
        } catch (Exception e) {
            LogHelper.error(e, "修改组织机构信息出现异常！", false);
            JSONObject resultJO = new JSONObject();
            resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_001);  //返回的状态码
            resultJO.put(MsgAndCode.RSP_DESC, e.getMessage());  //返回的状态码描述
            return resultJO.toString();
        }
        return result;
    }

    /**
     * 2.4.4删除组织机构信息
     * @param organizationDto
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/delete", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    public String deleteOrganizationInfo(@RequestBody AllpayOrganizationDto organizationDto){
        LogHelper.info("2.4.4删除组织机构信息==============organizationDto.getOrganizationId()=" + organizationDto.getOrganizationId());
        String result = "";
        try {
            result = organizationService.delete(organizationDto);
        } catch (Exception e) {
            LogHelper.error(e, "删除组织机构信息出现异常！", false);
            JSONObject resultJO = new JSONObject();
            resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_001);  //返回的状态码
            resultJO.put(MsgAndCode.RSP_DESC, e.getMessage());  //返回的状态码描述
            return resultJO.toString();
        }
        return result;
    }

    /**
     * 2.4.5获取组织机构信息
     * @param organizationDto
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/get", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    public String getOrganizationInfoById(@RequestBody AllpayOrganizationDto organizationDto){
        LogHelper.info("2.4.5获取组织机构信息============organizationDto.getOrganizationId()="+organizationDto.getOrganizationId());
        String result = "";
        try {
            result = organizationService.getById(organizationDto);
        } catch (Exception e) {
            LogHelper.error(e, "获取组织机构信息出现异常！", false);
            JSONObject resultJO = new JSONObject();
            resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_001);  //返回的状态码
            resultJO.put(MsgAndCode.RSP_DESC, e.getMessage());  //返回的状态码描述
            return resultJO.toString();
        }
        return result;
    }
}

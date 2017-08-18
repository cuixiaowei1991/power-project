package com.cn.controller;

import com.cn.MsgCode.MsgAndCode;
import com.cn.common.LogHelper;
import com.cn.entity.dto.AllpayMenuDto;
import com.cn.entity.po.AllpayMenu;
import com.cn.service.AllpayMenuService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 菜单管理
 * Created by WangWenFang on 2016/11/23.
 */
@Controller
@RequestMapping("/allpayMenu")
public class AllpayMenuController {

    @Autowired
    private AllpayMenuService allpayMenuService;

    /**
     * 2.5.6获取菜单信息列表
     * @param allpayMenuDto
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getList", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    public String getMenuInfoList(@RequestBody AllpayMenuDto allpayMenuDto){
        LogHelper.info("2.5.1获取菜单信息列表=============allpayMenuDto.getMenuLevel()="+allpayMenuDto.getMenuLevel());
        String result = "";
        try {
            result = allpayMenuService.obtainList(allpayMenuDto, null, null);
        } catch (Exception e) {
            LogHelper.error(e, "获取菜单信息列表出现异常！", false);
            JSONObject resultJO = new JSONObject();
            resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_001);  //返回的状态码
            resultJO.put(MsgAndCode.RSP_DESC, e.getMessage());  //返回的状态码描述
            return resultJO.toString();
        }
        return result;
    }

    /**
     * 2.5.7新增菜单信息
     * @param allpayMenuDto
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/insert", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    public String insertMenuInfo(@RequestBody AllpayMenuDto allpayMenuDto){
        LogHelper.info("2.5.7新增菜单信息==============allpayMenuDto.getMenuSuperiorId()="+allpayMenuDto.getMenuSuperiorId());
        String result = "";
        try {
            result = allpayMenuService.add(allpayMenuDto);
        } catch (Exception e) {
            LogHelper.error(e, "新增菜单信息出现异常！", false);
            JSONObject resultJO = new JSONObject();
            resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_001);  //返回的状态码
            resultJO.put(MsgAndCode.RSP_DESC, e.getMessage());  //返回的状态码描述
            return resultJO.toString();
        }
        return result;
    }

    /**
     * 2.5.8修改菜单信息
     * @param allpayMenuDto
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/update", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    public String updateMenuInfo(@RequestBody AllpayMenuDto allpayMenuDto){
        LogHelper.info("2.5.8修改菜单信息==============allpayMenuDto.getMenuName()="+allpayMenuDto.getMenuName());
        String result = "";
        try {
            result = allpayMenuService.update(allpayMenuDto);
        } catch (Exception e) {
            LogHelper.error(e, "修改菜单信息出现异常！", false);
            JSONObject resultJO = new JSONObject();
            resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_001);  //返回的状态码
            resultJO.put(MsgAndCode.RSP_DESC, e.getMessage());  //返回的状态码描述
            return resultJO.toString();
        }
        return result;
    }

    /**
     * 2.5.9获取菜单信息
     * @param allpayMenuDto
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/get", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    public String getMenuInfoById(@RequestBody AllpayMenuDto allpayMenuDto){
        LogHelper.info("2.5.9获取菜单信息==============allpayMenuDto.getMenuId()=" + allpayMenuDto.getMenuId());
        String result = "";
        try {
            result = allpayMenuService.getById(allpayMenuDto);
        } catch (Exception e) {
            LogHelper.error(e, "获取菜单信息出现异常！", false);
            JSONObject resultJO = new JSONObject();
            resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_001);  //返回的状态码
            resultJO.put(MsgAndCode.RSP_DESC, e.getMessage());  //返回的状态码描述
            return resultJO.toString();
        }
        return result;
    }

    /**
     * 2.5.10删除菜单信息
     * @param allpayMenuDto
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/delete", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    public String deleteMenuInfo(@RequestBody AllpayMenuDto allpayMenuDto){
        LogHelper.info("2.5.10删除菜单信息============allpayMenuDto.getMenuId()="+allpayMenuDto.getMenuId());
        String result = "";
        try {
            result = allpayMenuService.delete(allpayMenuDto);
        } catch (Exception e) {
            LogHelper.error(e, "删除菜单信息出现异常！", false);
            JSONObject resultJO = new JSONObject();
            resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_001);  //返回的状态码
            resultJO.put(MsgAndCode.RSP_DESC, e.getMessage());  //返回的状态码描述
            return resultJO.toString();
        }
        return result;
    }

}

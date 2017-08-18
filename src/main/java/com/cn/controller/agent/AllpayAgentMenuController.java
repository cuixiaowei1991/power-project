package com.cn.controller.agent;

import com.cn.MsgCode.MsgAndCode;
import com.cn.common.LogHelper;
import com.cn.entity.dto.AllpayMenuDto;
import com.cn.entity.po.AllpayMenu;
import com.cn.service.AllpayMenuService;
import com.cn.service.agent.AllpayAgentMenuService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *  代理商 菜单管理
 *  Created by sun.yayi on 2016/11/30.
 */
@Controller
@RequestMapping("/allpayAgentMenu")
public class AllpayAgentMenuController {

    @Autowired
    private AllpayAgentMenuService allpayAgentMenuServiceImpl;

    /**
     * 2.1.1获取代理商菜单信息列表
     * @param allpayMenuDto
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getList", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    public String getMenuInfoList(@RequestBody AllpayMenuDto allpayMenuDto){
        LogHelper.info("2.1.1获取代理商菜单信息列表 请求参数="+allpayMenuDto.toString());
        String result = "";
        try {
            result = allpayAgentMenuServiceImpl.obtainList(allpayMenuDto, null, null);
        } catch (Exception e) {
            LogHelper.error(e, "获取代理商菜单信息列表出现异常！", false);
            JSONObject resultJO = new JSONObject();
            resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_001);  //返回的状态码
            resultJO.put(MsgAndCode.RSP_DESC, e.getMessage());  //返回的状态码描述
            return resultJO.toString();
        }
        return result;
    }

    /**
     * 2.1.2新增代理商菜单信息列表
     * @param allpayMenuDto
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/insert", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    public String insertMenuInfo(@RequestBody AllpayMenuDto allpayMenuDto){
        LogHelper.info("2.1.2新增代理商菜单信息列表 请求参数="+allpayMenuDto.toString());
        String result = "";
        try {
            result = allpayAgentMenuServiceImpl.add(allpayMenuDto);
        } catch (Exception e) {
            LogHelper.error(e, "新增代理商菜单信息出现异常！", false);
            JSONObject resultJO = new JSONObject();
            resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_001);  //返回的状态码
            resultJO.put(MsgAndCode.RSP_DESC, e.getMessage());  //返回的状态码描述
            return resultJO.toString();
        }
        return result;
    }

    /**
     * 2.1.3修改代理商菜单信息列表
     * @param allpayMenuDto
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/update", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    public String updateMenuInfo(@RequestBody AllpayMenuDto allpayMenuDto){
        LogHelper.info("2.1.3修改代理商菜单信息列表 请求参数="+allpayMenuDto.toString());
        String result = "";
        try {
            result = allpayAgentMenuServiceImpl.update(allpayMenuDto);
        } catch (Exception e) {
            LogHelper.error(e, "修改代理商菜单信息出现异常！", false);
            JSONObject resultJO = new JSONObject();
            resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_001);  //返回的状态码
            resultJO.put(MsgAndCode.RSP_DESC, e.getMessage());  //返回的状态码描述
            return resultJO.toString();
        }
        return result;
    }

    /**
     * 2.1.4获取代理商菜单信息
     * @param allpayMenuDto
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/get", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    public String getMenuInfoById(@RequestBody AllpayMenuDto allpayMenuDto){
        LogHelper.info("2.1.4获取代理商菜单信息 请求参数="+allpayMenuDto.toString());
        String result = "";
        try {
            result = allpayAgentMenuServiceImpl.getById(allpayMenuDto);
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
     * 2.1.5删除代理商菜单信息
     * @param allpayMenuDto
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/delete", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    public String deleteMenuInfo(@RequestBody AllpayMenuDto allpayMenuDto){
        LogHelper.info("2.1.5删除代理商菜单信息 请求参数="+allpayMenuDto.toString());
        String result = "";
        try {
            result = allpayAgentMenuServiceImpl.delete(allpayMenuDto);
        } catch (Exception e) {
            LogHelper.error(e, "删除代理商菜单信息出现异常！", false);
            JSONObject resultJO = new JSONObject();
            resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_001);  //返回的状态码
            resultJO.put(MsgAndCode.RSP_DESC, e.getMessage());  //返回的状态码描述
            return resultJO.toString();
        }
        return result;
    }

}

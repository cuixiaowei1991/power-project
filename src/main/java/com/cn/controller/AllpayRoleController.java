package com.cn.controller;

import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cn.MsgCode.MsgAndCode;
import com.cn.common.LogHelper;
import com.cn.service.AllpayRoleService;

/**
 * 角色管理controller
 * @author songzhili
 * 2016年11月23日下午3:12:52
 */
@Controller
@RequestMapping("/allPayRole")
public class AllpayRoleController {
  
	@Autowired
	private AllpayRoleService allpayRoleService;
	
	@ResponseBody
	@RequestMapping(value="/insert",method=RequestMethod.POST,produces="application/json;charset=UTF-8")
	public String insert(@RequestBody Map<String, Object> source){
        
		LogHelper.info("============>新增角色,roleName:"+source.get("roleName"));
		String result = null;
		try {
			result = allpayRoleService.insert(source);
		} catch (Exception ex) {
			 LogHelper.error(ex, "新增角色异常!!!!!", false);
			 return returnErrorMessage();
		}
		return result;
	}
	
	@ResponseBody
	@RequestMapping(value="/delete",method=RequestMethod.POST,produces="application/json;charset=UTF-8")
	public String delete(@RequestBody Map<String, Object> source){
		
		LogHelper.info("============>删除角色,roleId:"+source.get("roleId"));
		String result = null;
		try {
			result = allpayRoleService.delete(source);
		} catch (Exception ex) {
			 LogHelper.error(ex, "删除角色异常!!!!!", false);
			 return returnErrorMessage();
		}
		return result;
	}
	
	@ResponseBody
	@RequestMapping(value="/update",method=RequestMethod.POST,produces="application/json;charset=UTF-8")
	public String update(@RequestBody Map<String, Object> source){
		
		LogHelper.info("============>修改角色,roleName:"+source.get("roleName"));
		String result = null;
		try {
			result = allpayRoleService.update(source);
		} catch (Exception ex) {
			 LogHelper.error(ex, "修改角色异常!!!!!", false);
			 return returnErrorMessage();
		}
		return result;
	}
	
	@ResponseBody
	@RequestMapping(value="/get",method=RequestMethod.POST,produces="application/json;charset=UTF-8")
	public String obtain(@RequestBody Map<String, Object> source){
		
		LogHelper.info("============>获取角色,roleId:"+source.get("roleId"));
		String result = null;
		try {
			result = allpayRoleService.obtain(source);
		} catch (Exception ex) {
			 LogHelper.error(ex, "获取角色异常!!!!!", false);
			 return returnErrorMessage();
		}
		return result;
	}
	
	@ResponseBody
	@RequestMapping(value="/list",method=RequestMethod.POST,produces="application/json;charset=UTF-8")
	public String obtainList(@RequestBody Map<String, Object> source){
		
		LogHelper.info("============>获取角色列表,roleName:"+source.get("roleName"));
		String result = null;
		try {
			result = allpayRoleService.obtainList(source);
		} catch (Exception ex) {
			 LogHelper.error(ex, "获取角色列表异常!!!!!", false);
			 return returnErrorMessage();
		}
		return result;
	}
	
	private String returnErrorMessage(){
		
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode node = mapper.createObjectNode();
		node.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_001);
		node.put(MsgAndCode.RSP_DESC, "服务器异常,请联系管理员!!!!!");
		return node.toString();
	}
}







package com.cn.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cn.MsgCode.MsgAndCode;
import com.cn.common.CommonHelper;
import com.cn.common.DESUtils;
import com.cn.common.LogHelper;
import com.cn.dao.AllpaySecurityCheckDao;
import com.cn.entity.po.AllpaySecurityCheck;

/**
 * 接口安全校验 controller
 * @author songzhili
 * 2017年1月14日下午6:09:18
 */
@Controller
@RequestMapping("/allpaySecurityCheck")
public class AllpaySecurityCheckController {

	
	@Autowired
	private AllpaySecurityCheckDao allpaySecurityCheckDao;
	
	@ResponseBody
	@RequestMapping(value="/insert",produces="application/json;charset=UTF-8")
	public String insert(@RequestBody Map<String, Object> source){
		
		LogHelper.info("============>新增接口安全,source:"+source);
		AllpaySecurityCheck security = new AllpaySecurityCheck();
		String result = insertOrUpdateSecurity(security, source,true);
		if(result != null){
			return result;
		}
		try {
			allpaySecurityCheckDao.insert(security);
		} catch (Exception ex) {
			 LogHelper.error(ex, "新增接口安全异常!!!!!", false);
			 return returnErrorMessage();
		}
		return returnSuccessMessage();
	}
	
	@ResponseBody
	@RequestMapping(value="/delete",produces="application/json;charset=UTF-8")
	public String delete(@RequestBody Map<String, Object> source){
		
		LogHelper.info("============>删除接口安全,source:"+source);
		if(CommonHelper.isNullOrEmpty(source.get("securityId"))){
			return  returnMissParamMessage("securityId");
		}
		try {
			allpaySecurityCheckDao.delete(source.get("securityId").toString());
		} catch (Exception ex) {
			 LogHelper.error(ex, "删除接口安全异常!!!!!", false);
			 return returnErrorMessage();
		}
		return returnSuccessMessage();
	}
	
	
	@ResponseBody
	@RequestMapping(value="/update",produces="application/json;charset=UTF-8")
	public String update(@RequestBody Map<String, Object> source){
		
		LogHelper.info("============>修改接口安全,source:"+source);
		if(CommonHelper.isNullOrEmpty(source.get("securityId"))){
			return  returnMissParamMessage("securityId");
		}
		try {
			AllpaySecurityCheck security = allpaySecurityCheckDao.obtain(source.get("securityId").toString());
			if(CommonHelper.isNullOrEmpty(security)){
				return returnNullObjectMsg("根据securityId:"+source.get("securityId").toString());
			}
			String result = insertOrUpdateSecurity(security, source, false);
			if(result != null){
				return result;
			}
			allpaySecurityCheckDao.update(security);
		} catch (Exception ex) {
			 LogHelper.error(ex, "修改接口安全异常!!!!!", false);
			 return returnErrorMessage();
		}
		return returnSuccessMessage();
	}
	
	@ResponseBody
	@RequestMapping(value="/get",produces="application/json;charset=UTF-8")
	public String obtain(@RequestBody Map<String, Object> source){
		
		LogHelper.info("============>获取接口安全信息,source:"+source);
		if(CommonHelper.isNullOrEmpty(source.get("securityId"))){
			return  returnMissParamMessage("securityId");
		}
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode node = mapper.createObjectNode();
		try {
			AllpaySecurityCheck security = allpaySecurityCheckDao.obtain(source.get("securityId").toString());
			if(CommonHelper.isNullOrEmpty(security)){
				return returnNullObjectMsg("根据securityId:"+source.get("securityId").toString());
			}
			node.put("systemName", checkTextIsOrNotNull(security.getSystemName()));
			node.put("domainOrAppId", checkTextIsOrNotNull(security.getDomainOrAppId()));
			node.put("randomString", checkTextIsOrNotNull(security.getRandomString()));
			node.put("status", checkTextIsOrNotNull(security.getStatus()));
			node.put("enable", checkTextIsOrNotNull(security.getEnable()));
		} catch (Exception ex) {
			 LogHelper.error(ex, "获取接口安全信息异常!!!!!", false);
			 return returnErrorMessage();
		}
		return returnSuccessMessage(node);
	}
	
	
	@ResponseBody
	@RequestMapping(value="/updateStatus",produces="application/json;charset=UTF-8")
	public String updateStatus(@RequestBody Map<String, Object> source){
		
		LogHelper.info("============>修改接口安全,source:"+source);
		if(CommonHelper.isNullOrEmpty(source.get("securityId"))
				|| CommonHelper.isNullOrEmpty(source.get("status"))){
			return  returnMissParamMessage("securityId或者status");
		}
		try {
			allpaySecurityCheckDao.updateStatus(source.get("securityId").toString(), 
					source.get("status").toString());
		} catch (Exception ex) {
			 LogHelper.error(ex, "修改接口安全异常!!!!!", false);
			 return returnErrorMessage();
		}
		return returnSuccessMessage();
	}
	
	@ResponseBody
	@RequestMapping(value="/updateEnable",produces="application/json;charset=UTF-8")
	public String updateEnable(@RequestBody Map<String, Object> source){
		
		LogHelper.info("============>修改接口安全,source:"+source);
		if(CommonHelper.isNullOrEmpty(source.get("securityId"))
				|| CommonHelper.isNullOrEmpty(source.get("enable"))){
			return  returnMissParamMessage("securityId或者enable");
		}
		try {
			allpaySecurityCheckDao.updateEnable(source.get("securityId").toString(), 
					source.get("enable").toString());
		} catch (Exception ex) {
			 LogHelper.error(ex, "修改接口安全异常!!!!!", false);
			 return returnErrorMessage();
		}
		return returnSuccessMessage();
	}
	
	@ResponseBody
	@RequestMapping(value="/list",produces="application/json;charset=UTF-8")
	public String obtainList(@RequestBody Map<String, Object> source){
		
		LogHelper.info("============>获取接口安全列表,source:"+source);
		if(CommonHelper.isNullOrEmpty(source.get("curragePage"))
				|| CommonHelper.isNullOrEmpty(source.get("pageSize"))){
			returnMissParamMessage("curragePage或者pageSize");
		}
		int currentPage = Integer.parseInt(source.get("curragePage").toString());
		int pageSize = Integer.parseInt(source.get("pageSize").toString());
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode node = mapper.createObjectNode();
		try {
			int total = allpaySecurityCheckDao.count();
			List<Map<String, Object>> list = allpaySecurityCheckDao.obtainList(currentPage, pageSize);
			ArrayNode array = mapper.createArrayNode();
			for(Map<String, Object> map : list){
				ObjectNode nodeOne = mapper.createObjectNode();
				nodeOne.put("systemName", checkTextIsOrNotNull(map.get("SYSTEM_NAME")));
				nodeOne.put("domainOrAppId", checkTextIsOrNotNull(map.get("DOMAIN_OR_APPID")));
				nodeOne.put("randomString", checkTextIsOrNotNull(map.get("RANDOM_STRING")));
				nodeOne.put("status", checkTextIsOrNotNull(map.get("STATUS")));
				nodeOne.put("enable", checkTextIsOrNotNull(map.get("ENABLE")));
				nodeOne.put("securityId", checkTextIsOrNotNull(map.get("SECURITY_ID")));
				array.add(nodeOne);
			}
			node.put("lists", array);
			node.put("total", total);
		} catch (Exception ex) {
			 LogHelper.error(ex, "获取接口安全列表!!!!!", false);
			 return returnErrorMessage();
		}
		node.put("curragePage", currentPage);
		node.put("pageSize", pageSize);
		return returnSuccessMessage(node);
	}
	
	/**
	 * 
	 * @param security
	 * @param source
	 * @return
	 */
	private String insertOrUpdateSecurity(AllpaySecurityCheck security,
			Map<String, Object> source,boolean must){
		
		//ip或手机端appId
		if(!CommonHelper.isNullOrEmpty(source.get("domainOrAppId"))){
			String domainOrAppId = source.get("domainOrAppId").toString();
			security.setDomainOrAppId(domainOrAppId);
			security.setDomainOrAppIdChange(domainOrAppId);
		}else{
			return returnMissParamMessage("domainOrAppId");
		}
		//系统名称 
		if(!CommonHelper.isNullOrEmpty(source.get("systemName"))){
			security.setSystemName(source.get("systemName").toString());
		}else{
			return returnMissParamMessage("systemName");
		}
		//是否启用
		if(!CommonHelper.isNullOrEmpty(source.get("status"))){
			security.setStatus(source.get("status").toString());
		}else{
			return returnMissParamMessage("status");
		}
		//是否放行
		if(!CommonHelper.isNullOrEmpty(source.get("enable"))){
			security.setEnable(source.get("enable").toString());
		}else{
			return returnMissParamMessage("enable");
		}
		if(must){//新增
			String domainOrAppId = source.get("domainOrAppId").toString();
			String uuid = UUID.randomUUID().toString().replace("-", "");
			security.setRandomString(uuid);
			security.setSecretString(DESUtils.encrypt(domainOrAppId+uuid, "111111112222222233333333"));
			security.setCreateTime(new Date());
		}
		return null;
	}
	/**
	 * 
	 * @return
	 */
	private String returnErrorMessage(){
		
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode node = mapper.createObjectNode();
		node.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_001);
		node.put(MsgAndCode.RSP_DESC, "服务器异常,请联系管理员!!!!!");
		return node.toString();
	}
	
	/**
	 * 返回正确的处理结果
	 * @return
	 */
	private String returnSuccessMessage(){
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode node = mapper.createObjectNode();
		node.put(MsgAndCode.RSP_CODE, MsgAndCode.SUCCESS_CODE);
		node.put(MsgAndCode.RSP_DESC, MsgAndCode.SUCCESS_MESSAGE);
		return node.toString();
	}
	/**
	 * 
	 * @param errorMessage
	 * @return
	 */
	private String returnMissParamMessage(String errorMessage){
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode node = mapper.createObjectNode();
		node.put(MsgAndCode.RSP_CODE, MsgAndCode.PARAM_MISSING_CODE);
		node.put(MsgAndCode.RSP_DESC, errorMessage+MsgAndCode.PARAM_MISSING_MSG);
		return node.toString();
	}
	
	/**
	 * 返回正确的处理结果
	 * @param node
	 * @return
	 */
	private String returnSuccessMessage(ObjectNode node){
		node.put(MsgAndCode.RSP_CODE, MsgAndCode.SUCCESS_CODE);
		node.put(MsgAndCode.RSP_DESC, MsgAndCode.SUCCESS_MESSAGE);
		return node.toString();
	}
	
	/**
	 * 根据参数无法　查询到相应的信息
	 * @param errorMsg
	 * @return
	 */
	private String returnNullObjectMsg(String errorMsg){
		
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode node = mapper.createObjectNode();
		node.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_003);
		node.put(MsgAndCode.RSP_DESC, errorMsg+"未查询到相应的信息!!!!!");
		return node.toString();
	}
	/**
	 * 校验　非空
	 * @param obj
	 * @return
	 */
	private String checkTextIsOrNotNull(Object obj){
		
		if(!CommonHelper.isNullOrEmpty(obj)){
			return obj.toString();
		}
		return "";
	}
}

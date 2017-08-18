package com.cn.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.ObjectNode;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cn.MsgCode.MsgAndCode;
import com.cn.common.CommonHelper;
import com.cn.common.CookieHelper;
import com.cn.dao.AllpayRoleDao;
import com.cn.entity.po.AllpayRole;
import com.cn.entity.po.AllpayRoleFunction;
import com.cn.service.AllpayRoleService;
/**
 * 角色管理service
 * @author songzhili
 * 2016年11月23日下午2:50:44
 */
@Service("allpayRoleService")
public class AllpayRoleServiceImpl implements AllpayRoleService {
    
	@Autowired
	private AllpayRoleDao allpayRoleDao;
	
	/**
	 * 创建角色
	 */
	public String insert(Map<String, Object> source) throws Exception {
		
		if(source.get("roleName") == null || "".equals(source.get("roleName"))
				|| source.get("roleIsStart") == null || "".equals(source.get("roleIsStart"))){
			return returnNullParamMessage("roleName或者roleIsStart");
		}

		boolean isExist=allpayRoleDao.isExistRoleName("",source.get("roleName").toString());// true是存在
		if(isExist){
			JSONObject resultJO = new JSONObject();
			resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_00608);
			resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.CODE_00608_MSG);
			return resultJO.toString();
		}

		/**1.添加角色信息**/
		AllpayRole role = new AllpayRole();
		role.setRole_isStart(Integer.parseInt(source.get("roleIsStart").toString()));
		role.setRole_name(source.get("roleName").toString());

		JSONObject publicFileds = CommonHelper.getPublicFileds(MsgAndCode.OPERATION_NEW, "", ""+source.get("userNameFromQXCookie"));
		String userName = publicFileds.getString("userName");
		Date now = CommonHelper.getStringToDate(publicFileds.getString("now"), "yyyy-MM-dd HH:mm:ss");
		String record = publicFileds.getString("record");

	    role.setALLPAY_CREATER(userName);  //创建人
	    role.setALLPAY_CREATETIME(now);  //创建时间
		role.setALLPAY_UPDATETIME(now);  //修改时间
		role.setALLPAY_UPDATER(userName);
	    role.setALLPAY_LOGICDEL("1");  //逻辑删除标记  1---未删除 2---已删除
	    role.setALLPAY_LOGRECORD(record);//操作日志记录(人物-时间-具体操作，不做清空，追加信息)
	    allpayRoleDao.insert(role);
	    /**2.添加关系表信息 ALLPAY_ROLE_FUNCTION**/
	    Object obj = source.get("lists");
	    /**插入关系表**/
	    insertMenuAndFunc(obj, role.getRoleId(), userName, now, record);
	    return returnSuccessMessage();
	}
    /**
     * 删除角色(不是物理删除)
     */
	public String delete(Map<String, Object> source) throws Exception {
		
		if(source.get("roleId") == null){
			return returnNullParamMessage("roleId");
		}
		String roleId = source.get("roleId").toString();
		AllpayRole role = allpayRoleDao.obtain(roleId);
		if(role == null){
			return returnNullObjectMsg("根据roleId:"+roleId);
		}

		JSONObject publicFileds = CommonHelper.getPublicFileds(MsgAndCode.OPERATION_DELETE, role.getALLPAY_LOGRECORD(), ""+source.get("userNameFromQXCookie"));
		String record = publicFileds.getString("record");

		allpayRoleDao.delete(roleId, false,record);
		return returnSuccessMessage();
	}
    /**
     * 修改角色
     */
	public String update(Map<String, Object> source) throws Exception {
		
		if(source.get("roleId") == null || "".equals(source.get("roleId"))
				|| source.get("roleName") == null || "".equals(source.get("roleName"))
				|| source.get("roleIsStart") == null || "".equals(source.get("roleIsStart"))){
			return returnNullParamMessage("roleId或者roleName或者roleIsStart");
		}
		String roleId = source.get("roleId").toString();
		boolean isExist=allpayRoleDao.isExistRoleName(roleId, source.get("roleName").toString());// true是存在
		if(isExist){
			JSONObject resultJO = new JSONObject();
			resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_00608);
			resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.CODE_00608_MSG);
			return resultJO.toString();
		}
		AllpayRole role = allpayRoleDao.obtain(roleId);
		if(role == null){
			return returnNullObjectMsg("根据roleId:"+roleId);
		}
		role.setRole_name(source.get("roleName").toString());
		role.setRole_isStart(Integer.parseInt(source.get("roleIsStart").toString()));

		JSONObject publicFileds = CommonHelper.getPublicFileds(MsgAndCode.OPERATION_UPDATE, role.getALLPAY_LOGRECORD(), ""+source.get("userNameFromQXCookie"));
		String userName = publicFileds.getString("userName");
		Date now = CommonHelper.getStringToDate(publicFileds.getString("now"), "yyyy-MM-dd HH:mm:ss");
		String record = publicFileds.getString("record");
		role.setALLPAY_UPDATER(userName);
		role.setALLPAY_UPDATETIME(now);
		role.setALLPAY_LOGRECORD(record);//操作日志记录(人物-时间-具体操作，不做清空，追加信息)
	    allpayRoleDao.update(role);
	    /**2.添加关系表信息 ALLPAY_ROLE_FUNCTION**/
	    Object obj = source.get("lists");
	    /**插入关系表**/
	    /**删除关系表中的menuId以及funcId**/
	    allpayRoleDao.deleteMenuIdAndFuncId(roleId);
	    /**修改 以新增的方式插入数据库**/
	    insertMenuAndFunc(obj, roleId, userName, now, record);
		return returnSuccessMessage();
	}
    /**
    * 获取角色
    */
	public String obtain(Map<String, Object> source) throws Exception {
		
		if(source.get("roleId") == null){
			return returnNullParamMessage("roleId");
		}
		/****/
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode node = mapper.createObjectNode();
		String roleId = source.get("roleId").toString();
		AllpayRole role = allpayRoleDao.obtain(roleId);
		if(role != null){
			node.put("roleId", roleId);
			node.put("roleName", role.getRole_name());
			node.put("roleIsStart", role.getRole_isStart());
			List<Map<String, Object>> idList = allpayRoleDao.queryForMeuIdAndfuncId(roleId);
			ArrayNode array = mapper.createArrayNode();
			if(!idList.isEmpty()){
				for(int t=0;t<idList.size();t++){
					Map<String, Object> idMap = idList.get(t);
					ObjectNode nodeOne = mapper.createObjectNode();
					if(idMap.get("ROLE_FUNCTION_MENUID") != null){
						nodeOne.put("roleMenuId", idMap.get("ROLE_FUNCTION_MENUID").toString());
						if(idMap.get("ROLE_FUNCTION_FUNID") != null){
							nodeOne.put("roleFunId", idMap.get("ROLE_FUNCTION_FUNID").toString());
							array.add(nodeOne);
						}
					}
				}
				node.put("lists", array);
			}else{
				node.put("lists", array);
			}
		}else{
			return returnNullObjectMsg("根据roleId:"+roleId);
		}
		return returnSuccessMessage(node);
	}
    /**
     * 获取角色列表
     */
	public String obtainList(Map<String, Object> source) throws Exception {
		
		if(source.get("curragePage") == null
				|| source.get("pageSize") == null
				|| source.get("roleName") == null){
			return returnNullParamMessage("curragePage或者pageSize或者roleName");
		}
		String roleName = source.get("roleName").toString();
		String roleIsStart = source.get("roleIsStart").toString();  //是否启用 1--启用 2--禁用
		String currentPage = source.get("curragePage").toString();
		String pageSize = source.get("pageSize").toString();
		List<Map<String, Object>> list = allpayRoleDao.obtainList(roleName, roleIsStart,
				Integer.parseInt(currentPage) - 1, Integer.parseInt(pageSize));
		int total = allpayRoleDao.count(roleName, roleIsStart);
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode node = mapper.createObjectNode();
		ArrayNode array = mapper.createArrayNode();
		if(!list.isEmpty()){
			for(int t=0;t<list.size();t++){
				Map<String, Object> map = list.get(t);
				ObjectNode nodeOne = mapper.createObjectNode();
				if(map.get("ROLE_ID") != null){
					nodeOne.put("roleId",map.get("ROLE_ID").toString());
				}else{
					nodeOne.put("roleId","");
				}
				if(map.get("ROLE_NAME") != null){
					nodeOne.put("roleName",map.get("ROLE_NAME").toString());
				}else{
					nodeOne.put("roleName","");
				}
				if(map.get("ROLE_ISSTART") != null){
					nodeOne.put("roleIsStart",map.get("ROLE_ISSTART").toString());
				}else{
					nodeOne.put("roleIsStart","");
				}
				array.add(nodeOne);
			}
		}/*else{
			StringBuilder together = new StringBuilder();
			together.append("根据roleName:").append(roleName);
			together.append(",roleIsStart:").append(roleName);
			return returnNullObjectMsg(together.toString());
		}*/
		node.put("lists", array);
		node.put("total", total);
		node.put("curragePage", currentPage);
		node.put("pageSize", pageSize);
		return returnSuccessMessage(node);
	}
	/**
	 * 把关联信息插入到ALLPAY_ROLE_FUNCTION
	 * @param obj
	 * @param roleId
	 * @param userName
	 * @param now
	 * @param record
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private void insertMenuAndFunc(Object obj,String roleId,String userName,
			Date now,String record) throws Exception{
		
	    if(obj instanceof List<?>){
	        List<Object> roleList = (List<Object>)obj;
	        int size = roleList.size();
	        if(size == 0){
	        	return;
	        }
	        /****/
	        for(int num=0; num<size; num++){
	        	Object objOne = roleList.get(num);
	        	if(objOne instanceof Map<?, ?>){
	        		Map<String, Object> mapTem = (Map<String, Object>)objOne;
	        		AllpayRoleFunction function = new AllpayRoleFunction();
	        		if(mapTem.get("roleMenuId") != null){
	        			function.setRole_function_menuId(mapTem.get("roleMenuId").toString());
	        		}
	        		if(mapTem.get("roleFunId") != null){
	        			function.setRole_function_funId(mapTem.get("roleFunId").toString());
	        		}
	        		if(mapTem.get("systemId") != null){
	        			function.setRole_function_systemId(mapTem.get("systemId").toString());
	        		}
	        		function.setRole_function_roleId(roleId);
	        		function.setALLPAY_CREATER(userName);
	        		function.setALLPAY_CREATETIME(now);
	        		function.setALLPAY_UPDATER(userName);
	        		function.setALLPAY_UPDATETIME(now);
	        		function.setALLPAY_LOGICDEL("1");
	        		function.setALLPAY_LOGRECORD(record);
	        		allpayRoleDao.insertAllpayRoleFunction(function);
	        	}
	        }
	    }
	}
    /**
     * 返回空处理信息
     * @param errorMsg
     * @return
     */
	private String returnNullParamMessage(String errorMsg){
		
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode node = mapper.createObjectNode();
		node.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_00110);
		node.put(MsgAndCode.RSP_DESC, errorMsg+"是必填参数,不能为空!!! 请检查你的入参!!!!!");
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
	 * 根据参数查询结果为null
	 * @param errorMessage
	 * @return
	 */
	private String returnNullObjectMsg(String errorMessage){
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode node = mapper.createObjectNode();
		node.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_00208);
		node.put(MsgAndCode.RSP_DESC, errorMessage+MsgAndCode.MESSAGE_00208);
		return node.toString();
	}
}

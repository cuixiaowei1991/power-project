package com.cn.service.impl.shop;

import com.cn.MsgCode.MsgAndCode;
import com.cn.common.CommonHelper;
import com.cn.common.LogHelper;
import com.cn.dao.shop.AllpayShopuserDao;
import com.cn.entity.dto.AllpayUserDto;
import com.cn.entity.po.shop.AllpayShopuser;
import com.cn.service.shop.AllpayShopuserService;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.ObjectNode;

import org.json.JSONObject;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

import static all.union.tools.codec.MD5Helper.md5;
import static com.cn.common.CommonHelper.getRandomNum;


/**
 * 商助用户管理业务层实现
 * Created by WangWenFang on 2016/12/15.
 */
@Service
@Transactional(rollbackFor = {Exception.class, RuntimeException.class})
public class AllpayShopuserServiceImpl implements AllpayShopuserService {

    @Autowired
    private AllpayShopuserDao allpayShopuserDao;

	@Override
    public JSONObject getAllpayShopuserList(AllpayUserDto bean) throws Exception {
        JSONObject resultJO=new JSONObject();
        JSONArray array=new JSONArray();
        List<Map<String, Object>> lists=(List<Map<String, Object>>)allpayShopuserDao.getAllpayShopuserList(bean);
        if(lists!=null && lists.size()>0){
            for(Map<String, Object> user:lists){
                JSONObject object=new JSONObject();
                object.put("shopuserId",user.get("SHOPUSER_ID"));  //商户用户表主键id
				object.put("merchantId",user.get("SHOPUSER_SHOPID"));  //商户Id
				object.put("shopuserRole",user.get("SHOPUSER_ROLE"));  //用户角色(商户管理员、门店管理员、收银员)
                object.put("shopuserName",user.get("SHOPUSER_NAME"));  //用户账号(名称)
                object.put("shopuserPhone", user.get("SHOPUSER_PHONE"));  //用户手机号
				object.put("isSuper", user.get("SHOPUSER_ISSUPER"));  //是否超管
                array.put(object);
            }
        }
        resultJO.put("lists", array);
        resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.SUCCESS_CODE);  //返回的状态码
        resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.SUCCESS_MESSAGE);  //返回的状态码描述
        return resultJO;
    }

	/**
	 * 业务系统调用的 新增商助用户信息
	 * @param source
	 * @return
	 * @throws Exception
	 */
	@Override
	public String insert(Map<String, Object> source) throws Exception {
		
		/**开始插入管理员信息**/
		AllpayShopuser shopUser = new AllpayShopuser();
		//商户id
		if(!CommonHelper.isNullOrEmpty(source.get("merchantId"))){
			shopUser.setShopuserShopid(source.get("merchantId").toString());
		}else{
			return returnMissParamMessage("merchantId");
		}
		//管理员
		if(!CommonHelper.isNullOrEmpty(source.get("mhtManager"))){
			shopUser.setShopuserName(source.get("mhtManager").toString());
		}else{
			return returnMissParamMessage("mhtManager");
		}
		//管理手机号
		if(!CommonHelper.isNullOrEmpty(source.get("mhtManagerPhone"))){
			shopUser.setShopuserPhone(source.get("mhtManagerPhone").toString());
		}else{
			return returnMissParamMessage("mhtManagerPhone");
		}
		/*if(!CommonHelper.isNullOrEmpty(source.get("isSuper"))){
			shopUser.setShopuserIssuper(Integer.parseInt(source.get("isSuper").toString()));  //是否是超管  1--是 2--否
		}else{
			return returnMissParamMessage("isSuper");
		}*/
		shopUser.setShopuserIssuper(2);

		JSONObject resultJO = new JSONObject();

		//判断账号是否存在
		boolean boo = allpayShopuserDao.checkIsExist("SHOPUSER_NAME", source.get("mhtManagerPhone").toString(), null);
		if(boo){
			resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_00100);
			resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_00100);
			return resultJO.toString();
		}

		//判断手机号是否存在
		boolean bool = allpayShopuserDao.checkIsExist("SHOPUSER_PHONE", source.get("mhtManagerPhone").toString(), null);
		if(bool){
			resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_00101);
			resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_00101);
			return resultJO.toString();
		}

		List<Map<String, Object>> agentList= (List<Map<String, Object>>) allpayShopuserDao.getAgentInfoByMerchantID(source.get("merchantId").toString());

		String randomNum="";//用户随机6位编号
		String num="";
		while(true)
		{
			randomNum=getRandomNum(6);
			if(agentList==null || null==agentList.get(0).get("AGENT_NUM"))
			{
				num="zl_0000"+randomNum;
			}
			else
			{
				num="zl_"+agentList.get(0).get("AGENT_NUM")+randomNum;
			}
			AllpayShopuser userByNickname=allpayShopuserDao.getShopUserByNickName(num);

			if(userByNickname==null)
			{
				break;
			}
		}
		shopUser.setShopuseraccountid(num);//规则 zl_代理商4位编码+6位随机数

		String userNameFromBusCookie = CommonHelper.nullToString(source.get("userNameFromBusCookie"));
		JSONObject publicFileds = CommonHelper.getPublicFileds(MsgAndCode.OPERATION_NEW, "", userNameFromBusCookie);
		String userName = publicFileds.getString("userName");
		Date now = CommonHelper.getStringToDate(publicFileds.getString("now"), "yyyy-MM-dd HH:mm:ss");
		String record = publicFileds.getString("record");

		shopUser.setShopuserRole("2");//用户角色(0门店管理,1门店业务,2商户管理)
		shopUser.setShopuserName("" + source.get("mhtManagerPhone"));
		shopUser.setShopuserNickname("" + source.get("mhtManager"));  //昵称
		shopUser.setShopuserPhone("" + source.get("mhtManagerPhone"));
		shopUser.setShopuserPassword(md5("111111","utf-8"));
		shopUser.setALLPAY_CREATER(userName);  //创建人
		shopUser.setALLPAY_CREATETIME(now);  //创建时间
		shopUser.setALLPAY_UPDATETIME(now);  //修改时间
		shopUser.setALLPAY_UPDATER(userName);
		shopUser.setALLPAY_LOGICDEL("1");  //逻辑删除标记  1---未删除 2---已删除
		shopUser.setShopuserIsstart(1);  //是否启用 1--启用 2--禁用
		shopUser.setShopuserIscashier(2);  //是否具备软POS收银能力 1--是 2---否
		shopUser.setALLPAY_LOGRECORD(record);
		allpayShopuserDao.insert(shopUser);
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode node = mapper.createObjectNode();
		node.put("shopuserId", shopUser.getShopuserId());
		return returnSuccessMessage(node);
	}

	/**
	 * 业务系统调用的 删除商户信息
	 * @param source
	 * @return
	 * @throws Exception
	 */
	@Override
	public String delete(Map<String, Object> source) throws Exception {
		
	    if(CommonHelper.isNullOrEmpty(source.get("shopuserId"))){
	    	return returnMissParamMessage("shopuserId");
	    }
	    boolean serious = false;
	    if(!CommonHelper.isNullOrEmpty(source.get("serious"))
	    		&& "1".equals(source.get("serious"))){
	    	serious = true;
	    }
	    allpayShopuserDao.delete(source.get("shopuserId").toString(), serious);
		return returnSuccessMessage();
	}
	
	
	@Override
	public String checkUserExist(Map<String, Object> source) throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode node = mapper.createObjectNode();
		if (CommonHelper.isNullOrEmpty(source.get("mhtManager"))
				|| CommonHelper.isNullOrEmpty(source.get("mhtManagerPhone"))) {
			return returnMissParamMessage("mhtManager或者mhtManagerPhone");
		}
		//判断账号是否存在
		boolean boo = allpayShopuserDao.checkIsExist("SHOPUSER_NAME", source.get("mhtManagerPhone").toString(), null);
		if(boo){
			node.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_00100);
			node.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_00100);
			node.put("exist", "1");
			return node.toString();
		}

		//判断手机号是否存在
		boolean bool = allpayShopuserDao.checkIsExist("SHOPUSER_PHONE", source.get("mhtManagerPhone").toString(), null);
		if(bool){
			node.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_00101);
			node.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_00101);
			node.put("exist", "1");
			return node.toString();
		}
		node.put("exist", "0");
		return returnSuccessMessage(node);






	/*	boolean exist = allpayShopuserDao.checkShopUserExist(source);


		if (exist) {
			node.put("exist", "1");
		} else {
			node.put("exist", "0");
		}*/

	}

	/**
	 * 业务系统调用的 导入商助用户
	 * @param source
	 * @return
	 * @throws Exception
	 */
	@Override
	public JSONObject importShopUser(Map<String, Object> source) throws Exception {
		JSONObject resultJO=new JSONObject();
		if(source!=null){
			String merchantName=CommonHelper.nullToString(source.get("merchantName"));  //商户名称(必填)
			String storeName=CommonHelper.nullToString(source.get("storeName"));  //门店名称(必填)
			String userType=CommonHelper.nullToString(source.get("userType"));  //商户类型
			String userName=CommonHelper.nullToString(source.get("userName"));  //管理员名称
			String userPhone=CommonHelper.nullToString(source.get("userPhone"));   //管理员手机
			String userEmail=CommonHelper.nullToString(source.get("userEmail"));  //联系邮箱
			String isSupportPos=CommonHelper.nullToString(source.get("isSupportPos"));  //是否支持软pos

			if(CommonHelper.isEmpty(userType)){
				resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_00402);
				resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_00402);
				return resultJO;
			}else{
				if(!"0".equals(userType) && !"1".equals(userType) && !"2".equals(userType)){
					resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_00407);
					resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_00407);
					return resultJO;
				}
			}
			if(CommonHelper.isEmpty(merchantName)){
				resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_00400);
				resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_00400);
				return resultJO;
			}
			if("1".equals(isSupportPos)){  //是否支持软pos
				if(CommonHelper.isEmpty(storeName)){
					resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_00401);
					resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_00401);
					return resultJO;
				}
			}
			if(CommonHelper.isEmpty(userName)){
				resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_00403);
				resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_00403);
				return resultJO;
			}
			if(CommonHelper.isEmpty(userPhone)){
				resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_00404);
				resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_00404);
				return resultJO;
			}
			if(CommonHelper.isEmpty(isSupportPos)){
				isSupportPos = "2";   //默认2，即不开通
			}else{
				if(!"2".equals(isSupportPos) && !"1".equals(isSupportPos)){
					resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_002);
					resultJO.put(MsgAndCode.RSP_DESC, "是否支持软POS收银不正确！");
					return resultJO;
				}
			}

			List<Map<String, Object>> listStore = null;  //门店列表
			String storeId = "";
			String merId = "";
			//判断商户名称是否存在
			List<Map<String, Object>> list= (List<Map<String, Object>>)allpayShopuserDao.checkMerName(merchantName);
			if(null != list && list.size() > 0){
				//判断该商户下门店名称是否存在
				if(!CommonHelper.isEmpty(storeName)){
					listStore = (List<Map<String, Object>>)allpayShopuserDao.checkStore(merchantName, storeName);
					if(null == listStore || listStore.size() == 0){
						resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_00411);
						resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_00411);
						return resultJO;
					}
					storeId = listStore.get(0).get("STORE_ID").toString();
				}
				merId = list.get(0).get("MERCHANT_ID").toString();

				//判断账号是否存在
				boolean boo = allpayShopuserDao.checkIsExist("SHOPUSER_NAME", userPhone, null);
				if(boo){
					resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_00100);
					resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_00100);
					return resultJO;
				}

				//判断手机号是否存在
				boolean bool = allpayShopuserDao.checkIsExist("SHOPUSER_PHONE", userPhone, null);
				if(bool){
					resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_00101);
					resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_00101);
					return resultJO;
				}

				//判断邮箱是否存在
				if(!CommonHelper.isEmpty(userEmail)){
					boolean boole = allpayShopuserDao.checkIsExist("SHOPUSER_EMAIL", userEmail, null);
					if(boole){
						resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_00413);
						resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_00413);
						return resultJO;
					}
				}
			}else{
				resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_00410);
				resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_00410);
				return resultJO;
			}

			//插入商助用户信息
			AllpayShopuser shopuser = new AllpayShopuser();
			shopuser.setShopuserName(userPhone);	//用户账号
			shopuser.setShopuserNickname(userName);  //昵称
			shopuser.setShopuserPhone(userPhone);	//用户手机号
			shopuser.setShopuserPassword(md5("111111","utf-8"));	//用户密码
			shopuser.setShopuserRole(userType);	//用户角色(0门店管理,1门店业务,2商户管理)
			shopuser.setShopuserShopid(merId);	//所属商户id
			shopuser.setShopuserIscashier(Integer.parseInt(isSupportPos));	//是否具备软POS收银能力 0:否 1:是
			shopuser.setShopuserStoreid(storeId);	//如果用户是网点管理员和收银员，那么该字段存对应网点id
			if(!CommonHelper.isEmpty(userEmail)){
				shopuser.setShopuseremail(userEmail);
			}
			//开始 生成用户账号ID
			List<Map<String, Object>> agentList= (List<Map<String, Object>>) allpayShopuserDao.getAgentInfoByMerchantID(merId);

			String randomNum="";//用户随机6位编号
			String num="";
			while(true)
			{
				randomNum=getRandomNum(6);
				if(agentList==null || null==agentList.get(0).get("AGENT_NUM"))
				{
					num="zl_0000"+randomNum;
				}
				else
				{
					num="zl_"+agentList.get(0).get("AGENT_NUM")+randomNum;
				}
				LogHelper.info("随机生成的6位数字为：" + randomNum);
				AllpayShopuser userByNickname=allpayShopuserDao.getShopUserByNickName(num);

				if(userByNickname==null)
				{
					break;
				}
			}
			//结束 生成用户账号ID
			shopuser.setShopuseraccountid(num);//规则 zl_代理商4位编码+6位随机数

			String userNameFromBusCookie = CommonHelper.nullToString(source.get("userNameFromBusCookie"));
			JSONObject publicFileds = CommonHelper.getPublicFileds(MsgAndCode.OPERATION_BULK_IMPORT, "", userNameFromBusCookie);
			String opName = publicFileds.getString("userName");
			Date now = CommonHelper.getStringToDate(publicFileds.getString("now"), "yyyy-MM-dd HH:mm:ss");
			String record = publicFileds.getString("record");

			shopuser.setALLPAY_CREATER(opName);
			shopuser.setALLPAY_CREATETIME(now);
			shopuser.setALLPAY_UPDATETIME(now);  //修改时间
			shopuser.setALLPAY_LOGICDEL("1");  //逻辑删除标记  1---未删除 2---已删除
			shopuser.setShopuserIsstart(1);  //是否启用 1--启用 2--禁用
			shopuser.setShopuserIssuper(2);  //是否是超管  1--是 2--否
			shopuser.setALLPAY_LOGRECORD(record);
			boolean boo = allpayShopuserDao.insert(shopuser);
			if(!boo){
				resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_00409);
				resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_00409);
				return resultJO;
			}
			resultJO.put("shopuserId",shopuser.getShopuserId());
			resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.SUCCESS_CODE);
			resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.SUCCESS_MESSAGE);
		}else{
			resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_003);
			resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_003);
			return resultJO;
		}
		return resultJO;
	}

	/**
	 *
	 * @param source
	 * @return
	 * @throws Exception
	 */
	@Override
	public String update(Map<String, Object> source) throws Exception {
		
		if(CommonHelper.isNullOrEmpty(source.get("merchantId"))){
			return returnMissParamMessage("merchantId");
		}
		/**开始修改管理员信息**/
		if(!CommonHelper.isNullOrEmpty(source.get("mhtManager"))
				|| !CommonHelper.isNullOrEmpty(source.get("mhtManagerPhone"))){
		    String merchantId = source.get("merchantId").toString();
			AllpayShopuser shopUser = allpayShopuserDao.obtain(merchantId);
			if(CommonHelper.isNullOrEmpty(shopUser)){
				return returnNullObjectMsg("根据merchantId:"+merchantId);
			}
			//管理员
			if(!CommonHelper.isNullOrEmpty(source.get("mhtManager"))){
				shopUser.setShopuserName(source.get("mhtManager").toString());
			}
			//管理手机号
			if(!CommonHelper.isNullOrEmpty(source.get("mhtManagerPhone"))){
				shopUser.setShopuserPhone(source.get("mhtManagerPhone").toString());
			}
			/*if(!CommonHelper.isNullOrEmpty(source.get("isSuper"))){
				shopUser.setShopuserIssuper(Integer.parseInt(source.get("isSuper").toString()));  //是否是超管  1--是 2--否
			}else{
				return returnMissParamMessage("isSuper");
			}*/
			shopUser.setShopuserIssuper(2);

			JSONObject resultJO = new JSONObject();

			//判断账号是否存在
			boolean boo = allpayShopuserDao.checkIsExist("SHOPUSER_NAME", source.get("mhtManager").toString(), shopUser.getShopuserId());
			if(boo){
				resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_00100);
				resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_00100);
				return resultJO.toString();
			}

			//判断手机号是否存在
			boolean bool= allpayShopuserDao.checkIsExist("SHOPUSER_PHONE", source.get("mhtManagerPhone").toString(), shopUser.getShopuserId());
			if(bool){
				resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_00101);
				resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_00101);
				return resultJO.toString();
			}

			String userNameFromMerCookie = CommonHelper.nullToString(source.get("userNameFromMerCookie"));
			JSONObject publicFileds = CommonHelper.getPublicFileds(MsgAndCode.OPERATION_UPDATE, shopUser.getALLPAY_LOGRECORD(), userNameFromMerCookie);
			String userName = publicFileds.getString("userName");
			Date now = CommonHelper.getStringToDate(publicFileds.getString("now"), "yyyy-MM-dd HH:mm:ss");
			String record = publicFileds.getString("record");

			shopUser.setALLPAY_UPDATETIME(now);  //修改时间
			shopUser.setALLPAY_UPDATER(userName);
			shopUser.setALLPAY_LOGRECORD(record);
			allpayShopuserDao.update(shopUser);
		}
		return returnSuccessMessage();
	}

	/**
	 * 商助系统的 2.1.1获取用户信息列表
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	@Override
	public String getShopUserInfoList(AllpayUserDto bean) throws Exception
	{
		//JSONObject resultJO=new JSONObject();

		ObjectMapper mapper = new ObjectMapper();

		ArrayNode array = mapper.createArrayNode();

		//JSONArray array=new JSONArray();
		if(CommonHelper.isNullOrEmpty(bean.getMerchantId()))
		{
			return returnMissParamMessage("shopId");
		}
		if(CommonHelper.isNullOrEmpty(bean.getCurragePage()) || CommonHelper.isNullOrEmpty(bean.getPageSize()))
		{
			return returnMissParamMessage("curragePage、pageSize");
		}
		List<Map<String, Object>> shopuserList= (List<Map<String, Object>>) allpayShopuserDao.getShopUserInfoList(bean,"page");
		List<Map<String, Object>> count= (List<Map<String, Object>>) allpayShopuserDao.getShopUserInfoList(bean,"findAll");
		if(shopuserList!=null && shopuserList.size()>0)
		{
			for(Map<String, Object> allpayShopuser : shopuserList)
			{
				ObjectNode node = mapper.createObjectNode();
				node.put("userId",allpayShopuser.get("SHOPUSER_ID").toString());
				node.put("userName",allpayShopuser.get("SHOPUSER_NAME").toString());
				node.put("userPhone",allpayShopuser.get("SHOPUSER_PHONE").toString());
				if("0".equals(allpayShopuser.get("SHOPUSER_ROLE").toString()))
				{
					node.put("roleName","网点管理员");
				}
				else if("1".equals(allpayShopuser.get("SHOPUSER_ROLE").toString()))
				{
					node.put("roleName","网点业务员");
				}
				else if("2".equals(allpayShopuser.get("SHOPUSER_ROLE").toString()))
				{
					node.put("roleName","商户管理员");
				}

				node.put("userRoleId",allpayShopuser.get("SHOPUSER_ROLE").toString());
				node.put("userStoreId",allpayShopuser.get("SHOPUSER_STOREID")==null?"":allpayShopuser.get("SHOPUSER_STOREID").toString());
				node.put("userStoreName", allpayShopuser.get("STORE_SHOPNAME") == null ? "" : allpayShopuser.get("STORE_SHOPNAME").toString());
				node.put("isCashier",allpayShopuser.get("SHOPUSER_ISCASHIER")==null?"":allpayShopuser.get("SHOPUSER_ISCASHIER").toString());
				node.put("createTime", allpayShopuser.get("ALLPAY_CREATETIME").toString());
				node.put("useIsStart",allpayShopuser.get("SHOPUSER_ISSTART").toString());
				node.put("isSuper", allpayShopuser.get("SHOPUSER_ISSUPER").toString());  //是否超管
				array.add(node);
			}
		}
		ObjectNode node_1 = mapper.createObjectNode();
		node_1.put("curragePage",bean.getCurragePage());
		node_1.put("pageSize", bean.getPageSize());
		node_1.put("total",count.size());
		node_1.put("lists", array);
		node_1.put(MsgAndCode.RSP_CODE, MsgAndCode.SUCCESS_CODE);  //返回的状态码
		node_1.put(MsgAndCode.RSP_DESC, MsgAndCode.SUCCESS_MESSAGE);  //返回的状态码描述
		LogHelper.info("获取商助用户信息列表result=" + node_1.toString());
		return node_1.toString();
	}

	/**
	 * 商助系统的 新增用户信息(商助用户信息插入)
	 * @param source
	 * @return
	 */
	@Override
	public String insertShopUserInfo(Map<String, Object> source) throws Exception {
		LogHelper.info("新增用户信息："+source);

		/**开始插入管理员信息**/
		AllpayShopuser shopUser = new AllpayShopuser();
		String result = checkUserInfo(source, shopUser, true);
		if(!CommonHelper.isNullOrEmpty(result)){
			return result;
		}
		List<Map<String, Object>> userListUserName= (List<Map<String, Object>>) allpayShopuserDao.checkShopUserExitByParamters(source.get("userName").toString());
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode node = mapper.createObjectNode();
		if(userListUserName.size()>0)
		{
			node.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_00100);
			node.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_00100);
			return node.toString();
		}
		List<Map<String, Object>> userListUserPhone= (List<Map<String, Object>>) allpayShopuserDao.checkShopUserExitByParamters(source.get("userPhone").toString());
		if(userListUserPhone.size()>0)
		{
			node.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_00101);
			node.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_00101);
			return node.toString();
		}


		List<Map<String, Object>> agentList= (List<Map<String, Object>>) allpayShopuserDao.getAgentInfoByMerchantID(source.get("userShopId").toString());

		String randomNum="";//用户随机6位编号
		String num="";
		while(true)
		{
			randomNum=getRandomNum(6);
			if(agentList==null || agentList.size() == 0)
			{
				num="zl_0000"+randomNum;
			}
			else
			{
				num="zl_"+agentList.get(0).get("AGENT_NUM")+randomNum;
			}
			LogHelper.info("随机生成的6位数字为：" + randomNum);
			//List<?> count = hibernateDao.listBySQL("SELECT * FROM ALLPAY_SHOPUSER T WHERE T.SHOPUSER_NICKNAME='" + num + "'", false);
			AllpayShopuser userByNickname=allpayShopuserDao.getShopUserByNickName(num);

			if(userByNickname==null)
			{
				break;
			}
		}
		shopUser.setShopuseraccountid(num);//规则 zl_代理商4位编码+6位随机数

		//昵称
		if(!CommonHelper.isNullOrEmpty(source.get("userNickName"))){
			shopUser.setShopuserNickname(source.get("userNickName").toString());
		}else{
			return returnMissParamMessage("userNickName");
		}

		String userNameFromMerCookie = CommonHelper.nullToString(source.get("userNameFromMerCookie"));
		JSONObject publicFileds = CommonHelper.getPublicFileds(MsgAndCode.OPERATION_NEW, "", userNameFromMerCookie);
		String userName = publicFileds.getString("userName");
		Date now = CommonHelper.getStringToDate(publicFileds.getString("now"), "yyyy-MM-dd HH:mm:ss");
		String record = publicFileds.getString("record");

		shopUser.setALLPAY_CREATER(userName);  //创建人
		shopUser.setALLPAY_CREATETIME(now);  //创建时间
		shopUser.setALLPAY_UPDATETIME(now);  //修改时间
		shopUser.setALLPAY_UPDATER(userName);
		shopUser.setALLPAY_LOGICDEL("1");  //逻辑删除标记  1---未删除 2---已删除
		shopUser.setALLPAY_LOGRECORD(record);
		allpayShopuserDao.insert(shopUser);
		/*ObjectMapper mapper = new ObjectMapper();
		ObjectNode node = mapper.createObjectNode();*/
		node.put("shopuserId", shopUser.getShopuserId());
		return returnSuccessMessage(node);
	}

	/**
	 * 用户信息非空验证
	 * @param source
	 * @param shopUser
	 * @return
	 */
	public String checkUserInfo(Map<String, Object> source, AllpayShopuser shopUser, boolean flag)  throws Exception {
		//商户id
		if(!CommonHelper.isNullOrEmpty(source.get("userShopId"))){
			shopUser.setShopuserShopid(source.get("userShopId").toString());
		}else{
			return returnMissParamMessage("userShopId");
		}
		//姓名
		if(!CommonHelper.isNullOrEmpty(source.get("userName"))){
			shopUser.setShopuserName(source.get("userName").toString());
		}else{
			return returnMissParamMessage("userName");
		}
		//手机号
		if(!CommonHelper.isNullOrEmpty(source.get("userPhone"))){
			shopUser.setShopuserPhone(source.get("userPhone").toString());
		}else{
			return returnMissParamMessage("userPhone");
		}
		//角色id
		if(!CommonHelper.isNullOrEmpty(source.get("userRoleId"))){
			shopUser.setShopuserRole(source.get("userRoleId").toString());
		}else{
			return returnMissParamMessage("userRoleId");
		}
		//是否具备软pos 1--是 2--否
		if(!CommonHelper.isNullOrEmpty(source.get("isCashier"))){
			shopUser.setShopuserIscashier(Integer.parseInt(source.get("isCashier").toString()));
		}else{
			return returnMissParamMessage("isCashier");
		}
		//门店id
		if("1".equals(source.get("isCashier"))){
			if(!CommonHelper.isNullOrEmpty(source.get("storeId"))){
				shopUser.setShopuserStoreid(source.get("storeId").toString());
			}else{
				return returnMissParamMessage("storeId");
			}
		}else{
			shopUser.setShopuserStoreid(source.get("storeId").toString());
		}
		//是否启用
		if(!CommonHelper.isNullOrEmpty(source.get("userIsStart"))){
			shopUser.setShopuserIsstart(Integer.parseInt(source.get("userIsStart").toString()));
		}else{
			return returnMissParamMessage("userIsStart");
		}
		//是否是超管  1--是 2--否
		/*if(!CommonHelper.isNullOrEmpty(source.get("isSuper"))){
			shopUser.setShopuserIssuper(Integer.parseInt(source.get("isSuper").toString()));
		}else{
			return returnMissParamMessage("isSuper");
		}*/
		shopUser.setShopuserIssuper(2);

		if(flag){
			//用户密码 默认111111
			if(!CommonHelper.isNullOrEmpty(source.get("userPassword"))){
				//md5加密
				shopUser.setShopuserPassword(md5(source.get("userPassword").toString(),"utf-8"));
			}else{
				return returnMissParamMessage("userPassword");
			}
		}
		return null;
	}

	/**
	 * 商助系统的 修改用户信息
 	 * @param source
	 * @return
	 */
	@Override
	public String updateShopUserInfo(Map<String, Object> source) throws Exception {
		if(CommonHelper.isNullOrEmpty(source.get("userId"))){
			return returnMissParamMessage("userId");
		}
		AllpayShopuser shopUser= allpayShopuserDao.getShopUserByID(source.get("userId").toString());
		if(shopUser==null)
		{
			return returnNullObjectMsg("用户id：" + source.get("userId").toString());
		}
		String result = checkUserInfo(source, shopUser, false);
		if(!CommonHelper.isNullOrEmpty(result)){
			return result;
		}
		//昵称
		if(!CommonHelper.isNullOrEmpty(source.get("userNickName"))){
			shopUser.setShopuserNickname(source.get("userNickName").toString());
		}else{
			return returnMissParamMessage("userNickName");
		}

		//判断账号是否存在
		boolean boo = allpayShopuserDao.checkIsExist("SHOPUSER_NAME", "" + source.get("userName"), shopUser.getShopuserId());
		JSONObject resultJO = new JSONObject();
		if(boo){
			resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_00100);
			resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_00100);
			return resultJO.toString();
		}

		//判断手机号是否存在
		String userPhone = CommonHelper.nullToString(source.get("userPhone"));
		boolean bool= allpayShopuserDao.checkIsExist("SHOPUSER_PHONE", userPhone, shopUser.getShopuserId());
		if(bool){
			resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_00101);
			resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_00101);
			return resultJO.toString();
		}

		String userNameFromMerCookie = CommonHelper.nullToString(source.get("userNameFromMerCookie"));
		JSONObject publicFileds = CommonHelper.getPublicFileds(MsgAndCode.OPERATION_UPDATE, shopUser.getALLPAY_LOGRECORD(), userNameFromMerCookie);
		String userName = publicFileds.getString("userName");
		Date now = CommonHelper.getStringToDate(publicFileds.getString("now"), "yyyy-MM-dd HH:mm:ss");
		String record = publicFileds.getString("record");

		shopUser.setALLPAY_UPDATETIME(now);  //修改时间
		shopUser.setALLPAY_UPDATER(userName);
		shopUser.setALLPAY_LOGRECORD(record);
		allpayShopuserDao.update(shopUser);
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode node = mapper.createObjectNode();
		node.put("shopuserId", shopUser.getShopuserId());
		return returnSuccessMessage(node);
	}


	/**
	 * 商助系统的 删除用户
	 * @param source
	 * @return
	 */
	@Override
	public String deleteShopUser(Map<String, Object> source) throws Exception {
		if(CommonHelper.isNullOrEmpty(source.get("userId"))){
			return returnMissParamMessage("userId");
		}
		AllpayShopuser shopUser= allpayShopuserDao.getShopUserByID(source.get("userId").toString());
		if(shopUser==null)
		{
			return returnNullObjectMsg("用户id：" + source.get("userId").toString());
		}

		String userNameFromMerCookie = CommonHelper.nullToString(source.get("userNameFromMerCookie"));
		JSONObject publicFileds = CommonHelper.getPublicFileds(MsgAndCode.OPERATION_DELETE, shopUser.getALLPAY_LOGRECORD(), userNameFromMerCookie);
		String userName = publicFileds.getString("userName");
		Date now = CommonHelper.getStringToDate(publicFileds.getString("now"), "yyyy-MM-dd HH:mm:ss");
		String record = publicFileds.getString("record");

		shopUser.setALLPAY_UPDATETIME(now);  //修改时间
		shopUser.setALLPAY_UPDATER(userName);
		shopUser.setALLPAY_LOGRECORD(record);
		shopUser.setALLPAY_LOGICDEL("2");  //逻辑删除标记  1---未删除 2---已删除
		allpayShopuserDao.update(shopUser);
		return returnSuccessMessage();
	}

	/**
	 * 商助系统的 获取用户信息
	 * @param source
	 * @return
	 */
	@Override
	public String getShopUserInfoById(Map<String, Object> source) throws Exception {
		if(CommonHelper.isNullOrEmpty(source.get("userId"))){
			return returnMissParamMessage("userId");
		}
		AllpayShopuser shopUser= allpayShopuserDao.getShopUserByID(source.get("userId").toString());

		JSONObject resultJO=new JSONObject();
		resultJO.put("userId",shopUser.getShopuserId());
		resultJO.put("userNickName",shopUser.getShopuserNickname()==null?"":shopUser.getShopuserNickname());
		resultJO.put("userName",shopUser.getShopuserName()==null?"":shopUser.getShopuserName());
		resultJO.put("userPhone",shopUser.getShopuserPhone()==null?"":shopUser.getShopuserPhone());
		resultJO.put("userAccountId",shopUser.getShopuseraccountid()==null?"":shopUser.getShopuseraccountid());
		if("0".equals(shopUser.getShopuserRole()))
		{
			resultJO.put("roleName","网点管理员");
		}
		else if("1".equals(shopUser.getShopuserRole()))
		{
			resultJO.put("roleName","网点业务员");
		}
		else if("2".equals(shopUser.getShopuserRole()))
		{
			resultJO.put("roleName","商户管理员");
		}
		resultJO.put("userRoleId",shopUser.getShopuserRole());
		resultJO.put("userShopId",shopUser.getShopuserShopid()==null?"":shopUser.getShopuserShopid());
		resultJO.put("userStoreId", shopUser.getShopuserStoreid() == null ? "" : shopUser.getShopuserStoreid());
		if(shopUser.getShopuserStoreid()==null || "".equals(shopUser.getShopuserStoreid()))
		{
			resultJO.put("userStoreName","");
		}
		else
		{
			List<Map<String, Object>> storeList= (List<Map<String, Object>>) allpayShopuserDao.getStoreInfo(shopUser.getShopuserStoreid());
			if(storeList==null || storeList.size()<=0)
			{
				resultJO.put("userStoreName","");
			}
			else
			{
				resultJO.put("userStoreName",storeList.get(0).get("STORE_SHOPNAME"));
			}
		}


		resultJO.put("isCashier", shopUser.getShopuserIscashier());
		resultJO.put("useIsStart", shopUser.getShopuserIsstart());
		resultJO.put("userPassword", shopUser.getShopuserPassword());
		resultJO.put("isSuper", shopUser.getShopuserIssuper());
		resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.SUCCESS_CODE);
		resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.SUCCESS_MESSAGE);
		return resultJO.toString();
	}

	/**
	 * 业务系统的 重置商户密码
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	@Override
	public String resetPassword(AllpayUserDto bean) throws Exception {
		JSONObject resultJO = new JSONObject();
		String userId = bean.getUserId();  //用户id
		if(CommonHelper.isEmpty(userId)){
			resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_00118);
			resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_00118);
			return resultJO.toString();
		}
		if(CommonHelper.isEmpty(bean.getUserPassword())){  //用户密码
			resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_00117);
			resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_00117);
			return resultJO.toString();
		}

		//查询用户信息
		AllpayShopuser shopuser = allpayShopuserDao.getShopUserByID(userId);//用户id
		if(null == shopuser){
			resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_00120);
			resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_00120);
			return resultJO.toString();
		}

		JSONObject publicFileds = CommonHelper.getPublicFileds(MsgAndCode.OPERATION_RESETPWD, shopuser.getALLPAY_LOGRECORD(), bean.getUserNameFromBusCookie());
		String userName = publicFileds.getString("userName");
		Date now = CommonHelper.getStringToDate(publicFileds.getString("now"), "yyyy-MM-dd HH:mm:ss");
		String record = publicFileds.getString("record");

		shopuser.setShopuserPassword(md5(bean.getUserPassword(), "utf-8"));
		shopuser.setALLPAY_UPDATER(userName);  //修改人
		shopuser.setALLPAY_UPDATETIME(now);  //修改时间
		shopuser.setALLPAY_LOGRECORD(record);  //操作日志记录(人物-时间-具体操作，不做清空，追加信息)
		boolean result = allpayShopuserDao.update(shopuser);
		if(result){
			resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.SUCCESS_CODE);
			resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.SUCCESS_MESSAGE);
		}else{
			resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_001);
			resultJO.put(MsgAndCode.RSP_DESC, "重置商户密码错误！");
		}
		return resultJO.toString();
	}

	/**
	 * 商助系统的 修改密码
	 * @param source
	 * @return
	 * @throws Exception
	 */
	@Override
	public JSONObject szUpdatePwd(Map<String, Object> source) throws Exception {
		JSONObject resultJO = new JSONObject();
		if(null == source || source.size() == 0){
			resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_001);
			resultJO.put(MsgAndCode.RSP_DESC, "用户id或密码不能为空！");
			return resultJO;
		}

		String username = CommonHelper.nullToString(source.get("username"));    //用户id
		String password = CommonHelper.nullToString(source.get("password")); 	//密码
		String newPassword = CommonHelper.nullToString(source.get("newPassword"));    //新密码
		if(CommonHelper.isEmpty(username) || CommonHelper.isEmpty(password)){
			resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_001);
			resultJO.put(MsgAndCode.RSP_DESC, "用户id或密码不能为空！");
			return resultJO;
		}
		if(CommonHelper.isEmpty(newPassword)){
			resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_001);
			resultJO.put(MsgAndCode.RSP_DESC, "新密码不能为空！");
			return resultJO;
		}

		//查询商助用户表
		List<AllpayShopuser> userList = (List<AllpayShopuser>)allpayShopuserDao.getUser(username, password);
		if(null == userList || userList.size() == 0){
			resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_001);
			resultJO.put(MsgAndCode.RSP_DESC, "用户id或密码错误！");
			return resultJO;
		}

		//修改密码
		boolean boo = allpayShopuserDao.updatePwd(source);
		if(!boo){
			resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_001);
			resultJO.put(MsgAndCode.RSP_DESC, "商助修改密码出现错误！");
			return resultJO;
		}
		resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.SUCCESS_CODE);
		resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.SUCCESS_MESSAGE);
		return resultJO;
	}

	@Override
	public JSONObject getShopIdByWechat(AllpayUserDto allpayUserDto) throws Exception {
		JSONObject resultJO = new JSONObject();
		String openid = CommonHelper.nullToString(allpayUserDto.getShopUserOpenId());
		String appid = CommonHelper.nullToString(allpayUserDto.getShopUserAppId());
		if(CommonHelper.isEmpty(openid) || CommonHelper.isEmpty(appid)){
			resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_001);
			resultJO.put(MsgAndCode.RSP_DESC, "微信openid或appid不能为空");
			return resultJO;
		}
		List<Map<String, Object>> lists = allpayShopuserDao.getShopIdByWechat(openid, appid);
		if(lists!=null && lists.size()>0){
			Map<String, Object> map=lists.get(0);
            resultJO.put("shopId",CommonHelper.nullToString(map.get("SHOPUSER_SHOPID")));
			resultJO.put("role",CommonHelper.nullToString(map.get("SHOPUSER_ROLE")));
			resultJO.put("storeId",CommonHelper.nullToString(map.get("SHOPUSER_STOREID")));
			resultJO.put("userId", CommonHelper.nullToString(map.get("SHOPUSER_ID")));
		}else{
			resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_001);
			resultJO.put(MsgAndCode.RSP_DESC, "根据openid和appid无法查询到任何商户信息！");
			return resultJO;
		}

		resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.SUCCESS_CODE);
		resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.SUCCESS_MESSAGE);
		return resultJO;
	}

	/**
	 * 微信商户用户登录
	 * @param source
	 * @return
	 * @throws Exception
	 */
	@Override
	public JSONObject userWechatLogin(Map<String, Object> source) throws Exception {
		JSONObject resultJO = new JSONObject();
		if(null == source || source.size() == 0){
			resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_00001);
			resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_00001);
			return resultJO;
		}

		String username = "";    //登录名
		String password = ""; 	//密码
		String shopUserAppId = "";	//公众号appid
		String shopUserOpenId = "";	//用户openid
		String shopUserUnionId = "";	//用户unionId全网唯一
		if(source.containsKey("userName") && !CommonHelper.isNullOrEmpty(source.get("userName"))){
			username = source.get("userName").toString();
		}else{
			resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_00001);
			resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_00001);
			return resultJO;
		}
		if(source.containsKey("passWord") && !CommonHelper.isNullOrEmpty(source.get("passWord"))){
			password = source.get("passWord").toString();
		}else{
			resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_00001);
			resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_00001);
			return resultJO;
		}
		if(source.containsKey("shopUserAppId") && !CommonHelper.isNullOrEmpty(source.get("shopUserAppId"))){
			shopUserAppId = source.get("shopUserAppId").toString();
		}/*else{
			resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_00003);
			resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_00003);
			return resultJO;
		}*/
		if(source.containsKey("shopUserOpenId") && !CommonHelper.isNullOrEmpty(source.get("shopUserOpenId"))){
			shopUserOpenId = source.get("shopUserOpenId").toString();
		}/*else{
			resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_00004);
			resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_00004);
			return resultJO;
		}*/
		if(source.containsKey("shopUserUnionId") && !CommonHelper.isNullOrEmpty(source.get("shopUserUnionId"))){
			shopUserUnionId = source.get("shopUserUnionId").toString();
		}

		//查询商助用户表
		List<AllpayShopuser> userList = (List<AllpayShopuser>)allpayShopuserDao.getUser(username,password);
		if(null == userList || userList.size() == 0){
			resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_00002);
			resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_00002);
			return resultJO;
		}

		//更新微信三方id到用户表
		AllpayShopuser shopuser = userList.get(0);

		JSONObject publicFileds = CommonHelper.getPublicFileds(MsgAndCode.OPERATION_WECHATLOGIN, shopuser.getALLPAY_LOGRECORD(), username);
		String userName = publicFileds.getString("userName");
		Date now = CommonHelper.getStringToDate(publicFileds.getString("now"), "yyyy-MM-dd HH:mm:ss");
		String record = publicFileds.getString("record");

		shopuser.setShopuserAppid(shopUserAppId);
		shopuser.setShopuserOpenid(shopUserOpenId);
		shopuser.setShopuserUnionID(shopUserUnionId);
		shopuser.setALLPAY_UPDATER(userName);  //修改人
		shopuser.setALLPAY_UPDATETIME(now);  //修改时间
		shopuser.setALLPAY_LOGRECORD(record);  //操作日志记录(人物-时间-具体操作，不做清空，追加信息)
		boolean result = allpayShopuserDao.update(shopuser);
		if(!result){
			resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_00005);
			resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_00005);
			return resultJO;
		}

		resultJO.put("userId", shopuser.getShopuserId());
		resultJO.put("shopId", shopuser.getShopuserShopid());
		resultJO.put("userRole", shopuser.getShopuserRole());
		resultJO.put("userStoreid", CommonHelper.nullToString(shopuser.getShopuserStoreid()));
		resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.SUCCESS_CODE);
		resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.SUCCESS_MESSAGE);
		return resultJO;
	}

	/**
	 * 微信商户用户退出登录（解绑微信三方信息）
	 * @param source
	 * @return
	 * @throws Exception
	 */
	@Override
	public JSONObject userWechatLoginOut(Map<String, Object> source) throws Exception {
		JSONObject resultJO = new JSONObject();
		if(null == source || source.size() == 0){
			resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_00118);
			resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_00118);
			return resultJO;
		}

		String useId = null;	//用户id
		String weChatName = null;	//微信名称
		if(source.containsKey("useId") && !CommonHelper.isNullOrEmpty(source.get("useId"))){
			useId = source.get("useId").toString();
		}else{
			resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_00118);
			resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_00118);
			return resultJO;
		}
		if(source.containsKey("weChatName") && !CommonHelper.isNullOrEmpty(source.get("weChatName"))){
			weChatName = source.get("weChatName").toString();
		}else{
			weChatName = "allpay";
		}

		//查询商助用户表
		AllpayShopuser shopuser = allpayShopuserDao.getShopUserByID(useId);
		if(null == shopuser){
			resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_00208);
			resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_00208);
			return resultJO;
		}

		//解绑微信三方id到用户表
		JSONObject publicFileds = CommonHelper.getPublicFileds(MsgAndCode.OPERATION_WECHATLOGIN+"退出", shopuser.getALLPAY_LOGRECORD(), weChatName);
		String userName = publicFileds.getString("userName");
		Date now = CommonHelper.getStringToDate(publicFileds.getString("now"), "yyyy-MM-dd HH:mm:ss");
		String record = publicFileds.getString("record");

		shopuser.setShopuserAppid("");
		shopuser.setShopuserOpenid("");
		shopuser.setShopuserUnionID("");
		shopuser.setALLPAY_UPDATER(userName);  //修改人
		shopuser.setALLPAY_UPDATETIME(now);  //修改时间
		shopuser.setALLPAY_LOGRECORD(record);  //操作日志记录(人物-时间-具体操作，不做清空，追加信息)
		boolean result = allpayShopuserDao.update(shopuser);
		if(!result){
			resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_001);
			resultJO.put(MsgAndCode.RSP_DESC, "退出登录异常！");
			return resultJO;
		}

		resultJO.put("userId", shopuser.getShopuserId());
		resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.SUCCESS_CODE);
		resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.SUCCESS_MESSAGE);
		return resultJO;
	}

	/**
	 * 返回处理成功信息
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
	 * @param node
	 * @return
	 */
	private String returnSuccessMessage(ObjectNode node){
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
	 * 根据参数无法　查询到相应的信息
	 * @param errorMsg
	 * @return
	 */
	private String returnNullObjectMsg(String errorMsg){
		
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode node = mapper.createObjectNode();
		node.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_00604);
		node.put(MsgAndCode.RSP_DESC, errorMsg+MsgAndCode.CODE_00604_MSG);
		return node.toString();
	}
}

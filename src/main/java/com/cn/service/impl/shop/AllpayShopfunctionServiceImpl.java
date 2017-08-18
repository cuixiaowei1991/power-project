package com.cn.service.impl.shop;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cn.common.CookieHelper;
import com.cn.dao.shop.AllpayShopuserDao;
import com.cn.entity.po.shop.*;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.ObjectNode;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cn.MsgCode.MsgAndCode;
import com.cn.common.CommonHelper;
import com.cn.common.LogHelper;
import com.cn.dao.shop.AllpayShopfunctionDao;
import com.cn.entity.dto.AllpayFunctionDto;
import com.cn.service.shop.AllpayShopfunctionService;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.plaf.basic.BasicEditorPaneUI;

/**
 * 商助资源（功能）管理业务层实现
 * Created by WangWenFang on 2016/11/30.
 */
@Service
@Transactional(rollbackFor = {Exception.class, RuntimeException.class})
public class AllpayShopfunctionServiceImpl implements AllpayShopfunctionService {

    @Autowired
    private AllpayShopfunctionDao allpayShopfunctionDao;

    @Autowired
    private AllpayShopuserDao allpayShopuserDao;

    public JSONObject getShopFuncInfoList(AllpayFunctionDto bean) throws Exception {
        JSONObject resultJO=new JSONObject();
        if(bean!=null){
            String curragePage=bean.getCurragePage();
            String pageSize=bean.getPageSize();
            if(null==curragePage  || null==pageSize){
                resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_00110);
                resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_00110);
                return resultJO;
            }
            int total = 0;
            JSONArray array=new JSONArray();
            List lists=allpayShopfunctionDao.getShopFuncInfoList(bean, Integer.parseInt(pageSize), Integer.parseInt(curragePage));
            total=allpayShopfunctionDao.count(bean);
            if(lists!=null && lists.size()>0){
                for(Object oj:lists){
                    Map map = (Map)oj;
                    JSONObject object=new JSONObject();
                    object.put("funcId",map.get("SHOPFUNCTION_ID"));
                    object.put("funcName",map.get("SHOPFUNCTION_NAME"));
                    object.put("funcIsStart",map.get("SHOPFUNCTION_STATE"));
                    object.put("funcUrl",map.get("SHOPFUNCTION_URL"));
                    object.put("funcCode",map.get("SHOPFUNCTION_CODE"));
                    object.put("isDefault", CommonHelper.nullToString(map.get("SHOPFUNCTION_ISDEFAULT")));
                    int type=Integer.parseInt(""+map.get("SHOPFUNCTION_TYPE"));
                    object.put("funcType",type);
                    if(type==1 || type==2){
                        String sup_top= map.get("TOP_MENU_NAME")+"_"+map.get("SUP_MENU_NAME");
                        object.put("funcSupMenuName",sup_top);
                    }/*else if(type==2){
                        object.put("funcSupMenuName","功能操作");
                    }*/else{ object.put("funcSupMenuName","");}
                    array.put(object);
                }
            }
            resultJO.put("lists", array);
            resultJO.put("curragePage", curragePage);
            resultJO.put("pageSize", pageSize);
            resultJO.put("total", total);
            resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.SUCCESS_CODE);  //返回的状态码
            resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.SUCCESS_MESSAGE);  //返回的状态码描述
        }else{
            resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_003);
            resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_003);
        }
        return resultJO;
    }

    public JSONObject insertShopFuncInfo(AllpayFunctionDto bean) throws Exception {
        JSONObject resultJO=new JSONObject();
        if(bean!=null){
            if(CommonHelper.isEmpty(bean.getFuncSupSystemId())){
                resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_00512);
                resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_00512);
                return resultJO;
            }
            if(CommonHelper.isEmpty(bean.getFuncName())){
                resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_00513);
                resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_00513);
                return resultJO;
            }
            if(CommonHelper.isEmpty(""+bean.getFuncState())){
                resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_00520);
                resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_00520);
                return resultJO;
            }
            if(CommonHelper.isEmpty(bean.getFuncSupMenuId())){
                resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_00516);
                resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_00516);
                return resultJO;
            }
            if(CommonHelper.isEmpty(bean.getFuncTopMenuId())){
                resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_00517);
                resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_00517);
                return resultJO;
            }
            if(CommonHelper.isEmpty(bean.getFuncType())){
                resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_00514);
                resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_00514);
                return resultJO;
            }else{
                if("1".equals(bean.getFuncType())){  //1--菜单栏目
                    if(CommonHelper.isEmpty(bean.getFuncUrl())){
                        resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_00515);
                        resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_00515);
                        return resultJO;
                    }
                }else if("2".equals(bean.getFuncType())){  //2--功能操作
                    if(CommonHelper.isEmpty(bean.getFuncCode())){
                        resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_00518);
                        resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_00518);
                        return resultJO;
                    }
                }else{
                    resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_00519);
                    resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_00519);
                    return resultJO;
                }
            }
            if(CommonHelper.isEmpty(bean.getIsDefault())){
                resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_00522);
                resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_00522);
                return resultJO;
            }

            AllpayShopfunction function=new AllpayShopfunction();
            function.setShopfunctionName(bean.getFuncName());
            function.setShopfunctionSupSystemid(bean.getFuncSupSystemId());
            function.setShopfunctionType(Integer.parseInt(bean.getFuncType()));
            function.setShopfunctionUrl(bean.getFuncUrl());
            function.setShopfunctionMenuid(bean.getFuncSupMenuId());
            function.setShopfunctionTopMenuid(bean.getFuncTopMenuId());
            function.setShopfunctionOrder(bean.getFuncOrder());
            function.setShopfunctionCode(bean.getFuncCode());
            function.setShopfunctionState(bean.getFuncState());
            function.setIsDefault(bean.getIsDefault());

            JSONObject publicFileds = CommonHelper.getPublicFileds(MsgAndCode.OPERATION_NEW, "", bean.getUserNameFromQXCookie());
            String userName = publicFileds.getString("userName");
            Date now = CommonHelper.getStringToDate(publicFileds.getString("now"), "yyyy-MM-dd HH:mm:ss");
            String record = publicFileds.getString("record");

            function.setALLPAY_CREATER(userName);
            function.setALLPAY_CREATETIME(now);
            function.setALLPAY_UPDATETIME(now);  //修改时间
            function.setALLPAY_LOGICDEL("1");
            function.setALLPAY_LOGRECORD(record);

            boolean result=allpayShopfunctionDao.saveOrUpdate(function);
            if(result){
                resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.SUCCESS_CODE);
                resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.SUCCESS_MESSAGE);
            }
        }else{
            resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_003);
            resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_003);
        }
        return resultJO;
    }

    public String getSupTopMenuName(String function_top_menuId, String function_sup_menuId) throws Exception {
        String result="";
        if(!CommonHelper.isEmpty(function_sup_menuId) && !CommonHelper.isEmpty(function_top_menuId)){
            AllpayShopmenu menu1=allpayShopfunctionDao.getMenuName(function_top_menuId);
            AllpayShopmenu menu2=allpayShopfunctionDao.getMenuName(function_sup_menuId);
            if(menu1!=null){
                result+=menu1.getShopmenuName()+"_";
            }
            if(menu2!=null){
                result+=menu2.getShopmenuName();
            }
        }
        return result;
    }

    public JSONObject updateShopFuncInfo(AllpayFunctionDto bean) throws Exception {
        JSONObject resultJO=new JSONObject();
        if(bean!=null){
            if(CommonHelper.isEmpty(bean.getFuncId())){
                resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_003);
                resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_003);
                return resultJO;
            }
            if(CommonHelper.isEmpty(bean.getFuncName())){
                resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_00513);
                resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_00513);
                return resultJO;
            }
            if(CommonHelper.isEmpty(bean.getFuncSupSystemId())){
                resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_00512);
                resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_00512);
                return resultJO;
            }
            if(CommonHelper.isEmpty(bean.getFuncType())){
                resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_00514);
                resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_00514);
                return resultJO;
            }else{
                if("1".equals(bean.getFuncType())){  //1--菜单栏目
                    if(CommonHelper.isEmpty(bean.getFuncUrl())){
                        resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_00515);
                        resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_00515);
                        return resultJO;
                    }
                }else if("2".equals(bean.getFuncType())){  //2--功能操作
                    if(CommonHelper.isEmpty(bean.getFuncCode())){
                        resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_00518);
                        resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_00518);
                        return resultJO;
                    }
                }else{
                    resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_00519);
                    resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_00519);
                    return resultJO;
                }
            }
            if(CommonHelper.isEmpty(""+bean.getFuncState())){
                resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_00520);
                resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_00520);
                return resultJO;
            }
            if(CommonHelper.isEmpty(bean.getFuncSupMenuId())){
                resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_00516);
                resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_00516);
                return resultJO;
            }
            if(CommonHelper.isEmpty(bean.getFuncTopMenuId())){
                resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_00517);
                resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_00517);
                return resultJO;
            }

            if(CommonHelper.isEmpty(bean.getIsDefault())){
                resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_00522);
                resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_00522);
                return resultJO;
            }
            AllpayShopfunction function=allpayShopfunctionDao.getShopFuncInfoById(bean.getFuncId());
            if(function!=null){
                function.setShopfunctionName(bean.getFuncName());
                function.setShopfunctionSupSystemid(bean.getFuncSupSystemId());
                function.setShopfunctionType(Integer.parseInt(bean.getFuncType()));
                function.setShopfunctionUrl(bean.getFuncUrl());
                function.setShopfunctionMenuid(bean.getFuncSupMenuId());
                function.setShopfunctionTopMenuid(bean.getFuncTopMenuId());
                function.setShopfunctionOrder(bean.getFuncOrder());
                function.setShopfunctionCode(bean.getFuncCode());
                function.setShopfunctionState(bean.getFuncState());
                function.setIsDefault(bean.getIsDefault());

                JSONObject publicFileds = CommonHelper.getPublicFileds(MsgAndCode.OPERATION_UPDATE, function.getALLPAY_LOGRECORD(), bean.getUserNameFromQXCookie());
                String userName = publicFileds.getString("userName");
                Date now = CommonHelper.getStringToDate(publicFileds.getString("now"), "yyyy-MM-dd HH:mm:ss");
                String record = publicFileds.getString("record");

                function.setALLPAY_UPDATER(userName);
                function.setALLPAY_UPDATETIME(now);
                function.setALLPAY_LOGRECORD(record);
                boolean result=allpayShopfunctionDao.saveOrUpdate(function);
                if(result){
                    resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.SUCCESS_CODE);
                    resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.SUCCESS_MESSAGE);
                }
            }else{
                resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_004);
                resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_004);
            }
        }else{
            resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_003);
            resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_003);
        }
        return resultJO;
    }

    public JSONObject deleteShopFuncInfo(AllpayFunctionDto bean) throws Exception {
        JSONObject resultJO=new JSONObject();
        if(bean!=null){
            if(CommonHelper.isEmpty(bean.getFuncId())){
                resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_003);
                resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_003);
                return resultJO;
            }
            AllpayShopfunction function=allpayShopfunctionDao.getShopFuncInfoById(bean.getFuncId());
            if(function!=null){
                JSONObject publicFileds = CommonHelper.getPublicFileds(MsgAndCode.OPERATION_DELETE, function.getALLPAY_LOGRECORD(),bean.getUserNameFromQXCookie());
                String userName = publicFileds.getString("userName");
                Date now = CommonHelper.getStringToDate(publicFileds.getString("now"), "yyyy-MM-dd HH:mm:ss");
                String record = publicFileds.getString("record");

                function.setALLPAY_LOGICDEL("2");
                function.setALLPAY_LOGRECORD(record);
                function.setALLPAY_UPDATER(userName);
                function.setALLPAY_UPDATETIME(now);
                boolean result=allpayShopfunctionDao.saveOrUpdate(function);
                if(result){
                    resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.SUCCESS_CODE);
                    resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.SUCCESS_MESSAGE);
                }
            }else{
                resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_004);
                resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_004);
            }
        }else{
            resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_003);
            resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_003);
        }
        return resultJO;
    }

    public JSONObject getShopFuncInfoById(AllpayFunctionDto bean) throws Exception {
        JSONObject resultJO=new JSONObject();
        if(bean!=null){
            if(CommonHelper.isEmpty(bean.getFuncId())){
                resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_003);
                resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_003);
                return resultJO;
            }
            AllpayShopfunction function=allpayShopfunctionDao.getShopFuncInfoById(bean.getFuncId());
            if(function!=null){
                resultJO.put("funcId", function.getShopfunctionId());
                resultJO.put("funcName", function.getShopfunctionName());
                resultJO.put("funcSupSystemId", function.getShopfunctionSupSystemid());
                resultJO.put("funcType", function.getShopfunctionType());
                resultJO.put("funcUrl", function.getShopfunctionUrl());
                resultJO.put("funcSupMenuId", function.getShopfunctionMenuid());
                resultJO.put("funcTopMenuId", function.getShopfunctionTopMenuid());
                resultJO.put("funcOrder", function.getShopfunctionOrder());
                resultJO.put("funcCode", function.getShopfunctionCode());
                resultJO.put("funcState", function.getShopfunctionState());
                resultJO.put("isDefault", function.getIsDefault());
                resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.SUCCESS_CODE);
                resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.SUCCESS_MESSAGE);
            }else{
                resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_004);
                resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_004);
            }

        }else{
            resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_003);
            resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_003);
        }
        return resultJO;
    }

    @Override
    public JSONObject getShopQxInfoList(Map<String, Object> source) throws Exception {
        JSONObject resultJO=new JSONObject();
        JSONArray array=new JSONArray();
        String shopId = source.get("shopId").toString();
        List list=allpayShopfunctionDao.getShopQxInfoList(shopId);
        HashMap<String,List<Map<String,Object>>> map=new HashMap<String, List<Map<String,Object>>>();
        if (list != null && list.size()>0) {
            for(int i=0;i<list.size();i++){
                Object ob = list.get(i);
                Map exmap = (Map) ob;
                String topMenuName= CommonHelper.nullToString(exmap.get("SHOPMENU_NAME"));
                String topMenuId=CommonHelper.nullToString(exmap.get("SHOPFUNCTION_TOP_MENUID"));
                String key=topMenuId+"_"+topMenuName;
                LogHelper.info("组成的key：" + key);

                if(map.containsKey(key)){
                    List ls=map.get(key) ;
                    ls.add(exmap);
                    map.put(key,ls);
                }else{
                    List ls=new ArrayList();
                    ls.add(exmap);
                    map.put(key,ls);
                }
            }
            if(map!=null && map.size()>0){
                for(Map.Entry<String, List<Map<String,Object>>> entry:map.entrySet()){
                    JSONObject object=new JSONObject();
                    String key=entry.getKey();
                    LogHelper.info("获取到的key==顶级菜单名称：" + key);
                    object.put("funcTopMenuId", key.split("_")[0]);
                    object.put("funcTopMenuName", key.split("_")[1]);
                    JSONArray arr=new JSONArray();
                    List<Map<String,Object>> v_list=entry.getValue();
                    for(int i=0;i<v_list.size()&& v_list.size()>0;i++){
                        JSONObject json=new JSONObject();
                        Map<String,Object> c_map=v_list.get(i);
                        json.put("funcId",CommonHelper.nullToString(c_map.get("SHOPFUNCTION_ID")));
                        json.put("funcName",CommonHelper.nullToString(c_map.get("SHOPFUNCTION_NAME")));
                        json.put("funcType",CommonHelper.nullToString(c_map.get("SHOPFUNCTION_TYPE")));
                        json.put("menuId",CommonHelper.nullToString(c_map.get("SHOPFUNCTION_MENUID")));
                        json.put("systemId",CommonHelper.nullToString(c_map.get("SHOPFUNCTION_SUP_SYSTEMID")));
                        json.put("isDefault",CommonHelper.nullToString(c_map.get("SHOPFUNCTION_ISDEFAULT")));
                        arr.put(json);
                    }
                    object.put("list", arr);
                    array.put(object);
                }
            }
        }
        resultJO.put("lists",array);
        resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.SUCCESS_CODE);
        resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.SUCCESS_MESSAGE);
        return resultJO;
    }

    @Override
    public JSONObject insertRoleInfo(Map<String, Object> source) throws Exception {
        JSONObject resultJO=new JSONObject();

        if(CommonHelper.isNullOrEmpty(source.get("roleType"))){
            resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_00700);
            resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_00700);
            return resultJO;
        }

        String roleType=source.get("roleType").toString();
        allpayShopfunctionDao.deleteRoleMatchFunc(roleType);

        Object obj = source.get("lists");
        if(obj instanceof List<?>){
            List<Object> objOne = (List<Object>)obj;
            for(Object objTwo : objOne){
                if(objTwo instanceof Map<?, ?>){
                    Map<String, Object> objThree = (Map<String, Object>)objTwo;
                    if(CommonHelper.isNullOrEmpty(objThree.get("isReduce"))
                            || CommonHelper.isNullOrEmpty(objThree.get("roleMenuId"))
                            || CommonHelper.isNullOrEmpty(objThree.get("roleFunId"))
                            || CommonHelper.isNullOrEmpty(objThree.get("systemId"))){
                        resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_00701);
                        resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_00701);
                        return resultJO;
                    }
                    insertRoleMatchFunc(objThree,roleType);
                }
            }
        }
        resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.SUCCESS_CODE);
        resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.SUCCESS_MESSAGE);
        return resultJO;
    }

    public JSONObject getMenuFuncInfoList() throws Exception {
        JSONObject resultJO=new JSONObject();
        JSONArray array=new JSONArray();
        List list=allpayShopfunctionDao.getMenuFuncInfoList();
        HashMap<String,List<Map<String,Object>>> map=new HashMap<String, List<Map<String,Object>>>();
        if (list != null && list.size()>0) {
            for(int i=0;i<list.size();i++){
                Object ob = list.get(i);
                Map exmap = (Map) ob;
                String topMenuName= CommonHelper.nullToString(exmap.get("SHOPMENU_NAME"));
                String topMenuId=CommonHelper.nullToString(exmap.get("SHOPFUNCTION_TOP_MENUID"));
                String key=topMenuId+"_"+topMenuName;
                LogHelper.info("组成的key：" + key);

                if(map.containsKey(key)){
                    List ls=map.get(key) ;
                    ls.add(exmap);
                    map.put(key,ls);
                }else{
                    List ls=new ArrayList();
                    ls.add(exmap);
                    map.put(key,ls);
                }
            }
            if(map!=null && map.size()>0){
                for(Map.Entry<String, List<Map<String,Object>>> entry:map.entrySet()){
                    JSONObject object=new JSONObject();
                    String key=entry.getKey();
                    LogHelper.info("获取到的key==顶级菜单名称：" + key);
                    object.put("funcTopMenuId", key.split("_")[0]);
                    object.put("funcTopMenuName", key.split("_")[1]);
                    JSONArray arr=new JSONArray();
                    List<Map<String,Object>> v_list=entry.getValue();
                    for(int i=0;i<v_list.size()&& v_list.size()>0;i++){
                        JSONObject json=new JSONObject();
                        Map<String,Object> c_map=v_list.get(i);
                        json.put("funcId",CommonHelper.nullToString(c_map.get("SHOPFUNCTION_ID")));
                        json.put("funcName",CommonHelper.nullToString(c_map.get("SHOPFUNCTION_NAME")));
                        json.put("funcType",CommonHelper.nullToString(c_map.get("SHOPFUNCTION_TYPE")));
                        json.put("menuId",CommonHelper.nullToString(c_map.get("SHOPFUNCTION_MENUID")));
                        json.put("systemId",CommonHelper.nullToString(c_map.get("SHOPFUNCTION_SUP_SYSTEMID")));
                        json.put("isDefault",CommonHelper.nullToString(c_map.get("SHOPFUNCTION_ISDEFAULT")));
                        arr.put(json);
                    }
                    object.put("list", arr);
                    array.put(object);
                }
            }
        }
        resultJO.put("lists",array);
        resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.SUCCESS_CODE);
        resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.SUCCESS_MESSAGE);
        return resultJO;
    }
    
	/**
	 *　新增商户设置信息
	 */
	@Override
	@SuppressWarnings("unchecked")
	public String insertMerchantMenuAndFunc(Map<String, Object> source) throws Exception {
		
		if(CommonHelper.isNullOrEmpty(source.get("merchantId"))){
			return returnMissParamMessage("merchantId");
		}
		String merchantId = source.get("merchantId").toString();
		allpayShopfunctionDao.deleteForMerchantId(merchantId);
		Object obj = source.get("lists");
		if(obj instanceof List<?>){
			List<Object> objOne = (List<Object>)obj;
			for(Object objTwo : objOne){
				if(objTwo instanceof Map<?, ?>){
					Map<String, Object> objThree = (Map<String, Object>)objTwo;
					if(CommonHelper.isNullOrEmpty(objThree.get("merchantMenuId"))
							|| CommonHelper.isNullOrEmpty(objThree.get("merchantFunId"))
							|| CommonHelper.isNullOrEmpty(objThree.get("systemId"))){
						return returnMissParamMessage("merchantMenuId或者merchantFunId或者systemId");
					}
					insertMenuAndFunc(objThree,merchantId);
				}
			}
		}
		return returnSuccessMessage();
	}

    /**
     * 业务系统调用的 新增商户设置信息
     * @param objThree
     * @param merchantId
     * @throws Exception
     */
	private void insertMenuAndFunc(Map<String, Object> objThree,String merchantId) throws Exception{
		
		AllpayShopmatchfunc shop = new AllpayShopmatchfunc();
		shop.setShopmatchfuncShopid(merchantId);
		shop.setShopmatchfuncSystemid(objThree.get("systemId").toString());
		shop.setShopmatchfuncMenuid(objThree.get("merchantMenuId").toString());
		shop.setShopmatchfuncFunctionid(objThree.get("merchantFunId").toString());

        JSONObject publicFileds = CommonHelper.getPublicFileds(MsgAndCode.OPERATION_NEW, "", ""+objThree.get("userNameFromBusCookie"));
        String userName = publicFileds.getString("userName");
        Date now = CommonHelper.getStringToDate(publicFileds.getString("now"), "yyyy-MM-dd HH:mm:ss");
        String record = publicFileds.getString("record");

	    shop.setALLPAY_CREATER(userName);  //创建人
	    shop.setALLPAY_CREATETIME(now);  //创建时间
	    shop.setALLPAY_UPDATETIME(now);  //修改时间
	    shop.setALLPAY_UPDATER(userName);
	    shop.setALLPAY_LOGICDEL("1");  //逻辑删除标记  1---未删除 2---已删除
	    shop.setALLPAY_LOGRECORD(record);
	    allpayShopfunctionDao.insertMerchantMenuAndFunc(shop);
	}

    private void insertRoleMatchFunc(Map<String, Object> objThree,String roleType) throws Exception{
        AllpayRolematchfunc rolematchfunc=new AllpayRolematchfunc();
        rolematchfunc.setRolematchfuncRole(roleType);
        rolematchfunc.setRolematchfuncFunctionid(CommonHelper.nullToString(objThree.get("roleFunId")));
        rolematchfunc.setRolematchfuncMenuid(CommonHelper.nullToString(objThree.get("roleMenuId")));
        rolematchfunc.setRolematchfuncSystemid(CommonHelper.nullToString(objThree.get("systemId")));
        rolematchfunc.setRolematchfuncIsReduce(CommonHelper.nullToString(objThree.get("isReduce")));

        JSONObject publicFileds = CommonHelper.getPublicFileds(MsgAndCode.OPERATION_NEW, "", ""+objThree.get("userNameFromQXCookie"));
        String userName = publicFileds.getString("userName");
        Date now = CommonHelper.getStringToDate(publicFileds.getString("now"), "yyyy-MM-dd HH:mm:ss");
        String record = publicFileds.getString("record");

        rolematchfunc.setALLPAY_CREATER(userName);  //创建人
        rolematchfunc.setALLPAY_CREATETIME(now);  //创建时间
        rolematchfunc.setALLPAY_UPDATETIME(now);  //修改时间
        rolematchfunc.setALLPAY_UPDATER(userName);
        rolematchfunc.setALLPAY_LOGICDEL("1");  //逻辑删除标记  1---未删除 2---已删除
        rolematchfunc.setALLPAY_LOGRECORD(record);
        allpayShopfunctionDao.insertRoleMatchFunc(rolematchfunc);
    }


	/**
	 * 获取商户设置信息
	 */
	@Override
	public String obtainMerchantMenuAndFuncList(Map<String, Object> source) throws Exception {
		
		if(CommonHelper.isNullOrEmpty(source.get("merchantId"))){
			return returnMissParamMessage("merchantId");
		}
		String merchantId = source.get("merchantId").toString();
		List<Map<String, Object>> list = allpayShopfunctionDao.obtainMerchantMenuAndFuncList(merchantId);
		String merchantName = null;
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode node = mapper.createObjectNode();
		ArrayNode funcArray = mapper.createArrayNode();
		ArrayNode menuArray = mapper.createArrayNode();
		if(!list.isEmpty()){
			for(int t=0;t<list.size();t++){
				Map<String, Object> map = list.get(t);
				if(!CommonHelper.isNullOrEmpty(map.get("MERCHANT_MERCHNAME"))){
					merchantName = map.get("MERCHANT_MERCHNAME").toString();
				}
				if(!CommonHelper.isNullOrEmpty(map.get("SHOPMATCHFUNC_FUNCTIONID"))){
					funcArray.add(map.get("SHOPMATCHFUNC_FUNCTIONID").toString());
				}
				if(!CommonHelper.isNullOrEmpty(map.get("SHOPMATCHFUNC_MENUID"))){
					menuArray.add(map.get("SHOPMATCHFUNC_MENUID").toString());
				}
			}
		}
		node.put("merchantId", merchantId);
		node.put("merchantName", checkIsOrNotNull(merchantName));
		node.put("merchantMenuId", menuArray);
		node.put("merchantFunId", funcArray);
		return returnSuccessMessage(node);
	}

    @Override
    public JSONObject getMenuInfoList(AllpayFunctionDto bean, String flag) throws Exception{
        JSONObject resultJO=new JSONObject();
        if(bean!=null){
            JSONArray array=new JSONArray();
            List<Map<String, Object>> lists=null;
            AllpayShopuser shopuser=allpayShopuserDao.getShopUserByID(bean.getUserId());
            if(Integer.parseInt(shopuser.getShopuserRole())==2){
                lists=(List<Map<String, Object>>)allpayShopfunctionDao.getAllMenuInfoList(bean,flag);
            }else{
                lists=(List<Map<String, Object>>)allpayShopfunctionDao.getMenuInfoList(bean, flag);
            }
            if(lists!=null && lists.size()>0){
                for(Map<String, Object> map:lists){
                    JSONObject object=new JSONObject();
                    object.put("funcId",map.get("SHOPFUNCTION_ID"));  //功能资源id
                    object.put("funcName",map.get("SHOPFUNCTION_NAME"));  //功能名称	显示中文
                    object.put("funcCode",map.get("SHOPFUNCTION_CODE"));  //识别码
                    object.put("funcUrl",map.get("SHOPFUNCTION_URL"));  //菜单栏目url地址
                    object.put("supSystemId",map.get("SHOPFUNCTION_SUP_SYSTEMID"));  //所属系统id
                    object.put("supSystemName",map.get("SYSTEM_NAME"));  //所属系统name
                    object.put("supSystemPath", map.get("SYSTEM_PATH"));	//所属系统 路径path
                    object.put("funcSupMenuId",map.get("SHOPFUNCTION_MENUID"));  //所属菜单id
                    object.put("funcSupMenuName",map.get("SHOPMENU_NAME"));  //所属菜单名称
                    object.put("funcTopMenuId",map.get("SHOPFUNCTION_TOP_MENUID"));  //所属顶级菜单id
                    object.put("funcTopMenuName",map.get("TOP_MENUNAME"));  //所属顶级菜单名称
                    object.put("funcType", map.get("SHOPFUNCTION_TYPE"));	//类型	1--菜单栏目 2---功能操作
                    object.put("topMenuOrder",map.get("TOP_SHOPMENUORDER")); //顶级菜单显示循序
                    object.put("supMenuOrder",map.get("SHOPMENU_ORDER"));	//父级菜单显示循序
                    object.put("funcOrder",map.get("SHOPFUNCTION_ORDER"));	//功能显示循序
                    array.put(object);
                }
            }
            resultJO.put("lists", array);
            resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.SUCCESS_CODE);  //返回的状态码
            resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.SUCCESS_MESSAGE);  //返回的状态码描述
        }else{
            resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_003);
            resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_003);
        }
        return resultJO;
    }

    /**
     *  2.4.1获取角色权限信息列表
     * @param source
     * @return
     * @throws Exception
     */
    @Override
    public JSONObject getRoleFuncList(Map<String, Object> source) throws Exception {
        JSONObject resultJO=new JSONObject();
        if(source!=null){
            JSONArray array=new JSONArray();
            List<Map<String, Object>> lists=null;
            String userType = ""+source.get("userType");
            if(CommonHelper.isEmpty(userType)){
                resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_00114);  //返回的状态码
                resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_00114);  //返回的状态码描述
                return resultJO;
            }
            lists=(List<Map<String, Object>>)allpayShopfunctionDao.getRoleFuncList(userType);
            if(lists!=null && lists.size()>0){
                for(Map<String, Object> map:lists){
                    JSONObject object=new JSONObject();
                    object.put("roleType",map.get("ROLEMATCHFUNC_ROLE"));  //角色type	网点管理员0，网点业务员1 ， 商户管理员2
                    object.put("roleIsStart",map.get("SHOPFUNCTION_STATE"));  //是否启用	1--启用 2--禁用
                    object.put("roleMenuId",map.get("ROLEMATCHFUNC_MENUID"));  //菜单ID	存储数组(多个ID)
                    object.put("roleFunId",map.get("ROLEMATCHFUNC_FUNCTIONID"));  //功能ID	存储数组(多个ID)
                    object.put("isReduce",map.get("ROLEMATCHFUNC_ISREDUCE"));  ////是否减去 默认权限  0减 1否
                    object.put("isDefault",map.get("SHOPFUNCTION_ISDEFAULT"));  //是否是默认功能
                    array.put(object);
                }
            }
            resultJO.put("lists", array);
            resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.SUCCESS_CODE);  //返回的状态码
            resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.SUCCESS_MESSAGE);  //返回的状态码描述
        }else{
            resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_003);
            resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_003);
        }
        return resultJO;
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
	 * 校验　非空
	 * @param obj
	 * @return
	 */
	private String checkIsOrNotNull(Object obj){
		
		if(!CommonHelper.isNullOrEmpty(obj)){
			return obj.toString();
		}
		return "";
	}
}

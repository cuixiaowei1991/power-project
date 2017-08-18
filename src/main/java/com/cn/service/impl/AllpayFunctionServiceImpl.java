package com.cn.service.impl;

import com.cn.MsgCode.MsgAndCode;
import com.cn.common.CommonHelper;
import com.cn.common.CookieHelper;
import com.cn.common.LogHelper;
import com.cn.dao.AllpayFunctionDao;
import com.cn.entity.dto.AllpayFunctionDto;
import com.cn.entity.po.AllpayFunction;
import com.cn.entity.po.AllpayMenu;
import com.cn.service.AllpayFunctionService;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 资源管理 业务 实现层
 * Created by sun.yayi on 2016/11/22.
 */
@Service
@Transactional(rollbackFor = {Exception.class, RuntimeException.class})
public class AllpayFunctionServiceImpl implements AllpayFunctionService {

    @Autowired
    private AllpayFunctionDao allpayFunctionDaoImpl;


    public JSONObject getMenuFuncInfoList() throws Exception {
        JSONObject resultJO=new JSONObject();
        JSONArray array=new JSONArray();
        List list=allpayFunctionDaoImpl.getMenuFuncInfoList();
        HashMap<String,List<Map<String,Object>>> map=new HashMap<String, List<Map<String,Object>>>();
        if (list != null && list.size()>0) {
            for(int i=0;i<list.size();i++){
                Object ob = list.get(i);
                Map exmap = (Map) ob;
                String topMenuName= CommonHelper.nullToString(exmap.get("MENU_NAME"));
                String topMenuId=CommonHelper.nullToString(exmap.get("FUNCTION_TOP_MENUID"));
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
                        json.put("funcId",CommonHelper.nullToString(c_map.get("FUNCTION_ID")));
                        json.put("funcName",CommonHelper.nullToString(c_map.get("FUNCTION_NAME")));
                        json.put("funcType",CommonHelper.nullToString(c_map.get("FUNCTION_TYPE")));
                        json.put("menuId",CommonHelper.nullToString(c_map.get("FUNCTION_SUP_MENUID")));
                        json.put("systemId",CommonHelper.nullToString(c_map.get("FUNCTION_SUP_SYSTEMID")));
                        json.put("isDefault",CommonHelper.nullToString(c_map.get("FUNCTION_ISDEFAULT")));
                        arr.put(json);
                    }
                    object.put("list", arr);
                    array.put(object);
                }
            }
            resultJO.put("lists",array);
            resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.SUCCESS_CODE);
            resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.SUCCESS_MESSAGE);
        }else{
            resultJO.put("lists",array);
            resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_004);
            resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_004);
        }
        return resultJO;
    }

    @Override
    public JSONObject getMenuInfoList(AllpayFunctionDto bean, String flag) throws Exception {
        JSONObject resultJO=new JSONObject();
        if(bean!=null){
            JSONArray array=new JSONArray();
            List<Map<String, Object>> lists=(List<Map<String, Object>>)allpayFunctionDaoImpl.getMenuInfoList(bean, flag);
            if(lists!=null && lists.size()>0){
                for(Map<String, Object> map:lists){
                    JSONObject object=new JSONObject();
                    object.put("funcId",map.get("FUNCTION_ID"));  //功能资源id
                    object.put("funcName",map.get("FUNCTION_NAME"));  //功能名称	显示中文
                    object.put("funcSupMenuId",map.get("FUNCTION_SUP_MENUID"));  //所属菜单id
                    object.put("funcSupMenuName",map.get("MENU_NAME"));  //所属菜单名称
                    object.put("supSystemPath", map.get("SYSTEM_PATH"));	//所属系统 路径path
                    object.put("funcTopMenuId",map.get("FUNCTION_TOP_MENUID"));  //所属顶级菜单id
                    object.put("funcTopMenuName",map.get("TOP_MENUNAME"));  //所属顶级菜单名称
                    object.put("funcType", map.get("FUNCTION_TYPE"));	//类型	1--菜单栏目 2---功能操作
                    object.put("funcUrl",map.get("FUNCTION_URL"));  //菜单栏目url地址
                    object.put("funcCode",map.get("FUNCTION_CODE"));  //识别码
                    object.put("supSystemId",map.get("FUNCTION_SUP_SYSTEMID"));  //所属系统id
                    object.put("supSystemName",map.get("SYSTEM_NAME"));  //所属系统name
                    object.put("topMenuOrder",map.get("TOP_MENUORDER")); //顶级菜单显示循序
                    object.put("supMenuOrder",map.get("MENU_ORDER"));	//父级菜单显示循序
                    object.put("funcOrder",map.get("FUNCTION_ORDER"));	//功能显示循序
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

    public JSONObject getAllpayFuncInfoList(AllpayFunctionDto bean) throws Exception {
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
            List lists=allpayFunctionDaoImpl.getAllpayFuncInfoList(bean,Integer.parseInt(pageSize),Integer.parseInt(curragePage));
            total=allpayFunctionDaoImpl.count(bean);
            if(lists!=null && lists.size()>0){
                for(Object oj:lists){
                    Map map = (Map)oj;
                    JSONObject object=new JSONObject();
                    object.put("funcId",CommonHelper.nullToString(map.get("FUNCTION_ID")));
                    object.put("funcName",CommonHelper.nullToString(map.get("FUNCTION_NAME")));
                    object.put("funcIsStart",CommonHelper.nullToString(map.get("FUNCTION_STATE")));
                    object.put("funcUrl",CommonHelper.nullToString(map.get("FUNCTION_URL")));
                    object.put("funcCode",CommonHelper.nullToString(map.get("FUNCTION_CODE")));
                    object.put("isDefault", CommonHelper.nullToString(map.get("FUNCTION_ISDEFAULT")));
                    int type=Integer.parseInt(""+map.get("FUNCTION_TYPE"));
                    object.put("funcType",type);
                    if(type==1 || type==2){
                        String sup_top= map.get("TOP_MENU_NAME")+ "_" +map.get("SUP_MENU_NAME");
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


    public JSONObject insertFuncInfo(AllpayFunctionDto bean) throws Exception {
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
            if(bean.getFuncState()==0){
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
                }else if("2".equals(bean.getFuncType())){
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
            AllpayFunction function=new AllpayFunction();
            function.setFunction_name(bean.getFuncName());
            function.setFunction_sup_systemId(bean.getFuncSupSystemId());
            function.setFunction_type(Integer.parseInt(bean.getFuncType()));
            function.setFunction_url(bean.getFuncUrl());
            function.setFunction_sup_menuId(bean.getFuncSupMenuId());
            function.setFunction_top_menuId(bean.getFuncTopMenuId());
            function.setFunction_order(bean.getFuncOrder());
            function.setFunction_code(bean.getFuncCode());
            function.setFunction_state(bean.getFuncState());
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

            boolean result=allpayFunctionDaoImpl.saveOrUpdate(function);
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
            AllpayMenu menu1=allpayFunctionDaoImpl.getMenuName(function_top_menuId);
            AllpayMenu menu2=allpayFunctionDaoImpl.getMenuName(function_sup_menuId);
            if(menu1!=null){
                result+=menu1.getMenu_name()+"_";
            }
            if(menu2!=null){
                result+=menu2.getMenu_name();
            }
        }
        return result;
    }

    public JSONObject updateFuncInfo(AllpayFunctionDto bean) throws Exception{
        JSONObject resultJO=new JSONObject();
        if(bean!=null){
            if(CommonHelper.isEmpty(bean.getFuncId())){
                resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_003);
                resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_003);
                return resultJO;
            }
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
            if(bean.getFuncState()==0){
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
                }else if("2".equals(bean.getFuncType())){
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
            AllpayFunction function=allpayFunctionDaoImpl.getAllpayFunctionById(bean.getFuncId());
            if(function!=null){
                function.setFunction_name(bean.getFuncName());
                function.setFunction_sup_systemId(bean.getFuncSupSystemId());
                function.setFunction_type(Integer.parseInt(bean.getFuncType()));
                function.setFunction_url(bean.getFuncUrl());
                function.setFunction_sup_menuId(bean.getFuncSupMenuId());
                function.setFunction_top_menuId(bean.getFuncTopMenuId());
                function.setFunction_order(bean.getFuncOrder());
                function.setFunction_code(bean.getFuncCode());
                function.setFunction_state(bean.getFuncState());
                function.setIsDefault(bean.getIsDefault());

                JSONObject publicFileds = CommonHelper.getPublicFileds(MsgAndCode.OPERATION_UPDATE, function.getALLPAY_LOGRECORD(), bean.getUserNameFromQXCookie());
                String userName = publicFileds.getString("userName");
                Date now = CommonHelper.getStringToDate(publicFileds.getString("now"), "yyyy-MM-dd HH:mm:ss");
                String record = publicFileds.getString("record");

                function.setALLPAY_UPDATER(userName);
                function.setALLPAY_UPDATETIME(now);
                function.setALLPAY_LOGRECORD(record);
                boolean result=allpayFunctionDaoImpl.saveOrUpdate(function);
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

    public JSONObject deleteFuncInfo(AllpayFunctionDto bean) throws Exception {
        JSONObject resultJO=new JSONObject();
        if(bean!=null){
            if(CommonHelper.isEmpty(bean.getFuncId())){
                resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_003);
                resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_003);
                return resultJO;
            }
            AllpayFunction function=allpayFunctionDaoImpl.getAllpayFunctionById(bean.getFuncId());
            if(function!=null){
                function.setALLPAY_LOGICDEL("2");

                JSONObject publicFileds = CommonHelper.getPublicFileds(MsgAndCode.OPERATION_DELETE, function.getALLPAY_LOGRECORD(), bean.getUserNameFromQXCookie());
                String userName = publicFileds.getString("userName");
                Date now = CommonHelper.getStringToDate(publicFileds.getString("now"), "yyyy-MM-dd HH:mm:ss");
                String record = publicFileds.getString("record");

                function.setALLPAY_LOGRECORD(record);
                function.setALLPAY_UPDATER(userName);
                function.setALLPAY_UPDATETIME(now);
                boolean result=allpayFunctionDaoImpl.saveOrUpdate(function);
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

    public JSONObject getAllpayFuncInfoById(AllpayFunctionDto bean) throws Exception {
        JSONObject resultJO=new JSONObject();
        if(bean!=null){
            if(CommonHelper.isEmpty(bean.getFuncId())){
                resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_003);
                resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_003);
                return resultJO;
            }
            AllpayFunction function=allpayFunctionDaoImpl.getAllpayFunctionById(bean.getFuncId());
            if(function!=null){
                resultJO.put("funcId",function.getFunctionId());
                resultJO.put("funcName",function.getFunction_name());
                resultJO.put("funcSupSystemId",function.getFunction_sup_systemId());
                resultJO.put("funcType",function.getFunction_type());
                resultJO.put("funcUrl",function.getFunction_url());
                resultJO.put("funcSupMenuId",function.getFunction_sup_menuId());
                resultJO.put("funcTopMenuId",function.getFunction_top_menuId());
                resultJO.put("funcOrder",function.getFunction_order());
                resultJO.put("funcCode",function.getFunction_code());
                resultJO.put("funcState",function.getFunction_state());
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
}

package com.cn.service.impl.agent;

import com.cn.MsgCode.MsgAndCode;
import com.cn.common.CommonHelper;
import com.cn.common.CookieHelper;
import com.cn.common.LogHelper;
import com.cn.dao.agent.AllpayAgentFunctionDao;
import com.cn.dao.agent.AllpayAgentMenuDao;
import com.cn.dao.agent.AllpayAgentUserDao;
import com.cn.entity.dto.AllpayFunctionDto;
import com.cn.entity.po.agent.AllpayAgentFunction;
import com.cn.entity.po.agent.AllpayAgentMenu;
import com.cn.entity.po.agent.AllpayAgentUser;
import com.cn.entity.po.agent.AllpayAgentUserMatchFunc;
import com.cn.service.agent.AllpayAgentFunctionService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 资源管理 业务 实现层
 * Created by sun.yayi on 2016/11/30.
 */
@Service
@Transactional(rollbackFor = {Exception.class, RuntimeException.class})
public class AllpayAgentFunctionServiceImpl implements AllpayAgentFunctionService {

    @Autowired
    private AllpayAgentFunctionDao allpayAgentFunctionDaoImpl;

    @Autowired
    private AllpayAgentMenuDao allpayAgentMenuDaoImpl;

    @Autowired
    private AllpayAgentUserDao allpayAgentUserDaoImpl;

    public JSONObject getMenuFuncInfoList() throws Exception {
        JSONObject resultJO=new JSONObject();
        JSONArray array=new JSONArray();
        List list=allpayAgentFunctionDaoImpl.getMenuFuncInfoList();
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
                        json.put("isDefault",CommonHelper.nullToString(c_map.get("AGENTFUNCTION_ISDEFAULT")));
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
            List<Map<String, Object>> lists=null;
            AllpayAgentUser agentUser=allpayAgentUserDaoImpl.getAgentUserById(bean.getUserId());
            if("1".equals(agentUser.getAgentuser_issuper())){
                lists=(List<Map<String, Object>>)allpayAgentFunctionDaoImpl.getAllMenuInfoList(bean,flag);
            }else{
                lists=(List<Map<String, Object>>)allpayAgentFunctionDaoImpl.getMenuInfoList(bean, flag);
            }
            if(lists!=null && lists.size()>0){
                for(Map<String, Object> map:lists){
                    JSONObject object=new JSONObject();
                    object.put("funcId",map.get("AGENTFUNCTION_ID"));  //功能资源id
                    object.put("funcName",map.get("AGENTFUNCTION_NAME"));  //功能名称	显示中文
                    object.put("funcCode",map.get("AGENTFUNCTION_CODE"));  //识别码
                    object.put("funcUrl",map.get("AGENTFUNCTION_URL"));  //菜单栏目url地址
                    object.put("supSystemId",map.get("AGENTFUNCTION_SYSTEMID"));  //所属系统id
                    object.put("supSystemName",map.get("SYSTEM_NAME"));  //所属系统name
                    object.put("supSystemPath", map.get("SYSTEM_PATH"));	//所属系统 路径path
                    object.put("funcSupMenuId",map.get("AGENTFUNCTION_MENUID"));  //所属菜单id
                    object.put("funcSupMenuName",map.get("AGENTMENU_NAME"));  //所属菜单名称
                    object.put("funcTopMenuId", map.get("AGENTFUNCTION_TOP_MENUID"));  //所属顶级菜单id
                    object.put("funcTopMenuName",map.get("TOP_MENUNAME"));  //所属顶级菜单名称
                    object.put("funcType", map.get("AGENTFUNCTION_TYPE"));	//类型	1--菜单栏目 2---功能操作
                    object.put("topMenuOrder",map.get("TOP_AGENTMENUORDER")); //顶级菜单显示循序
                    object.put("supMenuOrder",map.get("AGENTMENU_ORDER"));	//父级菜单显示循序
                    object.put("funcOrder",map.get("AGENTFUNCTION_ORDER"));	//功能显示循序
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

    @Override
    public JSONObject getAgentFuncList(AllpayFunctionDto bean) throws Exception {
        JSONObject resultJO=new JSONObject();
        if(bean!=null){
            String agentUserId=bean.getAgentUserId();
            if(CommonHelper.isEmpty(agentUserId)){
                resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_00118);
                resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_00118);
                return resultJO;
            }

            JSONArray array=new JSONArray();
            List<Map<String, Object>> lists=(List<Map<String, Object>>)allpayAgentFunctionDaoImpl.getAgentFuncList(agentUserId);
            if(lists!=null && lists.size()>0){
                for(Map<String, Object> map:lists){
                    JSONObject object=new JSONObject();
                    object.put("userId",map.get("AGENTUSERMATCHFUNC_USERID"));  //用户id
                    object.put("userName",map.get("AGENTUSER_NAME"));  //用户账号
                    object.put("systemId",map.get("AGENTUSERMATCHFUNC_SYSTEMID"));  //系统id
                    object.put("userMenuId",map.get("AGENTUSERMATCHFUNC_MENUID"));  //菜单ID	存储数组(多个ID)
                    object.put("userFunId",map.get("AGENTROLEMATCHFUNC_FUNCID"));  //功能ID	存储数组(多个ID)
                    object.put("isReduce",map.get("AGENTUSERMATCHFUNC_ISREDUCE"));  ////是否减去 默认权限  0减 1否
                    object.put("isDefault",map.get("AGENTFUNCTION_ISDEFAULT"));  //是否是默认功能
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

    @Override
    public JSONObject getAgentQxInfoList(Map<String, Object> source) throws Exception {
        JSONObject resultJO=new JSONObject();
        JSONArray array=new JSONArray();
        String agentId = source.get("agentId").toString();
        List<Map<String, Object>> list=(List<Map<String, Object>>)allpayAgentFunctionDaoImpl.getAgentQxInfoList(agentId);
        HashMap<String,List<Map<String,Object>>> map=new HashMap<String, List<Map<String,Object>>>();
        if (list != null && list.size()>0) {
            for(Map<String, Object> exmap : list){
                String topMenuName= CommonHelper.nullToString(exmap.get("TOP_MENU_NAME"));
                String topMenuId=CommonHelper.nullToString(exmap.get("AGENTFUNCTION_TOP_MENUID"));
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
                        json.put("funcId",CommonHelper.nullToString(c_map.get("AGENTFUNCTION_ID")));
                        json.put("funcName",CommonHelper.nullToString(c_map.get("AGENTFUNCTION_NAME")));
                        json.put("funcType",CommonHelper.nullToString(c_map.get("AGENTFUNCTION_TYPE")));
                        json.put("funcSupMenuId",CommonHelper.nullToString(c_map.get("AGENTFUNCTION_MENUID")));
                        json.put("funcSupMenuName",CommonHelper.nullToString(c_map.get("AGENTMENU_NAME")));
                        json.put("funcSystem",CommonHelper.nullToString(c_map.get("AGENTFUNCTION_SYSTEMID")));
                        json.put("isDefault",CommonHelper.nullToString(c_map.get("AGENTFUNCTION_ISDEFAULT")));
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
            List lists=allpayAgentFunctionDaoImpl.getAllpayFuncInfoList(bean,Integer.parseInt(pageSize),Integer.parseInt(curragePage)-1);
            total=allpayAgentFunctionDaoImpl.count(bean);
            if(lists!=null && lists.size()>0){
                for(Object oj:lists){
                    Map map = (Map)oj;
                    JSONObject object=new JSONObject();
                    object.put("funcId",map.get("AGENTFUNCTION_ID"));
                    object.put("funcName",map.get("AGENTFUNCTION_NAME"));
                    object.put("funcIsStart",map.get("AGENTFUNCTION_STATE"));
                    object.put("funcUrl",map.get("AGENTFUNCTION_URL"));
                    object.put("funcCode",map.get("AGENTFUNCTION_CODE"));
                    object.put("isDefault", CommonHelper.nullToString(map.get("AGENTFUNCTION_ISDEFAULT")));
                    int type=Integer.parseInt(""+map.get("AGENTFUNCTION_TYPE"));
                    object.put("funcType",type);
                    if(type==1 || type==2){
                        String sup_top= map.get("TOP_MENU_NAME")+"_"+map.get("SUP_MENU_NAME");
                        object.put("funcSupMenuName",sup_top);
                    }else{ object.put("funcSupMenuName","");}
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
            AllpayAgentFunction function=new AllpayAgentFunction();
            function.setAgentfunction_name(bean.getFuncName());
            function.setAgentfunction_systemid(bean.getFuncSupSystemId());
            function.setAgentfunction_type(Integer.parseInt(bean.getFuncType()));
            function.setAgentfunction_url(bean.getFuncUrl());
            function.setAgentfunction_menuid(bean.getFuncSupMenuId());
            function.setAgentfunction_top_menuid(bean.getFuncTopMenuId());
            function.setAgentfunction_order(bean.getFuncOrder());
            function.setAgentfunction_code(bean.getFuncCode());
            function.setAgentfunction_state(bean.getFuncState());
            function.setIsDefault(bean.getIsDefault());

            JSONObject publicFileds = CommonHelper.getPublicFileds(MsgAndCode.OPERATION_NEW, "",bean.getUserNameFromQXCookie());
            String userName = publicFileds.getString("userName");
            Date now = CommonHelper.getStringToDate(publicFileds.getString("now"), "yyyy-MM-dd HH:mm:ss");
            String record = publicFileds.getString("record");

            function.setALLPAY_CREATER(userName);
            function.setALLPAY_CREATETIME(now);
            function.setALLPAY_UPDATETIME(now);  //修改时间
            function.setALLPAY_LOGICDEL("1");
            function.setALLPAY_LOGRECORD(record);
            boolean result=allpayAgentFunctionDaoImpl.saveOrUpdate(function);
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

    @Override
    public JSONObject insertAgentUserInfo(Map<String, Object> source) throws Exception {
        JSONObject resultJO=new JSONObject();
        if(CommonHelper.isNullOrEmpty(source.get("userId"))){  //用户id
            resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_00118);
            resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_00118);
            return resultJO;
        }

        String userId=source.get("userId").toString();
        allpayAgentFunctionDaoImpl.deleteAgentUserInfo(userId);

        Object obj = source.get("lists");
        if(obj instanceof List<?>){
            List<Object> objOne = (List<Object>)obj;
            for(Object objTwo : objOne){
                if(objTwo instanceof Map<?, ?>){
                    Map<String, Object> objThree = (Map<String, Object>)objTwo;
                    if(CommonHelper.isNullOrEmpty(objThree.get("isReduce"))
                            || CommonHelper.isNullOrEmpty(objThree.get("userMenuId"))
                            || CommonHelper.isNullOrEmpty(objThree.get("userFunId"))
                            || CommonHelper.isNullOrEmpty(objThree.get("systemId"))){
                        resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_00701);
                        resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_00701);
                        return resultJO;
                    }
                    insertAgentUserMatchFunc(objThree, userId);
                }
            }
        }
        resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.SUCCESS_CODE);
        resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.SUCCESS_MESSAGE);
        return resultJO;
    }

    private void insertAgentUserMatchFunc(Map<String, Object> objThree,String userId) throws Exception{
        AllpayAgentUserMatchFunc agentUserMatchFunc=new AllpayAgentUserMatchFunc();
        agentUserMatchFunc.setAGENTUSERMATCHFUNC_USERID(userId);
        agentUserMatchFunc.setAGENTROLEMATCHFUNC_FUNCID(CommonHelper.nullToString(objThree.get("userFunId")));
        agentUserMatchFunc.setAGENTUSERMATCHFUNC_MENUID(CommonHelper.nullToString(objThree.get("userMenuId")));
        agentUserMatchFunc.setAGENTUSERMATCHFUNC_SYSTEMID(CommonHelper.nullToString(objThree.get("systemId")));
        agentUserMatchFunc.setAgentUserMatchFunc_isReduce(CommonHelper.nullToString(objThree.get("isReduce")));


        JSONObject publicFileds = CommonHelper.getPublicFileds(MsgAndCode.OPERATION_NEW, "", ""+objThree.get("userNameFromQXCookie"));
        String userName = publicFileds.getString("userName");
        Date now = CommonHelper.getStringToDate(publicFileds.getString("now"), "yyyy-MM-dd HH:mm:ss");
        String record = publicFileds.getString("record");

        agentUserMatchFunc.setALLPAY_CREATER(userName);  //创建人
        agentUserMatchFunc.setALLPAY_CREATETIME(now);  //创建时间
        agentUserMatchFunc.setALLPAY_UPDATETIME(now);  //修改时间
        agentUserMatchFunc.setALLPAY_UPDATER(userName);
        agentUserMatchFunc.setALLPAY_LOGICDEL("1");  //逻辑删除标记  1---未删除 2---已删除
        agentUserMatchFunc.setALLPAY_LOGRECORD(record);
        allpayAgentFunctionDaoImpl.insertAgentUserMatchFunc(agentUserMatchFunc);
    }

    public String getSupTopMenuName(String function_top_menuId, String function_sup_menuId) throws Exception {
        String result="";
        if(!CommonHelper.isEmpty(function_sup_menuId) && !CommonHelper.isEmpty(function_top_menuId)){
            AllpayAgentMenu menu1=allpayAgentMenuDaoImpl.getById(function_top_menuId);
            AllpayAgentMenu menu2=allpayAgentMenuDaoImpl.getById(function_sup_menuId);
            if(menu1!=null){
                result+=menu1.getAgentmenu_name()+"_";
            }
            if(menu2!=null){
                result+=menu2.getAgentmenu_name();
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
            AllpayAgentFunction function=allpayAgentFunctionDaoImpl.getAllpayFunctionById(bean.getFuncId());
            if(function!=null){
                function.setAgentfunction_name(bean.getFuncName());
                function.setAgentfunction_systemid(bean.getFuncSupSystemId());
                function.setAgentfunction_type(Integer.parseInt(bean.getFuncType()));
                function.setAgentfunction_url(bean.getFuncUrl());
                function.setAgentfunction_menuid(bean.getFuncSupMenuId());
                function.setAgentfunction_top_menuid(bean.getFuncTopMenuId());
                function.setAgentfunction_order(bean.getFuncOrder());
                function.setAgentfunction_code(bean.getFuncCode());
                function.setAgentfunction_state(bean.getFuncState());
                function.setIsDefault(bean.getIsDefault());

                JSONObject publicFileds = CommonHelper.getPublicFileds(MsgAndCode.OPERATION_UPDATE, function.getALLPAY_LOGRECORD(),bean.getUserNameFromQXCookie());
                String userName = publicFileds.getString("userName");
                Date now = CommonHelper.getStringToDate(publicFileds.getString("now"), "yyyy-MM-dd HH:mm:ss");
                String record = publicFileds.getString("record");

                function.setALLPAY_UPDATER(userName);  //TODO
                function.setALLPAY_UPDATETIME(now);
                function.setALLPAY_LOGRECORD(record);
                boolean result=allpayAgentFunctionDaoImpl.saveOrUpdate(function);
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
            AllpayAgentFunction function=allpayAgentFunctionDaoImpl.getAllpayFunctionById(bean.getFuncId());
            if(function!=null){
                JSONObject publicFileds = CommonHelper.getPublicFileds(MsgAndCode.OPERATION_DELETE, function.getALLPAY_LOGRECORD(),bean.getUserNameFromQXCookie());
                String userName = publicFileds.getString("userName");
                Date now = CommonHelper.getStringToDate(publicFileds.getString("now"), "yyyy-MM-dd HH:mm:ss");
                String record = publicFileds.getString("record");

                function.setALLPAY_LOGRECORD(record);
                function.setALLPAY_UPDATER(userName);  //TODO
                function.setALLPAY_UPDATETIME(now);
                function.setALLPAY_LOGICDEL("2");
                boolean result=allpayAgentFunctionDaoImpl.saveOrUpdate(function);
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
            AllpayAgentFunction function=allpayAgentFunctionDaoImpl.getAllpayFunctionById(bean.getFuncId());
            if(function!=null){
                resultJO.put("funcId", function.getAgentfunction_id());
                resultJO.put("funcName",function.getAgentfunction_name());
                resultJO.put("funcSupSystemId",function.getAgentfunction_systemid());
                resultJO.put("funcType",function.getAgentfunction_type());
                resultJO.put("funcUrl",function.getAgentfunction_url());
                resultJO.put("funcSupMenuId",function.getAgentfunction_menuid());
                resultJO.put("funcTopMenuId",function.getAgentfunction_top_menuid());
                resultJO.put("funcOrder",function.getAgentfunction_order());
                resultJO.put("funcCode", function.getAgentfunction_code());
                resultJO.put("funcState", function.getAgentfunction_state());
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

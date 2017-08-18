package com.cn.service.impl.agent;

import com.cn.MsgCode.MsgAndCode;
import com.cn.common.CommonHelper;
import com.cn.common.LogHelper;
import com.cn.dao.agent.AllpayAgentMatchFuncDao;
import com.cn.entity.dto.AgentFunc;
import com.cn.entity.dto.AllpayAgentMatchFuncDto;
import com.cn.entity.po.agent.AllpayAgentMatchFunc;
import com.cn.service.agent.AllpayAgentMatchFuncService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by sun.yayi on 2016/12/7.
 */
@Service
@org.springframework.transaction.annotation.Transactional(rollbackFor = {Exception.class, RuntimeException.class})
public class AllpayAgentMatchFuncServiceImpl implements AllpayAgentMatchFuncService {

    @Autowired
    private AllpayAgentMatchFuncDao allpayAgentMatchFuncDaoImpl;

    public JSONObject insertAgentSetInfo(AllpayAgentMatchFuncDto bean) throws Exception{
        JSONObject resultJO=new JSONObject();
        if(bean!=null){
            String agentId=bean.getAgentId();
            /*JSONArray array=bean.getLists();
            if(CommonHelper.isEmpty(agentId)){
                resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_00600);
                resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_00600);
                return resultJO;
            }
           for(int i=0;i<array.length();i++){
               JSONObject object=array.getJSONObject(i);
               AllpayAgentMatchFunc allpayAgentMatchFunc=new AllpayAgentMatchFunc();
               allpayAgentMatchFunc.setAgentmatchfunc_agentid(agentId);
               allpayAgentMatchFunc.setAgentmatchfunc_functionid(object.getString("agentFunId"));
               allpayAgentMatchFunc.setAgentmatchfunc_funsystemid(object.getString("systemId"));
               allpayAgentMatchFunc.setAgentmatchfunc_menuid(object.getString("agentMenuId"));
               allpayAgentMatchFunc.setALLPAY_CREATER("");
               Date now = CommonHelper.getNowDateShort();
               allpayAgentMatchFunc.setALLPAY_CREATETIME(now);
               allpayAgentMatchFunc.setALLPAY_UPDATETIME(now);  //修改时间
               allpayAgentMatchFunc.setALLPAY_LOGICDEL("1");
               allpayAgentMatchFunc.setALLPAY_LOGRECORD("人-" + now + "-" + MsgAndCode.OPERATION_NEW);
               allpayAgentMatchFuncDaoImpl.saveOrUpate(allpayAgentMatchFunc);
           }*/
            resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.SUCCESS_CODE);
            resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.SUCCESS_MESSAGE);
        }else{
            resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_003);
            resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_003);
        }
        return resultJO;
    }

    public JSONObject getAgentSetInfo(AllpayAgentMatchFuncDto bean) throws Exception {
        JSONObject resultJO=new JSONObject();
        if(bean!=null){
            String agentId=bean.getAgentId();
            if(CommonHelper.isEmpty(agentId)){
                resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_00600);
                resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_00600);
                return resultJO;
            }
            List<Map<String, Object>> list=allpayAgentMatchFuncDaoImpl.getAgentSetInfo(bean);
            JSONArray array=new JSONArray();
            String agentid="";
            String agentname="";
            if(list!=null && list.size()>0){
                 agentid=CommonHelper.nullToString(list.get(0).get("AGENTMATCHFUNC_AGENTID"));
                 agentname=CommonHelper.nullToString(list.get(0).get("AGENT_NAME"));
                for(int i=0;i<list.size();i++){
                    Map<String, Object> map=list.get(i);
                    JSONObject object=new JSONObject();
                    object.put("agentMenuId",CommonHelper.nullToString(map.get("AGENTMATCHFUNC_MENUID")));
                    object.put("agentFunId",CommonHelper.nullToString(map.get("AGENTMATCHFUNC_FUNCTIONID")));
                    object.put("systemId",CommonHelper.nullToString(map.get("AGENTMATCHFUNC_FUNSYSTEMID")));
                    array.put(object);
                }
            }
            resultJO.put("agentId", agentid);
            resultJO.put("agentName",agentname);
            resultJO.put("lists",array);
            resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.SUCCESS_CODE);
            resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.SUCCESS_MESSAGE);
        }else{
            resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_003);
            resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_003);
        }
        return resultJO;
    }

    /**
     * 业务系统调用的 新增代理商设置信息
     * @param bean
     * @return
     * @throws Exception
     */
    public JSONObject updateAgentSetInfo(AllpayAgentMatchFuncDto bean) throws Exception {
        JSONObject resultJO=new JSONObject();
        if(bean!=null){
            String agentId=bean.getAgentId();
            List<AgentFunc> list=bean.getLists();
            if(list!=null && list.size()>0){
                if(CommonHelper.isEmpty(agentId)){
                    resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_00600);
                    resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_00600);
                    return resultJO;
                }
                List<AllpayAgentMatchFunc> lists=allpayAgentMatchFuncDaoImpl.getListByAgentId(agentId);
                if(lists!=null && lists.size()>0){
                    for(int i=0;i<lists.size();i++){
                        AllpayAgentMatchFunc allpayAgentMatchFunc= lists.get(i);

                        JSONObject publicFileds = CommonHelper.getPublicFileds(MsgAndCode.OPERATION_DELETE, allpayAgentMatchFunc.getALLPAY_LOGRECORD(),bean.getUserNameFromBusCookie());
                        String userName = publicFileds.getString("userName");
                        Date now = CommonHelper.getStringToDate(publicFileds.getString("now"), "yyyy-MM-dd HH:mm:ss");
                        String record = publicFileds.getString("record");

                        allpayAgentMatchFunc.setALLPAY_CREATER(userName);
                        allpayAgentMatchFunc.setALLPAY_UPDATETIME(now);  //修改时间
                        allpayAgentMatchFunc.setALLPAY_LOGICDEL("2");
                        allpayAgentMatchFunc.setALLPAY_LOGRECORD(record);
                        allpayAgentMatchFuncDaoImpl.saveOrUpate(allpayAgentMatchFunc);
                    }
                }
                for(int i=0;i<list.size();i++){
                    AgentFunc agentFunc=list.get(i);
                    AllpayAgentMatchFunc allpayAgentMatchFunc=new AllpayAgentMatchFunc();
                    allpayAgentMatchFunc.setAgentmatchfunc_agentid(agentId);
                    allpayAgentMatchFunc.setAgentmatchfunc_functionid(agentFunc.getAgentFunId());
                    allpayAgentMatchFunc.setAgentmatchfunc_funsystemid(agentFunc.getSystemId());
                    allpayAgentMatchFunc.setAgentmatchfunc_menuid(agentFunc.getAgentMenuId());

                    JSONObject publicFileds = CommonHelper.getPublicFileds(MsgAndCode.OPERATION_UPDATE, allpayAgentMatchFunc.getALLPAY_LOGRECORD(),bean.getUserNameFromQXCookie());
                    String userName = publicFileds.getString("userName");
                    Date now = CommonHelper.getStringToDate(publicFileds.getString("now"), "yyyy-MM-dd HH:mm:ss");
                    String record = publicFileds.getString("record");

                    allpayAgentMatchFunc.setALLPAY_CREATER(userName);
                    allpayAgentMatchFunc.setALLPAY_CREATETIME(now);
                    allpayAgentMatchFunc.setALLPAY_UPDATETIME(now);  //修改时间
                    allpayAgentMatchFunc.setALLPAY_LOGICDEL("1");
                    allpayAgentMatchFunc.setALLPAY_LOGRECORD(record);
                    allpayAgentMatchFuncDaoImpl.saveOrUpate(allpayAgentMatchFunc);
                }
            }
            resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.SUCCESS_CODE);
            resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.SUCCESS_MESSAGE);
        }else{
            resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_003);
            resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_003);
        }
        return resultJO;
    }
}

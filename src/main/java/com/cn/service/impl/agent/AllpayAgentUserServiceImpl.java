package com.cn.service.impl.agent;

import com.alibaba.fastjson.JSONArray;
import com.cn.MsgCode.MsgAndCode;
import com.cn.common.CommonHelper;
import com.cn.common.CookieHelper;
import com.cn.dao.agent.AllpayAgentUserDao;
import com.cn.entity.dto.AllpayUserDto;
import com.cn.entity.po.agent.AllpayAgentFunction;
import com.cn.entity.po.agent.AllpayAgentUser;
import com.cn.service.agent.AllpayAgentUserService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

import static all.union.tools.codec.MD5Helper.md5;

/**
 * Created by sun.yayi on 2017/1/11.
 */
@Service
@Transactional(rollbackFor = {Exception.class, RuntimeException.class})
public class AllpayAgentUserServiceImpl implements AllpayAgentUserService {

    @Autowired
    private AllpayAgentUserDao allpayAgentUserDaoImpl;

    @Override
    public JSONObject insertAgent(AllpayUserDto bean) throws Exception {
        JSONObject resultJO=new JSONObject();
        if(bean!=null){
            if(CommonHelper.isEmpty(bean.getAgentId())){
                resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_00600);
                resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_00600);
                return resultJO;
            }
            if(CommonHelper.isEmpty(bean.getUserName())){
                resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_00112);
                resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_00112);
                return resultJO;
            }
            if(CommonHelper.isEmpty(bean.getUserPhone())){
                resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_00113);
                resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_00113);
                return resultJO;
            }
            AllpayAgentUser agentUser=new AllpayAgentUser();
            agentUser.setAgentuser_agentid(bean.getAgentId());
            agentUser.setAgentuser_nickname(bean.getUserName());
            agentUser.setAgentuser_name(bean.getUserPhone());
            agentUser.setAgentuser_phone(bean.getUserPhone());
            agentUser.setAgentuser_password(md5("111111","utf-8"));
            agentUser.setAgentuser_isstart(1);
            agentUser.setAgentuser_issuper("2");

            JSONObject publicFileds = CommonHelper.getPublicFileds(MsgAndCode.OPERATION_NEW, "",bean.getUserNameFromBusCookie());
            String userName = publicFileds.getString("userName");
            Date now = CommonHelper.getStringToDate(publicFileds.getString("now"), "yyyy-MM-dd HH:mm:ss");
            String record = publicFileds.getString("record");

            agentUser.setALLPAY_CREATER(userName);
            agentUser.setALLPAY_CREATETIME(now);  //创建时间
            agentUser.setALLPAY_UPDATETIME(now);  //修改时间
            agentUser.setALLPAY_UPDATER(userName);
            agentUser.setALLPAY_LOGICDEL("1");  //逻辑删除标记  1---未删除 2---已删除
            agentUser.setALLPAY_LOGRECORD(record);
            boolean result=allpayAgentUserDaoImpl.saveOrUpdate(agentUser);
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
    public JSONObject checkAgentUserExist(Map<String, Object> source) throws Exception {
        JSONObject resultJO=new JSONObject();
        if(source!=null){
            String legalPersonName=CommonHelper.nullToString(source.get("legalPersonName"));
            String lagalPersonPhone=CommonHelper.nullToString(source.get("lagalPersonPhone"));
            if(CommonHelper.isEmpty(legalPersonName)){
                resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_00112);
                resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_00112);
                return resultJO;
            }
            if(CommonHelper.isEmpty(lagalPersonPhone)){
                resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_00113);
                resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_00113);
                return resultJO;
            }
            String isExit="0";
            List<Map<String,Object>> list=allpayAgentUserDaoImpl.isExist(lagalPersonPhone);
            if(list!=null && list.size()>0){
                isExit="1";
            }
            resultJO.put("exist",isExit);
            resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.SUCCESS_CODE);
            resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.SUCCESS_MESSAGE);
        }else{
            resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_003);
            resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_003);
            return resultJO;
        }
        return resultJO;
    }

    @Override
    public JSONObject getList(AllpayUserDto bean) throws Exception {
        JSONObject resultJO=new JSONObject();
        if(bean!=null){
            String agentId = bean.getAgentId();	//代理商id
            Integer curragePage = bean.getCurragePage();	//当前页码
            Integer pageSize = bean.getPageSize();	//每页显示记录条数
            if(CommonHelper.isEmpty(agentId)){
                resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_00600);
                resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_00600);
                return resultJO;
            }
            if(null == curragePage || null == pageSize){
                resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_00110);
                resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_00110);
                return resultJO;
            }
            int total = 0;
            List<Map<String, Object>> list = (List<Map<String, Object>>)allpayAgentUserDaoImpl.getList(bean, curragePage - 1, pageSize);
            total = allpayAgentUserDaoImpl.count(bean);
            JSONArray array = new JSONArray();
            if(null != list && list.size() > 0){
                for(Map<String, Object> map : list){
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("userId", map.get("AGENTUSER_ID"));  //用户id
                    jsonObject.put("agentId",map.get("AGENTUSER_AGENTID"));  //代理商id
                    jsonObject.put("agentName", map.get("AGENT_NAME"));  //代理商名称
                    jsonObject.put("userName", map.get("AGENTUSER_NAME"));  //用户名称
                    jsonObject.put("userPhone", map.get("AGENTUSER_PHONE"));  //用户手机号
                    jsonObject.put("userNickName", map.get("AGENTUSER_NICKNAME"));  //用户昵称
                    jsonObject.put("useIsStart", map.get("AGENTUSER_ISSTART"));   //是否启用 	1--启用 2--禁用
                    jsonObject.put("createTime", map.get("ALLPAY_CREATETIME"));   //创建时间
                    array.add(jsonObject);
                }
            }
            resultJO.put("lists", array);
            resultJO.put("curragePage", curragePage);  //当前页
            resultJO.put("pageSize", pageSize);  //每页显示记录条数
            resultJO.put("total", total);  //数据的总条数
            resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.SUCCESS_CODE);  //返回的状态码
            resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.SUCCESS_MESSAGE);  //返回的状态码描述
        }else{
            resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_003);
            resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_003);
        }
        return resultJO;
    }

    @Override
    public JSONObject insertAgentUser(AllpayUserDto bean) throws Exception {
        JSONObject resultJO=new JSONObject();
        if(bean!=null){
            JSONObject jo = checkPram(bean);
            if(null != jo){
                return jo;
            }
            if(CommonHelper.isEmpty(bean.getUserPassword())){
                resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_00117);
                resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_00117);
                return resultJO;
            }

            AllpayAgentUser agentUser=new AllpayAgentUser();
            agentUser.setAgentuser_agentid(bean.getAgentId());
            agentUser.setAgentuser_name(bean.getUserName());
            agentUser.setAgentuser_nickname(bean.getUserNickName());
            agentUser.setAgentuser_phone(bean.getUserPhone());
            agentUser.setAgentuser_password(md5(bean.getUserPassword(),"utf-8"));
            agentUser.setAgentuser_isstart(Integer.valueOf(bean.getUserIsStart()));
            agentUser.setAgentuser_issuper("2");

            JSONObject publicFileds = CommonHelper.getPublicFileds(MsgAndCode.OPERATION_NEW, "",bean.getUserNameFromAgentCookie());
            String userName = publicFileds.getString("userName");
            Date now = CommonHelper.getStringToDate(publicFileds.getString("now"), "yyyy-MM-dd HH:mm:ss");
            String record = publicFileds.getString("record");

            agentUser.setALLPAY_CREATER(userName);
            agentUser.setALLPAY_CREATETIME(now);  //创建时间
            agentUser.setALLPAY_UPDATETIME(now);  //修改时间
            agentUser.setALLPAY_UPDATER(userName);
            agentUser.setALLPAY_LOGICDEL("1");  //逻辑删除标记  1---未删除 2---已删除
            agentUser.setALLPAY_LOGRECORD(record);
            boolean result=allpayAgentUserDaoImpl.saveOrUpdate(agentUser);
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

    public JSONObject checkPram(AllpayUserDto bean) throws Exception {
        JSONObject resultJO=new JSONObject();
        if(CommonHelper.isEmpty(bean.getAgentId())){
            resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_00600);
            resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_00600);
            return resultJO;
        }
        if(CommonHelper.isEmpty(bean.getUserName())){
            resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_00112);
            resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_00112);
            return resultJO;
        }
        if(CommonHelper.isEmpty(bean.getUserPhone())){
            resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_00113);
            resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_00113);
            return resultJO;
        }
        if(CommonHelper.isEmpty(bean.getUserNickName())){
            resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_00111);
            resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_00111);
            return resultJO;
        }
        if(CommonHelper.isEmpty(bean.getUserIsStart())){
            resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_00116);
            resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_00116);
            return resultJO;
        }
        //判断账号或手机号是否存在
        boolean boo = allpayAgentUserDaoImpl.checkIsExist("AGENTUSER_NAME", bean.getUserName(), bean.getUserId());
        if(boo){
            resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_00100);
            resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_00100);
            return resultJO;
        }
        boolean bool = allpayAgentUserDaoImpl.checkIsExist("AGENTUSER_PHONE", bean.getUserPhone(), bean.getUserId());
        if(bool){
            resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_00101);
            resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_00101);
            return resultJO;
        }
        return null;
    }

    public <T> T getById(Class<T> clazz, String userId) throws Exception {
        return allpayAgentUserDaoImpl.getById(clazz, userId);
    }

    @Override
    public JSONObject update(AllpayUserDto bean) throws Exception {
        JSONObject resultJO=new JSONObject();
        if(bean!=null){
            String userId = bean.getUserId();
            if(CommonHelper.isEmpty(userId)){
                resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_00118);
                resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_00118);
                return resultJO;
            }

            JSONObject jo = checkPram(bean);
            if(null != jo){
                return jo;
            }

            AllpayAgentUser agentUser=getById(AllpayAgentUser.class, userId);
            if(null == agentUser){
                resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_00120);
                resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_00120);
                return resultJO;
            }

            agentUser.setAgentuser_agentid(bean.getAgentId());
            agentUser.setAgentuser_name(bean.getUserName());
            agentUser.setAgentuser_nickname(bean.getUserNickName());
            agentUser.setAgentuser_phone(bean.getUserPhone());
            agentUser.setAgentuser_isstart(Integer.valueOf(bean.getUserIsStart()));

            JSONObject publicFileds = CommonHelper.getPublicFileds(MsgAndCode.OPERATION_UPDATE, "",bean.getUserNameFromAgentCookie());
            String userName = publicFileds.getString("userName");
            Date now = CommonHelper.getStringToDate(publicFileds.getString("now"), "yyyy-MM-dd HH:mm:ss");
            String record = publicFileds.getString("record");

            agentUser.setALLPAY_UPDATETIME(now);  //修改时间
            agentUser.setALLPAY_UPDATER(userName);
            agentUser.setALLPAY_LOGRECORD(record);
            boolean result=allpayAgentUserDaoImpl.saveOrUpdate(agentUser);
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
    public JSONObject delete(AllpayUserDto bean) throws Exception {
        JSONObject resultJO=new JSONObject();
        if(bean!=null){
            String userId = bean.getUserId();
            if(CommonHelper.isEmpty(userId)){
                resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_00118);
                resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_00118);
                return resultJO;
            }

            AllpayAgentUser agentUser=getById(AllpayAgentUser.class, userId);
            if(null == agentUser){
                resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_00120);
                resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_00120);
                return resultJO;
            }

            JSONObject publicFileds = CommonHelper.getPublicFileds(MsgAndCode.OPERATION_DELETE, "",bean.getUserNameFromAgentCookie());
            String userName = publicFileds.getString("userName");
            Date now = CommonHelper.getStringToDate(publicFileds.getString("now"), "yyyy-MM-dd HH:mm:ss");
            String record = publicFileds.getString("record");

            agentUser.setALLPAY_UPDATETIME(now);  //修改时间
            agentUser.setALLPAY_UPDATER(userName);
            agentUser.setALLPAY_LOGRECORD(record);
            agentUser.setALLPAY_LOGICDEL("2");  //逻辑删除标记  1---未删除 2---已删除
            boolean result=allpayAgentUserDaoImpl.saveOrUpdate(agentUser);
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
    public JSONObject get(AllpayUserDto bean) throws Exception {
        JSONObject resultJO=new JSONObject();
        if(bean!=null){
            String userId = bean.getUserId();
            if(CommonHelper.isEmpty(userId)){
                resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_00118);
                resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_00118);
                return resultJO;
            }

            AllpayAgentUser agentUser=getById(AllpayAgentUser.class, userId);
            if(null == agentUser){
                resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_00120);
                resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_00120);
                return resultJO;
            }

            List<Map<String, Object>> agentList = (List<Map<String, Object>>)allpayAgentUserDaoImpl.getAgentInfo(agentUser.getAgentuser_agentid());
            if(null == agentList || agentList.size() == 0){
                resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_00601);
                resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_00601);
                return resultJO;
            }

            resultJO.put("userId", agentUser.getAgentuser_id());	//用户id
            resultJO.put("userNickName", agentUser.getAgentuser_nickname()); //账号(昵称)
            resultJO.put("userName", agentUser.getAgentuser_name());	//用户名称
            resultJO.put("userPhone", agentUser.getAgentuser_phone()); //手机号
            resultJO.put("agentId", agentUser.getAgentuser_agentid());	//代理商id
            resultJO.put("agentName", agentList.get(0).get("AGENT_NAME"));   //代理商名称
            resultJO.put("useIsStart", agentUser.getAgentuser_isstart());	 //是否启用 	1--启用 2--禁用
            resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.SUCCESS_CODE);
            resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.SUCCESS_MESSAGE);
        }else{
            resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_003);
            resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_003);
        }
        return resultJO;
    }

    /**
     * 代理商系统的 修改密码
     * @param source
     * @return
     * @throws Exception
     */
    @Override
    public JSONObject agentUpdatePwd(Map<String, Object> source) throws Exception {
        JSONObject resultJO = new JSONObject();
        if(null == source || source.size() == 0){
            resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_001);
            resultJO.put(MsgAndCode.RSP_DESC, "用户id或密码不能为空！");
            return resultJO;
        }

        String userId = CommonHelper.nullToString(source.get("userId"));    //用户id
        String password = CommonHelper.nullToString(source.get("password")); 	//密码
        String newPassword = CommonHelper.nullToString(source.get("newPassword"));    //新密码
        if(CommonHelper.isEmpty(userId) || CommonHelper.isEmpty(password)){
            resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_001);
            resultJO.put(MsgAndCode.RSP_DESC, "用户id或密码不能为空！");
            return resultJO;
        }
        if(CommonHelper.isEmpty(newPassword)){
            resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_001);
            resultJO.put(MsgAndCode.RSP_DESC, "新密码不能为空！");
            return resultJO;
        }

        //查询代理商用户表
        List<AllpayAgentUser> userList = (List<AllpayAgentUser>)allpayAgentUserDaoImpl.getUser(source);
        if(null == userList || userList.size() == 0){
            resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_001);
            resultJO.put(MsgAndCode.RSP_DESC, "用户id或密码错误！");
            return resultJO;
        }

        //修改密码
        boolean boo = allpayAgentUserDaoImpl.updatePwd(source);
        if(!boo){
            resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_001);
            resultJO.put(MsgAndCode.RSP_DESC, "代理商系统修改密码出现错误！");
            return resultJO;
        }
        resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.SUCCESS_CODE);
        resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.SUCCESS_MESSAGE);
        return resultJO;
    }

    /**
     * 重置密码
     * @param source
     * @return
     * @throws Exception
     */
    @Override
    public JSONObject agentResetPwd(Map<String, Object> source) throws Exception {
        JSONObject resultJO = new JSONObject();
        String userId = CommonHelper.nullToString(source.get("userId"));    //用户id
        String password = CommonHelper.nullToString(source.get("password")); 	//密码
        if(CommonHelper.isEmpty(userId) || CommonHelper.isEmpty(password)){
            resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_001);
            resultJO.put(MsgAndCode.RSP_DESC, "用户id或重置密码不能为空！");
            return resultJO;
        }

        //查询代理商用户表
        AllpayAgentUser agentUser = allpayAgentUserDaoImpl.getById(AllpayAgentUser.class, userId);
        if(null == agentUser){
            resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_00120);
            resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_00120);
            return resultJO;
        }

        String userNameFromBusCookie = CommonHelper.nullToString(source.get("userNameFromBusCookie"));
        JSONObject publicFileds = CommonHelper.getPublicFileds(MsgAndCode.OPERATION_RESETPWD, agentUser.getALLPAY_LOGRECORD(), userNameFromBusCookie);
        String userName = publicFileds.getString("userName");
        Date now = CommonHelper.getStringToDate(publicFileds.getString("now"), "yyyy-MM-dd HH:mm:ss");
        String record = publicFileds.getString("record");

        agentUser.setAgentuser_password(md5(password, "utf-8"));
        agentUser.setALLPAY_UPDATER(userName);  //修改人
        agentUser.setALLPAY_UPDATETIME(now);  //修改时间
        agentUser.setALLPAY_LOGRECORD(record);  //操作日志记录(人物-时间-具体操作，不做清空，追加信息)
        boolean result = allpayAgentUserDaoImpl.saveOrUpdate(agentUser);
        if(result){
            resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.SUCCESS_CODE);
            resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.SUCCESS_MESSAGE);
        }else{
            resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_001);
            resultJO.put(MsgAndCode.RSP_DESC, "重置代理商用户密码失败！");
        }
        return resultJO;
    }
}

package com.cn.service.impl;

import com.cn.MsgCode.MsgAndCode;
import com.cn.common.CommonHelper;
import com.cn.common.LogHelper;
import com.cn.dao.QXUserLoginDao;
import com.cn.entity.po.AllpaySuperUser;
import com.cn.service.QXUserLoginService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 登录管理业务层实现
 * Created by WangWenFang on 2017/1/19.
 */
@Service
@Transactional(rollbackFor = {Exception.class, RuntimeException.class})
public class QXUserLoginServiceImpl implements QXUserLoginService {

    @Autowired
    private QXUserLoginDao loginDao;

    @Override
    public JSONObject getUser(Map<String, Object> source) throws Exception {
        JSONObject resultJO = new JSONObject();
        if(null == source || source.size() == 0){
            resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_00001);
            resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_00001);
            return resultJO;
        }

        String username = ""+source.get("username");    //登录名
        String password = ""+source.get("password"); 	//密码
        if(CommonHelper.isEmpty(username) || CommonHelper.isEmpty(password)){
            resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_00001);
            resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_00001);
            return resultJO;
        }

        //查询权限系统用户表（初始登录用户）
        List<AllpaySuperUser> userList = (List<AllpaySuperUser>)loginDao.getUser(source);
        if(null == userList || userList.size() == 0){
            resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_00002);
            resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_00002);
            return resultJO;
        }
        resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.SUCCESS_CODE);
        resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.SUCCESS_MESSAGE);
        return resultJO;
    }

    @Override
    public JSONObject updatePwd(Map<String, Object> source) throws Exception {
        JSONObject resultJO = getUser(source);
        if(!"000".equals(resultJO.getString("rspCode"))){
            return resultJO;
        }

        String newPassword = ""+source.get("newPassword");    //新密码
        String confirmNewPwd = ""+source.get("confirmNewPwd"); 	//确认新密码
        if(CommonHelper.isEmpty(newPassword) || CommonHelper.isEmpty(confirmNewPwd)){
            resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_001);
            resultJO.put(MsgAndCode.RSP_DESC, "新密码、旧密码不能为空！");
            return resultJO;
        }
        if(!newPassword.equals(confirmNewPwd)){
            resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_001);
            resultJO.put(MsgAndCode.RSP_DESC, "新密码和确认密码不一致！");
            return resultJO;
        }
        //修改密码
        boolean boo = loginDao.updatePwd(source);
        if(!boo){
            resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_001);
            resultJO.put(MsgAndCode.RSP_DESC, "修改密码出现错误！");
            return resultJO;
        }
        resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.SUCCESS_CODE);
        resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.SUCCESS_MESSAGE);
        return resultJO;
    }
}

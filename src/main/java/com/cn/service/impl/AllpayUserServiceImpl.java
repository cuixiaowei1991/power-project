package com.cn.service.impl;

import com.cn.MsgCode.MsgAndCode;
import com.cn.common.CommonHelper;
import com.cn.dao.AllpayUserDao;
import com.cn.entity.dto.AllpayUserDto;
import com.cn.entity.po.AllpayOrganization;
import com.cn.entity.po.AllpayRole;
import com.cn.entity.po.AllpayUser;
import com.cn.service.AllpayUserService;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Observer;

import static all.union.tools.codec.MD5Helper.md5;
import static com.cn.common.CookieHelper.getCookieByName;

/**
 * 用户管理业务层实现
 * Created by WangWenFang on 2016/11/17.
 */
@Service
@Transactional(rollbackFor = {Exception.class, RuntimeException.class})
public class AllpayUserServiceImpl implements AllpayUserService {

    @Autowired
    private AllpayUserDao allpayUserDao;

    public String add(AllpayUserDto allpayUserDto) throws Exception {
        JSONObject resultJO = new JSONObject();
        if(CommonHelper.isEmpty(allpayUserDto.getUserNickName())){
            resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_00111);
            resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_00111);
            return resultJO.toString();
        }
        if(CommonHelper.isEmpty(allpayUserDto.getUserName())){
            resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_00112);
            resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_00112);
            return resultJO.toString();
        }
        if(CommonHelper.isEmpty(allpayUserDto.getUserPhone())){
            resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_00113);
            resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_00113);
            return resultJO.toString();
        }
        if(CommonHelper.isEmpty(allpayUserDto.getUserRoleId())){
            resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_00114);
            resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_00114);
            return resultJO.toString();
        }
        if(CommonHelper.isEmpty(allpayUserDto.getUserOrganizationId())){
            resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_00115);
            resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_00115);
            return resultJO.toString();
        }
        if(CommonHelper.isEmpty(allpayUserDto.getUserIsStart())){
            resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_00116);
            resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_00116);
            return resultJO.toString();
        }
        if(CommonHelper.isEmpty(allpayUserDto.getUserPassword())){
            resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_00117);
            resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_00117);
            return resultJO.toString();
        }
//        if(CommonHelper.isEmpty(getCookieByName("userName"))){
//            resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_002);
//            resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_002);
//            return resultJO.toString();
//        }

        //判断账号、手机号是否存在
        boolean boo = allpayUserDao.checkNameOrPhone("USER_NAME", allpayUserDto.getUserName(), null);
        if(boo){
            resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_00100);
            resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_00100);
            return resultJO.toString();
        }

        boolean bool = allpayUserDao.checkNameOrPhone("USER_PHONE", allpayUserDto.getUserPhone(), null);
        if(bool){
            resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_00101);
            resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_00101);
            return resultJO.toString();
        }

        AllpayUser allpayUser = new AllpayUser();
        allpayUser.setUser_nickName(allpayUserDto.getUserNickName());
        allpayUser.setUser_name(allpayUserDto.getUserName());
        allpayUser.setUser_phone(allpayUserDto.getUserPhone());
        allpayUser.setUser_roleId(allpayUserDto.getUserRoleId());
        allpayUser.setUser_organizationId(allpayUserDto.getUserOrganizationId());
        allpayUser.setUser_isStart(Integer.parseInt(allpayUserDto.getUserIsStart()));
        allpayUser.setUser_password(md5(allpayUserDto.getUserPassword(),"utf-8"));

        JSONObject publicFileds = CommonHelper.getPublicFileds(MsgAndCode.OPERATION_NEW, "", allpayUserDto.getUserNameFromQXCookie());
        String userName = publicFileds.getString("userName");
        Date now = CommonHelper.getStringToDate(publicFileds.getString("now"), "yyyy-MM-dd HH:mm:ss");
        String record = publicFileds.getString("record");

        allpayUser.setALLPAY_CREATER(userName);  //创建人
        allpayUser.setALLPAY_CREATETIME(now);  //创建时间
        allpayUser.setALLPAY_UPDATETIME(now);  //修改时间
        allpayUser.setALLPAY_LOGICDEL("1");  //逻辑删除标记  1---未删除 2---已删除
        allpayUser.setALLPAY_LOGRECORD(record);  //操作日志记录(人物-时间-具体操作，不做清空，追加信息)
        boolean result = allpayUserDao.add(allpayUser);
        if(result){
            resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.SUCCESS_CODE);
            resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.SUCCESS_MESSAGE);
        }else{
            resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_001);
            resultJO.put(MsgAndCode.RSP_DESC, "新增用户信息错误！");
        }
        return resultJO.toString();
    }

    public String delete(AllpayUserDto allpayUserDto) throws Exception {
        JSONObject resultJO = new JSONObject();
        String userId = allpayUserDto.getUserId();
        if(CommonHelper.isEmpty(userId)){
            resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_00118);
            resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_00118);
            return resultJO.toString();
        }
//        if (CommonHelper.isEmpty(getCookieByName("userName"))) {
//            resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_002);
//            resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_002);
//            return resultJO.toString();
//        }

        AllpayUser allpayUser = getById(AllpayUser.class, userId);
        if(null == allpayUser){
            resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_00120);
            resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_00120);
            return resultJO.toString();
        }

        JSONObject publicFileds = CommonHelper.getPublicFileds(MsgAndCode.OPERATION_DELETE, allpayUser.getALLPAY_LOGRECORD(), allpayUserDto.getUserNameFromQXCookie());
        String userName = publicFileds.getString("userName");
        Date now = CommonHelper.getStringToDate(publicFileds.getString("now"), "yyyy-MM-dd HH:mm:ss");
        String record = publicFileds.getString("record");

        //删除用户信息
        allpayUser.setALLPAY_UPDATER(userName);  //修改人
        allpayUser.setALLPAY_UPDATETIME(now);  //修改时间
        allpayUser.setALLPAY_LOGICDEL("2");  //逻辑删除标记  1---未删除 2---已删除
        allpayUser.setALLPAY_LOGRECORD(record);  //操作日志记录(人物-时间-具体操作，不做清空，追加信息)

        boolean result = allpayUserDao.update(allpayUser);
        if(result){
            resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.SUCCESS_CODE);
            resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.SUCCESS_MESSAGE);
        }else{
            resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_001);
            resultJO.put(MsgAndCode.RSP_DESC, "删除用户信息错误！");
        }
        return resultJO.toString();
    }

    public String update(AllpayUserDto allpayUserDto, String flag) throws Exception {
        JSONObject resultJO = new JSONObject();
        String userId = allpayUserDto.getUserId();  //用户id
        if(CommonHelper.isEmpty(userId)){
            resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_00118);
            resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_00118);
            return resultJO.toString();
        }
//        if (CommonHelper.isEmpty(getCookieByName("userName"))) {
//            resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_002);
//            resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_002);
//            return resultJO.toString();
//        }

        //查询用户信息
        AllpayUser allpayUser = getById(AllpayUser.class, userId);//用户id
        if(null == allpayUser){
            resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_00120);
            resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_00120);
            return resultJO.toString();
        }

        String operation = "";
        if("update".equals(flag)){  //修改用户信息
            if(CommonHelper.isEmpty(allpayUserDto.getUserNickName())){
                resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_00111);
                resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_00111);
                return resultJO.toString();
            }
            if(CommonHelper.isEmpty(allpayUserDto.getUserName())){
                resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_00112);
                resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_00112);
                return resultJO.toString();
            }
            if(CommonHelper.isEmpty(allpayUserDto.getUserPhone())){
                resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_00113);
                resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_00113);
                return resultJO.toString();
            }
            if(CommonHelper.isEmpty(allpayUserDto.getUserRoleId())){
                resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_00114);
                resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_00114);
                return resultJO.toString();
            }
            if(CommonHelper.isEmpty(allpayUserDto.getUserOrganizationId())){
                resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_00115);
                resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_00115);
                return resultJO.toString();
            }
            if(CommonHelper.isEmpty(allpayUserDto.getUserIsStart())){
                resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_00116);
                resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_00116);
                return resultJO.toString();
            }

            //判断账号、手机号是否存在
            boolean boo = allpayUserDao.checkNameOrPhone("USER_NAME", allpayUserDto.getUserName(), userId);
            if(boo){
                resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_00100);
                resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_00100);
                return resultJO.toString();
            }

            boolean bool = allpayUserDao.checkNameOrPhone("USER_PHONE", allpayUserDto.getUserPhone(), userId);
            if(bool){
                resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_00101);
                resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_00101);
                return resultJO.toString();
            }

            allpayUser.setUser_nickName(allpayUserDto.getUserNickName());
            allpayUser.setUser_name(allpayUserDto.getUserName());
            allpayUser.setUser_phone(allpayUserDto.getUserPhone());
            allpayUser.setUser_roleId(allpayUserDto.getUserRoleId());
            allpayUser.setUser_organizationId(allpayUserDto.getUserOrganizationId());
            allpayUser.setUser_isStart(Integer.parseInt(allpayUserDto.getUserIsStart()));
            operation = MsgAndCode.OPERATION_UPDATE;
        }else if("resetPassword".equals(flag)){  //重置用户密码
            if(CommonHelper.isEmpty(allpayUserDto.getUserPassword())){  //用户密码
                resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_00117);
                resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_00117);
                return resultJO.toString();
            }

            allpayUser.setUser_password(md5(allpayUserDto.getUserPassword(), "utf-8"));
            operation = MsgAndCode.OPERATION_RESETPWD;
        }else{
            resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_00119);
            resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_00119);
            return resultJO.toString();
        }

        JSONObject publicFileds = CommonHelper.getPublicFileds(operation, allpayUser.getALLPAY_LOGRECORD(), allpayUserDto.getUserNameFromQXCookie());
        String userName = publicFileds.getString("userName");
        Date now = CommonHelper.getStringToDate(publicFileds.getString("now"), "yyyy-MM-dd HH:mm:ss");
        String record = publicFileds.getString("record");

        allpayUser.setALLPAY_UPDATER(userName);  //修改人
        allpayUser.setALLPAY_UPDATETIME(now);  //修改时间
        allpayUser.setALLPAY_LOGRECORD(record);  //操作日志记录(人物-时间-具体操作，不做清空，追加信息)
        boolean result = allpayUserDao.update(allpayUser);
        if(result){
            resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.SUCCESS_CODE);
            resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.SUCCESS_MESSAGE);
        }else{
            resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_001);
            resultJO.put(MsgAndCode.RSP_DESC, "修改用户信息错误！");
        }
        return resultJO.toString();
    }

    public String obtainList(AllpayUserDto allpayUserDto, Class clazz, Integer currentPage, Integer pageSize) throws Exception {
        JSONObject resultJO = new JSONObject();
        if(null == currentPage || null == pageSize){ //当前页码, 每页显示记录条数
            resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_00110);
            resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_00110);
            return resultJO.toString();
        }

        JSONArray array = new JSONArray();
        List list = allpayUserDao.obtainList(allpayUserDto, currentPage, pageSize);
        int total = count(allpayUserDto);
        if(null != list && list.size() > 0){
            for(int i=0,count=list.size(); i<count; i++){
                JSONObject allpayUserJO = new JSONObject();
                Object object = list.get(i);
                Map map = (Map)object;

                allpayUserJO.put("userId", map.get("USER_ID"));  //用户id
                allpayUserJO.put("userName", map.get("USER_NAME"));	//用户名称
                allpayUserJO.put("userPhone", map.get("USER_PHONE"));	//用户手机号
                allpayUserJO.put("roleName", map.get("ROLE_NAME"));	//用户角色名称
                allpayUserJO.put("userRoleId", CommonHelper.nullToString(map.get("USER_ROLEID")));	//用户角色ID
                allpayUserJO.put("userOrganizationId", map.get("USER_ORGANIZATIONID"));	//用户组织机构ID
                allpayUserJO.put("organizationName", CommonHelper.nullToString(map.get("ORGANIZATION_NAME")));	     //组织名称（部门）
                allpayUserJO.put("useIsStart", map.get("USER_ISSTART"));	//是否启用 1--启用 2--禁用
                array.put(allpayUserJO);
            }
        }
        resultJO.put("lists", array);
        resultJO.put("curragePage", allpayUserDto.getCurragePage());  //当前页
        resultJO.put("pageSize", allpayUserDto.getPageSize());  //每页显示记录条数
        resultJO.put("total", total);  //数据的总条数
        resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.SUCCESS_CODE);  //返回的状态码
        resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.SUCCESS_MESSAGE);  //返回的状态码描述
        return resultJO.toString();
    }

    public <T> T getById(Class<T> clazz, String userId) throws Exception {
        return allpayUserDao.getById(clazz, userId);
    }

    public String getById(AllpayUserDto allpayUserDto) throws Exception {
        String userId = allpayUserDto.getUserId();
        JSONObject resultJO = new JSONObject();
        if(CommonHelper.isEmpty(userId)){
            resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_00118);
            resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_00118);
            return resultJO.toString();
        }
        AllpayUser allpayUser = getById(AllpayUser.class, userId);
        if(null == allpayUser){
            resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_00120);
            resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_00120);
            return resultJO.toString();
        }

        //返回前端信息
        String userRoleName = "";
        AllpayRole allpayRole = getById(AllpayRole.class, allpayUser.getUser_roleId());
        if(null != allpayRole){
            userRoleName = allpayRole.getRole_name();
        }
        String userOrganizationName = "";
        AllpayOrganization allpayOrganization = getById(AllpayOrganization.class, allpayUser.getUser_organizationId());
        if(null != allpayOrganization){
            userOrganizationName = allpayOrganization.getOrganization_name();
        }
        resultJO.put("userId", userId);	//用户id
        resultJO.put("userNickName", allpayUser.getUser_nickName());	//账号(昵称)
        resultJO.put("userName", allpayUser.getUser_name()); //用户名称
        resultJO.put("userPhone", allpayUser.getUser_phone());	//手机号
        resultJO.put("roleName",userRoleName);	//用户角色名称
        resultJO.put("userRoleId", allpayUser.getUser_roleId());	//用户角色ID
        resultJO.put("userOrganizationId", allpayUser.getUser_organizationId());	//用户组织机构ID
        resultJO.put("organizationName", userOrganizationName);	     //组织名称（部门）
        resultJO.put("useIsStart", allpayUser.getUser_isStart());	     //是否启用 	1--启用 2--禁用
        resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.SUCCESS_CODE);   //apay返回的状态码	000 为正常返回,其他为异常返回
        resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.SUCCESS_MESSAGE); //apay返回的状态码描述	异常返回时的异常说明
        return resultJO.toString();
    }

    public int count(AllpayUserDto allpayUserDto) throws Exception {
        return allpayUserDao.count(allpayUserDto);
    }

    /**
     * 业务系统的 修改密码
     * @param source
     * @return
     * @throws Exception
     */
    @Override
    public JSONObject bizUpdatePwd(Map<String, Object> source) throws Exception {
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

        //查询业务用户表
        List<AllpayUser> userList = (List<AllpayUser>)allpayUserDao.getUser(source);
        if(null == userList || userList.size() == 0){
            resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_001);
            resultJO.put(MsgAndCode.RSP_DESC, "用户id或密码错误！");
            return resultJO;
        }

        //修改密码
        boolean boo = allpayUserDao.updatePwd(source);
        if(!boo){
            resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_001);
            resultJO.put(MsgAndCode.RSP_DESC, "业务修改密码出现错误！");
            return resultJO;
        }
        resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.SUCCESS_CODE);
        resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.SUCCESS_MESSAGE);
        return resultJO;
    }
}

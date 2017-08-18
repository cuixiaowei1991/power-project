package com.cn.dao.impl;

import com.cn.MsgCode.MsgAndCode;
import com.cn.common.CommonHelper;
import com.cn.dao.AllpayUserDao;
import com.cn.dao.util.HibernateDAO;
import com.cn.entity.dto.AllpayUserDto;
import com.cn.entity.po.AllpayUser;
import org.hibernate.criterion.*;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

import static all.union.tools.codec.MD5Helper.md5;

/**
 * 用户管理dao层实现
 * Created by WangWenFang on 2016/11/17.
 */
@Repository("allpayUserDao")
@Transactional
public class AllpayUserDaoImpl implements AllpayUserDao {

    @Autowired
    private HibernateDAO hibernateDAO;

    public boolean add(AllpayUser allpayUser) throws Exception {
        String result =  hibernateDAO.saveOrUpdate(allpayUser);
        if("success".equals(result)){
            return true;
        }
        return false;
    }

    public boolean delete(String userId) throws Exception {
        String result = hibernateDAO.removeById(AllpayUser.class, userId);
        if("success".equals(result)){
            return true;
        }
        return false;
    }

    public boolean update(AllpayUser allpayUser) throws Exception {
        String result =  hibernateDAO.saveOrUpdate(allpayUser);
        if("success".equals(result)){
            return true;
        }
        return false;
    }

    public <T> List<?> obtainList(AllpayUserDto allpayUserDto, Integer currentPage, Integer pageSize) throws Exception {
        StringBuffer sql = new StringBuffer("SELECT U.USER_ID, U.USER_NAME, U.USER_PHONE, U.USER_ROLEID, U.USER_ORGANIZATIONID, U.USER_ISSTART, R.ROLE_NAME, O.ORGANIZATION_NAME ");
        sql.append("FROM ALLPAY_USER U LEFT JOIN ALLPAY_ROLE R ON U.USER_ROLEID = R.ROLE_ID ")
                .append("LEFT JOIN ALLPAY_ORGANIZATION O ON U.USER_ORGANIZATIONID = O.ORGANIZATION_ID WHERE 1=1 ");

        if(!CommonHelper.isEmpty(allpayUserDto.getUserName())){ //用户名称
            sql.append("AND U.USER_NAME LIKE '%").append(allpayUserDto.getUserName()).append("%' ");
        }
        if(!CommonHelper.isEmpty(allpayUserDto.getUserPhone())){ //用户手机号
            sql.append("AND U.USER_PHONE = '").append(allpayUserDto.getUserPhone()).append("' ");
        }
        if(!CommonHelper.isEmpty(allpayUserDto.getUserRoleId())){ //用户角色ID
            sql.append("AND U.USER_ROLEID = '").append(allpayUserDto.getUserRoleId()).append("' ");
        }
        if(!CommonHelper.isEmpty(allpayUserDto.getUserOrganizationId())){ //用户组织机构ID
            sql.append("AND U.USER_ORGANIZATIONID = '").append(allpayUserDto.getUserOrganizationId()).append("' ");
        }
        if(!CommonHelper.isEmpty(allpayUserDto.getUserIsStart())){ //是否启用	1--启用 2--禁用
            sql.append("AND U.USER_ISSTART = ").append(allpayUserDto.getUserIsStart());
        }
        sql.append(" AND U.ALLPAY_LOGICDEL=1 ORDER BY U.ALLPAY_UPDATETIME DESC");  //ALLPAY_LOGICDEL 1 未删除
        return hibernateDAO.listByPageBySQL(sql.toString(), pageSize, currentPage, false);
    }

    public <T> T getById(Class<T> clazz, String userId) throws Exception {
        return hibernateDAO.listById(clazz, userId, false);
    }

    public int count(AllpayUserDto allpayUserDto) throws Exception {
        StringBuffer sql = new StringBuffer("SELECT U.USER_ID ");
        sql.append("FROM ALLPAY_USER U LEFT JOIN ALLPAY_ROLE R ON U.USER_ROLEID = R.ROLE_ID ")
                .append("LEFT JOIN ALLPAY_ORGANIZATION O ON U.USER_ORGANIZATIONID = O.ORGANIZATION_ID WHERE 1=1 ");

        if(!CommonHelper.isEmpty(allpayUserDto.getUserName())){ //用户名称
            sql.append("AND U.USER_NAME LIKE '%").append(allpayUserDto.getUserName()).append("%' ");
        }
        if(!CommonHelper.isEmpty(allpayUserDto.getUserPhone())){ //用户手机号
            sql.append("AND U.USER_PHONE = '").append(allpayUserDto.getUserPhone()).append("' ");
        }
        if(!CommonHelper.isEmpty(allpayUserDto.getUserRoleId())){ //用户角色ID
            sql.append("AND U.USER_ROLEID = '").append(allpayUserDto.getUserRoleId()).append("' ");
        }
        if(!CommonHelper.isEmpty(allpayUserDto.getUserOrganizationId())){ //用户组织机构ID
            sql.append("AND U.USER_ORGANIZATIONID = '").append(allpayUserDto.getUserOrganizationId()).append("' ");
        }
        if(!CommonHelper.isEmpty(allpayUserDto.getUserIsStart())){ //是否启用	1--启用 2--禁用
            sql.append("AND U.USER_ISSTART = ").append(allpayUserDto.getUserIsStart());
        }
        sql.append(" AND U.ALLPAY_LOGICDEL=1");  //ALLPAY_LOGICDEL 1 未删除
        return hibernateDAO.CountBySQL(sql.toString());
    }

    @Override
    public boolean checkNameOrPhone(String field, String value, String userId) throws Exception {
        StringBuilder together = new StringBuilder();
        together.append("SELECT U.USER_ID FROM ALLPAY_USER U WHERE 1=1");
        if(!CommonHelper.isEmpty(value)){
            together.append(" AND U.").append(field).append("='").append(value).append("'");
        }
        together.append(" AND U.ALLPAY_LOGICDEL = 1");
        List<Map<String, Object>> list = (List<Map<String, Object>>)
                hibernateDAO.listBySQL(together.toString(), false);
        if(!list.isEmpty()){
            Map<String, Object> map = list.get(0);
            if(CommonHelper.isNullOrEmpty(userId)){
                return true;
            }else if(!(userId.equals(map.get("USER_ID")))) {
                return true;
            }
            return false;
        }
        return false;
    }

    @Override
    public List<?> getUser(Map<String, Object> source) throws Exception {
        String hql = "from AllpayUser where userId = '"+source.get("username")+"' and user_password = '"+md5(source.get("password").toString(),"utf-8")+"' AND ALLPAY_LOGICDEL = 1";
        return hibernateDAO.listByHQL(hql);
    }

    @Override
    public boolean updatePwd(Map<String, Object> source) throws Exception {
        String userNameFromAgentCookie = CommonHelper.nullToString(source.get("UserNameFromBusCookie"));
        JSONObject publicFileds = CommonHelper.getPublicFileds(MsgAndCode.OPERATION_UPDATEPWD, "", userNameFromAgentCookie);
        String userName = publicFileds.getString("userName");
        String now = publicFileds.getString("now");
        String record = publicFileds.getString("record");

        StringBuffer sql = new StringBuffer("UPDATE ALLPAY_USER SU SET SU.USER_PASSWORD = '").append(md5(source.get("newPassword").toString(), "utf-8")).append("',");
        sql.append(" SU.ALLPAY_UPDATETIME = TO_DATE('").append(now).append("', 'YYYY-MM-DD HH24:MI:SS'),")
                .append(" SU.ALLPAY_UPDATER = '").append(userName).append("',")
                .append(" SU.ALLPAY_LOGRECORD = '").append(record).append("'")
                .append(" WHERE SU.USER_ID = '").append(source.get("username")).append("'")
                .append(" AND SU.USER_PASSWORD = '").append(md5(source.get("password").toString(), "utf-8")).append("'")
                .append(" AND ALLPAY_LOGICDEL = 1");
        String result = hibernateDAO.executeBySql(sql.toString());
        if ("success".equals(result)){
            return true;
        }
        return false;
    }
}

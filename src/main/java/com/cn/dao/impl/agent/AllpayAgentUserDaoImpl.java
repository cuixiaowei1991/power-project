package com.cn.dao.impl.agent;

import com.cn.MsgCode.MsgAndCode;
import com.cn.common.CommonHelper;
import com.cn.dao.agent.AllpayAgentUserDao;
import com.cn.dao.util.HibernateDAO;
import com.cn.entity.dto.AllpayUserDto;
import com.cn.entity.po.agent.AllpayAgentUser;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

import static all.union.tools.codec.MD5Helper.md5;

/**
 * Created by sun.yayi on 2017/1/11.
 */
@Repository
public class AllpayAgentUserDaoImpl implements AllpayAgentUserDao {
    @Autowired
    private HibernateDAO hibernateDAO;

    @Override
    public AllpayAgentUser getAgentUserById(String agentId) throws Exception {
        AllpayAgentUser agentUser=null;
        agentUser=hibernateDAO.listById(AllpayAgentUser.class,agentId,false);
        return agentUser;
    }

    @Override
    public boolean saveOrUpdate(AllpayAgentUser agentUser) throws Exception {
        String result =  hibernateDAO.saveOrUpdate(agentUser);
        if("success".equals(result)){
            return true;
        }
        return false;
    }

    @Override
    public List<Map<String, Object>> isExist(String phone) throws Exception {
        String sql="SELECT AU.AGENTUSER_ID FROM ALLPAY_AGENTUSER AU WHERE AU.AGENTUSER_PHONE='"+phone+"' AND AU.ALLPAY_LOGICDEL=1";
        List<Map<String,Object>> list=null;
        list= (List<Map<String, Object>>) hibernateDAO.listBySQL(sql.toString(),false);
        return list;
    }

    @Override
    public List<?> getList(AllpayUserDto bean,Integer currentPage,Integer pageSize) throws Exception {
        StringBuffer sql = new StringBuffer("SELECT AU.AGENTUSER_ID, AU.AGENTUSER_AGENTID, AU.AGENTUSER_NAME, AU.AGENTUSER_NICKNAME, AU.AGENTUSER_PHONE, AU.AGENTUSER_ISSTART,AA.AGENT_NAME,TO_CHAR(AU.ALLPAY_CREATETIME, 'YYYY-MM-DD HH24:MI:SS') ALLPAY_CREATETIME");
        sql.append(getSql(bean));
        sql.append(" ORDER BY AU.ALLPAY_UPDATETIME DESC");  //ALLPAY_LOGICDEL 1 未删除
        return hibernateDAO.listByPageBySQL(sql.toString(), pageSize, currentPage, false);
    }

    public int count(AllpayUserDto bean) throws Exception {
        StringBuffer sql = new StringBuffer("SELECT AU.AGENTUSER_ID");
        sql.append(getSql(bean));
        return hibernateDAO.CountBySQL(sql.toString());
    }

    public String getSql(AllpayUserDto bean){
        StringBuffer sql = new StringBuffer(" FROM ALLPAY_AGENTUSER AU LEFT JOIN ALLPAY_AGENT AA ON AU.AGENTUSER_AGENTID = AA.AGENT_ID AND AA.ALLPAY_LOGICDEL = 1 WHERE 1=1");
        if(!CommonHelper.isEmpty(bean.getAgentId())){ //代理商id
            sql.append(" AND AU.AGENTUSER_AGENTID = '").append(bean.getAgentId()).append("' ");
        }
        if(!CommonHelper.isEmpty(bean.getUserName())) {    //用户账号
            sql.append(" AND AU.AGENTUSER_NAME like '%").append(bean.getUserName()).append("%' ");
        }
        if(!CommonHelper.isEmpty(bean.getUserPhone())) {    //用户手机号
            sql.append(" AND AU.AGENTUSER_PHONE = '").append(bean.getUserPhone()).append("' ");
        }
        if(!CommonHelper.isEmpty(bean.getUserNickName())) {    //用户姓名
            sql.append(" AND AU.AGENTUSER_NICKNAME like '%").append(bean.getUserNickName()).append("%' ");
        }
        sql.append(" AND AU.ALLPAY_LOGICDEL=1");  //ALLPAY_LOGICDEL 1 未删除
        return sql.toString();
    }

    public <T> T getById(Class<T> clazz, String userId) throws Exception {
        return hibernateDAO.listById(clazz, userId, false);
    }

    @Override
    public List<?> getAgentInfo(String agentId) {
        String sql="SELECT AA.AGENT_NAME FROM ALLPAY_AGENT AA" +
                " WHERE AA.AGENT_ID='"+agentId+"' AND AA.ALLPAY_LOGICDEL = 1";
        List<?> list=hibernateDAO.listBySQL(sql, false);
        return list;
    }

    @Override
    public List<?> getUser(Map<String, Object> source) throws Exception {
        String hql = "from AllpayAgentUser where agentuser_id = '"+source.get("userId")+"' and agentuser_password = '"+md5(source.get("password").toString(), "utf-8")+"' AND ALLPAY_LOGICDEL = 1";
        return hibernateDAO.listByHQL(hql);
    }

    @Override
    public boolean updatePwd(Map<String, Object> source) throws Exception {
        String userNameFromAgentCookie = CommonHelper.nullToString(source.get("UserNameFromAgentCookie"));
        JSONObject publicFileds = CommonHelper.getPublicFileds(MsgAndCode.OPERATION_UPDATEPWD, "", userNameFromAgentCookie);
        String userName = publicFileds.getString("userName");
        String now = publicFileds.getString("now");
        String record = publicFileds.getString("record");

        StringBuffer sql = new StringBuffer("UPDATE ALLPAY_AGENTUSER U SET U.AGENTUSER_PASSWORD = '").append(md5(source.get("newPassword").toString(), "utf-8")).append("',");
        sql.append(" U.ALLPAY_UPDATETIME = TO_DATE('").append(now).append("', 'YYYY-MM-DD HH24:MI:SS'),")
                .append(" U.ALLPAY_UPDATER = '").append(userName).append("',")
                .append(" U.ALLPAY_LOGRECORD = '").append(record).append("'")
                .append(" WHERE U.AGENTUSER_ID = '").append(source.get("userId")).append("'")
                .append(" AND U.AGENTUSER_PASSWORD = '").append(md5(source.get("password").toString(), "utf-8")).append("'")
                .append(" AND U.ALLPAY_LOGICDEL = 1");
        String result = hibernateDAO.executeBySql(sql.toString());
        if ("success".equals(result)){
            return true;
        }
        return false;
    }

    /**
     * 判断code字段的value值是否存在
     * @param code
     * @param value
     * @param id
     * @return
     * @throws Exception
     */
    public boolean checkIsExist(String code, String value, String id) throws Exception {
        String sql = "SELECT U.AGENTUSER_ID FROM ALLPAY_AGENTUSER U WHERE U."+code+" = '"+value+"' AND U.ALLPAY_LOGICDEL = 1";
        List<Map<String, Object>> list = (List<Map<String, Object>>)hibernateDAO.listBySQL(sql, false);
        if(!list.isEmpty()){
            Map<String, Object> map = list.get(0);
            if(CommonHelper.isNullOrEmpty(id)){
                return true;
            }else if(!(id.equals(map.get("AGENTUSER_ID")))) {
                return true;
            }
            return false;
        }
        return false;
    }
}

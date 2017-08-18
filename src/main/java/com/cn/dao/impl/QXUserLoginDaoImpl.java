package com.cn.dao.impl;

import com.cn.dao.QXUserLoginDao;
import com.cn.dao.util.HibernateDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 登录管理dao层实现
 * Created by WangWenFang on 2017/1/19.
 */
@Repository("loginDao")
@Transactional
public class QXUserLoginDaoImpl implements QXUserLoginDao {

    @Autowired
    private HibernateDAO hibernateDao;

    @Override
    public List<?> getUser(Map<String, Object> source) throws Exception {
        String hql = "from AllpaySuperUser where superuser_name = '"+source.get("username")+"' and superuser_password = '"+source.get("password")+"' AND ALLPAY_LOGICDEL = 1";
        return hibernateDao.listByHQL(hql);
    }

    @Override
    public boolean updatePwd(Map<String, Object> source) throws Exception {
        String sql = "UPDATE ALLPAY_SUPERUSER SU SET SU.SUPERUSER_PASSWORD = '"+source.get("newPassword")+"' WHERE SU.SUPERUSER_NAME = '"
                +source.get("username")+"' AND SU.SUPERUSER_PASSWORD = '"+source.get("password")+"' AND ALLPAY_LOGICDEL = 1";
        String result = hibernateDao.executeBySql(sql);
        if ("success".equals(result)){
            return true;
        }
        return false;
    }
}

package com.cn.dao.impl;

import com.cn.dao.userTestDao;
import com.cn.dao.util.HibernateDAO;
import com.cn.entity.UserTest;
import org.apache.poi.hssf.record.formula.functions.T;
import org.hibernate.Session;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 *
 * 所有持久层均在daoimpl中处理
 * Created by cuixiaowei on 2016/11/8.
 */

@Transactional
@Repository("userTestDao")
public class userTestDaoImpl implements userTestDao {

    @Autowired
    private HibernateDAO hibernateDAO;

    public UserTest getuserTest(String aa) {
        /*方法一*/
        Session session= hibernateDAO.sessionFactory.getCurrentSession();
        //(UserTest) session.get(UserTest.class,aa)


        /*方法二*/
        List<UserTest> userTests= hibernateDAO.listByAll(UserTest.class,true);

        return (UserTest) session.get(UserTest.class,aa);
    }
}

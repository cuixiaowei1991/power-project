package com.cn.service.impl;

import com.cn.dao.userTestDao;
import com.cn.entity.UserTest;
import com.cn.service.userTestservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by cuixiaowei on 2016/11/8.
 */

/*所有业务逻辑在该层处理*/

@Service
public class userTestserviceImpl implements userTestservice {

    @Autowired
    private userTestDao uu;
    public UserTest getUserTest(String usernum) {

        return uu.getuserTest(usernum);
    }
}

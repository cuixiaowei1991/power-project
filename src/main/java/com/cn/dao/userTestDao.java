package com.cn.dao;

import com.cn.entity.UserTest;
import org.apache.poi.hssf.record.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by cuixiaowei on 2016/11/8.
 */
public interface userTestDao {
    public UserTest getuserTest(String aa);
}

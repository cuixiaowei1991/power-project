package com.cn.controller;

import com.cn.common.LogHelper;


import com.cn.entity.UserTest;
import com.cn.service.userTestservice;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Map;

import static com.cn.common.CookieHelper.getCookieByName;

/**
 * Created by cuixiaowei on 2016/11/8.
 */

/*controller只提供方法调用，不做任何业务逻辑处理*/

@Controller
@RequestMapping("/user")
public class userTestController  {

    @Autowired
    private userTestservice us;


    @ResponseBody
    @RequestMapping(value="/get",produces="application/json;charset=UTF-8")
    public Object getUserTest(@RequestBody Map<String, Object> source)
    {


        LogHelper.info("测试iaaanfo");
        System.out.println("++++++" + source);
//        MailSenderHelper.sendMail("测试主题", "测试内容\n测试内容");
        /**
         * 获取cookie信息
         */


        String username= getCookieByName("username");


        System.out.println("username :"+username);
//        List<UserTest> configures = CommonHelper.converseMapToObject(
//                UserTest.class, source);

        UserTest userTest =null;
        try
        {
            userTest= us.getUserTest("");
            System.out.println("查询名字："+userTest.getUsername());
        }catch (Exception e)
        {
            StringWriter sw=new StringWriter();
            PrintWriter pw=new PrintWriter(sw);
            e.printStackTrace(pw);
            LogHelper.error(e, "获取用户信息", false);
            LogHelper.info("测试iaaanfo");
            //e.printStackTrace();


        }

        ModelMap map= new ModelMap();
        map.put("aa","2");
        return  map;
    }

}

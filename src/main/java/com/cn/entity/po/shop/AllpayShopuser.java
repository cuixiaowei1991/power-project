package com.cn.entity.po.shop;

import com.cn.entity.publicFields.publicFields;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * 用户表
 * Created by WangWenFang on 2016/11/29.
 */
@Entity
@Table(name="ALLPAY_SHOPUSER")
@GenericGenerator(name = "system-uuid", strategy = "uuid")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class AllpayShopuser extends publicFields {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @Column(name = "SHOPUSER_ID", length = 32)
    private String shopuserId; //商户用户表主键id

    @Column(name = "SHOPUSER_NICKNAME", length = 100)
    private String shopuserNickname;	//用户昵称

    @Column(name = "SHOPUSER_NAME", length = 50)
    private String shopuserName;	//用户账号

    @Column(name = "SHOPUSER_PHONE", length = 50)
    private String shopuserPhone;	//用户手机号

    @Column(name = "SHOPUSER_PASSWORD", length = 50)
    private String shopuserPassword;	//用户密码

    @Column(name = "SHOPUSER_ISSTART", length = 1)
    private int shopuserIsstart;	//是否启用 1--启用 2--禁用

    @Column(name = "SHOPUSER_ROLE", length = 32)
    private String shopuserRole;	//用户角色(商户管理员、门店管理员、收银员)

    @Column(name = "SHOPUSER_SHOPID", length = 32)
    private String shopuserShopid;	//所属商户id

    @Column(name = "SHOPUSER_ISSUPER", length = 1)
    private int shopuserIssuper;	//是否是超管  1--是 2--否

    @Column(name = "SHOPUSER_ISCASHIER", length = 1)
    private int shopuserIscashier;	//是否具有软POS收银能力  1--是 2--否

    @Column(name = "SHOPUSER_STOREID", length = 32)
    private String shopuserStoreid;	//如果用户是网点管理员和收银员，那么该字段存对应网点id

    @Column(name = "SHOPUSER_NUM", length = 10)
    private String shopuserNum;	//商助系统随机生成九位数 w唯一去重 用于导入

    @Column(name = "SHOPUSER_APPID", length = 50)
    private String shopuserAppid;	//微信appid

    @Column(name = "SHOPUSER_OPENID", length = 100)
    private String shopuserOpenid;	//微信openid

    @Column(name = "SHOPUSER_UNIONID", length = 100)
    private String shopuserUnionID;	//微信unionid

    @Column(name = "SHOPUSER_EMAIL", length = 100)
    private String shopuseremail;	//用户邮箱

    @Column(name = "SHOPUSER_ACCOUNTID", length = 100)
    private String shopuseraccountid;	//用户账号ID
    /****/
    @Column(name = "LAST_LOGIN_TIME", length = 100)
    private String lastLoginTime;	//用户最后一次登录时间
    
    public String getShopuseraccountid() {
        return shopuseraccountid;
    }

    public void setShopuseraccountid(String shopuseraccountid) {
        this.shopuseraccountid = shopuseraccountid;
    }

    public String getShopuseremail() {
        return shopuseremail;
    }

    public void setShopuseremail(String shopuseremail) {
        this.shopuseremail = shopuseremail;
    }

    public String getShopuserAppid() {
        return shopuserAppid;
    }

    public void setShopuserAppid(String shopuserAppid) {
        this.shopuserAppid = shopuserAppid;
    }

    public String getShopuserOpenid() {
        return shopuserOpenid;
    }

    public void setShopuserOpenid(String shopuserOpenid) {
        this.shopuserOpenid = shopuserOpenid;
    }

    public String getShopuserUnionID() {
        return shopuserUnionID;
    }

    public void setShopuserUnionID(String shopuserUnionID) {
        this.shopuserUnionID = shopuserUnionID;
    }

    public String getShopuserNum() {
        return shopuserNum;
    }

    public void setShopuserNum(String shopuserNum) {
        this.shopuserNum = shopuserNum;
    }

    public String getShopuserStoreid() {
        return shopuserStoreid;
    }

    public void setShopuserStoreid(String shopuserStoreid) {
        this.shopuserStoreid = shopuserStoreid;
    }

    public int getShopuserIscashier() {
        return shopuserIscashier;
    }

    public void setShopuserIscashier(int shopuserIscashier) {
        this.shopuserIscashier = shopuserIscashier;
    }

    public String getShopuserId() {
        return shopuserId;
    }

    public void setShopuserId(String shopuserId) {
        this.shopuserId = shopuserId;
    }

    public String getShopuserNickname() {
        return shopuserNickname;
    }

    public void setShopuserNickname(String shopuserNickname) {
        this.shopuserNickname = shopuserNickname;
    }

    public String getShopuserName() {
        return shopuserName;
    }

    public void setShopuserName(String shopuserName) {
        this.shopuserName = shopuserName;
    }

    public String getShopuserPhone() {
        return shopuserPhone;
    }

    public void setShopuserPhone(String shopuserPhone) {
        this.shopuserPhone = shopuserPhone;
    }

    public String getShopuserPassword() {
        return shopuserPassword;
    }

    public void setShopuserPassword(String shopuserPassword) {
        this.shopuserPassword = shopuserPassword;
    }

    public int getShopuserIsstart() {
        return shopuserIsstart;
    }

    public void setShopuserIsstart(int shopuserIsstart) {
        this.shopuserIsstart = shopuserIsstart;
    }

    public String getShopuserRole() {
        return shopuserRole;
    }

    public void setShopuserRole(String shopuserRole) {
        this.shopuserRole = shopuserRole;
    }

    public String getShopuserShopid() {
        return shopuserShopid;
    }

    public void setShopuserShopid(String shopuserShopid) {
        this.shopuserShopid = shopuserShopid;
    }

    public int getShopuserIssuper() {
        return shopuserIssuper;
    }

    public void setShopuserIssuper(int shopuserIssuper) {
        this.shopuserIssuper = shopuserIssuper;
    }
}

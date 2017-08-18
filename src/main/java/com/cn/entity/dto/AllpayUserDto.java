package com.cn.entity.dto;

import org.codehaus.jackson.annotate.JsonProperty;

import java.io.Serializable;

/**
 * 用户管理dto
 * Created by WangWenFang on 2016/11/22.
 */
public class AllpayUserDto implements Serializable {

    @JsonProperty
    private String userId;	//用户id

    @JsonProperty
    private String userName;	//用户名称

    @JsonProperty
    private String userPhone;	//用户手机号, 不得超过11位

    @JsonProperty
    private String userRoleId;	//用户角色ID

    @JsonProperty
    private String userOrganizationId;	    //用户组织机构ID

    @JsonProperty
    private Integer curragePage;	//当前页码

    @JsonProperty
    private Integer pageSize;	//每页显示记录条数

    @JsonProperty
    private String userNickName;	//账号(昵称)

    @JsonProperty
    private String userIsStart;	//是否启用	1--启用 2--禁用

    @JsonProperty
    private String userPassword;	//用户密码	默认111111(写死)

    @JsonProperty
    private String shopuserIscashier;	//是否具有软POS收银能力  1--是 2--否;

    @JsonProperty
    private String merchantId;	//所属商户id

    @JsonProperty
    private String agentId; //代理商Id

    @JsonProperty
    private String shopUserAppId;

    @JsonProperty
    private String shopUserOpenId;

    @JsonProperty
    private String userNameFromQXCookie; //权限系统登录用户名

    @JsonProperty
    private String userNameFromBusCookie;  //业务系统登录名

    @JsonProperty
    private String userNameFromAgentCookie;  //代理商系统登录名

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserRoleId() {
        return userRoleId;
    }

    public void setUserRoleId(String userRoleId) {
        this.userRoleId = userRoleId;
    }

    public String getUserOrganizationId() {
        return userOrganizationId;
    }

    public void setUserOrganizationId(String userOrganizationId) {
        this.userOrganizationId = userOrganizationId;
    }

    public Integer getCurragePage() {
        return curragePage;
    }

    public void setCurragePage(Integer curragePage) {
        this.curragePage = curragePage;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getUserNickName() {
        return userNickName;
    }

    public void setUserNickName(String userNickName) {
        this.userNickName = userNickName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserIsStart() {
        return userIsStart;
    }

    public void setUserIsStart(String userIsStart) {
        this.userIsStart = userIsStart;
    }

    public String getShopuserIscashier() {
        return shopuserIscashier;
    }

    public void setShopuserIscashier(String shopuserIscashier) {
        this.shopuserIscashier = shopuserIscashier;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getUserNameFromQXCookie() {
        return userNameFromQXCookie;
    }

    public void setUserNameFromQXCookie(String userNameFromQXCookie) {
        this.userNameFromQXCookie = userNameFromQXCookie;
    }

    public String getUserNameFromBusCookie() {
        return userNameFromBusCookie;
    }

    public void setUserNameFromBusCookie(String userNameFromBusCookie) {
        this.userNameFromBusCookie = userNameFromBusCookie;
    }

    public String getUserNameFromAgentCookie() {
        return userNameFromAgentCookie;
    }

    public void setUserNameFromAgentCookie(String userNameFromAgentCookie) {
        this.userNameFromAgentCookie = userNameFromAgentCookie;
    }

    public String getShopUserAppId() {
        return shopUserAppId;
    }

    public void setShopUserAppId(String shopUserAppId) {
        this.shopUserAppId = shopUserAppId;
    }

    public String getShopUserOpenId() {
        return shopUserOpenId;
    }

    public void setShopUserOpenId(String shopUserOpenId) {
        this.shopUserOpenId = shopUserOpenId;
    }

    @Override
    public String toString() {
        return "AllpayUserDto{" +
                "userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", userPhone='" + userPhone + '\'' +
                ", userRoleId='" + userRoleId + '\'' +
                ", userOrganizationId='" + userOrganizationId + '\'' +
                ", curragePage=" + curragePage +
                ", pageSize=" + pageSize +
                ", userNickName='" + userNickName + '\'' +
                ", userIsStart='" + userIsStart + '\'' +
                ", userPassword='" + userPassword + '\'' +
                ", shopuserIscashier='" + shopuserIscashier + '\'' +
                ", merchantId='" + merchantId + '\'' +
                ", agentId='" + agentId + '\'' +
                ", shopUserAppId='" + shopUserAppId + '\'' +
                ", shopUserOpenId='" + shopUserOpenId + '\'' +
                ", userNameFromQXCookie='" + userNameFromQXCookie + '\'' +
                ", userNameFromBusCookie='" + userNameFromBusCookie + '\'' +
                ", userNameFromAgentCookie='" + userNameFromAgentCookie + '\'' +
                '}';
    }
}

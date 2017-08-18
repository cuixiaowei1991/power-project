package com.cn.entity.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Created by sun.yayi on 2016/11/22.
 */
public class AllpayFunctionDto implements Serializable {

    @JsonProperty
    private String funcId;
    @JsonProperty
    private String funcName;

    @JsonProperty
    private String funcType;

    @JsonProperty
    private String curragePage;

    @JsonProperty
    private String pageSize;

    @JsonProperty
    private String funcSupSystemId;
    @JsonProperty
    private String funcUrl;
    @JsonProperty
    private String funcSupMenuId;
    @JsonProperty
    private String funcTopMenuId;
    @JsonProperty
    private String funcCode;
    @JsonProperty
    private int funcOrder;
    @JsonProperty
    private int funcState;

    @JsonProperty
    private String userId;	//用户id
    @JsonProperty
    private String menuId;	//菜单id
    @JsonProperty
    private String isDefault; //是否默认功能  1是 2否
    @JsonProperty
    private String userNameFromQXCookie; //权限系统登录用户名

    @JsonProperty
    private String agentUserId;	   //代理商用户id

    @JsonProperty
    private String agentId;  //代理商id

    @Override
    public String toString() {
        return "AllpayFunctionDto{" +
                "funcId='" + funcId + '\'' +
                ", funcName='" + funcName + '\'' +
                ", funcType='" + funcType + '\'' +
                ", curragePage='" + curragePage + '\'' +
                ", pageSize='" + pageSize + '\'' +
                ", funcSupSystemId='" + funcSupSystemId + '\'' +
                ", funcUrl='" + funcUrl + '\'' +
                ", funcSupMenuId='" + funcSupMenuId + '\'' +
                ", funcTopMenuId='" + funcTopMenuId + '\'' +
                ", funcCode='" + funcCode + '\'' +
                ", funcOrder=" + funcOrder +
                ", funcState=" + funcState +
                ", userId='" + userId + '\'' +
                ", menuId='" + menuId + '\'' +
                ", isDefault='" + isDefault + '\'' +
                ", agentUserId='" + agentUserId + '\'' +
                ", agentId='" + agentId + '\'' +
                ", userNameFromQXCookie='" + userNameFromQXCookie + '\'' +
                '}';
    }

    public String getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(String isDefault) {
        this.isDefault = isDefault;
    }

    public String getFuncName() {
        return funcName;
    }

    public void setFuncName(String funcName) {
        this.funcName = funcName;
    }

    public String getFuncType() {
        return funcType;
    }

    public void setFuncType(String funcType) {
        this.funcType = funcType;
    }

    public String getCurragePage() {
        return curragePage;
    }

    public void setCurragePage(String curragePage) {
        this.curragePage = curragePage;
    }

    public String getPageSize() {
        return pageSize;
    }

    public void setPageSize(String pageSize) {
        this.pageSize = pageSize;
    }

    public String getFuncSupSystemId() {
        return funcSupSystemId;
    }

    public void setFuncSupSystemId(String funcSupSystemId) {
        this.funcSupSystemId = funcSupSystemId;
    }

    public String getFuncUrl() {
        return funcUrl;
    }

    public void setFuncUrl(String funcUrl) {
        this.funcUrl = funcUrl;
    }

    public String getFuncSupMenuId() {
        return funcSupMenuId;
    }

    public void setFuncSupMenuId(String funcSupMenuId) {
        this.funcSupMenuId = funcSupMenuId;
    }

    public String getFuncTopMenuId() {
        return funcTopMenuId;
    }

    public void setFuncTopMenuId(String funcTopMenuId) {
        this.funcTopMenuId = funcTopMenuId;
    }

    public String getFuncCode() {
        return funcCode;
    }

    public void setFuncCode(String funcCode) {
        this.funcCode = funcCode;
    }

    public int getFuncOrder() {
        return funcOrder;
    }

    public void setFuncOrder(int funcOrder) {
        this.funcOrder = funcOrder;
    }

    public int getFuncState() {
        return funcState;
    }

    public void setFuncState(int funcState) {
        this.funcState = funcState;
    }

    public String getFuncId() {
        return funcId;
    }

    public void setFuncId(String funcId) {
        this.funcId = funcId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    public String getUserNameFromQXCookie() {
        return userNameFromQXCookie;
    }

    public void setUserNameFromQXCookie(String userNameFromQXCookie) {
        this.userNameFromQXCookie = userNameFromQXCookie;
    }

    public String getAgentUserId() {
        return agentUserId;
    }

    public void setAgentUserId(String agentUserId) {
        this.agentUserId = agentUserId;
    }

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }
}

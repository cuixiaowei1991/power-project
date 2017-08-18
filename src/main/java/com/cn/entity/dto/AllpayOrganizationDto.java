package com.cn.entity.dto;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * 组织机构管理dto
 * Created by WangWenFang on 2016/11/23.
 */
public class AllpayOrganizationDto {

    @JsonProperty
    private String organizationId;	//组织机构ID

    @JsonProperty
    private String organizationName;	//组织机构名称	显示中文

    @JsonProperty
    private String organizationCode;    //组织机构编码

    @JsonProperty
    private String organizationType;	//组织机构类型	1--分公司2---部门

    @JsonProperty
    private String organizationAddress;	//组织机构地址

    @JsonProperty
    private String organizationUserName;	//负责人

    @JsonProperty
    private String organizationUserPhone;	//负责人电话

    @JsonProperty
    private String organizationState;	//状态	1--启用 2---禁用

    @JsonProperty
    private String curragePage;	//当前页

    @JsonProperty
    private String pageSize;	//每页显示记录条数

    @JsonProperty
    private String userNameFromQXCookie;  //登录名称

    public String getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }

    public String getOrganizationType() {
        return organizationType;
    }

    public void setOrganizationType(String organizationType) {
        this.organizationType = organizationType;
    }

    public String getOrganizationAddress() {
        return organizationAddress;
    }

    public void setOrganizationAddress(String organizationAddress) {
        this.organizationAddress = organizationAddress;
    }

    public String getOrganizationUserName() {
        return organizationUserName;
    }

    public void setOrganizationUserName(String organizationUserName) {
        this.organizationUserName = organizationUserName;
    }

    public String getOrganizationUserPhone() {
        return organizationUserPhone;
    }

    public void setOrganizationUserPhone(String organizationUserPhone) {
        this.organizationUserPhone = organizationUserPhone;
    }

    public String getOrganizationState() {
        return organizationState;
    }

    public void setOrganizationState(String organizationState) {
        this.organizationState = organizationState;
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

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public String getOrganizationCode() {
        return organizationCode;
    }

    public void setOrganizationCode(String organizationCode) {
        this.organizationCode = organizationCode;
    }

    public String getUserNameFromQXCookie() {
        return userNameFromQXCookie;
    }

    public void setUserNameFromQXCookie(String userNameFromQXCookie) {
        this.userNameFromQXCookie = userNameFromQXCookie;
    }

    @Override
    public String toString() {
        return "AllpayOrganizationDto{" +
                "organizationId='" + organizationId + '\'' +
                ", organizationName='" + organizationName + '\'' +
                ", organizationCode='" + organizationCode + '\'' +
                ", organizationType='" + organizationType + '\'' +
                ", organizationAddress='" + organizationAddress + '\'' +
                ", organizationUserName='" + organizationUserName + '\'' +
                ", organizationUserPhone='" + organizationUserPhone + '\'' +
                ", organizationState='" + organizationState + '\'' +
                ", userNameFromQXCookie='" + userNameFromQXCookie + '\'' +
                ", curragePage='" + curragePage + '\'' +
                ", pageSize='" + pageSize + '\'' +
                '}';
    }
}

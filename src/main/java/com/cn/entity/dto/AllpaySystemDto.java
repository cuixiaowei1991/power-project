package com.cn.entity.dto;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * 系统管理dto
 * Created by WangWenFang on 2016/11/23.
 */
public class AllpaySystemDto {

    @JsonProperty
    private String systemId;	//系统ID

    @JsonProperty
    private String systemName;	//系统名称

    @JsonProperty
    private String systemPath;	//系统路径

    @JsonProperty
    private String systemState;	//系统状态

    @JsonProperty
    private String userNameFromQXCookie;  //登录名称

    public String getSystemId() {
        return systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }

    public String getSystemName() {
        return systemName;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }

    public String getSystemPath() {
        return systemPath;
    }

    public void setSystemPath(String systemPath) {
        this.systemPath = systemPath;
    }

    public String getSystemState() {
        return systemState;
    }

    public void setSystemState(String systemState) {
        this.systemState = systemState;
    }

    public String getUserNameFromQXCookie() {
        return userNameFromQXCookie;
    }

    public void setUserNameFromQXCookie(String userNameFromQXCookie) {
        this.userNameFromQXCookie = userNameFromQXCookie;
    }
}

package com.cn.entity.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.json.JSONArray;

import java.io.Serializable;
import java.util.List;

/**
 * Created by sun.yayi on 2016/12/7.
 */
public class AllpayAgentMatchFuncDto implements Serializable {
    @JsonProperty
    private String agentId;	//代理商id

    @JsonProperty
    private List<AgentFunc> lists;
    @JsonProperty
    private String userNameFromQXCookie;

    @JsonProperty
    private String userNameFromBusCookie;

    @Override
    public String toString() {
        return "AllpayAgentMatchFuncDto{" +
                "agentId='" + agentId + '\'' +
                ", lists=" + lists +
                ", userNameFromQXCookie='" + userNameFromQXCookie + '\'' +
                ", userNameFromBusCookie='" + userNameFromBusCookie + '\'' +
                '}';
    }

    public String getUserNameFromQXCookie() {
        return userNameFromQXCookie;
    }

    public void setUserNameFromQXCookie(String userNameFromQXCookie) {
        this.userNameFromQXCookie = userNameFromQXCookie;
    }

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }

    public List<AgentFunc> getLists() {
        return lists;
    }

    public void setLists(List<AgentFunc> lists) {
        this.lists = lists;
    }

    public String getUserNameFromBusCookie() {
        return userNameFromBusCookie;
    }

    public void setUserNameFromBusCookie(String userNameFromBusCookie) {
        this.userNameFromBusCookie = userNameFromBusCookie;
    }
}

package com.cn.entity.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Created by sun.yayi on 2016/12/20.
 */
public class AgentFunc implements Serializable {
    @JsonProperty
    private String agentMenuId;

    @JsonProperty
    private String agentFunId;

    @JsonProperty
    private String systemId;

    @Override
    public String toString() {
        return "AgentFunc{" +
                "agentMenuId='" + agentMenuId + '\'' +
                ", agentFunId='" + agentFunId + '\'' +
                ", systemId='" + systemId + '\'' +
                '}';
    }

    public String getAgentMenuId() {
        return agentMenuId;
    }

    public void setAgentMenuId(String agentMenuId) {
        this.agentMenuId = agentMenuId;
    }

    public String getAgentFunId() {
        return agentFunId;
    }

    public void setAgentFunId(String agentFunId) {
        this.agentFunId = agentFunId;
    }

    public String getSystemId() {
        return systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }
}

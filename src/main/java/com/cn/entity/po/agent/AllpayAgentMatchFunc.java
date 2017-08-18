package com.cn.entity.po.agent;

import com.cn.entity.publicFields.publicFields;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Created by sun.yayi on 2016/11/29.
 *
 * 代理商功能关系表
 */
@Entity
@Table(name="ALLPAY_AGENTMATCHFUNC")
@GenericGenerator(name = "system-uuid", strategy = "uuid")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class AllpayAgentMatchFunc extends publicFields {
    /**
     * 代理商功能关系表主键
     */
    @Id
    @GeneratedValue(generator = "system-uuid")
    @Column(name = "AGENTMATCHFUNC_ID", length = 32)
    private String agentmatchfunc_id;

    /**
     * 代理商id
     */
    @Column(name = "AGENTMATCHFUNC_AGENTID", length = 32)
    private String agentmatchfunc_agentid;

    /**
     * 代理商权限功能表主键id
     */
    @Column(name = "AGENTMATCHFUNC_FUNCTIONID", length = 32)
    private String agentmatchfunc_functionid;

    /**
     * 菜单id
     */
    @Column(name = "AGENTMATCHFUNC_MENUID", length = 32)
    private String agentmatchfunc_menuid;

    /**
     * 所属系统id
     */
    @Column(name = "AGENTMATCHFUNC_FUNSYSTEMID", length = 32)
    private String agentmatchfunc_funsystemid;

    public String getAgentmatchfunc_id() {
        return agentmatchfunc_id;
    }

    public void setAgentmatchfunc_id(String agentmatchfunc_id) {
        this.agentmatchfunc_id = agentmatchfunc_id;
    }

    public String getAgentmatchfunc_agentid() {
        return agentmatchfunc_agentid;
    }

    public void setAgentmatchfunc_agentid(String agentmatchfunc_agentid) {
        this.agentmatchfunc_agentid = agentmatchfunc_agentid;
    }

    public String getAgentmatchfunc_functionid() {
        return agentmatchfunc_functionid;
    }

    public void setAgentmatchfunc_functionid(String agentmatchfunc_functionid) {
        this.agentmatchfunc_functionid = agentmatchfunc_functionid;
    }

    public String getAgentmatchfunc_menuid() {
        return agentmatchfunc_menuid;
    }

    public void setAgentmatchfunc_menuid(String agentmatchfunc_menuid) {
        this.agentmatchfunc_menuid = agentmatchfunc_menuid;
    }

    public String getAgentmatchfunc_funsystemid() {
        return agentmatchfunc_funsystemid;
    }

    public void setAgentmatchfunc_funsystemid(String agentmatchfunc_funsystemid) {
        this.agentmatchfunc_funsystemid = agentmatchfunc_funsystemid;
    }
}

package com.cn.entity.po.agent;

import com.cn.entity.publicFields.publicFields;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Created by sun.yayi on 2016/11/29.
 *
 *  代理商功能表（资源表）
 */
@Entity
@Table(name="ALLPAY_AGENTFUNCTION")
@GenericGenerator(name = "system-uuid", strategy = "uuid")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class AllpayAgentFunction extends publicFields {
    /**
     * 功能表主键id
     */
    @Id
    @GeneratedValue(generator = "system-uuid")
    @Column(name = "AGENTFUNCTION_ID", length = 32)
    private String agentfunction_id;

    /**
     * 功能名称
     */
    @Column(name = "AGENTFUNCTION_NAME", length = 200)
    private String agentfunction_name;

    /**
     * 类型 1--菜单栏目 2---功能操作
     （1：url、归属菜单id、功能显示循序  2：功能识别码）
     */
    @Column(name = "AGENTFUNCTION_TYPE", length = 1)
    private int agentfunction_type;

    /**
     * 菜单栏目url地址（菜单栏目）
     */
    @Column(name = "AGENTFUNCTION_URL", length = 200)
    private String agentfunction_url;

    /**
     * 归属菜单id（菜单栏目）
     */
    @Column(name = "AGENTFUNCTION_MENUID", length = 32)
    private String agentfunction_menuid;

    /**
     * 归属顶级菜单id
     */
    @Column(name = "AGENTFUNCTION_TOP_MENUID", length = 32)
    private String agentfunction_top_menuid;

    /**
     * 所属系统id
     */
    @Column(name = "AGENTFUNCTION_SYSTEMID", length = 32)
    private String agentfunction_systemid;

    /**
     * 功能显示顺序（菜单栏目）
     */
    @Column(name = "AGENTFUNCTION_ORDER", length = 10)
    private int agentfunction_order;


    /**
     *  功能识别码（功能操作）
     */
    @Column(name = "AGENTFUNCTION_CODE", length = 200)
    private String agentfunction_code;

    /**
     *  是否启用1--启用 2---禁用
     */
    @Column(name = "AGENTFUNCTION_STATE", length = 1)
    private int agentfunction_state;

    /**
     * 是否默认功能 1是 2否
     */
    @Column(name = "AGENTFUNCTION_ISDEFAULT", length = 5)
    private String isDefault;

    public String getAgentfunction_id() {
        return agentfunction_id;
    }

    public void setAgentfunction_id(String agentfunction_id) {
        this.agentfunction_id = agentfunction_id;
    }

    public String getAgentfunction_name() {
        return agentfunction_name;
    }

    public void setAgentfunction_name(String agentfunction_name) {
        this.agentfunction_name = agentfunction_name;
    }

    public int getAgentfunction_type() {
        return agentfunction_type;
    }

    public void setAgentfunction_type(int agentfunction_type) {
        this.agentfunction_type = agentfunction_type;
    }

    public String getAgentfunction_url() {
        return agentfunction_url;
    }

    public void setAgentfunction_url(String agentfunction_url) {
        this.agentfunction_url = agentfunction_url;
    }

    public String getAgentfunction_menuid() {
        return agentfunction_menuid;
    }

    public void setAgentfunction_menuid(String agentfunction_menuid) {
        this.agentfunction_menuid = agentfunction_menuid;
    }

    public String getAgentfunction_top_menuid() {
        return agentfunction_top_menuid;
    }

    public void setAgentfunction_top_menuid(String agentfunction_top_menuid) {
        this.agentfunction_top_menuid = agentfunction_top_menuid;
    }

    public int getAgentfunction_order() {
        return agentfunction_order;
    }

    public void setAgentfunction_order(int agentfunction_order) {
        this.agentfunction_order = agentfunction_order;
    }

    public String getAgentfunction_code() {
        return agentfunction_code;
    }

    public void setAgentfunction_code(String agentfunction_code) {
        this.agentfunction_code = agentfunction_code;
    }

    public int getAgentfunction_state() {
        return agentfunction_state;
    }

    public void setAgentfunction_state(int agentfunction_state) {
        this.agentfunction_state = agentfunction_state;
    }

    public String getAgentfunction_systemid() {
        return agentfunction_systemid;
    }

    public void setAgentfunction_systemid(String agentfunction_systemid) {
        this.agentfunction_systemid = agentfunction_systemid;
    }

    public String getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(String isDefault) {
        this.isDefault = isDefault;
    }
}

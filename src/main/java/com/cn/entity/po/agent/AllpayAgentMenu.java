package com.cn.entity.po.agent;

import com.cn.entity.publicFields.publicFields;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Created by sun.yayi on 2016/11/29.
 *
 * 代理商菜单表
 */
@Entity
@Table(name="ALLPAY_AGENTMENU")
@GenericGenerator(name = "system-uuid", strategy = "uuid")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class AllpayAgentMenu extends publicFields {
    /**
     * 菜单表主键id
     */
    @Id
    @GeneratedValue(generator = "system-uuid")
    @Column(name = "AGENTMENU_ID", length = 32)
    private String agentmenu_id;

    /**
     * 菜单名称
     */
    @Column(name = "AGENTMENU_NAME", length = 200)
    private String agentmenu_name;

    /**
     * 菜单级别
     */
    @Column(name = "AGENTMENU_LEVEL", length = 5)
    private String agentmenu_level;

    /**
     * 菜单启用状态 1---启用 2---禁用
     */
    @Column(name = "AGENTMENU_STATE", length = 1)
    private int agentmenu_state;

    /**
     * 菜单显示顺序
     */
    @Column(name = "AGENTMENU_ORDER", length = 50)
    private int agentmenu_order;

    /**
     * 上级菜单id
     */
    @Column(name = "AGENTMENU_SUPERIORID", length = 32)
    private String agentmenu_superiorid;

    public String getAgentmenu_id() {
        return agentmenu_id;
    }

    public void setAgentmenu_id(String agentmenu_id) {
        this.agentmenu_id = agentmenu_id;
    }

    public String getAgentmenu_name() {
        return agentmenu_name;
    }

    public void setAgentmenu_name(String agentmenu_name) {
        this.agentmenu_name = agentmenu_name;
    }

    public int getAgentmenu_state() {
        return agentmenu_state;
    }

    public void setAgentmenu_state(int agentmenu_state) {
        this.agentmenu_state = agentmenu_state;
    }

    public int getAgentmenu_order() {
        return agentmenu_order;
    }

    public void setAgentmenu_order(int agentmenu_order) {
        this.agentmenu_order = agentmenu_order;
    }

    public String getAgentmenu_superiorid() {
        return agentmenu_superiorid;
    }

    public void setAgentmenu_superiorid(String agentmenu_superiorid) {
        this.agentmenu_superiorid = agentmenu_superiorid;
    }

    public String getAgentmenu_level() {
        return agentmenu_level;
    }

    public void setAgentmenu_level(String agentmenu_level) {
        this.agentmenu_level = agentmenu_level;
    }
}

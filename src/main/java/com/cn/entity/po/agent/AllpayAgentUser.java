package com.cn.entity.po.agent;

import com.cn.entity.publicFields.publicFields;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Created by sun.yayi on 2016/11/29.
 *
 * 代理商用户表
 */
@Entity
@Table(name="ALLPAY_AGENTUSER")
@GenericGenerator(name = "system-uuid", strategy = "uuid")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class AllpayAgentUser extends publicFields {
    /**
     * 代理商用户表 主键ID
     */
    @Id
    @GeneratedValue(generator = "system-uuid")
    @Column(name = "AGENTUSER_ID", length = 32)
    private String agentuser_id;

    /**
     * 用户昵称
     */
    @Column(name = "AGENTUSER_NICKNAME", length = 200)
    private String agentuser_nickname;

    /**
     * 用户账号
     */
    @Column(name = "AGENTUSER_NAME", length = 50)
    private String agentuser_name;

    /**
     * 用户手机号
     */
    @Column(name = "AGENTUSER_PHONE", length = 50)
    private String agentuser_phone;


    /**
     * 用户密码
     */
    @Column(name = "AGENTUSER_PASSWORD", length = 50)
    private String agentuser_password;

    /**
     * 是否启用 1--启用 2--禁用
     */
    @Column(name = "AGENTUSER_ISSTART", length = 1)
    private int agentuser_isstart;

    /**
     * 所属代理商id
     */
    @Column(name = "AGENTUSER_AGENTID", length = 32)
    private String agentuser_agentid;

    /**
     * 代理商下是否是超管（1--是  2---否）
     */
    @Column(name = "AGENTUSER_ISSUPER", length = 1)
    private String agentuser_issuper;

    /**
     * 管理员编号(6位) 唯一去重 用于导入
     */
    @Column(name = "AGENTUSER_NUM", length = 10)
    private String agentuser_num;

    @Column(name = "AGENTUSER_EMAIL", length = 100)
    private String agentuser_email;	//代理商用户邮箱

    public String getAgentuser_num() {
        return agentuser_num;
    }

    public void setAgentuser_num(String agentuser_num) {
        this.agentuser_num = agentuser_num;
    }

    public String getAgentuser_email() {
        return agentuser_email;
    }

    public void setAgentuser_email(String agentuser_email) {
        this.agentuser_email = agentuser_email;
    }

    public String getAgentuser_id() {
        return agentuser_id;
    }

    public void setAgentuser_id(String agentuser_id) {
        this.agentuser_id = agentuser_id;
    }

    public String getAgentuser_nickname() {
        return agentuser_nickname;
    }

    public void setAgentuser_nickname(String agentuser_nickname) {
        this.agentuser_nickname = agentuser_nickname;
    }

    public String getAgentuser_name() {
        return agentuser_name;
    }

    public void setAgentuser_name(String agentuser_name) {
        this.agentuser_name = agentuser_name;
    }

    public String getAgentuser_phone() {
        return agentuser_phone;
    }

    public void setAgentuser_phone(String agentuser_phone) {
        this.agentuser_phone = agentuser_phone;
    }

    public String getAgentuser_password() {
        return agentuser_password;
    }

    public void setAgentuser_password(String agentuser_password) {
        this.agentuser_password = agentuser_password;
    }

    public int getAgentuser_isstart() {
        return agentuser_isstart;
    }

    public void setAgentuser_isstart(int agentuser_isstart) {
        this.agentuser_isstart = agentuser_isstart;
    }

    public String getAgentuser_agentid() {
        return agentuser_agentid;
    }

    public void setAgentuser_agentid(String agentuser_agentid) {
        this.agentuser_agentid = agentuser_agentid;
    }

    public String getAgentuser_issuper() {
        return agentuser_issuper;
    }

    public void setAgentuser_issuper(String agentuser_issuper) {
        this.agentuser_issuper = agentuser_issuper;
    }
}

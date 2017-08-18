package com.cn.entity.po;

import com.cn.entity.publicFields.publicFields;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Created by sun.yayi on 2016/11/14.
 */
@Entity
@Table(name="ALLPAY_ROLE")
@GenericGenerator(name = "system-uuid", strategy = "uuid")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Inheritance(strategy = InheritanceType.JOINED)
public class AllpayRole extends publicFields {

    /**
     * 主键id
     */
    @Id
    @GeneratedValue(generator = "system-uuid")
    @Column(name = "ROLE_ID", length = 32)
    private String roleId;

    /**
     * 角色名称
     */
    @Column(name = "ROLE_NAME", length = 50)
    private String role_name;


    /**
     * 是否启用 1--启用 2--禁用
     */
    @Column(name = "ROLE_ISSTART", length = 50)
    private int role_isStart;

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getRole_name() {
        return role_name;
    }

    public void setRole_name(String role_name) {
        this.role_name = role_name;
    }

    public int getRole_isStart() {
        return role_isStart;
    }

    public void setRole_isStart(int role_isStart) {
        this.role_isStart = role_isStart;
    }
}

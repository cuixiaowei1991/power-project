package com.cn.entity.po;

import com.cn.entity.publicFields.publicFields;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Created by sun.yayi on 2016/11/15.
 *
 * 角色资源关系表（角色功能关系表
 */
@Entity
@Table(name="ALLPAY_ROLE_FUNCTION")
@GenericGenerator(name = "system-uuid", strategy = "uuid")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class AllpayRoleFunction extends publicFields {

    /**
     * 角色资源关系表主键
     */
    @Id
    @GeneratedValue(generator = "system-uuid")
    @Column(name = "ROLE_FUNCTION_ID", length = 32)
    private String roleFunctionId;

    /**
     * 菜单id
     */
    @Column(name = "ROLE_FUNCTION_MENUID", length = 32)
    private String role_function_menuId;

    /**
     * 功能id
     */
    @Column(name = "ROLE_FUNCTION_FUNID", length = 32)
    private String role_function_funId;

    /**
     * 资源所属系统id
     */
    @Column(name = "ROLE_FUNCTION_FUNSYSTEMID", length = 32)
    private String role_function_systemId;

    /**
     * 角色id
     */
    @Column(name = "ROLE_FUNCTION_ROLEID", length = 32)
    private String role_function_roleId;

    public String getRoleFunctionId() {
        return roleFunctionId;
    }

    public void setRoleFunctionId(String roleFunctionId) {
        this.roleFunctionId = roleFunctionId;
    }

    public String getRole_function_menuId() {
        return role_function_menuId;
    }

    public void setRole_function_menuId(String role_function_menuId) {
        this.role_function_menuId = role_function_menuId;
    }

    public String getRole_function_funId() {
        return role_function_funId;
    }

    public void setRole_function_funId(String role_function_funId) {
        this.role_function_funId = role_function_funId;
    }

    public String getRole_function_systemId() {
        return role_function_systemId;
    }

    public void setRole_function_systemId(String role_function_systemId) {
        this.role_function_systemId = role_function_systemId;
    }

    public String getRole_function_roleId() {
        return role_function_roleId;
    }

    public void setRole_function_roleId(String role_function_roleId) {
        this.role_function_roleId = role_function_roleId;
    }
}

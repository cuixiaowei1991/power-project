package com.cn.entity.po;

import com.cn.entity.publicFields.publicFields;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Created by sun.yayi on 2016/11/15.
 *
 * 功能表（资源表）
 */
@Entity
@Table(name="ALLPAY_FUNCTION")
@GenericGenerator(name = "system-uuid", strategy = "uuid")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class AllpayFunction extends publicFields {



    /**
     * 功能表主键id
     */
    @Id
    @GeneratedValue(generator = "system-uuid")
    @Column(name = "FUNCTION_ID", length = 32)
    private String functionId;

    /**
     * 功能名称
     */
    @Column(name = "FUNCTION_NAME", length = 200)
    private String function_name;

    /**
     * 所属系统id
     */
    @Column(name = "FUNCTION_SUP_SYSTEMID", length = 32)
    private String function_sup_systemId;

    /**
     * 类型 1--菜单栏目 2---功能操作
     （1：url、归属菜单id、功能显示循序  2：功能识别码）
     */
    @Column(name = "FUNCTION_TYPE", length = 1)
    private int function_type;

    /**
     * 菜单栏目url地址（菜单栏目）
     */
    @Column(name = "FUNCTION_URL", length = 200)
    private String function_url;

    /**
     * 归属菜单id（菜单栏目）
     */
    @Column(name = "FUNCTION_SUP_MENUID", length = 32)
    private String function_sup_menuId;

    /**
     * 归属顶级菜单id
     */
    @Column(name = "FUNCTION_TOP_MENUID", length = 32)
    private String function_top_menuId;


    /**
     * 功能显示顺序（菜单栏目）
     */
    @Column(name = "FUNCTION_ORDER", length = 10)
    private int function_order;


    /**
     *  功能识别码（功能操作）
     */
    @Column(name = "FUNCTION_CODE", length = 200)
    private String function_code;

    /**
     *  是否启用1--启用 2---禁用
     */
    @Column(name = "FUNCTION_STATE", length = 1)
    private int function_state;

    /**
     * 是否默认功能  1是 2否
     */
    @Column(name = "FUNCTION_ISDEFAULT", length = 1)
    private String isDefault;

    public String getFunctionId() {
        return functionId;
    }

    public void setFunctionId(String functionId) {
        this.functionId = functionId;
    }

    public String getFunction_name() {
        return function_name;
    }

    public void setFunction_name(String function_name) {
        this.function_name = function_name;
    }

    public String getFunction_sup_systemId() {
        return function_sup_systemId;
    }

    public void setFunction_sup_systemId(String function_sup_systemId) {
        this.function_sup_systemId = function_sup_systemId;
    }

    public int getFunction_type() {
        return function_type;
    }

    public void setFunction_type(int function_type) {
        this.function_type = function_type;
    }

    public String getFunction_url() {
        return function_url;
    }

    public void setFunction_url(String function_url) {
        this.function_url = function_url;
    }

    public String getFunction_sup_menuId() {
        return function_sup_menuId;
    }

    public void setFunction_sup_menuId(String function_sup_menuId) {
        this.function_sup_menuId = function_sup_menuId;
    }

    public int getFunction_order() {
        return function_order;
    }

    public void setFunction_order(int function_order) {
        this.function_order = function_order;
    }

    public String getFunction_code() {
        return function_code;
    }

    public void setFunction_code(String function_code) {
        this.function_code = function_code;
    }

    public int getFunction_state() {
        return function_state;
    }

    public void setFunction_state(int function_state) {
        this.function_state = function_state;
    }

    public String getFunction_top_menuId() {
        return function_top_menuId;
    }

    public void setFunction_top_menuId(String function_top_menuId) {
        this.function_top_menuId = function_top_menuId;
    }

    public String getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(String isDefault) {
        this.isDefault = isDefault;
    }
}

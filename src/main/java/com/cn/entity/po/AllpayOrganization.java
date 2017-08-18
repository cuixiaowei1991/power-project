package com.cn.entity.po;

import com.cn.entity.publicFields.publicFields;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Created by sun.yayi on 2016/11/15.
 *
 * 组织机构表
 */
@Entity
@Table(name="ALLPAY_ORGANIZATION")
@GenericGenerator(name = "system-uuid", strategy = "uuid")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class AllpayOrganization extends publicFields {

    /**
     * 组织机构表主键
     */
    @Id
    @GeneratedValue(generator = "system-uuid")
    @Column(name = "ORGANIZATION_ID", length = 32)
    private String organizationId;

    /**
     * 组织机构名称
     */
    @Column(name = "ORGANIZATION_NAME", length = 200)
    private String organization_name;

    /**
     * 组织机构编码
     */
    @Column(name = "ORGANIZATION_CODE", length = 20)
    private String organization_code;

    /**
     * 机构类型类型
     */
    @Column(name = "ORGANIZATION_TYPE", length = 10)
    private int organization_type;

    /**
     * 地址
     */
    @Column(name = "ORGANIZATION_ADDRESS", length = 200)
    private String organization_address;

    /**
     * 负责人姓名
     */
    @Column(name = "ORGANIZATION_USERNAME", length = 50)
    private String organization_username;

    /**
     *  负责人电话
     */
    @Column(name = "ORGANIZATION_USERPHONE", length = 50)
    private String organization_userPhone;

    /**
     * 是否启用 1--启用 2--禁用
     */
    @Column(name = "ORGANIZATION_STATE", length = 1)
    private int organization_state;

    public String getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }

    public int getOrganization_type() {
        return organization_type;
    }

    public void setOrganization_type(int organization_type) {
        this.organization_type = organization_type;
    }

    public String getOrganization_address() {
        return organization_address;
    }

    public void setOrganization_address(String organization_address) {
        this.organization_address = organization_address;
    }

    public String getOrganization_username() {
        return organization_username;
    }

    public void setOrganization_username(String organization_username) {
        this.organization_username = organization_username;
    }

    public String getOrganization_userPhone() {
        return organization_userPhone;
    }

    public void setOrganization_userPhone(String organization_userPhone) {
        this.organization_userPhone = organization_userPhone;
    }

    public String getOrganization_name() {
        return organization_name;
    }

    public void setOrganization_name(String organization_name) {
        this.organization_name = organization_name;
    }

    public int getOrganization_state() {
        return organization_state;
    }

    public void setOrganization_state(int organization_state) {
        this.organization_state = organization_state;
    }

    public String getOrganization_code() {
        return organization_code;
    }

    public void setOrganization_code(String organization_code) {
        this.organization_code = organization_code;
    }
}

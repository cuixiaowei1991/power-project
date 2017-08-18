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
@Table(name="ALLPAY_USER")
@GenericGenerator(name = "system-uuid", strategy = "uuid")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Inheritance(strategy = InheritanceType.JOINED)
public class AllpayUser extends publicFields {

    /**
     * 主键id
     */
    @Id
    @GeneratedValue(generator = "system-uuid")
    @Column(name = "USER_ID", length = 32)
    private String userId;


    /**
     * 用户昵称
     */
    @Column(name = "USER_NICKNAME", length = 100)
    private String user_nickName;

    /**
     * 用户帐号
     */
    @Column(name = "USER_NAME", length = 50)
    private String user_name;

    /**
     * 用户电话
     */
    @Column(name = "USER_PHONE", length = 50)
    private String user_phone;

    /**
     * 密码
     */
    @Column(name = "USER_PASSWORD", length = 50)
    private String user_password;


    /**
     * 是否启用 1--启用 2--禁用
     */
    @Column(name = "USER_ISSTART", length = 50)
    private int user_isStart;


    /**
     * 对应 用户角色表中ID
     */
    @Column(name = "USER_ROLEID", length = 32)
    private String user_roleId;

    /**
     * 对应 用户组织机构ID
     */
    @Column(name = "USER_ORGANIZATIONID", length = 32)
    private String user_organizationId;

    /**
     * 业务系统随机生成七位数(系统唯一) 用于导入
     */
    @Column(name = "USER_NUM", length = 10)
    private String user_num;

    /**
     * 用户邮箱
     */
    @Column(name = "USER_EMAIL", length = 100)
    private String user_email;

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public String getUser_num() {
        return user_num;
    }

    public void setUser_num(String user_num) {
        this.user_num = user_num;
    }

    public String getUser_organizationId() {
        return user_organizationId;
    }

    public void setUser_organizationId(String user_organizationId) {
        this.user_organizationId = user_organizationId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUser_nickName() {
        return user_nickName;
    }

    public void setUser_nickName(String user_nickName) {
        this.user_nickName = user_nickName;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_phone() {
        return user_phone;
    }

    public void setUser_phone(String user_phone) {
        this.user_phone = user_phone;
    }

    public String getUser_password() {
        return user_password;
    }

    public void setUser_password(String user_password) {
        this.user_password = user_password;
    }

    public int getUser_isStart() {
        return user_isStart;
    }

    public void setUser_isStart(int user_isStart) {
        this.user_isStart = user_isStart;
    }

    public String getUser_roleId() {
        return user_roleId;
    }

    public void setUser_roleId(String user_roleId) {
        this.user_roleId = user_roleId;
    }
}

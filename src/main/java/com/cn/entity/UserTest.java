package com.cn.entity;

import com.cn.entity.publicFields.publicFields;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Created by cuixiaowei on 2016/11/8.
 */
@Entity
@Table(name = "UserTest")
@GenericGenerator(name = "system-uuid", strategy = "uuid")
@org.hibernate.annotations.AccessType("field")
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class UserTest extends publicFields {
    /**
     * 主键id
     */
    @Id
    @GeneratedValue(generator = "system-uuid")
    @Column(name = "userid", length = 32)
    private String userid;
    /**
     * 用户姓名
     */
    @Column(name = "username", length = 1000)
    private String username;
    /**
     * 用户号
     */
    @Column(name = "usernum", length = 100)
    private String usernum;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsernum() {
        return usernum;
    }

    public void setUsernum(String usernum) {
        this.usernum = usernum;
    }
}

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
@Table(name="ALLPAY_SUPERUSER")
@GenericGenerator(name = "system-uuid", strategy = "uuid")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Inheritance(strategy = InheritanceType.JOINED)
public class AllpaySuperUser extends publicFields {

    /**
     * 主键id
     */
    @Id
    @GeneratedValue(generator = "system-uuid")
    @Column(name = "SUPERUSER_ID", length = 32)
    private String superuser_id;


    /**
     * 用户昵称
     */
    @Column(name = "SUPERUSER_NAME", length = 200)
    private String superuser_name;

    /**
     * 密码
     */
    @Column(name = "SUPERUSER_PASSWORD", length = 50)
    private String superuser_password;

    public String getSuperuser_id() {
        return superuser_id;
    }

    public void setSuperuser_id(String superuser_id) {
        this.superuser_id = superuser_id;
    }

    public String getSuperuser_name() {
        return superuser_name;
    }

    public void setSuperuser_name(String superuser_name) {
        this.superuser_name = superuser_name;
    }

    public String getSuperuser_password() {
        return superuser_password;
    }

    public void setSuperuser_password(String superuser_password) {
        this.superuser_password = superuser_password;
    }
}

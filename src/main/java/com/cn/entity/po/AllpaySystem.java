package com.cn.entity.po;

import com.cn.entity.publicFields.publicFields;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Created by sun.yayi on 2016/11/15.
 */
@Entity
@Table(name="ALLPAY_SYSTEM")
@GenericGenerator(name = "system-uuid", strategy = "uuid")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class AllpaySystem extends publicFields {
    /**
     * 主键id
     */
    @Id
    @GeneratedValue(generator = "system-uuid")
    @Column(name = "SYSTEM_ID", length = 32)
    private String systemId;

    /**
     * 系统名称
     */
    @Column(name = "SYSTEM_NAME", length = 200)
    private String system_name;

    /**
     * 系统路径
     */
    @Column(name = "SYSTEM_PATH", length = 2000)
    private String system_path;


    /**
     * 系统状态 1---启用 2---停用
     */
    @Column(name = "SYSTEM_STATE", length = 50)
    private int system_state;

    public String getSystemId() {
        return systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }

    public String getSystem_name() {
        return system_name;
    }

    public void setSystem_name(String system_name) {
        this.system_name = system_name;
    }

    public String getSystem_path() {
        return system_path;
    }

    public void setSystem_path(String system_path) {
        this.system_path = system_path;
    }

    public int getSystem_state() {
        return system_state;
    }

    public void setSystem_state(int system_state) {
        this.system_state = system_state;
    }
}

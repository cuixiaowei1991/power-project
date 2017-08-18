package com.cn.entity.po.agent;

import com.cn.entity.publicFields.publicFields;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Created by sun.yayi on 2016/11/29.
 *
 * 角色功能关系表
 */
@Entity
@Table(name="ALLPAY_AGENTUSERMATCHFUNC")
@GenericGenerator(name = "system-uuid", strategy = "uuid")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class AllpayAgentUserMatchFunc extends publicFields {
    /**
     *角色功能关系表 主键
     */
    @Id
    @GeneratedValue(generator = "system-uuid")
    @Column(name = "AGENTUSERMATCHFUNC_ID", length = 32)
    private String AGENTROLEMATCHFUNC_ID;

    /**
     * 用户id
     */
    @Column(name = "AGENTUSERMATCHFUNC_USERID", length = 32)
    private String AGENTUSERMATCHFUNC_USERID;

    /**
     * 代理商权限功能表主键id
     */
    @Column(name = "AGENTROLEMATCHFUNC_FUNCID", length = 32)
    private String AGENTROLEMATCHFUNC_FUNCID;

    /**
     * 代理商权限菜单表主键id
     */
    @Column(name = "AGENTUSERMATCHFUNC_MENUID", length = 32)
    private String AGENTUSERMATCHFUNC_MENUID;

    /**
     * 系统id
     */
    @Column(name = "AGENTUSERMATCHFUNC_SYSTEMID", length = 32)
    private String AGENTUSERMATCHFUNC_SYSTEMID;

    @Column(name = "AGENTUSERMATCHFUNC_ISREDUCE", length = 2)
    private String agentUserMatchFunc_isReduce; //默认权限 是否减去 0减 1正常增加




    public String getAGENTROLEMATCHFUNC_ID() {
        return AGENTROLEMATCHFUNC_ID;
    }

    public void setAGENTROLEMATCHFUNC_ID(String AGENTROLEMATCHFUNC_ID) {
        this.AGENTROLEMATCHFUNC_ID = AGENTROLEMATCHFUNC_ID;
    }

    public String getAGENTUSERMATCHFUNC_USERID() {
        return AGENTUSERMATCHFUNC_USERID;
    }

    public void setAGENTUSERMATCHFUNC_USERID(String AGENTUSERMATCHFUNC_USERID) {
        this.AGENTUSERMATCHFUNC_USERID = AGENTUSERMATCHFUNC_USERID;
    }

    public String getAGENTROLEMATCHFUNC_FUNCID() {
        return AGENTROLEMATCHFUNC_FUNCID;
    }

    public void setAGENTROLEMATCHFUNC_FUNCID(String AGENTROLEMATCHFUNC_FUNCID) {
        this.AGENTROLEMATCHFUNC_FUNCID = AGENTROLEMATCHFUNC_FUNCID;
    }

    public String getAGENTUSERMATCHFUNC_MENUID() {
        return AGENTUSERMATCHFUNC_MENUID;
    }

    public void setAGENTUSERMATCHFUNC_MENUID(String AGENTUSERMATCHFUNC_MENUID) {
        this.AGENTUSERMATCHFUNC_MENUID = AGENTUSERMATCHFUNC_MENUID;
    }

    public String getAGENTUSERMATCHFUNC_SYSTEMID() {
        return AGENTUSERMATCHFUNC_SYSTEMID;
    }

    public void setAGENTUSERMATCHFUNC_SYSTEMID(String AGENTUSERMATCHFUNC_SYSTEMID) {
        this.AGENTUSERMATCHFUNC_SYSTEMID = AGENTUSERMATCHFUNC_SYSTEMID;
    }

    public String getAgentUserMatchFunc_isReduce() {
        return agentUserMatchFunc_isReduce;
    }

    public void setAgentUserMatchFunc_isReduce(String agentUserMatchFunc_isReduce) {
        this.agentUserMatchFunc_isReduce = agentUserMatchFunc_isReduce;
    }

}

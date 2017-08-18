package com.cn.entity.po.shop;

import com.cn.entity.publicFields.publicFields;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * 角色功能关系表
 * Created by WangWenFang on 2016/11/29.
 */
@Entity
@Table(name="ALLPAY_ROLEMATCHFUNC")
@GenericGenerator(name = "system-uuid", strategy = "uuid")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class AllpayRolematchfunc extends publicFields {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @Column(name = "ROLEMATCHFUNC_ID", length = 32)
    private String rolematchfuncId;	//角色功能关系表主键

    @Column(name = "ROLEMATCHFUNC_ROLE", length = 32)
    private String rolematchfuncRole;	//用户角色(商户管理员、门店管理员、收银员)

    @Column(name = "ROLEMATCHFUNC_FUNCTIONID", length = 32)
    private String rolematchfuncFunctionid;	//商助权限功能表主键id

    @Column(name = "ROLEMATCHFUNC_MENUID", length = 32)
    private String rolematchfuncMenuid;	//商助权限菜单表主键id

    @Column(name = "ROLEMATCHFUNC_SYSTEMID", length = 32)
    private String rolematchfuncSystemid;	//所属系统id

    @Column(name = "ROLEMATCHFUNC_ISREDUCE", length = 2)
    private String rolematchfuncIsReduce; //默认权限 是否减去 0减 1正常增加


    public String getRolematchfuncId() {
        return rolematchfuncId;
    }

    public void setRolematchfuncId(String rolematchfuncId) {
        this.rolematchfuncId = rolematchfuncId;
    }

    public String getRolematchfuncRole() {
        return rolematchfuncRole;
    }

    public void setRolematchfuncRole(String rolematchfuncRole) {
        this.rolematchfuncRole = rolematchfuncRole;
    }

    public String getRolematchfuncFunctionid() {
        return rolematchfuncFunctionid;
    }

    public void setRolematchfuncFunctionid(String rolematchfuncFunctionid) {
        this.rolematchfuncFunctionid = rolematchfuncFunctionid;
    }

    public String getRolematchfuncMenuid() {
        return rolematchfuncMenuid;
    }

    public void setRolematchfuncMenuid(String rolematchfuncMenuid) {
        this.rolematchfuncMenuid = rolematchfuncMenuid;
    }

    public String getRolematchfuncSystemid() {
        return rolematchfuncSystemid;
    }

    public void setRolematchfuncSystemid(String rolematchfuncSystemid) {
        this.rolematchfuncSystemid = rolematchfuncSystemid;
    }

    public String getRolematchfuncIsReduce() {
        return rolematchfuncIsReduce;
    }

    public void setRolematchfuncIsReduce(String rolematchfuncIsReduce) {
        this.rolematchfuncIsReduce = rolematchfuncIsReduce;
    }
}

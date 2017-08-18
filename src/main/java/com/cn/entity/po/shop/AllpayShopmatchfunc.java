package com.cn.entity.po.shop;

import com.cn.entity.publicFields.publicFields;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * 商户功能关系表
 * Created by WangWenFang on 2016/11/29.
 */
@Entity
@Table(name="ALLPAY_SHOPMATCHFUNC")
@GenericGenerator(name = "system-uuid", strategy = "uuid")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class AllpayShopmatchfunc extends publicFields {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @Column(name = "SHOPMATCHFUNC_ID", length = 32)
    private String shopmatchfuncId;	//商户功能关系表主键

    @Column(name = "SHOPMATCHFUNC_SHOPID", length = 32)
    private String shopmatchfuncShopid;	//商户id

    @Column(name = "SHOPMATCHFUNC_FUNCTIONID", length = 32)
    private String shopmatchfuncFunctionid;	//商助权限功能表主键id

    @Column(name = "SHOPMATCHFUNC_MENUID", length = 32)
    private String shopmatchfuncMenuid;	//商助权限菜单表主键id

    @Column(name = "SHOPMATCHFUNC_SYSTEMID", length = 32)
    private String shopmatchfuncSystemid;	//所属系统id

    /**
     *  0:停用 1：审核通过2:合同到期  3、4:预注册 5:待审核 6:驳回(商户)
     *  AUDITING:审核中（0），AUDIT_FAILED：审核驳回（1），AUDIT_SUCCESS：审核通过（2），INIT-初始（3），PROCESS-处理中（0），SUCCESS-成功（2），FAIL-失败（1） （口碑）
     */
    @Column(name = "ALLPAY_STATE", length = 1)
    private int ALLPAY_STATE;

    /**
     *  1---启用  0---停用
     */
    @Column(name = "ALLPAY_ISSTART", length = 1)
    private int ALLPAY_ISSTART;

    public int getALLPAY_STATE() {
        return ALLPAY_STATE;
    }

    public void setALLPAY_STATE(int ALLPAY_STATE) {
        this.ALLPAY_STATE = ALLPAY_STATE;
    }

    public int getALLPAY_ISSTART() {
        return ALLPAY_ISSTART;
    }

    public void setALLPAY_ISSTART(int ALLPAY_ISSTART) {
        this.ALLPAY_ISSTART = ALLPAY_ISSTART;
    }

    public String getShopmatchfuncId() {
        return shopmatchfuncId;
    }

    public void setShopmatchfuncId(String shopmatchfuncId) {
        this.shopmatchfuncId = shopmatchfuncId;
    }

    public String getShopmatchfuncShopid() {
        return shopmatchfuncShopid;
    }

    public void setShopmatchfuncShopid(String shopmatchfuncShopid) {
        this.shopmatchfuncShopid = shopmatchfuncShopid;
    }

    public String getShopmatchfuncFunctionid() {
        return shopmatchfuncFunctionid;
    }

    public void setShopmatchfuncFunctionid(String shopmatchfuncFunctionid) {
        this.shopmatchfuncFunctionid = shopmatchfuncFunctionid;
    }

    public String getShopmatchfuncMenuid() {
        return shopmatchfuncMenuid;
    }

    public void setShopmatchfuncMenuid(String shopmatchfuncMenuid) {
        this.shopmatchfuncMenuid = shopmatchfuncMenuid;
    }

    public String getShopmatchfuncSystemid() {
        return shopmatchfuncSystemid;
    }

    public void setShopmatchfuncSystemid(String shopmatchfuncSystemid) {
        this.shopmatchfuncSystemid = shopmatchfuncSystemid;
    }
}

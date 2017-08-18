package com.cn.entity.po.shop;

import com.cn.entity.publicFields.publicFields;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * 功能表（资源表）
 * Created by WangWenFang on 2016/11/29.
 */
@Entity
@Table(name="ALLPAY_SHOPFUNCTION")
@GenericGenerator(name = "system-uuid", strategy = "uuid")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class AllpayShopfunction extends publicFields {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @Column(name = "SHOPFUNCTION_ID", length = 32)
    private String shopfunctionId;	//商户功能表主键id

    @Column(name = "SHOPFUNCTION_NAME", length = 2000)
    private String shopfunctionName;	//功能名称

    @Column(name = "SHOPFUNCTION_SUP_SYSTEMID", length = 32)
    private String shopfunctionSupSystemid;	//所属系统id

    @Column(name = "SHOPFUNCTION_TYPE", length = 1)
    private int shopfunctionType;	//"类型 1--菜单栏目 2---功能操作（1：url、归属菜单id、功能显示循序  2：功能识别码）"

    @Column(name = "SHOPFUNCTION_URL", length = 2000)
    private String shopfunctionUrl;	//菜单栏目url地址（菜单栏目）

    @Column(name = "SHOPFUNCTION_MENUID", length = 32)
    private String shopfunctionMenuid;	//归属菜单id（菜单栏目）

    @Column(name = "SHOPFUNCTION_TOP_MENUID", length = 32)
    private String shopfunctionTopMenuid;	//归属顶级菜单id（菜单栏目）

    @Column(name = "SHOPFUNCTION_ORDER", length = 10)
    private int	shopfunctionOrder; //功能显示循序（菜单栏目）

    @Column(name = "SHOPFUNCTION_CODE", length = 1000)
    private String shopfunctionCode;	//功能识别码（功能操作）

    @Column(name = "SHOPFUNCTION_STATE", length = 1)
    private int shopfunctionState;	//是否启用 1--启用 2---禁用

    /**
     * 是否默认功能  1是 2否
     */
    @Column(name = "SHOPFUNCTION_ISDEFAULT", length = 1)
    private String isDefault;

    public String getShopfunctionId() {
        return shopfunctionId;
    }

    public void setShopfunctionId(String shopfunctionId) {
        this.shopfunctionId = shopfunctionId;
    }

    public String getShopfunctionName() {
        return shopfunctionName;
    }

    public void setShopfunctionName(String shopfunctionName) {
        this.shopfunctionName = shopfunctionName;
    }

    public String getShopfunctionSupSystemid() {
        return shopfunctionSupSystemid;
    }

    public void setShopfunctionSupSystemid(String shopfunctionSupSystemid) {
        this.shopfunctionSupSystemid = shopfunctionSupSystemid;
    }

    public int getShopfunctionType() {
        return shopfunctionType;
    }

    public void setShopfunctionType(int shopfunctionType) {
        this.shopfunctionType = shopfunctionType;
    }

    public String getShopfunctionUrl() {
        return shopfunctionUrl;
    }

    public void setShopfunctionUrl(String shopfunctionUrl) {
        this.shopfunctionUrl = shopfunctionUrl;
    }

    public String getShopfunctionMenuid() {
        return shopfunctionMenuid;
    }

    public void setShopfunctionMenuid(String shopfunctionMenuid) {
        this.shopfunctionMenuid = shopfunctionMenuid;
    }

    public String getShopfunctionTopMenuid() {
        return shopfunctionTopMenuid;
    }

    public void setShopfunctionTopMenuid(String shopfunctionTopMenuid) {
        this.shopfunctionTopMenuid = shopfunctionTopMenuid;
    }

    public int getShopfunctionOrder() {
        return shopfunctionOrder;
    }

    public void setShopfunctionOrder(int shopfunctionOrder) {
        this.shopfunctionOrder = shopfunctionOrder;
    }

    public String getShopfunctionCode() {
        return shopfunctionCode;
    }

    public void setShopfunctionCode(String shopfunctionCode) {
        this.shopfunctionCode = shopfunctionCode;
    }

    public int getShopfunctionState() {
        return shopfunctionState;
    }

    public void setShopfunctionState(int shopfunctionState) {
        this.shopfunctionState = shopfunctionState;
    }

    public String getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(String isDefault) {
        this.isDefault = isDefault;
    }
}

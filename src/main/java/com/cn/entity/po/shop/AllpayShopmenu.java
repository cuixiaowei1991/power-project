package com.cn.entity.po.shop;

import com.cn.entity.publicFields.publicFields;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * 菜单表
 * Created by WangWenFang on 2016/11/29.
 */
@Entity
@Table(name="ALLPAY_SHOPMENU")
@GenericGenerator(name = "system-uuid", strategy = "uuid")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class AllpayShopmenu extends publicFields {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @Column(name = "SHOPMENU_ID", length = 32)
    private String shopmenuId;  //商户菜单表主键id

    @Column(name = "SHOPMENU_NAME", length = 200)
    private String shopmenuName;	//菜单名称

    @Column(name = "SHOPMENU_STATE", length = 1)
    private int shopmenuState;	//菜单启用状态 1---启用 2---禁用	1

    @Column(name = "SHOPMENU_ORDER", length = 10)
    private int shopmenuOrder;	//菜单显示顺序

    @Column(name = "SHOPMENU_SUPERIORID", length = 32)
    private String shopmenuSuperiorid;	//上级菜单id

    @Column(name = "SHOPMENU_LEVEL", length = 5)
    private String shopmenuLevel;	//级别 1-一级 2-二级


    public String getShopmenuId() {
        return shopmenuId;
    }

    public void setShopmenuId(String shopmenuId) {
        this.shopmenuId = shopmenuId;
    }

    public String getShopmenuName() {
        return shopmenuName;
    }

    public void setShopmenuName(String shopmenuName) {
        this.shopmenuName = shopmenuName;
    }

    public int getShopmenuState() {
        return shopmenuState;
    }

    public void setShopmenuState(int shopmenuState) {
        this.shopmenuState = shopmenuState;
    }

    public int getShopmenuOrder() {
        return shopmenuOrder;
    }

    public void setShopmenuOrder(int shopmenuOrder) {
        this.shopmenuOrder = shopmenuOrder;
    }

    public String getShopmenuSuperiorid() {
        return shopmenuSuperiorid;
    }

    public void setShopmenuSuperiorid(String shopmenuSuperiorid) {
        this.shopmenuSuperiorid = shopmenuSuperiorid;
    }

    public String getShopmenuLevel() {
        return shopmenuLevel;
    }

    public void setShopmenuLevel(String shopmenuLevel) {
        this.shopmenuLevel = shopmenuLevel;
    }
}

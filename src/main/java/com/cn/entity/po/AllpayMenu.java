package com.cn.entity.po;

import com.cn.entity.publicFields.publicFields;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Created by sun.yayi on 2016/11/15.
 *
 * 菜单表
 */
@Entity
@Table(name="ALLPAY_MENU")
@GenericGenerator(name = "system-uuid", strategy = "uuid")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class AllpayMenu extends publicFields {
    /**
     * 菜单表主键id
     */
    @Id
    @GeneratedValue(generator = "system-uuid")
    @Column(name = "MENU_ID", length = 32)
    private String menuId;

    /**
     * 菜单名称
     */
    @Column(name = "MENU_NAME", length = 200)
    private String menu_name;

    /**
     * 菜单级别
     */
    @Column(name = "MENU_LEVEL", length = 5)
    private String menu_level;


    /**
     * 菜单启用状态 1---启用 2---禁用
     */
    @Column(name = "MENU_STATE", length = 1)
    private int menu_state;

    /**
     * 菜单显示顺序
     */
    @Column(name = "MENU_ORDER", length = 50)
    private int menu_order;

    /**
     * 上级菜单id
     */
    @Column(name = "MENU_SUPERIORID", length = 32)
    private String menu_superId;

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    public String getMenu_name() {
        return menu_name;
    }

    public void setMenu_name(String menu_name) {
        this.menu_name = menu_name;
    }

    public int getMenu_state() {
        return menu_state;
    }

    public void setMenu_state(int menu_state) {
        this.menu_state = menu_state;
    }

    public int getMenu_order() {
        return menu_order;
    }

    public void setMenu_order(int menu_order) {
        this.menu_order = menu_order;
    }

    public String getMenu_superId() {
        return menu_superId;
    }

    public void setMenu_superId(String menu_superId) {
        this.menu_superId = menu_superId;
    }

    public String getMenu_level() {
        return menu_level;
    }

    public void setMenu_level(String menu_level) {
        this.menu_level = menu_level;
    }
}

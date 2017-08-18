package com.cn.entity.dto;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * 菜单管理dto
 * Created by WangWenFang on 2016/11/23.
 */
public class AllpayMenuDto {

    @JsonProperty
    private String menuId;	//菜单ID

    @JsonProperty
    private String menuName;	//菜单名称

    @JsonProperty
    private String menuOrder;	//菜单顺序

    @JsonProperty
    private String menuSuperiorId;	//上级菜单ID

    @JsonProperty
    private String menuLevel;	//级别	一级，二级

    @JsonProperty
    private String menuState;	//状态	1--启用 2---禁用

    @JsonProperty
    private String userNameFromQXCookie; //登录用户名

    @Override
    public String toString() {
        return "AllpayMenuDto{" +
                "menuId='" + menuId + '\'' +
                ", menuName='" + menuName + '\'' +
                ", menuOrder='" + menuOrder + '\'' +
                ", menuSuperiorId='" + menuSuperiorId + '\'' +
                ", menuLevel='" + menuLevel + '\'' +
                ", menuState='" + menuState + '\'' +
                ", userNameFromQXCookie='" + userNameFromQXCookie + '\'' +
                '}';
    }

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getMenuOrder() {
        return menuOrder;
    }

    public void setMenuOrder(String menuOrder) {
        this.menuOrder = menuOrder;
    }

    public String getMenuSuperiorId() {
        return menuSuperiorId;
    }

    public void setMenuSuperiorId(String menuSuperiorId) {
        this.menuSuperiorId = menuSuperiorId;
    }

    public String getMenuLevel() {
        return menuLevel;
    }

    public void setMenuLevel(String menuLevel) {
        this.menuLevel = menuLevel;
    }

    public String getMenuState() {
        return menuState;
    }

    public void setMenuState(String menuState) {
        this.menuState = menuState;
    }

    public String getUserNameFromQXCookie() {
        return userNameFromQXCookie;
    }

    public void setUserNameFromQXCookie(String userNameFromQXCookie) {
        this.userNameFromQXCookie = userNameFromQXCookie;
    }
}

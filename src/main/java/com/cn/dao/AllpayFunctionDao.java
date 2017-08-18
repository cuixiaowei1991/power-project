package com.cn.dao;

import com.cn.entity.dto.AllpayFunctionDto;
import com.cn.entity.po.AllpayFunction;
import com.cn.entity.po.AllpayMenu;
import com.cn.entity.po.AllpayUser;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Criterion;

import java.util.List;

/**
 * Created by sun.yayi on 2016/11/22.
 */
public interface AllpayFunctionDao {
    /**
     *  2.3.6获取所有菜单及功能信息列表（已启用状态）
     * @return
     */
    List getMenuFuncInfoList() throws Exception;

    /**
     *  2.3.1获取资源管理信息列表
     * @return
     */
    List<?> getAllpayFuncInfoList(AllpayFunctionDto bean,Integer currentPage,Integer pageSize) throws Exception;

    /**
     * 获取菜单名称
     * @param function_top_menuId
     * @return
     */
    AllpayMenu getMenuName(String function_top_menuId) throws Exception;

    boolean saveOrUpdate(AllpayFunction function) throws Exception;

    /**
     *  根据Id获取对象实例
     * @param funcId
     * @return
     */
    AllpayFunction getAllpayFunctionById(String funcId) throws Exception;

    /**
     * 根据Id删除对象
     * @param funcId
     * @return
     * @throws Exception
     */
    boolean delete(String funcId) throws Exception;

    /**
     * 总条数
     * @param bean
     * @return
     * @throws Exception
     */
    int count(AllpayFunctionDto bean) throws Exception;

    /**
     *  2.6.1获取业务菜单信息列表
     *  2.6.2获取菜单功能信息列表
     * @return
     */
    List<?> getMenuInfoList(AllpayFunctionDto bean, String flag) throws Exception;

}

package com.cn.dao.shop;

import java.util.List;
import java.util.Map;

import com.cn.entity.dto.AllpayFunctionDto;
import com.cn.entity.po.shop.*;

/**
 * 商助资源（功能）管理dao层接口
 * Created by WangWenFang on 2016/11/30.
 */
public interface AllpayShopfunctionDao {

    /**
     *  2.2.6获取商助所有菜单及功能信息列表（已启用状态）
     * @return
     */
    List getMenuFuncInfoList() throws Exception;

    /**
     *  2.2.1获取商助资源管理信息列表
     * @return
     */
    List<?> getShopFuncInfoList(AllpayFunctionDto bean,Integer currentPage,Integer pageSize) throws Exception;

    /**
     * 获取菜单名称
     * @param function_top_menuId
     * @return
     */
    AllpayShopmenu getMenuName(String function_top_menuId) throws Exception;

    /**
     * 2.2.2新增商助功能资源信息
     * 2.2.3修改商助功能资源信息
     * @param function
     * @return
     * @throws Exception
     */
    boolean saveOrUpdate(AllpayShopfunction function) throws Exception;

    /**
     *  根据Id获取对象实例
     * @param funcId
     * @return
     */
    AllpayShopfunction getShopFuncInfoById(String funcId) throws Exception;

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
    /****/
    public String insertMerchantMenuAndFunc(AllpayShopmatchfunc shop)throws Exception;
    /****/
    public List<Map<String, Object>> obtainMerchantMenuAndFuncList(String merchantId)throws Exception;
    /****/
    public String deleteForMerchantId(String merchantId)throws Exception;


    List<?> getMenuInfoList(AllpayFunctionDto bean, String flag) throws Exception;


    List<?> getAllMenuInfoList(AllpayFunctionDto bean,String flag) throws Exception;

    List getShopQxInfoList(String shopId) throws Exception;

    boolean deleteRoleMatchFunc(String roleType) throws Exception;

    boolean insertRoleMatchFunc(AllpayRolematchfunc rolematchfunc) throws Exception;

    /**
     * 获取角色权限信息列表
     * @param userType
     * @return
     */
    List<?> getRoleFuncList(String userType);
}

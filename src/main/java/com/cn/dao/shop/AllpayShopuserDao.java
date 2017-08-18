package com.cn.dao.shop;

import java.util.List;
import java.util.Map;

import com.cn.entity.dto.AllpayUserDto;
import com.cn.entity.po.shop.AllpayShopuser;

/**
 * 商助用户管理dao层接口
 * Created by WangWenFang on 2016/12/15.
 */
public interface AllpayShopuserDao {
    
	boolean insert(AllpayShopuser shop) throws Exception;

	boolean delete(String shopuserId,boolean serious)throws Exception;

	boolean update(AllpayShopuser shop) throws Exception;

	AllpayShopuser obtain(String shopuserId)throws Exception;

    public boolean checkShopUserExist(Map<String, Object> source)throws Exception;

    List<?> getAllpayShopuserList(AllpayUserDto bean) throws Exception;

	List<?> checkMerName(String merName) throws Exception;

	List<?> checkStore(String merName, String storeName) throws Exception;

	boolean checkIsExist(String code, String value, String id) throws Exception;

	/**
	 * 获取用户信息列表（商助系统用）
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	List<?> getShopUserInfoList(AllpayUserDto bean,String marked) throws Exception;

	/**
	 * 根据主键查询
	 * @param shopuserId
	 * @return
	 * @throws Exception
	 */
	public AllpayShopuser getShopUserByID(String shopuserId)throws Exception;

	/**
	 * 根据商户id查询商户所属代理 的 4位编码
	 * @param shopid
	 * @return
	 */
	public List<?> getAgentInfoByMerchantID(String shopid);

	/**
	 * 根据用户昵称查询（zl_四位代理商编号+随机6位数）
	 * @param nickname
	 * @return
	 * @throws Exception
	 */
	public AllpayShopuser getShopUserByNickName(String nickname)throws Exception;

	public List<?> getStoreInfo(String storeid);

	public List<?> checkShopUserExitByParamters(String paramters) throws Exception;

	List<?> getUser(String username,String password) throws Exception;

	boolean updatePwd(Map<String, Object> source) throws Exception;

	List<Map<String, Object>> getShopIdByWechat(String openid, String appid) throws Exception;
}

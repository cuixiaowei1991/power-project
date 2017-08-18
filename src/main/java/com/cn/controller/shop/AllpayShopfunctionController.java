package com.cn.controller.shop;

import com.cn.MsgCode.MsgAndCode;
import com.cn.common.LogHelper;
import com.cn.entity.dto.AllpayFunctionDto;
import com.cn.service.shop.AllpayShopfunctionService;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ObjectNode;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * 商助资源（功能）管理
 * Created by WangWenFang on 2016/11/30.
 */
@Controller
@RequestMapping("/allpayShopFunction")
public class AllpayShopfunctionController {

    @Autowired
    private AllpayShopfunctionService allpayShopfunctionService;

    /**
     * 2.2.1获取商助资源管理信息列表
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/list", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    public String getShopFuncInfoList(@RequestBody AllpayFunctionDto bean){
        LogHelper.info("2.2.1获取商助资源管理信息列表 请求参数：" + bean.toString());
        JSONObject resultJO = new JSONObject();
        try{
            resultJO=allpayShopfunctionService.getShopFuncInfoList(bean);
        }catch (Exception e) {
            resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_005);
            resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_005);
            e.printStackTrace();
        }
        return resultJO.toString();
    }

    /**
     * 2.2.2新增商助功能资源信息
     * @param bean
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/insert", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    public String insertShopFuncInfo(@RequestBody AllpayFunctionDto bean){
        LogHelper.info("2.2.2新增商助功能资源信息 请求参数："+bean.toString());
        JSONObject resultJO = new JSONObject();
        try{
            resultJO= allpayShopfunctionService.insertShopFuncInfo(bean);
        }catch (Exception e) {
            resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_005);
            resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_005);
            e.printStackTrace();
        }
        return resultJO.toString();
    }

    /**
     *  2.2.3修改商助功能资源信息
     * @param bean
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/update", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    public String updateShopFuncInfo(@RequestBody AllpayFunctionDto bean){
        LogHelper.info(" 2.2.3修改商助功能资源信息 请求参数："+bean.toString());
        JSONObject resultJO = new JSONObject();
        try{
            resultJO= allpayShopfunctionService.updateShopFuncInfo(bean);
        }catch (Exception e) {
            resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_005);
            resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_005);
            e.printStackTrace();
        }
        return resultJO.toString();
    }

    /**
     *  2.2.4删除商助功能资源信息
     * @param bean
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/delete", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    public String deleteShopFuncInfo(@RequestBody AllpayFunctionDto bean){
        LogHelper.info("2.2.4删除商助功能资源信息 请求参数："+bean.toString());
        JSONObject resultJO = new JSONObject();
        try{
            resultJO= allpayShopfunctionService.deleteShopFuncInfo(bean);
        }catch (Exception e) {
            resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_005);
            resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_005);
            e.printStackTrace();
        }
        return resultJO.toString();
    }

    /**
     * 2.2.5获取商助功能资源信息
     * @param bean
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/get", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    public String getShopFuncInfoById(@RequestBody AllpayFunctionDto bean){
        LogHelper.info("2.2.5获取商助功能资源信息 请求参数："+bean.toString());
        JSONObject resultJO = new JSONObject();
        try{
            resultJO= allpayShopfunctionService.getShopFuncInfoById(bean);
        }catch (Exception e) {
            resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_005);
            resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_005);
            e.printStackTrace();
        }
        return resultJO.toString();
    }


    /**
     * 2.2.6获取商户所有菜单及功能信息列表（已启用状态）
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/getMenuFuncList", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    public String getMenuFuncInfoList(){
        JSONObject resultJO = new JSONObject();
        try{
            resultJO=allpayShopfunctionService.getMenuFuncInfoList();
        } catch (Exception e) {
            resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_005);
            resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_005);
            e.printStackTrace();
        }
        return resultJO.toString();
    }


    /**
	 * 新增商户设置信息
	 * @param source
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/insertMenuAndFunc",produces="application/json;charset=UTF-8")
	public String insertMerchantMenuAndFunc(@RequestBody Map<String, Object> source){
		LogHelper.info("========>商户管理新增商户设置信息");
		String result = null;
		try {
			result = allpayShopfunctionService.insertMerchantMenuAndFunc(source);
		} catch (Exception ex) {
			LogHelper.error(ex, "商户管理新增商户设置信息异常!!!!!", false);
			return returnErrorMessage();
		}
		return result;
	}

    /**
     * 2.6.1获取业务菜单信息列表
     * @param bean
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/getMenuInfoList", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    public String getMenuInfoList(@RequestBody AllpayFunctionDto bean){
        LogHelper.info("2.6.1获取商助业务菜单信息列表 请求参数："+bean.toString());
        JSONObject resultJO = new JSONObject();
        try{
            resultJO= allpayShopfunctionService.getMenuInfoList(bean, "1");
        }catch (Exception e) {
            resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_005);
            resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_005);
            e.printStackTrace();
        }
        return resultJO.toString();
    }

    /**
     * 2.6.2获取菜单功能信息列表
     * @param bean
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/getUserMenuFuncInfoList", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    public String getUserMenuFuncInfoList(@RequestBody AllpayFunctionDto bean){
        LogHelper.info("2.6.2获取商助菜单功能信息列表 请求参数："+bean.toString());
        JSONObject resultJO = new JSONObject();
        try{
            resultJO= allpayShopfunctionService.getMenuInfoList(bean, null);
        }catch (Exception e) {
            resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_005);
            resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_005);
            e.printStackTrace();
        }
        return resultJO.toString();
    }

	/**
	 * 获取商户设置信息
	 * @param source
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/getMenuAndFunc",produces="application/json;charset=UTF-8")
	public String obtainMerchantMenuAndFunc(@RequestBody Map<String, Object> source){
		LogHelper.info("========>商户管理获取商户设置信息");
		String result = null;
		try {
			result = allpayShopfunctionService.obtainMerchantMenuAndFuncList(source);
		} catch (Exception ex) {
			LogHelper.error(ex, "商户管理获取商户设置信息异常!!!!!", false);
			return returnErrorMessage();
		}
		return result;
	}


    /**
     *  2.4.2新增角色权限信息
     * @param source
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/insertRoleInfo", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    public String insertRoleInfo(@RequestBody Map<String, Object> source){
        LogHelper.info("新增用户设置----------controller：--------"+source);
        JSONObject resultJO = new JSONObject();
        try{
            resultJO=allpayShopfunctionService.insertRoleInfo(source);
        } catch (Exception e) {
            resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_005);
            resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_005);
            e.printStackTrace();
        }
        return resultJO.toString();
    }

    /**
     * 2.4.3获取商户权限信息列表
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/getShopQxInfoList", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    public String getShopQxInfoList(@RequestBody Map<String, Object> source){
        JSONObject resultJO = new JSONObject(source);
        try{
            resultJO=allpayShopfunctionService.getShopQxInfoList(source);
        } catch (Exception e) {
            resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_005);
            resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_005);
            e.printStackTrace();
        }
        return resultJO.toString();
    }


	/**
	 * 返回服务器异常信息
	 * @return
	 */
    private String returnErrorMessage(){
		
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode node = mapper.createObjectNode();
		node.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_00603);
		node.put(MsgAndCode.RSP_DESC, MsgAndCode.CODE_00603_MSG);
		return node.toString();
	}

    /**
     * 2.4.1获取角色权限信息列表
     * @param source
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/getRoleFuncList", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    public String getRoleFuncList(@RequestBody Map<String, Object> source){
        LogHelper.info("2.4.1获取角色权限信息列表 请求参数："+source.toString());
        JSONObject resultJO = new JSONObject();
        try{
            resultJO= allpayShopfunctionService.getRoleFuncList(source);
        } catch (Exception e) {
            resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_005);
            resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_005);
            e.printStackTrace();
        }
        return resultJO.toString();
    }
}

package com.cn.service.impl.agent;

import com.cn.MsgCode.MsgAndCode;
import com.cn.common.CommonHelper;
import com.cn.dao.agent.AllpayAgentMenuDao;
import com.cn.entity.dto.AllpayMenuDto;
import com.cn.entity.po.agent.AllpayAgentMenu;
import com.cn.service.agent.AllpayAgentMenuService;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.cn.common.CookieHelper.getCookieByName;

/**
 * 菜单管理业务层实现
 * Created by sunyayi on 2016/11/30.
 */
@Service
@Transactional(rollbackFor = {Exception.class, RuntimeException.class})
public class AllpayAgentMenuServiceImpl implements AllpayAgentMenuService {

    @Autowired
    private AllpayAgentMenuDao allpayAgentMenuDaoImpl;


    public String obtainList(AllpayMenuDto allpayMenuDto, Integer currentPage, Integer pageSize) throws Exception {
        JSONObject resultJO = new JSONObject();
        JSONArray array = new JSONArray();
        List list = allpayAgentMenuDaoImpl.obtainList(allpayMenuDto, currentPage, pageSize);
        if(null != list && list.size() > 0){
            for(int i=0,count=list.size(); i<count; i++){
                JSONObject allpayMenuJO = new JSONObject();
                Object object = list.get(i);
                Map map = (Map)object;

                allpayMenuJO.put("menuId", map.get("AGENTMENU_ID"));  //菜单ID
                allpayMenuJO.put("menuName", map.get("AGENTMENU_NAME"));	//菜单名称
                allpayMenuJO.put("menuOrder", map.get("AGENTMENU_ORDER"));	//菜单顺序
                allpayMenuJO.put("menuSuperiorId", map.get("AGENTMENU_SUPERIORID"));	//上级菜单ID
                allpayMenuJO.put("menuSuperiorName", map.get("MENUSUPERIORNAME"));	//上级菜单名称
                allpayMenuJO.put("menuLevel", map.get("AGENTMENU_LEVEL"));	//级别	一级，二级
                allpayMenuJO.put("menuState", map.get("AGENTMENU_STATE"));	//状态	1--启用 2---禁用
                array.put(allpayMenuJO);
            }
        }
        resultJO.put("lists", array);
        resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.SUCCESS_CODE);  //返回的状态码
        resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.SUCCESS_MESSAGE);  //返回的状态码描述
        return resultJO.toString();
    }

    public String add(AllpayMenuDto allpayMenuDto) throws Exception {
        JSONObject resultJO = new JSONObject();
        if(CommonHelper.isEmpty(allpayMenuDto.getMenuName())){  //菜单名称
            resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_00506);
            resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_00506);
            return resultJO.toString();
        }else{
            boolean isExist=allpayAgentMenuDaoImpl.isExistMenuName(allpayMenuDto);// true是存在
            if(isExist){
                resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_00605);
                resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.CODE_00605_MSG);
                return resultJO.toString();
            }
        }
        if(CommonHelper.isEmpty(allpayMenuDto.getMenuOrder())){  //菜单顺序
            resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_00507);
            resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_00507);
            return resultJO.toString();
        }
        if(CommonHelper.isEmpty(allpayMenuDto.getMenuSuperiorId())){   //上级菜单ID
            resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_00508);
            resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_00508);
            return resultJO.toString();
        }
        if(CommonHelper.isEmpty(allpayMenuDto.getMenuLevel())){  //级别	一级，二级
            resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_00509);
            resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_00509);
            return resultJO.toString();
        }
        if(CommonHelper.isEmpty(allpayMenuDto.getMenuState())){   //状态	1--启用 2---禁用
            resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_00510);
            resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_00510);
            return resultJO.toString();
        }
//        if (CommonHelper.isEmpty(getCookieByName("userName"))) {
//            resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_002);
//            resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_002);
//            return resultJO.toString();
//        }


        AllpayAgentMenu allpayAgentMenu = new AllpayAgentMenu();
        allpayAgentMenu.setAgentmenu_name(allpayMenuDto.getMenuName());  //菜单名称
        allpayAgentMenu.setAgentmenu_order(Integer.parseInt(allpayMenuDto.getMenuOrder()));  //菜单顺序
        allpayAgentMenu.setAgentmenu_superiorid(allpayMenuDto.getMenuSuperiorId());  //上级菜单ID
        allpayAgentMenu.setAgentmenu_level(allpayMenuDto.getMenuLevel()); //级别	一级，二级
        allpayAgentMenu.setAgentmenu_state(Integer.parseInt(allpayMenuDto.getMenuState())); //状态	1--启用 2---禁用

        JSONObject publicFileds = CommonHelper.getPublicFileds(MsgAndCode.OPERATION_NEW, "",allpayMenuDto.getUserNameFromQXCookie());
        String userName = publicFileds.getString("userName");
        Date now = CommonHelper.getStringToDate(publicFileds.getString("now"), "yyyy-MM-dd HH:mm:ss");
        String record = publicFileds.getString("record");

        allpayAgentMenu.setALLPAY_CREATER(userName);  //创建人
        allpayAgentMenu.setALLPAY_CREATETIME(now);  //创建时间
        allpayAgentMenu.setALLPAY_UPDATETIME(now);  //修改时间
        allpayAgentMenu.setALLPAY_LOGICDEL("1");  //逻辑删除标记  1---未删除 2---已删除
        allpayAgentMenu.setALLPAY_LOGRECORD(record);  //操作日志记录(人物-时间-具体操作，不做清空，追加信息)
        boolean result = allpayAgentMenuDaoImpl.add(allpayAgentMenu);
        if(result){
            resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.SUCCESS_CODE);
            resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.SUCCESS_MESSAGE);
        }else{
            resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_001);
            resultJO.put(MsgAndCode.RSP_DESC, "新增菜单信息错误！");
        }
        return resultJO.toString();
    }

    public String update(AllpayMenuDto allpayMenuDto) throws Exception {
        JSONObject resultJO = new JSONObject();
        String menuId = allpayMenuDto.getMenuId();  //菜单id
        if(CommonHelper.isEmpty(menuId)){
            resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_00505);
            resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_00505);
            return resultJO.toString();
        }
        if(CommonHelper.isEmpty(allpayMenuDto.getMenuName())){  //菜单名称
            resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_00506);
            resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_00506);
            return resultJO.toString();
        }else{
            boolean isExist=allpayAgentMenuDaoImpl.isExistMenuName(allpayMenuDto);// true是存在
            if(isExist){
                resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_00605);
                resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.CODE_00605_MSG);
                return resultJO.toString();
            }
        }
        if(CommonHelper.isEmpty(allpayMenuDto.getMenuOrder())){  //菜单顺序
            resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_00507);
            resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_00507);
            return resultJO.toString();
        }
        if(CommonHelper.isEmpty(allpayMenuDto.getMenuSuperiorId())){   //上级菜单ID
            resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_00508);
            resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_00508);
            return resultJO.toString();
        }
        if(CommonHelper.isEmpty(allpayMenuDto.getMenuLevel())){  //级别	一级，二级
            resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_00509);
            resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_00509);
            return resultJO.toString();
        }
        if(CommonHelper.isEmpty(allpayMenuDto.getMenuState())){   //状态	1--启用 2---禁用
            resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_00510);
            resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_00510);
            return resultJO.toString();
        }
//        if (CommonHelper.isEmpty(getCookieByName("userName"))) {
//            resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_002);
//            resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_002);
//            return resultJO.toString();
//        }

        //修改菜单信息
        AllpayAgentMenu allpayAgentMenu = allpayAgentMenuDaoImpl.getById(menuId);
        if(null == allpayAgentMenu){
            resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_00511);
            resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_00511);
            return resultJO.toString();
        }

        allpayAgentMenu.setAgentmenu_name(allpayMenuDto.getMenuName());
        allpayAgentMenu.setAgentmenu_level(allpayMenuDto.getMenuLevel());
        allpayAgentMenu.setAgentmenu_state(Integer.parseInt(allpayMenuDto.getMenuState()));
        allpayAgentMenu.setAgentmenu_superiorid(allpayMenuDto.getMenuSuperiorId());
        allpayAgentMenu.setAgentmenu_order(Integer.parseInt(allpayMenuDto.getMenuOrder()));

        JSONObject publicFileds = CommonHelper.getPublicFileds(MsgAndCode.OPERATION_UPDATE, allpayAgentMenu.getALLPAY_LOGRECORD(),allpayMenuDto.getUserNameFromQXCookie());
        String userName = publicFileds.getString("userName");
        Date now = CommonHelper.getStringToDate(publicFileds.getString("now"), "yyyy-MM-dd HH:mm:ss");
        String record = publicFileds.getString("record");

        allpayAgentMenu.setALLPAY_UPDATER(userName);  //修改人
        allpayAgentMenu.setALLPAY_UPDATETIME(now);  //修改时间
        allpayAgentMenu.setALLPAY_LOGRECORD(record);  //操作日志记录(人物-时间-具体操作，不做清空，追加信息)
        boolean result = allpayAgentMenuDaoImpl.update(allpayAgentMenu);
        if(result){
            resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.SUCCESS_CODE);
            resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.SUCCESS_MESSAGE);
        }else{
            resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_001);
            resultJO.put(MsgAndCode.RSP_DESC, "修改菜单信息错误！");
        }
        return resultJO.toString();
    }

    public String getById(AllpayMenuDto allpayMenuDto) throws Exception {
        JSONObject resultJO = new JSONObject();
        String menuId = allpayMenuDto.getMenuId();  //菜单id
        if(CommonHelper.isEmpty(menuId)){
            resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_00505);
            resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_00505);
            return resultJO.toString();
        }
        AllpayAgentMenu allpayAgentMenu = allpayAgentMenuDaoImpl.getById(menuId);
        if(null == allpayAgentMenu){
            resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_00511);
            resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_00511);
            return resultJO.toString();
        }

        //返回前端信息
        resultJO.put("menuId", allpayAgentMenu.getAgentmenu_id());  //菜单ID
        resultJO.put("menuName", allpayAgentMenu.getAgentmenu_name());    //菜单名称
        resultJO.put("menuOrder", allpayAgentMenu.getAgentmenu_order());    //菜单顺序
        resultJO.put("menuSuperiorId", allpayAgentMenu.getAgentmenu_superiorid());    //上级菜单ID
        resultJO.put("menuLevel", allpayAgentMenu.getAgentmenu_level());    //级别	一级，二级
        resultJO.put("menuState", allpayAgentMenu.getAgentmenu_state());	//状态	1--启用 2---禁用
        resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.SUCCESS_CODE);   //apay返回的状态码	000 为正常返回,其他为异常返回
        resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.SUCCESS_MESSAGE); //apay返回的状态码描述	异常返回时的异常说明
        return resultJO.toString();
    }

    public String delete(AllpayMenuDto allpayMenuDto) throws Exception {
        JSONObject resultJO = new JSONObject();
        String menuId = allpayMenuDto.getMenuId();
        if(CommonHelper.isEmpty(menuId)){
            resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_00505);
            resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_00505);
            return resultJO.toString();
        }

//        if (CommonHelper.isEmpty(getCookieByName("userName"))) {
//            resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_002);
//            resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_002);
//            return resultJO.toString();
//        }

        AllpayAgentMenu allpayAgentMenu = allpayAgentMenuDaoImpl.getById(menuId);
        if(null == allpayAgentMenu){
            resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_00511);
            resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_00511);
            return resultJO.toString();
        }

        JSONObject publicFileds = CommonHelper.getPublicFileds(MsgAndCode.OPERATION_DELETE, allpayAgentMenu.getALLPAY_LOGRECORD(),allpayMenuDto.getUserNameFromQXCookie());
        String userName = publicFileds.getString("userName");
        Date now = CommonHelper.getStringToDate(publicFileds.getString("now"), "yyyy-MM-dd HH:mm:ss");
        String record = publicFileds.getString("record");

        allpayAgentMenu.setALLPAY_UPDATER(userName);  //修改人
        allpayAgentMenu.setALLPAY_UPDATETIME(now);  //修改时间
        allpayAgentMenu.setALLPAY_LOGICDEL("2");  //逻辑删除标记  1---未删除 2---已删除
        allpayAgentMenu.setALLPAY_LOGRECORD(record);  //操作日志记录(人物-时间-具体操作，不做清空，追加信息)

        boolean result = allpayAgentMenuDaoImpl.update(allpayAgentMenu);
        if(result){
            resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.SUCCESS_CODE);
            resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.SUCCESS_MESSAGE);
        }else{
            resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_001);
            resultJO.put(MsgAndCode.RSP_DESC, "删除菜单信息错误！");
        }
        return resultJO.toString();
    }
}

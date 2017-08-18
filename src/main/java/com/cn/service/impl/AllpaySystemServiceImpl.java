package com.cn.service.impl;

import com.cn.MsgCode.MsgAndCode;
import com.cn.common.CommonHelper;
import com.cn.dao.AllpaySystemDao;
import com.cn.entity.dto.AllpaySystemDto;
import com.cn.entity.po.AllpaySystem;
import com.cn.service.AllpaySystemService;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

import static com.cn.common.CookieHelper.getCookieByName;

/**
 * 系统管理业务层实现
 * Created by WangWenFang on 2016/11/23.
 */
@Service
@Transactional(rollbackFor = {Exception.class, RuntimeException.class})
public class AllpaySystemServiceImpl implements AllpaySystemService {

    @Autowired
    private AllpaySystemDao allpaySystemDao;

    public String add(AllpaySystemDto allpaySystemDto) throws Exception {
        JSONObject resultJO = new JSONObject();
        if(CommonHelper.isEmpty(allpaySystemDto.getSystemName())){  //系统名称
            resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_00501);
            resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_00501);
            return resultJO.toString();
        }else{
            boolean isExist=allpaySystemDao.isExistSysName(allpaySystemDto);// true是存在
            if(isExist){
                resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_00606);
                resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.CODE_00606_MSG);
                return resultJO.toString();
            }
        }
        if(CommonHelper.isEmpty(allpaySystemDto.getSystemPath())){  //系统路径
            resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_00502);
            resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_00502);
            return resultJO.toString();
        }
        if(CommonHelper.isEmpty(allpaySystemDto.getSystemState())){   //系统状态
            resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_00503);
            resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_00503);
            return resultJO.toString();
        }
//        if (CommonHelper.isEmpty(getCookieByName("userName"))) {
//            resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_002);
//            resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_002);
//            return resultJO.toString();
//        }

        AllpaySystem allpaySystem = new AllpaySystem();
        allpaySystem.setSystem_name(allpaySystemDto.getSystemName());  //系统名称
        allpaySystem.setSystem_path(allpaySystemDto.getSystemPath());  //系统路径
        allpaySystem.setSystem_state(Integer.parseInt(allpaySystemDto.getSystemState()));  //系统状态

        JSONObject publicFileds = CommonHelper.getPublicFileds(MsgAndCode.OPERATION_NEW, "", allpaySystemDto.getUserNameFromQXCookie());
        String userName = publicFileds.getString("userName");
        Date now = CommonHelper.getStringToDate(publicFileds.getString("now"), "yyyy-MM-dd HH:mm:ss");
        String record = publicFileds.getString("record");

        allpaySystem.setALLPAY_CREATER(userName);  //创建人
        allpaySystem.setALLPAY_CREATETIME(now);  //创建时间
        allpaySystem.setALLPAY_UPDATETIME(now);  //修改时间
        allpaySystem.setALLPAY_LOGICDEL("1");  //逻辑删除标记  1---未删除 2---已删除
        allpaySystem.setALLPAY_LOGRECORD(record);  //操作日志记录(人物-时间-具体操作，不做清空，追加信息)
        boolean result = allpaySystemDao.add(allpaySystem);
        if(result){
            resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.SUCCESS_CODE);
            resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.SUCCESS_MESSAGE);
        }else{
            resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_001);
            resultJO.put(MsgAndCode.RSP_DESC, "新增系统信息错误！");
        }
        return resultJO.toString();
    }

    public String delete(AllpaySystemDto allpaySystemDto) throws Exception {
        JSONObject resultJO = new JSONObject();
        String systemId = allpaySystemDto.getSystemId();
        if(CommonHelper.isEmpty(systemId)){
            resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_00500);
            resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_00500);
            return resultJO.toString();
        }

//        if (CommonHelper.isEmpty(getCookieByName("userName"))) {
//            resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_002);
//            resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_002);
//            return resultJO.toString();
//        }

        AllpaySystem allpaySystem = getById(AllpaySystem.class, systemId);
        if(null == allpaySystem){
            resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_00504);
            resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_00504);
            return resultJO.toString();
        }

        JSONObject publicFileds = CommonHelper.getPublicFileds(MsgAndCode.OPERATION_DELETE, allpaySystem.getALLPAY_LOGRECORD(), allpaySystemDto.getUserNameFromQXCookie());
        String userName = publicFileds.getString("userName");
        Date now = CommonHelper.getStringToDate(publicFileds.getString("now"), "yyyy-MM-dd HH:mm:ss");
        String record = publicFileds.getString("record");

        allpaySystem.setALLPAY_UPDATER(userName);  //修改人
        allpaySystem.setALLPAY_UPDATETIME(now);  //修改时间
        allpaySystem.setALLPAY_LOGICDEL("2");  //逻辑删除标记  1---未删除 2---已删除
        allpaySystem.setALLPAY_LOGRECORD(record);  //操作日志记录(人物-时间-具体操作，不做清空，追加信息)

        boolean result = allpaySystemDao.update(allpaySystem);
        if(result){
            resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.SUCCESS_CODE);
            resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.SUCCESS_MESSAGE);
        }else{
            resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_001);
            resultJO.put(MsgAndCode.RSP_DESC, "删除系统信息错误！");
        }
        return resultJO.toString();
    }

    public String update(AllpaySystemDto allpaySystemDto) throws Exception {
        JSONObject resultJO = new JSONObject();
        String systemId = allpaySystemDto.getSystemId();  //系统id
        if(CommonHelper.isEmpty(systemId)){
            resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_00500);
            resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_00500);
            return resultJO.toString();
        }
        if(CommonHelper.isEmpty(allpaySystemDto.getSystemName())){  //系统名称
            resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_00501);
            resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_00501);
            return resultJO.toString();
        }
        if(CommonHelper.isEmpty(allpaySystemDto.getSystemPath())){  //系统路径
            resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_00502);
            resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_00502);
            return resultJO.toString();
        }
        if(CommonHelper.isEmpty(allpaySystemDto.getSystemState())){   //系统状态
            resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_00503);
            resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_00503);
            return resultJO.toString();
        }
//        if (CommonHelper.isEmpty(getCookieByName("userName"))) {
//            resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_002);
//            resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_002);
//            return resultJO.toString();
//        }

        //查询并修改系统信息
        AllpaySystem allpaySystem = getById(AllpaySystem.class, systemId);
        if(null == allpaySystem){
            resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_00504);
            resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_00504);
            return resultJO.toString();
        }

        allpaySystem.setSystem_name(allpaySystemDto.getSystemName());
        allpaySystem.setSystem_path(allpaySystemDto.getSystemPath());
        allpaySystem.setSystem_state(Integer.parseInt(allpaySystemDto.getSystemState()));

        JSONObject publicFileds = CommonHelper.getPublicFileds(MsgAndCode.OPERATION_UPDATE, allpaySystem.getALLPAY_LOGRECORD(), allpaySystemDto.getUserNameFromQXCookie());
        String userName = publicFileds.getString("userName");
        Date now = CommonHelper.getStringToDate(publicFileds.getString("now"), "yyyy-MM-dd HH:mm:ss");
        String record = publicFileds.getString("record");

        allpaySystem.setALLPAY_UPDATER(userName);  //修改人
        allpaySystem.setALLPAY_UPDATETIME(now);  //修改时间
        allpaySystem.setALLPAY_LOGRECORD(record);  //操作日志记录(人物-时间-具体操作，不做清空，追加信息)
        boolean result = allpaySystemDao.update(allpaySystem);
        if(result){
            resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.SUCCESS_CODE);
            resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.SUCCESS_MESSAGE);
        }else{
            resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_001);
            resultJO.put(MsgAndCode.RSP_DESC, "修改系统信息错误！");
        }
        return resultJO.toString();
    }

    public String obtainList(AllpaySystemDto allpaySystemDto, Class clazz, Integer currentPage, Integer pageSize) throws Exception {
        JSONObject resultJO = new JSONObject();

        Conjunction con = Restrictions.conjunction();
        if(!CommonHelper.isEmpty(allpaySystemDto.getSystemState())){ //是否启用	1--启用 2--禁用
            con.add(Restrictions.eq("system_state", Integer.parseInt(allpaySystemDto.getSystemState())));
        }
        con.add(Restrictions.eq("ALLPAY_LOGICDEL", "1"));  //未删除
        JSONArray array = new JSONArray();
        List<AllpaySystem> list = allpaySystemDao.obtainList(clazz, con, currentPage, pageSize);
        if(null != list && list.size() > 0){
            for(int i=0,count=list.size(); i<count; i++){
                JSONObject allpaySystemJO = new JSONObject();
                AllpaySystem allpaySystem = list.get(i);

                allpaySystemJO.put("systemId", allpaySystem.getSystemId());  //系统ID
                allpaySystemJO.put("systemName", allpaySystem.getSystem_name());	//系统名称
                allpaySystemJO.put("systemPath", allpaySystem.getSystem_path());	//系统路径
                allpaySystemJO.put("systemState", allpaySystem.getSystem_state());	//状态	1--启用 2---禁用
                array.put(allpaySystemJO);
            }
        }
        resultJO.put("lists", array);
        resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.SUCCESS_CODE);  //返回的状态码
        resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.SUCCESS_MESSAGE);  //返回的状态码描述
        return resultJO.toString();
    }

    public <T> T getById(Class<T> clazz, String userId) throws Exception {
        return allpaySystemDao.getById(clazz, userId);
    }

    public String getById(AllpaySystemDto allpaySystemDto) throws Exception {
        JSONObject jsonObject = new JSONObject();
        String systemId = allpaySystemDto.getSystemId();
        if(CommonHelper.isEmpty(systemId)){
            jsonObject.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_00500);  //返回的状态码
            jsonObject.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_00500);  //返回的状态码描述
            return jsonObject.toString();
        }
        AllpaySystem allpaySystem = getById(AllpaySystem.class, systemId);
        if(null == allpaySystem){
            jsonObject.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_00504);
            jsonObject.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_00504);
            return jsonObject.toString();
        }

        //返回前端信息
        jsonObject.put("systemId", allpaySystem.getSystemId());	//系统ID
        jsonObject.put("systemName", allpaySystem.getSystem_name());	//系统名称
        jsonObject.put("systemPath", allpaySystem.getSystem_path()); //系统路径
        jsonObject.put("systemState", allpaySystem.getSystem_state());	//状态	1--启用 2---禁用
        jsonObject.put(MsgAndCode.RSP_CODE, MsgAndCode.SUCCESS_CODE);   //apay返回的状态码	000 为正常返回,其他为异常返回
        jsonObject.put(MsgAndCode.RSP_DESC, MsgAndCode.SUCCESS_MESSAGE); //apay返回的状态码描述	异常返回时的异常说明
        return jsonObject.toString();
    }

    public int count(Criterion criterion) throws Exception {
        return allpaySystemDao.count(criterion);
    }
}

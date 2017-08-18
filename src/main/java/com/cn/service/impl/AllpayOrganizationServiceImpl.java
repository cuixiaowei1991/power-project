package com.cn.service.impl;

import com.cn.MsgCode.MsgAndCode;
import com.cn.common.CommonHelper;
import com.cn.dao.AllpayOrganizationDao;
import com.cn.entity.dto.AllpayOrganizationDto;
import com.cn.entity.po.AllpayOrganization;
import com.cn.service.AllpayOrganizationService;
import com.cn.service.impl.kafkaserviceimpl.SendToKafkaServiceImpl;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
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
 * 组织机构管理业务层实现
 * Created by WangWenFang on 2016/11/23.
 */
@Service
@Transactional(rollbackFor = {Exception.class, RuntimeException.class})
public class AllpayOrganizationServiceImpl implements AllpayOrganizationService {

    @Autowired
    private AllpayOrganizationDao allpayOrganizationDao;

    @Autowired
    private SendToKafkaServiceImpl sendToKafkaService;

    public String add(AllpayOrganizationDto organizationDto) throws Exception {
        JSONObject resultJO = new JSONObject();
        if(CommonHelper.isEmpty(organizationDto.getOrganizationName())){  //组织机构名称
            resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_00200);
            resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_00200);
            return resultJO.toString();
        }else{
            boolean isExist=allpayOrganizationDao.isExistOrganName(organizationDto);// true是存在
            if(isExist){
                resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_00607);
                resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.CODE_00607_MSG);
                return resultJO.toString();
            }
        }
        if(CommonHelper.isEmpty(organizationDto.getOrganizationCode())){  //组织机构编码
            resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_00209);
            resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_00209);
            return resultJO.toString();
        }
        if(CommonHelper.isEmpty(organizationDto.getOrganizationType())){  //组织机构类型	1--分公司2---部门
            resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_00201);
            resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_00201);
            return resultJO.toString();
        }
        if(CommonHelper.isEmpty(organizationDto.getOrganizationAddress())){   //组织机构地址
            resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_00202);
            resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_00202);
            return resultJO.toString();
        }
        if(CommonHelper.isEmpty(organizationDto.getOrganizationUserName())){  //负责人
            resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_00203);
            resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_00203);
            return resultJO.toString();
        }
        if(CommonHelper.isEmpty(organizationDto.getOrganizationUserPhone())){  //负责人电话
            resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_00204);
            resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_00204);
            return resultJO.toString();
        }
        if(CommonHelper.isEmpty(organizationDto.getOrganizationState())){  //状态	1--启用 2---禁用
            resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_00205);
            resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_00205);
            return resultJO.toString();
        }
//        if(CommonHelper.isEmpty(getCookieByName("userName"))){
//            resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_002);
//            resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_002);
//            return resultJO.toString();
//        }

        AllpayOrganization organization = new AllpayOrganization();
        organization.setOrganization_name(organizationDto.getOrganizationName());  //组织机构名称
        organization.setOrganization_code(organizationDto.getOrganizationCode());  //组织机构编码
        organization.setOrganization_type(Integer.parseInt(organizationDto.getOrganizationType()));  //组织机构类型
        organization.setOrganization_address(organizationDto.getOrganizationAddress());  //组织机构地址
        organization.setOrganization_username(organizationDto.getOrganizationUserName());  //负责人
        organization.setOrganization_userPhone(organizationDto.getOrganizationUserPhone());  //负责人电话
        organization.setOrganization_state(Integer.parseInt(organizationDto.getOrganizationState()));  //状态

        JSONObject publicFileds = CommonHelper.getPublicFileds(MsgAndCode.OPERATION_NEW, "", organizationDto.getUserNameFromQXCookie());
        String userName = publicFileds.getString("userName");
        Date now = CommonHelper.getStringToDate(publicFileds.getString("now"), "yyyy-MM-dd HH:mm:ss");
        String record = publicFileds.getString("record");

        organization.setALLPAY_CREATER(userName);  //创建人
        organization.setALLPAY_CREATETIME(now);  //创建时间
        organization.setALLPAY_UPDATETIME(now);  //修改时间
        organization.setALLPAY_LOGICDEL("1");  //逻辑删除标记  1---未删除 2---已删除
        organization.setALLPAY_LOGRECORD(record);  //操作日志记录(人物-时间-具体操作，不做清空，追加信息)
        boolean result = allpayOrganizationDao.add(organization);
        if(result){
            sendToKafkaService.sentOrganizationToKafka(organization.getOrganizationId(),"");
            resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.SUCCESS_CODE);
            resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.SUCCESS_MESSAGE);
        }else{
            resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_001);
            resultJO.put(MsgAndCode.RSP_DESC, "新增组织机构信息错误！");
        }
        return resultJO.toString();
    }

    public String delete(AllpayOrganizationDto organizationDto) throws Exception {
        JSONObject resultJO = new JSONObject();
        String organizationId = organizationDto.getOrganizationId();
        if(CommonHelper.isEmpty(organizationId)){
            resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_00206);
            resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_00206);
            return resultJO.toString();
        }

//        if (CommonHelper.isEmpty(getCookieByName("userName"))) {
//            resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_002);
//            resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_002);
//            return resultJO.toString();
//        }

        AllpayOrganization organization = getById(AllpayOrganization.class, organizationId);
        if(null == organization){
            resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_00207);
            resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_00207);
            return resultJO.toString();
        }

        JSONObject publicFileds = CommonHelper.getPublicFileds(MsgAndCode.OPERATION_DELETE, organization.getALLPAY_LOGRECORD(), organizationDto.getUserNameFromQXCookie());
        String userName = publicFileds.getString("userName");
        Date now = CommonHelper.getStringToDate(publicFileds.getString("now"), "yyyy-MM-dd HH:mm:ss");
        String record = publicFileds.getString("record");

        organization.setALLPAY_UPDATER(userName);  //修改人
        organization.setALLPAY_UPDATETIME(now);  //修改时间
        organization.setALLPAY_LOGICDEL("2");  //逻辑删除标记  1---未删除 2---已删除
        organization.setALLPAY_LOGRECORD(record);  //操作日志记录(人物-时间-具体操作，不做清空，追加信息)

        boolean result = allpayOrganizationDao.update(organization);
        if(result){
            resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.SUCCESS_CODE);
            resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.SUCCESS_MESSAGE);
        }else{
            resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_001);
            resultJO.put(MsgAndCode.RSP_DESC, "删除组织机构信息错误！");
        }
        return resultJO.toString();
    }

    public String update(AllpayOrganizationDto organizationDto) throws Exception {
        JSONObject resultJO = new JSONObject();
        String organizationId = organizationDto.getOrganizationId();
        if(CommonHelper.isEmpty(organizationId)){  //组织机构id
            resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_00206);
            resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_00206);
            return resultJO.toString();
        }
//        if (CommonHelper.isEmpty(getCookieByName("userName"))) {
//            resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_002);
//            resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_002);
//            return resultJO.toString();
//        }

        if(CommonHelper.isEmpty(organizationDto.getOrganizationName())){
            resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_00200);
            resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_00200);
            return resultJO.toString();
        }else{
            boolean isExist=allpayOrganizationDao.isExistOrganName(organizationDto);// true是存在
            if(isExist){
                resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_00607);
                resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.CODE_00607_MSG);
                return resultJO.toString();
            }
        }
        if(CommonHelper.isEmpty(organizationDto.getOrganizationCode())){  //组织机构编码
            resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_00209);
            resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_00209);
            return resultJO.toString();
        }
        if(CommonHelper.isEmpty(organizationDto.getOrganizationType())){
            resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_00201);
            resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_00201);
            return resultJO.toString();
        }
        if(CommonHelper.isEmpty(organizationDto.getOrganizationAddress())){
            resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_00202);
            resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_00202);
            return resultJO.toString();
        }
        if(CommonHelper.isEmpty(organizationDto.getOrganizationUserName())){
            resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_00203);
            resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_00203);
            return resultJO.toString();
        }
        if(CommonHelper.isEmpty(organizationDto.getOrganizationUserPhone())){
            resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_00204);
            resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_00204);
            return resultJO.toString();
        }
        if(CommonHelper.isEmpty(organizationDto.getOrganizationState())){
            resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_00205);
            resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_00205);
            return resultJO.toString();
        }

        //修改组织机构信息
        AllpayOrganization organization = getById(AllpayOrganization.class, organizationId);
        if(null == organization){
            resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_00207);
            resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_00207);
            return resultJO.toString();
        }

        organization.setOrganization_name(organizationDto.getOrganizationName());
        organization.setOrganization_code(organizationDto.getOrganizationCode());
        organization.setOrganization_type(Integer.parseInt(organizationDto.getOrganizationType()));
        organization.setOrganization_address(organizationDto.getOrganizationAddress());
        organization.setOrganization_username(organizationDto.getOrganizationUserName());
        organization.setOrganization_userPhone(organizationDto.getOrganizationUserPhone());
        organization.setOrganization_state(Integer.parseInt(organizationDto.getOrganizationState()));

        JSONObject publicFileds = CommonHelper.getPublicFileds(MsgAndCode.OPERATION_UPDATE, organization.getALLPAY_LOGRECORD(), organizationDto.getUserNameFromQXCookie());
        String userName = publicFileds.getString("userName");
        Date now = CommonHelper.getStringToDate(publicFileds.getString("now"), "yyyy-MM-dd HH:mm:ss");
        String record = publicFileds.getString("record");

        organization.setALLPAY_UPDATER(userName);  //修改人
        organization.setALLPAY_UPDATETIME(now);  //修改时间
        organization.setALLPAY_LOGRECORD(record);  //操作日志记录(人物-时间-具体操作，不做清空，追加信息)
        boolean result = allpayOrganizationDao.update(organization);
        if(result){
            sendToKafkaService.sentOrganizationToKafka(organizationId,"");
            resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.SUCCESS_CODE);
            resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.SUCCESS_MESSAGE);
        }else{
            resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_001);
            resultJO.put(MsgAndCode.RSP_DESC, "修改组织机构信息错误！");
        }
        return resultJO.toString();
    }

    public String obtainList(AllpayOrganizationDto organizationDto, Class clazz, Integer currentPage, Integer pageSize) throws Exception {
        JSONObject resultJO = new JSONObject();
        if(null == currentPage || null == pageSize){ //当前页码, 每页显示记录条数
            resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_00110);
            resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_00110);
            return resultJO.toString();
        }

        Conjunction con = Restrictions.conjunction();
        if(!CommonHelper.isEmpty(organizationDto.getOrganizationName())){ //组织机构名称
            con.add(Restrictions.like("organization_name", organizationDto.getOrganizationName(), MatchMode.ANYWHERE));
        }
        if(!CommonHelper.isEmpty(organizationDto.getOrganizationState())){ //是否启用	1--启用 2--禁用
            con.add(Restrictions.eq("organization_state", Integer.parseInt(organizationDto.getOrganizationState())));
        }
        con.add(Restrictions.eq("ALLPAY_LOGICDEL", "1"));  //未删除
        int total = 0;
        JSONArray array = new JSONArray();
        List<AllpayOrganization> list = allpayOrganizationDao.obtainList(clazz, con, currentPage, pageSize);
        total = count(con);
        if(null != list && list.size() > 0){
            for(int i=0,count=list.size(); i<count; i++){
                JSONObject allpayOrganizationJO = new JSONObject();
                AllpayOrganization organization = list.get(i);

                allpayOrganizationJO.put("organizationId", organization.getOrganizationId());  //组织机构ID
                allpayOrganizationJO.put("organizationName", organization.getOrganization_name());	//组织机构名称
                allpayOrganizationJO.put("organizationCode", organization.getOrganization_code());	//组织机构编码
                allpayOrganizationJO.put("organizationType", organization.getOrganization_type());	//组织机构类型	1--分公司2---部门
                allpayOrganizationJO.put("organizationAddress", organization.getOrganization_address());	//组织机构地址
                allpayOrganizationJO.put("organizationUserName", organization.getOrganization_username());	//负责人
                allpayOrganizationJO.put("organizationUserPhone", organization.getOrganization_userPhone());	//用负责人电话
                allpayOrganizationJO.put("organizationState", organization.getOrganization_state());	//状态	1--启用 2---禁用
                array.put(allpayOrganizationJO);
            }
        }
        resultJO.put("lists", array);
        resultJO.put("curragePage", organizationDto.getCurragePage());  //当前页
        resultJO.put("pageSize", organizationDto.getPageSize());  //每页显示记录条数
        resultJO.put("total", total);  //数据的总条数
        resultJO.put(MsgAndCode.RSP_CODE, MsgAndCode.SUCCESS_CODE);  //返回的状态码
        resultJO.put(MsgAndCode.RSP_DESC, MsgAndCode.SUCCESS_MESSAGE);  //返回的状态码描述
        return resultJO.toString();
    }

    public <T> T getById(Class<T> clazz, String userId) throws Exception {
        return allpayOrganizationDao.getById(clazz, userId);
    }

    public String getById(AllpayOrganizationDto organizationDto) throws Exception {
        JSONObject jsonObject = new JSONObject();
        String organizationId = organizationDto.getOrganizationId();
        if(CommonHelper.isEmpty(organizationId)){
            jsonObject.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_00206);  //返回的状态码
            jsonObject.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_00206);  //返回的状态码描述
            return jsonObject.toString();
        }
        AllpayOrganization organization = getById(AllpayOrganization.class, organizationId);
        if(null == organization){
            jsonObject.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_00207);
            jsonObject.put(MsgAndCode.RSP_DESC, MsgAndCode.MESSAGE_00207);
            return jsonObject.toString();
        }

        //返回前端信息
        jsonObject.put("organizationId", organization.getOrganizationId());	//组织机构ID
        jsonObject.put("organizationName", organization.getOrganization_name());	//组织机构名称
        jsonObject.put("organizationCode", organization.getOrganization_code());	//组织机构编码
        jsonObject.put("organizationType", organization.getOrganization_type()); //组织机构类型	1--分公司2---部门
        jsonObject.put("organizationAddress", organization.getOrganization_address());	//组织机构地址
        jsonObject.put("organizationUserName",organization.getOrganization_username());	//负责人
        jsonObject.put("organizationUserPhone", organization.getOrganization_userPhone());	//负责人电话
        jsonObject.put("organizationState", organization.getOrganization_state());	     //状态	1--启用 2---禁用
        jsonObject.put(MsgAndCode.RSP_CODE, MsgAndCode.SUCCESS_CODE);   //apay返回的状态码	000 为正常返回,其他为异常返回
        jsonObject.put(MsgAndCode.RSP_DESC, MsgAndCode.SUCCESS_MESSAGE); //apay返回的状态码描述	异常返回时的异常说明
        return jsonObject.toString();
    }

    public int count(Criterion criterion) throws Exception {
        return allpayOrganizationDao.count(criterion);
    }
}

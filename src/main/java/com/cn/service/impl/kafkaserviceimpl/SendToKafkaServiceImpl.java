package com.cn.service.impl.kafkaserviceimpl;

import all.union.tools.basic.StringUtilsHelper;
import com.cn.common.LogHelper;
import com.cn.dao.AllpayOrganizationDao;
import com.cn.entity.po.AllpayOrganization;
import com.cn.service.SendToKafkaService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.cn.common.CommonHelper.formatTime;


/**
 * Created by cuixiaowei on 2016/11/24.
 */
@Service
public class SendToKafkaServiceImpl implements SendToKafkaService {
	
    @Autowired
    private AllpayOrganizationDao allpayOrganizationDao;

    @Autowired
    private KafkaProducerServiceImpl kafkaProducerService;


    /**
     * 推送分公司信息至kafka
     * @param organizationId
     * @return
     */
    public boolean sentOrganizationToKafka(String organizationId,String marked) throws Exception {
        LogHelper.info("分公司信息推送kafka---->分公司id：" + organizationId + "，删除标记marked：" + marked);
        if(StringUtilsHelper.isEmpty(organizationId))
        {
            LogHelper.info("================未传入分公司id================");
            return false;
        }
        AllpayOrganization organization=null;

        try {
            organization = allpayOrganizationDao.getById(AllpayOrganization.class, organizationId);
        }catch (Exception e)
        {
            //ignore
        }
        if(organization==null)
        {
            LogHelper.info("================未查到相关分公司================");
            return false;
        }
        JSONObject js = new JSONObject();
        //维护kafka分信息
        js.put("branchId",organization.getOrganizationId());/*分公司ID*/
        js.put("baranchName",organization.getOrganization_name()==null ? "":organization.getOrganization_name()); /*分公司名称*/
        js.put("branchType",organization.getOrganization_type());/*1：分公司 0：总部* 2部门*/
        js.put("managerPhoneNumber",organization.getOrganization_userPhone()==null ? "":organization.getOrganization_userPhone());/*分公司负责人手机号*/
        js.put("address",organization.getOrganization_address()==null ?"":organization.getOrganization_address());/*分公司地址*/
        js.put("branchManager",organization.getOrganization_username()==null ?"":organization.getOrganization_username());/*分公司负责人*/
        js.put("provice","");/*分公司省份  已作废，传空值*/
        js.put("city","");/*城市已作废，传空值*/
        js.put("branchCode",organization.getOrganization_code()==null ?"":organization.getOrganization_code());/*分公司编码*/
        js.put("regionIDs","");/*分公司包含的城市/省份ID  已作废，传空值*/
        js.put("function","");/*createNewBranch 新建分公司；editBranch  修改分公司 已作废，传空值*/
        Boolean sendToKafka=kafkaProducerService.send("apay_branch",js.toString());
        LogHelper.info("推送分公司信息至kafka，返回的结果：" + sendToKafka);
        return sendToKafka;
    }


    public boolean sendTestToKafka(String posParterId)
    {
        Boolean result=kafkaProducerService.send("apay_merchant", "测试");
        LogHelper.info("推送测试信息至kafka，返回结果：" + result);
        return result;
    }


}

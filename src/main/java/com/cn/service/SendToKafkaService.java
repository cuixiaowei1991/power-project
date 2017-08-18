package com.cn.service;

/**
 * Created by cuixiaowei on 2016/11/24.
 */
public interface SendToKafkaService {
    /**
     * 推送分公司信息至kafka
     * @param organizationId
     * @return
     */
    public boolean sentOrganizationToKafka(String organizationId, String marked) throws Exception;



    public boolean sendTestToKafka(String posParterId);


}

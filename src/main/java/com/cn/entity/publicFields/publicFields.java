package com.cn.entity.publicFields;

import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.util.Date;

/**
 * Created by cuixiaowei on 2016/11/14.
 */
@MappedSuperclass
public class publicFields {

    /**
     * 创建人
     */
    @Column(name = "ALLPAY_CREATER", length = 1000)
    private String ALLPAY_CREATER;
    /**
     * 创建时间
     */
    @Column(name = "ALLPAY_CREATETIME")
    @Type(type = "java.util.Date")
    private Date ALLPAY_CREATETIME;

    /**
     * 修改人
     */
    @Column(name = "ALLPAY_UPDATER", length = 1000)
    private String ALLPAY_UPDATER;

    /**
     * 修改时间
     */
    @Column(name = "ALLPAY_UPDATETIME")
    @Type(type = "java.util.Date")
    private Date ALLPAY_UPDATETIME;

    /**
     * 逻辑删除标记  1---未删除 2---已删除
     */
    @Column(name = "ALLPAY_LOGICDEL", length = 1)
    private String ALLPAY_LOGICDEL;

    /**
     * 操作日志记录(人物-时间-具体操作，不做清空，追加信息)
     */
    @Column(name = "ALLPAY_LOGRECORD", length = 3000)
    private String ALLPAY_LOGRECORD;




    public String getALLPAY_CREATER() {
        return ALLPAY_CREATER;
    }

    public void setALLPAY_CREATER(String ALLPAY_CREATER) {
        this.ALLPAY_CREATER = ALLPAY_CREATER;
    }

    public Date getALLPAY_CREATETIME() {
        return ALLPAY_CREATETIME;
    }

    public void setALLPAY_CREATETIME(Date ALLPAY_CREATETIME) {
        this.ALLPAY_CREATETIME = ALLPAY_CREATETIME;
    }

    public String getALLPAY_UPDATER() {
        return ALLPAY_UPDATER;
    }

    public void setALLPAY_UPDATER(String ALLPAY_UPDATER) {
        this.ALLPAY_UPDATER = ALLPAY_UPDATER;
    }

    public Date getALLPAY_UPDATETIME() {
        return ALLPAY_UPDATETIME;
    }

    public void setALLPAY_UPDATETIME(Date ALLPAY_UPDATETIME) {
        this.ALLPAY_UPDATETIME = ALLPAY_UPDATETIME;
    }

    public String getALLPAY_LOGICDEL() {
        return ALLPAY_LOGICDEL;
    }

    public void setALLPAY_LOGICDEL(String ALLPAY_LOGICDEL) {
        this.ALLPAY_LOGICDEL = ALLPAY_LOGICDEL;
    }

    public String getALLPAY_LOGRECORD() {
        return ALLPAY_LOGRECORD;
    }

    public void setALLPAY_LOGRECORD(String ALLPAY_LOGRECORD) {
        this.ALLPAY_LOGRECORD = ALLPAY_LOGRECORD;
    }
}

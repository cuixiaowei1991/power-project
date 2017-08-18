package com.cn.entity.po;

import java.util.Date;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

/**
 * 接口安全校验　实体类
 * @author songzhili
 * 2017年1月14日下午5:49:23
 */
@Entity
@Table(name="ALLPAY_SECURITY_CHECK")
@GenericGenerator(name = "system-uuid", strategy = "uuid")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class AllpaySecurityCheck {
   
	
	@Id
    @GeneratedValue(generator = "system-uuid")
    @Column(name = "SECURITY_ID", length = 32)
    private String securityId;
	/**系统名称**/
	@Column(name = "SYSTEM_NAME", length = 500)
	private String systemName;
	/**ip或手机端appId**/
	@Column(name = "DOMAIN_OR_APPID", length = 500)
	private String domainOrAppId;
	/**格式化后的appId或者IP**/
	@Column(name = "DOMAIN_OR_APPID_CHANGE", length = 500)
	private String domainOrAppIdChange;
	/**程序随机生成的字符　返回给相应的调用方**/
	@Column(name = "RANDOM_STRING", length = 50)
	private String randomString;
	/**加密字符**/
	@Column(name = "SECRET_STRING", length = 1000)
	private String secretString;
	/**
	 * 是否启用
	 * 1:是
	 * 0:否
	 * **/
	@Column(name = "STATUS", length = 2)
	private String status;
	/**
	 * 是否放行
	 * 1:是
	 * 0:否
	 * **/
	@Column(name = "ENABLE", length = 2)
	private String enable;
	/****/
	@Column(name = "CREATETIME")
    @Type(type = "java.util.Date")
    private Date createTime;
	
	public String getSecurityId() {
		return securityId;
	}
	public void setSecurityId(String securityId) {
		this.securityId = securityId;
	}
	public String getDomainOrAppId() {
		return domainOrAppId;
	}
	public void setDomainOrAppId(String domainOrAppId) {
		this.domainOrAppId = domainOrAppId;
	}
	public String getRandomString() {
		return randomString;
	}
	public void setRandomString(String randomString) {
		this.randomString = randomString;
	}
	public String getSecretString() {
		return secretString;
	}
	public void setSecretString(String secretString) {
		this.secretString = secretString;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getEnable() {
		return enable;
	}
	public void setEnable(String enable) {
		this.enable = enable;
	}
	public String getSystemName() {
		return systemName;
	}
	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getDomainOrAppIdChange() {
		return domainOrAppIdChange;
	}
	public void setDomainOrAppIdChange(String domainOrAppIdChange) {
		this.domainOrAppIdChange = domainOrAppIdChange;
	}
}

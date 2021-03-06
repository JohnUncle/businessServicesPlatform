package cn.com.businessservicesplatform.model.vo;

import cn.com.businessservicesplatform.model.mysql.BaseUser;

public class BaseUserVo extends BaseUser {
	
	 public BaseUserVo(){}
	 
	 public BaseUserVo(BaseUser baseUser){
		 super.setEmail(baseUser.getEmail());
		 super.setMobilePhone(baseUser.getMobilePhone());
		 super.setId(baseUser.getId());
		 super.setLoginName(baseUser.getLoginName());
		 super.setRaleName(baseUser.getRaleName());
		 super.setType(baseUser.getType());
		 super.setStatus(baseUser.getStatus());
		 super.setSex(baseUser.getSex());
	 }
	
	 private String loginPwd2;
	 
	 private String oldLoginPwd;
	 
	 private String callbackUrl;
	 
	 private String verifyCode;
	 
	 private Integer companyId;
	 

	public String getOldLoginPwd() {
		return oldLoginPwd;
	}

	public void setOldLoginPwd(String oldLoginPwd) {
		this.oldLoginPwd = oldLoginPwd;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public String getCallbackUrl() {
		return callbackUrl;
	}

	public void setCallbackUrl(String callbackUrl) {
		this.callbackUrl = callbackUrl;
	}

	public String getVerifyCode() {
		return verifyCode;
	}

	public void setVerifyCode(String verifyCode) {
		this.verifyCode = verifyCode;
	}

	public String getLoginPwd2() {
		return loginPwd2;
	}

	public void setLoginPwd2(String loginPwd2) {
		this.loginPwd2 = loginPwd2;
	}

}

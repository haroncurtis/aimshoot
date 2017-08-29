package haron.aimshoot.module;

import org.apache.commons.lang.math.NumberUtils;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;

import haron.aimshoot.controller.CommonController;
import haron.aimshoot.vo.AuthVO;
import haron.aimshoot.vo.LangVO;
import haron.aimshoot.vo.UserVO;
import haron.aimshoot.vo.ViewVO;

public class ValidationCheck {
	@Autowired CommonController commonController;
	
	// ログイン
	public void checkLogin(UserVO userVo, AuthVO authVo, LangVO langVo, SecuritySafe securitySafe){
		if(userVo.getUser_id().length() == 0){commonController.setActionStatus(userVo, langVo, Value.ALERT_FAILED_ID_CODE); return;}
			else{userVo.setUser_id(securitySafe.decryptedRSA(userVo.getUser_id(), authVo));}
		if(userVo.getUser_pwd().length() == 0){commonController.setActionStatus(userVo, langVo, Value.ALERT_FAILED_PWD_CODE); return;}
			else{userVo.setUser_pwd(securitySafe.decryptedRSA(userVo.getUser_pwd(), authVo));}
	}
	
	// 会員加入
	public void checkJoin(UserVO userVo, AuthVO authVo, LangVO langVo, SecuritySafe securitySafe){
		if(userVo.getFirst_name().length() == 0 || userVo.getLast_name().length() == 0){commonController.setActionStatus(userVo, langVo, Value.ALERT_FAILED_NAME_CODE); return;}
		if(userVo.getSex() == null || userVo.getSex() == 0){commonController.setActionStatus(userVo, langVo, Value.ALERT_FAILED_SEX_CODE); return;}
		if(!NumberUtils.isNumber(userVo.getBirth_year()) || 
				!NumberUtils.isNumber(userVo.getBirth_month()) ||
				!NumberUtils.isNumber(userVo.getBirth_day())){
			commonController.setActionStatus(userVo, langVo, Value.ALERT_FAILED_BIRTY_CODE); return;
		}
			if(userVo.getUser_id().length() == 0){commonController.setActionStatus(userVo, langVo, Value.ALERT_FAILED_ID_CODE); return;}
			else{userVo.setUser_id(securitySafe.decryptedRSA(userVo.getUser_id(), authVo));}
		if(userVo.getUser_pwd().length() == 0){commonController.setActionStatus(userVo, langVo, Value.ALERT_FAILED_PWD_CODE); return;}
			else{userVo.setUser_pwd(securitySafe.decryptedRSA(userVo.getUser_pwd(), authVo));}
		if(userVo.getUser_repwd().length() == 0){commonController.setActionStatus(userVo, langVo, Value.ALERT_FAILED_REPWD_CODE); return;}
			else{userVo.setUser_repwd(securitySafe.decryptedRSA(userVo.getUser_repwd(), authVo));}
		if(!userVo.getUser_pwd().equals(userVo.getUser_repwd())){commonController.setActionStatus(userVo, langVo, Value.ALERT_FAILED_REPWD_DISCORD_CODE); return;}		
		if(userVo.getEmail() == null){commonController.setActionStatus(userVo, langVo, Value.ALERT_FAILED_EMAIL_CODE); return;}
	}
	
	// ログイン-パスワード比較
	public void checkPwd(UserVO selectUserVo, UserVO userVo, LangVO langVo, ViewVO viewVo){
		if(selectUserVo == null){commonController.setActionStatus(userVo, langVo, Value.ALERT_FAILED_LOGIN_CODE); return;}
		if(selectUserVo.getStatus() == 3){commonController.setActionStatus(userVo, langVo, Value.ALERT_FAILED_USER_PUNISH_CODE); return;}
		if(BCrypt.checkpw(userVo.getUser_pwd(), selectUserVo.getUser_pwd()) == false){ commonController.setActionStatus(userVo, langVo, Value.ALERT_FAILED_LOGIN_CODE); return;}
		
		System.out.println("userVo.getUser_pwd():" + userVo.getUser_pwd() + " ,selectUserVo.getUser_pwd():" + selectUserVo.getUser_pwd() + " ,auth:" + BCrypt.checkpw(userVo.getUser_pwd(), selectUserVo.getUser_pwd()));
		
		if(viewVo.getView_name() == "accountSettingView"){
			if(BCrypt.checkpw(userVo.getUser_repwd(), userVo.getUser_change_pwd()) == false){ commonController.setActionStatus(userVo, langVo, Value.ALERT_FAILED_CHAGNE_PWD_CODE); return;}
		}
				
	}
	
	public void checkPwdEXE(UserVO userVo, AuthVO authVo, LangVO langVo, SecuritySafe securitySafe, UserVO selectUserVo,  ViewVO viewVo){
		if(userVo.getUser_pwd().length() == 0){commonController.setActionStatus(userVo, langVo, Value.ALERT_FAILED_PWD_CODE); return;}
		else{userVo.setUser_pwd(securitySafe.decryptedRSA(userVo.getUser_pwd(), authVo));}
		if(userVo.getUser_change_pwd().length() == 0){commonController.setActionStatus(userVo, langVo, Value.ALERT_FAILED_CHAGNE_PWD_CODE); return;}
		else{userVo.setUser_change_pwd(securitySafe.decryptedRSA(userVo.getUser_change_pwd(), authVo));}
		if(userVo.getUser_repwd().length() == 0){commonController.setActionStatus(userVo, langVo, Value.ALERT_FAILED_REPWD_CODE); return;}
		else{userVo.setUser_repwd(securitySafe.decryptedRSA(userVo.getUser_repwd(), authVo));}
	
		userVo.setUser_change_pwd(securitySafe.encryptBCrypt(userVo.getUser_change_pwd()));
		checkPwd(selectUserVo, userVo, langVo, viewVo);
	}

	// トークンチェック
	public void accessTokenCheck(AuthVO authVo, UserVO userVo, LangVO langVo, UserVO selectUserVo) {
		
		if(selectUserVo == null ||
				!authVo.getProject_name().equals(Value.PROJECT_NAME) ||
				!authVo.getLast_login_ip().equals(selectUserVo.getLast_login_ip())){
			commonController.setActionStatus(userVo, langVo, Value.ALERT_FAILED_ACCESS_TOKEN_CODE); return;
		}
		
		if((System.currentTimeMillis() - authVo.getToken_time()) > 30000000){ commonController.setActionStatus(userVo, langVo, Value.ALERT_FAILED_TOKEN_TIME_CODE); return;	}
		
	}
	
}

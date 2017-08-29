package haron.aimshoot.module;

import org.springframework.data.redis.core.ValueOperations;

import haron.aimshoot.vo.LangVO;
import haron.aimshoot.vo.UserVO;
import haron.aimshoot.vo.ViewVO;

public class ViewList {

	// User
	public static String JOINVIEW ="joinView";
	public static String LOGINVIEW ="loginView";
	public static String MAINVIEW ="mainView";
	public static String ACCOUNTSETTINGVIEW ="accountSettingView";
	public static String JOINVIEW_PATH ="user/join.jsp";
	public static String LOGINVIEW_PATH ="user/login.jsp";
	public static String MAINVIEW_PATH ="main/main.jsp";
	public static String ACCOUNTSETTINGVIEW_PATH ="user/account_setting.jsp";
	
	// Menu
	public static String PROFILE = "profile";
	public static String PROFILE_SETTING = "profile_setting";
	public static String CHANGE_PWD = "change_pwd";
	public static String CATEGORY_SETTING = "category_setting";
	
	
	public static void getView(ViewVO viewVo){
		switch(viewVo.getView_name()){
			case "joinView":  viewVo.setView_path(JOINVIEW_PATH); break;
			case "loginView":  viewVo.setView_path(LOGINVIEW_PATH); break;
			case "mainView":  viewVo.setView_path(MAINVIEW_PATH); break;
			case "accountSettingView":  viewVo.setView_path(ACCOUNTSETTINGVIEW_PATH); break;
		}
		
	}
	
	public static void accountSettingView(ViewVO viewVo, LangVO langVo, ValueOperations<String,String> msgOps){
		viewVo.setView_name(ViewList.ACCOUNTSETTINGVIEW);
		viewVo.setMenu(ViewList.PROFILE);
		viewVo.setMenu_view("<li class=\"active\"><a onclick=\"execAction('accountSettingView');\"><i class=\"glyphicon glyphicon-pencil\"></i>" + msgOps.get("profile_setting_" + langVo.getLang_type()) + "</a></li><li><a onclick=\"execAction('changePwd');\"><i class=\"glyphicon glyphicon-download\"></i>" + msgOps.get("change_pwd_" + langVo.getLang_type()) + "</a></li>"
    			+ "<li><a onclick=\"execAction('categorySettingView');\"><i class=\"glyphicon glyphicon-download\"></i>" + msgOps.get("category_setting_" + langVo.getLang_type()) + "</a></li>");
		viewVo.setMenu_form("<%@ include file=\"accountsetting/change_pwd.jsp\" %>");
	}
	
	public static void changePwd(ViewVO viewVo, LangVO langVo, ValueOperations<String,String> msgOps){
		viewVo.setMenu(ViewList.CHANGE_PWD);
		viewVo.setMenu_view("<li><a onclick=\"execAction('accountSettingView');\"><i class=\"glyphicon glyphicon-pencil\"></i>" + msgOps.get("profile_setting_" + langVo.getLang_type()) + "</a></li>" +
						"<li class=\"active\"><a onclick=\"execAction('changePwd');\"><i class=\"glyphicon glyphicon-download\"></i>" + msgOps.get("change_pwd_" + langVo.getLang_type()) + "</a></li>" +
        				"<li><a onclick=\"execAction('categorySettingView');\"><i class=\"glyphicon glyphicon-download\"></i>" + msgOps.get("category_setting_" + langVo.getLang_type()) + "</a></li>");
		viewVo.setMenu_form("<%@ include file=\"accountsetting/change_pwd.jsp\" %>");
	}
	
	public static void categorySettingView(ViewVO viewVo, LangVO langVo, ValueOperations<String,String> msgOps){
		viewVo.setMenu(ViewList.CATEGORY_SETTING);
		
		viewVo.setMenu_view("<li><a onclick=\"execAction('accountSettingView');\"><i class=\"glyphicon glyphicon-pencil\"></i>" + msgOps.get("profile_setting_" + langVo.getLang_type()) + "</a></li>" +
				"<li><a onclick=\"execAction('changePwd');\"><i class=\"glyphicon glyphicon-download\"></i>" + msgOps.get("change_pwd_" + langVo.getLang_type()) + "</a></li>" +
				"<li class=\"active\"><a onclick=\"execAction('categorySettingView');\"><i class=\"glyphicon glyphicon-download\"></i>" + msgOps.get("category_setting_" + langVo.getLang_type()) + "</a></li>");
		viewVo.setMenu_form("<%@ include file=\"accountsetting/change_pwd.jsp\" %>");
	}
	
	public static void join(ViewVO viewVo){
		
		
		
	}
	
	
}

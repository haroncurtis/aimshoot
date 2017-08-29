package haron.aimshoot.controller;

import haron.aimshoot.dao.ContentDAO;
import haron.aimshoot.dao.UserDAO;
import haron.aimshoot.module.SecuritySafe;
import haron.aimshoot.module.ValidationCheck;
import haron.aimshoot.module.Value;
import haron.aimshoot.module.ViewList;
import haron.aimshoot.vo.AuthVO;
import haron.aimshoot.vo.CategoryVO;
import haron.aimshoot.vo.LangVO;
import haron.aimshoot.vo.UserVO;
import haron.aimshoot.vo.ViewVO;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ValueOperations;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

@SuppressWarnings("serial")
public class CommonController  extends ActionSupport implements Preparable {

	@Resource(name="msgRedis") private ValueOperations<String,String> msgOps;
	@Resource(name="langRedis") private ValueOperations<String, ArrayList<LangVO>> langOps;
	@Autowired private ArrayList<LangVO> langAR;
	
	private HttpServletRequest request;
	private HttpServletResponse response;
	private HttpSession session;
	
	// Controller
	@Autowired private RedisController redisController;;
		
	// VO
	@Autowired private UserVO userVo;
	@Autowired private AuthVO authVo;
	@Autowired private ViewVO viewVo;
	private UserVO selectUserVo;
	
	@Autowired private LangVO langVo;
	
	// DAO
	@Autowired private ContentDAO contentDao;
	@Autowired private UserDAO userDao;
	
	// Module
	@Autowired private SecuritySafe securitySafe;
	@Autowired private ValidationCheck validationCheck;
	
	// Else
	private JSONObject json;
	
	public String changeLang(){
		System.out.println("IN-changeLang");
		System.out.println("userVo333:" + userVo.getAction_code());
		
		for(LangVO langViewVo:langAR){
			langViewVo.setLang_type_view(langVo.getLang_type());
		}
		initData(userVo, viewVo,authVo, request, session, msgOps, contentDao, langOps, langAR, langVo);
		ViewList.getView(viewVo);
		return Value.SUCCESS;
	}
	
	public String flushAllRedis(){
		redisController.flushAllRedis();
		initData(userVo, viewVo,authVo, request, session, msgOps, contentDao, langOps, langAR, langVo);
		return Value.SUCCESS;
	}
	
	
	public void setActionStatus(UserVO userVo, LangVO langVo, int action_code){

		userVo.setAction_code(action_code);
		
		switch(action_code){
			case Value.ACTOIN_SUCCESS_CODE: userVo.setMsg_key(Value.ACTOIN_SUCCESS); break;
			case Value.ALERT_FAILED_ID_CODE: userVo.setMsg_key(Value.ALERT_FAILED_ID); break;
			case Value.ALERT_FAILED_PWD_CODE: userVo.setMsg_key(Value.ALERT_FAILED_PWD); break;
			case Value.ALERT_FAILED_LOGIN_CODE: userVo.setMsg_key(Value.ALERT_FAILED_LOGIN); break;
			case Value.ALERT_FAILED_NAME_CODE: userVo.setMsg_key(Value.ALERT_FAILED_NAME); break;
			case Value.ALERT_FAILED_SEX_CODE: userVo.setMsg_key(Value.ALERT_FAILED_SEX); break;
			case Value.ALERT_FAILED_BIRTY_CODE: userVo.setMsg_key(Value.ALERT_FAILED_BIRTY); break;
			case Value.ALERT_FAILED_EMAIL_CODE: userVo.setMsg_key(Value.ALERT_FAILED_EMAIL); break;
			case Value.ALERT_FAILED_REPWD_CODE: userVo.setMsg_key(Value.ALERT_FAILED_REPWD); break;
			case Value.ALERT_FAILED_REPWD_DISCORD_CODE: userVo.setMsg_key(Value.ALERT_FAILED_REPWD_DISCORD); break;
			case Value.ALERT_FAILED_DUPLICATION_ID_CODE: userVo.setMsg_key(Value.ALERT_FAILED_DUPLICATION_ID); break;
			case Value.ALERT_FAILED_USER_PUNISH_CODE: userVo.setMsg_key(Value.ALERT_FAILED_USER_PUNISH); break;
			case Value.ALERT_FAILED_ACCESS_TOKEN_CODE: userVo.setMsg_key(Value.ALERT_FAILED_ACCESS_TOKEN); break;
			case Value.ALERT_FAILED_TOKEN_TIME_CODE: userVo.setMsg_key(Value.ALERT_FAILED_TOKEN_TIME); break;
			case Value.ALERT_FAILED_CHAGNE_PWD_CODE: userVo.setMsg_key(Value.ALERT_FAILED_CHAGNE_PWD); break;
		}
		userVo.setMsg_value(msgOps.get(userVo.getMsg_key() + "_" + langVo.getLang_type()));
	}
	
	public boolean checkActionCode(UserVO userVo){
		if(userVo.getAction_code() == 1){return true;}
		return false;
	}
	
	
	// 初期化
	public void initData(UserVO userVo, ViewVO viewVo, AuthVO authVo, HttpServletRequest request, HttpSession session,
			ValueOperations<String, String> msgOps, ContentDAO contentDao,
			ValueOperations<String, ArrayList<LangVO>> langOps,
			ArrayList<LangVO> langAR, LangVO langVo){
		
		if(checkActionCode(userVo) & !viewVo.getView_name().equals(ViewList.LOGINVIEW)){
			securitySafe.generateToken(userVo, authVo);
		}
		
		ViewList.getView(viewVo);
		redisController.getAll(request, session, msgOps, contentDao, langOps, langAR, langVo, authVo);
	}
	
	public void process(HttpServletRequest request, HttpServletResponse response, UserVO userVo, ViewVO viewVo, LangVO langVo, AuthVO authVo){
		setActionStatus(userVo, langVo, Value.ACTOIN_SUCCESS_CODE);
		authVo = (AuthVO)request.getAttribute("authVo");

		if(authVo.getAccess_token() != null){
			 if(authVo.getAccess_token().length > 0){
				 accessTokenCheck(request, authVo, userVo, viewVo, langVo, userDao);
			 }
			 
			 // アクセストークン満了でログイン画面に遷移
			 if(userVo.getAction_code() == Value.ALERT_FAILED_TOKEN_TIME_CODE || userVo.getAction_code() == Value.ALERT_FAILED_ACCESS_TOKEN_CODE ){
				 authVo.setAccess_token(null);
				 try {
					response.sendRedirect("/AimShoot/checkSession.action");
				} catch (IOException e) {
					e.printStackTrace();
				}
			 }
		}
	}
	
	// Access Tokenチェック
	public void accessTokenCheck(HttpServletRequest request, AuthVO authVo, UserVO userVo, ViewVO viewVo,LangVO langVo, UserDAO userDao){
	
		
		json = JSONObject.fromObject(securitySafe.decryptAccessToken(authVo));
		authVo.setProject_name(json.getString("project_name"));
		authVo.setUser_no(Integer.parseInt(json.getString("user_no")));
		authVo.setLast_login_ip(json.getString("last_login_ip"));
		authVo.setToken_time(Long.parseLong(json.getString("token_time")));
	
		selectUserVo = new UserVO();
		selectUserVo.setUser_no(authVo.getUser_no());
		selectUserVo = userDao.selectUser(selectUserVo);
	
		validationCheck.accessTokenCheck(authVo, userVo, langVo, selectUserVo);
		if(userVo.getAction_code() != 1){
			viewVo.setView_name("loginView");
			authVo.setAccess_token(null);
			request.setAttribute("authVo", authVo);
		}
	}
	
	
	// 日時取得
	public String getDate(){
		return new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.US).format(new Date());
	}
	
	// ユーザーIP取得
	public String getUserIP(HttpServletRequest request) {
		
		/**
		InetAddress addr = null;
		try {
			addr = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		System.out.println("IpAddress:" + addr.getHostAddress());
		*/
		
		String ip = request.getHeader("X-FORWARDED-FOR");
		if (ip == null) {ip = request.getHeader("Proxy-Client-IP");}
		if (ip == null) {ip = request.getHeader("WL-Proxy-Client-IP");}
		if (ip == null) {ip = request.getHeader("HTTP_CLIENT_IP");}
		if (ip == null) {ip = request.getHeader("HTTP_X_FORWARDED_FOR");}
		if (ip == null) {ip = request.getRemoteAddr();}
		return ip;
	}
	
	@Override
	public void prepare() throws Exception {
		request = ServletActionContext.getRequest();
		response = ServletActionContext.getResponse();
		session = request.getSession(false);
		process(request, response, userVo, viewVo,langVo, authVo);
	}

	public LangVO getLangVo() {
		return langVo;
	}

	public void setLangVo(LangVO langVo) {
		this.langVo = langVo;
	}

	public UserVO getUserVo() {
		return userVo;
	}

	public void setUserVo(UserVO userVo) {
		this.userVo = userVo;
	}

	public ValueOperations<String, String> getMsgOps() {
		return msgOps;
	}

	public void setMsgOps(ValueOperations<String, String> msgOps) {
		this.msgOps = msgOps;
	}

	public ValueOperations<String, ArrayList<LangVO>> getLangOps() {
		return langOps;
	}

	public void setLangOps(ValueOperations<String, ArrayList<LangVO>> langOps) {
		this.langOps = langOps;
	}

	public ArrayList<LangVO> getLangAR() {
		return langAR;
	}

	public void setLangAR(ArrayList<LangVO> langAR) {
		this.langAR = langAR;
	}

	public RedisController getRedisController() {
		return redisController;
	}

	public void setRedisController(RedisController redisController) {
		this.redisController = redisController;
	}

	public AuthVO getAuthVo() {
		return authVo;
	}

	public void setAuthVo(AuthVO authVo) {
		this.authVo = authVo;
	}

	public UserDAO getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDAO userDao) {
		this.userDao = userDao;
	}
	
	
}

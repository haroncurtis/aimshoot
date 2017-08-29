package haron.aimshoot.controller;

import haron.aimshoot.dao.ContentDAO;
import haron.aimshoot.dao.UserDAO;
import haron.aimshoot.module.SecuritySafe;
import haron.aimshoot.module.ValidationCheck;
import haron.aimshoot.module.Value;
import haron.aimshoot.module.ViewList;
import haron.aimshoot.vo.AuthVO;
import haron.aimshoot.vo.CategoryVO;
import haron.aimshoot.vo.InfoVO;
import haron.aimshoot.vo.LangVO;
import haron.aimshoot.vo.MsgVO;
import haron.aimshoot.vo.UserVO;
import haron.aimshoot.vo.ViewVO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.arnx.jsonic.parse.JSONParser;

import org.apache.struts2.ServletActionContext;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ValueOperations;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
import com.opensymphony.xwork2.util.finder.ClassFinder.Info;
	
@SuppressWarnings("serial")
public class UserController extends ActionSupport implements Preparable{
	private HttpServletRequest request;
	private HttpServletResponse response;
	private HttpSession session;
	private ValueOperations<String,String> masterData;
	@Autowired private ArrayList<LangVO> langAR;
	@Autowired private ArrayList<MsgVO> msgAR;
	@Resource(name="msgRedis") private ValueOperations<String,String> msgOps;
	@Resource(name="langRedis") private ValueOperations<String, ArrayList<LangVO>> langOps;

	@Autowired private ArrayList<UserVO> yearAR;
	@Autowired private ArrayList<UserVO> monthAR;
	@Autowired private ArrayList<UserVO> dayAR;
	
	// Controller
	@Autowired private RedisController redisController;
	@Autowired private CommonController commonController;
	private MasterDataController masterDataController;
	
	// VO
	@Autowired private UserVO userVo;
	private UserVO selectUserVo;
	@Autowired private AuthVO authVo;
	@Autowired private LangVO langVo;
	@Autowired private ViewVO viewVo;
	@Autowired private CategoryVO categoryVo;
	
	// DAO
	@Autowired private ContentDAO contentDao;
	@Autowired private UserDAO userDao;
	
	// Module
	@Autowired private SecuritySafe securitySafe;
	@Autowired private ValidationCheck validationCheck;
	
	// Common
	private int nowYear;
	private Calendar calendar;
	private JSONObject categoryList;
	private ArrayList<CategoryVO> categoryAR;
	private ArrayList<HashMap<String, Object>> category1;
	private ArrayList<HashMap<String, Object>> category2;
	private ArrayList<HashMap<String, Object>> category3;
	
	/**
	 * 認証チェック
	 * 2017/05/19
	 */
	public String checkSession(){
		System.out.println("IN checkSession");
		 
		if(authVo == null || authVo.getAccess_token() == null){
			// 未承認 - ログイン画面に遷移
			viewVo.setView_name(ViewList.LOGINVIEW);
		}else{
			// 承認済　- メイン画面に遷移
			viewVo.setView_name(ViewList.MAINVIEW);
		}
		commonController.initData(userVo, viewVo, authVo, request, session, msgOps, contentDao, langOps, langAR, langVo);
		
		return Value.SUCCESS;
	}
	
	/**
	 * ログイン
	 * 2017/05/19
	 */
	public String login(){

		validationCheck.checkLogin(userVo, authVo, langVo, securitySafe);
		
		if(authVo == null || authVo.getAccess_token() == null){
			
			// 入力欄確認
			if(userVo.getAction_code() == 1){
				selectUserVo = userDao.login(userVo);
			
				//　DBからユーザーとパスワード一致確認
				validationCheck.checkPwd(selectUserVo, userVo, langVo, viewVo);
				
				if(commonController.checkActionCode(userVo)){
					userVo.setUser_no(selectUserVo.getUser_no());
					userVo.setUser_id(selectUserVo.getUser_id());
					userVo.setLast_name(selectUserVo.getLast_name());
					userVo.setFirst_name(selectUserVo.getFirst_name());
					userVo.setLast_login_ip(commonController.getUserIP(request));
					userVo.setLast_login_date(commonController.getDate());
					userDao.updateLogin(userVo);
					viewVo.setView_name("mainView");
				}
			}
		}else{
			if(commonController.checkActionCode(userVo)){
				viewVo.setView_name(ViewList.MAINVIEW);
			}
		}
		commonController.initData(userVo, viewVo, authVo, request, session, msgOps, contentDao, langOps, langAR, langVo);
		return Value.SUCCESS;
	}
	
	/**
	 * ログアウト
	 * 2017/05/19
	 */
	public String logout(){
		
		viewVo.setView_name(ViewList.LOGINVIEW);
		commonController.initData(userVo, viewVo, authVo, request, session, msgOps, contentDao, langOps, langAR, langVo);
		
		authVo.setAccess_token(null);
		request.setAttribute("authVo", authVo); 
		
		return Value.SUCCESS;
	}

	/**
	 * 会員加入+
	 * 2017/05/19
	 */
	public String join(){
		
		validationCheck.checkJoin(userVo, authVo, langVo, securitySafe);
		
		for(int i = 0; i < yearAR.size(); i++){
			UserVO birthVo = yearAR.get(i);
			birthVo.setBirth_year_view(userVo.getBirth_year());
		}
		for(int i = 0; i < monthAR.size(); i++){
			UserVO birthVo = monthAR.get(i);
			birthVo.setBirth_month_view(userVo.getBirth_month());
		}
		for(int i = 0; i < dayAR.size(); i++){
			UserVO birthVo = dayAR.get(i);
			birthVo.setBirth_day_view(userVo.getBirth_day());
		}
		
		if(userVo.getAction_code() == 1){
			selectUserVo = userDao.login(userVo);
			if(selectUserVo == null){
				if(userVo.getBirth_month().length() < 2){userVo.setBirth_month("0" + userVo.getBirth_month());}
				if(userVo.getBirth_day().length() < 2){userVo.setBirth_day("0" + userVo.getBirth_day());}		
				userVo.setBirth(userVo.getBirth_year() + "-" + userVo.getBirth_month() + "-" + userVo.getBirth_day());
				userVo.setAuth(0);
				userVo.setCreate_date(commonController.getDate());
				userVo.setUser_pwd(securitySafe.encryptBCrypt(userVo.getUser_pwd()));
				userDao.insertUser(userVo);
			}else{
				// ID重複
				commonController.setActionStatus(userVo, langVo, Value.ALERT_FAILED_DUPLICATION_ID_CODE);
			}
		}
		
		commonController.initData(userVo, viewVo, authVo, request, session, msgOps, contentDao, langOps, langAR, langVo);
		
		return Value.SUCCESS;
	}
	
	
	public String setting(){
		
		System.out.println("IN-setting");
		
		
		
		return Value.SUCCESS;
	}
	
/**
 * --------------------------------------------------
 * 
 * 画面遷移 
 * 
 * 	--------------------------------------------------
 */
	
	/**
	 * 会員加入に画面遷移
	 * 2017/05/19
	 */
	public String joinView(){
		
		viewVo.setView_name(ViewList.JOINVIEW);
		// 日付		
		calendar = Calendar.getInstance();
		nowYear = calendar.getInstance().get(Calendar.YEAR);
		
		UserVO userBirthBlankVo = new UserVO();
		userBirthBlankVo.setBirth_year(" ");
		userBirthBlankVo.setBirth_month(" ");
		userBirthBlankVo.setBirth_day(" ");
		userBirthBlankVo.setBirth_year_view(" ");
		userBirthBlankVo.setBirth_month_view(" ");
		userBirthBlankVo.setBirth_day_view(" ");
		
		if(yearAR.size() == 0){
			yearAR.add(userBirthBlankVo);
			for(int i = nowYear; i >= (nowYear - 120); i--){ 
				UserVO userBirthVo = new UserVO(); 
				userBirthVo.setBirth_year(Integer.toString(i));
				userBirthVo.setBirth_year_view(" ");
				yearAR.add(userBirthVo);
			}
		}

		if(monthAR.size() == 0){
			monthAR.add(userBirthBlankVo);
			for(int i = 1; i <= 12; i++){ 
				UserVO userBirthVo = new UserVO(); 
				userBirthVo.setBirth_month(Integer.toString(i));
				userBirthVo.setBirth_day_view(" ");
				monthAR.add(userBirthVo);
			}
		}

		if(dayAR.size() == 0){
			dayAR.add(userBirthBlankVo);
			for(int i = 1; i <= 31; i++){ 
				UserVO userBirthVo = new UserVO(); 
				userBirthVo.setBirth_day(Integer.toString(i));
				userBirthVo.setBirth_day_view(" ");
				dayAR.add(userBirthVo);
			}
		}
		
		commonController.initData(userVo, viewVo, authVo, request, session, msgOps, contentDao, langOps, langAR, langVo);
		authVo.setAccess_token(null);
		request.setAttribute("authVo", authVo);
		
		return Value.SUCCESS;
	}
	
	
	/**
	 * アカウント設定の画面遷移
	 * 2017/05/22
	 */
	public String accountSettingView(){
		System.out.println("IN-accountSettingView");
		
		ViewList.accountSettingView(viewVo, langVo, msgOps);
		userVo = userDao.selectUser(userVo);
		userVo.setAction_code(Value.ACTOIN_SUCCESS_CODE);
		
		commonController.initData(userVo, viewVo, authVo, request, session, msgOps, contentDao, langOps, langAR, langVo);
		return Value.SUCCESS;
	}
	
	public String changePwd(){
		System.out.println("IN-changePwd");
		ViewList.changePwd(viewVo, langVo, msgOps);
		commonController.initData(userVo, viewVo, authVo, request, session, msgOps, contentDao, langOps, langAR, langVo);
		return Value.SUCCESS;
	}
	
	
	/**
	 * 2017/07/27 - 金成国
	 * パスワード変更
	 */
	public String changePwdEXE(){
		System.out.println("IN-changePwdEXE");		
		selectUserVo = userDao.selectUser(userVo);
		validationCheck.checkPwdEXE(userVo, authVo, langVo, securitySafe, selectUserVo, viewVo);
		
		if(userVo.getAction_code() == Value.ACTOIN_SUCCESS_CODE){
			userDao.updatePWD(userVo);
		}
		
		commonController.initData(userVo, viewVo, authVo, request, session, msgOps, contentDao, langOps, langAR, langVo);
		return Value.SUCCESS;
	}
	
	/**
	 * 2017/08/08 - 金成国
	 * プロファイル編集
	 */
	
	public String profileEditConfirm(){
		userDao.updateProfile(userVo);
		
		commonController.initData(userVo, viewVo, authVo, request, session, msgOps, contentDao, langOps, langAR, langVo);		
		return Value.SUCCESS;
	}
	
	public String categorySettingView(){
		System.out.println("IN-categorySettingView");
		
		categoryVo = userDao.selectCategory(userVo);
		if(categoryVo == null){
			categoryVo = new CategoryVO();
			categoryVo.setCategory_cnt(1);
			categoryVo.setUser_no(userVo.getUser_no());
			categoryVo.setCategory_list("[{\"category_name\":\"カテゴリ\",\"category_no\":\"100001\",\"items\":[]}]");
			categoryVo.setCreate_date(commonController.getDate());
			categoryVo.setModified_date(commonController.getDate());
			System.out.println("cnt" + categoryVo.getCategory_cnt() + ", user_no:" + categoryVo.getUser_no() + ", list:" + categoryVo.getCategory_list());
	
			userDao.insertCategory(categoryVo);
		}
		
		
	/**	
		categoryList = new JSONObject();
		HashMap<String, Object> objectData;
		ArrayList<HashMap<String, Object>> objectDataList;
				
		categoryAR = userDao.selectCategory(userVo);
	
		// objectDataList = new ArrayList<HashMap<String, Object>>();
		
		
		category1 =  new ArrayList<HashMap<String, Object>>();
		category2 =  new ArrayList<HashMap<String, Object>>();
		category3 =  new ArrayList<HashMap<String, Object>>();
		
		try {
			for(CategoryVO categoryVo : categoryAR){
				
				objectData = new HashMap<String, Object>();
				objectData.put("category_no",categoryVo.getCategory_no());
				objectData.put("category_parent",categoryVo.getCategory_parent());
				objectData.put("category_name",categoryVo.getCategory_name());
				objectData.put("selectbox_no",categoryVo.getSelectbox_no());
				objectData.put("category_parent",categoryVo.getCategory_parent());
				
				System.out.println("aa:" + categoryVo.getCategory_name() + ", objectData:" + objectData);
				
				if(categoryVo.getSelectbox_no() == 0){
					category1.add(objectData);
				}else if(categoryVo.getSelectbox_no() == 1){
					category2.add(objectData);
				}else{
					category3.add(objectData);
				}
			}
			
			categoryList.accumulate("category1", category1);
			categoryList.accumulate("category2", category2);
			categoryList.accumulate("category3", category3);
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		categoryVo.setCategoryList(categoryList.toString());
		
		System.out.println("-----------json--------------:" + categoryList);
		*/
		
		ViewList.categorySettingView(viewVo, langVo, msgOps);
		commonController.initData(userVo, viewVo, authVo, request, session, msgOps, contentDao, langOps, langAR, langVo);
		
		return Value.SUCCESS;
	}
	
	public String selectCategoryParent(){
		System.out.println("IN-selectCategory");
		
		viewVo.setView_name(ViewList.ACCOUNTSETTINGVIEW);
		commonController.initData(userVo, viewVo, authVo, request, session, msgOps, contentDao, langOps, langAR, langVo);
		
		return Value.SUCCESS;
	}
	
	public String updateCategory(){
		System.out.println("IN-updateCategory");
		categoryVo.setUser_no(userVo.getUser_no());
		categoryVo.setModified_date(commonController.getDate());
		System.out.println("categoryList:" + categoryVo.getCategory_list() + ", user_no:" + categoryVo.getUser_no());
		
		// userDao.deleteCategory(userVo);
		
		userDao.updateCategory(categoryVo);
		categoryVo = userDao.selectCategory(userVo);
		
		commonController.initData(userVo, viewVo, authVo, request, session, msgOps, contentDao, langOps, langAR, langVo);		
		return Value.SUCCESS;
	}
	
	public String selectCategoryChild1(){
		System.out.println("IN-selectCategoryChild1");
		
		return Value.SUCCESS;
	}

	public String selectCategoryChild2(){
		System.out.println("IN-selectCategoryChild2");
		
		return Value.SUCCESS;
	}
	
	@Override
	public void prepare() throws Exception {
		request = ServletActionContext.getRequest();
		response = ServletActionContext.getResponse();
		session = request.getSession(false);
		commonController.process(request, response, userVo, viewVo, langVo, authVo);
		
	/**	
		request = ServletActionContext.getRequest();
		response = ServletActionContext.getResponse();

		session = request.getSession(false);
		commonController.setActionStatus(userVo, langVo, Value.ACTOIN_SUCCESS_CODE);
		authVo = (AuthVO)request.getAttribute("authVo");

		if(authVo.getAccess_token() != null){
			 if(authVo.getAccess_token().length > 0){
				 commonController.accessTokenCheck(request, authVo, userVo, langVo, userDao);
			 }
			 
			 // アクセストークン満了でログイン画面に遷移
			 if(userVo.getAction_code() == Value.ALERT_FAILED_TOKEN_TIME_CODE || userVo.getAction_code() == Value.ALERT_FAILED_ACCESS_TOKEN_CODE ){
				 authVo.setAccess_token(null);
				 response.sendRedirect("/AimShoot/checkSession.action");
			 }
		}
		*/
	}
	
	public UserVO getUserVo() {
		return userVo;
	}

	public void setUserVo(UserVO userVo) {
		this.userVo = userVo;
	}

	public ValueOperations<String, String> getMasterData() {
		return masterData;
	}

	public void setMasterData(ValueOperations<String, String> masterData) {
		this.masterData = masterData;
	}

	public ArrayList<LangVO> getLangAR() {
		return langAR;
	}

	public void setLangAR(ArrayList<LangVO> langAR) {
		this.langAR = langAR;
	}

	public ArrayList<MsgVO> getMsgAR() {
		return msgAR;
	}

	public void setMsgAR(ArrayList<MsgVO> msgAR) {
		this.msgAR = msgAR;
	}

	public void setMasterDataController(MasterDataController masterDataController) {
		this.masterDataController = masterDataController;
	}

	public ValueOperations<String, String> getMsgOps() {
		return msgOps;
	}

	public void setMsgOps(ValueOperations<String, String> msgOps) {
		this.msgOps = msgOps;
	}

	public LangVO getLangVo() {
		return langVo;
	}

	public void setLangVo(LangVO langVo) {
		this.langVo = langVo;
	}


	public AuthVO getAuthVo() {
		return authVo;
	}

	public void setAuthVo(AuthVO authVo) {
		this.authVo = authVo;
	}

	public ValueOperations<String, ArrayList<LangVO>> getLangOps() {
		return langOps;
	}

	public void setLangOps(ValueOperations<String, ArrayList<LangVO>> langOps) {
		this.langOps = langOps;
	}

	public ArrayList<UserVO> getYearAR() {
		return yearAR;
	}

	public void setYearAR(ArrayList<UserVO> yearAR) {
		this.yearAR = yearAR;
	}

	public ArrayList<UserVO> getMonthAR() {
		return monthAR;
	}

	public void setMonthAR(ArrayList<UserVO> monthAR) {
		this.monthAR = monthAR;
	}

	public ArrayList<UserVO> getDayAR() {
		return dayAR;
	}

	public void setDayAR(ArrayList<UserVO> dayAR) {
		this.dayAR = dayAR;
	}

	public ViewVO getViewVo() {
		return viewVo;
	}

	public void setViewVo(ViewVO viewVo) {
		this.viewVo = viewVo;
	}

	public CategoryVO getCategoryVo() {
		return categoryVo;
	}

	public void setCategoryVo(CategoryVO categoryVo) {
		this.categoryVo = categoryVo;
	}

	public JSONObject getCategoryList() {
		return categoryList;
	}

	public void setCategoryList(JSONObject categoryList) {
		this.categoryList = categoryList;
	}
	
	
}

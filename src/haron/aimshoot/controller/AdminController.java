package haron.aimshoot.controller;

import java.util.ArrayList;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ValueOperations;

import haron.aimshoot.dao.ContentDAO;
import haron.aimshoot.module.SecuritySafe;
import haron.aimshoot.module.Value;
import haron.aimshoot.vo.AuthVO;
import haron.aimshoot.vo.LangVO;
import haron.aimshoot.vo.UserVO;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

@SuppressWarnings("serial")
public class AdminController extends ActionSupport implements Preparable{
	@Resource(name="msgRedis") private ValueOperations<String,String> msgOps;
	@Resource(name="langRedis") private ValueOperations<String, ArrayList<LangVO>> langOps;
	@Autowired private ArrayList<LangVO> langAR;
	
	private HttpServletRequest request;
	private HttpSession session;
	
	// Controller
	@Autowired private RedisController redisController;;
	@Autowired private CommonController commonController;
		
	// VO
	private UserVO userVo;
	@Autowired private LangVO langVo;
	private AuthVO authVo;	
	
	// DAO
	@Autowired private ContentDAO contentDao;
	
	public String flushAllRedis(){
		System.out.println("IN-flushAllRedis");
		redisController.flushAllRedis();
		initData();
		return Value.SUCCESS;
	}
	
	public void initData(){
		redisController.getAll(request, session, msgOps, contentDao, langOps, langAR, langVo, authVo);
	}
	
	@Override
	public void prepare() throws Exception {
		request = ServletActionContext.getRequest();
		session = request.getSession(false);
		commonController.setActionStatus(userVo, langVo, Value.ACTOIN_SUCCESS_CODE);
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

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public HttpSession getSession() {
		return session;
	}

	public void setSession(HttpSession session) {
		this.session = session;
	}

	public RedisController getRedisController() {
		return redisController;
	}

	public void setRedisController(RedisController redisController) {
		this.redisController = redisController;
	}

	public UserVO getUserVo() {
		return userVo;
	}

	public void setUserVo(UserVO userVo) {
		this.userVo = userVo;
	}

	public LangVO getLangVo() {
		return langVo;
	}

	public void setLangVo(LangVO langVo) {
		this.langVo = langVo;
	}

	public ContentDAO getContentDao() {
		return contentDao;
	}

	public void setContentDao(ContentDAO contentDao) {
		this.contentDao = contentDao;
	}

	public AuthVO getAuthVo() {
		return authVo;
	}

	public void setAuthVo(AuthVO authVo) {
		this.authVo = authVo;
	}
}

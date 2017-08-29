package haron.aimshoot.controller;

import haron.aimshoot.dao.ContentDAO;
import haron.aimshoot.module.Value;
import haron.aimshoot.vo.AuthVO;
import haron.aimshoot.vo.CategoryVO;
import haron.aimshoot.vo.LangVO;
import haron.aimshoot.vo.MsgVO;
import haron.aimshoot.vo.UserVO;

import java.util.ArrayList;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

@SuppressWarnings({"serial" })
public class MainController extends ActionSupport implements Preparable {
	private HttpServletRequest request;
	private HttpSession session;
	private ValueOperations<String,String> masterData;
	@Autowired private ArrayList<LangVO> langAR;
	@Autowired private ArrayList<MsgVO> msgAR;
	@Resource(name="msgRedis") private ValueOperations<String,String> msgOps;
	@Resource(name="langRedis") private ValueOperations<String, ArrayList<LangVO>> langOps;
	
	
	// Controller
	@Autowired private RedisController redisController;
	@Autowired private CommonController commonController;
	private MasterDataController masterDataController;
		
	// VO
	private LangVO langVo;
	private UserVO userVo;
		
	// DAO
	@Autowired private ContentDAO contentDao;
		
	public String scheduleView(){
		System.out.println("IN-scheduleView");
		
		return Value.SUCCESS;
	}
	
	
	@Override
	public void prepare() throws Exception {
		request = ServletActionContext.getRequest();
		session = request.getSession(false);
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


	public RedisController getRedisController() {
		return redisController;
	}


	public void setRedisController(RedisController redisController) {
		this.redisController = redisController;
	}


	public CommonController getCommonController() {
		return commonController;
	}


	public void setCommonController(CommonController commonController) {
		this.commonController = commonController;
	}


	public MasterDataController getMasterDataController() {
		return masterDataController;
	}


	public void setMasterDataController(MasterDataController masterDataController) {
		this.masterDataController = masterDataController;
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


	public ContentDAO getContentDao() {
		return contentDao;
	}


	public void setContentDao(ContentDAO contentDao) {
		this.contentDao = contentDao;
	}
}

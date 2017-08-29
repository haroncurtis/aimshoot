package haron.aimshoot.controller;

import haron.aimshoot.dao.ContentDAO;
import haron.aimshoot.module.SecuritySafe;
import haron.aimshoot.module.Value;
import haron.aimshoot.module.ViewList;
import haron.aimshoot.vo.AuthVO;
import haron.aimshoot.vo.CategoryVO;
import haron.aimshoot.vo.LangVO;
import haron.aimshoot.vo.UserVO;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

@SuppressWarnings("serial")
public class RedisController{
	private JSONArray jsonArray;
	@Autowired private RedisTemplate<String, String> msgRedis;
	@Autowired private RedisTemplate<String, ArrayList<LangVO>> langRedis;
	@Autowired private UserVO userVo;
	@Autowired private SecuritySafe securitySafe;
	
	public void flushAllRedis(){		
		
		try {
			msgRedis.execute(new RedisCallback() {
                @Override
                public Object doInRedis(RedisConnection connection) throws DataAccessException {
                    connection.flushAll();
                    return null;
                }
            });
        } catch (Exception e) {
            System.out.println("모든 캐시를 삭제하는데 실패했습니다.");
        }
		
		try {
			langRedis.execute(new RedisCallback() {
                @Override
                public Object doInRedis(RedisConnection connection) throws DataAccessException {
                    connection.flushAll();
                    return null;
                }
            });
        } catch (Exception e) {
            System.out.println("모든 캐시를 삭제하는데 실패했습니다.");
        }
	}
	
	
	public void getLangAR(ContentDAO contentDao, ValueOperations<String, ArrayList<LangVO>> langOps, ArrayList<LangVO> langAR, String REDIS_LANG){
		
		if(langOps.size(REDIS_LANG) == 0){
			contentDao.selectLang(langOps, REDIS_LANG);
		}
		
		try {
			 jsonArray = new JSONArray(langOps.get(REDIS_LANG).toString());
		} catch (JSONException e1) {
			e1.printStackTrace();
		}
		
		for(int i=0; i < jsonArray.length(); i++){
			JSONObject jsonOb = new JSONObject();
			LangVO langVo = new LangVO();
			try {
				jsonOb = (JSONObject) jsonArray.get(i);
				langVo.setLang_type(jsonOb.getString("lang_type"));
				langVo.setLang_display(jsonOb.getString("lang_display"));
				langVo.setLang_type_view("en");
				langAR.add(langVo);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 *　基本設定
	 */
	public void getAll(HttpServletRequest request, HttpSession session,
			ValueOperations<String, String> msgOps, ContentDAO contentDao,
			ValueOperations<String, ArrayList<LangVO>> langOps,
			ArrayList<LangVO> langAR, LangVO langVo, AuthVO authVo) {
		
		if(langVo.getLang_type() == null){
			langVo.setLang_type("en");
			session.setAttribute("langVo", langVo);
		}

		// RSAキー発行
		if(authVo == null || 
				authVo.getPrivateKey() == null ||
				authVo.getPublicKey() == null ||
				authVo.getPublicKeyExponent() == null ||
				authVo.getPublicKeyModulus() == null)
		{
			securitySafe.createRSA(request, authVo);
		}
		
		//　言語種類
		if(langAR.size() == 0){
			getLangAR(contentDao, langOps, langAR , Value.REDIS_LANG);
		}
		
		// メッセージ取得
		if(msgOps.get(Value.REDIS_MSGSIZE) == null){
			contentDao.selectMsg(msgOps, Value.REDIS_MSGSIZE);
		}
		
	}
	
}

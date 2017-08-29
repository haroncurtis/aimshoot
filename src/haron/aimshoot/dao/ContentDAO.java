package haron.aimshoot.dao;

import haron.aimshoot.dao.mapper.ContentMapper;
import haron.aimshoot.vo.LangVO;
import haron.aimshoot.vo.MsgVO;

import java.util.ArrayList;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ValueOperations;

public class ContentDAO{
	 private SqlSession sqlSession;
	 private ArrayList<MsgVO> msgAR;
	 private ArrayList<LangVO> langAR;
	 @Autowired private ContentMapper contentMapper;


	public void selectLang(ValueOperations<String, ArrayList<LangVO>> langOps,
			String REDIS_LANG) {
		
		langAR = contentMapper.selectLang();
		langOps.set(REDIS_LANG, langAR);
	}
		
	public void selectMsg(ValueOperations<String, String> msgOps, String REDIS_MSGSIZE) {
		System.out.println("IN-selectMsg contentMapper:" + contentMapper);
		msgAR = contentMapper.selectMsg();
		for(MsgVO msgVo : msgAR){
			msgOps.set(msgVo.getMsg_key() + "_" + "en" , msgVo.getMsg_en());
			msgOps.set(msgVo.getMsg_key() + "_" + "kr" , msgVo.getMsg_kr());
			msgOps.set(msgVo.getMsg_key() + "_" + "jp" , msgVo.getMsg_jp());
			msgOps.set(msgVo.getMsg_key() + "_" + "ch" , msgVo.getMsg_ch());
		    msgOps.set("lang", "en");
		}
		 msgOps.set(REDIS_MSGSIZE, String.valueOf(msgAR.size()));
	}

	public SqlSession getSqlSession() {
		return sqlSession;
	}


	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}

}

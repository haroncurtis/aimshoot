package haron.aimshoot.dao;

import haron.aimshoot.dao.impl.MasterDataImpl;
import haron.aimshoot.vo.LangVO;

import java.util.ArrayList;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;

public class MasterDataDAO{
	@Autowired
	private MasterDataImpl masterDataImpl;
	private SqlSession sqlSession;

	public void selectLang(ArrayList<LangVO> langAR) {
		System.out.println("langAR" + langAR);
		System.out.println("sqlSession:" + sqlSession);
		System.out.println("masterDataImpl:" + masterDataImpl);
		langAR = masterDataImpl.selectLang();
	}
	/**
	@Override
	public void selectMsg() {
		return msgAR = masterDataImpl.selectMsg();
	}
	*/

	
	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}
	
}

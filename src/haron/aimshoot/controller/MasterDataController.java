package haron.aimshoot.controller;

import haron.aimshoot.dao.MasterDataDAO;
import haron.aimshoot.vo.LangVO;
import haron.aimshoot.vo.MsgVO;
import java.util.ArrayList;
import javax.servlet.http.HttpSession;

public class MasterDataController {
	public void init(HttpSession session, ArrayList<LangVO> langAR, ArrayList<MsgVO> msgAR, MasterDataDAO masterDataDao){
		
		System.out.println("masterDao:" + masterDataDao);
		masterDataDao.selectLang(langAR);
		
	}
}

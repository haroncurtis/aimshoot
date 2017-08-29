package haron.aimshoot.dao.impl;

import haron.aimshoot.vo.LangVO;
import haron.aimshoot.vo.MsgVO;
import java.util.ArrayList;

public interface MasterDataImpl {
	public ArrayList<MsgVO> selectMsg();
	public ArrayList<LangVO> selectLang();
}

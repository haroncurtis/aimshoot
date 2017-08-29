package haron.aimshoot.dao.mapper;


import haron.aimshoot.vo.LangVO;
import haron.aimshoot.vo.MsgVO;

import java.util.ArrayList;

public interface ContentMapper {
	public ArrayList<LangVO> selectLang();
	public ArrayList<MsgVO> selectMsg();
}

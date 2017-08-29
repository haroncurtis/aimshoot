package haron.aimshoot.dao.mapper;

import java.util.ArrayList;

import haron.aimshoot.vo.CategoryVO;
import haron.aimshoot.vo.UserVO;

public interface UserMapper {
	// SELECT
	public UserVO selectUser(UserVO userVo);
	public UserVO login(UserVO userVo);
	public CategoryVO selectCategory(UserVO userVo);
	
	// INSERT
	public void insertUser(UserVO userVo);
	public void insertCateogry(CategoryVO categoryVo);
	
	// UPDATE
	public void updatePWD(UserVO userVo);
	public void updateLogin(UserVO userVo);
	public void updateProfile(UserVO userVo);
	
	// DELETE
	public void deleteCategory(UserVO userVo);
	public void updateCategory(CategoryVO categoryVo);
	
	

}

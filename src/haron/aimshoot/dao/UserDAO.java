package haron.aimshoot.dao;

import java.util.ArrayList;

import haron.aimshoot.dao.mapper.UserMapper;
import haron.aimshoot.vo.CategoryVO;
import haron.aimshoot.vo.UserVO;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;

public class UserDAO {
	private SqlSession sqlSession;
	@Autowired private UserMapper userMapper;
	
	// SELECT
	public UserVO login(UserVO userVo){return userMapper.login(userVo);}
	public CategoryVO selectCategory(UserVO userVo){return userMapper.selectCategory(userVo);}
		
	// INSERT
	public void insertUser(UserVO userVo){userMapper.insertUser(userVo);}
	public void insertCategory(CategoryVO categoryVo) {userMapper.insertCateogry(categoryVo);}
	
	// トークンチェック
	public UserVO selectUser(UserVO userVo) {return userMapper.selectUser(userVo);}
	
	// UPDATE
	public void updatePWD(UserVO userVo){userMapper.updatePWD(userVo);}
	public void updateLogin(UserVO userVo){userMapper.updateLogin(userVo);}
	public void updateProfile(UserVO userVo){userMapper.updateProfile(userVo);}
	public void updateCategory(CategoryVO categoryVo) {userMapper.updateCategory(categoryVo);}
	
	// DELETE
	public void deleteCategory(UserVO userVo){userMapper.deleteCategory(userVo);}
	
	
	public SqlSession getSqlSession() {
		return sqlSession;
	}

	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}

}

package com.choose.Message.mapper.dao;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.choose.Message.bean.User;
import com.choose.Message.mapper.UserMapper;

/**
 * 用户信息数据操作实现层
 * 
 * @version 1.0
 * @author 周化益
 * @since 2016-02-15
 */
@Repository
public class UserDao extends CommonDao<User> implements UserMapper {
	private static Class<User> entityClass = User.class;
	
	@Override
	public long registerUser(Map<String, Object> userMap) {
		return super.add1(userMap, entityClass);
	}

	@Override
	public Map<String, Object> searchUser(long userId) {
		String sql = " SELECT * FROM user WHERE user_id = " + userId;
		return super.findBySql(sql);
	}

}

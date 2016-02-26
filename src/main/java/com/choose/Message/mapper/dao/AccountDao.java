package com.choose.Message.mapper.dao;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.choose.Message.bean.Account;
import com.choose.Message.mapper.AccountMapper;

/**
 * 用户账号信息数据操作实现层
 * 
 * @version 1.0
 * @author 周化益
 * @since 2016-02-15
 */

@Repository
public class AccountDao extends CommonDao<Account> implements AccountMapper {
	private static Class<Account> entityClass = Account.class;
	
	@Override
	public long addAccount(Map<String, Object> accountMap) {
		return super.add1(accountMap,entityClass);
	}
	
	@Override
	public Map<String, Object> loginAccount(String account, String password) {
		String sql = " SELECT * FROM account WHERE account_name = #account AND account_password = #password";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("account", account);
		params.put("password", password);
		return super.findBySql(super.sqlAppend(sql, params));
	}
	
	@Override
	public boolean updatePwd(long userId, String oldPwd, String newPwd) {
		String sql = " UPDATE Account SET account_password = #newPwd WHERE account_userId = " + userId +
				" AND account_password = #oldPwd";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("newPwd", newPwd);
		params.put("oldPwd", oldPwd);
		return super.updateClass(super.sqlAppend(sql, params));
	}
	
	@Override
	public boolean resetPwdById(long userId, String newPwd) {
		String sql = " UPDATE Account SET account_password = #newPwd WHERE account_userId = " + userId;
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("newPwd", newPwd);
		return super.updateClass(super.sqlAppend(sql, params));
	}
	
	@Override
	public boolean validateAccount(String account) {
		String sql = " SELECT COUNT(*) FROM Account WHERE account_name = #account";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("account", account);
		return super.getCount(super.sqlAppend(sql, params)) > 0;
	}
	
	@Override
	public long findAccount(String account) {
		String sql = " SELECT account_userId FROM Account WHERE account_name = #account";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("account", account);
		return super.getCount(super.sqlAppend(sql, params));
	}
}

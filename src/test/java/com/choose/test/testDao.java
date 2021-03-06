package com.choose.test;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.choose.Message.mapper.AccountMapper;
import com.choose.Message.mapper.dao.CommonDao;
import com.choose.Message.service.AccountService;
import com.choose.Message.util.CommonUtil;

/**
 * 测试数据库的交互操作
 * 
 * @version 1.0
 * @author 周化益
 * @since 2016-02-15
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:com/choose/Message/resource/spring-mybatis.xml")
public class testDao {
	@Resource
	private CommonDao<?> commonDao;
	
	@Resource
	private AccountMapper accountDao;
	
	@Resource
	private AccountService accountService;

	@Test
	public void test() {
		//addAccount();
		loginAccount();
	}
	
	private void addAccount() {
		Map<String, Object> accountMap = new HashMap<String, Object>();
		accountMap.put("account_name", "admin");
		accountMap.put("account_password", CommonUtil.string2MD5("123"));
		accountMap.put("account_type", 1);
		accountMap.put("account_source", 1);
		System.out.println(accountDao.addAccount(accountMap));
	}
	
	private void loginAccount() {
		System.out.println(accountDao.loginAccount("admin", CommonUtil.string2MD5("123")));
	}
}

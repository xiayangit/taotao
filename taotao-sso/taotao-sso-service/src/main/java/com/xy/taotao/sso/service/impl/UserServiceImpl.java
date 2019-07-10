package com.xy.taotao.sso.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import com.xy.taotao.common.pojo.TaoTaoResult;
import com.xy.taotao.common.utils.JsonUtils;
import com.xy.taotao.mapper.TbUserMapper;
import com.xy.taotao.pojo.TbUser;
import com.xy.taotao.pojo.TbUserExample;
import com.xy.taotao.pojo.TbUserExample.Criteria;
import com.xy.taotao.redis.JedisClient;
import com.xy.taotao.sso.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private TbUserMapper userMapper;

	@Autowired
	private JedisClient jedisClient;

	@Override
	public TaoTaoResult login(String name, String password) {
		TbUserExample e = new TbUserExample();
		Criteria createCriteria = e.createCriteria();
		createCriteria.andUsernameEqualTo(name);
		List<TbUser> selectByExample = userMapper.selectByExample(e);
		if (selectByExample == null || selectByExample.size() == 0) {
			return TaoTaoResult.build(400, "用户名或密码错误");
		}
		TbUser u = selectByExample.get(0);
		System.out.println(DigestUtils.md5Digest(password.getBytes()).toString());
		System.out.println(u.getPassword());
		if (!DigestUtils.md5DigestAsHex(password.getBytes()).toString().equals(u.getPassword())) {
			return TaoTaoResult.build(400, "用户名或密码错误");
		}
		String token = UUID.randomUUID().toString();
		u.setPassword(null);
		jedisClient.set("USER:" + token, JsonUtils.objectToJson(u));
		jedisClient.expire("USER:" + token, 1800);
		return TaoTaoResult.ok(token);
	}

	@Override
	public TaoTaoResult getUserByToken(String token) {
		String string = jedisClient.get("USER:" + token);
		if(string == null || "".equals(string)|| "null".equals(string)) {
			return TaoTaoResult.build(400, "用户未登录");
		}
		jedisClient.expire("USER:" + token, 1800);
		TbUser jsonToPojo = JsonUtils.jsonToPojo(string, TbUser.class);
		return TaoTaoResult.ok(jsonToPojo);
	}

}

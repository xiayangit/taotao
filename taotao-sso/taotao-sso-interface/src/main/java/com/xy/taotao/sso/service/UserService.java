package com.xy.taotao.sso.service;

import com.xy.taotao.common.pojo.TaoTaoResult;

public interface UserService {

	public TaoTaoResult login(String name,String password);

	public TaoTaoResult getUserByToken(String token);
}

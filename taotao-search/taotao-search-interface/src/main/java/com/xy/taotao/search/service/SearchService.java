package com.xy.taotao.search.service;

import com.xy.taotao.common.pojo.SearchResult;

public interface SearchService {

	public SearchResult serch(String keyword,Integer page,Integer rows);
	
}

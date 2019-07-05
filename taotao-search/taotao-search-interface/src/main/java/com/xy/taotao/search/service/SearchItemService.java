package com.xy.taotao.search.service;

import com.xy.taotao.common.pojo.SearchResult;
import com.xy.taotao.common.pojo.TaoTaoResult;

public interface SearchItemService {

	public TaoTaoResult getAllItem();
		
	public SearchResult searchAll(String query);
}

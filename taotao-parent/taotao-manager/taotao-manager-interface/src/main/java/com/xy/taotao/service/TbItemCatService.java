package com.xy.taotao.service;

import java.util.List;

import com.xy.taotao.common.pojo.EasyTreeNode;

public interface TbItemCatService {

	public List<EasyTreeNode> getList(long parentId);
	
}

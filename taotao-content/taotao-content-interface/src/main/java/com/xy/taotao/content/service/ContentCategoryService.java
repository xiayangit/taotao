package com.xy.taotao.content.service;

import java.util.List;

import com.xy.taotao.common.pojo.EasyTreeNode;
import com.xy.taotao.common.pojo.TaoTaoResult;
import com.xy.taotao.pojo.TbContentCategory;

public interface ContentCategoryService {

	public List<EasyTreeNode> getList(long parentId);

	public TaoTaoResult add(Long id, String name);
}

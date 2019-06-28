package com.xy.taotao.content.service.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xy.taotao.common.pojo.EasyTreeNode;
import com.xy.taotao.content.service.ContentCategoryService;
import com.xy.taotao.common.pojo.TaoTaoResult;
import com.xy.taotao.mapper.TbContentCategoryMapper;
import com.xy.taotao.pojo.TbContentCategory;
import com.xy.taotao.pojo.TbContentCategoryExample;
import com.xy.taotao.pojo.TbContentCategoryExample.Criteria;

@Service
public class ContentCategoryServiceImpl implements ContentCategoryService {

	@Autowired
	private TbContentCategoryMapper cateMapper;

	@Override
	public List<EasyTreeNode> getList(long parentId) {
		TbContentCategoryExample e = new TbContentCategoryExample();
		Criteria createCriteria = e.createCriteria();
		createCriteria.andParentIdEqualTo(parentId);
		List<TbContentCategory> list = cateMapper.selectByExample(e);
		List<EasyTreeNode> collect = list.stream().map(bean -> {
			EasyTreeNode t = new EasyTreeNode();
			t.setId(bean.getId());
			t.setText(bean.getName());
			t.setState(bean.getIsParent() ? "closed" : "open");
			return t;
		}).collect(Collectors.toList());
		return collect;
	}

	@Override
	public TaoTaoResult add(Long id, String name) {
		TbContentCategory c = new TbContentCategory();
		c.setParentId(id);
		c.setName(name);
		c.setStatus(1);
		c.setSortOrder(1);
		c.setUpdated(new Date());
		c.setCreated(new Date());
		c.setIsParent(false);
		cateMapper.insert(c);
		TbContentCategory s = cateMapper.selectByPrimaryKey(id);
		if(!s.getIsParent()) {
			s.setIsParent(!s.getIsParent());
		}
		cateMapper.updateByPrimaryKey(s);
		return TaoTaoResult.ok(c);
	}

}

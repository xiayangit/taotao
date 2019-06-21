package com.xy.taotao.service.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xy.taotao.common.pojo.EasyTreeNode;
import com.xy.taotao.mapper.TbItemCatMapper;
import com.xy.taotao.pojo.TbItemCat;
import com.xy.taotao.pojo.TbItemCatExample;
import com.xy.taotao.pojo.TbItemCatExample.Criteria;
import com.xy.taotao.service.TbItemCatService;

@Service
public class TbItemCatServiceImpl implements TbItemCatService,Serializable {

	@Autowired
	private TbItemCatMapper itemCatMapper;
	
	@Override
	public List<EasyTreeNode> getList(long parentId) {
		TbItemCatExample e = new TbItemCatExample();
		Criteria createCriteria = e.createCriteria();
		createCriteria.andParentIdEqualTo(parentId);
		List<TbItemCat> selectByExample = itemCatMapper.selectByExample(e);
		List<EasyTreeNode> list = selectByExample.stream().map(bean -> {
			EasyTreeNode node = new EasyTreeNode();
			node.setId(bean.getId());
			node.setText(bean.getName());
			node.setState(bean.getIsParent()?"closed":"open");
			return node;
		}).collect(Collectors.toList());
		/*List<EasyTreeNode> list = new ArrayList<>();
		for(TbItemCat item:selectByExample) {
			EasyTreeNode s = new EasyTreeNode();
			s.setId(item.getId());
			s.setText(item.getName());
			s.setState(item.getIsParent()?"closed":"open");
			list.add(s);
		}*/
		return list;
	}

}

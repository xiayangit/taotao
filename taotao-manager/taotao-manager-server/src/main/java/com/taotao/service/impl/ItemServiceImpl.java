package com.taotao.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.mapper.TbItemMapper;
import com.taotao.pojo.EasyUIDataGridResult;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemExample;
import com.taotao.service.ItemService;

@Service
public class ItemServiceImpl implements ItemService {

	@Autowired
	private TbItemMapper itemMapper;
	
	@Override
	public TbItem getItem(long id) {
		return itemMapper.selectByPrimaryKey(id);
	}

	@Override
	public EasyUIDataGridResult getItemList(int page, int rows) {
		PageHelper.startPage(page, rows);
		TbItemExample itemExample = new TbItemExample();
		List<TbItem> list = itemMapper.selectByExample(itemExample);
		PageInfo<TbItem> pageInfo = new PageInfo<>(list);
		EasyUIDataGridResult e = new EasyUIDataGridResult();
		e.setTotal(pageInfo.getTotal());
		e.setRows(list);
		return e;
	}

}

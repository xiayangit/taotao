package com.xy.taotao.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xy.taotao.common.pojo.EasyDataGridResult;
import com.xy.taotao.common.pojo.TaoTaoResult;
import com.xy.taotao.common.utils.IDUtils;
import com.xy.taotao.mapper.TbItemDescMapper;
import com.xy.taotao.mapper.TbItemMapper;
import com.xy.taotao.pojo.TbItem;
import com.xy.taotao.pojo.TbItemDesc;
import com.xy.taotao.pojo.TbItemExample;
import com.xy.taotao.service.TbItemService;

@Service
public class TbItemServiceImpl implements TbItemService{

	@Autowired
	private TbItemMapper itemMapper;
	
	@Autowired 
	private TbItemDescMapper itemDescMapper;
	
	@Override
	public TbItem getItem(long pkey) {
		TbItem selectByPrimaryKey = itemMapper.selectByPrimaryKey(pkey);
		return selectByPrimaryKey;
	}

	@Override
	public EasyDataGridResult list(int page, int rows) {
		PageHelper.startPage(page, rows);
		TbItemExample e = new TbItemExample();
		List<TbItem> selectByExample = itemMapper.selectByExample(e);
		PageInfo<TbItem> info = new PageInfo<>(selectByExample);
		EasyDataGridResult ea = new EasyDataGridResult();
		ea.setTotal(info.getTotal());
		ea.setRows(selectByExample);
		return ea;
	}

	@Override
	public TaoTaoResult add(TbItem item, String desc) {
		long genItemId = IDUtils.genItemId();
		item.setId(genItemId);
		item.setStatus((byte) 1);
		item.setCreated(new Date());
		item.setUpdated(new Date());
		itemMapper.insert(item);
		TbItemDesc d= new TbItemDesc();
		d.setCreated(new Date());
		d.setItemDesc(desc);
		d.setItemId(genItemId);
		d.setUpdated(new Date());
		itemDescMapper.insert(d);
		return TaoTaoResult.ok();
	}

}

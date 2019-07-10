package com.xy.taotao.service;

import com.xy.taotao.common.pojo.EasyDataGridResult;
import com.xy.taotao.common.pojo.TaoTaoResult;
import com.xy.taotao.pojo.TbItem;
import com.xy.taotao.pojo.TbItemDesc;

public interface TbItemService {

	public TbItem getItem(long pkey);
	public EasyDataGridResult list(int page,int rows);
	TaoTaoResult add(TbItem item,String desc);
	TbItemDesc getDesc(long key);
}

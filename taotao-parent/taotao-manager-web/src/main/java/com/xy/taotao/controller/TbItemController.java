package com.xy.taotao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xy.taotao.common.pojo.EasyDataGridResult;
import com.xy.taotao.pojo.TbItem;
import com.xy.taotao.service.TbItemService;

@Controller
public class TbItemController {

	@Autowired
	private TbItemService tbItemServiceImpl;
	
	@RequestMapping("/item/{itemId}")
	@ResponseBody
	public TbItem getPdt(@PathVariable long itemId) {
		return tbItemServiceImpl.getItem(itemId);
	}
	
	@RequestMapping("/item/list")
	@ResponseBody
	public EasyDataGridResult list(int page,int rows) {
		return tbItemServiceImpl.list(page, rows);
	}
	
	@RequestMapping(value = "/item/save",method=RequestMethod.POST)
	@ResponseBody
	public Object add(TbItem item,String desc) {
		return tbItemServiceImpl.add(item, desc);
	}
}

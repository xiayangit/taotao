package com.xy.taotao.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xy.taotao.common.pojo.EasyTreeNode;
import com.xy.taotao.service.TbItemCatService;

@Controller
public class TbItemCatController {

	@Autowired
	private TbItemCatService itemCatServiceImpl;
	
	@RequestMapping("/item/cat/list")
	@ResponseBody
	public List<EasyTreeNode> getList(@RequestParam(name="id",defaultValue="0")long parentId){
		List<EasyTreeNode> list = itemCatServiceImpl.getList(parentId);
		return list;
	}
	
}

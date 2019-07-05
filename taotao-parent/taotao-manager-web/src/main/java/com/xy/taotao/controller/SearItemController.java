package com.xy.taotao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xy.taotao.search.service.SearchItemService;

@Controller
public class SearItemController {

	@Autowired
	private SearchItemService searchItemServiceImpl;
	
	@RequestMapping("/index/impl")
	@ResponseBody
	public Object implIndex() {
		return searchItemServiceImpl.getAllItem();
	}
	
}

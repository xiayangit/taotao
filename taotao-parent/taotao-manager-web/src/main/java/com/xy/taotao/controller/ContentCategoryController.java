package com.xy.taotao.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xy.taotao.common.pojo.EasyTreeNode;
import com.xy.taotao.common.pojo.TaoTaoResult;
import com.xy.taotao.content.service.ContentCategoryService;

@Controller
public class ContentCategoryController {

	@Autowired
	private ContentCategoryService contentCategoryService;
	
	@RequestMapping("/content/category/list")
	@ResponseBody
	public List<EasyTreeNode> getList(@RequestParam(name="id",defaultValue="0")Long parentId){
		return contentCategoryService.getList(parentId);
	}
	
	@RequestMapping("/content/category/create")
	@ResponseBody
	public TaoTaoResult add(@RequestParam(name="parentId")long id,String name) {
		return contentCategoryService.add(id, name);
	}
}

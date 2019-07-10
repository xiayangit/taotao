package com.xy.taotao.item.controller;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import com.xy.taotao.pojo.TbItemDesc;
import com.xy.taotao.service.TbItemService;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

@Controller
public class ItemInfoController {
	
	@Autowired
	private TbItemService itemService;
	
	@Autowired
	private FreeMarkerConfigurer freeMarkerConfigurer;

	@RequestMapping("/item/{id}")
	public String info(@PathVariable("id")Long itemId,Model model) {
		item i = new item(itemService.getItem(itemId));
		TbItemDesc desc = itemService.getDesc(itemId);
		model.addAttribute("item", i);
		model.addAttribute("itemDesc", desc);
		return "item";
	}
	
	@RequestMapping("/genhtml")
	@ResponseBody
	public String test(HttpServletRequest request,HttpServletResponse response) {
		try {
			//根据配置获取模板所在位置 与设置模板编码
			Configuration configuration = freeMarkerConfigurer.createConfiguration();
			//设置模板的名称
			Template template = configuration.getTemplate("hello.ftl");
			//创建原数据
			Map data = new HashMap<>();
			data.put("hello", "测试模板创建成功");
			//创建输出文件的位置
			Writer out = new FileWriter(new File("D:/test/hello.html"));
			//将数据填充至模板并创建输出的文件
			template.process(data, out);
			out.close();
		} catch (IOException | TemplateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "OK";
	}
}

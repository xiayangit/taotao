package com.xy.taotao.search.controller;

import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xy.taotao.common.pojo.SearchResult;
import com.xy.taotao.search.service.SearchService;

@Controller
public class SearchController {

	@Autowired
	private SearchService searchServiceImpl;

	@Value("${SEARCH_ROWS}")
	private Integer SEARCH_ROWS;

	@RequestMapping("/search")
	public String search(@RequestParam(name = "q") String query, @RequestParam(defaultValue = "1") Integer page,Model model) {
		try {
			query = new String (query.getBytes("iso8859-1"),"utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		SearchResult serch = searchServiceImpl.serch(query, page, SEARCH_ROWS);
		model.addAttribute("query",query);
		model.addAttribute("totalPages",serch.getTotalPages());
		model.addAttribute("itemList",serch.getItemList());
		model.addAttribute("page",page);
		return "search";
	}

}

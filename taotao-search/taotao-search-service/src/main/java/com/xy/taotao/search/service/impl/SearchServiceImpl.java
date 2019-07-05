package com.xy.taotao.search.service.impl;

import org.apache.solr.client.solrj.SolrQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xy.taotao.common.pojo.SearchResult;
import com.xy.taotao.search.dao.SearchDao;
import com.xy.taotao.search.service.SearchService;

@Service
public class SearchServiceImpl implements SearchService {

	@Autowired
	private SearchDao searchDao;

	@Override
	public SearchResult serch(String keyword, Integer page, Integer rows) {
		SolrQuery q = new SolrQuery();
		q.setQuery(keyword);
		q.setStart((page - 1) * rows);
		q.setRows(rows);
		q.set("df", "item_title");
		q.setHighlight(true);
		// 设置高亮域
		q.addHighlightField("item_title");
		// 设置高亮前后缀
		q.setHighlightSimplePre("<font style=\"color:'red'>\"");
		q.setHighlightSimplePost("</font>");
		SearchResult search = searchDao.search(q);
		long recordCount = search.getRecordCount();
		search.setTotalPages(recordCount % rows > 0 ? (recordCount / rows) + 1 : recordCount / rows);
		return search;
	}

}

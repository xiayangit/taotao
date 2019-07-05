package com.xy.taotao.search.dao;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.xy.taotao.common.pojo.SearchItem;
import com.xy.taotao.common.pojo.SearchResult;

@Repository
public class SearchDao {

	@Autowired
	private SolrServer solrServer;
	
	public SearchResult search(SolrQuery query) {
		try {
			QueryResponse response = solrServer.query(query);
			SolrDocumentList results = response.getResults();
			long numFound = results.getNumFound();
			SearchResult s = new SearchResult();
			Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();
			List<SearchItem> collect = results.stream().map(search -> {
				SearchItem s1 = new SearchItem();
				Map<String, List<String>> map = highlighting.get((String)search.get("id"));
				List<String> list = map.get("item_title");
				String title = "";
				if(list != null && list.size() > 0) {
					title = list.get(0);
				}else {
					title = (String)search.get("item_title");
				}
				s1.setId((String)search.get("id"));
				s1.setCategory_name((String)search.get("item_category_name"));
				s1.setImage((String)search.get("item_image"));
				s1.setItem_desc((String)search.get("item_desc"));
				s1.setPrice((Long)search.get("item_price"));
				s1.setSell_point((String)search.get("item_sell_point"));
				s1.setTitle(title);
				return s1;
			}).collect(Collectors.toList());
			s.setItemList(collect);
			s.setRecordCount(numFound);
			return s;
		} catch (SolrServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
}

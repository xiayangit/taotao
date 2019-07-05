package com.xy.taotao.search.service.impl;

import java.util.List;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xy.taotao.common.pojo.SearchItem;
import com.xy.taotao.common.pojo.SearchResult;
import com.xy.taotao.common.pojo.TaoTaoResult;
import com.xy.taotao.search.mapper.SearchItemMapper;
import com.xy.taotao.search.service.SearchItemService;

@Service
public class SearchItemServiceImpl implements SearchItemService{

	@Autowired
	private SearchItemMapper searchMapper;
	@Autowired
	private SolrServer soleServer;
	
	@Override
	public TaoTaoResult getAllItem() {
		try {
			List<SearchItem> list = searchMapper.getList();
			for (SearchItem item : list) {
				SolrInputDocument dc = new SolrInputDocument();
				dc.addField("id", item.getId());
				dc.addField("item_title", item.getTitle());
				dc.addField("item_sell_point", item.getSell_point());
				dc.addField("item_price", item.getPrice());
				dc.addField("item_image", item.getImage());
				dc.addField("item_category_name", item.getCategory_name());
				dc.addField("item_desc", item.getItem_desc());
				soleServer.add(dc);
			}
			soleServer.commit();
		} catch (Exception e) {
			e.printStackTrace();
			TaoTaoResult.build(500, "数据导入失败");
		}
		return TaoTaoResult.ok();
	}

	@Override
	public SearchResult searchAll(String query) {
		// TODO Auto-generated method stub
		return null;
	}

}

package com.xy.taotao.search.listener;

import java.io.IOException;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;

import com.xy.taotao.common.pojo.SearchItem;
import com.xy.taotao.common.utils.JsonUtils;

public class MessageListener implements javax.jms.MessageListener{

	@Autowired
	private SolrServer solrServer;
	
	@Override
	public void onMessage(Message message) {
		// TODO Auto-generated method stub
		//取消息结果
		TextMessage m = (TextMessage) message;
		SearchItem s = new SearchItem();
		try {
			String text = m.getText();
			s = JsonUtils.jsonToPojo(text, SearchItem.class);
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//创建solr文档对象
		SolrInputDocument d = new SolrInputDocument();
		//向各个文本域中插入值
		d.addField("id", s.getId());
		d.addField("item_title", s.getTitle());
		d.addField("item_sell_point", s.getSell_point());
		d.addField("item_price", s.getPrice());
		d.addField("item_image", s.getImage());
		d.addField("item_category_name", s.getCategory_name());
		d.addField("item_desc", s.getItem_desc());
		//将文本对象写入索引库
		try {
			solrServer.add(d);
			solrServer.commit();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

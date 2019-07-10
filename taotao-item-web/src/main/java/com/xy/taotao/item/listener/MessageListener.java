package com.xy.taotao.item.listener;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import com.xy.taotao.common.pojo.SearchItem;
import com.xy.taotao.common.utils.JsonUtils;
import com.xy.taotao.item.controller.item;
import com.xy.taotao.pojo.TbItemDesc;
import com.xy.taotao.service.TbItemService;

import freemarker.template.Configuration;
import freemarker.template.Template;

public class MessageListener implements javax.jms.MessageListener{

	@Autowired
	private FreeMarkerConfigurer freeMarkerConfigurer;
	
	@Autowired
	private TbItemService itemService;
	
	@Override
	public void onMessage(Message message) {
		TextMessage m = (TextMessage)message;
		try {
			String text = m.getText();
			SearchItem jsonToPojo = JsonUtils.jsonToPojo(text, SearchItem.class);
			Configuration configuration = freeMarkerConfigurer.createConfiguration();
			Template template = configuration.getTemplate("item.ftl");
			Thread.sleep(1000);
			//创建原数据
			Map data = new HashMap<>();
			item i = new item(itemService.getItem(Long.parseLong(jsonToPojo.getId())));
			TbItemDesc desc = itemService.getDesc(Long.parseLong(jsonToPojo.getId()));
			data.put("item", i);
			data.put("itemDesc", desc);
			Writer w = new FileWriter(new File("D:/test/"+jsonToPojo.getId()+".html"));
			template.process(data, w);
			w.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

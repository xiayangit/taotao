package com.xy.taotao.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xy.taotao.common.pojo.EasyDataGridResult;
import com.xy.taotao.common.pojo.SearchItem;
import com.xy.taotao.common.pojo.TaoTaoResult;
import com.xy.taotao.common.utils.IDUtils;
import com.xy.taotao.common.utils.JsonUtils;
import com.xy.taotao.mapper.TbItemDescMapper;
import com.xy.taotao.mapper.TbItemMapper;
import com.xy.taotao.pojo.TbItem;
import com.xy.taotao.pojo.TbItemDesc;
import com.xy.taotao.pojo.TbItemExample;
import com.xy.taotao.redis.JedisClient;
import com.xy.taotao.service.TbItemService;

@Service
public class TbItemServiceImpl implements TbItemService {

	@Autowired
	private TbItemMapper itemMapper;

	@Autowired
	private TbItemDescMapper itemDescMapper;

	@Autowired
	private JmsTemplate jsmTemplate;

	@Resource(name = "addItemTopic")
	private Destination destination;

	@Autowired
	private JedisClient jedisClient;

	@Override
	public TbItem getItem(long pkey) {
		// 先查缓存
		try {
			String string = jedisClient.get("ITEM:" + pkey + "INFO");
			if (string != null && !"".equals(string) && !"null".equals(string)) {
				TbItem jsonToPojo = JsonUtils.jsonToPojo(string, TbItem.class);
				return jsonToPojo;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		TbItem selectByPrimaryKey = itemMapper.selectByPrimaryKey(pkey);
		// 把查询结果添加到缓存
		try {
			jedisClient.set("ITEM:" + pkey + "INFO", JsonUtils.objectToJson(selectByPrimaryKey));
			// 设置缓存时间为1天
			jedisClient.expire("ITEM:" + pkey + "INFO", 86400);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return selectByPrimaryKey;
	}

	@Override
	public EasyDataGridResult list(int page, int rows) {
		PageHelper.startPage(page, rows);
		TbItemExample e = new TbItemExample();
		List<TbItem> selectByExample = itemMapper.selectByExample(e);
		PageInfo<TbItem> info = new PageInfo<>(selectByExample);
		EasyDataGridResult ea = new EasyDataGridResult();
		ea.setTotal(info.getTotal());
		ea.setRows(selectByExample);
		return ea;
	}

	@Override
	public TaoTaoResult add(TbItem item, String desc) {
		long genItemId = IDUtils.genItemId();
		item.setId(genItemId);
		item.setStatus((byte) 1);
		item.setCreated(new Date());
		item.setUpdated(new Date());
		itemMapper.insert(item);
		TbItemDesc d = new TbItemDesc();
		d.setCreated(new Date());
		d.setItemDesc(desc);
		d.setItemId(genItemId);
		d.setUpdated(new Date());
		itemDescMapper.insert(d);
		SearchItem i = new SearchItem();
		i.setCategory_name("分类0");
		i.setId(String.valueOf(genItemId));
		i.setImage(item.getImage());
		i.setItem_desc(desc);
		i.setPrice(item.getPrice());
		i.setSell_point(item.getSellPoint());
		i.setTitle(item.getTitle());
		String json = JsonUtils.objectToJson(i);
		jsmTemplate.send(destination, new MessageCreator() {

			@Override
			public Message createMessage(Session session) throws JMSException {
				TextMessage msg = session.createTextMessage(json);
				return msg;
			}
		});
		return TaoTaoResult.ok();
	}

	@Override
	public TbItemDesc getDesc(long key) {
		// 先查缓存
		try {
			String string = jedisClient.get("ITEM:" + key + "DESC");
			if (string != null && !"".equals(string) && !"null".equals(string)) {
				TbItemDesc jsonToPojo = JsonUtils.jsonToPojo(string, TbItemDesc.class);
				return jsonToPojo;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		TbItemDesc selectByPrimaryKey = itemDescMapper.selectByPrimaryKey(key);
		// 把查询结果添加到缓存
		try {
			jedisClient.set("ITEM:" + key + "DESC", JsonUtils.objectToJson(selectByPrimaryKey));
			// 设置缓存时间为1天
			jedisClient.expire("ITEM:" + key + "DESC", 86400);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return selectByPrimaryKey;
	}

}

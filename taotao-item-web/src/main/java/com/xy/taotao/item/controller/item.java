package com.xy.taotao.item.controller;

import com.xy.taotao.pojo.TbItem;

public class item extends TbItem{

	public item(TbItem item){
		setId(item.getId());
		setTitle(item.getTitle());
		setSellPoint(item.getSellPoint());
		setPrice(item.getPrice());
		setNum(item.getNum());
		setBarcode(item.getBarcode());
		setImage(item.getImage());
		setCid(item.getCid());
		setStatus(item.getStatus());
		setCreated(item.getCreated());
		setUpdated(item.getUpdated());
	}
	
	private String[] images;
	
	public String[] getImages() {
		if(this.getImage() != null && !"".equals(this.getImage())) {
			return this.getImage().split(",");
		}
		return null;
	}
}

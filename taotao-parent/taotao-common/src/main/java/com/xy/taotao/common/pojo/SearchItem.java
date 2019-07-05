package com.xy.taotao.common.pojo;

import java.io.Serializable;

import lombok.Data;

@Data
public class SearchItem implements Serializable {

	private String id;
	private String title;
	private String sell_point;
	private long price;
	private String image;
	private String category_name;
	private String item_desc;
}

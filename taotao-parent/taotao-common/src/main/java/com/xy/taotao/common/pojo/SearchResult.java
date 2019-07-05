package com.xy.taotao.common.pojo;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class SearchResult implements Serializable{

	private long totalPages;
	private long recordCount;
	private List<SearchItem> itemList;
	
}

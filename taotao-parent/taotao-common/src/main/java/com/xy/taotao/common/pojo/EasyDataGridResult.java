package com.xy.taotao.common.pojo;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class EasyDataGridResult implements Serializable{

	private long total;
	private List rows;
	
}

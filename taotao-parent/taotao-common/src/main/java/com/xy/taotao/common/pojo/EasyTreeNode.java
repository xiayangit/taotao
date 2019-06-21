package com.xy.taotao.common.pojo;

import java.io.Serializable;

import lombok.Data;

@Data
public class EasyTreeNode implements Serializable{

	private long id;
	private String text;
	private String state;
}

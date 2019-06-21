package com.xy.taotao.common.pojo;

import java.io.Serializable;


public class TaoTaoResult implements Serializable{

	private Integer status;

	private String msg;

	private Object data;

	public static TaoTaoResult ok(Object data) {
		return new TaoTaoResult(data);
	}

	public static TaoTaoResult ok() {
		return new TaoTaoResult(null);
	}

	public static TaoTaoResult build(Integer status, String msg, Object data) {
		return new TaoTaoResult(status, msg, data);
	}

	public static TaoTaoResult build(Integer status, String msg) {
		return new TaoTaoResult(status, msg, null);
	}
	
	

	public TaoTaoResult() {
		super();
	}

	public TaoTaoResult(Integer status, String msg, Object data) {
		super();
		this.status = status;
		this.msg = msg;
		this.data = data;
	}

	public TaoTaoResult(Object data) {
		this.status = 200;
		this.msg = "OK";
		this.data = data;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

}

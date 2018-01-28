package com.odianyun;

import java.io.Serializable;

/**
 * 请求体基类
 *
 * @author lxs
 * @version 1.0
 *
 */
public class BaseRequestBean implements Serializable {
	private static final long serialVersionUID = 1L;
	public String code;
	public String message;
}

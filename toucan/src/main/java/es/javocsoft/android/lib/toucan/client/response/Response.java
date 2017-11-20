/**
 * JavocSoft Toucan API Client Library.
 *
 *   Copyright (C) 2013 JavocSoft - Javier González Serrano.
 *
 *   This file is part of JavcoSoft Toucan API client Library.
 *
 *   This library is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   JavocSoft Toucan Client Library is distributed in the hope that it will 
 *   be useful, but WITHOUT ANY WARRANTY; without even the implied warranty 
 *   of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with JavocSoft Toucan API Client Library. If not, 
 *   see <http://www.gnu.org/licenses/>.
 */
package es.javocsoft.android.lib.toucan.client.response;

import java.util.Date;

import com.google.gson.annotations.Expose;


/**
 * A response object that holds the result of a 
 * request.
 *
 * @author JavocSoft, 2017
 * @since 2017
 */
public class Response {

	public static final int RESULT_OK = 0;
	public static final int ERROR_BAD_REQUEST = -10;
	public static final int ERROR_SYSTEM = -100;	
	public static final int ERROR_DB = -200;	
	public static final int ERROR_UNEXPECTED = -300;
	
	
	/**
	 * The response code.
	 */
	@Expose
	private int code;

	/**
	 * The response message. An status
	 * message. 
	 * 
	 * (may be <code>null</code>).
	 */
	@Expose
	private String msg;
	
	/**
	 * This field will contain the result
	 * if a result is required.
	 * 
	 * (may be <code>null</code>).
	 */
	@Expose
	private Object data;
	
	/**
	 * The time when server answers
	 */
	@Expose
	private long ts;
	
	/**
	 * Application last version.
	 */
	@Expose
	private Integer appVersion;
	
	
	public Response(int code) {
		this(code, null, null);
	}
	
	public Response(int code, String msg, Object data) {
		this.code = code;
		this.msg = msg;
		this.data = data;
		this.ts = new Date().getTime();
	}

	
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getResult() {
		return data;
	}
	public void setResult(Object data) {
		this.data = data;
	}

	public long getTs() {
		return ts;
	}
	public void setTs(long ts) {
		this.ts = ts;
	}

	public Integer getAppVersion() {
		return appVersion;
	}
	public void setAppVersion(Integer appVersion) {
		this.appVersion = appVersion;
	}	
	
}
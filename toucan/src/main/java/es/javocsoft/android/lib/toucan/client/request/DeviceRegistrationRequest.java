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
package es.javocsoft.android.lib.toucan.client.request;

import com.google.gson.annotations.Expose;

import es.javocsoft.android.lib.toucan.client.request.bean.DeviceRegistrationBean;


/**
 * A device registration request.
 *
 * @author JavocSoft, 2017
 * @since 2017
 */
public class DeviceRegistrationRequest {
	
	@Expose
	private String appKey;
	@Expose
	private String appHashSignature; //SHA-1 of PubKey + API TOKEN
	@Expose
	private String hashSignature; //SHA-1 of PubKey + the request data bean as String 
	
	@Expose
	private DeviceRegistrationBean data;
		
	
	public DeviceRegistrationRequest() {}

	
	public DeviceRegistrationRequest(String appKey, DeviceRegistrationBean data, String appHashSignature, String hashSignature) {
		this.appKey = appKey;
		this.data = data;
		this.appHashSignature = appHashSignature;
		this.hashSignature = hashSignature;				
	}
	
	public void fillFromRequest(DeviceRegistrationRequest data) {
		this.appKey = data.appKey;
		this.data = data.data;
		this.appHashSignature = data.appHashSignature;
		this.hashSignature = data.hashSignature;				
	}
	
	
	//GETTERS & SETTERS
	
	public String getAppKey() {
		return appKey;
	}
	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}

	public String getAppHashSignature() {
		return appHashSignature;
	}
	public void setAppHashSignature(String appHashSignature) {
		this.appHashSignature = appHashSignature;
	}

	public String getHashSignature() {
		return hashSignature;
	}
	public void setHashSignature(String hashSignature) {
		this.hashSignature = hashSignature;
	}

	public DeviceRegistrationBean getData() {
		return data;
	}
	public void setData(DeviceRegistrationBean data) {
		this.data = data;
	}
		
}

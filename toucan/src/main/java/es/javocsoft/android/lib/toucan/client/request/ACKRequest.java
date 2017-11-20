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


/**
 * An ACK request for read or received notification.
 * 
 * @author JavocSoft, 2017
 * @since 2017
 */
public class ACKRequest {
	
	@Expose
	private String token;
	@Expose
	private String nId;
	@Expose
	private String nRef;
	/* Just in case in a multiple delivery the user receives two notifications in the same
	 * package but with different texts. This only could be possible when uploading a file
	 * with external Ids. */
	@Expose
	private String message;
	
	@Expose
	private String appKey;
	@Expose
	private String appHashSignature; //SHA-1 of PubKey + API TOKEN
	
	
	public ACKRequest() {}

	
	public ACKRequest(String token, String nId, String nRef, String appKey, String appHashSignature, String message) {		
		this.token = token;
		this.nId = nId;
		this.nRef = nRef;
		this.appKey = appKey;
		this.message = message;
		
		this.appHashSignature = appHashSignature;		
	}
	
	public void fillFromRequest(ACKRequest data) {
		this.token = data.token;
		this.nId = data.nId;
		this.nRef = data.nRef;
		this.appKey = data.appKey;
		this.message = data.message;
		
		this.appHashSignature = data.appHashSignature;		
	}


	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}

	public String getnId() {
		return nId;
	}
	public void setnId(String nId) {
		this.nId = nId;
	}

	public String getnRef() {
		return nRef;
	}
	public void setnRef(String nRef) {
		this.nRef = nRef;
	}
		
	public String getAppKey() {
		return appKey;
	}
	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}
	
	/** Just in case in a multiple delivery the user receives two notifications in the same
	 * package but with different texts. This only could be possible when uploading a file
	 * with external Ids. */
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

	public String getAppHashSignature() {
		return appHashSignature;
	}
	public void setAppHashSignature(String appHashSignature) {
		this.appHashSignature = appHashSignature;
	}

}

package es.javocsoft.android.lib.toucan.client.request.bean;

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
import java.util.Date;

import com.google.gson.annotations.Expose;


/**
 * Each user can have n application tags.
 * 
 * This class holds a tag.
 *
 * @author JavocSoft, 2017
 * @since 2017
 */
public class AppUserTag {
	
	@Expose
	private int id;	
	
	@Expose
	private int appId;
	@Expose
	private String devUniqueId;
	@Expose
	private String tag;
	@Expose
	private Date tsCreated;
	
	
	//CONSTRUCTORS
	
	public AppUserTag() {}

	
	public AppUserTag(int appId, String devUniqueId, String tag, Date tsCreated) {
		super();
		this.appId = appId;
		this.devUniqueId = devUniqueId;
		this.tag = tag;
		this.tsCreated = tsCreated;
	}


	//GETTERS & SETTERS

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public int getAppId() {
		return appId;
	}
	public void setAppId(int appId) {
		this.appId = appId;
	}

	/**
	 * This is a unique device Id identificator that the
	 * device sends to us.
	 * 
	 * @return
	 */
	public String getDevUniqueId() {
		return devUniqueId;
	}
	public void setDevUniqueId(String devUniqueId) {
		this.devUniqueId = devUniqueId;
	}

	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}

	public Date getTsCreated() {
		return tsCreated;
	}
	public void setTsCreated(Date tsCreated) {
		this.tsCreated = tsCreated;
	}
	
}

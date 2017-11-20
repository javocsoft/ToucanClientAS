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
package es.javocsoft.android.lib.toucan.client.thread.callback;

import android.content.Context;

import com.google.gson.annotations.Expose;

import es.javocsoft.android.lib.toucan.client.ToucanClient;
import es.javocsoft.android.lib.toucan.client.response.Response;


/**
 * If a callback is needed, extend this class to achieve it.
 *
 * @author JavocSoft, 2017
 * @since 2017
 *
 */
public abstract class ResponseCallback extends Thread implements Runnable {

	protected static final String TAG = ToucanClient.LOG_TAG + "::CALLBACK";
	
	public static final int CALLBACK_OPERATION_LIST_TAGS = 1;
	public static final int CALLBACK_OPERATION_ADD_TAGS = 2;
	public static final int CALLBACK_OPERATION_REMOVE_TAGS = 3;
	public static final int CALLBACK_OPERATION_RESET_TAGS = 4;
	public static final int CALLBACK_OPERATION_DEVICE_REGISTRATION = 4;
	public static final int CALLBACK_OPERATION_DEVICE_INFORMREFERRAL = 4;
	public static final int CALLBACK_OPERATION_ACK_RECEIVED = 4;
	public static final int CALLBACK_OPERATION_ACK_READ = 4;
	
	private transient Response response;
	private transient Context context;
	
	@Expose
	private String callbackClassName;
	@Expose	
	private int callbackOperation;
	
	
	public ResponseCallback(String callbackClassName) {
		this.callbackClassName = callbackClassName;
	}

		
	@Override
	public void run() {
		doWork();
	}
	
	private void doWork() {		
		callback();
	}
	
	public String getCallbackClassName() {
		return callbackClassName;
	}	
	
	
	public void setCallbackOperation(int callbackOperation) {
		this.callbackOperation = callbackOperation;
	}
	public int getCallbackOperation() {
		return callbackOperation;
	}
	public void setContext(Context context) {
		this.context = context;
	}
	public void setResponse(Response response) {
		this.response = response;		
	}
	public Response getResponse() {
		return response;
	}
	
	
	protected Context getContext() {
		return context;
	}
	
	
	public abstract void callback();
	
}

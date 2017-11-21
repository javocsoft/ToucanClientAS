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
package es.javocsoft.android.lib.toucan.client.thread;

import java.lang.reflect.Constructor;

import android.content.Context;
import android.util.Log;

import com.google.gson.annotations.Expose;
import com.google.gson.internal.LinkedTreeMap;

import es.javocsoft.android.lib.toolbox.ToolBox;
import es.javocsoft.android.lib.toolbox.ToolBox.HASH_TYPE;
import es.javocsoft.android.lib.toolbox.json.GsonProcessor;
import es.javocsoft.android.lib.toucan.client.ToucanClient;
import es.javocsoft.android.lib.toucan.client.request.ACKRequest;
import es.javocsoft.android.lib.toucan.client.request.AppDevTagsOperationRequest;
import es.javocsoft.android.lib.toucan.client.request.DeviceRegistrationRequest;
import es.javocsoft.android.lib.toucan.client.response.Response;
import es.javocsoft.android.lib.toucan.client.response.exception.ResponseParseException;
import es.javocsoft.android.lib.toucan.client.thread.callback.ResponseCallback;

/**
 * Base API operation request to JavocSoft Toucan Notifications API.
 *
 * @author JavocSoft, 2017
 * @since 2017
 *
 */
public abstract class ToucanWorker extends Thread implements Runnable {

	public static enum TOUCAN_WORKER_TYPE {GET, POST};
	public static enum TOUCAN_WORKER_POST_DATA_TYPE {REGISTRATION, ACK, TAGS};
	
	
	protected transient Context context;
	
	@Expose
	protected String jobName;
	@Expose
	protected String apiToken;
	@Expose
	protected String endpoint;
	@Expose
	protected String opname;
	@Expose
	protected Object data;
	@Expose
	protected String dataString;
	@Expose
	protected TOUCAN_WORKER_POST_DATA_TYPE dataType;
	
	private transient String jsonData;

	@Expose
	protected boolean ignoreSSLErrors;
	@Expose
	protected TOUCAN_WORKER_TYPE type;
	@Expose
	protected int typeInt;
	@Expose
	protected boolean running;
	
	protected ResponseCallback callback;
	@Expose
	protected String callbackString;
	
	
	public ToucanWorker(TOUCAN_WORKER_TYPE type, Context context, String apiToken, String endpoint, String opName, boolean ignoreSSLErrors, ResponseCallback callback) {
		this.type = type;
		this.typeInt = type.ordinal();
		this.context = context;
		this.apiToken = apiToken;
		this.endpoint = endpoint;
		this.opname = opName;
		this.ignoreSSLErrors = ignoreSSLErrors;
		this.callback = callback;
		if(callback!=null)
			callbackString = GsonProcessor.getInstance().getGsonWithExposedFilter().toJson(callback);
		init();
	}
	
	public ToucanWorker(TOUCAN_WORKER_TYPE type, Context context, String apiToken, Object data, TOUCAN_WORKER_POST_DATA_TYPE dataType, String endpoint, String opName, boolean ignoreSSLErrors, ResponseCallback callback) {
		this.type = type;
		this.typeInt = type.ordinal();
		this.context = context;
		this.data = data;
		if(data!=null)
			this.dataString = GsonProcessor.getInstance().getGsonWithExposedFilter().toJson(data);
		this.dataType = dataType;
		this.apiToken = apiToken;
		this.endpoint = endpoint;
		this.opname = opName;
		this.ignoreSSLErrors = ignoreSSLErrors;
		this.callback = callback;
		if(callback!=null)
			callbackString = GsonProcessor.getInstance().getGsonWithExposedFilter().toJson(callback);
		init();
	}
	
	private void init() {
		//We create the unique JobName				
		jsonData = GsonProcessor.getInstance().getGsonWithExposedFilter().toJson(this);
		this.jobName = ToucanClient.CACHED_REQUEST_FILE_PREFIX + ToolBox.crypto_getHASH(jsonData.getBytes(), HASH_TYPE.sha1);		
	}
	
	@Override
	public void run() {
		running = true;
		if(ToolBox.net_isNetworkAvailable(context)){
			doWork();
		}		
	}
	
	public String getJobId() {
		return jobName;
	}
	
	public String getOperationName() {
		return opname;
	}

	public boolean isIgnoreSSLErrors() {
		return ignoreSSLErrors;
	}
	
	public boolean isRunning() {
		return running;
	}
	
	public TOUCAN_WORKER_TYPE getType() {
		return type;
	}
	
	public int getTypeInt() {
		return typeInt;
	}
	
	public void setContext(Context context) {
		this.context = context;		
	}
	
	/**
	 * Recreates a POSt data object from the JSOn string.
	 */
	public void initData() {
		if(data!=null) {
			if(data instanceof LinkedTreeMap){
				if(dataType==TOUCAN_WORKER_POST_DATA_TYPE.REGISTRATION) {
					DeviceRegistrationRequest devRegRequest = GsonProcessor.getInstance().getGsonWithExposedFilter().fromJson(dataString, DeviceRegistrationRequest.class);
					this.data = devRegRequest;
				}else if(dataType==TOUCAN_WORKER_POST_DATA_TYPE.ACK){
					ACKRequest ackRequest = GsonProcessor.getInstance().getGsonWithExposedFilter().fromJson(dataString, ACKRequest.class);
					this.data = ackRequest;
				}else if(dataType==TOUCAN_WORKER_POST_DATA_TYPE.TAGS){
					AppDevTagsOperationRequest tagsRequest = GsonProcessor.getInstance().getGsonWithExposedFilter().fromJson(dataString, AppDevTagsOperationRequest.class);
					this.data = tagsRequest;
				}
			}else{
				Log.i(ToucanClient.LOG_TAG, "Unknown data format.");
			}
		}else{
			Log.i(ToucanClient.LOG_TAG, "No data");
		}
	}
	
	/**
	 * When operation finishes we do finalization tasks.
	 * 
	 * @param success
	 */
	protected void operationDone(boolean success, final Response response) {
		running = false;
		if(success) {
			try {
				if(ToolBox.storage_checkIfFileExistsInInternalStorage(context, jobName)) {
					ToolBox.storage_deleteDataFromInternalStorage(context, jobName);
					Log.i(ToucanClient.LOG_TAG, "Pending operation request deleted form disk (" + jobName + ")");
				}
				
				if(callbackString!=null && callbackString.length()>0) {
					//TODO FIX. Until we discover why we can not access to any
					//			of the method of a recovered ResponseCallback we
					//			recreate it by this way, from the JSON string.
					
					String[] cbInfo = callbackString.split(","); //Just in case there are more fields.
					if(cbInfo!=null && cbInfo.length>=1) {
						
						//Look for the custom assigned to operation callback class
						String callbackClassName = null;
						String callbackOperation = null;
						String[] cbFieldInfo = null;
						for(String cbClassField:cbInfo){
							//Purge the string
							cbClassField = cbClassField.replaceAll("\"", "")
														.replaceAll("\\{", "")
														.replaceAll("\\}", "");
							
							cbFieldInfo = cbClassField.split(":");
							if(cbFieldInfo!=null && cbFieldInfo.length==2) {								
								String cbField = cbFieldInfo[0];
								if(cbField.equals("callbackClassName")) {
									String cbFieldvalue = cbFieldInfo[1];
									callbackClassName = cbFieldvalue;									
								}else if(cbField.equals("callbackOperation")) {
									callbackOperation = cbFieldInfo[1];
								}
							}
						}
						
						
						if(callbackClassName!=null) {
							Log.i(ToucanClient.LOG_TAG, "Operation callback present (" + callbackClassName + "). launching it.");
							
							//We recover the callback
							try {
								//Instantiate using the constructor and casting to 
								//the desired object
								Class clazz = Class.forName(callbackClassName);
								Constructor constructor = clazz.getConstructor();						
								ResponseCallback rc = (ResponseCallback)clazz.cast(constructor.newInstance());
								rc.setContext(context);
								rc.setResponse(response);
								if(callbackOperation!=null && callbackOperation.length()>0)
									rc.setCallbackOperation(Integer.parseInt(callbackOperation));
								Log.i(ToucanClient.LOG_TAG, "Operation callback recreated.");
								rc.start();
								Log.i(ToucanClient.LOG_TAG, "Operation callback (" + callbackClassName + "). launched.");
								
							} catch (ClassNotFoundException e) {
								Log.e(ToucanClient.LOG_TAG, "No callback class found! [" + callbackClassName + "] :: " + e.getMessage(), e);
							} catch (InstantiationException e) {
								Log.e(ToucanClient.LOG_TAG, "Callback class instantation error! [" + callbackClassName + "] :: " + e.getMessage(), e);
							} catch (NoSuchMethodException e) {
								Log.e(ToucanClient.LOG_TAG, "No callback method found [" + callbackClassName + "] :: " + e.getMessage(), e);
							} catch (IllegalArgumentException e) {
								Log.e(ToucanClient.LOG_TAG, "Callback class method mismatch arguments! [" + callbackClassName + "] :: " + e.getMessage(), e);
							} catch (Exception e) {
								Log.e(ToucanClient.LOG_TAG, "Callback class unexpected execution error! [" + callbackClassName + "] :: " + e.getMessage(), e);
							}
						}					
					}else{
						Log.e(ToucanClient.LOG_TAG, "Callback class without anay field! Aborted.");
					}
				}
			} catch (Exception e) {
				Log.e(ToucanClient.LOG_TAG, "Pending operation request cached file could not be deleted [" +e.getMessage() + "].",e);
			}
		}else{
			//We save to try again later
			cacheOperationRequest(this);
		}
	}
	
	/**
	 * parses the server response.
	 * 
	 * @param jsonResponse
	 * @return
	 */
	protected Response parseResponse(String jsonResponse) throws ResponseParseException {
		Response result = null;
    	try{
    		result = GsonProcessor.getInstance().getGsonWithExposedFilter().fromJson(jsonResponse, Response.class);
    		if(result==null) {
    			Log.e(ToucanClient.LOG_TAG, "Operation done but response could not be parsed");
    			throw new ResponseParseException("Response could not be parsed");
    		}
    	}catch(Exception e) {
    		Log.e(ToucanClient.LOG_TAG, "Operation done but response parsing exception happened [" +e.getMessage() +"]", e);
    		throw new ResponseParseException("Response could not be parsed [" + e.getMessage() + "]", e);
    	}
    	
    	return result;
	}
	
	
	/**
	 * Creates a ToucanPostWorker object from JSON data.
	 * 
	 * @param jsonData
	 * @return
	 */
	public static ToucanPostWorker initializePOSTFromJSON(String jsonData) {
		ToucanPostWorker res = null;
		
		ToucanWorker temp = GsonProcessor.getInstance().getGsonWithExposedFilter().fromJson(jsonData, ToucanPostWorker.class);
		res = new ToucanPostWorker(temp);
		if(temp.callbackString!=null && temp.callbackString.length()>0)
			res.callbackString = temp.callbackString;
		
		return res;
	}
	
	/**
	 * Creates a ToucanGetWorker object from JSON data.
	 * 
	 * @param jsonData
	 * @return
	 */
	public static ToucanGetWorker initializeGETFromJSON(String jsonData) {
		ToucanGetWorker res = null;
		
		ToucanWorker temp = GsonProcessor.getInstance().getGsonWithExposedFilter().fromJson(jsonData, ToucanGetWorker.class);
		res = new ToucanGetWorker(temp);		
		if(temp.callbackString!=null && temp.callbackString.length()>0)
			res.callbackString = temp.callbackString;
		
		return res;
	}
	
	abstract public void doWork();
	
	
	
	// AUXILIAR
	
	/**
	 * Saves in disk the operation (or overwrites it) for 
	 * afterwards delivery try.
	 * 
	 * @param operation
	 */
	private void cacheOperationRequest(ToucanWorker operation) {
		try{
			String jsonData = GsonProcessor.getInstance().getGsonWithExposedFilter().toJson(operation);
			String fileName = operation.getJobId();			
			ToolBox.storage_storeDataInInternalStorage(context, fileName, jsonData.getBytes());
			Log.i(ToucanClient.LOG_TAG, "Saved pending operation request to disk (" + operation.getOperationName() + "/" + operation.getJobId() + ")");
		}catch(Exception e) {
			Log.e(ToucanClient.LOG_TAG, "Error cacheOperationRequest() - Operation request could not be cached [" + e.getMessage() + "].",e);
		}
	}
}

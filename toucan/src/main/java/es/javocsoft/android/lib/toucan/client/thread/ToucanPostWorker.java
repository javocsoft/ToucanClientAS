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

import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.util.Log;
import es.javocsoft.android.lib.toolbox.ToolBox;
import es.javocsoft.android.lib.toolbox.ToolBox.HTTP_METHOD;
import es.javocsoft.android.lib.toolbox.json.GsonProcessor;
import es.javocsoft.android.lib.toucan.client.ToucanClient;
import es.javocsoft.android.lib.toucan.client.response.Response;
import es.javocsoft.android.lib.toucan.client.response.exception.ResponseParseException;
import es.javocsoft.android.lib.toucan.client.thread.callback.ResponseCallback;

/**
 * POST operation to JavocSoft Toucan Notifications API.
 *
 * @author JavocSoft, 2017
 * @since 2017
 *
 */
public class ToucanPostWorker extends ToucanWorker {

	
	public ToucanPostWorker(Context context, String apiToken, Object data, TOUCAN_WORKER_POST_DATA_TYPE dataType, String endpoint, String opName, boolean ignoreSSLErrors, ResponseCallback callback) {
		super(TOUCAN_WORKER_TYPE.POST, context, apiToken, data, dataType, endpoint, opName, ignoreSSLErrors, callback);
	}
	
	public ToucanPostWorker(ToucanWorker tWorker) {
		super(TOUCAN_WORKER_TYPE.POST, tWorker.context, tWorker.apiToken, tWorker.data, tWorker.dataType, tWorker.endpoint, tWorker.opname, tWorker.isIgnoreSSLErrors(), tWorker.callback);
	}
	
	@Override
	public void doWork() {			
		//Do the POST request to the API			
		try {
			//Prepare header data for Toucan API
			Map<String, String> headersData = new HashMap<String, String>();
			headersData.put("Authorization", "ttmSecTKN " + apiToken);
			
			String jsonData = GsonProcessor.getInstance().getGsonWithExposedFilter().toJson(data);
			String finalUrl = (endpoint);

			Log.i(ToucanClient.LOG_TAG, opname.toUpperCase() + ". Request <<" + jsonData +
					">>. Sent to Toucan API. IgnoreSSL? " + ignoreSSLErrors);

			String jsonDataKey = null;
        	String response = ToolBox.net_httpclient_doAction(
        			HTTP_METHOD.POST, 
        			finalUrl, 
        			jsonDataKey, jsonData, headersData, ignoreSSLErrors);
        	
        	Log.i(ToucanClient.LOG_TAG, opname.toUpperCase() + ". Request <<" + jsonData + 
                    ">>. Sent to Toucan API. Call response '" + response + "'");               
        	        	
        	Response res = parseResponse(response);
        	if(res.getCode()==Response.RESULT_OK){
				Log.i(ToucanClient.LOG_TAG, "Operation done and response from server OK.");
			}else{
				Log.i(ToucanClient.LOG_TAG, "Operation done but response code not OK [Code: " + res.getCode() + "]->" + res.getMsg());				    				
			}
        	
        	operationDone(true, res);
        
		}catch(ResponseParseException e) {
			Log.e(ToucanClient.LOG_TAG, "Error parsing server response for operation '" + opname.toUpperCase() + "' request to Toucan API (" + e.getMessage() + ")", e);
			operationDone(false, null);
    	}catch(Exception e) {
    		Log.e(ToucanClient.LOG_TAG, "Error in operation " + opname.toUpperCase() + " request to Toucan API (" + e.getMessage() + ")", e);
    		operationDone(false, null);
    	}
	}
	
}
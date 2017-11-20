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
package es.javocsoft.android.lib.toucan.client.service;

import java.io.File;
import java.io.FilenameFilter;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import es.javocsoft.android.lib.toolbox.ToolBox;
import es.javocsoft.android.lib.toucan.client.ToucanClient;
import es.javocsoft.android.lib.toucan.client.thread.ToucanGetWorker;
import es.javocsoft.android.lib.toucan.client.thread.ToucanPostWorker;
import es.javocsoft.android.lib.toucan.client.thread.ToucanWorker;

/**
 * Service that delivers the pending operation request to the
 * notification server API.
 * 
 * By using a service for this, we avoid the system to destroy
 * the application if more resources are needed. Operation of 
 * this kind (deliveries) should always be done by using a service.
 * 
 * We use the IntentService because the advantages explained here: 
 * http://developer.android.com/guide/components/services.html
 *
 * Set this code in your AndroidManifest.xml to declare it.
 * <br>
 *
 * <pre>{@code
 * <service android:name="es.javocsoft.android.lib.toucan.client.service.PendingOperationsDeliveryService" />
 * }</pre>
 * 
 * @author JavocSoft, 2017
 * @since 2017
 */
public class PendingOperationsDeliveryService extends IntentService {
	
	private static final String SERVICE_NAME = "PendingOperationsDeliveryService";

	@SuppressWarnings("unused")
	public PendingOperationsDeliveryService() {
		super(SERVICE_NAME);
	}

	@SuppressWarnings("unused")
	public PendingOperationsDeliveryService(String name) {
		super(name);
	}
	
	
	@Override
	protected void onHandleIntent(Intent intent) {
		
		String[] pendingOperations = listCachedOperations(getApplicationContext());
		if(pendingOperations!=null && pendingOperations.length>0){
			if(ToolBox.net_isNetworkAvailable(getApplicationContext())){
				Log.i(ToucanClient.LOG_TAG, "Total pending operations: " + pendingOperations.length);
				sendCachedOperations(pendingOperations, getApplicationContext());
			}else{
				Log.i(ToucanClient.LOG_TAG, SERVICE_NAME + ": no network connection, skipping pending operations.");
			}
		}
		stopSelf();
	}
	
	// AUXILIAR FUNCTIONS ----------------------------------------------------------------------------------------------
	
	private static synchronized String[] listCachedOperations(Context context){
		String filePath = context.getFilesDir().getAbsolutePath();//returns current directory.
		File appInternalDir = new File(filePath);
		String[] pendingRequests = appInternalDir.list(new FilenameFilter(){
			public boolean accept(File arg0, String name) {
				return name.startsWith(ToucanClient.CACHED_REQUEST_FILE_PREFIX);			
			}});
		
		return pendingRequests;
	}
	
	private static synchronized void sendCachedOperations(String[] pendingRequestOp, Context context){
		Log.i(ToucanClient.LOG_TAG, SERVICE_NAME + ": Pending operation requests to send: " + pendingRequestOp.length);
		for(String requestOpFile:pendingRequestOp){			
			try {
				byte[] pendingRequestBytes = ToolBox.storage_readDataFromInternalStorage(context, requestOpFile);
				if(pendingRequestBytes!=null && pendingRequestBytes.length>0){
					startOperation(pendingRequestBytes, context);					
				}
				//We delete only if the operation is successfully done from the operations itself.				
			} catch (Exception e) {
				Log.e(ToucanClient.LOG_TAG, SERVICE_NAME + ": Error sending pending operation request ("+e.getMessage()+")",e);
			}			
		}
	}
		
	private static synchronized void startOperation(byte[] pendingRequestData, Context context) {
		String jsonData = new String(pendingRequestData);
		if(jsonData!=null && jsonData.length()>0) {
			new DeliverPendingRequest(jsonData, context).start();
		}else{
			Log.e(ToucanClient.LOG_TAG, "No JSON data to send cached operation!!.");
		}
	}
	
	
	static class DeliverPendingRequest extends Thread implements Runnable {
		
		private String jsonData;
		private Context context;
		
		public DeliverPendingRequest(String jsonData, Context context) {
			this.jsonData = jsonData;
			this.context = context;
		}

		@Override
		public void run() {
			//Determine the delivery type
			if(jsonData.indexOf("\"type\":\"POST\"")!=-1){
				ToucanPostWorker temp = ToucanWorker.initializePOSTFromJSON(jsonData);
				temp.initData();
				temp.setContext(context);
				temp.start();
			}else{
				//GET operation otherwise.
				ToucanGetWorker temp = ToucanWorker.initializeGETFromJSON(jsonData);
				temp.initData();
				temp.setContext(context);				
				temp.start();				
			}			
		}
		
	}
	
}

package iti.gp.navigation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.database.SQLException;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;

import iti.gp.navigation.db.*;

public class APScanHelper {

	Context context;
	WifiManager mainWifi;
	List<ScanResult> wifiList;
	ArrayList<AccessPoint> APsDataBase;
	ArrayList<AccessPoint> APsScaned;

	public APScanHelper(Context cxt)
	{
		context = cxt;
	}
	
	// Start Scan WiFi and Get List From Access Point
	public void startWifiWork() {
		// Start Scan about Access Point
		mainWifi = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
		
		boolean flag = mainWifi.startScan();
		if (flag == true) {
			// get List of Object from access point Found
			wifiList = mainWifi.getScanResults();
			// first Case WiFi Mobile Off
			if (wifiList == null) {
				String Title = "WiFi Is not Connected";
				String Message = "WiFi Is not Connected Please Open WIFI and Try Again";
				MessageHandler.messageBox(Title, Message, context);
			}
			// second Case Access Point Is not Connected
			else if (wifiList.isEmpty()) {
				String Title = "Access Point Error";
				String Message = "Access Point Is not Connected Please Open Access Point and Try Again";
				MessageHandler.messageBox(Title, Message, context);
			}
			// thread Case Access Point Is Connected and WiFi Worked
			else {
				ListInfoWifi();
			}
		}
		// Scan WIFI Error
		else {
			String Title = "Scan WIFI Error";
			String Message = "WiFi Is not Connected Or Access Point Is not Worked";
			MessageHandler.messageBox(Title, Message, context);
		}
	}
	
	public void getDataBaseAPs()
	{		
		DBHelper myDbHelper;
        myDbHelper = new DBHelper(context);
 
        try {
	        	myDbHelper.createDataBase();
	        	
	 	} catch (IOException ioe) {
	 
	 		throw new Error("Unable to create database");
	 	}
        
        
	 	try {
	 		APsDataBase = new ArrayList<AccessPoint>();
	 		myDbHelper.openDataBase();
	 		APsDataBase =  myDbHelper.getAPNodes();	 		
	 	}
	 	
		catch(SQLException sqle){
	 		throw sqle;	 
	 	}
	}
	
	//Function to get Information about access point
	public void ListInfoWifi() {		
		if(APsDataBase == null)
		{
			getDataBaseAPs();
		}
		
		APsScaned = new ArrayList<AccessPoint>();
		
		for (int i = 0; i < wifiList.size(); i++) {
			if (wifiList.get(i).SSID.equalsIgnoreCase("ITI_Students") || wifiList.get(i).SSID.equalsIgnoreCase("ITI_ALEX")
					|| wifiList.get(i).SSID.equalsIgnoreCase("MSM")) {
				for(int j=0 ; j<APsDataBase.size() ; j++)
				{
					if (wifiList.get(i).BSSID.equalsIgnoreCase( APsDataBase.get(j).getMac_Address() ))
					{
						APsDataBase.get(j).setlevel(wifiList.get(i).level);
						APsDataBase.get(j).setDistance(APsDataBase.get(j).Calculate_Distance());
						APsScaned.add(APsDataBase.get(j));
					}	
				}
			}
		}
	}
}
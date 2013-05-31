package iti.gp.navigation;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;



public class MessageHandler {
	
	//Message Box To Handler Error 
		public static void messageBox(String str, String str2,Context context) {
			AlertDialog.Builder dlgAlert = new AlertDialog.Builder(context);
			dlgAlert.setTitle(str);
			dlgAlert.setMessage(str2);
			dlgAlert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int whichButton) {
				}
			});
			dlgAlert.setCancelable(true);
			dlgAlert.create().show();
		}
}

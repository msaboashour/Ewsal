package iti.gp.navigation;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.widget.ProgressBar;

public class StartApp extends Activity {

	ProgressBar progressBar;
	private int progressBarStatus = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start_app);
		
		progressBar = (ProgressBar)findViewById(R.id.progressBar1);
		
		Thread timer = new Thread(){
			public void run(){
				try{
					sleep(5000);						            
				}catch (InterruptedException e) {
					e.printStackTrace();
				}finally{
					//Intent in = new Intent(myContext, Relative.class)
					Intent mainActivity = new Intent(StartApp.this, MainActivity.class);
					startActivity(mainActivity);
				}
			}
		};
		timer.start();
		
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		finish();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}

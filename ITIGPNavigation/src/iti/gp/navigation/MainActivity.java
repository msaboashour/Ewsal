package iti.gp.navigation;


import java.util.Timer;
import java.util.TimerTask;


import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends Base {

	ImageView img;
	Context MyContext ;
	TextView txt1;
	Timer myTimer;	
	Position position;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		MyContext = this;
 		
		img = (ImageView) findViewById(R.id.imgFloor11);			
		position = new Position(MyContext);	
		txt1 = (TextView) findViewById(R.id.textView1);
			
		myTimer = new Timer();
		myTimer.schedule(new TimerTask() {
			@Override
			public void run() {
				TimerMethod();
				}
			}, 0, 1000);		
	}
		
	private void TimerMethod() {
		this.runOnUiThread(Timer_Tick);
	}

	private Runnable Timer_Tick = new Runnable() {
		public void run() {		
			position.getCurrentPosition();		
			StringBuilder str = new StringBuilder();
			
			for(int i=0 ; i< position.myHelper.APsScaned.size(); i++)
			{
				str.append( "mac: " + position.myHelper.APsScaned.get(i).getMac_Address() + 
							"   Distance:"+ position.myHelper.APsScaned.get(i).getDistance() +
							"   Level: "+position.myHelper.APsScaned.get(i).getlevel()+"\n" );
			}
			str.append( "x:" + position.getxPos() + "    y:"+position.getyPos() +"  " );
			txt1.setText(str.toString());			
			
			SetPosition( (float) position.getxPos(), (float) position.getyPos());			
		}
	};
	
	void SetPosition(float xCord, float yCord)
	{
		Bitmap imgFloor = BitmapFactory.decodeResource( MainActivity.this.getResources(), R.drawable.map_75);
		
		float x =  convertPixelsToDp(  xCord, MyContext);
		float y =  convertPixelsToDp(  yCord, MyContext);
		
		Canvas canvas = new Canvas(imgFloor);
		Paint paint = new Paint();
		paint.setColor(Color.RED);
		canvas.drawCircle(x, y, 20, paint);
		img.setImageBitmap(imgFloor);
	}	
	
	/**
	 * This method converts device specific pixels to density independent pixels.
	 * 
	 * @param px A value in px (pixels) unit. Which we need to convert into db
	 * @param context Context to get resources and device specific display metrics
	 * @return A float value to represent dp equivalent to px value
	 */
	
	public static float convertPixelsToDp(float px, Context context){
	    Resources resources = context.getResources();
	    DisplayMetrics metrics = resources.getDisplayMetrics();
	    float dp = px / (metrics.densityDpi / 160f);
	    return dp;
	}

}

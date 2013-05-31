package iti.gp.navigation;

import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;

public class Base extends Activity {


	 @Override
	    public boolean onCreateOptionsMenu(Menu menu)
	    {
		 menu.add(1, 1, 0, "Navigation").setIcon(R.drawable.ic_launcher);
	     menu.add(1, 2, 1, "Map").setIcon(R.drawable.map);
	     menu.add(1, 3, 2, "About us").setIcon(R.drawable.inf);	    
	     return true;		 
	    }

	 
	 @Override
	    public boolean onOptionsItemSelected(MenuItem item)
	    {
	     
		 switch(item.getItemId())
	     {
	     case 1:
	    	 if(this.getClass().getName() != Navigation.class.getName())
	    	 {
		    	 Intent navInt = new Intent(this, Navigation.class);
		    	 startActivity(navInt);
		    	 finish();
	    	 }
	      return true;
	     case 2:
	    	 if(this.getClass().getName() !=  AbstractMapActivity.class.getName())
	    	 {
		    	 Intent mapInt = new Intent(this, AbstractMapActivity.class);
		    	 startActivity(mapInt);
		    	 finish();
	    	 }
	      return true;
	     case 3:
	    	 String Title = "About The Project";
	    	 String Message = "It gives turn-by-turn directions to reach a certain location inside certain organization (ITI) and providing the user with a dynamic leading tool in indoor locations which facilitates reaching their goals in the fastest way";
	    	 MessageHandler.messageBox(Title, Message, this);
	      return true;

	     }
	     return super.onOptionsItemSelected(item);

	    }

	
}

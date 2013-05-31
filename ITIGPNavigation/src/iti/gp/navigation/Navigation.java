package iti.gp.navigation;

import iti.gp.navigation.db.DBHelper;
import iti.gp.navigation.db.Node;

import java.util.ArrayList;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class Navigation extends Base {

	DBHelper myDbHelper;
	Node node;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_navigation);

		myDbHelper = new DBHelper(this);

		try {
			myDbHelper.openDataBase();
			spinner();
		} catch (Exception e) {

		}
		
		
		
		
	}

	public void spinner() {
		final Spinner countriesSpiner = (Spinner) findViewById(R.id.spinner1);
		ArrayList<Node> other_node = new ArrayList<Node>();
		// myDbHelper = new DBHelper(this);

		other_node = myDbHelper.GetLocationNodes();
		ArrayAdapter<Node> adapter = new ArrayAdapter<Node>(this,
				android.R.layout.simple_spinner_dropdown_item, other_node);
		countriesSpiner.setAdapter(adapter);
		countriesSpiner
				.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
					public void onItemSelected(AdapterView<?> adapterView,
							View view, int i, long l) {
							node = (Node) countriesSpiner.getSelectedItem();
					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {
						// TODO Auto-generated method stub
					}
				});
	}

}

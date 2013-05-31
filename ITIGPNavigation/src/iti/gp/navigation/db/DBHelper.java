package iti.gp.navigation.db;


import iti.gp.navigation.AccessPoint;
import iti.gp.navigation.algorithm.Edge1;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

	// The Android's default system path of your application database.
	private static String DB_PATH = "/data/data/iti.gp.navigation/databases/";
	private static String DB_NAME = "IndoorDB";

	private static final String DATABASE_TABLE_Node = "NodeTable";

	public static final String KEY_NodeID = "_id";
	public static final String KEY_NodeName = "NodeName";
	public static final String KEY_NodexCord = "NodeXcord";
	public static final String KEY_NodeyCord = "NodeYcord";
	public static final String KEY_NodeType = "NodeType";
	public static final String KEY_NodeInfo = "NodeInfo";
	
	private static final String DATABASE_TABLE_Edge = "Edge";
	public static final String KEY_EdgeID = "_id";
	public static final String Start = "StartID";
	public static final String End = "EndID";
	public static final String Cost = "Cost";
	

	public enum NodeTyp {
		 AP(1), Location(2);
		private int id;

		NodeTyp(int id) {
			this.id = id;
		}

		static NodeTyp fromValue(int value) {
			for (NodeTyp my : NodeTyp.values()) {
				if (my.id == value) {
					return my;
				}
			}
			return null;
		}

		public int getValue() {
			return id;
		}
	}

	private SQLiteDatabase ourDataBase;
	private final Context myContext;

	/**
	 * Constructor Takes and keeps a reference of the passed context in order to
	 * access to the application assets and resources.
	 * 
	 * @param context
	 */
	public DBHelper(Context context) {
		super(context, DB_NAME, null, 1);
		this.myContext = context;
	}

	/**
	 * Creates a empty database on the system and rewrites it with your own
	 * database.
	 * */
	public void createDataBase() throws IOException {

		boolean dbExist = checkDataBase();
		if (dbExist) {
			myContext.deleteDatabase(DB_NAME);
			copyDataBase();
		} else {
			// By calling this method and empty database will be created into
			// the default system path
			// of your application so we are gonna be able to overwrite that
			// database with our database.
			this.getReadableDatabase();
			try {

				copyDataBase();

			} catch (IOException e) {
				throw new Error("Error copying database");
			}
		}
	}

	/**
	 * Check if the database already exist to avoid re-copying the file each
	 * time you open the application.
	 * 
	 * @return true if it exists, false if it doesn't
	 */
	private boolean checkDataBase() {
		SQLiteDatabase checkDB = null;
		try {
			String myPath = DB_PATH + DB_NAME;
			checkDB = SQLiteDatabase.openDatabase(myPath, null,
					SQLiteDatabase.OPEN_READONLY);
		} catch (SQLiteException e) {
			// database does't exist yet.
		}
		if (checkDB != null) {
			checkDB.close();
		}

		return checkDB != null ? true : false;
	}

	/**
	 * Copies your database from your local assets-folder to the just created
	 * empty database in the system folder, from where it can be accessed and
	 * handled. This is done by transfering bytestream. *
	 */

	private void copyDataBase() throws IOException {

		// Open your local db as the input stream
		InputStream myInput = myContext.getAssets().open(DB_NAME);

		// Path to the just created empty db
		String outFileName = DB_PATH + DB_NAME;

		// Open the empty db as the output stream
		OutputStream myOutput = new FileOutputStream(outFileName);

		// transfer bytes from the inputfile to the outputfile
		byte[] buffer = new byte[1024];
		int length;
		while ((length = myInput.read(buffer)) > 0) {
			myOutput.write(buffer, 0, length);
		}

		// Close the streams
		myOutput.flush();
		myOutput.close();
		myInput.close();

	}

	public void openDataBase() throws SQLException {
		// Open the database
		String myPath = DB_PATH + DB_NAME;
		ourDataBase = SQLiteDatabase.openDatabase(myPath, null,
				SQLiteDatabase.OPEN_READONLY);

	}

	@Override
	public synchronized void close() {
		if (ourDataBase != null)
			ourDataBase.close();
		super.close();
	}

	@Override
	public void onCreate(SQLiteDatabase db) {

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

	// Add your public helper methods to access and get content from the
	// database.
	// You could return cursors by doing "return ourDataBase.query(....)" so
	// it'd be easy
	// to you to create adapters for your views.	
	public ArrayList<Node> getAllNodes() {
		String[] columns = new String[] { KEY_NodeID, KEY_NodeName,
				KEY_NodexCord, KEY_NodeyCord, KEY_NodeType, KEY_NodeInfo };

		Cursor c = ourDataBase.query(DATABASE_TABLE_Node, columns, null, null,
				null, null, null);
		
		ArrayList<Node> nods = new ArrayList<Node>();

		int iRow = c.getColumnIndex(KEY_NodeID);
		int iName = c.getColumnIndex(KEY_NodeName);
		int iXcord = c.getColumnIndex(KEY_NodexCord);
		int iYcord = c.getColumnIndex(KEY_NodeyCord);
		int iTyp = c.getColumnIndex(KEY_NodeType);
		int iInfo = c.getColumnIndex(KEY_NodeInfo);

		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
			Node n = new Node();
			n.set_id(Integer.parseInt(c.getString(iRow)));
			n.setNodeName(c.getString(iName));
			n.setNodeXcord(Float.parseFloat(c.getString(iXcord)));
			n.setNodeYcord(Float.parseFloat(c.getString(iYcord)));
			n.setNodeTyp(NodeTyp.fromValue(Integer.parseInt(c.getString(iTyp))));
			n.setNodeInfo(c.getString(iInfo));
			nods.add(n);
		}

		return nods;
	}
/*
	public ArrayList<AccessPoint> getAPNodes(ArrayList<AccessPoint> APsList) {
		String[] columns = new String[] { KEY_NodeID, KEY_NodeName,
				KEY_NodexCord, KEY_NodeyCord, KEY_NodeType, KEY_NodeInfo };

		StringBuilder whereST = new StringBuilder();
		String[] whereArgs = new String[APsList.size()]; // The value of the column specified above for the rows to be 
													  // included in the response

		for (int i = 0; i < APsList.size(); i++) {
			whereST.append(KEY_NodeInfo + "=?");
			whereArgs[i] = APsList.get(i).getMac_Address();
			if (i + 1 < APsList.size())
				whereST.append(" OR ");
		}
		;

		String where = whereST.toString();

		Cursor c = ourDataBase.query(DATABASE_TABLE_Node, columns, where,
				whereArgs, null, null, null);

		ArrayList<AccessPoint> nods = new ArrayList<AccessPoint>();
		int iRow = c.getColumnIndex(KEY_NodeID);
		int iName = c.getColumnIndex(KEY_NodeName);
		int iXcord = c.getColumnIndex(KEY_NodexCord);
		int iYcord = c.getColumnIndex(KEY_NodeyCord);
		int iTyp = c.getColumnIndex(KEY_NodeType);
		int iInfo = c.getColumnIndex(KEY_NodeInfo);

		
		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {			
			Node n = new Node();
			n.set_id(Integer.parseInt(c.getString(iRow)));
			n.setNodeName(c.getString(iName));
			n.setNodeXcord(Float.parseFloat(c.getString(iXcord)));
			n.setNodeYcord(Float.parseFloat(c.getString(iYcord)));
			n.setNodeTyp(NodeTyp.fromValue(Integer.parseInt(c.getString(iTyp))));
			n.setNodeInfo(c.getString(iInfo));
			nods.add(n);
			 
		}
		return nods;
	}
*/	
	
	public ArrayList<Node> GetLocationNodes() {
		String[] columns = new String[] { KEY_NodeID, KEY_NodeName,
				KEY_NodexCord, KEY_NodeyCord, KEY_NodeType, KEY_NodeInfo };

		String where = KEY_NodeType+"=?";
		String[] whereArgs = new String[1];
		whereArgs[0] = Integer.toString( NodeTyp.Location.getValue() );
		
		Cursor c = ourDataBase.query(DATABASE_TABLE_Node, columns, where,
				whereArgs, null, null, null);

		ArrayList<Node> OtherNodes = new ArrayList<Node>();
		int iRow = c.getColumnIndex(KEY_NodeID);
		int iName = c.getColumnIndex(KEY_NodeName);
		int iXcord = c.getColumnIndex(KEY_NodexCord);
		int iYcord = c.getColumnIndex(KEY_NodeyCord);
		int iInfo = c.getColumnIndex(KEY_NodeInfo);
		
		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
			
			Node _node = new Node();
			_node.set_id(Integer.parseInt(c.getString(iRow)));
			_node.setNodeName(c.getString(iName));
			_node.setNodeXcord(Float.parseFloat(c.getString(iXcord)));
			_node.setNodeYcord(Float.parseFloat(c.getString(iYcord)));
			_node.setNodeInfo(c.getString(iInfo));
			_node.setNodeTyp(NodeTyp.Location);
			OtherNodes.add(_node);
		}

		return OtherNodes;
	}
	
	
	public ArrayList<AccessPoint> getAPNodes() {
		String[] columns = new String[] { KEY_NodeID, KEY_NodeName,
				KEY_NodexCord, KEY_NodeyCord, KEY_NodeType, KEY_NodeInfo };

		String where = KEY_NodeType+"=?";
		String[] whereArgs = new String[1];
		whereArgs[0] = Integer.toString( NodeTyp.AP.getValue() );
		
		Cursor c = ourDataBase.query(DATABASE_TABLE_Node, columns, where,
				whereArgs, null, null, null);

		ArrayList<AccessPoint> APsList = new ArrayList<AccessPoint>();
		int iRow = c.getColumnIndex(KEY_NodeID);
		int iName = c.getColumnIndex(KEY_NodeName);
		int iXcord = c.getColumnIndex(KEY_NodexCord);
		int iYcord = c.getColumnIndex(KEY_NodeyCord);
		int iInfo = c.getColumnIndex(KEY_NodeInfo);
		
		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
			
			AccessPoint ap = new AccessPoint();
			ap.setId(Integer.parseInt(c.getString(iRow)));
			ap.setName(c.getString(iName));
			ap.setxCordinate(Float.parseFloat(c.getString(iXcord)));
			ap.setyCordinate(Float.parseFloat(c.getString(iYcord)));			
			ap.setMac_Address(c.getString(iInfo));
			APsList.add(ap);
		}

		return APsList;
	}
	
	public ArrayList<Edge1> getAllEdges() {
		
		String[] columns = new String[] {KEY_EdgeID,Start,End,Cost};
		Cursor c = ourDataBase.query(DATABASE_TABLE_Edge, columns, null, null,
				null, null, null);

		ArrayList<Edge1> edg = new ArrayList<Edge1>();
		int I = c.getColumnIndex(KEY_EdgeID);
		int S = c.getColumnIndex(Start);
		int E = c.getColumnIndex(End);
		int C = c.getColumnIndex(Cost);
		
		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
			Edge1 n = new Edge1();
			n.set_id(Integer.parseInt(c.getString(I)));
			n.set_startid(Integer.parseInt(c.getString(S)));
			n.setEndid(Integer.parseInt(c.getString(E)));
			n.set_Cost(Integer.parseInt(c.getString(C)));		
			edg.add(n);
		}

		return edg;
	}
	
	public ArrayList<Edge1> fetch(String startnode)
	   {String[] columns = new String[] {KEY_EdgeID,Start,End,Cost};
		   Cursor c1=  ourDataBase.query(DATABASE_TABLE_Edge, columns, "StartID="+startnode, null,null, null, null);
		   ArrayList<Edge1> es = new ArrayList<Edge1>();   
		   int id1 = c1.getColumnIndex(KEY_EdgeID);
		   int istart = c1.getColumnIndex(Start);
		   int iend = c1.getColumnIndex(End);
		   int icost = c1.getColumnIndex(Cost);
		   for (c1.moveToFirst(); !c1.isAfterLast(); c1.moveToNext()) {
				Edge1 n = new Edge1();
				n.set_id(Integer.parseInt(c1.getString(id1)));
				n.set_startid(Integer.parseInt(c1.getString(istart)));
				n.setEndid(Integer.parseInt(c1.getString(iend)));
				n.set_Cost(Integer.parseInt(c1.getString(icost)));
				 es.add(n);
		   }
		  return es;
	   
	   }
	
}
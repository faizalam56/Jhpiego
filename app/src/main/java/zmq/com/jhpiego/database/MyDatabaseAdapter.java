package zmq.com.jhpiego.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class MyDatabaseAdapter {

	public static String DATABASE_NAME 		= 	"analytics";
	public static int DATABASE_VERSION 		= 	1;
	
	MyHelper myHelper;
	SQLiteDatabase sqLiteDatabase;
	Context context;
	
	public MyDatabaseAdapter(Context c) {
		context = c;
	}
	
	public MyDatabaseAdapter openToRead() {
		
		myHelper = new MyHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
		sqLiteDatabase = myHelper.getReadableDatabase();	
		return this;	
	}
	public MyDatabaseAdapter openToWrite() {
		myHelper = new MyHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
		sqLiteDatabase = myHelper.getWritableDatabase();
		return this;		
	}
	
	public void close() {
		
		if(myHelper != null){
			myHelper.close();
		}
	}
	

	
	//////////////////////COMMAN_FUNCTION//////////////////////////////
	
	public long insertData(String tableName, ContentValues contentValues) {
		// TODO Auto-generated method stub
		long index = sqLiteDatabase.insert(tableName, null, contentValues);
		return index;
	}
	
	public Cursor selectData(String tableName, String[] columns, String whereClause, String[] matchValue, String orderBy, String limit){
		
		Cursor c = sqLiteDatabase.query(tableName, columns, whereClause, matchValue, null, orderBy, limit);
		return c;
		
	}
	
	public int deleteData(String tableName, String whereClouse){
		int rowAffected = sqLiteDatabase.delete(tableName, whereClouse, null);		
		System.out.println("All rows Deleted Successfully");
		return rowAffected;		
	}
	
	public int updateData(String tableName, String whereClause, ContentValues values, String[] strings){
		int rowAffected = sqLiteDatabase.update(tableName, values, whereClause, strings);//(tableName, whereClouse, null);		
		System.out.println("All rows Updated Successfully");
		return rowAffected;		
	}
	
	public Cursor selectSpecificData(String tableName, String[] columns, String whereClause){
		
		Cursor c = sqLiteDatabase.query(tableName, columns, null, null, null, null, null);
		return c;
		
	}

	public boolean dropTable(String tableName){

		String drop= "DROP TABLE IF EXISTS "+tableName;
		try {
			sqLiteDatabase.execSQL(drop);
			sqLiteDatabase.execSQL(MyDatabaseAdapter.SCRIPT_CREATE_TABLE_1);
			return true;
		}catch (Exception e){
			return false;
		}
	}

	public Cursor getLastRecord(){

		String selectQuery= "SELECT * FROM "+DATABASE_TABLE_1+" ORDER BY _id DESC LIMIT 1";

		Cursor cursor = sqLiteDatabase.rawQuery(selectQuery, null);
		return cursor;
	}
	
	//////////////////// TABLE => PATIENT_REPORT ///////////////////////////
		
	public static final String DATABASE_TABLE_1 = "Player_Report";

    public static final String KEY_ID = "_id";
	public static final String KEY_CONTENT_1 = "date";
	public static final String KEY_CONTENT_2 = "lounchTime";
	public static final String KEY_CONTENT_3 = "sessions";
	public static final String KEY_CONTENT_4 = "startTime";
	public static final String KEY_CONTENT_5 = "gender";
	public static final String KEY_CONTENT_6 = "endTime";
	public static final String KEY_CONTENT_7 = "relation";
	public static final String KEY_CONTENT_8 = "risk";
	public static final String KEY_CONTENT_9 = "impulse";
	public static final String KEY_CONTENT_10 = "knoledge";
	public static final String KEY_CONTENT_11 = "bonus";
	public static final String KEY_CONTENT_12 = "path";

	public static final String KEY_CONTENT_13 = "decision_1";
	public static final String KEY_CONTENT_14 = "decision_2";
	public static final String KEY_CONTENT_15 = "decision_3";
	public static final String KEY_CONTENT_16 = "decision_4";
	public static final String KEY_CONTENT_17 = "decision_5";
	public static final String KEY_CONTENT_18 = "decision_6";
	public static final String KEY_CONTENT_19 = "decision_7";
	public static final String KEY_CONTENT_20 = "decision_8";
	public static final String KEY_CONTENT_21 = "decision_9";
	public static final String KEY_CONTENT_22 = "decision_10";
	public static final String KEY_CONTENT_23 = "decision_11";
	public static final String KEY_CONTENT_24 = "decision_12";
	public static final String KEY_CONTENT_25 = "decision_13";
	public static final String KEY_CONTENT_26 = "decision_14";
	public static final String KEY_CONTENT_27 = "decision_15";


	
	public static final String SCRIPT_CREATE_TABLE_1 =
			
			"CREATE TABLE IF NOT EXISTS " + DATABASE_TABLE_1 + "("
					+ KEY_ID + " integer primary key autoincrement, "
					+ KEY_CONTENT_1 + " TEXT NOT NULL, "
					+ KEY_CONTENT_2 + " TEXT NOT NULL, "
					+ KEY_CONTENT_3 + " TEXT NOT NULL, "
					+ KEY_CONTENT_4 + " TEXT NOT NULL, "
					+ KEY_CONTENT_5 + " TEXT NOT NULL, "
					+ KEY_CONTENT_6 + " TEXT NOT NULL, "
					+ KEY_CONTENT_7 + " TEXT NOT NULL, "
					+ KEY_CONTENT_8 + " TEXT NOT NULL, "
					+ KEY_CONTENT_9 + " TEXT NOT NULL, "
					+ KEY_CONTENT_10 + " TEXT NOT NULL, "
					+ KEY_CONTENT_11 + " TEXT NOT NULL, "
					+ KEY_CONTENT_12 + " TEXT NOT NULL, "
					+ KEY_CONTENT_13 + " TEXT NOT NULL, "
					+ KEY_CONTENT_14 + " TEXT NOT NULL, "
					+ KEY_CONTENT_15 + " TEXT NOT NULL, "
					+ KEY_CONTENT_16 + " TEXT NOT NULL, "
					+ KEY_CONTENT_17 + " TEXT NOT NULL, "
					+ KEY_CONTENT_18 + " TEXT NOT NULL, "
					+ KEY_CONTENT_19 + " TEXT NOT NULL, "
					+ KEY_CONTENT_20 + " TEXT NOT NULL, "
					+ KEY_CONTENT_21 + " TEXT NOT NULL, "
					+ KEY_CONTENT_22 + " TEXT NOT NULL, "
					+ KEY_CONTENT_23 + " TEXT NOT NULL, "
					+ KEY_CONTENT_24 + " TEXT NOT NULL, "
					+ KEY_CONTENT_25 + " TEXT NOT NULL, "
					+ KEY_CONTENT_26 + " TEXT NOT NULL, "
					+ KEY_CONTENT_27 + " TEXT NOT NULL);";

	
}

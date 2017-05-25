package zmq.com.jhpiego.preferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import java.sql.Types;

public class MyPreference {
	
	private static String fileName = "temp_data";
	private static SharedPreferences mPreferences;
	
	public static void saveStringKeyValue(Context context,  String key, String value) {

		mPreferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
		Editor editor = mPreferences.edit();
		editor.putString(key, value);
		editor.commit();
	}
	
	public static String getStringValue(Context context,  String key){

		if(key.equals("sessionsId")){

			mPreferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
			String value = mPreferences.getString(key, "0");
			int i = Integer.parseInt(value);
			i++;
			return ""+i;

		}else{

			mPreferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
			String value = mPreferences.getString(key, "ZMQ");
			return value;
		}

		
	}
	
	public static void saveIntKeyValue(Context context, String key, int value) {

		mPreferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
		Editor editor = mPreferences.edit();
		editor.putInt(key, value);
		editor.commit();
	}
	
	public static int getIntValue(Context context, String key){

		mPreferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
		int value = mPreferences.getInt(key, 0);
		return value;
		
	}

	public static String getSession(Context context, String key){

		mPreferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
		String value = mPreferences.getString(key, "0");
		int i = Integer.parseInt(value);
		i++;
		return ""+i;

	}
	
	public static void removeKeys(Context context, int key) {


        String []keys = {"date","launchTime","sessionsId","startTime","gender","endTime","relation","risk","impulse","knowledge","bonus","path","relationGrand","riskGrand","impulseGrand","knowledgeGrand","D1","D2","D3","D4","D5","D6","D7","D8","D9","D10","D11","D12","D13","D14","D15"};

		mPreferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
		Editor editor = mPreferences.edit();

		for (String string : keys) {

	      if(key==0) {
			  if (mPreferences.contains(string)) {
				  editor.remove(string);
			  }
		  }else{
			  if (mPreferences.contains(string) && !((string.equals("date") || (string.equals("launchTime") || string.equals("gender") || string.equals("sessionsId"))))) {
				  editor.remove(string);
			  }
		  }
		}
		editor.commit();
	}
	
	
}

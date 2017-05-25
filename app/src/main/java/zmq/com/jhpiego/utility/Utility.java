package zmq.com.jhpiego.utility;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

import java.util.HashMap;

import zmq.com.jhpiego.database.MyDatabaseAdapter;
import zmq.com.jhpiego.preferences.MyPreference;


public class Utility {

    public static HashMap getMap(String []keys,int[]values){
        HashMap<String,Integer> map=new HashMap<String,Integer>();
        for(int i=0;i<keys.length;i++){
            map.put(keys[i],values[i]);
        }
        return  map;
    }

    public static void saveDataInDB(Context context) {

        ContentValues values = new ContentValues();


        values.put(MyDatabaseAdapter.KEY_CONTENT_1, MyPreference.getStringValue(context, "date"));
        values.put(MyDatabaseAdapter.KEY_CONTENT_2, MyPreference.getStringValue(context,"launchTime"));
        values.put(MyDatabaseAdapter.KEY_CONTENT_3, MyPreference.getStringValue(context,"sessionsId"));
        values.put(MyDatabaseAdapter.KEY_CONTENT_4, MyPreference.getStringValue(context,"startTime"));
        values.put(MyDatabaseAdapter.KEY_CONTENT_5, MyPreference.getStringValue(context,"gender"));
        values.put(MyDatabaseAdapter.KEY_CONTENT_6, MyPreference.getStringValue(context,"endTime"));

        values.put(MyDatabaseAdapter.KEY_CONTENT_7, "" + MyPreference.getIntValue(context, "relation")+"/"+MyPreference.getIntValue(context, "relationGrand"));
        values.put(MyDatabaseAdapter.KEY_CONTENT_8, "" + MyPreference.getIntValue(context, "risk")+"/"+MyPreference.getIntValue(context, "riskGrand"));
        values.put(MyDatabaseAdapter.KEY_CONTENT_9, "" + MyPreference.getIntValue(context, "impulse")+"/"+MyPreference.getIntValue(context, "impulseGrand"));
        values.put(MyDatabaseAdapter.KEY_CONTENT_10, "" + MyPreference.getIntValue(context, "knowledge")+"/"+MyPreference.getIntValue(context, "knowledgeGrand"));
        values.put(MyDatabaseAdapter.KEY_CONTENT_11, "" + MyPreference.getIntValue(context, "bonus"));
        values.put(MyDatabaseAdapter.KEY_CONTENT_12, MyPreference.getStringValue(context,"path"));

        values.put(MyDatabaseAdapter.KEY_CONTENT_13,  MyPreference.getStringValue(context,"D1"));
        values.put(MyDatabaseAdapter.KEY_CONTENT_14,  MyPreference.getStringValue(context,"D2"));
        values.put(MyDatabaseAdapter.KEY_CONTENT_15,  MyPreference.getStringValue(context,"D3"));
        values.put(MyDatabaseAdapter.KEY_CONTENT_16,  MyPreference.getStringValue(context,"D4"));
        values.put(MyDatabaseAdapter.KEY_CONTENT_17,  MyPreference.getStringValue(context,"D5"));
        values.put(MyDatabaseAdapter.KEY_CONTENT_18,  MyPreference.getStringValue(context,"D6"));
        values.put(MyDatabaseAdapter.KEY_CONTENT_19,  MyPreference.getStringValue(context,"D7"));
        values.put(MyDatabaseAdapter.KEY_CONTENT_20, MyPreference.getStringValue(context,"D8"));
        values.put(MyDatabaseAdapter.KEY_CONTENT_21, MyPreference.getStringValue(context,"D9"));
        values.put(MyDatabaseAdapter.KEY_CONTENT_22, MyPreference.getStringValue(context,"D10"));
        values.put(MyDatabaseAdapter.KEY_CONTENT_23, MyPreference.getStringValue(context,"D11"));
        values.put(MyDatabaseAdapter.KEY_CONTENT_24, MyPreference.getStringValue(context,"D12"));
        values.put(MyDatabaseAdapter.KEY_CONTENT_25, MyPreference.getStringValue(context,"D13" ));
        values.put(MyDatabaseAdapter.KEY_CONTENT_26, MyPreference.getStringValue(context,"D14" ));
        values.put(MyDatabaseAdapter.KEY_CONTENT_27, MyPreference.getStringValue(context,"D15"));


        MyDatabaseAdapter adapter = new MyDatabaseAdapter(context);
        adapter.openToWrite();
        long rowId = adapter.insertData(MyDatabaseAdapter.DATABASE_TABLE_1, values);

        adapter.close();
    }

    public static Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight) {
	    int width = bm.getWidth();
	    int height = bm.getHeight();
	    float scaleWidth = ((float) newWidth) / width;
	    float scaleHeight = ((float) newHeight) / height;
	    // CREATE A MATRIX FOR THE MANIPULATION
	    Matrix matrix = new Matrix();
	    // RESIZE THE BIT MAP
	    matrix.postScale(scaleWidth, scaleHeight);

	    // "RECREATE" THE NEW BITMAP
	    Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false);
	    return resizedBitmap;
	}
//	public static Bitmap createImage(int resId){
//		  Bitmap btmp= Constant.decodeSampledBitmapFromResource(GlobalVariables.getResource, resId);
//		  return btmp;
//	  }
    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId ) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);
        int imageHeight = (int)(options.outHeight* GlobalVariables.yScale_factor);//scaled height   by shah
        int imageWidth = (int)(options.outWidth * GlobalVariables.xScale_factor);// scaled width    by shah
        // Calculate inSampleSize
//        options.inSampleSize = calculateInSampleSize(options, imageWidth, imageHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return Utility.getResizedBitmap(BitmapFactory.decodeResource(res, resId, options), imageWidth, imageHeight);
    }
    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }
}

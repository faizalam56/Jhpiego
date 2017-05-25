package zmq.com.jhpiego.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.PowerManager;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.google.android.gms.analytics.GoogleAnalytics;

import zmq.com.jhpiego.R;
import zmq.com.jhpiego.girlactivity.G_AfterMath;
import zmq.com.jhpiego.girlactivity.G_Scene4_5_Activity;
import zmq.com.jhpiego.girlactivity.G_Story1;
import zmq.com.jhpiego.girlactivity.G_Story2;
import zmq.com.jhpiego.girlactivity.G_Story2GetHotHeavy;
import zmq.com.jhpiego.girlactivity.G_Story2SubActivity;
import zmq.com.jhpiego.girlactivity.G_Story3;
import zmq.com.jhpiego.utility.GlobalVariables;
import zmq.com.jhpiego.utility.MySound;

public class Play extends Activity {

    Intent replayIntent;
    int replayIndex;
    PowerManager.WakeLock mWakeLock;
    Button yes,no;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_play);
        yes=(Button)findViewById(R.id.yes_button);
        no=(Button)findViewById(R.id.no_button);

        final PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        this.mWakeLock = pm.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK, "Play");
        this.mWakeLock.acquire();

        printSecreenInfo();

        GlobalVariables.getResource=getResources();

        int scaledSize = GlobalVariables.getResource.getDimensionPixelSize(R.dimen.text_size);
        GlobalVariables. paint= new Paint();
        GlobalVariables. paint.setTextSize(scaledSize);
        GlobalVariables. paint=GlobalVariables.paint;
        GlobalVariables. paint.setColor(Color.WHITE);
        GlobalVariables.initialize();

        Intent intent =new Intent();
        //for male female char selection
//        intent.setClass(Play.this,Story1.class);
//        startActivityForResult(intent,1);
//        replayIntent=intent;
//        replayIndex=1;

        intent.setClass(Play.this,PlayerSelectionActivity.class);
        startActivityForResult(intent,0);
        replayIntent=intent;
        replayIndex=0;


//        intent.setClass(Play.this,G_Story1.class);
//        startActivityForResult(intent,1);
//        replayIntent=intent;
//        replayIndex=1;

//        intent.setClass(Play.this,G_Story2.class);
//        startActivityForResult(intent,2);
//        replayIntent=intent;
//        replayIndex=2;

//        intent.setClass(Play.this,G_Story2SubActivity.class);
//        GlobalVariables.SWITCH_SCENE=300;
//        startActivityForResult(intent,2);
//        replayIntent=intent;
//        replayIndex=2;

//        intent.setClass(Play.this,G_Scene4_5_Activity.class);
//        startActivityForResult(intent,2);
//        replayIntent=intent;
//        replayIndex=2;

//        intent.setClass(Play.this,G_AfterMath.class);
//        startActivityForResult(intent,2);
//        replayIntent=intent;
//        replayIndex=2;

//        intent.setClass(Play.this,G_Story2GetHotHeavy.class);
//        startActivityForResult(intent,2);
//        replayIntent=intent;
//        replayIndex=2;

//        intent.setClass(Play.this,Story2.class);
//        startActivityForResult(intent,2);
//        replayIntent=intent;
//        replayIndex=2;

//        intent.setClass(Play.this,GameB.class);
//        startActivityForResult(intent,2);
//        replayIntent=intent;
//        replayIndex=2;

//        intent.setClass(Play.this,Story3.class);
//        startActivityForResult(intent,3);
//        replayIntent=intent;
//        replayIndex=3;

//        intent.setClass(Play.this,Story2GetHotHeavy.class);
//        startActivityForResult(intent,2);
//        replayIntent=intent;
//        replayIndex=2;

//        intent.setClass(Play.this,Scene4_5_Activity.class);
//        startActivityForResult(intent,2);
//        replayIntent=intent;
//        replayIndex=2;

//        intent.setClass(Play.this,Story2SubActivity.class);
//        GlobalVariables.SWITCH_SCENE=200;
//        startActivityForResult(intent,2);
//        replayIntent=intent;
//        replayIndex=2;

//        intent.setClass(Play.this,AfterMath.class);
//        startActivityForResult(intent,2);
//        replayIntent=intent;
//        replayIndex=2;
//        setContentView(new MySurfaceView(Play.this));

      yes.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
                finish();
                MySound.stopSound(Play.this,GlobalVariables.Audio_File_Name);
                Intent intent =new Intent();
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("EXIT",true);
                intent.setClass(Play.this,MenuScreen.class);
                startActivity(intent);
          }
      });

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(replayIntent, replayIndex);
            }
        });
    }
    @Override
    public void onBackPressed() {
        // do nothing.
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

      if(GlobalVariables.selectedPlayer==999) {
          switch (requestCode) {
              case 0:
                  if (resultCode == RESULT_OK) {
                        Intent intent = new Intent();
                        intent.setClass(Play.this,Story1.class);
                        startActivityForResult(intent,1);
                        replayIntent=intent;
                        replayIndex=1;
                  }
                  break;

              case 1:
                  if (resultCode == RESULT_OK) {

                      Intent intent = new Intent();
                      intent.setClass(Play.this, Story2.class);
                      intent.putExtra("maleX", data.getIntExtra("maleX", 0));
                      intent.putExtra("maleY", data.getIntExtra("maleY", 0));
                      intent.putExtra("femaleX", data.getIntExtra("femaleX", 0));
                      intent.putExtra("femaleY", data.getIntExtra("femaleY", 0));
                      startActivityForResult(intent, 2);
                      replayIntent = intent;
                      replayIndex = 2;
                  } else if (resultCode == 10) {
                      Intent intent = new Intent();
                      intent.setClass(Play.this, Story2.class);
                      startActivityForResult(intent, 2);
                      replayIntent = intent;
                      replayIndex = 2;
                  } else if (resultCode == 123) {
                      MySound.stopSound(Play.this, GlobalVariables.Audio_File_Name);
                      Intent intent = new Intent();
                      intent.setClass(this, PathActivity.class);
                      startActivity(intent);

                      replayIntent = intent;

                  } else if (resultCode == 302) {// For Home Button which is not working yet.
                      //finish();
                      startActivity(replayIntent);

                  } else if (resultCode == 007) { //onBackPressed
                      //finish();
                      closeDialogBox();

                      //startActivityForResult(replayIntent,2);
                  } else {
                      System.out.println(" onActivityResult.........");
                      MySound.stopSound(Play.this, GlobalVariables.Audio_File_Name);
                      finish();

                  }
                  break;

              case 2:
                  if (resultCode == RESULT_OK) {
                      Intent intent = new Intent();
                      intent.setClass(Play.this, Story3.class);
//                    int temMaleX=data.getIntExtra("maleX",0);
                      intent.putExtra("maleX", data.getIntExtra("maleX", 0));
                      intent.putExtra("maleY", data.getIntExtra("maleY", 0));
                      startActivityForResult(intent, 3);
                      replayIntent = intent;
                      replayIndex = 3;
                  } else if (resultCode == GlobalVariables.REPLAY) {
                      Intent intent = new Intent();
                      intent.setClass(Play.this, Story2.class);
                      startActivityForResult(intent, 2);
                      replayIntent = intent;
                      replayIndex = 2;
                  } else if (resultCode == 100 || resultCode == 200 || resultCode == 300) {
                      Intent intent = new Intent();
                      intent.setClass(Play.this, Story2SubActivity.class);
                      startActivityForResult(intent, 2);
                      replayIntent = intent;
                      replayIndex = 2;
                  } else if (resultCode == 5) {
                      Intent intent = new Intent();
                      intent.setClass(Play.this, Story2GetHotHeavy.class);
                      startActivityForResult(intent, 2);
                      replayIntent = intent;
                      replayIndex = 2;
                  } else if (resultCode == 30) {
                      Intent intent = new Intent();
                      intent.setClass(Play.this, Scene4_5_Activity.class);
                      startActivityForResult(intent, 2);
                      replayIntent = intent;
                      replayIndex = 2;
                  } else if (resultCode == 20 || resultCode == 25) {
                      Intent intent = new Intent();
                      intent.setClass(Play.this, Story2.class);
                      startActivityForResult(intent, 2);
                      replayIntent = intent;
                      replayIndex = 2;
                  } else if (resultCode == 500) {
                      Intent intent = new Intent();
                      intent.setClass(Play.this, AfterMath.class);
                      startActivityForResult(intent, 2);
                      replayIntent = intent;
                      replayIndex = 2;
                  } else if (resultCode == 10) {
                      Intent intent = new Intent();
                      intent.setClass(Play.this, Story2.class);
                      startActivityForResult(intent, 2);
                      replayIntent = intent;
                      replayIndex = 2;
                  } else if (resultCode == 1000) {
                      Intent intent = new Intent();
                      intent.setClass(Play.this, GameA.class);
                      startActivityForResult(intent, 2);
                      replayIntent = intent;
                      replayIndex = 2;
                  } else if (resultCode == 555) {
                      Intent intent = new Intent();
                      intent.setClass(Play.this, GameB.class);
                      startActivityForResult(intent, 2);
                      replayIntent = intent;
                      replayIndex = 2;
                  } else if (resultCode == 123) {
                      MySound.stopSound(Play.this, GlobalVariables.Audio_File_Name);
                      Intent intent = new Intent();
                      intent.setClass(this, PathActivity.class);
                      startActivity(intent);

                      replayIntent = intent;
                  } else if (resultCode == 007) { //onBackPressed
                      //finish();
                      closeDialogBox();
                      //startActivityForResult(replayIntent,2);
                  } else {
                      MySound.stopSound(Play.this, GlobalVariables.Audio_File_Name);
                      finish();

                  }
                  break;

              case 3:
                  if (resultCode == RESULT_OK) {
                      Intent intent = new Intent();
                      intent.setClass(Play.this, Story2.class);
                      startActivityForResult(intent, 2);
                      replayIntent = intent;

                  } else if (resultCode == 20 || resultCode == 25) {
                      Intent intent = new Intent();
                      intent.setClass(Play.this, Story2.class);
                      startActivityForResult(intent, 2);
                      replayIntent = intent;
                      replayIndex = 2;
                  } else if (resultCode == 555) {
                      Intent intent = new Intent();
                      intent.setClass(Play.this, GameB.class);
                      startActivityForResult(intent, 2);
                      replayIntent = intent;
                      replayIndex = 2;
                  } else if (resultCode == 123) {
                      MySound.stopSound(Play.this, GlobalVariables.Audio_File_Name);
                      Intent intent = new Intent();
                      intent.setClass(this, PathActivity.class);
                      startActivity(intent);

                      replayIntent = intent;
                  } else if (resultCode == 007) { //onBackPressed
                      //finish();
                      closeDialogBox();
                      //startActivityForResult(replayIntent,2);
                  } else {
                      MySound.stopSound(Play.this, GlobalVariables.Audio_File_Name);
                      finish();

                  }

                  break;

          }

      }




        else {//Female part controlling here........

          switch (requestCode) {
              case 0:
                  if (resultCode == RESULT_OK) {
                      Intent intent = new Intent();
                      intent.setClass(Play.this,G_Story1.class);
                      startActivityForResult(intent,1);
                      replayIntent=intent;
                      replayIndex=1;
                  }
                  break;

              case 1:
                  if (resultCode == RESULT_OK) {

                      Intent intent = new Intent();
                      intent.setClass(Play.this, G_Story2.class);
                      intent.putExtra("maleX", data.getIntExtra("maleX", 0));
                      intent.putExtra("maleY", data.getIntExtra("maleY", 0));
                      intent.putExtra("femaleX", data.getIntExtra("femaleX", 0));
                      intent.putExtra("femaleY", data.getIntExtra("femaleY", 0));
                      startActivityForResult(intent, 2);
                      replayIntent = intent;
                      replayIndex = 2;
                  } else if (resultCode == 10) {
                      Intent intent = new Intent();
                      intent.setClass(Play.this, G_Story2.class);
                      startActivityForResult(intent, 2);
                      replayIntent = intent;
                      replayIndex = 2;
                  } else if (resultCode == 123) {
                      MySound.stopSound(Play.this, GlobalVariables.Audio_File_Name);
                      Intent intent = new Intent();
                      intent.setClass(this, PathActivity.class);
                      startActivity(intent);

                      replayIntent = intent;

                  }
                  else if (resultCode == 666) {//On Incoming Call
                      MySound.stopSound(Play.this, GlobalVariables.Audio_File_Name);
                      MySound.playSound(Play.this, GlobalVariables.Audio_File_Name);
                      startActivityForResult(replayIntent, replayIndex);

                  }
                  else if (resultCode == 302) {// For Home Button which is not working yet.
                      //finish();
                      startActivity(replayIntent);

                  } else if (resultCode == 007) { //onBackPressed
                      //finish();
                      closeDialogBox();

                      //startActivityForResult(replayIntent,2);
                  } else {
                      System.out.println(" onActivityResult.........");
                      MySound.stopSound(Play.this, GlobalVariables.Audio_File_Name);
                      finish();

                  }
                  break;

              case 2:
                  if (resultCode == RESULT_OK) {
                      Intent intent = new Intent();
                      intent.setClass(Play.this, G_Story3.class);
//                    int temMaleX=data.getIntExtra("maleX",0);
                      intent.putExtra("maleX", data.getIntExtra("maleX", 0));
                      intent.putExtra("maleY", data.getIntExtra("maleY", 0));
                      startActivityForResult(intent, 3);
                      replayIntent = intent;
                      replayIndex = 3;
                  } else if (resultCode == GlobalVariables.REPLAY) {
                      Intent intent = new Intent();
                      intent.setClass(Play.this, G_Story2.class);
                      startActivityForResult(intent, 2);
                      replayIntent = intent;
                      replayIndex = 2;
                  } else if (resultCode == 100 || resultCode == 200 || resultCode == 300) {
                      Intent intent = new Intent();
                      intent.setClass(Play.this, G_Story2SubActivity.class);
                      startActivityForResult(intent, 2);
                      replayIntent = intent;
                      replayIndex = 2;
                  } else if (resultCode == 5) {
                      Intent intent = new Intent();
                      intent.setClass(Play.this, G_Story2GetHotHeavy.class);
                      startActivityForResult(intent, 2);
                      replayIntent = intent;
                      replayIndex = 2;
                  } else if (resultCode == 30) {
                      Intent intent = new Intent();
                      intent.setClass(Play.this, G_Scene4_5_Activity.class);
                      startActivityForResult(intent, 2);
                      replayIntent = intent;
                      replayIndex = 2;
                  } else if (resultCode == 20 || resultCode == 25) {
                      Intent intent = new Intent();
                      intent.setClass(Play.this, G_Story2.class);
                      startActivityForResult(intent, 2);
                      replayIntent = intent;
                      replayIndex = 2;
                  } else if (resultCode == 500) {
                      Intent intent = new Intent();
                      intent.setClass(Play.this, G_AfterMath.class);
                      startActivityForResult(intent, 2);
                      replayIntent = intent;
                      replayIndex = 2;
                  } else if (resultCode == 10) {
                      Intent intent = new Intent();
                      intent.setClass(Play.this, G_Story2.class);
                      startActivityForResult(intent, 2);
                      replayIntent = intent;
                      replayIndex = 2;
                  } else if (resultCode == 1000) {
                      Intent intent = new Intent();
                      intent.setClass(Play.this, GameA.class);
                      startActivityForResult(intent, 2);
                      replayIntent = intent;
                      replayIndex = 2;
                  } else if (resultCode == 555) {
                      Intent intent = new Intent();
                      intent.setClass(Play.this, GameB.class);
                      startActivityForResult(intent, 2);
                      replayIntent = intent;
                      replayIndex = 2;
                  } else if (resultCode == 123) {
                      MySound.stopSound(Play.this, GlobalVariables.Audio_File_Name);
                      Intent intent = new Intent();
                      intent.setClass(this, PathActivity.class);
                      startActivity(intent);

                      replayIntent = intent;
                  }
                  else if (resultCode == 666) {//On Incoming Call
                      MySound.stopSound(Play.this, GlobalVariables.Audio_File_Name);
                      startActivityForResult(replayIntent, replayIndex);

                  }
                  else if (resultCode == 666) {
                      MySound.stopSound(Play.this, GlobalVariables.Audio_File_Name);
                      MySound.playSound(Play.this, GlobalVariables.Audio_File_Name);
                      startActivityForResult(replayIntent, replayIndex);

                  }else if (resultCode == 007) { //onBackPressed
                      //finish();
                      closeDialogBox();
                      //startActivityForResult(replayIntent,2);
                  } else {
                      MySound.stopSound(Play.this, GlobalVariables.Audio_File_Name);
                      finish();

                  }
                  break;

              case 3:
                  if (resultCode == RESULT_OK) {
                      Intent intent = new Intent();
                      intent.setClass(Play.this, G_Story2.class);
                      startActivityForResult(intent, 2);
                      replayIntent = intent;

                  } else if (resultCode == 20 || resultCode == 25) {
                      Intent intent = new Intent();
                      intent.setClass(Play.this, G_Story2.class);
                      startActivityForResult(intent, 2);
                      replayIntent = intent;
                      replayIndex = 2;
                  } else if (resultCode == 555) {// Club Game
                      Intent intent = new Intent();
                      intent.setClass(Play.this, GameB.class);
                      startActivityForResult(intent, 2);
                      replayIntent = intent;
                      replayIndex = 2;
                  } else if (resultCode == 123) {
                      MySound.stopSound(Play.this, GlobalVariables.Audio_File_Name);
                      Intent intent = new Intent();
                      intent.setClass(this, PathActivity.class);
                      startActivity(intent);

                      replayIntent = intent;
                  }
                  else if (resultCode == 666) {//On Incoming Call
                      MySound.stopSound(Play.this, GlobalVariables.Audio_File_Name);
                      MySound.playSound(Play.this, GlobalVariables.Audio_File_Name);
                      startActivityForResult(replayIntent, replayIndex);

                  }
                  else if (resultCode == 007) { //onBackPressed
                      //finish();
                      closeDialogBox();
                      //startActivityForResult(replayIntent,2);
                  } else {
                      MySound.stopSound(Play.this, GlobalVariables.Audio_File_Name);
                      finish();

                  }

                  break;

          }
      }
        GlobalVariables.myIntent=replayIntent;
    }

    void printSecreenInfo(){

        Display display = getWindowManager().getDefaultDisplay();

        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);

        //Log.i("", "density :" + metrics.density);
        float a=metrics.densityDpi;
//        GlobalVariables.xScale_factor=1;
        GlobalVariables.xScale_factor=(float) (metrics.widthPixels/800.0);

        GlobalVariables.yScale_factor=(float) (metrics.heightPixels/480.0);
        // density interms of dpi
       // Log.i("", "D density :" +  metrics.densityDpi);

        // horizontal pixel resolution
        //Log.i("", "width pix :" +  metrics.widthPixels);
        GlobalVariables.width=metrics.widthPixels;
        // vertical pixel resolution
        //Log.i("", "height pix :" +  metrics.heightPixels);
        GlobalVariables.height=metrics.heightPixels;
        // actual horizontal dpi
       // Log.i("", "xdpi :" +  metrics.xdpi);

        // actual vertical dpi
        //Log.i("", "ydpi :" + metrics.ydpi);



        int currentapiVersion = android.os.Build.VERSION.SDK_INT;
        if (currentapiVersion >= android.os.Build.VERSION_CODES.FROYO){
            // Do something for froyo and above versions
        } else{
            // do something for phones running an SDK before froyo
        }

    }

    public void closeDialogBox(){
//        AlertDialog dialog = null;
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        @SuppressLint("NewApi")
//        ActionBar.LayoutParams params = new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT);
//        dialog = builder.create();
//        dialog.setCancelable(false);
//
//        dialog.setButton2("No", new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int which) {
//
//                startActivityForResult(replayIntent, replayIndex);
//            }
//        });
//
//        dialog.setButton3("Yes", new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.dismiss();
//                finish();
//                Intent intent =new Intent();
//                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                intent.putExtra("EXIT",true);
//                intent.setClass(Play.this,MenuScreen.class);
//                startActivity(intent);
//            }
//        });
//        //**************************************************************************************
////        LinearLayout layout = new LinearLayout(this);
////        Bitmap set_cancel_bgTemp=Utility.decodeSampledBitmapFromResource(GlobalVariables.getResource, R.drawable.set_cancel_bg);
////        Drawable set_cancel_bg =new BitmapDrawable(GlobalVariables.getResource, set_cancel_bgTemp);
////
////        layout.setBackground(set_cancel_bg);
////        layout.setGravity(Gravity.CENTER_VERTICAL);
//
////        Bitmap small_iconTemp=Utility.decodeSampledBitmapFromResource(GlobalVariables.getResource, R.drawable.small_icon);
////        Drawable small_icon =new BitmapDrawable(GlobalVariables.getResource, small_iconTemp);
//
////        ImageView imageView = new ImageView(this);
////        imageView.setBackground(small_icon);
//
////        TextView textView = new TextView(this);
////        textView.setText("Are You Sure");
////        textView.setTextSize(30);
////        layout.setPadding(10, 10, 10, 10);
////        textView.setTextColor(Color.WHITE);
////        textView.setPadding(20, 10, 10, 10);
//        //layout.addView(imageView);
//        //layout.addView(textView);
//
//        //dialog.setCustomTitle(layout);
//        //dialog.setIcon(R.drawable.small_icon);
//        //***********************************************************************************************
//        LinearLayout layout1 = new LinearLayout(this);
//        LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(800, 480);
//        layout1.setLayoutParams(layoutParams);
//
//        Bitmap exit_bg_nwTemp=Utility.decodeSampledBitmapFromResource(GlobalVariables.getResource, R.drawable.exit_bg);
//        Drawable exit_bg_nw =new BitmapDrawable(GlobalVariables.getResource, exit_bg_nwTemp);
//
//        layout1.setBackground(exit_bg_nw);
//        TextView textView1 = new TextView(this);
//        textView1.setText("");
//        textView1.setTextSize(30);
//        textView1.setTextColor(Color.WHITE);
//        layout1.setGravity(Gravity.CENTER);
//
//        layout1.addView(textView1);
//        dialog.setView(layout1);
//
//        dialog.show();
//
//        //**************************************************************
//        Bitmap set_cancel_bg_nwTemp=Utility.decodeSampledBitmapFromResource(GlobalVariables.getResource, R.drawable.set_cancel_bg_nw);
//        Drawable set_cancel_bg_nw =new BitmapDrawable(GlobalVariables.getResource, set_cancel_bg_nwTemp);
//
//        Button b = dialog.getButton(AlertDialog.BUTTON2);
//        b.setBackground(set_cancel_bg_nw);
//        b.setTextSize(30);
//        b.setTextColor(Color.WHITE);
//
//        Button b1 = dialog.getButton(AlertDialog.BUTTON3);
//        b1.setBackground(set_cancel_bg_nw);
//        b1.setTextSize(30);
//        b1.setTextColor(Color.WHITE);
//
//        int dividerId = GlobalVariables.getResource.getIdentifier(
//                "android:id/titleDivider", null, null);
//        View divider = dialog.findViewById(dividerId);
//        divider.setBackgroundResource(R.drawable.divider);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

            GoogleAnalytics.getInstance(this).reportActivityStop(this);

    }
}

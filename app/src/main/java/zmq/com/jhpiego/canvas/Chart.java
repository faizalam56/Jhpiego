package zmq.com.jhpiego.canvas;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import zmq.com.jhpiego.R;
import zmq.com.jhpiego.activity.ShowScoreActivity;


/**
 * Created by zmq162 on 10/6/15.
 */
public class Chart extends View{

    private Paint linePaint;
    Context context;
    Bitmap niddle1;
    Bitmap background;
    Bitmap speedBgRisk,speedBgRelation,speedBgImpulse,speedBgKnowledge;
    RectF speedRectF;
    Bitmap halfCircle;

    float angleRelation = 0;
    float angleEndRelation = 45;
    float angleRisk = 0;
    float angleEndRisk = 60;
    float angleImpulse = 0;
    float angleEndImpulse =135 ;
    float angleKnoledge = 0;
    float angleEndKnoledge = 120;
    int percent = 8;

    boolean boolRel = false;
    int counterRel = 0;
    int arrRel[] = {10,20,16,12,8,4,2};

    boolean boolRisk = false;
    int counterRisk = 0;
   // int arrRisk[] = {10,20,16,12,8,4,2};

    boolean boolImpulse = false;
    int counterImpulse = 0;
   // int arrImpulse[] = {10,20,16,12,8,4,2};

    boolean boolKnoledge = false;
    int counterKnoledge = 0;
  //  int arrKnoledge[] = {10,20,16,12,8,4,2};

    Intent mIntent;
    public Chart(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public Chart(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public Chart(Context context, Intent intent) {
        super(context);
        this.context = context;
        init();
        mIntent = intent;
        angleEndRelation = calculateEndAngle(intent.getStringExtra("Relation"));
        angleEndRisk = calculateEndAngle(intent.getStringExtra("Risk"));
        angleEndImpulse = calculateEndAngle(intent.getStringExtra("Impulse"));
        angleEndKnoledge = calculateEndAngle(intent.getStringExtra("Knoledge"));

    }

    private void init() {

        linePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        linePaint.setStyle(Paint.Style.FILL);
        linePaint.setColor(Color.BLACK);

        background = BitmapFactory.decodeResource(context.getResources(), R.drawable.speedometer_bg);
        speedBgRisk= BitmapFactory.decodeResource(context.getResources(), R.drawable.risk);
        speedBgRelation= BitmapFactory.decodeResource(context.getResources(), R.drawable.relationship);
        speedBgImpulse= BitmapFactory.decodeResource(context.getResources(), R.drawable.impulse);
        speedBgKnowledge= BitmapFactory.decodeResource(context.getResources(), R.drawable.knowledge);

        halfCircle = BitmapFactory.decodeResource(context.getResources(), R.drawable.speedometer_needle_circle);
        niddle1 = BitmapFactory.decodeResource(context.getResources(), R.drawable.speedometer_horozontal_needle);

    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int height = canvas.getHeight();
        int width = canvas.getWidth();

        drawLine(canvas, width, height);

        showRisk(canvas, niddle1, width, height);
        showRelation(canvas, niddle1, width, height);
        showImpulse(canvas, niddle1, width, height);
        showKnowledge(canvas, niddle1, width, height);
        invalidate(); // Cause a re-draw

    }



    @Override
    public boolean onTouchEvent(MotionEvent event) {

//        Toast.makeText(context,"TTTTTTTTTTT",Toast.LENGTH_SHORT).show();
//        System.out.println("hi............");
//        Log.d("SAIFI", "hi...........hi");
        String str = mIntent.getStringExtra("Knoledge");
        System.out.print("asfddfddddddddddddd         "+str);


        switch(event.getAction())
        {


            case MotionEvent.ACTION_DOWN:

                Intent intent = new Intent(context, ShowScoreActivity.class);
                intent.putExtra("Risk",mIntent.getStringExtra("Risk"));
                intent.putExtra("Relation",mIntent.getStringExtra("Relation"));
                intent.putExtra("Impulse",mIntent.getStringExtra("Impulse"));
                intent.putExtra("Knoledge",mIntent.getStringExtra("Knoledge"));
                intent.putExtra("Bonus",mIntent.getStringExtra("Bonus"));


                context.startActivity(intent);
                break;
//            case MotionEvent.ACTION_MOVE:
//
//                Intent intent1 = new Intent(context, ShowScoreActivity.class);
//                intent1.putExtra("Risk",mIntent.getStringExtra("Risk"));
//                intent1.putExtra("Relation",mIntent.getStringExtra("Relation"));
//                intent1.putExtra("Impulse",mIntent.getStringExtra("Impulse"));
//                intent1.putExtra("Knoledge",mIntent.getStringExtra("Knoledge"));
//                intent1.putExtra("Bonus",mIntent.getStringExtra("Bonus"));
//                context.startActivity(intent1);
//
//                break;
//            case MotionEvent.ACTION_UP:
//
//                Intent intent2 = new Intent(context, ShowScoreActivity.class);
//                intent2.putExtra("Risk",mIntent.getStringExtra("Risk"));
//                intent2.putExtra("Relation",mIntent.getStringExtra("Relation"));
//                intent2.putExtra("Impulse",mIntent.getStringExtra("Impulse"));
//                intent2.putExtra("Knoledge",mIntent.getStringExtra("Knoledge"));
//                intent2.putExtra("Bonus",mIntent.getStringExtra("Bonus"));
//                context.startActivity(intent2);
//                break;
        }

        return super.onTouchEvent(event);

    }

    private void drawLine(Canvas canvas, int canvasWidth,int canvasHeight) {



        linePaint.setColor(Color.BLACK);
        linePaint.setStrokeWidth(2f);


        canvas.drawLine(0, canvasHeight / 2, canvasWidth, canvasHeight / 2, linePaint);
        canvas.drawLine(canvasWidth / 4, 0, canvasWidth / 4, canvasHeight, linePaint);
        canvas.drawLine(canvasWidth / 2, 0, canvasWidth / 2, canvasHeight, linePaint);
        canvas.drawLine(canvasWidth * 3 / 4, 0, canvasWidth * 3 / 4, canvasHeight, linePaint);

        speedRectF = new RectF(0,0,canvasWidth,canvasHeight);
        canvas.drawBitmap(background, new Rect(0, 0, background.getWidth(), background.getHeight()), speedRectF, null);


    }

//    // show Relation
//    private void showRelation(Canvas canvas, Bitmap source, int canvasWidth, int canvasHeight){
//
//
//        speedRectF=	new RectF(0,0,canvasWidth/2,canvasHeight/2);
//        canvas.drawBitmap(speedBg, new Rect(0, 0, speedBg.getWidth(), speedBg.getHeight()), speedRectF, null);
//
//        //Log.d("SAIFI",""+" Test");
//
//
//            Matrix matrix = new Matrix();
//            //   float angle = (float) (System.currentTimeMillis() % 1000) / 1000 * 90;
//
//
//        //    System.out.println("angle " + angleRelation);
//
//
//            matrix.reset();
//            // here we gives the point bottom center of an image Vertical niddle
//            //matrix.postTranslate(-source.getWidth() / 2, -source.getHeight());
//            // here we gives the point bottom center of an image Horizontal niddle
//            matrix.postTranslate(-source.getWidth(), -source.getHeight()/2);
//            matrix.postRotate(angleRelation);
//
//            //here  gives the points(center) about to be rotate on canvas Vitical
//            //matrix.postTranslate(canvasWidth / 4, canvasHeight / 2 - source.getWidth() / 2);
//
//            //here  gives the points(center) about to be rotate on canvas Horizontal
//            matrix.postTranslate(canvasWidth / 4, canvasHeight / 2 - source.getHeight() / 2 );
//            canvas.drawBitmap(source, matrix, null);
//
//        if(angleRelation < angleEndRelation && !boolRel) {
//            angleRelation++;
//        //    Log.d("SAIFI", "***aaaa " + angleRelation);
//
//        }
//        else{
//            boolRel=true;
//
//            if(counterRel <=6){
//
//                commonRel( angleEndRelation);
//            }
//
//        }
//        canvas.drawBitmap(halfCircle, canvasWidth / 4 - halfCircle.getWidth() / 2, canvasHeight / 2 - halfCircle.getHeight(), null);
//    }
//
//    // show Risk
//    private void showRisk(Canvas canvas, Bitmap source, int canvasWidth, int canvasHeight){
//
//        speedRectF=	new RectF(canvasWidth/2,0,canvasWidth,canvasHeight/2);
//        canvas.drawBitmap(speedBg, new Rect(0, 0, speedBg.getWidth(), speedBg.getHeight()), speedRectF, null);
//
//    //    Log.d("SAIFI",""+" Test");
//
//        Matrix matrix = new Matrix();
//
//        //   float angle = (float) (System.currentTimeMillis() % 1000) / 1000 * 90;
//
//
//    //    System.out.println("angle " + angleRisk);
//
//    //    Log.d("SAIFI", "" + angleRisk);
//        matrix.reset();
//        matrix.postTranslate(-source.getWidth() , -source.getHeight() / 2);
//        matrix.postRotate(angleRisk);
//        matrix.postTranslate(canvasWidth * 3 / 4, canvasHeight / 2 - source.getHeight() / 2);
//        canvas.drawBitmap(source, matrix, null);
//
//        if(angleRisk < angleEndRisk && !boolRisk) {
//            angleRisk++;
//    //        Log.d("SAIFI", "***aaaa " + angleRisk);
//
//        }
//        else{
//            boolRisk=true;
//
//            if(counterRisk <=6){
//
//                commonRisk(angleEndRisk);
//            }
//
//        }
//
//        canvas.drawBitmap(halfCircle, canvasWidth* 3 / 4 - halfCircle.getWidth() / 2, canvasHeight/2 - halfCircle.getHeight(), null);
//    }

    // show Risk
    private void showRisk(Canvas canvas, Bitmap source, int canvasWidth, int canvasHeight){

        speedRectF=	new RectF(0,0,canvasWidth/2,canvasHeight/2 - 30);
        canvas.drawBitmap(speedBgRisk, new Rect(0, 0, speedBgRisk.getWidth(), speedBgRisk.getHeight()), speedRectF, null);

        //Log.d("SAIFI",""+" Test");


        Matrix matrix = new Matrix();
        //   float angle = (float) (System.currentTimeMillis() % 1000) / 1000 * 90;


        //    System.out.println("angle " + angleRelation);


        matrix.reset();
        // here we gives the point bottom center of an image Vertical niddle
        //matrix.postTranslate(-source.getWidth() / 2, -source.getHeight());
        // here we gives the point bottom center of an image Horizontal niddle
        matrix.postTranslate(-source.getWidth(), -source.getHeight()/2 );
        matrix.postRotate(angleRisk);

        //here  gives the points(center) about to be rotate on canvas Vitical
        //matrix.postTranslate(canvasWidth / 4, canvasHeight / 2 - source.getWidth() / 2);

        //here  gives the points(center) about to be rotate on canvas Horizontal
        matrix.postTranslate(canvasWidth / 4, canvasHeight / 2 - source.getHeight() / 2 - 30  );
        canvas.drawBitmap(source, matrix, null);

        if(angleRisk < angleEndRisk && !boolRisk) {
            angleRisk++;
            //    Log.d("SAIFI", "***aaaa " + angleRelation);

        }
        else{
            boolRisk=true;

            if(counterRisk <=6){

                commonRisk( angleEndRisk);
            }

        }
        canvas.drawBitmap(halfCircle, canvasWidth / 4 - halfCircle.getWidth() / 2, canvasHeight / 2 - halfCircle.getHeight() -30, null);

    }

    // show Relation
    private void showRelation(Canvas canvas, Bitmap source, int canvasWidth, int canvasHeight){

        speedRectF=	new RectF(canvasWidth/2,0,canvasWidth,canvasHeight/2-30);
        canvas.drawBitmap(speedBgRelation, new Rect(0, 0, speedBgRelation.getWidth(), speedBgRelation.getHeight()), speedRectF, null);

        //    Log.d("SAIFI",""+" Test");

        Matrix matrix = new Matrix();

        //   float angle = (float) (System.currentTimeMillis() % 1000) / 1000 * 90;


        //    System.out.println("angle " + angleRisk);

        //    Log.d("SAIFI", "" + angleRisk);
        matrix.reset();
        matrix.postTranslate(-source.getWidth() , -source.getHeight() / 2);
        matrix.postRotate(angleRelation);
        matrix.postTranslate(canvasWidth * 3 / 4, canvasHeight / 2 - source.getHeight() / 2 -30);
        canvas.drawBitmap(source, matrix, null);

        if(angleRelation < angleEndRelation && !boolRel) {
            angleRelation++;
            //        Log.d("SAIFI", "***aaaa " + angleRisk);

        }
        else{
            boolRel=true;

            if(counterRel <=6){

                commonRel(angleEndRelation);
            }

        }

        canvas.drawBitmap(halfCircle, canvasWidth* 3 / 4 - halfCircle.getWidth() / 2, canvasHeight/2 - halfCircle.getHeight() -30, null);
    }


    // show Impulse
    private void showImpulse(Canvas canvas, Bitmap source, int canvasWidth, int canvasHeight){

        speedRectF=	new RectF(0,canvasHeight/2 ,canvasWidth/2,canvasHeight - 40);
        canvas.drawBitmap(speedBgImpulse, new Rect(0, 0, speedBgImpulse.getWidth(), speedBgImpulse.getHeight()), speedRectF, null);

    //    Log.d("SAIFI",""+" Test");
        Matrix matrix = new Matrix();

        //   float angle = (float) (System.currentTimeMillis() % 1000) / 1000 * 90;


    //    System.out.println("angle "+angleImpulse);

    //    Log.d("SAIFI", "" + angleImpulse);
        matrix.reset();
        matrix.postTranslate(-source.getWidth() , -source.getHeight() / 2);
        matrix.postRotate(angleImpulse);
        matrix.postTranslate(canvasWidth/4, canvasHeight-source.getHeight() / 2 -40);
        canvas.drawBitmap(source, matrix, null);

        if(angleImpulse < angleEndImpulse && !boolImpulse) {
            angleImpulse++;
    //        Log.d("SAIFI", "***aaaa " + angleImpulse);

        }
        else{
            boolImpulse=true;

            if(counterImpulse <=6){

                commonImpulse(angleEndImpulse);
            }

        }
        canvas.drawBitmap(halfCircle, canvasWidth/ 4 - halfCircle.getWidth() / 2, canvasHeight - halfCircle.getHeight() -40, null);

    }

    // show Knowledge
    private void showKnowledge(Canvas canvas, Bitmap source, int canvasWidth, int canvasHeight){

        speedRectF=	new RectF(canvasWidth/2, canvasHeight/2, canvasWidth, canvasHeight -40);
        canvas.drawBitmap(speedBgKnowledge, new Rect(0, 0, speedBgKnowledge.getWidth(), speedBgKnowledge.getHeight()), speedRectF, null);

    //    Log.d("SAIFI", "" + " Test");
        Matrix matrix = new Matrix();

    //    System.out.println("angle " + angleKnoledge);

    //    Log.d("SAIFI", "" + angleKnoledge);
        matrix.reset();
        matrix.postTranslate(-source.getWidth(), -source.getHeight() / 2);
        matrix.postRotate(angleKnoledge);
        matrix.postTranslate(canvasWidth * 3 / 4, canvasHeight - source.getHeight() / 2 -40);
        canvas.drawBitmap(source, matrix, null);

        if(angleKnoledge < angleEndKnoledge && !boolKnoledge) {
            angleKnoledge++;
    //        Log.d("SAIFI", "***aaaa " + angleKnoledge);

        }
        else{
            boolKnoledge=true;

            if(counterRel <=6){

                commonKnoledge( angleEndKnoledge);
            }

        }

        canvas.drawBitmap(halfCircle, canvasWidth* 3 / 4 - halfCircle.getWidth() / 2, canvasHeight - halfCircle.getHeight()-40, null);
    }


    private int  calculateEndAngle(String expression){
        int angle = 0;
        String  str[] = expression.split("/");

        if(Integer.parseInt(str[1])!=0) {

            int percent = (Integer.parseInt(str[0])*100 / Integer.parseInt(str[1]));
            angle = (180*percent)/100;

        }
        else{
            angle = 0;
        }
        return angle;
    }



    private void  commonRel( float accurateAngle){


        if(counterRel == 0 ||counterRel == 2 ||counterRel == 4 ||counterRel == 6 ){

            if(angleRelation < accurateAngle + arrRel[counterRel]){

                angleRelation++;
            }
             if(angleRelation == accurateAngle + arrRel[counterRel]){
                 counterRel++;
            }
        }
        else  if(counterRel == 1 ||counterRel == 3 ||counterRel == 5 ){

            if(angleRelation > accurateAngle - arrRel[counterRel]){
                angleRelation--;
            }
            else if(angleRelation == accurateAngle - arrRel[counterRel]){
                counterRel++;
            }
        }
    }

    private void  commonRisk( float accurateAngle){


        if(counterRisk == 0 ||counterRisk == 2 ||counterRisk == 4 ||counterRisk == 6 ){

            if(angleRisk < accurateAngle + arrRel[counterRisk]){

                angleRisk++;
            }
            if(angleRisk == accurateAngle + arrRel[counterRisk]){
                counterRisk++;
            }
        }
        else  if(counterRisk == 1 ||counterRisk == 3 ||counterRisk == 5 ){

            if(angleRisk > accurateAngle - arrRel[counterRisk]){
                angleRisk--;
            }
            else if(angleRisk == accurateAngle - arrRel[counterRisk]){
                counterRisk++;
            }
        }
    }

    private void  commonImpulse( float accurateAngle){


        if(counterImpulse == 0 ||counterImpulse == 2 ||counterImpulse == 4 ||counterImpulse == 6 ){

            if(angleImpulse < accurateAngle + arrRel[counterImpulse]){

                angleImpulse++;
            }
            if(angleImpulse == accurateAngle + arrRel[counterImpulse]){
                counterImpulse++;
            }
        }
        else  if(counterImpulse == 1 ||counterImpulse == 3 ||counterImpulse == 5 ){

            if(angleImpulse > accurateAngle - arrRel[counterImpulse]){
                angleImpulse--;
            }
            else if(angleImpulse == accurateAngle - arrRel[counterImpulse]){
                counterImpulse++;
            }
        }
    }

    private void  commonKnoledge( float accurateAngle){


        if(counterKnoledge == 0 ||counterKnoledge == 2 ||counterKnoledge == 4 ||counterKnoledge == 6 ){

            if(angleKnoledge < accurateAngle + arrRel[counterKnoledge]){

                angleKnoledge++;
            }
            if(angleKnoledge == accurateAngle + arrRel[counterKnoledge]){
                counterKnoledge++;
            }
        }
        else  if(counterKnoledge == 1 ||counterKnoledge == 3 ||counterKnoledge == 5 ){

            if(angleKnoledge > accurateAngle - arrRel[counterKnoledge]){
                angleKnoledge--;
            }
            else if(angleKnoledge == accurateAngle - arrRel[counterKnoledge]){
                counterKnoledge++;
            }
        }
    }

}


/////////////////////////////////////////////////////////////////////////////////////////////////////

//        else{
//
//            Log.d("SAIFI", "++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++=");
////                for (int i = 0; i < 4; i++) {
////
////                    if (angleRelation == angleEndRelation) {
////
////                        angleEndRelation = angleEndRelation + angleEndRelation * percent / 100;
////                        angleRelation += 2;
////                        matrix.postTranslate(-source.getWidth(), -source.getHeight() / 2);
////                        matrix.postRotate(angleRelation);
////                        matrix.postTranslate(canvasWidth / 4, canvasHeight / 2 - source.getHeight() / 2);
////                        canvas.drawBitmap(source, matrix, null);
////
////                    } else {
////
////                        angleRelation = angleEndRelation - angleEndRelation * 2 * percent / 100;
////                        angleRelation -= 2;
////                        matrix.postTranslate(-source.getWidth(), -source.getHeight() / 2);
////                        matrix.postRotate(angleRelation);
////                        matrix.postTranslate(canvasWidth / 4, canvasHeight / 2 - source.getHeight() / 2);
////                        canvas.drawBitmap(source, matrix, null);
////
////                    }
////
////                }
//
//        }
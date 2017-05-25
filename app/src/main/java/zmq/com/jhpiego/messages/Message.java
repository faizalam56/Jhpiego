package zmq.com.jhpiego.messages;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;

import zmq.com.jhpiego.interfaces.StoryAction;
import zmq.com.jhpiego.sprite.ShahSprite;
import zmq.com.jhpiego.utility.GlobalVariables;
import zmq.com.jhpiego.utility.StringDraw;
import zmq.com.jhpiego.utility.Utility;

/**
 * Created by ZMQ154 on 10/4/2015.
 */
 public  class Message implements StoryAction {
   public ShahSprite mess_bgImage;
    public String messString;
//    protected int width=GlobalVariables.width;
//    protected int height=GlobalVariables.height;
    public int messPosX;
    public int messPosY;
    public int messPadding=(int)(10*GlobalVariables.xScale_factor);

    protected boolean visible=true;

    public int messLinePixelWidth;
    public Message(int resId, String messString) {
        this.mess_bgImage = new ShahSprite(Utility.decodeSampledBitmapFromResource(GlobalVariables.getResource, resId));
        messLinePixelWidth= mess_bgImage.getWidth()-messPadding;
        this.setMessString(messString);
        int xPos=GlobalVariables.width/2- mess_bgImage.getWidth()/2;
        int yPos=GlobalVariables.height/2- mess_bgImage.getHeight()/2;
        this.mess_bgImage.setPosition(xPos, yPos);
        this.setMessTextPosition(xPos+messPadding/2,yPos+messPadding/2);
//        this.setMessTextPosition(this.mess_bgImage.getX()+5,this.mess_bgImage.getY()+5);

//        String [] temp= StringDraw.breakTextIntoMultipleLine(StringDraw.extractWords(messString+" ", ' '),mess_bgImage.getWidth()-10);
//
//        this.messString = StringDraw.joinStringWithCharacter(temp,"\n");
    }

    public void setMess_bgImage(ShahSprite mess_bgImage) {
        this.mess_bgImage = mess_bgImage;
    }

    public void setMessString(String messString) {

        messString=messString.replace("\n"," ");
        String[] words=StringDraw.extractWords(messString+" ", ' ');
        String [] line= StringDraw.breakTextIntoMultipleLine(words,messLinePixelWidth);

        this.messString = StringDraw.joinStringWithCharacter(line,"\n");
//        this.messString = messString;
    }

    @Override
    public void recycle() {
        if (!mess_bgImage.sourceImage.isRecycled()) {
            mess_bgImage.sourceImage.recycle();
        }
    }

    @Override
    public void update(Canvas g, Paint paint) {
        if(visible){
            mess_bgImage.paint(g,null);
            drawMyText(messString,messPosX,messPosY,g,paint);
        }
    }
    public void drawMyText(String text, int x, int y,Canvas g, Paint paint){

        y=y+10;
           for (String line: text.split("\n"))
           {
               g.drawText(line, x, y, paint);
               y += -paint.ascent() + paint.descent();
           }

   }
    public void setMessTextPosition(int messPosX,int messPosY) {
        this.messPosX = messPosX;
        this.messPosY = messPosY;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public void setMessPosX(int messPosX) {
        this.messPosX = messPosX;
    }

    public void setMessPosY(int messPosY) {
        this.messPosY = messPosY;
    }

    public int getMessLinePixelWidth() {
        return messLinePixelWidth;
    }

    public void setMessLinePixelWidth(int messLinePixelWidth) {
        this.messLinePixelWidth = messLinePixelWidth;
        this.setMessString(messString);
    }
/*
 @ description: set the message position on screen
 */
    public void setPosition(int xPos, int yPos) {
        this.mess_bgImage.setPosition(xPos, yPos);
        this.setMessTextPosition(xPos+messPadding/2,yPos+messPadding/2);
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return true;
    }

}

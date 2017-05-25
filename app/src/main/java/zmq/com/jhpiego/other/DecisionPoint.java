package zmq.com.jhpiego.other;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;

import zmq.com.jhpiego.interfaces.StoryAction;
import zmq.com.jhpiego.sprite.ShahSprite;
import zmq.com.jhpiego.utility.GlobalVariables;
import zmq.com.jhpiego.utility.Utility;

/**
 * Created by ZMQ154 on 21/4/2015.
 */
public class DecisionPoint implements StoryAction {

    public ShahSprite getBgImage() {
        return bgImage;
    }

    public String messString;
    public int messPosX;
    public int messPosY;

    public ShahSprite bgImage;
    public ShahSprite[] btnImageArray;

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    protected boolean visible=true;

    public DecisionPoint(int bgResId,int[] btnResArray) {

        messString="What will you do now?";
        btnImageArray=new ShahSprite[btnResArray.length];
        bgImage=new ShahSprite(Utility.decodeSampledBitmapFromResource(GlobalVariables.getResource, bgResId));
        for (int i = 0; i < btnResArray.length; i++) {
            Bitmap tempBtn=Utility.decodeSampledBitmapFromResource(GlobalVariables.getResource, btnResArray[i]);
            btnImageArray[i] = new ShahSprite(tempBtn,tempBtn.getWidth()/2,tempBtn.getHeight());
        }
        this.setPosition(GlobalVariables.width/2- bgImage.getWidth()/2,GlobalVariables.height/2- bgImage.getHeight()/2);
    }

    @Override
    public void update(Canvas g, Paint paint) {
        if(visible){
            bgImage.paint(g,null);
            for (int i = 0; i < btnImageArray.length; i++) {
                ShahSprite shahSprite = btnImageArray[i];
                shahSprite.paint(g,null);
            }
            drawMyText(messString, messPosX, messPosY, g, paint);
        }
    }
    public void setMessTextPosition(int messPosX,int messPosY) {
        this.messPosX = messPosX;
        this.messPosY = messPosY;
    }
    public ShahSprite getBtnAtIndext(int index) throws IndexOutOfBoundsException{
//        int length= (btnImageArray!=null?btnImageArray.length:0);
        if(btnImageArray!=null&& index<btnImageArray.length){
            return btnImageArray[index];
        }
        throw new ArrayIndexOutOfBoundsException(index);
    }
    public void setPosition(int xPos, int yPos) {
        bgImage.setPosition(xPos,yPos);
        int gap=5;
        if (bgImage != null && btnImageArray!=null) {
          gap=(bgImage.getHeight()-this.getBtnAtIndext(0).getHeight()*btnImageArray.length)/(btnImageArray.length+1);
          //gap+=5;
        }

        for (int i = 0; i < btnImageArray.length; i++) {
            ShahSprite shahSprite = btnImageArray[i];
            shahSprite.setPosition(xPos+(bgImage.getWidth()/2-shahSprite.getWidth()/2),gap +(i>0? btnImageArray[i-1].getY()+btnImageArray[i-1].getHeight():yPos));
        }
        this.setMessTextPosition(bgImage.getX(),bgImage.getY()-12);
    }
    public void drawMyText(String text, int x, int y,Canvas g, Paint paint){

        for (String line: text.split("\n"))
        {
            g.drawText(line, x, y, paint);
            y += -paint.ascent() + paint.descent();
        }

    }
    public void setActive(){
        for (int i = 0; i < btnImageArray.length; i++) {
            this.getBtnAtIndext(i).setFrame(1);
        }
    }
    public int getX(){
        return bgImage.getX();
    }

    public int getY(){
        return bgImage.getY();
    }

    @Override
    public void recycle() {
        if (!bgImage.sourceImage.isRecycled()) {
            bgImage.recycle();
        }
        for (int i = 0; i < btnImageArray.length; i++) {
            ShahSprite shahSprite = btnImageArray[i];
            if(!shahSprite.sourceImage.isRecycled()){
                shahSprite.recycle();
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        return true;
    }

}

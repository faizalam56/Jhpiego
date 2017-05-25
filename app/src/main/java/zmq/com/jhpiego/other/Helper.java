package zmq.com.jhpiego.other;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;

import java.util.Random;

import zmq.com.jhpiego.R;
import zmq.com.jhpiego.interfaces.StoryAction;
import zmq.com.jhpiego.sprite.ShahSprite;
import zmq.com.jhpiego.utility.GlobalVariables;
import zmq.com.jhpiego.utility.StringDraw;
import zmq.com.jhpiego.utility.Utility;

/**
 * Created by ZMQ154 on 23/4/2015.
 */
//public class Helper implements StoryAction {
//
//    public ShahSprite bgImage,actionButton,text_bg_image,selected_helper;
//    public ShahSprite[] helper_sprite_array;
//    public String[] helper_text_array;
//    int rand1,rand2;
//
//    protected boolean visible=true;
//
//    protected boolean text_visible=false;
//
//    protected boolean used;
//    public boolean isUsed() {
//        return used;
//    }
//
//    public void setUsed(boolean used) {
//        this.setText_visible(true);
//        this.used = used;
//    }
//
//
//
//    public String messString;
//    public int messPosX;
//    public int messPosY;
//    public int messPadding=10;
//
//    public int messLinePixelWidth;
//
//    public Helper(String[] helper_text_array) {
//        rand1=new Random().nextInt(2);
//        this.helper_text_array = helper_text_array;
//
//        this.bgImage=new ShahSprite(Utility.decodeSampledBitmapFromResource(GlobalVariables.getResource, R.drawable.helper_icons_bg));
//        actionButton = new ShahSprite(Utility.decodeSampledBitmapFromResource(GlobalVariables.getResource, R.drawable.continue1));
//
//        text_bg_image=new ShahSprite(Utility.decodeSampledBitmapFromResource(GlobalVariables.getResource, R.drawable.helpers_message_bg));
//
//
//        rand2=new Random().nextInt(2);
//
//        int  helper_image_array[]={(rand1==0?R.drawable.leo01a:R.drawable.diva01b),
//                (rand2==0?R.drawable.cathy02a:R.drawable.patrick02b),
//                R.drawable.counselor03,
//                R.drawable.aunt04};
//        helper_sprite_array=new ShahSprite[helper_image_array.length];
//        for (int i = 0; i < helper_image_array.length; i++) {
//            Bitmap tempImage=Utility.decodeSampledBitmapFromResource(GlobalVariables.getResource, helper_image_array[i]);
//              helper_sprite_array[i]=new ShahSprite(tempImage,tempImage.getWidth()/2,tempImage.getHeight());
//
//        }
//        ShahSprite temp=gethelperAtIndext(0);
//        selected_helper=new ShahSprite(temp,temp.getWidth(),temp.getHeight());
////        this.messString=helper_text_array[0];
//        messLinePixelWidth= text_bg_image.getWidth()-selected_helper.getWidth()-messPadding;
//        this.setMessString(this.helper_text_array[0]);
//        this.setPosition(GlobalVariables.width-(bgImage.getWidth()-10),0/*GlobalVariables.height/2-bgImage.getHeight()/2*/);
//    }
//
//    @Override
//    public void update(Canvas g, Paint paint) {
//        if (visible) {
//            bgImage.paint(g,null);
//            for (int i = 0; i < helper_sprite_array.length; i++) {
//                ShahSprite shahSprite = helper_sprite_array[i];
//                shahSprite.paint(g,null);
//            }
//            if(text_visible){
//                text_bg_image.paint(g,null);
//                selected_helper.paint(g,null);
//                drawMyText(messString, messPosX, messPosY, g, paint);
//                actionButton.paint(g,null);
//            }
//        }
//    }
//
//    public ShahSprite gethelperAtIndext(int index) throws IndexOutOfBoundsException{
//
//        if(helper_sprite_array!=null&& index<helper_sprite_array.length){
//            return helper_sprite_array[index];
//        }
//        throw new ArrayIndexOutOfBoundsException(index);
//    }
//
//    public void setMessTextPosition(int messPosX,int messPosY) {
//        this.messPosX = messPosX;
//        this.messPosY = messPosY;
//    }
//
//    public void setMessStringForIndex(int index) throws ArrayIndexOutOfBoundsException{
//        int  helper_selected_image[]={(rand1==0?R.drawable.leo_stand:R.drawable.diva_stand),
//                (rand2==0?R.drawable.cathy_stand:R.drawable.patrick_stand),
//                R.drawable.counselor_stand,
//                R.drawable.aunty_stand};
//
//        if(helper_text_array!=null&& index<helper_text_array.length){
//            ShahSprite temp=new ShahSprite(Utility.decodeSampledBitmapFromResource(GlobalVariables.getResource, helper_selected_image[index]));
//            //ShahSprite temp=gethelperAtIndext(helper_selected_image[index]);
//            selected_helper=new ShahSprite(temp,temp.getWidth(),temp.getHeight());
//            selected_helper.setPosition(text_bg_image.getX(), text_bg_image.getY()+10);
//            this.setMessString(this.helper_text_array[index]);
//            return ;
//        }
//        throw new ArrayIndexOutOfBoundsException(index);
//    }
//
//    public void setMessString(String messString) {
//
//        messString=messString.replace("\n"," ");
//        String[] words= StringDraw.extractWords(messString + " ", ' ');
//        String [] line= StringDraw.breakTextIntoMultipleLine(words,messLinePixelWidth);
//        this.messString = StringDraw.joinStringWithCharacter(line,"\n");
////        this.messString = messString;
//    }
//
//    public void drawMyText(String text, int x, int y,Canvas g, Paint paint){
//
//        for (String line: text.split("\n"))
//        {
//            g.drawText(line, x, y, paint);
//            y += -paint.ascent() + paint.descent();
//        }
//
//    }
//
//    /*
// @ description: set the message position on screen
// */
//    public void setPosition(int xPos, int yPos) {
//        this.bgImage.setPosition(xPos, yPos);
//        int gap=0;
//        for (int i = 0; i < helper_sprite_array.length; i++) {
//            ShahSprite shahSprite = helper_sprite_array[i];
//            shahSprite.setPosition(xPos+(bgImage.getWidth()/2-shahSprite.getWidth()/2),gap +(i>0? helper_sprite_array[i-1].getY()+helper_sprite_array[i-1].getHeight():yPos));
//        }
//        text_bg_image.setPosition(xPos-text_bg_image.getWidth(),yPos);
//        actionButton.setPosition(text_bg_image.getX()+text_bg_image.getWidth()/2-actionButton.getWidth()/2,text_bg_image.getY()+text_bg_image.getHeight()-actionButton.getHeight());
//        selected_helper.setPosition(text_bg_image.getX(), text_bg_image.getY());
//        this.setMessTextPosition(text_bg_image.getX()+selected_helper.getWidth()+messPadding/2,text_bg_image.getY()+30+selected_helper.getHeight()/2);
//    }
//    public int getMessLinePixelWidth() {
//        return messLinePixelWidth;
//    }
//
//    public void setMessLinePixelWidth(int messLinePixelWidth) {
//        this.messLinePixelWidth = messLinePixelWidth;
//    }
//
//    public int getMessPosX() {
//        return messPosX;
//    }
//
//    public void setMessPosX(int messPosX) {
//        this.messPosX = messPosX;
//    }
//
//    public int getMessPosY() {
//        return messPosY;
//    }
//
//    public void setMessPosY(int messPosY) {
//        this.messPosY = messPosY;
//    }
//
//    public String getMessString() {
//        return messString;
//    }
//
//    public boolean isText_visible() {
//        return text_visible;
//    }
//
//    public void setText_visible(boolean text_visible) {
//        this.text_visible = text_visible;
//    }
//
//    public boolean isVisible() {
//        return visible;
//    }
//
//    public void setVisible(boolean visible) {
//        this.visible = visible;
//    }
//
//
//    @Override
//    public void recycle() {
//        bgImage.recycle();
//        actionButton.recycle();
//        text_bg_image.recycle();
//        selected_helper.recycle();
//        for (int i = 0; i < helper_sprite_array.length; i++) {
//            ShahSprite shahSprite = helper_sprite_array[i];
//            shahSprite.recycle();
//        }
//    }
//
//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        return false;
//    }
//
//
//}


public class Helper implements StoryAction {

    public ShahSprite bgImage,actionButton,text_bg_image,selected_helper;
    public ShahSprite[] helper_sprite_array;
    public String[] helper_text_array;
    int rand1,rand2;
    private String Title="Oopssssss";
    protected boolean visible=true;

    protected boolean text_visible=false;
    protected boolean used;
    public String messString;
    public int messPosX;
    public int messPosY;
    public int messPadding=(int)(GlobalVariables.xScale_factor*10);

    public int messLinePixelWidth;
    public int helper_xCord=(int)(GlobalVariables.xScale_factor*315);
    public int bg_Image_center;

    public Helper(String[] helper_text_array) {
        rand1=new Random().nextInt(2);
        this.helper_text_array = helper_text_array;

        this.bgImage=new ShahSprite(Utility.decodeSampledBitmapFromResource(GlobalVariables.getResource, R.drawable.helper_icons_bg));
        actionButton = new ShahSprite(Utility.decodeSampledBitmapFromResource(GlobalVariables.getResource, R.drawable.continue1));

        text_bg_image=new ShahSprite(Utility.decodeSampledBitmapFromResource(GlobalVariables.getResource, R.drawable.faded_bg));
        bg_Image_center=helper_xCord+(text_bg_image.getWidth()-bgImage.getWidth()-helper_xCord)/2;

        rand2=new Random().nextInt(2);

        int  helper_image_array[]={(rand1==0?R.drawable.leo01a:R.drawable.diva01b),
                (rand2==0?R.drawable.cathy02a:R.drawable.patrick02b),
                R.drawable.counselor03,
                R.drawable.aunt04};
        helper_sprite_array=new ShahSprite[helper_image_array.length];
        for (int i = 0; i < helper_image_array.length; i++) {
            Bitmap tempImage=Utility.decodeSampledBitmapFromResource(GlobalVariables.getResource, helper_image_array[i]);
            helper_sprite_array[i]=new ShahSprite(tempImage,tempImage.getWidth()/2,tempImage.getHeight());

        }
        ShahSprite temp=gethelperAtIndext(0);
        selected_helper=new ShahSprite(temp,temp.getWidth(),temp.getHeight());
        selected_helper.setVisible(false);
//        this.messString=helper_text_array[0];
        messLinePixelWidth= text_bg_image.getWidth()-(bgImage.getWidth()+helper_xCord+messPadding);
        this.setMessString(this.helper_text_array[0]);
        this.setPosition(GlobalVariables.width-(bgImage.getWidth()-messPadding),0/*GlobalVariables.height/2-bgImage.getHeight()/2*/);
    }

    @Override
    public void update(Canvas g, Paint paint) {
        if (visible) {
            bgImage.paint(g,null);
            for (int i = 0; i < helper_sprite_array.length; i++) {
                ShahSprite shahSprite = helper_sprite_array[i];
                shahSprite.paint(g,null);
            }
            if(text_visible){

                text_bg_image.paint(g,null);
                selected_helper.paint(g,null);
                paint.setColor(Color.YELLOW);
                drawMyText(Title, bg_Image_center - messPadding - actionButton.getWidth() / 2, (int) (GlobalVariables.yScale_factor * 110), g, paint);

                paint.setColor(Color.WHITE);
                drawMyText(messString, messPosX, messPosY, g, paint);
                actionButton.paint(g,null);
            }
        }
    }

    public ShahSprite gethelperAtIndext(int index) throws IndexOutOfBoundsException{

        if(helper_sprite_array!=null&& index<helper_sprite_array.length){
            return helper_sprite_array[index];
        }
        throw new ArrayIndexOutOfBoundsException(index);
    }

    public void setMessTextPosition(int messPosX,int messPosY) {
        this.messPosX = messPosX;
        this.messPosY = messPosY;
    }

    public void setMessStringForIndex(int index) throws ArrayIndexOutOfBoundsException{
        int  helper_selected_image[]={(rand1==0?R.drawable.leo_stand:R.drawable.diva_stand),
                (rand2==0?R.drawable.cathy_stand:R.drawable.patrick_stand),
                R.drawable.counselor_stand,
                R.drawable.aunty_stand};

        final String title[] = {(rand1==0?"Leo":"Diva Dorcas"),
                              (rand2==0?"Cathy":"Patrick"),
                              "Counselor", "Aunty"};
        Title=title[index];
        if(helper_text_array!=null&& index<helper_text_array.length){
            ShahSprite temp=new ShahSprite(Utility.decodeSampledBitmapFromResource(GlobalVariables.getResource, helper_selected_image[index]));
            //ShahSprite temp=gethelperAtIndext(helper_selected_image[index]);
            selected_helper=new ShahSprite(temp,temp.getWidth(),temp.getHeight());
            selected_helper.setPosition(0, 0);
            selected_helper.setVisible(true);
            this.setMessString(this.helper_text_array[index]);
            return ;
        }
        throw new ArrayIndexOutOfBoundsException(index);
    }

    public void setMessString(String messString) {

        messString=messString.replace("\n"," ");
        String[] words= StringDraw.extractWords(messString + " ", ' ');
        String [] line= StringDraw.breakTextIntoMultipleLine(words,messLinePixelWidth);
        this.messString = StringDraw.joinStringWithCharacter(line,"\n");
//        this.messString = messString;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.setText_visible(true);
        this.used = used;
    }

    public void drawMyText(String text, int x, int y,Canvas g, Paint paint){

        for (String line: text.split("\n"))
        {
            g.drawText(line, x, y, paint);
            y += -paint.ascent() + paint.descent();
        }

    }

    /*
 @ description: set the message position on screen
 */
    public void setPosition(int xPos, int yPos) {
        this.bgImage.setPosition(xPos, yPos);
        int gap=0;
        for (int i = 0; i < helper_sprite_array.length; i++) {
            ShahSprite shahSprite = helper_sprite_array[i];
            shahSprite.setPosition(xPos+(bgImage.getWidth()/2-shahSprite.getWidth()/2),gap +(i>0? helper_sprite_array[i-1].getY()+helper_sprite_array[i-1].getHeight():yPos));
        }
        text_bg_image.setPosition(-bgImage.getWidth(),0);
        actionButton.setPosition(bg_Image_center-actionButton.getWidth()/2-messPadding,text_bg_image.getY()+text_bg_image.getHeight()-(int)(GlobalVariables.yScale_factor*130));
        selected_helper.setPosition(0, 0);
        this.setMessTextPosition(helper_xCord,(int)(GlobalVariables.yScale_factor*155));
//        this.setMessTextPosition(GlobalVariables.width/2,text_bg_image.getY()+30+selected_helper.getHeight()/2);

    }
    public int getMessLinePixelWidth() {
        return messLinePixelWidth;
    }

    public void setMessLinePixelWidth(int messLinePixelWidth) {
        this.messLinePixelWidth = messLinePixelWidth;
    }

    public int getMessPosX() {
        return messPosX;
    }

    public void setMessPosX(int messPosX) {
        this.messPosX = messPosX;
    }

    public int getMessPosY() {
        return messPosY;
    }

    public void setMessPosY(int messPosY) {
        this.messPosY = messPosY;
    }

    public String getMessString() {
        return messString;
    }

    public boolean isText_visible() {
        return text_visible;
    }

    public void setText_visible(boolean text_visible) {
        this.text_visible = text_visible;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }


    @Override
    public void recycle() {
        bgImage.recycle();
        actionButton.recycle();
        text_bg_image.recycle();
        selected_helper.recycle();
        for (int i = 0; i < helper_sprite_array.length; i++) {
            ShahSprite shahSprite = helper_sprite_array[i];
            shahSprite.recycle();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return false;
    }


}
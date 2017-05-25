package zmq.com.jhpiego.messages;

import zmq.com.jhpiego.utility.GlobalVariables;

/**
 * Created by ZMQ154 on 16/4/2015.
 */
public class InnerThought extends Message {

    public int mess_left_padding = (int)(GlobalVariables.xScale_factor*50);
    public static final int TOP_LEFT=1;
    public static final int TOP_RIGHT=2;
    public static final int BOTTOM_LEFT=4;
    public static final int BOTTOM_RIGHT=8;

    /*set dynamic position*/
    public InnerThought(int resId, String messString) {
        super(resId, messString);
        //this.setMessLinePixelWidth(mess_bgImage.getWidth() - (mess_left_padding + messPadding));
    }

    public void setPosition(int xPos, int yPos) {
        this.mess_bgImage.setPosition(xPos, yPos);
        this.setMessTextPosition(xPos+mess_left_padding+messPadding/2,yPos+messPadding/2);
    }
    public void setPosition(int xPos, int yPos,int REGION) {
        this.mess_bgImage.setPosition(xPos, yPos);
        switch (REGION) {
            case 1:
                this.setMessTextPosition(xPos+messPadding/2,yPos+messPadding);/*TOP_LEFT*/
                break;
            case 2:
                this.setMessTextPosition(xPos+mess_left_padding+messPadding/2,yPos+messPadding);/*TOP_RIGHT*/
                break;
            case 4:
                this.setMessTextPosition(xPos+messPadding/2,yPos+mess_left_padding+messPadding/2); /*BOTTOM_LEFT*/
                break;
            case 8:
                this.setMessTextPosition(xPos+messPadding/2+mess_left_padding,yPos+mess_left_padding+messPadding/2); /*BOTTOM_RIGHT*/
                break;
        }
    }


    public void setThoughtPositions(InnerThought innerThought,int REGION){

        switch (REGION) {
            case 1://top left
                innerThought.setMessLinePixelWidth((int)(GlobalVariables.xScale_factor*250));
                innerThought.setMessTextPosition(innerThought.mess_bgImage.getX() +(int)(GlobalVariables.xScale_factor*22),innerThought.mess_bgImage.getY() +(int)(GlobalVariables.yScale_factor*22));

                break;
            case 2://top right
                innerThought.setMessLinePixelWidth((int)(GlobalVariables.xScale_factor*250));
                innerThought.setMessTextPosition(innerThought.mess_bgImage.getX() +(int)(GlobalVariables.xScale_factor*33), innerThought.mess_bgImage.getY() +(int)(GlobalVariables.yScale_factor*23));
                break;

            case 3://bottom left
                innerThought.setMessLinePixelWidth((int)(GlobalVariables.xScale_factor*250));
                innerThought.setMessTextPosition(innerThought.mess_bgImage.getX() + (int)(GlobalVariables.xScale_factor*20), innerThought.mess_bgImage.getY() +(int)(GlobalVariables.yScale_factor*50));

                break;
            case 4://bottom right
                innerThought.setMessLinePixelWidth((int)(GlobalVariables.xScale_factor*250));
                innerThought.setMessTextPosition(innerThought.mess_bgImage.getX() +(int)(GlobalVariables.xScale_factor*35), innerThought.mess_bgImage.getY() +(int)(GlobalVariables.yScale_factor*50));
                break;
        }

    }
}


/*   innerThought_top_left.setThoughtPositions(innerThought_top_left,1);
     innerThought_top_right.setThoughtPositions(innerThought_top_right,2);
     innerThought_bottom_left.setThoughtPositions(innerThought_bottom_left,3);
     innerThought_bottom_right.setThoughtPositions(innerThought_bottom_right,4);*/
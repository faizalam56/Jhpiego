package zmq.com.jhpiego.messages;

/**
 * Created by ZMQ154 on 8/5/2015.
 */
public class PopUpMessageBottom  extends  PopUpMessage{

    public PopUpMessageBottom(int resId, String messString, int btn1ResId, int btn2ResId) {
        super(resId, messString, btn1ResId, btn2ResId);
    }
    public void setPosition(int xPos, int yPos) {
//        mess_bgImage.setPosition(xPos,yPos);
        super.setPosition(xPos,yPos);

        actionButton2.setPosition(xPos+mess_bgImage.getWidth()-actionButton2.getWidth()-75,yPos+mess_bgImage.getHeight()/2-actionButton2.getHeight()/2);
        actionButton1.setPosition(actionButton2.getX()-actionButton1.getWidth()-20,yPos+mess_bgImage.getHeight()/2-actionButton1.getHeight()/2);
//        this.setMessTextPosition(xPos+this.messPadding/2,yPos+this.messPadding/2);
    }
}

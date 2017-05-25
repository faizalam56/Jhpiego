package zmq.com.jhpiego.utility;

import android.content.Context;
import android.media.MediaPlayer;


public class MySound {
    public static MediaPlayer mediaPlayer=new MediaPlayer();
    public static boolean isLooping=true;

    public static void playSound(Context context,int uri){
        mediaPlayer=MediaPlayer.create(context,uri);
        mediaPlayer.start();

        mediaPlayer.setLooping(isLooping);
        GlobalVariables.Audio_File_Name=uri;
    }
    public static void stopSound(Context context,int uri){
        if(mediaPlayer.isPlaying()){
         mediaPlayer.stop();
        }
    }

    public static void stopAndPlay(Context context,int filename[]){
        if(filename.length>1){
            stopSound(context,filename[0]);
            playSound(context,filename[1]);
            GlobalVariables.Audio_File_Name=filename[1];
        }
        else{
            playSound(context,filename[0]);
            GlobalVariables.Audio_File_Name=filename[0];
        }

    }

   public static void playSoundOnDemand(Context context,int uri){
       MediaPlayer mediaPlayer=MediaPlayer.create(context,uri);
       mediaPlayer.start();
       mediaPlayer.setLooping(false);
       mediaPlayer.setVolume(100, 100);
       if(!mediaPlayer.isPlaying()){
           //mediaPlayer.stop();
       }
   }

    
}

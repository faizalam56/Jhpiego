package zmq.com.jhpiego.preferences;

import android.content.Context;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by zmq162 on 3/6/15.
 */
public class UpdateScore {

    public static void score(Context context, HashMap<String, Integer> map){

        Set keys = map.keySet();
        Iterator itr = keys.iterator();

        String key;
        Integer value;

        while(itr.hasNext())
        {
            key = (String)itr.next();
            value = (Integer)map.get(key);
            saveScore(context,key,value);
            //System.out.println(key + " - "+ value);
        }

    }

    public static void saveScore(Context context, String key, Integer value){

        if(key.equalsIgnoreCase("relation")){

            updateScore(context, key, value);
            updateScore(context, "relationGrand", 10);
        }
        else if(key.equalsIgnoreCase("risk")){

            updateScore(context, key, value);
            updateScore(context, "riskGrand", 10);
        }
        else if(key.equalsIgnoreCase("impulse")){

            updateScore(context, key, value);
            updateScore(context, "impulseGrand", 10);
        }
        else if(key.equalsIgnoreCase("knowledge")){

            updateScore(context, key, value);
            updateScore(context, "knowledgeGrand", 10);
        }
        else if(key.equalsIgnoreCase("bonus")){

            updateScore(context, key, value);
        }
    }

    public static void updateScore(Context context, String key, Integer value){

        int oldValue = MyPreference.getIntValue(context,key);
        MyPreference.saveIntKeyValue(context, key, (oldValue + value));

        System.out.println("KEY  "+key+ "        "+"Value "+(oldValue + value));
    }

    public static void setModulePath(Context context,String value){

        String oldPAth = MyPreference.getStringValue(context, "path");

        if(oldPAth == null || oldPAth.equalsIgnoreCase("ZMQ")){

            MyPreference.saveStringKeyValue(context, "path", (value));
        }else{
            MyPreference.saveStringKeyValue(context, "path", oldPAth+" / "+(value));
        }
        System.out.println("PATH   "+oldPAth+" / "+(value) );
    }

    public static void setDecisionTime(Context context,String key, String value){

        String oldPAth = MyPreference.getStringValue(context, key);

        if(oldPAth == null || oldPAth.equalsIgnoreCase("ZMQ")){

            MyPreference.saveStringKeyValue(context, key, (value));
        }else{
            MyPreference.saveStringKeyValue(context, key, oldPAth+" ### "+(value));
        }
    }



}

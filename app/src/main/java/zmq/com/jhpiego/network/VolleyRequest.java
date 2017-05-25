package zmq.com.jhpiego.network;

import android.content.Context;
import android.database.Cursor;
import android.telephony.TelephonyManager;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.HashMap;
import java.util.Map;

import zmq.com.jhpiego.database.MyDatabaseAdapter;


/**
 * Created by zmq162 on 14/9/15.
 */
public class VolleyRequest {

    Context context;
    String methodname = "SubmitAndroidData"; //"TestData";//"SubmitAndroidData";
    static  String url = "http://zmqtech.com/Meerachannel.asmx?WSDL";;//"http://zmqtech.com/MobileForMother.asmx?WSDL";
    Map<String,String> params;
    String []keys = {"date","launchTime","sessionsId","startTime","gender","endTime","relation","risk","impulse","knowledge","bonus","path","D1","D2","D3","D4","D5","D6","D7","D8","D9","D10","D11","D12","D13","D14","D15"};



    public VolleyRequest(Context context){
        this.context = context;
        readDataFromDB();
        networkTask();
    }

    public void networkTask(){

    new Thread(){

            String serviceUrl = url;
            String serviceNameSpace = "http://tempuri.org/";
            String soapAction = serviceNameSpace+methodname;
            String methodName = methodname;
            SoapObject request = new SoapObject(serviceNameSpace, methodName);

            public void run() {

        for (int i =0; i<params.size(); i++){

            request.addProperty(keys[i],params.get(keys[i]));
        }


                TelephonyManager mngr = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
                request.addProperty("imino", mngr.getDeviceId());


//            for(int i=0;i<request.getPropertyCount();i++){
//                System.out.println(" " +keys[i]  +" : "+request.getProperty(i));
//            }

            SoapSerializationEnvelope envelope = new  SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);
            HttpTransportSE j2meHttpTransport = new HttpTransportSE(serviceUrl);
            j2meHttpTransport.debug = true;

            try {

                j2meHttpTransport.call(soapAction, envelope);

                SoapObject Response=(SoapObject)envelope.bodyIn;
                System.out.println("RESPONSE NEWWWWWW .... "+Response.toString());

                String Response1=Response.getProperty(0).toString();

                System.out.println("RESPONSE 1 .... "+Response1);

                if(Response1.equalsIgnoreCase("1")){

                    // here we drop the table
                    MyDatabaseAdapter adapter  = new MyDatabaseAdapter(context);

                    adapter.openToWrite();

                    if(adapter.dropTable(MyDatabaseAdapter.DATABASE_TABLE_1)){

                        System.out.println("Table Dropped Successfully.....");

                    }else {
                        System.out.println("Error Occured.....");
                    }
                    adapter.close();

                }

            }catch(Exception e)
            {

                System.out.println("Exception....");
                e.printStackTrace();
            }
            }
             }.start();


    }

   public void  readDataFromDB(){

       MyDatabaseAdapter adapter  = new MyDatabaseAdapter(context);

       adapter.openToRead();


        Cursor cursor = adapter.selectData(MyDatabaseAdapter.DATABASE_TABLE_1, null, null, null, null, null);
        int totalRecords = cursor.getCount();
        String column[] = new String[cursor.getColumnCount()-1] ;

       if(cursor != null){

           if(cursor.moveToFirst()){

               for (int i = 0; i < cursor.getCount(); i++) {

                   for(int j = 1; j < cursor.getColumnCount(); j++){

                       if(i == 0){
                           column[j-1] = cursor.getString(j);
                       }else
                       {
                           column[j-1] += "&"+cursor.getString(j);
                       }
                   }
                   cursor.moveToNext();
               }
           }
           cursor.close();
       }
       adapter.close();

       params = new HashMap<String, String>();

       int k=0;
       for (String s : column){
           params.put(keys[k],column[k]);
           System.out.println("AT " + k + "  " + params.get(keys[k]));
           k++;

       }


   }


}

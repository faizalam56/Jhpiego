package zmq.com.jhpiego.activity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import zmq.com.jhpiego.canvas.Chart;
import zmq.com.jhpiego.database.MyDatabaseAdapter;


/**
 * Created by zmq162 on 15/6/15.
 */
public class PathActivity extends MyActivity {

    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        intent = getIntent();
        getLastRecord();
        setContentView(new Chart(this,intent));
    }


    private void getLastRecord(){



        MyDatabaseAdapter dbAdapter = new MyDatabaseAdapter(this);

        dbAdapter.openToRead();

        // SELECT *         FROM    TABLE        WHERE   ID = (SELECT MAX(ID)  FROM TABLE);

    //    Cursor c = dbAdapter.selectData(MyDatabaseAdapter.DATABASE_TABLE_1, null, null, null,"date DESC","1");
        Cursor c = dbAdapter.getLastRecord();
        int count = c.getCount();

        if (c != null ) {

            c.moveToFirst();

            intent.putExtra("Relation", c.getString(c.getColumnIndex("relation")));
            intent.putExtra("Risk", c.getString(c.getColumnIndex("risk")));
            intent.putExtra("Impulse", c.getString(c.getColumnIndex("impulse")));
            intent.putExtra("Knoledge", c.getString(c.getColumnIndex("knoledge")));
            intent.putExtra("Bonus", c.getString(c.getColumnIndex("bonus")));

            c.close();
        }
        dbAdapter.close();

    }
}
package zmq.com.jhpiego.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import zmq.com.jhpiego.R;

public class ShowScoreActivity extends MyActivity {

    int total;
    ImageView close;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_score);

        init();
    }

    private void init() {

        close = (ImageView) findViewById(R.id.close);
        TextView title = (TextView) findViewById(R.id.title);
        TextView relation = (TextView) findViewById(R.id.relation);
        TextView risk = (TextView) findViewById(R.id.risk);
        TextView impulse = (TextView) findViewById(R.id.impulse);
        TextView knoledge = (TextView) findViewById(R.id.knoledge);
        TextView bonus = (TextView) findViewById(R.id.bonus);
        TextView totalt = (TextView) findViewById(R.id.total);

        //    title.setText("");

        String str = getIntent().getStringExtra("Knoledge");
        System.out.println("Knoledge" + str);

        risk.setText("" + calculatePoint(getIntent().getStringExtra("Risk")));
        relation.setText(""+calculatePoint(getIntent().getStringExtra("Relation")));
        impulse.setText(""+calculatePoint(getIntent().getStringExtra("Impulse")));
        knoledge.setText(""+calculatePoint(getIntent().getStringExtra("Knoledge")));
        bonus.setText(""+calculatePoint(getIntent().getStringExtra("Bonus")));
        totalt.setText(""+total);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                finish();
                Intent intent =new Intent();
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("EXIT",true);
                intent.setClass(ShowScoreActivity.this,MenuScreen.class);
                startActivity(intent);

            }
        });
    }



    private String calculatePoint(String expression) {

        String str[] = expression.split("/");
        int value = Integer.parseInt(str[0]);
        total = total + value;
        return expression;
    }


}

package zmq.com.jhpiego.activity;

        import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.widget.ImageButton;

import zmq.com.jhpiego.R;
import zmq.com.jhpiego.database.ViewpagerAdpter;
        import zmq.com.jhpiego.utility.MySound;

public class Introduction extends MyActivity{

    ViewPager viewPager;
    PagerAdapter adapter;
    ImageButton back,next;
    //TextView title;
    int current;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

        //setContentView(R.layout.introduction_homepage);

//         Intent intent=getIntent();
//         String layoutVal=intent.getStringExtra("switch");
        //if(layoutVal.equals("1")) {
            setContentView(R.layout.activity_introduction);
            back= (ImageButton) findViewById(R.id.imageButtonl);
            next= (ImageButton) findViewById(R.id.imageButtonR);

          back.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  viewPager.setCurrentItem(getItem(-1), true);
              }
          });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(getItem(+1), true);

            }
        });
            final String content[] = {
                    "She is your coolest friend. She's always dating the coolest guys and tuned in to the latest gossip!",
                    "He is your coolest friend. He’s always dating the hottest girl & tuned into the latest gossip",
                    "He is a Brighter Future peer educator. He knows lots about health and relationships and is always willing to listen.",
                    "She is a Brighter Future peer educator. She knows lots about health and relationships and is always willing to listen",
                    "She is everyone's favorite health care worker at the campus clinic. She is cool, non judgmental, keeps confidentiality and always ready to help out.",
                    "She is your favorite auntie and you admire her a lot. She is smart, sophisticated, and easy to talk to."};

            final String title[] = {"Diva Dorcas", "Leo", "Patrick", "Cathy", "Dr.G", "Auntie M"};
            int imageId[] = {R.drawable.introduction_diva, R.drawable.introduction_leo, R.drawable.introduction_patric, R.drawable.introduction_cathy, R.drawable.introduction_counselor, R.drawable.introduction_aunt};


            ImageButton aboutImageButton = (ImageButton) findViewById(R.id.intro_back);
            aboutImageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MySound.playSoundOnDemand(Introduction.this, R.raw.tap_sound);

                    Intent intent = new Intent();
                    intent.setClass(Introduction.this, MenuScreen.class);
                    startActivity(intent);
                    finish();

                }
            });

            viewPager = (ViewPager) findViewById(R.id.pager);
            // Pass results to ViewPagerAdapter Class
            adapter = new ViewpagerAdpter(this, content, title, imageId);
            // Binds the Adapter to the ViewPager
//        adapter.getItemPosition(content);
            viewPager.setAdapter(adapter);
            //title.setText(content[current]);

            System.out.println("Current = " + viewPager.getCurrentItem());

            viewPager.setOnPageChangeListener(new OnPageChangeListener() {

                @Override
                public void onPageSelected(int arg0) {
                    // TODO Auto-generated method stub
                    current = arg0;
                    // title.setText(content[current]);

                    //	Toast.makeText(LearningActivity.this, "Current = "+arg0, Toast.LENGTH_SHORT).show();

                }

                @Override
                public void onPageScrolled(int arg0, float arg1, int arg2) {
                    // TODO Auto-generated method stub

                    System.out.println("onPageScrolled is called");


                }

                @Override
                public void onPageScrollStateChanged(int arg0) {
                    // TODO Auto-generated method stub
                    System.out.println("onPageScrollStateChanged is called ="+arg0);

                    if(viewPager.getCurrentItem()!=0){
                        back.setVisibility(View.VISIBLE);
                    }
                    else{
                        back.setVisibility(View.INVISIBLE);
                    }

                    if(viewPager.getCurrentItem()==5){
                        next.setVisibility(View.INVISIBLE);
                    }
                    else{
                        next.setVisibility(View.VISIBLE);
                    }
                }
            });
       // }


    }
    private int getItem(int i) {
        return viewPager.getCurrentItem() + i;
    }
//    public void switchLayout(View v){
//        finish();
//        Intent intent =new Intent();
//        intent.putExtra("switch","1");
//        intent.setClass(Introduction.this,Introduction.class);
//        startActivity(intent);
//    }
    @Override
    protected void onPause() {
        super.onPause();
    }

}

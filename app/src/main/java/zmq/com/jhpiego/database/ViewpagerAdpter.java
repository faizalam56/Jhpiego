package zmq.com.jhpiego.database;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import zmq.com.jhpiego.R;

public class ViewpagerAdpter extends PagerAdapter {
    // Declare Variables
    Context context;
    String[] content;
    String[]header;
    int[] imageId;
    LayoutInflater inflater;

    public ViewpagerAdpter(Context context, String[] content,String[]header, int[] imageId) {

        this.context = context;
        this.content = content;
        this.header=header;
        this.imageId = imageId;

    }

    @Override
    public int getCount() {

      return content.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((LinearLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {

        // Declare Variables

        TextView txtcontent,char_Name;
        ImageView imgimageId;

        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.page_item, container,
                false);

        txtcontent = (TextView) itemView.findViewById(R.id.content);
        char_Name=  (TextView)itemView.findViewById(R.id.introheader);
        char_Name.setText(header[position]);
        char_Name.setTextColor(Color.YELLOW);
        imgimageId=(ImageView)itemView.findViewById(R.id.imageView);
        txtcontent.setText(content[position]);
        txtcontent.setTextColor(Color.WHITE);
        imgimageId.setImageResource(imageId[position]);
        ((ViewPager) container).addView(itemView);


        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // Remove viewpager_item.xml from ViewPager
        ((ViewPager) container).removeView((LinearLayout) object);

    }
}
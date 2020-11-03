package abartech.mobile.callcenter118.CustomControl;

import java.util.ArrayList;
import abartech.mobile.callcenter118.G;
import abartech.mobile.callcenter118.R;
import abartech.mobile.callcenter118.StructHa;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;


public class AdapterNews extends ArrayAdapter<StructHa> {

    public AdapterNews(ArrayList<StructHa> array) {
        super(G.context, R.layout.item_news, array);

    }


    private static class ViewHolder {

        NewControlTextView txtTitle;
        NewControlTextView txtDate;
        LinearLayout       layNews;

        LinearLayout       layImgNews;

        ImageLoader        imageLoader = G.getInstance().getImageLoader();
        NetworkImageView   thumbNail;


        public ViewHolder(View view) {

            txtTitle = (NewControlTextView) view.findViewById(R.id.txt_title);
            txtDate = (NewControlTextView) view.findViewById(R.id.txt_date);
            layNews = (LinearLayout) view.findViewById(R.id.lay_news);

            layImgNews = (LinearLayout) view.findViewById(R.id.lay_img_news);

            thumbNail = (NetworkImageView) view.findViewById(R.id.img_news);

        }


        public void fill(ArrayAdapter<StructHa> adapter, final StructHa item, int position) {
            //Toast.makeText(G.context, "jj" + item.name, Toast.LENGTH_LONG).show();
            txtTitle.setText(" " + item.name);
            txtDate.setText(" " + item.faliy);
            txtDate.setTag(item.address);
            layNews.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    String url = item.address;
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    G.currentActivity.startActivity(i);
                }
            });
            if (item.imgAddress.equals("") || item.imgAddress == null) {
                Log.i("IMG", "jaber" + item.imgAddress);
                thumbNail.setVisibility(View.GONE);
                layImgNews.setVisibility(View.GONE);
            } else {
                imageLoader.get(item.imgAddress, ImageLoader.getImageListener(thumbNail, R.drawable.loading, R.drawable.image_not_found));

                thumbNail.setImageUrl(item.imgAddress, imageLoader);

            }

        }
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        StructHa item = getItem(position);
        if (convertView == null) {
            convertView = G.inflater.inflate(R.layout.item_news, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.fill(this, item, position);
        Animation animation = AnimationUtils.loadAnimation(G.currentActivity,
                android.R.anim.slide_in_left);
        convertView.startAnimation(animation);
        return convertView;
    }
}
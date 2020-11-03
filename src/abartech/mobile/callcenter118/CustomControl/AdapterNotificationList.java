package abartech.mobile.callcenter118.CustomControl;

import java.util.ArrayList;
import abartech.mobile.callcenter118.G;
import abartech.mobile.callcenter118.R;
import abartech.mobile.callcenter118.ShowNotification;
import abartech.mobile.callcenter118.database.Notification;
import abartech.mobile.callcenter118.database.StrucNotification;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;


public class AdapterNotificationList extends ArrayAdapter<StrucNotification> {

    public AdapterNotificationList(ArrayList<StrucNotification> array) {
        super(G.context, R.layout.item_notific, array);

    }


    private static class ViewHolder {

        NewControlTextView txtTitle;

        LinearLayout       layItem;

        ImageView          imgView;

        ImageLoader        imageLoader = G.getInstance().getImageLoader();
        NetworkImageView   thumbNail;
        NetworkInfo        netInfo;


        public ViewHolder(View view) {

            txtTitle = (NewControlTextView) view.findViewById(R.id.txt_title);
            layItem = (LinearLayout) view.findViewById(R.id.lay_item);
            imgView = (ImageView) view.findViewById(R.id.img_view);

            thumbNail = (NetworkImageView) view.findViewById(R.id.img_notific_do);
            ConnectivityManager cm = (ConnectivityManager) G.currentActivity.getSystemService(Context.CONNECTIVITY_SERVICE);
            netInfo = cm.getActiveNetworkInfo();
        }


        public void fill(ArrayAdapter<StrucNotification> adapter, final StrucNotification item, int position) {
            txtTitle.setText(item.title);

            layItem.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    Intent intentm = new Intent(G.currentActivity, ShowNotification.class);
                    intentm.putExtra("id", item.id);
                    G.currentActivity.startActivity(intentm);

                    Notification notific = new Notification();
                    Log.i("KOK", "" + item.id);
                    notific.setId(item.id);
                    notific.updateView();

                    imgView.setImageResource(R.drawable.view);
                }
            });

            if (item.view == 0) {
                imgView.setImageResource(R.drawable.unview);
            } else {
                imgView.setImageResource(R.drawable.view);
            }

            if ( !item.image.equals("n") && netInfo != null && netInfo.isConnectedOrConnecting()) {
                imageLoader.get(item.image, ImageLoader.getImageListener(thumbNail, R.drawable.loading, R.drawable.image_not_found));

                thumbNail.setImageUrl(item.image, imageLoader);
            } else {
                thumbNail.setVisibility(View.GONE);
            }

        }
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        StrucNotification item = getItem(position);
        if (convertView == null) {
            convertView = G.inflater.inflate(R.layout.item_notific, parent, false);
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
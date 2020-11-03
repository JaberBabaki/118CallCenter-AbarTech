package abartech.mobile.callcenter118.CustomControl;

import java.util.ArrayList;
import abartech.mobile.callcenter118.G;
import abartech.mobile.callcenter118.R;
import abartech.mobile.callcenter118.database.StrucNecessary;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;


public class AdapterNecessaryList extends ArrayAdapter<StrucNecessary> {

    public AdapterNecessaryList(ArrayList<StrucNecessary> array) {
        super(G.context, R.layout.item_necesaary, array);

    }


    private static class ViewHolder {

        NewControlTextView txtTitle;
        NewControlTextView txtNumber;

        LinearLayout       layItem;

        ImageView          imgArrow;


        public ViewHolder(View view) {

            txtTitle = (NewControlTextView) view.findViewById(R.id.txt_title);
            txtNumber = (NewControlTextView) view.findViewById(R.id.txt_number_nec);
            layItem = (LinearLayout) view.findViewById(R.id.lay_item);
            imgArrow = (ImageView) view.findViewById(R.id.img_arrow_faq);

        }


        public void fill(ArrayAdapter<StrucNecessary> adapter, final StrucNecessary item, int position) {
            txtTitle.setText("" + item.title);
            txtNumber.setText("" + item.number);
            imgArrow.setImageResource(R.drawable.call_nes);
            layItem.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    Intent callIntent = new Intent(Intent.ACTION_DIAL);
                    String str = "" + item.number;
                    callIntent.setData(Uri.parse("tel:" + str));
                    G.currentActivity.startActivity(callIntent);
                }
            });

        }
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        StrucNecessary item = getItem(position);
        if (convertView == null) {
            convertView = G.inflater.inflate(R.layout.item_necesaary, parent, false);
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
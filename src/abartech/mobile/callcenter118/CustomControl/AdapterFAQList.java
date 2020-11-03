package abartech.mobile.callcenter118.CustomControl;

import java.util.ArrayList;
import abartech.mobile.callcenter118.G;
import abartech.mobile.callcenter118.R;
import abartech.mobile.callcenter118.database.StrucFAQ;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;


public class AdapterFAQList extends ArrayAdapter<StrucFAQ> {

    public AdapterFAQList(ArrayList<StrucFAQ> array) {
        super(G.context, R.layout.item_faq, array);

    }


    private static class ViewHolder {

        NewControlTextView txtTitle;
        NewControlTextView txtContent;

        LinearLayout       layTitle;
        LinearLayout       layContent;

        LinearLayout       layItem;
        LinearLayout       layItem2;

        ImageView          imgArrow;


        public ViewHolder(View view) {

            txtTitle = (NewControlTextView) view.findViewById(R.id.txt_title);
            txtContent = (NewControlTextView) view.findViewById(R.id.txt_content);

            layTitle = (LinearLayout) view.findViewById(R.id.title);
            layContent = (LinearLayout) view.findViewById(R.id.content);
            layItem = (LinearLayout) view.findViewById(R.id.lay_item);

            imgArrow = (ImageView) view.findViewById(R.id.img_arrow_faq);

        }


        public void fill(ArrayAdapter<StrucFAQ> adapter, final StrucFAQ item, int position) {
            //Toast.makeText(G.context, "jj  " + item.var, Toast.LENGTH_LONG).show();
            txtContent.setLineSpacing(1, (float) 1.25);
            txtTitle.setText(item.id + "- " + item.title);
            txtContent.setText(item.content);

            layTitle.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    if (item.state == 0) {
                        layContent.setVisibility(View.VISIBLE);
                        imgArrow.setImageResource(R.drawable.arrows_faq_l);
                        item.state = 1;
                    } else {
                        imgArrow.setImageResource(R.drawable.arrows_faq_r);
                        layContent.setVisibility(View.GONE);
                        item.state = 0;
                    }

                }
            });
            layContent.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    if (item.state == 0) {
                        layContent.setVisibility(View.VISIBLE);
                        item.state = 1;
                        imgArrow.setImageResource(R.drawable.arrows_faq_l);
                    } else {
                        layContent.setVisibility(View.GONE);
                        imgArrow.setImageResource(R.drawable.arrows_faq_r);
                        item.state = 0;
                    }

                }
            });

            layItem.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    if (item.state == 0) {
                        layContent.setVisibility(View.VISIBLE);
                        item.state = 1;
                    } else {
                        layContent.setVisibility(View.GONE);
                        item.state = 0;
                    }

                }
            });

        }
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        StrucFAQ item = getItem(position);
        if (convertView == null) {
            convertView = G.inflater.inflate(R.layout.item_faq, parent, false);
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
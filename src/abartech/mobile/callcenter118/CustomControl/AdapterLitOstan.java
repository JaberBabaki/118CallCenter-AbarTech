package abartech.mobile.callcenter118.CustomControl;

import java.util.ArrayList;
import abartech.mobile.callcenter118.G;
import abartech.mobile.callcenter118.R;
import abartech.mobile.callcenter118.StructHa;
import abartech.mobile.callcenter118.database.Check;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;


public class AdapterLitOstan extends ArrayAdapter<StructHa> {

    public AdapterLitOstan(ArrayList<StructHa> array) {
        super(G.context, R.layout.item_ostan, array);

    }


    private static class ViewHolder {

        NewControlTextView txtNameStore;


        public ViewHolder(View view) {

            txtNameStore = (NewControlTextView) view.findViewById(R.id.txt_name_ostan);

        }


        public void fill(ArrayAdapter<StructHa> adapter, final StructHa item, int position) {
            //Toast.makeText(G.context, "jj" + item.name, Toast.LENGTH_LONG).show();
            txtNameStore.setText(" " + item.name);
            txtNameStore.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    G.btnShhar.setText(txtNameStore.getText().toString());
                    if (G.dialog3.isShowing()) {
                        G.dialog3.dismiss();
                    }
                    G.btnShharLegal.setText(txtNameStore.getText().toString());

                    Check check = new Check();
                    check.setShahr(txtNameStore.getText().toString());
                    check.updateCity();
                }
            });

        }
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        StructHa item = getItem(position);
        if (convertView == null) {
            convertView = G.inflater.inflate(R.layout.item_ostan, parent, false);
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
package abartech.mobile.callcenter118;

import java.util.ArrayList;
import abartech.mobile.callcenter118.database.Notification;
import abartech.mobile.callcenter118.database.StrucNotification;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;


public class ShowNotification extends Enhance {

    ArrayList<StrucNotification> Data        = new ArrayList<StrucNotification>();
    int                          id;

    ImageLoader                  imageLoader = G.getInstance().getImageLoader();
    NetworkImageView             thumbNail;


    @Override
    protected void onResume() {
        super.onResume();
        G.currentActivity = this;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_notification_root);

        Bundle extras = getIntent().getExtras();
        id = 0;
        if (extras != null) {
            id = extras.getInt("id");
        }
        Notification no = new Notification();
        no.setId(id);
        Data = no.selectOne();

        TextView txtTitleShow = (TextView) findViewById(R.id.txt_title_show);
        TextView txtText = (TextView) findViewById(R.id.txtMainMain);
        TextView txtDate = (TextView) findViewById(R.id.txtDateMain);

        Button btnDelete = (Button) findViewById(R.id.btn_delete_noti);
        ConnectivityManager cm = (ConnectivityManager) G.currentActivity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        thumbNail = (NetworkImageView) findViewById(R.id.img_notific_do_show);
        if ( !Data.get(0).image.equals("n") && netInfo != null && netInfo.isConnectedOrConnecting()) {
            imageLoader.get(Data.get(0).image, ImageLoader.getImageListener(thumbNail, R.drawable.loading, R.drawable.image_not_found));

            thumbNail.setImageUrl(Data.get(0).image, imageLoader);
        } else {
            thumbNail.setVisibility(View.GONE);
        }
        //Toast.makeText(G.context, Data.get(0).title, Toast.LENGTH_LONG).show();
        txtTitleShow.setText("" + Data.get(0).title);
        txtText.setText("" + Data.get(0).text);
        txtDate.setText("" + Data.get(0).date);
        txtText.setLineSpacing(1, (float) 1.25);

        btnDelete.setTypeface(G.font1);
        btnDelete.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                final Dialog dialog2 = new Dialog(G.currentActivity);
                dialog2.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                dialog2.setContentView(R.layout.dialog_delete_noti);
                Button btnBack = (Button) dialog2.findViewById(R.id.btn_back);
                Button btnDeleteOk = (Button) dialog2.findViewById(R.id.btn_delete_ok);
                btnBack.setTypeface(G.font1);
                btnDeleteOk.setTypeface(G.font1);
                btnBack.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View arg0) {
                        dialog2.dismiss();
                    }
                });
                btnDeleteOk.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View arg0) {
                        Notification no = new Notification();
                        no.setId(id);
                        no.deleteOne();
                        dialog2.dismiss();
                        Intent intent = new Intent(G.currentActivity, ListNotification.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        finish();
                        G.currentActivity.startActivity(intent);
                    }
                });
                //dialog2.setCancelable(false);
                dialog2.show();
            }
        });
        final DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_lay);
        ImageView imgMen = (ImageView) findViewById(R.id.img_menu);
        ImageView imgHelp = (ImageView) findViewById(R.id.img_help);
        LinearLayout layAbout = (LinearLayout) findViewById(R.id.lay_about);
        LinearLayout laySite = (LinearLayout) findViewById(R.id.lay_site);
        LinearLayout layShar = (LinearLayout) findViewById(R.id.lay_shar);
        LinearLayout layLogin = (LinearLayout) findViewById(R.id.lay_login);
        LinearLayout layIdea = (LinearLayout) findViewById(R.id.lay_idea);
        LinearLayout layUpdate = (LinearLayout) findViewById(R.id.lay_update);
        TextView txt_about = (TextView) findViewById(R.id.txt_about);
        TextView txtNews = (TextView) findViewById(R.id.txt_news_sl);
        txtNews.setText("اخبار");
        //  txt_about.setText("صفحه اصلی");
        TextView txtIdea = (TextView) findViewById(R.id.txt_idea);
        //  txtIdea.setText("ارسال نظرات");

        ImageView imgHome = (ImageView) findViewById(R.id.img_home);
        imgHome.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(G.currentActivity, Call118Activity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                finish();
                G.currentActivity.startActivity(intent);
            }
        });
        imgMen.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (drawerLayout.isDrawerOpen(Gravity.RIGHT)) {
                    drawerLayout.closeDrawers();
                } else {
                    drawerLayout.openDrawer(Gravity.RIGHT);
                }
            }
        });

        LinearLayout laySurvey = (LinearLayout) findViewById(R.id.lay_survey);
        laySurvey.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                sendNazar(1);
                //  Intent intent = new Intent(G.currentActivity, Call118Activity.class);
                //  intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                //  G.currentActivity.startActivity(intent);
                drawerLayout.closeDrawers();

            }
        });
        LinearLayout layNesessary = (LinearLayout) findViewById(R.id.lay_necessary);
        layNesessary.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(G.currentActivity, NecessaryList.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                finish();
                G.currentActivity.startActivity(intent);
                drawerLayout.closeDrawers();

            }
        });
        layAbout.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // Intent intent = new Intent(G.currentActivity, Call118Activity.class);
                //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                //  G.currentActivity.startActivity(intent);
                drawerLayout.closeDrawers();
            }
        });
        layUpdate.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                drawerLayout.closeDrawers();
                drawerLayout.closeDrawers();
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, G.textShare);
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
            }
        });

        laySite.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                drawerLayout.closeDrawers();
                String url = G.url;
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        layShar.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                drawerLayout.closeDrawers();
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT,
                        "سامانه 118 مازندران:http://cafebazaar.ir/app/abartech.mobile.callcenter118/?l=fa");
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
            }
        });

        layIdea.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                drawerLayout.closeDrawers();
                //  Intent intent = new Intent(G.currentActivity, Call118Activity.class);
                // intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                // G.currentActivity.startActivity(intent);
            }
        });
        layLogin.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                drawerLayout.closeDrawers();
                Intent intent = new Intent(G.currentActivity, NewsPage.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                finish();
                G.currentActivity.startActivity(intent);
            }
        });

        ViewGroup layFiber = (ViewGroup) findViewById(R.id.lay_fibernet);
        layFiber.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                drawerLayout.closeDrawers();
                fibeNet();
            }
        });

        LinearLayout layTelegram = (LinearLayout) findViewById(R.id.lay_telgram);
        layTelegram.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                drawerLayout.closeDrawers();
                telegram();
            }
        });

    }
}

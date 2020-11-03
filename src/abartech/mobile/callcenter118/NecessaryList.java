package abartech.mobile.callcenter118;

import java.util.ArrayList;
import abartech.mobile.callcenter118.CustomControl.AdapterNecessaryList;
import abartech.mobile.callcenter118.database.Necessary;
import abartech.mobile.callcenter118.database.StrucNecessary;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;


public class NecessaryList extends Enhance {

    ArrayList<StrucNecessary> Data = new ArrayList<StrucNecessary>();


    //String P5l;
    @Override
    protected void onResume() {
        super.onResume();
        G.currentActivity = this;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.necessary_root);

        Necessary necessary = new Necessary();
        Data = necessary.getNecessary();
        ListView listView = (ListView) findViewById(R.id.lst_necessary);
        ArrayAdapter adapter = new AdapterNecessaryList(Data);
        //Toast.makeText(G.context, Data.get(1).name, Toast.LENGTH_LONG).show();
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        listView.setTextFilterEnabled(true);

        final DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_lay);
        ImageView imgMen = (ImageView) findViewById(R.id.img_menu);
        ImageView imgHelp = (ImageView) findViewById(R.id.img_help);
        LinearLayout layAbout = (LinearLayout) findViewById(R.id.lay_about);
        LinearLayout laySite = (LinearLayout) findViewById(R.id.lay_site);
        LinearLayout layShar = (LinearLayout) findViewById(R.id.lay_shar);
        LinearLayout layIdea = (LinearLayout) findViewById(R.id.lay_idea);
        LinearLayout layUpdate = (LinearLayout) findViewById(R.id.lay_update);
        TextView txt_about = (TextView) findViewById(R.id.txt_about);
        TextView txtNews = (TextView) findViewById(R.id.txt_news_sl);
        txtNews.setText("اخبار");
        txt_about.setText("درباره ما");
        TextView txtIdea = (TextView) findViewById(R.id.txt_idea);
        // txtIdea.setText("صفحه اصلی");
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
        ImageView imgNotification = (ImageView) findViewById(R.id.img_noti);
        imgNotification.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(G.currentActivity, ListNotification.class);
                finish();
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                G.currentActivity.startActivity(intent);
            }
        });
        ImageView imgHome = (ImageView) findViewById(R.id.img_home);
        imgHome.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(G.currentActivity, Call118Activity.class);
                finish();
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                G.currentActivity.startActivity(intent);
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
                //Intent intent = new Intent(G.currentActivity, NecessaryList.class);
                //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                //finish();
                // G.currentActivity.startActivity(intent);
                drawerLayout.closeDrawers();

            }
        });
        layAbout.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(G.currentActivity, About.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                finish();
                G.currentActivity.startActivity(intent);
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
                // drawerLayout.closeDrawers();
                Intent intent = new Intent(G.currentActivity, Idea.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                finish();
                G.currentActivity.startActivity(intent);
                drawerLayout.closeDrawers();
            }
        });

        ViewGroup layLogin = (ViewGroup) findViewById(R.id.lay_login);
        ViewGroup layFiber = (ViewGroup) findViewById(R.id.lay_fibernet);
        layFiber.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                drawerLayout.closeDrawers();
                fibeNet();
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

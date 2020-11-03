package abartech.mobile.callcenter118;

import abartech.mobile.callcenter118.CustomControl.PagerAdapter;
import abartech.mobile.callcenter118.CustomControl.SlidingTabLayout;
import abartech.mobile.callcenter118.database.Check;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


public class Call118Activity extends Enhance {

    private long backPressTime = 0L;


    @Override
    protected void onResume() {
        super.onResume();
        G.currentActivity = this;
    }


    @Override
    public void onBackPressed() {

        final Check ostan = new Check();
        int y = ostan.getSur();
        Log.i("LOG", y + "val");
        if (y == 1) {

            Toast toast = Toast.makeText(this, "برای خروج یکبار دیگر ضربه بزنید",
                    Toast.LENGTH_SHORT);
            if (backPressTime >= System.currentTimeMillis() - 2000L) {
                if (toast != null)
                    toast.cancel();
                super.onBackPressed();
                finish();
            } else {
                toast.show();
                backPressTime = System.currentTimeMillis();
            }

        } else {
            sendNazar(0);
        }

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_root);

        Intent myIntent = new Intent(G.context, MyReceiver.class);
        myIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(G.context, 0, myIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        G.alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 3000, pendingIntent);

        final DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ImageView imgMen = (ImageView) findViewById(R.id.men);
        ImageView imgHelp = (ImageView) findViewById(R.id.img_help);

        LinearLayout layAbout = (LinearLayout) findViewById(R.id.lay_about);
        LinearLayout laySite = (LinearLayout) findViewById(R.id.lay_site);
        LinearLayout layShar = (LinearLayout) findViewById(R.id.lay_shar);
        LinearLayout layIdea = (LinearLayout) findViewById(R.id.lay_idea);
        LinearLayout layUpdate = (LinearLayout) findViewById(R.id.lay_update);
        LinearLayout layFiber = (LinearLayout) findViewById(R.id.lay_fibernet);
        LinearLayout tel = (LinearLayout) findViewById(R.id.lay_telgram);

        TextView txt_idea = (TextView) findViewById(R.id.txt_idea);
        TextView txt_about = (TextView) findViewById(R.id.txt_about);
        TextView txtNews = (TextView) findViewById(R.id.txt_news_sl);
        txtNews.setText("اخبار");
        txt_about.setText("درباره ما");
        txt_idea.setText("ارسال نظرات");
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
                G.currentActivity.startActivity(intent);
            }
        });

        imgHelp.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent intent = new Intent(G.currentActivity, Help.class);
                G.currentActivity.startActivity(intent);

                //getWel();

                /*Check check = new Check();
                check.updateWel0();*/

                /* final Dialog dialog2 = new Dialog(G.currentActivity);
                 dialog2.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                 dialog2.setContentView(R.layout.dialog_help);
                 Button btn_back = (Button) dialog2.findViewById(R.id.btn_corect_help);
                 btn_back.setTypeface(G.font1);
                 btn_back.setOnClickListener(new OnClickListener() {

                     @Override
                     public void onClick(View arg0) {
                         getNewev();
                         dialog2.dismiss();
                     }
                 });
                 dialog2.setCancelable(false);
                 dialog2.show();*/
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
                G.currentActivity.startActivity(intent);
                drawerLayout.closeDrawers();

            }
        });
        layAbout.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                drawerLayout.closeDrawers();
                Intent intent = new Intent(G.currentActivity, About.class);
                G.currentActivity.startActivity(intent);
            }
        });
        layUpdate.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                drawerLayout.closeDrawers();
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, G.textShare);
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
                //getNew();
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
                        "سامانه 118 مازندران:" +
                                "\n" +
                                " http://cafebazaar.ir/app/abartech.mobile.callcenter118/?l=fa");
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
            }
        });

        layIdea.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                drawerLayout.closeDrawers();
                Intent intent = new Intent(G.currentActivity, Idea.class);
                G.currentActivity.startActivity(intent);
            }
        });

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

        ViewGroup layLogin = (ViewGroup) findViewById(R.id.lay_login);
        layLogin.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                drawerLayout.closeDrawers();
                Intent intent = new Intent(G.currentActivity, NewsPage.class);
                G.currentActivity.startActivity(intent);
            }
        });

        ViewPager vpPager = (ViewPager) findViewById(R.id.view_pager);
        PagerAdapter adapterPage = new PagerAdapter(this, getSupportFragmentManager());

        vpPager.setAdapter(adapterPage);
        SlidingTabLayout sli = (SlidingTabLayout) findViewById(R.id.sliding_tabs);

        sli.setViewPager(vpPager);
        sli.setDividerColors(0);

        vpPager.setCurrentItem(2);
        vpPager.setVerticalScrollBarEnabled(true);

        vpPager.setCurrentItem(adapterPage.getCount());
    }
}
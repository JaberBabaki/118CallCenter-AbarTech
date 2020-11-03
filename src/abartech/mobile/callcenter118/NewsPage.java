package abartech.mobile.callcenter118;

import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import abartech.mobile.callcenter118.CustomControl.AdapterNews;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;


public class NewsPage extends Enhance {

    ArrayList<StructHa> Data = new ArrayList<StructHa>();
    public ListView     listView;


    @Override
    protected void onResume() {
        super.onResume();
        G.currentActivity = this;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_root);
        listView = (ListView) findViewById(R.id.lst_news);
        ImageView imgM = (ImageView) findViewById(R.id.img_menu_news);
        ImageView imgRefresh = (ImageView) findViewById(R.id.img_refresh);

        imgRefresh.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Data.clear();
                reciveNews();
            }
        });

        reciveNews();

        final DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_la);
        LinearLayout layAbout = (LinearLayout) findViewById(R.id.lay_about);
        LinearLayout laySite = (LinearLayout) findViewById(R.id.lay_site);
        LinearLayout layShar = (LinearLayout) findViewById(R.id.lay_shar);
        LinearLayout layIdea = (LinearLayout) findViewById(R.id.lay_idea);
        LinearLayout layUpdate = (LinearLayout) findViewById(R.id.lay_update);
        TextView txt_idea = (TextView) findViewById(R.id.txt_idea);
        TextView txt_about = (TextView) findViewById(R.id.txt_about);
        TextView txtNews = (TextView) findViewById(R.id.txt_news_sl);
        // txtNews.setText("صفحه اصلی");
        txt_about.setText("درباره ما");
        txt_idea.setText("ارسال نظرات");

        imgM.setOnClickListener(new OnClickListener() {

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
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                finish();
                G.currentActivity.startActivity(intent);
            }
        });

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
                drawerLayout.closeDrawers();
                Intent intent = new Intent(G.currentActivity, About.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                finish();
                G.currentActivity.startActivity(intent);
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
                Intent intent = new Intent(G.currentActivity, Idea.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                finish();
                G.currentActivity.startActivity(intent);
            }
        });

        ViewGroup layLogin = (ViewGroup) findViewById(R.id.lay_login);
        layLogin.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                drawerLayout.closeDrawers();
                // Intent intent = new Intent(G.currentActivity, Call118Activity.class);
                // intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                // G.currentActivity.startActivity(intent);
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


    private void showListView() {

        ArrayAdapter adapter = new AdapterNews(Data);
        //Toast.makeText(G.context, Data.get(1).name, Toast.LENGTH_LONG).show();
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        listView.setTextFilterEnabled(true);

    }


    public void reciveNews() {
        ConnectivityManager cm = (ConnectivityManager) G.currentActivity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            recive();
        } else {
            final Dialog dialog2 = new Dialog(this);
            dialog2.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
            dialog2.setContentView(R.layout.dialog_conect);
            TextView txt_header_conect = (TextView) dialog2.findViewById(R.id.txt_header_conected);
            TextView txt_message_conect = (TextView) dialog2.findViewById(R.id.txt_message_conected);
            Button btn_setting = (Button) dialog2.findViewById(R.id.btn_setting);
            Button btn_back = (Button) dialog2.findViewById(R.id.btn_back);
            txt_header_conect.setTypeface(G.font1);
            txt_message_conect.setTypeface(G.font1);
            btn_back.setTypeface(G.font1);
            btn_setting.setTypeface(G.font1);
            btn_setting.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    // img_sign.setVisibility(View.GONE);
                    Intent intent = new Intent(Intent.ACTION_MAIN);
                    intent.setClassName("com.android.settings", "com.android.settings.wifi.WifiSettings");
                    startActivity(intent);
                }
            });
            btn_back.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    // img_sign.setVisibility(View.GONE);
                    dialog2.dismiss();
                }
            });
            dialog2.setCancelable(false);
            dialog2.show();
        }
    }


    public void recive() {
        try {
            final Dialog prg = new Dialog(this);
            prg.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
            prg.setContentView(R.layout.dialog_wait);
            TextView txt_header_conect = (TextView) prg.findViewById(R.id.txt_dialog);
            txt_header_conect.setTypeface(G.font2);
            prg.show();

            String url = G.url + "news.aspx";

            StringRequest postRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>()
                    {

                        @Override
                        public void onResponse(String response) {
                            Log.i("LOG", "" + response);
                            try {
                                JSONArray array = new JSONArray(response);
                                for (int i = 0; i < array.length(); i++) {
                                    StructHa task = new StructHa();
                                    JSONObject object = array.getJSONObject(i);
                                    task.name = object.getString("Title");
                                    task.faliy = object.getString("Date");
                                    task.imgAddress = Html.fromHtml(object.getString("Image")).toString();//object.getString("Image").replace("\u0026", "&");
                                    //String java = 
                                    task.address = object.getString("Url");
                                    //Toast.makeText(G.context, object.getString("Url"), Toast.LENGTH_LONG).show();
                                    Data.add(task);
                                }
                                prg.hide();
                                //Toast.makeText(G.context, "jaber babaki" + Data.size(), Toast.LENGTH_SHORT).show();
                                // prg.setVisibility(View.GONE);
                                showListView();
                            }
                            catch (JSONException e) {
                                prg.hide();
                                e.printStackTrace();
                                //Toast.makeText(G.context, "لطفا دوباره تلاش کنید", Toast.LENGTH_LONG).show();
                            }

                        }
                    },
                    new Response.ErrorListener()
                    {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            prg.hide();
                            //Toast.makeText(G.context, "لطفا دوباره تلاش کنید", Toast.LENGTH_LONG).show();
                        }
                    });
            postRequest.setRetryPolicy((RetryPolicy) new DefaultRetryPolicy(
                    4000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            G.getInstance().addToRequestQueue(postRequest);
        }
        catch (Exception e) {
            Log.i("LOG", " kjk" + e.toString());
        }

    }
}

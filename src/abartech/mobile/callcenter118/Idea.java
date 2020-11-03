package abartech.mobile.callcenter118;

import java.net.URLEncoder;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


public class Idea extends Enhance {

    int    ErrorName   = 0;
    int    ErrorText   = 0;
    int    ErrorChangr = 0;

    String P1l;
    String P2l;
    String P3l;
    String P4l;
    String P5l;


    //String P5l;
    @Override
    protected void onResume() {
        super.onResume();
        G.currentActivity = this;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.idea_root);
        Enhance.getNewev();
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
        //   txtIdea.setText("صفحه اصلی");

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
                finish();
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                G.currentActivity.startActivity(intent);
                drawerLayout.closeDrawers();

            }
        });
        layAbout.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(G.currentActivity, About.class);
                finish();
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
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
                drawerLayout.closeDrawers();
                // Intent intent = new Intent(G.currentActivity, Call118Activity.class);
                //  intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                // G.currentActivity.startActivity(intent);
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
                finish();
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
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

        final EditText edtName = (EditText) findViewById(R.id.edt_name_idea);
        final EditText edtEmail = (EditText) findViewById(R.id.edt_email_idea);
        final EditText edtText = (EditText) findViewById(R.id.edt_text_idea);

        Button btnRegister = (Button) findViewById(R.id.btn_send_idea);

        edtName.setTypeface(G.font2);
        edtEmail.setTypeface(G.font2);
        edtText.setTypeface(G.font2);
        btnRegister.setTypeface(G.font1);

        edtName.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                edtName.setBackgroundResource(R.drawable.selector);
                ErrorName = 0;
                ErrorChangr = 0;

            }


            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                // TODO Auto-generated method stub

            }


            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub

            }
        });
        edtText.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                edtText.setBackgroundResource(R.drawable.selector);
                ErrorText = 0;
                ErrorChangr = 0;
            }


            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                // TODO Auto-generated method stub

            }


            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub

            }
        });

        btnRegister.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Enhance.getNewev();
                Enhance.getimei();
                ConnectivityManager cm = (ConnectivityManager) G.currentActivity.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo netInfo = cm.getActiveNetworkInfo();
                if (netInfo != null && netInfo.isConnectedOrConnecting()) {

                    final String P1 = edtName.getText().toString().trim();
                    final String P2 = edtEmail.getText().toString().trim();
                    //final String P3 = G.phoneNumber;
                    //Toast.makeText(G.context, "number :==" + P3, Toast.LENGTH_SHORT).show();
                    final String P4 = edtText.getText().toString().trim();

                    /* if (P1.length() < 3 && P3.length() >= 0) {
                         ErrorName = 1;
                     }*/
                    if (P4.length() < 5 && P4.length() >= 0) {
                        ErrorText = 1;
                    }

                    if (P1.equals(P1l) && P2.equals(P2l) && P4.equals(P4l)) {
                        ErrorChangr = 1;
                    }

                    final Dialog dialog5 = new Dialog(G.currentActivity);
                    dialog5.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                    dialog5.setContentView(R.layout.dialog_error);
                    TextView txtHeaderError = (TextView) dialog5.findViewById(R.id.txt_header_error);
                    final TextView txtNameError = (TextView) dialog5.findViewById(R.id.txt_name_error);
                    final TextView txtFamilyError = (TextView) dialog5.findViewById(R.id.txt_family_eror);
                    final TextView txtAddressError = (TextView) dialog5.findViewById(R.id.txt_address_error);
                    final TextView txtChangeError = (TextView) dialog5.findViewById(R.id.txt_change_error);
                    Button btnOk = (Button) dialog5.findViewById(R.id.btn_ok);

                    btnOk.setTypeface(G.font1);
                    btnOk.setOnClickListener(new OnClickListener() {

                        @Override
                        public void onClick(View arg0) {
                            txtNameError.setVisibility(View.GONE);
                            txtFamilyError.setVisibility(View.GONE);
                            txtAddressError.setVisibility(View.GONE);
                            txtChangeError.setVisibility(View.GONE);
                            dialog5.dismiss();
                        }
                    });
                    dialog5.setCancelable(false);

                    if (ErrorName == 1) {
                        txtNameError.setVisibility(View.VISIBLE);
                        edtName.setBackgroundResource(R.drawable.selector_error);
                        txtNameError.setText("نام وارد شده حداقل 3 حرفی باید باشد");
                    }
                    if (ErrorText == 1) {
                        txtFamilyError.setVisibility(View.VISIBLE);
                        edtText.setBackgroundResource(R.drawable.selector_error);
                        txtFamilyError.setText("متن نظر باید بیشتر از 5 حرف باشد");
                    }

                    if (ErrorName == 0 && ErrorText == 0 && ErrorChangr == 1) {
                        txtChangeError.setVisibility(View.VISIBLE);
                        //txtChangeError.setBackgroundResource(R.drawable.selector_error);
                        txtChangeError.setText("لطفا داده های ورودی را تغییر دهید");
                    }
                    if (ErrorName == 0 && ErrorText == 0 && ErrorChangr == 0) {
                        recive(P1, P2, P4);
                    } else {
                        dialog5.show();
                    }

                    P1l = P1;
                    P2l = P2;
                    P4l = P4;

                } else {
                    final Dialog dialog2 = new Dialog(G.currentActivity);
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
        });

    }


    public void recive(final String p1, final String p2, final String p3) {
        try {
            final Dialog prg = new Dialog(G.currentActivity);
            prg.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
            prg.setContentView(R.layout.dialog_wait);
            TextView txt_header_conect = (TextView) prg.findViewById(R.id.txt_dialog);
            txt_header_conect.setTypeface(G.font2);
            prg.show();

            String url = G.url + "getmessage.aspx?" +
                    "p1=" + URLEncoder.encode(p1, "UTF-8") +
                    "&p2=" + URLEncoder.encode(p2, "UTF-8") +
                    "&p3=" + URLEncoder.encode(G.GetPhoneNumber, "UTF-8") +
                    "&p4=" + URLEncoder.encode(p3, "UTF-8") +
                    "&p5=" + URLEncoder.encode(G.GetDeviceID, "UTF-8");
            RequestQueue queue = Volley.newRequestQueue(G.context);
            StringRequest postRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>()
                    {

                        @Override
                        public void onResponse(String response) {
                            Log.i("LOG", "" + response);
                            String str = "no";

                            prg.hide();
                            if (response.equals("ok")) {
                                Toast.makeText(G.context, " نظر شما با موفقیت ثبت شد", Toast.LENGTH_SHORT).show();
                            }

                        }
                    },
                    new Response.ErrorListener()
                    {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            prg.hide();
                            Toast.makeText(G.context, "لطفا دوباره تلاش کنید", Toast.LENGTH_LONG).show();
                        }
                    });
            postRequest.setRetryPolicy((RetryPolicy) new DefaultRetryPolicy(
                    4000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            queue.add(postRequest);
        }
        catch (Exception e) {
            Log.i("LOG", " kjk" + e.toString());
        }

    }
}

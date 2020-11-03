package abartech.mobile.callcenter118;

import java.net.URLEncoder;
import abartech.mobile.callcenter118.database.Check;
import android.app.Dialog;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationCompat.Builder;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


public class Enhance extends FragmentActivity {

    private NotificationManager mNotifyManager;
    private Builder             mBuilder;
    int                         id       = 1;

    private int                 veryGoog = 0;
    private int                 Goog     = 0;
    private int                 notBad   = 0;
    private int                 weak     = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    public void noti() {

        mNotifyManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mBuilder = new NotificationCompat.Builder(G.currentActivity);
        mBuilder.setContentTitle("دانلود فایل")
                .setContentText("در حال دریافت...")
                .setSmallIcon(R.drawable.ic_launcher);

        new Downloader().execute();
    }


    private class Downloader extends AsyncTask<Void, Integer, Integer> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            // Displays the progress bar for the first time.
            mBuilder.setProgress(100, 0, false);
            mNotifyManager.notify(id, mBuilder.build());
        }


        @Override
        protected void onProgressUpdate(Integer... values) {
            mBuilder.setProgress(100, values[0], false);
            mNotifyManager.notify(id, mBuilder.build());
            super.onProgressUpdate(values);
        }


        @Override
        protected Integer doInBackground(Void... params) {
            int i;
            for (i = 0; i <= 100; i += 5) {
                // Sets the progress indicator completion percentage
                publishProgress(Math.min(i, 100));
                try {
                    // Sleep for 5 seconds
                    Thread.sleep(2 * 1000);
                }
                catch (InterruptedException e) {
                    Log.d("TAG", "sleep failure");
                }
            }
            return null;
        }


        @Override
        protected void onPostExecute(Integer result) {
            super.onPostExecute(result);
            mBuilder.setContentText("اتمام دانلود");
            // Removes the progress bar
            mBuilder.setProgress(0, 0, false);

            mNotifyManager.notify(id, mBuilder.build());
        }
    }


    public static void getNew() {
        ConnectivityManager cm = (ConnectivityManager) G.context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            try {
                final Dialog prg = new Dialog(G.currentActivity);
                prg.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                prg.setContentView(R.layout.dialog_wait);
                TextView txt_header_conect = (TextView) prg.findViewById(R.id.txt_dialog);
                txt_header_conect.setTypeface(G.font2);
                prg.show();

                String url = G.url + "updateapp.aspx?" +
                        "p1=" + URLEncoder.encode(G.verApp, "UTF-8");

                RequestQueue queue = Volley.newRequestQueue(G.context);
                StringRequest postRequest = new StringRequest(Request.Method.GET, url,
                        new Response.Listener<String>()
                        {

                            @Override
                            public void onResponse(String response) {

                                if ( !response.equals("")) {
                                    // Toast.makeText(G.context, response, Toast.LENGTH_SHORT).show();
                                    String url = response;
                                    Intent i = new Intent(Intent.ACTION_VIEW);
                                    i.setData(Uri.parse(url));
                                    G.currentActivity.startActivity(i);
                                    //  FileDownloader.download(response.toString(), G.VER + "/" + "jaber3.png");
                                    // noti();
                                } else {
                                    Toast.makeText(G.context, "نسخه جدیدی برای دانلود وجود ندارد", Toast.LENGTH_SHORT).show();
                                }
                                prg.hide();
                                //Toast.makeText(G.context, "jaber babaki" + Data.size(), Toast.LENGTH_SHORT).show();
                                // prg.setVisibility(View.GONE);

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
                queue.add(postRequest);
            }
            catch (Exception e) {
                Log.i("LOG", " kjk" + e.toString());
            }
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
                    G.currentActivity.startActivity(intent);
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


    public static void getNewev() {
        ConnectivityManager cm = (ConnectivityManager) G.context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            try {
                final Dialog prg = new Dialog(G.currentActivity);
                prg.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                prg.setContentView(R.layout.dialog_wait);
                TextView txt_header_conect = (TextView) prg.findViewById(R.id.txt_dialog);
                txt_header_conect.setTypeface(G.font2);
                //prg.show();

                String url = G.url + "updateapp.aspx?" +
                        "p1=" + URLEncoder.encode(G.verApp, "UTF-8");

                RequestQueue queue = Volley.newRequestQueue(G.context);
                StringRequest postRequest = new StringRequest(Request.Method.GET, url,
                        new Response.Listener<String>()
                        {

                            @Override
                            public void onResponse(final String response) {

                                if ( !response.equals("")) {
                                    //Toast.makeText(G.context, response, Toast.LENGTH_SHORT).show();

                                    final Dialog dialog2 = new Dialog(G.currentActivity);
                                    dialog2.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                                    dialog2.setContentView(R.layout.dialog_update);
                                    Button btn_back = (Button) dialog2.findViewById(R.id.btn_corect_help);
                                    btn_back.setTypeface(G.font1);
                                    btn_back.setOnClickListener(new OnClickListener() {

                                        @Override
                                        public void onClick(View arg0) {
                                            String url = response;
                                            Intent i = new Intent(Intent.ACTION_VIEW);
                                            i.setData(Uri.parse(url));
                                            G.currentActivity.startActivity(i);
                                            dialog2.dismiss();
                                        }
                                    });
                                    dialog2.setCancelable(false);
                                    dialog2.show();

                                    //  FileDownloader.download(response.toString(), G.VER + "/" + "jaber3.png");
                                    // noti();
                                } else {
                                    // Toast.makeText(G.context, "نسخه جدیدی برای دانلود وجود ندارد", Toast.LENGTH_SHORT).show();
                                }
                                prg.hide();
                                //Toast.makeText(G.context, "jaber babaki" + Data.size(), Toast.LENGTH_SHORT).show();
                                // prg.setVisibility(View.GONE);

                            }
                        },
                        new Response.ErrorListener()
                        {

                            @Override
                            public void onErrorResponse(VolleyError error) {
                                prg.hide();
                                // Toast.makeText(G.context, "لطفا دوباره تلاش کنید", Toast.LENGTH_LONG).show();
                            }
                        });
                queue.add(postRequest);
            }
            catch (Exception e) {
                Log.i("LOG", " kjk" + e.toString());
            }
        } else {

        }

    }


    public static void getimei() {
        final Check ostan = new Check();
        int y = ostan.getLog();
        // Toast.makeText(G.context, "" + y, Toast.LENGTH_SHORT).show();
        if (y == 0) {
            ConnectivityManager cm = (ConnectivityManager) G.context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getActiveNetworkInfo();
            if (netInfo != null && netInfo.isConnectedOrConnecting()) {
                try {
                    final Dialog prg = new Dialog(G.currentActivity);
                    prg.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                    prg.setContentView(R.layout.dialog_wait);
                    TextView txt_header_conect = (TextView) prg.findViewById(R.id.txt_dialog);
                    txt_header_conect.setTypeface(G.font2);
                    // prg.show();

                    String url = G.url + "get_user_info.aspx?" +
                            "&p1=" + URLEncoder.encode(G.GetDeviceID, "UTF-8") +
                            "&p2=" + URLEncoder.encode(G.GetPhoneNumber, "UTF-8") +
                            "&p3=" + URLEncoder.encode(G.getSimOperatorName, "UTF-8") +
                            "&p4=" + URLEncoder.encode(G.GetDeviceSoftwareVersion, "UTF-8") +
                            "&p5=" + URLEncoder.encode(G.GetAndroidVersion, "UTF-8") +
                            "&p6=" + URLEncoder.encode(G.GetSDKVersion, "UTF-8") +
                            "&p7=" + URLEncoder.encode(G.GetSimSerialNumber, "UTF-8") +
                            "&pv=" + URLEncoder.encode(G.verApp, "UTF-8");

                    //Toast.makeText(G.context, G.imei + "  " + G.phoneNumber + "  " + G.operatorName + "  " + G.softWare, Toast.LENGTH_SHORT).show();
                    RequestQueue queue = Volley.newRequestQueue(G.context);
                    StringRequest postRequest = new StringRequest(Request.Method.GET, url,
                            new Response.Listener<String>()
                            {

                                @Override
                                public void onResponse(final String response) {

                                    if ( !response.equals("")) {
                                        //Toast.makeText(G.context, response, Toast.LENGTH_SHORT).show();
                                        ostan.updateLog();
                                        //  FileDownloader.download(response.toString(), G.VER + "/" + "jaber3.png");
                                        // noti();
                                    } else {
                                        //Toast.makeText(G.context, "نسخه جدیدی برای دانلود وجود ندارد", Toast.LENGTH_SHORT).show();
                                    }
                                    prg.hide();
                                    //Toast.makeText(G.context, "jaber babaki" + Data.size(), Toast.LENGTH_SHORT).show();
                                    // prg.setVisibility(View.GONE);

                                }
                            },
                            new Response.ErrorListener()
                            {

                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    prg.hide();
                                    // Toast.makeText(G.context, "لطفا دوباره تلاش کنید", Toast.LENGTH_LONG).show();
                                }
                            });
                    queue.add(postRequest);
                }
                catch (Exception e) {
                    Log.i("LOG", " kjk" + e.toString());
                }
            } else {

            }

        }
    }


    public static void getWel() {
        final Check check = new Check();
        int y = check.getWelcom();
        if (y == 0) {
            // Toast.makeText(G.context, "sghl  " + y, Toast.LENGTH_SHORT).show();
            final Dialog dialogChangeRecent = new Dialog(G.currentActivity);
            dialogChangeRecent.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
            dialogChangeRecent.setContentView(R.layout.dialog_welcom);
            dialogChangeRecent.getOwnerActivity();
            dialogChangeRecent.onBackPressed();
            Button btn_back = (Button) dialogChangeRecent.findViewById(R.id.btn_ok_welcom);
            btn_back.setTypeface(G.font1);
            btn_back.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    check.updateWelcom();
                    dialogChangeRecent.dismiss();
                }
            });

            dialogChangeRecent.setCancelable(false);
            //if (G.dialogChangeRecent.isShowing()) {
            dialogChangeRecent.show();
            //}
        } else {

        }
    }


    public void sendSur(String val) {

        Log.i("LOG", "val");

        final Check ostan = new Check();
        int y = ostan.getSur();
        Log.i("LOG", y + "val");

        ConnectivityManager cm = (ConnectivityManager) G.context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            try {
                Log.i("LOG", "val3");
                String url = G.url + "nazarsanji.aspx?" +
                        "p1=" + URLEncoder.encode(G.GetDeviceID, "UTF-8") +
                        "&p2=" + URLEncoder.encode(val, "UTF-8");

                RequestQueue queue = Volley.newRequestQueue(G.context);
                StringRequest postRequest = new StringRequest(Request.Method.GET, url,
                        new Response.Listener<String>()
                        {

                            @Override
                            public void onResponse(String response) {

                                if (response.equals("ok")) {
                                    Log.i("LOG", response);
                                    ostan.updateSur();

                                    Toast.makeText(G.context, " نظر شما با موفقیت ثبت شد", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(G.context, " اشکال در ثبت نظر", Toast.LENGTH_SHORT).show();
                                }
                                //prg.hide();
                                try {
                                    Thread.sleep(1000);
                                    //int pid = android.os.Process.myPid();
                                    //android.os.Process.killProcess(pid);
                                }
                                catch (InterruptedException e) {
                                    // TODO Auto-generated catch block
                                    e.printStackTrace();
                                }

                                //Toast.makeText(G.context, "jaber babaki" + Data.size(), Toast.LENGTH_SHORT).show();
                                // prg.setVisibility(View.GONE);

                            }
                        },
                        new Response.ErrorListener()
                        {

                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // prg.hide();
                                Toast.makeText(G.context, "لطفا دوباره تلاش کنید", Toast.LENGTH_LONG).show();
                            }
                        });
                queue.add(postRequest);
            }
            catch (Exception e) {
                Log.i("LOG", " kjk" + e.toString());
            }
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
                    G.currentActivity.startActivity(intent);
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


    public void fibeNet() {
        ConnectivityManager cm = (ConnectivityManager) G.context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {

            final Dialog dialog2 = new Dialog(G.currentActivity);
            dialog2.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
            dialog2.setContentView(R.layout.dialog_fiber);
            Button btn_back = (Button) dialog2.findViewById(R.id.btn_down);
            btn_back.setTypeface(G.font1);
            btn_back.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    String url = G.urlFiber;
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    G.currentActivity.startActivity(i);
                    dialog2.dismiss();
                }
            });
            //dialog2.setCancelable(false);
            dialog2.show();
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
                    G.currentActivity.startActivity(intent);
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


    public static boolean openApp(Context context, String packageName) {
        PackageManager manager = context.getPackageManager();
        Intent i = manager.getLaunchIntentForPackage(packageName);
        if (i == null) {
            return false;
            //throw new PackageManager.NameNotFoundException();
        }
        i.addCategory(Intent.CATEGORY_LAUNCHER);
        context.startActivity(i);
        return true;
    }


    public void sendNazar(final int p) {

        final Dialog dialog2 = new Dialog(G.currentActivity);
        dialog2.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog2.setContentView(R.layout.dialog_survey);

        LinearLayout layVeryGood = (LinearLayout) dialog2.findViewById(R.id.lay_very_good);
        LinearLayout layGood = (LinearLayout) dialog2.findViewById(R.id.lay_good);
        LinearLayout layNotBad = (LinearLayout) dialog2.findViewById(R.id.lay_not_bad);
        LinearLayout layWeak = (LinearLayout) dialog2.findViewById(R.id.lay_weak);

        final ImageView imgVeryGood = (ImageView) dialog2.findViewById(R.id.img_very_goood);
        final ImageView imgGood = (ImageView) dialog2.findViewById(R.id.img_goood);
        final ImageView imgNotBad = (ImageView) dialog2.findViewById(R.id.img_not_bad);
        final ImageView imgWeak = (ImageView) dialog2.findViewById(R.id.img_weak);

        Button btnSendIdea = (Button) dialog2.findViewById(R.id.btn_send);
        Button btnExit = (Button) dialog2.findViewById(R.id.btn_exit);

        if (p == 1) {
            btnExit.setText("بازگشت");
        }
        btnSendIdea.setTypeface(G.font1);
        btnExit.setTypeface(G.font1);

        layVeryGood.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                veryGoog = 1;
                Goog = 0;
                notBad = 0;
                weak = 0;

                imgVeryGood.setImageResource(R.drawable.pppp);

                imgWeak.setImageResource(R.drawable.ppp);
                imgGood.setImageResource(R.drawable.ppp);
                imgNotBad.setImageResource(R.drawable.ppp);

            }
        });

        layGood.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                veryGoog = 0;
                Goog = 1;
                notBad = 0;
                weak = 0;

                imgGood.setImageResource(R.drawable.pppp);

                imgVeryGood.setImageResource(R.drawable.ppp);
                imgWeak.setImageResource(R.drawable.ppp);
                imgNotBad.setImageResource(R.drawable.ppp);

            }
        });

        layNotBad.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                veryGoog = 0;
                Goog = 0;
                notBad = 1;
                weak = 0;

                imgNotBad.setImageResource(R.drawable.pppp);

                imgVeryGood.setImageResource(R.drawable.ppp);
                imgWeak.setImageResource(R.drawable.ppp);
                imgGood.setImageResource(R.drawable.ppp);

            }
        });

        layWeak.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                veryGoog = 0;
                Goog = 0;
                notBad = 0;
                weak = 1;

                imgWeak.setImageResource(R.drawable.pppp);

                imgVeryGood.setImageResource(R.drawable.ppp);
                imgGood.setImageResource(R.drawable.ppp);
                imgNotBad.setImageResource(R.drawable.ppp);

            }
        });
        btnExit.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                if ( !(p == 1)) {
                    int pid = android.os.Process.myPid();
                    android.os.Process.killProcess(pid);

                }
                dialog2.dismiss();
            }
        });

        btnSendIdea.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                String val = null;
                if (veryGoog == 1) {
                    val = "1";
                } else if (Goog == 1) {
                    val = "2";
                }
                else if (notBad == 1) {
                    val = "3";

                } else if (weak == 1) {
                    val = "4";
                } else {
                    val = "5";
                }

                if (val.equals("5")) {
                    Toast.makeText(G.context, "لطفا گزینه ای را انتخاب کنید", Toast.LENGTH_SHORT).show();
                } else {

                    Log.i("LOG", val);

                    sendSur(val);

                    dialog2.dismiss();
                    Log.i("LOG", "val");

                }
            }
        });
        //dialog2.setCancelable(false);
        dialog2.show();

    }


    public void telegram() {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://telegram.me/abartech"));
        startActivity(intent);
        /* final Dialog dialog2 = new Dialog(G.currentActivity);
         dialog2.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
         dialog2.setContentView(R.layout.dialog_choice_telegram);
         TextView txt_header_conect = (TextView) dialog2.findViewById(R.id.txt_telegram_nopa);
         TextView txtNopa = (TextView) dialog2.findViewById(R.id.txt_telegram_nopa);
         TextView txtSoft = (TextView) dialog2.findViewById(R.id.txt_telegram_soft);
         TextView txtAbartech = (TextView) dialog2.findViewById(R.id.txt_telegram_abartech);
         Button btnBack = (Button) dialog2.findViewById(R.id.btn_back_telegram);

         btnBack.setTypeface(G.font1);

         txtNopa.setOnClickListener(new OnClickListener() {

             @Override
             public void onClick(View arg0) {
                 Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://telegram.me/startupcompany"));
                 startActivity(intent);
             }
         });

         txtSoft.setOnClickListener(new OnClickListener() {

             @Override
             public void onClick(View arg0) {
                 Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://telegram.me/abarsoft"));
                 startActivity(intent);
             }
         });

         txtAbartech.setOnClickListener(new OnClickListener() {

             @Override
             public void onClick(View arg0) {
                 Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://telegram.me/abartech"));
                 startActivity(intent);
             }
         });

         btnBack.setOnClickListener(new OnClickListener() {

             @Override
             public void onClick(View arg0) {
                 // img_sign.setVisibility(View.GONE);
                 dialog2.dismiss();
             }
         });
         dialog2.setCancelable(false);
         dialog2.show();*/
    }
}

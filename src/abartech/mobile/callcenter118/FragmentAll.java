package abartech.mobile.callcenter118;

import java.net.URLEncoder;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import abartech.mobile.callcenter118.CustomControl.AdapterLitOstan;
import abartech.mobile.callcenter118.CustomControl.AdapterNumberList;
import abartech.mobile.callcenter118.database.Check;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
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


public class FragmentAll extends Fragment {

    ArrayList<StructHa> Data         = new ArrayList<StructHa>();
    ProgressDialog      pDialog;

    LinearLayout        txtNo;
    Button              btnGoHelp;

    ListView            listView;
    LinearLayout        layMain;
    ImageView           imgArrow;
    //ProgressBar         prg;
    int                 chang        = 0;
    String              P1l;
    String              P2l;
    String              P3l;
    String              P4l;
    String              P5l;

    int                 ErrorShar    = 0;
    int                 ErrorName    = 0;
    int                 ErrorFamily  = 0;
    int                 ErrorAddress = 0;
    int                 ErrorChangr  = 0;
    EditText            edtName;
    EditText            edtFamily;
    EditText            edtCity;
    EditText            edtAddress;
    Button              btnSearch;

    ArrayList<StructHa> list         = new ArrayList<StructHa>();
    ArrayList<StructHa> Data2        = new ArrayList<StructHa>();
    String              name         = "";
    ArrayAdapter        adapterList;


    public static Fragment instance() {
        Fragment fregment = new FragmentAll();
        return fregment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup layout = (ViewGroup) inflater.inflate(R.layout.frg_main, null);
        return layout;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        GetData();
        Enhance.getWel();
        Enhance.getNewev();
        Enhance.getimei();
        // Enhance.getWel();
    }


    @Override
    public void onResume() {
        super.onResume();

        GetData();

    }


    public void GetData() {

        btnSearch = (Button) getView().findViewById(R.id.btn_search);
        edtName = (EditText) getView().findViewById(R.id.edt_name);
        edtFamily = (EditText) getView().findViewById(R.id.edt_family);
        // edtCity = (EditText) getView().findViewById(R.id.edt_city);
        edtAddress = (EditText) getView().findViewById(R.id.edt_address);

        listView = (ListView) getView().findViewById(R.id.lst_main);
        layMain = (LinearLayout) getView().findViewById(R.id.lay_main);
        imgArrow = (ImageView) getView().findViewById(R.id.img_arrow);
        G.btnShhar = (Button) getView().findViewById(R.id.btn_shhar);

        // G.btnShharSynce = (Button) getView().findViewById(R.id.btn_shahr_sync);
        // G.btnShharLegal = (Button) getView().findViewById(R.id.btn_shhar_legal);

        G.btnShhar.setTypeface(G.font2);
        txtNo = (LinearLayout) getView().findViewById(R.id.txt_no);
        btnGoHelp = (Button) getView().findViewById(R.id.btn_go_help);
        btnGoHelp.setTypeface(G.font2);
        btnGoHelp.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(G.currentActivity, Help.class);
                G.currentActivity.startActivity(intent);
            }
        });
        G.btnShhar.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                createSpinner();
                ErrorShar = 0;
            }
        });

        Check check = new Check();
        String city = check.getCity();
        if ( !city.equals("n")) {
            G.btnShhar.setText(city);
        }

        edtName.setTypeface(G.font2);
        edtFamily.setTypeface(G.font2);
        edtName.clearFocus();
        edtAddress.setTypeface(G.font2);

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
        edtAddress.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                edtAddress.setBackgroundResource(R.drawable.selector);
                ErrorAddress = 0;
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
        edtFamily.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                // edtFamily.setBackgroundResource(R.drawable.selector);
            }


            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                edtFamily.setBackgroundResource(R.drawable.selector);
                ErrorFamily = 0;
                ErrorChangr = 0;
            }


            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub

            }
        });
        edtAddress.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView arg0, int actionId, KeyEvent arg2) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    search();
                    return true;
                }
                return false;
            }
        });
        btnSearch.setTypeface(G.font2);
        btnSearch.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                search();
            }
        });

    }


    public void search() {
        Enhance.getNewev();
        Enhance.getimei();
        ConnectivityManager cm = (ConnectivityManager) G.currentActivity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            /* ErrorName = 0;
             ErrorFamily = 0;
             ErrorAddress = 0;
             ErrorChangr = 0;*/
            final String P1 = "حقیقی";
            final String P2 = G.btnShhar.getText().toString().trim();
            // Toast.makeText(G.context, G.btnShhar.getText().toString() + "---" + P2, Toast.LENGTH_SHORT).show();
            final String P3 = edtName.getText().toString().trim();
            final String P4 = edtFamily.getText().toString().trim();
            final String P5 = edtAddress.getText().toString().trim();
            if (layMain.getVisibility() == 8) {
                btnSearch.setText("جستجو");
                layMain.setVisibility(View.VISIBLE);
                imgArrow.setImageResource(R.drawable.arrowdu);
            } else {

                if (G.btnShhar.getText().length() == 0) {
                    ErrorShar = 1;
                }

                if (P3.length() < 3 && P3.length() >= 0) {
                    ErrorName = 1;
                }

                if (P4.length() < 3 && P4.length() >= 0) {
                    ErrorFamily = 1;
                }
                /* if (P5.length() < 3 && P5.length() >= 0) {
                     ErrorAddress = 1;
                 }*/

                if ((G.btnShhar.getText().toString().trim().equals(P2l) && P3.equals(P3l) && P4.equals(P4l) && P5.equals(P5l))) {
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
                        // edtAddress.setBackgroundResource(R.drawable.selector);
                        //edtName.setBackgroundResource(R.drawable.selector);
                        //edtFamily.setBackgroundResource(R.drawable.selector);
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
                if (ErrorFamily == 1) {
                    txtFamilyError.setVisibility(View.VISIBLE);
                    edtFamily.setBackgroundResource(R.drawable.selector_error);
                    txtFamilyError.setText("فامیلی وارد شده حداقل 3 حرفی باید باشد");
                }
                if (ErrorAddress == 1) {
                    txtAddressError.setVisibility(View.VISIBLE);
                    edtAddress.setBackgroundResource(R.drawable.selector_error);
                    txtAddressError.setText("آدرس وارد شده حداقل 3 حرفی باید باشد");
                }
                if (ErrorShar == 1) {

                    txtChangeError.setVisibility(View.VISIBLE);
                    txtChangeError.setText("لطفا شهر مورد نظر را انتخاب کنید");
                }

                if (ErrorName == 0 && ErrorFamily == 0 && ErrorAddress == 0 && ErrorChangr == 1) {
                    txtChangeError.setVisibility(View.VISIBLE);
                    //txtChangeError.setBackgroundResource(R.drawable.selector_error);
                    txtChangeError.setText("لطفا داده های ورودی را تغییر دهید");
                }
                if (ErrorName == 0 && ErrorFamily == 0 && ErrorAddress == 0 && ErrorShar == 0) {
                    Data.clear();
                    recive(P1, P2, P3, P4, P5);
                } else {
                    dialog5.show();
                }

            }
            //P1l = P1;
            P2l = G.btnShhar.getText().toString().trim();
            P3l = P3;
            P4l = P4;
            P5l = P5;

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

            //

        }
    }


    private void showListView() {
        if (Data.size() == 0) {
            txtNo.setVisibility(View.VISIBLE);
            listView.setVisibility(View.GONE);
        } else {
            txtNo.setVisibility(View.GONE);
            listView.setVisibility(View.VISIBLE);
            layMain.setVisibility(View.GONE);
            imgArrow.setImageResource(R.drawable.arrowd);
            ArrayAdapter adapter = new AdapterNumberList(Data);
            //Toast.makeText(G.context, Data.get(1).name, Toast.LENGTH_LONG).show();
            listView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            listView.setTextFilterEnabled(true);
            btnSearch.setText("مجدد");

        }
    }


    private void createSpinner() {

        list.clear();
        Data.clear();

        /* StructHa store = new StructHa();
         store.name = "بابل";
         list.add(store);
         StructHa store1 = new StructHa();
         store1.name = "بابلسر";
         list.add(store1);
         StructHa store2 = new StructHa();
         store2.name = "بهنمیر";
         list.add(store2);
         StructHa store3 = new StructHa();
         store3.name = "توابع آمل";
         list.add(store3);
         StructHa store4 = new StructHa();
         store4.name = "آمل";
         list.add(store4);
         StructHa store5 = new StructHa();
         store5.name = "نور";
         list.add(store5);
         StructHa store6 = new StructHa();
         store6.name = "محمود آباد";
         list.add(store6);
         StructHa store7 = new StructHa();
         store7.name = "جويبار";
         list.add(store7);
         StructHa store8 = new StructHa();
         store8.name = "زیرآب";
         list.add(store8);
         StructHa store9 = new StructHa();
         store9.name = "ساری";
         list.add(store9);
         StructHa store10 = new StructHa();
         store10.name = "نکا";
         list.add(store10);
         StructHa store11 = new StructHa();
         store11.name = "چالوس و نوشهر";
         list.add(store11);
         StructHa store12 = new StructHa();
         store12.name = "توابع چالوس";
         list.add(store12);
         StructHa store13 = new StructHa();
         store13.name = "فریدونکار";
         list.add(store13);
         StructHa store14 = new StructHa();
         store14.name = "روستا";
         list.add(store14);
         StructHa store15 = new StructHa();
         store15.name = "رامسر";
         list.add(store15);
         StructHa store16 = new StructHa();
         store16.name = "قائمشهر";
         list.add(store16);
         StructHa store17 = new StructHa();
         store17.name = "بهشهر";
         list.add(store17);
         StructHa store18 = new StructHa();
         store18.name = "رستم کلاه";
         list.add(store18);
         StructHa store19 = new StructHa();
         store19.name = "گلوگاه";
         list.add(store19);
         StructHa store20 = new StructHa();
         store20.name = "تنکابن";
         list.add(store20);
         StructHa store21 = new StructHa();
         store21.name = "هولار";
         list.add(store21);
         StructHa store22 = new StructHa();
         store22.name = "رستم کلا";
         list.add(store22);
         StructHa store445 = new StructHa();
         store445.name = "گلوگاه";
         list.add(store445);
         StructHa store554 = new StructHa();
         store554.name = "ليره سر";
         list.add(store554);
         StructHa store23 = new StructHa();
         store23.name = "كشكو";
         list.add(store23);
         StructHa store64 = new StructHa();
         store64.name = "سه هزار";
         list.add(store64);
         StructHa store24 = new StructHa();
         store24.name = "برسه";
         list.add(store24);
         StructHa store25 = new StructHa();
         store25.name = "نعمت آباد";
         list.add(store25);
         StructHa store26 = new StructHa();
         store26.name = "دوهزار";
         list.add(store26);
         StructHa store27 = new StructHa();
         store27.name = "قلعه گردن";
         list.add(store27);
         StructHa store28 = new StructHa();
         store28.name = "عظيميه";
         list.add(store28);
         StructHa store29 = new StructHa();
         store29.name = "لتاك";
         list.add(store29);
         StructHa store30 = new StructHa();
         store30.name = "شیرود";
         list.add(store30);
         StructHa store31 = new StructHa();
         store31.name = "ولي آباد";
         list.add(store31);
         StructHa store32 = new StructHa();
         store32.name = "سليمان آباد";
         list.add(store32);
         StructHa store33 = new StructHa();
         store33.name = "لشتو";
         list.add(store33);
         StructHa store34 = new StructHa();
         store34.name = "دينار سرا";
         list.add(store34);
         StructHa store35 = new StructHa();
         store35.name = "گل علي آباد";
         list.add(store35);
         StructHa store36 = new StructHa();
         store36.name = "گليجان";
         list.add(store36);
         StructHa store37 = new StructHa();
         store37.name = "ايثارده";
         list.add(store37);
         StructHa store38 = new StructHa();
         store38.name = "جليل آباد";
         list.add(store38);
         StructHa store39 = new StructHa();
         store39.name = "عباس آباد";
         list.add(store39);
         StructHa store40 = new StructHa();
         store40.name = "نشتارود";
         list.add(store40);
         StructHa store41 = new StructHa();
         store41.name = "دراسرا";
         list.add(store41);
         StructHa store42 = new StructHa();
         store42.name = "كترا";
         list.add(store42);
         StructHa store43 = new StructHa();
         store43.name = "كاظم كلا";
         list.add(store43);
         StructHa store44 = new StructHa();
         store44.name = "اسبچين";
         list.add(store44);
         StructHa store45 = new StructHa();
         store45.name = "رودگر محله";
         list.add(store45);
         StructHa store46 = new StructHa();
         store46.name = "فقيه آباد";
         list.add(store46);
         StructHa store47 = new StructHa();
         store47.name = "سفيد آب";
         list.add(store47);
         StructHa store48 = new StructHa();
         store48.name = "سلمانشهر";
         list.add(store48);
         //StructHa store49 = new StructHa();
         // store49.name = "سلمانشهر";
         // list.add(store49);
         StructHa store50 = new StructHa();
         store50.name = "کلارآباد";
         list.add(store50);
         StructHa store51 = new StructHa();
         store51.name = "دانيال";
         list.add(store51);
         StructHa store52 = new StructHa();
         store52.name = "ولكستان";
         list.add(store52);
         */

        StructHa store4 = new StructHa();
        store4.name = "آمل";
        list.add(store4);

        StructHa store = new StructHa();
        store.name = "بابل";
        list.add(store);

        StructHa store1 = new StructHa();
        store1.name = "بابلسر";
        list.add(store1);

        StructHa store17 = new StructHa();
        store17.name = "بهشهر";
        list.add(store17);

        StructHa store2 = new StructHa();
        store2.name = "بهنمیر";
        list.add(store2);

        StructHa store20 = new StructHa();
        store20.name = "تنکابن";
        list.add(store20);

        StructHa store11u = new StructHa();
        store11u.name = "جویبار";
        list.add(store11u);

        StructHa store11 = new StructHa();
        store11.name = "چالوس";
        list.add(store11);

        StructHa store15 = new StructHa();
        store15.name = "رامسر";
        list.add(store15);

        StructHa store9 = new StructHa();
        store9.name = "ساری";
        list.add(store9);

        StructHa store52u = new StructHa();
        store52u.name = "سلمانشهر";
        list.add(store52u);

        StructHa store52 = new StructHa();
        store52.name = "سوادکوه";
        list.add(store52);

        StructHa store39 = new StructHa();
        store39.name = "عباس آباد";
        list.add(store39);

        StructHa store13 = new StructHa();
        store13.name = "فریدون کنار";
        list.add(store13);

        StructHa store16 = new StructHa();
        store16.name = "قائم شهر";
        list.add(store16);

        StructHa store6 = new StructHa();
        store6.name = "محمود آباد";
        list.add(store6);

        StructHa store10 = new StructHa();
        store10.name = "نکاء";
        list.add(store10);

        StructHa store5 = new StructHa();
        store5.name = "نور";
        list.add(store5);

        G.dialog3 = new Dialog(G.currentActivity);
        G.dialog3.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        G.dialog3.setContentView(R.layout.lay_list);
        final ListView cs = (ListView) G.dialog3.findViewById(R.id.lst_st);
        final EditText edtOstan = (EditText) G.dialog3.findViewById(R.id.edt_name_list_shar);
        edtOstan.setHint("انتخاب شهر");
        edtOstan.setTypeface(G.font1);
        edtOstan.clearFocus();
        adapterList = new AdapterLitOstan(list);
        cs.setAdapter(adapterList);
        cs.setTextFilterEnabled(true);
        // dialog3.setCancelable(false);
        G.dialog3.show();
        edtOstan.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int arg1, int arg2, int arg3) {
                String searchString = edtOstan.getText().toString().trim();

                int textLength = searchString.length();

                Data2.clear();
                for (int i = 0; i <= (list.size() - 1); i++) {

                    name = list.get(i).name;

                    if (textLength <= name.length()) {

                        if (searchString.equalsIgnoreCase(name.substring(0, textLength)))
                            Data2.add(list.get(i));
                    }
                }

                adapterList = new AdapterLitOstan(Data2);
                cs.setAdapter(adapterList);
                adapterList.notifyDataSetChanged();
            }


            @Override
            public void beforeTextChanged(CharSequence s, int arg1, int arg2,
                                          int arg3) {

            }


            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }


    private String Search_String(String str) {
        String strRes = "";
        // Toast.makeText(G.context, "شهر" + str, Toast.LENGTH_LONG).show();
        for (int i = 0; i < G.cities.length; i++) {
            // Toast.makeText(G.context, G.cities[i] + "   " + str, Toast.LENGTH_LONG).show();
            if (str.equals(G.cities[i])) {

                strRes = G.city_code[i];
                //Toast.makeText(G.context, strRes, Toast.LENGTH_LONG).show();
            }
        }
        //Toast.makeText(G.context, "کد" + strRes, Toast.LENGTH_LONG).show();
        return strRes;
    }


    public void recive(final String p1, final String p2, final String p3, final String p4, final String p5) {
        try {
            final Dialog prg = new Dialog(G.currentActivity);
            prg.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
            prg.setContentView(R.layout.dialog_wait);
            TextView txt_header_conect = (TextView) prg.findViewById(R.id.txt_dialog);
            txt_header_conect.setTypeface(G.font2);
            prg.show();

            String url = G.url + "118_ver3.aspx?" +
                    "p1=" + URLEncoder.encode(p1, "UTF-8") +
                    "&p2=" + URLEncoder.encode(p2, "UTF-8") +
                    "&p3=" + URLEncoder.encode(p3, "UTF-8") +
                    "&p4=" + URLEncoder.encode(p4, "UTF-8") +
                    "&p5=" + URLEncoder.encode(p5, "UTF-8") +
                    "&pe=" + URLEncoder.encode(G.GetDeviceID, "UTF-8") +
                    "&pv=" + URLEncoder.encode(G.verApp, "UTF-8");
            RequestQueue queue = Volley.newRequestQueue(G.context);
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

                                    task.name = object.getString("Name");
                                    task.faliy = object.getString("Family");
                                    task.address = object.getString("Address");
                                    task.phone = object.getString("Phone1");
                                    task.var = 1;
                                    //Toast.makeText(G.context, object.getString("Address"), Toast.LENGTH_LONG).show();
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
                                Toast.makeText(G.context, "لطفا دوباره تلاش کنید", Toast.LENGTH_LONG).show();
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

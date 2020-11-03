package abartech.mobile.callcenter118;

import java.net.URLEncoder;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import abartech.mobile.callcenter118.CustomControl.AdapterLitOstan;
import abartech.mobile.callcenter118.CustomControl.AdapterLitsActivity;
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
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


public class FragmentLegal extends Fragment {

    ProgressDialog      pDialog;
    ListView            listView;
    LinearLayout        layMain;
    ImageView           imgArrow;
    int                 chang         = 0;

    String              P2l;
    String              P3l;
    String              P4l;

    int                 ErrorNameCity = 0;
    int                 ErrorActivate = 0;
    int                 ErrorChangr   = 0;
    int                 ErrorShar     = 0;

    Button              btnSearchLegal;
    EditText            edtNameArea;
    EditText            edtAddress;

    ArrayList<StructHa> list          = new ArrayList<StructHa>();
    ArrayList<StructHa> Data2         = new ArrayList<StructHa>();
    String              name          = "";
    ArrayAdapter        adapterList;

    ArrayList<StructHa> list1         = new ArrayList<StructHa>();
    ArrayList<StructHa> Data22        = new ArrayList<StructHa>();
    String              name1         = "";
    ArrayAdapter        adapterList1;

    LinearLayout        txtNo;
    Button              btnGoHelp;


    public static Fragment instance() {
        Fragment fregment = new FragmentLegal();
        return fregment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup layout = (ViewGroup) inflater.inflate(R.layout.frg_legal, null);
        return layout;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getData();

    }


    @Override
    public void onResume() {
        super.onResume();
        // showListView();
        getData();
    }


    public void getData() {
        btnSearchLegal = (Button) getView().findViewById(R.id.btn_search_legal);
        edtNameArea = (EditText) getView().findViewById(R.id.edt_name_area);
        edtAddress = (EditText) getView().findViewById(R.id.edt_address_legal);
        G.btnActivate = (Button) getView().findViewById(R.id.edt_active);
        // final EditText edtCityLegal = (EditText) getView().findViewById(R.id.edt_city_legal);
        G.btnShharLegal = (Button) getView().findViewById(R.id.btn_shhar_legal);
        G.btnShharLegal.setTypeface(G.font2);
        G.btnShharLegal.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                createSpinner();
                ErrorShar = 0;
            }
        });
        btnSearchLegal.setTypeface(G.font2);

        edtNameArea.setTypeface(G.font2);
        edtAddress.setTypeface(G.font2);

        G.btnActivate.setTypeface(G.font2);

        Check check = new Check();
        String city = check.getCity();
        if ( !city.equals("n")) {
            G.btnShharLegal.setText(city);
        }

        listView = (ListView) getView().findViewById(R.id.lst_legal);
        layMain = (LinearLayout) getView().findViewById(R.id.lay_legal);

        imgArrow = (ImageView) getView().findViewById(R.id.img_arrow_legal);
        txtNo = (LinearLayout) getView().findViewById(R.id.txt_no_legal);
        btnGoHelp = (Button) getView().findViewById(R.id.btn_go_help);
        btnGoHelp.setTypeface(G.font2);
        btnGoHelp.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(G.currentActivity, Help.class);
                G.currentActivity.startActivity(intent);
            }
        });
        edtNameArea.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                edtNameArea.setBackgroundResource(R.drawable.selector);
                ErrorNameCity = 0;
                ErrorChangr = 0;

            }


            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                ErrorNameCity = 0;
                ErrorChangr = 0;
            }


            @Override
            public void afterTextChanged(Editable arg0) {
                ErrorNameCity = 0;
                ErrorChangr = 0;
            }
        });
        G.btnActivate.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                G.btnActivate.setBackgroundResource(R.drawable.selector);
                ErrorActivate = 0;
                ErrorChangr = 0;

            }


            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                ErrorActivate = 0;
                ErrorChangr = 0;
            }


            @Override
            public void afterTextChanged(Editable arg0) {
                ErrorActivate = 0;
                ErrorChangr = 0;
            }
        });
        G.btnActivate.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                createSpinner2();
                ErrorChangr = 0;
            }
        });
        // edtCityLegal.addTextChangedListener(textWatchert);

        edtNameArea.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView arg0, int actionId, KeyEvent arg2) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    search();
                    return true;
                }
                return false;
            }
        });
        btnSearchLegal.setOnClickListener(new OnClickListener() {

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
            final String P1 = "حقوقی";
            final String P2 = G.btnShharLegal.getText().toString().trim();
            final String P3 = edtNameArea.getText().toString().trim();
            final String P4 = G.btnActivate.getText().toString().trim();
            final String P5 = edtAddress.getText().toString().trim();
            //final String P4 = G.btnActivate.getText().toString().trim();

            if (layMain.getVisibility() == 8) {
                btnSearchLegal.setText("جستجو");
                layMain.setVisibility(View.VISIBLE);
                imgArrow.setImageResource(R.drawable.arrowdu);
            } else {

                if (G.btnShharLegal.getText().length() == 0) {
                    ErrorShar = 1;
                }

                if (P3.length() < 2 && P3.length() >= 0) {
                    ErrorNameCity = 1;
                }
                /* if (P4.length() < 3 && P4.length() >= 0) {
                     ErrorActivate = 1;
                 }*/
                if ((G.btnShharLegal.getText().toString().trim().equals(P2l) && P3.equals(P3l) && P4.equals(P4l))) {
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
                if (ErrorNameCity == 1) {
                    txtNameError.setVisibility(View.VISIBLE);
                    edtNameArea.setBackgroundResource(R.drawable.selector_error);
                    txtNameError.setText("نام وارد شده حداقل 2 حرفی باید باشد");
                }
                if (ErrorActivate == 1) {
                    txtFamilyError.setVisibility(View.VISIBLE);
                    G.btnActivate.setBackgroundResource(R.drawable.selector_error);
                    txtFamilyError.setText("نوع فعالیت وارد شده حداقل 3 حرفی باید باشد");
                }
                if (ErrorShar == 1) {
                    txtChangeError.setVisibility(View.VISIBLE);
                    txtChangeError.setText("لطفا شهر مورد نظر را انتخاب کنید");
                }

                if (ErrorNameCity == 0 && ErrorActivate == 0 && ErrorChangr == 1) {
                    txtChangeError.setVisibility(View.VISIBLE);
                    //txtChangeError.setBackgroundResource(R.drawable.selector_error);
                    txtChangeError.setText("لطفا داده های ورودی را تغییر دهید");
                }
                if (ErrorNameCity == 0 && ErrorActivate == 0 && ErrorShar == 0) {
                    G.DataLegal.clear();
                    recive(P1, P2, P3, P4, P5);
                } else {
                    dialog5.show();
                }
            }
            P2l = G.btnShharLegal.getText().toString().trim();
            P3l = P3;
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
        ///
    }


    private void showListView() {
        if (G.DataLegal.size() == 0) {
            txtNo.setVisibility(View.VISIBLE);
            listView.setVisibility(View.GONE);
        } else {
            txtNo.setVisibility(View.GONE);
            listView.setVisibility(View.VISIBLE);
            layMain.setVisibility(View.GONE);
            imgArrow.setImageResource(R.drawable.arrowd);
            ArrayAdapter adapter = new AdapterNumberList(G.DataLegal);
            //Toast.makeText(G.context, Data.get(1).name, Toast.LENGTH_LONG).show();
            listView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            listView.setTextFilterEnabled(true);
            btnSearchLegal.setText("مجدد");

        }
    }


    private void createSpinner() {
        list.clear();
        Data2.clear();

        /*StructHa store = new StructHa();
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
        list.add(store52);*/

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
        final EditText edtOstan = (EditText) G.dialog3.findViewById(R.id.edt_name_list_shar);
        edtOstan.setHint("انتخاب شهر");
        edtOstan.setTypeface(G.font1);
        final ListView cs = (ListView) G.dialog3.findViewById(R.id.lst_st);

        adapterList = new AdapterLitOstan(list);
        cs.setAdapter(adapterList);
        cs.setTextFilterEnabled(true);

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
        // dialog3.setCancelable(false);
        G.dialog3.show();
    }


    private String Search_String(String str) {
        String strRes = "";
        // Toast.makeText(G.context, "شهر" + str, Toast.LENGTH_LONG).show();
        for (int i = 0; i < G.cities.length; i++) {
            if (str.equals(G.cities[i]))
                strRes = G.city_code[i];
        }
        Toast.makeText(G.context, "کد" + strRes, Toast.LENGTH_LONG).show();
        return strRes;
    }


    public void recive(final String p1, final String p2, final String p3, final String p4, String p5) {
        try {
            //Toast.makeText(G.context, p1 + "  " + p2 + "   " + p3 + "    " + p4, Toast.LENGTH_LONG).show();
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
                                    task.name = object.getString("Family") + " " + object.getString("Name");
                                    //  task.name = object.getString("Name");
                                    task.address = object.getString("Address");
                                    task.phone = object.getString("Phone1");
                                    task.var = 2;
                                    //Toast.makeText(G.context, object.getString("Name"), Toast.LENGTH_LONG).show();
                                    G.DataLegal.add(task);
                                }
                                prg.hide();
                                //Toast.makeText(G.context, "jaber babaki" + Data.size(), Toast.LENGTH_SHORT).show();
                                showListView();
                            }
                            catch (JSONException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                                prg.hide();
                                Toast.makeText(G.context, "لطفا دوباره تلاش کنید", Toast.LENGTH_LONG).show();
                            }

                        }
                    },
                    new Response.ErrorListener()
                    {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            error.printStackTrace();
                            prg.hide();
                            Toast.makeText(G.context, "لطفا دوباره تلاش کنید", Toast.LENGTH_LONG).show();
                        }
                    });

            queue.add(postRequest);
        }
        catch (Exception e) {}

    }


    private void createSpinner2() {

        list1.clear();
        Data22.clear();

        StructHa store = new StructHa();
        store.name = "آتش نشانی";
        list1.add(store);
        StructHa store1 = new StructHa();
        store1.name = "آرایشگاه";
        list1.add(store1);
        StructHa store2 = new StructHa();
        store2.name = "آزمایشگاه";
        list1.add(store2);
        StructHa store3 = new StructHa();
        store3.name = "آستانه";
        list1.add(store3);
        StructHa store4 = new StructHa();
        store4.name = "آشپزخانه مرکزی";
        list1.add(store4);
        StructHa store5 = new StructHa();
        store5.name = "آلومینیم";
        list1.add(store5);
        StructHa store6 = new StructHa();
        store6.name = "آموزشگاه";
        list1.add(store6);
        StructHa store7 = new StructHa();
        store7.name = "آهن فروشی";
        list1.add(store7);
        StructHa store8 = new StructHa();
        store8.name = "ابزاریراق";
        list1.add(store8);
        StructHa store9 = new StructHa();
        store9.name = "اتحادیه";
        list1.add(store9);
        StructHa store10 = new StructHa();
        store10.name = "اتوسرویس-تعویض روغنی";
        list1.add(store10);
        StructHa store11 = new StructHa();
        store11.name = "اجتماعی -انجمن";
        list1.add(store11);
        StructHa store12 = new StructHa();
        store12.name = "اخوند";
        list1.add(store12);
        StructHa store13 = new StructHa();
        store13.name = "اداره";
        list1.add(store13);
        StructHa store14 = new StructHa();
        store14.name = "اداره برق";
        list1.add(store14);
        StructHa store15 = new StructHa();
        store15.name = "اردوگاه";
        list1.add(store15);
        StructHa store16 = new StructHa();
        store16.name = "اطوشویی-خشکشویی";
        list1.add(store16);
        StructHa store17 = new StructHa();
        store17.name = "اغذیه فروشی";
        list1.add(store17);
        StructHa store18 = new StructHa();
        store18.name = "الکتریکی";
        list1.add(store18);
        StructHa store19 = new StructHa();
        store19.name = "انبارها";
        list1.add(store19);
        StructHa store20 = new StructHa();
        store20.name = "انواع تعمیرگاه";
        list1.add(store20);
        StructHa store21 = new StructHa();
        store21.name = "اورژانس";
        list1.add(store21);
        StructHa store22 = new StructHa();
        store22.name = "ایرانیت فروشی";
        list1.add(store22);
        StructHa store445 = new StructHa();
        store445.name = "ایزوگام";
        list1.add(store445);
        StructHa store554 = new StructHa();
        store554.name = "باربری";
        list1.add(store554);
        StructHa store23 = new StructHa();
        store23.name = "باسکول";
        list1.add(store23);
        StructHa store64 = new StructHa();
        store64.name = "باشگاها";
        list1.add(store64);
        StructHa store24 = new StructHa();
        store24.name = "باطری سازی";
        list1.add(store24);
        StructHa store25 = new StructHa();
        store25.name = "بانک";
        list1.add(store25);
        StructHa store26 = new StructHa();
        store26.name = "بخشداری";
        list1.add(store26);
        StructHa store27 = new StructHa();
        store27.name = "برنج فروشی";
        list1.add(store27);
        StructHa store28 = new StructHa();
        store28.name = "بلوک سازی";
        list1.add(store28);
        StructHa store29 = new StructHa();
        store29.name = "بنگاه";
        list1.add(store29);
        StructHa store30 = new StructHa();
        store30.name = "بنیاد15خرداد";
        list1.add(store30);
        StructHa store31 = new StructHa();
        store31.name = "بنیادشهید";
        list1.add(store31);
        StructHa store32 = new StructHa();
        store32.name = "بنیادمسکن";
        list1.add(store32);
        StructHa store33 = new StructHa();
        store33.name = "بهداری -بهداشت";
        list1.add(store33);
        StructHa store34 = new StructHa();
        store34.name = "بهزیستی";
        list1.add(store34);
        StructHa store35 = new StructHa();
        store35.name = "بوتیک";
        list1.add(store35);
        StructHa store36 = new StructHa();
        store36.name = "بیمارستان";
        list1.add(store36);
        StructHa store37 = new StructHa();
        store37.name = "بیمه";
        list1.add(store37);
        StructHa store38 = new StructHa();
        store38.name = "پارچه فروشی";
        list1.add(store38);
        StructHa store39 = new StructHa();
        store39.name = "پارکینگ";
        list1.add(store39);
        StructHa store40 = new StructHa();
        store40.name = "پاساژ";
        list1.add(store40);
        StructHa store41 = new StructHa();
        store41.name = "پایگاه";
        list1.add(store41);
        StructHa store42 = new StructHa();
        store42.name = "رستوران";
        list1.add(store42);
        StructHa store43 = new StructHa();
        store43.name = "پرده فروشی";
        list1.add(store43);
        StructHa store44 = new StructHa();
        store44.name = "پست";
        list1.add(store44);
        StructHa store45 = new StructHa();
        store45.name = "پلاژ";
        list1.add(store45);
        StructHa store46 = new StructHa();
        store46.name = "پلیس راه";
        list1.add(store46);
        StructHa store47 = new StructHa();
        store47.name = "پمپ بنزین ها";
        list1.add(store47);
        StructHa store48 = new StructHa();
        store48.name = "پمپ فروشی";
        list1.add(store48);
        StructHa store49 = new StructHa();
        store49.name = "پنچری-آپاراتی";
        list1.add(store49);
        StructHa store50 = new StructHa();
        store50.name = "پیتزافروشی ها";
        list1.add(store50);
        StructHa store51 = new StructHa();
        store51.name = "تابلوسازی";
        list1.add(store51);
        StructHa store52 = new StructHa();
        store52.name = "تاسیسات";
        list1.add(store52);
        StructHa store53 = new StructHa();
        store53.name = "تاکسی تلفنی";
        list1.add(store53);
        StructHa store54 = new StructHa();
        store54.name = "تبلیغات";
        list1.add(store54);
        StructHa store55 = new StructHa();
        store55.name = "تخلیه چاه -لوله بازکنی";
        list1.add(store55);
        StructHa store56 = new StructHa();
        store56.name = "تراشکاری";
        list1.add(store56);
        StructHa store57 = new StructHa();
        store57.name = "تربیت بدنی وباشگاه ها";
        list1.add(store57);
        StructHa store58 = new StructHa();
        store58.name = "تزریقات";
        list1.add(store58);
        StructHa store59 = new StructHa();
        store59.name = "تعاونی روستایی";
        list1.add(store59);
        StructHa store60 = new StructHa();
        store60.name = "تیپاكس ها";
        list1.add(store60);
        StructHa store61 = new StructHa();
        store61.name = "جرثقیل";
        list1.add(store61);
        StructHa store62 = new StructHa();
        store62.name = "جمعیت هلال احمر";
        list1.add(store62);
        StructHa store63 = new StructHa();
        store63.name = "جهاد";
        list1.add(store63);
        StructHa store65 = new StructHa();
        store65.name = "جوشکاری";
        list1.add(store65);
        StructHa store66 = new StructHa();
        store66.name = "چاپخانه";
        list1.add(store66);
        StructHa store67 = new StructHa();
        store67.name = "چلوکبابی";
        list1.add(store67);
        StructHa store68 = new StructHa();
        store68.name = "چوبفروشی ونجاری";
        list1.add(store68);
        StructHa store69 = new StructHa();
        store69.name = "حج واوقاف وامورخیریه";
        list1.add(store69);
        StructHa store70 = new StructHa();
        store70.name = "حفاظت محیطزیست";
        list1.add(store70);
        StructHa store71 = new StructHa();
        store71.name = "حوزه";
        list1.add(store71);
        StructHa store72 = new StructHa();
        store72.name = "خیاطی";
        list1.add(store72);
        StructHa store73 = new StructHa();
        store73.name = "دادگستری";
        list1.add(store73);
        StructHa store74 = new StructHa();
        store74.name = "داربست";
        list1.add(store74);
        StructHa store75 = new StructHa();
        store75.name = "داروخانه";
        list1.add(store75);
        StructHa store76 = new StructHa();
        store76.name = "دامپزشکی ودامپزشکان";
        list1.add(store76);
        StructHa store77 = new StructHa();
        store77.name = "دانشگاه";
        list1.add(store77);
        StructHa store78 = new StructHa();
        store78.name = "دبیرها";
        list1.add(store78);
        StructHa store79 = new StructHa();
        store79.name = "درمانگاه";
        list1.add(store79);
        StructHa store80 = new StructHa();
        store80.name = "دزدگیر";
        list1.add(store80);
        StructHa store81 = new StructHa();
        store81.name = "دفترازدواج و طلاق";
        list1.add(store81);
        StructHa store82 = new StructHa();
        store82.name = "دفتراسنادرسمی";
        list1.add(store82);
        StructHa store83 = new StructHa();
        store83.name = "دفترامام جمعه";
        list1.add(store83);
        StructHa store84 = new StructHa();
        store84.name = "دفترخانه";
        list1.add(store84);
        StructHa store85 = new StructHa();
        store85.name = "دفترشرکت";
        list1.add(store85);
        StructHa store86 = new StructHa();
        store86.name = "دندانسازی";
        list1.add(store86);

        StructHa storeq = new StructHa();
        storeq.name = "دکتر";
        list1.add(storeq);
        StructHa storeq1 = new StructHa();
        storeq1.name = "رادیاتورسازی";
        list1.add(storeq1);
        StructHa storeq2 = new StructHa();
        storeq2.name = "رادیولوژی";
        list1.add(storeq2);
        StructHa storeq3 = new StructHa();
        storeq3.name = "راهنمایی ورانندگی";
        list1.add(storeq3);
        StructHa storeq4 = new StructHa();
        storeq4.name = "رستورانت";
        list1.add(storeq4);
        StructHa storeq5 = new StructHa();
        storeq5.name = "رنگ فروشی";
        list1.add(storeq5);
        StructHa storeq6 = new StructHa();
        storeq6.name = "روزنامه";
        list1.add(storeq6);
        StructHa storeq7 = new StructHa();
        storeq7.name = "زرگری";
        list1.add(storeq7);
        StructHa storeq8 = new StructHa();
        storeq8.name = "زندان";
        list1.add(storeq8);
        StructHa storeq9 = new StructHa();
        storeq9.name = "سازمانها";
        list1.add(storeq9);
        StructHa storeq10 = new StructHa();
        storeq10.name = "ساعت سازی";
        list1.add(storeq10);
        StructHa storeq11 = new StructHa();
        storeq11.name = "سپاه";
        list1.add(storeq11);
        StructHa storeq12 = new StructHa();
        storeq12.name = "ستاد";
        list1.add(storeq12);
        StructHa storeq13 = new StructHa();
        storeq13.name = "سردخانه";
        list1.add(storeq13);
        StructHa storeq14 = new StructHa();
        storeq14.name = "سم فروشی";
        list1.add(storeq14);
        StructHa storeq15 = new StructHa();
        storeq15.name = "سنگ فروشی";
        list1.add(storeq15);
        StructHa storeq16 = new StructHa();
        storeq16.name = "سوپرلبنیات";
        list1.add(storeq16);
        StructHa storeq17 = new StructHa();
        storeq17.name = "سوپرمارکت";
        list1.add(storeq17);
        StructHa storeq18 = new StructHa();
        storeq18.name = "سینما";
        list1.add(storeq18);
        StructHa storeq19 = new StructHa();
        storeq19.name = "شالیکوبی";
        list1.add(storeq19);
        StructHa storeq20 = new StructHa();
        storeq20.name = "شرکت";
        list1.add(storeq20);
        StructHa storeq21 = new StructHa();
        storeq21.name = "شرکت تعاونی";
        list1.add(storeq21);
        StructHa storeq22 = new StructHa();
        storeq22.name = "شرکت خدماتی";
        list1.add(storeq22);
        StructHa storeq445 = new StructHa();
        storeq445.name = "شرکت ساختمانی";
        list1.add(storeq445);
        StructHa storeq554 = new StructHa();
        storeq554.name = "شرکت گاز";
        list1.add(storeq554);
        StructHa storeq23 = new StructHa();
        storeq23.name = "شرکت نفت";
        list1.add(storeq23);
        StructHa storeq64 = new StructHa();
        storeq64.name = "شهرداری";
        list1.add(storeq64);
        StructHa storeq24 = new StructHa();
        storeq24.name = "شهرک";
        list1.add(storeq24);
        StructHa storeq25 = new StructHa();
        storeq25.name = "شورا-دهیاری";
        list1.add(storeq25);
        StructHa storeq26 = new StructHa();
        storeq26.name = "شوفاژکاری";
        list1.add(storeq26);
        StructHa storeq27 = new StructHa();
        storeq27.name = "شیشه بری";
        list1.add(storeq27);
        StructHa storeq28 = new StructHa();
        storeq28.name = "صداوسیما";
        list1.add(storeq28);
        StructHa storeq29 = new StructHa();
        storeq29.name = "صنایع";
        list1.add(storeq29);
        StructHa storeq30 = new StructHa();
        storeq30.name = "صندوق قرض الحسنه";
        list1.add(storeq30);
        StructHa storeq31 = new StructHa();
        storeq31.name = "ظروف کرایه";
        list1.add(storeq31);
        StructHa storeq32 = new StructHa();
        storeq32.name = "عطاری";
        list1.add(storeq32);
        StructHa storeq33 = new StructHa();
        storeq33.name = "عینک فروشی";
        list1.add(storeq33);
        StructHa storeq34 = new StructHa();
        storeq34.name = "عکاسی";
        list1.add(storeq34);
        StructHa storeq35 = new StructHa();
        storeq35.name = "فتوکپی";
        list1.add(storeq35);
        StructHa storeq36 = new StructHa();
        storeq36.name = "فرش فروشی";
        list1.add(storeq36);
        StructHa storeq37 = new StructHa();
        storeq37.name = "فرمانداری";
        list1.add(storeq37);
        StructHa storeq38 = new StructHa();
        storeq38.name = "فروشگاه";
        list1.add(storeq38);
        StructHa storeq39 = new StructHa();
        storeq39.name = "فیزیوتراپی";
        list1.add(storeq39);

        StructHa storeq40 = new StructHa();
        storeq40.name = "قالیشویی";
        list1.add(storeq40);
        StructHa storeq41 = new StructHa();
        storeq41.name = "قالیشویی";
        list1.add(storeq41);
        StructHa storeq42 = new StructHa();
        storeq42.name = "قصابی";
        list1.add(storeq42);
        StructHa storeq43 = new StructHa();
        storeq43.name = "قنادی";
        list1.add(storeq43);
        StructHa storeq44 = new StructHa();
        storeq44.name = "قهوه خانه";
        list1.add(storeq44);
        StructHa storeq45 = new StructHa();
        storeq45.name = "گالری";
        list1.add(storeq45);
        StructHa storeq46 = new StructHa();
        storeq46.name = "گرمابه";
        list1.add(storeq46);
        StructHa storeq47 = new StructHa();
        storeq47.name = "گلخانه یاگلفروشی";
        list1.add(storeq47);
        StructHa storeq48 = new StructHa();
        storeq48.name = "لابراتوار-بهداشتی";
        list1.add(storeq48);
        StructHa storeq49 = new StructHa();
        storeq49.name = "لاستیک فروشی";
        list1.add(storeq49);
        StructHa storeq50 = new StructHa();
        storeq50.name = "لنت کوبی";
        list1.add(storeq50);
        StructHa storeq51 = new StructHa();
        storeq51.name = "لوازم التحریر";
        list1.add(storeq51);
        StructHa storeq52 = new StructHa();
        storeq52.name = "لوازم بهداشتی";
        list1.add(storeq52);
        StructHa storeq53 = new StructHa();
        storeq53.name = "لوازم خانگی";
        list1.add(storeq53);
        StructHa storeq54 = new StructHa();
        storeq54.name = "لوازم ساختمانی";
        list1.add(storeq54);
        StructHa storeq55 = new StructHa();
        storeq55.name = "لوازم یدکی";
        list1.add(storeq55);
        StructHa storeq56 = new StructHa();
        storeq56.name = "لوازم کشاورزی";
        list1.add(storeq56);
        StructHa storeq57 = new StructHa();
        storeq57.name = "لوله فروشی";
        list1.add(storeq57);
        StructHa storeq58 = new StructHa();
        storeq58.name = "لیتوگرافی";
        list1.add(storeq58);
        StructHa storeq59 = new StructHa();
        storeq59.name = "ماهی فروشی";
        list1.add(storeq59);
        StructHa storeq60 = new StructHa();
        storeq60.name = "مبل فروشی";
        list1.add(storeq60);
        StructHa storeq61 = new StructHa();
        storeq61.name = "مجتمع";
        list1.add(storeq61);
        StructHa storeq62 = new StructHa();
        storeq62.name = "مجمع امورصنفی";
        list1.add(storeq62);
        StructHa storeq63 = new StructHa();
        storeq63.name = "مدرسه";
        list1.add(storeq63);
        StructHa storeq65 = new StructHa();
        storeq65.name = "مرغ فروشی";
        list1.add(storeq65);
        StructHa storeq66 = new StructHa();
        storeq66.name = "مزون عروس";
        list1.add(storeq66);
        StructHa storeq67 = new StructHa();
        storeq67.name = "مسافرخانه";
        list1.add(storeq67);
        StructHa storeq68 = new StructHa();
        storeq68.name = "مسجد";
        list1.add(storeq68);
        StructHa storeq69 = new StructHa();
        storeq69.name = "مصالح فروشی";
        list1.add(storeq69);
        StructHa storeq70 = new StructHa();
        storeq70.name = "مغازه";
        list1.add(storeq70);
        StructHa storeq71 = new StructHa();
        storeq71.name = "مهدکودک";
        list1.add(storeq71);
        StructHa storeq72 = new StructHa();
        storeq72.name = "مهمان سرا";
        list1.add(storeq72);
        StructHa storeq73 = new StructHa();
        storeq73.name = "موبایل فروشی";
        list1.add(storeq73);
        StructHa storeq74 = new StructHa();
        storeq74.name = "موسسه";
        list1.add(storeq74);
        StructHa storeq75 = new StructHa();
        storeq75.name = "مینی بوس -سواری";
        list1.add(storeq75);
        StructHa storeq76 = new StructHa();
        storeq76.name = "میوه فروشی";
        list1.add(storeq76);
        StructHa storeq77 = new StructHa();
        storeq77.name = "نانوایی";
        list1.add(storeq77);
        StructHa storeq78 = new StructHa();
        storeq78.name = "نگارخانه";
        list1.add(storeq78);
        StructHa storeq79 = new StructHa();
        storeq79.name = "نگهبانی";
        list1.add(storeq79);
        StructHa storeq80 = new StructHa();
        storeq80.name = "نمایشگاه ماشین";
        list1.add(storeq80);
        StructHa storeq81 = new StructHa();
        storeq81.name = "نمایندگی";
        list1.add(storeq81);
        StructHa storeq82 = new StructHa();
        storeq82.name = "نهادها";
        list1.add(storeq82);
        StructHa storeq83 = new StructHa();
        storeq83.name = "نیروی انتظامی";
        list1.add(storeq83);
        StructHa storeq84 = new StructHa();
        storeq84.name = "هتل -تالار";
        list1.add(storeq84);

        StructHa storeqq60 = new StructHa();
        storeqq60.name = "همکارمخابراتی";
        list1.add(storeqq60);
        StructHa storeqq61 = new StructHa();
        storeqq61.name = "هنرستان";
        list1.add(storeqq61);
        StructHa storeqq62 = new StructHa();
        storeqq62.name = "هواپیمایی";
        list1.add(storeqq62);
        StructHa storeqq63 = new StructHa();
        storeqq63.name = "هییت -گروهها";
        list1.add(storeqq63);
        StructHa storeqq65 = new StructHa();
        storeqq65.name = "واشرسازی";
        list1.add(storeqq65);
        StructHa storeqq66 = new StructHa();
        storeqq66.name = "واطلاعات ضروری شهری";
        list1.add(storeqq66);
        StructHa storeqq67 = new StructHa();
        storeqq67.name = "ویدیوکلوپ";
        list1.add(storeqq67);
        StructHa storeqq68 = new StructHa();
        storeqq68.name = "وکلا";
        list1.add(storeqq68);
        StructHa storeqq69 = new StructHa();
        storeqq69.name = "کابینت سازی";
        list1.add(storeqq69);
        StructHa storeqq70 = new StructHa();
        storeqq70.name = "کارخانه";
        list1.add(storeqq70);
        StructHa storeqq71 = new StructHa();
        storeqq71.name = "کارگاه";
        list1.add(storeqq71);
        StructHa storeqq72 = new StructHa();
        storeqq72.name = "کارواش";
        list1.add(storeqq72);
        StructHa storeqq73 = new StructHa();
        storeqq73.name = "کاشی فروشی";
        list1.add(storeqq73);
        StructHa storeqq74 = new StructHa();
        storeqq74.name = "کامپیوتر";
        list1.add(storeqq74);
        StructHa storeqq75 = new StructHa();
        storeqq75.name = "کانون";
        list1.add(storeqq75);
        StructHa storeqq76 = new StructHa();
        storeqq76.name = "کتابخانه -فروشی";
        list1.add(storeqq76);
        StructHa storeqq77 = new StructHa();
        storeqq77.name = "کد استان";
        list1.add(storeqq77);
        StructHa storeqq78 = new StructHa();
        storeqq78.name = "کفش فروشی-کیف";
        list1.add(storeqq78);
        StructHa storeqq79 = new StructHa();
        storeqq79.name = "نگهبانی";
        list1.add(storeqq79);
        StructHa storeqq80 = new StructHa();
        storeqq80.name = "کلیدسازی";
        list1.add(storeqq80);
        StructHa storeqq81 = new StructHa();
        storeqq81.name = "کمیته امداد";
        list1.add(storeqq81);

        G.dialog4 = new Dialog(G.currentActivity);
        G.dialog4.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        G.dialog4.setContentView(R.layout.lay_list);
        final ListView cs = (ListView) G.dialog4.findViewById(R.id.lst_st);
        final EditText edtOstan = (EditText) G.dialog4.findViewById(R.id.edt_name_list_shar);
        edtOstan.setHint("نوع فعالیت");
        edtOstan.setTypeface(G.font1);
        adapterList1 = new AdapterLitsActivity(list1);
        cs.setAdapter(adapterList1);
        cs.setTextFilterEnabled(true);
        edtOstan.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int arg1, int arg2, int arg3) {
                String searchString = edtOstan.getText().toString().trim();

                int textLength = searchString.length();

                Data22.clear();
                for (int i = 0; i <= (list1.size() - 1); i++) {

                    name = list1.get(i).name;

                    if (textLength <= name.length()) {

                        if (searchString.equalsIgnoreCase(name.substring(0, textLength)))
                            Data22.add(list1.get(i));
                    }
                }

                adapterList1 = new AdapterLitsActivity(Data22);
                cs.setAdapter(adapterList1);
                adapterList1.notifyDataSetChanged();
            }


            @Override
            public void beforeTextChanged(CharSequence s, int arg1, int arg2,
                                          int arg3) {

            }


            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        // dialog3.setCancelable(false);
        G.dialog4.show();
    }
}

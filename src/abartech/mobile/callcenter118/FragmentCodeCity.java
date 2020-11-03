package abartech.mobile.callcenter118;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import abartech.mobile.callcenter118.CustomControl.AdapterNumberList;
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


public class FragmentCodeCity extends Fragment {

    ArrayList<StructHa> Data        = new ArrayList<StructHa>();
    ProgressDialog      pDialog;

    LinearLayout        txtNo;
    Button              btnGoHelp;

    ListView            listView;
    LinearLayout        layCity;
    ImageView           imgArrow;
    int                 chang       = 0;

    String              P2l;

    int                 ErrorName   = 0;
    int                 ErrorChangr = 0;

    EditText            edtNameCity;


    public static Fragment instance() {
        Fragment fregment = new FragmentCodeCity();
        return fregment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup layout = (ViewGroup) inflater.inflate(R.layout.frg_code_city, null);
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
        getData();
    }


    public void getData() {

        Button btnSearchCityOstan = (Button) getView().findViewById(R.id.btn_search_city_ostan);
        edtNameCity = (EditText) getView().findViewById(R.id.edt_name_city_ostan);

        edtNameCity.setTypeface(G.font2);

        btnSearchCityOstan.setTypeface(G.font2);

        listView = (ListView) getView().findViewById(R.id.lst_city);
        layCity = (LinearLayout) getView().findViewById(R.id.lay_city);
        imgArrow = (ImageView) getView().findViewById(R.id.img_arrow_city);

        txtNo = (LinearLayout) getView().findViewById(R.id.txt_no_city);
        btnGoHelp = (Button) getView().findViewById(R.id.btn_go_help);
        btnGoHelp.setTypeface(G.font2);
        btnGoHelp.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(G.currentActivity, Help.class);
                G.currentActivity.startActivity(intent);
            }
        });
        TextWatcher textWatchert = new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                edtNameCity.setBackgroundResource(R.drawable.selector);
                ErrorName = 0;
                ErrorChangr = 0;
            }


            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                edtNameCity.setBackgroundResource(R.drawable.selector);
                ErrorName = 0;
                ErrorChangr = 0;
            }


            @Override
            public void afterTextChanged(Editable arg0) {
                edtNameCity.setBackgroundResource(R.drawable.selector);
                ErrorName = 0;
                ErrorChangr = 0;
            }
        };

        edtNameCity.addTextChangedListener(textWatchert);

        edtNameCity.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView arg0, int actionId, KeyEvent arg2) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    search();
                    return true;
                }
                return false;
            }
        });

        btnSearchCityOstan.setTypeface(G.font2);
        btnSearchCityOstan.setOnClickListener(new OnClickListener() {

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
            final String P1 = "شهر";
            final String P2 = edtNameCity.getText().toString().trim();

            if (layCity.getVisibility() == 8) {
                layCity.setVisibility(View.VISIBLE);
                imgArrow.setImageResource(R.drawable.arrowdu);
            } else {
                if (P2.length() < 2 && P2.length() >= 0) {
                    ErrorName = 1;
                }
                if ((P2.equals(P2l))) {
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
                    edtNameCity.setBackgroundResource(R.drawable.selector_error);
                    txtNameError.setText("نام وارد شده حداقل 2 حرفی باید باشد");
                }

                if (ErrorName == 0 && ErrorChangr == 1) {
                    txtChangeError.setVisibility(View.VISIBLE);
                    //txtChangeError.setBackgroundResource(R.drawable.selector_error);
                    txtChangeError.setText("لطفا داده های ورودی را تغییر دهید");
                }

                if (ErrorName == 0) {
                    Data.clear();
                    recive(P1, P2);
                } else {
                    dialog5.show();
                }
            }
            P2l = P2;

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
        Log.i("LO", "jaber");
        if (Data.size() == 0) {
            txtNo.setVisibility(View.VISIBLE);
            listView.setVisibility(View.GONE);
        } else {
            txtNo.setVisibility(View.GONE);
            listView.setVisibility(View.VISIBLE);
            layCity.setVisibility(View.GONE);
            imgArrow.setImageResource(R.drawable.arrowd);
            ArrayAdapter adapter = new AdapterNumberList(Data);
            //Toast.makeText(G.context, Data.get(1).name, Toast.LENGTH_LONG).show();
            listView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            listView.setTextFilterEnabled(true);

        }
    }


    public void recive(final String p1, final String p2) {

        final Dialog prg = new Dialog(G.currentActivity);
        prg.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        prg.setContentView(R.layout.dialog_wait);
        TextView txt_header_conect = (TextView) prg.findViewById(R.id.txt_dialog);
        txt_header_conect.setTypeface(G.font2);
        prg.show();
        String url = null;
        try {
            url = G.url + "118_ver3.aspx?" +
                    "p1=" + URLEncoder.encode(p1, "UTF-8") +
                    "&p2=" + URLEncoder.encode(p2, "UTF-8") +
                    "&pe=" + URLEncoder.encode(G.GetDeviceID, "UTF-8") +
                    "&pv=" + URLEncoder.encode(G.verApp, "UTF-8");
        }
        catch (UnsupportedEncodingException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        Log.i("LO", "" + " h1");
        RequestQueue queue = Volley.newRequestQueue(G.context);
        StringRequest postRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>()
                {

                    @Override
                    public void onResponse(String response) {
                        Log.i("LO", "" + response);
                        try {
                            JSONArray array = new JSONArray(response);
                            Log.i("LO", "" + "h2");
                            for (int i = 0; i < array.length(); i++) {
                                StructHa task = new StructHa();
                                JSONObject object = array.getJSONObject(i);
                                task.faliy = object.getString("Family");
                                task.name = object.getString("Name");
                                task.phone = object.getString("Phone1");
                                task.var = 3;
                                //Toast.makeText(G.context, object.getString("Name"), Toast.LENGTH_LONG).show();
                                Data.add(task);
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
                        prg.hide();
                        Toast.makeText(G.context, "لطفا دوباره تلاش کنید", Toast.LENGTH_LONG).show();
                    }
                });

        queue.add(postRequest);

    }
}

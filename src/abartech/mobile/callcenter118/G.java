package abartech.mobile.callcenter118;

import java.util.ArrayList;
import abartech.mobile.callcenter118.database.DataAccess;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.Application;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.Button;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;


public class G extends Application {

    public static Context             context;
    public static final String        DIR_SDCARD           = Environment.getExternalStorageDirectory().getAbsolutePath();
    public static String              DB_NAME_Ass          = "db/calab.db";
    public static String              DB_PATH              = DIR_SDCARD + "/CallCenter/";
    public static String              VER                  = DIR_SDCARD + "/CallCenter/ver";
    public static String              DB_NAME              = "calab.db";
    public static String              FONT1_NAME           = "font/IRAN-Sans-Light.otf";
    public static String              FONT2_NAME           = "font/IRAN-Sans-Light.otf";
    public static final int           DOWNLOAD_BUFFER_SIZE = 8 * 1024;
    public static Typeface            font1;
    public static Typeface            font2;
    public static LayoutInflater      inflater;
    public static Activity            currentActivity;
    public static String              access_token;
    public static String              verApp               = "2.93";
    public static String              textShare            = "سامانه 118 مازندران: \n http://118.ict-tcm.ir/App";

    /* public static String[]            cities               = { "آمل", "بابل",
                                                            "بابلسر", "بهشهر", "بهنمیر", "تنکابن", "چالوس", "رامسر", "ساری",
                                                            "زیرآب", "عباس آباد", "فریدونکنار", "قائمشهر",
                                                            "محمودآباد", "نکا", "نور", "نوشهر" };*/

    /* public static String[]            city_code            = { "121", "111", "112", "15252", "113", "19242", "191", "1925", "151", "125", "192462", "1125", "12322", "123", "152",
                                                            "122", "191" };*/

    public static Dialog              dialog3;
    public static Button              btnShhar;
    public static Button              btnShharSynce;
    public static Button              btnShharLegal;
    public static Dialog              dialog4;
    public static Button              btnActivate;
    public static String              url                  = "http://118.ict-tcm.ir/";
    public static String              urlFiber             = "http://land.fibernet.ir/app/";

    public static String              GetPhoneNumber;
    public static String              GetDeviceID;
    public static String              GetDeviceSoftwareVersion;
    public static String              GetSimSerialNumber;
    public static String              GetAndroidVersion;
    public static String              GetSDKVersion;
    public static String              getSimOperatorName;

    public static String              android_id;
    public static SharedPreferences   preferences;

    public static ArrayList<StructHa> DataLegal            = new ArrayList<StructHa>();

    public static AlarmManager        alarmManager;

    private static G                  mInstance;
    private ImageLoader               mImageLoader;
    private RequestQueue              mRequestQueue;
    public static final String        TAG                  = G.class.getSimpleName();


    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;

        context = getApplicationContext();
        font1 = Typeface.createFromAsset(context.getAssets(), FONT1_NAME);
        font2 = Typeface.createFromAsset(context.getAssets(), FONT2_NAME);
        inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        imei();
        Log.i("IMI", G.GetDeviceID + "/n   " + G.GetPhoneNumber + "/n   " + G.GetDeviceSoftwareVersion + "/n   " + G.GetSimSerialNumber + "/n  " + G.GetAndroidVersion + "/n   " + G.GetSDKVersion + "/n  " + G.getSimOperatorName);
        DataAccess dataAccess = new DataAccess();
        dataAccess.copyDatabase();

        /* android_id = Secure.getString(G.context.getContentResolver(), Secure.ANDROID_ID);
         BluetoothAdapter m_BluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
         String m_bluetoothAdd = m_BluetoothAdapter.getAddress();*/

    }


    public void imei() {
        PhoneInfoClass phoneInfoClass = new PhoneInfoClass();
        phoneInfoClass.GetInfoPhone();
    }

    public static String[] cities    = { "بابل", "بابلسر",
                                     "بهنمیر", "توابع آمل", "آمل", "نور", "محمود آباد", "جويبار", "زیرآب",
                                     "ساری", "نکا", "چالوس و نوشهر", "توابع چالوس", "فریدونکار", "روستا",
                                     "رامسر", "قائمشهر", "بهشهر", "رستم کلاه", "گلوگاه", "تنکابن", "هولار",
                                     "رستم کلا", "گلوگاه", "ليره سر", "كشكو", "سه هزار", "برسه", "نعمت آباد",
                                     "دوهزار", "قلعه گردن", "عظيميه", "لتاك ", "شیرود", "ولي آباد", "سليمان آباد",
                                     "لشتو", "دينار سرا", "گل علي آباد", "گليجان", "ايثارده", "جليل آباد", "عباس آباد",
                                     "نشتارود", "دراسرا", "كترا", "كاظم كلا", "اسبچين ", "رودگر محله", "فقيه آباد",
                                     "سفيد آب", "سلمانشهر", "کلارآباد", "دانيال", "ولكستان" };

    public static String[] city_code = { "111", "112", "113", "120", "121", "122", "123", "124", "125", "151", "152", "191", "192", "1125", "1231",
                                     "1925", "12322", "15252", "15253", "15262", "19242", "152367", "152536",
                                     "152622", "192429", "192432", "192435", "192437", "192438", "192439",
                                     "192442", "192445", "192446", "192447", "192448", "192452", "192453", "192455",
                                     "192456", "192457", "192458", "192459", "192462", "192466", "192469", "192472",
                                     "192474", "192475", "192476", "192478", "192479", "192482", "192492", "192494", "192495" };


    ////
    public static synchronized G getInstance() {
        return mInstance;
    }


    public ImageLoader getImageLoader() {
        getRequestQueue();
        if (mImageLoader == null) {
            mImageLoader = new ImageLoader(this.mRequestQueue,
                    new LruBitmapCache());
        }
        return this.mImageLoader;
    }


    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }


    public <T> void addToRequestQueue(Request<T> req, String tag) {
        // set the default tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }


    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }


    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }

}

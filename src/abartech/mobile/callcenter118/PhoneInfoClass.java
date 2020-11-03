package abartech.mobile.callcenter118;

import android.content.Context;
import android.telephony.TelephonyManager;


public class PhoneInfoClass {

    private TelephonyManager myTM = null;


    public PhoneInfoClass() {}


    public void GetInfoPhone() {

        myTM = (TelephonyManager) G.context.getSystemService(Context.TELEPHONY_SERVICE);

        G.GetPhoneNumber = GetPhoneNumber();
        G.GetDeviceID = myTM.getDeviceId();
        G.GetDeviceSoftwareVersion = GetDeviceSoftwareVersion();
        G.GetSimSerialNumber = GetSimSerialNumber();
        G.GetAndroidVersion = GetAndroidVersion();
        G.GetSDKVersion = GetSDKVersion();
        G.getSimOperatorName = getSimOperatorName();

    }


    public String GetPhoneNumber() {
        return myTM.getLine1Number();
    }


    public String GetDeviceID() {
        return getDeviceID(myTM);
    }


    public String getSimOperatorName() {
        return myTM.getSimOperatorName();
    }


    private String getDeviceID(TelephonyManager phonyManager) {
        String id = phonyManager.getDeviceId();
        if (id == null) {
            id = "not available";
        }
        int phoneType = phonyManager.getPhoneType();
        switch (phoneType) {
            case TelephonyManager.PHONE_TYPE_NONE:
                return "NONE: " + id;
            case TelephonyManager.PHONE_TYPE_GSM:
                return "GSM: IMEI=" + id;
            case TelephonyManager.PHONE_TYPE_CDMA:
                return "CDMA: MEID/ESN=" + id;

            default:
                return "UNKNOWN: ID=" + id;
        }
    }


    public String GetDeviceSoftwareVersion() {
        return myTM.getDeviceSoftwareVersion();
    }


    public String GetSimSerialNumber() {
        return myTM.getSimSerialNumber();
    }


    public String GetAndroidVersion() {
        return android.os.Build.VERSION.RELEASE;
    }


    public String GetSDKVersion() {
        return Integer.toString(android.os.Build.VERSION.SDK_INT);
    }
}

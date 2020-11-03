package abartech.mobile.callcenter118.CustomControl;

import abartech.mobile.callcenter118.FragmentAll;
import abartech.mobile.callcenter118.FragmentCodeCity;
import abartech.mobile.callcenter118.FragmentCodeCountry;
import abartech.mobile.callcenter118.FragmentLegal;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


public class PagerAdapter extends FragmentPagerAdapter {

    private Context context;


    public PagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        this.context = context;

    }


    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "کد کشور ها";
            case 1:
                return "کد استان ها  ";
            case 2:
                return "مشترکین حقوقی ";
            case 3:
                return "مشترکین حقیقی";
            default:
                return "ظ‡ظ…ظ‡ ";

        }
    }


    @Override
    public Fragment getItem(int position) {

        switch (position) {

            case 0:
                return FragmentCodeCountry.instance();
            case 1:
                return FragmentCodeCity.instance();
            case 2:
                return FragmentLegal.instance();
            case 3:
                return FragmentAll.instance();
            default:
                return FragmentAll.instance();
        }
    }


    @Override
    public int getCount() {
        return 4;
    }

}
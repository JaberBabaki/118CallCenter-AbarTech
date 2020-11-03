package abartech.mobile.callcenter118.database;

import java.util.ArrayList;
import android.database.Cursor;


public class Necessary extends DataAccess {

    private String            shahr;
    private Integer           ostan;
    private int               id;
    ArrayList<StrucNecessary> Data = new ArrayList<StrucNecessary>();


    public ArrayList<StrucNecessary> getNecessary() {
        ArrayList<StrucNecessary> Data = new ArrayList<StrucNecessary>();
        openDatabase();
        String sql = "SELECT * FROM necessary";
        Cursor cursor = newDb.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            StrucNecessary faq = new StrucNecessary();
            faq.title = cursor.getString(cursor.getColumnIndex("title"));
            faq.number = cursor.getString(cursor.getColumnIndex("number"));
            Data.add(faq);

        }

        cursor.close();
        newDb.close();
        return Data;

    }
}

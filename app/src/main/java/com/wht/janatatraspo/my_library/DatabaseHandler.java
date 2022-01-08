package com.wht.janatatraspo.my_library;/*
package com.wht.community.my_library;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.wht.seaamigo.Model.Data;
import com.wht.seaamigo.Model.LocalFavourite;

import java.util.ArrayList;
import java.util.Calendar;


public class DatabaseHandler extends SQLiteOpenHelper {
    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 2;
    private Context context;
    private Calendar cal = Calendar.getInstance();
    private ProgressDialog progressDialog;

    // Database Name
    private static final String DATABASE_NAME = "DRS";

    //table name
    private static final String TABLE_SEAMIGO = "tbl_seamigo";
    private static final String TABLE_Favourite = "tbl_fav";

    //tbl status

    private static final String KEY_ID = "id";
    private static final String KEY_MainCategory = "main_category";
    private static final String KEY_SubCategory = "sub_category";
    private static final String KEY_Name = "name";
    private static final String KEY_State = "state";
    private static final String KEY_City = "city";
    private static final String KEY_Pincode = "pincode";
    private static final String KEY_Contact = "contact";
    private static final String KEY_Fax = "fax";
    private static final String KEY_EmailId = "email_id";
    private static final String KEY_AdditionalInfo = "additional_info";
    private static final String KEY_ApprovedNo = "approved_no";
    private static final String KEY_Address = "address";
    private static final String KEY_IsFavourite = "is_fav";

    //tabl fav
    private static final String KEY_Fav_ID = "fav_id";
    private static final String KEY_Reg_ID = "reg_id";


    private static DatabaseHandler instance;

    public static DatabaseHandler getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseHandler(context);
        }
        return instance;
    }

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_STATUS_TABLE = "CREATE TABLE " + TABLE_SEAMIGO + "("
                + KEY_ID + " TEXT,"
                + KEY_MainCategory + " TEXT,"
                + KEY_SubCategory + " TEXT,"
                + KEY_Name + " TEXT,"
                + KEY_State + " TEXT,"
                + KEY_City + " TEXT,"
                + KEY_Pincode + " TEXT,"
                + KEY_Contact + " TEXT,"
                + KEY_Fax + " TEXT,"
                + KEY_EmailId + " TEXT,"
                + KEY_AdditionalInfo + " TEXT,"
                + KEY_ApprovedNo + " TEXT,"
                + KEY_Address + " TEXT,"
                + KEY_IsFavourite + " TEXT"
                + ")";

        String CREATE_FAV_TABLE = "CREATE TABLE " + TABLE_Favourite + "("
                + KEY_Fav_ID + " TEXT,"
                + KEY_Reg_ID + " TEXT"
                + ")";


        db.execSQL(CREATE_STATUS_TABLE);
        db.execSQL(CREATE_FAV_TABLE);

    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SEAMIGO);
        onCreate(db);
    }

    public boolean insert_datain_table_fav(String reg_id, String record_id) {

        final SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();


        Log.d("remarkinsert", "insert_datain_table_reason: " + record_id);
        Log.d("remarkinsert", "insert_datain_table_reason: " + reg_id);

        values.put(KEY_Fav_ID, record_id);//1
        values.put(KEY_Reg_ID, reg_id);//2

        db.insert(TABLE_Favourite, null, values);
        Log.d("my_tag2-->", "record inserted");

        db.close();
        getCountFav();
        return true;
        // Inserting Row
        //db.execSQL("INSERT INTO " + TABLE_NOTIFICATION + "(" + KEY_NOTIFICATION_MESSAGE +") " + "VALUES ("+message+")");
    }

    public boolean delete_from_fav(String reg_id, String record_id) {
        final SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + TABLE_Favourite + " where " +
                KEY_Fav_ID + " = " +record_id + " AND " +
                KEY_Reg_ID + " = " +reg_id);
        //db.delete(TABLE_CART, null, null);
        getCountFav();
        db.close();
        return true;
    }

    public boolean check_wheather_fav(String reg_id, String record_id) {

        //String countQuery = "SELECT * FROM " + TABLE_NOTIFICATION +" WHERE "+KEY_NOTIFICATION_READ+" == '0'";
        String countQuery = "SELECT * FROM " + TABLE_Favourite + " where " +
                KEY_Fav_ID + " = " +record_id + " AND " +
                KEY_Reg_ID + " = " +reg_id;
        final SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int cnt = cursor.getCount();
        Log.d("isFavOrNot", "check: " + cnt);
        cursor.close();
        //db.close();
        if(cnt > 0) {
            return true;
        }else {
            return  false;
        }
    }

    public boolean insertData(ArrayList<Data> dataArrayList) {
        final SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        Log.d("sizeArray", "insert_notification: " + dataArrayList.size());
        for (int i = 0; i < dataArrayList.size(); i++) {

            Log.d("Salve", "Category: " + dataArrayList.get(i).getMain_category());
            Log.d("Salve", "Name: " + dataArrayList.get(i).getName());

            values.put(KEY_ID, dataArrayList.get(i).getId());//5
            values.put(KEY_MainCategory, dataArrayList.get(i).getMain_category());//5
            values.put(KEY_SubCategory, dataArrayList.get(i).getSub_category());//6
            values.put(KEY_Name, dataArrayList.get(i).getName());//7
            values.put(KEY_State, dataArrayList.get(i).getState());//8
            values.put(KEY_City, dataArrayList.get(i).getCity());//9
            values.put(KEY_Pincode, dataArrayList.get(i).getPincode());//10
            values.put(KEY_Contact, dataArrayList.get(i).getContact());//11
            values.put(KEY_Fax, dataArrayList.get(i).getFax());//12
            values.put(KEY_EmailId, dataArrayList.get(i).getEmail_id());//13
            values.put(KEY_AdditionalInfo, dataArrayList.get(i).getAdditional_info());//14
            values.put(KEY_ApprovedNo, dataArrayList.get(i).getApproved_no());//15
            values.put(KEY_Address, dataArrayList.get(i).getAddress());//16
            values.put(KEY_IsFavourite, "0");//17

            db.insert(TABLE_SEAMIGO, null, values);
            Log.d("Values_check-->", "record inserted");

        }

        db.close();
        return true;
    }

    public ArrayList<Data> getSelectedElements(String value, String search) {

        ArrayList<Data> list = new ArrayList<Data>();
        String selectQuery = "";

        Log.d("Value", "getSelectedElements: " + value);

        if (value.equals("2")) {

            if (search.isEmpty()) {
                selectQuery = "SELECT  * FROM " + TABLE_SEAMIGO + " where "
                        + KEY_MainCategory + " = " + value + " ORDER BY " + KEY_Name + " ASC ";
            } else {

                selectQuery = "SELECT  * FROM " + TABLE_SEAMIGO + " where "
                        + KEY_MainCategory + " = " + value + " AND ("
                        + KEY_Name + " LIKE '%" + search + "%' OR "
                        + KEY_State + " LIKE '%" + search + "%' OR "
                        + KEY_City + " LIKE '%" + search + "%' OR "
                        + KEY_Pincode + " LIKE '%" + search + "%' OR "
                        + KEY_Contact + " LIKE '%" + search + "%' OR "
                        + KEY_Fax + " LIKE '%" + search + "%' OR "
                        + KEY_EmailId + " LIKE '%" + search + "%' OR "
                        + KEY_AdditionalInfo + " LIKE '%" + search + "%' OR "
                        + KEY_ApprovedNo + " LIKE '%" + search + "%' OR "
                        + KEY_Address + " LIKE '%" + search + "%' )";
                //+ " AND " + KEY_MainCategory + " = " + value ;
            }
        } else {
            if (search.isEmpty()) {
                selectQuery = "SELECT  * FROM " + TABLE_SEAMIGO + " where "
                        + KEY_MainCategory + " = " + value;
            } else {

                selectQuery = "SELECT  * FROM " + TABLE_SEAMIGO + " where "
                        + KEY_MainCategory + " = " + value + " AND ("
                        + KEY_Name + " LIKE '%" + search + "%' OR "
                        + KEY_State + " LIKE '%" + search + "%' OR "
                        + KEY_City + " LIKE '%" + search + "%' OR "
                        + KEY_Pincode + " LIKE '%" + search + "%' OR "
                        + KEY_Contact + " LIKE '%" + search + "%' OR "
                        + KEY_Fax + " LIKE '%" + search + "%' OR "
                        + KEY_EmailId + " LIKE '%" + search + "%' OR "
                        + KEY_AdditionalInfo + " LIKE '%" + search + "%' OR "
                        + KEY_ApprovedNo + " LIKE '%" + search + "%' OR "
                        + KEY_Address + " LIKE '%" + search + "%' )";
                //+ " AND " + KEY_MainCategory + " = " + value ;
            }
        }

        Log.d("Query", "getSelectedElements: " + selectQuery);
        SQLiteDatabase db = this.getReadableDatabase();
        try {

            Cursor cursor = db.rawQuery(selectQuery, null);
            try {
                //FormatedDate formatedDate= FormatedDate.getInstance(context);
                // looping through all rows and adding to list
                if (cursor.moveToFirst()) {
                    do {

                        Data obj = new Data(
                                cursor.getString(0),
                                cursor.getString(1),
                                cursor.getString(2),
                                cursor.getString(3),
                                cursor.getString(4),
                                cursor.getString(5),
                                cursor.getString(6),
                                cursor.getString(7),
                                cursor.getString(8),
                                cursor.getString(9),
                                cursor.getString(10),
                                cursor.getString(11),
                                cursor.getString(12),
                                cursor.getString(13)


                        );

                        //you could add additional columns here..
                        list.add(obj);
                    } while (cursor.moveToNext());
                }

            } finally {
                try {
                    cursor.close();
                } catch (Exception ignore) {
                }
            }

        } finally {
            try {
                db.close();
            } catch (Exception ignore) {
            }
        }
        return list;
    }

    public ArrayList<Data> getFavElements(String value, String search) {

        ArrayList<Data> list = new ArrayList<Data>();
        String selectQuery = "";

        Log.d("Value", "getSelectedElements: " + value);


            if (search.isEmpty()) {
                selectQuery = "SELECT  * FROM " + TABLE_SEAMIGO + " where "
                        + KEY_Name + " != ''" + " ORDER BY " + KEY_MainCategory + " ASC ";
            }


        Log.d("Query", "getSelectedElements: " + selectQuery);
        SQLiteDatabase db = this.getReadableDatabase();
        try {

            Cursor cursor = db.rawQuery(selectQuery, null);
            try {
                //FormatedDate formatedDate= FormatedDate.getInstance(context);
                // looping through all rows and adding to list
                if (cursor.moveToFirst()) {
                    do {

                        Data obj = new Data(
                                cursor.getString(0),
                                cursor.getString(1),
                                cursor.getString(2),
                                cursor.getString(3),
                                cursor.getString(4),
                                cursor.getString(5),
                                cursor.getString(6),
                                cursor.getString(7),
                                cursor.getString(8),
                                cursor.getString(9),
                                cursor.getString(10),
                                cursor.getString(11),
                                cursor.getString(12),
                                cursor.getString(13)


                        );

                        //you could add additional columns here..
                        list.add(obj);
                    } while (cursor.moveToNext());
                }

            } finally {
                try {
                    cursor.close();
                } catch (Exception ignore) {
                }
            }

        } finally {
            try {
                db.close();
            } catch (Exception ignore) {
            }
        }
        return list;
    }

    public ArrayList<LocalFavourite> getSelectedElementsFav(String reg_id) {

        ArrayList<LocalFavourite> list = new ArrayList<LocalFavourite>();
        String selectQuery = "";

                selectQuery = "SELECT  * FROM " + TABLE_Favourite + " where "
                        + KEY_Reg_ID + " = " + reg_id ;


        Log.d("Query", "getSelectedElements: " + selectQuery);
        SQLiteDatabase db = this.getReadableDatabase();
        try {

            Cursor cursor = db.rawQuery(selectQuery, null);
            try {
                //FormatedDate formatedDate= FormatedDate.getInstance(context);
                // looping through all rows and adding to list
                if (cursor.moveToFirst()) {
                    do {

                        LocalFavourite obj = new LocalFavourite(
                                cursor.getString(0),
                                cursor.getString(1)


                        );

                        //you could add additional columns here..
                        list.add(obj);
                    } while (cursor.moveToNext());
                }

            } finally {
                try {
                    cursor.close();
                } catch (Exception ignore) {
                }
            }

        } finally {
            try {
                db.close();
            } catch (Exception ignore) {
            }
        }
        return list;
    }

    public long getCount() {
        //String countQuery = "SELECT * FROM " + TABLE_NOTIFICATION +" WHERE "+KEY_NOTIFICATION_READ+" == '0'";
        String countQuery = "SELECT * FROM " + TABLE_SEAMIGO;
        final SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int cnt = cursor.getCount();
        Log.d("localdatabasecount", "getCount: " + cnt);
        cursor.close();
        //db.close();
        return cnt;
    }

    public long getCountFav() {
        //String countQuery = "SELECT * FROM " + TABLE_NOTIFICATION +" WHERE "+KEY_NOTIFICATION_READ+" == '0'";
        String countQuery = "SELECT * FROM " + TABLE_Favourite;
        final SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int cnt = cursor.getCount();
        Log.d("localdatabasecountFav", "getCount: " + cnt);
        cursor.close();
        //db.close();
        return cnt;
    }

    public boolean deleteAllTableData() {
        Log.d("deleteAllTableData", "deleteAllTableData: ");
        final SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_SEAMIGO);
        db.close();
        return true;
    }

    public boolean deleteTableData() {
        final SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + TABLE_SEAMIGO);
        //db.delete(TABLE_CART, null, null);
        db.close();
        return true;
    }

    public void deleteDeliveredData() {
        SQLiteDatabase db = this.getWritableDatabase();
        String CREATE_STATUS_TABLE = "CREATE TABLE " + TABLE_SEAMIGO + "("
                + KEY_ID + " TEXT,"
                + KEY_MainCategory + " TEXT,"
                + KEY_SubCategory + " TEXT,"
                + KEY_Name + " TEXT,"
                + KEY_State + " TEXT,"
                + KEY_City + " TEXT,"
                + KEY_Pincode + " TEXT,"
                + KEY_Contact + " TEXT,"
                + KEY_Fax + " TEXT,"
                + KEY_EmailId + " TEXT,"
                + KEY_AdditionalInfo + " TEXT,"
                + KEY_ApprovedNo + " TEXT,"
                + KEY_Address + " TEXT"
                + ")";


        db.execSQL(CREATE_STATUS_TABLE);

        //db.execSQL("delete from " + TABLE_SEAMIGO);
        //db.execSQL("delete from " + TABLE_SEAMIGO +" WHERE "+ KEY_Status + " = 'NOT DELIVERED'");
        //db.delete(TABLE_CART, null, null);
        db.close();
    }

    public boolean deleteTable() {
        final SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_SEAMIGO, null, null);
        db.close();
        return true;

    }

}
*/

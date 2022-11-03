package com.cindytech.isecure.db;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import androidx.annotation.Nullable;
import com.cindytech.isecure.model.Model;
import com.cindytech.isecure.ui.settings.SettingsFragment;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SettingsDB extends SQLiteOpenHelper {

    public static final String MODEL_TABLE = "MODEL_TABLE";
    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_MODEL_NUMBER = "MODEL_NUMBER";
    public static final String COLUMN_MODEL_ARM = "MODEL_ARM";
    public static final String COLUMN_MODEL_DISARM = "MODEL_DISARM";
    public static final String COLUMN_MODEL_NIGHT = "MODEL_NIGHT";
    public static final String COLUMN_MODEL_DAY = "MODEL_DAY";
    public static final String COLUMN_MODEL_STATUS = "MODEL_STATUS";
    public static final String COLUMN_MODEL_ENABLE = "MODEL_ENABLE";
    public static final String COLUMN_MODEL_DISABLE = "MODEL_DISABLE";
    public static final String COLUMN_MODEL_TIME = "MODEL_TIME";
    public static final String COLUMN_MODEL_PASSWORD = "MODEL_PASSWORD";


    public SettingsDB(@Nullable SettingsFragment context) {
        super(context.getContext(), "model.db", null, 1);
    }

    // this is called the firs time a database is accessed. There should be code in here to create a new database.
    @Override
    public void onCreate(SQLiteDatabase db) {

        Log.d("PIPPOOOO", "created");
        String createTableStatement = "CREATE TABLE " + MODEL_TABLE + " (" + COLUMN_ID + " INTEGER PRIMARY KEY, " + COLUMN_MODEL_NUMBER + " TEXT, " + COLUMN_MODEL_ARM + " TEXT, " + COLUMN_MODEL_DISARM + " TEXT , " + COLUMN_MODEL_NIGHT + " TEXT, " + COLUMN_MODEL_DAY + " TEXT, " + COLUMN_MODEL_STATUS + " TEXT, " + COLUMN_MODEL_ENABLE + " TEXT, " + COLUMN_MODEL_DISABLE + " TEXT, " + COLUMN_MODEL_TIME + " TEXT, " + COLUMN_MODEL_PASSWORD + " TEXT)";
        db.execSQL(createTableStatement);

    }


    // this is called if the database version number changes. It present previous users apps from breaking when you change the database design.
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    //update Model based on existing ID
    public void updateModel(Model model) {

        ///String updateSQL = "Update " + MODEL_TABLE + " SET Column1 = someValue WHERE columnId = "+ someValue;

        //myDataBase.execSQL(strSQL);
        // calling a method to get writable database.
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        // on below line we are calling a update method to update our database and passing our values.
        // and we are comparing it with name of our course which is stored in original name variable.

        @SuppressLint("SimpleDateFormat") String currentTime = new SimpleDateFormat("HH:mm").format(new Date());
        String time = "TIME " +currentTime;
        Log.d("koha", time);

        /*
         on below line we are passing all values
         along with its key and value pair.
        cv.put(COLUMN_ID, 1);
        */
        cv.put(COLUMN_MODEL_NUMBER, model.getNumber());
        cv.put(COLUMN_MODEL_ARM, model.getArm());
        cv.put(COLUMN_MODEL_DISARM, model.getDisarm());
        cv.put(COLUMN_MODEL_NIGHT, model.getNight());
        cv.put(COLUMN_MODEL_DAY, model.getDay());
        cv.put(COLUMN_MODEL_STATUS, model.getStatus());
        cv.put(COLUMN_MODEL_ENABLE, model.getEnable());
        cv.put(COLUMN_MODEL_DISABLE, model.getDisable());
        cv.put(COLUMN_MODEL_TIME, time);
        cv.put(COLUMN_MODEL_PASSWORD, model.getPassword());

        String ID = "1";

        db.update(MODEL_TABLE, cv, "ID = ?", new String[]{ID});
        db.close();

        /*long insert = db.insert(MODEL_TABLE, null, cv);
        if (insert == -1) {
            return false;
        }
        else {
            return true;
        }*/
    }

    public Cursor uploadSettings(){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        //String qry = "SELECT * FROM "+TABLE_NAME+" WHERE ID="+id;
        /*Model model = new Model();
        model.setId(1);
        model.setNumber(cursor.getString(1));
        model.setArm(cursor.getString(2));
        model.setDisarm(cursor.getString(3));
        model.setNight(cursor.getString(4));
        model.setDay(cursor.getString(5));
        model.setStatus(cursor.getString(6));
        model.setEnable(cursor.getString(7));
        model.setDisable(cursor.getString(8));
        model.setTime(cursor.getString(9));
        model.setPassword(cursor.getString(10));*/

        //Log.d("outputttt" , model.toString());
        Log.d("SETTING", "inside update method");
        return sqLiteDatabase.rawQuery("SELECT * FROM "+MODEL_TABLE+" WHERE ID=1 ",null);
    }

    //add one signle model method
    public boolean addOne(Model model){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        //cv.put(COLUMN_ID, 1);
        cv.put(COLUMN_MODEL_NUMBER, model.getNumber());
        cv.put(COLUMN_MODEL_ARM, model.getArm());
        cv.put(COLUMN_MODEL_DISARM, model.getDisarm());
        cv.put(COLUMN_MODEL_NIGHT, model.getNight());
        cv.put(COLUMN_MODEL_DAY, model.getDay());
        cv.put(COLUMN_MODEL_STATUS, model.getStatus());
        cv.put(COLUMN_MODEL_ENABLE, model.getEnable());
        cv.put(COLUMN_MODEL_DISABLE, model.getDisable());
        cv.put(COLUMN_MODEL_TIME, model.getTime());
        cv.put(COLUMN_MODEL_PASSWORD, model.getPassword());

        long insert = db.insert(MODEL_TABLE, null, cv);
        if (insert == -1) return false;
        else return true;
    }

    public boolean firstLogin(){
        Cursor res = uploadSettings();
        res.moveToFirst();
        if (res.isBeforeFirst()){
            return true;//means empty result set
        } else {
            return false;
        }
    }
}
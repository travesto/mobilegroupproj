package teammint.classroster;

/**
 * Created by Darius Bell on 9/29/2017.
 */

import android.content.ContentValues;

import android.content.Context;

import android.database.Cursor;

import android.database.DatabaseErrorHandler;

import android.database.sqlite.SQLiteDatabase;

import android.database.sqlite.SQLiteOpenHelper;

import android.graphics.Bitmap;
import android.util.Log;
import android.util.StringBuilderPrinter;


public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "DatabaseHelper";
    public static final String DATABASE_NAME = "SQLiteExample.db";
    private static final int DATABASE_VERSION = 1;
    public static final String PERSON_TABLE_NAME = "ClassA";
    public static final String PERSON_COLUMN_ID = "_id";
    public static final String PERSON_COLUMN_FNAME = "name";
    public static final String PERSON_COLUMN_LNAME = "name";
    public static final String PERSON_COLUMN_MAJOR = "CS";
    public static final String PERSON_COLUMN_location = "location";
    public static final String PERSON_COLUMN_GENDER = "gender";
//    public static final String PERSON_COLUMN_OS = "os";
    public static final String PERSON_COLUMN_IMAGE = "IMAGE";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME , null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + PERSON_TABLE_NAME + "(" +
                PERSON_COLUMN_ID + " INTEGER PRIMARY KEY, " +
                //PERSON_COLUMN_IMAGE + " BLOB, " +
                PERSON_COLUMN_FNAME + " TEXT, " +
                PERSON_COLUMN_LNAME + " TEXT, " +
                PERSON_COLUMN_MAJOR + " TEXT, " +
                PERSON_COLUMN_location + " TEXT, " +
//                PERSON_COLUMN_OS + " TEXT, " +
                PERSON_COLUMN_GENDER + " Text)"
        );
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

        db.execSQL("DROP IF TABLE EXISTS " + PERSON_TABLE_NAME);

        onCreate(db);

    }

    public boolean insertPerson(String Fname,String Lname, String maj, String gender,String location, String os ) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(PERSON_COLUMN_FNAME, Fname);
        contentValues.put(PERSON_COLUMN_LNAME, Lname);
        contentValues.put(PERSON_COLUMN_MAJOR, maj);
        contentValues.put(PERSON_COLUMN_location, location);
        contentValues.put(PERSON_COLUMN_GENDER, gender);
//        contentValues.put(PERSON_COLUMN_OS, os);
        db.insert(PERSON_TABLE_NAME, null, contentValues);
        return true;
    }


    public Cursor getAllPersons() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery( "SELECT * FROM " + PERSON_TABLE_NAME, null );
        return res;
    }
}

package teammint.classroster.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Darius Bell on 10/9/2017.
 */

public class DBhelper extends SQLiteOpenHelper{

    public static final  String DB_FILE_NAME = "classroster.db";
    public static final int DB_VERSION = 1;

    public DBhelper(Context context) {
        super(context, DB_FILE_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(StudentsTable.SQL_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL(StudentsTable.SQL_DELETE);
        onCreate(db);
    }
}

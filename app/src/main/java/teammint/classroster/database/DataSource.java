package teammint.classroster.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import teammint.classroster.model.DataStudent;

/**
 * Created by Darius Bell on 10/9/2017.
 */

public class DataSource {

    private Context mContext;
    private SQLiteDatabase mdatabase;
    private SQLiteOpenHelper mdbHelper;

    public DataSource(Context context) {
        this.mContext = context;
        mdbHelper = new DBhelper(mContext);
        mdatabase = mdbHelper.getWritableDatabase();
    }
    public void open(){
        mdatabase = mdbHelper.getWritableDatabase();
    }
    public void wipe() { mdbHelper.onUpgrade(mdatabase,0,0);}
    public void close(){
    mdbHelper.close();

    }
    public DataStudent createStudent(DataStudent student){
        ContentValues values = student.toValues();
        mdatabase.insert(StudentsTable.TABLE_STUDENT,null,values);
        return student;
    }

    public List<DataStudent> rawQuery(String S)
    {
        Cursor cursor = mdatabase.rawQuery(S,new String[]{});
        List<DataStudent> dataStudents = new ArrayList<>();
        while(cursor.moveToNext())
        {
            DataStudent student = new DataStudent();
            student.setID(cursor.getString(cursor.getColumnIndex(StudentsTable.COLUMN_ID)));
            student.setFName(cursor.getString(cursor.getColumnIndex(StudentsTable.COLUMN_FNAME)));
            student.setLName(cursor.getString(cursor.getColumnIndex(StudentsTable.COLUMN_LNAME)));
            student.setMajor(cursor.getString(cursor.getColumnIndex(StudentsTable.COLUMN_MAJOR)));
            student.setHome(cursor.getString(cursor.getColumnIndex(StudentsTable.COLUMN_LOCATION)));
            student.setGender(cursor.getString(cursor.getColumnIndex(StudentsTable.COLUMN_GENDER)));
            student.setNotes(cursor.getString(cursor.getColumnIndex(StudentsTable.COLUMN_NOTES)));
//            student.setOS(cursor.getString(cursor.getColumnIndex(StudentsTable.COLUMN_OS)));
            student.setImage(cursor.getBlob(7));
            dataStudents.add(student);
        }
        cursor.close();
        return dataStudents;
    }

    public List<DataStudent> getAll(){
        List<DataStudent> dataStudents = new ArrayList<>();
        Cursor cursor = mdatabase.query(StudentsTable.TABLE_STUDENT, StudentsTable.ALL_COL,
                null,null,null,null,"UPPER("+StudentsTable.COLUMN_FNAME+")");
        while(cursor.moveToNext()){
            DataStudent student = new DataStudent();
            student.setID(cursor.getString(cursor.getColumnIndex(StudentsTable.COLUMN_ID)));
            student.setFName(cursor.getString(cursor.getColumnIndex(StudentsTable.COLUMN_FNAME)));
            student.setLName(cursor.getString(cursor.getColumnIndex(StudentsTable.COLUMN_LNAME)));
            student.setMajor(cursor.getString(cursor.getColumnIndex(StudentsTable.COLUMN_MAJOR)));
            student.setHome(cursor.getString(cursor.getColumnIndex(StudentsTable.COLUMN_LOCATION)));
            student.setGender(cursor.getString(cursor.getColumnIndex(StudentsTable.COLUMN_GENDER)));
//            student.setOS(cursor.getString(cursor.getColumnIndex(StudentsTable.COLUMN_OS)));
            student.setNotes(cursor.getString(cursor.getColumnIndex(StudentsTable.COLUMN_NOTES)));
            student.setImage(cursor.getBlob(7));
            dataStudents.add(student);
        }
        cursor.close();
        return dataStudents;
    }
    public List<DataStudent> getAllWomen(){
        List<DataStudent> dataStudents = new ArrayList<>();
        String whereClause = "gender = ?";
        String[] whereArgs = new String[] {
                "Female"
        };
        Cursor cursor = mdatabase.query(StudentsTable.TABLE_STUDENT, StudentsTable.ALL_COL,whereClause,whereArgs,null,null,StudentsTable.COLUMN_FNAME);
        while(cursor.moveToNext()){
            DataStudent student = new DataStudent();
            student.setID(cursor.getString(cursor.getColumnIndex(StudentsTable.COLUMN_ID)));
            student.setFName(cursor.getString(cursor.getColumnIndex(StudentsTable.COLUMN_FNAME)));
            student.setLName(cursor.getString(cursor.getColumnIndex(StudentsTable.COLUMN_LNAME)));
            student.setMajor(cursor.getString(cursor.getColumnIndex(StudentsTable.COLUMN_MAJOR)));
            student.setHome(cursor.getString(cursor.getColumnIndex(StudentsTable.COLUMN_LOCATION)));
            student.setGender(cursor.getString(cursor.getColumnIndex(StudentsTable.COLUMN_GENDER)));
//            student.setOS(cursor.getString(cursor.getColumnIndex(StudentsTable.COLUMN_OS)));
            student.setNotes(cursor.getString(cursor.getColumnIndex(StudentsTable.COLUMN_NOTES)));
            student.setImage(cursor.getBlob(7));
            dataStudents.add(student);
        }
        cursor.close();
        return dataStudents;
    }
}

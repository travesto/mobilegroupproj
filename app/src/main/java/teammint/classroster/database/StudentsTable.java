package teammint.classroster.database;

/**
 * Created by Darius Bell on 10/9/2017.
 */

public class StudentsTable {
    public static final String TABLE_STUDENT = "students";
    public static final String COLUMN_ID = "studentId";
    public static final String COLUMN_FNAME = "studentFName";
    public static final String COLUMN_LNAME = "studentLName";
    public static final String COLUMN_MAJOR = "major";
    public static final String COLUMN_LOCATION = "location";
    public static final String COLUMN_GENDER = "gender";
    public static final String COLUMN_NOTES = "notes";
    public static final String COLUMN_IMAGE = "image";

    public static final String[] ALL_COL = {
            COLUMN_ID,COLUMN_FNAME,COLUMN_LNAME,COLUMN_MAJOR,COLUMN_LOCATION,
            COLUMN_GENDER,COLUMN_NOTES,COLUMN_IMAGE};

    public static final String SQL_CREATE =
            "CREATE TABLE " + TABLE_STUDENT + "(" +
                    COLUMN_ID + " TEXT PRIMARY KEY," +
                    COLUMN_FNAME + " TEXT," +
                    COLUMN_LNAME + " TEXT," +
                    COLUMN_MAJOR + " TEXT," +
                    COLUMN_LOCATION + " TEXT," +
                    COLUMN_GENDER + " TEXT," +
                    COLUMN_NOTES + " TEXT," +
                    COLUMN_IMAGE + " BLOB" + ");";

    public static final String SQL_DELETE =
            "DROP TABLE " + TABLE_STUDENT;
}
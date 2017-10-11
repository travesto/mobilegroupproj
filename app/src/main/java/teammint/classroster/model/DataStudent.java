package teammint.classroster.model;

import android.content.ContentValues;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;

import teammint.classroster.database.StudentsTable;

import static teammint.classroster.database.StudentsTable.COLUMN_IMAGE;

/**
 * Created by Darius Bell on 10/9/2017.
 */

public class DataStudent {

    private String studentid;
    private String studentFName;
    private String studentLName;
    private String major;
    private String location;
    private String gender;
    private String notes;
    private Bitmap image;
    private Byte[] imageStore;

        public DataStudent(){

        }

        private static byte[] getBytes(Bitmap bitmap) {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
            return stream.toByteArray();
        }
        private static Bitmap getImage(byte[] image) {
            return BitmapFactory.decodeByteArray(image, 0, image.length);
        }

        //Setters
        public void setFName(String n) {
            this.studentFName = n;
        }
        public void setLName(String n) {
            this.studentLName = n;
        }
        public void setMajor(String m) {
            this.major = m;
        }
        public void setHome(String h) {
            this.location = h;
        }
        public void setNotes(String o) {
            this.notes = o;
        }
        public void setImage(Bitmap b) {
            this.image = b;
        }
        public void setImage(byte[] b) {
            this.image = getImage(b);
        }
        public void setGender(String g) {
            this.gender = g;
        }
        public void setID(String a) {
            this.studentid = a;
        }
        //public void setImage(Bitmap c) {this.image=c;}

        //Getters
        public String getID() {
            return this.studentid;
        }
        public String getFName() {
            return this.studentFName;
        }
        public String getLName() {
            return this.studentLName;
        }
        public String getMajor() {
            return this.major;
        }
        public String getHome() {
            return this.location;
        }
        public String getNotes() {
            return this.notes;
        }
        public byte[] getImage() {
            return getBytes(this.image);
        }
        public String getGender() {
            return this.gender;
        }

        public ContentValues toValues()
        {
            ContentValues val = new ContentValues(8);
            val.put(StudentsTable.COLUMN_ID,studentid);
            val.put(StudentsTable.COLUMN_FNAME, studentFName);
            val.put(StudentsTable.COLUMN_LNAME, studentLName);
            val.put(StudentsTable.COLUMN_MAJOR, major);
            val.put(StudentsTable.COLUMN_LOCATION, location);
            val.put(StudentsTable.COLUMN_GENDER, gender);
            val.put(StudentsTable.COLUMN_NOTES, notes);
            val.put(StudentsTable.COLUMN_IMAGE,getBytes(image));
            return val;
        }


}

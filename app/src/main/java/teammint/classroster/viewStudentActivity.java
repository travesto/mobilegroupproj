package teammint.classroster;

import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import teammint.classroster.database.DataSource;
import teammint.classroster.model.DataStudent;

public class viewStudentActivity extends AppCompatActivity {


    DataSource mDataSource;
    DataStudent DS;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_view_student);
            String ID = getIntent().getStringExtra("StudentID");

            mDataSource = new DataSource(this);
            mDataSource.open();
            DS = mDataSource.rawQuery("SELECT * FROM students WHERE studentId = '" + ID+"'").get(0);

            ((TextView) findViewById(R.id.fName)).setText(DS.getFName());
            ((TextView) findViewById(R.id.lName)).setText(DS.getLName());
            ((TextView) findViewById(R.id.major)).setText(DS.getMajor());
            ((TextView) findViewById(R.id.location)).setText(DS.getHome());
            ((TextView) findViewById(R.id.gender)).setText(DS.getGender());
            ((TextView) findViewById(R.id.note)).setText(DS.getNotes());
            ((ImageView) findViewById(R.id.Frame)).setImageBitmap(DataStudent.convertImage(DS.getImage()));
//            ((TextView) findViewById(R.id.spinnerOS)).setText(DS.getOS());
        }
        catch (Exception e)
        {
            String s = e.getMessage();
            s = s;
        }
    }

}

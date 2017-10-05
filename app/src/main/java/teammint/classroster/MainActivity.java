
package teammint.classroster;

import android.app.Activity;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.BaseColumns;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TabHost;
import android.content.Intent;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import android.content.ContentValues;

import android.content.Context;

import android.database.Cursor;
import java.sql.Blob;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements StudentFragment.OnFragmentInteractionListener {
    //Declarations

    public static final ArrayList<Student> Students = new ArrayList<Student>();

    TabHost tabHost;
    Button btn;
    private static  final int CAMERA_REQUEST = 123;
    ImageView b;

    DatabaseHelper mDatabaseHelper;
    private Button btnAdd, btnViewData;
    private EditText Fname;
    private EditText Lname;
    private EditText major;
    private EditText hometown;
    private EditText notes;
    private EditText image;
    private ListView listView;

    //functions
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn = (Button) findViewById(R.id.takePic);
        b = (ImageView) findViewById(R.id.Frame);
        Fname = (EditText) findViewById(R.id.fName);
        Lname = (EditText) findViewById(R.id.lName);
        major = (EditText) findViewById(R.id.major);
        hometown = (EditText) findViewById(R.id.location);
        notes = (EditText) findViewById(R.id.note);
        btnAdd = (Button) findViewById(R.id.addStudent);
        mDatabaseHelper = new DatabaseHelper(this);

        TabHost host = (TabHost)findViewById(R.id.tabHost);
        host.setup();

        //Tab 1
        TabHost.TabSpec spec = host.newTabSpec("Add Student");
        spec.setContent(R.id.tab1);
        spec.setIndicator("Add Student");
        host.addTab(spec);

        //Tab 2
        spec = host.newTabSpec("View All");
        spec.setContent(R.id.studentsView);
        spec.setIndicator("View All");
        host.addTab(spec);

        final Spinner dropdown = (Spinner)findViewById(R.id.gender);
        //create a list of items for the spinner.
        String[] items = new String[]{"Male", "Female"};
        //create an adapter to describe how the items are displayed, adapters are used in several places in android.
        //There are multiple variations of this, but this is the basic variant.
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.spinner_layout, items);
        //set the spinners adapter to the previously created one.

        dropdown.setAdapter(adapter);

        btnAdd.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String Entry1 = Fname.getText().toString();
                String Entry2 = Lname.getText().toString();
                String Entry3 = major.getText().toString();
                String Entry4 = dropdown.getSelectedItem().toString();
                String Entry5 = hometown.getText().toString();
                if (Entry1.length() != 0 || Entry2.length() != 0 || Entry3.length() != 0 || Entry4.length() != 0 || Entry5.length() != 0) {

                    AddData(Entry1,Entry2,Entry3,Entry4,Entry5);
                } else {
                    toastMessage("You must put something in the text field!");
                }
            }
        });
        /*
        final Cursor cursor = mDatabaseHelper.getAllPersons();
        String [] columns = new String[] {
                DatabaseHelper.PERSON_COLUMN_ID,
                DatabaseHelper.PERSON_COLUMN_FNAME
        };
        int [] widgets = new int[] {
                R.id.personID,
                R.id.personName
        };
        */

        /*
        SimpleCursorAdapter cursorAdapter = new SimpleCursorAdapter(this, R.layout.person_info,
                cursor, columns, widgets, 0);

        listView = (ListView)findViewById(R.id.listview);
        listView.setAdapter(cursorAdapter);
        */
        try {
            for (int i = 0; i < 20; i++)
                Students.add(new Student(i));

            FragmentManager FragMan = getSupportFragmentManager();
            for (int i = 0; i < Students.size(); i++)
            {
                FragmentTransaction FragTran = FragMan.beginTransaction();
                StudentFragment SF =  StudentFragment.newInstance(Students.get(i));
                FragTran.add(R.id.studentsView, SF, Students.get(i).getName());
                FragTran.commit();
                FragMan.executePendingTransactions();
            }
        }
        catch(Exception e)
        {
            String s = e.getMessage();
            s = s;
        }
    }

    //Capture camera intent
    public void btnClick(View v)
    {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA_REQUEST);
    }
    //Clear picture data
    public void clear(View v)
    {
        b.setImageBitmap(null);
    }
    //Store camera data in bitmap and set frame
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK)
        {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            b.setImageBitmap(photo);
        }
    }
    public void AddData(String Fname,String Lname, String maj, String gender,String location ) {

        boolean insertData = mDatabaseHelper.insertPerson(Fname,Lname,maj,gender,location);

        if (insertData) {
            toastMessage("Data Successfully Inserted!");
        } else {

            toastMessage("Something went wrong");
        }
    }
    private void toastMessage(String message){

        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();

    }
    public void onFragmentInteraction(Uri uri)
    {

    }
}

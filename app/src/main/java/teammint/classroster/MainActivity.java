
package teammint.classroster;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteException;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import teammint.classroster.database.DataSource;
import teammint.classroster.model.DataStudent;


public class MainActivity extends AppCompatActivity implements StudentFragment.OnFragmentInteractionListener {
    //Declarations

    public static final ArrayList<Student> Students = new ArrayList<Student>();
    TabHost tabHost;
    Button btn;
    private static  final int CAMERA_REQUEST = 123;
    ImageView b;
    DataSource mDataSource;
    //DatabaseHelper mDatabaseHelper;
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
        //DatabaseHelper = new DatabaseHelper(this);
        mDataSource = new DataSource(this);
        mDataSource.open();
        toastMessage("Database Created");



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
            DataStudent mike = new DataStudent();

            @Override
            public void onClick(View v) {
                mike.setID(UUID.randomUUID().toString());
                mike.setFName(Fname.getText().toString());
                mike.setLName(Lname.getText().toString());
                mike.setMajor(major.getText().toString());
                mike.setHome(hometown.getText().toString());
                mike.setGender(dropdown.getSelectedItem().toString());
                mike.setNotes(notes.getText().toString());
                mike.setImage("Yeeap");
                //long numTimes = mDataSource.getdataSimilarCount(notes.toString());

                if (Fname.length() != 0 || Lname.length() != 0 || major.length() != 0 || hometown.length() != 0 || notes.length() != 0) {
                    try {
                        mDataSource.createStudent(mike);
                    } catch (SQLiteException e) {
                        e.printStackTrace();
                    }
                    toastMessage("Save Sucessfull!");
                } else {
                    toastMessage("You must enter data in ALL text fields!!");
                }
            }

        });
        List<DataStudent> listfromDB = mDataSource.getAll();
        List<String> studentNames = new ArrayList<>();
        for(DataStudent student : listfromDB)
        {
            studentNames.add(student.getFName());
        }
        ArrayAdapter<String> ada = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,studentNames);
        ListView listView = (ListView) findViewById(R.id.ListView);
        listView.setAdapter(ada);
/*
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
        */
    }
    @Override
    protected void onPause()
    {
        super.onPause();
        mDataSource.close();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        mDataSource.open();
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
    private void toastMessage(String message){

        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();

    }
    public void onFragmentInteraction(Uri uri)
    {

    }
}

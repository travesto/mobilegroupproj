
package teammint.classroster;

import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.database.sqlite.SQLiteException;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import teammint.classroster.database.DataSource;
import teammint.classroster.model.DataStudent;


public class MainActivity extends AppCompatActivity implements StudentFragment.OnFragmentInteractionListener {
    //Declarations

    TabHost tabHost;
    Button btn;
    private static  final int CAMERA_REQUEST = 123;
    ImageView b;
    DataSource mDataSource;
    private Button btnAdd, btnViewData;
//    private Spinner os;
    private EditText Fname;
    private EditText Lname;
    private AutoCompleteTextView  major;
    private EditText hometown;
    private EditText notes;
    private EditText image;
    private ListView listView;
    private boolean picTaken;
    private Bitmap photo;

    //functions
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        try {
            picTaken = false;

            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            btn = (Button) findViewById(R.id.takePic);
            b = (ImageView) findViewById(R.id.Frame);
            Fname = (EditText) findViewById(R.id.fName);
            Lname = (EditText) findViewById(R.id.lName);
//            os = (Spinner) findViewById(R.id.spinnerOS);
            // Get a reference to the AutoCompleteTextView in the layout
            major = (AutoCompleteTextView) findViewById(R.id.major);
            // Get the string array
            String[] countries = getResources().getStringArray(R.array.majors_array);
            // Create the adapter and set it to the AutoCompleteTextView
            ArrayAdapter<String> adapt = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, countries);
            major.setAdapter(adapt);
            hometown = (EditText) findViewById(R.id.location);
            notes = (EditText) findViewById(R.id.note);
            btnAdd = (Button) findViewById(R.id.addStudent);
            mDataSource = new DataSource(this);
            mDataSource.open();

            //mDataSource.wipe();
            loadData();


            final TabHost host = (TabHost) findViewById(R.id.tabHost);
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


//            final Spinner dropdownOS = (Spinner) findViewById(R.id.spinnerOS);
//            String[] itemsOS = new String[]{"Mac", "Windows", "Linux :("};
//            ArrayAdapter<String> adapterOS = new ArrayAdapter<>(this, R.layout.spinner_layout, itemsOS);
//            dropdownOS.setAdapter(adapterOS);

            final Spinner dropdown = (Spinner) findViewById(R.id.gender);
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
                    mike.setImage(photo);
                    //Object o = os.getSelectedItem();
                    //mike.setOS(o.toString());
                    //long numTimes = mDataSource.getdataSimilarCount(notes.toString());

                    if (Fname.length() != 0 && Lname.length() != 0 && major.length() != 0 && hometown.length() != 0 && notes.length() != 0 && picTaken) {
                        try {
                            mDataSource.createStudent(mike);
                            loadData();
                            host.setCurrentTab(1);
                        } catch (SQLiteException e) {
                            e.printStackTrace();
                        }

                    } else {
                        toastMessage("You must enter data in ALL text fields or Take Picture!!");
                    }
                }
            });
        }
        catch (Exception e)
        {
            String s = e.getMessage();
            s = s;
        }

    }
    @Override
    protected void onPause() { super.onPause(); mDataSource.close();}

    @Override
    protected void onResume()
    {
        super.onResume();
        mDataSource.open();
    }
    @Override
    public void onBackPressed() { }
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
        picTaken = false;
    }
    //Store camera data in bitmap and set frame
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK)
        {
            photo = (Bitmap) data.getExtras().get("data");
            b.setImageBitmap(photo);
            picTaken = true;
        }
    }
    private void toastMessage(String message){

        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();

    }
    public void onFragmentInteraction(Uri uri)
    {

    }
    public void loadData(){
        try {
            FragmentManager FragMan = getSupportFragmentManager();
            ((LinearLayout) findViewById(R.id.studentsView)).removeAllViews();
            FragmentTransaction FragTran = FragMan.beginTransaction();
            for(final DataStudent s: mDataSource.getAll())
            {
                final StudentFragment SF =  StudentFragment.newInstance(s);
                final MainActivity ActivityMe = this;
                SF.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View view)
                    {
                        Intent intent = new Intent(SF.getActivity() ,viewStudentActivity.class);
                        intent.putExtra("StudentID",s.getID());
                        startActivity(intent);
                    }
                });
                FragTran.add(R.id.studentsView, SF, s.hashCode()+s.getLName());
            }
            FragTran.commit();
        }
        catch(Exception e)
        {
            String s = e.getMessage();
            s = s;
        }
    }/*
    public void loadData2(){
        try {
            FragmentManager FragMan = getSupportFragmentManager();
            FragmentTransaction FragTran = FragMan.beginTransaction();
            for (DataStudent s: mDataSource.getAllWomen())
            {
                StudentFragment SF =  StudentFragment.newInstance(s);
                FragTran.add(R.id.studentsView, SF, s.hashCode()+s.getLName());
            }
            FragTran.commit();
        }
        catch(Exception e)
        {
            String s = e.getMessage();
            s = s;
        }
    }*/
}

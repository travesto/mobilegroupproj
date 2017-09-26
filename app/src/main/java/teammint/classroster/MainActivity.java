package teammint.classroster;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TabHost;
import android.content.Intent;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;


public class MainActivity extends AppCompatActivity {
    TabHost tabHost;
    Button btn;
    private static  final int CAMERA_REQUEST = 123;
    ImageView b;
    Drawable D;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn = (Button) findViewById(R.id.takePic);
        b = (ImageView) findViewById(R.id.Frame);

        TabHost host = (TabHost)findViewById(R.id.tabHost);
        host.setup();

        //Tab 1
        TabHost.TabSpec spec = host.newTabSpec("Add Student");
        spec.setContent(R.id.tab1);
        spec.setIndicator("Add Student");
        host.addTab(spec);

        //Tab 2
        spec = host.newTabSpec("View All");
        spec.setContent(R.id.tab2);
        spec.setIndicator("View All");
        host.addTab(spec);

        Spinner dropdown = (Spinner)findViewById(R.id.gender);
        //create a list of items for the spinner.
        String[] items = new String[]{"Male", "Female"};
        //create an adapter to describe how the items are displayed, adapters are used in several places in android.
        //There are multiple variations of this, but this is the basic variant.
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items);
        //set the spinners adapter to the previously created one.
        dropdown.setAdapter(adapter);
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
}

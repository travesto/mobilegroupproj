package teammint.classroster;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TabHost;


public class MainActivity extends AppCompatActivity {
    TabHost tabHost;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
        String[] items = new String[]{"Male", "Female","Other"};
//create an adapter to describe how the items are displayed, adapters are used in several places in android.
//There are multiple variations of this, but this is the basic variant.
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items);
//set the spinners adapter to the previously created one.
        dropdown.setAdapter(adapter);
    }
}

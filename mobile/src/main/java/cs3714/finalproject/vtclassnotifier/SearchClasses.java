package cs3714.finalproject.vtclassnotifier;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

public class SearchClasses extends AppCompatActivity {

    Spinner spinnerSubj;
    Spinner spinnerClass;
    Button search;
    ListView results;
    String[] subjectArray;
    String [] classArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_classes);

        spinnerSubj = (Spinner) findViewById(R.id.spinnerSubjects);
        spinnerClass = (Spinner) findViewById(R.id.spinnerClasses);
        search = (Button) findViewById(R.id.sendQuery);
        results = (ListView) findViewById(R.id.results);

        subjectArray = new String[]{
                "Computer Science",
                "Mechanical Engineering",
                "Testing",
                "Test2",
                "Test3"
        };

        classArray = new String[]{
                "2114",
                "2505",
                "3114",
                "3714",
                "3724"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, subjectArray);
        spinnerSubj.setAdapter(adapter);

        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, classArray);
        spinnerClass.setAdapter(adapter2);

    }
}

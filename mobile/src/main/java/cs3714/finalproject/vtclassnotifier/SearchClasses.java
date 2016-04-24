package cs3714.finalproject.vtclassnotifier;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

public class SearchClasses extends AppCompatActivity {

    Spinner spinnerTerm;
    Spinner spinnerSubj;
    Button search;
    ListView results;
    String[] subjectArray;
    String [] termArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_classes);

        spinnerSubj = (Spinner) findViewById(R.id.spinnerSubjects);
        spinnerTerm = (Spinner) findViewById(R.id.spinnerTerm);
        search = (Button) findViewById(R.id.sendQuery);
        results = (ListView) findViewById(R.id.results);


        termArray = new String[]{
                "Summer I 2016",
                "Summer II 2016",
                "Fall 2016",
                "Winter 2016",
                "Spring 2017"
        };

        subjectArray = new String[]{
                "Computer Science",
                "Mechanical Engineering",
                "Testing",
                "Test2",
                "Test3"
        };


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, subjectArray);
        spinnerSubj.setAdapter(adapter);


        ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, termArray);
        spinnerTerm.setAdapter(adapter3);

    }
}

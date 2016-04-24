package cs3714.finalproject.vtclassnotifier;

import android.app.ActionBar;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;

public class ClassCartActivity extends AppCompatActivity implements View.OnClickListener {

    Button backHome;
    Button remove1;
    Button remove2;
    Button remove3;
    String[] testArray;
    GridView gridView;
    LinearLayout ll;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_cart);
        backHome = (Button) findViewById(R.id.backHome);
        backHome.setOnClickListener(this);
        gridView = (GridView) findViewById(R.id.gridView);
        ll = (LinearLayout) findViewById(R.id.buttonList);

        for (int i = 0; i < 3; i++) {
            Button btn = new Button(this);
            btn.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                    btn.setId(i);
            btn.setText("X");
            btn.setOnClickListener(this);
            ll.addView(btn);

        }


        testArray = new String[]{
                "CS 2114",
                "31423",
                "CS 3114",
                "41242",
                "CS 2505",
                "51423"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, testArray);

        gridView.setAdapter(adapter);






    }

    @Override
    public void onClick(View v) {
        if (v.getId() == backHome.getId()) {
            finish();
        }
    }
}

package cs3714.finalproject.vtclassnotifier;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.HashMap;

public class ClassCartActivity extends AppCompatActivity implements View.OnClickListener {

    Button backHome;
    Button remove1;
    Button remove2;
    Button remove3;
    String[] testArray;
    GridView gridView;
    LinearLayout ll;
    HashMap<Integer, CourseInfo> hashMap;
    HashMap<Integer, Integer> idToCRN;
    ArrayAdapter<String> adapter;
    ArrayList<String> c;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_cart);
        backHome = (Button) findViewById(R.id.backHome);
        backHome.setOnClickListener(this);
        gridView = (GridView) findViewById(R.id.gridView);
        ll = (LinearLayout) findViewById(R.id.buttonList);
        idToCRN = new HashMap<>();
//        for (int i = 0; i < 3; i++) {
//            Button btn = new Button(this);
//            btn.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
//                    btn.setId(i);
//            btn.setText("X");
//            btn.setOnClickListener(this);
//            ll.addView(btn);
//
//        }


//        testArray = new String[]{
//                "CS 2114",
//                "31423",
//                "CS 3114",
//                "41242",
//                "CS 2505",
//                "51423"
//        };
//
        hashMap = new HashMap<>();
        HashMap<Integer, ArrayList<String>> h = null;
        Intent i = getIntent();

        c = new ArrayList<>();
        if (i != null)
        {
            h = (HashMap)i.getSerializableExtra("CART");
        }
        if(h != null) {
            for(ArrayList<String> list : h.values()) {
                CourseInfo info = new CourseInfo(list);
                hashMap.put(info.getCrn(), info);
                c.add(info.getDepartment() + "-" + info.getCourseNumber());
                c.add(info.getCrn() + "");
                Button btn = new Button(this);
                btn.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                int id = View.generateViewId();
                btn.setId(id);
                idToCRN.put(id, info.getCrn());
                btn.setText("X");
                btn.setOnClickListener(this);
                ll.addView(btn);
            }

            adapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_1, c);

            gridView.setAdapter(adapter);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent();
        i.putExtra("CART", hashMap);
        setResult(RESULT_OK, i);
        finish();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == backHome.getId()) {
            Intent i = new Intent();
            i.putExtra("CART", hashMap);
            setResult(RESULT_OK, i);
            finish();
        }
        else if(idToCRN.containsKey(v.getId()))
        {

            CourseInfo c = hashMap.remove(idToCRN.remove(v.getId()));
            adapter.remove(c.getDepartment() + "-" + c.getCourseNumber());
            adapter.remove(c.getCrn() + "");
            adapter.notifyDataSetChanged();
            v.setVisibility(View.GONE);
        }
    }
}

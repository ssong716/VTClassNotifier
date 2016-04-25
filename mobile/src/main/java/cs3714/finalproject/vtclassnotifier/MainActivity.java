package cs3714.finalproject.vtclassnotifier;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button submit;
    Button classCart;
    Button searchClasses;
    Button options;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        submit = (Button) findViewById(R.id.submitButton);
        classCart = (Button) findViewById(R.id.cart);
        searchClasses = (Button) findViewById(R.id.searchClasses);
        options = (Button) findViewById(R.id.options);
        submit.setOnClickListener(this);
        classCart.setOnClickListener(this);
        searchClasses.setOnClickListener(this);
        options.setOnClickListener(this);



    }

    @Override
    public void onClick(View v) {
        if (v.getId() == submit.getId()) {

        } else if (v.getId() == classCart.getId()) {

        } else if (v.getId() == searchClasses.getId()) {
            Intent searchClass = new Intent(this, SearchClasses.class);
            startActivity(searchClass);

        } else if (v.getId() == options.getId()) {

        }


    }
}

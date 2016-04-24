package cs3714.finalproject.vtclassnotifier;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText crn;
    Button submit;
    Button classCart;
    Button searchClasses;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        submit = (Button) findViewById(R.id.submitButton);
        classCart = (Button) findViewById(R.id.cart);
        searchClasses = (Button) findViewById(R.id.searchClasses);
        submit.setOnClickListener(this);
        classCart.setOnClickListener(this);
        searchClasses.setOnClickListener(this);
        crn = (EditText) findViewById(R.id.crnNumber);



    }

    @Override
    public void onClick(View v) {
        if (v.getId() == submit.getId()) {


        } else if (v.getId() == classCart.getId()) {
            Intent classCart = new Intent(this, ClassCartActivity.class);
            startActivity(classCart);

        } else if (v.getId() == searchClasses.getId()) {
            Intent searchClass = new Intent(this, SearchClasses.class);
            startActivity(searchClass);

        }


    }
}

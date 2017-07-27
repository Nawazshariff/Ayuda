package com.example.tabu.testing;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by SummyTabu on 3/12/2016.
 */
public class Accept extends Activity  {
    TextView t1, t2, t3, t4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.accept);
        t1= (TextView) findViewById(R.id.accname);
        t2= (TextView) findViewById(R.id.accdes);
        t3= (TextView) findViewById(R.id.accloc);
        t4= (TextView) findViewById(R.id.acctime);

        Intent accept = getIntent();
        String name= accept.getStringExtra("name");
        t1.setText(name);

    }
}

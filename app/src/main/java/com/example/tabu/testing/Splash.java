package com.example.tabu.testing;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Created by nawazshariff on 17-03-2016.
 */
public class Splash extends Activity {
    SharedPreferences sharedPreferences;
    TextView t1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        sharedPreferences = getSharedPreferences("nawaz", MODE_PRIVATE);
        t1= (TextView) findViewById(R.id.splashtext);

        Thread thread = new Thread() {
            @Override
            public void run() {
                try {

                    sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                Intent s;

                {
                    if (sharedPreferences.getBoolean("signup", false)) {
                        s = new Intent(Splash.this, Home.class);
                    } else
                        s = new Intent(Splash.this,MainActivity.class);

                    startActivity(s);

                    finish();
                }
            }
        };
        thread.start();

    }
}

package com.example.tabu.testing;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by SummyTabu on 3/11/2016.
 */
public class Home extends Activity implements View.OnClickListener {
    TextView t1;
    Button donate,receive;
    private ListView mDrawerList;
    private ArrayAdapter<String> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        t1= (TextView) findViewById(R.id.status);
        donate= (Button) findViewById(R.id.donate);

        mDrawerList = (ListView)findViewById(R.id.navList);
        String[] osArray = { "HOME", "MESSAGES" };
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, osArray);
        mDrawerList.setAdapter(mAdapter);





        donate.setOnClickListener(this);

        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(Home.this, "Time for an upgrade!", Toast.LENGTH_SHORT).show();
                if(position==0){
                    Intent home=new Intent(Home.this,Home.class);
                    startActivity(home);

                }
                else
                {
                    Intent msg=new Intent(Home.this,Messages.class);
                    startActivity(msg);

                }
            }
        });
    }

    @Override
    public void onClick(View v) {

        Intent i = new Intent(this, Description.class);
        startActivity(i);

    }
    }


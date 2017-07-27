package com.example.tabu.testing;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;

import android.os.Parcelable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by nawazshariff on 19-03-2016.
 */
public class Messages extends Activity {
    TextView t;
    RecyclerView recyclerView;
    String name,location,description,time;
    BufferedReader mBufferedInputStream;
    String Response = "";
    List<DataHolder> data=new ArrayList<>();
    Context context;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.messages);
        onNewIntent(getIntent());
        t= (TextView) findViewById(R.id.messages);
        recyclerView = (RecyclerView)
                findViewById(R.id.recyclerView);


          fecthing();




    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Bundle extras=intent.getExtras();
        if(extras!=null)
        {
            if(extras.containsKey("name")){
                 name=extras.getString("name");
            }
            if(extras.containsKey("location")){
                 location=extras.getString("location");
            }
            if(extras.containsKey("description")){
                 description=extras.getString("description");
            }
            if(extras.containsKey("time")){
                 time=extras.getString("time");
            }

        }
    }
        private  void fecthing()
        {
            new asynctask().execute();
        }

    public class asynctask extends AsyncTask<Void, Void, Boolean> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();


        }

        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                URL url = new URL("http://nawaznowman.net16.net/recycler.php");
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                httpURLConnection.setConnectTimeout(15000);
                httpURLConnection.setReadTimeout(10000);
                httpURLConnection.setDoInput(true);
                //  httpURLConnection.setDoOutput(true);


                httpURLConnection.connect();


                if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {

                    mBufferedInputStream = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                    String inline;
                    while ((inline = mBufferedInputStream.readLine()) != null) {
                        Response += inline;
                    }
                    mBufferedInputStream.close();

                    Log.d("beforeparsing1",Response);


                    parseJson(Response);


                } else {
                    Log.d("nawaz", "something wrong");

                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);



           recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

            ArticleAdapter mAdapter = new ArticleAdapter(getApplicationContext(),data);
            mAdapter.notifyDataSetChanged();
            recyclerView.setAdapter(mAdapter);







        }

        JSONObject jobject1;
        JSONArray jArray ;




        private void parseJson(String response) {
            //   Information data1;

            Log.d("jason", response);

            try {
             jArray=new JSONArray(response);
                for (int i = 0; i < jArray.length(); i++) {
                   JSONObject jobject= jArray.getJSONObject(i);
                    DataHolder hold = new DataHolder();
                    hold.name=jobject.getString("uname");
                    hold.loc=jobject.getString("ulocation");
                    hold.des=jobject.getString("udescription");
                    hold.time=jobject.getString("utime");
                    data.add(hold);

                    // dataList.add(data1);
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


    }
}

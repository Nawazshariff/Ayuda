package com.example.tabu.testing;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by SummyTabu on 3/11/2016.
 */
public class Description extends Activity implements View.OnClickListener {
    TextView t1, t2, t3, t4, t5;
    EditText e1, e2, e3, e4;
    Button b;
    BufferedReader mBufferedInputStream;
    String Response = "";
    String d;
    //    String[] c;
    ArrayList<String> c = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.description);

        t1 = (TextView) findViewById(R.id.textView2);
        t2 = (TextView) findViewById(R.id.textView6);
        t3 = (TextView) findViewById(R.id.textView3);
        t4 = (TextView) findViewById(R.id.textView5);
        t5 = (TextView) findViewById(R.id.textView);

        e1 = (EditText) findViewById(R.id.editText);
        e2 = (EditText) findViewById(R.id.editText2);
        e3 = (EditText) findViewById(R.id.editText3);
        e4 = (EditText) findViewById(R.id.editText4);

        b = (Button) findViewById(R.id.button);




        b.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        if (e1.getText().toString().length() < 1 || e2.getText().toString().length() < 1 || e3.getText().toString().length() < 1 | e4.getText().toString().length() < 1) {

            // out of range
            Toast.makeText(this, "please enter something", Toast.LENGTH_LONG).show();
        } else {
            String[] data = {e1.getText().toString(), e2.getText().toString(), e3.getText().toString(), e4.getText().toString()};

            new MyAsyncTask().execute(data);
            submitting();    //email
            new asynctask1().execute(data);     //push notification


        }

    }

    private void submitting() {
        new asynctask().execute();
    }



    private class asynctask1 extends AsyncTask<String, Integer, Double> {

        @Override
        protected Double doInBackground(String... params) {
            // TODO Auto-generated method stub
//            postData(params[0]);
            //Log.d("background","params"+params);
            test(params);
            return null;
        }

        protected void onPostExecute(Double result){

            Toast.makeText(getApplicationContext(), "notification ", Toast.LENGTH_LONG).show();

        }
        protected void onProgressUpdate(Integer... progress){

        }


    }

    public void test(String[] value){

        String Response="";
    Log.d("TAG","value[0]"+value[0]);
        URL url;
        try {
            url = new URL("http://nawaznowman.net16.net/senddata.php");
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

            httpURLConnection.setDoInput(true);
            //182.71.135.170
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setRequestMethod("POST");

            Uri.Builder builder = new Uri.Builder()
                    .appendQueryParameter("name",value[0])
                    .appendQueryParameter("description",value[1])
                    .appendQueryParameter("location",value[2])
                    .appendQueryParameter("time",value[3]);

            String query = builder.build().getEncodedQuery();

            OutputStream os = httpURLConnection.getOutputStream();

            BufferedWriter mBufferedWriter = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            mBufferedWriter.write(query);
            mBufferedWriter.flush();
            mBufferedWriter.close();
            os.close();

            httpURLConnection.connect();
            BufferedReader mBufferedInputStream = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
            String inline;
            while ((inline = mBufferedInputStream.readLine()) != null) {
                Response += inline;
            }
            mBufferedInputStream.close();
            Log.d("response", Response);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }





    public class asynctask extends AsyncTask<Void, Void, Boolean> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(Description.this, "enterd the async", Toast.LENGTH_SHORT).show();

        }

        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                URL url = new URL("http://nawaznowman.net16.net/final.php");
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

            //nawazadapter.notifyDataSetChanged();
            Intent email = new Intent(Intent.ACTION_SEND);
            String t[] = new String[50];
            for (int j = 0; j < c.size(); j++)
                t[j] = c.get(j);
            email.putExtra(Intent.EXTRA_EMAIL, t);
            Log.d("TEst", c + "");
            email.putExtra(Intent.EXTRA_SUBJECT, "A new item is up for donation by "+e1.getText().toString());

            email.putExtra(Intent.EXTRA_TEXT, "DETAILS ARE :-  \n"+"ITEM : "+e2.getText().toString()+"\n"+"LOCATION : "+e3.getText().toString()
            +"\n"+"DATE AND TIME : "+e4.getText().toString());
            email.setType("message/rfc822");
            startActivity(Intent.createChooser(email, "Choose an Email client :"));
            finish();


        }

        JSONObject jsonArray;


        private void parseJson(String response) {
            //   Information data1;
            Log.d("darshan", response);

            try {
                jsonArray = new JSONObject(response);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject sub = jsonArray.getJSONObject(i + "");


                    d = sub.getString("email");
                    Log.d("nawaz", d);
                    c.add(d);
                    // dataList.add(data1);

                }


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


    }

    private class MyAsyncTask extends AsyncTask<String, Integer, Double> {

        @Override
        protected Double doInBackground(String... params) {
            // TODO Auto-generated method stub
//            postData(params[0]);
            test(params[0], params[1], params[2], params[3]);
            return null;
        }

        protected void onPostExecute(Double result) {

            Toast.makeText(getApplicationContext(), "your data has been recorded", Toast.LENGTH_LONG).show();
            //  Intent intent=new Intent(MainActivity.this,Home.class);
            //startActivity(intent);
        }

        protected void onProgressUpdate(Integer... progress) {

        }

        public void postData(String valueIWantToSend) {
            // Create a new HttpClient and Post Header
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost("http://nawaznowman.net16.net/server.php");

            try {
                // Add your data
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("myHttpData", valueIWantToSend));
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                // Execute HTTP Post Request
                HttpResponse response = httpclient.execute(httppost);

            } catch (ClientProtocolException e) {
                // TODO Auto-generated catch block
            } catch (IOException e) {
                // TODO Auto-generated catch block
            }
        }

    }

    public void test(String value1, String value2, String value3, String value4) {

        String Response = "";

        URL url;
        try {
            url = new URL("http://nawaznowman.net16.net/description.php");
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

            httpURLConnection.setDoInput(true);
            //182.71.135.170
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setRequestMethod("POST");

            Uri.Builder builder = new Uri.Builder()
                    .appendQueryParameter("name", value1)
                    .appendQueryParameter("description", value2)
                    .appendQueryParameter("location", value3)
                    .appendQueryParameter("time", value4);

            String query = builder.build().getEncodedQuery();

            OutputStream os = httpURLConnection.getOutputStream();

            BufferedWriter mBufferedWriter = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            mBufferedWriter.write(query);
            mBufferedWriter.flush();
            mBufferedWriter.close();
            os.close();

            httpURLConnection.connect();
            BufferedReader mBufferedInputStream = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
            String inline;
            while ((inline = mBufferedInputStream.readLine()) != null) {
                Response += inline;
            }
            mBufferedInputStream.close();
            Log.d("response", Response);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}


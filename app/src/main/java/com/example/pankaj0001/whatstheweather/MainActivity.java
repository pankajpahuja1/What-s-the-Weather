package com.example.pankaj0001.whatstheweather;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {
    TextView show ;
    EditText city;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        show =findViewById(R.id.weathertextView);
        city=findViewById(R.id.cityEditText);


    }
    public void showWeather(View view)
    {
        Weatherclass weatherclass= new Weatherclass();
        try {
            weatherclass.execute("https://api.openweathermap.org/data/2.5/weather?q="+city.getText().toString()+"&APPID=acdaf2918b810e6bce50a1c8a0580e67").get();

            InputMethodManager inputMethodManager= (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(city.getWindowToken(),0);
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(),"Couldn't Find Weather",Toast.LENGTH_LONG).show();
        }/*
        catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        */

    }//http://api.openweathermap.org/data/2.5/weather?q=paris&APPID=acdaf2918b810e6bce50a1c8a0580e67


    public class Weatherclass extends AsyncTask<String,Void,String>
    {

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            try {

                JSONObject jsonObject= new JSONObject(result);

            // System.out.println(jsonObject.getString("weather"));
               // System.out.println("*************************************************************");
               JSONArray jsonArray= jsonObject.getJSONArray("weather");
               String main =jsonArray.getJSONObject(0).getString("main");
               String desc= jsonArray.getJSONObject(0).getString("description");
              // System.out.println("***************mai hu khalnayak**********************");

              //  System.out.println(main+":"+desc);

                show.setText(main+":"+desc);

            }/* catch (JSONException e) {
                e.printStackTrace();
            }
            */catch (Exception e) {
                Toast.makeText(getApplicationContext(),"Couldn't Find Weather",Toast.LENGTH_LONG).show();
            }


        }

        @Override
        protected String doInBackground(String... strings) {

            try {
                String result="";
                URL url= new URL(strings[0]);
                HttpsURLConnection httpsURLConnection =(HttpsURLConnection) url.openConnection();
                httpsURLConnection.connect();
                InputStream inputStream= httpsURLConnection.getInputStream();
                InputStreamReader reader= new InputStreamReader(inputStream);


                int data = reader.read();

                while(data!=-1)
                {
                    char putInString= (char)data;
                    result+= putInString;
                    data= reader.read();
                 //   Log.i("ye chalgya demo class","function");
                }

                return result;



            } /*catch (Exception e) {
                Toast.makeText(getApplicationContext(),"Couldn't Find Weather",Toast.LENGTH_LONG);
            }
            */ catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            catch (Exception e) {
                Toast.makeText(getApplicationContext(),"Couldn't Find Weather",Toast.LENGTH_LONG).show();

            }


            return null;


        }

    }




}

package me.a3zcs.restaurants.restaurants;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ProgressBar;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private final String ENDPOINT = "http://www.3zcs.me/restaurant.json";
    ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);
        FetchData obj = new FetchData();
        obj.execute(ENDPOINT);
    }


    private class FetchData extends AsyncTask<String, Void, List<String>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //TODO START PROGRESS BAR

        }

        @Override
        protected List<String> doInBackground(String... strings) {
            List<String> imageList = new ArrayList<>();
            HttpURLConnection connection = null;
            try {

                URL url = new URL(strings[0]);

                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");

                connection.connect();

                InputStream stream = new BufferedInputStream(connection.getInputStream());
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream, "UTF-8"));

                StringBuilder builder = new StringBuilder();
                String inputString;
                while ((inputString = bufferedReader.readLine()) != null) {
                    builder.append(inputString);
                }

                Log.i("result", builder.toString());
                JSONObject topLevel = new JSONObject(builder.toString());
                //TODO FILL imageList WITH URLs

            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }

            if (connection != null)
                connection.disconnect();
            return imageList;

        }

        @Override
        protected void onPostExecute(List<String> images) {
            super.onPostExecute(images);
            //TODO STOP PROGRESS BAR, PRINT OUR IMAGE URLS ON LOG

            for (int i = 0; i < images.size(); i++)
                Log.i("My List", images.get(i));
        }
    }
}

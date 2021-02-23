package com.example.android.hw2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class FourthActivity extends AppCompatActivity {

    private TextView textView_name;
    private TextView textView_abv;
    private TextView textView_firstBrewed;
    private TextView textView_description;
    private TextView textView_foodPairings;
    private TextView textView_tips;
    private ImageView imageView_image;

    private static AsyncHttpClient client = new AsyncHttpClient();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fourth);
        Intent intent = getIntent();

        textView_name = findViewById(R.id.textView_namePersonal);
        textView_abv = findViewById(R.id.textView_abv);
        textView_firstBrewed = findViewById(R.id.textView_firstBrewed);
        textView_description = findViewById(R.id.textView_descriptionPersonal);
        textView_foodPairings = findViewById(R.id.textView_food);
        textView_tips = findViewById(R.id.textView_tips);
        imageView_image = findViewById(R.id.imageView_brewPersonal);


        String name = intent.getStringExtra("name");

        String api_url = "https://api.punkapi.com/v2/beers?beer_name=" + name;
        client.get(api_url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    JSONArray json = new JSONArray(new String(responseBody));
                    JSONObject object = json.getJSONObject(0);

                    textView_name.setText(object.getString("name"));
                    textView_abv.setText("ABV: " + object.getString("abv"));
                    textView_firstBrewed.setText("First Brewed: " + object.getString("first_brewed"));
                    textView_description.setText("Description: " + object.getString("description"));
                    textView_tips.setText("Brewers Tips: " + object.getString("brewers_tips"));

                    String img_url = object.getString("image_url");
                    Picasso.get().load(img_url).into(imageView_image);
                    JSONArray foodPairing = object.getJSONArray("food_pairing");


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.e("api error", new String(responseBody));
            }
        });
    }
}

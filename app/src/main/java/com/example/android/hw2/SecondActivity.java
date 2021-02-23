package com.example.android.hw2;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

import cz.msebera.android.httpclient.Header;

public class SecondActivity extends AppCompatActivity {


    private static String api_url;
    private static AsyncHttpClient client = new AsyncHttpClient();
    private String[] inputStrings = new String[4]; // 5th is to check which date combination
    private Button button_go;

    // XML input forms
    private EditText editText_beerName;
    private EditText date_start;
    private EditText date_end;
    private Switch switch_highPoint;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        // find all input forms
        editText_beerName = findViewById(R.id.editText_beerName);
        date_start = findViewById(R.id.date_start);
        date_end = findViewById(R.id.date_end);
        switch_highPoint = findViewById(R.id.switch_highPoint);

        button_go = findViewById(R.id.button_go);
        button_go.setOnClickListener(v -> getInputs(v));

    }

    // get inputs
    private void getInputs(View view){
        Arrays.fill(inputStrings, null);
        // check beer name
        if(!editText_beerName.getText().toString().trim().equals("")){
            inputStrings[0] = editText_beerName.getText().toString().trim();
        }

        // check high-point beer
        if(switch_highPoint.isChecked()){
            inputStrings[3] = "true";
        }

        // check date validity
//        date_start.getText().toString().split("/");
//        date_end.getText().toString().split("/");

        // after a certain date
        if (!date_start.getText().toString().isEmpty() && date_end.getText().toString().isEmpty()){
            if(date_start.getText().toString().split("/")[0].length() == 2 && date_start.getText().toString().split("/")[1].length() == 4) {
                inputStrings[1] = date_start.getText().toString().replace("/", "-");
            }
            else{
                showToastDate(view);
            }
        }
        // before a certain date
        else if (!date_end.getText().toString().isEmpty() && date_start.getText().toString().isEmpty()){
            if(date_end.getText().toString().split("/")[0].length() == 2 && date_end.getText().toString().split("/")[1].length() == 4) {
                inputStrings[2] = date_end.getText().toString().replace("/", "-");
            }
            else{
                showToastDate(view);
            }
        }
        // between certain dates
        else if (!date_end.getText().toString().isEmpty() && !date_start.getText().toString().isEmpty()){
            if(date_end.getText().toString().split("/")[0].length() == 2 && date_end.getText().toString().split("/")[1].length() == 4 && date_end.getText().toString().split("/")[0].length() == 2 && date_end.getText().toString().split("/")[1].length() == 4) {
                inputStrings[1] = date_start.getText().toString().replace("/", "-");
                inputStrings[2] = date_end.getText().toString().replace("/", "-");
            }
            else{
                showToastDate(view);
            }
        }

        launchNextActivity(view);
    }

    // toast for incomplete form
    public void showToastDate(View view){
        // create a toast with a message saying hello
        Toast toast = Toast.makeText(this, R.string.toast_message_date, Toast.LENGTH_SHORT); // short -> 2 seconds?
        toast.show();
    }

    public void launchNextActivity(View view){

        api_url = "https://api.punkapi.com/v2/beers?";

        // build api_url with inputStrings
        if(inputStrings[0] != null){
            api_url += "&beer_name=" + inputStrings[0];
        }

        if(inputStrings[1] != null){
            api_url += "&brewed_after=" + inputStrings[1];
        }
        if(inputStrings[2] != null){
            api_url += "&brewed_before=" + inputStrings[2];
        }
        if(inputStrings[3] != null){
            api_url += "&abv_gt=3.99";
        }
        else{
            api_url += "&abv_lt=4";
        }

        Log.println(Log.INFO, "api_url", api_url);
        client.get(api_url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Intent intent = new Intent(SecondActivity.this, ThirdActivity.class);

                try {
                    JSONArray jsonArray = new JSONArray(new String(responseBody));

                    String[] names = new String[jsonArray.length()];
                    String[] descriptions = new String[jsonArray.length()];
                    String[] img_url = new String[jsonArray.length()];


                    for (int i = 0; i < jsonArray.length(); i++){
                        JSONObject object = jsonArray.getJSONObject(i);
                        names[i] = object.getString("name");
                        descriptions[i] = object.getString("description");
                        img_url[i] = object.getString("image_url");
                    }

                    Log.println(Log.INFO, "names", names[0]);
                    Log.println(Log.INFO, "descriptions", descriptions[0]);
                    Log.println(Log.INFO, "img_url", img_url[0]);
                    intent.putExtra("names", names);
                    intent.putExtra("descriptions", descriptions);
                    intent.putExtra("img_url", img_url);
                    intent.putExtra("count", jsonArray.length());
                    startActivity(intent);

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

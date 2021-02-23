package com.example.android.hw2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    private Button start_button;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        start_button = findViewById(R.id.start_buttton);
        start_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.this.launchNextActivity(v);
            }
        });

        // get imageView
        imageView = findViewById(R.id.imageView_main);
        // set imageView to assets image
        InputStream inputStream = null;
        try {
            inputStream = getAssets().open("brew.png");
            Drawable brew = Drawable.createFromStream(inputStream, null);
            imageView.setImageDrawable(brew);
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void launchNextActivity(View view){
        Intent intent  = new Intent(MainActivity.this, SecondActivity.class);
        startActivity(intent);
    }
}
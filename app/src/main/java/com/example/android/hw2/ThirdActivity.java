package com.example.android.hw2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ThirdActivity extends AppCompatActivity {

    private ArrayList<Brew> brews;
    private RecyclerView recyclerView;
    private Context context;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        setContentView(R.layout.activity_third);

        recyclerView = findViewById(R.id.recyclerView);
        context = recyclerView.getContext();
        brews = new ArrayList<>();

        int count = intent.getIntExtra("count", 0);


        String[] names = new String[count];
        String[] descriptions = new String[count];
        String[] img_url = new String[count];

        names = intent.getStringArrayExtra("names");
        descriptions = intent.getStringArrayExtra("descriptions");
        img_url = intent.getStringArrayExtra("img_url");


        for (int i = 0; i < count; i++) {
            Brew brew = new Brew(names[i],
                    descriptions[i],
                    img_url[i]);
            // add it to the array list
            brews.add(brew);
        }

        BrewAdapter adapter = new BrewAdapter(brews, context);


        recyclerView.setAdapter(adapter);
        // define where to add the layout manager
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        // you need a layout manager, otherwise your recycler view is not going to show


        // a lot of properties you can set
        // smooth scrolling
        recyclerView.setHasFixedSize(true); // enables a smoother scrolling

        // add decorations
        // DividerItemDecorations
        // add a line between each row
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);

        recyclerView.addItemDecoration(itemDecoration);
    }
}

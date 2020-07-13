package com.example.dominik.flappybird;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class Stats extends AppCompatActivity {

    SQLiteHelper db;
    ListView list;
    ListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        db = new SQLiteHelper(this);
        list = findViewById(R.id.listOfScores);

        ArrayList<DBScore> scores = db.getAllScores();
        adapter = new ListAdapter(this, R.layout.score, scores);
        if(scores.size() < 10) {
            for(int i = scores.size(); i < 10; i++){
                adapter.add(new DBScore(0));
            }
        }
        list.setAdapter(adapter);
    }
}

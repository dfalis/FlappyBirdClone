package com.example.dominik.flappybird;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Menu extends AppCompatActivity {

    Button btnPlay;
    Button btnStats;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        btnPlay = findViewById(R.id.btnPlay);
        btnStats = findViewById(R.id.btnStats);
        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startGame();
            }
        });
        btnStats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showStats();
            }
        });
    }

    private void startGame(){
        Intent game = new Intent(this, Game.class);
        startActivity(game);
    }
    private void showStats(){
        Intent stats = new Intent(this, Stats.class);
        startActivity(stats);
    }
}

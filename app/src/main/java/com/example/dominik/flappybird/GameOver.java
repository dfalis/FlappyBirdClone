package com.example.dominik.flappybird;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class GameOver extends AppCompatActivity {
    Button playAgain;
    TextView points;
    SQLiteHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        playAgain = findViewById(R.id.btnPlayAgain);
        points = findViewById(R.id.points);

        Intent intent = getIntent();
        int score = intent.getIntExtra("SCORE", 0);
        points.setText(score + (score == 1 ? " point" : " points"));
        db = new SQLiteHelper(this);
        db.addScore(new DBScore(score));


        playAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startGame();
            }
        });
    }

    private void startGame(){
        Intent game = new Intent(this, Game.class);
        startActivity(game);
    }
}

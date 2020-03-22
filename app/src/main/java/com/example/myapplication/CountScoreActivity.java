package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class CountScoreActivity extends AppCompatActivity {

    TextView score;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_count_score2);

        score=(TextView) findViewById(R.id.score);
    }
    public  void btnAdd3(View btn){
        showScore(3);
    }
    public  void btnAdd2(View btn){
        showScore(2);

    }
    public  void btnAdd1(View btn){
        showScore(1);

    }
    public  void btnReset(View btn){
        score.setText("0");
    }
    private void showScore(int inc){
        String oldScore=(String)score.getText();
        int nowScore=Integer.parseInt(oldScore)+inc;
        score.setText(""+nowScore);
    }
}

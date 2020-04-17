package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class CountScoreActivity extends AppCompatActivity {

    TextView score;
    TextView score2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_count_score2);

        score=(TextView) findViewById(R.id.score);
        score2=(TextView) findViewById(R.id.T2score);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        String scorea=((TextView) findViewById(R.id.score)).getText().toString();
        String scoreb=((TextView) findViewById(R.id.T2score)).getText().toString();

        outState.putString("teama_score",scorea);
        outState.putString("teamb_score",scoreb);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        String a=savedInstanceState.getString("teama_score");
        String b=savedInstanceState.getString("teamb_score");

        ((TextView) findViewById(R.id.score)).setText(a);
        ((TextView) findViewById(R.id.T2score)).setText(b);
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

    public  void btn2Add3(View btn){
        showScore2(3);
    }
    public  void btn2Add2(View btn){
        showScore2(2);

    }
    public  void btn2Add1(View btn){
        showScore2(1);

    }


    public  void btnReset(View btn){
        score.setText("0");
        score2.setText("0");
    }
    private void showScore(int inc){
        String oldScore=(String)score.getText();
        int nowScore=Integer.parseInt(oldScore)+inc;
        score.setText(""+nowScore);
    }
    private void showScore2(int inc){
        String oldScore=(String)score2.getText();
        int nowScore=Integer.parseInt(oldScore)+inc;
        score2.setText(""+nowScore);
    }
}

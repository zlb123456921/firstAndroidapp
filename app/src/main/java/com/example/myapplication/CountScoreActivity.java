package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

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

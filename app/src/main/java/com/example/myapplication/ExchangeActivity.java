package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ExchangeActivity extends AppCompatActivity {
    TextView rmb;
    TextView exchanged;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exchange);

        rmb=(TextView) findViewById(R.id.editText3);
        exchanged=(TextView) findViewById(R.id.textView3);
    }
    public void onClick2(View m_view){
        String origin=(String)rmb.getText().toString();
        if(origin.isEmpty()==true){
            Toast.makeText(this,"金额不能为空",Toast.LENGTH_SHORT).show();
            return;
        }
        double result=Double.parseDouble(origin);
        if(m_view.getId()==R.id.button3){ result*=0.1413d; }
        if(m_view.getId()==R.id.button4){result*=0.1277d;}
        if(m_view.getId()==R.id.button5){result*=0.1153d;}
        if(m_view.getId()==R.id.button6){result*=15.3098d;}
        if(m_view.getId()==R.id.button7){result*=0.1356d;}
        if(m_view.getId()==R.id.button8){result*=0.2315d;}
        if(m_view.getId()==R.id.button9){result*=0.1978d;}
        if(m_view.getId()==R.id.button10){result*=1.0954d;}
        exchanged.setText(String.format("%.2f",result));
    }


      public void open_one(View v){
          Intent count = new Intent(this,CountScoreActivity.class);
          startActivity(count);
     }
}

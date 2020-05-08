package com.example.myapplication;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SingleExchangeActivity extends AppCompatActivity {
    String title=null;
    Float rate=0f;
    TextView input=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_exchange);

       title = getIntent().getStringExtra("title");
       rate = getIntent().getFloatExtra("rate",0f);
        Log.i("SingleTitle=",title);
        ((TextView)findViewById(R.id.textView)).setText(title);
        input=(TextView)findViewById(R.id.editText11);
        input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                TextView show=(TextView) SingleExchangeActivity.this.findViewById(R.id.textView4);
                if(s.length()>0){
                    Float temp=Float.parseFloat(String.valueOf(s.toString()));

                    show.setText(temp+"RMB==>"+(100/rate*temp));
                }else
                    show.setText("");

            }
        });
    }
}

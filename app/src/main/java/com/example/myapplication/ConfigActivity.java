package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ConfigActivity extends AppCompatActivity {
    TextView usa_t;
    TextView eur_t;
    TextView eng_t;
    TextView jan_t;
    TextView fra_t;
    TextView aus_t;
    TextView can_t;
    TextView hon_t;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);


        Intent intent=getIntent();
        float usa2=intent.getFloatExtra("usa_rate",0.0f);
        float eur2=intent.getFloatExtra("eur_rate",0.0f);
        float eng2=intent.getFloatExtra("eng_rate",0.0f);
        float jan2=intent.getFloatExtra("jan_rate",0.0f);
        float fra2=intent.getFloatExtra("fra_rate",0.0f);
        float aus2=intent.getFloatExtra("aus_rate",0.0f);
        float can2=intent.getFloatExtra("can_rate",0.0f);
        float hon2=intent.getFloatExtra("hon_rate",0.0f);

        usa_t=(TextView) findViewById(R.id.editText2);
        eur_t=(TextView) findViewById(R.id.editText4);
        eng_t=(TextView) findViewById(R.id.editText5);
        jan_t=(TextView) findViewById(R.id.editText6);
        fra_t=(TextView) findViewById(R.id.editText7);
        aus_t=(TextView) findViewById(R.id.editText8);
        can_t=(TextView) findViewById(R.id.editText9);
        hon_t=(TextView) findViewById(R.id.editText10);

        usa_t.setText(String.format("%.2f",usa2));
        eur_t.setText(String.format("%.2f",eur2));
        eng_t.setText(String.format("%.2f",eng2));
        jan_t.setText(String.format("%.2f",jan2));
        fra_t.setText(String.format("%.2f",fra2));
        aus_t.setText(String.format("%.2f",aus2));
        can_t.setText(String.format("%.2f",can2));
        hon_t.setText(String.format("%.2f",hon2));

        Log.i("config","oncreate:usa2="+usa2);
    }




    public  void openOne_Save(View btn){

        String origin=(String)usa_t.getText().toString();
        String origin2=(String)eur_t.getText().toString();
        String origin3=(String)eng_t.getText().toString();
        String origin4=(String)jan_t.getText().toString();
        String origin5=(String)fra_t.getText().toString();
        String origin6=(String)aus_t.getText().toString();
        String origin7=(String)can_t.getText().toString();
        String origin8=(String)hon_t.getText().toString();
        if(origin.isEmpty()==true||origin2.isEmpty()==true||origin3.isEmpty()==true||origin4.isEmpty()==true||origin5.isEmpty()==true||origin6.isEmpty()==true||origin7.isEmpty()==true||origin8.isEmpty()==true){
            Toast.makeText(this,"汇率不能为空",Toast.LENGTH_SHORT).show();
            return;
        }
        //回传数据
        float usa3=Float.parseFloat(origin);
        float eur3=Float.parseFloat(origin2);
        float eng3=Float.parseFloat(origin3);
        float fra3=Float.parseFloat(origin5);
        float jan3=Float.parseFloat(origin4);
        float aus3=Float.parseFloat(origin6);
        float can3=Float.parseFloat(origin7);
        float hon3=Float.parseFloat(origin8);

        Intent intent=getIntent();
        intent.putExtra("new_usa",usa3);
        intent.putExtra("new_eur",eur3);
        intent.putExtra("new_eng",eng3);
        intent.putExtra("new_jan",jan3);
        intent.putExtra("new_fra",fra3);
        intent.putExtra("new_aus",aus3);
        intent.putExtra("new_can",can3);
        intent.putExtra("new_hon",hon3);
        Log.i("congig","回传给exc的usa"+usa3);
        setResult(2,intent);
        //结束
        finish();
    }
}

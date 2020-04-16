package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ExchangeActivity extends AppCompatActivity implements Runnable{
    TextView rmb;
    TextView exchanged;
    private float usa=0.1413f;
    private float eur=0.1277f;
    private float eng=0.1153f;
    private float jan=15.3098f;
    private float fra=0.1356f;
    private float aus=0.2315f;
    private float can=0.1978f;
    private float hon=1.0954f;

    Handler my_handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exchange);

        rmb=(TextView) findViewById(R.id.editText3);
        exchanged=(TextView) findViewById(R.id.textView3);

        SharedPreferences sp= PreferenceManager.getDefaultSharedPreferences(this);
        usa=sp.getFloat("usa_rate",0.0f);
        eur=sp.getFloat("eur_rate",0.0f);
        eng=sp.getFloat("eng_rate",0.0f);
        jan=sp.getFloat("jan_rate",0.0f);
        fra=sp.getFloat("fra_rate",0.0f);
        aus=sp.getFloat("aus_rate",0.0f);
        can=sp.getFloat("can_rate",0.0f);
        hon=sp.getFloat("hon_rate",0.0f);

        Thread my_Thread=new Thread(this);
        my_Thread.start();
        my_handler=new Handler(){
            @Override
            public  void handleMessage(Message msg){
                super.handleMessage(msg);
                if(msg.what==5){
                    String str=(String)msg.obj;
                    Log.i("thread",str);
                }
            }

        };

    }


    public void onClick2(View m_view){
        String origin=(String)rmb.getText().toString();
        if(origin.isEmpty()==true){
            Toast.makeText(this,"金额不能为空",Toast.LENGTH_SHORT).show();
            return;
        }
        float result=Float.parseFloat(origin);
        if(m_view.getId()==R.id.button3){ result*=usa; }
        if(m_view.getId()==R.id.button4){result*=eur;}
        if(m_view.getId()==R.id.button5){result*=eng;}
        if(m_view.getId()==R.id.button6){result*=jan;}
        if(m_view.getId()==R.id.button7){result*=fra;}
        if(m_view.getId()==R.id.button8){result*=aus;}
        if(m_view.getId()==R.id.button9){result*=can;}
        if(m_view.getId()==R.id.button10){result*=hon;}
        exchanged.setText(String.format("%.2f",result));




    }
      public void  config(View v){
          openConfig();
      }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.rate,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.Menu_set){
            openConfig();
        }
        return super.onOptionsItemSelected(item);
    }

    private void openConfig() {
        Intent config = new Intent(this, ConfigActivity.class);
        config.putExtra("usa_rate", usa);
        config.putExtra("eur_rate", eur);
        config.putExtra("eng_rate", eng);
        config.putExtra("jan_rate", jan);
        config.putExtra("fra_rate", fra);
        config.putExtra("aus_rate", aus);
        config.putExtra("can_rate", can);
        config.putExtra("hon_rate", hon);
//          startActivity(config);
        startActivityForResult(config, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1&&resultCode==2){
            usa=data.getFloatExtra("new_usa",0.0f);
            eur=data.getFloatExtra("new_eur",0.0f);
            eng=data.getFloatExtra("new_eng",0.0f);
            jan=data.getFloatExtra("new_jan",0.0f);
            fra=data.getFloatExtra("new_fra",0.0f);
            aus=data.getFloatExtra("new_aus",0.0f);
            can=data.getFloatExtra("new_can",0.0f);
            hon=data.getFloatExtra("new_hon",0.0f);
            Log.i("exc2","收到config数据："+usa);

            SharedPreferences sp=PreferenceManager.getDefaultSharedPreferences(this);
            SharedPreferences.Editor myEdt= sp.edit();

            myEdt.putFloat("usa_rate", usa);
            myEdt.putFloat("eur_rate", eur);
            myEdt.putFloat("eng_rate", eng);
            myEdt.putFloat("jan_rate", jan);
            myEdt.putFloat("fra_rate", fra);
            myEdt.putFloat("can_rate", can);
            myEdt.putFloat("hon_rate", hon);
            myEdt.commit();

            Log.i("rate","汇率保存至本地文件");

        }
        super.onActivityResult(requestCode,resultCode,data);
    }

    public void open_one(View v){
          Intent count = new Intent(this,CountScoreActivity.class);
          startActivity(count);
     }

    @Override
    public void run() {
     Log.i("thread","线程启动器");

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //获取Msg对象并返回主线程

        Message msg=my_handler.obtainMessage(5);
//       msg.what=5;
        msg.obj="hello from run";
        my_handler.sendMessage(msg);

        //获取网页数据
        URL myUrl= null;
        try {
            myUrl = new URL("http://www.usd-cny.com/icbc.htm");//http://www.usd-cny.com/icbc.htm
            HttpURLConnection mhttp= (HttpURLConnection) myUrl.openConnection();
            InputStream in=mhttp.getInputStream();
            String html=InputStream2String(in);
            Log.i("TAG",html);
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String InputStream2String(InputStream inStream) throws IOException {
        final int bufferSize = 1024;
        final char[] buffer = new char[bufferSize];
        final StringBuilder out = new StringBuilder();
        Reader in = new InputStreamReader(inStream, "gb2312");
        for (; ; ) {
            int rsz = in.read(buffer, 0, buffer.length);
            if (rsz < 0)
                break;
            out.append(buffer, 0, rsz);
        }
        return out.toString();
    }
}

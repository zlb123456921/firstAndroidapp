package com.example.myapplication;

import android.app.ListActivity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RateListActivity extends ListActivity implements Runnable{
    String [] date={"wait a second..."};
    Handler my_handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_rate_list);

        ListAdapter adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,date);
        setListAdapter(adapter);

        Thread t=new Thread(this);
        t.start();

        my_handler=new Handler(){
            @Override
            public void handleMessage(Message msg){
                if(msg.what==7){
                    List <String>recv=(List<String>) msg.obj;
                    ListAdapter adapter=new ArrayAdapter<String>(RateListActivity.this,android.R.layout.simple_list_item_1,recv);
                    setListAdapter(adapter);

                }
                super.handleMessage(msg);
            }
        };





    }

    @Override
    public void run() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Document doc = null;
        List <String> rate_list =new ArrayList<String>();
        try {
            doc = Jsoup.connect("http://www.usd-cny.com/bankofchina.htm").get();
            Log.i("jsoup",doc.title());

            //获取table元素
            Elements tables=doc.getElementsByTag("table");
            Element table0=tables.get(0);
            //获取td元素
            Elements ths=table0.getElementsByTag("td");
            for (int i=0;i<ths.size();i+=6){
                Element tdbz=ths.get(i);
                Element tdrate=ths.get(i+5);
                String bz=tdbz.text();
                Float rate=100f/Float.parseFloat(tdrate.text());
              rate_list.add(bz+"==>"+rate);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        Message msg=my_handler.obtainMessage(7);
        msg.obj=rate_list;
        my_handler.sendMessage(msg);

    }
}

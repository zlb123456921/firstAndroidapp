
package com.example.myapplication;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.SimpleAdapter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Ratelist2Activity extends ListActivity implements Runnable{
    private String TAG="myList2";

    Handler myHandler;
    private ArrayList<HashMap<String,String>> listItem;
    private SimpleAdapter listItemAdapter;
    private int msgWhat=7;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_ratelist2);
        initListView();
        this.setListAdapter(listItemAdapter);

        Thread t = new Thread(this);
        t.start();

        myHandler=new Handler(){
            @Override
            public void handleMessage(Message msg){
                if(msg.what==8){
                    List<HashMap<String,String>> recv= (List<HashMap<String, String>>) msg.obj;
                    listItemAdapter=new SimpleAdapter(Ratelist2Activity.this,recv,
                            R.layout.activity_ratelist2,
                            new String[]{"ItemTitle","ItemDetail"},
                            new int[]{R.id.itemTitle,R.id.itemDetail});
                    setListAdapter(listItemAdapter);
                }
                super.handleMessage(msg);
            }
        };

        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                HashMap<String,String> map=(HashMap<String, String>) getListView().getItemAtPosition(position);
                String title=map.get("ItemTitle");
                String detail=map.get("ItemDetail");

                Intent singleRate=new Intent(Ratelist2Activity.this,SingleExchangeActivity.class);
                singleRate.putExtra("title",title);
                singleRate.putExtra("rate",Float.parseFloat(detail));
                startActivity(singleRate);

            }
        }

        );

    }
    private void initListView(){
        listItem=new ArrayList<HashMap<String, String>>();
        for (int i = 0; i <10 ; i++) {
            HashMap<String,String> map= new HashMap<String, String>();
            map.put("ItemTitle","Rate:"+i);
            map.put("ItemDetail","detail:"+i);
            listItem.add(map);
        }
        listItemAdapter=new SimpleAdapter(this,listItem,
                R.layout.activity_ratelist2,
                new String[]{"ItemTitle","ItemDetail"},
                new int[]{R.id.itemTitle,R.id.itemDetail});
    }

    @Override
    public void run() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Document doc = null;
        List<HashMap<String,String>> rate_list =new ArrayList<>();
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
                HashMap<String,String> map =new HashMap<>();
                String bz=tdbz.text();
                Float rate=100f/Float.parseFloat(tdrate.text());
                map.put("ItemTitle",bz);
                map.put("ItemDetail",rate+"");
                rate_list.add(map);
                //rate_list.add(bz+"==>"+rate);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        Message msg=myHandler.obtainMessage(8);
        msg.obj=rate_list;
        myHandler.sendMessage(msg);

    }
}

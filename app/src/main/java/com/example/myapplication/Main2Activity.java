package com.example.myapplication;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main2Activity extends AppCompatActivity implements Runnable  {
    private String updateDate="";
    TextView input;
    Handler my_handler;
    private ListView mListView;
    ListAdapter adapter;
    Map<String,String> selected;
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        input=(TextView)findViewById(R.id.editText12);
        mListView = (ListView) findViewById(R.id.result_box);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String listkey = (String) mListView.getItemAtPosition(position);
                String url=selected.get(listkey);
                //屌用浏览器
                Uri uri = Uri.parse(url);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
                Log.i("ListViewListener==>",url);
            }
        });


//判断数据是否需要更新
        SharedPreferences sp=getSharedPreferences("SchoolReport", Activity.MODE_PRIVATE);
        updateDate=sp.getString("update_date","");


        Date today = Calendar.getInstance().getTime();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        final String todayStr=sdf.format(today);


        Date todaydate = null;
        Date olddate=null;
        try {
            todaydate = sdf.parse(todayStr);
            olddate=sdf.parse(updateDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long ts = todaydate.getTime();
        long os= olddate.getTime();


        if((ts-os)>604800017){
            Thread my_Thread=new Thread(this);
            my_Thread.start();
            Log.i("date","启动更新数据"+ts+"/t"+os);
        }else{
            Log.i("date","无需启动更新数据");
        }

//判断日期是否相差一周
        my_handler=new Handler(){
            @Override
            public  void handleMessage(Message msg){
                super.handleMessage(msg);
                if(msg.what==5){

                    HashMap<String,String> recv=(HashMap<String, String>)msg.obj;


                    SharedPreferences sp=getSharedPreferences("SchoolReport", Activity.MODE_PRIVATE);
//                    SharedPreferences sp=PreferenceManager.getDefaultSharedPreferences(this);
                    SharedPreferences.Editor myEdt= sp.edit();
                    myEdt.putString("update_date",todayStr);

                    for (String key : recv.keySet()) {
                        myEdt.putString(key,recv.get(key));
//                        Log.i("Share==>",key+"===存储成功"+recv.get(key));
                    }
                    myEdt.commit();

                }
            }

        };
    }

    public void onClick4(View m_view){
        String searchInput=(String)input.getText().toString();
        if(searchInput.isEmpty()==true){
            Toast.makeText(this,"内容不能为空",Toast.LENGTH_SHORT).show();
            return;
        }
        SharedPreferences sp=getSharedPreferences("SchoolReport", Activity.MODE_PRIVATE);
        Map<String,String> result= (Map<String, String>) sp.getAll();
        selected=new HashMap<>();
        List<String> listKey=new ArrayList<>();
        result.remove("update_date");
        for (String key : result.keySet()) {
            if(key.indexOf(searchInput)>=0){
                selected.put(key,result.get(key));
                listKey.add(key);
                Log.i("result==>",key+"===添加结果数据成功"+result.get(key));
            }
        }

        if (selected.isEmpty()) {
            Toast.makeText(this,"未找到相关通知，请尝试搜索较少的关键字",Toast.LENGTH_SHORT).show();
            return;
        }else{
            ListAdapter adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,listKey);
            mListView.setAdapter(adapter);
        }
    }


    @Override
    public void run() {
        Bundle mbd=new Bundle();
        HashMap<String,String> map =new HashMap<>();
        Document doc = null;
        String url="https://www.swufe.edu.cn";
        try {
            doc = Jsoup.connect("https://www.swufe.edu.cn/index/tzgg.htm").timeout(50000000).maxBodySize(0).get();
            //通过延迟50000000毫秒,设置响应body不限制
           // Log.i("jsoup", String.valueOf(doc));
            //获取table元素
//            Elements tables=doc.getElementsByClass("whitenewslist clearfix");
            Elements ul=doc.select("ul.whitenewslist.clearfix");
            Elements li=(ul.get(0)).select("li");

            for (Element i:li){
                Element a=i.select("a").first();
                String linkHref = a.attr("href");
                String title =a.attr("title");
                linkHref=url+linkHref.substring(2);

                map.put(title,linkHref);
               // Log.i("jsoup", String.valueOf(a));
//                Log.i("jsoup", linkHref);
//                Log.i("jsoup", title);
            }


        } catch (IOException e) {
            e.printStackTrace();
        }

        Message msg=my_handler.obtainMessage(5);
        msg.obj=map;
        my_handler.sendMessage(msg);

    }
}

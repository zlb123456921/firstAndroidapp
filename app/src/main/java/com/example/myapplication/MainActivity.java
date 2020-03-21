package com.example.myapplication;

//import  android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import java.lang.*;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager2.widget.ViewPager2;

import android.preference.EditTextPreference;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity implements  View.OnClickListener{

    EditText a;
    TextView t;
    Button b;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        a=(EditText) findViewById(R.id.editText);
        b=(Button) findViewById(R.id.button);
        t=(TextView)findViewById(R.id.textView2);
        b.setOnClickListener(this);
//        b.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public  void onClick(View v) {
//                Log.i("main", "onClick called");
//                String a1 = a.getText().toString();
//                Double a2 = Double.parseDouble(a1);
//                a2 = a2 * 1.8 + 32;
//                t.setText("华氏度："+a2);
//            }
//
//        });

    }


    @Override
    public  void onClick(View v) {
        Log.i("main", "onClick called");
        String a1 = a.getText().toString();
        Double a2 = Double.parseDouble(a1);
        a2 = a2 * 1.8 + 32;
        t.setText("华氏度："+a2);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}

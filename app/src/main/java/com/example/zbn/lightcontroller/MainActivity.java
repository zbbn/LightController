package com.example.zbn.lightcontroller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    public TextView tvName;
    public Button btnLamp1, btnLamp2, btnLamp3, btnAll;
    //public Controller controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvName = findViewById(R.id.tvName);
        btnLamp1 = findViewById(R.id.btnLamp1);
        btnLamp2 = findViewById(R.id.btnLamp2);
        btnLamp3 = findViewById(R.id.btnLamp3);
        btnAll = findViewById(R.id.btnAll);

        btnLamp1.setOnClickListener(new Lamp1Listener());
        btnLamp2.setOnClickListener(new Lamp2Listener());
        btnLamp3.setOnClickListener(new Lamp3Listener());
        btnAll.setOnClickListener(new LampAllListener());
    }

    public void lampAllClick(View view) {
        Intent intent = new Intent(this, LampAll.class);
        startActivity(intent);
    }

    public void lamp1Click(View view) {
        Intent intent = new Intent(this, Lamp1Activity.class);
        startActivity(intent);
    }

    public void lamp2Click(View view) {
        Intent intent = new Intent(this, Lamp2Activity.class);
        startActivity(intent);
    }

    public void lamp3Click(View view) {
        Intent intent = new Intent(this, Lamp3Activity.class);
        startActivity(intent);
    }

    private class LampAllListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            lampAllClick(view);
        }
    }

    private class Lamp1Listener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            lamp1Click(view);
        }
    }

    private class Lamp2Listener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            lamp2Click(view);
        }
    }

    private class Lamp3Listener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            lamp3Click(view);
        }
    }
}

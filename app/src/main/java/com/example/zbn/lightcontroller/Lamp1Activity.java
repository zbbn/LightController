package com.example.zbn.lightcontroller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class Lamp1Activity extends AppCompatActivity {
    public Controller controller;
    public Button btnOn, btnOff,
            btnDecBright, btnIncBright,
            btnDecHue, btnIncHue,
            btnBack;
    public TextView tvBrightness, tvHue,
            tvInfoBrightness, tvInfoHue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lamp1);

        tvInfoBrightness = findViewById(R.id.tvInfoBrightness);
        tvInfoHue = findViewById(R.id.tvInfoHue);

        btnOn = findViewById(R.id.btnOn);
        btnOff = findViewById(R.id.btnOff);
        btnOn.setOnClickListener(new BtnOnListener());
        btnOff.setOnClickListener(new BtnOffListener());

        tvBrightness = findViewById(R.id.tvBrightness);
        btnDecBright = findViewById(R.id.btnDecBright);
        btnIncBright = findViewById(R.id.btnIncBright);
        btnDecBright.setOnClickListener(new BtnDecreaseBrightnessListener());
        btnIncBright.setOnClickListener(new BtnIncreaseBrightnessListener());

        tvHue = findViewById(R.id.tvHue);
        btnDecHue = findViewById(R.id.btnDecHue);
        btnIncHue = findViewById(R.id.btnIncHue);
        btnDecHue.setOnClickListener(new BtnDecreaseHueListener());
        btnIncHue.setOnClickListener(new BtnIncreaseHueListener());

        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new BtnBackListener());
        controller = new Controller(this.getApplicationContext());
    }

    public void updateValues() {
        if(controller.lampNbr == 1)
            tvInfoBrightness.setText("Brightness: " + controller.lampBrightness);
            tvInfoHue.setText("Hue: " + controller.lampHue);
    }

    private class BtnDecreaseHueListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
           controller.decreaseLampHue(1);
            updateValues();
        }
    }

    private class BtnIncreaseHueListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            controller.increaseLampHue(1);
            updateValues();
        }
    }

    private class BtnDecreaseBrightnessListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            controller.decreaseLampBrightness(1);
            updateValues();
        }
    }

    private class BtnIncreaseBrightnessListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            controller.increaseLampBrightness(1);
            updateValues();
        }
    }

    public void backToMainActivity(View view) {
        //Intent intent = new Intent(this, MainActivity.class);
        //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        //startActivity(intent);
        this.finish();
    }

    private class BtnBackListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            controller.killToken();
            backToMainActivity(view);
        }
    }

    private class BtnOnListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            controller.lampOn(1);
        }
    }

    private class BtnOffListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            controller.lampOff(1);
        }
    }
}

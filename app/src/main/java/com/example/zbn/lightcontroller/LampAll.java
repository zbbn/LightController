package com.example.zbn.lightcontroller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class LampAll extends AppCompatActivity {
    public Controller controller;
    public Button btnOn, btnOff,
            btnDecBright, btnIncBright,
            btnDecHue, btnIncHue,
            btnBack;

    public TextView tvBrightness, tvHue,
            tvInfoBrightness, tvInfoBrightness2,
            tvInfoBrightness3, tvInfoHue, tvInfoHue2,
            tvInfoHue3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lamp_all);

        tvInfoBrightness = findViewById(R.id.tvInfoBrightness);
        tvInfoBrightness2 = findViewById(R.id.tvInfoBrightness2);
        tvInfoBrightness3 = findViewById(R.id.tvInfoBrightness3);

        tvInfoHue = findViewById(R.id.tvInfoHue);
        tvInfoHue2 = findViewById(R.id.tvInfoHue2);
        tvInfoHue3 = findViewById(R.id.tvInfoHue3);

        btnOn = findViewById(R.id.btnOn);
        btnOff = findViewById(R.id.btnOff);
        btnOn.setOnClickListener(new BtnOnListener());
        btnOff.setOnClickListener(new BtnOffListener());

        tvBrightness = findViewById(R.id.tvInfoBrightness);
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
        controller = new Controller(this.getApplicationContext(),"1");
    }

    public void updateAllValues() {
        tvInfoBrightness.setText("Brightness: " + controller.lampBrightness);
        tvInfoHue.setText("Hue: " + controller.lampHue);
        tvInfoBrightness2.setText("Brightness: " + controller.lampBrightness);
        tvInfoHue2.setText("Hue: " + controller.lampHue);
        tvInfoBrightness3.setText("Brightness: " + controller.lampBrightness);
        tvInfoHue3.setText("Hue: " + controller.lampHue);
    }

    private class BtnDecreaseHueListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            controller.decreaseAllLampHue();
            updateAllValues();
        }
    }

    private class BtnIncreaseHueListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            controller.increaseAllLampHue();
            updateAllValues();
        }
    }

    private class BtnDecreaseBrightnessListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            controller.decreaseAllLampBrightness();
            updateAllValues();
        }
    }

    private class BtnIncreaseBrightnessListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            controller.increaseAllLampBrightness();
            updateAllValues();
        }
    }

    public void backToMainActivity(View view) {
        //Intent intent = new Intent(this, MainActivity.class);
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
            controller.allLampOn();
        }
    }

    private class BtnOffListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            controller.allLampOff();
        }
    }
}

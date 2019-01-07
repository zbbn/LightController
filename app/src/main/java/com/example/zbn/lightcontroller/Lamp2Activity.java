package com.example.zbn.lightcontroller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Lamp2Activity extends AppCompatActivity {
    public Button btnOn, btnOff,
            btnDecBright, btnIncBright,
            btnDecHue, btnIncHue,
            btnDecSat, btnIncSat,
            btnBack;
    public TextView tvBrightness, tvHue, tvSaturation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lamp_all);

        btnOn = findViewById(R.id.btnOn);
        btnOff = findViewById(R.id.btnOff);

        tvBrightness = findViewById(R.id.tvBrightness);
        btnDecBright = findViewById(R.id.btnDecBright);
        btnIncBright = findViewById(R.id.btnIncBright);

        tvHue = findViewById(R.id.tvHue);
        btnDecHue = findViewById(R.id.btnDecHue);
        btnIncHue = findViewById(R.id.btnIncHue);

        tvSaturation = findViewById(R.id.tvSaturation);
        btnDecSat = findViewById(R.id.btnDecSat);
        btnIncSat = findViewById(R.id.btnIncSat);

        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new BtnBackListener());
    }

    public void backToMainActivity(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private class BtnBackListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            backToMainActivity(view);
        }
    }
}

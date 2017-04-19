package com.example.diamo.iturn;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextClock;
import android.widget.TextView;

public class RulesActivity extends AppCompatActivity {
    private Button backToGame;
    private Button showCalender;
    private Button backToRules;
    private DatePicker calender;
    private TextClock clock;
    private TextView rules;
    private TextView caution;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rules);

        onCreateNew();

        backToGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainPage = new Intent(getApplicationContext(), GameActivity.class);
                startActivity(mainPage);
            }
        });
        showCalender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calender.setVisibility(View.VISIBLE);
                backToRules.setVisibility(View.VISIBLE);

                backToGame.setVisibility(View.INVISIBLE);
                showCalender.setVisibility(View.INVISIBLE);
                clock.setVisibility(View.INVISIBLE);
                rules.setVisibility(View.INVISIBLE);
                caution.setVisibility(View.INVISIBLE);

            }
        });
        backToRules.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackToRules();
            }
        });

    }

    private void onCreateNew() {
        backToGame = (Button) findViewById(R.id.buttonBack);
        showCalender = (Button) findViewById(R.id.buttonShowCalender);
        calender = (DatePicker) findViewById(R.id.calender);
        clock = (TextClock) findViewById(R.id.textClock);
        rules = (TextView) findViewById(R.id.textView);
        caution = (TextView) findViewById(R.id.caution);
        backToRules = (Button) findViewById(R.id.backToRules);

        calender.setVisibility(View.INVISIBLE);
        backToRules.setVisibility(View.INVISIBLE);
    }
    private void onBackToRules() {
        calender.setVisibility(View.INVISIBLE);
        backToRules.setVisibility(View.INVISIBLE);

        backToGame.setVisibility(View.VISIBLE);
        showCalender.setVisibility(View.VISIBLE);
        clock.setVisibility(View.VISIBLE);
        rules.setVisibility(View.VISIBLE);
        caution.setVisibility(View.VISIBLE);

    }
}

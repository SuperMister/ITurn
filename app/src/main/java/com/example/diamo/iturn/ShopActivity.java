package com.example.diamo.iturn;


import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;


public class ShopActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Button backBtn;
    Button buyBtn;
    TextView scoreInShop;
    ImageView tick1, tick2, tick3, tick4;
    ArrayList<ImageView> boughtBalls = new ArrayList<>();
    TextView successText;
    RadioButton blue, green, rose;
    int amountOfBoughtBalls = 1;
    String chosenBall;
    ImageView defaultBall, blueBall, greenBall, roseBall;
    int highScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        chosenBall = sharedPreferences.getString("ballMode", "default");
        amountOfBoughtBalls = sharedPreferences.getInt("boughtBalls", 1);
        onCreateNew();

        highScore = sharedPreferences.getInt("highScoreSaved", 0);

        String score = getString(R.string.score) + String.valueOf(highScore);
        scoreInShop.setText(score);


        boughtBalls.add(defaultBall);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainPage = new Intent(getApplicationContext(), GameActivity.class);
                startActivity(mainPage);
            }
        });
        defaultBallOnClick();
        blueBallOnClick();
        greenBallOnClick();
        roseBallOnClick();
    }
    public void defaultBallOnClick() {
        defaultBall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tick1.setVisibility(View.VISIBLE);
                tick2.setVisibility(View.INVISIBLE);
                tick3.setVisibility(View.INVISIBLE);
                tick4.setVisibility(View.INVISIBLE);
                editor = sharedPreferences.edit();
                editor.putString("ballMode", "default");
                editor.apply();
            }
        });
    }
    public void blueBallOnClick() {
        blueBall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (boughtBalls.contains(blueBall)) {
                    tick1.setVisibility(View.INVISIBLE);
                    tick2.setVisibility(View.VISIBLE);
                    tick3.setVisibility(View.INVISIBLE);
                    tick4.setVisibility(View.INVISIBLE);
                    editor = sharedPreferences.edit();
                    editor.putString("ballMode", "blue");
                    editor.apply();
                }
            }
        });
    }
    public void greenBallOnClick() {
        greenBall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (boughtBalls.contains(greenBall)) {
                    tick1.setVisibility(View.INVISIBLE);
                    tick2.setVisibility(View.INVISIBLE);
                    tick3.setVisibility(View.VISIBLE);
                    tick4.setVisibility(View.INVISIBLE);
                    editor = sharedPreferences.edit();
                    editor.putString("ballMode", "green");
                    editor.apply();
                }
            }
        });
    }
    public void roseBallOnClick() {
        roseBall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (boughtBalls.contains(roseBall)) {
                    tick1.setVisibility(View.INVISIBLE);
                    tick2.setVisibility(View.INVISIBLE);
                    tick3.setVisibility(View.INVISIBLE);
                    tick4.setVisibility(View.VISIBLE);
                    editor = sharedPreferences.edit();
                    editor.putString("ballMode", "rose");
                    editor.apply();
                }
            }
        });
    }



    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();
        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.blueRadioButton:
                if (checked) {
                    green.setChecked(false);
                    rose.setChecked(false);
                    if (highScore >= 50) {
                        buyBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                boughtBalls.add(blueBall);
                                successText.setText(R.string.NiceBuy);
                                tick2.setVisibility(View.VISIBLE);
                                tick1.setVisibility(View.INVISIBLE);
                                tick3.setVisibility(View.INVISIBLE);
                                tick4.setVisibility(View.INVISIBLE);
                                successText.setText(R.string.NiceBuy);
                                amountOfBoughtBalls += 1;
                                editor = sharedPreferences.edit();
                                editor.putString("ballMode", "blue");
                                editor.putInt("boughtBalls", 2);
                                editor.apply();
                            }
                        });
                    }
                    break;
                }
            case R.id.greenRadioButton:
                if (checked) {
                    blue.setChecked(false);
                    rose.setChecked(false);
                    if (highScore >= 100) {
                        buyBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                boughtBalls.add(greenBall);
                                successText.setText(R.string.NiceBuy);
                                tick3.setVisibility(View.VISIBLE);
                                tick1.setVisibility(View.INVISIBLE);
                                tick2.setVisibility(View.INVISIBLE);
                                tick4.setVisibility(View.INVISIBLE);
                                amountOfBoughtBalls += 1;
                                editor = sharedPreferences.edit();
                                editor.putString("ballMode", "green");
                                editor.putInt("boughtBalls", 3);
                                editor.apply();
                            }
                        });
                    }
                    break;
                }
            case R.id.roseRadioButton:
                if (checked) {
                    blue.setChecked(false);
                    green.setChecked(false);
                    if (highScore >= 200) {
                        buyBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                boughtBalls.add(roseBall);
                                successText.setText(R.string.NiceBuy);
                                tick4.setVisibility(View.VISIBLE);
                                tick1.setVisibility(View.INVISIBLE);
                                tick2.setVisibility(View.INVISIBLE);
                                tick3.setVisibility(View.INVISIBLE);
                                amountOfBoughtBalls += 1;
                                editor = sharedPreferences.edit();
                                editor.putString("ballMode", "rose");
                                editor.putInt("boughtBalls", 4);
                                editor.apply();
                            }
                        });
                    }
                    break;
                }
        }
    }




    private void onCreateNew() {
        scoreInShop = (TextView) findViewById(R.id.scoreInShop);
        backBtn = (Button) findViewById(R.id.buttonBack);
        buyBtn = (Button) findViewById(R.id.buyBtn);
        successText = (TextView) findViewById(R.id.successText);

        blue = (RadioButton) findViewById(R.id.blueRadioButton);
        green = (RadioButton) findViewById(R.id.greenRadioButton);
        rose = (RadioButton) findViewById(R.id.roseRadioButton);

        defaultBall = (ImageView) findViewById(R.id.defaultBall);
        blueBall = (ImageView) findViewById(R.id.blueBall);
        greenBall = (ImageView) findViewById(R.id.greenBall);
        roseBall = (ImageView) findViewById(R.id.roseBall);

        tick1 = (ImageView) findViewById(R.id.tick1);
        tick2 = (ImageView) findViewById(R.id.tick2);
        tick3 = (ImageView) findViewById(R.id.tick3);
        tick4 = (ImageView) findViewById(R.id.tick4);

        if (amountOfBoughtBalls == 2) {
            boughtBalls.add(blueBall);
        } else if (amountOfBoughtBalls == 3) {
            boughtBalls.add(blueBall);
            boughtBalls.add(greenBall);
        } else if (amountOfBoughtBalls == 4) {
            boughtBalls.add(blueBall);
            boughtBalls.add(greenBall);
            boughtBalls.add(roseBall);
        }

        if (chosenBall.equals("default")) {
            tick1.setVisibility(View.VISIBLE);
            tick2.setVisibility(View.INVISIBLE);
            tick3.setVisibility(View.INVISIBLE);
            tick4.setVisibility(View.INVISIBLE);
        } else if (chosenBall.equals("blue")) {
            tick1.setVisibility(View.INVISIBLE);
            tick2.setVisibility(View.VISIBLE);
            tick3.setVisibility(View.INVISIBLE);
            tick4.setVisibility(View.INVISIBLE);
        } else if (chosenBall.equals("green")) {
            tick1.setVisibility(View.INVISIBLE);
            tick2.setVisibility(View.INVISIBLE);
            tick3.setVisibility(View.VISIBLE);
            tick4.setVisibility(View.INVISIBLE);
        } else if (chosenBall.equals("rose")) {
            tick1.setVisibility(View.INVISIBLE);
            tick2.setVisibility(View.INVISIBLE);
            tick3.setVisibility(View.INVISIBLE);
            tick4.setVisibility(View.VISIBLE);
        }

    }

}

package com.example.diamo.iturn;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


/**
         ______/\_______
       (________________)
              (oo)
      /--------\/
     / |     ||
    *  ||----||
       ~~    ~~
    MEXICAN COW for easy debugging.
*/




public class GameActivity extends AppCompatActivity {
    String ballMode = "default";

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    List<ImageView> pillarsList = new ArrayList<>();
    List<ImageView> pillarsTopList = new ArrayList<>();

    Handler handler = new Handler(); // handler for delay

    Boolean gameOverAction;
    Boolean ballRight;

    int scoreValue;
    int highScoreValue;

    Button rulesBtn;

    Button settingsBtn;

    RelativeLayout layout;
    Button playBtn, retryBtn, shopBtn;
    TextView score, highscoreOnBoard, scoreOnBoard, highScoreStartNum;
    ImageView ball, gameOver, scoreBoard, logo, highScoreStartImg, talisman, dino;
    ImageView pillar1, pillar2, pillar3, pillar4, pillar5, pillar6, pillar7, pillar8, pillar9, pillar10;
    ImageView pillar11, pillar12, pillar13, pillar14, pillar15, pillar16, pillar17;
    ImageView pillarTop1, pillarTop2, pillarTop3, pillarTop4, pillarTop5, pillarTop6;
    Runnable action;

    Rect ballRect = new Rect();
    Rect pillar1Rect = new Rect();
    Rect pillar2Rect = new Rect();
    Rect pillar3Rect = new Rect();
    Rect pillar4Rect = new Rect();
    Rect pillar5Rect = new Rect();
    Rect pillar6Rect = new Rect();
    Rect pillar7Rect = new Rect();
    Rect pillar8Rect = new Rect();
    Rect pillar9Rect = new Rect();
    Rect pillar10Rect = new Rect();
    Rect pillar11Rect = new Rect();
    Rect pillar12Rect = new Rect();
    Rect pillar13Rect = new Rect();
    Rect pillar14Rect = new Rect();
    Rect pillar15Rect = new Rect();
    Rect pillar16Rect = new Rect();
    Rect pillar17Rect = new Rect();
    Rect pillartopRect1 = new Rect();
    Rect pillartopRect2 = new Rect();
    Rect pillartopRect3 = new Rect();
    Rect pillartopRect4 = new Rect();
    Rect pillartopRect5 = new Rect();
    Rect pillartopRect6 = new Rect();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final MediaPlayer mediaPlayer = MediaPlayer.create(GameActivity.this, R.raw.reloadedinstaller);
        mediaPlayer.start();

        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        ballMode = preferences.getString("ballMode", "default");
        createPillarsList();
        createPillarsTopList();
        onCreateNew();

        shopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent shopPage = new Intent(getApplicationContext(), com.example.diamo.iturn.ShopActivity.class);
                startActivity(shopPage);
            }
        });

        rulesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent rulesPage = new Intent(getApplicationContext(), com.example.diamo.iturn.RulesActivity.class);
                startActivity(rulesPage);
            }
        });

        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playBtnClicked();
                movement();
            }
        });
        settingsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mediaPlayer.isPlaying()) {
                    mediaPlayer.start();
                } else {
                    mediaPlayer.pause();
                }
            }
        });
    }

    private void playBtnClicked() {
        logo.setVisibility(View.INVISIBLE);
        playBtn.setVisibility(View.INVISIBLE);

        // functionality later
        dino.setVisibility(View.INVISIBLE);
        rulesBtn.setVisibility(View.INVISIBLE);
        settingsBtn.setVisibility(View.INVISIBLE);
        shopBtn.setVisibility(View.INVISIBLE);
        highScoreStartImg.setVisibility(View.INVISIBLE);
        highScoreStartNum.setVisibility(View.INVISIBLE);

        pillar1.setX(260);
        pillar1.setY(780);
        ball.setX(320);
        ball.setY(820);
        talisman.setX(320);
        talisman.setY(50);
        pillarsSetUp();

        for (ImageView pillar : pillarsList) { // Set all pillars visible.
            pillar.setVisibility(View.VISIBLE);
        }
        for (ImageView pillarTop : pillarsTopList) { // Set all pillar tops visible.
            pillarTop.setVisibility(View.VISIBLE);
        }
        score.setText(String.valueOf(scoreValue));
        talisman.setVisibility(View.VISIBLE);
        score.setVisibility(View.VISIBLE);
        ball.setVisibility(View.VISIBLE);
    }

    private void pillarsSetUp() {
        pillar2.setX(pillar1.getX() + 78);
        pillar2.setY(pillar1.getY() - 55);
        pillar3.setX(pillar2.getX() + 78);
        pillar3.setY(pillar2.getY() - 55);
        for (int i = 3; i < pillarsList.size(); i++) {
            pillarsList.get(i).setX(pillarPlacementX(pillarsList.get(i - 1).getX()));
            pillarsList.get(i).setY(pillarPlacementY(pillarsList.get(i - 1).getY()));
        }
        pillarTop1.setX(pillar2.getX() + 10.5f);
        pillarTop1.setY(pillar2.getY() + 10.5f);
        pillarTop2.setX(pillar1.getX() + 10.5f);
        pillarTop2.setY(pillar1.getY() + 10.5f);
    }

    private void movement() {
        final int delay = 45;
        action = new Runnable() {
            @Override
            public void run() {
                gameProcess();
                handler.postDelayed(this, delay);
            }
        };
        handler.postDelayed(action, delay);
    }


    private void gameProcess() {
        layout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    if (!gameOverAction) {
                        scoreValue++;
                        score.setText(String.valueOf(scoreValue));
                        ballRight = !ballRight;
                    }
                    return true;
                }
                return false;
            }
        });
        if (ballRight) {
            ball.setX(ball.getX() + 6.7f);
            talisman.setX(talisman.getX() - 3f);
        } else {
            ball.setX(ball.getX() - 6.7f);
            talisman.setX(talisman.getX() + 3f);
        }
        for (ImageView pillar : pillarsList) { // pillars' flow
            pillar.setY(pillar.getY() + 5);
        }
        ball.getHitRect(ballRect);
        getHitRectPillars();
        redrawPillarToTheTop();
        setPillarTops();
        if (!ballOnTheWall()) {
            gameOver();
        }
    }

    private void redrawPillarToTheTop() {
        for (int i = 0; i < pillarsList.size(); i++) {
            if (i == 0) {
                if (checkPillarOut(pillarsList.get(i).getY())){
                    putViewToBack(pillarsList.get(i));
                    pillarsList.get(i).setX(pillarPlacementX(pillarsList.get(pillarsList.size() - 1).getX()));
                    pillarsList.get(i).setY(pillarPlacementY(pillarsList.get(pillarsList.size() - 1).getY()));
                }
            } else {
                if (checkPillarOut(pillarsList.get(i).getY())) {
                    putViewToBack(pillarsList.get(i));
                    pillarsList.get(i).setX(pillarPlacementX(pillarsList.get(i - 1).getX()));
                    pillarsList.get(i).setY(pillarPlacementY(pillarsList.get(i - 1).getY()));
                }
            }
        }
    }

    private boolean ballOnTheWall() {
        return Rect.intersects(ballRect, pillartopRect1) || Rect.intersects(ballRect, pillartopRect2)
                || Rect.intersects(ballRect, pillartopRect3)
                || Rect.intersects(ballRect, pillartopRect4)
                || Rect.intersects(ballRect, pillartopRect5) || Rect.intersects(ballRect, pillartopRect6);
    }

    private void gameOver() {
        gameOverAction = true;
        handler.removeCallbacksAndMessages(null);

        for (ImageView pillar : pillarsList) {
            putViewToBack(pillar);
            pillar.setVisibility(View.INVISIBLE);
        }
        for (ImageView pillarTop : pillarsTopList) {
            pillarTop.setVisibility(View.INVISIBLE);
        }
        talisman.setVisibility(View.INVISIBLE);
        ball.setVisibility(View.INVISIBLE);
        score.setVisibility(View.INVISIBLE);

        dino.setVisibility(View.VISIBLE);
        rulesBtn.setVisibility(View.VISIBLE);
        settingsBtn.setVisibility(View.VISIBLE);
        shopBtn.setVisibility(View.VISIBLE);

        if (scoreValue > highScoreValue) {
            highScoreValue = scoreValue;
            editor = preferences.edit();
            editor.putInt("highScoreSaved", highScoreValue);
            editor.putString("jurec", "ahuennqj");
            editor.apply();
        }
        scoreOnBoard.setText(String.valueOf(scoreValue));
        String data = preferences.getString("jure", "ouch");
        highscoreOnBoard.setText(data);
        highscoreOnBoard.setText(String.valueOf(highScoreValue));

        gameOver.setVisibility(View.VISIBLE);
        retryBtn.setVisibility(View.VISIBLE);
        scoreBoard.setVisibility(View.VISIBLE);
        scoreOnBoard.setVisibility(View.VISIBLE);
        highscoreOnBoard.setVisibility(View.VISIBLE);
        shopBtn.setVisibility(View.VISIBLE);

        shopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent shopPage = new Intent(getApplicationContext(), com.example.diamo.iturn.ShopActivity.class);
                startActivity(shopPage);
            }
        });

        retryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onCreateNew();
                playBtnClicked();
                movement(); //can be removed, depends on you my God
            }
        });
    }

    private static void putViewToBack(View child) {
        ViewGroup parent = (ViewGroup) child.getParent();
        parent.removeView(child);
        parent.addView(child, 6);
    }

    private void putTopToBack(View child) {
        ViewGroup parent = (ViewGroup) child.getParent();
        parent.removeView(child);
        parent.addView(child, 0);
    }

    private boolean checkPillarOut(float Y) {
        return Y > 1000f; // (900f)
    }

    private float pillarPlacementX(float X) {
        float pillarNewX = 0f;

        int random = (int)(Math.random() * 2 + 1);
        if (random == 1) {
            if (X > 465) {
                pillarNewX = X - 79;
            } else {
                pillarNewX = X + 78;
            }
        } else {
            if (X < 40) {
                pillarNewX = X + 78;
            } else {
                pillarNewX = X - 79;
            }
        }
        return pillarNewX;
    }
    private float pillarPlacementY(float Y) {
        Y = Y - 57;
        return Y;
    }


    private void onCreateNew() {
        gameOverAction = false;
        ballRight = true;
        scoreValue = 0;

        highScoreValue = preferences.getInt("highScoreSaved", 0);

        rulesBtn = (Button)findViewById(R.id.rules);
        dino = (ImageView) findViewById(R.id.dino);

        settingsBtn = (Button)findViewById(R.id.settings);
        shopBtn = (Button)findViewById(R.id.shop);
        highScoreStartImg = (ImageView)findViewById(R.id.highScoreStartImg);
        highScoreStartNum = (TextView)findViewById(R.id.highScoreStart);
        highScoreStartNum.setText(String.valueOf(highScoreValue));


        layout = (RelativeLayout)findViewById(R.id.activity_main); // layout
        talisman = (ImageView) findViewById(R.id.talisman);
        logo = (ImageView)findViewById(R.id.logo); // logo on start layout
        playBtn = (Button)findViewById(R.id.playBtn); // play button
        retryBtn = (Button)findViewById(R.id.retryBtn); // retry button
        score = (TextView)findViewById(R.id.scoreInGame); // score in game process
        scoreOnBoard = (TextView)findViewById(R.id.scoreOnBoard); // this game score on game over layout
        highscoreOnBoard = (TextView)findViewById(R.id.highScoreOnBoard); // high score on game over layout
        if (ballMode.equals("default")) {
            ball = (ImageView)findViewById(R.id.ball); // ball in game
        } else if (ballMode.equals("blue")){
            ball = (ImageView) findViewById(R.id.blueBall);
        } else if (ballMode.equals("green")) {
            ball = (ImageView) findViewById(R.id.greenBall);
        } else if (ballMode.equals("rose")) {
            ball = (ImageView) findViewById(R.id.roseBall);
        }

        gameOver = (ImageView)findViewById(R.id.GameOver); // game over image
        scoreBoard = (ImageView)findViewById(R.id.scoreBoard); // score board on game over layout


        for (ImageView pillar : pillarsList) { // Set all pillars invisible.
            pillar.setVisibility(View.INVISIBLE);
        }
        for (ImageView pillarTop : pillarsTopList) { // Set all pillar tops invisible.
            pillarTop.setVisibility(View.INVISIBLE);
        }
        talisman.setVisibility(View.INVISIBLE);
        retryBtn.setVisibility(View.INVISIBLE);
        score.setVisibility(View.INVISIBLE);
        scoreOnBoard.setVisibility(View.INVISIBLE);
        highscoreOnBoard.setVisibility(View.INVISIBLE);
        ball.setVisibility(View.INVISIBLE);
        gameOver.setVisibility(View.INVISIBLE);
        scoreBoard.setVisibility(View.INVISIBLE);

    }

    /**
     * Create list of pillar images.
     */
    private void createPillarsList() {
        pillar1 = (ImageView)findViewById(R.id.Pillar1);// pillars for the game process
        pillarsList.add(pillar1);
        pillar2 = (ImageView)findViewById(R.id.Pillar2);
        pillarsList.add(pillar2);
        pillar3 = (ImageView)findViewById(R.id.Pillar3);
        pillarsList.add(pillar3);
        pillar4 = (ImageView)findViewById(R.id.Pillar4);
        pillarsList.add(pillar4);
        pillar5 = (ImageView)findViewById(R.id.Pillar5);
        pillarsList.add(pillar5);
        pillar6 = (ImageView)findViewById(R.id.Pillar6);
        pillarsList.add(pillar6);
        pillar7 = (ImageView)findViewById(R.id.Pillar7);
        pillarsList.add(pillar7);
        pillar8 = (ImageView)findViewById(R.id.Pillar8);
        pillarsList.add(pillar8);
        pillar9 = (ImageView)findViewById(R.id.Pillar9);
        pillarsList.add(pillar9);
        pillar10 = (ImageView)findViewById(R.id.Pillar10);
        pillarsList.add(pillar10);
        pillar11 = (ImageView)findViewById(R.id.Pillar11);
        pillarsList.add(pillar11);
        pillar12 = (ImageView)findViewById(R.id.Pillar12);
        pillarsList.add(pillar12);
        pillar13 = (ImageView)findViewById(R.id.Pillar13);
        pillarsList.add(pillar13);
        pillar14 = (ImageView)findViewById(R.id.Pillar14);
        pillarsList.add(pillar14);
        pillar15 = (ImageView)findViewById(R.id.Pillar15);
        pillarsList.add(pillar15);
        pillar16 = (ImageView)findViewById(R.id.Pillar16);
        pillarsList.add(pillar16);
        pillar17 = (ImageView)findViewById(R.id.Pillar17);
        pillarsList.add(pillar17);

    }
    private void getHitRectPillars() {
        pillar1.getHitRect(pillar1Rect);
        pillar2.getHitRect(pillar2Rect);
        pillar3.getHitRect(pillar3Rect);
        pillar4.getHitRect(pillar4Rect);
        pillar5.getHitRect(pillar5Rect);
        pillar6.getHitRect(pillar6Rect);
        pillar7.getHitRect(pillar7Rect);
        pillar8.getHitRect(pillar8Rect);
        pillar9.getHitRect(pillar9Rect);
        pillar10.getHitRect(pillar10Rect);
        pillar11.getHitRect(pillar11Rect);
        pillar12.getHitRect(pillar12Rect);
        pillar13.getHitRect(pillar13Rect);
        pillar14.getHitRect(pillar14Rect);
        pillar15.getHitRect(pillar15Rect);
        pillar16.getHitRect(pillar16Rect);
        pillar17.getHitRect(pillar17Rect);

        pillarTop1.getHitRect(pillartopRect1);
        pillarTop2.getHitRect(pillartopRect2);
        pillarTop3.getHitRect(pillartopRect3);
        pillarTop4.getHitRect(pillartopRect4);
        pillarTop5.getHitRect(pillartopRect5);
        pillarTop6.getHitRect(pillartopRect6);
    }

    private void createPillarsTopList() {
        pillarTop1 = (ImageView)findViewById(R.id.pillarTop1);
        pillarsTopList.add(pillarTop1);
        pillarTop2 = (ImageView)findViewById(R.id.pillarTop2);
        pillarsTopList.add(pillarTop2);
        pillarTop3 = (ImageView)findViewById(R.id.pillarTop3);
        pillarsTopList.add(pillarTop3);
        pillarTop4 = (ImageView)findViewById(R.id.pillarTop4);
        pillarsTopList.add(pillarTop4);
        pillarTop5 = (ImageView)findViewById(R.id.pillarTop5);
        pillarsTopList.add(pillarTop5);
        pillarTop6 = (ImageView)findViewById(R.id.pillarTop6);
        pillarsTopList.add(pillarTop6);
    }
    private void setPillarTops() {
        if (Rect.intersects(ballRect, pillar1Rect)) {
            pillarTop4.setVisibility(View.VISIBLE);
            pillarTop5.setVisibility(View.VISIBLE);
            pillarTop6.setVisibility(View.VISIBLE);
            pillarTop1.setX(pillar2.getX() + 10.5f);
            pillarTop1.setY(pillar2.getY() + 10.5f);
            pillarTop2.setX(pillar1.getX() + 10.5f);
            pillarTop2.setY(pillar1.getY() + 10.5f);
            pillarTop3.setX(pillar17.getX() + 10.5f);
            pillarTop3.setY(pillar17.getY() + 10.5f);
            pillarTop4.setX(pillar16.getX() + 10.5f);
            pillarTop4.setY(pillar16.getY() + 10.5f);
            pillarTop5.setX(pillar15.getX() + 10.5f);
            pillarTop5.setY(pillar15.getY() + 10.5f);
            pillarTop6.setX(pillar14.getX() + 10.5f);
            pillarTop6.setY(pillar14.getY() + 10.5f);
            putTopToBack(pillarTop1);
            putTopToBack(pillarTop2);
            putTopToBack(pillarTop3);
            putTopToBack(pillarTop4);
            putTopToBack(pillarTop5);
            putTopToBack(pillarTop6);

        } else if (Rect.intersects(ballRect, pillar2Rect)) {
            pillarTop1.setX(pillar3.getX() + 10.5f);
            pillarTop1.setY(pillar3.getY() + 10.5f);
            pillarTop2.setX(pillar2.getX() + 10.5f);
            pillarTop2.setY(pillar2.getY() + 10.5f);
            pillarTop3.setX(pillar1.getX() + 10.5f);
            pillarTop3.setY(pillar1.getY() + 10.5f);
            pillarTop4.setX(pillar17.getX() + 10.5f);
            pillarTop4.setY(pillar17.getY() + 10.5f);
            pillarTop5.setX(pillar16.getX() + 10.5f);
            pillarTop5.setY(pillar16.getY() + 10.5f);
            pillarTop6.setX(pillar15.getX() + 10.5f);
            pillarTop6.setY(pillar15.getY() + 10.5f);
        } else if (Rect.intersects(ballRect, pillar3Rect)) {
            pillarTop4.setVisibility(View.INVISIBLE);
            pillarTop5.setVisibility(View.INVISIBLE);
            pillarTop6.setVisibility(View.INVISIBLE);
            pillarTop1.setX(pillar4.getX() + 10.5f);
            pillarTop1.setY(pillar4.getY() + 10.5f);
            pillarTop2.setX(pillar3.getX() + 10.5f);
            pillarTop2.setY(pillar3.getY() + 10.5f);
            pillarTop3.setX(pillar2.getX() + 10.5f);
            pillarTop3.setY(pillar2.getY() + 10.5f);
        } else if (Rect.intersects(ballRect, pillar4Rect)) {
            pillarTop1.setX(pillar5.getX() + 10.5f);
            pillarTop1.setY(pillar5.getY() + 10.5f);
            pillarTop2.setX(pillar4.getX() + 10.5f);
            pillarTop2.setY(pillar4.getY() + 10.5f);
            pillarTop3.setX(pillar3.getX() + 10.5f);
            pillarTop3.setY(pillar3.getY() + 10.5f);
        } else if (Rect.intersects(ballRect, pillar5Rect)) {
            pillarTop1.setX(pillar6.getX() + 10.5f);
            pillarTop1.setY(pillar6.getY() + 10.5f);
            pillarTop2.setX(pillar5.getX() + 10.5f);
            pillarTop2.setY(pillar5.getY() + 10.5f);
            pillarTop3.setX(pillar4.getX() + 10.5f);
            pillarTop3.setY(pillar4.getY() + 10.5f);
        } else if (Rect.intersects(ballRect, pillar6Rect)) {
            pillarTop1.setX(pillar7.getX() + 10.5f);
            pillarTop1.setY(pillar7.getY() + 10.5f);
            pillarTop2.setX(pillar6.getX() + 10.5f);
            pillarTop2.setY(pillar6.getY() + 10.5f);
            pillarTop3.setX(pillar5.getX() + 10.5f);
            pillarTop3.setY(pillar5.getY() + 10.5f);
        } else if (Rect.intersects(ballRect, pillar7Rect)) {
            pillarTop1.setX(pillar8.getX() + 10.5f);
            pillarTop1.setY(pillar8.getY() + 10.5f);
            pillarTop2.setX(pillar7.getX() + 10.5f);
            pillarTop2.setY(pillar7.getY() + 10.5f);
            pillarTop3.setX(pillar6.getX() + 10.5f);
            pillarTop3.setY(pillar6.getY() + 10.5f);
        } else if (Rect.intersects(ballRect, pillar8Rect)) {
            pillarTop1.setX(pillar9.getX() + 10.5f);
            pillarTop1.setY(pillar9.getY() + 10.5f);
            pillarTop2.setX(pillar8.getX() + 10.5f);
            pillarTop2.setY(pillar8.getY() + 10.5f);
            pillarTop3.setX(pillar7.getX() + 10.5f);
            pillarTop3.setY(pillar7.getY() + 10.5f);
        } else if (Rect.intersects(ballRect, pillar9Rect)) {
            pillarTop1.setX(pillar10.getX() + 10.5f);
            pillarTop1.setY(pillar10.getY() + 10.5f);
            pillarTop2.setX(pillar9.getX() + 10.5f);
            pillarTop2.setY(pillar9.getY() + 10.5f);
            pillarTop3.setX(pillar8.getX() + 10.5f);
            pillarTop3.setY(pillar8.getY() + 10.5f);
        } else if (Rect.intersects(ballRect, pillar10Rect)) {
            pillarTop1.setX(pillar11.getX() + 10.5f);
            pillarTop1.setY(pillar11.getY() + 10.5f);
            pillarTop2.setX(pillar10.getX() + 10.5f);
            pillarTop2.setY(pillar10.getY() + 10.5f);
            pillarTop3.setX(pillar9.getX() + 10.5f);
            pillarTop3.setY(pillar9.getY() + 10.5f);
        } else if (Rect.intersects(ballRect, pillar11Rect)) {
            pillarTop1.setX(pillar12.getX() + 10.5f);
            pillarTop1.setY(pillar12.getY() + 10.5f);
            pillarTop2.setX(pillar11.getX() + 10.5f);
            pillarTop2.setY(pillar11.getY() + 10.5f);
            pillarTop3.setX(pillar10.getX() + 10.5f);
            pillarTop3.setY(pillar10.getY() + 10.5f);
        } else if (Rect.intersects(ballRect, pillar12Rect)) {
            pillarTop1.setX(pillar13.getX() + 10.5f);
            pillarTop1.setY(pillar13.getY() + 10.5f);
            pillarTop2.setX(pillar12.getX() + 10.5f);
            pillarTop2.setY(pillar12.getY() + 10.5f);
            pillarTop3.setX(pillar11.getX() + 10.5f);
            pillarTop3.setY(pillar11.getY() + 10.5f);
        } else if (Rect.intersects(ballRect, pillar13Rect)) {
            pillarTop1.setX(pillar14.getX() + 10.5f);
            pillarTop1.setY(pillar14.getY() + 10.5f);
            pillarTop2.setX(pillar13.getX() + 10.5f);
            pillarTop2.setY(pillar13.getY() + 10.5f);
            pillarTop3.setX(pillar12.getX() + 10.5f);
            pillarTop3.setY(pillar12.getY() + 10.5f);
        } else if (Rect.intersects(ballRect, pillar14Rect)) {
            pillarTop1.setX(pillar15.getX() + 10.5f);
            pillarTop1.setY(pillar15.getY() + 10.5f);
            pillarTop2.setX(pillar14.getX() + 10.5f);
            pillarTop2.setY(pillar14.getY() + 10.5f);
            pillarTop3.setX(pillar13.getX() + 10.5f);
            pillarTop3.setY(pillar13.getY() + 10.5f);
        } else if (Rect.intersects(ballRect, pillar15Rect)) {
            pillarTop1.setX(pillar16.getX() + 10.5f);
            pillarTop1.setY(pillar16.getY() + 10.5f);
            pillarTop2.setX(pillar15.getX() + 10.5f);
            pillarTop2.setY(pillar15.getY() + 10.5f);
            pillarTop3.setX(pillar14.getX() + 10.5f);
            pillarTop3.setY(pillar14.getY() + 10.5f);
        } else if (Rect.intersects(ballRect, pillar16Rect)) {
            pillarTop1.setX(pillar17.getX() + 10.5f);
            pillarTop1.setY(pillar17.getY() + 10.5f);
            pillarTop2.setX(pillar16.getX() + 10.5f);
            pillarTop2.setY(pillar16.getY() + 10.5f);
            pillarTop3.setX(pillar15.getX() + 10.5f);
            pillarTop3.setY(pillar15.getY() + 10.5f);
        } else if (Rect.intersects(ballRect, pillar17Rect)) {
            pillarTop1.setX(pillar1.getX() + 10.5f);
            pillarTop1.setY(pillar1.getY() + 10.5f);
            pillarTop2.setX(pillar17.getX() + 10.5f);
            pillarTop2.setY(pillar17.getY() + 10.5f);
            pillarTop3.setX(pillar16.getX() + 10.5f);
            pillarTop3.setY(pillar16.getY() + 10.5f);
        }
    }
}

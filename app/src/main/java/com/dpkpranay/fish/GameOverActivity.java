package com.dpkpranay.fish;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class GameOverActivity extends AppCompatActivity {
    private Button btn;
    private TextView tv;
    String sco;
    MediaPlayer mm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);
        btn=(Button)findViewById(R.id.play_again_btn);
        tv=(TextView)findViewById(R.id.tv);
        mm=MediaPlayer.create(this,R.raw.appl);
        mm.start();

        sco=getIntent().getExtras().get("score").toString();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(GameOverActivity.this,MainActivity.class));
            }
        });

        tv.setText("score:"+sco);
    }
}

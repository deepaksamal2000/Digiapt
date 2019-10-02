package com.ellipsonic.digiapt_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.VideoView;

import java.io.File;

public class SecondActivity extends AppCompatActivity {

    VideoView videoView;
  Uri file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        videoView = findViewById(R.id.videoview);
        Intent i = getIntent();
        try {
            if (Uri.parse(i.getExtras().get("url").toString())!=null)
                file = Uri.parse(i.getExtras().get("url").toString());

            //Intent data= Intent.getIntent(intent.getStringExtra("url"));
            videoView.setVideoURI(file);
            videoView.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

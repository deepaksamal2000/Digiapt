package com.ellipsonic.digiapt_project;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import java.io.File;

public class MainActivity extends AppCompatActivity {
    Button record, gallery;
    MediaController m;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        record=findViewById(R.id.record);
        gallery=findViewById(R.id.gallery);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();

        m = new MediaController(this);
        record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isStoragePermissionGranted())
                captureVideo();
            }
        });
        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectVideo();
            }
        });



    }
    private void selectVideo() {
        Intent intent = new Intent();
        intent.setType("video/*");
        intent.setAction(Intent.ACTION_PICK);
        intent.putExtra(MediaStore.EXTRA_SIZE_LIMIT,10);
        startActivityForResult(Intent.createChooser(intent,"Select Video"),200);

    }
    public void captureVideo(){
        Intent camera_intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        File video_file = getFilepath();
        Uri video_uri=Uri.fromFile(video_file);

        camera_intent.putExtra(MediaStore.EXTRA_OUTPUT,video_uri);
        

        camera_intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY,1);
        camera_intent.putExtra(MediaStore.EXTRA_DURATION_LIMIT,5);
        if (camera_intent.resolveActivity(getPackageManager()) != null) {

            startActivityForResult(camera_intent, 100);
        }

    }

    private File getFilepath() {

        File folder = new File(Environment.getExternalStorageDirectory(),"deepak");
        if (!folder.exists())
            folder.mkdir();

        File video_file = new File(folder,"sample_video.mp4");
        return video_file;
    }

    @SuppressLint("MissingSuperCall")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {



        if (requestCode == 100 ) {
            if (resultCode == RESULT_OK ){

                Intent intent = new Intent(this, SecondActivity.class);


            intent.putExtra("url", data.getData());
            startActivity(intent);
            }

        }

        if (requestCode == 200  ) {
            if (resultCode == RESULT_OK){
                Intent intent = new Intent(this, SecondActivity.class);

                        intent.putExtra("url", data.getData());
                        startActivity(intent);
            }


        }
    }

    public  boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v("permission","Permission is granted");
                return true;
            } else {

                Log.v("permission","Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Log.v("permission","Permission is granted");
            return true;
        }
    }
}

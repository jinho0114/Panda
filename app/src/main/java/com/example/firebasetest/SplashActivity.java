package com.example.firebasetest;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_panda);



        Handler handler = new Handler();
        handler.postDelayed(new splashHandler() ,3000); //3초뒤에 다음 화면으로 넘어가기

    }

    private class splashHandler implements Runnable {
        @Override
        public void run() {
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
            SplashActivity.this.finish();
        }
    }
}


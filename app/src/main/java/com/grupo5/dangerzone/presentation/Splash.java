package com.grupo5.dangerzone.presentation;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.grupo5.dangerzone.R;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        TextView textoApp = findViewById(R.id.textView);
        ImageView logo = findViewById(R.id.imageView);
        new Handler().postDelayed(new Runnable() {
            @Override public void run() {
                Intent abrirApp = new Intent(Splash.this, LoginActivity.class);
                ActivityOptions activityOptions =
                        ActivityOptions.makeCustomAnimation(Splash.this,
                                R.anim.fui_slide_in_right, R.anim.transicion_vista_splash);
                startActivity(abrirApp,activityOptions.toBundle());
                finish();
            }
        },2500);
    }
}

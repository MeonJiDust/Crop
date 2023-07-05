package com.example.appcontest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
// new keyboard test
//public class MainActivity extends AppCompatActivity{
//
//  private FirebaseAuth mAuth;
//  private FirebaseDatabase mDatabase;
//  private DatabaseReference mReference;
/*
    private TextView simpletext;

    protected void onCreate(Bundle saved
 */
public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mReference;

    private TextView simpletext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        simpletext = (TextView) findViewById(R.id.simpletext);

        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_in);
        simpletext.startAnimation(animation);


        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getApplicationContext(), LogInActivity.class); //Veget 뭐시기를 로그인으로 바꿔주기
                startActivity(intent);
                finish();
            }
        }, 3000);
    }
    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
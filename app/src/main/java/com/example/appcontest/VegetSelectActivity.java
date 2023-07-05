package com.example.appcontest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class VegetSelectActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;

    Button btn_logout;

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_veget_select);

        database = FirebaseDatabase.getInstance();

        //DatabaseReference는 데이터베이스의 특정 위치로 연결하는 거라고 생각하면 된다.
        //현재 연결은 데이터베이스에만 딱 연결해놓고
        //키값(테이블 또는 속성)의 위치 까지는 들어가지는 않은 모습이다.
        databaseReference = database.getReference();

        mAuth = FirebaseAuth.getInstance();

        btn_logout = findViewById(R.id.btn_logout);
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout();
            }
        });
        ImageView imageView1 = findViewById(R.id.circle_rice);
        imageView1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View veiw){
                Intent intent = new Intent(VegetSelectActivity.this, CropListActivity.class);
                sharedPreferences = getSharedPreferences("test", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("tag", 0);
                editor.commit();
                startActivity(intent);
            }
        });

        ImageView imageView2 = findViewById(R.id.circle_vegetable);
        imageView2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View veiw){
                Intent intent = new Intent(VegetSelectActivity.this, CropListActivity.class);
                intent.putExtra("tag", 1);
                sharedPreferences = getSharedPreferences("test", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("tag", 1);
                editor.commit();
                startActivity(intent);

            }
        });
        ImageView imageView3 = findViewById(R.id.circle_charmggae);
        imageView3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View veiw){
                Intent intent = new Intent(VegetSelectActivity.this, CropListActivity.class);
                intent.putExtra("tag", 2);
                sharedPreferences = getSharedPreferences("test", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("tag", 2);
                editor.commit();
                startActivity(intent);
            }
        });

        ImageView imageView4 = findViewById(R.id.circle_fruit);
        imageView4.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View veiw){
                Intent intent = new Intent(VegetSelectActivity.this, CropListActivity.class);
                intent.putExtra("tag", 3);
                sharedPreferences = getSharedPreferences("test", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("tag", 3);
                editor.commit();
                startActivity(intent);
            }
        });
    }
    private void logout() {

        mAuth.signOut();
        onBackPressed();
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(VegetSelectActivity.this, LogInActivity.class); //지금 액티비티에서 다른 액티비티로 이동하는 인텐트 설정
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);    //인텐트 플래그 설정
        startActivity(intent);  //인텐트 이동
        finish();   //현재 액티비티 종료
    }
}
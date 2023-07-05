package com.example.appcontest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Field;

public class CropDetailActivity extends AppCompatActivity {

    TextView tv_title_detail, tv_contents_detail, crops_view_detail, tv_harvest_detail, tv_price_detail, tv_location_detail;
    Button btn_enroll_back_detail, button_buy;
    ImageView crop_image_detail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_detail);

        crop_image_detail = findViewById(R.id.crop_image_detail);

        btn_enroll_back_detail = findViewById(R.id.btn_enroll_back_detail);
        btn_enroll_back_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        button_buy = findViewById(R.id.button_buy);
        button_buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(CropDetailActivity.this, "구현 중입니다", Toast.LENGTH_SHORT).show();
                    onBackPressed();
            }
        });

        tv_title_detail = findViewById(R.id.tv_title_detail);
        tv_contents_detail = findViewById(R.id.tv_contents_detail);
        crops_view_detail = findViewById(R.id.crops_view_detail);
        tv_harvest_detail = findViewById(R.id.tv_harvest_detail);
        tv_price_detail = findViewById(R.id.tv_price_detail);
        tv_location_detail = findViewById(R.id.tv_location_detail);

        Intent intent = getIntent();

        int resID = getResId(intent.getExtras().getString("uri"), R.drawable.class);

        tv_title_detail.setText(intent.getExtras().getString("title"));
        tv_contents_detail.setText(intent.getExtras().getString("contents"));
        crops_view_detail.setText("5.513원   ");
        tv_harvest_detail.setText(intent.getExtras().getString("harvest"));
        tv_price_detail.setText(intent.getExtras().getString("price"));
        tv_location_detail.setText(intent.getExtras().getString("location"));
        crop_image_detail.setImageResource(resID);


    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(CropDetailActivity.this, CropListActivity.class); //지금 액티비티에서 다른 액티비티로 이동하는 인텐트 설정
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);    //인텐트 플래그 설정
        startActivity(intent);  //인텐트 이동
        finish();   //현재 액티비티 종료
    }
    public static int getResId(String resName, Class<?> c) {

        try {
            Field idField = c.getDeclaredField(resName);
            return idField.getInt(idField);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
}
package com.example.appcontest;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class EnrollVegetActivity extends AppCompatActivity {

    private final int GALLERY_CODE = 10;

    private FirebaseAuth mAuth;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mReference;
    private FirebaseStorage storage;
    private FirebaseFirestore db;


    AlertDialog.Builder builder;
    String crops[];
    String quote[];

    String filename;

    int crop_type;

    Button btn_enroll_crop, btn_select_image, btn_select_food_crops, btn_enroll_back;
    EditText et_title, et_contents, et_price, et_harvest, et_location;

    ImageView crops_type_title;
    ImageView crop_image;

    TextView crops_view;

    WebView webView;
    private Handler handler;

    private SharedPreferences sharedPreferences;

    //
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        View view = getCurrentFocus();
        if (view != null && (ev.getAction() == MotionEvent.ACTION_UP || ev.getAction() == MotionEvent.ACTION_MOVE)
                && view instanceof EditText && !view.getClass().getName().startsWith("android.webkit.")) {
            int scrcoords[] = new int[2];
            view.getLocationOnScreen(scrcoords);
            float x = ev.getRawX() + view.getLeft() - scrcoords[0];
            float y = ev.getRawY() + view.getTop() - scrcoords[1];
            if (x < view.getLeft() || x > view.getRight() || y < view.getTop() || y > view.getBottom())
                ((InputMethodManager)this.getSystemService(Context.INPUT_METHOD_SERVICE))
                        .hideSoftInputFromWindow((this.getWindow().getDecorView().getApplicationWindowToken()), 0);
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enroll_veget);

        btn_enroll_back = findViewById(R.id.btn_enroll_back);
        btn_enroll_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        Intent intent = getIntent();
        crop_type = intent.getExtras().getInt("tag");

        sharedPreferences = getSharedPreferences("test", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("tag", crop_type);
        editor.commit();

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();
        mReference = mDatabase.getInstance().getReference();
        db = FirebaseFirestore.getInstance();

        crops_view = findViewById(R.id.crops_view);
        crops_type_title = findViewById(R.id.crops_type);
        crop_image = findViewById(R.id.crop_image);

        if(crop_type == 0){
            crops_type_title.setImageResource(R.drawable.food_crops_title);
        }else if(crop_type == 1){
            crops_type_title.setImageResource(R.drawable.vegetables_title);
        }else if(crop_type == 2){
            crops_type_title.setImageResource(R.drawable.special_crops_title);
        }else{
            crops_type_title.setImageResource(R.drawable.fruits_title);
        }

        btn_enroll_crop = findViewById(R.id.enroll_crop);
        btn_select_food_crops = findViewById(R.id.btn_select_food_crops);
        btn_select_image = findViewById(R.id.btn_select_image);

        et_title = findViewById(R.id.et_title);
        et_contents = findViewById(R.id.et_contents);
        et_price = findViewById(R.id.et_price);
        et_harvest = findViewById(R.id.et_harvest);
        et_location = findViewById(R.id.et_location);

        btn_select_food_crops.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });
//
//        et_location.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                initWebView();
//            }
//        });

        btn_enroll_crop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try{
                    FirebaseUser user = mAuth.getCurrentUser();

                    mReference.child("UserAccount").child(user.getUid()).child("name").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            String value = snapshot.getValue(String.class);
                            String username = value;

                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
                            String date  = sdf.format(new Date());

                            String title = et_title.getText().toString();
                            String contents = et_contents.getText().toString();
                            String price = et_price.getText().toString();
                            String harvest = et_harvest.getText().toString();
                            String location = et_location.getText().toString();

                            //EnrollInfo enrollInfo = new EnrollInfo(username, title, contents, price, harvest, date, location, uri);
                            //db.collection("cropList").add(enrollInfo);
                            Toast.makeText(EnrollVegetActivity.this, "기능 구현 중입니다", Toast.LENGTH_SHORT).show();
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                        }
                    });
                }catch (Exception e){
                    Toast.makeText(EnrollVegetActivity.this, "오류 수정 중입니다", Toast.LENGTH_SHORT).show();
                    Log.e("EnrollVegetActivity", "등록 버튼 클릭");
                }
                onBackPressed();
            }

        });

        btn_select_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadAlbum();
            }
        });
    }

//    //public void initWebView() {
//
//        // WebView 설정
//        webView = (WebView)findViewById(R.id.address_webview);
//
//        webView.setVisibility(View.VISIBLE);
//        // javascript 를 사용할 수 있게 셋팅.
//        webView.getSettings().setJavaScriptEnabled(true);
//
//        // javascript 의 window.open 허용.
//        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
//
//        webView.setWebChromeClient(new WebChromeClient());
//
//        // "TestApp" 이름이 중요합니다. javascript 에서 호출되는 이름과 동일해야 합니다.
//        webView.addJavascriptInterface(new AndroidBridge(), "TestApp");
//
//        webView.loadUrl("http://dmaps.daum.net/map_js_init/postcode.v2.js");
//    }


//    private class AndroidBridge {
//        @JavascriptInterface
//        public void setAddress(final String param1, final String param2, final String param3) {
//            handler = new Handler();
//            handler.post(new Runnable() {
//                @Override
//                public void run() {
//                    et_location.setText(String.format("(%s) %s %s", param1, param2, param3));
//                }
//            });
//        }
//    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(EnrollVegetActivity.this, CropListActivity.class); //지금 액티비티에서 다른 액티비티로 이동하  는 인텐트 설정
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);    //인텐트 플래그 설정
        startActivity(intent);  //인텐트 이동
        finish();   //현재 액티비티 종료
    }

    private void loadAlbum() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch(requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    Uri uri = data.getData();
                    crop_image.setImageURI(uri);
                }
                break;
        }
        /*
        if(requestCode == GALLERY_CODE){
            Uri file = data.getData();
            filename = file + ".jpg";
            StorageReference storageRef = storage.getReference();
            StorageReference riversRef = storageRef.child("photo_img/" + filename);
            UploadTask uploadTask = riversRef.putFile(file);

            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(EnrollVegetActivity.this, "사진이 정상적으로 업로드 되지 않았습니다.", Toast.LENGTH_SHORT).show();
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    riversRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            Glide.with(EnrollVegetActivity.this).load(uri).into(crop_image);
                        }
                    });
                }
            });
        }

         */
    }

    private void showDialog() {


        if (crop_type == 0){
            crops = getResources().getStringArray(R.array.food_crops);
            quote = getResources().getStringArray(R.array.food_crops_quote);
        }else if(crop_type == 1){
            crops = getResources().getStringArray(R.array.vegetables);
            quote = getResources().getStringArray(R.array.vegetables_quote);
        }else if(crop_type == 2){
            crops = getResources().getStringArray(R.array.special_crops);
            quote = getResources().getStringArray(R.array.special_crops_quote);
        }else{
            crops = getResources().getStringArray(R.array.fruits);
            quote = getResources().getStringArray(R.array.fruits_quote);

        }

        builder = new AlertDialog.Builder(EnrollVegetActivity.this);
        builder.setTitle("작물을 선택하세요");
        builder.setItems(crops, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                btn_select_food_crops.setText(crops[i]);
                crops_view.setText(quote[i] + "   ");
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        return super.onKeyDown(keyCode, event);
    }
}
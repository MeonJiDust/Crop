package com.example.appcontest;

import static java.sql.DriverManager.println;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    private static final String TAG = "RegisterActivity";

    private FirebaseAuth mAuth;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mReference;

    SQLiteDatabase database;

    AlertDialog.Builder builder;

    TextView file_name;
    EditText etEmail, etPW, etCheckPW, etName, etPhoneNumber;
    Button btnRegister, select_file;
    RadioButton btnSeller, btnBuyer;
    RadioGroup radioGroup;
    String[] user_type = new String[1];

    ConstraintLayout layout;

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        View view = getCurrentFocus();
        if (view != null && (ev.getAction() == MotionEvent.ACTION_UP || ev.getAction() == MotionEvent.ACTION_MOVE) && view instanceof EditText && !view.getClass().getName().startsWith("android.webkit.")) {
            int scrcoords[] = new int[2];
            view.getLocationOnScreen(scrcoords);
            float x = ev.getRawX() + view.getLeft() - scrcoords[0];
            float y = ev.getRawY() + view.getTop() - scrcoords[1];
            if (x < view.getLeft() || x > view.getRight() || y < view.getTop() || y > view.getBottom())
                ((InputMethodManager)this.getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow((this.getWindow().getDecorView().getApplicationWindowToken()), 0);
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        layout = findViewById(R.id.registerActivity);

        select_file = findViewById(R.id.select_file);
        file_name = findViewById(R.id.file_name);
        radioGroup = findViewById(R.id.radioGroup);
        RadioGroup.OnCheckedChangeListener radiogroupListener = new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                if (i == R.id.seller){
                    user_type[0] = "판매자";
                    file_name.setVisibility(View.VISIBLE);
                    select_file.setVisibility(View.VISIBLE);
                }else if(i == R.id.buyer){
                    user_type[0] = "구매자";
                    file_name.setVisibility(View.INVISIBLE);
                    select_file.setVisibility(View.INVISIBLE);
                }
            }
        };

        radioGroup.setOnCheckedChangeListener(radiogroupListener);

        try{
            mAuth = FirebaseAuth.getInstance();
            mDatabase = FirebaseDatabase.getInstance();
            mReference = mDatabase.getInstance().getReference();
        }catch (Exception e){
            Log.e(TAG, "파이어베이스 연동 오류", e);
        }

//        database = openOrCreateDatabase("AppContest", MODE_PRIVATE,null) ; //보안때문에 요즘은 대부분 PRIVATE사용, SQLiteDatabase객체가 반환됨
//        if(database !=null){
//            println("데이터베이스 오픈됨");
//        }
//
//        if(database!= null) {
//            //_id는 SQLite에서 내부적으로 관리되는 내부 id이다.
//            String sql = "create table " + "userAccount" + "(_id integer PRIMARY KEY autoincrement, user_type text, name text, phoneNum text, email text, password text)";
//            database.execSQL(sql);
//
//            println("테이블 생성됨.");
//        }else {
//            println("데이터베이스를 먼저 오픈하십쇼");
//        }



        btnSeller = findViewById(R.id.seller);
        btnBuyer = findViewById(R.id.buyer);
        etName = findViewById(R.id.etName);
        etPhoneNumber = findViewById(R.id.etPhoneNumber);
        etEmail = findViewById(R.id.etEmail);
        etPW = findViewById(R.id.etPW);
        etCheckPW = findViewById(R.id.etCheckPW);

        btnRegister = findViewById(R.id.btnRegister);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = etEmail.getText().toString().trim();
                String pwd = etPW.getText().toString().trim();
                String pwdCheck = etCheckPW.getText().toString().trim();


                if(pwd.equals(pwdCheck)){

                    Log.d(TAG, "등록버튼" + email + " , " + pwd);

                    final ProgressDialog mDialog = new ProgressDialog(RegisterActivity.this);
                    mDialog.setMessage("가입중입니다...");
                    mDialog.show();


                    try{
                        mAuth.createUserWithEmailAndPassword(email, pwd).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {

                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    //여기까지 들어오는데 5분 정도 걸리는데 그 이유 못 찾음...

                                    mDialog.dismiss();


                                    //firebase의 realtime database에 데이터 전송 안 됨.
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    UserInfo userInfo = new UserInfo();

                                    String name = etName.getText().toString();
                                    String email = etEmail.getText().toString();
                                    String phone = etPhoneNumber.getText().toString();
                                    String pw = etPW.getText().toString();

                                    Log.d(TAG,"user_type[0] :: " + user_type[0]);


                                    userInfo.setUid(user.getUid());
                                    Log.d(TAG, user.getUid());
                                    userInfo.setEmail(user.getEmail());
                                    Log.d(TAG, user.getEmail());
                                    userInfo.setPassword(pw);
                                    userInfo.setName(name);
                                    userInfo.setPhone(phone);

//                                if(database != null){
//                                    String sql = "insert into userAccount(user_type, name, phoneNum, email, password) values(?, ?, ?, ?, ?)";
//                                    Object[] params = {user_type[0], name, phone, email, pw};
//                                    database.execSQL(sql, params);//이런식으로 두번쨰 파라미터로 이런식으로 객체를 전달하면 sql문의 ?를 이 params에 있는 데이터를 물음표를 대체해준다.
//                                    println("데이터 추가함");
//
//                                }else {
//                                    println("데이터베이스를 먼저 오픈하시오");
//                                }

                                    mReference.child("UserAccount").child(user.getUid()).setValue(userInfo);
                                    onBackPressed();

                                    Toast.makeText(RegisterActivity.this, "회원가입에 성공하였습니다.", Toast.LENGTH_SHORT).show();
                                }else{
                                    mDialog.dismiss();
                                    Toast.makeText(RegisterActivity.this, "이미 존재하는 아이디입니다.", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                            }
                        });
                    }catch(Exception e){
                        mDialog.dismiss();
                        Toast.makeText(RegisterActivity.this, "정보를 입력하세요", Toast.LENGTH_SHORT).show();
                        Log.e(TAG, "정보 미입력");
                    }

                }else{
                    Toast.makeText(RegisterActivity.this, "비밀번호가 틀렸습니다. 다시 입력해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(RegisterActivity.this, LogInActivity.class); //지금 액티비티에서 다른 액티비티로 이동하는 인텐트 설정
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);    //인텐트 플래그 설정
        startActivity(intent);  //인텐트 이동
        finish();   //현재 액티비티 종료
    }
}
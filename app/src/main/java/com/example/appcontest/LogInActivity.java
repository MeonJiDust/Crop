package com.example.appcontest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LogInActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";

    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    EditText etEmail, etPW;

    public void onStart() {
        super.onStart();
        currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            reload();
        }
    }
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
        setContentView(R.layout.activity_log_in);

        mAuth = FirebaseAuth.getInstance();



        etEmail = findViewById(R.id.text_id);
        etPW = findViewById(R.id.text_pw);

        Button LoginButton = (Button) findViewById(R.id.button_login);
        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = etEmail.getText().toString().trim();
                String pwd = etPW.getText().toString().trim();

                if (email.equals("")) {
                    Toast.makeText(LogInActivity.this, "이메일을 입력해 주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (pwd.equals("")) {
                    Toast.makeText(LogInActivity.this, "비밀번호를 입력해 주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }

//                if(database != null){
//                    String sql = "select email, password from "+ "userAccount";
//                    Cursor cursor = database.rawQuery(sql, null); //파라미터는 없으니깐 null 값 넣어주면된다.
//                    //println("조회된 데이터개수 :" + cursor.getCount());
//                    //for문으로해도되고 while 문으로 해도됨.
//                    for( int i = 0; i< cursor.getCount(); i++){
//                        cursor.moveToNext();//이걸 해줘야 다음 레코드로 넘어가게된다.
//                        String name = cursor.getString(0); //첫번쨰 칼럼을 뽑아줌
//                        int age = cursor.getInt(1);
//                        String mobile = cursor.getString(2);
//                        //println("#" + i + " -> " +  name + ", " + age + ", "+ mobile );
//                    }
//                    cursor.close(); //cursor라는것도 실제 데이터베이스 저장소를 접근하는 것이기 때문에 자원이 한정되있다. 그러므로 웬만하면 마지막에 close를 꼭 해줘야한다.
//                }

                mAuth.signInWithEmailAndPassword(email, pwd).addOnCompleteListener(LogInActivity.this, new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //이메일, 비밀번호 잘못 입력시 클릭하고 여기까지 들어와서 else문까지 가는 데에 3분 이상 걸리는 이유 아직 못 찾음.
                        //제대로 입력하면 if문으로 바로 들어감.

                        if (task.isSuccessful()) {
                            Toast.makeText(LogInActivity.this, "로그인 되었습니다.", Toast.LENGTH_SHORT).show();
                            finish();
                            Intent intent = new Intent(getApplicationContext(), VegetSelectActivity.class);
                            startActivity(intent);
                        } else {
                            Log.d(TAG, "이메일 혹은 비밀번호 오류");
                            Toast.makeText(LogInActivity.this, "가입되지 않은 계정이거나 정보 재입력해주세요.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        Button RegisterButton = (Button) findViewById(R.id.button_register);
        RegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent RegisterIntent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(RegisterIntent);
            }
        });
    }

    private void reload() {
        Intent SelectIntent = new Intent(getApplicationContext(), VegetSelectActivity.class);
        startActivity(SelectIntent);
    }
}
package com.example.appcontest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;

public class CropListActivity extends AppCompatActivity {

    public static final int REQUEST_CODE = 101;

    private FirebaseAuth mAuth;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mReference;
    private FirebaseFirestore db;

    RecyclerView recyclerView;
    CustomAdapter adapter;
    ArrayList<EnrollInfo> enrollList;

    LinearLayout mLinea_empty;
    Button btn_enroll_crop, btn_enroll_back_list;

    ImageView crops_type_title;
    TextView empty_view;

    int crop_type;

    Context mContext;

    private SharedPreferences sharedPreferences;

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
        setContentView(R.layout.activity_crop_list);


        Intent intent = getIntent();
        sharedPreferences = getSharedPreferences("test", MODE_PRIVATE);

        crop_type = sharedPreferences.getInt("tag", 0);

        btn_enroll_crop = findViewById(R.id.enroll_crop_list);
        crops_type_title = findViewById(R.id.crops_type_list);

        empty_view = findViewById(R.id.empty_view);

        mContext = this;

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();
        mReference = mDatabase.getInstance().getReference();
        FirebaseUser user = mAuth.getCurrentUser();

        btn_enroll_back_list = findViewById(R.id.btn_enroll_back_list);
        btn_enroll_back_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


//        String user_classify = mReference.child("UserAccount").child(user.getUid()).child("user_classify").toString();
//
//        if(user_classify == "판매자"){
//            btn_enroll_crop.setVisibility(View.VISIBLE);
//        }else if(user_classify == "구매자"){
//            btn_enroll_crop.setVisibility(View.INVISIBLE);
//        }

        if(crop_type == 0){
            crops_type_title.setImageResource(R.drawable.food_crops_title);
        }else if(crop_type == 1){
            crops_type_title.setImageResource(R.drawable.vegetables_title);
        }else if(crop_type == 2){
            crops_type_title.setImageResource(R.drawable.special_crops_title);
        }else{
            crops_type_title.setImageResource(R.drawable.fruits_title);
        }

        btn_enroll_crop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(CropListActivity.this, EnrollVegetActivity.class);
                intent.putExtra("tag", crop_type);

                startActivity(intent);

            }
        });

        adapter = new CustomAdapter();
        adapter.setListener(new MyEventListener() {
            @Override
            public void onCliclkEvent(int id) {

//                CropDetailActivity dialog = new CropDetailActivity(mContext , boardList.get(id).title, boardList.get(id).date,boardList.get(id).board,boardList.get(id).name);
//                dialog.show();
                Intent intent = new Intent(CropListActivity.this, CropDetailActivity.class);
                intent.putExtra("title", enrollList.get(id).title);
                intent.putExtra("name", enrollList.get(id).name);
                intent.putExtra("price", enrollList.get(id).price);
                intent.putExtra("harvest", enrollList.get(id).harvest);
                intent.putExtra("contents", enrollList.get(id).contents);
                intent.putExtra("location", enrollList.get(id).location);
                intent.putExtra("uri", enrollList.get(id).uri);
                startActivity(intent);
            }
        });
        recyclerView = findViewById(R.id.recyclerview);
        mLinea_empty = findViewById(R.id.empty_linear);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        enrollList = new ArrayList<>();

        enrollList.add(new EnrollInfo("귤 팔아요~", "송유경 농업인", "2022-11-19", "10,000원", "전라북도 익산시", "11/19", "맛있는 귤이 단돈 10,000원!!~ 모두 사세요", "rbf"));
        enrollList.add(new EnrollInfo("쌀 10kg에 60,000원 파격 특가!!!", "송유경 친구 ", "2022-10-19", "60,000원","전라남도 장성군", "11/19", "햅쌀 찹쌀 다 있어요~~ 모두모두 전화로 문의 주세요~~","tkf"));
        enrollList.add(new EnrollInfo("검은콩 200g 5,900원", "이션 귀농인", "2021-11-19", "5,900원", "전라북도 전주시","11/19", "검은콩 국수에 제격인 검은콩 200g!!! 여기가 제일 싸요~", "rjadmszhd"));
        enrollList.add(new EnrollInfo("강릉 솔향 배추 3포기 만원대~", "어느 인간", "2022-11-10", "12,000원", "강원도 강릉시", "11/19", "김장철에 꼭 필요한 절임배추!! 여기가 제일 싸요", "qocn"));
        enrollList.add(new EnrollInfo("사과 5kg에 3만원~~", "유시 연합", "2022-11-20", "20,900원", "제주특별자치도 서귀포시", "11/19", "맛있는 사과~ 제주도 사과~~", "tkrhk"));


        if( enrollList.size() > 0){
            recyclerView.setVisibility( View.VISIBLE );
            mLinea_empty.setVisibility( View.GONE );

            adapter.notifyDataSetChanged();
        }else{
            recyclerView.setVisibility( View.GONE );
            mLinea_empty.setVisibility( View.VISIBLE );
        }

        db = FirebaseFirestore.getInstance();

       // getList();

    }


    class ItemClick_Event implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            //laskfdja
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(CropListActivity.this, VegetSelectActivity.class); //지금 액티비티에서 다른 액티비티로 이동하는 인텐트 설정
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);    //인텐트 플래그 설정
        startActivity(intent);  //인텐트 이동
        finish();   //현재 액티비티 종료
    }



    public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.CustomViewHolder> {
        public MyEventListener listener;

        public class CustomViewHolder extends RecyclerView.ViewHolder {
            protected TextView titleView, nameView , dateView, priceView, locationView, contentsView, harvestView;
            protected ImageView image_view_list;
            private View mMain_View;
            public CustomViewHolder(View view) {
                super(view);
                mMain_View = view;
                this.titleView = (TextView) view.findViewById(R.id.title_list);
                this.nameView = (TextView) view.findViewById(R.id.name_list);
                this.dateView = (TextView) view.findViewById(R.id.enroll_date_list);
                this.priceView = (TextView) view.findViewById(R.id.price_list);
                this.locationView = (TextView) view.findViewById(R.id.location_list);
                this.image_view_list = (ImageView) view.findViewById(R.id.image_view_list);

                mMain_View.setOnClickListener(new ItemClick_Event() );
            }
        }


        public void setListener(MyEventListener listener) {
            this.listener = listener;
        }
        @Override
        public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

            View view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.item_view, viewGroup, false);
            CustomViewHolder viewHolder = new CustomViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull CustomViewHolder viewholder, @SuppressLint("RecyclerView") int position) {
            viewholder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onCliclkEvent(position);
                }
            });

            int resID = getResId(enrollList.get(position).getUri(), R.drawable.class);
            Log.i("-del", "11");
            viewholder.titleView.setText(enrollList.get(position).getTitle());
            viewholder.nameView.setText("등록자 : " + enrollList.get(position).getName());
            viewholder.dateView.setText("등록일 : " + enrollList.get(position).getDate());
            viewholder.priceView.setText("가격 : " + enrollList.get(position).getPrice());
            viewholder.locationView.setText("지역 : " + enrollList.get(position).getLocation());
            viewholder.image_view_list.setImageResource(resID);


        }
        @Override
        public int getItemViewType(int position) {
            return position;
        }

        @Override
        public int getItemCount() {
            Log.i("-del : ", "개수 : "+enrollList.size());
            return enrollList.size();
        }
    }

    public interface MyEventListener {
        void onCliclkEvent(int id);
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
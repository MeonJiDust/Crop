# 💡주제

#### ▸ 농산물 시세 확인 및 판매 어플리케이션

# 📝 배경

농부들이 농산물을 시장 등에 판매할 때마다 농산물의 시세를 확인하러 kamis등의 농산물 시세 확인 웹사이트를 들어가야한다는 불편함을 줄이고자 만들게 된 어플리케이션입니다.

사용자(판매자)는 농산물 그 날 농산물의 시세를 확인하고 바로 어플에 판매 글을 등록할 수 있으며, 또 다른 사용자(구매자)는 마트 등에 납품할 때 생기는 수수료가 붙지 않은 농산물을 구입할 수 있습니다.

# ⭐️ 주요 코드 리뷰

▸ 작물 등록 화면에서 '작물선택'버튼을 누르면 작물을 선택할 수 있는 다이얼로그가 띄워지게 되고 작물을 선택하면, **해당 작물에 대한 현재 시세를 볼 수 있는** 기능
```
builder = new AlertDialog.Builder(EnrollVegetActivity.this);
        builder.setTitle("작물을 선택하세요");
        builder.setItems(crops, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                btn_select_food_crops.setText(crops[i]);
                crops_view.setText(quote[i] + "   ");
            }
        });
```

▸ 이전 화면에서 선택한 작물의 종류에 따라 그 다음 화면의 상단 사진이 바뀌어야하는데, 작물마다 Activity를 따로 쓰기엔 너무 비효율적인 것 같았다. 

그래서 SharedPreferences 라이브러리를 사용하였다.

이전 Activity에서 선택 작물에 대한 태그값을 putExtra로 다음 Activity로 넘겨주고, 같은 태그값을 SharedPreferences에 저장한다. 다음 Activity에서는 받은 태그값으로 선택 작물에 따라 각각 다른 상단사진을 띄울 수 있게된다.

```
//이전 Activity에서 2번째 작물 선택
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
```

▸

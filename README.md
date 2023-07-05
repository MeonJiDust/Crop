# 💡TOPIC

#### ▸ 농산물 시세 확인 및 판매 어플리케이션

# 📝 Summary

농부들이 농산물을 시장 등에 판매할 때마다 농산물의 시세를 확인하러 kamis등의 농산물 시세 확인 웹사이트를 들어가야한다는 불편함을 줄이고자 만들게 된 어플리케이션입니다.

사용자(판매자)는 농산물 그 날 농산물의 시세를 확인하고 바로 어플에 판매 글을 등록할 수 있으며, 또 다른 사용자(구매자)는 마트 등에 납품할 때 생기는 수수료가 붙지 않은 농산물을 구입할 수 있습니다.

# ⭐️ Key Function

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


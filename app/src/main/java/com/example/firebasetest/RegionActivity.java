package com.example.firebasetest;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

public class RegionActivity extends AppCompatActivity {
    TextView textView;
    TextView textView2;
    Button button_newCase, button_totalCase, button_death;
    ImageView busan, chungbuk, chungnam, daegu,daejeon,gangwon,gyeongbuk,gyeonggi,gyeongnam,incheon,jeju,jeonbuk,jeonnam,sejong,seoul,ulsan,chart1,chart2,chart3;
    final static String TAG = "MapFinal";
    String resultText;
    ImageView[] imageViews = new ImageView[16];
    Integer[] imageViewsId = {R.id.seoul, R.id.incheon, R.id.gyeonggido, R.id.gangwondo, R.id.gyeongbuk, R.id.gyeongnam, R.id.jeonbuk, R.id.jeonnam, R.id.chungbuk, R.id.chungnam, R.id.sejong, R.id.busan, R.id.daegu, R.id.daejeon, R.id.ulsan, R.id.jeju};
    String[] region_arr = {"seoul", "incheon", "gyeonggi", "gangwon", "gyeongbuk", "gyeongnam", "jeonbuk", "jeonnam", "chungbuk", "chungnam", "sejong", "busan", "daegu", "daejeon", "ulsan", "jeju"};

    int i;
    int value_num = 0;
    String region_select;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_btn1:
                final String[] region_arr2 = new String[]{"서울", "인천", "경기", "강원", "경북", "경남", "전북", "전남", "충북", "충남", "세종", "부산", "대구", "대전", "울산", "제주"};
                AlertDialog.Builder dlg = new AlertDialog.Builder(RegionActivity.this);
                dlg.setItems(region_arr2, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AlertDialog.Builder dlg2 = new AlertDialog.Builder(RegionActivity.this);
                        dlg2.setTitle(region_arr2[which] + " 정보");
                        for (i = 0; i < 16; i++) {
                            if (region_arr2[i] == region_arr2[which]) {
                                region_select = region_arr[i];
                            }
                        }
                        Region_inform(region_select);
                        dlg2.setMessage(textView2.getText());
                        dlg2.setPositiveButton("확인", null);
                        dlg2.show();
                    }
                });
                dlg.setTitle("지역을 선택하세요");
                dlg.setPositiveButton("확인", null);
                dlg.show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_region);
        Toolbar toolbar = findViewById (R.id.toolbar);
        setSupportActionBar (toolbar); //액티비티의 앱바(App Bar)로 지정

        textView = (TextView) findViewById(R.id.text1);
        textView2 = (TextView) findViewById(R.id.text2);
        button_newCase = (Button) findViewById(R.id.button_newCase);
        button_totalCase = (Button) findViewById(R.id.button_totalCase);
        button_death = (Button) findViewById(R.id.button_death);
        busan = (ImageView) findViewById(R.id.busan);
        chungbuk = (ImageView) findViewById(R.id.chungbuk);
        chungnam = (ImageView) findViewById(R.id.chungnam);
        daegu = (ImageView) findViewById(R.id.daegu);
        daejeon = (ImageView) findViewById(R.id.daejeon);
        gangwon = (ImageView) findViewById(R.id.gangwondo);
        gyeongbuk = (ImageView) findViewById(R.id.gyeongbuk);
        gyeonggi = (ImageView) findViewById(R.id.gyeonggido);
        gyeongnam = (ImageView) findViewById(R.id.gyeongnam);
        incheon = (ImageView) findViewById(R.id.incheon);
        jeju = (ImageView) findViewById(R.id.jeju);
        jeonbuk = (ImageView) findViewById(R.id.jeonbuk);
        jeonnam = (ImageView) findViewById(R.id.jeonnam);
        sejong = (ImageView) findViewById(R.id.sejong);
        seoul = (ImageView) findViewById(R.id.seoul);
        ulsan = (ImageView) findViewById(R.id.ulsan);
        chart1 = (ImageView) findViewById(R.id.chart_newCase);
        chart2 = (ImageView) findViewById(R.id.chart_totalCase);
        chart3 = (ImageView) findViewById(R.id.chart_death);

        button_newCase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (i = 0; i < 16; i++) {
                    Region(region_arr[i], "newCase");
                    Color_newCase(region_arr[i], value_num);
                }
                chart1.setVisibility(View.VISIBLE);
                chart2.setVisibility(View.INVISIBLE);
                chart3.setVisibility(View.INVISIBLE);
            }
        });
        button_totalCase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (i = 0; i < 16; i++) {
                    Region(region_arr[i], "totalCase");
                    Color_totalCase(region_arr[i], value_num);
                }
                chart1.setVisibility(View.INVISIBLE);
                chart2.setVisibility(View.VISIBLE);
                chart3.setVisibility(View.INVISIBLE);
            }
        });
        button_death.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (i = 0; i < 16; i++) {
                    Region(region_arr[i], "death");
                    Color_death(region_arr[i], value_num);
                }
                chart1.setVisibility(View.INVISIBLE);
                chart2.setVisibility(View.INVISIBLE);
                chart3.setVisibility(View.VISIBLE);
            }
        });

    }
    private void Color_newCase(String countryname, int newCase) {
        for (i = 0; i < 16; i++) {
            imageViews[i] = (ImageView) findViewById(imageViewsId[i]);
            if (countryname.equals(region_arr[i])) {
                if (newCase < 1000) {
                    imageViews[i].setColorFilter(Color.parseColor("#EEC9C9"));
                } else if (newCase < 2000) {
                    imageViews[i].setColorFilter(Color.parseColor("#EBADAD"));
                } else if (newCase < 2500) {
                    imageViews[i].setColorFilter(Color.parseColor("#EA9090"));
                } else if (newCase < 4000) {
                    imageViews[i].setColorFilter(Color.parseColor("#EB6F6F"));
                } else if (newCase < 5000) {
                    imageViews[i].setColorFilter(Color.parseColor("#EF4D4D"));
                } else {
                    imageViews[i].setColorFilter(Color.parseColor("#F62828"));
                }
                break;
            }
        }
    }

    private void Color_totalCase(String countryname, int totalCase) {
        for (i = 0; i < 16; i++) {
            imageViews[i] = (ImageView) findViewById(imageViewsId[i]);
            if (countryname.equals(region_arr[i])) {
                if (totalCase < 500000) {
                    imageViews[i].setColorFilter(Color.parseColor("#EEC9C9"));
                } else if (totalCase < 900000) {
                    imageViews[i].setColorFilter(Color.parseColor("#EBADAD"));
                } else if (totalCase < 1300000) {
                    imageViews[i].setColorFilter(Color.parseColor("#EA9090"));
                } else if (totalCase < 1800000) {
                    imageViews[i].setColorFilter(Color.parseColor("#EB6F6F"));
                } else if (totalCase < 5000000) {
                    imageViews[i].setColorFilter(Color.parseColor("#EF4D4D"));
                } else {
                    imageViews[i].setColorFilter(Color.parseColor("#F62828"));
                }
                break;
            }
        }
    }

    private void Color_death(String countryname, int death) {
        for (i = 0; i < 16; i++) {
            imageViews[i] = (ImageView) findViewById(imageViewsId[i]);
            if (countryname.equals(region_arr[i])) {
                if (death < 100) {
                    imageViews[i].setColorFilter(Color.parseColor("#EEC9C9"));
                } else if (death < 500) {
                    imageViews[i].setColorFilter(Color.parseColor("#EBADAD"));
                } else if (death < 1000) {
                    imageViews[i].setColorFilter(Color.parseColor("#EA9090"));
                } else if (death < 1800) {
                    imageViews[i].setColorFilter(Color.parseColor("#EB6F6F"));
                } else if (death < 6000) {
                    imageViews[i].setColorFilter(Color.parseColor("#EF4D4D"));
                } else {
                    imageViews[i].setColorFilter(Color.parseColor("#F62828"));
                }
                break;
            }
        }
    }

    public int Region(String region, String value) {
        try {
            resultText = new Task().execute().get();
            JSONObject jsonObject = new JSONObject(resultText);
            JSONObject name = (JSONObject) jsonObject.get(region);
            JSONObject korea = (JSONObject) jsonObject.get("korea");
            StringBuilder sb = new StringBuilder();
            sb.append("한국의 누적 확진자 수 :"+ korea.get(value));
            textView.setText(sb.toString());
            String totalCase_get = name.get(value).toString();
            String totalCase_str = totalCase_get.replace(",","");
            value_num = Integer.parseInt(totalCase_str);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return value_num;
    }

    public void Region_inform(String region) {
        try {
            resultText = new Task().execute().get();
            JSONObject jsonObject = new JSONObject(resultText);
            JSONObject name = (JSONObject) jsonObject.get(region);
            StringBuilder sb = new StringBuilder();
            sb.append(name.get("countryName") + "의 신규 확진자 수 :" + name.get("newCase")
                    + "\n" +name.get("countryName") + "의 누적 확진자 수 :"+ name.get("totalCase")
                    + "\n"+name.get("countryName") + "의 누적 사망자 수 :"+ name.get("death")
                    + "\n"+name.get("countryName") + "의 신규 해외유입 확진자 수 :"+ name.get("newFcase"));
            textView2.setText(sb.toString());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}

package com.muzikun.one.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.muzikun.one.R;

import com.muzikun.one.lib.util.Helper;
import com.muzikun.one.widget.ViewDialogRegister;



public class AnalyzeActivity extends AppCompatActivity implements View.OnClickListener {


    String fx = "";
    String cx = "";
    int flag;

    private LinearLayout analyzeBackLl;

    private LinearLayout analyzeTitleLl;

    private TextView analyzeFangwei;

    private TextView analyzeChaoxiang;

    private LinearLayout analyzeFangweiLl;

    private TextView analyzeDirectionTop;

    private TextView analyzeDirectionLeft;

    private ImageView analyze1;

    private ImageView analyze2;

    private ImageView analyze3;

    private ImageView analyze4;

    private ImageView analyze5;

    private ImageView analyze6;

    private ImageView analyze7;

    private ImageView analyze8;

    private ImageView analyze9;

    private TextView analyzeDirectionRight;

    private TextView analyzeDirectionBottom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analyze);

        Helper.setColor(this, Color.parseColor("#891C21"));


        fx = getIntent().getStringExtra("fangxiang");
        cx = getIntent().getStringExtra("chaoxiang");
        initView();
    }

    private void initView() {
        analyzeBackLl = (LinearLayout) findViewById(R.id.analyze_back_ll);

        analyzeTitleLl = (LinearLayout) findViewById(R.id.analyze_title_ll);

        analyzeFangwei = (TextView) findViewById(R.id.analyze_fangwei);

        analyzeChaoxiang = (TextView) findViewById(R.id.analyze_chaoxiang);

        analyzeFangweiLl = (LinearLayout) findViewById(R.id.analyze_fangwei_ll);

        analyzeDirectionTop = (TextView) findViewById(R.id.analyze_direction_top);

        analyzeDirectionLeft = (TextView) findViewById(R.id.analyze_direction_left);
        analyze1 = (ImageView) findViewById(R.id.analyze_1);
        analyze2 = (ImageView) findViewById(R.id.analyze_2);
        analyze3 = (ImageView) findViewById(R.id.analyze_3);
        analyze4 = (ImageView) findViewById(R.id.analyze_4);
        analyze5 = (ImageView) findViewById(R.id.analyze_5);
        analyze6 = (ImageView) findViewById(R.id.analyze_6);
        analyze7 = (ImageView) findViewById(R.id.analyze_7);
        analyze8 = (ImageView) findViewById(R.id.analyze_8);
        analyze9 = (ImageView) findViewById(R.id.analyze_9);
        analyzeDirectionRight = (TextView) findViewById(R.id.analyze_direction_right);
        analyzeDirectionBottom = (TextView) findViewById(R.id.analyze_direction_bottom);

//        @OnClick({R.id.analyze_back_ll, R.id.analyze_1, R.id.analyze_2, R.id.analyze_3, R.id.analyze_4, R.id.analyze_5, R.id.analyze_6, R.id.analyze_7, R.id.analyze_8, R.id.analyze_9})

        analyzeBackLl.setOnClickListener(this);
        analyze1.setOnClickListener(this);
        analyze2.setOnClickListener(this);
        analyze3.setOnClickListener(this);
        analyze4.setOnClickListener(this);
        analyze5.setOnClickListener(this);
        analyze6.setOnClickListener(this);
        analyze7.setOnClickListener(this);
        analyze8.setOnClickListener(this);
        analyze9.setOnClickListener(this);
       
        


        if ("东北".equals(fx)) {
            flag = 1;
            analyze1.setImageResource(R.drawable.jueming);
            analyze2.setImageResource(R.drawable.shengqi);
            analyze3.setImageResource(R.drawable.huohai);
            analyze4.setImageResource(R.drawable.yannian);
            analyze5.setImageResource(R.drawable.zaizhu);
            analyze6.setImageResource(R.drawable.wugui);
            analyze7.setImageResource(R.drawable.tianyi);
            analyze8.setImageResource(R.drawable.fuwei);
            analyze9.setImageResource(R.drawable.liusha);
            analyzeFangwei.setText("房屋布局:" + cx);
            analyzeChaoxiang.setText("飞星:捐财伤丁");
            analyzeDirectionTop.setText("向东北");
            analyzeDirectionLeft.setText("西北");
            analyzeDirectionRight.setText("东南");
            analyzeDirectionBottom.setText("坐西南");
        } else if ("东南".equals(fx)) {
            flag = 2;
            analyze1.setImageResource(R.drawable.wugui);
            analyze2.setImageResource(R.drawable.huohai);
            analyze3.setImageResource(R.drawable.jueming);
            analyze4.setImageResource(R.drawable.tianyi);
            analyze5.setImageResource(R.drawable.zaizhu);
            analyze6.setImageResource(R.drawable.yannian);
            analyze7.setImageResource(R.drawable.liusha);
            analyze8.setImageResource(R.drawable.fuwei);
            analyze9.setImageResource(R.drawable.shengqi);
            analyzeFangwei.setText("房屋布局:" + cx);
            analyzeChaoxiang.setText("飞星:旺财旺丁");
            analyzeDirectionTop.setText("向东南");
            analyzeDirectionLeft.setText("东北");
            analyzeDirectionRight.setText("西南");
            analyzeDirectionBottom.setText("坐西北");
        } else if ("西北".equals(fx)) {
            flag = 3;
            analyze1.setImageResource(R.drawable.liusha);
            analyze2.setImageResource(R.drawable.huohai);
            analyze3.setImageResource(R.drawable.shengqi);
            analyze4.setImageResource(R.drawable.wugui);
            analyze5.setImageResource(R.drawable.zaizhu);
            analyze6.setImageResource(R.drawable.jueming);
            analyze7.setImageResource(R.drawable.tianyi);
            analyze8.setImageResource(R.drawable.fuwei);
            analyze9.setImageResource(R.drawable.yannian);
            analyzeFangwei.setText("房屋布局:" + cx);
            analyzeChaoxiang.setText("飞星:捐财伤丁");
            analyzeDirectionTop.setText("向西北");
            analyzeDirectionLeft.setText("西南");
            analyzeDirectionRight.setText("东北");
            analyzeDirectionBottom.setText("坐东南");
        } else if ("西南".equals(fx)) {
            flag = 4;
            analyze1.setImageResource(R.drawable.huohai);
            analyze2.setImageResource(R.drawable.shengqi);
            analyze3.setImageResource(R.drawable.yannian);
            analyze4.setImageResource(R.drawable.jueming);
            analyze5.setImageResource(R.drawable.zaizhu);
            analyze6.setImageResource(R.drawable.tianyi);
            analyze7.setImageResource(R.drawable.liusha);
            analyze8.setImageResource(R.drawable.fuwei);
            analyze9.setImageResource(R.drawable.wugui);
            analyzeFangwei.setText("房屋布局:" + cx);
            analyzeChaoxiang.setText("飞星:捐财伤丁");
            analyzeDirectionTop.setText("向西南");
            analyzeDirectionLeft.setText("正东");
            analyzeDirectionRight.setText("正西");
            analyzeDirectionBottom.setText("坐东北");
        } else if ("正北".equals(fx)) {
            flag = 5;
            analyze1.setImageResource(R.drawable.jueming);
            analyze2.setImageResource(R.drawable.yannian);
            analyze3.setImageResource(R.drawable.huohai);
            analyze4.setImageResource(R.drawable.wugui);
            analyze5.setImageResource(R.drawable.zaizhu);
            analyze6.setImageResource(R.drawable.shengqi);
            analyze7.setImageResource(R.drawable.liusha);
            analyze8.setImageResource(R.drawable.fuwei);
            analyze9.setImageResource(R.drawable.tianyi);
            analyzeFangwei.setText("房屋布局:" + cx);
            analyzeChaoxiang.setText("飞星:旺丁不旺财");
            analyzeDirectionTop.setText("向正北");
            analyzeDirectionLeft.setText("正西");
            analyzeDirectionRight.setText("正东");
            analyzeDirectionBottom.setText("坐正南");
        } else if ("正东".equals(fx)) {
            flag = 6;
            analyze1.setImageResource(R.drawable.yannian);
            analyze2.setImageResource(R.drawable.jueming);
            analyze3.setImageResource(R.drawable.liusha);
            analyze4.setImageResource(R.drawable.huohai);
            analyze5.setImageResource(R.drawable.zaizhu);
            analyze6.setImageResource(R.drawable.wugui);
            analyze7.setImageResource(R.drawable.shengqi);
            analyze8.setImageResource(R.drawable.fuwei);
            analyze9.setImageResource(R.drawable.tianyi);
            analyzeFangwei.setText("房屋布局:" + cx);
            analyzeChaoxiang.setText("飞星:旺财旺丁");
            analyzeDirectionTop.setText("向正东");
            analyzeDirectionLeft.setText("正北");
            analyzeDirectionRight.setText("正南");
            analyzeDirectionBottom.setText("坐正西");
        } else if ("正南".equals(fx)) {
            flag = 7;
            analyze1.setImageResource(R.drawable.shengqi);
            analyze2.setImageResource(R.drawable.yannian);
            analyze3.setImageResource(R.drawable.jueming);
            analyze4.setImageResource(R.drawable.tianyi);
            analyze5.setImageResource(R.drawable.zaizhu);
            analyze6.setImageResource(R.drawable.huohai);
            analyze7.setImageResource(R.drawable.wugui);
            analyze8.setImageResource(R.drawable.fuwei);
            analyze9.setImageResource(R.drawable.liusha);
            analyzeFangwei.setText("房屋布局:" + cx);
            analyzeChaoxiang.setText("飞星:旺丁不旺财");
            analyzeDirectionTop.setText("向正南");
            analyzeDirectionLeft.setText("正东");
            analyzeDirectionRight.setText("正西");
            analyzeDirectionBottom.setText("坐正北");
        } else if ("正西".equals(fx)) {
            flag = 8;
            analyze1.setImageResource(R.drawable.huohai);
            analyze2.setImageResource(R.drawable.jueming);
            analyze3.setImageResource(R.drawable.wugui);
            analyze4.setImageResource(R.drawable.shengqi);
            analyze5.setImageResource(R.drawable.zaizhu);
            analyze6.setImageResource(R.drawable.tianyi);
            analyze7.setImageResource(R.drawable.yannian);
            analyze8.setImageResource(R.drawable.fuwei);
            analyze9.setImageResource(R.drawable.liusha);
            analyzeFangwei.setText("房屋布局:" + cx);
            analyzeChaoxiang.setText("飞星:旺丁不旺财");
            analyzeDirectionTop.setText("向正西");
            analyzeDirectionLeft.setText("正南");
            analyzeDirectionRight.setText("正北");
            analyzeDirectionBottom.setText("坐正东");
        }
    }

     public void onClick(View view) {
         int i = view.getId();
         if (i == R.id.analyze_back_ll) {
             finish();

         } else if (i == R.id.analyze_1) {
             if (flag == 1 || flag == 5) {
                 setJueMing();
             } else if (flag == 2) {
                 setWuGui();
             } else if (flag == 3) {
                 setLiuSha();
             } else if (flag == 4 || flag == 8) {
                 setHuoHai();
             } else if (flag == 6) {
                 setYanNian();
             } else {
                 setShengqi();
             }

         } else if (i == R.id.analyze_2) {
             if (flag == 1 || flag == 4) {
                 setShengqi();
             } else if (flag == 2 || flag == 3) {
                 setHuoHai();
             } else if (flag == 5 || flag == 7) {
                 setYanNian();
             } else if (flag == 6 || flag == 8) {
                 setJueMing();
             }

         } else if (i == R.id.analyze_3) {
             if (flag == 1 || flag == 5) {
                 setHuoHai();
             } else if (flag == 2 || flag == 7) {
                 setJueMing();
             } else if (flag == 3) {
                 setShengqi();
             } else if (flag == 4) {
                 setYanNian();
             } else if (flag == 6) {
                 setLiuSha();
             } else {
                 setWuGui();
             }

         } else if (i == R.id.analyze_4) {
             if (flag == 1) {
                 setYanNian();
             } else if (flag == 2 || flag == 7) {
                 setTianYi();
             } else if (flag == 3 || flag == 5) {
                 setWuGui();
             } else if (flag == 4) {
                 setJueMing();
             } else if (flag == 6) {
                 setHuoHai();
             } else {
                 setShengqi();
             }

         } else if (i == R.id.analyze_5) {
             setZhaiZhu();

         } else if (i == R.id.analyze_6) {
             if (flag == 1 || flag == 6) {
                 setWuGui();
             } else if (flag == 2) {
                 setYanNian();
             } else if (flag == 3) {
                 setJueMing();
             } else if (flag == 4 || flag == 8) {
                 setTianYi();
             } else if (flag == 5) {
                 setShengqi();
             } else {
                 setHuoHai();
             }

         } else if (i == R.id.analyze_7) {
             if (flag == 1 || flag == 3) {
                 setTianYi();
             } else if (flag == 2 || flag == 4 || flag == 5) {
                 setLiuSha();
             } else if (flag == 6) {
                 setShengqi();
             } else if (flag == 7) {
                 setWuGui();
             } else {
                 setYanNian();
             }

         } else if (i == R.id.analyze_8) {
             setFuWei();

         } else if (i == R.id.analyze_9) {
             if (flag == 1 || flag == 7 || flag == 8) {
                 setLiuSha();
             } else if (flag == 2) {
                 setShengqi();
             } else if (flag == 3) {
                 setYanNian();
             } else if (flag == 4) {
                 setWuGui();
             } else {
                 setYanNian();
             }

         }
    }

    private void setJueMing() {
        new ViewDialogRegister(AnalyzeActivity.this, R.style.MyDialog)
                .setBackGroundType(2)
                .setTitleText("绝命位")
                .setContentText("灭寿，损丁，破财，容易患病，用昱珀配九叶铃莲，封印厄运使得阴阳归位")
                .setScaleText("100%")
                .show();
    }

    private void setHuoHai() {
        new ViewDialogRegister(AnalyzeActivity.this, R.style.MyDialog)
                .setBackGroundType(2)
                .setTitleText("祸害位")
                .setContentText("疾病，散财，伤人，不宜坐卧室，情绪难控，财运难安")
                .setScaleText("25%")
                .show();
    }

    private void setFuWei() {
        new ViewDialogRegister(AnalyzeActivity.this, R.style.MyDialog)
                .setBackGroundType(1)
                .setTitleText("伏位")
                .setContentText("与世无争，大吉大利，有利健康")
                .setScaleText("25%")
                .show();
    }

    private void setLiuSha() {
        new ViewDialogRegister(AnalyzeActivity.this, R.style.MyDialog)
                .setBackGroundType(2)
                .setTitleText("六煞位")
                .setContentText("吵架，婚姻问题，步步桃花，对已婚人士不利")
                .setScaleText("50%")
                .show();
    }

    private void setShengqi() {
        new ViewDialogRegister(AnalyzeActivity.this, R.style.MyDialog)
                .setBackGroundType(1)
                .setTitleText("生气位")
                .setContentText("事业，学业，旺财，钱财来得快，且又持久")
                .setScaleText("100%")
                .show();
    }

    private void setTianYi() {
        new ViewDialogRegister(AnalyzeActivity.this, R.style.MyDialog)
                .setBackGroundType(1)
                .setTitleText("天医位")
                .setContentText("稳定成长之财运，陆续存有少量钱财")
                .setScaleText("50%")
                .show();
    }

    private void setWuGui() {
        new ViewDialogRegister(AnalyzeActivity.this, R.style.MyDialog)
                .setBackGroundType(2)
                .setTitleText("五鬼位")
                .setContentText("易争吵，小人，破财，容易患病，得八尺神照镜，放在卧室内可以彻底消除化解保平安")
                .setScaleText("75%")
                .show();
    }

    private void setYanNian() {
        new ViewDialogRegister(AnalyzeActivity.this, R.style.MyDialog)
                .setBackGroundType(1)
                .setTitleText("延年位")
                .setContentText("姻缘，旺财，长寿，钱财来得快，亦可积聚")
                .setScaleText("70%")
                .show();
    }

    private void setZhaiZhu() {
        new ViewDialogRegister(AnalyzeActivity.this, R.style.MyDialog)
                .setBackGroundType(3)
                .setTitleText("宅主位")
                .setContentText("住宅中央，您现在所站的位置")
//                .setScaleText("70%")
                .show();
    }
}

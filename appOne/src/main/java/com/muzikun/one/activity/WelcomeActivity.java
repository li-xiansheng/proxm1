package com.muzikun.one.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.muzikun.one.R;

/**
 * Created by leeking on 16/6/14.
 */
public class WelcomeActivity extends Activity {
    private ImageView centerImageView = null;
    private ImageView topImageView = null;
    private ImageView logoImageView = null;
    private ImageView pagerImageView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//去掉信息栏
        setContentView(R.layout.activity_welcome);

//        initView();


        new Thread(){
            @Override
            public void run() {
                try {
                    sleep(2000);
                } catch (InterruptedException e) {

                }
                Intent it = new Intent(WelcomeActivity.this,MainActivity.class);
                startActivity(it);
                overridePendingTransition(R.anim.in_from_left, R.anim.out_from_right);
                finish();
            }
        }.start();


    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {

            return false;
        }
        return super.onKeyDown(keyCode, event);
    }
//    private void initView() {
//        centerImageView = (ImageView) findViewById(R.id.activity_center_image);
//        topImageView = (ImageView) findViewById(R.id.activity_hello_top_image);
//        pagerImageView = (ImageView) findViewById(R.id.welcome_activity_hello_pager_image);
//        logoImageView = (ImageView) findViewById(R.id.activity_bottom_logo);
//
//        Animation animation = AnimationUtils.loadAnimation(this,R.anim.welcome_run);
//        Animation animation2 = AnimationUtils.loadAnimation(this,R.anim.welcome_top_image);
//        Animation animation3 = AnimationUtils.loadAnimation(this,R.anim.welcome_logo);
//        centerImageView.setAnimation(animation);
//        topImageView.setAnimation(animation2);
//        logoImageView.setAnimation(animation3);
//
//        animation3.setAnimationListener(new Animation.AnimationListener() {
//            @Override
//            public void onAnimationStart(Animation animation) {
//
//            }
//
//            @Override
//            public void onAnimationEnd(Animation animation) {
//                new GetImage().execute();
//                new Thread(){
//                    @Override
//                    public void run() {
//                        try {
//                            sleep(1200);
//                        } catch (InterruptedException e) {
//
//                        }
//                        Intent it = new Intent(WelcomeActivity.this,MainActivity.class);
//                        startActivity(it);
//                        finish();
//                    }
//                }.start();
//            }
//
//            @Override
//            public void onAnimationRepeat(Animation animation) {
//
//            }
//        });
//    }
//
//
//    class GetImage extends AsyncTask<String,String,String>{
//
//        @Override
//        protected String doInBackground(String... params) {
//            String result = Net.getApiJson(A.GET_PAPE_IMAGE);
//            return result;
//        }
//
//        @Override
//        protected void onPostExecute(String s) {
//            Log.i("imageurl",s);
//            if(s != "error"){
//                try{
//                    String result = new String(s);
//                    WelcomeActivity.this.loadImage(result);
//                }catch (Exception e){
//                    Log.i("imageurl","noload");
//                    return;
//                }
//            }else{
//                Log.i("imageurl","noload2");
//                return;
//            }
//
//        }
//    }
//
//
//    public void loadImage(String s){
//        Glide.with(WelcomeActivity.this)
//                .load("http://"+s)
//                .crossFade()
//                .into(pagerImageView);
//
//    }
}

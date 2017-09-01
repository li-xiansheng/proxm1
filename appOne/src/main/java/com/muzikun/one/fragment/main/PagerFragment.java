package com.muzikun.one.fragment.main;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.muzikun.one.R;
import com.muzikun.one.activity.AnalyzeActivity;
import com.muzikun.one.activity.CourseActivity;
import com.muzikun.one.util.EventUtil;
import com.muzikun.one.util.JumpUtil;

import static android.content.Context.SENSOR_SERVICE;


/**
 * Created by leeking on 16/6/15.
 */
public class PagerFragment extends Fragment implements SensorEventListener {


    private String TAG = "PagerFragment";

    private TextView fangwei;
    private TextView chaoxiang;
    private ImageView luopan;
    private ImageView fenxi;
    private LinearLayout jiaocheng;
    private ImageView fugaiSure;
    private FrameLayout fugaiView;


    private View mainView = null;

    private float currentDegree = 0f;

    String fangXiang = "";
    String chaoXiang = "";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mainView = inflater.inflate(R.layout.fragment_page, container, false);

        initView(mainView);
        initEvent();
        return mainView;
    }


    private void initView(View mainView) {

        fangwei = (TextView) mainView.findViewById(R.id.fangwei);
        chaoxiang = (TextView) mainView.findViewById(R.id.chaoxiang);
        luopan = (ImageView) mainView.findViewById(R.id.luopan);
        fenxi = (ImageView) mainView.findViewById(R.id.fenxi);
        jiaocheng = (LinearLayout) mainView.findViewById(R.id.jiaocheng);
        fugaiSure = (ImageView) mainView.findViewById(R.id.fugai_sure);
        fugaiView = (FrameLayout) mainView.findViewById(R.id.fugai_view);


        // 传感器管理器
        SensorManager sm = (SensorManager) getActivity().getSystemService(SENSOR_SERVICE);
        // 注册传感器(Sensor.TYPE_ORIENTATION(方向传感器);SENSOR_DELAY_FASTEST(0毫秒延迟);
        // SENSOR_DELAY_GAME(20,000毫秒延迟)、SENSOR_DELAY_UI(60,000毫秒延迟))
        sm.registerListener(this,
                sm.getDefaultSensor(Sensor.TYPE_ORIENTATION),
                SensorManager.SENSOR_DELAY_FASTEST);

        EventUtil.setTouchState(fenxi);
        EventUtil.setTouchState(jiaocheng);
    }

    private void initEvent() {
        fenxi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AnalyzeActivity.class);
                intent.putExtra("fangxiang", fangXiang);
                intent.putExtra("chaoxiang", chaoXiang);
                startActivity(intent);
//                JumpUtil.jump(getActivity(), AnalyzeActivity.class);
            }
        });

        jiaocheng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JumpUtil.jump(getActivity(), CourseActivity.class);
            }
        });

        fugaiSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fugaiView.setVisibility(View.GONE);
            }
        });
    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ORIENTATION) {
            float degree = event.values[0];

            /*
            RotateAnimation类：旋转变化动画类

            参数说明:

            fromDegrees：旋转的开始角度。
            toDegrees：旋转的结束角度。
            pivotXType：X轴的伸缩模式，可以取值为ABSOLUTE、RELATIVE_TO_SELF、RELATIVE_TO_PARENT。
            pivotXValue：X坐标的伸缩值。
            pivotYType：Y轴的伸缩模式，可以取值为ABSOLUTE、RELATIVE_TO_SELF、RELATIVE_TO_PARENT。
            pivotYValue：Y坐标的伸缩值
            */
            RotateAnimation ra = new RotateAnimation(currentDegree, -degree,
                    Animation.RELATIVE_TO_SELF, 0.5f,
                    Animation.RELATIVE_TO_SELF, 0.5f);
            //旋转过程持续时间
            ra.setDuration(200);
            //罗盘图片使用旋转动画
            luopan.startAnimation(ra);

            Log.i(TAG, "degree" + degree);
//            fangwei.setText();
            showFangWei(degree);

            currentDegree = -degree;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    private void showFangWei(double degree) {
        if (degree >= 0 && degree <= 22) {
            fangXiang = "正北";
            if (degree >= 0 && degree <= 7) {
                chaoXiang = "坐午向子";
            } else if (degree > 7) {
                chaoXiang = "坐丁向癸";
            }
        } else if (degree > 22 && degree <= 67) {
            fangXiang = "东北";
            if (degree > 22 && degree <= 37) {
                chaoXiang = "坐未向丑";
            } else if (degree > 37 && degree <= 52) {
                chaoXiang = "坐坤向艮";
            } else {
                chaoXiang = "坐申向寅";
            }
        } else if (degree > 67 && degree <= 112) {
            fangXiang = "正东";
            if (degree > 67 && degree <= 82) {
                chaoXiang = "坐庚向甲";
            } else if (degree > 82 && degree <= 97) {
                chaoXiang = "坐酉向卯";
            } else {
                chaoXiang = "坐辛向乙";
            }
        } else if (degree > 112 && degree <= 157) {
            fangXiang = "东南";
            if (degree > 112 && degree <= 127) {
                chaoXiang = "坐戌向辰";
            } else if (degree > 127 && degree <= 142) {
                chaoXiang = "坐乾向巽";
            } else {
                chaoXiang = "坐亥向巳";
            }
        } else if (degree > 157 && degree <= 202) {
            fangXiang = "正南";
            if (degree > 157 && degree <= 172) {
                chaoXiang = "坐壬向丙";
            } else if (degree > 172 && degree <= 187) {
                chaoXiang = "坐子向午";
            } else {
                chaoXiang = "坐癸向丁";
            }
        } else if (degree > 202 && degree <= 247) {
            fangXiang = "西南";
            if (degree > 202 && degree <= 217) {
                chaoXiang = "坐丑向未";
            } else if (degree > 217 && degree <= 232) {
                chaoXiang = "坐艮向坤";
            } else {
                chaoXiang = "坐寅向申";
            }
        } else if (degree > 247 && degree <= 292) {
            fangXiang = "正西";
            if (degree > 247 && degree <= 262) {
                chaoXiang = "坐甲向庚";
            } else if (degree > 262 && degree <= 277) {
                chaoXiang = "坐卯向酉";
            } else {
                chaoXiang = "坐乙向辛";
            }
        } else if (degree > 292 && degree <= 337) {
            fangXiang = "西北";
            if (degree > 292 && degree <= 307) {
                chaoXiang = "坐辰向戌";
            } else if (degree > 307 && degree <= 322) {
                chaoXiang = "坐巽向乾";
            } else {
                chaoXiang = "坐巳向亥";
            }
        } else {
            fangXiang = "正北";
            if (degree > 337 && degree <= 352) {
                chaoXiang = "坐丙向壬";
            } else {
                chaoXiang = "坐午向子";
            }
        }
        fangwei.setText(fangXiang + ":" + EventUtil.FormatDouble(degree + 1));
        chaoxiang.setText(chaoXiang);
    }
}

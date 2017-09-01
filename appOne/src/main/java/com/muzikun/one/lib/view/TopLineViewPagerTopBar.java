package com.muzikun.one.lib.view;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.muzikun.one.data.bean.TopLineViewPagerTitleBean;

import java.util.List;


/**
 * Created by muzikun on 2016-04-26.
 */
public class TopLineViewPagerTopBar extends LinearLayout {
    private String defaultTextColor = "#ffc9c9";
    private int size = 0;
    private OnTopLineItemCLickListener onTopLineItemCLickListener = null;
    public int itemWidth = 0;


    public TopLineViewPagerTopBar(Context context) {
        super(context);
    }

    public TopLineViewPagerTopBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }



    public void setView(List<TopLineViewPagerTitleBean> titles){
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        for (int i = 0 ; i < titles.size() ; i ++){
            size++;
            TextView textView = new TextView(getContext());
            textView.setPadding(30,42,30,42);
            textView.setTextSize(16);
            textView.setGravity(Gravity.CENTER);
            textView.setTextColor(Color.parseColor(defaultTextColor));
            textView.setText(titles.get(i).getName());
            textView.setTag("text"+String.valueOf(i));
            textView.setOnClickListener(new NewNavBarOnclickListener(this,titles.get(i).getCid(),i,textView));
            textView.setLayoutParams(params);
            addView(textView);
        }

    }



    public int[]  selectView(int position){
        TextView textView;

        for(int i = 0  ; i < size ; i ++ ){
            textView = (TextView)findViewWithTag("text"+String.valueOf(i));
            textView.setTextColor(Color.parseColor(defaultTextColor));
        }

        textView = (TextView)findViewWithTag("text"+String.valueOf(position));
        textView.setTextColor(Color.parseColor("#FFFFFF"));
        int[] location = new int[2];
        textView.getLocationInWindow(location);
        float p = textView.getWidth();
        this.setItemWidth(p);
        location[1] = itemWidth;
        return location;
    }

    public OnTopLineItemCLickListener getOnTopLineItemCLickListener() {
        return onTopLineItemCLickListener;
    }

    public void setOnTopLineItemCLickListener(OnTopLineItemCLickListener onTopLineItemCLickListener) {
        this.onTopLineItemCLickListener = onTopLineItemCLickListener;
    }


    public float getItemWidth() {
        return itemWidth;
    }

    public void setItemWidth(float itemWidth) {
        if(this.itemWidth!=0){
            if(this.itemWidth > (int)itemWidth){
                this.itemWidth = (int)itemWidth;
            }
        }else{
            this.itemWidth = (int)itemWidth;
        }

    }


    public interface  OnTopLineItemCLickListener{
         void onItemCLick(View veiw, int itemId, int position);
    }

    public class NewNavBarOnclickListener implements OnClickListener {

        private int itemId = 0;
        private TopLineViewPagerTopBar topLineViewPagerTopBar = null;
        private View view = null;
        private int position = 0;

        public NewNavBarOnclickListener(TopLineViewPagerTopBar topLineViewPagerTopBar , int itemId , int position, View view){
            this.itemId = itemId;
            this.topLineViewPagerTopBar = topLineViewPagerTopBar;
            this.view =  view;
            this.position =  position;
        }
        @Override
        public void onClick(View view) {
            if(topLineViewPagerTopBar.getOnTopLineItemCLickListener()==null){
                return;
            }else{
                topLineViewPagerTopBar.getOnTopLineItemCLickListener().onItemCLick(this.view,itemId,position);
            }
        }

    }

}

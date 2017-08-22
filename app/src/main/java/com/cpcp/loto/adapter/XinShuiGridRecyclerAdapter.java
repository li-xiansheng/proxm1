package com.cpcp.loto.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.cpcp.loto.R;
import com.cpcp.loto.base.BaseRecycleViewAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 功能描述：心水-生肖号码适配器
 */

public class XinShuiGridRecyclerAdapter extends BaseRecycleViewAdapter {

    private int maxChecked=0;
    private List<Integer> mListPosition;

    public void setMaxChecked(int maxChecked) {
        this.maxChecked = maxChecked;
    }

    public XinShuiGridRecyclerAdapter(Context context, List<?> data) {
        super.BaseRecycleViewAdapter(context, data);
        mListPosition = new ArrayList<>();
    }

    @Override
    protected RecyclerView.ViewHolder baseCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.item_send_xinshui_grid, parent, false);
        ViewHolder ViewHolder = new ViewHolder(view);
        return ViewHolder;
    }

    @Override
    protected void bindView(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof ViewHolder) {
            ViewHolder viewHolder = (ViewHolder) holder;


            final AppCompatCheckBox checkbox = viewHolder.checkbox;


            final List<Map<String, Object>> list = (List<Map<String, Object>>) mListData;
            final Map<String, Object> map = (Map<String, Object>) mListData.get(position);
            String strName = (String) map.get("name");
            final boolean isChecked = (boolean) map.get("isChecked");
            checkbox.setText(strName);
            if (isChecked) {
//                checkbox.setBackgroundResource(R.color.red);
                checkbox.setChecked(true);
            } else {
//                checkbox.setBackgroundResource(R.color.grayBg);
                checkbox.setChecked(false);

            }

            checkbox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    if (isChecked) {
                        checkbox.setChecked(false);
                        map.put("isChecked", false);
                        mListPosition.remove(new Integer(position));
                    } else {
                        checkbox.setChecked(true);
                        map.put("isChecked", true);
                        mListPosition.add(position);
                    }

                    int index = 0;

                    for (Map<String, Object> map : list) {
                        boolean isChecked = (boolean) map.get("isChecked");
                        if (isChecked) {
                            index += 1;
                        }
                    }
                    if (index >maxChecked) {
                        Map<String, Object> map = (Map<String, Object>) mListData.get((int)mListPosition.get(0));
                        map.put("isChecked", false);
                        mListPosition.remove(0);

                    }
                    notifyDataSetChanged();
                }
            });
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.checkbox)
        AppCompatCheckBox checkbox;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}

package com.muzikun.one.fragment.news;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.muzikun.one.R;

/**
 * Created by likun on 16/7/22.
 * URL    : www.muzikun.com
 * E-MAIL : 522525970@qq.com
 */
public class SendCommentFragment extends Fragment{
    private View mainView = null;
    private TextView cancelView = null;
    private TextView sendView   = null;
    private EditText editText   = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mainView = inflater.inflate(R.layout.fragment_comment,container,false);
        initView();
        return mainView;
    }

    private void initView() {

        sendView    = (TextView) mainView.findViewById(R.id.fragment_send_comment);
        editText    = (EditText) mainView.findViewById(R.id.fragment_comment_edit);


    }


}

package com.muzikun.one.fragment.myhome;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.muzikun.one.R;
import com.muzikun.one.activity.RegisterActivity;
import com.muzikun.one.lib.util.Auth;
import com.muzikun.one.lib.util.Helper;

/**
 * Created by likun on 16/6/29.
 * URL    : www.muzikun.com
 * E-MAIL : 522525970@qq.com
 */
public class SetFragment extends Fragment implements View.OnClickListener {

    private View mainView = null;
    private Button logout = null;
    private TextView clearCacheView = null;
    private TextView resetPassword = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mainView = inflater.from(getActivity()).inflate(R.layout.fragment_set,container,false);
        initView();
        return this.mainView;
    }

    private void initView() {
        logout = (Button) mainView.findViewById(R.id.fragment_set_logout);
        clearCacheView = (TextView) mainView.findViewById(R.id.fragment_set_clear);
        resetPassword = (TextView) mainView.findViewById(R.id.fragment_set_changepass);

        logout.setOnClickListener(this);
        clearCacheView.setOnClickListener(this);
        resetPassword.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.fragment_set_logout) {
            logout();

        } else if (i == R.id.fragment_set_changepass) {
            register();

        } else if (i == R.id.fragment_set_clear) {
            clearCache();

        } else {
        }
    }

    private void logout() {
        Auth.logout(getActivity());
        Helper.T(getActivity(),"退出成功！");
        getActivity().finish();
    }

    private void register() {
        Intent it = new Intent(getActivity(),RegisterActivity.class);
        startActivity(it);
    }
    private void clearCache() {
        Helper.createToast(getActivity(),"清除成功！",2).show();
    }


}

package com.chen.schedule.myschedule.pager;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chen.schedule.myschedule.R;
import com.chen.schedule.myschedule.utils.BusProvider;
import com.chen.schedule.myschedule.utils.Events;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 2017年创建
 * @author 陈鑫
 */
public class AboutMePager extends BasePager implements View.OnClickListener {

    @Bind(R.id.about_bg)
    LinearLayout about_bg;
    @Bind(R.id.github)
    TextView github;
    @Bind(R.id.weibo)
    TextView weibo;

    public AboutMePager(Activity mActivity) {
        super(mActivity);
    }

    @Override
    public View initView() {
        View view = View.inflate(mActivity, R.layout.aboutme_pager, null);

        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void initData() {
        about_bg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BusProvider.getInstance().send(new Events.AgendaListViewTouchedEvent());
            }
        });

        github.setOnClickListener(this);
        weibo.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.github:
                Intent it1 = new Intent(Intent.ACTION_VIEW, Uri.parse(github.getText().toString()));
                it1.setClassName("com.android.browser", "com.android.browser.BrowserActivity");
                mActivity.startActivity(it1);
                break;
            case R.id.weibo:
                Intent it2 = new Intent(Intent.ACTION_VIEW, Uri.parse(weibo.getText().toString()));
                it2.setClassName("com.android.browser", "com.android.browser.BrowserActivity");
                mActivity.startActivity(it2);
                break;
        }
    }
}

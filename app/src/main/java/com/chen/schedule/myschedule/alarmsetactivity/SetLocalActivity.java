package com.chen.schedule.myschedule.alarmsetactivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

import com.chen.schedule.myschedule.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 2017年创建
 * 设置事件地点的默认值，不填则默认“无”
 * @author 陈鑫
 */
public class SetLocalActivity extends AppCompatActivity {

    @Bind(R.id.ed_local)
    EditText ed_local;
    @OnClick(R.id.tv_save) void saveAndClose(){
        /**
         * 设置local的值
         * 如果不填则显示无
         * 添加地址则获取地址的值
         */
        Intent intent=new Intent();
        if(ed_local.getText().toString().equals("")){
            intent.putExtra("local", "无");
            setResult(2, intent);
            finish();
        }else {
            intent.putExtra("local", ed_local.getText().toString());
            setResult(2, intent);
            finish();
        }

    }

    /**
     *返回按钮
     *
     */
    @OnClick(R.id.left_local_back) void finishClose(){
        finish();
    }

    /**
     *设置保存按钮的消息响应
     *
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_local);
        ButterKnife.bind(this);
    }
}

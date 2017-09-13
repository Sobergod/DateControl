package com.chen.schedule.myschedule.alarmremind;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * 2017年创建
 * @author 陈鑫
 */
public class AlarmServiceBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent serviceIntent = new Intent(context, AlarmService.class);
        context.startService(serviceIntent);
    }
}

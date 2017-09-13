package com.chen.schedule.myschedule.alarmremind;

import android.app.Activity;
import android.content.Intent;

/**
 * 2017年创建
 * @author 陈鑫
 */
public class SendAlarmBroadcast {

    public static void startAlarmService(Activity activity){
        Intent startAlarmServiceIntent = new Intent(activity,AlarmServiceBroadcastReceiver.class);
        activity.sendBroadcast(startAlarmServiceIntent,null);
    }
}

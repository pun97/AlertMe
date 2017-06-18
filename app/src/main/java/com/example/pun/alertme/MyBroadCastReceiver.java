package com.example.pun.alertme;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import static android.provider.Telephony.Sms.Intents.SMS_RECEIVED_ACTION;

/**
 * Created by Pun on 6/18/2017.
 */

public class MyBroadCastReceiver extends BroadcastReceiver {
    TextView textView;

    MyBroadCastReceiver() {
        textView = null;
    }

    MyBroadCastReceiver(TextView tv) {
        textView = tv;
    }

    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        Toast.makeText(context, "ACTION:" + action, Toast.LENGTH_LONG).show();
        if (textView != null)
            textView.setText(action);
        if (action.equals("android.provider.Telephony.SMS_RECEIVED")) {
            Bundle Data = intent.getExtras();
            String SMS_BUNDLE = "pdus";
            Object[] SMS = (Object[]) Data.get(SMS_BUNDLE);

            String sms = "";

            for (int i = 0; i < SMS.length; i++) {
                SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) SMS[i]);
                sms = sms + "FROM:" + smsMessage.getOriginatingAddress().toString() + "\n";
                sms = sms + smsMessage.getMessageBody().toString() + "\n";
            }
            if (textView != null)
                textView.setText(sms);
        }

    }
}

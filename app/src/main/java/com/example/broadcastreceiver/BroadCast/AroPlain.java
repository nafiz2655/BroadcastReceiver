package com.example.broadcastreceiver.BroadCast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.widget.TextView;

public class AroPlain extends BroadcastReceiver {

    private TextView textView;

    public AroPlain() {
    }

    public AroPlain(TextView textView) {
        this.textView = textView;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (isAirplaneModeOn(context)) {
                    textEditor("#> Airplane mode is on");

        } else {
            textEditor("#> Airplane mode is off");
        }

    }

    private boolean isAirplaneModeOn(Context context) {
        return Settings.Global.getInt(context.getContentResolver(), Settings.Global.AIRPLANE_MODE_ON, 0) != 0;
    }

    private void textEditor(final String text) {
        if (textView == null) {
            Log.e("CastumReciver", "TextView is not initialized.");
            return;
        }
        textView.append("\n");

        textView.post(new Runnable() {
            @Override
            public void run() {

                final int[] wordIndex = {0};
                Handler handler = new Handler();

                // Split the text into words
                final String[] words = text.split("");

                // Runnable to update the textView
                final Runnable addWordRunnable = new Runnable() {
                    @Override
                    public void run() {
                        if (wordIndex[0] < words.length) {
                            // Append the word to the textView
                            textView.append(words[wordIndex[0]] + "");
                            wordIndex[0]++;
                            // Post this runnable again with a delay
                            handler.postDelayed(this, 200); // 500ms delay between words
                        }
                    }
                };

                // Start the process
                handler.post(addWordRunnable);
            }
        });
    }
}

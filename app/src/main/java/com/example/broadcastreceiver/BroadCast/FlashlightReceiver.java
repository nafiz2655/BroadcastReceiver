package com.example.broadcastreceiver.BroadCast;

// FlashlightReceiver.java


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

public class FlashlightReceiver extends BroadcastReceiver {

    TextView textView;

    public FlashlightReceiver() {
    }

    public FlashlightReceiver(TextView textView) {
        this.textView = textView;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if ("com.example.broadcastreceiver.FLASHLIGHT_STATE_CHANGED".equals(intent.getAction())) {
            boolean isFlashlightOn = intent.getBooleanExtra("flashlight_state", false);
            if (isFlashlightOn) {
                textEditor("#> Flashlight is ON");
            } else {
                textEditor("#> Flashlight is OFF");
            }
        }
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

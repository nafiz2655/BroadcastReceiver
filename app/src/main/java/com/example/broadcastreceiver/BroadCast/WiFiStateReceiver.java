package com.example.broadcastreceiver.BroadCast;



import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

public class WiFiStateReceiver extends BroadcastReceiver {

    TextView textView;

    public WiFiStateReceiver() {
    }

    public WiFiStateReceiver(TextView textView) {
        this.textView = textView;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (WifiManager.WIFI_STATE_CHANGED_ACTION.equals(intent.getAction())) {
            int wifiState = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE, WifiManager.WIFI_STATE_UNKNOWN);

            switch (wifiState) {
                case WifiManager.WIFI_STATE_ENABLED:
                    textEditor("#> Wi-Fi is ON");
                    Log.d("WiFiStateReceiver", "Wi-Fi is ON");
                    break;

                case WifiManager.WIFI_STATE_DISABLED:
                    textEditor("#> Wi-Fi is OFF");
                    Log.d("WiFiStateReceiver", "Wi-Fi is OFF");
                    break;

                default:
                    Log.d("WiFiStateReceiver", "Wi-Fi state changed");
                    break;
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

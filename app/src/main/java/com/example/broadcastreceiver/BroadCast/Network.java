package com.example.broadcastreceiver.BroadCast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.os.Build;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

public class Network extends BroadcastReceiver {

    TextView textView;

    public Network() {
    }

    public Network(TextView textView) {
        this.textView = textView;
    }

    @Override
    public void onReceive(final Context context, final Intent intent) {
        if (isNetworkAvailable(context)) {
        textEditor("#> Mobile Data ON ");
        } else {
            textEditor("#> Mobile Data OFF");
        }
    }

    private boolean isNetworkAvailable(Context context) {
        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connMgr != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                // For API 23 and above
                android.net.Network network = connMgr.getActiveNetwork();
                if (network != null) {
                    NetworkCapabilities capabilities = connMgr.getNetworkCapabilities(network);
                    return capabilities != null &&
                            (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR));
                }
            } else {
                // For older versions (below API 23)
                android.net.NetworkInfo mobile = connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);


                if (mobile != null && mobile.isConnected()) {
                    return true; // Mobile data is connected
                }
            }
        }
        return false;
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

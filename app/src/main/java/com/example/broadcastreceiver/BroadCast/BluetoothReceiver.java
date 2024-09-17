package com.example.broadcastreceiver.BroadCast;

import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

public class BluetoothReceiver extends BroadcastReceiver {

    TextView textView;
    Context context;

    public BluetoothReceiver() {
    }

    public BluetoothReceiver(TextView textView, Context context) {
        this.textView = textView;
        this.context = context;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (isBluetoothStateChanged(intent)) {
        }
    }

    private boolean isBluetoothStateChanged(Intent intent) {
        String action = intent.getAction();
        if (BluetoothAdapter.ACTION_STATE_CHANGED.equals(action)) {
            int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, BluetoothAdapter.ERROR);
            switch (state) {
                case BluetoothAdapter.STATE_OFF:
                    textEditor("#> Bluetooth OFF Successfully");
                    return true;
                case BluetoothAdapter.STATE_TURNING_OFF:
                    // Handle Bluetooth turning off
                    return true;
                case BluetoothAdapter.STATE_ON:
                    textEditor("#> Bluetooth ON Successfully");
                    return true;
                case BluetoothAdapter.STATE_TURNING_ON:
                    // Handle Bluetooth turning on
                    return true;
                default:
                    return false;
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

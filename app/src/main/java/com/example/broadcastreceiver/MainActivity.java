package com.example.broadcastreceiver;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.broadcastreceiver.BroadCast.AroPlain;
import com.example.broadcastreceiver.BroadCast.BluetoothReceiver;
import com.example.broadcastreceiver.BroadCast.FlashlightReceiver;
import com.example.broadcastreceiver.BroadCast.Network;
import com.example.broadcastreceiver.BroadCast.WiFiStateReceiver;

public class MainActivity extends AppCompatActivity {



    AroPlain aroPlain;
    WiFiStateReceiver wiFi;
    Network network;
    BluetoothReceiver bluetoothReceiver;
    FlashlightReceiver flashlightReceiver;




    TextView textView,textView2,textView3,textView4;
    ImageView restart;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        textView = findViewById(R.id.textView);
        textView2 = findViewById(R.id.textView2);
        textView3 = findViewById(R.id.textView3);
        textView4 = findViewById(R.id.textView4);
        restart = findViewById(R.id.restart);


        aroPlain = new AroPlain(textView3);
        wiFi = new WiFiStateReceiver(textView);
        network = new Network(textView2);
        bluetoothReceiver = new BluetoothReceiver(textView4,this);
        flashlightReceiver = new FlashlightReceiver(textView2);



        IntentFilter airplaneModeFilter = new IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED);
        registerReceiver(aroPlain, airplaneModeFilter);

        IntentFilter wifiStateFilter = new IntentFilter(WifiManager.WIFI_STATE_CHANGED_ACTION);
        registerReceiver(wiFi, wifiStateFilter);

        IntentFilter networkIntentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(network, networkIntentFilter);

        IntentFilter bluhooth = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
        registerReceiver(bluetoothReceiver, bluhooth);


        IntentFilter flashlight = new IntentFilter("com.example.broadcastreceiver.FLASHLIGHT_STATE_CHANGED");
        registerReceiver(flashlightReceiver, flashlight);





        restart.setOnClickListener( view -> {
            textView.setText("");
            textView2.setText("");
            textView3.setText("");
            textView4.setText("");
        });


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(aroPlain);

    }
}
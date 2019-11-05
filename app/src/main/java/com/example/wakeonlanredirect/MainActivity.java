package com.example.wakeonlanredirect;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Switch;
import android.widget.CompoundButton;
import android.widget.Button;
import android.widget.EditText;
import android.view.View;
import android.os.StrictMode;
import android.content.Intent;

public class MainActivity extends AppCompatActivity{
static int port = 6666;
static String address = "255.255.255.255";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectNetwork().permitNetwork().penaltyLog().build());
        Switch switch1 = findViewById(R.id.switch1);
        Button button1 = findViewById(R.id.button1);
        Button button2 = findViewById(R.id.button2);

        button1.setOnClickListener(new View.OnClickListener()
        {
            EditText text = findViewById(R.id.editText1);
            @Override
            public void onClick(View v)
            {
                port = Integer.parseInt(text.getText().toString());
            }
        });

        button2.setOnClickListener(new View.OnClickListener()
        {
            EditText text = findViewById(R.id.editText2);
            @Override
            public void onClick(View v)
            {
                address = text.getText().toString();
            }
        });

        switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    startService(new Intent(MainActivity.this, PacketRedirectService.class));
                } else {
                    stopService(new Intent(MainActivity.this, PacketRedirectService.class));
                }

            }
        });
    }
}
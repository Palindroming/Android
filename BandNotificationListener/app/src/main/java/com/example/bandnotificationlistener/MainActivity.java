package com.example.bandnotificationlistener;

import android.content.Intent;

import android.os.Bundle;

import android.provider.Settings;

import android.view.View;

import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        Button button = findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {

                Intent intent = new Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS);

                startActivity(intent);

            }

        });

    }

}
package com.example.mrehman.vieweditandroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button mlswipeButton;
    private Button mrswipeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mlswipeButton = (Button) findViewById(R.id.lswipe);
        mrswipeButton = (Button) findViewById(R.id.rswipe);

        Intent i = new Intent(this, RegistrationService.class);
        startService(i);
    }
}

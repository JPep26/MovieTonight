package com.example.movietonight;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    private ImageView img_backmove;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        img_backmove = findViewById(R.id.img_backmove);

        img_backmove.setOnClickListener(view ->
                startActivity(new Intent(LoginActivity.this, StartActivity.class)));
    }

}
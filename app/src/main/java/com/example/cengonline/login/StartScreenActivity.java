package com.example.cengonline.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.cengonline.MainActivity;
import com.example.cengonline.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class StartScreenActivity extends AppCompatActivity {

    Button login,register;
    FirebaseUser currentUser;

    //Şimdilik login üzerinde çalıştığımızdan kapattık. Sonra açacağız.
    @Override
    protected void onStart() {
        super.onStart();
        currentUser = FirebaseAuth.getInstance().getCurrentUser();

        if(currentUser!=null)
        {
            Intent intent = new Intent(StartScreenActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_screen);

        login = findViewById(R.id.btn_login);
        register = findViewById(R.id.btn_register);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentLogin = new Intent(StartScreenActivity.this, LoginActivity.class);
                startActivity(intentLogin);
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentRegister = new Intent(StartScreenActivity.this, RegisterActivity.class);
                startActivity(intentRegister);
            }
        });

    }
}

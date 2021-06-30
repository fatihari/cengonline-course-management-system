package com.example.cengonline;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.cengonline.profile.LogoutActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ProfileActivity extends AppCompatActivity {

    TextView show_profile, logout;
    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Remove title bar
        if(getSupportActionBar()!=null)
            getSupportActionBar().hide();
        setContentView(R.layout.activity_profile);

        bottomNavigationView = findViewById(R.id.main_bottom_nav);
        //Course Activity page select
        bottomNavigationView.setSelectedItemId(R.id.nav_profile);
        //Switch to other pages
        switchPage();

        show_profile = findViewById(R.id.show_profile);
        logout = findViewById(R.id.logout);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            logout();
            }
        });

    }

    private void switchPage(){

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){

                    case R.id.nav_profile:
                        return true;


                    case R.id.nav_assignments:
                        Intent intent = new Intent(getApplicationContext(),AssignmentsActivity.class);
                        startActivity(intent);
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.nav_messages:
                        Intent intentMessage = new Intent(getApplicationContext(),MessagesActivity.class);
                        startActivity(intentMessage);
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.nav_announcements:
                        Intent intentAnnounce = new Intent(getApplicationContext(),AnnouncementsActivity.class);
                        startActivity(intentAnnounce);
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.nav_courses:
                        Intent intentProfile = new Intent(getApplicationContext(),MainActivity.class);
                        startActivity(intentProfile);
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });
    }
    private void logout(){
        Intent logoutIntent = new Intent(ProfileActivity.this, LogoutActivity.class);
        startActivity(logoutIntent);

    }
}

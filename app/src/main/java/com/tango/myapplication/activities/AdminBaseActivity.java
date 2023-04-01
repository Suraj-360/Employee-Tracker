package com.tango.myapplication.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.view.GravityCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.tango.myapplication.R;
import com.tango.myapplication.databinding.ActivityAdminBaseBinding;

import java.util.Objects;

public class AdminBaseActivity extends AppCompatActivity {

    ActivityAdminBaseBinding activityAdminBaseBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityAdminBaseBinding = ActivityAdminBaseBinding.inflate(getLayoutInflater());
        setContentView(activityAdminBaseBinding.getRoot());
        Objects.requireNonNull(Objects.requireNonNull(Objects.requireNonNull(getSupportActionBar()))).setBackgroundDrawable(AppCompatResources.getDrawable(this, R.color.pink));

        activityAdminBaseBinding.createUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent createUser = new Intent(AdminBaseActivity.this,CreateUserActivity.class);
                startActivity(createUser);
            }
        });

        activityAdminBaseBinding.usersTrack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent createUser = new Intent(AdminBaseActivity.this,UsersTrackActivity.class);
                startActivity(createUser);
            }
        });

        activityAdminBaseBinding.logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent resultIntent = new Intent(AdminBaseActivity.this, OptionActivity.class);
                startActivity(resultIntent);
                finish();
            }
        });
    }
}
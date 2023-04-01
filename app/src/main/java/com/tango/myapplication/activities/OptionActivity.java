package com.tango.myapplication.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.tango.myapplication.R;
import com.tango.myapplication.databinding.ActivityOptionBinding;

import java.util.Objects;

public class OptionActivity extends AppCompatActivity
{
    ActivityOptionBinding activityOptionBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        activityOptionBinding = ActivityOptionBinding.inflate(getLayoutInflater());
        setContentView(activityOptionBinding.getRoot());
        Objects.requireNonNull(Objects.requireNonNull(Objects.requireNonNull(getSupportActionBar()))).setBackgroundDrawable(AppCompatResources.getDrawable(this, R.color.pink));
        activityOptionBinding.user.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                Intent userLogin = new Intent(OptionActivity.this,UserLoginActivity.class);
                startActivity(userLogin);

            }
        });

        activityOptionBinding.admin.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                Intent adminLogin = new Intent(OptionActivity.this, AdminLoginActivity.class);
                startActivity(adminLogin);
            }
        });
    }
}
package com.tango.myapplication.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;

import android.os.Bundle;

import com.tango.myapplication.R;
import com.tango.myapplication.databinding.ActivityUsersDetailsBinding;

import java.util.Objects;

public class UsersDetailsActivity extends AppCompatActivity
{
    ActivityUsersDetailsBinding activityUsersDetailsBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityUsersDetailsBinding = ActivityUsersDetailsBinding.inflate(getLayoutInflater());
        setContentView(activityUsersDetailsBinding.getRoot());
        Objects.requireNonNull(Objects.requireNonNull(Objects.requireNonNull(getSupportActionBar()))).setBackgroundDrawable(AppCompatResources.getDrawable(this, R.color.pink));
    }
}

package com.tango.myapplication.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tango.myapplication.R;
import com.tango.myapplication.adapters.ListAdapter;
import com.tango.myapplication.databinding.ActivityUsersTrackBinding;
import com.tango.myapplication.models.UserModel;

import java.util.ArrayList;
import java.util.Objects;

public class UsersTrackActivity extends AppCompatActivity {

    ActivityUsersTrackBinding activityUsersTrackBinding;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://pro-e9573-default-rtdb.firebaseio.com/");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityUsersTrackBinding = ActivityUsersTrackBinding.inflate(getLayoutInflater());
        setContentView(activityUsersTrackBinding.getRoot());
        Objects.requireNonNull(Objects.requireNonNull(Objects.requireNonNull(getSupportActionBar()))).setBackgroundDrawable(AppCompatResources.getDrawable(this, R.color.pink));
        ArrayList<UserModel> arrayList = new ArrayList<>();
        databaseReference.child("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot: snapshot.getChildren())
                {
                    Log.d("Data",dataSnapshot.getChildren().toString());
                    Log.d("username",dataSnapshot.child("username").getValue(String.class));
                    Log.d("username",dataSnapshot.child("location").getValue(String.class));
                    Log.d("username",dataSnapshot.child("email").getValue(String.class));
                    Log.d("username",dataSnapshot.child("email").getValue(String.class));
                    Toast.makeText(UsersTrackActivity.this, "done ", Toast.LENGTH_SHORT).show();
                    UserModel userModel = new UserModel(dataSnapshot.child("empID").getValue(String.class),dataSnapshot.child("username").getValue(String.class),dataSnapshot.child("location").getValue(String.class),dataSnapshot.child("email").getValue(String.class),dataSnapshot.child("phone").getValue(String.class));
                    Log.d("user",userModel.getUsername()+userModel.getEmail()+userModel.getPhone()+userModel.getLocation()+userModel.getEmpID());
                    arrayList.add(userModel);
                }
                ListAdapter listAdapter = new ListAdapter(arrayList,UsersTrackActivity.this);
                activityUsersTrackBinding.listView.setAdapter(listAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {

            }
        });
    }
}
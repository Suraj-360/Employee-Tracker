package com.tango.myapplication.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tango.myapplication.R;
import com.tango.myapplication.databinding.ActivityUserBaseBinding;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class UserBaseActivity extends AppCompatActivity
{
    ActivityUserBaseBinding activityUserBaseBinding;
    FusedLocationProviderClient fusedLocationProviderClient;
    String userId;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://pro-e9573-default-rtdb.firebaseio.com/");
    private final static int REQUEST_CODE = 100;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        activityUserBaseBinding = ActivityUserBaseBinding.inflate(getLayoutInflater());
        setContentView(activityUserBaseBinding.getRoot());
        Objects.requireNonNull(Objects.requireNonNull(Objects.requireNonNull(getSupportActionBar()))).setBackgroundDrawable(AppCompatResources.getDrawable(this, R.color.pink));

        userId = getIntent().getStringExtra("userID");
        databaseReference.child("Users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                String username = snapshot.child(userId).child("username").getValue(String.class);
                String phone = snapshot.child(userId).child("phone").getValue(String.class);
                String email = snapshot.child(userId).child("email").getValue(String.class);
                String empId = snapshot.child(userId).child("empID").getValue(String.class);
                activityUserBaseBinding.empID.setText(MessageFormat.format("Employee ID :- {0}", empId));
                activityUserBaseBinding.empName.setText(MessageFormat.format("Employee Name :- {0}", username));
                activityUserBaseBinding.empEmail.setText(MessageFormat.format("Employee Email :- {0}", email));
                activityUserBaseBinding.empPhone.setText(MessageFormat.format("Employee Phone:- {0}", phone));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        activityUserBaseBinding.logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent resultIntent = new Intent(UserBaseActivity.this, OptionActivity.class);
                startActivity(resultIntent);
                finish();
            }
        });
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        if (ContextCompat.checkSelfPermission(UserBaseActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            if (ActivityCompat.shouldShowRequestPermissionRationale(UserBaseActivity.this,
                    Manifest.permission.ACCESS_FINE_LOCATION)){
                ActivityCompat.requestPermissions(UserBaseActivity.this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }else{
                ActivityCompat.requestPermissions(UserBaseActivity.this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }
        }

        getLastLocation();
    }

    private void getLastLocation()
    {
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED)
        {
            fusedLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if(location!=null)
                    {
                        Geocoder geocoder = new Geocoder(UserBaseActivity.this, Locale.getDefault());
                        try {
                            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
                            databaseReference.child("Users").child(userId).child("location").setValue(addresses.get(0).getAddressLine(0));

                        } catch (IOException e)
                        {
                            e.printStackTrace();
                        }
                    }
                }
            });
        }
        else
        {
            askPermission();
        }
    }

    private void askPermission()
    {
        ActivityCompat.requestPermissions(UserBaseActivity.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if(requestCode==REQUEST_CODE)
        {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                getLastLocation();
            }
            else
            {
                Toast.makeText(UserBaseActivity.this,"Required Permission",Toast.LENGTH_SHORT).show();
                databaseReference.child("Users").child(userId).child("location").setValue("");
                databaseReference.child("Users").child(userId).child("status").setValue("InActive");
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

}
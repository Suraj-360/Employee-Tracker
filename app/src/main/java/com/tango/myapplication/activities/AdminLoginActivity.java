package com.tango.myapplication.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tango.myapplication.R;
import com.tango.myapplication.databinding.ActivityAdminLoginBinding;
import java.util.Objects;

public class AdminLoginActivity extends AppCompatActivity
{
    ActivityAdminLoginBinding activityAdminLoginBinding;
    private String user;
    private String password;
    DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        activityAdminLoginBinding = ActivityAdminLoginBinding.inflate(getLayoutInflater());
        setContentView(activityAdminLoginBinding.getRoot());
        Objects.requireNonNull(Objects.requireNonNull(Objects.requireNonNull(getSupportActionBar()))).setBackgroundDrawable(AppCompatResources.getDrawable(this, R.color.pink));
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://pro-e9573-default-rtdb.firebaseio.com/");

        if (firebaseAuth.getCurrentUser() != null)
        {
            Intent dashBoardIntent = new Intent(AdminLoginActivity.this, AdminBaseActivity.class);
            startActivity(dashBoardIntent);
            finish();
        }

        activityAdminLoginBinding.loginBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                user = activityAdminLoginBinding.username.getText().toString();
                password = activityAdminLoginBinding.password.getText().toString();
                firebaseAuth.signInWithEmailAndPassword(user,password).addOnCompleteListener(new OnCompleteListener<AuthResult>()
                {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        if(task.isSuccessful())
                        {
                            databaseReference.child("Admin").addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    String userID = FirebaseAuth.getInstance().getUid();
                                    assert userID != null;
                                    if(snapshot.hasChild(userID))
                                    {
                                        final String dPass = Objects.requireNonNull(snapshot.child(userID).child("password").getValue()).toString();
                                        if(Objects.equals(password, dPass))
                                        {
                                            Intent adminActivityIntent = new Intent(AdminLoginActivity.this,AdminBaseActivity.class);
                                            startActivity(adminActivityIntent);
                                            finish();
                                            Toast.makeText(getApplicationContext(),"Successfully Login",Toast.LENGTH_SHORT).show();
                                        }
                                        else
                                        {
                                            Toast.makeText(getApplicationContext(),"Password Wrong",Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                    else
                                    {
                                        Toast.makeText(getApplicationContext(),"Phone number or username not exist in database",Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        }
                        else
                        {
                            Toast.makeText(AdminLoginActivity.this,task.getException() + "",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}
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
import com.tango.myapplication.databinding.ActivityUserLoginBinding;
import java.util.Objects;

public class UserLoginActivity extends AppCompatActivity
{
    ActivityUserLoginBinding activityUserLoginBinding;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://pro-e9573-default-rtdb.firebaseio.com/").child("");
    private String username;
    private String password;
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        activityUserLoginBinding = ActivityUserLoginBinding.inflate(getLayoutInflater());
        setContentView(activityUserLoginBinding.getRoot());
        Objects.requireNonNull(Objects.requireNonNull(Objects.requireNonNull(getSupportActionBar()))).setBackgroundDrawable(AppCompatResources.getDrawable(this, R.color.pink));
        firebaseAuth = FirebaseAuth.getInstance();

        if (firebaseAuth.getCurrentUser() != null)
        {
            Intent dashBoardIntent = new Intent(UserLoginActivity.this, UserBaseActivity.class);
            dashBoardIntent.putExtra("userID",firebaseAuth.getUid());
            startActivity(dashBoardIntent);
            finish();
        }

        activityUserLoginBinding.loginBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                username = activityUserLoginBinding.username.getText().toString();
                password = activityUserLoginBinding.password.getText().toString();
                if(!Objects.equals(username, "") && !password.equals(""))
                {
                    firebaseAuth.signInWithEmailAndPassword(username, password).addOnCompleteListener(new OnCompleteListener<AuthResult>()
                    {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful())
                            {
                                databaseReference.child("Users").addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        String userID = FirebaseAuth.getInstance().getUid();
                                        assert userID != null;
                                        if(snapshot.hasChild(userID))
                                        {
                                            final String dPass = Objects.requireNonNull(snapshot.child(userID).child("password").getValue()).toString();
                                            if(Objects.equals(password, dPass))
                                            {
                                                Intent userDashBoard = new Intent(UserLoginActivity.this, UserBaseActivity.class);
                                                userDashBoard.putExtra("userID",userID);
                                                startActivity(userDashBoard);
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
                                Toast.makeText(UserLoginActivity.this, task.getException() + " :-(", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Please enter valid data to login",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
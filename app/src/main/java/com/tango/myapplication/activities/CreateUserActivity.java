package com.tango.myapplication.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;

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
import com.tango.myapplication.databinding.ActivityCreateUserBinding;
import com.tango.myapplication.models.UserModel;
import java.util.Objects;

public class CreateUserActivity extends AppCompatActivity {

    DatabaseReference databaseReference;
    ActivityCreateUserBinding activityCreateUserBinding;
    private String username;
    private String password;
    private String address;
    private String phone;
    private String status;
    private String email;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        activityCreateUserBinding = ActivityCreateUserBinding.inflate(getLayoutInflater());
        setContentView(activityCreateUserBinding.getRoot());
        Objects.requireNonNull(Objects.requireNonNull(Objects.requireNonNull(getSupportActionBar()))).setBackgroundDrawable(AppCompatResources.getDrawable(this, R.color.pink));
        databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://pro-e9573-default-rtdb.firebaseio.com/");
        firebaseAuth = FirebaseAuth.getInstance();
        activityCreateUserBinding.createUserBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                // user details
                username = activityCreateUserBinding.username.getText().toString();
                password = activityCreateUserBinding.password.getText().toString();
                address = activityCreateUserBinding.address.getText().toString();
                status = activityCreateUserBinding.status.getText().toString();
                phone = activityCreateUserBinding.phone.getText().toString();
                email = activityCreateUserBinding.email.getText().toString();
                if(!Objects.equals(username, "") && !Objects.equals(password, "") && !Objects.equals(address, "") && !Objects.equals(status, "") && !Objects.equals(phone, "") && !email.equals(""))
                {
                    UserModel userModel = new UserModel(username, password, status, email, phone, address);
                    firebaseAuth.createUserWithEmailAndPassword(userModel.getEmail(), userModel.getPassword()).addOnCompleteListener(new OnCompleteListener<AuthResult>()
                    {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful())
                            {
                                databaseReference.child("Users").addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        String userId = Objects.requireNonNull(task.getResult().getUser()).getUid();
                                        if(snapshot.hasChild(userId))
                                        {
                                            Toast.makeText(getApplicationContext(),"Email is already registered.", Toast.LENGTH_SHORT).show();
                                        }
                                        else
                                        {
                                            databaseReference.child("Users").child(userId).child("username").setValue(userModel.getUsername());
                                            databaseReference.child("Users").child(userId).child("password").setValue(userModel.getPassword());
                                            databaseReference.child("Users").child(userId).child("address").setValue(userModel.getAddress());
                                            databaseReference.child("Users").child(userId).child("phone").setValue(userModel.getPhone());
                                            databaseReference.child("Users").child(userId).child("email").setValue(userModel.getEmail());
                                            databaseReference.child("Users").child(userId).child("empID").setValue(userModel.getStatus());
                                            databaseReference.child("Users").child(userId).child("location").setValue("");
                                            Toast.makeText(getApplicationContext(),"Account create successfully.", Toast.LENGTH_SHORT).show();
                                            finish();
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error)
                                    {

                                    }
                                });

                            } else
                            {
                                Toast.makeText(CreateUserActivity.this, "Something went wrong.. :-(" + Objects.requireNonNull(task.getException()), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Fill all details", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
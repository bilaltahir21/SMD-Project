package com.fast.tiffan_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;

public class Sign_up extends AppCompatActivity {


    EditText firstName,lastName,phoneNumber,Password,confirmPassword,verificationCode;
    Button SignUp,Verify;
    TextView loginPage;
    RadioButton male,female;

    LinearLayout verfication_layout,signup_layout;
    DatabaseReference myDatabaseReference;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks callbacks;
    private FirebaseAuth mAuth;
    private ProgressDialog loadingBar;
    private String mVerificationId;
    private PhoneAuthProvider.ForceResendingToken mResendToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(!isNetworkAvailable()){
            Toast.makeText(getApplicationContext() , "NETWORK NOT AVAILABLE!" , Toast.LENGTH_LONG);
            setContentView(R.layout.no_internet);
        }
        else {

            setContentView(R.layout.activity_sign_up);
            myDatabaseReference = FirebaseDatabase.getInstance().getReference();
            mAuth = FirebaseAuth.getInstance();
            initializeVariables();


            loginPage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    sendUserToLoginActivity();
                }
            });
            SignUp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (VerifyInputs()) {
                        if (isNetworkAvailable()) {
                            sendVerificationCode();
                        }
                        else
                        {
                            Toast toast = Toast.makeText(getApplicationContext() , "No Internet Connection", Toast.LENGTH_LONG);
                            toast.show();
                        }

                    }
                }
            });

            Verify.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    verifyNumber();
                }
            });


            callbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                @Override
                public void onVerificationCompleted(@NotNull PhoneAuthCredential phoneAuthCredential) {
                    signInWithPhoneAuthCredential(phoneAuthCredential);
                }

                @Override
                public void onVerificationFailed(FirebaseException e) {

                    Toast.makeText(Sign_up.this, "Error : " + e.toString(), Toast.LENGTH_LONG).show();
                    loadingBar.dismiss();
                    signup_layout.setVisibility(View.VISIBLE);
                    verfication_layout.setVisibility(View.GONE);

                }

                public void onCodeSent(@NotNull String verificationId,
                                       @NotNull PhoneAuthProvider.ForceResendingToken token) {
                    mVerificationId = verificationId;
                    mResendToken = token;


                    Toast.makeText(Sign_up.this, "Code has been sent, please check and verify...", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                    signup_layout.setVisibility(View.GONE);
                    verfication_layout.setVisibility(View.VISIBLE);
                }
            };
        }
    }

    private boolean VerifyInputs()
    {
        if((TextUtils.isEmpty(firstName.getText().toString())))
        {
            Toast.makeText(this, "Enter First Name", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if((TextUtils.isEmpty(lastName.getText().toString())))
        {
            Toast.makeText(this, "Enter Last Name", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if((TextUtils.isEmpty(phoneNumber.getText().toString())))
        {
            Toast.makeText(this, "Enter Phone Number", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if((TextUtils.isEmpty(Password.getText().toString())))
        {
            Toast.makeText(this, "Enter Password", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if((TextUtils.isEmpty(confirmPassword.getText().toString())))
        {
            Toast.makeText(this, "Enter Confirm Password", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(!(Password.getText().toString().equals(confirmPassword.getText().toString())))
        {
            Toast.makeText(this, "Passwords does not match", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(Password.getText().toString().length()<5)
        {
            Toast.makeText(this, "Enter Atleast 6 Digit Password", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;

    }

    private void verifyNumber() {

        verfication_layout.setVisibility(View.VISIBLE);

        String verification_Code = verificationCode.getText().toString();

        if (TextUtils.isEmpty(verification_Code))
        {
            Toast.makeText(Sign_up.this, "Please write verification code first...", Toast.LENGTH_SHORT).show();
        }
        else
        {
            loadingBar.setTitle("Verification Code");
            loadingBar.setMessage("Please wait, while we are verifying verification code...");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();

            PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, verification_Code);
            signInWithPhoneAuthCredential(credential);

        }

    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential)
    {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference("Users");
                            rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NotNull DataSnapshot snapshot) {
                                    if (snapshot.hasChild(phoneNumber.getText().toString())) {
                                        Toast.makeText(Sign_up.this, "Account Already Exists!", Toast.LENGTH_SHORT).show();
                                        sendUserToLoginActivity();
                                    }
                                    else {

                                        myDatabaseReference.child("Users").child(phoneNumber.getText().toString()).child("First Name")
                                                .setValue(firstName.getText().toString());
                                        myDatabaseReference.child("Users").child(phoneNumber.getText().toString()).child("Last Name")
                                                .setValue(lastName.getText().toString());
                                        myDatabaseReference.child("Users").child(phoneNumber.getText().toString()).child("Password")
                                                .setValue(Password.getText().toString());
                                        loadingBar.dismiss();

                                        if(male.isChecked())
                                        {
                                            myDatabaseReference.child("Users").child(phoneNumber.getText().toString()).child("Gender")
                                                    .setValue("Male");

                                        }
                                        else {
                                            myDatabaseReference.child("Users").child(phoneNumber.getText().toString()).child("Gender")
                                                    .setValue("Female");
                                        }

                                        myDatabaseReference.child("Users").child(phoneNumber.getText().toString()).child("Address").child("City")
                                                .setValue("City");
                                        myDatabaseReference.child("Users").child(phoneNumber.getText().toString()).child("Address").child("House")
                                                .setValue("House");
                                        myDatabaseReference.child("Users").child(phoneNumber.getText().toString()).child("Address").child("Street")
                                                .setValue("Street");
                                        myDatabaseReference.child("Users").child(phoneNumber.getText().toString()).child("Address").child("Town")
                                                .setValue("Town");

                                        Toast.makeText(Sign_up.this, "Account Created Successfully!", Toast.LENGTH_SHORT).show();
                                        // Toast.makeText(Sign_up.this, "Congratulations, you're logged in Successfully.", Toast.LENGTH_SHORT).show();
                                        Intent returnIntent = new Intent();
                                        returnIntent.putExtra("phone",phoneNumber.getText().toString());
                                        returnIntent.putExtra("password",Password.getText().toString());
                                        setResult(Sign_up.RESULT_OK,returnIntent);
                                        finish();
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                        }
                        else {
                            if(!isNetworkAvailable())
                            {
                                Toast.makeText(Sign_up.this, "Please Check Your Internet Connection" , Toast.LENGTH_LONG).show();
                                loadingBar.dismiss();
                            }

                            else {

                                String message = task.getException().toString();
                                if (task.getException() instanceof FirebaseAuthInvalidCredentialsException){
                                    Toast.makeText(Sign_up.this, "Wrong Verification Code Entered! " + message, Toast.LENGTH_LONG).show();
                                    loadingBar.dismiss();
                                }

                                else {
                                    Toast.makeText(Sign_up.this, "Error: " + message, Toast.LENGTH_LONG).show();
                                    loadingBar.dismiss();
                                }
                            }
                        }

                    }
                });
    }



    private void SendUserToRestaurant() {
        Intent mainIntent = new Intent(Sign_up.this, Restaurant.class);
        startActivity(mainIntent);
        finish();
    }

    private void sendVerificationCode() {

        String phone_Number = phoneNumber.getText().toString();

        loadingBar.setTitle("Phone Verification");
        loadingBar.setMessage("Please wait, while we are authenticating using your phone...");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();


        PhoneAuthProvider.getInstance().verifyPhoneNumber(phone_Number, 60, TimeUnit.SECONDS, Sign_up.this, callbacks);

    }


    private void sendUserToLoginActivity() {
        Intent mainIntent = new Intent(Sign_up.this, com.fast.tiffan_project.MainActivity.class);
        startActivity(mainIntent);
        this.finish();
    }

    private void initializeVariables() {
        Verify=findViewById(R.id.btn_verify);
        verificationCode=findViewById(R.id.edt_verficationCode);
        firstName=findViewById(R.id.edt_firstname);
        lastName=findViewById(R.id.edt_lastName);
        phoneNumber=findViewById(R.id.edt_phoneNumber);
        Password=findViewById(R.id.edt_password);
        confirmPassword=findViewById(R.id.edt_confirmPassword);
        SignUp=findViewById(R.id.btn_signUp);
        loginPage=findViewById(R.id.link_LogIn);
        male=findViewById(R.id.rb_male);
        female=findViewById(R.id.rb_female);
        loadingBar = new ProgressDialog(this);


        signup_layout = findViewById(R.id.layout_signUp);
        verfication_layout = findViewById(R.id.layout_verification);
        male.setChecked(true);
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

}

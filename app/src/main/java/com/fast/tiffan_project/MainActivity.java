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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    EditText PhoneNumber,VerificationCode,password;
    Button LoginButton,VerifyButton;
    TextView SignUp;



    DatabaseReference myDatabaseReference;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks callbacks;
    private FirebaseAuth mAuth;
    private ProgressDialog loadingBar;
    private String mVerificationId;
    private PhoneAuthProvider.ForceResendingToken mResendToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDatabaseReference = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        initializeVaribles();

        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (VerifyInputs()) {
                    checkIfAccountExist();
                }
            }
        });

        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendUserToSignUpActivity();
            }
        });
      /*  callbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential)
            {
                signInWithPhoneAuthCredential(phoneAuthCredential);
            }

            @Override
            public void onVerificationFailed(FirebaseException e)
            {

                Toast.makeText(MainActivity.this, "Error : "+e.toString(), Toast.LENGTH_LONG).show();
                loadingBar.dismiss();
                PhoneNumber.setVisibility(View.VISIBLE);
                SignUp.setVisibility(View.VISIBLE);
                VerificationCode.setVisibility(View.INVISIBLE);
                VerifyButton.setVisibility(View.INVISIBLE);
            }

            public void onCodeSent(String verificationId,
                                   PhoneAuthProvider.ForceResendingToken token)
            {

                mVerificationId = verificationId;
                mResendToken = token;
                Toast.makeText(MainActivity.this, "Code has been sent, please check and verify...", Toast.LENGTH_SHORT).show();
                loadingBar.dismiss();

                PhoneNumber.setVisibility(View.INVISIBLE);
                SignUp.setVisibility(View.INVISIBLE);

                VerificationCode.setVisibility(View.VISIBLE);
                VerifyButton.setVisibility(View.VISIBLE);
            }
        };*/
    }


    private void checkIfAccountExist() {
        final DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference("Users");
        rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                  if (snapshot.hasChild(PhoneNumber.getText().toString())) {
                // run some code
                      VerifyPassword();

                } else {
                    Toast.makeText(MainActivity.this, "You need to SignUp First ", Toast.LENGTH_SHORT).show();
                    sendUserToSignUpActivity();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    private void VerifyPassword() {
        final DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference("Users").child(PhoneNumber.getText().toString()).child("Password");
        rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                    // run some code
                    String DB_password=snapshot.getValue().toString();  // checkitout
                    Toast.makeText(MainActivity.this, DB_password, Toast.LENGTH_LONG).show();
                    if(DB_password.equals(password.getText().toString()))
                    {
                        sendUserToResturant();
                    }
                    else
                    {
                        Toast.makeText(MainActivity.this, "Wrong Password!", Toast.LENGTH_SHORT).show();
                    }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(MainActivity.this, "hahaha", Toast.LENGTH_SHORT).show();

            }
        });
    }

   /* private void verifyNumber() {

        // InputUserPhoneNumber.setVisibility(View.INVISIBLE);
        VerificationCode.setVisibility(View.VISIBLE);


        String verification_Code = VerificationCode.getText().toString();

        if (TextUtils.isEmpty(verification_Code))
        {
            Toast.makeText(MainActivity.this, "Please write verification code first...", Toast.LENGTH_SHORT).show();
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

    }*/
    /*private void signInWithPhoneAuthCredential(PhoneAuthCredential credential)
    {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference("Users");
                            rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot snapshot) {
                                    if (snapshot.hasChild(PhoneNumber.getText().toString())) {
                                        // run some code
                                        sendUserToResturant();
                                    }
                                    else{
                                        Toast.makeText(MainActivity.this, "You need to SignUp First ", Toast.LENGTH_SHORT).show();
                                        sendUserToSignUpActivity();
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });


                        } else {
                            if(isNetworkAvailable()==false)
                            {
                                Toast.makeText(MainActivity.this, "Please Check Your Internet Connection" , Toast.LENGTH_LONG).show();
                                loadingBar.dismiss();
                            }

                            else {

                                String message = task.getException().toString();
                                if (task.getException() instanceof FirebaseAuthInvalidCredentialsException){
                                    Toast.makeText(MainActivity.this, "Wrong Verrification Code Entered! " + message, Toast.LENGTH_LONG).show();
                                    loadingBar.dismiss();
                                }

                                else {
                                    Toast.makeText(MainActivity.this, "Error: " + message, Toast.LENGTH_LONG).show();
                                    loadingBar.dismiss();
                                }
                            }
                        }

                    }
                });
    }*/

    private void sendUserToResturant() {
        startActivity(new Intent(MainActivity.this, Restaurant.class));
        finish();
    }

    private void sendVerificationCode() {

        String phone_Number = PhoneNumber.getText().toString();

        loadingBar.setTitle("Phone Verification");
        loadingBar.setMessage("Please wait, while we are authenticating using your phone...");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();

        PhoneAuthProvider.getInstance().verifyPhoneNumber(phone_Number, 60, TimeUnit.SECONDS, MainActivity.this, callbacks);

    }

    private boolean VerifyInputs() {
        if((TextUtils.isEmpty(PhoneNumber.getText().toString())))
        {
            Toast.makeText(this, "Enter Phone Number", Toast.LENGTH_SHORT).show();
            return false;
        }
        else{

            if( PhoneNumber.getText().toString().charAt(0)=='+')
            {
                if(PhoneNumber.getText().toString().length()!=13)
                {
                    Toast.makeText(this, "Enter Valid Phone Number", Toast.LENGTH_SHORT).show();
                }
            }
            else
            {
                if(PhoneNumber.getText().toString().length()!=11)
                {
                    Toast.makeText(this, "Enter Valid Phone Number", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    String phone=PhoneNumber.getText().toString();
                    String countrycode="+92";
//                    char number[11];
                    for(int i=0;i<11;i++)
                    {

                    }
                }
            }

        }
        return true;
    }

    private void sendUserToSignUpActivity() {
        startActivity(new Intent(MainActivity.this, Sign_up.class));
        finish();
    }

    private void initializeVaribles() {
        PhoneNumber=findViewById(R.id.edt_number);
        password=findViewById(R.id.edt_password_login);
        LoginButton=findViewById(R.id.btn_logIn);
        SignUp=findViewById(R.id.txt_link_SignUp);
        loadingBar = new ProgressDialog(this);

    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}

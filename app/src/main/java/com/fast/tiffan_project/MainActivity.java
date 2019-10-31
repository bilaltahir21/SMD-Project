package com.fast.tiffan_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {



    EditText PhoneNumber,VerificationCode,password;
    Button LoginButton,VerifyButton;
    TextView SignUp;

    final static String SharePrefernce = "MySharedPreference";


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
//                    sendUserToResturant();
                }
            });

            SignUp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    sendUserToSignUpActivity();
                }
            });

        }

    }


    private void checkIfAccountExist() {
        final DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference("Users");
        rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.hasChild(PhoneNumber.getText().toString())) {
                    // run some code
                    if(!VerifyPasswordLocally()) {
                        VerifyPassword();
                    }

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

    private boolean VerifyPasswordLocally()
    {
        SharedPreferences prefs = getSharedPreferences("MySharedPreference", MODE_PRIVATE);
        String phone = prefs.getString("phone", "notsaved");//"No name defined" is the default value.
        String pass = prefs.getString("password", "notsaved"); //0 is the default value.
        if(!phone.equals("notsaved") && !pass.equals("notsaved"))
        {
            if(PhoneNumber.getText().toString().equals(phone) && password.getText().toString().equals(pass))
            {

                sendUserToResturant();
                return true;
            }
            else{
                Toast.makeText(this, "wrong Password!", Toast.LENGTH_SHORT).show();
                return true;
            }
        }
        else
        {
            return false;
        }

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

    private void sendUserToResturant() {
        String phone = PhoneNumber.getText().toString();
        SharedPreferences.Editor editor = getSharedPreferences(SharePrefernce, MODE_PRIVATE).edit();
        editor.putString("phone", phone);
        editor.putString("LogIn", "true");
        editor.apply();
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
                    return false;
                }
            }
            else if(PhoneNumber.getText().toString().charAt(0)=='0')
            {
                if(PhoneNumber.getText().toString().length()!=11)
                {
                    Toast.makeText(this, "Enter Valid Phone Number", Toast.LENGTH_SHORT).show();
                    return false;
                }
                else
                {
                    String phone=PhoneNumber.getText().toString();
                    String countrycode="+92";
                    char []number=new char[10];
                    for(int i=0;i<10;i++)
                    {
                        number[i]=phone.charAt(i+1);
                    }
                    String Phone_Number="";
                    Phone_Number=Phone_Number+countrycode;
                    String num=new String(number);
                    Phone_Number=Phone_Number+num;
                    PhoneNumber.setText(Phone_Number);

                }
            }
            else {
                Toast.makeText(this, "Not a Valid Phone Number Registered in Pakistan", Toast.LENGTH_SHORT).show();
            }

        }
        return true;
    }



    private void sendUserToSignUpActivity() {

        Intent i = new Intent(MainActivity.this, Sign_up.class);
        startActivityForResult(i, 1);
//        this.finish();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == Sign_up.RESULT_OK) {

                String phone = data.getStringExtra("phone");
                String pass = data.getStringExtra("password");
                PhoneNumber.setText(phone);
                password.setText(pass);
                SharedPreferences.Editor editor = getSharedPreferences(SharePrefernce, MODE_PRIVATE).edit();
                editor.putString("phone", phone);
                editor.putString("password", pass);
                editor.apply();

            }
            if (resultCode == Sign_up.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }

}

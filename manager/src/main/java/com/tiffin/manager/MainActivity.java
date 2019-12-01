package com.tiffin.manager;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.media.AudioAttributes;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tiffin.manager.ui.main.SectionsPagerAdapter;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("WrongConstant")
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference reference = firebaseDatabase.getReference();
        reference.child("Order")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Notify();
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
    }



    public void Notify()
    {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, "222")
                .setSmallIcon(R.drawable.logo)
                .setContentTitle("Order")
                .setContentText("A new Order have been added in the Queue.")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);//notificationId is a unique int for each notification that you must define
        notificationManager.notify(101, mBuilder.build());

    }




    private void Notification(){


        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference reference = firebaseDatabase.getReference();
        reference.child("Orders")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Iterable<DataSnapshot> children = dataSnapshot.getChildren();

                        //if (child.getType == Type.ADDED)
                            {
                                //show notification
                                //Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                Intent intent=new Intent();
                                PendingIntent pi = PendingIntent.getActivity(getApplicationContext(), 101, intent, 0);
                                NotificationManager nm = (NotificationManager) getApplicationContext().getSystemService(NOTIFICATION_SERVICE);
                                NotificationChannel channel = null;
                                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {

                                    AudioAttributes att = new AudioAttributes.Builder()
                                            .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                                            .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH)
                                            .build();

                                    channel = new NotificationChannel("222", "my_channel", NotificationManager.IMPORTANCE_HIGH);
                                    channel.setDescription("Game Notifications");
                                    channel.enableLights(true);
                                    channel.setVibrationPattern(new long[]{0, 1000, 500, 1000});
                                    channel.enableVibration(true);
                                    Objects.requireNonNull(nm).createNotificationChannel(channel);
                                }

                                NotificationCompat.Builder builder =
                                        new NotificationCompat.Builder(
                                                getApplicationContext(), "222")
                                                .setContentTitle("Order")
                                                .setAutoCancel(true)
//                                                .setLargeIcon(((BitmapDrawable)getDrawable(R.drawable.seek)).getBitmap())
//                                                .setSmallIcon(R.drawable.seek)
                                                //.setSound(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.electro))
                                                .setContentIntent(pi)
                                                .setContentText("A new Order have bben received")
                                                .setWhen(System.currentTimeMillis())
                                                .setPriority(Notification.PRIORITY_MAX)
                                        ;

                                builder.setPriority(NotificationCompat.PRIORITY_HIGH);
                                try
                                {
                                    nm.notify(101,builder.build());
                                }
                                catch(Exception e)
                                {
                                    String n="Notification Failed";
                                    Log.e("Alert:",n);
                                }
                            }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }
}
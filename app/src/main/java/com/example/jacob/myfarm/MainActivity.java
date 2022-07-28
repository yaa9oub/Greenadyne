package com.example.jacob.myfarm;

import android.app.ActivityOptions;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    String used ;
    SQLiteDatabase db ;
    DatabaseHelper mDataBaseHelper ;
    EditText user , pass ;
    ImageView logo ;
    Button signup , login , forgpass , signer;
    RelativeLayout layout1 , layout2 ;
    Handler handler = new Handler();

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            Cursor connected = mDataBaseHelper.isConnected();
            used = "";

            while(connected.moveToNext()){
                used = connected.getString(0);
            }
                if (used.length() != 0) {
                Intent intent = new Intent(MainActivity.this,Home.class);
                    intent.putExtra("user",used);
                    startActivity(intent);
            }
            else
            {
                layout1.setVisibility(View.VISIBLE);
                //layout2.setVisibility(View.VISIBLE);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toast.makeText(this, "THIS APP BELONGS TO ATASTSO CLUB", Toast.LENGTH_SHORT).show();
        mDataBaseHelper = new DatabaseHelper(this);
        user = findViewById(R.id.useredt);
        pass = findViewById(R.id.passedt);
        forgpass = findViewById(R.id.forgotpass);
        logo = findViewById(R.id.logoimg);
        login = findViewById(R.id.loginbtn);
        layout1 = findViewById(R.id.rellay1);
        layout2 = findViewById(R.id.rellay2);
        signup = findViewById(R.id.signbtn);
        signer = findViewById(R.id.signinbtn);

        Animation animation = AnimationUtils.loadAnimation(this,R.anim.lefttoright);
        logo.startAnimation(animation);

        handler.postDelayed(runnable, 2000);
        user.setText("atastso");
        pass.setText("issatso");

    login.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String name = user.getText().toString(), passw = pass.getText().toString();
            if (name.isEmpty() || passw.isEmpty())
                Toast.makeText(MainActivity.this, "You need to fill in the blanks !!", Toast.LENGTH_SHORT).show();
            else {
                Cursor data = mDataBaseHelper.getData(name, passw);
                while(data.moveToNext()){
                    used = data.getString(0);
                }
                if (used.length()!=0) {
                    mDataBaseHelper.connect(name);
                    Intent intent = new Intent(MainActivity.this, Home.class);
                    intent.putExtra("user",name);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slideinright,R.anim.slideoutleft);
                    Toast.makeText(MainActivity.this, "WELCOME !!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Soory ! wrong username or password !!", Toast.LENGTH_SHORT).show();
                }
            }
        }
    });

    signup.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(MainActivity.this,signingup.class);

            //Pair[] pairs = new Pair[1];
            //pairs[0] = new Pair<View,String>(logo,"trans1");
            //ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this,pairs);
            startActivity(intent);
            overridePendingTransition(R.anim.slideinright,R.anim.slideoutleft);
        }
    });

    signer.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(MainActivity.this,signingup.class);
            Pair[] pairs = new Pair[1];
            pairs[0] = new Pair<View,String>(logo,"trans1");
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this,pairs);
            startActivity(intent,options.toBundle());
        }
    });

    forgpass.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
        layout1.setVisibility(View.GONE);
        layout2.setVisibility(View.VISIBLE);
        }
    });

    }

    private static long back_pressed;
    @Override
    public void onBackPressed() {

        if (back_pressed + 2000 > System.currentTimeMillis()){
            moveTaskToBack(true);
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
        }
        else{
            Toast.makeText(getBaseContext(), "Press once again to exit", Toast.LENGTH_SHORT).show();
            back_pressed = System.currentTimeMillis();
        }
    }

    public void getuser()
    {
        Cursor connected = mDataBaseHelper.isConnected();
        used = "";
        while(connected.moveToNext()){
            used = connected.getString(0);
        }
        Toast.makeText(MainActivity.this, used, Toast.LENGTH_SHORT).show();

    }
}

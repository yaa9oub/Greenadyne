package com.example.jacob.myfarm;

import android.app.ActivityOptions;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class settings extends AppCompatActivity {

    TextView user ;
    ImageView logo ;
    EditText name , pass , email , phone;
    Button save , logout ;
    DatabaseHelper mDataBaseHelper ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        logo = findViewById(R.id.logoimg);
        user = findViewById(R.id.usertxt);
        name = findViewById(R.id.nameeditText);
        pass = findViewById(R.id.passeditText);
        email = findViewById(R.id.adreditText);
        phone = findViewById(R.id.PhoneeditText);
        save = findViewById(R.id.savebtn);
        logout = findViewById(R.id.Logbutton);
        mDataBaseHelper = new DatabaseHelper(this);

        Intent intent = getIntent();
        final String using = intent.getStringExtra("user");
        user.setText(using.toUpperCase());

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String namestr = name.getText().toString() ;
                String passstr = pass.getText().toString() ;
                String emailstr = email.getText().toString() ;
                String phonestr = phone.getText().toString() ;
                if(namestr.isEmpty() || passstr.isEmpty() || emailstr.isEmpty() || phonestr.isEmpty())
                {
                    Toast.makeText(settings.this, "Fill your fields", Toast.LENGTH_SHORT).show();
                }
                else
                {

                }
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDataBaseHelper.disconnect();
                Intent intent = new Intent(settings.this,MainActivity.class);
                Pair[] pairs = new Pair[1];
                pairs[0] = new Pair<View,String>(logo,"trans1");
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(settings.this,pairs);
                startActivity(intent,options.toBundle());
            }
        });

        }

    @Override
    public void onResume() {
        super.onResume();
        Cursor connected = mDataBaseHelper.isConnected();
        String used = "";

        while(connected.moveToNext()){
            used = connected.getString(0);
        }
        if (used.length() == 0) {
            Intent intent = new Intent(settings.this,MainActivity.class);
            startActivity(intent);
        }
    }

}

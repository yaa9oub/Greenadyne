package com.example.jacob.myfarm;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class signingup extends AppCompatActivity {

    EditText user ,name , pass , email , phone ;
    Button sign , cancel ;
    DatabaseHelper mDataBaseHelper ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signingup);

        mDataBaseHelper = new DatabaseHelper(this);

        user = findViewById(R.id.useredt);
        name = findViewById(R.id.nameedt);
        pass = findViewById(R.id.passedt);
        email = findViewById(R.id.adredt);
        phone = findViewById(R.id.phoneedt);
        sign = findViewById(R.id.signbtn);
        cancel = findViewById(R.id.cancelbtn);

        sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userstr = user.getText().toString() ;
                String namestr = name.getText().toString() ;
                String passstr = pass.getText().toString() ;
                String emailstr = email.getText().toString() ;
                String phonestr = phone.getText().toString() ;
                if(userstr.isEmpty() || namestr.isEmpty() || passstr.isEmpty() || emailstr.isEmpty() || phonestr.isEmpty())
                {
                    Toast.makeText(signingup.this, "Fill your fields", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    AddData(userstr,passstr);
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(signingup.this,MainActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slideinleft,R.anim.slideoutright);
            }
        });

    }

    public void AddData (String user ,String pass) {
        boolean insertData = mDataBaseHelper.addData(user,pass);


        if (insertData) {
            Toast.makeText(this, "Signed up Successfully !", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(signingup.this,MainActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.fadein,R.anim.fadeout);
        } else {
            Toast.makeText(this, "Something went wrong , repeat again later !!", Toast.LENGTH_SHORT).show();
        }

    }

}

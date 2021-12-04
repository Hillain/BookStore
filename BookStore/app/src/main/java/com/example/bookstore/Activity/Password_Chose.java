package com.example.bookstore.Activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.bookstore.R;

public class Password_Chose extends BaseActivity {
    private Button btn_email;
    private Button btn_phone;
    private TextView go_login;

    protected void init(Context context) {
        super.init(context);
        setContentView(R.layout.password_activity);
        btn_email = findViewById(R.id.email_password);
        btn_phone = findViewById(R.id.phone_password);
        go_login = findViewById(R.id.go_login);
        btn_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(Password_Chose.this, Password_ByEmail.class);
                startActivity(intent);
            }
        });
        btn_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(Password_Chose.this, Password_ByPhone.class);
                startActivity(intent);
            }
        });
        go_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(Password_Chose.this, Login.class);
                startActivity(intent);
            }
        });
    }
}

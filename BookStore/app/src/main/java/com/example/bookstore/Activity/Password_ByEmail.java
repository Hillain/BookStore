package com.example.bookstore.Activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bookstore.R;
import com.example.bookstore.Utils.Contants;
import com.lljjcoder.citypickerview.widget.CityPicker;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Password_ByEmail extends BaseActivity {
    private EditText username;
    private EditText password;
    private EditText emailcode;
    private EditText surepassword;
    private EditText email;
    private Button btn_edit;
    private Button btn_getCode;
    private TextView go_login;
    private String verifyCdoe;
    private boolean correct=false;
    private String delay = null;
    private int T = 0;
    Handler mhandler = new Handler();

    protected void init(Context context) {
        super.init(context);
        setContentView(R.layout.password_email);
        username = findViewById(R.id.R_username);
        password = findViewById(R.id.R_password);
        surepassword = findViewById(R.id.surepassword);
        emailcode = findViewById(R.id.email_code);
        btn_getCode = findViewById(R.id.btn_getCode);
        email = findViewById(R.id.R_email);
        go_login = findViewById(R.id.go_login);
        btn_edit = findViewById(R.id.btn_edit);
        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = username.getText().toString();
                String pwd = password.getText().toString();
                String email_str = email.getText().toString();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
                    if (!username.getText().toString().isEmpty() && !password.getText().toString().isEmpty()) {
                        if (password.getText().toString().equals(surepassword.getText().toString())) {
                            if (!email.getText().toString().isEmpty() || emailValidation(email.getText().toString())) {
                                if(!emailcode.getText().toString().isEmpty()&&emailcode.getText().toString().equals(verifyCdoe)){//验证验证码是否正确的方法
                                    String params = "?username=" + name + "&password=" + pwd;
                                    try {
                                        getJSONByVolley(Contants.BASEURL + "user/updatePwd" + params, null);
                                    } catch (UnsupportedEncodingException e) {
                                        e.printStackTrace();
                                    }
                                    Toast.makeText(Password_ByEmail.this, "修改成功", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(Password_ByEmail.this, "验证码错误", Toast.LENGTH_SHORT).show();
                                    Log.d("asdasd", "verifyCdoe="+verifyCdoe+"emailcode="+emailcode);
                                }
                            } else {
                                Toast.makeText(Password_ByEmail.this, "邮箱输入异常", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(Password_ByEmail.this, "密码不一致", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(Password_ByEmail.this, "用户名,密码不能为空", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        go_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(Password_ByEmail.this, Login.class);
                startActivity(intent);
            }
        });
        btn_getCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    correctEmail(email.getText().toString());
                    if(!email.getText().toString().isEmpty() && emailValidation(email.getText().toString())) {//验证邮箱是否正确
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                if(correct){
                                    Toast.makeText(Password_ByEmail.this,"已发送验证码，请等待",Toast.LENGTH_SHORT).show();
                                    T = 60;
                                    new Thread(new Password_ByEmail.ButtonTimer()).start();
                                    try {
                                        getJSONByVolley(Contants.BASEURL + "user/getcode"+ "?email="+ email.getText().toString() , null);
                                    } catch (UnsupportedEncodingException e) {
                                        e.printStackTrace();
                                    }
                                }else{
                                    Toast.makeText(Password_ByEmail.this, "该邮箱非账号绑定邮箱！", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }, 3000);//3秒后执行Runnable中的run方法
                    }else{
                        Toast.makeText(Password_ByEmail.this, "邮箱格式不正确或为空！", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    protected void setJSONDataToView(JSONObject response) {
        try {
            Log.e("yanger", "函数======= " + response.toString());
            if (!"0".equals(response.getString("success"))) {
                if(response.getString("success").length()>3&&response.getString("success").length()<5)verifyCdoe = response.getString("success");
                else if(response.getString("success").length()==9){
                    Log.d("emailMessage", response.getString("success"));
                    correct = true;
                }else if(response.getString("success").length()==10){
                    Log.d("emailMessage", response.getString("success"));
                    correct = false;
                } else{
                    getToast("修改成功，请登录！");
                    Intent intent = new Intent();
                    intent.setClass(Password_ByEmail.this, Login.class);
                    startActivity(intent);
                    Password_ByEmail.this.finish();
                }
            }  else {
                getToast("修改失败，检查网络或更换用户名！");
            }
        } catch (JSONException e) {
        }
    }

    public boolean emailValidation(String email) {
        String strPattern = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";
        Pattern p = Pattern.compile(strPattern);
        Matcher m = p.matcher(email.trim());
        System.out.println(email+"====="+m.matches());
        return m.matches();
    }
    public void correctEmail(String email) {
        try {
            getJSONByVolley(Contants.BASEURL + "user/correctEmail?email=" + email + "&username=" + username.getText().toString(), null);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
    class ButtonTimer implements Runnable{
        @Override
        public void run() {
            while(T > 0){
                mhandler.post(new Runnable() {
                    @Override
                    public void run() {
                        btn_getCode.setClickable(false);
                        String str = T + "秒后重新获取";
                        btn_getCode.setText(str);
                    }
                });
                try{
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                T--;
            }
            //倒计时结束
            mhandler.post(new Runnable() {
                @Override
                public void run() {
                    btn_getCode.setClickable(true);
                    btn_getCode.setText("获取验证码");
                }
            });
            T = 60;
        }
    }
}
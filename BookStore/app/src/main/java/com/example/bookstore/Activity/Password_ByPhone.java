package com.example.bookstore.Activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
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
import com.mob.MobSDK;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

public class Password_ByPhone extends BaseActivity{
    private EditText username;
    private EditText password;
    private EditText surepassword;
    private EditText pnumber;
    private EditText code;
    private Button btn_edit;
    private Button btn_getCode;
    private TextView go_login;
    private boolean Flag=false;
    private boolean correct=false;
    //…………………………………………
    EventHandler eh;
    Handler mhandler = new Handler();
    int T = 0;
    String phonenumber = "";
    boolean flag = false;

    @Override
    protected void init(Context context) {
        super.init(context);
        setContentView(R.layout.password_phone);
        btn_getCode = findViewById(R.id.btn_getCode);
        btn_edit = findViewById(R.id.btn_edit);
        password = findViewById(R.id.R_password);
        username = findViewById(R.id.username);
        pnumber = findViewById(R.id.R_pnumber);
        surepassword = findViewById(R.id.surepassword);
        code = findViewById(R.id.phone_code);
        go_login = findViewById(R.id.go_login);
        go_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(Password_ByPhone.this, Login.class);
                startActivity(intent);
            }
        });
        btn_getCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                correctPhone(pnumber.getText().toString());
                if(legal(pnumber.getText().toString())){
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if(correct){
                                getCode();
                            }else{
                                Toast.makeText(Password_ByPhone.this,"该手机非账号绑定手机！",Toast.LENGTH_SHORT).show();
                            }
                        }
                    }, 3000);//3秒后执行Runnable中的run方法
                }else{
                    Toast.makeText(Password_ByPhone.this,"手机号非法！",Toast.LENGTH_SHORT).show();
                }
            }
        });
        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verification();
            }
        });
        MobSDK.init(this);
        //        MobSDK的必要授权
        MobSDK.submitPolicyGrantResult(true,null);
        //        修改监听回调
        eh = new EventHandler(){
            @Override
            public void afterEvent(int event, int result, Object data) {
                //TODO 此处不可直接处理UI线程，处理后续操作需传到主线程中操作
                Message msg = new Message();
                msg.arg1 = event;
                msg.arg2 = result;
                msg.obj = data;
                handler.sendMessage(msg);
            }
        };
        SMSSDK.registerEventHandler(eh);
    }

    @Override
    protected void setJSONDataToView(JSONObject response) {
        try {
            Log.e("yanger", "函数======= " + response.toString());
            if (response.getString("success").length()>3&&response.getString("success").length()<5) {
                correct=true;
            }else if (!"0".equals(response.getString("success"))&&response.getString("success").length()<2) {
                flag = true;
                Flag = true;
            }else{
                correct=false;
            }
            if(flag&&Flag){
                getToast("修改成功，请登录！");
                Intent intent = new Intent();
                intent.setClass(Password_ByPhone.this, Login.class);
                startActivity(intent);
            }else if(!flag&&Flag){
                Toast.makeText(Password_ByPhone.this,"修改失败",Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
        }
    }

    public void getCode(){
        //System.out.println("=============================="+phonenumber);
        SMSSDK.getVerificationCode("86", pnumber.getText().toString());
        //System.out.println("====123123213213213213123123213====");
    }

    public void verification(){
        Toast.makeText(Password_ByPhone.this,code.getText().toString(),Toast.LENGTH_SHORT).show();
        SMSSDK.submitVerificationCode("86", pnumber.getText().toString(), code.getText().toString());
    }

    //    注销监听，避免内存泄露
    protected void onDestroy() {
        super.onDestroy();
        SMSSDK.unregisterEventHandler(eh);
    }

    Handler handler = new Handler(Looper.getMainLooper(), new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            int event = msg.arg1;
            int result = msg.arg2;
            Object data = msg.obj;
            if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE){
                if (result == SMSSDK.RESULT_COMPLETE){
                    System.out.println("已发送验证码，请等待");
                    Toast.makeText(Password_ByPhone.this,"已发送验证码，请等待",Toast.LENGTH_SHORT).show();
                    T = 60;
                    new Thread(new ButtonTimer()).start();
                }else{
                    ((Throwable)data).printStackTrace();
                }
            }else if(event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE){
                if(result == SMSSDK.RESULT_COMPLETE){
                    System.out.println("验证码通过");
                    String pwd = password.getText().toString();
                    String user  = username.getText().toString();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
                        if (password.getText().toString().equals(surepassword.getText().toString())) {
                                if (!pnumber.getText().toString().isEmpty()) {
                                    String params = "?username=" + user + "&password=" + pwd;
                                    try {
                                        getJSONByVolley(Contants.BASEURL + "user/updatePwd" + params, null);
                                    } catch (UnsupportedEncodingException e) {
                                        e.printStackTrace();
                                    }
                                } else {
                                    Toast.makeText(Password_ByPhone.this, "手机号不能为空", Toast.LENGTH_SHORT).show();
                                }
                        } else {
                            Toast.makeText(Password_ByPhone.this, "密码不一致", Toast.LENGTH_SHORT).show();
                        }
                    }
                }else{
                    ((Throwable)data).printStackTrace();
                    Toast.makeText(Password_ByPhone.this,"验证码错误，请重新输入",Toast.LENGTH_SHORT).show();
                }
            }
            return false;
        }
    });

    //    等待验证码，动态按钮倒计时
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
//            倒计时结束
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
    public void correctPhone(String phone) {
        try {
            getJSONByVolley(Contants.BASEURL + "user/correctPhone?pnumber" + phone, null);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
    public boolean legal(String phone){
        String strPattern = "[1][3578]\\d{9}";
        Pattern p = Pattern.compile(strPattern);
        Matcher m = p.matcher(phone.trim());
        System.out.println(phone+"====="+m.matches());
        return m.matches();
    }
}
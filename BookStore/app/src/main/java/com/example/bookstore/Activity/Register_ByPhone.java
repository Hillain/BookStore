package com.example.bookstore.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
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
import com.example.bookstore.MainActivity;
import com.example.bookstore.R;
import com.example.bookstore.Utils.Contants;
import com.lljjcoder.citypickerview.widget.CityPicker;
import com.mob.MobSDK;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

public class Register_ByPhone extends BaseActivity{
    private EditText username;
    private EditText password;
    private EditText surepassword;
    private EditText pnumber;
    private EditText code;
    private TextView address;
    private Button btn_Register;
    private Button btn_getCode;
    private TextView go_login;
    private CityPicker cityPicker;
    private RadioButton manbutton, wonmanbutton;
    //…………………………………………
    EventHandler eh;
    Handler mhandler = new Handler();
    int T = 0;
    String phonenumber = "";
    boolean flag = false;

    @Override
    protected void init(Context context) {
        super.init(context);
        setContentView(R.layout.register_phone);
        btn_getCode = findViewById(R.id.btn_getCode);
        username = findViewById(R.id.R_username);
        password = findViewById(R.id.R_password);
        pnumber = findViewById(R.id.R_pnumber);
        surepassword = findViewById(R.id.surepassword);
        manbutton = findViewById(R.id.reg_man);
        wonmanbutton = findViewById(R.id.reg_woman);
        code = findViewById(R.id.phone_code);
        address = findViewById(R.id.R_province);
        go_login = findViewById(R.id.go_login);
        btn_Register = findViewById(R.id.btn_register);
        go_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(Register_ByPhone.this, Login.class);
                startActivity(intent);
            }
        });
        address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initcityPicker();
                cityPicker.show();
            }
        });
        btn_getCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getCode();
            }
        });
        btn_Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verification();
            }
        });
        MobSDK.init(this);
        //        MobSDK的必要授权
        MobSDK.submitPolicyGrantResult(true,null);
        //        注册监听回调
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
            if (!"0".equals(response.getString("success"))) {
                flag = true;
            }
            if(flag){
                getToast("注册成功，请登录！");
                Intent intent = new Intent();
                intent.setClass(Register_ByPhone.this, Login.class);
                startActivity(intent);
            }else{
                Toast.makeText(Register_ByPhone.this,"注册失败",Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
        }
    }

    private void initcityPicker() {
        cityPicker = new CityPicker.Builder(Register_ByPhone.this)
                .textSize(16).title("地址选择")
                .backgroundPop(0xa0000000).titleBackgroundColor("#EFB81C")
                .titleTextColor("#000000").backgroundPop(0xa0000000)
                .confirTextColor("#000000").cancelTextColor("#000000")
                .province("江西省")
                .city("赣州市")
                .district("章贡区")
                .textColor(Color.parseColor("#000000"))
                .provinceCyclic(true)
                .cityCyclic(false)
                .districtCyclic(false)
                .visibleItemsCount(7)
                .itemPadding(10)
                .onlyShowProvinceAndCity(false)
                .build();
        cityPicker.setOnCityItemClickListener(new CityPicker.OnCityItemClickListener() {
            @Override
            public void onSelected(String... strings) {
                String province = strings[0];
                String city = strings[1];
                String district = strings[2];
                address.setText(String.format("%s %s %s", province, city, district));
            }
            @Override
            public void onCancel() {
            }
        });
    }

    public void getCode(){
        //System.out.println("=============================="+phonenumber);
        SMSSDK.getVerificationCode("86", pnumber.getText().toString());
        //System.out.println("====123123213213213213123123213====");
    }

    public void verification(){
        Toast.makeText(Register_ByPhone.this,code.getText().toString(),Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(Register_ByPhone.this,"已发送验证码，请等待",Toast.LENGTH_SHORT).show();
                    T = 60;
                    new Thread(new ButtonTimer()).start();
                }else{
                    ((Throwable)data).printStackTrace();
                }
            }else if(event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE){
                if(result == SMSSDK.RESULT_COMPLETE){
                    System.out.println("验证码通过");
                    String name = username.getText().toString();
                    String pwd = password.getText().toString();
                    String phone = pnumber.getText().toString();
                    String sex = null;
                    String addr = address.getText().toString();
                    if (manbutton.isChecked()) sex = "男";
                    else sex = "女";
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
                        if (!username.getText().toString().isEmpty() && !password.getText().toString().isEmpty()) {
                            if (password.getText().toString().equals(surepassword.getText().toString())) {
                                if (!address.getText().toString().isEmpty()) {
                                    if (!pnumber.getText().toString().isEmpty()) {
                                        try {
                                            Log.d("asdasd", sex + "========" + addr);
                                            sex = URLEncoder.encode(sex, "UTF-8");
                                            sex = URLEncoder.encode(sex, "UTF-8");
                                            addr = URLEncoder.encode(addr, "UTF-8");
                                            addr = URLEncoder.encode(addr, "UTF-8");
                                            Log.d("asdasd", sex + "========" + addr);
                                        } catch (UnsupportedEncodingException e) {
                                            e.printStackTrace();
                                        }
                                        String params = "?username=" + name + "&password=" + pwd + "&pnumber=" + phone + "&sex=" + sex + "&addr=" + addr + "&email=" + "";
                                        try {
                                            getJSONByVolley(Contants.BASEURL + "user/register" + params, null);
                                        } catch (UnsupportedEncodingException e) {
                                            e.printStackTrace();
                                        }
                                    } else {
                                        Toast.makeText(Register_ByPhone.this, "手机号不能为空", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Toast.makeText(Register_ByPhone.this, "城市不能为空", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(Register_ByPhone.this, "密码不一致", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(Register_ByPhone.this, "用户名,密码不能为空", Toast.LENGTH_SHORT).show();
                        }
                    }
                }else{
                    ((Throwable)data).printStackTrace();
                    Toast.makeText(Register_ByPhone.this,"验证码错误，请重新输入",Toast.LENGTH_SHORT).show();
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
}
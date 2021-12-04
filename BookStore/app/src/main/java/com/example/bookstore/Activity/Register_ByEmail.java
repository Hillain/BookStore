package com.example.bookstore.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SyncStatusObserver;
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

import cn.smssdk.EventHandler;

public class Register_ByEmail extends BaseActivity {
    private EditText username;
    private EditText password;
    private EditText emailcode;
    private EditText surepassword;
    private EditText email;
    private TextView address;
    private Button btn_Register;
    private Button btn_getCode;
    private TextView go_login;
    private CityPicker cityPicker;
    private String verifyCdoe;
    private RadioButton manbutton, wonmanbutton;
    private int T = 0;
    Handler mhandler = new Handler();

    protected void init(Context context) {
        super.init(context);
        setContentView(R.layout.register_email);
        username = findViewById(R.id.R_username);
        password = findViewById(R.id.R_password);
        surepassword = findViewById(R.id.surepassword);
        manbutton = findViewById(R.id.reg_man);
        emailcode = findViewById(R.id.email_code);
        btn_getCode = findViewById(R.id.btn_getCode);
        email = findViewById(R.id.R_email);
        wonmanbutton = findViewById(R.id.reg_woman);
        address = findViewById(R.id.R_province);
        go_login = findViewById(R.id.go_login);
        btn_Register = findViewById(R.id.btn_register);
        btn_Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = username.getText().toString();
                String pwd = password.getText().toString();
                String sex = null;
                String email_str = email.getText().toString();
                String addr = address.getText().toString();
                if (manbutton.isChecked()) sex = "男";
                else sex = "女";
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
                    if (!username.getText().toString().isEmpty() && !password.getText().toString().isEmpty()) {
                        if (password.getText().toString().equals(surepassword.getText().toString())) {
                            if (!address.getText().toString().isEmpty()) {
                                if (!email.getText().toString().isEmpty() || emailValidation(email.getText().toString())) {
                                    if(!emailcode.getText().toString().isEmpty()&&emailcode.getText().toString().equals(verifyCdoe)){//验证验证码是否正确的方法
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
                                        String params = "?username=" + name + "&password=" + pwd + "&pnumber=" + "" + "&sex=" + sex + "&addr=" + addr + "&email=" + email_str;
                                        try {
                                            getJSONByVolley(Contants.BASEURL + "user/register" + params, null);
                                        } catch (UnsupportedEncodingException e) {
                                            e.printStackTrace();
                                        }
                                        Toast.makeText(Register_ByEmail.this, "注册成功", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(Register_ByEmail.this, "验证码错误", Toast.LENGTH_SHORT).show();
                                        Log.d("asdasd", "verifyCdoe="+verifyCdoe+"emailcode="+emailcode);
                                    }
                                } else {
                                    Toast.makeText(Register_ByEmail.this, "邮箱输入异常", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(Register_ByEmail.this, "城市不能为空", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(Register_ByEmail.this, "密码不一致", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(Register_ByEmail.this, "用户名,密码不能为空", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        go_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(Register_ByEmail.this, Login.class);
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
                try {
                    if(!email.getText().toString().isEmpty() && emailValidation(email.getText().toString())) {//验证邮箱是否正确
                        Toast.makeText(Register_ByEmail.this,"已发送验证码，请等待",Toast.LENGTH_SHORT).show();
                        T = 60;
                        new Thread(new Register_ByEmail.ButtonTimer()).start();
                        getJSONByVolley(Contants.BASEURL + "user/getcode"+ "?email="+ email.getText().toString() , null);
                    }else {
                        Toast.makeText(Register_ByEmail.this, "邮箱格式不正确或为空！", Toast.LENGTH_SHORT).show();
                    }
                } catch (UnsupportedEncodingException e) {
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
                if(response.getString("success").length()>3)verifyCdoe = response.getString("success");
                else{
                    getToast("注册成功，请登录！");
                    Intent intent = new Intent();
                    intent.setClass(Register_ByEmail.this, Login.class);
                    startActivity(intent);
                    Register_ByEmail.this.finish();
                }
            }  else {
                getToast("注册失败，检查网络或更换用户名！");
            }
        } catch (JSONException e) {
        }
    }

    private void initcityPicker() {
        cityPicker = new CityPicker.Builder(Register_ByEmail.this)
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
    public boolean emailValidation(String email) {
        String strPattern = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";
        Pattern p = Pattern.compile(strPattern);
        Matcher m = p.matcher(email.trim());
        System.out.println(email+"====="+m.matches());
        return m.matches();
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
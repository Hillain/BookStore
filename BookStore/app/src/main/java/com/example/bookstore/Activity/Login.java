package com.example.bookstore.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.bookstore.MainActivity;
import com.example.bookstore.R;
import com.example.bookstore.Utils.Contants;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class Login extends BaseActivity {
    private EditText username;
    private EditText password;
    private Button btn_login;
    private Button btn_reg;
    private TextView forget;
    private TextView register;
    protected void init(Context context){
        super.init(context);
        setContentView(R.layout.login_activity);
        forget = findViewById(R.id.forget);
        username=findViewById(R.id.username);
        btn_login=findViewById(R.id.btn_login);
        password=findViewById(R.id.password);
        //btn_reg=(Button)findViewById(R.id.register);
        register=findViewById(R.id.register);
        /*btn_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(Login.this, Register_ByPhone.class);
                startActivity(intent);
            }
        });*/
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(Login.this, Regist_Chose.class);
                startActivity(intent);
            }
        });
        btn_login.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String name=username.getText().toString();
                String pwd= password.getText().toString();
                if(name.length()>0&pwd.length()>0){
                    String params = "?username=" + name + "&password=" + pwd;
                    try {
                        getJSONByVolley(Contants.BASEURL + "user/login" + params,null);
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                } else {
                    getToast("用户名和密码不能为空");
                }
            }
        });
        forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(Login.this, Password_Chose.class);
                startActivity(intent);
            }
        });
    }
    @Override
    protected void setJSONDataToView(JSONObject response) {
        try {
//            User user=new User();
//            user=new Gson().fromJson(response.toString(),User.class);
            Log.e("yang", "函数结尾1======= "+"12321312321321312312321312321312");
            if(!"0".equals(response.getString("username"))) {
                Log.e("yang", "函数结尾1======= "+"77777777777777777777777777777777");

                Intent intent = new Intent();
                intent.setClass(Login.this, MainActivity.class);
                startActivity(intent);
                Login.this.finish();
                Log.e("yang", "函数结尾1======= "+"12321312321321312312321312321312");
                saveUser(response.getString("id"),response.getString("nickname"),
                        response.getString("sex"),response.getString("sign"),
                        response.getString("password"),response.getString("pnumber"),
                        response.getString("email"));
                Log.d("123123123", response.getString("id")+" "+response.getString("nickname")+" "+
                        response.getString("sex")+" "+response.getString("sign")+" "+
                        response.getString("password")+" "+response.getString("pnumber")+" "+
                        response.getString("email"));
            }
            else {getToast("用户名或密码不正确");}
        }
        catch(JSONException e) {
        }
    }
    public void saveUser(String id,String nickname,String sex,
                         String sign,String password,String phone,String email) {
        Log.e("yang", "函数结尾1======= "+"232333333333333333333333333333333");
        SharedPreferences sharedPreferences = getSharedPreferences("userinfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();//获取编辑器
        Log.e("yang", "函数结尾1======= "+"232333333333333333333333333333333");
        //保存查询的用户信息
        int userid=Integer.parseInt(id);
        editor.putInt("id", userid);
        editor.putString("nickname", nickname);
        editor.putString("sex", sex);
        editor.putString("sign", sign);
        editor.putString("password", password);
        editor.putString("phone", phone);
        editor.putString("email", email);

        Log.e("Login_message","保存用户数据");
        editor.commit();//提交修改
    }

}

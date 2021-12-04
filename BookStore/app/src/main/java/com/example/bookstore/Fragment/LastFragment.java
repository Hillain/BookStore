package com.example.bookstore.Fragment;


import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.bookstore.Activity.Login;
import com.example.bookstore.Activity.Password_Chose;
import com.example.bookstore.Activity.Regist_Chose;
import com.example.bookstore.Adapter.ItemView;
import com.example.bookstore.BookApplication;
import com.example.bookstore.R;
import com.example.bookstore.Utils.Contants;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import static android.content.Context.MODE_PRIVATE;

public class LastFragment extends Fragment {
    protected RequestQueue requestQueue = null;
    private ItemView nickName;
    private ItemView sex;
    private ItemView signName;
    private ItemView editpwd;
    private ItemView editphone;
    private ItemView editemail;
    private ItemView version;
    private Button zhuxiao;
    private int id;
    private View view;
    private String username;
    public LastFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        BookApplication app = (BookApplication) getActivity().getApplication();
        requestQueue = app.requestQueue;
        view=inflater.inflate(R.layout.fragment_last, container, false);
        nickName= view.findViewById(R.id.nickName);
        sex = view.findViewById(R.id.sex);
        signName = view.findViewById(R.id.signName);
        editpwd = view.findViewById(R.id.editpwd);
        editphone = view.findViewById(R.id.editphone);
        editemail = view.findViewById(R.id.editemail);
        version = view.findViewById(R.id.version);
        zhuxiao = view.findViewById(R.id.btn_zhuxiao);
        SharedPreferences userSettings= getActivity().getSharedPreferences("userinfo", 0);
        id=userSettings.getInt("id",0);
        String nickname= userSettings.getString("nickname","");
        username = userSettings.getString("username","");
        String sex_str= userSettings.getString("sex","");
        String signname= userSettings.getString("sign","");
        String phone= userSettings.getString("phone","");
        String email= userSettings.getString("email","");
        Log.d("用户信息：", "id、nickname、sex、signname、password、phone、email"+
                id+" "+nickname+" "+sex_str+" "+signname+" "+phone+" "+email);
        nickName.setRightDesc(nickname);
        sex.setRightDesc(sex_str);
        signName.setRightDesc(signname);
        editphone.setRightDesc(phone);
        editemail.setRightDesc(email);

        //鼠标单击事件
        editpwd.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  editPwd();
              }
        });
        editphone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editPhone();
            }
        });
        editemail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editEmail();
            }
        });
        version.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                version();
            }
        });
        nickName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editNickName();
            }
        });
        sex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editSex();
            }
        });
        signName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editSignName();
            }
        });
        zhuxiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("userinfo", MODE_PRIVATE); sharedPreferences.edit().clear().commit();
                Intent intent = new Intent(getActivity(), Login.class);
                startActivity(intent);
                getActivity().finish();
            }
        });
        return view;
    }
    //一些单击方法
    public void editNickName(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // 通过LayoutInflater来加载一个xml的布局文件作为一个View对象
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_nick, null);
        // 设置我们自己定义的布局文件作为弹出框的Content
        builder.setView(view); //这个位置十分重要，只有位于这个位置逻辑才是正确的
        final AlertDialog dialog = builder.show();
        final EditText et_Threshold = view.findViewById(R.id.edThreshold);
        //et_Threshold.setText(mGamePadBitmap.setThresholdValue);
        view.findViewById(R.id.btn_confirm).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                String nickname=et_Threshold.getText().toString();
                if(nickname.length()<1){
                    Toast.makeText(getContext(), "还没有输入！", Toast.LENGTH_SHORT).show();
                }else{
                    nickName.setRightDesc(nickname);
                    try {
                        nickname = URLEncoder.encode(nickname, "UTF-8");
                        nickname = URLEncoder.encode(nickname, "UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    String url= Contants.BASEURL+"user/updateNick?id="+id+"&nickname="+nickname;
                    volleyupdata(url);
                    Toast.makeText(getContext(), "修改成功！", Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();
            }
        });
        view.findViewById(R.id.btn_cancle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //取消+关闭对话框 //写相关的服务代码
                dialog.dismiss();
            }
        });
    }
    public void editSex(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // 通过LayoutInflater来加载一个xml的布局文件作为一个View对象
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_sex, null);
        // 设置我们自己定义的布局文件作为弹出框的Content
        builder.setView(view); //这个位置十分重要，只有位于这个位置逻辑才是正确的
        final AlertDialog dialog = builder.show();
        final EditText et_Threshold = view.findViewById(R.id.edThreshold);
        //et_Threshold.setText(mGamePadBitmap.setThresholdValue);
        view.findViewById(R.id.btn_confirm).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                String sex_str=et_Threshold.getText().toString();
                if(sex_str.length()<1){
                    Toast.makeText(getContext(), "还没有输入！", Toast.LENGTH_SHORT).show();
                }else if(!sex_str.equals("男")&&!sex_str.equals("女")){
                    Toast.makeText(getContext(), "不可以不男不女哟！", Toast.LENGTH_SHORT).show();
                }else{
                    sex.setRightDesc(sex_str);
                    try {
                        sex_str = URLEncoder.encode(sex_str, "UTF-8");
                        sex_str = URLEncoder.encode(sex_str, "UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    String url= Contants.BASEURL+"user/updateSex?id="+id+"&sex="+sex_str;
                    volleyupdata(url);
                    dialog.dismiss();
                    Toast.makeText(getContext(), "修改成功！", Toast.LENGTH_SHORT).show();
                }
            }
        });
        view.findViewById(R.id.btn_cancle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //取消+关闭对话框 //写相关的服务代码
                dialog.dismiss();
            }
        });
    }
    public void editSignName(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // 通过LayoutInflater来加载一个xml的布局文件作为一个View对象
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_sign, null);
        // 设置我们自己定义的布局文件作为弹出框的Content
        builder.setView(view); //这个位置十分重要，只有位于这个位置逻辑才是正确的
        final AlertDialog dialog = builder.show();
        final EditText et_Threshold = view.findViewById(R.id.edThreshold);
        //et_Threshold.setText(mGamePadBitmap.setThresholdValue);
        view.findViewById(R.id.btn_confirm).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                String sign=et_Threshold.getText().toString();
                if(sign.length()<1){
                    Toast.makeText(getContext(), "还没有输入！", Toast.LENGTH_SHORT).show();
                }else{
                    signName.setRightDesc(sign);
                    try {
                        sign = URLEncoder.encode(sign, "UTF-8");
                        sign = URLEncoder.encode(sign, "UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    String url= Contants.BASEURL+"user/updateSign?id="+id+"&sign="+sign;
                    volleyupdata(url);
                }
                dialog.dismiss();
            }
        });
        view.findViewById(R.id.btn_cancle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //取消+关闭对话框 //写相关的服务代码
                dialog.dismiss();
            }
        });
    }
    public void editPwd(){
            final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            // 通过LayoutInflater来加载一个xml的布局文件作为一个View对象
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_pwd, null);
            // 设置我们自己定义的布局文件作为弹出框的Content
            builder.setView(view); //这个位置十分重要，只有位于这个位置逻辑才是正确的
            final AlertDialog dialog = builder.show();
            final EditText et_Threshold = view.findViewById(R.id.edThreshold);
            //et_Threshold.setText(mGamePadBitmap.setThresholdValue);
            view.findViewById(R.id.btn_confirm).setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v) {
                    String newpass=et_Threshold.getText().toString();
                    if(newpass.length()<1){
                        Toast.makeText(getContext(), "还没有输入！", Toast.LENGTH_SHORT).show();
                    }else{
                        String url= Contants.BASEURL+"user/updatePwd?username="+username+"&password="+newpass;
                        volleyupdata(url);
                        Toast.makeText(getContext(), "修改成功！", Toast.LENGTH_SHORT).show();
                    }
                    dialog.dismiss();
            }
        });
        view.findViewById(R.id.btn_cancle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //取消+关闭对话框 //写相关的服务代码
                dialog.dismiss();
            }
        });
    }
    public void editPhone(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // 通过LayoutInflater来加载一个xml的布局文件作为一个View对象
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_phone, null);
        // 设置我们自己定义的布局文件作为弹出框的Content
        builder.setView(view); //这个位置十分重要，只有位于这个位置逻辑才是正确的
        final AlertDialog dialog = builder.show();
        final EditText et_Threshold = view.findViewById(R.id.edThreshold);
        //et_Threshold.setText(mGamePadBitmap.setThresholdValue);
        view.findViewById(R.id.btn_confirm).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                String phone=et_Threshold.getText().toString();
                if(phone.length()<1){
                    Toast.makeText(getContext(), "还没有输入！", Toast.LENGTH_SHORT).show();
                }else{
                    String url= Contants.BASEURL+"user/updatePhone?id="+id+"&pnumber="+phone;
                    volleyupdata(url);
                    Toast.makeText(getContext(), "修改成功！", Toast.LENGTH_SHORT).show();
                }
                editphone.setRightDesc(phone);
                dialog.dismiss();
            }
        });
        view.findViewById(R.id.btn_cancle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //取消+关闭对话框 //写相关的服务代码
                dialog.dismiss();
            }
        });
    }
    public void editEmail(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // 通过LayoutInflater来加载一个xml的布局文件作为一个View对象
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_email, null);
        // 设置我们自己定义的布局文件作为弹出框的Content
        builder.setView(view); //这个位置十分重要，只有位于这个位置逻辑才是正确的
        final AlertDialog dialog = builder.show();
        final EditText et_Threshold = view.findViewById(R.id.edThreshold);
        //et_Threshold.setText(mGamePadBitmap.setThresholdValue);
        view.findViewById(R.id.btn_confirm).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                String email=et_Threshold.getText().toString();
                if(email.length()<1){
                    Toast.makeText(getContext(), "还没有输入！", Toast.LENGTH_SHORT).show();
                }else{
                    String url= Contants.BASEURL+"user/updateEmail?id="+id+"&email="+email;
                    volleyupdata(url);
                    Toast.makeText(getContext(), "修改成功！", Toast.LENGTH_SHORT).show();
                }
                editemail.setRightDesc(email);
                dialog.dismiss();
            }
        });
        view.findViewById(R.id.btn_cancle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //取消+关闭对话框 //写相关的服务代码
                dialog.dismiss();
            }
        });
    }
    public void version(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // 通过LayoutInflater来加载一个xml的布局文件作为一个View对象
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_version, null);
        // 设置我们自己定义的布局文件作为弹出框的Content
        builder.setView(view); //这个位置十分重要，只有位于这个位置逻辑才是正确的
        final AlertDialog dialog = builder.show();
        //et_Threshold.setText(mGamePadBitmap.setThresholdValue);
        view.findViewById(R.id.btn_confirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //取消+关闭对话框 //写相关的服务代码
                dialog.dismiss();
            }
        });
    }


    //与服务器连接的部分代码
    private void volleyupdata(String url) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                try {
                    if(jsonObject.getString("success").equals("1")){
                        Toast.makeText(getActivity(),"修改成功!",Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getActivity(),"修改失败!",Toast.LENGTH_LONG).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(getActivity(),"请求失败!",Toast.LENGTH_LONG).show();
            }
        });
        requestQueue.add(jsonObjectRequest);
    }
}

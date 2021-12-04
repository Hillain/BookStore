package com.example.bookstore.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.example.bookstore.MainActivity;
import com.example.bookstore.R;


public class Welcome extends AppCompatActivity {
   private boolean isFirstUse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         SharedPreferences thevalue = getSharedPreferences("isFirstUse", Context.MODE_PRIVATE);
         isFirstUse = thevalue.getBoolean("isFirstUse", true);
        /**
         *如果用户不是第一次使用则直接调转到显示界面,否则调转到引导界面
         */
        if (isFirstUse) {
            //实例化Editor对象
           SharedPreferences.Editor editor = thevalue.edit();
            //存入数据
            editor.putBoolean("isFirstUse", false);
            //提交修改
            editor.commit();
           Intent intent = new Intent(this, WelcomeGuideActivity.class);
            startActivity(intent);
            /*使用finish将该activity进行销毁，否则，在按下手机返回键时，会返回至启动页*/
            finish();

         //   Log.v("debug22", "这里为true");
        } else {
            setContentView(R.layout.activity_welcome);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    /*2秒后进入主页*/
                    enterHomeActivity();
                }
            }, 2000);
        }

    }
    private void enterHomeActivity () {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}

/**
 　　（1）使用Activity类的getSharedPreferences方法获得SharedPreferences对象，其中存储key-value的文件的名称由getSharedPreferences方法的第一个参数指定，第二个参数指定访问应用程序私有文件的权限。

 　　（2）使用SharedPreferences接口的edit获得SharedPreferences.Editor对象。

 　　（3）通过SharedPreferences.Editor接口的putXxx方法保存key-value对。其中Xxx表示不同的数据类型。例如：字符串类型的value需要用putString方法。

 　　（4）通过SharedPreferences.Editor接口的commit方法保存key-value对。commit方法相当于数据库事务中的提交（commit）操作。
 **/
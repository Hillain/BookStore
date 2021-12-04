package com.example.bookstore.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextPaint;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.bookstore.BookApplication;
import com.example.bookstore.MainActivity;
import com.example.bookstore.R;
import com.example.bookstore.Utils.Contants;
import com.example.bookstore.pojo.Book;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;

public class BookInfo extends AppCompatActivity {
    protected RequestQueue requestQueue = null;
    private BookApplication app;
    private ImageView BookPic;
    private TextView BookName;
    private TextView Bookprice;
    private TextView Bookauthor;
    private TextView Bookpublish;
    private TextView Bookinfo;
    private Button add_cars;
    private Button btn_add;
    private Button btn_reduce;
    private EditText num;
    private TextView bookstotal;
    private Float danjia;
    int user_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_info);
        app=(BookApplication)getApplication();
        requestQueue =app.requestQueue;
        SharedPreferences userSettings= getSharedPreferences("userinfo", 0);
        user_id= userSettings.getInt("id",0);
        Log.e("pp","这里。。。。。。。"+user_id);
        getGoodDetail();
    }
    public void getGoodDetail() {
        Bundle bundle = this.getIntent().getExtras();//获取Bundle
        final Book book = (Book) bundle.getSerializable("GoodObj");//获取Bundle中的商品对象
        BookName = (TextView) this.findViewById(R.id.sm);
        BookName.setText(book.getBkname());
        //商品图片
         BookPic = (ImageView) this.findViewById(R.id.tp);
        try {
            URL picUrl = new URL(Contants.BASEURL+book.getBurl().toString());
            Bitmap pngBM = BitmapFactory.decodeStream(picUrl.openStream());
            BookPic.setImageBitmap(pngBM);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Bookprice = (TextView) this.findViewById(R.id.jg);
        Bookprice.setText(book.getBprice().toString());
        danjia=book.getBprice();
        Bookauthor = (TextView) this.findViewById(R.id.zz);
        Bookauthor.setText(book.getBauthor());
        Bookpublish = (TextView) this.findViewById(R.id.cbs);
        Bookpublish.setText(book.getBpublish());
        Bookinfo = (TextView) this.findViewById(R.id.jj);
        Bookinfo.setText(book.getBinfo());
        bookstotal=findViewById(R.id.books_total);
        bookstotal.setText(book.getBprice().toString());
        Changnum();
        add_cars=findViewById(R.id.add_cars);
        add_cars.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(user_id==0){
                    Toast.makeText(getApplication(),"请先登录!",Toast.LENGTH_LONG).show();
                }else {
                    float total = book.getBprice() * Integer.parseInt(num.getText().toString());
                    String zjg = String.valueOf(total);
                    String bookName = book.getBkname();
                    try {
                        bookName = URLEncoder.encode(bookName, "UTF-8");
                        bookName = URLEncoder.encode(bookName, "UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    String url = Contants.BASEURL + "Book/inscar?id="+user_id+"&bid=" + String.valueOf(book.getBookid()) + "&bname=" + bookName + "&bnumber=" + num.getText() + "&total=" + zjg + "&burl=" + book.getBurl();
                    insetcar(url);

                }
            }
        });
    }

public void insetcar(String url){
    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
        @Override
        public void onResponse(JSONObject jsonObject) {

            try {
                if(jsonObject.getString("success").equals("1")){
                    Toast.makeText(getApplication(),"添加成功!",Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();

                } else {
                    Toast.makeText(getApplication(),"添加失败!",Toast.LENGTH_LONG).show();
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            //订单删除成功
        }

    }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError volleyError) {
            Toast.makeText(getApplication(),"请求失败!",Toast.LENGTH_LONG).show();
        }
    });
    requestQueue.add(jsonObjectRequest);
}

    private void Changnum() {
        btn_add=findViewById(R.id.btn_add);
        btn_reduce=findViewById(R.id.btn_reduce);
        num=findViewById(R.id.edit_num);

        final float  to =danjia;
        btn_reduce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int now= Integer.parseInt(String.valueOf(num.getText()));
                double prices = Double.parseDouble(String.valueOf(bookstotal.getText()));

                if(now<2){
                    Toast.makeText(getApplication(),"数量最小值为1！",Toast.LENGTH_LONG).show();
                }else {
                    String reduce= String.valueOf(now-1);
                    String totalreduce= String.valueOf(prices-to);
                    num.setText(reduce);
                    bookstotal.setText(totalreduce);
                }
            }
        });
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int now= Integer.parseInt(String.valueOf(num.getText()));
                double prices = Double.parseDouble(String.valueOf(bookstotal.getText()));
                String add= String.valueOf(now+1);
                String totalreduce= String.valueOf(prices+to);
                num.setText(add);
                bookstotal.setText(totalreduce);
            }
        });
    }
}

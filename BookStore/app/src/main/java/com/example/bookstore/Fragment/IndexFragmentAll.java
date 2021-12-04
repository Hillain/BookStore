package com.example.bookstore.Fragment;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.support.v4.util.LruCache;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.bookstore.Activity.BookInfo;
import com.example.bookstore.BookApplication;
import com.example.bookstore.R;
import com.example.bookstore.Utils.Contants;
import com.example.bookstore.pojo.Book;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.io.InputStream;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class IndexFragmentAll extends Fragment {
    String b;
    private TextView fist;
    private ListView listView;
    private View view;
    private List<Book> bookList;
    protected RequestQueue requestQueue = null;
    protected Context context;
    public IndexFragmentAll() {
        // Required empty public constructor
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        BookApplication app = (BookApplication) getActivity().getApplication();
        requestQueue = app.requestQueue;
        context = this.getActivity();
        view = inflater.inflate(R.layout.fragment_index_fragment_all, container, false);
        listView = (ListView) view.findViewById(R.id.Book_list);
        int a = getArguments().getInt("cid");
        if (a == 0) {
            b = "";
        } else {
            b = "?lb=" + a;
        }
        Integer shop_id = getArguments().getInt("shop_id");
        getJSONArrayByVolley(Contants.BASEURL + "Book/GetAllBook" + b);
        return view;
    }
    public void getJSONArrayByVolley(String url) {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        setJSONArrayToView(response);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError arg0) {
                        Toast.makeText(context, "网络连接失败！", Toast.LENGTH_SHORT).show();
                    }
                });
        requestQueue.add(jsonArrayRequest);
    }

    protected void setJSONArrayToView(JSONArray data) {
        Gson gson = new Gson();
        SimpleAdapter adapter = new SimpleAdapter(getActivity(), getBookList(data),
                R.layout.listiew_bookitem, new String[]{"img", "name", "money",
                "zhe"}, new int[]{R.id.tripImg, R.id.tripTitle,
                R.id.tripSegName, R.id.tripProv});
        listView.setAdapter(adapter);


//        setViewValue的返回值是true，则表示绑定已经完成,
//        将不再调用系统默认的绑定实现。如果返回值为false，视图将按以下顺序绑定数据：
        adapter.setViewBinder(new SimpleAdapter.ViewBinder() {
            public boolean setViewValue(View arg0, Object arg1,
                                        String textRepresentation) {
                if ((arg0 instanceof ImageView) & (arg1 instanceof Bitmap)) {// 判断是否为我们要处理的对象
                    ImageView imageView = (ImageView) arg0;
                    Bitmap bitmap = (Bitmap) arg1;
                    imageView.setImageBitmap(bitmap);
                    Log.d("aaa", "对的啊");
                    return true;
                } else {
                    Log.d("aaa", "错的啊");
                    return false;

                }

            }
        });
        //单机列表响应事间
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Book book = bookList.get(position);// 获取当前列表项选中的商品
                Log.e("当前", book.getBkname());
                Intent it = new Intent();//创建Intent对象
                Bundle bundle = new Bundle();//创建Bundle对象
                it.setClass(getActivity(), BookInfo.class);
                bundle.putSerializable("GoodObj", (Serializable) book);
                it.putExtras(bundle);
                startActivity(it);
             //   getActivity().finish();
            }
        });
    }

    public List<Map<String, Object>> getBookList(JSONArray data) {
        //Book book=new Book();
        Gson gson = new Gson();
        bookList = gson.fromJson(data.toString(), new TypeToken<List<Book>>() {
        }.getType());
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < bookList.size(); i += 1) {
            Map<String, Object> map = new HashMap<String, Object>();
            Book book = bookList.get(i);

       //  showImageByNetworkImageView("http://10.204.0.26:8080" + book.getBurl().toString() + ".jpg");
            try {
                StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());
                URL picUrl = new URL(Contants.BASEURL + book.getBurl().toString());
                HttpURLConnection conn = (HttpURLConnection) picUrl.openConnection();
                conn.setConnectTimeout(6000);
                conn.setDoInput(true);
                conn.setUseCaches(true);
                conn.connect();
                InputStream is = conn.getInputStream();
                Bitmap pngBM = BitmapFactory.decodeStream(is);
                map.put("img", pngBM);
            } catch (Exception e) {
                e.printStackTrace();
                Log.d("aaa", "错的");
            }

            map.put("name", "图书名称：" + book.getBkname());
            map.put("zhe", " 图书价格：" + "￥" + book.getBprice());
            map.put("money", "库存：" + book.getBstock());

            list.add(map);
        }
        return list;
    }


}
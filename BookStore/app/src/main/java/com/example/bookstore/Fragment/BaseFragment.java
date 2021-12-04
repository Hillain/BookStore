package com.example.bookstore.Fragment;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.bookstore.BookApplication;
import com.example.bookstore.MainActivity;
import com.example.bookstore.R;

import org.json.JSONArray;
import org.json.JSONObject;


public abstract class BaseFragment extends Fragment {
    protected int user_id;
    protected String username,userpass;
    protected RequestQueue requestQueue = null;
    protected Context context;

    protected abstract View init(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState);

    protected void setJSONDataToView(String url,JSONObject data) {
    }

    protected void setJSONArrayToView(JSONArray data) {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        SharedPreferences preferences = getActivity().getSharedPreferences("userInfo",
//                Activity.MODE_PRIVATE);
//        user_id = preferences.getInt("user_id", 0) ;
//        username = preferences.getString("username", "");
//        userpass = preferences.getString("userpass", "");
        BookApplication app=(BookApplication)getActivity().getApplication();
        requestQueue=app.requestQueue;
        context = this.getActivity();
        View v = init(inflater, container, savedInstanceState);
        return v;
    }

    /**
     * 利用Volley获取String数据，格式为JSON形式
     */
    public void getJSONByVolley(String url) {
        final String flag=url;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        setJSONDataToView(flag,response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError arg0) {
                      getNetError();
                    }
                });
        requestQueue.add(jsonObjectRequest);
    }
    public void getJSONByVolley(String url,JSONObject params) {
            final String flag = url;
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                    Request.Method.GET,
                    url,
                    params,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            setJSONDataToView(flag, response);
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError arg0) {
                            arg0.printStackTrace();
                        }
                    });
            requestQueue.add(jsonObjectRequest);
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
                      getNetError();
                    }
                });
        requestQueue.add(jsonArrayRequest);
    }
    public void loadImageByVolley(String imgurl,ImageView imgView){
        final LruCache<String, Bitmap> lruCache = new LruCache<String, Bitmap>(20);
        ImageLoader.ImageCache imageCache = new ImageLoader.ImageCache() {
            @Override
            public void putBitmap(String key, Bitmap value) {
                lruCache.put(key, value);
            }
            @Override
            public Bitmap getBitmap(String key) {
                return lruCache.get(key);
            }
        };
        ImageLoader imageLoader = new ImageLoader(requestQueue, imageCache);
      //  ImageLoader.ImageListener listener = ImageLoader.getImageListener(imgView, R.drawable.error,R.drawable.error);
      //  imageLoader.get(imgurl, listener);
    }
    public void changeFrament(Fragment fragment, Bundle bundle, String tag) {

        FragmentManager fgManager = ((MainActivity) context).getFragmentManager();
        for (int i = 0, count = fgManager.getBackStackEntryCount(); i < count; i++) {
            fgManager.popBackStack();
        }
        FragmentTransaction fg = fgManager.beginTransaction();
        fragment.setArguments(bundle);
      // fg.add(R.id.fragmentRoot, fragment, tag);
        fg.addToBackStack(tag);
        fg.commit();
    }

    public void getToast(String text) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }

    public void getNetError() {
        getToast("亲~网络连接失败！");
    }
}

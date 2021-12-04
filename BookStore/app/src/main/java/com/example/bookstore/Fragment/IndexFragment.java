package com.example.bookstore.Fragment;



import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.bookstore.Adapter.NewsPageFragmentAdapter;
import com.example.bookstore.BookApplication;
import com.example.bookstore.R;
import com.example.bookstore.Utils.Contants;
import com.example.bookstore.pojo.Book;
import com.example.bookstore.pojo.Category;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class IndexFragment extends Fragment implements ViewPager.OnPageChangeListener {
    private View view=null;
    private RadioGroup rgChannel=null;
    private ViewPager viewPager;
    private HorizontalScrollView hvChannel=null;
    protected RequestQueue requestQueue = null;
    private Context context;
    private List<Category> bookList;
    public IndexFragment() {
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        BookApplication app=(BookApplication)getActivity().getApplication();
        requestQueue=app.requestQueue;
        context=this.getActivity();
        // Inflate the layout for this fragment
        if(view==null){
            view=inflater.inflate(R.layout.fragment_index, null);
            rgChannel=(RadioGroup)view.findViewById(R.id.rgChannel);
            viewPager=(ViewPager)view.findViewById(R.id.vpNewsList);
            hvChannel=(HorizontalScrollView)view.findViewById(R.id.hvChannel);
            rgChannel.setOnCheckedChangeListener(
                    new RadioGroup.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(RadioGroup group,
                                                     int checkedId) {
                            viewPager.setCurrentItem(checkedId);
                            Log.e("aaa","事件"+checkedId);
                        }
                    });
            initTab(inflater);
            initViewPager();
        }
        ViewGroup parent=(ViewGroup)view.getParent();
        if(parent!=null){
            parent.removeView(view);
        }
        return view;
    }
    private List<Fragment> newsChannelList=new ArrayList<Fragment>();
    private NewsPageFragmentAdapter adapter;
    private void initViewPager(){
        final List<String> channelList=new ArrayList<String>();
        channelList.add("全部图书");
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Contants.BASEURL+"Book/GetBookCategory",
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Gson gson=new Gson();
                        bookList=gson.fromJson(response.toString(),new TypeToken<List<Category>>(){}.getType());
                        for (int i = 0; i <bookList.size(); i += 1) {
                            // Map<String, Object> map = new HashMap<String, Object>();
                            Category category = bookList.get(i);
                            channelList.add(category.getlx());
                        }
                             addFragment(channelList);
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
    //动态添加Fragment
    private void addFragment(List<String> channelList){
        for(int i=0;i<channelList.size();i++){
            IndexFragmentAll indexFragmentAll=new IndexFragmentAll();

            Bundle bundle=new Bundle();
            bundle.putInt("cid",i);
            indexFragmentAll.setArguments(bundle);
            newsChannelList.add(indexFragmentAll);
            // newsChannelList.add(new IndexFragmentHistory());
        }
        adapter=new NewsPageFragmentAdapter(super.getActivity().getSupportFragmentManager(), newsChannelList);
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(2);
        viewPager.setCurrentItem(0);
        viewPager.setOnPageChangeListener(this);
    }
    private void initTab(final LayoutInflater inflater){
        final List<String> channelList=new ArrayList<String>();
        channelList.add("全部图书");
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Contants.BASEURL+"Book/GetBookCategory",
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Gson gson=new Gson();
                        bookList=gson.fromJson(response.toString(),new TypeToken<List<Category>>(){}.getType());
                        for (int i = 0; i <bookList.size(); i += 1) {
                            // Map<String, Object> map = new HashMap<String, Object>();
                            Category category = bookList.get(i);
                            channelList.add(category.getlx());
                        }
                        for(int i=0;i<channelList.size();i++){
                            RadioButton rb=(RadioButton)inflater.
                                    inflate(R.layout.tab_rb, null);
                            rb.setId(i);
                            rb.setText(channelList.get(i).toString());
                            RadioGroup.LayoutParams params=new
                                    RadioGroup.LayoutParams(RadioGroup.LayoutParams.WRAP_CONTENT,
                                    RadioGroup.LayoutParams.WRAP_CONTENT);
                            rgChannel.addView(rb,params);
                        }
                        rgChannel.check(0);
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
    @Override
    public void onPageSelected(int idx) {
        setTab(idx);
    }

    private void setTab(int idx){
        RadioButton rb=(RadioButton)rgChannel.getChildAt(idx);
        rb.setChecked(true);
        int left=rb.getLeft();
        int width=rb.getMeasuredWidth();
        DisplayMetrics metrics=new DisplayMetrics();
        super.getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int screenWidth=metrics.widthPixels;
        int len=left+width/2-screenWidth/2;
        hvChannel.smoothScrollTo(len, 0);
    }

    @Override
    public void onPageScrollStateChanged(int arg0) {
    }
    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {
    }

}



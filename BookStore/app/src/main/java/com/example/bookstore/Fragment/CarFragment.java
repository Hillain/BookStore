package com.example.bookstore.Fragment;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.bookstore.Activity.Login;
import com.example.bookstore.BookApplication;
import com.example.bookstore.MainActivity;
import com.example.bookstore.R;
import com.example.bookstore.Utils.Contants;
import com.example.bookstore.Utils.GetImageByUrl;
import com.example.bookstore.pojo.Book;
import com.example.bookstore.pojo.Car;
import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class CarFragment extends Fragment {
    String url_getAllOrders;
    private ProgressBar pb;
    private TextView addorder;
    private TextView totalpri;
    private View view = null;
    private ListView news_list;
    private List<Car> Listcar;
    protected RequestQueue requestQueue = null;
    protected Context context;
    float totalprice=0;
    Map<String,String> map=new HashMap<String,String>();
    public CarFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        BookApplication app = (BookApplication) getActivity().getApplication();
        requestQueue = app.requestQueue;
        context = this.getActivity();

        if (view == null) {
            view = inflater.inflate(R.layout.fragment_car, null);
            //绑定listview控件
            news_list = view.findViewById(R.id.car_list);
            int userid= readUser();
            url_getAllOrders= Contants.BASEURL+"Book/GetCar?id="+userid;
            volleyGetAllOrders(url_getAllOrders);
        }
        Log.e("ooo",String.valueOf(totalprice));
        pb=view.findViewById(R.id.pb);
        addorder=view.findViewById(R.id.tv_settlement);
        totalpri=view.findViewById(R.id.total);
       // totalpri.setText(map.get("total"));
        addorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
              String time=df.format(new Date());
              String a=map.get("total");
              String b=map.get("bname").substring(0,map.get("bname").length()-1);
              String c=map.get("number").substring(0,map.get("number").length()-1);
              int id=readUser();
              Log.e("uuu",a+b+c);
              String burl="img/jiben.jpg";
              String url=Contants.BASEURL+"Book/insertorder?uid="+id+"&bname="+b+"&bnumber="+c+"&oprice="+a+"&burl="+burl;
              Log.e("cv", url );

                new MyAsnTask().execute(url);

            }
        });
        return view;
    }

    private void volleyGetAllOrders(String url) {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Gson gson = new Gson();
                        Listcar= gson.fromJson(response.toString(), new TypeToken<List<Car>>() {
                        }.getType());
                        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
                        for (int i = 0; i < Listcar.size(); i += 1) {
                            Map<String, Object> map = new HashMap<String, Object>();
                            Car car = Listcar.get(i);
                            map.put("bid",car.getBid());
                            map.put("name", car.getBname());
                            map.put("number",car.getBnumber());
                            map.put("price", car.getTotal());
                            //System.out.println("url======="+car.getBurl());
                            map.put("url",car.getBurl());
                            list.add(map);
                        }
                        MyAdapter myAdapter = new MyAdapter(getContext(),list);
                        news_list.setAdapter(myAdapter);
                        totalpri.setText(map.get("total"));

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

    public final class ViewHolder{
        public TextView carname,carnum,carprice;
        public Button Deletecar;
        public ImageView img;

    }

    public class MyAdapter extends BaseAdapter {
        private final List<Map<String,Object>> list;
        private LayoutInflater inflater;


        public MyAdapter(Context context, List<Map<String,Object>> list){
            this.inflater = LayoutInflater.from(context);
            this.list = list;
            get();
        }
       public void get(){
           String bname="";
           String bnumber="";
            for(int i=0;i<getCount();i++){
               totalprice+=Float.parseFloat(list.get(i).get("price").toString());
               bname+=list.get(i).get("name").toString()+",";
               bnumber+=list.get(i).get("number")+",";
            }
            map.put("total",String.valueOf(totalprice));
            map.put("bname",bname);
            map.put("number",bnumber);
        }
        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(final int i, View view, ViewGroup viewGroup) {
            ViewHolder holder = null;
            if (view == null){
                holder = new ViewHolder();
                view = inflater.inflate(R.layout.listview_caritem, null);
                holder.carname = view.findViewById(R.id.carname);
                holder.carnum = view.findViewById(R.id.carnum);
                holder.carprice = view.findViewById(R.id.carprice);
                Log.e("ttt","实化空间");
                holder.Deletecar = view.findViewById(R.id.Deletecar);
                holder.img = view.findViewById(R.id.carImg);
                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }
            String num=String.valueOf(list.get(i).get("number"));
            GetImageByUrl getImageByUrl = new GetImageByUrl();
            System.out.println("url="+list.get(i).get("url"));
            getImageByUrl.setImage(holder.img, Contants.BASEURL+list.get(i).get("url"));
            holder.carname.setText("书籍名称: " +list.get(i).get("name").toString());
            holder.carnum.setText("购买数量:" +num);
            holder.carprice.setText("价格: " + list.get(i).get("price").toString());
            //get(list.get(i).get("price").toString());
           // totalprice+=Float.parseFloat(list.get(i).get("price").toString());
            holder.Deletecar.setTag(i);
            holder.Deletecar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String bId = list.get(i).get("bid").toString();
                    String url = Contants.BASEURL +"Book/deletecar?id="+bId;
                    volleyDeleteOrder(url);
                }
            });
            return view;
        }
    }

    /**
     * 读取用户信息
     * @return
     */
    private int readUser(){
        SharedPreferences settings = getActivity().getSharedPreferences("userinfo", 0);
        return settings.getInt("id", 0);
    }

    private void volleyDeleteOrder(String url){
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {

                try {
                   if(jsonObject.getString("success").equals("1")){
                       Toast.makeText(getActivity(),"删除成功!",Toast.LENGTH_LONG).show();
                       volleyGetAllOrders(url_getAllOrders);
                   } else {
                       Toast.makeText(getActivity(),"删除失败!",Toast.LENGTH_LONG).show();
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

   public void insertorder(final String url){
       JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
               Request.Method.POST,
               url,
               null,
               new Response.Listener<JSONObject>() {
                   @Override
                   public void onResponse(JSONObject response) {
                     //  Toast.makeText(getActivity(),"下单成功!",Toast.LENGTH_LONG).show();
                       clearcar(Contants.BASEURL+"Book/clearcar?id="+readUser());

                   }
               },
               new Response.ErrorListener() {
                   @Override
                   public void onErrorResponse(VolleyError arg0) {
                       Toast.makeText(getActivity(),"连接失败!",Toast.LENGTH_LONG).show();
                   }
               });
       requestQueue.add(jsonObjectRequest);

    }

    public void clearcar(String url){
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                volleyGetAllOrders(url_getAllOrders);
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(getActivity(),"请求失败!",Toast.LENGTH_LONG).show();
            }
        });
        requestQueue.add(jsonObjectRequest);
    }

    class MyAsnTask extends AsyncTask<String,Integer,String >{

        @Override
        protected String doInBackground(String... strings) {
            String ret=null;
            Integer bushu,sleeptime;
            bushu=1;
            sleeptime=500;
            String url=strings[0];
            insertorder(url);
            for(Integer i=1;i<=10;i+=bushu){
                publishProgress(i);
                SystemClock.sleep(sleeptime);
            }
            return "下单成功";
        }

        @Override
        protected void onPreExecute() {
            pb.setProgress(0);
            addorder.setEnabled(false);
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            int p=pb.getMax()/10*values[0];
            pb.setProgress(p);
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String s) {
            addorder.setEnabled(true);
            Toast.makeText(getActivity(),s,Toast.LENGTH_LONG).show();
            Intent intent = new Intent(getActivity(), MainActivity.class);
            startActivity(intent);
        }
    }

}

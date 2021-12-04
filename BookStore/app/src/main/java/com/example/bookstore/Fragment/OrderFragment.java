package com.example.bookstore.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.BaseAdapter;
        import android.widget.Button;
        import android.widget.ImageView;
        import android.widget.ListView;
        import android.widget.TextView;
        import android.widget.Toast;

        import com.android.volley.Request;
        import com.android.volley.RequestQueue;
        import com.android.volley.Response;
        import com.android.volley.VolleyError;
        import com.android.volley.toolbox.JsonArrayRequest;
        import com.android.volley.toolbox.JsonObjectRequest;
        import com.example.bookstore.BookApplication;
import com.example.bookstore.R;
import com.example.bookstore.Utils.Contants;
import com.example.bookstore.Utils.GetImageByUrl;
import com.example.bookstore.pojo.Order;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class OrderFragment extends Fragment {
    String url_getAllOrders;
    private TextView addorder;
    private TextView totalpri;
    private View view = null;
    private ListView news_list;
    private List<Order> Listorder;
    protected RequestQueue requestQueue = null;
    protected Context context;
    float totalprice=0;
    Map<String,String> map=new HashMap<String,String>();
    public OrderFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        BookApplication app = (BookApplication) getActivity().getApplication();
        requestQueue = app.requestQueue;
        context = this.getActivity();

            view = inflater.inflate(R.layout.fragment_order, null);
            news_list = view.findViewById(R.id.order_list);
            int userid= readUser();
            url_getAllOrders= Contants.BASEURL+"Book/GetOrder?id="+userid;
            volleyGetAllOrders(url_getAllOrders);
           return view;
    }

    public void volleyGetAllOrders(String url) {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Gson gson = new Gson();
                        Listorder= gson.fromJson(response.toString(), new TypeToken<List<Order>>() {
                        }.getType());
                        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
                        Log.e("qqqqq",Listorder.toString());
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        for (int i = 0; i < Listorder.size(); i += 1) {
                            Map<String, Object> map = new HashMap<String, Object>();
                            Order order = Listorder.get(i);
                            map.put("oid",order.getOid());
                            map.put("date",  sdf.format(order.getOdate()));
                            map.put("name",order.getBname());
                            map.put("number", order.getBnumber());
                            map.put("price",order.getOprice());
                            map.put("url",order.getBurl());
                            list.add(map);
                        }

                          Log.e("dddddd",list.toString());
                       MyAdapter myAdapter = new MyAdapter(getContext(),list);
                       news_list.setAdapter(myAdapter);
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
        public TextView orderid,orderdate,ordername,ordernum,orderprice;
        public Button Deleteorder;
        public ImageView img;

    }

    public class MyAdapter extends BaseAdapter {
        private final List<Map<String,Object>> list;
        private LayoutInflater inflater;


        public MyAdapter(Context context, List<Map<String,Object>> list){
            this.inflater = LayoutInflater.from(context);
            this.list = list;

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
                view = inflater.inflate(R.layout.listview_orderitem, null);
                holder.img=view.findViewById(R.id.o_image);
                holder.orderid = view.findViewById(R.id.o_id);
                holder.orderdate = view.findViewById(R.id.o_time);
                holder.ordername = view.findViewById(R.id.o_name);
                holder.ordernum = view.findViewById(R.id.o_number);
                holder.orderprice = view.findViewById(R.id.o_total);
                holder.Deleteorder = view.findViewById(R.id.o_delete);
                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }

           GetImageByUrl getImageByUrl = new GetImageByUrl();
           getImageByUrl.setImage(holder.img, Contants.BASEURL+list.get(i).get("url"));
            holder.orderid.setText(list.get(i).get("oid").toString());
            holder.orderdate.setText(list.get(i).get("date").toString());
            holder.ordername.setText(list.get(i).get("name").toString());
            holder.ordernum.setText(list.get(i).get("number").toString());
            holder.orderprice.setText(list.get(i).get("price").toString());
            holder.Deleteorder.setTag(i);
            holder.Deleteorder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String oId = list.get(i).get("oid").toString();
                    String url = Contants.BASEURL +"Book/deleteorder?id="+oId;
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

}

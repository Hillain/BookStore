//package com.example.bookstore.Adapter;
//
//import android.content.Context;
//import android.content.DialogInterface;
//import android.os.AsyncTask;
//import android.support.v7.app.AlertDialog;
//import android.text.Editable;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.ImageView;
//import android.widget.ListView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.net.HttpURLConnection;
//import java.net.MalformedURLException;
//import java.net.URL;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.Random;
//
//public class ListViewAdapter extends BaseAdapter {
//    //static List<ImageView> imageViewList = new ArrayList<ImageView>();
//
//    private List<Map<String, Object>> data;
//    private LayoutInflater layoutInflater;
//    private Context context;
//
//    public ListViewAdapter(Context context, List<Map<String, Object>> data) {
//        this.context = context;
//        this.data = data;
//        this.layoutInflater = LayoutInflater.from(context);
//    }
//
//    /**
//     * 组件集合，对应list.xml中的控件
//     *
//     * @author Administrator
//     */
//    public final class ProductInfo {
//        public ImageView image;
//        public TextView productName;
//        public TextView productPrice;
//        public TextView productStock;
//        public Button buttonView;
//    }
//
//    @Override
//    public int getCount() {
//        return data.size();
//    }
//
//    /**
//     * 获得某一位置的数据
//     */
//    @Override
//    public Object getItem(int position) {
//        return data.get(position);
//    }
//
//    /**
//     * 获得唯一标识
//     */
//    @Override
//    public long getItemId(int position) {
//        return position;
//    }
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        ProductInfo product = null;
//        if (convertView == null) {
//            product = new ProductInfo();
//            //获得组件，实例化组件
//            convertView = layoutInflater.inflate(R.layout.listview, null);
//            product.image = (ImageView) convertView.findViewById(R.id.image);
//            product.productName = (TextView) convertView.findViewById(R.id.productName);
//            product.productPrice = (TextView) convertView.findViewById(R.id.productPrice);
//            product.productStock = (TextView) convertView.findViewById(R.id.productStock);
//            product.buttonView = (Button) convertView.findViewById(R.id.buttonView);
//            convertView.setTag(product);
//        } else {
//            product = (ProductInfo) convertView.getTag();
//        }
//        //绑定数据
//        GetImageByUrl getImageByUrl = new GetImageByUrl();
//        getImageByUrl.setImage(product.image, (String) data.get(position).get("productIconUrl"));
//        product.productName.setText((String) data.get(position).get("productName"));
//        product.productPrice.setText((String) data.get(position).get("productPrice"));
//        product.productStock.setText((String) data.get(position).get("productStock"));
//        product.buttonView.setTag((String) data.get(position).get("productId"));
//        product.buttonView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(final View v) {
//                final EditText et = new EditText(JudgeResult.alertActivity);
//                AlertDialog.Builder builder = new AlertDialog.Builder(JudgeResult.alertActivity);
//                builder.setView(et);
//                builder.setTitle("请输入数量");     //设置对话框标题
//                builder.setIcon(android.R.drawable.btn_star);      //设置对话框标题前的图标
//                builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog_pwd, int which) {
//
//                        String str = et.getText() + "";
//                        if (str == "" || str == null) {
//                            Toast.makeText(JudgeResult.toaskApplication, "输入错误，加入失败！！!", Toast.LENGTH_LONG).show();
//                        } else {
//                            MyTask myTask = new MyTask();
//                            myTask.execute((String) v.getTag(), et.getText() + "");
//                        }
//
//
//                        //Toast.makeText(JudgeResult.alertActivity, "dialog_pwd"+ , Toast.LENGTH_SHORT).show();
//                    }
//                });
//                builder.setCancelable(true);   //设置按钮是否可以按返回键取消,false则不可以取消
//
//                AlertDialog dialog_pwd = builder.create();  //创建对话框
//
//                dialog_pwd.setCanceledOnTouchOutside(true);      //设置弹出框失去焦点是否隐藏,即点击屏蔽其它地方是否隐藏
//
//                dialog_pwd.show();
//            }
//        });
//        return convertView;
//    }
//
//    class MyTask extends AsyncTask<String, Integer, String> {
//        @Override
//        protected String doInBackground(String... strings) {
//            String url = "http://" + JudgeResult.ip + ":8080/insertOrderDetail?sellerId=" + JudgeResult.userID + "&productId=" + strings[0] + "&productQuantity=" + strings[1];
//            Log.i("type111", "url:" + url);
//            return this.sendGET(url);
//        }
//
//        public String sendGET(String url) {
//            String result = "";
//            try {
//                URL realurl = new URL(url);
//                Log.i("11111111111111111", "sendGET: " + url);
//                HttpURLConnection conn = (HttpURLConnection) realurl.openConnection();
//                conn.setConnectTimeout(6000);
//                conn.setRequestMethod("GET");
//                InputStream in = conn.getInputStream();
//                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
//                String line = "";
//                while ((line = reader.readLine()) != null) {
//                    result = line;
//                }
//            } catch (MalformedURLException eio) {
//                eio.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            return result;
//        }
//
//        @Override
//        protected void onPostExecute(String s) {
//            //{"code":1,"data":1}
//            try {
//                JSONObject jsonObject = new JSONObject(s);
//                if (jsonObject.getInt("code") == 1) {
//                    Toast.makeText(JudgeResult.toaskApplication, "加入成功!", Toast.LENGTH_LONG).show();
//                    //  Toast.makeText(this.getApplicationContext(), "成功！！！", Toast.LENGTH_SHORT).show();
//                } else {
//                    //  Toast.makeText(this.getApplicationContext(), "失败！！！", Toast.LENGTH_SHORT).show();
//                    Toast.makeText(JudgeResult.toaskApplication, "加入失败,请重试！！", Toast.LENGTH_LONG).show();
//                    //Log.i("iii","失败！！！");
//                }
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//}
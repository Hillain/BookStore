package com.example.bookstore;

import android.content.ClipData;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bookstore.Activity.Login;
import com.example.bookstore.Fragment.CarFragment;
import com.example.bookstore.Fragment.IndexFragment;
import com.example.bookstore.Fragment.LastFragment;
import com.example.bookstore.Fragment.OrderFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private ViewPager mViewPager;
    private FragmentPagerAdapter mAdapter;
    private List<Fragment> mFragments;
    private LinearLayout mTabWechat;
    private LinearLayout mTabFriend;
    private LinearLayout mTabContact;
    private LinearLayout mTabSetting;
    private ImageButton mImgWechat;
    private ImageButton mImgFriend;
    private ImageButton mImgContact;
    private ImageButton mImgSetting;
   private  MenuItem n_home;
   private ImageView tou;
   private TextView username;
    int user_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        //获取navigationView里面的nav_header
        final View headerLayout =navigationView.inflateHeaderView(R.layout.nav_header_main);
        tou=headerLayout.findViewById(R.id.touxiang);
        username=headerLayout.findViewById(R.id.heder_username);
        SharedPreferences userSettings= getSharedPreferences("userinfo", 0);
        user_id= userSettings.getInt("id",0);
        final String user_name=userSettings.getString("username","");
        if(user_id==0){
            username.setText("点击头像登录");
        }else{
            username.setText("欢迎你_"+user_name);
        }
        tou.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(user_id==0||username.getText().equals("点击头像登录")){
                    Intent intent = new Intent(getApplicationContext(), Login.class);
                    startActivity(intent);
                }else {
                    //注销登录
                    SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("userinfo", MODE_PRIVATE); sharedPreferences.edit().clear().commit();
                    username.setText("点击头像登录");
                    Toast.makeText(getApplicationContext(),"已注销",Toast.LENGTH_LONG).show();
                   // headerLayout.invalidate();
                }
            }
        });
        initViews();
        initEvents();
        initDatas();
    }

    View.OnClickListener onClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            resetImgs();
            //根据点击的Tab切换不同的页面及设置对应的ImageButton为绿色
            switch (v.getId()) {
                case R.id.id_tab_wechat:
                    selectTab(0);
                    break;
                case R.id.id_tab_friend:
                    selectTab(1);
                    break;
                case R.id.id_tab_contact:
                    selectTab(2);
                    break;
                case R.id.id_tab_setting:
                    selectTab(3);
                    break;
            }

        }
    };
    private void initViews() {
        mViewPager = (ViewPager) findViewById(R.id.id_viewpager);
        mTabWechat = (LinearLayout) findViewById(R.id.id_tab_wechat);
        mTabFriend = (LinearLayout) findViewById(R.id.id_tab_friend);
        mTabContact = (LinearLayout) findViewById(R.id.id_tab_contact);
        mTabSetting = (LinearLayout) findViewById(R.id.id_tab_setting);
        mImgWechat = (ImageButton) findViewById(R.id.id_tab_wechat_img);
        mImgFriend = (ImageButton) findViewById(R.id.id_tab_friend_img);
        mImgContact = (ImageButton) findViewById(R.id.id_tab_contact_img);
        mImgSetting = (ImageButton) findViewById(R.id.id_tab_setting_img);
    }

    private void selectTab(int i) {
        //根据点击的Tab设置对应的ImageButton为绿色
        switch (i) {
            case 0:
                mImgWechat.setImageResource(R.drawable.tab_weixin_pressed);
                break;
            case 1:
                mImgFriend.setImageResource(R.drawable.tab_car_pressed);
                break;
            case 2:
                mImgContact.setImageResource(R.drawable.tab_order_pressed);
                break;
            case 3:
                mImgSetting.setImageResource(R.drawable.tab_person_pressed);
                break;
        }
        mViewPager.setCurrentItem(i);
    }

    private void resetImgs() {
        mImgWechat.setImageResource(R.drawable.tab_weixin_nomal);
        mImgFriend.setImageResource(R.drawable.tab_car_normal);
        mImgContact.setImageResource(R.drawable.tab_order_normal);
        mImgSetting.setImageResource(R.drawable.tab_person_normal);
    }
    private void initEvents() {
        mTabWechat.setOnClickListener(onClickListener);
        mTabFriend.setOnClickListener(onClickListener);
        mTabContact.setOnClickListener(onClickListener);
        mTabSetting.setOnClickListener(onClickListener);
    }
    private void initDatas() {
        mFragments = new ArrayList<>();
        mFragments.add(new IndexFragment());
        mFragments.add(new CarFragment());
        mFragments.add(new OrderFragment());
        mFragments.add(new LastFragment());
        mAdapter=new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {//从集合中获取对应位置的fragment
                return mFragments.get(position);
            }
            @Override
            public int getCount() {//获取fragment总数
                return mFragments.size();
            }
        };
        mViewPager.setAdapter(mAdapter);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {//页面滚动事件
            }
            @Override
            public void onPageSelected(int position) {
                 mViewPager.setCurrentItem(position);
                 resetImgs();
                 selectTab(position);
            }
            @Override
            public void onPageScrollStateChanged(int i) {
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_home) {
            Log.e("ggg","点击事件？");
        } else if (id == R.id.nav_gallery) {
        } else if (id == R.id.nav_slideshow) {
        } else if (id == R.id.nav_tools) {
        } else if (id == R.id.nav_share) {
        } else if (id == R.id.nav_send) {
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}

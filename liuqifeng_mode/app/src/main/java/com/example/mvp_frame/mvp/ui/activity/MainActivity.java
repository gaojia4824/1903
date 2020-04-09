package com.example.mvp_frame.mvp.ui.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.example.mvp_frame.R;
import com.example.mvp_frame.base.BaseActivity;
import com.example.mvp_frame.mvp.ui.adapter.HomeVpAbapter;
import com.example.mvp_frame.mvp.ui.fragment.HomeFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {

    @BindView(R.id.home_viwepager)
    ViewPager mViewPager;
    @BindView(R.id.home_navigatio)
    BottomNavigationView mBottomNavigationView;

    //初始化监听器,切换tab
    @Override
    protected void initListenner() {
        mBottomNavigationView.setOnNavigationItemSelectedListener(menuItem -> {
            switch (menuItem.getItemId()) {
                case R.id.item_home:
                    mViewPager.setCurrentItem(0);
                    Log.e("tag","MainActivity类的第1个");
                    break;
                case R.id.item_daohang:
                    mViewPager.setCurrentItem(1);
                    Log.e("tag","MainActivity类的第2个");
                    break;
                case R.id.item_tixi:
                    mViewPager.setCurrentItem(2);
                    Log.e("tag","MainActivity类的第3个");
                    break;
                case R.id.item_gongzhonghao:
                    mViewPager.setCurrentItem(3);
                    Log.e("tag","MainActivity类的第4个");
                    break;
            }
            return true;
        });

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //当viewPager页面切换的时候让下面的tab标签跟随切换
                mBottomNavigationView.getMenu().getItem(position).setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    //初始化控件的方法
    @Override
    protected void onViewCreated() {
        //设置适配器加载Fragment
        List<HomeFragment> fragments = getHomeFragment();
        HomeVpAbapter homeVpAbapter = new HomeVpAbapter(getSupportFragmentManager(), fragments);
        mViewPager.setAdapter(homeVpAbapter);
    }

    //把Fragment添加到集合,加载Fragment并复用Fragment
    private List<HomeFragment> getHomeFragment() {
        List<HomeFragment> fragments = new ArrayList<HomeFragment>();
        for (int i = 0; i < 4; i++) {
            HomeFragment homeFragment = new HomeFragment(i);
            fragments.add(homeFragment);
        }
        return fragments;
    }

    //初始化加载布局
    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }


}

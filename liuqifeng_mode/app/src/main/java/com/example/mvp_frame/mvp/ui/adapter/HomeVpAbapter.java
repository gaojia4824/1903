package com.example.mvp_frame.mvp.ui.adapter;

import com.example.mvp_frame.mvp.ui.activity.MainActivity;
import com.example.mvp_frame.mvp.ui.fragment.HomeFragment;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class HomeVpAbapter extends FragmentPagerAdapter {
    List<HomeFragment> mFragments;

    public HomeVpAbapter(@NonNull FragmentManager fm,List<HomeFragment> fragments) {
        super(fm);
        this.mFragments=fragments;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }
}

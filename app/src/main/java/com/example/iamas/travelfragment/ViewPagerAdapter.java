package com.example.iamas.travelfragment;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


public class ViewPagerAdapter extends FragmentPagerAdapter {

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    //프래그먼트 교체를 보여주는 역할
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0: return OneFragment.newInstance();
            case 1: return TwoFragment.newInstance();
            case 2: return ThreeFragment.newInstance();
            case 3: return FourFragment.newInstance();
            case 4: return FiveFragment.newInstance();
            case 5: return SixFragment.newInstance();
            default: return null;
        }

    }

    @Override
    public int getCount() {
        return 6;
    }

    //상단의 탭 레이아웃 인디케이터에 텍스트를 선언해주는것
    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0: return "코스" ;
            case 1: return "쇼핑";
            case 2: return "축제";
            case 3: return "맛집";
            case 4: return "액티비티";
            case 5: return "문화";
            default: return null;
        }
    }

}

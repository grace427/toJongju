package com.example.iamas.travelfragment;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;


public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    final static String TAG = "MainActivity";


    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    //프래그먼트 교체를 보여주는 역할
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return OneFragment.newInstance();
            case 1:
                return TwoFragment.newInstance();
            case 2:
                return ThreeFragment.newInstance();
            case 3:
                return FourFragment.newInstance();
            case 4:
                return FiveFragment.newInstance();
            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        return 5;
    }

    //상단의 탭 레이아웃 인디케이터에 텍스트를 선언해주는것
    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "축제";
            case 1:
                return "맛집";
            case 2:
                return "액티비티";
            case 3:
                return "문화";
            case 4:
                return "쇼핑";
            default:
                return null;
        }
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }
}

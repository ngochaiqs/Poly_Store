package com.poly_store.activity.ui.main;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.poly_store.R;
import com.poly_store.fragment.FragmentDHDaHuy;
import com.poly_store.fragment.FragmentDHDangGiao;
import com.poly_store.fragment.FragmentDHDongGoi;
import com.poly_store.fragment.FragmentDHThanhCong;
import com.poly_store.fragment.FragmentDHXuLy;


/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter2 extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_text_3, R.string.tab_text_4, R.string.tab_text_5, R.string.tab_text_6, R.string.tab_text_7};
    private final Context mContext;

    public SectionsPagerAdapter2(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position){
            case 0:
                fragment = new FragmentDHXuLy();
                break;
            case 1:
                fragment = new FragmentDHDongGoi();
                break;
            case 2:
                fragment = new FragmentDHDangGiao();
                break;
            case 3:
                fragment = new FragmentDHThanhCong();
                break;
            case 4:
                fragment = new FragmentDHDaHuy();
                break;
        }
        return fragment;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        // Show 2 total pages.
        return 5;
    }
}
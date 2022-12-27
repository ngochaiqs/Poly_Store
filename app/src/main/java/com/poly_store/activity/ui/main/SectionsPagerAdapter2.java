package com.poly_store.activity.ui.main;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.poly_store.R;
import com.poly_store.fragment.AoKhoacFragment;
import com.poly_store.fragment.AoSoMiFragment;
import com.poly_store.fragment.AoThunFragment;
import com.poly_store.fragment.QuanJeansFragment;

public class SectionsPagerAdapter2 extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_main_1, R.string.tab_main_2, R.string.tab_main_3, R.string.tab_main_4};
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
                fragment = new AoKhoacFragment();
                break;
            case 1:
                fragment = new AoThunFragment();
                break;
            case 2:
                fragment = new AoSoMiFragment();
                break;
            case 3:
                fragment = new QuanJeansFragment();
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
        return 4;
    }
}
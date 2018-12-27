package com.garcia.adrian.triviaapp.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.garcia.adrian.triviaapp.fragments.CategoryFragment;
import com.garcia.adrian.triviaapp.fragments.HistoryFragment;
import com.garcia.adrian.triviaapp.fragments.ModeFragment;

public class CategoryAdapter extends FragmentPagerAdapter {

    public CategoryAdapter(FragmentManager fm) {
        super(fm);
    }

    private String[] titulos = {"Modo juego", "Categorías", "Historial"};
    private final int count = 3;

    @Override
    public Fragment getItem(int i) {
        switch (i) {
            case 0:
                return new ModeFragment();
            case 1:
                return new CategoryFragment();
            case 2:
                return new HistoryFragment();
            default:
                return new ModeFragment();
        }

    }

    @Override
    public int getCount() {
        return count;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titulos[position];
    }
}

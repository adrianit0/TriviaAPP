package com.garcia.adrian.triviaapp.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.garcia.adrian.triviaapp.fragments.FragmentMenuCategoria;
import com.garcia.adrian.triviaapp.fragments.FragmentMenuHistorial;
import com.garcia.adrian.triviaapp.fragments.FragmentMenuModos;

public class CategoryAdapter extends FragmentPagerAdapter {

    public CategoryAdapter(FragmentManager fm) {
        super(fm);
    }

    private String[] titulos = {"Modo juego", "Categorías", "Historial"};

    @Override
    public Fragment getItem(int i) {
        switch (i) {
            case 0:
                return new FragmentMenuModos();
            case 1:
                return new FragmentMenuCategoria();
            case 2:
                return new FragmentMenuHistorial();
            default:
                return new FragmentMenuModos();
        }

    }

    @Override
    public int getCount() {
        return titulos.length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titulos[position];
    }
}

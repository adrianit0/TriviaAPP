package com.garcia.adrian.triviaapp.activities;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.garcia.adrian.triviaapp.R;
import com.garcia.adrian.triviaapp.adapter.CategoryAdapter;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Contenido del mainActivity
        ViewPager viewPager = findViewById(R.id.viewpager);

        CategoryAdapter preguntaAdapter = new CategoryAdapter(getSupportFragmentManager());

        viewPager.setAdapter(preguntaAdapter);

        // Give the TabLayout the ViewPager
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tablayout);
        tabLayout.setupWithViewPager(viewPager);

    }
}
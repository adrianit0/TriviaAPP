package com.garcia.adrian.triviaapp.activities;

import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.garcia.adrian.triviaapp.R;
import com.garcia.adrian.triviaapp.adapter.CategoryAdapter;
import com.garcia.adrian.triviaapp.fragments.FragmentMenuCategoria;
import com.garcia.adrian.triviaapp.fragments.FragmentMenuHistorial;


public class MainActivity extends AppCompatActivity implements FragmentMenuCategoria.onFragmentLoadedListener, FragmentMenuHistorial.OnHistoricalFragmentLoadedListener  {

    private FragmentMenuHistorial fragment;
    private FragmentMenuHistorial.OnHistoricalDataLoadedListener callback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Contenido del mainActivity
        ViewPager viewPager = findViewById(R.id.viewpager);
        CategoryAdapter preguntaAdapter = new CategoryAdapter(getSupportFragmentManager());
        viewPager.setAdapter(preguntaAdapter);

        // Give the TabLayout the ViewPager
        TabLayout tabLayout = findViewById(R.id.tablayout);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabTextColors(Color.parseColor("#FFFFFF"),Color.parseColor("#AAAAAA"));
    }

    @Override
    // Para que funcione tiene que darse que el fragment historial y categoria esten cargados
    // Por eso hasta que no que no devuelva ambos el callback no se ejecutará.
    public void onFragmentLoaded(FragmentMenuHistorial.OnHistoricalDataLoadedListener callback) {
        this.callback =callback;

        if (fragment!=null)
            fragment.recibirCallback(callback);
    }

    @Override
    public void onHistoricalFragmentLoadedListener(FragmentMenuHistorial fragment) {
        this.fragment = fragment;

        if (callback!=null)
            fragment.recibirCallback(callback);
    }

    /*
    // Testeo del traductor de Google
    // Actualización: Hace falta pagar para una KEY de la API.
    private void traducirTexto () {
        // Instantiates a client
        Translate translate = TranslateOptions.getDefaultInstance().getService();

        // The text to translate
        String text = "Hello, world!";

        // Translates some text into Russian
        Translation translation =
                translate.translate(
                        text,
                        Translate.TranslateOption.sourceLanguage("en"),
                        Translate.TranslateOption.targetLanguage("es"));


        Log.e("Sin traducir: ",  text);
        Log.e("Traducido: ", translation.getTranslatedText());

    }*/
}
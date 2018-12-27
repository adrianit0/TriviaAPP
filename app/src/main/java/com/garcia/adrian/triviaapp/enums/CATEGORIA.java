package com.garcia.adrian.triviaapp.enums;

import android.content.Context;

import com.garcia.adrian.triviaapp.R;


// Un enum con todas las categorias con la ID de la API
public enum CATEGORIA {
    AnyCategory (-1),
    GeneralKnowledge(9),
    Book(10),
    Film(11),
    Music(12),
    MusicalTheatre(13),
    Television(14),
    VideoGame(15),
    BoardGame(16),
    Science(17),
    Computer(18),
    Mathematics (19),
    Mythology (20),
    Sport(21),
    Geography(22),
    History(23),
    Politic(24),
    Art(25),
    Celebrity(26),
    Animal(27),
    Vehicle(28),
    Comic(29),
    Gadget(30),
    AnimeManga(31),
    CartoonAnimation(32);

    private int id;

    CATEGORIA (int id) {
        this.id = id;
    }

    public int getID() {
        return id;
    }

    public String getName (Context res) {
        switch (id) {
            case -1:
                return res.getString(R.string.categoria1);
            case 9:
                return res.getString(R.string.categoria2);
            case 10:
                return res.getString(R.string.categoria3);
            case 11:
                return res.getString(R.string.categoria4);
            case 12:
                return res.getString(R.string.categoria5);
            case 13:
                return res.getString(R.string.categoria6);
            case 14:
                return res.getString(R.string.categoria7);
            case 15:
                return res.getString(R.string.categoria8);
            case 16:
                return res.getString(R.string.categoria9);
            case 17:
                return res.getString(R.string.categoria10);
            case 18:
                return res.getString(R.string.categoria11);
            case 19:
                return res.getString(R.string.categoria12);
            case 20:
                return res.getString(R.string.categoria13);
            case 21:
                return res.getString(R.string.categoria14);
            case 22:
                return res.getString(R.string.categoria15);
            case 23:
                return res.getString(R.string.categoria16);
            case 24:
                return res.getString(R.string.categoria17);
            case 25:
                return res.getString(R.string.categoria18);
            case 26:
                return res.getString(R.string.categoria19);
            case 27:
                return res.getString(R.string.categoria20);
            case 28:
                return res.getString(R.string.categoria21);
            case 29:
                return res.getString(R.string.categoria22);
            case 30:
                return res.getString(R.string.categoria23);
            case 31:
                return res.getString(R.string.categoria24);
            case 32:
                return res.getString(R.string.categoria25);
        }

        return "";
    }
};
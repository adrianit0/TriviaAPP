package com.garcia.adrian.triviaapp.enums;

import android.content.Context;

import com.garcia.adrian.triviaapp.R;
import com.garcia.adrian.triviaapp.fragments.CategoryFragment;


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

    public static CATEGORIA fromID(int id) {
        if (id<9)
            return CATEGORIA.AnyCategory;

        return CATEGORIA.values()[id-8];
    }

    public static CATEGORIA fromString(String cad){
        switch (cad){
            //TODO: Mejorar esta parte
            case "General Knowledge":
                return CATEGORIA.values()[1];
            case "Entertainment: Books":
                return CATEGORIA.values()[2];
            case "Entertainment: Film":
                return CATEGORIA.values()[3];
            case "Entertainment: Music":
                return CATEGORIA.values()[4];
            case "Entertainment: Musicals & Theatres":
                return CATEGORIA.values()[5];
            case "Entertainment: Television":
                return CATEGORIA.values()[6];
            case "Entertainment: Video Games":
                return CATEGORIA.values()[7];
            case "Entertainment: Board Games":
                return CATEGORIA.values()[8];
            case "Science & Nature":
                return CATEGORIA.values()[9];
            case "Science: Computers":
                return CATEGORIA.values()[10];
            case "Science: Mathematics":
                return CATEGORIA.values()[11];
            case "Mythology":
                return CATEGORIA.values()[12];
            case "Sports":
                return CATEGORIA.values()[13];
            case "Geography":
                return CATEGORIA.values()[14];
            case "History":
                return CATEGORIA.values()[15];
            case "Politics":
                return CATEGORIA.values()[16];
            case "Art":
                return CATEGORIA.values()[17];
            case "Celebrities":
                return CATEGORIA.values()[18];
            case "Animals":
                return CATEGORIA.values()[19];
            case "Vehicles":
                return CATEGORIA.values()[20];
            case "Entertainment: Comics":
                return CATEGORIA.values()[21];
            case "Science: Gadgets":
                return CATEGORIA.values()[22];
            case "Entertainment: Japanese Anime & Manga":
                return CATEGORIA.values()[23];
            case "Entertainment: Cartoon & Animations":
                return CATEGORIA.values()[24];
        }

        return CATEGORIA.AnyCategory;
    }
};
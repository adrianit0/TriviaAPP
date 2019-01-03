package com.garcia.adrian.triviaapp.util;

import android.arch.persistence.room.TypeConverter;

import com.garcia.adrian.triviaapp.enums.CATEGORIA;

import java.sql.Date;

public class CategoriaConverter {
    @TypeConverter
    public static CATEGORIA toCategoria(Integer id){
        return id == null ? null: CATEGORIA.values()[id];
    }

    @TypeConverter
    public static Integer fromCategoria(CATEGORIA categoria){
        return categoria == null ? null : categoria.getID();
    }
}

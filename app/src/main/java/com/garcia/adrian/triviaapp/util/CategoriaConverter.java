package com.garcia.adrian.triviaapp.util;

import android.arch.persistence.room.TypeConverter;

import com.garcia.adrian.triviaapp.enums.CATEGORIA;

import java.sql.Date;

public class CategoriaConverter {
    @TypeConverter
    public static CATEGORIA toCategoria(Integer id){
        if (id==-1)
            return CATEGORIA.values()[0];
        return id == null ? null: CATEGORIA.fromID(id);
    }

    @TypeConverter
    public static Integer fromCategoria(CATEGORIA categoria){
        return categoria == null ? null : categoria.getID();
    }
}

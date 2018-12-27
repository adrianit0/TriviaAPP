package com.garcia.adrian.triviaapp.data;

import android.provider.BaseColumns;

public class PartidaContract {

    private PartidaContract() {}

    public class PartidaEntry implements BaseColumns {
        public static final String TABLE_NAME = "partida";
        public static final String NOMBRE_COLUMNA_PARTIDA_CATEGORIA = "categoria";
        public static final String NOMBRE_COLUMNA_PARTIDA_FECHA = "fecha";
        // TODO: Seguir
    }
}

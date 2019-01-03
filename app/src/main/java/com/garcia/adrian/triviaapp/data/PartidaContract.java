package com.garcia.adrian.triviaapp.data;

import android.provider.BaseColumns;

public class PartidaContract {

    private PartidaContract() {}
    // TODO: BORRRAR
    public class PartidaEntry implements BaseColumns {
        public static final String TABLE_NAME = "partida";
        public static final String NOMBRE_COLUMNA_PARTIDA_PUNTUACION = "puntuacion";
        public static final String NOMBRE_COLUMNA_PARTIDA_ACERTADAS = "acertadas";
        public static final String NOMBRE_COLUMNA_PARTIDA_CATEGORIA = "categoria";
        public static final String NOMBRE_COLUMNA_PARTIDA_FECHA = "fecha";
    }

    public class PreguntaEntry implements BaseColumns {
        public static final String TABLE_NAME = "partida";
        public static final String NOMBRE_COLUMNA_PREGUNTA_NOMBRE = "nombre";
        public static final String NOMBRE_COLUMNA_PREGUNTA_TU_RESPUESTA = "tuRespuesta";
        public static final String NOMBRE_COLUMNA_PREGUNTA_RESPUESTA_CORRECTA = "respuestaCorrecta";
        public static final String NOMBRE_COLUMNA_PREGUNTA_PUNTOS = "puntos";
    }
}

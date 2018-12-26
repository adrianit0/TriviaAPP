package com.garcia.adrian.triviaapp;

public class Pregunta {
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
    };

    // Un enum para la dificultad
    public enum DIFICULTAD {
        Any ("", 0), Easy ("easy", 100), Medium ("medium", 200), Hard("hard", 300);

        private String texto;
        private int puntuacion;

        DIFICULTAD (String texto, int puntuacion){
            this.texto = texto;
            this.puntuacion = puntuacion;
        }

        public String getTexto() {
            return texto;
        }

        public int getPuntuacion() {
            return puntuacion;
        }
    };

    private String enunciado;
    private String[] opciones;  // La primera respuesta será siempre la correcta.


    public Pregunta(String enunciado, String[] opciones) {
        this.enunciado = enunciado;
        this.opciones = opciones;
    }

    /**
     * Devuelve las preguntas de manera aleatoria
     * */
    public String[] getOpcionesRandomly() {
        int size = this.opciones.length;

        String[] _opciones = new String[size];

        int[] _valores = new int[size];
        for (int i = 0; i < _valores.length; i++) {
            _valores[i]=i;
        }


        return _opciones;
    }

    public String getEnunciado() {
        return enunciado;
    }

    public void setEnunciado(String enunciado) {
        this.enunciado = enunciado;
    }

    public String[] getOpciones() {
        return opciones;
    }

    public void setOpciones(String[] opciones) {
        this.opciones = opciones;
    }
}

package com.garcia.adrian.triviaapp.model.juego;

/*
* Preguntas para responder y mostrar dentro del juego.
*
* No es el mismo que "Pregunta" pues esta hecho para el historia.
* */
public class PreguntaJuego {

    private int id;             // ID dentro de la partida, del 0 al total.
    private String categoria;
    private String tipo;        // multiple o boolean
    private String dificultad;  // dificultad

    private String enunciado;

    private String[] opciones;  // La primera respuesta será siempre la correcta.


    public PreguntaJuego(int id, String categoria, String tipo, String dificultad, String enunciado, String[] opciones) {
        this.id = id;
        this.categoria = categoria;
        this.tipo = tipo;
        this.dificultad = dificultad;
        this.enunciado = enunciado;
        this.opciones = opciones;
    }

    public PreguntaJuego(int id, String enunciado, String[] opciones) {
        this.id =id;
        this.enunciado = enunciado;
        this.opciones = opciones;

        tipo = "any";
        dificultad = "any";
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

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDificultad() {
        return dificultad;
    }

    public void setDificultad(String dificultad) {
        this.dificultad = dificultad;
    }
}

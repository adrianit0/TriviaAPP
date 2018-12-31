package com.garcia.adrian.triviaapp.model.juego;

import com.garcia.adrian.triviaapp.enums.CATEGORIA;
import com.garcia.adrian.triviaapp.enums.DIFICULTAD;

/*
* Preguntas para responder y mostrar dentro del juego.
*
* No es el mismo que "Pregunta" pues esta hecho para el historia.
* */
public class PreguntaJuego {

    private int id;             // ID dentro de la partida, del 0 al total.
    private CATEGORIA categoria;
    private String tipo;        // multiple o boolean
    private DIFICULTAD dificultad;  // dificultad

    private String enunciado;

    private String[] opciones;  // La primera respuesta será siempre la correcta.
    private int correctAnswer;  // Devuelve la respuesta correcta

    private int yourAnswer;     // Tu respuesta


    public PreguntaJuego(int id, CATEGORIA categoria, String tipo, DIFICULTAD dificultad, String enunciado, String[] opciones) {
        this.id = id;
        this.categoria = categoria;
        this.tipo = tipo;
        this.dificultad = dificultad;
        this.enunciado = enunciado;
        this.opciones = opciones;

        this.yourAnswer=-1;
    }

    public PreguntaJuego(int id, String enunciado, String[] opciones) {
        this.id =id;
        this.enunciado = enunciado;
        this.opciones = opciones;

        tipo = "any";
        dificultad = DIFICULTAD.Any;
        categoria = CATEGORIA.AnyCategory;

        this.yourAnswer=-1;
    }

    /**
     * Devuelve las preguntas de manera aleatoria
     * */
    public String[] getOpcionesRandomly() {
        int size = this.opciones.length;

        String[] _opciones = new String[size];

        int[] _valores = new int[size];
        for (int i = 0; i < size; i++) {
            _valores[i]=i;
        }

        for (int i = 0; i < size; i++){
            int randomPos = (int)(Math.random()*((double)(size-i)));
            int lastPos = size-i-1;
            int aux = _valores[randomPos];
            _valores[randomPos]= _valores[size-i-1];
            _valores[lastPos]=aux;

            if (aux==0)
                correctAnswer=lastPos;
        }

        for (int i = 0; i < size; i++) {
            _opciones[i] = this.opciones[_valores[i]];
        }

        return _opciones;
    }

    public int getCorrectAnswer() {
        return correctAnswer;
    }

    public void setYourAnswer(int yourAnswer) {
        this.yourAnswer = yourAnswer;
    }

    public boolean isCorrect () {
        return correctAnswer==yourAnswer;
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

    public CATEGORIA getCategoria() {
        return categoria;
    }

    public void setCategoria(CATEGORIA categoria) {
        this.categoria = categoria;
    }

    public DIFICULTAD getDificultad() {
        return dificultad;
    }

    public void setDificultad(DIFICULTAD dificultad) {
        this.dificultad = dificultad;
    }
}

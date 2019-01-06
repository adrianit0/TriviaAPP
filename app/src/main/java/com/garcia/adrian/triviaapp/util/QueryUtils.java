package com.garcia.adrian.triviaapp.util;

import android.util.Log;

import com.garcia.adrian.triviaapp.enums.CATEGORIA;
import com.garcia.adrian.triviaapp.enums.DIFICULTAD;
import com.garcia.adrian.triviaapp.model.juego.PreguntaJuego;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

import org.apache.commons.lang3.StringEscapeUtils;

/**
 * Clase que sirve para convertir el JSON extraido de internet en un array de preguntas
 */
public final class QueryUtils {

    private QueryUtils() { }

    public static ArrayList<PreguntaJuego> extractQuestions(String s) {

        ArrayList<PreguntaJuego> preguntas = new ArrayList<>();

        try {

            JSONObject root = new JSONObject(s);
            JSONArray array = root.getJSONArray("results");

            for (int i = 0; i < array.length(); i++) {
                JSONObject preguntaJSON = (JSONObject) array.get(i);

                String category = preguntaJSON.getString("category");
                String type = preguntaJSON.getString("type");
                String difficulty = preguntaJSON.getString("difficulty");
                String question = convertFromHTML(preguntaJSON.getString("question"));

                String[] answers = new String [(type.equals("multiple")?4:2)];
                answers[0] =  convertFromHTML(preguntaJSON.getString("correct_answer")); // Respuesta correcta

                JSONArray incorrect = preguntaJSON.getJSONArray("incorrect_answers");

                for (int k = 0; k < incorrect.length(); k++) {
                    answers[k+1] = convertFromHTML((String) incorrect.get(k));
                }

                PreguntaJuego t = new PreguntaJuego(i, CATEGORIA.fromString(category), type, DIFICULTAD.fromString(difficulty), question, answers);

                preguntas.add(t);
            }
        } catch (JSONException e) {
            Log.e("QueryUtils", "Problem parsing the questions JSON results", e);
        }
        return preguntas;
    }

    private static String convertFromHTML (String texto) {
        return StringEscapeUtils.unescapeHtml4(texto);
    }

    // Captura todos los datos de las preguntas
    public static ArrayList<PreguntaJuego> fetchQuestionData(String requestUrl) {
        URL url = createUrl(requestUrl);

        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e("fetchData::QueryUtils", "Error closing input stream", e);
        }

        ArrayList<PreguntaJuego> preguntas = extractQuestions(jsonResponse);

        return preguntas;
    }

    // Crea la URL
    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e("createUrl::QueryUtils", "Error with creating URL ", e);
        }
        return url;
    }

    // Crea el HTTP request
    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // Tiene que ser 200 para que sea aceptado
            // En cualquier otro caso, dará error
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e("httpRequest::Query", "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e("httpRequest::Query", "Problem retrieving the trivia JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }
}
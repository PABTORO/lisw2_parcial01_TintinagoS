package co.edu.unicauca.saberpro.model;

import java.util.List;
import java.util.ArrayList;

public class Pregunta {
    private int id;
    private String enunciado;
    private List<String> opciones; // debe contener 4 opciones
    private String respuestaCorrecta;
    private String estado; // "borrador", "publicada"

    public Pregunta(int id, String enunciado, List<String> opciones, String respuestaCorrecta, String estado) {
        this.id = id;
        this.enunciado = enunciado;
        // error 1: si opciones llegaba nulo o vacio quedaba sin inicializar
        // solucion: se valida y en ese caso, se crea una lista que este vacia
        if (opciones == null || opciones.isEmpty()) {
            this.opciones = new ArrayList<>();
        } else {
            this.opciones = opciones;
        }
        this.respuestaCorrecta = respuestaCorrecta;
        this.estado = estado;
    }

    public int getId() { return id; }
    public String getEnunciado() { return enunciado; }
    public List<String> getOpciones() { return opciones; }
    public String getRespuestaCorrecta() { return respuestaCorrecta; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}

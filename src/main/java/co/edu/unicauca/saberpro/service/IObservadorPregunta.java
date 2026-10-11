package co.edu.unicauca.saberpro.service;

import co.edu.unicauca.saberpro.model.Pregunta;

public interface IObservadorPregunta {
    void onPreguntaRegistrada(Pregunta p);
}

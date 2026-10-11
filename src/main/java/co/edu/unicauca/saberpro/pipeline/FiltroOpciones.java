package co.edu.unicauca.saberpro.pipeline;

import co.edu.unicauca.saberpro.model.Pregunta;

public class FiltroOpciones implements IFiltroPregunta {
    @Override
    public boolean procesar(Pregunta pregunta) {
        if (pregunta == null || pregunta.getOpciones() == null) {
            return false;
        }
        // error 2: la logica estaba invertida (devolvia false con 4 opciones)
        // solucion: ahora devuelve true cuando hay exactamente 4 opciones
        if (pregunta.getOpciones().size() == 4) {
            return true;
        }
        return false;
    }
}

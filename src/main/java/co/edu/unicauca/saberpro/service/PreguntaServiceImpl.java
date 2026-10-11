package co.edu.unicauca.saberpro.service;

import co.edu.unicauca.saberpro.model.Pregunta;
import co.edu.unicauca.saberpro.pipeline.ValidadorPipeline;
import co.edu.unicauca.saberpro.repository.IPreguntaRepository;
import java.util.ArrayList;
import java.util.List;

public class PreguntaServiceImpl implements IPreguntaService {
    private final IPreguntaRepository repository;
    private final ValidadorPipeline pipeline;
    private final List<IObservadorPregunta> observadores = new ArrayList<>();

    public PreguntaServiceImpl(IPreguntaRepository repository, ValidadorPipeline pipeline) {
        this.repository = repository;
        this.pipeline = pipeline;
    }

    @Override
    public void agregarObservador(IObservadorPregunta observador) {
        observadores.add(observador);
    }

    private void notificarObservadores(Pregunta pregunta) {
        for (IObservadorPregunta observador : observadores) {
            observador.onPreguntaRegistrada(pregunta);
        }
    }

    // error 3: el metodo se llamaba guardarPregunta y no coincicia con IPreguntaService, por eso no compilaba
    // solucion: se debe renombrar a registrarPregunta y se agrego @Override.
    @Override
    public boolean registrarPregunta(Pregunta pregunta) {
        if (pipeline.ejecutar(pregunta)) {
            boolean guardada = repository.guardar(pregunta);
            if (guardada) {
                notificarObservadores(pregunta);
            }
            return guardada;
        }
        return false;
    }

    @Override
    public List<Pregunta> obtenerTodas() {
        return repository.listar();
    }
}

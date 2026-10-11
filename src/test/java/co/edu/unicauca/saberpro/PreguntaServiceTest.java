package co.edu.unicauca.saberpro;

import co.edu.unicauca.saberpro.model.Pregunta;
import co.edu.unicauca.saberpro.pipeline.FiltroEnunciado;
import co.edu.unicauca.saberpro.pipeline.FiltroOpciones;
import co.edu.unicauca.saberpro.pipeline.ValidadorPipeline;
import co.edu.unicauca.saberpro.repository.PreguntaRepositoryImpl;
import co.edu.unicauca.saberpro.service.IPreguntaService;
import co.edu.unicauca.saberpro.service.PreguntaServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PreguntaServiceTest {

    private IPreguntaService service;

    @BeforeEach
    void setUp() {
        ValidadorPipeline pipeline = new ValidadorPipeline();
        pipeline.addFiltro(new FiltroEnunciado());
        pipeline.addFiltro(new FiltroOpciones());
        service = new PreguntaServiceImpl(new PreguntaRepositoryImpl(), pipeline);
    }

    @Test
    void registraPreguntaValida() {
        Pregunta p = new Pregunta(1, "¿Que es un filtro?",
                Arrays.asList("A", "B", "C", "D"), "A", "Borrador");
        assertTrue(service.registrarPregunta(p));
        assertEquals(1, service.obtenerTodas().size());
    }

    @Test
    void rechazaPreguntaConTresOpciones() {
        Pregunta p = new Pregunta(2, "¿Que es un filtro?",
                Arrays.asList("A", "B", "C"), "A", "Borrador");
        assertFalse(service.registrarPregunta(p));
        assertEquals(0, service.obtenerTodas().size());
    }

    @Test
    void notificaObservadorCuandoSeRegistraPregunta() {
        List<Pregunta> notificadas = new ArrayList<>();
        service.agregarObservador(p -> notificadas.add(p));
        Pregunta p = new Pregunta(3, "¿Que es un filtro?",
                Arrays.asList("A", "B", "C", "D"), "A", "Borrador");
        service.registrarPregunta(p);
        assertEquals(1, notificadas.size());
        assertSame(p, notificadas.get(0));
    }

    @Test
    void noNotificaSiLaPreguntaEsRechazada() {
        List<Pregunta> notificadas = new ArrayList<>();
        service.agregarObservador(p -> notificadas.add(p));
        Pregunta p = new Pregunta(4, "¿Que es un filtro?",
                Arrays.asList("A", "B"), "A", "Borrador");
        service.registrarPregunta(p);
        assertTrue(notificadas.isEmpty());
    }

    @Test
    void notificaATodosLosObservadores() {
        List<Pregunta> primero = new ArrayList<>();
        List<Pregunta> segundo = new ArrayList<>();
        service.agregarObservador(p -> primero.add(p));
        service.agregarObservador(p -> segundo.add(p));
        service.registrarPregunta(new Pregunta(5, "¿Que es un filtro?",
                Arrays.asList("A", "B", "C", "D"), "A", "Borrador"));
        assertEquals(1, primero.size());
        assertEquals(1, segundo.size());
    }
}

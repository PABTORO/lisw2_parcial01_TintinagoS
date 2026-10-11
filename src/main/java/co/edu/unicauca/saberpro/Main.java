package co.edu.unicauca.saberpro;

import co.edu.unicauca.saberpro.model.Pregunta;
import co.edu.unicauca.saberpro.pipeline.*;
import co.edu.unicauca.saberpro.repository.*;
import co.edu.unicauca.saberpro.service.*;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== BANCO DE PREGUNTAS SABER PRO ===");

        // dependencias (DIP)
        IPreguntaRepository repository = new PreguntaRepositoryImpl();
        ValidadorPipeline pipeline = new ValidadorPipeline();
        pipeline.addFiltro(new FiltroEnunciado());
        pipeline.addFiltro(new FiltroOpciones());

        // error 4: al servicio se le pasaba null en lugar del pipeline, lo que causaba NullPointerException
        // solucion: se inyecta el pipeline ya configurado con los filtros
        IPreguntaService service = new PreguntaServiceImpl(repository, pipeline);

        // observador: avisa por consola cuando se registra una pregunta
        service.agregarObservador(p -> System.out.println(
                "Notificacion: se registro la pregunta " + p.getId() + " (" + p.getEstado() + ")"));

        // pregunta de prueba
        Pregunta p1 = new Pregunta(
            1,
            "¿Cual es el objetivo principal del patron Pipes and Filters?",
            Arrays.asList("A. Dividir proceso en etapas", "B. Crear BD", "C. Usar MVC", "D. Ninguna"),
            "A. Dividir proceso en etapas",
            "Borrador"
        );

        boolean exito = service.registrarPregunta(p1);
        if (exito) {
            System.out.println(" Pregunta registrada con exito. Total en banco: " + service.obtenerTodas().size());
        } else {
            System.out.println(" No se pudo registrar la pregunta debido a fallos en el pipeline.");
        }
    }
}
